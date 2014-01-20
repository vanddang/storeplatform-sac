package com.skplanet.storeplatform.sac.member.miscellaneous.service;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.idp.vo.IDPReceiverM;
import com.skplanet.storeplatform.external.client.message.sci.MessageSCI;
import com.skplanet.storeplatform.external.client.message.vo.SmsSendReq;
import com.skplanet.storeplatform.external.client.uaps.sci.UAPSSCI;
import com.skplanet.storeplatform.external.client.uaps.vo.OpmdRes;
import com.skplanet.storeplatform.external.client.uaps.vo.UapsReq;
import com.skplanet.storeplatform.framework.core.exception.ErrorMessageBuilder;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.StringUtil;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSCI;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserResponse;
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmPhoneAuthorizationCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmPhoneAuthorizationCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetCaptchaRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetOpmdReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetOpmdRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetPhoneAuthorizationCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetPhoneAuthorizationCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetUaCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetUaCodeRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.idp.service.IDPService;
import com.skplanet.storeplatform.sac.member.miscellaneous.repository.MiscellaneousRepository;
import com.skplanet.storeplatform.sac.member.miscellaneous.vo.ServiceAuthDTO;

/**
 * 
 * 기타 기느능 관련 인터페이스 구현체
 * 
 * Updated on : 2014. 1. 7. Updated by : 김다슬, 인크로스.
 */
@Service
public class MiscellaneousServiceImpl implements MiscellaneousService {
	private static final Logger LOGGER = LoggerFactory.getLogger(MiscellaneousServiceImpl.class);

	@Autowired
	private UAPSSCI uapsSCI; // UAPS 연동 Interface.

	@Autowired
	private UserSCI userSCI; // 회원 Component 사용자 기능 Interface.

	@Autowired
	private DeviceSCI deviceSCI; // 회원 Component 휴대기기 기능 Interface.

	@Autowired
	private IDPService idpService; // IDP 연동 class.

	@Autowired
	private MessageSCI messageSCI; // 기타 Component 메시지전송 기능 Interface.

	@Autowired
	private MiscellaneousRepository repository; // 기타 기능 Repository.

