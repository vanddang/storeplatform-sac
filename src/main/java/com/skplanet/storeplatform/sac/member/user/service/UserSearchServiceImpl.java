/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.user.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.idp.vo.IDPReceiverM;
import com.skplanet.storeplatform.external.client.idp.vo.ImIDPReceiverM;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.common.vo.MbrClauseAgree;
import com.skplanet.storeplatform.member.client.common.vo.MbrMangItemPtcr;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSCI;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchAgreementListRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchAgreementListResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceListRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchManagementListRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchManagementListResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserResponse;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.member.vo.common.Agreement;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrAuth;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrLglAgent;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserExtraInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ExistReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ExistRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceReq;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.idp.service.IDPService;
import com.skplanet.storeplatform.sac.member.common.idp.service.ImIDPService;

/**
 * 회원 조회 서비스 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 1. 7. Updated by : 강신완, 부르칸.
 */
@Service
public class UserSearchServiceImpl implements UserSearchService {

	private static final Logger logger = LoggerFactory.getLogger(UserSearchServiceImpl.class);

	private static CommonRequest commonRequest;

	static {
		commonRequest = new CommonRequest();
	}

	@Autowired
	private UserSCI userSCI;

	@Autowired
	private DeviceSCI deviceSCI;

	@Autowired
	private IDPService idpService;

	@Autowired
	private MemberCommonComponent memberCommonComponent;

	private IDPReceiverM idpReceiverM;

	@Autowired
	private ImIDPService imIdpService;

	private ImIDPReceiverM imIdpReceiverM;

