/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.user.sci.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.idp.sci.ImIdpSCI;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.UserInfoIdpSearchServerEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.UserInfoIdpSearchServerEcRes;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.common.vo.MbrClauseAgree;
import com.skplanet.storeplatform.member.client.common.vo.MbrMangItemPtcr;
import com.skplanet.storeplatform.member.client.common.vo.MemberPoint;
import com.skplanet.storeplatform.member.client.common.vo.SearchMbrSapUserInfo;
import com.skplanet.storeplatform.member.client.common.vo.SearchMemberPointRequest;
import com.skplanet.storeplatform.member.client.common.vo.SearchMemberPointResponse;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSCI;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.DeviceMbrStatus;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceListRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceListResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceOwnerRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceOwnerResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchExtentUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchExtentUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchManagementListRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchManagementListResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchManagementRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchManagementResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchMbrDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchMbrDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchMbrSapUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchMbrSapUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchMbrUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchMbrUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserExtraInfoRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserExtraInfoResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserSegmentRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserSegmentResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserDeviceKey;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrDevice;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrStatus;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.GradeInfoSac;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchOrderUserByDeviceIdSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchOrderUserByDeviceIdSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchSapUserInfoSac;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchSapUserSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchSapUserSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchSocialAccountSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchSocialAccountSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserDeviceSac;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserDeviceSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserExtraInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserExtraInfoSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserGradeSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserGradeSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSegmentSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSegmentSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SocialUserInfoSac;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.UserDeviceInfoSac;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.UserExtraInfoSac;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.UserInfoSac;
import com.skplanet.storeplatform.sac.client.member.vo.common.Agreement;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrAuth;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrLglAgent;
import com.skplanet.storeplatform.sac.client.member.vo.common.OcbInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserExtraInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserMbrPnsh;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ExistReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ExistRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.GetOcbInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.GetOcbInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.util.DeviceUtil;
import com.skplanet.storeplatform.sac.member.common.vo.Device;

/**
 * 사용자 내부 메서드 서비스 구현체
 * 
 * Updated on : 2014. 5. 20. Updated by : 심대진, 다모아 솔루션.
 */
