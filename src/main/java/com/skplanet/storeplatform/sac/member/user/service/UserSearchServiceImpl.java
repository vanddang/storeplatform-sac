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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.idp.vo.IdpReceiverM;
import com.skplanet.storeplatform.external.client.idp.vo.ImIdpReceiverM;
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
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchAgreeSiteRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchAgreeSiteResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchAgreementListRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchAgreementListResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchManagementListRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchManagementListResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserkeyTrackRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserkeyTrackResponse;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.member.vo.common.Agreement;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrAuth;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrLglAgent;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserExtraInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserMbrPnsh;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.IndividualPolicyInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailByDeviceIdSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailByDeviceIdSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ExistReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ExistRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.GetProvisioningHistoryReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.GetProvisioningHistoryRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListTermsAgreementSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListTermsAgreementSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.MbrOneidSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.MbrOneidSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchAgreementRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchIdSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchIdSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchPasswordSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchPasswordSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.UserExtraInfoRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.idp.service.IdpService;
import com.skplanet.storeplatform.sac.member.common.idp.service.ImIdpService;

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
	private IdpService idpService;

	@Autowired
	private MemberCommonComponent memberCommonComponent;

	private IdpReceiverM idpReceiverM;

	@Autowired
	private ImIdpService imIdpService;

	private ImIdpReceiverM imIdpReceiverM;

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

		logger.info("============================================ userInfo Request : {}", detailReq.toString());
		logger.info("============================================ userInfo Response : {}", userInfo.toString());

		result.setUserKey(userInfo.getUserKey());
		result.setUserType(userInfo.getUserType());
		result.setUserId(userInfo.getUserId());
		result.setIsRealName(userInfo.getIsRealName());
		result.setAgencyYn(userInfo.getIsParent());
		result.setUserEmail(userInfo.getUserEmail());
		result.setUserMainStatus(userInfo.getUserMainStatus());
		result.setUserSubStatus(userInfo.getUserSubStatus());

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

		/**
		 * 모번호 조회 (989 일 경우만)
		 */
		if (req.getDeviceId() != null) {
			String opmdMdnInfo = this.mcc.getOpmdMdnInfo(req.getDeviceId());
			req.setDeviceId(opmdMdnInfo);
			logger.info("모번호 조회 getOpmdMdnInfo: {}", opmdMdnInfo);
		}

		/* 회원 기본 정보 */
		this.mcc.getUserBaseInfo("deviceId", req.getDeviceId(), sacHeader);

		SearchUserkeyTrackRequest scReq = new SearchUserkeyTrackRequest();
		SearchUserkeyTrackResponse scRes = new SearchUserkeyTrackResponse();
		GetProvisioningHistoryRes res = new GetProvisioningHistoryRes();

		scReq.setCommonRequest(commonRequest);
		scReq.setDeviceID(req.getDeviceId());
		scRes = this.userSCI.searchUserkeyTrack(scReq);

		res.setWorkdCd("US003206");
		res.setAfterUserKey(StringUtil.setTrim(scRes.getUserkeyTrack().getAfterUserKey()));
		res.setPreUserKey(StringUtil.setTrim(scRes.getUserkeyTrack().getPreUserKey()));

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
			throw new StorePlatformException("SAC_MEM_0002", "userInfo.getImSvcNo()");
		}

		/* OneId 정보조회 */
		SearchAgreeSiteRequest scReq = new SearchAgreeSiteRequest();
		SearchAgreeSiteResponse scRes = new SearchAgreeSiteResponse();
		scReq.setCommonRequest(commonRequest);
		scReq.setImSvcNo(info.getImSvcNo());
		scRes = this.userSCI.searchAgreeSite(scReq);

		/* OneId 데이터 세팅 */
		MbrOneidSacRes res = new MbrOneidSacRes();
		res.setIsCi(StringUtil.setTrim(scRes.getMbrOneID().getIsCi()));
		res.setIsRealName(StringUtil.setTrim(scRes.getMbrOneID().getIsRealName()));
		res.setIsMemberPoint(StringUtil.setTrim(scRes.getMbrOneID().getIsMemberPoint()));

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
		UserInfo info = this.mcc.getUserBaseInfo("deviceId", req.getDeviceId(), sacHeader);

		/* 디바이스 단건 조회 */
		ListDeviceReq scReq = new ListDeviceReq();
		ListDeviceRes scRes = new ListDeviceRes();
		scReq.setUserKey(info.getUserKey());
		scReq.setDeviceId(req.getDeviceId());
		scRes = this.deviceService.listDevice(sacHeader, scReq);
		DeviceInfo deviceInfo = scRes.getDeviceInfoList().get(0);
		SearchIdSacRes res = new SearchIdSacRes();

		if (info.getImSvcNo() == null || info.getImSvcNo().equals("")) {

			if (info.getUserType().equals(MemberConstants.USER_TYPE_MOBILE)) {
				// 무선회원
				throw new StorePlatformException("SAC_MEM_1300", info.getUserType());
			} else if (info.getUserType().equals(MemberConstants.USER_TYPE_IDPID)) {
				// ID 회원
				UserInfo deviceIdSearch = this.mcc.getUserBaseInfo("deviceId", deviceInfo.getDeviceId(), sacHeader);
				res.setUserId(deviceIdSearch.getUserId());
			}
		} else {
			// 통합회원 : 회원연락처와 휴대기기 ID가 일치하는 경우
			if (info.getUserPhone().equals(deviceInfo.getDeviceId())) {
				UserInfo deviceIdSearch = this.mcc.getUserBaseInfo("deviceId", deviceInfo.getDeviceId(), sacHeader);
				res.setUserId(deviceIdSearch.getUserId());
			} else if (!info.getUserPhone().equals(deviceInfo.getDeviceId())) {
				throw new StorePlatformException("SAC_MEM_1301", info.getUserPhone(), deviceInfo.getDeviceId());
			} else if (info.getUserPhone() == null && "".equals(info.getUserPhone())) {
				throw new StorePlatformException("SAC_MEM_0001", "getUserPhone()");
			}
		}

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

		if (info.getImSvcNo() == null || info.getImSvcNo().equals("")) { // IDP 회원
			//			mapUrl.put("cmd", "findPasswd");
			//            mapUrl.put("key_type", "3"); // 사용자 ID
			//            mapUrl.put("key", sMemMbrId);
			//            mapUrl.put("watermark_auth", "2");
		} else { // 통합 IDP 회원
			// 통합ID회원 프로파일 조회
			ImIdpReceiverM profileInfo = this.imIdpService.userInfoIdpSearchServer(info.getImSvcNo());

			//			mapUrl.put("cmd", "TXResetUserPwdIDP");
			//            mapUrl.put("operation_mode", StringUtils.defaultIfEmpty(Config.get("idp.im.operation_mode"), "real"));
			//            mapUrl.put("key_type", "2");
			//            mapUrl.put("key", sMemMbrId);
			//            
			//            mapUrl.put("lang_code", "KOR");
			//            Date dtCur = new Date();
			//            mapUrl.put("modify_req_date", new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(dtCur));
			//            mapUrl.put("modify_req_time", new SimpleDateFormat("HHmmss", Locale.KOREA).format(dtCur));

			if (profileInfo.getResponseBody().getIs_user_tn_auth().equals("Y")) { // 휴대폰 인증
				//				mapUrl.put("user_tn", profileInfo.getResponseBody().getUser_tn());
				//                mapUrl.put("user_tn_nation_cd", "82");
				//                mapUrl.put("user_tn_type", "M");
				//                mapUrl.put("is_user_tn_auth", "Y");
				//                mapUrl.put("is_email_auth", "N");
			} else if (profileInfo.getResponseBody().getIs_email_auth().equals("Y")) { // 이메일 인증
				//				mapUrl.put("is_email_auth", "Y");
				//                mapUrl.put("user_email", profileInfo.getResponseBody().getUser_email());
				//                mapUrl.put("is_user_tn_auth", "N");
			}
		}

		SearchPasswordSacRes res = new SearchPasswordSacRes();
		logger.info("SearchIdSacRes : ", res.toString());

		return res;
	}

	// 사용자 인증정보 세팅
	public MbrAuth mbrAuth(DetailReq req, SacRequestHeader sacHeader) {

		SearchUserRequest searchUserRequest = new SearchUserRequest();
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(sacHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(sacHeader.getTenantHeader().getTenantId());
		searchUserRequest.setCommonRequest(commonRequest);

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

	/* SC API 회원 기본정보 조회 */
	@Override
	public DetailRes searchUserBase(DetailReq req, SacRequestHeader sacHeader) {

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
			// userInfo.setUserAddress(StringUtil.setTrim(schUserRes.getUserMbr().getUserAddress()));
			userInfo.setUserBirthDay(StringUtil.setTrim(schUserRes.getUserMbr().getUserBirthDay()));
			// userInfo.setUserCity(StringUtil.setTrim(schUserRes.getUserMbr().getUserCity()));
			userInfo.setUserCountry(StringUtil.setTrim(schUserRes.getUserMbr().getUserCountry()));
			// userInfo.setUserDetailAddress(StringUtil.setTrim(schUserRes.getUserMbr().getUserDetailAddress()));
			userInfo.setUserEmail(StringUtil.setTrim(schUserRes.getUserMbr().getUserEmail()));
			userInfo.setUserId(StringUtil.setTrim(schUserRes.getUserMbr().getUserID()));
			userInfo.setUserKey(StringUtil.setTrim(schUserRes.getUserMbr().getUserKey()));
			userInfo.setUserLanguage(StringUtil.setTrim(schUserRes.getUserMbr().getUserLanguage()));
			userInfo.setUserMainStatus(StringUtil.setTrim(schUserRes.getUserMbr().getUserMainStatus()));
			userInfo.setUserName(StringUtil.setTrim(schUserRes.getUserMbr().getUserName()));
			userInfo.setUserPhone(StringUtil.setTrim(schUserRes.getUserMbr().getUserPhone()));
			userInfo.setUserPhoneCountry(StringUtil.setTrim(schUserRes.getUserMbr().getUserPhoneCountry()));
			userInfo.setUserSex(StringUtil.setTrim(schUserRes.getUserMbr().getUserSex()));
			// userInfo.setUserState(StringUtil.setTrim(schUserRes.getUserMbr().getUserState()));
			userInfo.setUserSubStatus(StringUtil.setTrim(schUserRes.getUserMbr().getUserSubStatus()));
			userInfo.setUserTelecom(StringUtil.setTrim(schUserRes.getUserMbr().getUserTelecom()));
			userInfo.setUserType(StringUtil.setTrim(schUserRes.getUserMbr().getUserType()));
			// userInfo.setUserZip(StringUtil.setTrim(schUserRes.getUserMbr().getUserZip()));

			if (schUserRes.getMbrMangItemPtcrList() != null) {
				for (MbrMangItemPtcr ptcr : schUserRes.getMbrMangItemPtcrList()) {

					logger.debug("============================================ UserExtraInfo CODE : {}", ptcr.getExtraProfile());
					logger.debug("============================================ UserExtraInfo VALUE : {}", ptcr.getExtraProfileValue());

					UserExtraInfo extra = new UserExtraInfo();
					extra.setExtraProfileCode(StringUtil.setTrim(ptcr.getExtraProfile()));
					extra.setExtraProfileValue(StringUtil.setTrim(ptcr.getExtraProfileValue()));

					listExtraInfo.add(extra);
				}

				userInfo.setUserExtraInfo(listExtraInfo);
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
			extra.setExtraProfileCode(StringUtil.setTrim(ptcr.getExtraProfile()));
			extra.setExtraProfileValue(StringUtil.setTrim(ptcr.getExtraProfileValue()));

			listExtraInfo.add(extra);

			extraRes.setAddInfoList(listExtraInfo);

		}
		return extraRes;

	}

	/* SC API 디바이스 리스트 조회 */
	@Override
	public ListDeviceRes listDevice(DetailReq req, SacRequestHeader sacHeader) {
		ListDeviceReq listDeviceReq = new ListDeviceReq();

		ExistReq existReq = new ExistReq();
		ExistRes existRes = new ExistRes();

		if (req.getUserKey() != null) {
			listDeviceReq.setUserKey(req.getUserKey());
			listDeviceReq.setIsMainDevice("N");
		} else if (req.getUserId() != null) {
			existReq.setUserId(req.getUserId());
			existRes = this.exist(sacHeader, existReq);

			listDeviceReq.setUserKey(existRes.getUserKey());
			listDeviceReq.setUserId(req.getUserId());
			listDeviceReq.setIsMainDevice("N");
		} else if (req.getDeviceKey() != null) {
			existReq.setDeviceKey(req.getDeviceKey());
			existRes = this.exist(sacHeader, existReq);

			listDeviceReq.setUserKey(existRes.getUserKey());
			listDeviceReq.setDeviceKey(req.getDeviceKey());
			listDeviceReq.setIsMainDevice("N");
		} else if (req.getDeviceId() != null) {
			existReq.setDeviceId(req.getDeviceId());
			existRes = this.exist(sacHeader, existReq);

			listDeviceReq.setUserKey(existRes.getUserKey());
			listDeviceReq.setDeviceId(req.getDeviceId());
			listDeviceReq.setIsMainDevice("N");
		}

		ListDeviceRes listDeviceRes = null;
		try {
			listDeviceRes = this.deviceService.listDevice(sacHeader, listDeviceReq);
			listDeviceRes.setDeviceInfoList(listDeviceRes.getDeviceInfoList());
		} catch (StorePlatformException ex) {
			if (!ex.getErrorInfo().getCode().equals(MemberConstants.SC_ERROR_NO_DATA)) {
				throw ex;
			}
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
		 * 단말 정보 setting.
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
		 * SC 회원의 등록된 휴대기기 상세정보를 조회 연동.
		 */
		SearchDeviceRequest searchDeviceRequest = new SearchDeviceRequest();
		searchDeviceRequest.setUserKey(chkDupRes.getUserMbr().getUserKey()); // 회원 조회시 내려온 UserKey setting.
		searchDeviceRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));
		searchDeviceRequest.setKeySearchList(keySearchList);
		SearchDeviceResponse searchDeviceResponse = this.deviceSCI.searchDevice(searchDeviceRequest);

		/**
		 * 사용자 정보 setting.
		 */
		response.setUserKey(chkDupRes.getUserMbr().getUserKey());
		response.setUserType(chkDupRes.getUserMbr().getUserType());
		response.setUserName(ObjectUtils.toString(chkDupRes.getUserMbr().getUserName()));
		response.setUserId(chkDupRes.getUserMbr().getUserID());
		response.setIsRealNameYn(chkDupRes.getUserMbr().getIsRealName());

		/**
		 * 우선순위 (실명인증 생년월일 > DB생년월일 > null)
		 */
		String userBirthday = this.getUserBirthday(sacHeader, chkDupRes.getUserMbr().getUserKey());
		if (StringUtil.equals(userBirthday, "") || userBirthday == null) {
			response.setUserBirthDay(ObjectUtils.toString(chkDupRes.getUserMbr().getUserBirthDay()));
		} else {
			response.setUserBirthDay(userBirthday);
		}

		/**
		 * 휴대기기 정보 setting.
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
	 * 본인 실명인증 생년월일을 조회한다.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param userKey
	 *            사용자 Key
	 * @return String (실명인증 생년월일)
	 */
	private String getUserBirthday(SacRequestHeader sacHeader, String userKey) {

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
		 * 회원 정보조회 (실명 인증정보)
		 */
		SearchUserResponse schUserRes = this.userSCI.searchUser(searchUserRequest);

		return schUserRes.getMbrAuth().getBirthDay();
	}

}
