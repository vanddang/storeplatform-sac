package com.skplanet.storeplatform.sac.member.miscellaneous.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import com.skplanet.pdp.sentinel.shuttle.TLogSentinelShuttle;
import com.skplanet.storeplatform.external.client.idp.sci.IdpSCI;
import com.skplanet.storeplatform.external.client.idp.sci.ImageSCI;
import com.skplanet.storeplatform.external.client.idp.vo.ImageReq;
import com.skplanet.storeplatform.external.client.idp.vo.ImageRes;
import com.skplanet.storeplatform.external.client.idp.vo.JoinSupServiceRequestEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.JoinSupServiceRequestEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.ServiceSubscriptionCheckEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.ServiceSubscriptionCheckEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.WaterMarkAuthEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.WaterMarkAuthImageEcRes;
import com.skplanet.storeplatform.external.client.inicis.sci.InicisSCI;
import com.skplanet.storeplatform.external.client.inicis.vo.InicisAuthAccountEcReq;
import com.skplanet.storeplatform.external.client.inicis.vo.InicisAuthAccountEcRes;
import com.skplanet.storeplatform.external.client.message.sci.MessageSCI;
import com.skplanet.storeplatform.external.client.message.vo.SmsSendEcReq;
import com.skplanet.storeplatform.external.client.uaps.sci.UapsSCI;
import com.skplanet.storeplatform.external.client.uaps.vo.UafmapEcRes;
import com.skplanet.storeplatform.external.client.uaps.vo.UapsEcReq;
import com.skplanet.storeplatform.external.client.uaps.vo.UserEcRes;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil.ShuttleSetter;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.common.vo.LimitTarget;
import com.skplanet.storeplatform.member.client.common.vo.RemovePolicyRequest;
import com.skplanet.storeplatform.member.client.common.vo.RemovePolicyResponse;
import com.skplanet.storeplatform.member.client.common.vo.SearchPolicyRequest;
import com.skplanet.storeplatform.member.client.common.vo.SearchPolicyResponse;
import com.skplanet.storeplatform.member.client.common.vo.UpdatePolicyRequest;
import com.skplanet.storeplatform.member.client.common.vo.UpdatePolicyResponse;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSCI;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateDCDRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.DCDInfo;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserResponse;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.AuthorizeAccountReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.AuthorizeAccountRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmCaptchaReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmCaptchaRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmEmailAuthorizationCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmEmailAuthorizationCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmPhoneAuthorizationCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmPhoneAuthorizationCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.CreateAdditionalServiceReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.CreateAdditionalServiceRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.CreateDCDReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.CreateDCDRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.CreateIndividualPolicyReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.CreateIndividualPolicyRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetAdditionalServiceReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetAdditionalServiceRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetCaptchaRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetEmailAuthorizationCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetEmailAuthorizationCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetIndividualPolicyReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetIndividualPolicyRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetModelCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetModelCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetOpmdReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetOpmdRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetPhoneAuthorizationCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetPhoneAuthorizationCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetUaCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetUaCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.IndividualPolicyInfo;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.RemoveIndividualPolicyReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.RemoveIndividualPolicyRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.vo.Device;
import com.skplanet.storeplatform.sac.member.miscellaneous.vo.ServiceAuth;

/**
 * 
 * 기타 기능 관련 인터페이스 구현체
 * 
 * Updated on : 2014. 1. 7. Updated by : 김다슬, 인크로스.
 */
@Service
public class MiscellaneousServiceImpl implements MiscellaneousService {
	private static final Logger LOGGER = LoggerFactory.getLogger(MiscellaneousServiceImpl.class);

	@Autowired
	private MemberCommonComponent commonComponent; // 회원 공통기능 컴포넌트

	@Autowired
	private UserSCI userSCI; // 회원 Component 사용자 기능 Interface.
	@Autowired
	private DeviceSCI deviceSCI; // 회원 Component 휴대기기 기능 Interface.

	@Autowired
	private IdpSCI idpSCI; // IDP 연동 Interface.
	@Autowired
	private UapsSCI uapsSCI; // UAPS 연동 Interface.
	@Autowired
	private MessageSCI messageSCI; // 메시지전송 기능 Interface.
	@Autowired
	private ImageSCI imageSCI; // 이미지를 String으로 변환 Interface.
	@Autowired
	private InicisSCI inicisSCI; // 이니시스 연동 Interface.

	@Autowired
	private MessageSourceAccessor messageSourceAccessor; // Message Properties

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDao;

	@Value("#{propertiesForSac['sms.auth.cnt']}")
	private int smsAuthCnt;

