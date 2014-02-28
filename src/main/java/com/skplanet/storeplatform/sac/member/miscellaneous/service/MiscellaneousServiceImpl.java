package com.skplanet.storeplatform.sac.member.miscellaneous.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

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
import com.skplanet.storeplatform.external.client.message.vo.SmsSendEcRes;
import com.skplanet.storeplatform.external.client.uaps.sci.UapsSCI;
import com.skplanet.storeplatform.external.client.uaps.vo.OpmdEcRes;
import com.skplanet.storeplatform.external.client.uaps.vo.UafmapEcRes;
import com.skplanet.storeplatform.external.client.uaps.vo.UapsEcReq;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
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
	private MemberCommonComponent commService; // 회원 공통 서비스

	@Autowired
	private UapsSCI uapsSCI; // UAPS 연동 Interface.

	@Autowired
	private UserSCI userSCI; // 회원 Component 사용자 기능 Interface.

	@Autowired
	private DeviceSCI deviceSCI; // 회원 Component 휴대기기 기능 Interface.

	@Autowired
	private IdpSCI idpSCI; // IDP 연동 Interface.

	@Autowired
	private MessageSCI messageSCI; // 기타 Component 메시지전송 기능 Interface.

	@Autowired
	private ImageSCI imageSCI; // Captcha 이미지 정보 Interface.

	@Autowired
	private MessageSourceAccessor messageSourceAccessor; // Message Properties

	@Autowired
	private MemberCommonComponent commonComponent; // 회원 공통기능 컴포넌트

	@Autowired
	private InicisSCI inicisSCI;

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDao;

	@Override
	public GetOpmdRes getOpmd(GetOpmdReq req) {
		String msisdn = req.getMsisdn();

		GetOpmdRes res = new GetOpmdRes();
		res.setMsisdn(msisdn);
		LOGGER.info("[MiscellaneousService.getOpmd] Request : {}", req);

		/** 1. OPMD번호(989)여부 검사 */
		if (StringUtils.substring(msisdn, 0, 3).equals("989")) {
			UapsEcReq uapsReq = new UapsEcReq();
			uapsReq.setDeviceId(msisdn);
			LOGGER.info("[MiscellaneousService.getOpmd] UPAS Request : {}", uapsReq);
			OpmdEcRes opmdRes = this.uapsSCI.getOpmdInfo(uapsReq);
			if (opmdRes != null) {
				res.setMsisdn(opmdRes.getMobileMdn());
				res.setOpmdMdn(opmdRes.getOpmdMdn());
				res.setMobileSvcMngNum(opmdRes.getMobileSvcMngNum());
				res.setPauseYN(opmdRes.getPauseYN());
				LOGGER.info("[MiscellaneousService.getOpmd] UPAS Response : {}", opmdRes);
			}
		} else {
			/** 2. OPMD 번호가 아닐경우, Request msisdn을 그대로 반환 */
			res.setMsisdn(msisdn);
			LOGGER.info("[MiscellaneousService.getOpmd] Non OPMD Number, Request값 그대로 내려줌.");
		}

		return res;
	}

	@Override
	public GetUaCodeRes getUaCode(SacRequestHeader requestHeader, GetUaCodeReq req) {
		String deviceModelNo = req.getDeviceModelNo();
		String msisdn = req.getMsisdn();
		String userKey = "";

		/* 헤더 정보 셋팅 */
		CommonRequest commonRequest = this.commonComponent.getSCCommonRequest(requestHeader);

		GetUaCodeRes response = new GetUaCodeRes();
		LOGGER.debug("[MiscellaneousService.getUaCode] Request {}", req);

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
			LOGGER.debug("[MiscellaneousService.getUaCode] SC Member Request {}");
			SearchUserResponse searchUserResponse = this.userSCI.searchUser(searchUserRequest);

			if (searchUserResponse == null || searchUserResponse.getUserMbr() == null) {
				throw new StorePlatformException("SAC_MEM_0003", "deviceId", msisdn);
			} else {
				userKey = searchUserResponse.getUserMbr().getUserKey();
				LOGGER.debug("[MiscellaneousService.getUaCode] SC S Response : {}", searchUserResponse.getUserMbr());
			}

			searchDeviceRequest.setKeySearchList(keySearchList);
			searchDeviceRequest.setUserKey(userKey);

			/* deviceId와 userKey로 deviceModelNo 조회 */
			SearchDeviceResponse searchDeviceResult = this.deviceSCI.searchDevice(searchDeviceRequest);

			/* deviceModelNo 조회 결과 확인 */
			if (searchDeviceResult != null
					&& StringUtils.isNotBlank(searchDeviceResult.getUserMbrDevice().getDeviceModelNo())) {
				LOGGER.debug("[MiscellaneousService.getUaCode] : {}", searchDeviceResult.getUserMbrDevice()
						.getUserMbrDeviceDetail());

				// DB 접속(TB_CM_DEVICE) - UaCode 조회
				String uaCode = this.commonDao.queryForObject("Miscellaneous.getUaCode", searchDeviceResult
						.getUserMbrDevice().getDeviceModelNo(), String.class);
				if (StringUtils.isNotBlank(uaCode)) {
					response.setUaCd(uaCode);
					LOGGER.info("## UA Code : {}", uaCode);
				} else {
					throw new StorePlatformException("SAC_MEM_3401", "msisdn", msisdn);
				}
			} else {
				throw new StorePlatformException("SAC_MEM_3402", "msisdn", msisdn);
			}

		} else if (StringUtils.isNotBlank(deviceModelNo)) { // deviceModelNo 가 파라미터로 들어온 경우
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

	// 헤더는 controller 에서 SacRequestHeader 셋팅된걸 사용한다. (tennatId, systemId 사용시에만 선언)
	@Override
	public GetPhoneAuthorizationCodeRes getPhoneAuthorizationCode(SacRequestHeader sacRequestHeader,
			GetPhoneAuthorizationCodeReq request) {
		String authCode = "";
		String tenantId = sacRequestHeader.getTenantHeader().getTenantId();
		String systemId = sacRequestHeader.getTenantHeader().getSystemId();
		String messageText = "auth.message.";
		String messageSender = "auth.message.sendNum.";

		/* 휴대폰 인증 코드 생성 */
		Random random = new Random();
		authCode += random.nextInt(999999); // 0~999999 사이의 난수 발생
		if (authCode.length() < 6) {
			int loop = 6 - authCode.length();
			for (int i = 0; i < loop; i++) { // 첫째자리수가 0으로 시작할 경우 "0"값을 앞에 추가.
				authCode = "0" + authCode;
			}
		}
		LOGGER.debug("## [SAC] authCode : {}", authCode);
		Object[] object = new Object[1];
		object[0] = authCode;

		if (tenantId.equals("S01")) { // 티스토어
			messageText += "tstore";
			messageSender += "tstore";
		} else if (tenantId.equals("S00") && systemId.equals("S00-01001")) { // 개발자
			messageText += "dev";
			messageSender += "dev";
		} else { // default
			messageText += "default";
			messageSender += "default";
		}

		messageText = this.messageSourceAccessor.getMessage(messageText, object, LocaleContextHolder.getLocale());
		messageSender = this.messageSourceAccessor.getMessage(messageSender, object, LocaleContextHolder.getLocale());
		LOGGER.debug("## [SAC] messageText : {}", messageText);
		LOGGER.debug("## [SAC] messageSender : {}", messageSender);

		/* 인증 Signautre 생성 - guid 형식 */
		String authSign = UUID.randomUUID().toString().replace("-", "");
		LOGGER.debug("## [SAC] authSign : {}", authSign);

		/* DB에 저장할 파라미터 셋팅 */
		ServiceAuth serviceAuthInfo = new ServiceAuth();
		serviceAuthInfo.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
		serviceAuthInfo.setAuthTypeCd("CM010901");
		serviceAuthInfo.setAuthSign(authSign);
		serviceAuthInfo.setAuthValue(authCode);

		LOGGER.info("## [SAC] TB_CM_SVC_AUTH INSERT Parameter : {}", serviceAuthInfo);
		this.commonDao.insert("Miscellaneous.createServiceAuthCode", serviceAuthInfo);

		/* External Comp.에 SMS 발송 요청 */
		SmsSendEcReq smsReq = new SmsSendEcReq();
		smsReq.setSrcId(request.getSrcId()); // test 값 : US004504
		smsReq.setSendMdn(messageSender);
		smsReq.setRecvMdn(request.getRecvMdn());
		smsReq.setTeleSvcId(request.getTeleSvcId()); // test 값 : 0 (단문 SM)
		smsReq.setMsg(messageText);
		if (StringUtils.isBlank(request.getCarrier())) { // 통신사정보 Optional
			smsReq.setCarrier(request.getCarrier());
		}

		LOGGER.info("## [SAC] EC - SMS 발송 요청 Request : {}", smsReq);
		SmsSendEcRes smsSendRes = this.messageSCI.smsSend(smsReq);
		LOGGER.info("## [SAC] EC - SMS 발송 결과 Response : {}", smsSendRes);

		GetPhoneAuthorizationCodeRes response = new GetPhoneAuthorizationCodeRes();

		response.setPhoneSign(authSign);
		return response;
	}

	@Override
	public ConfirmPhoneAuthorizationCodeRes confirmPhoneAutorizationCode(ConfirmPhoneAuthorizationCodeReq request) {
		ConfirmPhoneAuthorizationCodeRes res = new ConfirmPhoneAuthorizationCodeRes();
		String authCode = request.getPhoneAuthCode();
		String authSign = request.getPhoneSign();
		String userPhone = request.getUserPhone();

		/* DB에 저장할 파라미터 셋팅 */
		ServiceAuth serviceAuthInfo = new ServiceAuth();
		serviceAuthInfo.setAuthTypeCd("CM010901");
		serviceAuthInfo.setAuthSign(authSign);
		serviceAuthInfo.setAuthValue(authCode);
		serviceAuthInfo.setTimeToLive(request.getTimeToLive());

		ServiceAuth resultInfo = this.commonDao.queryForObject("Miscellaneous.searchPhoneAuthInfo", serviceAuthInfo,
				ServiceAuth.class);

		if (resultInfo == null) {
			// (인증코드 불일치, 인증Sign 불일치, 인증정보 없음)
			throw new StorePlatformException("SAC_MEM_3003");
		}

		if ("Y".equals(resultInfo.getAuthComptYn())) {
			throw new StorePlatformException("SAC_MEM_3001");
		}

		if (StringUtils.isNotBlank(resultInfo.getCurrDt()) && Double.parseDouble(resultInfo.getCurrDt()) < 0) {
			throw new StorePlatformException("SAC_MEM_3002");
		}

		String authSeq = resultInfo.getAuthSeq();
		if (StringUtils.isNotBlank(authSeq)) {
			this.commonDao.update("Miscellaneous.updateServiceAuthYn", authSeq);
		}
		res.setUserPhone(userPhone);
		return res;
	}

	@Override
	public GetCaptchaRes getCaptcha() {
		String waterMarkImageUrl = "";
		String waterMarkImageSign = "";
		String waterMarkImageString = "";
		String signData = "";
		GetCaptchaRes response = new GetCaptchaRes();

		/* IDP 연동해서 waterMarkImage URL과 Signature 받기 */

		LOGGER.info("## IDP Service 호출.");
		WaterMarkAuthImageEcRes waterMarkAuthImageEcRes = this.idpSCI.warterMarkImageUrl();

		if (waterMarkAuthImageEcRes != null && StringUtils.isNotBlank(waterMarkAuthImageEcRes.getImageUrl())) {
			waterMarkImageUrl = waterMarkAuthImageEcRes.getImageUrl();
			waterMarkImageSign = waterMarkAuthImageEcRes.getImageSign();
			signData = waterMarkAuthImageEcRes.getSignData();

			LOGGER.info("## IDP Service 결과.");
			LOGGER.debug("## >> Image_url : {} ", waterMarkAuthImageEcRes.getImageUrl());
			LOGGER.debug("## >> Image_sign : {} ", waterMarkAuthImageEcRes.getImageSign());
			LOGGER.debug("## >> Sign_data : {} ", waterMarkAuthImageEcRes.getSignData());

			LOGGER.info("## waterMarkImageUrl 정상 발급.");
			String urlPath = waterMarkImageUrl.substring(waterMarkImageUrl.indexOf("/watermark"));

			ImageReq req = new ImageReq();
			req.setProtocol(waterMarkImageUrl.substring(0, 5).equals("https") ? "https" : "http"); // HTTP or HTTPS
			req.setUrlPath(urlPath);
			ImageRes imageRes = this.imageSCI.convert(req);

			if (imageRes != null && StringUtils.isNotBlank(imageRes.getImgData())) {
				waterMarkImageString = imageRes.getImgData();
				LOGGER.info("## Captcha 문자 발급 성공.");
				LOGGER.debug("## >> WaterMark ImageString : {}", waterMarkImageString);
			}
		}

		// warterMarkImageString 를 다음 태그의 물음표(???)에 넣으면 이미지 확인 가능
		// <img alt="Embedded Image" src="data:image/png;base64,???"/>

		response.setImageData(waterMarkImageString);
		response.setImageSign(waterMarkImageSign);
		response.setSignData(signData);

		return response;
	}

	@Override
	public ConfirmCaptchaRes confirmCaptcha(ConfirmCaptchaReq request) {

		/* IDP 호출 ( Request 파라미터 전달 ) */
		LOGGER.info("## IDP Service 호출.");

		WaterMarkAuthEcReq waterMarkAuthEcReq = new WaterMarkAuthEcReq();
		waterMarkAuthEcReq.setUserCode(request.getAuthCode());
		waterMarkAuthEcReq.setImageSign(request.getImageSign());
		waterMarkAuthEcReq.setSignData(request.getSignData());

		this.idpSCI.waterMarkAuth(waterMarkAuthEcReq);
		LOGGER.info("Captcha 문자 확인 성공.");

		ConfirmCaptchaRes response = new ConfirmCaptchaRes();
		LOGGER.info("## Captcha 문자 인증 Service 종료.");
		return response;
	}

	@Override
	public GetEmailAuthorizationCodeRes getEmailAuthorizationCode(SacRequestHeader sacRequestHeader,
			GetEmailAuthorizationCodeReq request) {

		String tenantId = sacRequestHeader.getTenantHeader().getTenantId();
		ServiceAuth serviceAuthReq = new ServiceAuth();
		serviceAuthReq.setAuthEmail(request.getUserEmail());
		serviceAuthReq.setMbrNo(request.getUserKey());

		// 1. 기존 인증코드 발급 여부 및 인증 여부 확인
		ServiceAuth authYnInfo = this.commonDao.queryForObject("Miscellaneous.searchEmailAuthYn", serviceAuthReq,
				ServiceAuth.class);

		String authCode = null;
		if (authYnInfo == null || "N".equals(authYnInfo.getAuthComptYn())) {

			// 2. 이메일 인증 코드 생성 - GUID 수준의 난수
			authCode = UUID.randomUUID().toString().replace("-", "");
			LOGGER.debug("## authCode : {}", authCode);

			// 3. DB에 저장(TB_CM_SVC_AUTH) - 인증서비스 코드, 인증코드, 회원Key, 인증 Email 주소
			ServiceAuth serviceAuthInfo = new ServiceAuth();
			serviceAuthInfo.setTenantId(tenantId);
			serviceAuthInfo.setAuthTypeCd("CM010902");
			serviceAuthInfo.setAuthValue(authCode);
			serviceAuthInfo.setMbrNo(request.getUserKey());
			serviceAuthInfo.setAuthSign("EmailAuthorization"); // 의미 없음. DB에 AUTH_SIGN 이 "NOT NULL"로 정의되어있음.
			serviceAuthInfo.setAuthEmail(request.getUserEmail());

			LOGGER.info("## TB에 저장할 값들 serviceAuthInfo : {}", serviceAuthInfo);
			this.commonDao.insert("Miscellaneous.createServiceAuthCode", serviceAuthInfo);
		} else {
			// 미인증 상태의 인증코드 존재.
			authCode = authYnInfo.getAuthValue();

			LOGGER.info("이미 발급된 회원 입니다. 기존 발급된 인증코드 전달. authCode : {}", authCode);
			// 인증 시간이 만료된 코드도 있으므로, 인증코드 생성시간 업데이트.
			this.commonDao.update("Miscellaneous.updateServiceAuthTime", authYnInfo.getAuthSeq());
		}

		// 4. 인증코드 Response
		GetEmailAuthorizationCodeRes response = new GetEmailAuthorizationCodeRes();
		response.setEmailAuthCode(authCode);
		LOGGER.info("## 이메일 인증 코드 발급 완료. response : {}", response.getEmailAuthCode());

		return response;
	}

	@Override
	public ConfirmEmailAuthorizationCodeRes confirmEmailAuthorizationCode(ConfirmEmailAuthorizationCodeReq request) {

		/** 1. 인증 코드로 DB 확인하여 , 회원 key, 회원 email 조회 */
		LOGGER.info("## 인증코드 정보 조회. Request : {}", request);
		String authValue = request.getEmailAuthCode();
		String timeToLive = request.getTimeToLive();

		ServiceAuth serviceAuthReq = new ServiceAuth();
		serviceAuthReq.setAuthValue(authValue);
		serviceAuthReq.setTimeToLive(timeToLive);
		ServiceAuth serviceAuthInfo = this.commonDao.queryForObject("Miscellaneous.searchEmailAuthInfo",
				serviceAuthReq, ServiceAuth.class);
		LOGGER.info("## 인증코드 정보 조회. Response : {}", serviceAuthInfo);

		/** 2. 인증코드 정보가 존재할 경우, 인증 처리 */
		if (serviceAuthInfo != null) {

			if ("Y".equals(serviceAuthInfo.getAuthComptYn())) { // 기존 인증된 코드일 경우
				throw new StorePlatformException("SAC_MEM_3001");
			}

			/** timeToLive 값이 존재 할 경우 인증코드 유효기간 검사 */
			if (StringUtils.isNotBlank(timeToLive) && StringUtils.isNotBlank(serviceAuthInfo.getCurrDt())
					&& (Double.parseDouble(serviceAuthInfo.getCurrDt()) < 0)) {
				throw new StorePlatformException("SAC_MEM_3002");
			}

			String authSeq = serviceAuthInfo.getAuthSeq();
			this.commonDao.update("Miscellaneous.updateServiceAuthYn", authSeq);
			LOGGER.info("## 이메일 인증 완료.");
		} else {
			throw new StorePlatformException("SAC_MEM_3003"); // 해당 인증코드가 DB Table에 존재하지 않음.
		}
		ConfirmEmailAuthorizationCodeRes response = new ConfirmEmailAuthorizationCodeRes();
		response.setUserEmail(serviceAuthInfo.getAuthEmail());
		response.setUserKey(serviceAuthInfo.getMbrNo());

		return response;
	}

	@Override
	public CreateAdditionalServiceRes createAdditionalService(CreateAdditionalServiceReq request) {
		CreateAdditionalServiceRes response = new CreateAdditionalServiceRes();

		LOGGER.info("## 부가서비스 가입 요청 - IDP 연동.");
		JoinSupServiceRequestEcReq joinSupServiceEcReq = new JoinSupServiceRequestEcReq();
		joinSupServiceEcReq.setSvcCode(request.getSvcCode());
		joinSupServiceEcReq.setUserMdn(request.getMsisdn());
		joinSupServiceEcReq.setUserSvcMngNum(request.getSvcMngNum());

		JoinSupServiceRequestEcRes joinSupServiceEcRes = this.idpSCI.joinSupServiceRequest(joinSupServiceEcReq);

		LOGGER.info("## 부가서비스 가입 요청 - IDP 연동. response {}", joinSupServiceEcRes);

		response.setSvcCode(joinSupServiceEcRes.getSvcCode()); // 부가서비스 코드
		response.setMsisdn(joinSupServiceEcRes.getUserMdn()); // 사용자 휴대폰번호

		return response;
	}

	@Override
	public GetAdditionalServiceRes getAdditionalService(GetAdditionalServiceReq request) {

		LOGGER.info("[MiscellaneousService.getAdditionalService] IDP Request {}", request);
		ServiceSubscriptionCheckEcReq serviceSubscriptionCheckEcReq = new ServiceSubscriptionCheckEcReq();
		serviceSubscriptionCheckEcReq.setUserMdn(request.getMsisdn());
		serviceSubscriptionCheckEcReq.setSvcCode(request.getSvcCode());
		ServiceSubscriptionCheckEcRes serviceSubscriptionCheckEcRes = this.idpSCI
				.serviceSubscriptionCheck(serviceSubscriptionCheckEcReq);
		LOGGER.info("[MiscellaneousService.getAdditionalService] IDP Response {}", serviceSubscriptionCheckEcRes);

		GetAdditionalServiceRes response = new GetAdditionalServiceRes();
		response.setMsisdn(serviceSubscriptionCheckEcRes.getUserMdn());
		response.setSvcCode(serviceSubscriptionCheckEcRes.getSvcCode());
		response.setSvcJoinResult(serviceSubscriptionCheckEcRes.getSvcResult());

		return response;
	}

	@Override
	public GetModelCodeRes getModelCode(GetModelCodeReq request) {
		GetModelCodeRes response = new GetModelCodeRes();
		String uaCd = request.getUaCd();
		String msisdn = request.getMsisdn();
		String errorKey = "uaCd";
		String errorValue = uaCd;

		errorKey = "msisdn";
		errorValue = msisdn;
		UapsEcReq uapsReq = new UapsEcReq();
		uapsReq.setDeviceId(request.getMsisdn());
		uapsReq.setType("mdn");
		LOGGER.info("## mdn으로 UA코드 조회 - UAPS 연동. request {}", uapsReq);
		UafmapEcRes uapsRes = this.uapsSCI.getDeviceInfo(uapsReq);
		if (uapsRes != null && StringUtils.isNotBlank(uapsRes.getDeviceModel()))
			uaCd = uapsRes.getDeviceModel();
		else
			throw new StorePlatformException("SAC_MEM_3401", errorKey, errorValue);

		LOGGER.info("## UA 코드로 deviceModelNo 조회 - TB_CM_DEVICE. uaCd : {}", uaCd);
		Device device = this.commonComponent.getPhoneInfoByUacd(uaCd);

		if (device != null && StringUtils.isNotBlank(device.getDeviceModelCd())) {
			response.setDeviceModelNo(device.getDeviceModelCd());
		} else {
			throw new StorePlatformException("SAC_MEM_3402", errorKey, errorValue);
		}

		return response;
	}

	@Override
	public AuthorizeAccountRes authorizeAccount(AuthorizeAccountReq request) {
		// 1. EC (Inicis 연동) 파라미터 전달, 결제계좌 정보 인증 요청
		InicisAuthAccountEcReq inicisAuthAccountEcReq = new InicisAuthAccountEcReq();
		inicisAuthAccountEcReq.setAcctNm(request.getBankAcctName());
		inicisAuthAccountEcReq.setBankCd(request.getBankCode());
		inicisAuthAccountEcReq.setAcctNo(request.getBankAccount());

		LOGGER.info("[Miscellaneous.authorizeAccount] Inicis Request : {}", inicisAuthAccountEcReq);
		InicisAuthAccountEcRes inicisAuthAccountEcRes = this.inicisSCI.authAccount(inicisAuthAccountEcReq);
		LOGGER.info("결제 계좌정보 인증 성공. ResultCode : {}", inicisAuthAccountEcRes.getResultCode());

		// // 2. EC (Inicis 연동) 결제계좌 인증 결과 로그.
		// if (!"EC_INICIS_1000".equals(inicisAuthAccountEcRes.getResultCode())) {
		// throw new StorePlatformException(inicisAuthAccountEcRes.getResultCode(),
		// inicisAuthAccountEcRes.getResultMsg());
		// }
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
	public CreateIndividualPolicyRes createIndividualPolicy(SacRequestHeader header, CreateIndividualPolicyReq req) {

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
	public RemoveIndividualPolicyRes removeIndividualPolicy(SacRequestHeader header, RemoveIndividualPolicyReq req) {

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
}
