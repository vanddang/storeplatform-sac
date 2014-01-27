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
import com.skplanet.storeplatform.external.client.uaps.sci.UapsSCI;
import com.skplanet.storeplatform.external.client.uaps.vo.UapsReq;
import com.skplanet.storeplatform.external.client.uaps.vo.UserRes;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.common.vo.MbrMangItemPtcr;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchManagementListRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchManagementListResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserResponse;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.MajorDeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserExtraInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserInfo;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetOpmdReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetUaCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.UserExtraInfoRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.repository.MemberCommonRepository;
import com.skplanet.storeplatform.sac.member.common.vo.Clause;
import com.skplanet.storeplatform.sac.member.common.vo.Device;
import com.skplanet.storeplatform.sac.member.miscellaneous.service.MiscellaneousService;
import com.skplanet.storeplatform.sac.member.user.service.DeviceService;

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
	private UapsSCI uapsSCI;

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
		return this.repository.searchMandatoryAgreeList(tenantId);
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
		return this.repository.searchPhoneInfo(deviceModelCd);
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
		UapsReq uapsReq = new UapsReq();
		uapsReq.setDeviceId(pReqParam);
		uapsReq.setType(type);
		return this.uapsSCI.getMappingInfo(uapsReq);
	}

	/**
	 * 
	 * <pre>
	 * </pre>
	 * 
	 * @param systemId
	 * @param tenantId
	 * @param userKey
	 * @param deviceInfo
	 * @throws Exception
	 */
	/**
	 * <pre>
	 * 휴대기기 등록 서브 모듈.
	 * SC회원콤포넌트에 휴대기기를 등록, 기등록된 회원의 휴대기기인 경우 구매이관처리, 약관이관, 통합회원인 경우 IDP에 무선회원 해지 요청.
	 * </pre>
	 * 
	 * @param systemId
	 *            String
	 * @param tenantId
	 *            String
	 * @param userKey
	 *            String
	 * @param deviceInfo
	 *            DeviceInfo
	 * @return deviceKey
	 * @throws Exception
	 *             Exception
	 */
	public String insertDeviceInfo(String systemId, String tenantId, String userKey, DeviceInfo deviceInfo)
			throws Exception {
		return this.deviceService.insertDeviceInfo(systemId, tenantId, userKey, deviceInfo);
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
	 *            검색 조건 타입 (userKey, userId, deviceKey, deviceId)
	 * @param keyValue
	 *            검색 조건 값
	 * @param sacHeader
	 *            공통 헤더
	 * @return UserInfo Value Object
	 * @throws Exception
	 *             Exception
	 */
	public UserInfo getUserBaseInfo(String keyType, String keyValue, SacRequestHeader sacHeader) throws Exception {
		LOGGER.debug("###### getUserBaseInfo Req : {}, {}, {}", keyType, keyValue, sacHeader.getTenantHeader()
				.toString());

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
		commonRequest.setSystemID(sacHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(sacHeader.getTenantHeader().getTenantId());
		searchUserRequest.setCommonRequest(commonRequest);
		searchUserRequest.setKeySearchList(keySearchList);

		/**
		 * SC 사용자 회원 기본정보를 조회
		 */
		UserInfo userInfo = new UserInfo();
		SearchUserResponse schUserRes = this.userSCI.searchUser(searchUserRequest);

		if (schUserRes.getUserMbr() == null) {
			throw new RuntimeException("######## 사용자 기본정보 조회 : 사용자 데이터 없음");
		} else if (StringUtils.equals(schUserRes.getCommonResponse().getResultCode(), MemberConstants.RESULT_SUCCES)
				&& schUserRes.getUserMbr() != null) {
			userInfo.setDeviceCount(StringUtil.setTrim(schUserRes.getUserMbr().getDeviceCount()));
			userInfo.setImMbrNo(StringUtil.setTrim(schUserRes.getUserMbr().getImMbrNo()));
			userInfo.setImRegDate(StringUtil.setTrim(schUserRes.getUserMbr().getImRegDate()));
			userInfo.setImSiteCode(StringUtil.setTrim(schUserRes.getUserMbr().getImSiteCode()));
			userInfo.setImSvcNo(StringUtil.setTrim(schUserRes.getUserMbr().getImSvcNo()));
			userInfo.setIsImChanged(StringUtil.setTrim(schUserRes.getUserMbr().getIsImChanged()));
			userInfo.setIsMemberPoint(StringUtil.setTrim(schUserRes.getUserMbr().getIsMemberPoint()));
			userInfo.setIsParent(StringUtil.setTrim(schUserRes.getUserMbr().getIsParent()));
			userInfo.setIsRealName(StringUtil.setTrim(schUserRes.getUserMbr().getIsRealName()));
			userInfo.setIsRecvEmail(StringUtil.setTrim(schUserRes.getUserMbr().getIsRecvEmail()));
			userInfo.setIsRecvSMS(StringUtil.setTrim(schUserRes.getUserMbr().getIsRecvSMS()));
			userInfo.setLoginStatusCode(StringUtil.setTrim(schUserRes.getUserMbr().getLoginStatusCode()));
			userInfo.setRegDate(StringUtil.setTrim(schUserRes.getUserMbr().getRegDate()));
			userInfo.setSecedeDate(StringUtil.setTrim(schUserRes.getUserMbr().getSecedeDate()));
			userInfo.setSecedeReasonCode(StringUtil.setTrim(schUserRes.getUserMbr().getSecedeReasonCode()));
			userInfo.setSecedeReasonMessage(StringUtil.setTrim(schUserRes.getUserMbr().getSecedeReasonMessage()));
			userInfo.setStopStatusCode(StringUtil.setTrim(schUserRes.getUserMbr().getStopStatusCode()));
			userInfo.setUserAddress(StringUtil.setTrim(schUserRes.getUserMbr().getUserAddress()));
			userInfo.setUserBirthDay(StringUtil.setTrim(schUserRes.getUserMbr().getUserBirthDay()));
			userInfo.setUserCity(StringUtil.setTrim(schUserRes.getUserMbr().getUserCity()));
			userInfo.setUserCountry(StringUtil.setTrim(schUserRes.getUserMbr().getUserCountry()));
			userInfo.setUserDetailAddress(StringUtil.setTrim(schUserRes.getUserMbr().getUserDetailAddress()));
			userInfo.setUserEmail(StringUtil.setTrim(schUserRes.getUserMbr().getUserEmail()));
			userInfo.setUserId(StringUtil.setTrim(schUserRes.getUserMbr().getUserID()));
			userInfo.setUserKey(StringUtil.setTrim(schUserRes.getUserMbr().getUserKey()));
			userInfo.setUserLanguage(StringUtil.setTrim(schUserRes.getUserMbr().getUserLanguage()));
			userInfo.setUserMainStatus(StringUtil.setTrim(schUserRes.getUserMbr().getUserMainStatus()));
			userInfo.setUserName(StringUtil.setTrim(schUserRes.getUserMbr().getUserName()));
			userInfo.setUserPhone(StringUtil.setTrim(schUserRes.getUserMbr().getUserPhone()));
			userInfo.setUserPhoneCountry(StringUtil.setTrim(schUserRes.getUserMbr().getUserPhoneCountry()));
			userInfo.setUserSex(StringUtil.setTrim(schUserRes.getUserMbr().getUserSex()));
			userInfo.setUserState(StringUtil.setTrim(schUserRes.getUserMbr().getUserState()));
			userInfo.setUserSubStatus(StringUtil.setTrim(schUserRes.getUserMbr().getUserSubStatus()));
			userInfo.setUserTelecom(StringUtil.setTrim(schUserRes.getUserMbr().getUserTelecom()));
			userInfo.setUserType(StringUtil.setTrim(schUserRes.getUserMbr().getUserType()));
			userInfo.setUserZip(StringUtil.setTrim(schUserRes.getUserMbr().getUserZip()));
		} else {
			LOGGER.debug("###### 사용자 기본 정보 조회 오류 : {}", searchUserRequest.toString());
			LOGGER.debug("###### 사용자 기본 정보 조회 오류 : {}", schUserRes.getUserMbr().toString());
			LOGGER.debug("###### 사용자 기본 정보 조회 오류 : {}", schUserRes.getCommonResponse().toString());
			throw new RuntimeException("사용자 기본 정보 조회 오류");
		}

		return userInfo;
	}

	/**
	 * <pre>
	 * 회원 부가 정보 조회.
	 * test userKey - "IF1023002708420090928145937"
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
	 * @return UserExtraInfo
	 * @throws Exception
	 *             Exception
	 */
	public UserExtraInfoRes getUserExtraInfo(String userKey, SacRequestHeader sacHeader) throws Exception {

		/**
		 * SearchManagementListRequest setting
		 */
		SearchManagementListRequest searchUserExtraRequest = new SearchManagementListRequest();
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(sacHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(sacHeader.getTenantHeader().getTenantId());
		searchUserExtraRequest.setCommonRequest(commonRequest);
		searchUserExtraRequest.setUserKey(userKey);

		/**
		 * SC 사용자 회원 부가정보를 조회
		 */
		UserExtraInfoRes extraRes = new UserExtraInfoRes();
		List<UserExtraInfo> listExtraInfo = new ArrayList<UserExtraInfo>();

		SearchManagementListResponse schUserExtraRes = this.userSCI.searchManagementList(searchUserExtraRequest);

		LOGGER.debug("############ 부가정보 리스트 Size : {}", schUserExtraRes.getMbrMangItemPtcrList().size());

		if (!StringUtils.equals(schUserExtraRes.getCommonResponse().getResultCode(), MemberConstants.RESULT_SUCCES)) {
			throw new RuntimeException("######## 사용자 부가정보 조회실패 : "
					+ schUserExtraRes.getCommonResponse().getResultCode() + " MSG : "
					+ schUserExtraRes.getCommonResponse().getResultMessage());
		} else if (StringUtils.equals(schUserExtraRes.getCommonResponse().getResultCode(),
				MemberConstants.RESULT_SUCCES)) {

			/* 유저키 세팅 */
			extraRes.setUserKey(schUserExtraRes.getUserKey());
			/* 부가정보 값 세팅 */
			for (MbrMangItemPtcr ptcr : schUserExtraRes.getMbrMangItemPtcrList()) {

				LOGGER.debug("###### SC 부가정보 데이터 검증 CODE {}", ptcr.getExtraProfile());
				LOGGER.debug("###### SC 부가정보 데이터 검증 VALUE {}", ptcr.getExtraProfileValue());

				if (ptcr.getExtraProfile() == null && ptcr.getExtraProfileValue() == null) {
					throw new RuntimeException("######## 사용자 부가정보 조회 : ProfileCode, ProfileValue 없음");
				}

				UserExtraInfo extra = new UserExtraInfo();
				extra.setExtraProfileCode(ptcr.getExtraProfile());
				extra.setExtraProfileValue(ptcr.getExtraProfileValue());

				listExtraInfo.add(extra);
			}

			extraRes.setAddInfoList(listExtraInfo);

		}
		return extraRes;
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
	 * 휴대기기 등록시에 기본정보 setting.
	 * </pre>
	 * 
	 * @param model
	 *            단말 모델
	 * @param deviceTelecom
	 *            이동통신사
	 * @param deviceId
	 *            (msisdn or uuid or mac value)
	 * @param deviceIdType
	 *            (msisdn or uuid or mac type)
	 * @return MajorDeviceInfo
	 * @throws Exception
	 *             익셉션
	 * 
	 */
	public MajorDeviceInfo getDeviceBaseInfo(String model, String deviceTelecom, String deviceId, String deviceIdType)
			throws Exception {

		MajorDeviceInfo majorDeviceInfo = new MajorDeviceInfo();

		/**
		 * 폰정보 조회후 단말 정보 세팅.
		 */
		Device deviceDTO = this.getPhoneInfo(model);
		if (deviceDTO == null) {

			/**
			 * 미지원 단말 setting
			 */
			LOGGER.info("## 미지원 단말 세팅.");
			majorDeviceInfo.setUacd(MemberConstants.NOT_SUPPORT_HP_UACODE); // UA 코드
			majorDeviceInfo.setDeviceTelecom(MemberConstants.DEVICE_TELECOM_NSH); // 이동 통신사
			majorDeviceInfo.setDeviceModelNo(MemberConstants.NOT_SUPPORT_HP_MODEL_CD); // 기기 모델 번호
			majorDeviceInfo.setDeviceNickName(MemberConstants.NOT_SUPPORT_HP_MODEL_NM); // 기기명

		} else {

			majorDeviceInfo.setUacd(deviceDTO.getUaCd()); // UA 코드
			majorDeviceInfo.setDeviceModelNo(deviceDTO.getDeviceModelCd()); // 기기 모델 번호
			majorDeviceInfo.setDeviceNickName(deviceDTO.getModelNm()); // 기기명

			/**
			 * 미지원 단말 예외처리. (UA 코드 여부에 따라 이동통신사 코드 변경)
			 */
			if (StringUtils.equals(deviceDTO.getUaCd(), MemberConstants.NOT_SUPPORT_HP_UACODE)) {
				majorDeviceInfo.setDeviceTelecom(MemberConstants.DEVICE_TELECOM_NSH); // 이동 통신사
			} else {
				majorDeviceInfo.setDeviceTelecom(deviceTelecom); // 이동 통신사
			}

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

		/**
		 * SKT 가입자일 경우 처리
		 */
		if (StringUtils.equals(deviceTelecom, MemberConstants.DEVICE_TELECOM_SKT)) {

			/**
			 * MDN 일경우만 UAPS 연동 하여 (SKT 서비스가입번호를 세팅한다.)
			 */
			if (StringUtils.equals(deviceIdType, MemberConstants.DEVICE_ID_TYPE_MSISDN)) {

				/**
				 * TODO 기타 파트 API 호출 (방화벽이 뚤리지 않아 Dummy 데이타가 내려온다.)
				 */
				UserRes userRes = this.getMappingInfo(deviceId, "mdn");
				if (userRes.getResultCode() == 0) {
					// LOGGER.debug("## UAPS 연동 : {}", userRes.toString());
					LOGGER.debug("## UAPS 연동 SKT 서비스 관리번호 : {}", userRes.getSvcMngNum());
					majorDeviceInfo.setImMngNum(userRes.getSvcMngNum());
				} else {
					throw new RuntimeException("UAPS 연동 실패~!!!!");
				}

			}

			/**
			 * OMD 단말 setting.
			 */
			if (this.repository.searchOmdCount(model) > 0) {

				LOGGER.info("## OMD 단말 setting.");
				majorDeviceInfo.setOmdUacd(model);

			}

		}

		LOGGER.info("## 단말 주요정보 : {}", majorDeviceInfo.toString());

		return majorDeviceInfo;

	}

	/**
	 * <pre>
	 * 이동통신사 Converting.
	 * </pre>
	 * 
	 * @param deviceTelecom
	 *            이동통신사
	 * @return String
	 * @throws Exception
	 *             Exception
	 */
	public String convertDeviceTelecom(String deviceTelecom) throws Exception {
		if (StringUtils.equals(deviceTelecom, MemberConstants.DEVICE_TELECOM_SKT)) {
			return MemberConstants.NM_DEVICE_TELECOM_SKT;
		} else if (StringUtils.equals(deviceTelecom, MemberConstants.DEVICE_TELECOM_KT)) {
			return MemberConstants.NM_DEVICE_TELECOM_KT;
		} else if (StringUtils.equals(deviceTelecom, MemberConstants.DEVICE_TELECOM_LGT)) {
			return MemberConstants.NM_DEVICE_TELECOM_LGT;
		} else if (StringUtils.equals(deviceTelecom, MemberConstants.DEVICE_TELECOM_OMD)) {
			return MemberConstants.NM_DEVICE_TELECOM_OMD;
		} else if (StringUtils.equals(deviceTelecom, MemberConstants.DEVICE_TELECOM_NSH)) {
			return MemberConstants.NM_DEVICE_TELECOM_NSH;
		} else if (StringUtils.equals(deviceTelecom, MemberConstants.DEVICE_TELECOM_NON)) {
			return MemberConstants.NM_DEVICE_TELECOM_NON;
		} else if (StringUtils.equals(deviceTelecom, MemberConstants.DEVICE_TELECOM_IOS)) {
			return MemberConstants.NM_DEVICE_TELECOM_IOS;
		} else {
			LOGGER.info("## Convert Device Telecom Exception!!!");
			throw new RuntimeException("Convert Device Telecom Exception!!!");
		}
	}
}