	@Override
	public GetOpmdRes getOpmd(GetOpmdReq req) throws Exception {
		String msisdn = req.getMsisdn();

		GetOpmdRes res = new GetOpmdRes();
		res.setMsisdn(msisdn);

		/** 1. OPMD번호(989)여부 검사 */
		if (StringUtils.substring(msisdn, 0, 3).equals("989")) {
			LOGGER.debug("############ >> msisdn {} ", msisdn);
			/** 1) OPMD 모번호 조회 (UAPS 연동) */
			OpmdRes opmdRes = new OpmdRes();
			UapsReq uapsReq = new UapsReq();
			uapsReq.setDeviceId(msisdn);
			opmdRes = this.uapsSCI.getOpmdInfo(uapsReq);

			LOGGER.debug("############ >> opmdRes {}", opmdRes);
			/** 2) UAPS 연동 결과 확인 */
			if (opmdRes.getResultCode() == 0) {
				LOGGER.debug("##### External Comp. UAPS 연동성공 {}", opmdRes.getResultCode());
				LOGGER.debug("##### OPMD MDN {}", opmdRes.getOpmdMdn());
				res.setMsisdn(opmdRes.getMobileMdn());
			} else {
				throw new RuntimeException("UAPS 연동 오류" + opmdRes.getResultCode());
			}
		} else {
			/** 2. OPMD 번호가 아닐경우, Request msisdn을 그대로 반환 */
			res.setMsisdn(msisdn);
			LOGGER.debug("OPMD 번호 아님, Request값 그대로 내려줌.");
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
					LOGGER.debug("######## >>>>>>>>> deviceModelNo {}: ", deviceModelNo);
					if (deviceModelNo != null) {
						// DB 접속(TB_CM_DEVICE) - UaCode 조회
						String uaCode = this.repository.getUaCode(deviceModelNo);
						if (uaCode != null) {
							response.setUaCd(uaCode);
						} else {
							LOGGER.info("deviceId에 해당하는 UA 코드 없음.");
						}
					}
				} else {
					LOGGER.info("SC 회원 API 조회 실패.");
					throw new StorePlatformException(ErrorMessageBuilder.create().defaultMessage("SC 회원 API 조회 실패")
							.build());
					// throw new Exception("[" + searchDeviceResult.getCommonResponse().getResultCode() + "] "
					// + searchDeviceResult.getCommonResponse().getResultMessage());
				}

			} else {
				throw new Exception("유효하지 않은 휴대폰 번호.");
			}
		}
		/** deviceModelNo 가 파라미터로 들어온 경우 */
		else if (deviceModelNo != null) {
			// DB 접속(TB_CM_DEVICE) - UaCode 조회
			String uaCode = this.repository.getUaCode(deviceModelNo);
			if (uaCode != null) {
				response.setUaCd(uaCode);
			} else {
				LOGGER.info("deviceModelNo에 해당하는 UA 코드 없음.");
			}
		}
		return response;
	}

	// 헤더는 controller 에서 SacRequestHeader 셋팅된걸 사용한다. (tennatId, systemId 사용시에만 선언)
	@Override
	public GetPhoneAuthorizationCodeRes getPhoneAuthorizationCode(SacRequestHeader sacRequestHeader,
			GetPhoneAuthorizationCodeReq request) throws Exception {
		String authCode = "";
		/* 1. 휴대폰 인증 코드 생성 */
		Random random = new Random();
		authCode += random.nextInt(999999); // 0~999999 사이의 난수 발생
		LOGGER.debug("###### >> authCode :" + authCode);

		/* 2. 인증 Signautre 생성 - guid 형식 */
		String authSign = UUID.randomUUID().toString().replace("-", "");
		LOGGER.debug("###### >> authSign : " + authSign);

		/* DB에 저장할 파라미터 셋팅 */
		ServiceAuthDTO serviceAuthInfo = new ServiceAuthDTO();
		serviceAuthInfo.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
		serviceAuthInfo.setAuthTypeCd("CM010901");
		serviceAuthInfo.setAuthSign(authSign);
		serviceAuthInfo.setAuthValue(authCode);

		this.repository.insertPhoneAuthCode(serviceAuthInfo);

		/* External Comp.에 SMS 발송 요청 */
		SmsSendReq smsReq = new SmsSendReq();
		smsReq.setSrcId(request.getSrcId()); // test 값 : US004504
		smsReq.setCarrier("SKT"); // Carrier로 파라미터명 변경 예정 ( 테넌트에서 부터 파라미터를 SKT, KT, LGT로 받는다. )
		smsReq.setSendMdn(request.getUserPhone());
		smsReq.setRecvMdn(request.getUserPhone());
		smsReq.setTeleSvcId(request.getTeleSvcId()); // test 값 : 0 (단문 SM)
		smsReq.setMsg(authCode);
		Map<String, String> map = new HashMap<String, String>();

		// External Comp. SMS 발송 기능 현재 작업중.###########################
		// map = this.messageSCI.smsSend(smsReq);
		// String sendResult = map.get("resultStatus");
		// External Comp. SMS 발송 기능 현재 작업중.###########################
		String sendResult = "success";

		GetPhoneAuthorizationCodeRes response = new GetPhoneAuthorizationCodeRes();

		/* SMS 발송 성공 여부를 확인 */
		if (sendResult.equals("success")) {
			response.setPhoneSign(authSign);
		} else {
			throw new Exception("External Component SMS 전송 오류.[" + sendResult + "]");
		}
		return response;
	}

	@Override
	public ConfirmPhoneAuthorizationCodeRes confirmPhoneAutorizationCode(ConfirmPhoneAuthorizationCodeReq request)
			throws Exception {
		ConfirmPhoneAuthorizationCodeRes res = new ConfirmPhoneAuthorizationCodeRes();
		String authCode = request.getPhoneAuthCode();
		String authSign = request.getPhoneSign();
		long timeToLive = Long.parseLong(request.getTimeToLive()); // Tenant 정책에 따라 변경되는 인증번호 생존 시간.
		String userPhone = request.getUserPhone();

		/* DB에 저장할 파라미터 셋팅 */
		ServiceAuthDTO serviceAuthInfo = new ServiceAuthDTO();
		serviceAuthInfo.setAuthTypeCd("CM010901");
		serviceAuthInfo.setAuthSign(authSign);
		serviceAuthInfo.setAuthValue(authCode);

		ServiceAuthDTO resultInfo = this.repository.getPhoneAuthYn(serviceAuthInfo);

		if (resultInfo == null) {
			throw new Exception("인증 코드가 일치 하지 않습니다.");
		}

		if (resultInfo.getAuthComptYn().equals("Y")) {
			throw new Exception("기인증된 인증 코드 입니다.");
		}

		/* 인증유효 시간 만료여부 확인 (현재시간 - 인증코드 생성일자) < timeToLive */
		String nowDate = DateUtil.getDateString(new Date(), "yyyyMMddHHmmss");
		LOGGER.debug(">>> nowDate : " + nowDate);
		long endTime = Long.parseLong(nowDate);

		String createDate = resultInfo.getAuthValueCreateDt();
		long startTime = Long.parseLong(createDate.toString());
		LOGGER.debug("endTime(system current time) : {}", endTime);
		LOGGER.debug("startTime(db create time): {}", startTime);

		long confirmTime = endTime - startTime; // 사용자가 인증코드 입력까지 걸린 분

		if (confirmTime > timeToLive) {
			LOGGER.debug("confirmTime : {}", confirmTime);
			throw new Exception("인증 시간이 만료된 인증 코드입니다.");
		}

		/* 인증유효 시간 내에 인증 요청 - 인증여부 "Y"로 Update. */
		this.repository.updatePhoneAuthYn(String.valueOf(resultInfo.getAuthSeq()));

		res.setUserPhone(userPhone);
		return res;
	}

	@Override
	public GetCaptchaRes getCaptcha() throws Exception {
		String waterMarkImageUrl = "";
		String waterMarkImageSign = "";
		IDPReceiverM idpReciver = new IDPReceiverM();
		/* 1. IDP 연동해서 waterMarkImage URL과 Signature 받기 */
		idpReciver = this.idpService.warterMarkImageUrl();
		waterMarkImageUrl = idpReciver.getResponseBody().getImage_url();
		waterMarkImageSign = idpReciver.getResponseBody().getImage_sign();
		LOGGER.debug("####### >> idpReciver {} ", idpReciver);

		/* 2. 파일 시스템으로 부터 이미지 파일 읽기 */
		File file = new File(waterMarkImageUrl);
		FileInputStream imageStream = new FileInputStream(file);
		byte ImageData[] = new byte[(int) file.length()];
		imageStream.read(ImageData);

		/** TODO 3. 이미지 Byte Array를 String으로 Converting */
		String imageDataString = encodeImage(ImageData);

		LOGGER.debug("################## Captcha 문자 발급 Servie 시작 ######################");
		URL imageUrl = new URL(waterMarkImageUrl);
		Image image = ImageIO.read(imageUrl);
		LOGGER.debug("######## >> imageString {}", image);
		byte[] waterMarkImageByte = waterMarkImageUrl.getBytes();

		/** LOGGER */

		/** TODO 5. 결과 Response */
		GetCaptchaRes response = new GetCaptchaRes();

		// response.setImageData(waterMarkImageByte.toString());
		response.setImageSign(waterMarkImageSign);

		LOGGER.debug("################## Captcha 문자 발급 Servie 끝 ######################");

		return response;
	}

	/**
	 * <pre>
	 * Encodes the byte array info base64 String.
	 * </pre>
	 * 
	 * @param imageByteArray
	 * @return
	 */
	public static String encodeImage(byte[] imageByteArray) {
		return Base64.encodeBase64URLSafeString(imageByteArray);
	}

	// /**
	// * <pre>
	// * method 설명.
	// * </pre>
	// *
	// * @param imageDataString
	// * @return
	// */
	// public static byte[] decodeImage(String imageDataString) {
	// return Base64.decodeBase64(imageDataString);
	// }

	/**
	 * 
	 * <pre>
	 * 휴대폰번호 유효성 검사.
	 * 1. 10자리 또는 11자리 인지 확인.
	 * 2. 010/011/016/017/018 인지 확인
	 * </pre>
	 * 
	 * @param mdn
	 * @return Y/N
	 */
	public String mdnValidation(String mdn) {
		String validation = "N";
		if (mdn.length() == 10 | mdn.length() == 11) {
			String temp = StringUtils.substring(mdn, 0, 3);
			if (temp.equals("010") || temp.equals("011") || temp.equals("016") || temp.equals("017")
					|| temp.equals("018")) {
				validation = "Y";
			}

		}
		return validation;
	}

	/**
	 * <pre>
	 * msisdn으로 userKey 조회하기 - (기타 기능 내 공통 기능 함수로 생성).
	 * </pre>
	 * 
	 * @param commonReq
	 * @param msisdn
	 * @return
	 * @throws Exception
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

}
