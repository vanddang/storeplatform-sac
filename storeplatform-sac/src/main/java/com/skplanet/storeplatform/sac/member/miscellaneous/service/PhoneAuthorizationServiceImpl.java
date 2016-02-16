package com.skplanet.storeplatform.sac.member.miscellaneous.service;

import com.skplanet.storeplatform.external.client.message.sci.MessageSCI;
import com.skplanet.storeplatform.external.client.message.vo.SmsSendEcReq;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.*;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.vo.CommonCode;
import com.skplanet.storeplatform.sac.member.miscellaneous.vo.ServiceAuth;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 
 * 휴대폰 인증 기능 관련 인터페이스 구현체
 * 
 * Updated on : 2014. 1. 7. Updated by : 김다슬, 인크로스.
 * Updated on : 2015. 12. 28. Updated by : 임근대, SKP. - Micellaneos 클래스에서 Captcha 관련 기능 클래스 분리
 */
@Service
public class PhoneAuthorizationServiceImpl implements PhoneAuthorizationService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PhoneAuthorizationServiceImpl.class);

	@Autowired
	private MessageSCI messageSCI; // 메시지전송 기능 Interface.

	@Autowired
	private MessageSourceAccessor messageSourceAccessor; // Message Properties

	@Autowired
	private MemberCommonComponent mcc;

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDao;

	@Value("#{propertiesForSac['sms.auth.cnt']}")
	private int smsAuthCnt;

	private static final Map<String, String> CARRIER_MNOCD_MAP = new HashMap<String, String>(){
		{
			put("SKT", "US001201");
			put("KTF", "US001202");
			put("LGT", "US001203");
		}
	};


	/**
	 * <pre>
	 * 휴대폰 인증 SMS 발송.
	 * </pre>
	 * 
	 * @param sacRequestHeader
	 *            SacRequestHeader
	 * @param request
	 *            GetPhoneAuthorizationCodeReq
	 * @return GetPhoneAuthorizationCodeRes
	 */
	@Override
	public GetPhoneAuthorizationCodeRes getPhoneAuthorizationCode(SacRequestHeader sacRequestHeader,
			GetPhoneAuthorizationCodeReq request) {

		String tenantId = sacRequestHeader.getTenantHeader().getTenantId();
		String systemId = sacRequestHeader.getTenantHeader().getSystemId();
		String messageText = "auth.message.";
		String messageSender = "auth.message.sendNum.";
		String authCode = "";

		String authMnoCd = CARRIER_MNOCD_MAP.get(request.getCarrier());

		// tenantId 별 prefix
		String prefixMessageText = "";
		// tenantId 별 cdId
		String tenantCdId = "";

		// 3분 이내 동일한 MDN과 SystemID로 요청 여부 확인.
		ServiceAuth confirmSendedSmsReq = new ServiceAuth();
		confirmSendedSmsReq.setAuthMnoCd(authMnoCd);
		confirmSendedSmsReq.setAuthMdn(request.getRecvMdn());
		confirmSendedSmsReq.setAuthTypeCd(MemberConstants.AUTH_TYPE_CD_SMS);
		confirmSendedSmsReq.setAuthCnt(this.smsAuthCnt); // 인증 실패 횟수 3회
		ServiceAuth confirmSendedSmsRes = this.commonDao.queryForObject("ServiceAuth.confirmSendedSms",
				confirmSendedSmsReq, ServiceAuth.class);

		GetPhoneAuthorizationCodeRes response = new GetPhoneAuthorizationCodeRes();

		if (confirmSendedSmsRes != null && confirmSendedSmsRes.getAuthSeq() != null) { // 3분 이내 동일한 MDN과 SystemID로 요청.
			response.setPhoneSign(confirmSendedSmsRes.getAuthSign());
		} else {

			/* 휴대폰 인증 코드 생성 */
			Random random = new Random();
			authCode += random.nextInt(999999); // 0~999999 사이의 난수 발생
			if (authCode.length() < 6) {
				int loop = 6 - authCode.length();
				for (int i = 0; i < loop; i++) { // 첫째자리수가 0으로 시작할 경우 "0"값을 앞에 추가.
					authCode = "0" + authCode;
				}
			}
			Object[] object = new Object[1];
			object[0] = authCode;

			if (MemberConstants.TENANT_ID_TSTORE.equals(tenantId)
					|| MemberConstants.TENANT_ID_OLLEH_MARKET.equals(tenantId)
					|| MemberConstants.TENANT_ID_UPLUS_STORE.equals(tenantId)) {

				messageText += "common";

				// tenantId 별 설정
				if (MemberConstants.TENANT_ID_TSTORE.equals(tenantId)) {
					tenantCdId = MemberConstants.SMS_SENDER_TENANT_ID_TSTORE;
					messageSender += "tstore";

				} else if (MemberConstants.TENANT_ID_OLLEH_MARKET.equals(tenantId)) {
					tenantCdId = MemberConstants.SMS_SENDER_TENANT_ID_OLLEH_MARKET;
					messageSender += "kstore";

				} else if (MemberConstants.TENANT_ID_UPLUS_STORE.equals(tenantId)) {
					tenantCdId = MemberConstants.SMS_SENDER_TENANT_ID_UPLUS_STORE;
					messageSender += "ustore";

				}

				// SMS발송 테넌트 구분 코드 목록 조회
				List<CommonCode> codes = this.mcc.getCommonCode(MemberConstants.SMS_SENDER_GRP_CD);

				for (CommonCode commonCode : codes) {
					if (tenantCdId.equals(commonCode.getCdId())) {
						// tenant별 prefix 문구 설정
						prefixMessageText = "[" + commonCode.getCdNm() + "]";
						break;
					}
				}

			} else if (MemberConstants.TENANT_ID_NON_SPECIFIC.equals(tenantId)
					&& MemberConstants.SYSTEM_ID_DEV_POC.equals(systemId)) {
				messageText += "dev";
				messageSender += "dev";
			} else { // default
				messageText += "default";
				messageSender += "default";
			}

			messageText = this.messageSourceAccessor.getMessage(messageText, object, LocaleContextHolder.getLocale());
			messageSender = this.messageSourceAccessor.getMessage(messageSender, object,
					LocaleContextHolder.getLocale());

			LOGGER.debug("## [SAC] prefixMessageText: {}, messageText : {}, messageSender : {}", prefixMessageText,
					messageText, messageSender);

			/* 인증 Signautre 생성 - guid 형식 */
			String authSign = UUID.randomUUID().toString().replace("-", "");
			LOGGER.debug("## [SAC] authSign : {}", authSign);

			/* DB에 저장할 파라미터 셋팅 */
			ServiceAuth serviceAuthInfo = new ServiceAuth();
			serviceAuthInfo.setAuthMnoCd(authMnoCd);
			serviceAuthInfo.setAuthMdn(request.getRecvMdn()); // 6/26 TB_CM_SVC_AUTH 신규 컬럼.
			serviceAuthInfo.setAuthTypeCd(MemberConstants.AUTH_TYPE_CD_SMS);
			serviceAuthInfo.setAuthSign(authSign);
			serviceAuthInfo.setAuthValue(authCode);

			this.commonDao.insert("ServiceAuth.createServiceAuthCode", serviceAuthInfo);

			/* External Comp.에 SMS 발송 요청 */
			SmsSendEcReq smsReq = new SmsSendEcReq();
			smsReq.setSrcId(request.getSrcId());
			smsReq.setSendMdn(messageSender);
			smsReq.setRecvMdn(request.getRecvMdn());
			smsReq.setTeleSvcId(request.getTeleSvcId());
			// tenantId 별 prefix 설정
			smsReq.setMsg(StringUtils.isNotEmpty(prefixMessageText) ? prefixMessageText + messageText : messageText);
			// 통신사정보 Optional
			smsReq.setCarrier(StringUtils.defaultIfBlank(request.getCarrier(), null));
			// 발송_순서 | 1~9, 1: 높음, 9:낮음
			smsReq.setSendOrder(MemberConstants.SMS_SEND_ORDER_FIRST);

			LOGGER.debug("[PhoneAuthorizationService.getPhoneAuthorizationCode] SAC->SMS 발송 Request : {}", smsReq);
			this.messageSCI.smsSend(smsReq);

			response.setPhoneSign(authSign);
		}
		LOGGER.debug("## Response : {}", response);
		return response;
	}

	/**
	 * <pre>
	 * 휴대폰 인증 코드 확인.
	 * </pre>
	 * 
	 * @param sacRequestHeader
	 *            SacRequestHeader
	 * @param request
	 *            ConfirmPhoneAuthorizationCodeReq
	 * @return ConfirmPhoneAuthorizationCodeRes
	 */
	@Override
	public ConfirmPhoneAuthorizationCodeRes confirmPhoneAutorizationCode(SacRequestHeader sacRequestHeader,
			ConfirmPhoneAuthorizationCodeReq request) {

		ConfirmPhoneAuthorizationCodeRes res = new ConfirmPhoneAuthorizationCodeRes();
		String authCode = request.getPhoneAuthCode();
		String authSign = request.getPhoneSign();
		String userPhone = request.getUserPhone();

		/* DB에 저장할 파라미터 셋팅 */
		ServiceAuth serviceAuthInfo = new ServiceAuth();
		serviceAuthInfo.setAuthTypeCd(MemberConstants.AUTH_TYPE_CD_SMS);
		serviceAuthInfo.setAuthSign(authSign);
		serviceAuthInfo.setAuthValue(authCode);
		serviceAuthInfo.setTimeToLive(request.getTimeToLive());
		// 인증정보 확인시 MDN 추가 확인 (2014.10.01)
		serviceAuthInfo.setAuthMdn(request.getUserPhone());

		ServiceAuth resultInfo = this.commonDao.queryForObject("ServiceAuth.searchPhoneAuthInfo", serviceAuthInfo,
				ServiceAuth.class);

		if (resultInfo == null) {
			// (인증코드 불일치, 인증Sign 불일치, 인증정보 없음), 인증 실패 카운트 update
			serviceAuthInfo.setAuthMdn(userPhone);
			int authCnt = -1;
			Object authCntObj = this.commonDao.queryForObject("ServiceAuth.searchPhoneAuthCnt", serviceAuthInfo);
			if (null != authCntObj) {
				authCnt = (Integer) authCntObj + 1;
				if (this.smsAuthCnt <= authCnt) {
					LOGGER.debug("smsAuthCnt : " + this.smsAuthCnt);
					LOGGER.debug("authCnt : " + authCnt);
					serviceAuthInfo.setAuthComptYn("F"); // 실패 처리 카운트(프로퍼티) 값과 처리전 카운트 + 1 값이 같으면 실패 처리 : F
				}
				this.commonDao.update("ServiceAuth.updateServiceAuthCnt", serviceAuthInfo);
			}

			if (authCnt >= this.smsAuthCnt) {
				throw new StorePlatformException("SAC_MEM_3005", this.smsAuthCnt); // 인증 실패 횟수 3회 이상
			} else {
				throw new StorePlatformException("SAC_MEM_3003");
			}
		}

		if (MemberConstants.USE_Y.equals(resultInfo.getAuthComptYn())) {
			throw new StorePlatformException("SAC_MEM_3001");
		}

		if (StringUtils.isNotBlank(resultInfo.getCurrDt()) && Double.parseDouble(resultInfo.getCurrDt()) < 0) {
			throw new StorePlatformException("SAC_MEM_3002");
		}

		if ("F".equals(resultInfo.getAuthComptYn())) {
			throw new StorePlatformException("SAC_MEM_3003");
		}

		String authSeq = resultInfo.getAuthSeq();
		if (StringUtils.isNotBlank(authSeq)) {
			this.commonDao.update("ServiceAuth.updateServiceAuthYn", authSeq);
		}
		res.setUserPhone(userPhone);
		LOGGER.debug("## Response : {}", res);
		return res;
	}

	/**
	 * <pre>
	 * 2.3.17.	휴대폰 인증 여부 확인.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            ConfirmPhoneAuthorizationCheckReq
	 * @return ConfirmPhoneAuthorizationCheckRes
	 */
	@Override
	public ConfirmPhoneAuthorizationCheckRes confirmPhoneAutorizationCheck(SacRequestHeader header,
			ConfirmPhoneAuthorizationCheckReq req) {
		String userPhone = req.getUserPhone();

		ServiceAuth serviceAuthInfo = new ServiceAuth();
		serviceAuthInfo.setAuthTypeCd(MemberConstants.AUTH_TYPE_CD_SMS);
		serviceAuthInfo.setAuthMdn(userPhone);
		serviceAuthInfo.setAuthSign(req.getPhoneSign());

		ServiceAuth resultInfo = this.commonDao.queryForObject("ServiceAuth.searchPhoneAuthCheck", serviceAuthInfo,
				ServiceAuth.class);

		if (resultInfo == null) {
			throw new StorePlatformException("SAC_MEM_0002", "휴대폰 인증");
		}

		ConfirmPhoneAuthorizationCheckRes res = new ConfirmPhoneAuthorizationCheckRes();
		res.setUserPhone(userPhone);
		res.setComptYn(resultInfo.getAuthComptYn());
		res.setRegDt(resultInfo.getRegDt());
		// 인증번호만 요청한 상태 공백처리
		res.setUpdDt(StringUtils.defaultIfEmpty(resultInfo.getUpdDt(), ""));
		return res;
	}
}
