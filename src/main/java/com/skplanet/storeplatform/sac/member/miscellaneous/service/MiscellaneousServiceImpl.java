package com.skplanet.storeplatform.sac.member.miscellaneous.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	private static final Logger logger = LoggerFactory.getLogger(MiscellaneousServiceImpl.class);

	@Autowired
	private UAPSSCI uapsSCI; // UAPS 연동 Interface.

	@Autowired
	private UserSCI userSCI; // 회원 Component 사용자 기능 Interface.

	@Autowired
	private DeviceSCI deviceSCI; // 회원 Component 휴대기기 기능 Interface.

	@Autowired
	private IDPService idpService; // IDP 연동 class.

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

		/** 1. OPMD번호(989)여부 검사 ( OPMD 번호가 아닐경우, Request msisdn을 그대로 반환 ) */
		if (StringUtils.substring(msisdn, 0, 3).equals("989")) {
			/** 1) 유효성 검사 (OPMD 번호) */
			if (msisdn.length() == 10) { // ex)9890001111
				logger.debug("############ >> msisdn {} ", msisdn);
				/** 2) OPMD 모번호 조회 (UAPS 연동) */
				OpmdRes opmdRes = new OpmdRes();
				opmdRes = this.uapsSCI.getOpmdInfo(msisdn);

				logger.debug("############ >> opmdRes {}", opmdRes);
				/** 3) UAPS 연동 결과 확인 */
				if (opmdRes.getResultCode() == 0) {
					logger.debug("##### External Comp. UAPS 연동성공 {}", opmdRes.getResultCode());
					logger.debug("##### OPMD MDN {}", opmdRes.getOpmdMdn());
					res.setMsisdn(opmdRes.getMobileMdn());
				} else {
					logger.debug("#####External Comp. UAPS 연동오류 {}", opmdRes.getResultCode());
					throw new RuntimeException("UAPS 연동 오류");
				}
			} else {
				logger.debug("########## Exception : 정상적인 휴대폰번호 아님. ##########");
				throw new Exception("유효하지 않은 휴대폰 번호.");
			}
		} else {
			/** 2. 유효성 검사 (OPMD 번호가 아닌 경우) */
			if (this.mdnValidation(msisdn).equals("Y")) {
				logger.debug("########## OPMD 번호 아님. ##########");
			} else {
				logger.debug("########## Exception : 정상적인 휴대폰번호 아님. ##########");
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
		logger.debug("########### GetUaCodeReq {}", req);

		/** 파라미터로 MSISDN만 넘어온 경우 */
		if (msisdn != null && deviceModelNo == null) {

			/** 1. SC 회원 Request 생성 */
			SearchUserRequest searchUserRequest = new SearchUserRequest();
			SearchDeviceRequest searchDeviceRequest = new SearchDeviceRequest();

			/** 2. MSISDN 유효성 검사 */
			String validation = this.mdnValidation(msisdn);
			if (validation.equals("Y")) {

				/** 3. 임시 공통헤더 생성 주입 */
				searchUserRequest.setCommonRequest(commonRequest);
				searchDeviceRequest.setCommonRequest(commonRequest);

				List<KeySearch> keySearchList = new ArrayList<KeySearch>();

				KeySearch keySearch = new KeySearch();
				keySearch.setKeyType(MemberConstants.KEY_TYPE_DEVICE_ID);
				keySearch.setKeyString(msisdn);
				keySearchList.add(keySearch);
				searchUserRequest.setKeySearchList(keySearchList);
				searchDeviceRequest.setKeySearchList(keySearchList);

				/** 4. deviceId로 userKey 조회 - SC 회원 "회원 기본 정보 조회" */
				SearchUserResponse searchUserResult = new SearchUserResponse();
				searchUserResult = this.userSCI.searchUser(searchUserRequest);

				if (StringUtil.equals(searchUserResult.getCommonResponse().getResultCode(),
						MemberConstants.RESULT_SUCCES)) {
					if (searchUserResult.getUserMbr() == null) {
						logger.debug("######## DeviceId에 해당하는 회원정보 없음. ########");
						throw new Exception("DeviceId에 해당하는 회원정보 없음.");
					} else {
						userKey = searchUserResult.getUserMbr().getUserKey();
						searchDeviceRequest.setUserKey(userKey);
					}

					logger.debug("######## >>>>>>>>> userKey {}: ", userKey);
				} else {
					logger.debug("######## SC 회원 회원 기본 정보 조회 API 연동 오류. ########");
					throw new Exception("[" + searchUserResult.getCommonResponse().getResultCode() + "] "
							+ searchUserResult.getCommonResponse().getResultMessage());
				}
				/** 5. deviceId와 userKey로 deviceModelNo 조회 */
				SearchDeviceResponse searchDeviceResult = new SearchDeviceResponse();
				searchDeviceResult = this.deviceSCI.searchDevice(searchDeviceRequest);

				deviceModelNo = searchDeviceResult.getUserMbrDevice().getDeviceModelNo();
				if (StringUtil.equals(searchDeviceResult.getCommonResponse().getResultCode(),
						MemberConstants.RESULT_SUCCES)) {
					logger.debug("######## >>>>>>>>> deviceModelNo {}: ", deviceModelNo);
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
	public GetCaptchaRes getCaptcha() throws Exception {

		GetCaptchaRes response = new GetCaptchaRes();
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

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param imageDataString
	 * @return
	 */
	public static byte[] decodeImage(String imageDataString) {
		return Base64.decodeBase64(imageDataString);
	}

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

}
