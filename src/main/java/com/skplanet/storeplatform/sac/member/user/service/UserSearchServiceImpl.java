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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.idp.sci.IdpSCI;
import com.skplanet.storeplatform.external.client.idp.sci.ImIdpSCI;
import com.skplanet.storeplatform.external.client.idp.vo.FindPasswdEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.FindPasswdEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.ResetUserPwdIdpEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.ResetUserPwdIdpEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.UserInfoIdpSearchServerEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.UserInfoIdpSearchServerEcRes;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.common.vo.MbrClauseAgree;
import com.skplanet.storeplatform.member.client.common.vo.MbrMangItemPtcr;
import com.skplanet.storeplatform.member.client.common.vo.SearchPolicyRequest;
import com.skplanet.storeplatform.member.client.common.vo.SearchPolicyResponse;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSCI;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.CheckDuplicationRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CheckDuplicationResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.DeviceSystemStats;
import com.skplanet.storeplatform.member.client.user.sci.vo.GameCenter;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchAgreeSiteRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchAgreeSiteResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchAgreementListRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchAgreementListResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceOSNumberRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceOSNumberResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchGameCenterRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchGameCenterResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchManagementListRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchManagementListResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchMbrUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchMbrUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserEmailRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserEmailResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbr;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrStatus;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.member.vo.common.Agreement;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrAuth;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrLglAgent;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserExtraInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserMbrPnsh;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.IndividualPolicyInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.DailyPhone;
import com.skplanet.storeplatform.sac.client.member.vo.user.DailyPhoneOs;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailByDeviceIdSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailByDeviceIdSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ExistReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ExistRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.GetProvisioningHistoryReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.GetProvisioningHistoryRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDailyPhoneOsSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListTermsAgreementSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListTermsAgreementSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.MbrOneidSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.MbrOneidSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchAgreementRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchIdSac;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchIdSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchIdSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchPasswordSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchPasswordSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchUserReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.UserExtraInfoRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.UserInfoByUserKey;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;

/**
 * 회원 조회 서비스 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 1. 7. Updated by : 강신완, 부르칸.
 */
@Service
public class UserSearchServiceImpl implements UserSearchService {

	private static final Logger logger = LoggerFactory.getLogger(UserSearchServiceImpl.class);

	@Autowired
	private MemberCommonComponent mcc;

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private DeviceSCI deviceSCI;

	private static CommonRequest commonRequest;

	static {
		commonRequest = new CommonRequest();
	}

	@Autowired
	private UserSCI userSCI;

	@Autowired
	private IdpSCI idpSCI;

	@Autowired
	private ImIdpSCI imIdpSCI;

	@Autowired
	private MemberCommonComponent memberCommonComponent;

	@Autowired
	private MemberCommonComponent commService; // 회원 공통 서비스

