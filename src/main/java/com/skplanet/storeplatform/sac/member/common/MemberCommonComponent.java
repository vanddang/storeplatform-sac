/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.external.client.icas.sci.ICASSCI;
import com.skplanet.storeplatform.external.client.uaps.sci.UAPSSCI;
import com.skplanet.storeplatform.external.client.uaps.vo.UserRes;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserResponse;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.MajorDeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserInfo;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetOpmdReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetUaCodeReq;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.repository.MemberCommonRepository;
import com.skplanet.storeplatform.sac.member.common.vo.Clause;
import com.skplanet.storeplatform.sac.member.common.vo.Device;
import com.skplanet.storeplatform.sac.member.miscellaneous.service.MiscellaneousService;
import com.skplanet.storeplatform.sac.member.user.service.DeviceService;
import com.skplanet.storeplatform.sac.member.user.service.UserSearchServiceImpl;

/**
 * 공통 기능을 임시로 정의해서 사용한다.
 * 
 * Updated on : 2014. 1. 7. Updated by : 심대진, 다모아 솔루션.
 */
@Component
public class MemberCommonComponent {

	private static final Logger LOGGER = LoggerFactory.getLogger(MemberCommonComponent.class);

	@Autowired
	private MemberCommonRepository repository;

	@Autowired
	private MiscellaneousService miscellaneousService;

	@Autowired
	private UserSearchServiceImpl userSelectService;

	@Autowired
	private UAPSSCI uapsSCI;

	@Autowired
	private UserSCI userSCI;

	@Autowired
	private ICASSCI icasSCI;

	@Autowired
	private DeviceService deviceService;

	/**
	 * <pre>
	 * 모번호 조회
	 * 989로 시작하는 MSISDN이 들어오면 모번호를 조회해서 반환하고, 
	 * 989로 시작하지 않는경우 파라미터로 받은 MSISDN을 그대로 반환.
	 * </pre>
	 * 
	 * @param msisdn
	 *            msisdn
	 * @return String
	 * @throws Exception
	 *             Exception
	 */
	public String getOpmdMdnInfo(String msisdn) throws Exception { // 2014. 01. 09. 김다슬, 인크로스. 수정
		GetOpmdReq req = new GetOpmdReq();
		req.setMsisdn(msisdn);

		return this.miscellaneousService.getOpmd(req).getMsisdn();
	}

	/**
	 * <pre>
	 * deviceModelNo로 UA 코드 정보 조회.
	 * </pre>
	 * 
	 * @param deviceModelNo
	 *            String
	 * @return String
	 * @throws Exception
	 *             Exception
	 */
	public String getUaCode(String tenantId, String deviceModelNo) throws Exception { // 2014. 01. 14. 김다슬, 인크로스. 추가
		GetUaCodeReq request = new GetUaCodeReq();
		request.setDeviceModelNo(deviceModelNo);

		SacRequestHeader requestHeader = new SacRequestHeader();
		requestHeader.getTenantHeader().setTenantId(tenantId);
		return this.miscellaneousService.getUaCode(requestHeader, request).getUaCd();
	}

	/**
	 * <pre>
	 * 필수 약관 동의 목록 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            tenantId
	 * @return List<ClauseDTO>
	 * @throws Exception
	 *             Exception
	 */
	public List<Clause> getMandAgreeList(String tenantId) throws Exception {
		return this.repository.getMandAgreeList(tenantId);
	}

	/**
	 * <pre>
	 * 폰 정보 조회.
	 * </pre>
	 * 
	 * @param deviceModelCd
	 *            deviceModelCd
	 * @return DeviceHeader
	 * @throws Exception
	 *             Exception
	 */
	public Device getPhoneInfo(String deviceModelCd) throws Exception {
		return this.repository.getPhoneInfo(deviceModelCd);
	}

