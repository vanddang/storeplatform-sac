package com.skplanet.storeplatform.sac.member.miscellaneous.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.external.client.idp.sci.ImageSCI;
import com.skplanet.storeplatform.external.client.idp.vo.IDPReceiverM;
import com.skplanet.storeplatform.external.client.idp.vo.ImageReq;
import com.skplanet.storeplatform.external.client.idp.vo.ImageReq.HTTP_METHOD;
import com.skplanet.storeplatform.external.client.idp.vo.ImageReq.HTTP_PROTOCOL;
import com.skplanet.storeplatform.external.client.idp.vo.ImageRes;
import com.skplanet.storeplatform.external.client.message.sci.MessageSCI;
import com.skplanet.storeplatform.external.client.message.vo.SmsSendReq;
import com.skplanet.storeplatform.external.client.message.vo.SmsSendRes;
import com.skplanet.storeplatform.external.client.uaps.sci.UapsSCI;
import com.skplanet.storeplatform.external.client.uaps.vo.OpmdRes;
import com.skplanet.storeplatform.external.client.uaps.vo.UapsReq;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtil;
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
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrDeviceDetail;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmCaptchaReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmCaptchaRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmEmailAuthorizationCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmEmailAuthorizationCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmPhoneAuthorizationCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmPhoneAuthorizationCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.CreateIndividualPolicyReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.CreateIndividualPolicyRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetCaptchaRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetEmailAuthorizationCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetEmailAuthorizationCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetIndividualPolicyReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetIndividualPolicyRes;
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
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.idp.service.IDPService;
import com.skplanet.storeplatform.sac.member.miscellaneous.vo.ServiceAuth;

/**
 * 
 * 기타 기능 관련 인터페이스 구현체
 * 
 * Updated on : 2014. 1. 7. Updated by : 김다슬, 인크로스.
 */
@Service
@Transactional
public class MiscellaneousServiceImpl implements MiscellaneousService {
	private static final Logger LOGGER = LoggerFactory.getLogger(MiscellaneousServiceImpl.class);

	@Autowired
	private UapsSCI uapsSCI; // UAPS 연동 Interface.

	@Autowired
	private UserSCI userSCI; // 회원 Component 사용자 기능 Interface.

	@Autowired
	private DeviceSCI deviceSCI; // 회원 Component 휴대기기 기능 Interface.

	@Autowired
	private IDPService idpService; // IDP 연동 class.

	@Autowired
	private MessageSCI messageSCI; // 기타 Component 메시지전송 기능 Interface.

	@Autowired
	private ImageSCI imageSCI;

	@Autowired
	private MessageSourceAccessor messageSourceAccessor; // Message Properties

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDao;

	@Override
	public GetOpmdRes getOpmd(GetOpmdReq req) throws Exception {
		String msisdn = req.getMsisdn();

		GetOpmdRes res = new GetOpmdRes();
		res.setMsisdn(msisdn);

		/** 1. OPMD번호(989)여부 검사 */
		if (StringUtils.substring(msisdn, 0, 3).equals("989")) {
			/** 1) OPMD 모번호 조회 (UAPS 연동) */
			OpmdRes opmdRes = new OpmdRes();
			UapsReq uapsReq = new UapsReq();
			uapsReq.setDeviceId(msisdn);
			LOGGER.info("## UAPS 연동 시작.");
			LOGGER.info("## Request : {}", uapsReq);
			opmdRes = this.uapsSCI.getOpmdInfo(uapsReq);

			LOGGER.info("## UAPS 연동 종료.");
			LOGGER.info("## Response {}", opmdRes);
			/** 2) UAPS 연동 결과 확인 */
			if (opmdRes.getResultCode() == 0) {
				LOGGER.info("##### External Comp. UAPS 연동성공 {}", opmdRes.getResultCode());
				res.setMsisdn(opmdRes.getMobileMdn());
			} else {
				throw new Exception("UAPS 연동 오류.[" + opmdRes.getResultCode() + "]");
			}
		} else {
			/** 2. OPMD 번호가 아닐경우, Request msisdn을 그대로 반환 */
			res.setMsisdn(msisdn);
			LOGGER.info("OPMD 번호 아님, Request값 그대로 내려줌.");
		}
		return res;
	}