	/**
	 * 회원 가입 조회
	 */
	@Override
	public ExistRes exist(SacRequestHeader sacHeader, ExistReq req) {
		ExistRes result = new ExistRes();
		DetailReq detailReq = new DetailReq();

		/* 헤더 정보 셋팅 */
		commonRequest.setSystemID(sacHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(sacHeader.getTenantHeader().getTenantId());

		/**
		 * 모번호 조회 (989 일 경우만)
		 */
		if (req.getDeviceId() != null) {
			String opmdMdn = this.mcc.getOpmdMdnInfo(req.getDeviceId());
			req.setDeviceId(opmdMdn);
			logger.info("============================================ getOpmdMdnInfo: {}", opmdMdn);
		}

		String userKey = StringUtil.setTrim(req.getUserKey());
		String userId = StringUtil.setTrim(req.getUserId());
		String deviceKey = StringUtil.setTrim(req.getDeviceKey());
		String deviceId = StringUtil.setTrim(req.getDeviceId());

		if (!"".equals(userKey)) {
			detailReq.setUserKey(userKey);
		} else if (!"".equals(userId)) {
			detailReq.setUserId(userId);
		} else if (!"".equals(deviceId)) {
			detailReq.setDeviceId(deviceId);
		} else if (!"".equals(deviceKey)) {
			detailReq.setDeviceKey(deviceKey);
		}

		// 회원정보 세팅
		UserInfo userInfo = this.searchUser(detailReq, sacHeader);

		logger.info("============================================ userInfo Request : {}", detailReq.toString());
		logger.info("============================================ userInfo Response : {}", userInfo.toString());

		result.setUserKey(StringUtil.setTrim(userInfo.getUserKey()));
		result.setUserType(StringUtil.setTrim(userInfo.getUserType()));
		result.setUserId(StringUtil.setTrim(userInfo.getUserId()));
		result.setIsRealName(StringUtil.setTrim(userInfo.getIsRealName()));
		result.setAgencyYn(StringUtil.setTrim(userInfo.getIsParent()));
		result.setUserEmail(StringUtil.setTrim(userInfo.getUserEmail()));
		result.setUserMainStatus(StringUtil.setTrim(userInfo.getUserMainStatus()));
		result.setUserSubStatus(StringUtil.setTrim(userInfo.getUserSubStatus()));

		return result;
	}

	/**
	 * 회원 정보 조회
	 */
	@Override
	public DetailRes detail(SacRequestHeader sacHeader, DetailReq req) {

		/* 헤더 정보 셋팅 */
		commonRequest.setSystemID(sacHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(sacHeader.getTenantHeader().getTenantId());

		/**
		 * 모번호 조회 (989 일 경우만)
		 */
		if (req.getDeviceId() != null) {
			String opmdMdnInfo = this.mcc.getOpmdMdnInfo(req.getDeviceId());
			req.setDeviceId(opmdMdnInfo);
			logger.info("모번호 조회 getOpmdMdnInfo: {}", opmdMdnInfo);
		}

		/* 회원 기본 정보 */
		DetailRes res = this.searchUserBase(req, sacHeader);

		/* 정보조회범위 */
		if (req.getSearchExtent() != null) {
			/* 회원 정보 + 부가정보 */
			if ("Y".equals(req.getSearchExtent().getUserInfoYn())) {
				UserInfo searchUser = this.searchUser(req, sacHeader);
				res.setUserInfo(searchUser);
			}
			/* 단말 + 부가정보 */
			if ("Y".equals(req.getSearchExtent().getDeviceInfoYn())) {
				try {
					ListDeviceRes listDeviceRes = this.listDevice(req, sacHeader);
					res.setDeviceInfoList(listDeviceRes.getDeviceInfoList());
				} catch (StorePlatformException ex) {
					/* 결과가 없는 경우만 제외하고 throw */
					if (!ex.getErrorInfo().getCode().equals(MemberConstants.SC_ERROR_NO_DATA)) {
						throw ex;
					}
				}
			}
			/* 약관동의정보 */
			if ("Y".equals(req.getSearchExtent().getAgreementInfoYn())) {
				SearchAgreementRes schAgreeRes = this.searchUserAgreement(req, sacHeader);
				res.setAgreementList(schAgreeRes.getAgreementList());
			}
			/* 실명인증정보 */
			if ("Y".equals(req.getSearchExtent().getMbrAuthInfoYn())) {
				MbrAuth mbrAuth = new MbrAuth();
				mbrAuth = this.mbrAuth(req, sacHeader);
				res.setMbrAuth(mbrAuth);
			}
			/* 법정대리인정보 */
			if ("Y".equals(req.getSearchExtent().getMbrLglAgentInfoYn())) {
				MbrLglAgent mbrLglAgent = new MbrLglAgent();
				mbrLglAgent = this.searchUsermbrLglAgent(req, sacHeader);
				res.setMbrLglAgent(mbrLglAgent);
			}
			/* 사용자징계정보 */
			if ("Y".equals(req.getSearchExtent().getMbrPnshInfoYn())) {
				UserMbrPnsh mbrPnsh = new UserMbrPnsh();
				mbrPnsh = this.searchUserMbrPnsh(req, sacHeader);
				res.setUserMbrPnsh(mbrPnsh);
			}

		}

		return res;
	}

	/**
	 * 회원 프로비저닝 이력 조회
	 */
	@Override
	public GetProvisioningHistoryRes getProvisioningHistory(SacRequestHeader sacHeader, GetProvisioningHistoryReq req) {

		/* 헤더 정보 셋팅 */
		commonRequest.setSystemID(sacHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(sacHeader.getTenantHeader().getTenantId());

		String deviceId = StringUtil.nvl(req.getDeviceId(), "");

		/**
		 * 모번호 조회 (989 일 경우만)
		 */
		if (req.getDeviceId() != null) {
			String opmdMdnInfo = this.mcc.getOpmdMdnInfo(req.getDeviceId());
			req.setDeviceId(opmdMdnInfo);
			logger.info("모번호 조회 getOpmdMdnInfo: {}", opmdMdnInfo);
		}

		/* 회원 기본 정보 */

		SearchGameCenterRequest scReq = new SearchGameCenterRequest();
		scReq.setCommonRequest(commonRequest);
		if (!deviceId.equals("")) {
			scReq.setWorkCode(MemberConstants.GAMECENTER_WORK_CD_USER_CHANGE);
			scReq.setDeviceID(req.getDeviceId());
			this.mcc.getUserBaseInfo("deviceId", req.getDeviceId(), sacHeader);
		}

		logger.info("SearchGameCenterRequest : {}", scReq.toString());

		SearchGameCenterResponse scRes = this.userSCI.searchGameCenter(scReq);
		GetProvisioningHistoryRes res = new GetProvisioningHistoryRes();

		for (GameCenter gameCenter : scRes.getGameCenterList()) {
			res.setOldUserKey(StringUtil.setTrim(gameCenter.getPreUserKey()));
			res.setUserKey(StringUtil.setTrim(gameCenter.getUserKey()));
			res.setWorkCd(StringUtil.setTrim(gameCenter.getWorkCode()));
			res.setRegDate(StringUtil.setTrim(gameCenter.getRegDate()));
		}

		return res;
	}

	/**
	 * OneId 정보조회
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param userKey
	 * @return
	 */
	@Override
	public MbrOneidSacRes searchUserOneId(SacRequestHeader sacHeader, MbrOneidSacReq req) {
		/* 헤더 정보 셋팅 */
		commonRequest.setSystemID(sacHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(sacHeader.getTenantHeader().getTenantId());

		/* 회원 기본 정보 */
		UserInfo info = this.mcc.getUserBaseInfo("userKey", req.getUserKey(), sacHeader);

		if (info.getImSvcNo() == null || info.getImSvcNo().equals("")) {
			throw new StorePlatformException("SAC_MEM_1302", req.getUserKey());
		}

		/* OneId 정보조회 */
		SearchAgreeSiteRequest scReq = new SearchAgreeSiteRequest();
		scReq.setCommonRequest(commonRequest);
		scReq.setImSvcNo(info.getImSvcNo());
		SearchAgreeSiteResponse scRes = this.userSCI.searchAgreeSite(scReq);

		/* OneId 데이터 세팅 */
		MbrOneidSacRes res = new MbrOneidSacRes();
		res.setIsCi(StringUtil.setTrimYn(scRes.getMbrOneID().getIsCi()));
		res.setIsRealName(StringUtil.setTrimYn(scRes.getMbrOneID().getIsRealName()));
		res.setIsMemberPoint(StringUtil.setTrimYn(scRes.getMbrOneID().getIsMemberPoint()));

		logger.info("MbrOneidSacRes : ", res.toString());

		return res;
	}

	/**
	 * 약관동의 목록 조회
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param deviceId
	 * @return
	 */
	@Override
	public ListTermsAgreementSacRes listTermsAgreement(SacRequestHeader sacHeader, ListTermsAgreementSacReq req)

	{

		/* 헤더 정보 셋팅 */
		commonRequest.setSystemID(sacHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(sacHeader.getTenantHeader().getTenantId());

		/* 회원 정보 조회 */
		this.mcc.getUserBaseInfo("userKey", req.getUserKey(), sacHeader);

		/* 약관동의 목록 조회 */
		List<Agreement> agreementList = new ArrayList<Agreement>();
		SearchAgreementListRequest schAgreementListReq = new SearchAgreementListRequest();
		schAgreementListReq.setUserKey(req.getUserKey());
		schAgreementListReq.setCommonRequest(commonRequest);
		SearchAgreementListResponse scRes = this.userSCI.searchAgreementList(schAgreementListReq);

		ListTermsAgreementSacRes res = new ListTermsAgreementSacRes();
		res.setUserKey(scRes.getUserKey());

		for (MbrClauseAgree scAgree : scRes.getMbrClauseAgreeList()) {
			Agreement agree = new Agreement();
			agree.setExtraAgreementId(StringUtil.setTrim(scAgree.getExtraAgreementID()));
			agree.setExtraAgreementVersion(StringUtil.setTrim(scAgree.getExtraAgreementVersion()));
			agree.setIsExtraAgreement(StringUtil.setTrim(scAgree.getIsExtraAgreement()));
			agree.setIsMandatory(StringUtil.setTrim(scAgree.getIsMandatory()));
			agree.setRegDate(StringUtil.setTrim(scAgree.getRegDate()));
			agree.setUpdateDate(StringUtil.setTrim(scAgree.getUpdateDate()));

			agreementList.add(agree);
		}

		res.setAgreementList(agreementList);

		logger.info("ListTermsAgreementSacReq : ", res.toString());

		return res;
	}

	/**
	 * ID 찾기
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param deviceId
	 * @return
	 */
	@Override
	public SearchIdSacRes searchId(SacRequestHeader sacHeader, SearchIdSacReq req) {

		/* 헤더 정보 셋팅 */
		commonRequest.setSystemID(sacHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(sacHeader.getTenantHeader().getTenantId());

		/* 회원 정보 조회 */
		List<SearchIdSac> sacList = new ArrayList<SearchIdSac>();
		if (!req.getDeviceId().equals("")) {

			String opmdMdn = this.commService.getOpmdMdnInfo(req.getDeviceId());
			req.setDeviceId(opmdMdn);
			logger.info("모번호 조회 getOpmdMdnInfo: {}", opmdMdn);

			UserInfo info = this.mcc.getUserBaseInfo("deviceId", req.getDeviceId(), sacHeader);
			SearchIdSac sac = new SearchIdSac();
			sac.setImSvcNo(StringUtil.setTrim(info.getImSvcNo()));
			sac.setRegDate(StringUtil.setTrim(info.getRegDate()));
			sac.setUserId(StringUtil.setTrim(info.getUserId()));
			sac.setUserType(StringUtil.setTrim(info.getUserType()));
			sac.setUserEmail(StringUtil.setTrim(info.getUserEmail()));

			if (info.getUserType().equals(MemberConstants.USER_TYPE_MOBILE)) {
				throw new StorePlatformException("SAC_MEM_1300", info.getUserType());
			}

			sacList.add(sac);
		} else if (!req.getUserEmail().equals("")) {
			sacList = this.searchUserEmail(req, sacHeader);
		}

		SearchIdSacRes res = new SearchIdSacRes();
		res.setSearchIdList(sacList);

		logger.info("SearchIdSacRes : ", res.toString());

		return res;
	}

	/**
	 * PASSWORD 찾기
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param deviceId
	 * @return
	 */
	@Override
	public SearchPasswordSacRes searchPassword(SacRequestHeader sacHeader, SearchPasswordSacReq req) {

		/* 헤더 정보 셋팅 */
		commonRequest.setSystemID(sacHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(sacHeader.getTenantHeader().getTenantId());

		/* 회원 정보 조회 */
		UserInfo info = this.mcc.getUserBaseInfo("userId", req.getUserId(), sacHeader);

		String checkId = "";
		if (!req.getUserEmail().equals("")) {
			/* UserId와 Email이 일치하는지 체크 */
			if (info.getUserEmail().equals(req.getUserEmail())) {
				checkId = "Y";
			} else {
				checkId = "N";
			}
		} else if (!req.getUserPhone().equals("")) {
			/* UserId와 Phone이 일치하는지 체크 */
			ListDeviceReq scReq = new ListDeviceReq();
			scReq.setUserKey(info.getUserKey());
			scReq.setDeviceId(req.getUserPhone());
			scReq.setIsMainDevice("N");

			ListDeviceRes listDeviceRes = this.deviceService.listDevice(sacHeader, scReq);

			if (listDeviceRes.getUserKey() != null) {
				checkId = "Y";
			} else {
				checkId = "N";
			}
		}

		if (checkId.equals("N")) {
			throw new StorePlatformException("SAC_MEM_0002", "Email or Phone");
		}

		SearchPasswordSacRes res = new SearchPasswordSacRes();
		if (info.getImSvcNo() == null || info.getImSvcNo().equals("")) {
			if (info.getUserType().equals(MemberConstants.USER_TYPE_MOBILE)) {
				// 무선회원
				throw new StorePlatformException("SAC_MEM_1300", info.getUserType());
			} else if (info.getUserType().equals(MemberConstants.USER_TYPE_IDPID)) {
				// IDP 회원
				FindPasswdEcReq ecReqFindpass = new FindPasswdEcReq();
				ecReqFindpass.setKeyType("3");
				ecReqFindpass.setKey(info.getUserId());
				ecReqFindpass.setWatermarkAuth("2");

				FindPasswdEcRes ecResFindpass = this.idpSCI.findPasswd(ecReqFindpass);

				logger.info("## IDP Request FindPasswd : {}", ecReqFindpass.toString());
				logger.info("## IDP Response FindPasswd : {}", ecResFindpass.getCommonRes().getResultText());

				res.setUserPw(ecResFindpass.getTempPasswd());

				if (!req.getUserEmail().equals("")) {
					res.setSendInfo(StringUtil.setTrim(req.getUserEmail()));
					res.setSendMean("01");
				} else if (!req.getUserPhone().equals("")) {
					res.setSendInfo(StringUtil.setTrim(req.getUserPhone()));
					res.setSendMean("02");
				} else if (req.getUserEmail().equals("") && req.getUserPhone().equals("")) {
					throw new StorePlatformException("SAC_MEM_0001", "userEmail or userPhone");
				}
			}

		} else {
			// 통합ID회원
			UserInfoIdpSearchServerEcReq ecReqUserInfo = new UserInfoIdpSearchServerEcReq();
			ecReqUserInfo.setKey(info.getImSvcNo()); // 통합 서비스 관리번호
			ecReqUserInfo.setKeyType("1");
			UserInfoIdpSearchServerEcRes ecResUserInfo = this.imIdpSCI.userInfoIdpSearchServer(ecReqUserInfo);

			logger.info("## ImIDP Request UserSearch : {}", ecReqUserInfo.toString());
			logger.info("## ImIDP Response UserSearch{}", ecResUserInfo.toString());

			// 통합ID회원 패스워드 리셋
			ResetUserPwdIdpEcReq ecReqResetUserPwd = new ResetUserPwdIdpEcReq();
			Date dtCur = new Date();
			ecReqResetUserPwd.setKeyType("1");
			ecReqResetUserPwd.setKey(info.getImSvcNo());
			ecReqResetUserPwd.setLangCode("KOR");
			ecReqResetUserPwd.setModifyReqDate(new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(dtCur));
			ecReqResetUserPwd.setModifyReqTime(new SimpleDateFormat("HHmmss", Locale.KOREA).format(dtCur));

			if (ecResUserInfo.getIsUserTnAuth().equals("Y")) {
				// 휴대폰 인증
				ecReqResetUserPwd.setUserTn(ecResUserInfo.getUserTn());
				ecReqResetUserPwd.setUserTnNationCd("82");
				ecReqResetUserPwd.setUserTnType("M");
				ecReqResetUserPwd.setIsUserTnAuth("Y");
				ecReqResetUserPwd.setIsEmailAuth("N");
			} else if (ecResUserInfo.getIsEmailAuth().equals("Y")) {
				// 이메일 인증
				ecReqResetUserPwd.setIsEmailAuth("Y");
				ecReqResetUserPwd.setUserEmail(ecResUserInfo.getUserEmail());
				ecReqResetUserPwd.setIsUserTnAuth("N");
			}

			logger.info("## ImIDP Request ResetUserPWD : {}", ecReqResetUserPwd.toString());

			ResetUserPwdIdpEcRes ecResResetUserPwd = this.imIdpSCI.resetUserPwdIdp(ecReqResetUserPwd);

			logger.info("## ImIDP Response ResetUserPWD : {}", ecResResetUserPwd.getCommonRes().getResultText());

			res.setSendInfo("");
			res.setUserPw("");
			res.setSendMean("03");
		}

		logger.info("SearchIdSacRes : ", res.toString());

		return res;
	}

	// userEmail 기반 사용자 조회
	@Override
	public List<SearchIdSac> searchUserEmail(SearchIdSacReq req, SacRequestHeader sacHeader) {

		SearchUserEmailRequest scReq = new SearchUserEmailRequest();
		scReq.setCommonRequest(commonRequest);
		scReq.setUserEmail(req.getUserEmail());

		SearchUserEmailResponse scRes = this.userSCI.searchUserEmail(scReq);

		List<SearchIdSac> searchIdList = new ArrayList<SearchIdSac>();
		for (UserMbr userMbr : scRes.getUserMbrList()) {

			if (!userMbr.getUserType().equals(MemberConstants.USER_TYPE_MOBILE)) {
				SearchIdSac sac = new SearchIdSac();
				sac.setUserId(StringUtil.setTrim(userMbr.getUserID()));
				sac.setUserType(StringUtil.setTrim(userMbr.getUserType()));
				sac.setRegDate(StringUtil.setTrim(userMbr.getRegDate()));
				sac.setImSvcNo(StringUtil.setTrim(userMbr.getImSvcNo()));
				sac.setUserEmail(StringUtil.setTrim(userMbr.getUserEmail()));

				searchIdList.add(sac);
			} else if (userMbr.getUserType().equals(MemberConstants.USER_TYPE_MOBILE)) {
				throw new StorePlatformException("SAC_MEM_1300", userMbr.getUserType());
			}

		}

		List<SearchIdSac> sacList = searchIdList;

		return sacList;
	}

	// 사용자 인증정보 세팅
	public MbrAuth mbrAuth(DetailReq req, SacRequestHeader sacHeader) {

		SearchUserRequest searchUserRequest = new SearchUserRequest();
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(sacHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(sacHeader.getTenantHeader().getTenantId());
		searchUserRequest.setCommonRequest(commonRequest);

		String userId = StringUtil.nvl(req.getUserId(), "");
		String userKey = StringUtil.nvl(req.getUserKey(), "");
		String deviceId = StringUtil.nvl(req.getDeviceId(), "");
		String deviceKey = StringUtil.nvl(req.getDeviceKey(), "");

		String keyType = "";
		String keyValue = "";
		if (!userKey.equals("")) {
			keyType = "userKey";
			keyValue = userKey;
		} else if (!userId.equals("")) {
			keyType = "userId";
			keyValue = userId;
		} else if (!deviceKey.equals("")) {
			keyType = "deviceKey";
			keyValue = deviceKey;
		} else if (!deviceId.equals("")) {
			keyType = "deviceId";
			keyValue = deviceId;
		}

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
		searchUserRequest.setKeySearchList(keySearchList);

		// SC
		SearchUserResponse schUserRes = this.userSCI.searchUser(searchUserRequest);
		MbrAuth mbrAuth = new MbrAuth();

		logger.info("###### schUserRes.getMbrAuth : " + schUserRes.getCommonResponse().getResultCode());
		logger.info("###### schUserRes.getMbrAuth : " + schUserRes.getCommonResponse().getResultMessage());

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
		mbrAuth.setSequence(StringUtil.setTrim(schUserRes.getMbrAuth().getSequence()));
		mbrAuth.setSex(StringUtil.setTrim(schUserRes.getMbrAuth().getSex()));
		mbrAuth.setTelecom(StringUtil.setTrim(schUserRes.getMbrAuth().getTelecom()));
		mbrAuth.setTenantID(StringUtil.setTrim(schUserRes.getMbrAuth().getTenantID()));
		mbrAuth.setUpdateDate(StringUtil.setTrim(schUserRes.getMbrAuth().getUpdateDate()));

		return mbrAuth;
	}

	// 법정 대리인정보 세팅
	public MbrLglAgent searchUsermbrLglAgent(DetailReq req, SacRequestHeader sacHeader) {

		SearchUserRequest searchUserRequest = new SearchUserRequest();
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(sacHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(sacHeader.getTenantHeader().getTenantId());
		searchUserRequest.setCommonRequest(commonRequest);

		String userId = StringUtil.nvl(req.getUserId(), "");
		String userKey = StringUtil.nvl(req.getUserKey(), "");
		String deviceId = StringUtil.nvl(req.getDeviceId(), "");
		String deviceKey = StringUtil.nvl(req.getDeviceKey(), "");

		String keyType = "";
		String keyValue = "";
		if (!userKey.equals("")) {
			keyType = "userKey";
			keyValue = userKey;
		} else if (!userId.equals("")) {
			keyType = "userId";
			keyValue = userId;
		} else if (!deviceKey.equals("")) {
			keyType = "deviceKey";
			keyValue = deviceKey;
		} else if (!deviceId.equals("")) {
			keyType = "deviceId";
			keyValue = deviceId;
		}

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
		searchUserRequest.setKeySearchList(keySearchList);

		/**
		 * SC 사용자 회원 법정대리인정보 조회
		 */
		SearchUserResponse schUserRes = this.userSCI.searchUser(searchUserRequest);
		MbrLglAgent mbrLglAgent = new MbrLglAgent();

		mbrLglAgent.setIsParent(StringUtil.setTrim(schUserRes.getMbrLglAgent().getIsParent()));
		mbrLglAgent.setMemberKey(StringUtil.setTrim(schUserRes.getMbrLglAgent().getMemberKey()));
		mbrLglAgent.setParentBirthDay(StringUtil.setTrim(schUserRes.getMbrLglAgent().getParentBirthDay()));
		mbrLglAgent.setParentCI(StringUtil.setTrim(schUserRes.getMbrLglAgent().getParentCI()));
		mbrLglAgent.setParentDate(StringUtil.setTrim(schUserRes.getMbrLglAgent().getParentDate()));
		mbrLglAgent.setParentEmail(StringUtil.setTrim(schUserRes.getMbrLglAgent().getParentEmail()));
		mbrLglAgent.setParentMDN(StringUtil.setTrim(schUserRes.getMbrLglAgent().getParentMDN()));
		mbrLglAgent.setParentName(StringUtil.setTrim(schUserRes.getMbrLglAgent().getParentName()));
		mbrLglAgent.setParentRealNameDate(StringUtil.setTrim(schUserRes.getMbrLglAgent().getParentRealNameDate()));
		mbrLglAgent.setParentRealNameMethod(StringUtil.setTrim(schUserRes.getMbrLglAgent().getParentRealNameMethod()));
		mbrLglAgent.setParentRealNameSite(StringUtil.setTrim(schUserRes.getMbrLglAgent().getParentRealNameSite()));
		mbrLglAgent.setParentTelecom(StringUtil.setTrim(schUserRes.getMbrLglAgent().getParentTelecom()));
		mbrLglAgent.setParentType(StringUtil.setTrim(schUserRes.getMbrLglAgent().getParentType()));
		mbrLglAgent.setSequence(StringUtil.setTrim(schUserRes.getMbrLglAgent().getSequence()));
		mbrLglAgent.setIsDomestic(StringUtil.setTrim(schUserRes.getMbrLglAgent().getIsDomestic()));
		mbrLglAgent.setTenantId(StringUtil.setTrim(schUserRes.getMbrLglAgent().getTenantID()));

		logger.debug("###### schUserRes.getMbrLglAgent Code : " + schUserRes.getCommonResponse().getResultCode());
		logger.debug("###### schUserRes.getMbrLglAgent Message : " + schUserRes.getCommonResponse().getResultMessage());
		logger.debug("###### schUserRes.getMbrLglAgent Req : " + searchUserRequest.toString());
		logger.debug("###### schUserRes.getMbrLglAgent Res : " + schUserRes.getMbrLglAgent().toString());

		return mbrLglAgent;
	}

	/* SC API 회원 징계정보 조회 */
	@Override
	public UserMbrPnsh searchUserMbrPnsh(DetailReq req, SacRequestHeader sacHeader) {

		String userId = StringUtil.nvl(req.getUserId(), "");
		String userKey = StringUtil.nvl(req.getUserKey(), "");
		String deviceId = StringUtil.nvl(req.getDeviceId(), "");
		String deviceKey = StringUtil.nvl(req.getDeviceKey(), "");

		String keyType = "";
		String keyValue = "";
		if (!userKey.equals("")) {
			keyType = "userKey";
			keyValue = userKey;
		} else if (!userId.equals("")) {
			keyType = "userId";
			keyValue = userId;
		} else if (!deviceKey.equals("")) {
			keyType = "deviceKey";
			keyValue = deviceKey;
		} else if (!deviceId.equals("")) {
			keyType = "deviceId";
			keyValue = deviceId;
		}

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
		 * SC 사용자 회원 징계정보 조회
		 */
		UserMbrPnsh res = new UserMbrPnsh();
		SearchUserResponse schUserRes = this.userSCI.searchUser(searchUserRequest);

		logger.debug("사용자 징계정보 조회 {}", schUserRes.getUserMbrPnsh().toString());

		res.setIsRestricted(StringUtil.setTrim(schUserRes.getUserMbrPnsh().getIsRestricted()));
		res.setRestrictCount(StringUtil.setTrim(schUserRes.getUserMbrPnsh().getRestrictCount()));
		res.setRestrictEndDate(StringUtil.setTrim(schUserRes.getUserMbrPnsh().getRestrictEndDate()));
		res.setRestrictId(StringUtil.setTrim(schUserRes.getUserMbrPnsh().getRestrictID()));
		res.setRestrictOwner(StringUtil.setTrim(schUserRes.getUserMbrPnsh().getRestrictOwner()));
		res.setRestrictRegisterDate(StringUtil.setTrim(schUserRes.getUserMbrPnsh().getRestrictRegisterDate()));
		res.setRestrictStartDate(StringUtil.setTrim(schUserRes.getUserMbrPnsh().getRestrictStartDate()));
		res.setTenantId(StringUtil.setTrim(schUserRes.getUserMbrPnsh().getTenantID()));
		res.setUserKey(StringUtil.setTrim(schUserRes.getUserMbrPnsh().getUserKey()));

		logger.debug("###### 회원징계정보조회 Req - keyType : {}, keyValue : {}", keyType, keyValue);
		logger.debug("###### 회원징계정보조회 Res : {}", res.toString());

		return res;

	}

	/* 각 단말의 OS별 누적 가입자 수 조회 */
	@Override
	public ListDailyPhoneOsSacRes listDailyPhoneOs(SacRequestHeader sacHeader) {
		/* 헤더 정보 셋팅 */
		commonRequest.setSystemID(sacHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(sacHeader.getTenantHeader().getTenantId());

		SearchDeviceOSNumberRequest scReq = new SearchDeviceOSNumberRequest();
		scReq.setCommonRequest(commonRequest);
		SearchDeviceOSNumberResponse scRes = this.userSCI.searchDeviceOSNumber(scReq);

		Iterator<List<DeviceSystemStats>> it = scRes.getDeviceSystemStatsMap().values().iterator();

		List<DailyPhoneOs> phoneOsList = new ArrayList<DailyPhoneOs>();

		while (it.hasNext()) {
			List<DeviceSystemStats> deviceSystemStats = it.next();

			List<DailyPhone> dailyPhoneList = new ArrayList<DailyPhone>();

			DailyPhoneOs dailyPhoneOs = new DailyPhoneOs();

			for (DeviceSystemStats stats : deviceSystemStats) {
				DailyPhone dailyPhone = new DailyPhone();

				dailyPhone.setOsVersion(StringUtil.setTrim(stats.getOsVersion()));
				dailyPhone.setEnctryCount(StringUtil.setTrim(stats.getEntryCount()));
				// dailyPhone.setModelName(StringUtil.setTrim(stats.getModelName()));

				dailyPhoneList.add(dailyPhone);
				dailyPhoneOs.setPhoneOsList(dailyPhoneList);
				dailyPhoneOs.setModelName(StringUtil.setTrim(stats.getModelName()));

			}

			phoneOsList.add(dailyPhoneOs);

		}
		ListDailyPhoneOsSacRes dailyPhoneOsSacList = new ListDailyPhoneOsSacRes();
		dailyPhoneOsSacList.setDailyPhoneList(phoneOsList);

		return dailyPhoneOsSacList;

	}

	/* SC API 회원 기본정보 조회 */
	@Override
	public DetailRes searchUserBase(DetailReq req, SacRequestHeader sacHeader) {

		String userId = StringUtil.nvl(req.getUserId(), "");
		String userKey = StringUtil.nvl(req.getUserKey(), "");
		String deviceId = StringUtil.nvl(req.getDeviceId(), "");
		String deviceKey = StringUtil.nvl(req.getDeviceKey(), "");

		String keyType = "";
		String keyValue = "";

		if (!userKey.equals("")) {
			keyType = "userKey";
			keyValue = userKey;
		} else if (!userId.equals("")) {
			keyType = "userId";
			keyValue = userId;
		} else if (!deviceKey.equals("")) {
			keyType = "deviceKey";
			keyValue = deviceKey;
		} else if (!deviceId.equals("")) {
			keyType = "deviceId";
			keyValue = deviceId;
		}

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
		DetailRes res = new DetailRes();
		SearchUserResponse schUserRes = this.userSCI.searchUser(searchUserRequest);

		logger.debug("사용자 기본정보 조회 {}", schUserRes.toString());

		res.setIsChangeSubject(StringUtil.setTrim(schUserRes.getIsChangeSubject()));
		res.setPwRegDate(StringUtil.setTrim(schUserRes.getPwRegDate()));
		res.setUserKey(StringUtil.setTrim(schUserRes.getUserKey()));

		logger.debug("###### 회원기본정보조회 Req - keyType : {}, keyValue : {}", keyType, keyValue);
		logger.debug("###### 회원기본정보조회 Res : {}", res.toString());

		return res;

	}

	/* SC API 회원정보 조회 */
	@Override
	public UserInfo searchUser(DetailReq req, SacRequestHeader sacHeader) {

		String userId = StringUtil.nvl(req.getUserId(), "");
		String userKey = StringUtil.nvl(req.getUserKey(), "");
		String deviceId = StringUtil.nvl(req.getDeviceId(), "");
		String deviceKey = StringUtil.nvl(req.getDeviceKey(), "");

		String keyType = "";
		String keyValue = "";
		if (!userKey.equals("")) {
			keyType = "userKey";
			keyValue = userKey;
		} else if (!userId.equals("")) {
			keyType = "userId";
			keyValue = userId;
		} else if (!deviceKey.equals("")) {
			keyType = "deviceKey";
			keyValue = deviceKey;
		} else if (!deviceId.equals("")) {
			keyType = "deviceId";
			keyValue = deviceId;
		}

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
		UserInfo userInfo = new UserInfo();
		List<UserExtraInfo> listExtraInfo = new ArrayList<UserExtraInfo>();

		SearchUserResponse schUserRes = this.userSCI.searchUser(searchUserRequest);

		if (schUserRes.getUserMbr() != null) {
			userInfo.setDeviceCount(StringUtil.setTrim(schUserRes.getUserMbr().getDeviceCount()));
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

			if (schUserRes.getMbrAuth().getIsRealName().equals("Y")) {
				userInfo.setUserBirthDay(StringUtil.setTrim(schUserRes.getMbrAuth().getBirthDay()));
				userInfo.setUserName(StringUtil.setTrim(schUserRes.getMbrAuth().getName()));
				userInfo.setUserSex(StringUtil.setTrim(schUserRes.getMbrAuth().getSex()));
			} else if (schUserRes.getMbrAuth().getIsRealName().equals("N")) {
				userInfo.setUserBirthDay(StringUtil.setTrim(schUserRes.getUserMbr().getUserBirthDay()));
				userInfo.setUserName(StringUtil.setTrim(schUserRes.getUserMbr().getUserName()));
				userInfo.setUserSex(StringUtil.setTrim(schUserRes.getUserMbr().getUserSex()));
			}

			if (schUserRes.getMbrMangItemPtcrList() != null) {
				for (MbrMangItemPtcr ptcr : schUserRes.getMbrMangItemPtcrList()) {

					logger.debug("============================================ UserExtraInfo CODE : {}", ptcr.getExtraProfile());
					logger.debug("============================================ UserExtraInfo VALUE : {}", ptcr.getExtraProfileValue());

					UserExtraInfo extra = new UserExtraInfo();
					extra.setExtraProfile(StringUtil.setTrim(ptcr.getExtraProfile()));
					extra.setExtraProfileValue(StringUtil.setTrim(ptcr.getExtraProfileValue()));

					listExtraInfo.add(extra);
				}

				userInfo.setUserExtraInfoList(listExtraInfo);
			}

		}

		logger.debug("============================================ UserSearch Req : {}", searchUserRequest.getKeySearchList().toString());
		logger.debug("============================================ UserSearch Res : {}", userInfo.toString());

		return userInfo;

	}

	/* SC API 회원부가정보 조회 Request : userKey */
	@Override
	public UserExtraInfoRes listUserExtra(DetailReq req, SacRequestHeader sacHeader) {

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

		logger.debug("############ 부가정보 리스트 Size : {}", schUserExtraRes.getMbrMangItemPtcrList().size());

		/* 유저키 세팅 */
		extraRes.setUserKey(schUserExtraRes.getUserKey());
		/* 부가정보 값 세팅 */
		for (MbrMangItemPtcr ptcr : schUserExtraRes.getMbrMangItemPtcrList()) {

			logger.debug("###### SC 부가정보 데이터 검증 CODE {}", ptcr.getExtraProfile());
			logger.debug("###### SC 부가정보 데이터 검증 VALUE {}", ptcr.getExtraProfileValue());

			UserExtraInfo extra = new UserExtraInfo();
			extra.setExtraProfile(StringUtil.setTrim(ptcr.getExtraProfile()));
			extra.setExtraProfileValue(StringUtil.setTrim(ptcr.getExtraProfileValue()));

			listExtraInfo.add(extra);

			extraRes.setUserExtraInfoList(listExtraInfo);

		}
		return extraRes;

	}

	/* SC API 디바이스 리스트 조회 */
	@Override
	public ListDeviceRes listDevice(DetailReq req, SacRequestHeader sacHeader) {
		ListDeviceReq listDeviceReq = new ListDeviceReq();

		String userId = StringUtil.nvl(req.getUserId(), "");
		String userKey = StringUtil.nvl(req.getUserKey(), "");
		String deviceId = StringUtil.nvl(req.getDeviceId(), "");
		String deviceKey = StringUtil.nvl(req.getDeviceKey(), "");

		ExistReq existReq = new ExistReq();
		ExistRes existRes = new ExistRes();

		if (!userKey.equals("")) {
			listDeviceReq.setUserKey(req.getUserKey());
			listDeviceReq.setIsMainDevice("N");
		} else if (!userId.equals("")) {
			existReq.setUserId(req.getUserId());
			existRes = this.exist(sacHeader, existReq);

			listDeviceReq.setUserKey(existRes.getUserKey());
			listDeviceReq.setUserId(req.getUserId());
			listDeviceReq.setIsMainDevice("N");
		} else if (!deviceKey.equals("")) {
			existReq.setDeviceKey(req.getDeviceKey());
			existRes = this.exist(sacHeader, existReq);

			listDeviceReq.setUserKey(existRes.getUserKey());
			listDeviceReq.setDeviceKey(req.getDeviceKey());
			listDeviceReq.setIsMainDevice("N");
		} else if (!deviceId.equals("")) {
			existReq.setDeviceId(req.getDeviceId());
			existRes = this.exist(sacHeader, existReq);

			listDeviceReq.setUserKey(existRes.getUserKey());
			listDeviceReq.setDeviceId(req.getDeviceId());
			listDeviceReq.setIsMainDevice("N");
		}

		ListDeviceRes listDeviceRes = this.deviceService.listDevice(sacHeader, listDeviceReq);
		if (listDeviceRes.getDeviceInfoList() != null) {
			listDeviceRes.setDeviceInfoList(listDeviceRes.getDeviceInfoList());
		}

		return listDeviceRes;
	}

	/* SC API 약관동의 목록 조회 */
	@Override
	public SearchAgreementRes searchUserAgreement(DetailReq req, SacRequestHeader sacHeader) {

		SearchUserRequest searchUserRequest = new SearchUserRequest();
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(sacHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(sacHeader.getTenantHeader().getTenantId());
		searchUserRequest.setCommonRequest(commonRequest);

		String userId = StringUtil.nvl(req.getUserId(), "");
		String userKey = StringUtil.nvl(req.getUserKey(), "");
		String deviceId = StringUtil.nvl(req.getDeviceId(), "");
		String deviceKey = StringUtil.nvl(req.getDeviceKey(), "");

		String keyType = "";
		String keyValue = "";
		if (!userKey.equals("")) {
			keyType = "userKey";
			keyValue = userKey;
		} else if (!userId.equals("")) {
			keyType = "userId";
			keyValue = userId;
		} else if (!deviceKey.equals("")) {
			keyType = "deviceKey";
			keyValue = deviceKey;
		} else if (!deviceId.equals("")) {
			keyType = "deviceId";
			keyValue = deviceId;
		}

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
		searchUserRequest.setKeySearchList(keySearchList);

		/**
		 * SC 사용자 약관동의 목록 조회
		 */
		SearchUserResponse searchUserResponse = this.userSCI.searchUser(searchUserRequest);
		SearchAgreementRes searchAgreementRes = new SearchAgreementRes();
		List<Agreement> listAgreement = new ArrayList<Agreement>();

		logger.debug("############ 약관동의 리스트 Size : {}", searchUserResponse.getMbrClauseAgreeList().size());

		/* 유저키 세팅 */
		searchAgreementRes.setUserKey(searchUserResponse.getUserKey());
		/* 약관동의 세팅 */
		for (MbrClauseAgree mbrAgree : searchUserResponse.getMbrClauseAgreeList()) {

			Agreement agree = new Agreement();
			agree.setExtraAgreementId(StringUtil.setTrim(mbrAgree.getExtraAgreementID()));
			agree.setExtraAgreementVersion(StringUtil.setTrim(mbrAgree.getExtraAgreementVersion()));
			agree.setIsExtraAgreement(StringUtil.setTrim(mbrAgree.getIsExtraAgreement()));

			listAgreement.add(agree);
		}

		searchAgreementRes.setAgreementList(listAgreement);

		return searchAgreementRes;

	}

	@Override
	public DetailByDeviceIdSacRes detailByDeviceId(SacRequestHeader sacHeader, DetailByDeviceIdSacReq req) {

		/**
		 * OPMD 단말 여부, OPMD 모번호 setting.
		 */
		DetailByDeviceIdSacRes response = this.setOmpdInfo(req);

		/**
		 * 사용자별 정책 리스트 setting.
		 */
		if (!StringUtils.equals(req.getKey(), "") && req.getPolicyCodeList() != null) {
			response.setPolicyCodeList(this.getIndividualPolicy(sacHeader, req));
		}

		/**
		 * 사용자 정보/단말 정보 setting.
		 */
		this.setDeviceInfo(sacHeader, req, response);

		/**
		 * SKT 이용정지회원 여부 setting.
		 */
		if (StringUtils.equals(response.getDeviceTelecom(), MemberConstants.DEVICE_TELECOM_SKT)) {
			response.setIsSktPause(this.mcc.getIsSktPause(req.getDeviceId()));
		}

		return response;
	}

	/**
	 * <pre>
	 * OPMD 단말 여부, OPMD 모번호 setting.
	 * </pre>
	 * 
	 * @param req
	 *            Request Value Object
	 * @return DetailByDeviceIdSacRes
	 */
	private DetailByDeviceIdSacRes setOmpdInfo(DetailByDeviceIdSacReq req) {

		DetailByDeviceIdSacRes response = new DetailByDeviceIdSacRes();

		if (StringUtils.equals(req.getDeviceIdType(), MemberConstants.DEVICE_ID_TYPE_MSISDN)) {

			/**
			 * OPMD 단말 여부, OPMD 모번호 setting.
			 */
			if (StringUtils.substring(req.getDeviceId(), 0, 3).equals("989")) {

				response.setIsOpmd(MemberConstants.USE_Y);
				response.setMsisdn(this.mcc.getOpmdMdnInfo(req.getDeviceId()));

			} else {

				response.setIsOpmd(MemberConstants.USE_N);

			}

		}

		return response;

	}

	/**
	 * <pre>
	 * 사용자별 정책 조회.
	 * </pre>
	 * 
	 * @param header
	 *            공통 헤더
	 * @param req
	 *            Request Value Object
	 * @return List<IndividualPolicyInfo>
	 */
	public List<IndividualPolicyInfo> getIndividualPolicy(SacRequestHeader header, DetailByDeviceIdSacReq req) {

		/**
		 * Request setting.
		 */
		SearchPolicyRequest policyRequest = new SearchPolicyRequest();
		List<String> codeList = new ArrayList<String>();
		for (int i = 0; i < req.getPolicyCodeList().size(); i++) {
			codeList.add(req.getPolicyCodeList().get(i).getPolicyCode());
		}
		policyRequest.setLimitPolicyCodeList(codeList);
		policyRequest.setLimitPolicyKey(req.getKey());

		/**
		 * SC 공통 정보 setting.
		 */
		policyRequest.setCommonRequest(this.mcc.getSCCommonRequest(header));
		logger.info("## policyRequest : {}", policyRequest.toString());

		List<IndividualPolicyInfo> policyInfos = null;

		try {

			/**
			 * SC 사용자 정책 리스트 조회 연동.
			 */
			SearchPolicyResponse policyResponse = this.userSCI.searchPolicyList(policyRequest);

			/**
			 * 처리 결과 setting.
			 */
			IndividualPolicyInfo policyInfo = null;
			if (policyResponse.getLimitTargetList().size() > 0) {

				if (policyResponse.getLimitTargetList().size() > 0) {
					policyInfos = new ArrayList<IndividualPolicyInfo>();
					for (int i = 0; i < policyResponse.getLimitTargetList().size(); i++) {
						policyInfo = new IndividualPolicyInfo();
						policyInfo.setKey(policyResponse.getLimitTargetList().get(i).getLimitPolicyKey());
						policyInfo.setPolicyCode(policyResponse.getLimitTargetList().get(i).getLimitPolicyCode());
						policyInfo.setValue(policyResponse.getLimitTargetList().get(i).getPolicyApplyValue());
						policyInfos.add(policyInfo);
					}
				}

			}

		} catch (StorePlatformException spe) {

			/**
			 * 조회된 데이타가 없을경우 Skip.
			 */
			if (StringUtils.equals(spe.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_DATA)) {
				logger.info("## 조회된 사용자 정책이 없습니다. [{}]", spe.getErrorInfo().getCode());
			} else {
				throw spe;
			}

		}

		return policyInfos;
	}

	/**
	 * <pre>
	 * 휴대기기 정보 setting.
	 * </pre>
	 * 
	 * @param sacHeader
	 * @param req
	 * @param response
	 * @return DetailByDeviceIdSacRes
	 */
	public DetailByDeviceIdSacRes setDeviceInfo(SacRequestHeader sacHeader, DetailByDeviceIdSacReq req, DetailByDeviceIdSacRes response) {

		/**
		 * 검색조건 정보 setting.
		 */
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySchUserKey = new KeySearch();
		keySchUserKey.setKeyType(MemberConstants.KEY_TYPE_DEVICE_ID);
		keySchUserKey.setKeyString(this.mcc.getOpmdMdnInfo(req.getDeviceId())); // 모번호 조회.
		keySearchList.add(keySchUserKey);

		/**
		 * 회원 조회 연동.
		 */
		CheckDuplicationRequest chkDupReq = new CheckDuplicationRequest();
		chkDupReq.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));
		chkDupReq.setKeySearchList(keySearchList);
		CheckDuplicationResponse chkDupRes = this.userSCI.checkDuplication(chkDupReq);
		if (StringUtils.equals(chkDupRes.getIsRegistered(), "N")) {

			logger.info("## 회원 정보가 존재 하지 않습니다. userKey, deviceKey, deviceTelecom 정보 없이 내려줌.");
			return response;

		}

		/**
		 * 사용자 기본 정보 조회.
		 */
		SearchUserResponse userInfo = this.getUserInfo(sacHeader, chkDupRes.getUserMbr().getUserKey());

		/**
		 * SC 회원의 등록된 휴대기기 상세정보를 조회 연동.
		 */
		SearchDeviceRequest searchDeviceRequest = new SearchDeviceRequest();
		searchDeviceRequest.setUserKey(userInfo.getUserKey()); // 회원 조회시 내려온 UserKey setting.
		searchDeviceRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));
		searchDeviceRequest.setKeySearchList(keySearchList);
		SearchDeviceResponse searchDeviceResponse = this.deviceSCI.searchDevice(searchDeviceRequest);

		/**
		 * 사용자 정보 setting.
		 */
		response.setUserKey(userInfo.getUserKey());
		response.setUserType(userInfo.getUserMbr().getUserType());
		response.setUserId(userInfo.getUserMbr().getUserID());
		response.setIsRealNameYn(userInfo.getUserMbr().getIsRealName());

		/**
		 * 실명인증정보 (이름, 생년월일) - 우선순위 (본인 실명인증 생년월일 > DB생년월일 > null)
		 */
		if (StringUtils.equals(userInfo.getUserMbr().getIsRealName(), MemberConstants.USE_Y)) {

			response.setUserName(ObjectUtils.toString(userInfo.getMbrAuth().getName()));
			response.setUserBirthDay(ObjectUtils.toString(userInfo.getMbrAuth().getBirthDay()));

		} else {

			response.setUserName(ObjectUtils.toString(userInfo.getUserMbr().getUserName()));
			response.setUserBirthDay(ObjectUtils.toString(userInfo.getUserMbr().getUserBirthDay()));

		}

		/**
		 * 단말 정보 setting.
		 */
		response.setDeviceKey(searchDeviceResponse.getUserMbrDevice().getDeviceKey());
		response.setDeviceId(searchDeviceResponse.getUserMbrDevice().getDeviceID());
		response.setModel(searchDeviceResponse.getUserMbrDevice().getDeviceModelNo());
		response.setDeviceTelecom(searchDeviceResponse.getUserMbrDevice().getDeviceTelecom());
		/* 선물수신가능 단말여부 (TB_CM_DEVICE의 GIFT_SPRT_YN) */
		response.setGiftYn(this.mcc.getPhoneInfo(searchDeviceResponse.getUserMbrDevice().getDeviceModelNo()).getGiftSprtYn());

		return response;

	}

	/**
	 * <pre>
	 * 사용자 정보를 조회한다.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param userKey
	 *            사용자 Key
	 * @return SearchUserResponse (사용자 정보)
	 */
	private SearchUserResponse getUserInfo(SacRequestHeader sacHeader, String userKey) {

		SearchUserRequest searchUserRequest = new SearchUserRequest();
		searchUserRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));

		/**
		 * 검색 조건 setting
		 */
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySchUserKey = new KeySearch();
		keySchUserKey.setKeyType(MemberConstants.KEY_TYPE_INSD_USERMBR_NO);
		keySchUserKey.setKeyString(userKey);
		keySearchList.add(keySchUserKey);
		searchUserRequest.setKeySearchList(keySearchList);

		/**
		 * 회원 정보조회.
		 */
		SearchUserResponse schUserRes = this.userSCI.searchUser(searchUserRequest);

		return schUserRes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.member.user.service.UserSearchService#
	 * searchUserByUserKey(com.skplanet.storeplatform
	 * .sac.client.member.vo.user.SearchUserReq)
	 */
	@Override
	public Map<String, UserInfoByUserKey> searchUserByUserKey(SacRequestHeader sacHeader, SearchUserReq request) {

		// 공통파라미터 셋팅
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(sacHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(sacHeader.getTenantHeader().getTenantId());

		List<String> userKeyList = request.getUserKeyList();

		SearchMbrUserRequest searchMbrUserRequest = new SearchMbrUserRequest();
		searchMbrUserRequest.setUserKeyList(userKeyList);
		searchMbrUserRequest.setCommonRequest(commonRequest);
		logger.info("[UserSearchServiceImpl.searchUserByUserKey] SC UserSCI.searchMbrUser() 호출.");
		SearchMbrUserResponse searchMbrUserResponse = this.userSCI.searchMbrUser(searchMbrUserRequest);

		logger.info("[UserSearchServiceImpl.searchUserByUserKey] SC ResultCode : {}", searchMbrUserResponse.getCommonResponse().getResultCode());

		Map<String, UserMbrStatus> userInfoMap = searchMbrUserResponse.getUserMbrStatusMap();

		Map<String, UserInfoByUserKey> resMap = new HashMap<String, UserInfoByUserKey>();
		UserInfoByUserKey userInfoByUserKey;

		if (userInfoMap != null && !"".equals(userInfoMap)) {
			for (int i = 0; i < userKeyList.size(); i++) {
				if (userInfoMap.get(userKeyList.get(i)) != null) {
					userInfoByUserKey = new UserInfoByUserKey();
					userInfoByUserKey.setUserKey(userInfoMap.get(userKeyList.get(i)).getUserKey());
					userInfoByUserKey.setUserId(userInfoMap.get(userKeyList.get(i)).getUserID());
					userInfoByUserKey.setUserMainStatus(userInfoMap.get(userKeyList.get(i)).getUserMainStatus());
					userInfoByUserKey.setUserSubStatus(userInfoMap.get(userKeyList.get(i)).getUserSubStatus());
					userInfoByUserKey.setUserType(userInfoMap.get(userKeyList.get(i)).getUserType());
					// 등록기기(deviceIdList) 없는경우, size=0 인 List로 내려달라고 SAC 전시 요청 -> SC 회원에서 size=0인 List로 내려주기로함.
					userInfoByUserKey.setDeviceIdList(userInfoMap.get(userKeyList.get(i)).getDeviceIDList());

					resMap.put(userKeyList.get(i), userInfoByUserKey);
				}
				// userKey에해당하는 회원정보 없을 경우, Map에 안내려줌.
				// else {
				// // userKey에해당하는 회원정보 없을 경우, userKey에 null 맵핑해서 전달.
				// resMap.put(userKeyList.get(i), new UserInfoByUserKey());
				// }
			}
			logger.info("[UserSearchServiceImpl.searchUserByUserKey] SAC UserInfo Response : {}", resMap);

		} else {
			throw new StorePlatformException("SAC_MEM_0003", "userKey", "");
		}

		return resMap;
	}

}