	/**
	 * <pre>
	 * 고객정보조회
	 * 모번호 조회및 단말 정보 조회(USPS 정보와 서비스 관리번호 UA_CD 값이 같이 들어와야함.)
	 * TODO 기타 파트에서 api 개발 완료되면 확인해봐야함.
	 * </pre>
	 * 
	 * @param pReqParam
	 *            pReqParam
	 * @param type
	 *            type
	 * @return UserRes
	 * @throws Exception
	 *             Exception
	 */
	public UserRes getMappingInfo(String pReqParam, String type) throws Exception {
		LOGGER.info("## 기타 파트 API 미구현...... (1월 27일 완료 예정이라함.)");
		return this.uapsSCI.getMappingInfo(pReqParam, type);
	}

	/**
	 * 
	 * <pre>
	 * 휴대기기 등록 서브 모듈
	 * SC회원콤포넌트에 휴대기기를 등록, 기등록된 회원의 휴대기기인 경우 구매이관처리, 약관이관, 통합회원인 경우 IDP에 무선회원 해지 요청.
	 * </pre>
	 * 
	 * @param systemId
	 * @param tenantId
	 * @param userKey
	 * @param deviceInfo
	 * @throws Exception
	 */
	public void insertDeviceInfo(String systemId, String tenantId, String userKey, DeviceInfo deviceInfo)
			throws Exception {
		this.deviceService.insertDeviceInfo(systemId, tenantId, userKey, deviceInfo);
	}

	/**
	 * <pre>
	 * 회원 기본 정보 조회 (기본정보만...조회 - TB_US_USERMBR).
	 * test userKey - "IF1023002708420090928145937"
	 * 
	 * TODO 추가 필요한 정보가 있으면 정의해서 쓰면됨.
	 * </pre>
	 * 
	 * @param keyType
	 *            검색 조건 타입
	 * @param keyValue
	 *            검색 조건 값
	 * @param systemId
	 *            시스템 아이디
	 * @param tenantId
	 *            테넌트 아이디
	 * @return UserInfo
	 * @throws Exception
	 *             Exception
	 */
	public UserInfo getUserBaseInfo(String keyType, String keyValue, String systemId, String tenantId) throws Exception {

		Map<String, Object> keyTypeMap = new HashMap<String, Object>();
		keyTypeMap.put("userKey", MemberConstants.KEY_TYPE_INSD_USERMBR_NO);
		keyTypeMap.put("userId", MemberConstants.KEY_TYPE_MBR_ID);
		keyTypeMap.put("deviceKey", MemberConstants.KEY_TYPE_INSD_DEVICE_ID);
		keyTypeMap.put("deviceId", MemberConstants.KEY_TYPE_DEVICE_ID);

		/**
		 * 검색 조건 setting
		 */
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySchUserKey = new KeySearch();
		keySchUserKey.setKeyType(ObjectUtils.toString(keyTypeMap.get(keyType)));
		keySchUserKey.setKeyString(keyValue);
		keySearchList.add(keySchUserKey);

		/**
		 * SearchUserRequest setting
		 */
		SearchUserRequest searchUserRequest = new SearchUserRequest();
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(systemId);
		commonRequest.setTenantID(tenantId);
		searchUserRequest.setCommonRequest(commonRequest);
		searchUserRequest.setKeySearchList(keySearchList);

		/**
		 * SC 사용자 회원 기본정보를 조회
		 */
		SearchUserResponse schUserRes = this.userSCI.searchUser(searchUserRequest);

		UserInfo userInfo = new UserInfo();
		if (StringUtils.equals(schUserRes.getCommonResponse().getResultCode(), MemberConstants.RESULT_SUCCES)) {

			/**
			 * 사용자 정보
			 */
			if (schUserRes.getUserMbr() != null) {
				userInfo.setUserKey(StringUtil.setTrim(schUserRes.getUserMbr().getUserKey()));
				userInfo.setUserType(StringUtil.setTrim(schUserRes.getUserMbr().getUserType()));
				userInfo.setUserMainStatus(StringUtil.setTrim(schUserRes.getUserMbr().getUserMainStatus()));
				userInfo.setUserSubStatus(StringUtil.setTrim(schUserRes.getUserMbr().getUserSubStatus()));
				userInfo.setImSvcNo(StringUtil.setTrim(schUserRes.getUserMbr().getImSvcNo()));
				userInfo.setIsImChanged(StringUtil.setTrim(schUserRes.getUserMbr().getIsImChanged()));
				userInfo.setImRegDat(StringUtil.setTrim(schUserRes.getUserMbr().getImRegDate()));
				userInfo.setUserId(StringUtil.setTrim(schUserRes.getUserMbr().getUserID()));
				userInfo.setUserTelecom(StringUtil.setTrim(schUserRes.getUserMbr().getUserTelecom()));
				userInfo.setUserPhoneCountry(StringUtil.setTrim(schUserRes.getUserMbr().getUserPhoneCountry()));
				userInfo.setUserPhone(StringUtil.setTrim(schUserRes.getUserMbr().getUserPhone()));
				userInfo.setIsRecvSMS(StringUtil.setTrim(schUserRes.getUserMbr().getIsRecvSMS()));
				userInfo.setUserEmail(StringUtil.setTrim(schUserRes.getUserMbr().getUserEmail()));
				userInfo.setIsRecvEmail(StringUtil.setTrim(schUserRes.getUserMbr().getIsRecvEmail()));
				userInfo.setUserName(StringUtil.setTrim(schUserRes.getUserMbr().getUserName()));
				userInfo.setUserSex(StringUtil.setTrim(schUserRes.getUserMbr().getUserSex()));
				userInfo.setUserBirthDay(StringUtil.setTrim(schUserRes.getUserMbr().getUserBirthDay()));
				userInfo.setUserZip(StringUtil.setTrim(schUserRes.getUserMbr().getUserZip()));
				userInfo.setUserAddress(StringUtil.setTrim(schUserRes.getUserMbr().getUserAddress()));
				userInfo.setUserDetailAddress(StringUtil.setTrim(schUserRes.getUserMbr().getUserDetailAddress()));
				userInfo.setUserCity(StringUtil.setTrim(schUserRes.getUserMbr().getUserCity()));
				userInfo.setUserState(StringUtil.setTrim(schUserRes.getUserMbr().getUserState()));
				userInfo.setUserCountry(StringUtil.setTrim(schUserRes.getUserMbr().getUserCountry()));
				userInfo.setUserLanguage(StringUtil.setTrim(schUserRes.getUserMbr().getUserLanguage()));
				userInfo.setIsParent(StringUtil.setTrim(schUserRes.getUserMbr().getIsParent()));
				userInfo.setIsRealName(StringUtil.setTrim(schUserRes.getUserMbr().getIsRealName()));
				userInfo.setImSiteCode(StringUtil.setTrim(schUserRes.getUserMbr().getImSiteCode()));
			} else {
				throw new RuntimeException("SearchUser.UserMbr (사용자정보) 데이터가 없습니다.");
			}

		}

		return userInfo;
	}