	@Override
	public GetUaCodeRes getUaCode(SacRequestHeader requestHeader, GetUaCodeReq req) throws Exception {
		String deviceModelNo = req.getDeviceModelNo();
		String msisdn = req.getMsisdn();
		String userKey = "";

		CommonRequest commonRequest = new CommonRequest();
		/* 헤더 정보 셋팅 */
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());

		GetUaCodeRes response = new GetUaCodeRes();
		LOGGER.debug("########### GetUaCodeReq {}", req);

		/** 파라미터로 MSISDN만 넘어온 경우 */
		if (msisdn != null && deviceModelNo == null) {

			/** 1. MSISDN 유효성 검사 */
			String validation = this.mdnValidation(msisdn);
			if (validation.equals("Y")) {

				/** 2. deviceId로 userKey 조회 - SC 회원 "회원 기본 정보 조회" */

				userKey = this.getUserKey(commonRequest, msisdn);

				/** 3. SC 회원 Request 생성 */
				SearchDeviceRequest searchDeviceRequest = new SearchDeviceRequest();

				/** 4. 공통헤더 Request 파라미터 셋팅 */
				searchDeviceRequest.setCommonRequest(commonRequest);

				List<KeySearch> keySearchList = new ArrayList<KeySearch>();
				KeySearch keySearch = new KeySearch();
				keySearch.setKeyType(MemberConstants.KEY_TYPE_DEVICE_ID);
				keySearch.setKeyString(msisdn);
				keySearchList.add(keySearch);

				searchDeviceRequest.setKeySearchList(keySearchList);
				searchDeviceRequest.setUserKey(userKey);

				/** 5. deviceId와 userKey로 deviceModelNo 조회 */
				SearchDeviceResponse searchDeviceResult = new SearchDeviceResponse();
				searchDeviceResult = this.deviceSCI.searchDevice(searchDeviceRequest);

				/** 6. deviceModelNo 조회 결과 확인 */
				if (StringUtil.equals(searchDeviceResult.getCommonResponse().getResultCode(),
						MemberConstants.RESULT_SUCCES)) {
					deviceModelNo = searchDeviceResult.getUserMbrDevice().getDeviceModelNo();
					LOGGER.debug("## Response deviceModelNo {}: ", deviceModelNo);
					if (deviceModelNo != null) {
						LOGGER.debug("## UserMbrDeviceDetail : {}", searchDeviceResult.getUserMbrDevice()
								.getUserMbrDeviceDetail());
						String uaCode = null;
						boolean isUaCode = false;
						List<UserMbrDeviceDetail> deviceDetails = searchDeviceResult.getUserMbrDevice()
								.getUserMbrDeviceDetail();
						LOGGER.info("## SC 회원  DeviceMapper.searchDeviceList에서 UA Code 확인.");
						for (int i = 0; i < deviceDetails.size(); i++) {
							isUaCode = deviceDetails.get(i).getExtraProfile().equals("US011404"); // UA코드 인지 여부
							if (isUaCode) {
								uaCode = deviceDetails.get(i).getExtraProfileValue();
							}
						}
						if (uaCode != null) {
							response.setUaCd(uaCode);
							LOGGER.info("## UA Code : {}", uaCode);
						} else {
							LOGGER.info("deviceId에 해당하는 UA 코드 없음.");
							throw new Exception("deviceId에 해당하는 UA 코드 없음.");
						}
					} else {
						throw new Exception("deviceId에 해당하는 UA 코드 없음.");
					}
				} else {
					throw new Exception("SC 회원 API 조회 실패[" + searchDeviceResult.getCommonResponse().getResultCode()
							+ "] " + searchDeviceResult.getCommonResponse().getResultMessage());
				}

			} else {
				throw new Exception("유효하지 않은 휴대폰 번호.");
			}
		} else if (deviceModelNo != null) { // deviceModelNo 가 파라미터로 들어온 경우
			// DB 접속(TB_CM_DEVICE) - UaCode 조회
			String uaCode = this.commonDao.queryForObject("Miscellaneous.getUaCode", deviceModelNo, String.class);
			if (uaCode != null) {
				response.setUaCd(uaCode);
			} else {
				LOGGER.info("deviceModelNo에 해당하는 UA 코드 없음.");
				throw new Exception("deviceModelNo에 해당하는 UA 코드 없음.");
			}
		}
		return response;
	}

	// 헤더는 controller 에서 SacRequestHeader 셋팅된걸 사용한다. (tennatId, systemId 사용시에만 선언)
	@Override
	public GetPhoneAuthorizationCodeRes getPhoneAuthorizationCode(SacRequestHeader sacRequestHeader,
			GetPhoneAuthorizationCodeReq request) throws Exception {
		String authCode = "";
		String tenantId = sacRequestHeader.getTenantHeader().getTenantId();
		String systemId = sacRequestHeader.getTenantHeader().getSystemId();
		String messageText = "auth.message.";
		String messageSender = "auth.message.sendNum.";

		/* 휴대폰 인증 코드 생성 */
		Random random = new Random();
		authCode += random.nextInt(999999); // 0~999999 사이의 난수 발생
		if (authCode.length() < 6) {
			LOGGER.debug("## 변경 전 인증번호 : {}", authCode);
			int loop = 6 - authCode.length();
			for (int i = 0; i < loop; i++) { // 첫째자리수가 0으로 시작할 경우 "0"값을 앞에 추가.
				authCode = "0" + authCode;
			}
			LOGGER.debug("## 변경 후 인증번호 : {}", authCode);
		}
		LOGGER.debug("## 인증번호 생성 >> authCode : {}", authCode);
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
		LOGGER.debug("## messageText : {}", messageText);
		LOGGER.debug("## messageSender : {}", messageSender);

		/* 인증 Signautre 생성 - guid 형식 */
		String authSign = UUID.randomUUID().toString().replace("-", "");
		LOGGER.debug("## 인증Sign 생성 >> authSign : {}", authSign);

		/* DB에 저장할 파라미터 셋팅 */
		ServiceAuth serviceAuthInfo = new ServiceAuth();
		serviceAuthInfo.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
		serviceAuthInfo.setAuthTypeCd("CM010901");
		serviceAuthInfo.setAuthSign(authSign);
		serviceAuthInfo.setAuthValue(authCode);

		LOGGER.info("## TB_CM_SVC_AUTH INSERT Parameter : {}", serviceAuthInfo);
		this.commonDao.insert("Miscellaneous.insertServiceAuthCode", serviceAuthInfo);

		/* External Comp.에 SMS 발송 요청 */
		SmsSendReq smsReq = new SmsSendReq();
		smsReq.setSrcId(request.getSrcId()); // test 값 : US004504
		smsReq.setCarrier(request.getCarrier()); // 테넌트에서 파라미터를 SKT, KT, LGT로 받는다.
		smsReq.setSendMdn(messageSender);
		smsReq.setRecvMdn(request.getRecvMdn());
		smsReq.setTeleSvcId(request.getTeleSvcId()); // test 값 : 0 (단문 SM)
		smsReq.setMsg(messageText);

		LOGGER.info("## EC - SMS 발송 요청 Request : {}", smsReq);
		/* External Comp. SMS 발송 기능 */
		SmsSendRes smsSendRes = this.messageSCI.smsSend(smsReq);
		LOGGER.info("## EC - SMS 발송 결과 Response : {}", smsSendRes);

		GetPhoneAuthorizationCodeRes response = new GetPhoneAuthorizationCodeRes();

		/* SMS 발송 성공 여부를 확인 */
		if (smsSendRes.getResultStatus().equals("success")) {
			LOGGER.info("## EC - SMS 발송 성공.");
			response.setPhoneSign(authSign);
		} else {
			throw new Exception("## EC SMS 전송 오류.[" + smsSendRes.getResultStatus() + "]");
		}
		return response;
	}

	@Override
	public ConfirmPhoneAuthorizationCodeRes confirmPhoneAutorizationCode(ConfirmPhoneAuthorizationCodeReq request)
			throws Exception {
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

		ServiceAuth resultInfo = this.commonDao.queryForObject("Miscellaneous.getPhoneAuthYn", serviceAuthInfo,
				ServiceAuth.class);

		if (resultInfo == null) {
			throw new Exception("인증 코드가 일치 하지 않습니다.");
		}

		if (resultInfo.getAuthComptYn().equals("Y")) {
			throw new Exception("기인증된 인증 코드 입니다.");
		}

		if (Double.parseDouble(resultInfo.getCurrDt()) < 0) {
			LOGGER.info("인증 시간이 만료된 인증 코드입니다.");
			throw new Exception("인증 시간이 만료된 인증 코드입니다.");
		}

		String authSeq = resultInfo.getAuthSeq();
		this.commonDao.update("Miscellaneous.updateServiceAuthYn", authSeq);

		res.setUserPhone(userPhone);
		return res;
	}

	@Override
	public GetCaptchaRes getCaptcha() throws Exception {
		String waterMarkImageUrl = "";
		String waterMarkImageSign = "";
		String waterMarkImageString = "";
		IDPReceiverM idpReciver = new IDPReceiverM();
		GetCaptchaRes response = new GetCaptchaRes();

		/* IDP 연동해서 waterMarkImage URL과 Signature 받기 */

		LOGGER.info("## IDP Service 호출.");
		idpReciver = this.idpService.warterMarkImageUrl();
		waterMarkImageUrl = idpReciver.getResponseBody().getImage_url();
		waterMarkImageSign = idpReciver.getResponseBody().getImage_sign();
		String signData = idpReciver.getResponseBody().getSign_data();

		LOGGER.info("## IDP Service 결과.");
		LOGGER.debug("## >> Image_url : {} ", idpReciver.getResponseBody().getImage_url());
		LOGGER.debug("## >> Image_sign : {} ", idpReciver.getResponseBody().getImage_sign());
		LOGGER.debug("## >> Sign_data : {} ", idpReciver.getResponseBody().getSign_data());

		if (waterMarkImageUrl != null) {
			LOGGER.info("## waterMarkImageUrl 정상 발급.");
			HTTP_PROTOCOL protocol = null;
			HTTP_METHOD method = null;
			String urlPath = waterMarkImageUrl.substring(waterMarkImageUrl.indexOf("/watermark"));

			if (waterMarkImageUrl.substring(0, 5).equals("https")) {
				protocol = HTTP_PROTOCOL.HTTPS;
				method = HTTP_METHOD.POST;
			} else {
				protocol = HTTP_PROTOCOL.HTTP;
				method = HTTP_METHOD.GET;
			}

			LOGGER.debug("## Request to ImageSCI >> Protocol : {}, Method : {}, UrlPath : {}", protocol, method,
					urlPath);

			ImageReq req = new ImageReq();
			req.setMethod(method); // GET or POST
			req.setProtocol(protocol); // HTTP or HTTPS
			req.setUrlPath(urlPath);
			ImageRes res = this.imageSCI.convert(req);

			waterMarkImageString = res.getImgData();
			LOGGER.debug("## >> WaterMark ImageString : {}", waterMarkImageString);
			LOGGER.info("## Captcha 문자 발급 완료.");
		}

		// warterMarkImageString 를 다음 태그의 물음표(???)에 넣으면 이미지 확인 가능
		// <img alt="Embedded Image" src="data:image/png;base64,???"/>

		response.setImageData(waterMarkImageString);
		response.setImageSign(waterMarkImageSign);
		response.setSignData(signData);

		return response;
	}

	@Override
	public ConfirmCaptchaRes confirmCaptcha(ConfirmCaptchaReq request) throws Exception {

		/* IDP 호출 ( Request 파라미터 전달 ) */
		IDPReceiverM idpReciver = new IDPReceiverM();

		LOGGER.info("## IDP Service 호출.");
		idpReciver = this.idpService.warterMarkAuth(request.getAuthCode(), request.getImageSign(),
				request.getSignData());
		LOGGER.info("## IDP Service 결과. Response{}", idpReciver);
		ConfirmCaptchaRes response = new ConfirmCaptchaRes();

		if (idpReciver.getResponseHeader().getResult().equals("1000")) {
			LOGGER.info("## IDP 연동 성공 resultCode : {}, resultMessage : {}", idpReciver.getResponseHeader().getResult(),
					idpReciver.getResponseHeader().getResult_text());
		} else {
			throw new Exception("IDP 호출 오류.resultCode : {" + idpReciver.getResponseHeader().getResult()
					+ "}, resultMessage : {" + idpReciver.getResponseHeader().getResult_text() + "}");
		}
		LOGGER.info("## Captcha 문자 인증 Service 종료.");
		return response;
	}

	@Override
	public GetEmailAuthorizationCodeRes getEmailAuthorizationCode(GetEmailAuthorizationCodeReq request)
			throws Exception {

		ServiceAuth serviceAuthReq = new ServiceAuth();
		serviceAuthReq.setAuthEmail(request.getUserEmail());
		serviceAuthReq.setMbrNo(request.getUserKey());

		/** 1. 기존 인증코드 발급 여부 및 인증 여부 확인 */
		List<ServiceAuth> serviceAuthList = this.commonDao.queryForList("Miscellaneous.getEmailAuthYn", serviceAuthReq,
				ServiceAuth.class);

		String isAuthEmail = "Y";
		String authCode = "";
		if (serviceAuthList != null) {
			for (int i = 0; i < serviceAuthList.size(); i++) {
				if (serviceAuthList.get(i).getAuthComptYn().equals("N")) {
					isAuthEmail = "N";
					authCode = serviceAuthList.get(i).getAuthValue();
				}
			}
		}

		LOGGER.info("## 인증코드 신규 발급 여부 : {}", isAuthEmail);

		if (isAuthEmail.equals("N")) { // 기존 인증코드 발급고 인증하지 않은 경우.
			LOGGER.info("이미 발급된 회원 입니다. 동일 인증코드 전달.");
			LOGGER.info("## authCode : {}", authCode);
		} else if (isAuthEmail.equals("Y") || serviceAuthList == null) { // 신규 인증 - NULL 또는 기존에 인증했으나 무효화된 값들을 가지고 있는경우.
			/** 2. 이메일 인증 코드 생성 - GUID 수준의 난수 */
			authCode = UUID.randomUUID().toString().replace("-", "");
			LOGGER.debug("## authCode : {}", authCode);

			/** 3. DB에 저장(TB_CM_SVC_AUTH) - 인증서비스 코드, 인증코드, 회원Key, 인증 Email 주소 */
			ServiceAuth serviceAuthInfo = new ServiceAuth();
			serviceAuthInfo.setAuthTypeCd("CM010902");
			serviceAuthInfo.setAuthValue(authCode);
			serviceAuthInfo.setMbrNo(request.getUserKey());
			serviceAuthInfo.setAuthSign("EmailAuthorization"); // 의미 없음. DB에 AUTH_SIGN 이 "NOT NULL"로 정의되어있음.
			serviceAuthInfo.setAuthEmail(request.getUserEmail());

			LOGGER.info("## TB에 저장할 값들 serviceAuthInfo : {}", serviceAuthInfo);
			this.commonDao.insert("Miscellaneous.insertServiceAuthCode", serviceAuthInfo);
		}

		/** 4. 인증코드 Response */

		GetEmailAuthorizationCodeRes response = new GetEmailAuthorizationCodeRes();
		response.setEmailAuthCode(authCode);
		LOGGER.info("## 이메일 인증 코드 발급 완료. response : {}", response.getEmailAuthCode());

		return response;
	}

	@Override
	public ConfirmEmailAuthorizationCodeRes confirmEmailAuthorizationCode(ConfirmEmailAuthorizationCodeReq request)
			throws Exception {

		/** 1. 인증 코드로 DB 확인하여 , 회원 key, 회원 email 조회 */
		LOGGER.info("## 인증코드 정보 조회. Request : {}", request);
		String authValue = request.getEmailAuthCode();
		String timeToLive = request.getTimeToLive();

		ServiceAuth serviceAuthReq = new ServiceAuth();
		serviceAuthReq.setAuthValue(authValue);
		serviceAuthReq.setTimeToLive(timeToLive);
		ServiceAuth serviceAuthInfo = this.commonDao.queryForObject("Miscellaneous.getEmailAuthInfo", serviceAuthReq,
				ServiceAuth.class);
		LOGGER.info("## 인증코드 정보 조회. Response : {}", serviceAuthInfo);

		/** 2. 인증코드 정보가 존재할 경우, 인증 처리 */
		if (serviceAuthInfo != null) {
			/** timeToLive 값이 존재 할 경우 인증코드 유효기간 검사 */
			if (timeToLive != null && (Double.parseDouble(serviceAuthInfo.getCurrDt()) < 0)) {
				LOGGER.info("## 인증 유효기간 만료.");
				throw new Exception("인증 유효기간이 만료되었습니다.");
			}

			if (serviceAuthInfo.getAuthComptYn().equals("Y")) { // 기존 인증된 코드일 경우
				throw new Exception("기존 인증된 회원입니다.");
			}
			String authSeq = serviceAuthInfo.getAuthSeq();
			this.commonDao.update("Miscellaneous.updateServiceAuthYn", authSeq);
			LOGGER.info("## 이메일 인증 완료.");
		} else {
			throw new Exception("유효하지 않은 인증코드 입니다.");
		}
		ConfirmEmailAuthorizationCodeRes response = new ConfirmEmailAuthorizationCodeRes();
		response.setUserEmail(serviceAuthInfo.getAuthEmail());
		response.setUserKey(serviceAuthInfo.getMbrNo());

		return response;
	}

	/**
	 * 
	 * <pre>
	 * 휴대폰번호 유효성 검사.
	 * - 10자리 또는 11자리 인지 확인.
	 * </pre>
	 * 
	 * @param mdn
	 *            String
	 * @return String
	 */
	public String mdnValidation(String mdn) {
		String validation = "N";
		if (mdn.length() == 10 | mdn.length() == 11) {
			validation = "Y";
		}
		return validation;
	}

	/**
	 * <pre>
	 * msisdn으로 userKey 조회하기 - (기타 기능 내 공통 기능 함수로 생성).
	 * </pre>
	 * 
	 * @param commonReq
	 *            CommonRequest
	 * @param msisdn
	 *            String
	 * @return String
	 * @throws Exception
	 *             Exception
	 */
	public String getUserKey(CommonRequest commonReq, String msisdn) throws Exception {
		String userKey = "";

		SearchUserRequest searchUserRequest = new SearchUserRequest();
		/** 1. 임시 공통헤더 생성 주입 */
		searchUserRequest.setCommonRequest(commonReq);

		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySearch = new KeySearch();
		keySearch.setKeyType(MemberConstants.KEY_TYPE_DEVICE_ID);
		keySearch.setKeyString(msisdn);
		keySearchList.add(keySearch);
		searchUserRequest.setKeySearchList(keySearchList);

		SearchUserResponse searchUserResponse = new SearchUserResponse();
		/** 2. deviceId(msisdn)로 userKey 조회 - SC 회원 "회원 기본 정보 조회" */
		searchUserResponse = this.userSCI.searchUser(searchUserRequest);

		/** 3. 회원정보 조회 성공 여부 확인 */
		if (StringUtil.equals(searchUserResponse.getCommonResponse().getResultCode(), MemberConstants.RESULT_SUCCES)) {
			if (searchUserResponse.getUserMbr() == null) {
				throw new Exception("DeviceId에 해당하는 회원정보 없음.");
			} else {
				userKey = searchUserResponse.getUserMbr().getUserKey();
				LOGGER.debug(">>>>>>> userKey:" + userKey);
			}
		} else {
			LOGGER.debug("######## SC 회원 회원 기본 정보 조회 API 연동 오류. ########");
			throw new Exception("[" + searchUserResponse.getCommonResponse().getResultCode() + "] "
					+ searchUserResponse.getCommonResponse().getResultMessage());
		}
		return userKey;
	}

	/**
	 * <pre>
	 * 2.3.8. 사용자별 정책 조회.
	 * </pre>
	 * 
	 * @param header
	 * @param req
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
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID(header.getTenantHeader().getTenantId());
		commonRequest.setSystemID(header.getTenantHeader().getSystemId());
		policyRequest.setCommonRequest(commonRequest);

		LOGGER.debug("==>>[SC] commonRequest.toString() : {}", commonRequest.toString());
		LOGGER.debug("==>>[SC] SearchPolicyRequest.toString() : {}", policyRequest.toString());

		/** 3. SC회원[searchPolicyList] Call. */
		SearchPolicyResponse policyResponse = this.userSCI.searchPolicyList(policyRequest);

		// Debug
		LOGGER.info("[UserSCI.searchPolicyList()] - Response CODE : {}, MESSAGE : {}", policyResponse
				.getCommonResponse().getResultCode(), policyResponse.getCommonResponse().getResultMessage());

		// TODO 실패 처리
		if (MemberConstants.RESULT_FAIL.equals(policyResponse.getCommonResponse().getResultCode())) {
			LOGGER.debug("[IndividualPolicyService] - getIndividualPolicy CODE {}, MESSAGE {} ", policyResponse
					.getCommonResponse().getResultCode(), policyResponse.getCommonResponse().getResultMessage());
		}

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
	 * @param req
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

		limitTargets.add(limitTarget);
		updatePolicyRequest.setLimitTargetList(limitTargets);

		LOGGER.debug("==>>[SC] LimitTarget.toString() : {}", limitTarget.toString());

		/** 2. 공통 파라미터 생성 및 주입. */
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID(header.getTenantHeader().getTenantId());
		commonRequest.setSystemID(header.getTenantHeader().getSystemId());
		updatePolicyRequest.setCommonRequest(commonRequest);

		LOGGER.debug("==>>[SC] CommonRequest.toString() : {}", commonRequest.toString());
		LOGGER.debug("==>>[SC] UpdatePolicyRequest.toString() : {}", updatePolicyRequest.toString());

		/** 3. SC회원[updatePolicy] Call. */
		UpdatePolicyResponse updatePolicyResponse = this.userSCI.updatePolicy(updatePolicyRequest);

		// Debug
		LOGGER.info("[UserSCI.updatePolicy()] - Response CODE : {}, MESSAGE : {}", updatePolicyResponse
				.getCommonResponse().getResultCode(), updatePolicyResponse.getCommonResponse().getResultMessage());

		// TODO 실패 처리
		if (MemberConstants.RESULT_FAIL.equals(updatePolicyResponse.getCommonResponse().getResultCode())) {
			LOGGER.debug("[IndividualPolicyService] - createIndividualPolicy CODE {}, MESSAGE {} ",
					updatePolicyResponse.getCommonResponse().getResultCode(), updatePolicyResponse.getCommonResponse()
							.getResultMessage());
		}

		/** 4. SC회원 Call 결과 값으로 Response 생성 및 주입. */
		CreateIndividualPolicyRes res = new CreateIndividualPolicyRes();

		if (updatePolicyResponse.getLimitTargetList().size() > 0) {
			res.setKey(updatePolicyResponse.getLimitTargetList().get(0).getLimitPolicyKey());
			res.setPolicyCode(updatePolicyResponse.getLimitTargetList().get(0).getLimitPolicyCode());
			res.setValue(updatePolicyResponse.getLimitTargetList().get(0).getPolicyApplyValue());
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
	 * @param req
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
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID(header.getTenantHeader().getTenantId());
		commonRequest.setSystemID(header.getTenantHeader().getSystemId());

		removePolicyRequest.setCommonRequest(commonRequest);
		removePolicyRequest.setLimitTargetList(limitTargetList);

		LOGGER.debug("==>>[SC] RemovePolicyRequest.toString() : {}", removePolicyRequest.toString());

		/** 3. SC회원[updatePolicy] Call. */
		RemovePolicyResponse removePolicyResponse = this.userSCI.removePolicy(removePolicyRequest);

		// Debug
		LOGGER.info("[UserSCI.removePolicy()] - Response CODE : {}, MESSAGE : {}", removePolicyResponse
				.getCommonResponse().getResultCode(), removePolicyResponse.getCommonResponse().getResultMessage());

		// TODO 실패 처리
		if (MemberConstants.RESULT_FAIL.equals(removePolicyResponse.getCommonResponse().getResultCode())) {
			LOGGER.debug("[IndividualPolicyService] - removeIndividualPolicy CODE {}, MESSAGE {} ",
					removePolicyResponse.getCommonResponse().getResultCode(), removePolicyResponse.getCommonResponse()
							.getResultMessage());
		}

		/** 4. SC회원 Call 결과 값으로 Response 생성 및 주입. */
		RemoveIndividualPolicyRes res = new RemoveIndividualPolicyRes();
		res.setPolicyCode(removePolicyResponse.getLimitPolicyCodeList().get(0));
		res.setKey(removePolicyResponse.getLimitPolicyCodeList().get(0));

		LOGGER.debug("==>>[SAC] RemoveIndividualPolicyRes.toString() : {}", res.toString());
		LOGGER.debug("###### MiscellaneousServiceImpl.removeIndividualPolicy [END] ######");
		return res;
	}
}