	/**
	 * <pre>
	 * OPMD 모회선 번호 조회.
	 * </pre>
	 * 
	 * @param req
	 *            GetOpmdReq
	 * @return GetOpmdRes
	 */
	@Override
	public GetOpmdRes getOpmd(GetOpmdReq req) {
		GetOpmdRes res = new GetOpmdRes();
		res.setMsisdn(this.commonComponent.getOpmdMdnInfo(req.getMsisdn()));
		return res;
	}

	/**
	 * <pre>
	 * UA 코드 정보 조회.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            GetUaCodeReq
	 * @return GetUaCodeRes
	 */
	@Override
	public GetUaCodeRes getUaCode(SacRequestHeader requestHeader, GetUaCodeReq req) {
		String deviceModelNo = req.getDeviceModelNo();
		String msisdn = req.getMsisdn();
		String userKey = null;

		/* 헤더 정보 셋팅 */
		CommonRequest commonRequest = this.commonComponent.getSCCommonRequest(requestHeader);

		GetUaCodeRes response = new GetUaCodeRes();
		/* 파라미터로 MSISDN만 넘어온 경우 */
		if (StringUtils.isNotBlank(msisdn) && StringUtils.isBlank(deviceModelNo)) {
			SearchUserRequest searchUserRequest = new SearchUserRequest();
			SearchDeviceRequest searchDeviceRequest = new SearchDeviceRequest();

			/* SC 공통헤더 Request 파라미터 셋팅 */
			searchUserRequest.setCommonRequest(commonRequest);
			searchDeviceRequest.setCommonRequest(commonRequest);

			List<KeySearch> keySearchList = new ArrayList<KeySearch>();
			KeySearch keySearch = new KeySearch();
			keySearch.setKeyType(MemberConstants.KEY_TYPE_DEVICE_ID);
			keySearch.setKeyString(msisdn);
			keySearchList.add(keySearch);
			searchUserRequest.setKeySearchList(keySearchList);

			/* deviceId(msisdn)로 userKey 조회 - SC 회원 "회원 기본 정보 조회" */
			SearchUserResponse searchUserResponse = this.userSCI.searchUser(searchUserRequest);

			if (searchUserResponse == null || searchUserResponse.getUserMbr() == null) {
				throw new StorePlatformException("SAC_MEM_0003", "deviceId", msisdn);
			} else {
				userKey = searchUserResponse.getUserMbr().getUserKey();
				LOGGER.debug("[MiscellaneousService.getUaCode] SC Response : {}", searchUserResponse.getUserMbr());
			}

			searchDeviceRequest.setKeySearchList(keySearchList);
			searchDeviceRequest.setUserKey(userKey);

			/* deviceId와 userKey로 deviceModelNo 조회 */
			SearchDeviceResponse searchDeviceResult = this.deviceSCI.searchDevice(searchDeviceRequest);

			/* deviceModelNo 조회 결과 확인 */
			if (searchDeviceResult != null
					&& StringUtils.isNotBlank(searchDeviceResult.getUserMbrDevice().getDeviceModelNo())) {
				deviceModelNo = searchDeviceResult.getUserMbrDevice().getDeviceModelNo();
			} else {
				throw new StorePlatformException("SAC_MEM_3402", "msisdn", msisdn);
			}
		}

		if (StringUtils.isNotBlank(deviceModelNo)) { // deviceModelNo 가 파라미터로 들어온 경우
			// DB 접속(TB_CM_DEVICE) - UaCode 조회
			String uaCode = this.commonDao.queryForObject("Miscellaneous.getUaCode", deviceModelNo, String.class);
			if (StringUtils.isNotBlank(uaCode)) {
				response.setUaCd(uaCode);
			} else {
				throw new StorePlatformException("SAC_MEM_3401", "deviceModelNo", deviceModelNo);
			}
		}
		return response;
	}

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