	/**
	 * <pre>
	 * ICAS 연동 getCustomer.
	 * </pre>
	 * 
	 * @param msisdn
	 *            String
	 * @return Map<String, String>
	 * @throws Exception
	 *             Exception
	 */
	public Map<String, String> getCustomer(String msisdn) throws Exception {
		return this.icasSCI.getCustomer(msisdn);
	}

	/**
	 * <pre>
	 * ICAS 연동 getCustomerCard.
	 * </pre>
	 * 
	 * @param msisdn
	 *            String
	 * @return Map<String, String>
	 * @throws Exception
	 *             Exception
	 */
	public Map<String, String> getCustomerCard(String msisdn) throws Exception {
		return this.icasSCI.getCustomerCard(msisdn);
	}

	/**
	 * <pre>
	 * ICAS 연동 getMvService.
	 * </pre>
	 * 
	 * @param msisdn
	 *            String
	 * @return Map<String, String>
	 * @throws Exception
	 *             Exception
	 */
	public Map<String, String> getMvService(String msisdn) throws Exception {
		return this.icasSCI.getMvService(msisdn);
	}

	/**
	 * <pre>
	 * UA 코드정보, SKT 통합관리번호, .
	 * </pre>
	 * 
	 * @param model
	 * @param deviceTelecom
	 * @param deviceId
	 * @param deviceIdType
	 * @return
	 * @throws Exception
	 *             MajorDeviceInfo
	 */
	public MajorDeviceInfo setCheckMajorDeviceInfo(String model, String deviceTelecom, String deviceId, String deviceIdType) throws Exception {

		MajorDeviceInfo majorDeviceInfo = new MajorDeviceInfo();

		/**
		 * 폰정보 조회 (Phone ModelCode)
		 */
		Device deviceDTO = this.getPhoneInfo(model);
		if (deviceDTO == null) {

			/**
			 * 미지원 단말 setting
			 */
			majorDeviceInfo.setUacd(MemberConstants.NOT_SUPPORT_HP_UACODE); // UA 코드
			majorDeviceInfo.setDeviceTelecom(MemberConstants.DEVICE_TELECOM_NSH); // 이동 통신사
			majorDeviceInfo.setDeviceModelNo(MemberConstants.NOT_SUPPORT_HP_MODEL_CD); // 기기 모델 번호
			majorDeviceInfo.setDeviceNickName(MemberConstants.NOT_SUPPORT_HP_MODEL_NM); // 기기명

		} else {

			// UA코드 setting
			if (StringUtils.equals(deviceTelecom, MemberConstants.DEVICE_TELECOM_SKT)) {

				/**
				 * SKT 가입자이면서 MSISDN 타입일 경우에만 UAPS 연동 하여 (SKT 서비스가입번호와 UACD를 세팅한다.)
				 */
				if (StringUtils.equals(deviceIdType, MemberConstants.DEVICE_ID_TYPE_MSISDN)) {
					UserRes userRes = this.getMappingInfo(deviceId, "mdn");
					if (userRes.getResultCode() == 0) {
						LOGGER.debug("## UAPS UA 코드             : {}", userRes.getDeviceModel());
						LOGGER.debug("## UAPS SKT 서비스 관리번호 : {}", userRes.getSvcMngNum());
						majorDeviceInfo.setDeviceModelNo(userRes.getDeviceModel());
						majorDeviceInfo.setImMngNum(userRes.getSvcMngNum());
					}

				}

				/**
				 * OMD 단말정보 조회
				 * 
				 * TODO 모바일 전용회원에서만 IDP 연동후에 내려주는 uacd 값을 가지고 조회했으며 세팅도 했다. (적용 여부 논의 해야 할듯...)
				 */
				if (this.repository.getOmdCount("") > 0) {
					majorDeviceInfo.setOmdUacd("");
				}

			} else {

				/**
				 * DB 정보로 uacd를 세팅한다.
				 */
				LOGGER.debug("## DB UA 코드 : {}", deviceDTO.getUaCd());
				majorDeviceInfo.setUacd(deviceDTO.getUaCd()); // UA 코드

			}

			majorDeviceInfo.setDeviceTelecom(deviceTelecom); // 이동 통신사
			majorDeviceInfo.setDeviceModelNo(deviceDTO.getDeviceModelCd()); // 기기 모델 번호
			majorDeviceInfo.setDeviceNickName(deviceDTO.getModelNm()); // 기기명

			/**
			 * UUID 일때 이동통신사코드가 IOS가 아니면 로그찍는다. (테넌트에서 잘못 올려준 데이타.) [[ AS-IS 로직은 하드코딩 했었음... IOS 이북 보관함 지원 uuid ]]
			 */
			if (StringUtils.equals(deviceIdType, MemberConstants.DEVICE_ID_TYPE_UUID)) {
				if (!StringUtils.equals(deviceTelecom, MemberConstants.DEVICE_TELECOM_IOS)) {
					LOGGER.warn("###############################################################################");
					LOGGER.warn("##### UUID 일때는 무조건 이동통신사 코드를 IOS로 줘야 한다. AI-IS 로직 반영.... #####");
					LOGGER.warn("###############################################################################");
				}
			}

		}

		LOGGER.info("## 단말 주요정보 : {}", majorDeviceInfo.toString());

		return majorDeviceInfo;

	}
}
