package com.skplanet.storeplatform.sac.member.miscellaneous.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.uaps.sci.UAPSSCI;
import com.skplanet.storeplatform.external.client.uaps.vo.OpmdRes;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSCI;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserRequest;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetOpmdReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetOpmdRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetUaCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetUaCodeRes;
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
	private UAPSSCI uapsSCI;

	@Autowired
	private UserSCI userSCI;

	@Autowired
	private DeviceSCI deviceSCI;

	@Autowired
	private MiscellaneousRepository repository;

	@Override
	public GetOpmdRes getOpmd(GetOpmdReq req) throws Exception {
		String msisdn = req.getMsisdn();

		GetOpmdRes res = new GetOpmdRes();
		res.setMsisdn(msisdn);

		// 자번호(989)가 아닌 경우, request로 전달된 msisdn 그대로 반환
		if (StringUtils.substring(msisdn, 0, 3).equals("989")) {
			logger.info("## 모번호 조회 START >> {}", msisdn);
			OpmdRes opmdRes = this.uapsSCI.getOpmdInfo(msisdn);// 989 MSISDN이 들어오면 여기 지금 에러남.
			logger.info("#################################");
			if (opmdRes.getResultCode() == 0) {
				res.setMsisdn(opmdRes.getMobileMdn());
			} else {
				throw new RuntimeException("UAPS 연동 오류");
			}

		}
		return res;
	}

	@Override
	public GetUaCodeRes getUaCode(GetUaCodeReq request) throws Exception {
		String deviceModelNo = request.getDeviceModelNo();
		String msisdn = request.getMsisdn();
		String userKey = "";

		GetUaCodeRes response = new GetUaCodeRes();
		logger.info("########### GetUaCodeReq {}", request);

		if (msisdn != null && deviceModelNo == null) { // 파라미터로 MSISDN만 넘어온 경우

			/** 1. SC 회원 Request 생성 */
			SearchUserRequest searchUserRequest = new SearchUserRequest();
			SearchDeviceRequest searchDeviceRequest = new SearchDeviceRequest();

			/** 2. MSISDN 유효성 검사 */
			String validation = this.mdnValidation(msisdn);
			if (validation.equals("Y")) {

				/** TODO 3. 임시 공통헤더 생성 주입 */
				searchUserRequest.setCommonRequest(this.imsiCommonRequest());
				searchDeviceRequest.setCommonRequest(this.imsiCommonRequest());
				logger.info("##### >> imsiCommonRequest {}", searchDeviceRequest.getCommonRequest());

				/**
				 * 검색 조건 타입 <br>
				 * INSD_USERMBR_NO : 내부 사용자 키 <br>
				 * MBR_ID : 사용자 ID <br>
				 * INSD_SELLERMBR_NO : 내부 판매자 키 <br>
				 * SELLERMBR_ID : 판매자 ID <br>
				 * USERMBR_NO : 통합서비스 키 <br>
				 * INSD_DEVICE_ID : 내부 기기 키 <br>
				 * DEVICE_ID : 기기 ID <br>
				 * EMAIL_ADDR : 사용자 이메일 <br>
				 * EMAIL : 판매자 이메일 <br>
				 * TEL_NO : 사용자 연락처 <br>
				 * WILS_TEL_NO : 판매자 연락처
				 */
				List<KeySearch> keySearchs = new ArrayList<KeySearch>();

				KeySearch keySearch = new KeySearch();
				keySearch = new KeySearch();
				keySearch.setKeyType("DEVICE_ID");
				keySearch.setKeyString(msisdn);
				keySearchs.add(keySearch);
				searchUserRequest.setKeySearchList(keySearchs);
				searchDeviceRequest.setKeySearchList(keySearchs);

				/** 4. deviceId로 userKey 조회 - SC 회원 "회원 기본 정보 조회" API > DEVICE_ID를 이용한 회원정보 조회 기능 미구현으로 아직 동작 안됨. */
				// userKey = this.userSCI.searchUser(searchUserRequest).getUserKey();
				// logger.info("######## >>>>>>>>> userKey {}: ", userKey);
				// searchDeviceRequest.setUserKey(userKey);
				searchDeviceRequest.setUserKey("IF1023002708420090928145937"); // 일단 조회 되도록 userkey 셋팅
																			   // (msisdn=01088902431)

				if (userKey == null) { // userKey가 존재하지 않을 경우.
					logger.info("########## Exception : userKey가 존재하지 않음. ##########");
					throw new Exception("userKey가 존재하지 않음.");
				}
				/** 5. deviceId로 deviceModelNo 조회 */
				deviceModelNo = this.deviceSCI.searchDevice(searchDeviceRequest).getUserMbrDevice().getDeviceModelNo();
				logger.info("######## >>>>>>>>> deviceModelNo {}: ", deviceModelNo);
			} else {
				logger.info("########## Exception : 정상적인 휴대폰번호 아님. ##########");
				throw new Exception("유효하지 않은 휴대폰 번호.");
			}
		}
		if (deviceModelNo != null) { // 파라미터로 deviceModelNo가 넘어왔거나, MDN으로 deviceModelNo를 조회해서 가져온 경우
			// DB 접속(TB_CM_DEVICE) - UaCode 조회
			String uaCode = this.repository.getUaCode(deviceModelNo);
			response.setUaCd(uaCode);
		}

		return response;
	}

	/**
	 * 
	 * <pre>
	 * 휴대폰번호 유효성 검사.
	 * </pre>
	 * 
	 * @param mdn
	 * @return Y/N
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
	 * TODO 임시 SC회원 전달용 공통헤더
	 * </pre>
	 * 
	 * @return
	 */
	private CommonRequest imsiCommonRequest() {
		/** TODO 임시 공통헤더 생성 주입 */
		CommonRequest commonRequest = new CommonRequest();
		// S001(ShopClient), S002(WEB), S003(OpenAPI)
		commonRequest.setSystemID("S001");
		commonRequest.setTenantID("S01");
		return commonRequest;
	}
}
