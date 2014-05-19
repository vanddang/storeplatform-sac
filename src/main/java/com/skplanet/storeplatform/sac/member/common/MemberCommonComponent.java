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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.external.client.icas.sci.IcasSCI;
import com.skplanet.storeplatform.external.client.icas.vo.GetCustomerCardEcReq;
import com.skplanet.storeplatform.external.client.icas.vo.GetCustomerCardEcRes;
import com.skplanet.storeplatform.external.client.icas.vo.GetCustomerEcReq;
import com.skplanet.storeplatform.external.client.icas.vo.GetCustomerEcRes;
import com.skplanet.storeplatform.external.client.icas.vo.GetMvnoEcReq;
import com.skplanet.storeplatform.external.client.icas.vo.GetMvnoEcRes;
import com.skplanet.storeplatform.external.client.uaps.sci.UapsSCI;
import com.skplanet.storeplatform.external.client.uaps.vo.OpmdEcRes;
import com.skplanet.storeplatform.external.client.uaps.vo.UapsEcReq;
import com.skplanet.storeplatform.external.client.uaps.vo.UserEcRes;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.common.vo.MbrClauseAgree;
import com.skplanet.storeplatform.member.client.common.vo.MbrMangItemPtcr;
import com.skplanet.storeplatform.member.client.seller.sci.SellerSCI;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchLoginInfoRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchLoginInfoResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSellerResponse;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchManagementListRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchManagementListResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserResponse;
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.member.vo.common.Agreement;
import com.skplanet.storeplatform.sac.client.member.vo.common.AgreementInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.MajorDeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrAuth;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrLglAgent;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserExtraInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserMbrPnsh;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchAgreementRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.UserExtraInfoRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.repository.MemberCommonRepository;
import com.skplanet.storeplatform.sac.member.common.vo.Clause;
import com.skplanet.storeplatform.sac.member.common.vo.Device;

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
	private UapsSCI uapsSCI;

	@Autowired
	private UserSCI userSCI;

	@Autowired
	private IcasSCI icasSCI;

	@Autowired
	private SellerSCI sellerSCI;

	@Value("#{propertiesForSac['idp.mobile.user.auth.key']}")
	public String fixedMobileUserAuthKey;

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
	 */
	public String getOpmdMdnInfo(String msisdn) { // 2014.03.27. 김다슬, 인크로스. 수정

		String resMsisdn = msisdn;
		// 1. OPMD번호(989)여부 검사
		if (StringUtils.substring(msisdn, 0, 3).equals("989")) {
			UapsEcReq uapsReq = new UapsEcReq();
			uapsReq.setDeviceId(msisdn);
			OpmdEcRes opmdRes = this.uapsSCI.getOpmdInfo(uapsReq);
			if (opmdRes != null) {
				resMsisdn = opmdRes.getMobileMdn();
				LOGGER.debug("[MiscellaneousService.getOpmd] SAC<-UPAS Connection Response : {}", opmdRes);
			}
		}
		// 2. OPMD 번호가 아닐경우, Request msisdn을 그대로 반환

		return resMsisdn;
	}

	/**
	 * <pre>
	 * SKT 이용정지회원 여부 조회.
	 * </pre>
	 * 
	 * @param deviceId
	 *            기기 ID
	 * @param deviceIdType
	 *            기기 ID 타입
	 * @return SKT 이용 정지 여부
	 */
	public String getIsSktPause(String deviceId, String deviceIdType) {

		UapsEcReq uapsReq = new UapsEcReq();
		uapsReq.setDeviceId(deviceId);

		try {

			UserEcRes userRes = this.getMappingInfo(deviceId, "mdn");
			LOGGER.debug("## >> UAPS Info : {}", userRes);
			return userRes.getPauseYN();

		} catch (StorePlatformException spe) {

			LOGGER.debug("## >> SKT 이용정지 여부 조회 실패로 이용정지여부 Skip....... ");
			LOGGER.debug("## Error Code : {}", spe.getErrorInfo().getCode());
			LOGGER.debug("## Error Msg  : {}", spe.getErrorInfo().getMessage());
			return "";

		}

	}

	/**
	 * <pre>
	 * 약관 목록 조회.
	 * </pre>
	 * 
	 * @return List<ClauseDTO>
	 */
	public List<Clause> getListClause(String tenantId) {
		return this.repository.searchClauseList(tenantId);
	}

	/**
	 * <pre>
	 * 약관 목록 상세 조회.
	 * </pre>
	 * 
	 * @return List<ClauseDTO>
	 */
	public List<Clause> getDetailClauseList(String clauseItemCd) {
		return this.repository.searchClauseDetail(clauseItemCd);
	}

	/**
	 * <pre>
	 * 테넌트 약관 코드조회.
	 * </pre>
	 * 
	 * @return ClauseDTO
	 */
	public Clause getTenantClauseCode(String clauseItemCd) {
		return this.repository.searchTenantClauseCode(clauseItemCd);
	}

	/**
	 * <pre>
	 * 폰 정보 조회.
	 * </pre>
	 * 
	 * @param deviceModelCd
	 *            deviceModelCd
	 * @return DeviceHeader
	 */
	public Device getPhoneInfo(String deviceModelCd) {
		return this.repository.searchPhoneInfo(deviceModelCd);
	}

	/**
	 * <pre>
	 * 폰 정보 조회.
	 * </pre>
	 * 
	 * @param uacd
	 *            uacd
	 * @return Device
	 */
	public Device getPhoneInfoByUacd(String uacd) {
		return this.repository.searchPhoneInfoByUacd(uacd);
	}

	/**
	 * <pre>
	 * 고객정보조회
	 * 모번호 조회및 단말 정보 조회(USPS 정보와 서비스 관리번호 UA_CD 값이 같이 들어와야함.)
	 * </pre>
	 * 
	 * @param pReqParam
	 *            pReqParam
	 * @param type
	 *            type
	 * @return UserEcRes
	 */
	public UserEcRes getMappingInfo(String pReqParam, String type) {
		UapsEcReq uapsReq = new UapsEcReq();
		uapsReq.setDeviceId(pReqParam);
		uapsReq.setType(type);
		return this.uapsSCI.getMappingInfo(uapsReq);
	}

	/**
	 * <pre>
	 * 회원 기본정보 조회.
	 * </pre>
	 * 
	 * @param keyType
	 *            검색 조건 타입 (userKey, userId, deviceKey, deviceId)
	 * @param keyValue
	 *            검색 조건 값
	 * @param sacHeader
	 *            공통 헤더
	 * @return UserInfo Value Object
	 */
	public UserInfo getUserBaseInfo(String keyType, String keyValue, SacRequestHeader sacHeader) {
		LOGGER.info("회원기본정보조회 Request : {}", keyValue);

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
		 * SC 사용자 회원 정보를 조회
		 */
		SearchUserResponse schUserRes = this.userSCI.searchUser(searchUserRequest);

		// 사용자 정보 세팅
		UserInfo userInfo = this.userInfo(schUserRes);

		LOGGER.info("회원기본정보조회 Response : {}", userInfo.getUserKey());

		return userInfo;
	}

	/**
	 * <pre>
	 * 회원 상세정보 조회.
	 * </pre>
	 * 
	 * @param keyType
	 *            검색 조건 타입 (userKey, userId, deviceKey, deviceId)
	 * @param keyValue
	 *            검색 조건 값
	 * @param sacHeader
	 *            공통 헤더
	 * @return UserInfo Value Object
	 */
	public DetailRes getUserDetailInfo(String keyType, String keyValue, SacRequestHeader sacHeader) {
		LOGGER.info("회원상세정보조회 Request : {}", keyValue);

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
		 * SC 사용자 회원 정보를 조회
		 */
		SearchUserResponse schUserRes = this.userSCI.searchUser(searchUserRequest);

		/* 사용자 정보 세팅 */
		UserInfo userInfo = this.userInfo(schUserRes);

		/* 실명인증 세팅 */
		MbrAuth mbrAuth = this.mbrAuth(schUserRes);

		/* 법정대리인 세팅 */
		MbrLglAgent mbrLglAgent = this.mbrLglAgent(schUserRes);

		/* 징계정보 세팅 */
		UserMbrPnsh mbrPnsh = this.mbrPnsh(schUserRes);

		/* 약관동의 목록 세팅 */
		List<Agreement> listAgreement = this.listAgreement(schUserRes);

		/* 기본정보 세팅 */
		DetailRes detailRes = new DetailRes();
		detailRes.setIsChangeSubject(StringUtil.setTrim(schUserRes.getIsChangeSubject()));
		detailRes.setPwRegDate(StringUtil.setTrim(schUserRes.getPwRegDate()));
		detailRes.setUserKey(StringUtil.setTrim(schUserRes.getUserKey()));

		/* 약관동의 세팅 */
		detailRes.setAgreementList(listAgreement);

		/* 회원정보 세팅 */
		detailRes.setUserInfo(userInfo);

		/* 실명인증 세팅 */
		detailRes.setMbrAuth(mbrAuth);

		/* 법정대리인 세팅 */
		detailRes.setMbrLglAgent(mbrLglAgent);

		/* 징계정보 세팅 */
		detailRes.setUserMbrPnsh(mbrPnsh);

		LOGGER.info("회원상세정보조회 Response : {}", detailRes.getUserKey());

		return detailRes;
	}

	/**
	 * <pre>
	 * 회원정보조회 - 약관정보세팅.
	 * </pre>
	 * 
	 * @param schUserRes
	 * @return
	 */
	private List<Agreement> listAgreement(SearchUserResponse schUserRes) {
		List<Agreement> listAgreement = new ArrayList<Agreement>();
		for (MbrClauseAgree mbrAgree : schUserRes.getMbrClauseAgreeList()) {

			Agreement agree = new Agreement();
			agree.setExtraAgreementId(StringUtil.setTrim(mbrAgree.getExtraAgreementID()));
			agree.setExtraAgreementVersion(StringUtil.setTrim(mbrAgree.getExtraAgreementVersion()));
			agree.setIsExtraAgreement(StringUtil.setTrim(mbrAgree.getIsExtraAgreement()));
			agree.setIsMandatory(StringUtil.setTrim(mbrAgree.getIsMandatory()));

			listAgreement.add(agree);
		}

		return listAgreement;
	}

	/**
	 * <pre>
	 * 회원정보조회 - 징계정보 세팅.
	 * </pre>
	 * 
	 * @param schUserRes
	 * @return
	 */
	private UserMbrPnsh mbrPnsh(SearchUserResponse schUserRes) {
		UserMbrPnsh mbrPnsh = new UserMbrPnsh();
		mbrPnsh.setIsRestricted(StringUtil.setTrim(schUserRes.getUserMbrPnsh().getIsRestricted()));
		mbrPnsh.setRestrictCount(StringUtil.setTrim(schUserRes.getUserMbrPnsh().getRestrictCount()));
		mbrPnsh.setRestrictEndDate(StringUtil.setTrim(schUserRes.getUserMbrPnsh().getRestrictEndDate()));
		mbrPnsh.setRestrictId(StringUtil.setTrim(schUserRes.getUserMbrPnsh().getRestrictID()));
		mbrPnsh.setRestrictOwner(StringUtil.setTrim(schUserRes.getUserMbrPnsh().getRestrictOwner()));
		mbrPnsh.setRestrictRegisterDate(StringUtil.setTrim(schUserRes.getUserMbrPnsh().getRestrictRegisterDate()));
		mbrPnsh.setRestrictStartDate(StringUtil.setTrim(schUserRes.getUserMbrPnsh().getRestrictStartDate()));
		mbrPnsh.setTenantId(StringUtil.setTrim(schUserRes.getUserMbrPnsh().getTenantID()));
		mbrPnsh.setUserKey(StringUtil.setTrim(schUserRes.getUserMbrPnsh().getUserKey()));

		return mbrPnsh;
	}

	/**
	 * <pre>
	 * 회원정보조회 - 법정대리인 세팅.
	 * </pre>
	 * 
	 * @param schUserRes
	 * @return
	 */
	private MbrLglAgent mbrLglAgent(SearchUserResponse schUserRes) {
		MbrLglAgent mbrLglAgent = new MbrLglAgent();
		mbrLglAgent.setIsParent(StringUtil.setTrim(schUserRes.getMbrLglAgent().getIsParent()));
		mbrLglAgent.setMemberKey(StringUtil.setTrim(schUserRes.getMbrLglAgent().getMemberKey()));
		mbrLglAgent.setParentBirthDay(StringUtil.setTrim(schUserRes.getMbrLglAgent().getParentBirthDay()));
		mbrLglAgent.setParentCI(StringUtil.setTrim(schUserRes.getMbrLglAgent().getParentCI()));
		mbrLglAgent.setParentDate(StringUtil.setTrim(schUserRes.getMbrLglAgent().getParentDate()));
		mbrLglAgent.setParentEmail(StringUtil.setTrim(schUserRes.getMbrLglAgent().getParentEmail()));
		mbrLglAgent.setParentMsisdn(StringUtil.setTrim(schUserRes.getMbrLglAgent().getParentMDN()));
		mbrLglAgent.setParentName(StringUtil.setTrim(schUserRes.getMbrLglAgent().getParentName()));
		mbrLglAgent.setParentRealNameDate(StringUtil.setTrim(schUserRes.getMbrLglAgent().getParentRealNameDate()));
		mbrLglAgent.setParentRealNameMethod(StringUtil.setTrim(schUserRes.getMbrLglAgent().getParentRealNameMethod()));
		mbrLglAgent.setParentRealNameSite(StringUtil.setTrim(schUserRes.getMbrLglAgent().getParentRealNameSite()));
		mbrLglAgent.setParentTelecom(StringUtil.setTrim(schUserRes.getMbrLglAgent().getParentTelecom()));
		mbrLglAgent.setParentType(StringUtil.setTrim(schUserRes.getMbrLglAgent().getParentType()));
		mbrLglAgent.setIsDomestic(StringUtil.setTrim(schUserRes.getMbrLglAgent().getIsDomestic()));

		return mbrLglAgent;
	}

	/**
	 * <pre>
	 * 회원정보조회 - 실명인증정보 세팅
	 * </pre>
	 * 
	 * @param schUserRes
	 * @return
	 */
	private MbrAuth mbrAuth(SearchUserResponse schUserRes) {
		MbrAuth mbrAuth = new MbrAuth();
		mbrAuth.setBirthDay(StringUtil.setTrim(schUserRes.getMbrAuth().getBirthDay()));
		mbrAuth.setCi(StringUtil.setTrim(schUserRes.getMbrAuth().getCi()));
		mbrAuth.setDi(StringUtil.setTrim(schUserRes.getMbrAuth().getDi()));
		mbrAuth.setIsDomestic(StringUtil.setTrim(schUserRes.getMbrAuth().getIsDomestic()));
		mbrAuth.setIsRealName(StringUtil.setTrim(schUserRes.getMbrAuth().getIsRealName()));
		mbrAuth.setMemberCategory(StringUtil.setTrim(schUserRes.getMbrAuth().getMemberCategory()));
		mbrAuth.setMemberKey(StringUtil.setTrim(schUserRes.getMbrAuth().getMemberKey()));
		mbrAuth.setName(StringUtil.setTrim(schUserRes.getMbrAuth().getName()));
		mbrAuth.setPhone(StringUtil.setTrim(schUserRes.getMbrAuth().getPhone()));
		mbrAuth.setRealNameDate(StringUtil.setTrim(schUserRes.getMbrAuth().getRealNameDate()));
		mbrAuth.setRealNameMethod(StringUtil.setTrim(schUserRes.getMbrAuth().getRealNameMethod()));
		mbrAuth.setRealNameSite(StringUtil.setTrim(schUserRes.getMbrAuth().getRealNameSite()));
		mbrAuth.setSex(StringUtil.setTrim(schUserRes.getMbrAuth().getSex()));
		mbrAuth.setTelecom(StringUtil.setTrim(schUserRes.getMbrAuth().getTelecom()));

		return mbrAuth;
	}

	/**
	 * <pre>
	 * 회원정보조회 - userInfo 세팅.
	 * </pre>
	 * 
	 * @param schUserRes
	 * @return
	 */
	private UserInfo userInfo(SearchUserResponse schUserRes) {
		UserInfo userInfo = new UserInfo();

		userInfo.setDeviceCount(StringUtil.setTrim(schUserRes.getUserMbr().getDeviceCount()));
		userInfo.setTotalDeviceCount(StringUtil.setTrim(schUserRes.getTotalDeviceCount()));
		userInfo.setImMbrNo(StringUtil.setTrim(schUserRes.getUserMbr().getImMbrNo()));
		userInfo.setImRegDate(StringUtil.setTrim(schUserRes.getUserMbr().getImRegDate()));
		userInfo.setImSiteCode(StringUtil.setTrim(schUserRes.getUserMbr().getImSiteCode()));
		userInfo.setImSvcNo(StringUtil.setTrim(schUserRes.getUserMbr().getImSvcNo()));
		userInfo.setIsMemberPoint(StringUtil.setTrim(schUserRes.getUserMbr().getIsMemberPoint()));
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
		userInfo.setUserCountry(StringUtil.setTrim(schUserRes.getUserMbr().getUserCountry()));
		userInfo.setUserEmail(StringUtil.setTrim(schUserRes.getUserMbr().getUserEmail()));
		userInfo.setUserId(StringUtil.setTrim(schUserRes.getUserMbr().getUserID()));
		userInfo.setUserKey(StringUtil.setTrim(schUserRes.getUserMbr().getUserKey()));
		userInfo.setUserLanguage(StringUtil.setTrim(schUserRes.getUserMbr().getUserLanguage()));
		userInfo.setUserMainStatus(StringUtil.setTrim(schUserRes.getUserMbr().getUserMainStatus()));
		userInfo.setUserPhone(StringUtil.setTrim(schUserRes.getUserMbr().getUserPhone()));
		userInfo.setUserPhoneCountry(StringUtil.setTrim(schUserRes.getUserMbr().getUserPhoneCountry()));
		userInfo.setUserSubStatus(StringUtil.setTrim(schUserRes.getUserMbr().getUserSubStatus()));
		userInfo.setUserTelecom(StringUtil.setTrim(schUserRes.getUserMbr().getUserTelecom()));
		userInfo.setUserType(StringUtil.setTrim(schUserRes.getUserMbr().getUserType()));

		// 실명인증이 되어 있으면 실명인증 데이터가 내려간다.
		// 실명인증이 되어 있지만 이름, 성명, 생년월일 데이터가 없으면 회원정보 데이터가 내려간다.
		if (schUserRes.getMbrAuth().getIsRealName().equals("Y")) {
			// 생년월일
			if (schUserRes.getMbrAuth().getBirthDay() != null) {
				userInfo.setUserBirthDay(StringUtil.setTrim(schUserRes.getMbrAuth().getBirthDay()));
			} else {
				userInfo.setUserBirthDay(StringUtil.setTrim(schUserRes.getUserMbr().getUserBirthDay()));
			}

			// 이름
			if (schUserRes.getMbrAuth().getName() != null) {
				userInfo.setUserName(StringUtil.setTrim(schUserRes.getMbrAuth().getName()));
			} else {
				userInfo.setUserName(StringUtil.setTrim(schUserRes.getUserMbr().getUserName()));
			}

			// 성별
			if (schUserRes.getMbrAuth().getSex() != null) {
				userInfo.setUserSex(StringUtil.setTrim(schUserRes.getMbrAuth().getSex()));
			} else {
				userInfo.setUserName(StringUtil.setTrim(schUserRes.getUserMbr().getUserSex()));
			}

		} else if (schUserRes.getMbrAuth().getIsRealName().equals("N")) {
			userInfo.setUserBirthDay(StringUtil.setTrim(schUserRes.getUserMbr().getUserBirthDay()));
			userInfo.setUserName(StringUtil.setTrim(schUserRes.getUserMbr().getUserName()));
			userInfo.setUserSex(StringUtil.setTrim(schUserRes.getUserMbr().getUserSex()));
		}

		if (schUserRes.getMbrMangItemPtcrList() != null) {
			List<UserExtraInfo> listExtraInfo = new ArrayList<UserExtraInfo>();
			for (MbrMangItemPtcr ptcr : schUserRes.getMbrMangItemPtcrList()) {

				UserExtraInfo extra = new UserExtraInfo();
				extra.setExtraProfile(StringUtil.setTrim(ptcr.getExtraProfile()));
				extra.setExtraProfileValue(StringUtil.setTrim(ptcr.getExtraProfileValue()));

				listExtraInfo.add(extra);
			}

			userInfo.setUserExtraInfoList(listExtraInfo);
		}

		return userInfo;
	}

	/**
	 * <pre>
	 * 회원 부가 정보 조회.
	 * </pre>
	 * 
	 * @return UserExtraInfoRes
	 * @throws Exception
	 */
	public UserExtraInfoRes getUserExtraInfo(String userKey, SacRequestHeader sacHeader) {

		LOGGER.info("회원부가정보조회 Request : {}", userKey);

		DetailReq req = new DetailReq();
		req.setUserKey(userKey);

		/**
		 * SearchManagementListRequest setting
		 */
		SearchManagementListRequest searchUserExtraRequest = new SearchManagementListRequest();
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(sacHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(sacHeader.getTenantHeader().getTenantId());
		searchUserExtraRequest.setCommonRequest(commonRequest);
		searchUserExtraRequest.setUserKey(req.getUserKey());

		/**
		 * SC 사용자 회원 부가정보를 조회
		 */
		UserExtraInfoRes extraRes = new UserExtraInfoRes();
		List<UserExtraInfo> listExtraInfo = new ArrayList<UserExtraInfo>();

		SearchManagementListResponse schUserExtraRes = this.userSCI.searchManagementList(searchUserExtraRequest);

		/* 유저키 세팅 */
		extraRes.setUserKey(schUserExtraRes.getUserKey());
		/* 부가정보 값 세팅 */
		for (MbrMangItemPtcr ptcr : schUserExtraRes.getMbrMangItemPtcrList()) {

			UserExtraInfo extra = new UserExtraInfo();
			extra.setExtraProfile(StringUtil.setTrim(ptcr.getExtraProfile()));
			extra.setExtraProfileValue(StringUtil.setTrim(ptcr.getExtraProfileValue()));

			listExtraInfo.add(extra);

			extraRes.setUserExtraInfoList(listExtraInfo);

		}

		UserExtraInfoRes res = extraRes;

		LOGGER.info("회원부가정보조회 Response : {}", res.getUserKey());

		return res;
	}

	/**
	 * <pre>
	 * 약관동의 목록 조회.
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
	public SearchAgreementRes getSearchAgreement(String userKey, SacRequestHeader sacHeader) {

		DetailReq req = new DetailReq();
		SearchAgreementRes res = new SearchAgreementRes();
		req.setUserKey(userKey);

		DetailRes detailRes = this.getUserDetailInfo("userKey", userKey, sacHeader);
		res.setAgreementList(detailRes.getAgreementList());

		return res;
	}

	/**
	 * <pre>
	 * ICAS 연동 getCustomer.
	 * </pre>
	 * 
	 * @param msisdn
	 *            String
	 * @return GetCustomerEcRes
	 * @throws Exception
	 *             Exception
	 */
	public GetCustomerEcRes getCustomer(String msisdn) {
		GetCustomerEcReq req = new GetCustomerEcReq();
		req.setDeviceId(msisdn);
		return this.icasSCI.getCustomer(req);
	}

	/**
	 * <pre>
	 * ICAS 연동 getCustomerCard.
	 * </pre>
	 * 
	 * @param msisdn
	 *            String
	 * @return GetCustomerCardEcRes
	 * @throws Exception
	 *             Exception
	 */
	public GetCustomerCardEcRes getCustomerCard(String msisdn) {
		GetCustomerCardEcReq req = new GetCustomerCardEcReq();
		req.setDeviceId(msisdn);
		return this.icasSCI.getCustomerCard(req);
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
	public GetMvnoEcRes getMvService(String msisdn) {
		GetMvnoEcReq req = new GetMvnoEcReq();
		return this.icasSCI.getMvService(req);
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
	 * 
	 */
	public MajorDeviceInfo getDeviceBaseInfo(String model, String deviceTelecom, String deviceId, String deviceIdType) {

		MajorDeviceInfo majorDeviceInfo = new MajorDeviceInfo();

		/**
		 * 폰정보 조회후 단말 정보 세팅.
		 */
		Device deviceDTO = this.getPhoneInfo(model);
		if (deviceDTO == null) {

			/**
			 * 미지원 단말 setting
			 */
			LOGGER.debug("## 미지원 단말 세팅.");
			LOGGER.info("<getDeviceBaseInfo> NOT SUPPORT DEVICE. mdn : {}, model : {}", deviceId, model);
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
			 * UUID 일때 이동통신사코드가 IOS가 아니면 로그찍는다. (테넌트에서 잘못 올려준 데이타.) [[ AS-IS 로직은
			 * 하드코딩 했었음... IOS 이북 보관함 지원 uuid ]]
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

				UserEcRes userRes = this.getMappingInfo(deviceId, "mdn");
				LOGGER.debug("## UAPS 연동 결과 toString() : {}", userRes);
				LOGGER.debug("## UAPS 연동 SKT 서비스 관리번호 : {}", userRes.getSvcMngNum());
				majorDeviceInfo.setSvcMangNum(userRes.getSvcMngNum());

			}

			/**
			 * OMD 단말 setting.
			 */
			if (this.repository.searchOmdCount(model) > 0) {

				LOGGER.debug("## OMD 단말 setting.");
				majorDeviceInfo.setOmdUacd(model);

			}

		}

		LOGGER.debug("## 단말 주요정보 : {}", majorDeviceInfo);

		return majorDeviceInfo;

	}

	/**
	 * <pre>
	 * 이동통신사 Converting.
	 * </pre>
	 * 
	 * @param deviceTelecom
	 *            이동통신사
	 * @return 이동통신사 변환
	 */
	public String convertDeviceTelecom(String deviceTelecom) {
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
		} else if (StringUtils.equals(deviceTelecom, MemberConstants.DEVICE_TELECOM_SKM)) {
			return MemberConstants.NM_DEVICE_TELECOM_SKM;
		} else {
			throw new StorePlatformException("SAC_MEM_1103", deviceTelecom);
		}
	}

	/**
	 * <pre>
	 * 이동통신사 코드 Converting.
	 * </pre>
	 * 
	 * @param deviceTelecom
	 *            이동통신사
	 * @return String 이동통신사 변환
	 */
	public String convertDeviceTelecomCode(String deviceTelecom) {
		if (StringUtils.equals(deviceTelecom, MemberConstants.NM_DEVICE_TELECOM_SKT)) {
			return MemberConstants.DEVICE_TELECOM_SKT;
		} else if (StringUtils.equals(deviceTelecom, MemberConstants.NM_DEVICE_TELECOM_KT)) {
			return MemberConstants.DEVICE_TELECOM_KT;
		} else if (StringUtils.equals(deviceTelecom, MemberConstants.NM_DEVICE_TELECOM_LGT)) {
			return MemberConstants.DEVICE_TELECOM_LGT;
		} else if (StringUtils.equals(deviceTelecom, MemberConstants.NM_DEVICE_TELECOM_OMD)) {
			return MemberConstants.DEVICE_TELECOM_OMD;
		} else if (StringUtils.equals(deviceTelecom, MemberConstants.NM_DEVICE_TELECOM_NSH)) {
			return MemberConstants.DEVICE_TELECOM_NSH;
		} else if (StringUtils.equals(deviceTelecom, MemberConstants.NM_DEVICE_TELECOM_NON)) {
			return MemberConstants.DEVICE_TELECOM_NON;
		} else if (StringUtils.equals(deviceTelecom, MemberConstants.NM_DEVICE_TELECOM_IOS)) {
			return MemberConstants.DEVICE_TELECOM_IOS;
		} else if (StringUtils.equals(deviceTelecom, MemberConstants.NM_DEVICE_TELECOM_SKM)) {
			return MemberConstants.DEVICE_TELECOM_SKM;
		} else {
			throw new StorePlatformException("SAC_MEM_1103", deviceTelecom);
		}
	}

	/**
	 * <pre>
	 * SC 공통 헤더 셋팅.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @return CommonRequest
	 */
	public CommonRequest getSCCommonRequest(SacRequestHeader header) {
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(header.getTenantHeader().getSystemId());
		commonRequest.setTenantID(header.getTenantHeader().getTenantId());
		LOGGER.debug("==>>[SC] CommonRequest.toString() : {}", commonRequest.toString());
		return commonRequest;
	}

	/**
	 * <pre>
	 * 판매자 SessionKey 조회.
	 * </pre>
	 * 
	 * @param commonRequest
	 *            CommonRequest
	 * @param sessionKey
	 *            String
	 * @param sellerKey
	 *            String
	 */
	public void checkSessionKey(CommonRequest commonRequest, String sessionKey, String sellerKey) {
		SearchLoginInfoRequest req = new SearchLoginInfoRequest();
		req.setCommonRequest(commonRequest);
		req.setSessionKey(sessionKey);
		SearchLoginInfoResponse res = this.sellerSCI.searchLoginInfo(req);

		if (!StringUtils.equals(sellerKey, res.getLoginInfo().getSellerKey())) {
			// 인증키가 유효하지 않습니다.
			throw new StorePlatformException("SAC_MEM_2002");
		}
		// TODO 만료로직 주석
		// Double expireDate = Double.parseDouble(res.getLoginInfo().getExpireDate());
		// Double sysdate = Double.parseDouble(this.getExpirationTime(0));
		// if (expireDate < sysdate) {
		// // 인증 만료시간 오버
		// throw new StorePlatformException("SAC_MEM_2003");
		// }
	}

	/**
	 * <pre>
	 * 판매자 회원 정보 조회.
	 * </pre>
	 * 
	 * @param commonRequest
	 *            CommonRequest
	 * @param keyType
	 *            : 검색조건
	 * @param key
	 *            : 검색값
	 * @return SearchSellerResponse
	 */
	public SearchSellerResponse getSearchSeller(CommonRequest commonRequest, String keyType, String key) {
		SearchSellerRequest searchSellerRequest = new SearchSellerRequest();
		searchSellerRequest.setCommonRequest(commonRequest);

		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySearch = new KeySearch();
		keySearch.setKeyType(keyType);
		keySearch.setKeyString(key);
		keySearchList.add(keySearch);
		searchSellerRequest.setKeySearchList(keySearchList);

		return this.sellerSCI.searchSeller(searchSellerRequest);
	}

	/**
	 * <pre>
	 * 인증 만료 시간 연장 .
	 * </pre>
	 * 
	 * @param hour
	 *            : 연장할 시간
	 * @return String : 현재시간 + 연장시간
	 */
	public String getExpirationTime(int hour) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, hour);
		return sdf.format(cal.getTime());
	}

	/**
	 * <pre>
	 * IDP 연동 여부 판단.
	 * 
	 * MDN 로그인시에만 fixedMobileUserAuthKey 내려감.
	 * 
	 * </pre>
	 * 
	 * @param userAuthkey
	 *            요청 userAuthKey
	 * @return boolean (true 면 연동한다.)
	 */
	public boolean isIdpConnect(String userAuthkey) {

		if (StringUtils.equals(userAuthkey, this.fixedMobileUserAuthKey)) {
			LOGGER.debug("## >> userAuthKey 가 Fixed 값과 같으면 IDP 연동 하지 않는다.");
			return false;
		} else {
			return true;
		}

	}

	/**
	 * <pre>
	 * 공통 약관정보의 필수약관여부, 약관버전를 맵핑해줌. [버전정보는 값이 들어올 경우만 맵핑해줌.]
	 * 
	 * 맵핑 정보 없을시 SAC_MEM_1105 에러 발생함.
	 * </pre>
	 */
	public List<AgreementInfo> getClauseMappingInfo(String tenantId, List<AgreementInfo> agreementList) {

		LOGGER.debug("## >> Before : {}", agreementList);

		if (agreementList.size() > 0) {

			for (AgreementInfo info : agreementList) {

				/**
				 * 맵핑할 약관정보 조회.
				 */
				Clause clauseInfo = this.repository.getClauseItemInfo(tenantId, info.getExtraAgreementId());

				/**
				 * 유효한 약관정보가 존재 하지 않을경우 에러.
				 */
				if (clauseInfo == null || StringUtils.isBlank(clauseInfo.getClauseId())) {
					throw new StorePlatformException("SAC_MEM_1105", info.getExtraAgreementId());
				}

				/**
				 * 필수 약관 여부 세팅.
				 */
				info.setMandAgreeYn(clauseInfo.getMandAgreeYn());

				/**
				 * 약관 버전 세팅.
				 */
				if (StringUtils.isBlank(info.getExtraAgreementVersion())) {
					info.setExtraAgreementVersion(clauseInfo.getClauseVer());
				}

			}

		}

		LOGGER.info("DB와 맵핑후 약관정보 : {}", agreementList);

		return agreementList;

	}

	/**
	 * <pre>
	 * 법정대리인 유효성 체크.
	 * </pre>
	 * 
	 * @param ownBirth
	 *            본인의 생년월일 (yyyymmdd)
	 * @param parentBirth
	 *            법정대리인 생년월일 (yyyymmdd)
	 */
	public void checkParentBirth(String ownBirth, String parentBirth) {

		LOGGER.info("법정대리인 유효성체크. [ownBirth={},parentBirth={}]", ownBirth, parentBirth);

		/**
		 * 날짜 형식 체크(yyyyMMdd).
		 */
		if (!DateUtil.isDate(ownBirth) || !DateUtil.isDate(parentBirth)) {
			throw new StorePlatformException("[dateFormat error (yyyyMMdd)]");
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		/**
		 * 법정대리인 만19세 이상인지 체크.
		 */
		Calendar todayCal = DateUtil.getCalendar(DateUtil.getToday("yyyyMMdd"));
		todayCal.add(Calendar.YEAR, -19); // 19년 감소
		String year_19 = sdf.format(todayCal.getTime());

		if (Integer.parseInt(parentBirth) > Integer.parseInt(year_19)) {
			throw new StorePlatformException("SAC_MEM_0004", parentBirth);
		}

		/**
		 * 법정대리인과 회원의 나이차이가 20살 이상 인지 체크.
		 */
		Calendar ownCal = DateUtil.getCalendar(ownBirth);
		ownCal.add(Calendar.YEAR, -20); // 20년 감소
		String year_20 = sdf.format(ownCal.getTime());

		if (Integer.parseInt(parentBirth) > Integer.parseInt(year_20)) {
			throw new StorePlatformException("SAC_MEM_0005", ownBirth, parentBirth);
		}

	}

}
