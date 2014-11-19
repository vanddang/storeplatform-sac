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
import com.skplanet.storeplatform.member.client.user.sci.vo.DeviceMbrStatus;
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
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchExtentUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchExtentUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchGameCenterRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchGameCenterResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchManagementListRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchManagementListResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchMbrDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchMbrDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchMbrUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchMbrUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserEmailRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserEmailResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserDeviceKey;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbr;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrStatus;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserDeviceSac;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserDeviceSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.UserDeviceInfoSac;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.UserInfoSac;
import com.skplanet.storeplatform.sac.client.member.vo.common.Agreement;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.GradeInfo;
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
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailV2Res;
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
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchIdSac;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchIdSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchIdSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchPasswordSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchPasswordSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.UserExtraInfoRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.util.CommonUtils;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;

/**
 * 회원 조회 서비스 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 1. 7. Updated by : 강신완, 부르칸.
 */
@Service
public class UserSearchServiceImpl implements UserSearchService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserSearchServiceImpl.class);

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
		DetailRes detailRes = this.srhUser(detailReq, sacHeader);

		result.setUserKey(StringUtil.setTrim(detailRes.getUserInfo().getUserKey()));
		result.setUserType(StringUtil.setTrim(detailRes.getUserInfo().getUserType()));
		result.setUserId(StringUtil.setTrim(detailRes.getUserInfo().getUserId()));
		result.setIsRealName(StringUtil.setTrim(detailRes.getUserInfo().getIsRealName()));
		result.setAgencyYn(StringUtil.setTrim(detailRes.getUserInfo().getIsParent()));
		result.setUserEmail(StringUtil.setTrim(detailRes.getUserInfo().getUserEmail()));
		result.setUserMainStatus(StringUtil.setTrim(detailRes.getUserInfo().getUserMainStatus()));
		result.setUserSubStatus(StringUtil.setTrim(detailRes.getUserInfo().getUserSubStatus()));

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
		}

		/* 회원 기본 정보 */
		DetailRes res = this.srhUser(req, sacHeader);

		/* 정보조회범위 */
		if (req.getSearchExtent() != null) {
			/* 회원 정보 + 부가정보 */
			if (!"Y".equals(req.getSearchExtent().getUserInfoYn())) {
				res.setUserInfo(null);
			}

			/* 단말 + 부가정보 */
			if ("Y".equals(req.getSearchExtent().getDeviceInfoYn())) {
				try {
					ListDeviceRes listDeviceRes = this.listDevice(req, sacHeader);

					if (listDeviceRes.getDeviceInfoList() != null) {
						res.setDeviceInfoList(listDeviceRes.getDeviceInfoList());
					} else {
						List<DeviceInfo> getDeviceInfoList = new ArrayList<DeviceInfo>();
						res.setDeviceInfoList(getDeviceInfoList);
					}

				} catch (StorePlatformException ex) {
					/* 결과가 없는 경우만 제외하고 throw */
					if (ex.getErrorInfo().getCode().equals(MemberConstants.SC_ERROR_NO_DATA)) {
						List<DeviceInfo> getDeviceInfoList = new ArrayList<DeviceInfo>();
						res.setDeviceInfoList(getDeviceInfoList);
					} else {
						throw ex;
					}
				}
			}

			/* 약관동의정보 */
			if (!"Y".equals(req.getSearchExtent().getAgreementInfoYn())) {
				res.setAgreementList(null);
			}

			/* 실명인증정보 */
			if (!"Y".equals(req.getSearchExtent().getMbrAuthInfoYn())) {
				res.setMbrAuth(null);
			}

			/* 법정대리인정보 */
			if (!"Y".equals(req.getSearchExtent().getMbrLglAgentInfoYn())) {
				res.setMbrLglAgent(null);
			}

			/* 사용자징계정보 */
			if (!"Y".equals(req.getSearchExtent().getMbrPnshInfoYn())) {
				res.setUserMbrPnsh(null);
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
		}

		/* 회원 기본 정보 */

		SearchGameCenterRequest scReq = new SearchGameCenterRequest();
		scReq.setCommonRequest(commonRequest);
		if (!deviceId.equals("")) {
			List<String> workCodeList = new ArrayList<String>();
			String userChange = MemberConstants.GAMECENTER_WORK_CD_USER_CHANGE;
			String imuserChange = MemberConstants.GAMECENTER_WORK_CD_IMUSER_CHANGE;

			workCodeList.add(userChange);
			workCodeList.add(imuserChange);

			scReq.setWorkCodeList(workCodeList);
			scReq.setDeviceID(req.getDeviceId());
		}

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
	public MbrOneidSacRes srhUserOneId(SacRequestHeader sacHeader, MbrOneidSacReq req) {
		/* 헤더 정보 셋팅 */
		commonRequest.setSystemID(sacHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(sacHeader.getTenantHeader().getTenantId());

		/* 회원 기본 정보 */
		UserInfo info = this.mcc.getUserBaseInfo("userKey", req.getUserKey(), sacHeader);

		if (info.getImSvcNo() == null || info.getImSvcNo().equals("")) {
			throw new StorePlatformException("SAC_MEM_1302", req.getUserKey());
		}

		String isCi = null; // CI 존재유무
		String isRealName = null; // 실명인증 유무
		String isMemberPoint = null; // OCB 가입여부

		if (StringUtil.equals(req.getSearchType(), "1")) { // IDP 통합서버 조회
			// UserInfoSearchServerEcReq idpReq = new UserInfoSearchServerEcReq();
			// idpReq.setKey(info.getImSvcNo());
			// UserInfoSearchServerEcRes res = this.imIdpSCI.userInfoSearchServer(idpReq);
			//
			// isCi = StringUtil.isNotBlank(res.getUserCi()) ? "Y" : "N";
			// isRealName = res.getIsRnameAuth(); // default : N
			// isMemberPoint = res.getJoinSstList().indexOf(MemberConstants.SSO_SST_CD_OCB_WEB) != -1 ? "Y" : "N";

			UserInfoIdpSearchServerEcReq idpReq = new UserInfoIdpSearchServerEcReq();
			idpReq.setKey(info.getImSvcNo()); // 통합서비스 관리번호
			UserInfoIdpSearchServerEcRes res = this.imIdpSCI.userInfoIdpSearchServer(idpReq);
			isCi = StringUtil.isNotBlank(res.getUserCi()) ? "Y" : "N";
			isRealName = res.getIsRnameAuth(); // default : N
			isMemberPoint = res.getJoinSstList().indexOf(MemberConstants.SSO_SST_CD_OCB_WEB) != -1 ? "Y" : "N";
		} else { // SC DB 조회
			SearchAgreeSiteRequest scReq = new SearchAgreeSiteRequest();
			scReq.setCommonRequest(commonRequest);
			scReq.setImSvcNo(info.getImSvcNo());
			SearchAgreeSiteResponse scRes = this.userSCI.searchAgreeSite(scReq);

			isCi = StringUtil.isNotBlank(scRes.getMbrOneID().getIsCi()) ? scRes.getMbrOneID().getIsCi() : "N";
			isRealName = StringUtil.isNotBlank(scRes.getMbrOneID().getIsRealName()) ? scRes.getMbrOneID()
					.getIsRealName() : "N";
			isMemberPoint = StringUtil.isNotBlank(scRes.getMbrOneID().getIsMemberPoint()) ? scRes.getMbrOneID()
					.getIsMemberPoint() : "N";
		}

		MbrOneidSacRes res = new MbrOneidSacRes();
		res.setIsCi(isCi);
		res.setIsRealName(isRealName);
		res.setIsMemberPoint(isMemberPoint);
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
		// this.mcc.getUserBaseInfo("userKey", req.getUserKey(), sacHeader);

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
			agree.setIsMandatory(StringUtil.setTrim(scAgree.getIsMandatory()));

			agreementList.add(agree);
		}

		res.setAgreementList(agreementList);

		LOGGER.debug("ListTermsAgreementSacReq : ", res.toString());

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
	public SearchIdSacRes srhId(SacRequestHeader sacHeader, SearchIdSacReq req) {

		/* 헤더 정보 셋팅 */
		commonRequest.setSystemID(sacHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(sacHeader.getTenantHeader().getTenantId());

		/* 회원 정보 조회 */
		List<SearchIdSac> sacList = new ArrayList<SearchIdSac>();
		if (!req.getDeviceId().equals("")) {

			String opmdMdn = this.mcc.getOpmdMdnInfo(req.getDeviceId());
			req.setDeviceId(opmdMdn);
			LOGGER.debug("모번호 조회 getOpmdMdnInfo: {}", opmdMdn);

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
			sacList = this.srhUserEmail(req, sacHeader);
		}

		SearchIdSacRes res = new SearchIdSacRes();
		res.setSearchIdList(sacList);

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
	public SearchPasswordSacRes srhPassword(SacRequestHeader sacHeader, SearchPasswordSacReq req) {

		/* 헤더 정보 셋팅 */
		commonRequest.setSystemID(sacHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(sacHeader.getTenantHeader().getTenantId());

		/* 회원 정보 조회 */
		UserInfo info = this.mcc.getUserBaseInfo("userId", req.getUserId(), sacHeader);

		String checkId = "";
		if (info.getImSvcNo() == null || info.getImSvcNo().equals("")) {

			if (!req.getUserEmail().equals("")) {
				/* UserId와 Email이 일치하는지 체크 */
				if (info.getUserEmail().equals(req.getUserEmail())) {
					checkId = "Y";
				} else {
					checkId = "N";
				}
			} else if (!req.getUserPhone().equals("")) {
				/* UserId와 Phone이 일치하는지 체크 */

				String opmdMdn = this.mcc.getOpmdMdnInfo(req.getUserPhone());
				req.setUserPhone(opmdMdn);

				ListDeviceReq scReq = new ListDeviceReq();
				scReq.setUserKey(info.getUserKey());
				scReq.setDeviceId(req.getUserPhone());
				scReq.setIsMainDevice("Y");

				ListDeviceRes listDeviceRes = this.deviceService.listDevice(sacHeader, scReq);

				if (listDeviceRes.getUserKey() != null) {
					checkId = "Y";
				} else {
					checkId = "N";
				}
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

				LOGGER.debug("## IDP Request FindPasswd : {}", ecReqFindpass.toString());
				LOGGER.debug("## IDP Response FindPasswd : {}", ecResFindpass.getCommonRes().getResultText());

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

			LOGGER.debug("## ImIDP Request UserSearch : {}", ecReqUserInfo.toString());
			LOGGER.debug("## ImIDP Response UserSearch{}", ecResUserInfo.toString());

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

			LOGGER.debug("## ImIDP Request ResetUserPWD : {}", ecReqResetUserPwd.toString());

			ResetUserPwdIdpEcRes ecResResetUserPwd = this.imIdpSCI.resetUserPwdIdp(ecReqResetUserPwd);

			LOGGER.debug("## ImIDP Response ResetUserPWD : {}", ecResResetUserPwd.getCommonRes().getResultText());

			res.setSendInfo("");
			res.setUserPw("");
			res.setSendMean("03");
		}

		return res;
	}

	// userEmail 기반 사용자 조회
	@Override
	public List<SearchIdSac> srhUserEmail(SearchIdSacReq req, SacRequestHeader sacHeader) {

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

	/* SC API 회원정보 조회 */
	@Override
	public DetailRes srhUser(DetailReq req, SacRequestHeader sacHeader) {

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

		DetailRes detailRes = new DetailRes();

		/* 기본정보 세팅 */
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

				LOGGER.debug("============================================ UserExtraInfo CODE : {}",
						ptcr.getExtraProfile());
				LOGGER.debug("============================================ UserExtraInfo VALUE : {}",
						ptcr.getExtraProfileValue());

				UserExtraInfo extra = new UserExtraInfo();
				extra.setExtraProfile(StringUtil.setTrim(ptcr.getExtraProfile()));
				extra.setExtraProfileValue(StringUtil.setTrim(ptcr.getExtraProfileValue()));

				listExtraInfo.add(extra);
			}

			userInfo.setUserExtraInfoList(listExtraInfo);
		}

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

		LOGGER.debug("############ 부가정보 리스트 Size : {}", schUserExtraRes.getMbrMangItemPtcrList().size());

		/* 유저키 세팅 */
		extraRes.setUserKey(schUserExtraRes.getUserKey());
		/* 부가정보 값 세팅 */
		for (MbrMangItemPtcr ptcr : schUserExtraRes.getMbrMangItemPtcrList()) {

			LOGGER.debug("###### SC 부가정보 데이터 검증 CODE {}", ptcr.getExtraProfile());
			LOGGER.debug("###### SC 부가정보 데이터 검증 VALUE {}", ptcr.getExtraProfileValue());

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
		 * SKT 이용정지회원 여부 setting. (기기 타입이 msisdn 일 경우만 연동 한다.)
		 */
		if (StringUtils.equals(req.getDeviceIdType(), MemberConstants.DEVICE_ID_TYPE_MSISDN)) {
			response.setIsSktStop(this.mcc.getIsSktPause(req.getDeviceId(), req.getDeviceIdType()));
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
		LOGGER.debug("## policyRequest : {}", policyRequest);

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
						policyInfo.setKey(ObjectUtils.toString(policyResponse.getLimitTargetList().get(i)
								.getLimitPolicyKey()));
						policyInfo.setPolicyCode(ObjectUtils.toString(policyResponse.getLimitTargetList().get(i)
								.getLimitPolicyCode()));
						policyInfo.setValue(ObjectUtils.toString(policyResponse.getLimitTargetList().get(i)
								.getPolicyApplyValue()));
						policyInfo.setLimitAmount(ObjectUtils.toString(policyResponse.getLimitTargetList().get(i)
								.getLimitAmount()));
						policyInfo.setPreLimitAmount(ObjectUtils.toString(policyResponse.getLimitTargetList().get(i)
								.getPreLimitAmount()));
						policyInfo.setPermissionType(ObjectUtils.toString(policyResponse.getLimitTargetList().get(i)
								.getPermissionType()));
						policyInfo.setIsUsed(ObjectUtils.toString(policyResponse.getLimitTargetList().get(i)
								.getIsUsed()));
						policyInfos.add(policyInfo);
					}
				}

			}

		} catch (StorePlatformException spe) {

			/**
			 * 조회된 데이타가 없을경우 Skip.
			 */
			if (StringUtils.equals(spe.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_DATA)) {
				LOGGER.debug("## 조회된 사용자 정책이 없습니다. [{}]", spe.getErrorInfo().getCode());
				/**
				 * Data가 없을 경우 Element 는 생성하는걸로...
				 */
				policyInfos = new ArrayList<IndividualPolicyInfo>();
				IndividualPolicyInfo policyInfo = new IndividualPolicyInfo();
				policyInfo.setPolicyCode("");
				policyInfo.setKey("");
				policyInfo.setValue("");
				policyInfo.setLimitAmount("");
				policyInfo.setPreLimitAmount("");
				policyInfo.setPermissionType("");
				policyInfo.setIsUsed("");
				policyInfos.add(policyInfo);
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
	public DetailByDeviceIdSacRes setDeviceInfo(SacRequestHeader sacHeader, DetailByDeviceIdSacReq req,
			DetailByDeviceIdSacRes response) {

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

			LOGGER.info("회원 정보가 존재 하지 않습니다. userKey, deviceKey, deviceTelecom 정보 없이 내려줌.");
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
		response.setGiftYn(this.mcc.getPhoneInfo(searchDeviceResponse.getUserMbrDevice().getDeviceModelNo())
				.getGiftSprtYn());

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

	/**
	 * <pre>
	 * userKey 목록을 이용하여 회원정보 목록조회.
	 * </pre>
	 * 
	 * @param request
	 *            SearchUserSacReq
	 * @return SearchUserSacRes
	 */
	@Override
	public SearchUserSacRes srhUserByUserKey(SacRequestHeader sacHeader, SearchUserSacReq request) {

		// 공통파라미터 셋팅
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(sacHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(sacHeader.getTenantHeader().getTenantId());

		List<String> userKeyList = request.getUserKeyList();

		SearchMbrUserRequest searchMbrUserRequest = new SearchMbrUserRequest();
		searchMbrUserRequest.setUserKeyList(userKeyList);
		searchMbrUserRequest.setCommonRequest(commonRequest);
		LOGGER.debug("SAC Request {}", request);
		SearchMbrUserResponse searchMbrUserResponse = this.userSCI.searchMbrUser(searchMbrUserRequest);

		LOGGER.debug("[UserSearchServiceImpl.searchUserByUserKey] SC ResultCode : {}", searchMbrUserResponse
				.getCommonResponse().getResultCode());

		Map<String, UserMbrStatus> userInfoMap = searchMbrUserResponse.getUserMbrStatusMap();

		Map<String, UserInfoSac> userInfo = new HashMap<String, UserInfoSac>();
		UserInfoSac userInfoSac = null;
		if (userInfoMap != null) {
			for (int i = 0; i < userKeyList.size(); i++) {
				if (userInfoMap.get(userKeyList.get(i)) != null) {
					userInfoSac = new UserInfoSac();
					userInfoSac.setUserKey(userInfoMap.get(userKeyList.get(i)).getUserKey());
					userInfoSac.setUserId(userInfoMap.get(userKeyList.get(i)).getUserID());
					userInfoSac.setUserMainStatus(userInfoMap.get(userKeyList.get(i)).getUserMainStatus());
					userInfoSac.setUserSubStatus(userInfoMap.get(userKeyList.get(i)).getUserSubStatus());
					userInfoSac.setUserType(userInfoMap.get(userKeyList.get(i)).getUserType());
					// 등록기기(deviceIdList) 없는경우, size=0 인 List로 내려달라고 SAC 전시 요청 -> SC 회원에서 size=0인 List로 내려주기로함.
					userInfoSac.setDeviceIdList(userInfoMap.get(userKeyList.get(i)).getDeviceIDList());

					userInfo.put(userKeyList.get(i), userInfoSac);
				}
			}
			LOGGER.debug("[ SAC UserInfo Response : {}", userInfo);

		}
		// 회원정보 없는 경우 SC 회원에서 Exception 처리함.
		SearchUserSacRes searchUserSacRes = new SearchUserSacRes();
		searchUserSacRes.setUserInfo(userInfo);

		return searchUserSacRes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.service.UserSearchService#
	 * searchUserByDeviceKey(com.skplanet.storeplatform .sac.client.member.vo.user.SearchUserDeviceReq)
	 */
	@Override
	public Map<String, UserDeviceInfoSac> srhUserByDeviceKey(SacRequestHeader sacHeader, SearchUserDeviceSacReq request) {

		// Request 를 보내기 위한 세팅
		List<UserDeviceKey> userDeviceKeyList = new ArrayList<UserDeviceKey>();

		for (SearchUserDeviceSac schUserDevice : request.getSearchUserDeviceReqList()) {
			UserDeviceKey userDeviceKey = new UserDeviceKey();
			userDeviceKey.setDeviceKey(schUserDevice.getDeviceKey());
			userDeviceKey.setUserKey(schUserDevice.getUserKey());
			userDeviceKeyList.add(userDeviceKey);
		}

		SearchMbrDeviceRequest searchMbrDeviceRequest = new SearchMbrDeviceRequest();

		searchMbrDeviceRequest.setDeviceKeyList(userDeviceKeyList);
		searchMbrDeviceRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));

		LOGGER.info("[UserSearchServiceImpl.searchUserByDeviceKey] SC UserSCI.searchMbrDevice() 호출.");

		SearchMbrDeviceResponse searchMbrDeviceResponse = this.userSCI.searchMbrDevice(searchMbrDeviceRequest);

		LOGGER.info("[UserSearchServiceImpl.searchUserByDeviceKey] SC ResultCode : {}", searchMbrDeviceResponse
				.getCommonResponse().getResultCode());

		Map<String, DeviceMbrStatus> userDeviceInfoMap = searchMbrDeviceResponse.getDeviceMbrStatusMap();

		Map<String, UserDeviceInfoSac> resMap = new HashMap<String, UserDeviceInfoSac>();

		if (userDeviceInfoMap != null) {
			UserDeviceInfoSac userDeviceInfoSac = null;
			for (int i = 0; i < userDeviceKeyList.size(); i++) {

				DeviceMbrStatus deviceMbrStatus = userDeviceInfoMap.get(userDeviceKeyList.get(i).getDeviceKey());

				if (deviceMbrStatus != null) {
					userDeviceInfoSac = new UserDeviceInfoSac();
					userDeviceInfoSac.setDeviceId(deviceMbrStatus.getDeviceID());
					userDeviceInfoSac.setDeviceModelNo(deviceMbrStatus.getDeviceModelNo());
					userDeviceInfoSac.setDeviceTelecom(deviceMbrStatus.getDeviceTelecom());
					userDeviceInfoSac.setUserMainStatus(deviceMbrStatus.getUserMainStatus());
					userDeviceInfoSac.setUserSubStatus(deviceMbrStatus.getUserSubStatus());
					userDeviceInfoSac.setIsRealName(deviceMbrStatus.getIsRealName());
					userDeviceInfoSac.setUserId(deviceMbrStatus.getUserID());
					userDeviceInfoSac.setUserType(deviceMbrStatus.getUserType());

					if (StringUtil.equals(deviceMbrStatus.getIsRealName(), "Y")) {

						userDeviceInfoSac
								.setUserBirthday(StringUtil.isNotBlank(deviceMbrStatus.getAuthBirthDay()) ? deviceMbrStatus
										.getAuthBirthDay() : deviceMbrStatus.getUserBirthDay());

						userDeviceInfoSac
								.setUserName(StringUtil.isNotBlank(deviceMbrStatus.getAuthName()) ? deviceMbrStatus
										.getAuthName() : deviceMbrStatus.getUserName());

					} else {

						userDeviceInfoSac.setUserBirthday(deviceMbrStatus.getUserBirthDay());
						userDeviceInfoSac.setUserName(deviceMbrStatus.getUserName());

					}

					resMap.put(deviceMbrStatus.getDeviceKey(), userDeviceInfoSac);
				}
			}

		}

		return resMap;
	}

	/**
	 * <pre>
	 * 회원 정보 조회V2.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            SacRequestHeader
	 * @param req
	 *            DetailReq
	 * @return DetailV2Res
	 */
	@Override
	public DetailV2Res detailV2(SacRequestHeader sacHeader, DetailReq req) {

		/**
		 * 모번호 조회 (989 일 경우만)
		 */
		if (req.getDeviceId() != null) {
			String opmdMdnInfo = this.mcc.getOpmdMdnInfo(req.getDeviceId());
			req.setDeviceId(opmdMdnInfo);
		}

		/** 회원 기본 정보V2. */
		DetailV2Res res = this.srhUserV2(req, sacHeader);

		/* 정보조회범위 */
		if (req.getSearchExtent() != null) {
			/* 회원 정보 + 부가정보 */
			if (!StringUtils.equals(MemberConstants.USE_Y, req.getSearchExtent().getUserInfoYn())) {
				res.setUserInfo(null);
			}

			/* 단말 + 부가정보 */
			if (StringUtils.equals(MemberConstants.USE_Y, req.getSearchExtent().getDeviceInfoYn())) {
				try {
					ListDeviceRes listDeviceRes = this.listDevice(req, sacHeader);

					if (listDeviceRes.getDeviceInfoList() != null) {
						res.setDeviceInfoList(listDeviceRes.getDeviceInfoList());
					} else {
						List<DeviceInfo> getDeviceInfoList = new ArrayList<DeviceInfo>();
						res.setDeviceInfoList(getDeviceInfoList);
					}

				} catch (StorePlatformException ex) {
					/* 결과가 없는 경우만 제외하고 throw */
					if (ex.getErrorInfo().getCode().equals(MemberConstants.SC_ERROR_NO_DATA)) {
						List<DeviceInfo> getDeviceInfoList = new ArrayList<DeviceInfo>();
						res.setDeviceInfoList(getDeviceInfoList);
					} else {
						throw ex;
					}
				}
			}

		}

		return res;
	}

	/**
	 * <pre>
	 * 회원 기본정보 V2.
	 * </pre>
	 * 
	 * @param req
	 *            DetailReq
	 * @param sacHeader
	 *            SacRequestHeader
	 * @return DetailV2Res
	 */
	public DetailV2Res srhUserV2(DetailReq req, SacRequestHeader sacHeader) {

		String keyType = "";
		String keyValue = "";
		if (StringUtils.isNotBlank(req.getUserKey())) {
			keyType = MemberConstants.KEY_TYPE_INSD_USERMBR_NO;
			keyValue = req.getUserKey();
		} else if (StringUtils.isNotBlank(req.getUserId())) {
			keyType = MemberConstants.KEY_TYPE_MBR_ID;
			keyValue = req.getUserId();
		} else if (StringUtils.isNotBlank(req.getDeviceId())) {
			keyType = MemberConstants.KEY_TYPE_DEVICE_ID;
			keyValue = req.getDeviceId();
		} else if (StringUtils.isNotBlank(req.getDeviceKey())) {
			keyType = MemberConstants.KEY_TYPE_INSD_DEVICE_ID;
			keyValue = req.getDeviceKey();
		} else if (StringUtils.isNotBlank(req.getMbrNo())) {
			keyType = MemberConstants.KEY_TYPE_USERMBR_NO;
			keyValue = req.getDeviceKey();
		}

		/**
		 * 검색 조건 setting
		 */
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySchUserKey = new KeySearch();
		keySchUserKey.setKeyType(keyType);
		keySchUserKey.setKeyString(keyValue);
		keySearchList.add(keySchUserKey);

		/**
		 * SearchUserRequest setting
		 */
		SearchExtentUserRequest searchExtentUserRequest = new SearchExtentUserRequest();
		searchExtentUserRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));
		searchExtentUserRequest.setKeySearchList(keySearchList);

		// 검색 테이블 조건
		searchExtentUserRequest.setUserInfoYn(req.getSearchExtent().getUserInfoYn());
		searchExtentUserRequest.setAgreementInfoYn(req.getSearchExtent().getAgreementInfoYn());
		searchExtentUserRequest.setMbrAuthInfoYn(req.getSearchExtent().getMbrAuthInfoYn());
		searchExtentUserRequest.setMbrLglAgentInfoYn(req.getSearchExtent().getMbrLglAgentInfoYn());
		searchExtentUserRequest.setMbrPnshInfoYn(req.getSearchExtent().getMbrPnshInfoYn());
		searchExtentUserRequest.setGradeInfoYn(req.getSearchExtent().getGradeInfoYn());

		/**
		 * SC 사용자 회원 정보를 조회v2
		 */
		SearchExtentUserResponse schUserRes = this.userSCI.searchExtentUser(searchExtentUserRequest);

		DetailV2Res detailV2Res = new DetailV2Res();

		// 기본정보 설정
		detailV2Res.setIsChangeSubject(StringUtil.setTrim(schUserRes.getIsChangeSubject()));
		detailV2Res.setPwRegDate(StringUtil.setTrim(schUserRes.getPwRegDate()));
		detailV2Res.setUserKey(StringUtil.setTrim(schUserRes.getUserKey()));

		// 회원정보 성절
		if (StringUtils.equals(MemberConstants.USE_Y, req.getSearchExtent().getUserInfoYn())) {
			detailV2Res.setUserInfo(this.userInfoV2(schUserRes));
		}

		// 약관동의 설정
		if (StringUtils.equals(MemberConstants.USE_Y, req.getSearchExtent().getAgreementInfoYn())) {
			detailV2Res.setAgreementList(this.getListAgreementV2(schUserRes));
		}

		// 실명인증 설정
		if (StringUtils.equals(MemberConstants.USE_Y, req.getSearchExtent().getMbrAuthInfoYn())) {
			detailV2Res.setMbrAuth(this.getMbrAuthV2(schUserRes));
		}

		// 법정대리인 설정
		if (StringUtils.equals(MemberConstants.USE_Y, req.getSearchExtent().getMbrLglAgentInfoYn())) {
			detailV2Res.setMbrLglAgent(this.getMbrLglAgentV2(schUserRes));
		}

		// 징계정보 설정
		if (StringUtils.equals(MemberConstants.USE_Y, req.getSearchExtent().getMbrPnshInfoYn())) {
			detailV2Res.setUserMbrPnsh(this.getMbrPnshV2(schUserRes));
		}

		// 회원 등급 정보 설정
		if (StringUtils.equals(MemberConstants.USE_Y, req.getSearchExtent().getGradeInfoYn())) {
			detailV2Res.setGradeInfo(this.getGradeV2(schUserRes));
		}

		return detailV2Res;

	}

	/**
	 * <pre>
	 * 회원정보조회v2 - 기본정보 설정.
	 * </pre>
	 * 
	 * @param schUserRes
	 *            SearchExtentUserResponse
	 * @return UserInfo
	 */
	private UserInfo userInfoV2(SearchExtentUserResponse schUserRes) {
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
		if (StringUtils.equals(MemberConstants.USE_Y, schUserRes.getMbrAuth().getIsRealName())) {
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

		} else if (StringUtils.equals(MemberConstants.USE_N, schUserRes.getMbrAuth().getIsRealName())) {
			userInfo.setUserBirthDay(StringUtil.setTrim(schUserRes.getUserMbr().getUserBirthDay()));
			userInfo.setUserName(StringUtil.setTrim(schUserRes.getUserMbr().getUserName()));
			userInfo.setUserSex(StringUtil.setTrim(schUserRes.getUserMbr().getUserSex()));
		}

		// realAge 설정시 성별 유무 로직 제거
		if (StringUtils.isNotBlank(userInfo.getUserBirthDay())) {
			// if (StringUtil.isNotBlank(userInfo.getUserSex()) && StringUtil.isNotBlank(userInfo.getUserSex())) {
			String ageChk = "";
			String userSex = userInfo.getUserSex();
			String userBirthDay = userInfo.getUserBirthDay();

			// 생년월일 검증
			if (CommonUtils.isValidation(CommonUtils.regxNumber(userBirthDay)) != null) {
				if ("M".equals(userSex)) {
					ageChk = ("19".equals(userBirthDay.substring(0, 2))) ? "1" : "3";
				} else if ("F".equals(userSex)) {
					ageChk = ("19".equals(userBirthDay.substring(0, 2))) ? "2" : "4";
				} else if (StringUtils.isBlank(userSex)) { // Default = 성별 미존재시 M 으로 설정
					ageChk = ("19".equals(userBirthDay.substring(0, 2))) ? "1" : "3";
				}
				int age = CommonUtils.getAgeBySocalNumber(userBirthDay.substring(2, 8), ageChk);
				userInfo.setRealAge(String.valueOf(age));
			}
			// }
		}

		if (schUserRes.getMbrMangItemPtcrList() != null) {
			List<UserExtraInfo> listExtraInfo = new ArrayList<UserExtraInfo>();
			for (MbrMangItemPtcr ptcr : schUserRes.getMbrMangItemPtcrList()) {

				LOGGER.debug("============================================ UserExtraInfo CODE : {}",
						ptcr.getExtraProfile());
				LOGGER.debug("============================================ UserExtraInfo VALUE : {}",
						ptcr.getExtraProfileValue());

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
	 * 회원정보조회v2 - 약관정보 설정.
	 * </pre>
	 * 
	 * @param schUserRes
	 *            SearchExtentUserResponse
	 * @return List<Agreement>
	 */
	private List<Agreement> getListAgreementV2(SearchExtentUserResponse schUserRes) {
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
	 * 회원정보조회v2 - 실명인증정보 세팅.
	 * </pre>
	 * 
	 * @param schUserRes
	 *            SearchExtentUserResponse
	 * @return MbrAuth
	 */
	private MbrAuth getMbrAuthV2(SearchExtentUserResponse schUserRes) {
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
	 * 회원정보조회v2 - 법정대리인 세팅.
	 * </pre>
	 * 
	 * @param schUserRes
	 *            SearchExtentUserResponse
	 * @return MbrLglAgent
	 */
	private MbrLglAgent getMbrLglAgentV2(SearchExtentUserResponse schUserRes) {
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
	 * 회원정보조회v2 - 징계정보 세팅.
	 * </pre>
	 * 
	 * @param schUserRes
	 *            SearchExtentUserResponse
	 * @return UserMbrPnsh
	 */
	private UserMbrPnsh getMbrPnshV2(SearchExtentUserResponse schUserRes) {
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
	 * 회원정보조회v2 - 등급정보 설정.
	 * </pre>
	 * 
	 * @param schUserRes
	 *            SearchExtentUserResponse
	 * @return GradeInfo
	 */
	private GradeInfo getGradeV2(SearchExtentUserResponse schUserRes) {
		GradeInfo gradeInfo = new GradeInfo();
		gradeInfo.setUserGradeCd(StringUtil.setTrim(schUserRes.getGrade().getUserGradeCd()));
		return gradeInfo;
	}
}
