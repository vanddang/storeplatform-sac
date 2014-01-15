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
import com.skplanet.storeplatform.sac.client.member.vo.common.UserInfo;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetOpmdReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetUaCodeReq;
import com.skplanet.storeplatform.sac.common.vo.Device;
import com.skplanet.storeplatform.sac.member.common.repository.MemberCommonRepository;
import com.skplanet.storeplatform.sac.member.common.vo.ClauseDTO;
import com.skplanet.storeplatform.sac.member.miscellaneous.service.MiscellaneousService;
import com.skplanet.storeplatform.sac.member.user.service.DeviceService;
import com.skplanet.storeplatform.sac.member.user.service.UserSelectServiceImpl;

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
	private UserSelectServiceImpl userSelectService;

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
	 * @param String
	 *            deviceModelNo
	 * @return String uaCode
	 * @throws Exception
	 */
	public String getUaCode(String deviceModelNo) throws Exception { // 2014. 01. 14. 김다슬, 인크로스. 추가
		GetUaCodeReq request = new GetUaCodeReq();
		request.setDeviceModelNo(deviceModelNo);
		return this.miscellaneousService.getUaCode(request).getUaCd();
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
	public List<ClauseDTO> getMandAgreeList(String tenantId) throws Exception {
		return this.repository.getMandAgreeList(tenantId);
	}

	/**
	 * <pre>
	 * 폰 정보 조회.
	 * </pre>
	 * 
	 * @param deviceModelCd
	 *            deviceModelCd
	 * @return Device
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
	 * @param userKey
	 *            String
	 * @param deviceInfo
	 *            DeviceInfo
	 * @throws Exception
	 *             Exception
	 */
	public void insertDeviceInfo(String userKey, DeviceInfo deviceInfo) throws Exception {
		this.deviceService.insertDeviceInfo(userKey, deviceInfo);
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
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getCustomer(String msisdn) throws Exception {
		return this.icasSCI.getCustomer(msisdn );
	}
	
	/**
	 * <pre>
	 * ICAS 연동 getCustomerCard.
	 * </pre>
	 * 
	 * @param msisdn
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getCustomerCard(String msisdn) throws Exception {
		return this.icasSCI.getCustomerCard(msisdn );
	}
	
	/**
	 * <pre>
	 * ICAS 연동 getMvService.
	 * </pre>
	 * 
	 * @param msisdn
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getMvService(String msisdn) throws Exception {
		return this.icasSCI.getMvService(msisdn );
	}

}
