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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.external.client.idp.vo.IDPReceiverM;
import com.skplanet.storeplatform.external.client.idp.vo.ImIDPReceiverM;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.MbrClauseAgree;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSCI;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchAgreementListRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchAgreementListResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserResponse;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.member.vo.common.Agreement;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrAuth;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrLglAgent;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ExistReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ExistRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchAgreementReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchAgreementRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.UserExtraInfoRes;
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
@Transactional
public class UserSearchServiceImpl implements UserSearchService {

	private static final Logger logger = LoggerFactory.getLogger(UserSearchServiceImpl.class);

	@Autowired
	private MemberCommonComponent mcc;

	@Autowired
	private DeviceService deviceService;

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
		DetailReq detailReq = new DetailReq();

		/* 헤더 정보 셋팅 */
		commonRequest.setSystemID(sacHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(sacHeader.getTenantHeader().getTenantId());

		/**
		 * 모번호 조회 (989 일 경우만)
		 */
		if (req.getDeviceId() != null) {
			req.setDeviceId(this.mcc.getOpmdMdnInfo(req.getDeviceId()));
			logger.info("모번호 조회 getOpmdMdnInfo: {}", this.mcc.getOpmdMdnInfo(req.getDeviceId()));
		}

		String userKey = StringUtil.setTrim(req.getUserKey());
		String userId = StringUtil.setTrim(req.getUserId());
		String deviceKey = StringUtil.setTrim(req.getDeviceKey());
		String deviceId = StringUtil.setTrim(req.getDeviceId());

		if (userKey != null && !"".equals(userKey)) {
			detailReq.setUserKey(userKey);
		} else if (userId != null && !"".equals(userId)) {
			detailReq.setUserId(userId);
		} else if (deviceId != null && !"".equals(deviceId)) {
			detailReq.setDeviceId(deviceId);
		} else if (deviceKey != null && !"".equals(deviceKey)) {
			detailReq.setDeviceKey(deviceKey);
		}

		// 회원정보 세팅
		UserInfo userInfo = this.searchUser(detailReq, sacHeader);

		logger.info("###### userInfo Request : {}", detailReq.toString());
		logger.info("###### userInfo Response : {}", userInfo.toString());

		if ("".equals(userInfo) || userInfo == null) {
			throw new RuntimeException("SC 회원조회 실패 : 해당 회원이 없음");
		} else {
			result.setUserKey(userInfo.getUserKey());
			result.setUserType(userInfo.getUserType());
			result.setUserId(userInfo.getUserId());
			result.setIsRealName(userInfo.getIsRealName());
			result.setAgencyYn(userInfo.getIsParent());
			result.setUserEmail(userInfo.getUserEmail());
			result.setUserMainStatus(userInfo.getUserMainStatus());
			result.setUserSubStatus(userInfo.getUserSubStatus());
		}

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
		DetailRes res = new DetailRes();

		/* 헤더 정보 셋팅 */
		commonRequest.setSystemID(sacHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(sacHeader.getTenantHeader().getTenantId());

		/**
		 * 모번호 조회 (989 일 경우만)
		 */
		req.setDeviceId(this.mcc.getOpmdMdnInfo(req.getDeviceId()));
		logger.info("모번호 조회 getOpmdMdnInfo: {}", this.mcc.getOpmdMdnInfo(req.getDeviceId()));

		/* 회원 기본 정보 */
		UserInfo searchUser = this.searchUser(req, sacHeader);

		/* 회원 부가 정보 */
		UserExtraInfoRes userExtraInfoRes = this.listUserExtra(req, sacHeader);
		searchUser.setUserExtraInfo(userExtraInfoRes.getAddInfoList());

		/* 정보조회범위 */
		if (req.getSearchExtent() != null) {
			/* 단말 + 부가정보 */
			if ("Y".equals(req.getSearchExtent().getDeviceInfoYn())) {
				ListDeviceRes listDeviceRes = this.listDevice(req, sacHeader);
				res.setDeviceInfoList(listDeviceRes.getDeviceInfoList());
			}
			/* 약관동의정보 */
			if ("Y".equals(req.getSearchExtent().getAgreementInfoYn())) {
				SearchAgreementReq schAgreeReq = new SearchAgreementReq();
				schAgreeReq.setUserKey(req.getUserKey());
				SearchAgreementRes schAgreeRes = this.searchAgreement(schAgreeReq, sacHeader);
				res.setAgreementList(schAgreeRes.getAgreementList());
			}
			/* 실명인증정보 */
			if ("Y".equals(req.getSearchExtent().getMbrAuthInfoYn())) {

			}
			/* 법정대리인정보 */
			if ("Y".equals(req.getSearchExtent().getMbrLglAgentInfoYn())) {

			}

		}

		// /* 회원기본정보 + 부가정보 */
		// res.setUserInfo(searchUser);
		//
		// SearchUserRequest schUserReq = new SearchUserRequest(); // 사용자 Request
		// SearchManagementListRequest schUserExtraInfoReq = new SearchManagementListRequest(); // 사용자 부가정보 Request
		// SearchDeviceRequest schDeviceReq = new SearchDeviceRequest(); // 디바이스 Request
		// SearchDeviceListRequest schDeviceListReq = new SearchDeviceListRequest(); // 디바이스 리스트 Request
		// SearchAgreementListRequest schAgreementListReq = new SearchAgreementListRequest(); // 약관동의 리스트 Request
		//
		// // 공통헤더 세팅
		// schUserReq.setCommonRequest(commonRequest);
		// schUserExtraInfoReq.setCommonRequest(commonRequest);
		// schDeviceListReq.setCommonRequest(commonRequest);
		// schDeviceReq.setCommonRequest(commonRequest);
		// schAgreementListReq.setCommonRequest(commonRequest);
		//
		// // 사용자 기본정보 세팅
		// UserInfo userInfo = this.userInfo(schUserReq);
		//
		// // 사용자 부가정보 리스트 세팅
		// schUserExtraInfoReq.setUserKey(userInfo.getUserKey());
		// List<UserExtraInfo> userExtraInfoList = new ArrayList<UserExtraInfo>();
		// UserExtraInfo userExtraInfo = this.userExtraInfo(schUserExtraInfoReq);
		// userExtraInfoList.add(userExtraInfo);
		// userInfo.setUserExtraInfo(userExtraInfoList);
		//
		// // 사용자 인증정보 세팅
		// List<MbrAuth> mbrAuthList = new ArrayList<MbrAuth>();
		// MbrAuth mbrAuth = this.mbrAuth(schUserReq);
		// mbrAuthList.add(mbrAuth);
		//
		// // 법정 대리인정보 세팅
		// List<MbrLglAgent> mbrLglAgentList = new ArrayList<MbrLglAgent>();
		// MbrLglAgent mbrLglAgent = this.mbrLglAgent(schUserReq);
		// mbrLglAgentList.add(mbrLglAgent);
		//
		// // 디바이스목록정보 세팅
		// ListDeviceReq listDeviceReq = new ListDeviceReq();
		// // ListDeviceRes listDeviceRes = this.memberCommonComponent.listDevice(requestHeader, listDeviceReq);
		//
		// // 약관동의목록정보 세팅
		// schAgreementListReq.setUserKey(userInfo.getUserKey());
		// List<Agreement> agreementList = new ArrayList<Agreement>();
		// Agreement agreement = this.agreementList(schAgreementListReq);
		// agreementList.add(agreement);
		//
		// result.setUserInfo(userInfo);
		// result.setMbrAuthList(mbrAuthList);
		// result.setMbrLglAgentList(mbrLglAgentList);
		// // result.setDeviceInfoList(listDeviceRes);
		// result.setAgreementList(agreementList);
		//
		// logger.info("###### getUserInfoList : " + result.getUserInfo());
		// logger.info("###### getMbrAuthList : " + result.getMbrAuthList());
		// logger.info("###### getMbrLglAgentList : " + result.getMbrLglAgentList());
		// logger.info("###### getDeviceInfoList : " + result.getDeviceInfoList());
		// logger.info("###### getAgreementList : " + result.getAgreementList());

		return res;
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

	/* SC API 회원정보 조회 */
	@Override
	public UserInfo searchUser(DetailReq req, SacRequestHeader sacHeader) throws Exception {

		String keyType = "";
		String keyValue = "";
		if (req.getUserKey() != null) {
			keyType = "userKey";
			keyValue = req.getUserKey();
		} else if (req.getUserId() != null) {
			keyType = "userId";
			keyValue = req.getUserId();
		} else if (req.getDeviceKey() != null) {
			keyType = "deviceKey";
			keyValue = req.getDeviceKey();
		} else if (req.getDeviceId() != null) {
			keyType = "deviceId";
			keyValue = req.getDeviceId();
		}

		UserInfo userInfo = this.mcc.getUserBaseInfo(keyType, keyValue, sacHeader);

		logger.debug("###### 회원기본정보조회 Req - keyType : {}, keyValue : {}", keyType, keyValue);
		logger.debug("###### 회원기본정보조회 Res : {}", userInfo.toString());

		return userInfo;
	}

	/* SC API 회원부가정보 조회 */
	@Override
	public UserExtraInfoRes listUserExtra(DetailReq req, SacRequestHeader sacHeader) throws Exception {
		UserExtraInfoRes extraRes = this.mcc.getUserExtraInfo(req.getUserKey(), sacHeader);

		return extraRes;
	}

	/* SC API 디바이스 리스트 조회 */
	@Override
	public ListDeviceRes listDevice(DetailReq req, SacRequestHeader sacHeader) throws Exception {
		ListDeviceReq listDeviceReq = new ListDeviceReq();
		listDeviceReq.setUserKey(req.getUserKey());
		listDeviceReq.setIsMainDevice("N");

		if (req.getUserId() != null) {
			listDeviceReq.setUserId(req.getUserId());
		} else if (req.getDeviceKey() != null) {
			listDeviceReq.setDeviceKey(req.getDeviceKey());
		} else if (req.getDeviceId() != null) {
			listDeviceReq.setDeviceId(req.getDeviceId());
		}

		ListDeviceRes listDeviceRes = this.deviceService.listDevice(sacHeader, listDeviceReq);

		return listDeviceRes;
	}

	/* SC API 약관동의 목록 조회 */
	@Override
	public SearchAgreementRes searchAgreement(SearchAgreementReq req, SacRequestHeader sacHeader) throws Exception {
		SearchAgreementRes schAgreeRes = this.mcc.getSearchAgreement(req.getUserKey(), sacHeader);

		return schAgreeRes;
	}
}