		// 3분 이내 동일한 MDN과 SystemID로 요청 여부 확인.
		ServiceAuth confirmSendedSmsReq = new ServiceAuth();
		confirmSendedSmsReq.setTenantId(tenantId);
		confirmSendedSmsReq.setSystemId(systemId);
		confirmSendedSmsReq.setAuthMdn(request.getRecvMdn());
		confirmSendedSmsReq.setAuthTypeCd(MemberConstants.AUTH_TYPE_CD_SMS);
		confirmSendedSmsReq.setAuthCnt(this.smsAuthCnt); // 인증 실패 횟수 3회
		ServiceAuth confirmSendedSmsRes = this.commonDao.queryForObject("Miscellaneous.confirmSendedSms",
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

			if (MemberConstants.TENANT_ID_TSTORE.equals(tenantId)) {
				messageText += "tstore";
				messageSender += "tstore";
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
			LOGGER.debug("## [SAC] messageText : {}, messageSender : {}", messageText, messageSender);

			/* 인증 Signautre 생성 - guid 형식 */
			String authSign = UUID.randomUUID().toString().replace("-", "");
			LOGGER.debug("## [SAC] authSign : {}", authSign);

			/* DB에 저장할 파라미터 셋팅 */
			ServiceAuth serviceAuthInfo = new ServiceAuth();
			serviceAuthInfo.setTenantId(tenantId);
			serviceAuthInfo.setSystemId(systemId); // 6/26 TB_CM_SVC_AUTH 신규 컬럼.
			serviceAuthInfo.setAuthMdn(request.getRecvMdn()); // 6/26 TB_CM_SVC_AUTH 신규 컬럼.
			serviceAuthInfo.setAuthTypeCd(MemberConstants.AUTH_TYPE_CD_SMS);
			serviceAuthInfo.setAuthSign(authSign);
			serviceAuthInfo.setAuthValue(authCode);

			this.commonDao.insert("Miscellaneous.createServiceAuthCode", serviceAuthInfo);

			/* External Comp.에 SMS 발송 요청 */
			SmsSendEcReq smsReq = new SmsSendEcReq();
			smsReq.setSrcId(request.getSrcId());
			smsReq.setSendMdn(messageSender);
			smsReq.setRecvMdn(request.getRecvMdn());
			smsReq.setTeleSvcId(request.getTeleSvcId());
			smsReq.setMsg(messageText);
			// 통신사정보 Optional
			smsReq.setCarrier(StringUtils.defaultIfBlank(request.getCarrier(), null));

			LOGGER.debug("[MiscellaneousService.getPhoneAuthorizationCode] SAC->SMS 발송 Request : {}", smsReq);
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
		String tenantId = sacRequestHeader.getTenantHeader().getTenantId();
		String systemId = sacRequestHeader.getTenantHeader().getSystemId();

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

		ServiceAuth resultInfo = this.commonDao.queryForObject("Miscellaneous.searchPhoneAuthInfo", serviceAuthInfo,
				ServiceAuth.class);

		if (resultInfo == null) {
			// (인증코드 불일치, 인증Sign 불일치, 인증정보 없음), 인증 실패 카운트 update
			serviceAuthInfo.setAuthMdn(userPhone);
			serviceAuthInfo.setTenantId(tenantId);
			serviceAuthInfo.setSystemId(systemId);
			int authCnt = -1;
			Object authCntObj = this.commonDao.queryForObject("Miscellaneous.searchPhoneAuthCnt", serviceAuthInfo);
			if (null != authCntObj) {
				authCnt = (Integer) authCntObj + 1;
				if (this.smsAuthCnt <= authCnt) {
					LOGGER.debug("smsAuthCnt : " + this.smsAuthCnt);
					LOGGER.debug("authCnt : " + authCnt);
					serviceAuthInfo.setAuthComptYn("F"); // 실패 처리 카운트(프로퍼티) 값과 처리전 카운트 + 1 값이 같으면 실패 처리 : F
				}
				this.commonDao.update("Miscellaneous.updateServiceAuthCnt", serviceAuthInfo);
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
			this.commonDao.update("Miscellaneous.updateServiceAuthYn", authSeq);
		}
		res.setUserPhone(userPhone);
		LOGGER.debug("## Response : {}", res);
		return res;
	}

	/**
	 * <pre>
	 * Captcha 문자 발급.
	 * </pre>
	 * 
	 * @return GetCaptchaRes
	 */
	@Override
	public GetCaptchaRes getCaptcha() {
		String waterMarkImageUrl = "";
		String waterMarkImageSign = "";
		String waterMarkImageString = "";
		String signData = "";
		GetCaptchaRes response = new GetCaptchaRes();

		/* IDP 연동해서 waterMarkImage URL과 Signature 받기 */

		WaterMarkAuthImageEcRes waterMarkAuthImageEcRes = this.idpSCI.warterMarkImageUrl();

		if (waterMarkAuthImageEcRes != null && StringUtils.isNotBlank(waterMarkAuthImageEcRes.getImageUrl())) {
			waterMarkImageUrl = waterMarkAuthImageEcRes.getImageUrl();
			waterMarkImageSign = waterMarkAuthImageEcRes.getImageSign();
			signData = waterMarkAuthImageEcRes.getSignData();

			LOGGER.debug("[MiscellaneousService.getCaptcha] SAC<-IDP Response : {}", waterMarkAuthImageEcRes);

			String urlPath = waterMarkImageUrl.substring(waterMarkImageUrl.indexOf("/watermark"));

			ImageReq req = new ImageReq();
			req.setProtocol(waterMarkImageUrl.substring(0, 5).equals("https") ? "https" : "http"); // HTTP or HTTPS
			req.setUrlPath(urlPath);
			ImageRes imageRes = this.imageSCI.convert(req);

			if (imageRes != null && StringUtils.isNotBlank(imageRes.getImgData())) {
				waterMarkImageString = imageRes.getImgData();
			}
		}

		response.setImageData(waterMarkImageString);
		response.setImageSign(waterMarkImageSign);
		response.setSignData(signData);

		return response;
	}

	/**
	 * <pre>
	 * Captcha 문자 확인.
	 * </pre>
	 * 
	 * @param request
	 *            ConfirmCaptchaReq
	 * @return ConfirmCaptchaRes
	 */
	@Override
	public ConfirmCaptchaRes confirmCaptcha(ConfirmCaptchaReq request) {

		WaterMarkAuthEcReq waterMarkAuthEcReq = new WaterMarkAuthEcReq();
		waterMarkAuthEcReq.setUserCode(request.getAuthCode());
		waterMarkAuthEcReq.setImageSign(request.getImageSign());
		waterMarkAuthEcReq.setSignData(request.getSignData());

		this.idpSCI.waterMarkAuth(waterMarkAuthEcReq);

		ConfirmCaptchaRes response = new ConfirmCaptchaRes();
		return response;
	}

	/**
	 * <pre>
	 * 이메일 인증 코드 생성.
	 * </pre>
	 * 
	 * @param sacRequestHeader
	 *            SacRequestHeader
	 * @param request
	 *            GetEmailAuthorizationCodeReq
	 * @return GetEmailAuthorizationCodeRes
	 */
	@Override
	public GetEmailAuthorizationCodeRes getEmailAuthorizationCode(SacRequestHeader sacRequestHeader,
			GetEmailAuthorizationCodeReq request) {

		String tenantId = sacRequestHeader.getTenantHeader().getTenantId();
		String systemId = sacRequestHeader.getTenantHeader().getSystemId();

		ServiceAuth serviceAuthReq = new ServiceAuth();
		serviceAuthReq.setAuthEmail(request.getUserEmail());
		serviceAuthReq.setMbrNo(request.getUserKey());

		// 1. 기존 인증코드 발급 여부 및 인증 여부 확인
		ServiceAuth authYnInfo = this.commonDao.queryForObject("Miscellaneous.searchEmailAuthYn", serviceAuthReq,
				ServiceAuth.class);

		// 2. 이메일 인증 코드 생성 - GUID 수준의 난수
		String authCode = UUID.randomUUID().toString().replace("-", "");
		LOGGER.debug("## authCode : {}", authCode);

		// 3. DB에 저장(TB_CM_SVC_AUTH)
		ServiceAuth serviceAuthInfo = new ServiceAuth();
		serviceAuthInfo.setAuthValue(authCode);
		serviceAuthInfo.setAuthEmail(request.getUserEmail());

		if (authYnInfo == null) {
			serviceAuthInfo.setTenantId(tenantId);
			serviceAuthInfo.setSystemId(systemId);
			serviceAuthInfo.setMbrNo(request.getUserKey());
			serviceAuthInfo.setAuthTypeCd(MemberConstants.AUTH_TYPE_CD_EMAIL);
			serviceAuthInfo.setAuthSign("EmailAuthorization"); // 의미 없음. DB에 AUTH_SIGN 이 "NOT NULL"로 정의되어있음.

			this.commonDao.insert("Miscellaneous.createServiceAuthCode", serviceAuthInfo);
			LOGGER.debug("인증코드 신규발급. authCode : {}", authCode);
		} else {
			// 미인증 상태의 인증코드 존재 - 신규 발급 코드로 업데이트.
			serviceAuthInfo.setAuthSeq(authYnInfo.getAuthSeq());
			this.commonDao.update("Miscellaneous.updateServiceAuthCode", serviceAuthInfo);
		}

		// 4. 인증코드 Response
		GetEmailAuthorizationCodeRes response = new GetEmailAuthorizationCodeRes();
		response.setEmailAuthCode(authCode);

		return response;
	}

	/**
	 * <pre>
	 * 이메일 인증 코드 확인.
	 * </pre>
	 * 
	 * @param request
	 *            ConfirmEmailAuthorizationCodeReq
	 * @return ConfirmEmailAuthorizationCodeRes
	 */
	@Override
	public ConfirmEmailAuthorizationCodeRes confirmEmailAuthorizationCode(ConfirmEmailAuthorizationCodeReq request) {

		// 1. 인증 코드로 DB 확인하여 , 회원 key, 회원 email 조회
		String authValue = request.getEmailAuthCode();
		String timeToLive = request.getTimeToLive();

		ServiceAuth serviceAuthReq = new ServiceAuth();
		serviceAuthReq.setAuthValue(authValue);
		serviceAuthReq.setTimeToLive(timeToLive);
		serviceAuthReq.setAuthTypeCd(MemberConstants.AUTH_TYPE_CD_EMAIL);
		ServiceAuth serviceAuthInfo = this.commonDao.queryForObject("Miscellaneous.searchEmailAuthInfo",
				serviceAuthReq, ServiceAuth.class);

		/** 2. 인증코드 정보가 존재할 경우, 인증 처리 */
		if (serviceAuthInfo != null) {

			if (MemberConstants.USE_Y.equals(serviceAuthInfo.getAuthComptYn())) { // 기존 인증된 코드일 경우
				throw new StorePlatformException("SAC_MEM_3001");
			}

			/** timeToLive 값이 존재 할 경우 인증코드 유효기간 검사 */
			if (StringUtils.isNotBlank(timeToLive) && StringUtils.isNotBlank(serviceAuthInfo.getCurrDt())
					&& (Double.parseDouble(serviceAuthInfo.getCurrDt()) < 0)) {
				throw new StorePlatformException("SAC_MEM_3002");
			}

			String authSeq = serviceAuthInfo.getAuthSeq();
			this.commonDao.update("Miscellaneous.updateServiceAuthYn", authSeq);
			LOGGER.debug("## 인증 완료.");
		} else {
			throw new StorePlatformException("SAC_MEM_3003"); // 해당 인증코드가 DB Table에 존재하지 않음.
		}
		ConfirmEmailAuthorizationCodeRes response = new ConfirmEmailAuthorizationCodeRes();
		response.setUserEmail(serviceAuthInfo.getAuthEmail());
		response.setUserKey(serviceAuthInfo.getMbrNo());

		return response;
	}

	/**
	 * <pre>
	 * 부가서비스 가입.
	 * </pre>
	 * 
	 * @param request
	 *            CreateAdditionalServiceReq
	 * @return CreateAdditionalServiceRes
	 */
	@Override
	public CreateAdditionalServiceRes regAdditionalService(CreateAdditionalServiceReq request) {

		CreateAdditionalServiceRes response = new CreateAdditionalServiceRes();

		JoinSupServiceRequestEcReq joinSupServiceEcReq = new JoinSupServiceRequestEcReq();
		joinSupServiceEcReq.setSvcCode(request.getSvcCode());
		joinSupServiceEcReq.setUserMdn(request.getMsisdn());
		joinSupServiceEcReq.setUserSvcMngNum(request.getSvcMngNum());

		/* FDS LOG START */
		final String deviceId = joinSupServiceEcReq.getUserMdn();
		final String serviceCode = joinSupServiceEcReq.getSvcCode();

		new TLogUtil().set(new ShuttleSetter() {
			@Override
			public void customize(TLogSentinelShuttle shuttle) {
				shuttle.log_id("TL_SAC_MEM_0003").device_id(deviceId).service_code(serviceCode);
				;
			}
		});
		/* FDS LOG END */

		JoinSupServiceRequestEcRes joinSupServiceEcRes = this.idpSCI.joinSupServiceRequest(joinSupServiceEcReq);

		response.setSvcCode(serviceCode); // 부가서비스 코드
		response.setMsisdn(joinSupServiceEcRes.getUserMdn()); // 사용자 휴대폰번호

		return response;
	}

	/**
	 * <pre>
	 * 부가서비스 가입 조회.
	 * </pre>
	 * 
	 * @param request
	 *            GetAdditionalServiceReq
	 * @return GetAdditionalServiceRes
	 */
	@Override
	public GetAdditionalServiceRes getAdditionalService(GetAdditionalServiceReq request) {

		ServiceSubscriptionCheckEcReq serviceSubscriptionCheckEcReq = new ServiceSubscriptionCheckEcReq();
		serviceSubscriptionCheckEcReq.setUserMdn(request.getMsisdn());
		serviceSubscriptionCheckEcReq.setSvcCode(request.getSvcCode());
		ServiceSubscriptionCheckEcRes serviceSubscriptionCheckEcRes = this.idpSCI
				.serviceSubscriptionCheck(serviceSubscriptionCheckEcReq);

		LOGGER.debug("[MiscellaneousService.getAdditionalService] SAC<-IDP Response {}", serviceSubscriptionCheckEcRes);

		GetAdditionalServiceRes response = new GetAdditionalServiceRes();
		response.setMsisdn(serviceSubscriptionCheckEcRes.getUserMdn());
		response.setSvcCode(serviceSubscriptionCheckEcRes.getSvcCode());
		response.setSvcJoinResult(serviceSubscriptionCheckEcRes.getSvcResult());
		LOGGER.debug("## Response {}", response);

		return response;
	}

	/**
	 * <pre>
	 * 단말 모델코드 조회.
	 * </pre>
	 * 
	 * @param request
	 *            GetModelCodeReq
	 * @return GetModelCodeRes
	 */
	@Override
	public GetModelCodeRes getModelCode(GetModelCodeReq request) {
		GetModelCodeRes response = new GetModelCodeRes();
		String uaCd = request.getUaCd();
		String msisdn = request.getMsisdn();
		String errorKey = "uaCd";
		String errorValue = uaCd;

		if (StringUtils.isNotBlank(msisdn) && StringUtils.isBlank(uaCd)) {
			errorKey = "msisdn";
			errorValue = msisdn;
			UapsEcReq uapsReq = new UapsEcReq();
			uapsReq.setDeviceId(request.getMsisdn());
			uapsReq.setType("mdn");

			UafmapEcRes uapsRes = this.uapsSCI.getDeviceInfo(uapsReq);
			if (uapsRes != null) {
				if (StringUtils.isNotBlank(uapsRes.getDeviceModel())) {
					uaCd = uapsRes.getDeviceModel();
				} else {
					// else uaCd가 존재하지 않을 경우 9999로 셋팅. - 비정상 데이터.
					LOGGER.info("## uaCd is not exists, Set uaCd=\"9999\"");
					uaCd = "9999";
				}
			}
		}

		// uaCd로 PhoneInfo 테이블 조회.
		Device device = this.commonComponent.getPhoneInfoByUacd(uaCd);

		if (device != null && StringUtils.isNotBlank(device.getDeviceModelCd())) {
			response.setDeviceModelNo(device.getDeviceModelCd());
		} else {
			throw new StorePlatformException("SAC_MEM_3402", errorKey, errorValue);
		}
		LOGGER.debug("## Response : {}", response);

		return response;
	}

	/**
	 * <pre>
	 * 결제 계좌 정보 인증.
	 * </pre>
	 * 
	 * @param request
	 *            AuthorizeAccountReq
	 * @return AuthorizeAccountRes
	 */
	@Override
	public AuthorizeAccountRes authorizeAccount(AuthorizeAccountReq request) {

		// 1. EC (Inicis 연동) 파라미터 전달, 결제계좌 정보 인증 요청
		InicisAuthAccountEcReq inicisAuthAccountEcReq = new InicisAuthAccountEcReq();
		inicisAuthAccountEcReq.setAcctNm(request.getBankAcctName());
		inicisAuthAccountEcReq.setBankCd(request.getBankCode());
		inicisAuthAccountEcReq.setAcctNo(request.getBankAccount());

		InicisAuthAccountEcRes inicisAuthAccountEcRes = this.inicisSCI.authAccount(inicisAuthAccountEcReq);
		LOGGER.debug("## 계좌인증 성공. Inicis ResultCode : {}", inicisAuthAccountEcRes.getResultCode());

		return new AuthorizeAccountRes();

	}

	/**
	 * <pre>
	 * 2.3.8. 사용자별 정책 조회.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            GetIndividualPolicyReq
	 * @return GetIndividualPolicyRes
	 */
	@Override
	public GetIndividualPolicyRes getIndividualPolicy(SacRequestHeader header, GetIndividualPolicyReq req) {

		LOGGER.debug("###### MiscellaneousServiceImpl.createIndividualPolicy [START] ######");

		/** 1. SC회원[UserSCI] Req 생성 및 주입 시작. */
		SearchPolicyRequest policyRequest = new SearchPolicyRequest();
		List<String> codeList = new ArrayList<String>();
		for (int i = 0; i < req.getPolicyCodeList().size(); i++) {
			codeList.add(req.getPolicyCodeList().get(i).getPolicyCode());
		}

		LOGGER.debug("==>>[SC] codeList.toString() : {}", codeList.toString());

		policyRequest.setLimitPolicyCodeList(codeList);
		policyRequest.setLimitPolicyKey(req.getKey());

		/** 2. 공통 파라미터 생성 및 주입. */
		policyRequest.setCommonRequest(this.commonComponent.getSCCommonRequest(header));

		LOGGER.debug("==>>[SC] SearchPolicyRequest.toString() : {}", policyRequest.toString());

		/** 3. SC회원[searchPolicyList] Call. */
		SearchPolicyResponse policyResponse = this.userSCI.searchPolicyList(policyRequest);

		/** 4. SC회원 Call 결과 값으로 Response 생성 및 주입. */
		GetIndividualPolicyRes res = new GetIndividualPolicyRes();
		List<IndividualPolicyInfo> policyInfos = null;
		IndividualPolicyInfo policyInfo = null;
		if (policyResponse.getLimitTargetList().size() > 0) {
			policyInfos = new ArrayList<IndividualPolicyInfo>();
			for (int i = 0; i < policyResponse.getLimitTargetList().size(); i++) {
				policyInfo = new IndividualPolicyInfo();
				policyInfo.setKey(policyResponse.getLimitTargetList().get(i).getLimitPolicyKey());
				policyInfo.setPolicyCode(policyResponse.getLimitTargetList().get(i).getLimitPolicyCode());
				policyInfo.setValue(policyResponse.getLimitTargetList().get(i).getPolicyApplyValue());
				policyInfo.setLimitAmount(policyResponse.getLimitTargetList().get(i).getLimitAmount());
				policyInfo.setPreLimitAmount(policyResponse.getLimitTargetList().get(i).getPreLimitAmount());
				policyInfo.setPermissionType(policyResponse.getLimitTargetList().get(i).getPermissionType());
				policyInfo.setIsUsed(policyResponse.getLimitTargetList().get(i).getIsUsed());
				policyInfos.add(policyInfo);
				LOGGER.debug("==>>[SAC] IndividualPolicyInfo[{}].toString() : {}", i, policyInfo.toString());
			}
		}
		res.setPolicyList(policyInfos);

		LOGGER.debug("==>>[SAC] GetIndividualPolicyRes.toString() : {}", res.toString());

		return res;
	}

	/**
	 * <pre>
	 * 2.3.9. 사용자별 정책 등록/수정.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            CreateIndividualPolicyReq
	 * @return CreateIndividualPolicyRes
	 */
	@Override
	public CreateIndividualPolicyRes regIndividualPolicy(SacRequestHeader header, CreateIndividualPolicyReq req) {

		LOGGER.debug("###### MiscellaneousServiceImpl.createIndividualPolicy [START] ######");

		/** 1. SC회원[UserSCI] Req 생성 및 주입 시작. */
		UpdatePolicyRequest updatePolicyRequest = new UpdatePolicyRequest();
		List<LimitTarget> limitTargets = new ArrayList<LimitTarget>();
		LimitTarget limitTarget = new LimitTarget();
		limitTarget.setLimitPolicyCode(req.getPolicyCode());
		limitTarget.setLimitPolicyKey(req.getKey());
		limitTarget.setPolicyApplyValue(req.getValue());
		limitTarget.setRegID(req.getRegId());
		limitTarget.setLimitAmount(req.getLimitAmount());
		limitTarget.setPreLimitAmount(req.getPreLimitAmount());
		limitTarget.setPermissionType(req.getPermissionType());
		limitTarget.setIsUsed(req.getIsUsed());

		limitTargets.add(limitTarget);
		updatePolicyRequest.setLimitTargetList(limitTargets);

		LOGGER.debug("==>>[SC] LimitTarget.toString() : {}", limitTarget.toString());

		/** 2. 공통 파라미터 생성 및 주입. */

		updatePolicyRequest.setCommonRequest(this.commonComponent.getSCCommonRequest(header));

		LOGGER.debug("==>>[SC] UpdatePolicyRequest.toString() : {}", updatePolicyRequest.toString());

		/** 3. SC회원[updatePolicy] Call. */
		UpdatePolicyResponse updatePolicyResponse = this.userSCI.updatePolicy(updatePolicyRequest);

		/** 4. SC회원 Call 결과 값으로 Response 생성 및 주입. */
		CreateIndividualPolicyRes res = new CreateIndividualPolicyRes();

		if (updatePolicyResponse.getLimitTargetList().size() > 0) {
			res.setKey(updatePolicyResponse.getLimitTargetList().get(0).getLimitPolicyKey());
			res.setPolicyCode(updatePolicyResponse.getLimitTargetList().get(0).getLimitPolicyCode());
			res.setValue(updatePolicyResponse.getLimitTargetList().get(0).getPolicyApplyValue());
			res.setIsUsed(updatePolicyResponse.getLimitTargetList().get(0).getIsUsed());
			res.setLimitAmount(updatePolicyResponse.getLimitTargetList().get(0).getLimitAmount());
			res.setPermissionType(updatePolicyResponse.getLimitTargetList().get(0).getPermissionType());
			res.setPreLimitAmount(updatePolicyResponse.getLimitTargetList().get(0).getPreLimitAmount());
		}

		LOGGER.debug("==>>[SAC] CreateIndividualPolicyRes.toString() : {}", res.toString());
		LOGGER.debug("###### MiscellaneousServiceImpl.createIndividualPolicy [START] ######");
		return res;
	}

	/**
	 * <pre>
	 * 2.3.10. 사용자별 정책 삭제.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            RemoveIndividualPolicyReq
	 * @return RemoveIndividualPolicyRes
	 */
	@Override
	public RemoveIndividualPolicyRes remIndividualPolicy(SacRequestHeader header, RemoveIndividualPolicyReq req) {

		LOGGER.debug("###### MiscellaneousServiceImpl.removeIndividualPolicy [START] ######");

		/** 1. SC회원[UserSCI] Req 생성 및 주입 시작. */
		RemovePolicyRequest removePolicyRequest = new RemovePolicyRequest();
		List<LimitTarget> limitTargetList = new ArrayList<LimitTarget>();
		LimitTarget limitTarget = new LimitTarget();
		limitTarget.setLimitPolicyCode(req.getPolicyCode());
		limitTarget.setLimitPolicyKey(req.getKey());

		limitTargetList.add(limitTarget);

		LOGGER.debug("==>>[SC] limitTarget.toString() : {}", limitTarget.toString());

		/** 2. 공통 파라미터 생성 및 주입. */
		removePolicyRequest.setCommonRequest(this.commonComponent.getSCCommonRequest(header));
		removePolicyRequest.setLimitTargetList(limitTargetList);

		LOGGER.debug("==>>[SC] RemovePolicyRequest.toString() : {}", removePolicyRequest.toString());

		/** 3. SC회원[updatePolicy] Call. */
		RemovePolicyResponse removePolicyResponse = this.userSCI.removePolicy(removePolicyRequest);

		/** 4. SC회원 Call 결과 값으로 Response 생성 및 주입. */
		RemoveIndividualPolicyRes res = new RemoveIndividualPolicyRes();
		res.setPolicyCode(removePolicyResponse.getLimitPolicyCodeList().get(0));
		res.setKey(req.getKey());

		LOGGER.debug("==>>[SAC] RemoveIndividualPolicyRes.toString() : {}", res.toString());
		LOGGER.debug("###### MiscellaneousServiceImpl.removeIndividualPolicy [END] ######");
		return res;
	}

	/**
	 * <pre>
	 * 2.3.16. DCD 가입.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            CreateDCDReq
	 * @return CreateDCDRes
	 */
	@Override
	public CreateDCDRes createDCD(SacRequestHeader header, CreateDCDReq req) {
		String systemId = header.getTenantHeader().getSystemId();
		String tenantId = header.getTenantHeader().getTenantId();
		String devideId = req.getDeviceId();
		String regCd = req.getEntryClass();
		String prodId = req.getProdId();
		String svcMngNum = "";

		// DCD 가입 요청 체크
		if (MemberConstants.DCD_REG_CD.equals(regCd) && MemberConstants.DCD_REG_PROD_ID.equals(prodId)) {
			CommonRequest commonRequest = new CommonRequest();
			commonRequest.setTenantID(tenantId);
			commonRequest.setSystemID(systemId);

			UapsEcReq uapsReq = new UapsEcReq();
			uapsReq.setDeviceId(devideId);
			uapsReq.setType("mdn");

			UserEcRes uapsRes = null;
			try {
				uapsRes = this.uapsSCI.getMappingInfo(uapsReq);
			} catch (StorePlatformException e) {
				LOGGER.info("## {} createDCD uaps error : {} {}", devideId, e.getErrorInfo().getCode(), e
						.getErrorInfo().getMessage());
			}

			if (uapsRes != null) {
				if (StringUtils.isNotBlank(uapsRes.getSvcMngNum())) {
					svcMngNum = uapsRes.getSvcMngNum();

					DCDInfo dcdInfo = new DCDInfo();
					dcdInfo.setRegChannel(systemId);
					dcdInfo.setTenantID(tenantId);
					dcdInfo.setEntryClass(regCd);
					dcdInfo.setServiceNumber(svcMngNum);
					dcdInfo.setDeviceID(devideId);
					dcdInfo.setRegDeviceID(null);
					dcdInfo.setPriorityClass("0");
					dcdInfo.setProductID(prodId);

					CreateDCDRequest createDcdReq = new CreateDCDRequest();
					createDcdReq.setCommonRequest(commonRequest);
					createDcdReq.setDCDInfo(dcdInfo);
					this.userSCI.createDCD(createDcdReq);
				} else {
					// else svcMngNum 존재하지 않을 경우
					LOGGER.info("## {} createDCD svcMngNum is not exists : ", devideId);
				}
			}
		}

		CreateDCDRes res = new CreateDCDRes();
		res.setDeviceId(devideId);
		return res;
	}
}