@Service
public class SearchUserSCIServiceImpl implements SearchUserSCIService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SearchUserSCIServiceImpl.class);

	@Autowired
	private UserSCI userSCI;

	@Autowired
	private DeviceSCI deviceSCI;

	@Autowired
	private MemberCommonComponent mcc;

	@Autowired
	private ImIdpSCI imIdpSCI;

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
		// tenantId 추가, incorss_bottangs, 2015.02.10
		searchMbrUserRequest
				.setTenantId(StringUtils.isBlank(request.getTenantId()) ? MemberConstants.TENANT_ID_TSTORE : request
						.getTenantId());
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
					// tenantId 추가, incross_bottangs, 2015.02.10
					userInfoSac.setTenantId(userInfoMap.get(userKeyList.get(i)).getTenantID());

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
	 * @see
	 * com.skplanet.storeplatform.sac.member.user.sci.service.SearchUserSCIService#srhSapUserByUserKey(com.skplanet.
	 * storeplatform.sac.common.header.vo.SacRequestHeader,
	 * com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchSapUserSacReq)
	 */
	@Override
	public SearchSapUserSacRes srhSapUserByUserKey(SacRequestHeader sacHeader, SearchSapUserSacReq request) {

		// 공통파라미터 셋팅
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(sacHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(sacHeader.getTenantHeader().getTenantId());

		List<SearchSapUserInfoSac> userKeyList = request.getUserKeyList();
		List<SearchMbrSapUserInfo> userKeyMbrList = null;
		if (userKeyList != null && userKeyList.size() > 0) {
			userKeyMbrList = new ArrayList<SearchMbrSapUserInfo>();
			SearchMbrSapUserInfo searchMbrSapUserInfo = null;
			for (SearchSapUserInfoSac info : userKeyList) {
				if (StringUtils.isBlank(info.getUserKey()) || StringUtils.isBlank(info.getTenantId())) {
					throw new StorePlatformException("SAC_MEM_0001",
							StringUtils.isBlank(info.getUserKey()) ? "userKey" : "tenantId");
				}
				searchMbrSapUserInfo = new SearchMbrSapUserInfo();
				searchMbrSapUserInfo.setTenantId(info.getTenantId());
				searchMbrSapUserInfo.setUserKey(info.getUserKey());
				userKeyMbrList.add(searchMbrSapUserInfo);
			}
		}

		SearchMbrSapUserRequest searchMbrSapUserRequest = new SearchMbrSapUserRequest();
		searchMbrSapUserRequest.setUserKeyList(userKeyMbrList);
		searchMbrSapUserRequest.setCommonRequest(commonRequest);
		SearchMbrSapUserResponse searchMbrSapUserResponse = this.userSCI.searchMbrSapUser(searchMbrSapUserRequest);

		Map<String, UserMbrStatus> userInfoMap = searchMbrSapUserResponse.getUserMbrStatusMap();

		Map<String, UserInfoSac> userInfo = new HashMap<String, UserInfoSac>();
		UserInfoSac userInfoSac = null;
		if (userInfoMap != null) {
			for (int i = 0; i < userKeyMbrList.size(); i++) {
				if (userInfoMap.get(userKeyMbrList.get(i).getUserKey()) != null) {
					userInfoSac = new UserInfoSac();
					userInfoSac.setUserKey(userInfoMap.get(userKeyMbrList.get(i).getUserKey()).getUserKey());
					userInfoSac.setUserId(userInfoMap.get(userKeyMbrList.get(i).getUserKey()).getUserID());
					userInfoSac.setUserMainStatus(userInfoMap.get(userKeyMbrList.get(i).getUserKey())
							.getUserMainStatus());
					userInfoSac
							.setUserSubStatus(userInfoMap.get(userKeyMbrList.get(i).getUserKey()).getUserSubStatus());
					userInfoSac.setUserType(userInfoMap.get(userKeyMbrList.get(i).getUserKey()).getUserType());
					// 등록기기(deviceIdList) 없는경우, size=0 인 List로 내려달라고 SAC 전시 요청 -> SC 회원에서 size=0인 List로 내려주기로함.
					userInfoSac.setDeviceIdList(userInfoMap.get(userKeyMbrList.get(i).getUserKey()).getDeviceIDList());
					// tenantId 추가, incross_bottangs, 2015.02.10
					userInfoSac.setTenantId(userInfoMap.get(userKeyMbrList.get(i).getUserKey()).getTenantID());

					userInfo.put(userKeyMbrList.get(i).getUserKey(), userInfoSac);
				}
			}
		}
		// 회원정보 없는 경우 SC 회원에서 Exception 처리함.
		SearchSapUserSacRes searchSapUserSacRes = new SearchSapUserSacRes();
		searchSapUserSacRes.setUserInfo(userInfo);

		return searchSapUserSacRes;

	}

	/**
	 * 회원 정보 조회
	 */
	@Override
	public DetailRes detail(SacRequestHeader sacHeader, DetailReq req) {

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
//		detailRes.setIsChangeSubject(StringUtil.setTrim(schUserRes.getIsChangeSubject()));
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

		ListDeviceRes listDeviceRes = this.listDevice(sacHeader, listDeviceReq);
		if (listDeviceRes.getDeviceInfoList() != null) {
			listDeviceRes.setDeviceInfoList(listDeviceRes.getDeviceInfoList());
		}

		return listDeviceRes;
	}

	@Override
	public ExistRes exist(SacRequestHeader sacHeader, ExistReq req) {
		ExistRes result = new ExistRes();
		DetailReq detailReq = new DetailReq();

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

	@Override
	public ListDeviceRes listDevice(SacRequestHeader requestHeader, ListDeviceReq req) {

		/* 헤더 정보 셋팅 */
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());

		String userKey = req.getUserKey();

		SearchDeviceListRequest schDeviceListReq = new SearchDeviceListRequest();
		schDeviceListReq.setUserKey(userKey);
		schDeviceListReq.setIsMainDevice(req.getIsMainDevice()); // 대표기기만 조회(Y), 모든기기 조회(N)
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch key = new KeySearch();

		ListDeviceRes res = new ListDeviceRes();

		if (StringUtils.isNotBlank(req.getDeviceId())) {
			/* 단건 조회 처리 */
			DeviceInfo deviceInfo = this.srhDevice(requestHeader, MemberConstants.KEY_TYPE_DEVICE_ID,
					req.getDeviceId(), userKey);
			if (deviceInfo != null) {
				res.setUserId(deviceInfo.getUserId());
				res.setUserKey(deviceInfo.getUserKey());
				List<DeviceInfo> deviceInfoList = new ArrayList<DeviceInfo>();
				deviceInfoList.add(deviceInfo);
				res.setDeviceInfoList(deviceInfoList);
			}

			return res;
		} else if (StringUtils.isNotBlank(req.getDeviceKey())) {
			/* 단건 조회 처리 */
			DeviceInfo deviceInfo = this.srhDevice(requestHeader, MemberConstants.KEY_TYPE_INSD_DEVICE_ID,
					req.getDeviceKey(), userKey);
			if (deviceInfo != null) {
				res.setUserId(deviceInfo.getUserId());
				res.setUserKey(deviceInfo.getUserKey());
				List<DeviceInfo> deviceInfoList = new ArrayList<DeviceInfo>();
				deviceInfoList.add(deviceInfo);
				res.setDeviceInfoList(deviceInfoList);
			}
			return res;
		} else if (StringUtils.isNotBlank(req.getUserId())) {
			key.setKeyType(MemberConstants.KEY_TYPE_MBR_ID);
			key.setKeyString(req.getUserId());
		} else if (StringUtils.isNotBlank(req.getUserKey())) {
			key.setKeyType(MemberConstants.KEY_TYPE_INSD_USERMBR_NO);
			key.setKeyString(req.getUserKey());
		}

		keySearchList.add(key);
		schDeviceListReq.setKeySearchList(keySearchList);
		schDeviceListReq.setCommonRequest(commonRequest);

		/* 사용자 휴대기기 목록 조회 */
		SearchDeviceListResponse schDeviceListRes = null;

		try {

			schDeviceListRes = this.deviceSCI.searchDeviceList(schDeviceListReq);
			/* response 셋팅 */
			res.setUserId(schDeviceListRes.getUserID());
			res.setUserKey(schDeviceListRes.getUserKey());

			List<DeviceInfo> deviceInfoList = new ArrayList<DeviceInfo>();
			for (UserMbrDevice userMbrDevice : schDeviceListRes.getUserMbrDevice()) {
				DeviceInfo deviceInfo = DeviceUtil.getConverterDeviceInfo(userMbrDevice);

				/* 폰정보 DB 조회하여 추가 정보 반영 */
				Device device = this.mcc.getPhoneInfo(deviceInfo.getDeviceModelNo());
				if (device != null) {
					/*deviceInfo.setMakeComp(device.getMnftCompCd());
					deviceInfo.setModelNm(device.getModelNm());
					deviceInfo.setVmType(device.getVmTypeCd());*/
				}
				deviceInfoList.add(deviceInfo);
			}
			res.setDeviceInfoList(deviceInfoList);

		} catch (StorePlatformException ex) {
			if (!StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_DATA)) {
				throw ex;
			}
		}

		return res;
	}

	@Override
	public DeviceInfo srhDevice(SacRequestHeader requestHeader, String keyType, String keyString, String userKey) {

		/* 헤더 정보 셋팅 */
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());

		SearchDeviceRequest searchDeviceRequest = new SearchDeviceRequest();
		searchDeviceRequest.setCommonRequest(commonRequest);

		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch key = new KeySearch();
		key.setKeyType(keyType);
		key.setKeyString(keyString);
		keySearchList.add(key);

		searchDeviceRequest.setUserKey(userKey);
		searchDeviceRequest.setKeySearchList(keySearchList);

		DeviceInfo deviceInfo = null;

		try {

			SearchDeviceResponse schDeviceRes = this.deviceSCI.searchDevice(searchDeviceRequest);

			deviceInfo = new DeviceInfo();
			deviceInfo = DeviceUtil.getConverterDeviceInfo(schDeviceRes.getUserMbrDevice());
			deviceInfo.setUserId(schDeviceRes.getUserID());
			deviceInfo.setUserKey(schDeviceRes.getUserKey());

			/* 폰정보 DB 조회하여 추가 정보 반영 */
			Device device = this.mcc.getPhoneInfo(deviceInfo.getDeviceModelNo());
			if (device != null) {
				/*deviceInfo.setMakeComp(device.getMnftCompCd());
				deviceInfo.setModelNm(device.getModelNm());
				deviceInfo.setVmType(device.getVmTypeCd());*/
			}

		} catch (StorePlatformException ex) {
			if (!StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_DATA)) {
				throw ex;
			}
		}

		return deviceInfo;
	}

	@Override
	public Map<String, UserDeviceInfoSac> srhUserByDeviceKey(SacRequestHeader sacHeader, SearchUserDeviceSacReq request) {

		// Request 를 보내기 위한 세팅
		List<UserDeviceKey> userDeviceKeyList = new ArrayList<UserDeviceKey>();

		for (SearchUserDeviceSac schUserDevice : request.getSearchUserDeviceReqList()) {
			UserDeviceKey userDeviceKey = new UserDeviceKey();
			if (StringUtils.isBlank(schUserDevice.getTenantId())) {
				userDeviceKey.setTenantId(MemberConstants.TENANT_ID_TSTORE);
			} else {
				userDeviceKey.setTenantId(schUserDevice.getTenantId());
			}

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
					userDeviceInfoSac.setTenantId(deviceMbrStatus.getTenantID());
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

					if (StringUtils.equals(deviceMbrStatus.getTenantID(), MemberConstants.TENANT_ID_OLLEH_MARKET)
							|| StringUtils.equals(deviceMbrStatus.getTenantID(), MemberConstants.TENANT_ID_UPLUS_STORE)) {
						userDeviceInfoSac.setMarketDeviceKey(deviceMbrStatus.getImMbrNo());
					}
					resMap.put(deviceMbrStatus.getDeviceKey(), userDeviceInfoSac);
				}
			}

		}

		return resMap;
	}

	@Override
	public GetOcbInformationRes getOcbInformation(SacRequestHeader sacHeader, GetOcbInformationReq req) {

		/**
		 * OCB 조회 요청.
		 */
		SearchMemberPointResponse searchMemberPointResponse = this.srhMemberPointList(sacHeader, req.getUserKey());

		/**
		 * 결과 setting.
		 */
		List<OcbInfo> ocbInfoList = new ArrayList<OcbInfo>();
		for (MemberPoint dbOcbInfo : searchMemberPointResponse.getMemberPointList()) {
			OcbInfo ocbInfo = new OcbInfo();
			ocbInfo.setUserKey(dbOcbInfo.getUserKey()); // 사용자 Key
			ocbInfo.setAuthMethodCode(dbOcbInfo.getAuthMethodCode()); // 인증방법 코드
			ocbInfo.setCardNumber(dbOcbInfo.getCardNumber()); // 카드 번호
			ocbInfo.setStartDate(dbOcbInfo.getStartDate()); // 사용시작 일시
			ocbInfo.setEndDate(dbOcbInfo.getEndDate()); // 사용종료 일시
			ocbInfo.setIsUsed(dbOcbInfo.getIsUsed()); // 사용여부 (Y/N)
			ocbInfo.setRegDate(dbOcbInfo.getRegDate()); // 사용여부 (Y/N)
			ocbInfoList.add(ocbInfo);
		}

		GetOcbInformationRes response = new GetOcbInformationRes();
		response.setOcbInfoList(ocbInfoList);

		return response;
	}

	/**
	 * <pre>
	 * 회원정보조회 - userInfo 세팅.
	 * </pre>
	 * 
	 * @param schUserRes
	 *            SearchUserResponse
	 * @return UserInfo
	 */
	private UserInfo userInfo(SearchUserResponse schUserRes) {
		UserInfo userInfo = new UserInfo();

		userInfo.setDeviceCount(StringUtil.setTrim(schUserRes.getDeviceCount()));
		userInfo.setTotalDeviceCount(StringUtil.setTrim(schUserRes.getTotalDeviceCount()));
		userInfo.setIsParent(StringUtil.setTrim(schUserRes.getUserMbr().getIsParent()));
		userInfo.setIsRealName(StringUtil.setTrim(schUserRes.getUserMbr().getIsRealName()));
		userInfo.setIsRecvEmail(StringUtil.setTrim(schUserRes.getUserMbr().getIsRecvEmail()));
		userInfo.setRegDate(StringUtil.setTrim(schUserRes.getUserMbr().getRegDate()));
		userInfo.setSecedeDate(StringUtil.setTrim(schUserRes.getUserMbr().getSecedeDate()));
		userInfo.setSecedeReasonCode(StringUtil.setTrim(schUserRes.getUserMbr().getSecedeReasonCode()));
		userInfo.setSecedeReasonMessage(StringUtil.setTrim(schUserRes.getUserMbr().getSecedeReasonMessage()));
		userInfo.setUserCountry(StringUtil.setTrim(schUserRes.getUserMbr().getUserCountry()));
		userInfo.setUserEmail(StringUtil.setTrim(schUserRes.getUserMbr().getUserEmail()));
		userInfo.setUserId(StringUtil.setTrim(schUserRes.getUserMbr().getUserID()));
		userInfo.setUserKey(StringUtil.setTrim(schUserRes.getUserMbr().getUserKey()));
		userInfo.setUserLanguage(StringUtil.setTrim(schUserRes.getUserMbr().getUserLanguage()));
		userInfo.setUserMainStatus(StringUtil.setTrim(schUserRes.getUserMbr().getUserMainStatus()));
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
				userInfo.setUserSex(StringUtil.setTrim(schUserRes.getUserMbr().getUserSex()));
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

	/**
	 * <pre>
	 * 회원정보조회 - 실명인증정보 세팅.
	 * </pre>
	 * 
	 * @param schUserRes
	 *            SearchUserResponse
	 * @return MbrAuth
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
	 * 회원정보조회 - 법정대리인 세팅.
	 * </pre>
	 * 
	 * @param schUserRes
	 *            SearchUserResponse
	 * @return MbrLglAgent
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
	 * 회원정보조회 - 징계정보 세팅.
	 * </pre>
	 * 
	 * @param schUserRes
	 *            SearchUserResponse
	 * @return UserMbrPnsh
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
	 * 회원정보조회 - 약관정보세팅.
	 * </pre>
	 * 
	 * @param schUserRes
	 *            SearchUserResponse
	 * @return List
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
	 * OCB 정보 조회.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param userKey
	 *            사용자 Key
	 * @return OCB 정보
	 */
	private SearchMemberPointResponse srhMemberPointList(SacRequestHeader sacHeader, String userKey) {

		SearchMemberPointRequest searchMemberPointRequest = new SearchMemberPointRequest();
		searchMemberPointRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));
		searchMemberPointRequest.setUserKey(userKey);

		/**
		 * OCB 조회 요청.
		 */
		SearchMemberPointResponse searchMemberPointResponse = this.userSCI
				.searchMemberPointList(searchMemberPointRequest);
		LOGGER.debug("### searchMemberPointResponse : {}", searchMemberPointResponse.getMemberPointList());

		return searchMemberPointResponse;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.sci.service.SearchUserSCIService #
	 * isOcbJoin(com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader , java.lang.String)
	 */
	@Override
	public boolean isOcbJoinIDP(String imSvcNo) {
		UserInfoIdpSearchServerEcReq idpReq = new UserInfoIdpSearchServerEcReq();
		idpReq.setKey(imSvcNo); // 통합서비스 관리번호
		UserInfoIdpSearchServerEcRes res = this.imIdpSCI.userInfoIdpSearchServer(idpReq);

		if (res.getJoinSstList().indexOf(MemberConstants.SSO_SST_CD_OCB_WEB) > -1) {
			return true;
		}

		return false;
	}

	/**
	 * <pre>
	 * deviceId, orderDt 이용하여 최근 회원정보(탈퇴포함) 조회.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param request
	 *            SearchOrderUserByDeviceIdSacReq
	 * @return SearchOrderUserByDeviceIdSacRes
	 */
	@Override
	public SearchOrderUserByDeviceIdSacRes searchOrderUserByDeviceId(SacRequestHeader header,
			SearchOrderUserByDeviceIdSacReq request) {

		SearchDeviceOwnerRequest searchDeviceOwnerRequest = new SearchDeviceOwnerRequest();
		searchDeviceOwnerRequest.setCommonRequest(this.mcc.getSCCommonRequest(header));
		searchDeviceOwnerRequest.setDeviceID(request.getDeviceId());
		searchDeviceOwnerRequest.setRegDate(request.getOrderDt());

		// SC.DeviceSCI Call
		SearchDeviceOwnerResponse searchDeviceOwnerResponse = this.deviceSCI
				.searchDeviceOwner(searchDeviceOwnerRequest);

		SearchOrderUserByDeviceIdSacRes res = new SearchOrderUserByDeviceIdSacRes();
		res.setUserKey(searchDeviceOwnerResponse.getUserKey());
		res.setDeviceKey(searchDeviceOwnerResponse.getDeviceKey());
		res.setAuthenticationDate(searchDeviceOwnerResponse.getAuthenticationDate());
		res.setAuthYn(searchDeviceOwnerResponse.getIsUsed());
		res.setTableName(searchDeviceOwnerResponse.getTableName());

		return res;
	}

	/**
	 * <pre>
	 * 회원 등급 조회.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            SearchUserGradeSacReq
	 * @return SearchUserGradeSacRes
	 */
	@Override
	public SearchUserGradeSacRes searchUserGrade(SacRequestHeader header, SearchUserGradeSacReq req) {

		SearchExtentUserRequest searchExtentUserRequest = new SearchExtentUserRequest();
		searchExtentUserRequest.setCommonRequest(this.mcc.getSCCommonRequest(header));

		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySchUserKey = new KeySearch();
		keySchUserKey.setKeyType(MemberConstants.KEY_TYPE_INSD_USERMBR_NO);
		keySchUserKey.setKeyString(req.getUserKey());
		keySearchList.add(keySchUserKey);
		searchExtentUserRequest.setKeySearchList(keySearchList);

		searchExtentUserRequest.setGradeInfoYn(MemberConstants.USE_Y);

		// SC Call
		SearchExtentUserResponse searchExtentUserResponse = this.userSCI.searchExtentUser(searchExtentUserRequest);

		SearchUserGradeSacRes res = new SearchUserGradeSacRes();
		res.setUserKey(searchExtentUserResponse.getUserKey());
		GradeInfoSac gradeInfoSac = new GradeInfoSac();
		if (searchExtentUserResponse.getGrade() != null) {
			gradeInfoSac.setUserGradeCd(searchExtentUserResponse.getGrade().getUserGradeCd());
		}
		res.setGradeInfoSac(gradeInfoSac);
		return res;
	}

	/**
	 * <pre>
	 * 회원정보 조회(전시용).
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            SearchUserForDisplaySacReq
	 * @return SearchUserForDisplaySacRes
	 */
	@Override
	public SearchUserSegmentSacRes searchUserSegment(SacRequestHeader header, SearchUserSegmentSacReq req) {

		SearchUserSegmentRequest searchUserSegmentRequest = new SearchUserSegmentRequest();
		searchUserSegmentRequest.setCommonRequest(this.mcc.getSCCommonRequest(header));
		searchUserSegmentRequest.setUserKey(req.getUserKey());
		searchUserSegmentRequest.setDeviceKey(req.getDeviceKey());

		// SC Call
		SearchUserSegmentResponse searchUserSegmentResponse = this.userSCI.searchUserSegment(searchUserSegmentRequest);

		SearchUserSegmentSacRes res = new SearchUserSegmentSacRes();
		// 기변여부
		res.setIsChanged(StringUtils.defaultString(searchUserSegmentResponse.getIsChanged(), null));
		// 가입일
		res.setEntryDay(searchUserSegmentResponse.getEntryDay());
		// 등급
		res.setUserGradeCd(searchUserSegmentResponse.getUserGradeCd());
		// 생년월일
		res.setUserBirthDay(searchUserSegmentResponse.getUserBirthDay());
		// 회원키
		res.setUserKey(searchUserSegmentResponse.getUserKey());
		// 성별
		res.setUserSex(searchUserSegmentResponse.getUserSex());

		return res;
	}

	/**
	 * <pre>
	 * 2.1.11. 회원 부가속성 정보 조회.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            SearchUserExtraInfoSacReq
	 * @return SearchUserExtraInfoSacRes
	 */
	@Override
	public SearchUserExtraInfoSacRes searchUserExtraInfo(SacRequestHeader header, SearchUserExtraInfoSacReq req) {
		SearchUserExtraInfoRequest searchUserExtraInfoRequest = new SearchUserExtraInfoRequest();
		searchUserExtraInfoRequest.setCommonRequest(this.mcc.getSCCommonRequest(header));
		searchUserExtraInfoRequest.setUserKeyList(req.getUserKeyList());
		searchUserExtraInfoRequest.setExtraProfileList(req.getExtraProfileList());

		SearchUserExtraInfoResponse searchUserExtraInfoResponse = this.userSCI
				.searchUserExtraInfo(searchUserExtraInfoRequest);

		Map<String, List<MbrMangItemPtcr>> searchUserExtraInfoResponseMap = searchUserExtraInfoResponse
				.getSearchUserExtraInfoMap();

		// return Map
		Map<String, List<UserExtraInfoSac>> searchUserExtraInfoSacRes = new HashMap<String, List<UserExtraInfoSac>>();
		List<UserExtraInfoSac> userExtraInfoSacList = null;

		for (Entry<String, List<MbrMangItemPtcr>> entry : searchUserExtraInfoResponseMap.entrySet()) {
			userExtraInfoSacList = new ArrayList<UserExtraInfoSac>();
			for (MbrMangItemPtcr mbrMangItemPtcr : entry.getValue()) {
				UserExtraInfoSac userExtraInfoSac = new UserExtraInfoSac();
				userExtraInfoSac.setExtraProfile(mbrMangItemPtcr.getExtraProfile());
				userExtraInfoSac.setExtraProfileValue(mbrMangItemPtcr.getExtraProfileValue());
				userExtraInfoSacList.add(userExtraInfoSac);
			}
			searchUserExtraInfoSacRes.put(entry.getKey(), userExtraInfoSacList);
		}

		// Iterator<String> keys = searchUserExtraInfoResponseMap.keySet().iterator();
		// while (keys.hasNext()) {
		// String key = keys.next();
		// for (MbrMangItemPtcr mangItemPtcr : searchUserExtraInfoResponseMap.get(key)) {
		// userExtraInfoSacList = new ArrayList<UserExtraInfoSac>();
		// for (MbrMangItemPtcr resultMangItemPtcr : searchUserExtraInfoResponseMap.get(key)) {
		// if (StringUtils.equals(key, mangItemPtcr.getUserKey())) {
		// UserExtraInfoSac userExtraInfoSac = new UserExtraInfoSac();
		// userExtraInfoSac.setExtraProfile(resultMangItemPtcr.getExtraProfile());
		// userExtraInfoSac.setExtraProfileValue(resultMangItemPtcr.getExtraProfileValue());
		// userExtraInfoSacList.add(userExtraInfoSac);
		// }
		// }
		// }
		// searchUserExtraInfoSacRes.put(key, userExtraInfoSacList);
		// }

		SearchUserExtraInfoSacRes res = new SearchUserExtraInfoSacRes();
		res.setSearchUserExtraInfoSacRes(searchUserExtraInfoSacRes);
		return res;
	}

	/**
	 * <pre>
	 * 2.1.14. 소셜 계정 등록 회원 정보 조회.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            SearchSocialAccountSacReq
	 * @return SearchSocialAccountSacRes
	 */
	@Override
	public SearchSocialAccountSacRes searchSocialAccount(SacRequestHeader header, SearchSocialAccountSacReq req) {

		CommonRequest commonRequest = this.mcc.getSCCommonRequest(header);

		// 1. userKey로 부가속성 테이블 조회 (구글,페이스북,카카오)
		SearchManagementListRequest searchManagementListRequest = new SearchManagementListRequest();
		searchManagementListRequest.setCommonRequest(commonRequest);
		searchManagementListRequest.setUserKey(req.getUserKey());
		SearchManagementListResponse searchManagementListResponse = null;
		try {
			searchManagementListResponse = this.userSCI.searchManagementList(searchManagementListRequest);
		} catch (StorePlatformException e) {
			if (e.getErrorInfo().getCode().equals(MemberConstants.SC_ERROR_NO_DATA)) {
				throw new StorePlatformException("SAC_MEM_0002", "social 계정");
			} else {
				throw e;
			}
		}

		List<MbrMangItemPtcr> mbrMangItemPtcr = new ArrayList<MbrMangItemPtcr>();
		MbrMangItemPtcr itemPtcr = null;
		String socialAcctIntId = "";
		String socialAcctType = "";
		boolean isSocialData = false;
		if (searchManagementListResponse.getMbrMangItemPtcrList() != null
				&& searchManagementListResponse.getMbrMangItemPtcrList().size() > 0) {
			for (MbrMangItemPtcr mangItemPtcr : searchManagementListResponse.getMbrMangItemPtcrList()) {
				if (StringUtils.equals(MemberConstants.USER_EXTRA_SOCIAL_ACCT_INT_ID, mangItemPtcr.getExtraProfile())) {
					itemPtcr = new MbrMangItemPtcr();
					itemPtcr.setExtraProfile(MemberConstants.USER_EXTRA_SOCIAL_ACCT_INT_ID);
					itemPtcr.setExtraProfileValue(mangItemPtcr.getExtraProfileValue());
					mbrMangItemPtcr.add(itemPtcr);
					socialAcctIntId = mangItemPtcr.getExtraProfileValue();
					isSocialData = true;
				}
				if (StringUtils.equals(MemberConstants.USER_EXTRA_SOCIAL_ACCT_TYPE, mangItemPtcr.getExtraProfile())) {
					itemPtcr = new MbrMangItemPtcr();
					itemPtcr.setExtraProfile(MemberConstants.USER_EXTRA_SOCIAL_ACCT_TYPE);
					itemPtcr.setExtraProfileValue(mangItemPtcr.getExtraProfileValue());
					mbrMangItemPtcr.add(itemPtcr);
					socialAcctType = mangItemPtcr.getExtraProfileValue();
					isSocialData = true;
				}
			}
		}

		if (!isSocialData) {
			throw new StorePlatformException("SAC_MEM_0002", "social 계정");
		}

		// 2. extraProfile 회원키 조회
		SearchManagementRequest searchManagementRequest = new SearchManagementRequest();
		searchManagementRequest.setCommonRequest(commonRequest);
		searchManagementRequest.setMbrMangItemPtcr(mbrMangItemPtcr);
		SearchManagementResponse searchManagementResponse = this.userSCI.searchManagement(searchManagementRequest);

		List<SearchMbrSapUserInfo> searchSapUserInfoList = new ArrayList<SearchMbrSapUserInfo>();
		SearchMbrSapUserInfo searchSapUserInfo = null;
		if (searchManagementResponse.getMbrMangItemPtcrList() != null
				&& searchManagementResponse.getMbrMangItemPtcrList().size() > 0) {
			searchSapUserInfoList = new ArrayList<SearchMbrSapUserInfo>();
			for (MbrMangItemPtcr mangItemPtcr : searchManagementResponse.getMbrMangItemPtcrList()) {
				if (StringUtils.equals(MemberConstants.USER_EXTRA_SOCIAL_ACCT_TYPE, mangItemPtcr.getExtraProfile())
						&& StringUtils.equals(socialAcctType, mangItemPtcr.getExtraProfileValue())) {
					for (MbrMangItemPtcr itemPtcr2 : searchManagementResponse.getMbrMangItemPtcrList()) {
						if (StringUtils.equals(MemberConstants.USER_EXTRA_SOCIAL_ACCT_INT_ID,
								itemPtcr2.getExtraProfile())
								&& StringUtils.equals(socialAcctIntId, itemPtcr2.getExtraProfileValue())) {
							if (StringUtils.equals(mangItemPtcr.getUserKey(), itemPtcr2.getUserKey())) {
								searchSapUserInfo = new SearchMbrSapUserInfo();
								searchSapUserInfo.setUserKey(itemPtcr2.getUserKey());
								searchSapUserInfo.setTenantId(itemPtcr2.getTenantID());
								searchSapUserInfoList.add(searchSapUserInfo);
								break;
							}
						}
					}
				}
			}
		}

		// 3. 테넌트 별로 회원 조회
		SearchMbrSapUserRequest searchMbrSapUserRequest = new SearchMbrSapUserRequest();
		searchMbrSapUserRequest.setUserKeyList(searchSapUserInfoList);
		searchMbrSapUserRequest.setCommonRequest(commonRequest);
		SearchMbrSapUserResponse searchMbrSapUserResponse = this.userSCI.searchMbrSapUser(searchMbrSapUserRequest);

		// 4. 회원 정보 조회 결과 값 응답 설정
		Map<String, UserMbrStatus> userInfoMap = searchMbrSapUserResponse.getUserMbrStatusMap();

		Map<String, SocialUserInfoSac> socialUserInfoMap = null;
		SocialUserInfoSac socialAccountInfo = null;
		if (userInfoMap != null) {
			socialUserInfoMap = new HashMap<String, SocialUserInfoSac>();
			for (int i = 0; i < searchSapUserInfoList.size(); i++) {
				if (userInfoMap.get(searchSapUserInfoList.get(i).getUserKey()) != null) {
					socialAccountInfo = new SocialUserInfoSac();
					socialAccountInfo.setTenantId(userInfoMap.get(searchSapUserInfoList.get(i).getUserKey())
							.getTenantID());
					socialAccountInfo.setUserKey(userInfoMap.get(searchSapUserInfoList.get(i).getUserKey())
							.getUserKey());
					// userId 셋팅
					if (StringUtils.equals(MemberConstants.USER_TYPE_MOBILE,
							userInfoMap.get(searchSapUserInfoList.get(i).getUserKey()).getUserType())) {
						if (userInfoMap.get(searchSapUserInfoList.get(i).getUserKey()).getDeviceIDList() != null
								&& userInfoMap.get(searchSapUserInfoList.get(i).getUserKey()).getDeviceIDList().size() > 0) {
							socialAccountInfo.setUserId(userInfoMap.get(searchSapUserInfoList.get(i).getUserKey())
									.getDeviceIDList().get(0));
						}
					} else {
						socialAccountInfo.setUserId(userInfoMap.get(searchSapUserInfoList.get(i).getUserKey())
								.getUserID());
					}
					socialAccountInfo.setUserType(userInfoMap.get(searchSapUserInfoList.get(i).getUserKey())
							.getUserType());
					socialAccountInfo.setDeviceKeyList(userInfoMap.get(searchSapUserInfoList.get(i).getUserKey())
							.getDeviceKeyList());
					socialUserInfoMap.put(searchSapUserInfoList.get(i).getUserKey(), socialAccountInfo);
				}
			}
		}

		// 회원정보 없는 경우 SC 회원에서 Exception 처리함.
		SearchSocialAccountSacRes searchSocialAccountSacRes = new SearchSocialAccountSacRes();
		searchSocialAccountSacRes.setSocialUserInfo(socialUserInfoMap);

		// 5. 응답 값 설정
		return searchSocialAccountSacRes;
	}
}
