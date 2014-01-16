package com.skplanet.storeplatform.sac.member.miscellaneous.service;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.rmi.dgc.VMID;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
import com.skplanet.storeplatform.framework.core.util.StringUtil;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSCI;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserResponse;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetCaptchaRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetOpmdReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetOpmdRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetPhoneAuthorizationCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetPhoneAuthorizationCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetUaCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetUaCodeRes;
import com.skplanet.storeplatform.sac.member.common.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.idp.service.IDPService;
import com.skplanet.storeplatform.sac.member.miscellaneous.repository.MiscellaneousRepository;

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

	private static final String SYSTEMID = "S001";
	private static final String TENANTID = "S01";
	private static CommonRequest commonRequest;

	static {
		commonRequest = new CommonRequest();
		commonRequest.setSystemID(SYSTEMID);
		commonRequest.setTenantID(TENANTID);
	}

	@Override
	public GetOpmdRes getOpmd(GetOpmdReq req) throws Exception {
		String msisdn = req.getMsisdn();

		GetOpmdRes res = new GetOpmdRes();
		res.setMsisdn(msisdn);

		/** 1. OPMD번호(989)여부 검사 */
		if (StringUtils.substring(msisdn, 0, 3).equals("989")) {
			/** 1) 유효성 검사 (OPMD 번호) */
			if (msisdn.length() == 10) { // ex)9890001111
				LOGGER.debug("############ >> msisdn {} ", msisdn);
				/** 2) OPMD 모번호 조회 (UAPS 연동) */
				OpmdRes opmdRes = new OpmdRes();
				opmdRes = this.uapsSCI.getOpmdInfo(msisdn);

				LOGGER.debug("############ >> opmdRes {}", opmdRes);
				/** 3) UAPS 연동 결과 확인 */
				if (opmdRes.getResultCode() == 0) {
					LOGGER.debug("##### External Comp. UAPS 연동성공 {}", opmdRes.getResultCode());
					LOGGER.debug("##### OPMD MDN {}", opmdRes.getOpmdMdn());
					res.setMsisdn(opmdRes.getMobileMdn());
				} else {
					throw new RuntimeException("UAPS 연동 오류");
				}
			} else {
				throw new Exception("유효하지 않은 휴대폰 번호.");
			}
		} else {
			/** 2. OPMD 번호가 아닐경우, Request msisdn을 그대로 반환 */
			if (this.mdnValidation(msisdn).equals("Y")) {
				res.setMsisdn(msisdn);
				LOGGER.debug("OPMD 번호 아님, Request 번호 그대로 내려줌.");
			} else {
				throw new Exception("유효하지 않은 휴대폰 번호.");
			}
		}
		return res;
	}

	@Override
	public GetUaCodeRes getUaCode(GetUaCodeReq req) throws Exception {
		String deviceModelNo = req.getDeviceModelNo();
		String msisdn = req.getMsisdn();
		String userKey = "";

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

				/** 4. 임시 공통헤더 생성 주입 및 Request 파라미터 셋팅 */
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
							throw new Exception("deviceModelNo에 해당하는 UA 코드 없음.");
						}
					}
				} else if (deviceModelNo == null) {
					throw new Exception("userKey와 deviceId에 해당하는 deviceModelNo 없음.");
				} else {
					throw new Exception("[" + searchDeviceResult.getCommonResponse().getResultCode() + "] "
							+ searchDeviceResult.getCommonResponse().getResultMessage());
				}

			} else {
				throw new Exception("유효하지 않은 휴대폰 번호.");
			}
		}
		/** deviceModelNo 가 파라미터로 들어온 경우 */
		else if ((msisdn != null && deviceModelNo != null) || (msisdn == null && deviceModelNo != null)) {
			// DB 접속(TB_CM_DEVICE) - UaCode 조회
			String uaCode = this.repository.getUaCode(deviceModelNo);
			if (uaCode != null) {
				response.setUaCd(uaCode);
			} else {
				throw new Exception("deviceModelNo에 해당하는 UA 코드 없음.");
			}
		} else {
			throw new Exception("필수 파라미터 없음.");
		}

		return response;
	}

	@Override
	public GetPhoneAuthorizationCodeRes getPhoneAuthorizationCode(GetPhoneAuthorizationCodeReq request)
			throws Exception {
		int authCodeLength = 6; // 인증 코드의 자리수
		String authCode = "";
		/** TODO 1. 휴대폰 인증 코드 생성 */
		Random random = new Random();
		for (int i = 0; i < 6; i++) {
			authCode += random.nextInt(9); // 0~9 사이의 난수 발생
		}
		LOGGER.debug("###### >> authCode :" + authCode);

		/** TODO 2. 인증 Signautre 생성 - guid 형식 */
		VMID guid = new VMID();
		String authSign = guid.toString();
		LOGGER.debug("###### >> authSign : " + authSign);

		/** TODO 3. External Comp.에 SMS 발송 요청 */
		SmsSendReq smsReq = new SmsSendReq();
		smsReq.setSrcId("OR00401"); // 요청측 ID? 는 뭐임?
		smsReq.setDeviceTelecom(request.getUserTelecom());
		smsReq.setSendMdn(request.getUserPhone());
		smsReq.setRecvMdn(request.getUserPhone());
		smsReq.setTeleSvcId("0");// 텔레서비스ID는 뭐임?
		smsReq.setMsg(authCode);
		Map<String, String> map = new HashMap<String, String>();
		map = this.messageSCI.smsSend(smsReq);
		String sendResult = map.get("resultStatus");

		/** SMS 발송 성공 여부를 확인 */
		if (sendResult.equals("success")) {
			/** userPhone을 통해 userKey값을 조회 - getUserKey Function 호출 */
			String userKey = this.getUserKey(commonRequest, request.getUserPhone());
			if (userKey == null) {
				throw new Exception("휴대폰번호에 해당하는 userKey 없음.");
			}

			/** 해당 회원이 인증되었는지 여부 확인 DB(auth_compt_yn) */
			HashMap<String, String> resultMap = new HashMap<String, String>();
			resultMap = this.repository.getPhoneAuthYn(userKey);
			if (resultMap.get("auth_compt_yn").equals("Y")) {
				throw new Exception("이미 인증된 회원 입니다.");
			} else {
				/** 기존 인증된 회원이 아닌경우 생성된 인증코드를 DB에 저장 */
				// 인증 코드 등록 일자 생성
				GregorianCalendar gCalendar = new GregorianCalendar();
				String createDate = gCalendar.getTime().toString();

				/** Request 파라미터를 Map에 셋팅 */
				HashMap<String, String> phoneAuthCodeInfo = new HashMap<String, String>();
				phoneAuthCodeInfo.put("mbr_no", userKey);
				phoneAuthCodeInfo.put("tenant_id", commonRequest.getTenantID());
				phoneAuthCodeInfo.put("auth_sign", authSign);
				phoneAuthCodeInfo.put("auth_value", authCode);
				phoneAuthCodeInfo.put("auth_value_create_dt", createDate);

				/** 인증코드 값이 있으면 update, 없으면 insert 처리함. */
				// this.repository.insertPhoneAuthCode(phoneAuthCodeInfo);
				this.repository.mergeIntoPhoneAuthCode(phoneAuthCodeInfo);
			}

		} else {
			throw new Exception("External Component SMS 전송 오류.[" + sendResult + "]");
		}

		/** TODO 6. 결과 Response */
		GetPhoneAuthorizationCodeRes response = new GetPhoneAuthorizationCodeRes();
		response.setPhoneSign(authSign);
		return response;
	}

	@Override
	public GetCaptchaRes getCaptcha() throws Exception {
		String waterMarkImageUrl = "";
		String waterMarkImageSign = "";
		IDPReceiverM idpReciver = new IDPReceiverM();
		/** 1. IDP 연동해서 waterMarkImage URL과 Signature 받기 */
		idpReciver = this.idpService.warterMarkImageUrl();
		waterMarkImageUrl = idpReciver.getResponseBody().getImage_url();
		waterMarkImageSign = idpReciver.getResponseBody().getImage_sign();
		LOGGER.debug("####### >> idpReciver {} ", idpReciver);

		/** TODO 2. 파일 시스템으로 부터 이미지 파일 읽기 */
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
			}
		} else {
			LOGGER.debug("######## SC 회원 회원 기본 정보 조회 API 연동 오류. ########");
			throw new Exception("[" + searchUserResponse.getCommonResponse().getResultCode() + "] "
					+ searchUserResponse.getCommonResponse().getResultMessage());
		}
		return userKey;
	}
}