	/**
	 * 회원 가입 조회
	 */
	@Override
	public ExistRes exist(SacRequestHeader sacHeader, ExistReq req) throws Exception {
		ExistRes result = new ExistRes();
		SearchUserRequest schUserReq = new SearchUserRequest();

		/* 헤더 정보 셋팅 */
		commonRequest.setSystemID(sacHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(sacHeader.getTenantHeader().getTenantId());

		String userKey = StringUtil.setTrim(req.getUserKey());
		String userId = StringUtil.setTrim(req.getUserId());
		String deviceKey = StringUtil.setTrim(req.getDeviceKey());
		String deviceId = StringUtil.setTrim(req.getDeviceId());

		List<KeySearch> keySchList = new ArrayList<KeySearch>();

		if (userKey != null && !"".equals(userKey)) {
			KeySearch keySchUserKey = new KeySearch();
			keySchUserKey.setKeyType("INSD_USERMBR_NO");
			keySchUserKey.setKeyString(userKey);
			keySchList.add(keySchUserKey);
		} else if (userId != null && !"".equals(userId)) {
			KeySearch keySchUserId = new KeySearch();
			keySchUserId.setKeyType("MBR_ID");
			keySchUserId.setKeyString(userId);
			keySchList.add(keySchUserId);
		} else if (deviceId != null && !"".equals(deviceId)) {
			KeySearch keySchDeviceId = new KeySearch();
			keySchDeviceId.setKeyType("INSD_DEVICE_ID");
			keySchDeviceId.setKeyString(deviceId);
			keySchList.add(keySchDeviceId);
		} else if (deviceKey != null && !"".equals(deviceKey)) {
			KeySearch keySchDeviceKey = new KeySearch();
			keySchDeviceKey.setKeyType("DEVICE_ID");
			keySchDeviceKey.setKeyString(deviceKey);
			keySchList.add(keySchDeviceKey);
		}

		// 회원정보 세팅
		List<UserInfo> userInfoList = new ArrayList<UserInfo>();
		schUserReq.setKeySearchList(keySchList);
		schUserReq.setCommonRequest(commonRequest);
		UserInfo userInfo = this.userInfo(schUserReq);
		userInfoList.add(userInfo);

		if ("".equals(userInfo) || userInfo == null) {
			throw new RuntimeException("SC 회원조회 실패 : 해당 회원이 없음");
		} else {
			for (UserInfo info : userInfoList) {
				userInfo.setImSvcNo(info.getImSvcNo());
				userInfo.setUserKey(info.getUserKey());
				userInfo.setUserType(info.getUserType());
				userInfo.setUserId(info.getUserId());
				userInfo.setIsRealName(info.getIsRealName());
				userInfo.setIsParent(info.getIsParent());
				userInfo.setUserEmail(info.getUserEmail());
				userInfo.setUserMainStatus(info.getUserMainStatus());
				userInfo.setUserSubStatus(info.getUserSubStatus());
			}

			result.setUserKey(userInfo.getUserKey());
			result.setUserType(userInfo.getUserType());
			result.setUserId(userInfo.getUserId());
			result.setIsRealName(userInfo.getIsRealName());
			result.setAgencyYn(userInfo.getIsParent());
			result.setUserEmail(userInfo.getUserEmail());
			result.setUserMainStatus(userInfo.getUserMainStatus());
			result.setUserSubStatus(userInfo.getUserSubStatus());
		}

		logger.info("###### userInfo.getImSvcNo : " + userInfo.getImSvcNo());

		// // IDP 사용자
		// if ("".equals(userInfo.getImSvcNo()) && userInfo.getImSvcNo() == null) {
		//
		// // IDP 연동 회원정보 조회
		// this.idpReceiverM = this.idpService.alredyJoinCheckByEmail(userInfo.getUserEmail());
		// logger.info("## alredyJoinCheckByEmail - Result Code : {}", this.idpReceiverM.getResponseHeader()
		// .getResult());
		// logger.info("## alredyJoinCheckByEmail - Result Text : {}", this.idpReceiverM.getResponseHeader()
		// .getResult_text());
		//
		// if (StringUtils.equals(this.idpReceiverM.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_OK)) {
		// result.setUserKey(userInfo.getUserKey());
		// result.setUserType(userInfo.getUserType());
		// result.setUserId(userInfo.getUserId());
		// result.setIsRealName(userInfo.getIsRealName());
		// result.setAgencyYn(userInfo.getIsParent());
		// result.setUserEmail(userInfo.getUserEmail());
		// result.setUserMainStatus(userInfo.getUserMainStatus());
		// result.setUserSubStatus(userInfo.getUserSubStatus());
		// } else {
		// throw new RuntimeException("IDP 회원조회 실패 : 해당 회원이 없음");
		// }
		//
		// }
		// // OneID 사용자
		// else if (!"".equals(userInfo.getImSvcNo()) && userInfo.getImSvcNo() != null) {
		// // OneID 연동 회원정보 조회 : UserId
		// this.imIdpReceiverM = this.imIdpService.checkIdStatusIdpIm(userInfo.getUserId());
		// // this.imIdpReceiverM = this.imIdpManager.checkDupId(userInfo.getUserId());
		// logger.info("## checkDupId - Result Code : {}", this.imIdpReceiverM.getResponseHeader().getResult());
		// logger.info("## checkDupId - Result Text : {}", this.imIdpReceiverM.getResponseHeader().getResult_text());
		//
		// if (StringUtils.equals(this.imIdpReceiverM.getResponseHeader().getResult(), ImIDPConstants.IDP_RES_CODE_OK))
		// {
		// result.setUserKey(userInfo.getUserKey());
		// result.setUserType(userInfo.getUserType());
		// result.setUserId(userInfo.getUserId());
		// result.setIsRealName(userInfo.getIsRealName());
		// result.setAgencyYn(userInfo.getIsParent());
		// result.setUserEmail(userInfo.getUserEmail());
		// result.setUserMainStatus(userInfo.getUserMainStatus());
		// result.setUserSubStatus(userInfo.getUserSubStatus());
		// } else {
		// throw new RuntimeException("OneID 회원조회 실패 : 해당 회원이 없음");
		// }
		// } else {
		// throw new RuntimeException("SC 데이터에서 오류발생 : imSvcNo 호출 에러");
		// }

		return result;
	}

	/**
	 * 회원 정보 조회
	 */
	@Override
	public DetailRes detail(SacRequestHeader sacHeader, DetailReq req) throws Exception {
		DetailRes result = new DetailRes();

		/* 헤더 정보 셋팅 */
		commonRequest.setSystemID(sacHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(sacHeader.getTenantHeader().getTenantId());

		List<KeySearch> keySchList = new ArrayList<KeySearch>();

		if (req.getUserKey() != null && !"".equals(req.getUserKey())) {
			KeySearch keySchUserKey = new KeySearch();
			keySchUserKey.setKeyType("INSD_USERMBR_NO");
			keySchUserKey.setKeyString(req.getUserKey());
			keySchList.add(keySchUserKey);
		} else if (req.getUserId() != null && !"".equals(req.getUserId())) {
			KeySearch keySchUserId = new KeySearch();
			keySchUserId.setKeyType("MBR_ID");
			keySchUserId.setKeyString(req.getUserId());
			keySchList.add(keySchUserId);
		} else if (req.getDeviceId() != null && !"".equals(req.getDeviceId())) {
			KeySearch keySchDeviceId = new KeySearch();
			keySchDeviceId.setKeyType("INSD_DEVICE_ID");
			keySchDeviceId.setKeyString(req.getDeviceId());
			keySchList.add(keySchDeviceId);
		} else if (req.getDeviceKey() != null && !"".equals(req.getUserKey())) {
			KeySearch keySchDeviceKey = new KeySearch();
			keySchDeviceKey.setKeyType("DEVICE_ID");
			keySchDeviceKey.setKeyString(req.getDeviceKey());
			keySchList.add(keySchDeviceKey);
		}

		logger.info("###### : Request : " + req.toString());
		logger.info("###### : keySchList : " + keySchList.toString());

		SearchUserRequest schUserReq = new SearchUserRequest(); // 사용자 Request
		SearchManagementListRequest schUserExtraInfoReq = new SearchManagementListRequest(); // 사용자 부가정보 Request
		SearchDeviceRequest schDeviceReq = new SearchDeviceRequest(); // 디바이스 Request
		SearchDeviceListRequest schDeviceListReq = new SearchDeviceListRequest(); // 디바이스 리스트 Request
		SearchAgreementListRequest schAgreementListReq = new SearchAgreementListRequest(); // 약관동의 리스트 Request

		schUserReq.setKeySearchList(keySchList);
		schDeviceListReq.setKeySearchList(keySchList);

		// 공통헤더 세팅
		schUserReq.setCommonRequest(commonRequest);
		schUserExtraInfoReq.setCommonRequest(commonRequest);
		schDeviceListReq.setCommonRequest(commonRequest);
		schDeviceReq.setCommonRequest(commonRequest);
		schAgreementListReq.setCommonRequest(commonRequest);

		// 사용자 기본정보 세팅
		List<UserInfo> userInfoList = new ArrayList<UserInfo>();
		UserInfo userInfo = this.userInfo(schUserReq);
		userInfoList.add(userInfo);

		// 사용자 부가정보 리스트 세팅
		schUserExtraInfoReq.setUserKey(userInfo.getUserKey());
		List<UserExtraInfo> userExtraInfoList = new ArrayList<UserExtraInfo>();
		UserExtraInfo userExtraInfo = this.userExtraInfo(schUserExtraInfoReq);
		userExtraInfoList.add(userExtraInfo);
		userInfo.setUserExtraInfo(userExtraInfoList);

		// 사용자 인증정보 세팅
		List<MbrAuth> mbrAuthList = new ArrayList<MbrAuth>();
		MbrAuth mbrAuth = this.mbrAuth(schUserReq);
		mbrAuthList.add(mbrAuth);

		// 법정 대리인정보 세팅
		List<MbrLglAgent> mbrLglAgentList = new ArrayList<MbrLglAgent>();
		MbrLglAgent mbrLglAgent = this.mbrLglAgent(schUserReq);
		mbrLglAgentList.add(mbrLglAgent);

		// 디바이스목록정보 세팅
		ListDeviceReq listDeviceReq = new ListDeviceReq();
		// ListDeviceRes listDeviceRes = this.memberCommonComponent.listDevice(requestHeader, listDeviceReq);

		// 약관동의목록정보 세팅
		schAgreementListReq.setUserKey(userInfo.getUserKey());
		List<Agreement> agreementList = new ArrayList<Agreement>();
		Agreement agreement = this.agreementList(schAgreementListReq);
		agreementList.add(agreement);

		result.setUserInfoList(userInfoList);
		result.setMbrAuthList(mbrAuthList);
		result.setMbrLglAgentList(mbrLglAgentList);
		// result.setDeviceInfoList(listDeviceRes);
		result.setAgreementList(agreementList);

		logger.info("###### getUserInfoList : " + result.getUserInfoList());
		logger.info("###### getMbrAuthList : " + result.getMbrAuthList());
		logger.info("###### getMbrLglAgentList : " + result.getMbrLglAgentList());
		logger.info("###### getDeviceInfoList : " + result.getDeviceInfoList());
		logger.info("###### getAgreementList : " + result.getAgreementList());

		return result;
	}

	// 사용자 정보
	public UserInfo userInfo(SearchUserRequest schUserReq) throws Exception {

		// 사용자 회원 기본정보 SC
		SearchUserResponse schUserRes = this.userSCI.searchUser(schUserReq);
		UserInfo userInfo = new UserInfo();

		logger.info("###### schUserRes : " + schUserRes.getCommonResponse().getResultCode());
		logger.info("###### schUserRes : " + schUserRes.getCommonResponse().getResultMessage());

		if ("0000".equals(schUserRes.getCommonResponse().getResultCode())) {

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

			/**
			 * 사용자 징계 정보
			 */
			if (schUserRes.getUserMbrPnsh() != null) {
				userInfo.setIsRestricted(StringUtil.setTrim(schUserRes.getUserMbrPnsh().getIsRestricted()));
				userInfo.setIsRestricted(StringUtil.setTrim(schUserRes.getUserMbrPnsh().getRestrictStartDate()));
				userInfo.setRestrictStartDate(StringUtil.setTrim(schUserRes.getUserMbrPnsh().getRestrictStartDate()));
				userInfo.setRestrictEndDate(StringUtil.setTrim(schUserRes.getUserMbrPnsh().getRestrictEndDate()));
				userInfo.setRestrictId(StringUtil.setTrim(schUserRes.getUserMbrPnsh().getRestrictID()));
				userInfo.setRestrictCount(StringUtil.setTrim(schUserRes.getUserMbrPnsh().getRestrictCount()));
				userInfo.setRestrictRegisterDate(StringUtil.setTrim(schUserRes.getUserMbrPnsh()
						.getRestrictRegisterDate()));
				userInfo.setRestrictOwner(StringUtil.setTrim(schUserRes.getUserMbrPnsh().getRestrictOwner()));
			} else {
				throw new RuntimeException("SearchUser.UserMbrPnsh (사용자 징계 정보) 데이터가 없습니다.");
			}

		}

		return userInfo;
	}

	// 사용자 부가정보
	public UserExtraInfo userExtraInfo(SearchManagementListRequest schUserExtraInfo) throws Exception {

		// 사용자 회원 기본정보 SC
		SearchManagementListResponse schUserExtraInfoRes = this.userSCI.searchManagementList(schUserExtraInfo);
		UserExtraInfo userExtraInfo = new UserExtraInfo();

		logger.info("###### schUserExtraInfoRes : " + schUserExtraInfoRes.getCommonResponse().getResultCode());
		logger.info("###### schUserExtraInfoRes : " + schUserExtraInfoRes.getCommonResponse().getResultMessage());

		if ("0000".equals(schUserExtraInfoRes.getCommonResponse().getResultCode())) {

			/**
			 * 사용자 부가정보
			 */
			if (schUserExtraInfoRes.getMbrMangItemPtcrList() != null) {
				for (MbrMangItemPtcr info : schUserExtraInfoRes.getMbrMangItemPtcrList()) {
					userExtraInfo.setExtraProfile(StringUtil.setTrim(info.getExtraProfile()));
					userExtraInfo.setExtraProfileValue(StringUtil.setTrim(info.getExtraProfileValue()));
				}
			} else {
				throw new RuntimeException("searchManagementList (사용자 부가정보) 데이터가 없습니다.");
			}

		} else {
			throw new RuntimeException("SC 호출에러 Code : " + schUserExtraInfoRes.getCommonResponse().getResultCode());
		}

		return userExtraInfo;
	}

	// 사용자 인증정보 세팅
	public MbrAuth mbrAuth(SearchUserRequest schUserReq) throws Exception {

		// SC
		SearchUserResponse schUserRes = this.userSCI.searchUser(schUserReq);
		MbrAuth mbrAuth = new MbrAuth();

		logger.info("###### schUserRes.getMbrAuth : " + schUserRes.getCommonResponse().getResultCode());
		logger.info("###### schUserRes.getMbrAuth : " + schUserRes.getCommonResponse().getResultMessage());

		if ("0000".equals(schUserRes.getCommonResponse().getResultCode())) {

			if (schUserRes.getMbrAuth() != null) {
				mbrAuth.setBirthDay(StringUtil.setTrim(schUserRes.getMbrAuth().getBirthDay()));
				mbrAuth.setCi(StringUtil.setTrim(schUserRes.getMbrAuth().getCi()));
				mbrAuth.setDi(StringUtil.setTrim(schUserRes.getMbrAuth().getDi()));
				mbrAuth.setIsRealName(StringUtil.setTrim(schUserRes.getMbrAuth().getIsRealName()));
				mbrAuth.setMemberCategory(StringUtil.setTrim(schUserRes.getMbrAuth().getMemberCategory()));
				mbrAuth.setMemberKey(StringUtil.setTrim(schUserRes.getMbrAuth().getMemberKey()));
				mbrAuth.setName(StringUtil.setTrim(schUserRes.getMbrAuth().getName()));
				mbrAuth.setPhone(StringUtil.setTrim(schUserRes.getMbrAuth().getPhone()));
				mbrAuth.setRealNameDate(StringUtil.setTrim(schUserRes.getMbrAuth().getRealNameDate()));
				mbrAuth.setRealNameMethod(StringUtil.setTrim(schUserRes.getMbrAuth().getRealNameMethod()));
				mbrAuth.setRealNameSite(StringUtil.setTrim(schUserRes.getMbrAuth().getRealNameSite()));
				mbrAuth.setSequence(StringUtil.setTrim(schUserRes.getMbrAuth().getSequence()));
				mbrAuth.setSex(StringUtil.setTrim(schUserRes.getMbrAuth().getSex()));
				mbrAuth.setTelecom(StringUtil.setTrim(schUserRes.getMbrAuth().getTelecom()));
				mbrAuth.setTenantID(StringUtil.setTrim(schUserRes.getMbrAuth().getTenantID()));
				mbrAuth.setUpdateDate(StringUtil.setTrim(schUserRes.getMbrAuth().getUpdateDate()));
			} else {
				throw new RuntimeException("SearchUser.MbrAuth (사용자 인증정보)가 없습니다.");
			}

		} else {
			throw new RuntimeException("SC 호출에러 Code : " + schUserRes.getCommonResponse().getResultCode());
		}

		return mbrAuth;
	}

	// 사용자 약관동의 세팅
	public Agreement agreementList(SearchAgreementListRequest schAgreementListReq) throws Exception {

		// SC
		SearchAgreementListResponse schAgreementListRes = this.userSCI.searchAgreementList(schAgreementListReq);
		Agreement agreement = new Agreement();

		logger.info("###### schAgreementListRes : " + schAgreementListRes.getCommonResponse().getResultCode());
		logger.info("###### schAgreementListRes : " + schAgreementListRes.getCommonResponse().getResultMessage());

		if ("0000".equals(schAgreementListRes.getCommonResponse().getResultCode())) {

			if (schAgreementListRes.getMbrClauseAgreeList() != null) {
				for (MbrClauseAgree info : schAgreementListRes.getMbrClauseAgreeList()) {
					agreement.setExtraAgreementId(StringUtil.setTrim(info.getExtraAgreementID()));
					agreement.setExtraAgreementVersion(StringUtil.setTrim(info.getExtraAgreementVersion()));
					agreement.setIsExtraAgreement(StringUtil.setTrim(info.getIsExtraAgreement()));
				}
			} else {
				throw new RuntimeException("SearchAgreementList (약관동의 정보)가 없습니다.");
			}

		} else {
			throw new RuntimeException("SC 호출에러 Code : " + schAgreementListRes.getCommonResponse().getResultCode());
		}

		return agreement;
	}

	// 법정 대리인정보 세팅
	public MbrLglAgent mbrLglAgent(SearchUserRequest schUserReq) throws Exception {

		// SC
		SearchUserResponse schUserRes = this.userSCI.searchUser(schUserReq);
		MbrLglAgent mbrLglAgent = new MbrLglAgent();

		logger.info("###### schUserRes.getMbrLglAgent : " + schUserRes.getCommonResponse().getResultCode());
		logger.info("###### schUserRes.getMbrLglAgent : " + schUserRes.getCommonResponse().getResultMessage());

		if ("0000".equals(schUserRes.getCommonResponse().getResultCode())) {

			if (schUserRes.getMbrLglAgent() != null) {
				mbrLglAgent.setIsParent(StringUtil.setTrim(schUserRes.getMbrLglAgent().getIsParent()));
				mbrLglAgent.setMemberKey(StringUtil.setTrim(schUserRes.getMbrLglAgent().getMemberKey()));
				mbrLglAgent.setParentBirthDay(StringUtil.setTrim(schUserRes.getMbrLglAgent().getParentBirthDay()));
				mbrLglAgent.setParentCI(StringUtil.setTrim(schUserRes.getMbrLglAgent().getParentCI()));
				mbrLglAgent.setParentDate(StringUtil.setTrim(schUserRes.getMbrLglAgent().getParentDate()));
				mbrLglAgent.setParentEmail(StringUtil.setTrim(schUserRes.getMbrLglAgent().getParentEmail()));
				mbrLglAgent.setParentMDN(StringUtil.setTrim(schUserRes.getMbrLglAgent().getParentMDN()));
				mbrLglAgent.setParentName(StringUtil.setTrim(schUserRes.getMbrLglAgent().getParentName()));
				mbrLglAgent.setParentRealNameDate(StringUtil.setTrim(schUserRes.getMbrLglAgent()
						.getParentRealNameDate()));
				mbrLglAgent.setParentRealNameMethod(StringUtil.setTrim(schUserRes.getMbrLglAgent()
						.getParentRealNameMethod()));
				mbrLglAgent.setParentRealNameSite(StringUtil.setTrim(schUserRes.getMbrLglAgent()
						.getParentRealNameSite()));
				mbrLglAgent.setParentTelecom(StringUtil.setTrim(schUserRes.getMbrLglAgent().getParentTelecom()));
				mbrLglAgent.setParentType(StringUtil.setTrim(schUserRes.getMbrLglAgent().getParentType()));
				mbrLglAgent.setSequence(StringUtil.setTrim(schUserRes.getMbrLglAgent().getSequence()));
			} else {
				throw new RuntimeException("SearchUser.MbrLglAgent (법정 대리인정보)가 업습니다.");
			}

		} else {
			throw new RuntimeException("SC 호출에러 Code : " + schUserRes.getCommonResponse().getResultCode());
		}

		return mbrLglAgent;
	}

}
