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

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.skplanet.storeplatform.external.client.idp.sci.IdpSCI;
import com.skplanet.storeplatform.external.client.idp.sci.ImIdpSCI;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.UserInfoIdpSearchServerEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.UserInfoIdpSearchServerEcRes;
import com.skplanet.storeplatform.external.client.syrup.sci.SyrupSCI;
import com.skplanet.storeplatform.external.client.syrup.vo.SsoCredentialCreateEcReq;
import com.skplanet.storeplatform.external.client.syrup.vo.SsoCredentialCreateEcRes;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.member.client.common.constant.Constant;
import com.skplanet.storeplatform.member.client.common.vo.*;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSCI;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.*;
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.*;
import com.skplanet.storeplatform.sac.client.member.vo.common.*;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrAuth;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrLglAgent;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserMbrPnsh;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.IndividualPolicyInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.*;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchSocialAccountSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchSocialAccountSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.util.CommonUtils;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.util.DeviceUtil;
import com.skplanet.storeplatform.sac.member.common.util.ValidationCheckUtils;
import com.skplanet.storeplatform.sac.member.common.vo.Device;
import com.skplanet.storeplatform.sac.member.domain.shared.UserClauseAgree;
import com.skplanet.storeplatform.sac.member.domain.shared.UserMember;
import com.skplanet.storeplatform.sac.member.repository.UserClauseAgreeRepository;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

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

	@Autowired
	private SyrupSCI syrupSCI;

	@Autowired
	private UserSCI userSCI;

	@Autowired
	private LimitTargetService limitTargetService;

	@Autowired
	private IdpSCI idpSCI;

	@Autowired
	private ImIdpSCI imIdpSCI;

    @Autowired
    private UserClauseAgreeRepository clauseAgreeRepository;

    @Autowired
    private UserMemberService memberService;

    @Autowired
    private UserExtraInfoService extraInfoService;

	/**
	 * 회원 가입 조회
	 */
	@Override
	public ExistRes exist(SacRequestHeader sacHeader, ExistReq req) {
		ExistRes result = new ExistRes();
		DetailReq detailReq = new DetailReq();

		/** 2. 모번호 조회 (989 일 경우만) */
		if (req.getDeviceId() != null) {
			String opmdMdn = this.mcc.getOpmdMdnInfo(req.getDeviceId());
			req.setDeviceId(opmdMdn);
		}

		/** 3. SC 회원 정보 조회 */
		String userKey = StringUtil.setTrim(req.getUserKey());
		String userId = StringUtil.setTrim(req.getUserId());
		String deviceKey = StringUtil.setTrim(req.getDeviceKey());
		String deviceId = StringUtil.setTrim(req.getDeviceId());
		String mbrNo = StringUtil.setTrim(req.getMbrNo());

		if (!"".equals(userKey)) {
			detailReq.setUserKey(userKey);
		} else if (!"".equals(userId)) {
			detailReq.setUserId(userId);
		} else if (!"".equals(deviceId)) {
			detailReq.setDeviceId(deviceId);
		} else if (!"".equals(deviceKey)) {
			detailReq.setDeviceKey(deviceKey);
		} else if (!"".equals(mbrNo)) {
			detailReq.setMbrNo(mbrNo);
		}
		SearchExtentReq searchExtent = new SearchExtentReq();
		searchExtent.setUserInfoYn(MemberConstants.USE_Y);
		detailReq.setSearchExtent(searchExtent);

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

		/**
		 * 모번호 조회 (989 일 경우만)
		 */
		if (req.getDeviceId() != null) {
			String opmdMdnInfo = this.mcc.getOpmdMdnInfo(req.getDeviceId());
			req.setDeviceId(opmdMdnInfo);
		}

		/* 회원 기본 정보 */
        DetailRes res = null;
        try{
            // TODO 회원정보 v2 로직으로 내부 로직 변환
            res = this.srhUser(req, sacHeader);
        }catch(StorePlatformException ex){
            if (StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_DATA)
                    || StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_USERKEY)) {
                String requestNm = null;
                String requestValue = null;

                if(StringUtils.isNotEmpty(req.getUserId())){
                    requestNm = "userId";
                    requestValue = req.getUserId();
                }else if(StringUtils.isNotEmpty(req.getUserKey())){
                    requestNm = "userKey";
                    requestValue = req.getUserKey();
                }else if(StringUtils.isNotEmpty(req.getDeviceId())) {
                    requestNm = "deviceId";
                    requestValue = req.getDeviceId();
                }else if(StringUtils.isNotEmpty(req.getDeviceKey())){
                    requestNm = "deviceKey";
                    requestValue = req.getDeviceKey();
                }
                throw new StorePlatformException("SAC_MEM_0003", requestNm, requestValue);
            }else{
                throw ex;
            }
        }

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

                    if (listDeviceRes.getDeviceInfoList() != null && listDeviceRes.getDeviceInfoList().size() > 0) {
                        /**
                         * 회원정보 v1 만 db 정보의 device_id, mdn 를 구분하여 device_id로 response return
                         * 1. 단건조회 : device_id OR device_key 조회 시 response 의 device_id를 req의 device_id로 setting 하여 return
                         * 2. 다중조회 : userId OR user_key 는 isMdn 체크 후 구분하여 return
                         */
                        List<DeviceInfo> getDeviceInfoList = new ArrayList<DeviceInfo>();
                        // 1. 단건 조회
                        if (StringUtils.isNotEmpty(req.getDeviceKey()) || StringUtils.isNotEmpty(req.getDeviceId())) {
                            DeviceInfo deviceInfo = new DeviceInfo();
                            deviceInfo = listDeviceRes.getDeviceInfoList().get(0);
                            deviceInfo.setDeviceId(req.getDeviceId());
                            deviceInfo.setMdn(null);
                            getDeviceInfoList.add(deviceInfo);

                        }else {
                            // 2. 다중 조회
                            for(DeviceInfo deviceInfo : listDeviceRes.getDeviceInfoList()){

                                if(StringUtils.isNotEmpty(deviceInfo.getMdn()) && !ValidationCheckUtils.isDeviceId(deviceInfo.getMdn())){
                                    // 유효한 MDN 일경우 deviceId setting
                                    deviceInfo.setDeviceId(deviceInfo.getMdn());
                                    deviceInfo.setMdn(null);

                                }else {
                                    // mdn 초기화
                                    deviceInfo.setMdn(null);
                                }

                                getDeviceInfoList.add(deviceInfo);
                            }
                        }

                        res.setDeviceInfoList(getDeviceInfoList);
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
	 * OneId 정보조회
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param req
	 * @return
	 */
	@Override
	public MbrOneidSacRes srhUserOneId(SacRequestHeader sacHeader, MbrOneidSacReq req) {
		/* 헤더 정보 셋팅 */
        CommonRequest commonRequest = CommonRequest.convert(sacHeader);

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
			scReq.setIsDormant(info.getIsDormant());
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
	 * @param req
	 * @return
	 */
	@Override
	public ListTermsAgreementSacRes listTermsAgreement(SacRequestHeader sacHeader, ListTermsAgreementSacReq req) {

        String userKey = req.getUserKey();

        UserMember member = memberService.findByUserKeyAndTransitRepo(userKey);

        if(member == null)
            throw new StorePlatformException("SC_MEM_9995");

        List<UserClauseAgree> agreeList = clauseAgreeRepository.findByInsdUsermbrNo(userKey);
        List<Agreement> mbrClauseAgreeList = Lists.transform(agreeList, new Function<UserClauseAgree, Agreement>() {
            @Override
            public Agreement apply(UserClauseAgree a) {
                return a.convertToAgreement();
            }
        });

        return new ListTermsAgreementSacRes(userKey, mbrClauseAgreeList);
    }

	/**
	 * ID 찾기
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param req
	 * @return
	 */
	@Override
	public SearchIdSacRes srhId(SacRequestHeader sacHeader, SearchIdSacReq req) {

		/** 1. 헤더 셋팅 */
        // Removed

		List<SearchIdSac> sacList = new ArrayList<SearchIdSac>();

		/** 2. deviceId로 ID 찾기 */
		if (!req.getDeviceId().equals("")) {
			/** 2-1. 모번호 조회 */
			String opmdMdn = this.mcc.getOpmdMdnInfo(req.getDeviceId());
			req.setDeviceId(opmdMdn);
			LOGGER.debug("모번호 조회 getOpmdMdnInfo: {}", opmdMdn);

			/** 2-2. req deviceId keyType 결정 */
			String keyType = MemberConstants.KEY_TYPE_MDN;
			if (ValidationCheckUtils.isDeviceId(opmdMdn)) {
				keyType = MemberConstants.KEY_TYPE_DEVICE_ID;
			}

			/** 2-3. SC 회원 정보 조회 */
			List<KeySearch> keySearchList = new ArrayList<KeySearch>();
			KeySearch keySchUserKey = new KeySearch();
			keySchUserKey.setKeyType(keyType);
			keySchUserKey.setKeyString(req.getDeviceId());
			keySearchList.add(keySchUserKey);

			SearchExtentUserRequest srhExtUserRequest = new SearchExtentUserRequest();
			srhExtUserRequest.setCommonRequest(CommonRequest.convert(sacHeader));
			srhExtUserRequest.setKeySearchList(keySearchList);
			srhExtUserRequest.setUserInfoYn(MemberConstants.USE_Y);

			SearchExtentUserResponse srhExtUserResponse = this.userSCI.searchExtentUser(srhExtUserRequest);

			SearchIdSac sac = new SearchIdSac();
			sac.setUserId(srhExtUserResponse.getUserMbr().getUserID());
			sac.setUserType(srhExtUserResponse.getUserMbr().getUserType());
			sac.setRegDate(srhExtUserResponse.getUserMbr().getRegDate());
			sac.setUserEmail(srhExtUserResponse.getUserMbr().getUserEmail());

			/** 2-4. 모바일 회원이면 오류처리 */
			if (StringUtils.equals(srhExtUserResponse.getUserMbr().getUserType(), MemberConstants.USER_TYPE_MOBILE)) {
				throw new StorePlatformException("SAC_MEM_1300", srhExtUserResponse.getUserMbr().getUserType());
			}

			sacList.add(sac);

		/** 3. email로 ID 찾기 */
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
	 * @param req
	 * @return
	 */
	@Override
	public SearchPasswordSacRes srhPassword(SacRequestHeader sacHeader, SearchPasswordSacReq req) {

		/** 1. 헤더 정보 셋팅 */
        CommonRequest commonRequest = CommonRequest.convert(sacHeader);

		/** 2. 회원 정보 조회. */
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySchUserKey = new KeySearch();
		keySchUserKey.setKeyType(MemberConstants.KEY_TYPE_MBR_ID);
		keySchUserKey.setKeyString(req.getUserId());
		keySearchList.add(keySchUserKey);

		SearchExtentUserRequest srhExtUserReq = new SearchExtentUserRequest();
		srhExtUserReq.setCommonRequest(commonRequest);
		srhExtUserReq.setKeySearchList(keySearchList);
		srhExtUserReq.setUserInfoYn(MemberConstants.USE_Y);

		SearchExtentUserResponse srhExtUserRes = this.userSCI.searchExtentUser(srhExtUserReq);

		/** 3. 가가입 상태일 경우 오류. */
		if (StringUtils.equals(srhExtUserRes.getUserMbr().getUserMainStatus(), MemberConstants.MAIN_STATUS_WATING)) {
			throw new StorePlatformException("SAC_MEM_0003", "userId", srhExtUserRes.getUserMbr().getUserID());
		}

		SearchPasswordSacRes res = new SearchPasswordSacRes();

		/** 4. 모바일, 네이버, 구글, 페이스북 아이디 사용자는 비밀번호 찾기 불가. */
		if(StringUtils.equals(srhExtUserRes.getUserMbr().getUserType(), MemberConstants.USER_TYPE_MOBILE)
				|| StringUtils.equals(srhExtUserRes.getUserMbr().getUserType(), MemberConstants.USER_TYPE_NAVER)
				|| StringUtils.equals(srhExtUserRes.getUserMbr().getUserType(), MemberConstants.USER_TYPE_GOOGLE)
				|| StringUtils.equals(srhExtUserRes.getUserMbr().getUserType(), MemberConstants.USER_TYPE_FACEBOOK)){
			throw new StorePlatformException("SAC_MEM_1304", srhExtUserRes.getUserMbr().getUserType());
		/** 5. 그외의 사용자는 비밀번호찾기 시작.  */
		}else{
			/** 5-1. userId에 userEmail이 있으면 email 발송 */
			if (StringUtils.isNotBlank(srhExtUserRes.getUserMbr().getUserEmail())) {
				res.setSendInfo(StringUtil.setTrim(srhExtUserRes.getUserMbr().getUserEmail()));
				res.setSendMean("01");
			/** 5-2. userId에 userEmail이 없는 경우 */
			} else {
				/** 5-2-1. req의 userPhone이 있는 경우 sms 발송 */
				if (StringUtils.isNotBlank(req.getUserPhone())) {
					res.setSendInfo(StringUtil.setTrim(req.getUserPhone()));
					res.setSendMean("02");
				/** 5-2-2. userId에 userEmail이 없고 req의 userPhone도 없는 경우 */
				} else {
					ListDeviceReq scReq = new ListDeviceReq();
					scReq.setUserKey(srhExtUserRes.getUserMbr().getUserKey());
					scReq.setIsMainDevice(MemberConstants.USE_N);

					ListDeviceRes listDeviceRes = this.deviceService.listDevice(sacHeader, scReq);

					/** 5-2-2-1. 붙어 있는 대표 기기로 sms 전송 */
					if (listDeviceRes.getDeviceInfoList() != null) {
						String mainDeviceMdn = "";
						for (DeviceInfo deviceInfo : listDeviceRes.getDeviceInfoList()) {
							if (StringUtils.equals(deviceInfo.getIsPrimary(), MemberConstants.USE_Y)) {
								mainDeviceMdn = deviceInfo.getMdn();
								break;
							}
						}
						if (StringUtils.isEmpty(mainDeviceMdn)){
							res.setSendInfo("");
							res.setSendMean("03");
						} else {
							res.setSendInfo(mainDeviceMdn);
							res.setSendMean("02");
						}
					/** 5-2-2-2. 붙어 있는 기기가 없으면 발송 하지 않음 */
					} else {
						res.setSendInfo("");
						res.setSendMean("03");
					}
				}
			}

			/** 5-3. 발송하지 않는 경우를 제외 하고 비밀번호 신규 생성후 응답 셋팅. */
			if (!StringUtils.equals(res.getSendMean(),"03")) {
				MbrPwd mbrPwd = new MbrPwd();
				mbrPwd.setMemberKey(srhExtUserRes.getUserMbr().getUserKey());
				mbrPwd.setIsDormant(srhExtUserRes.getUserMbr().getIsDormant());

				ResetPasswordUserRequest scRPUReq = new ResetPasswordUserRequest();
				scRPUReq.setCommonRequest(commonRequest);
				scRPUReq.setMbrPwd(mbrPwd);

				ResetPasswordUserResponse scRPURes = this.userSCI.updateResetPasswordUser(scRPUReq);
				res.setUserPw(scRPURes.getUserPW());
			} else {
				res.setUserPw("");
			}
		}

		return res;
	}

	// userEmail 기반 사용자 조회
	@Override
	public List<SearchIdSac> srhUserEmail(SearchIdSacReq req, SacRequestHeader sacHeader) {

		SearchUserEmailRequest scReq = new SearchUserEmailRequest();
		scReq.setCommonRequest(CommonRequest.convert(sacHeader));
		scReq.setUserEmail(req.getUserEmail());

		SearchUserEmailResponse scRes = this.userSCI.searchUserEmail(scReq);

		List<SearchIdSac> searchIdList = new ArrayList<SearchIdSac>();
		for (UserMbr userMbr : scRes.getUserMbrList()) {

			if (!userMbr.getUserType().equals(MemberConstants.USER_TYPE_MOBILE)) {
				SearchIdSac sac = new SearchIdSac();
				sac.setUserId(StringUtil.setTrim(userMbr.getUserID()));
				sac.setUserType(StringUtil.setTrim(userMbr.getUserType()));
				sac.setRegDate(StringUtil.setTrim(userMbr.getRegDate()));
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

		SearchDeviceOSNumberRequest scReq = new SearchDeviceOSNumberRequest();
		scReq.setCommonRequest(CommonRequest.convert(sacHeader));
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
        String keyType = "";
        String keyValue = "";

        if (StringUtils.isNotBlank(req.getUserKey())) {
            keyType = MemberConstants.KEY_TYPE_INSD_USERMBR_NO;
            keyValue = req.getUserKey();
        } else if (StringUtils.isNotBlank(req.getUserId())) {
            keyType = MemberConstants.KEY_TYPE_MBR_ID;
            keyValue = req.getUserId();
        } else if (StringUtils.isNotBlank(req.getDeviceId())) {

            if(ValidationCheckUtils.isDeviceId(req.getDeviceId())){
                keyType = MemberConstants.KEY_TYPE_DEVICE_ID;
                keyValue = req.getDeviceId();
            }else{
                keyType = MemberConstants.KEY_TYPE_MDN;
                keyValue = req.getDeviceId();
            }

        } else if (StringUtils.isNotBlank(req.getDeviceKey())) {
            keyType = MemberConstants.KEY_TYPE_INSD_DEVICE_ID;
            keyValue = req.getDeviceKey();
        } else if (StringUtils.isNotBlank(req.getMbrNo())) {
            keyType = MemberConstants.KEY_TYPE_USERMBR_NO;
            keyValue = req.getMbrNo();
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
        searchExtentUserRequest.setCommonRequest(CommonRequest.convert(sacHeader));
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

        DetailRes detailRes = new DetailRes();

		/* 기본정보 세팅 */
        detailRes.setPwRegDate(StringUtil.setTrim(schUserRes.getPwRegDate()));
        detailRes.setUserKey(StringUtil.setTrim(schUserRes.getUserKey()));

        // 회원정보 설정
        if (StringUtils.equals(MemberConstants.USE_Y, req.getSearchExtent().getUserInfoYn())) {
            detailRes.setUserInfo(this.userInfo(schUserRes));
        }

        // 약관동의 설정
        if (StringUtils.equals(MemberConstants.USE_Y, req.getSearchExtent().getAgreementInfoYn())) {
            detailRes.setAgreementList(this.listAgreement(schUserRes));
        }

        // 실명인증 설정
        if (StringUtils.equals(MemberConstants.USE_Y, req.getSearchExtent().getMbrAuthInfoYn())) {
            detailRes.setMbrAuth(this.mbrAuth(schUserRes));
        }

        // 법정대리인 설정
        if (StringUtils.equals(MemberConstants.USE_Y, req.getSearchExtent().getMbrLglAgentInfoYn())) {
            detailRes.setMbrLglAgent(this.mbrLglAgent(schUserRes));
        }

        // 징계정보 설정
        if (StringUtils.equals(MemberConstants.USE_Y, req.getSearchExtent().getMbrPnshInfoYn())) {
            detailRes.setUserMbrPnsh(this.mbrPnsh(schUserRes));
        }

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
	private List<Agreement> listAgreement(SearchExtentUserResponse schUserRes) {
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
	private UserMbrPnsh mbrPnsh(SearchExtentUserResponse schUserRes) {
		UserMbrPnsh mbrPnsh = new UserMbrPnsh();
		mbrPnsh.setIsRestricted(StringUtil.setTrim(schUserRes.getUserMbrPnsh().getIsRestricted()));
		mbrPnsh.setRestrictCount(StringUtil.setTrim(schUserRes.getUserMbrPnsh().getRestrictCount()));
		mbrPnsh.setRestrictEndDate(StringUtil.setTrim(schUserRes.getUserMbrPnsh().getRestrictEndDate()));
		mbrPnsh.setRestrictId(StringUtil.setTrim(schUserRes.getUserMbrPnsh().getRestrictID()));
		mbrPnsh.setRestrictOwner(StringUtil.setTrim(schUserRes.getUserMbrPnsh().getRestrictOwner()));
		mbrPnsh.setRestrictRegisterDate(StringUtil.setTrim(schUserRes.getUserMbrPnsh().getRestrictRegisterDate()));
		mbrPnsh.setRestrictStartDate(StringUtil.setTrim(schUserRes.getUserMbrPnsh().getRestrictStartDate()));
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
	private MbrLglAgent mbrLglAgent(SearchExtentUserResponse schUserRes) {
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
	private MbrAuth mbrAuth(SearchExtentUserResponse schUserRes) {
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
	private UserInfo userInfo(SearchExtentUserResponse schUserRes) {
		UserInfo userInfo = new UserInfo();

		userInfo.setDeviceCount(StringUtil.setTrim(schUserRes.getUserMbr().getDeviceCount()));
        userInfo.setTotalDeviceCount(StringUtil.setTrim(schUserRes.getTotalDeviceCount()));
        userInfo.setUserKey(StringUtil.setTrim(schUserRes.getUserMbr().getUserKey()));
        userInfo.setUserType(StringUtil.setTrim(schUserRes.getUserMbr().getUserType()));
        userInfo.setUserMainStatus(StringUtil.setTrim(schUserRes.getUserMbr().getUserMainStatus()));
        userInfo.setUserSubStatus(StringUtil.setTrim(schUserRes.getUserMbr().getUserSubStatus()));
        userInfo.setUserId(StringUtil.setTrim(schUserRes.getUserMbr().getUserID()));
        userInfo.setUserEmail(StringUtil.setTrim(schUserRes.getUserMbr().getUserEmail()));
        userInfo.setIsRecvEmail(StringUtil.setTrim(schUserRes.getUserMbr().getIsRecvEmail()));
        userInfo.setRegDate(StringUtil.setTrim(schUserRes.getUserMbr().getRegDate()));
        userInfo.setSecedeDate(StringUtil.setTrim(schUserRes.getUserMbr().getSecedeDate()));
        userInfo.setSecedeReasonMessage(StringUtil.setTrim(schUserRes.getUserMbr().getSecedeReasonMessage()));
		userInfo.setIsParent(StringUtil.setTrim(schUserRes.getUserMbr().getIsParent()));
		userInfo.setIsRealName(StringUtil.setTrim(schUserRes.getUserMbr().getIsRealName()));
		userInfo.setIsDormant(schUserRes.getUserMbr().getIsDormant());

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

    /* SC API 디바이스 리스트 조회 */
	@Override
	public ListDeviceRes listDevice(DetailReq req, SacRequestHeader sacHeader) {
		ListDeviceReq listDeviceReq = new ListDeviceReq();

		if (StringUtils.isNotEmpty(req.getUserKey())) {
			listDeviceReq.setUserKey(req.getUserKey());
			listDeviceReq.setIsMainDevice(Constant.TYPE_YN_N);

		} else if (StringUtils.isNotEmpty(req.getUserId())) {
			listDeviceReq.setUserKey(req.getUserKey());
			listDeviceReq.setUserId(req.getUserId());
			listDeviceReq.setIsMainDevice(Constant.TYPE_YN_N);

		} else if (StringUtils.isNotEmpty(req.getDeviceKey())) {
			listDeviceReq.setUserKey(req.getUserKey());
			listDeviceReq.setDeviceKey(req.getDeviceKey());
			listDeviceReq.setIsMainDevice(Constant.TYPE_YN_N);

		} else if (StringUtils.isNotEmpty(req.getDeviceId())){
			listDeviceReq.setUserKey(req.getUserKey());
            listDeviceReq.setIsMainDevice(Constant.TYPE_YN_N);

            if(ValidationCheckUtils.isDeviceId(req.getDeviceId())){
                listDeviceReq.setDeviceId(req.getDeviceId());
            }else{
                listDeviceReq.setMdn(req.getDeviceId());
            }
		}

		ListDeviceRes listDeviceRes = this.deviceService.listDevice(sacHeader, listDeviceReq);
		if (listDeviceRes.getDeviceInfoList() != null) {
			listDeviceRes.setDeviceInfoList(listDeviceRes.getDeviceInfoList());
		}

		return listDeviceRes;
	}

	@Override
	public DetailByDeviceIdSacRes detailByDeviceId(SacRequestHeader sacHeader, DetailByDeviceIdSacReq req) {

		/** OPMD 단말 여부, OPMD 모번호 setting. */
		DetailByDeviceIdSacRes response = this.setOmpdInfo(req);

		/** 사용자별 정책 리스트 setting. */
		if (!StringUtils.equals(req.getKey(), "") && req.getPolicyCodeList() != null) {
			response.setPolicyCodeList(this.getIndividualPolicy(sacHeader, req));
		}

		/** 사용자 정보/단말 정보 setting. */
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
			SearchPolicyResponse policyResponse = this.limitTargetService.searchPolicyList(policyRequest);

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

		/** 검색조건 정보 setting. */
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySchUserKey = new KeySearch();
		if (StringUtils.equals(req.getDeviceIdType(), MemberConstants.DEVICE_ID_TYPE_MSISDN)) {
			keySchUserKey.setKeyType(MemberConstants.KEY_TYPE_MDN);
		} else {
			keySchUserKey.setKeyType(MemberConstants.KEY_TYPE_DEVICE_ID);
		}
		keySchUserKey.setKeyString(this.mcc.getOpmdMdnInfo(req.getDeviceId())); // 모번호 조회.
		keySearchList.add(keySchUserKey);

		/** 회원 조회 연동. */
		CheckDuplicationRequest chkDupReq = new CheckDuplicationRequest();
		chkDupReq.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));
		chkDupReq.setKeySearchList(keySearchList);
		CheckDuplicationResponse chkDupRes = this.userSCI.checkDuplication(chkDupReq);
		if (StringUtils.equals(chkDupRes.getIsRegistered(), "N")) {

			LOGGER.info("회원 정보가 존재 하지 않습니다. userKey, deviceKey, deviceTelecom 정보 없이 내려줌.");
			return response;

		}

		/** 사용자 기본 정보 조회. */
		SearchUserResponse userInfo = this.getUserInfo(sacHeader, chkDupRes.getUserMbr().getUserKey());

		/** SC 회원의 등록된 휴대기기 상세정보를 조회 연동. */
		SearchDeviceRequest searchDeviceRequest = new SearchDeviceRequest();
		searchDeviceRequest.setUserKey(userInfo.getUserKey()); // 회원 조회시 내려온 UserKey setting.
		searchDeviceRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));
		searchDeviceRequest.setKeySearchList(keySearchList);
		SearchDeviceResponse searchDeviceResponse = this.deviceSCI.searchDevice(searchDeviceRequest);

		/** 사용자 정보 setting. */
		response.setUserKey(userInfo.getUserKey());
		response.setUserType(userInfo.getUserMbr().getUserType());
		response.setUserId(userInfo.getUserMbr().getUserID());
		response.setIsRealNameYn(userInfo.getUserMbr().getIsRealName());

		/** 실명인증정보 (이름, 생년월일) - 우선순위 (본인 실명인증 생년월일 > DB생년월일 > null). */
		if (StringUtils.equals(userInfo.getUserMbr().getIsRealName(), MemberConstants.USE_Y)) {

			response.setUserName(ObjectUtils.toString(userInfo.getMbrAuth().getName()));
			response.setUserBirthDay(ObjectUtils.toString(userInfo.getMbrAuth().getBirthDay()));

		} else {

			response.setUserName(ObjectUtils.toString(userInfo.getUserMbr().getUserName()));
			response.setUserBirthDay(ObjectUtils.toString(userInfo.getUserMbr().getUserBirthDay()));

		}

		/** 단말 정보 setting. */
		response.setDeviceKey(searchDeviceResponse.getUserMbrDevice().getDeviceKey());
		if (StringUtils.equals(req.getDeviceIdType(), MemberConstants.DEVICE_ID_TYPE_MSISDN)) {
			response.setDeviceId(searchDeviceResponse.getUserMbrDevice().getMdn());
		} else {
			response.setDeviceId(searchDeviceResponse.getUserMbrDevice().getDeviceID());
		}
		response.setModel(searchDeviceResponse.getUserMbrDevice().getDeviceModelNo());
		response.setDeviceTelecom(searchDeviceResponse.getUserMbrDevice().getDeviceTelecom());
		/** 선물수신가능 단말여부 (TB_CM_DEVICE의 GIFT_SPRT_YN). */
		Device cmDevice = this.mcc.getPhoneInfo(searchDeviceResponse.getUserMbrDevice().getDeviceModelNo());
		if (cmDevice != null) {
			response.setGiftYn(cmDevice.getGiftSprtYn());
		} else {
			response.setGiftYn(MemberConstants.USE_N);
		}

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
    @Deprecated
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
//					userInfoSac.setDeviceIdList(userInfoMap.get(userKeyList.get(i)).getDeviceIDList());

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

            if(ValidationCheckUtils.isDeviceId(req.getDeviceId())){
                keyType = MemberConstants.KEY_TYPE_DEVICE_ID;
                keyValue = req.getDeviceId();
            }else{
                keyType = MemberConstants.KEY_TYPE_MDN;
                keyValue = req.getDeviceId();
            }

		} else if (StringUtils.isNotBlank(req.getDeviceKey())) {
			keyType = MemberConstants.KEY_TYPE_INSD_DEVICE_ID;
			keyValue = req.getDeviceKey();
		} else if (StringUtils.isNotBlank(req.getMbrNo())) {
			keyType = MemberConstants.KEY_TYPE_USERMBR_NO;
			keyValue = req.getMbrNo();
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
		detailV2Res.setPwRegDate(StringUtil.setTrim(schUserRes.getPwRegDate()));
		detailV2Res.setUserKey(StringUtil.setTrim(schUserRes.getUserKey()));

		// 회원정보 설정
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
        userInfo.setUserKey(StringUtil.setTrim(schUserRes.getUserMbr().getUserKey()));
        userInfo.setUserType(StringUtil.setTrim(schUserRes.getUserMbr().getUserType()));
        userInfo.setUserMainStatus(StringUtil.setTrim(schUserRes.getUserMbr().getUserMainStatus()));
        userInfo.setUserSubStatus(StringUtil.setTrim(schUserRes.getUserMbr().getUserSubStatus()));
        userInfo.setUserId(StringUtil.setTrim(schUserRes.getUserMbr().getUserID()));
        userInfo.setUserEmail(StringUtil.setTrim(schUserRes.getUserMbr().getUserEmail()));
        userInfo.setIsRecvEmail(StringUtil.setTrim(schUserRes.getUserMbr().getIsRecvEmail()));
        userInfo.setUserUpdEmail(StringUtil.setTrim(schUserRes.getUserMbr().getUserUpdEmail()));
        userInfo.setRegDate(StringUtil.setTrim(schUserRes.getUserMbr().getRegDate()));
        userInfo.setSecedeDate(StringUtil.setTrim(schUserRes.getUserMbr().getSecedeDate()));
        userInfo.setSecedeReasonMessage(StringUtil.setTrim(schUserRes.getUserMbr().getSecedeReasonMessage()));
        userInfo.setIsParent(StringUtil.setTrim(schUserRes.getUserMbr().getIsParent()));
        userInfo.setIsRealName(StringUtil.setTrim(schUserRes.getUserMbr().getIsRealName()));
		userInfo.setIsDormant(schUserRes.getUserMbr().getIsDormant());

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
				userInfo.setUserSex(StringUtil.setTrim(schUserRes.getUserMbr().getUserSex()));
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
				} else { // Default M 으로 설정
					ageChk = ("19".equals(userBirthDay.substring(0, 2))) ? "1" : "3";
				}
				int age = CommonUtils.getAgeBySocalNumber(userBirthDay.substring(2, 8), ageChk);
				// 14세 미만이거나 실명인증 여부 Y 인 경우만 만나이 내려줌.
				if (age < 14 || StringUtils.equals(MemberConstants.USE_Y, schUserRes.getMbrAuth().getIsRealName())) {
					userInfo.setRealAge(String.valueOf(age));
				}
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
        mbrAuth.setIsDomestic(StringUtil.setTrim(schUserRes.getMbrAuth().getIsDomestic()));
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
        mbrLglAgent.setParentRealNameMethod(StringUtil.setTrim(schUserRes.getMbrLglAgent().getParentRealNameMethod()));
        mbrLglAgent.setParentName(StringUtil.setTrim(schUserRes.getMbrLglAgent().getParentName()));
        mbrLglAgent.setParentType(StringUtil.setTrim(schUserRes.getMbrLglAgent().getParentType()));
        mbrLglAgent.setParentDate(StringUtil.setTrim(schUserRes.getMbrLglAgent().getParentDate()));
        mbrLglAgent.setParentEmail(StringUtil.setTrim(schUserRes.getMbrLglAgent().getParentEmail()));
        mbrLglAgent.setParentBirthDay(StringUtil.setTrim(schUserRes.getMbrLglAgent().getParentBirthDay()));
        mbrLglAgent.setParentTelecom(StringUtil.setTrim(schUserRes.getMbrLglAgent().getParentTelecom()));
        mbrLglAgent.setParentMsisdn(StringUtil.setTrim(schUserRes.getMbrLglAgent().getParentMDN()));
        mbrLglAgent.setParentCI(StringUtil.setTrim(schUserRes.getMbrLglAgent().getParentCI()));
        mbrLglAgent.setParentRealNameDate(StringUtil.setTrim(schUserRes.getMbrLglAgent().getParentRealNameDate()));
        mbrLglAgent.setParentRealNameSite(StringUtil.setTrim(schUserRes.getMbrLglAgent().getParentRealNameSite()));
		mbrLglAgent.setMemberKey(StringUtil.setTrim(schUserRes.getMbrLglAgent().getMemberKey()));
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

	/**
	 * <pre>
	 * 2.1.49. 회원 가입 여부 리스트 조회.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            SacRequestHeader
	 * @param req
	 *            ExistListSacReq
	 * @return ExistListSacRes
	 */
	@Override
	public ExistListSacRes existList(SacRequestHeader sacHeader, ExistListSacReq req) {

		ExistListRequest existListRequest = new ExistListRequest();
		existListRequest.setDeviceIdList(req.getDeviceIdList());
		existListRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));

		LOGGER.debug("SAC Request deviceIdSize : {}", req.getDeviceIdList().size());

		ExistListResponse existListResponse = this.userSCI.existList(existListRequest);

		List<DeviceInfo> list = new ArrayList<DeviceInfo>();
		for (UserMbrDevice userMbrDevice : existListResponse.getDeviceIdList()) {
			DeviceInfo deviceInfo = new DeviceInfo();
			deviceInfo.setDeviceId(userMbrDevice.getDeviceID());
			deviceInfo.setUserKey(userMbrDevice.getUserKey());
			list.add(deviceInfo);
		}

		ExistListSacRes res = new ExistListSacRes();
		res.setDeviceIdList(list);

		return res;

	}

	/**
	 * 2.1.56. 가입 테넌트 정보 목록 조회.
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            ListTenantReq
	 * @return ListTenantRes
	 */
	@Override
	public ListTenantRes listTenant(SacRequestHeader requestHeader, ListTenantReq req) {

		/**
		 * ListTenantReq setting
		 */
		ListTenantRequest listTenantRequest = new ListTenantRequest();
		listTenantRequest.setDeviceId(req.getDeviceId());

		/**
		 * SC 휴대기기로 테넌트 리스트를 조회 기능 호출.
		 */
		ListTenantResponse listTenantResponse = this.userSCI.searchTenantList(listTenantRequest);

		List<TenantInfo> tenantList = new ArrayList<TenantInfo>();
		for (String tenantId : listTenantResponse.getTenantList()) {
			TenantInfo tenantInfo = new TenantInfo();
			tenantInfo.setTenantId(tenantId);

			tenantList.add(tenantInfo);
		}

		ListTenantRes res = new ListTenantRes();
		res.setDeviceId(req.getDeviceId());
		res.setTenantList(tenantList);

		return res;
	}

	/**
	 * <pre>
	 * 2.1.58. 소셜 계정 등록 가능 여부 체크.
	 * </pre>
	 * 
	 * @param header
	 *            CheckSocialAccountSacReq
	 * @param req
	 *            CheckSocialAccountSacReq
	 * @return CheckSocialAccountSacRes
	 */
	@Override
	public CheckSocialAccountSacRes checkSocialAccount(SacRequestHeader header, CheckSocialAccountSacReq req) {

		CheckSocialAccountSacRes res = new CheckSocialAccountSacRes();

		CommonRequest commonRequest = this.mcc.getSCCommonRequest(header);

		// Response
		int socialMemberCnt = 0;
		int socialRegCnt = 0;
		String socialRegDate = null;

		try {
			// 1. 회원 부가속성 조회 (Tenant_id 구분없이) : 사이즈 => socialMemberCnt
			SearchManagementRequest searchManagementRequest = new SearchManagementRequest();
			searchManagementRequest.setCommonRequest(commonRequest);
			List<MbrMangItemPtcr> mbrMangItemPtcr = new ArrayList<MbrMangItemPtcr>();
			MbrMangItemPtcr mangItemPtcr = null;
			mangItemPtcr = new MbrMangItemPtcr();
			mangItemPtcr.setExtraProfile(MemberConstants.USER_EXTRA_SOCIAL_ACCT_TYPE);
			mangItemPtcr.setExtraProfileValue(req.getSocialAcctType());
			mbrMangItemPtcr.add(mangItemPtcr);
			mangItemPtcr = new MbrMangItemPtcr();
			mangItemPtcr.setExtraProfile(MemberConstants.USER_EXTRA_SOCIAL_ACCT_INT_ID);
			mangItemPtcr.setExtraProfileValue(req.getSocialAcctIntId());
			mbrMangItemPtcr.add(mangItemPtcr);
			searchManagementRequest.setMbrMangItemPtcr(mbrMangItemPtcr);
			SearchManagementResponse searchManagementResponse = this.userSCI.searchManagement(searchManagementRequest);

			for (MbrMangItemPtcr itemPtcr : searchManagementResponse.getMbrMangItemPtcrList()) {
				if (StringUtils.equals(MemberConstants.USER_EXTRA_SOCIAL_ACCT_TYPE, itemPtcr.getExtraProfile())
						&& StringUtils.equals(req.getSocialAcctType(), itemPtcr.getExtraProfileValue())) {
					for (MbrMangItemPtcr itemPtcr2 : searchManagementResponse.getMbrMangItemPtcrList()) {
						if (StringUtils.equals(MemberConstants.USER_EXTRA_SOCIAL_ACCT_INT_ID,
								itemPtcr2.getExtraProfile())
								&& StringUtils.equals(req.getSocialAcctIntId(), itemPtcr2.getExtraProfileValue())) {
							if (StringUtils.equals(itemPtcr.getUserKey(), itemPtcr2.getUserKey())) {
								socialMemberCnt++;
								break;
							}
						}
					}
				}
			}

		} catch (StorePlatformException e) {
			// 소셜 부가속성이 없을경우 => socialMemberCnt = 0.
			LOGGER.info("회원 부가속성 결과 없음 : socialMemberCnt [{}]", socialMemberCnt);
		}
		try {

			// 2. 소셜이력 테이블 조회 (한달이내 등록) : 사이즈 => socialRegCnt
			SearchSocialAccountRequest searchSocialAccountRequest = new SearchSocialAccountRequest();
			searchSocialAccountRequest.setCommonRequest(commonRequest);
			searchSocialAccountRequest.setUserKey(req.getUserKey());
			SearchSocialAccountResponse searchSocialAccountResponse = this.userSCI
					.searchSocialAccount(searchSocialAccountRequest);
			socialRegCnt = searchSocialAccountResponse.getSocialAccountList().size();

			// 3. 소셜이력 조회값이 2 => 첫번째 등록한 날짜 + 한달 + 1일 응답 => socialRegDate
			if (socialRegCnt > 1) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				Calendar todayCal = DateUtil.getCalendar(searchSocialAccountResponse.getSocialAccountList().get(1)
						.getInsDt());
				todayCal.add(Calendar.MONTH, 1); // +1 한달
				todayCal.add(Calendar.DATE, 1); // +1 하루
				socialRegDate = sdf.format(todayCal.getTime());
			}

		} catch (Exception e) {
			// 소셜계정 등록 이력이 없을경우 => socialRegCnt = 0.
			LOGGER.info("소셜계정 등록 이력 없음 : socialRegCnt [{}]", socialRegCnt);
		}

		// 4. 소셜 계정 등록 가능여부 => regYn
		if (socialMemberCnt < 5 && socialRegCnt < 2) {
			res.setRegYn(MemberConstants.USE_Y);
		} else {
			res.setRegYn(MemberConstants.USE_N);
			if (socialMemberCnt > 4) {
				res.setSocialMemberCnt(String.valueOf(socialMemberCnt));
			}
			if (socialRegCnt > 1) {
				res.setSocialRegCnt(String.valueOf(socialRegCnt));
				res.setSocialRegDate(socialRegDate);
			}
		}

		res.setUserKey(req.getUserKey());
		return res;
	}

	/**
	 * <pre>
	 * 2.1.59. 소셜 계정 등록 회원 리스트.
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
			searchManagementListResponse = new SearchManagementListResponse(); // this.userSCI.searchManagementList(searchManagementListRequest);
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

		List<SearchMbrSapUserInfo> searchSapUserInfoList = null;
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

		// 응답값 설정을 위한 객체 선언.
		DeviceInfo deviceInfo = null;
		List<String> deviceKeyList = null;
		List<DeviceInfo> deviceInfos = null;
		SocialAccountInfo socialAccountInfo = null;
		List<SocialAccountInfo> socialAccountInfos = null;

		if (userInfoMap != null) {
			// 소셜정보 리스트 객체 생성.
			socialAccountInfos = new ArrayList<SocialAccountInfo>();
			for (int i = 0; i < searchSapUserInfoList.size(); i++) {
				if (userInfoMap.get(searchSapUserInfoList.get(i).getUserKey()) != null) {
					// 소셜정보 생성.
					socialAccountInfo = new SocialAccountInfo();
					socialAccountInfo.setTenantId(userInfoMap.get(searchSapUserInfoList.get(i).getUserKey())
							.getTenantID());
					socialAccountInfo.setUserKey(userInfoMap.get(searchSapUserInfoList.get(i).getUserKey())
							.getUserKey());
					// userId 셋팅
					if (StringUtils.equals(MemberConstants.USER_TYPE_MOBILE,
							userInfoMap.get(searchSapUserInfoList.get(i).getUserKey()).getUserType())) {
						if (userInfoMap.get(searchSapUserInfoList.get(i).getUserKey()).getDeviceIDList() != null
								&& userInfoMap.get(searchSapUserInfoList.get(i).getUserKey()).getDeviceIDList().size() > 0) {
//							socialAccountInfo.setUserId(userInfoMap.get(searchSapUserInfoList.get(i).getUserKey())
//									.getDeviceIDList().get(0));
						}
					} else {
						socialAccountInfo.setUserId(userInfoMap.get(searchSapUserInfoList.get(i).getUserKey())
								.getUserID());
					}
					socialAccountInfo.setUserType(userInfoMap.get(searchSapUserInfoList.get(i).getUserKey())
							.getUserType());

					// deviceKey List Get.
					deviceKeyList = userInfoMap.get(searchSapUserInfoList.get(i).getUserKey()).getDeviceKeyList();
					if (deviceKeyList != null && deviceKeyList.size() > 0) {
						// 디바이스정보 리스트 생성.
						deviceInfos = new ArrayList<DeviceInfo>();
						for (String deviceKey : deviceKeyList) {
							// 디바이스 정보 생성.
							deviceInfo = new DeviceInfo();
							deviceInfo.setDeviceKey(deviceKey);
							deviceInfos.add(deviceInfo);
						}
						socialAccountInfo.setDeviceKeyList(deviceInfos);
					}
					socialAccountInfos.add(socialAccountInfo);
				}
			}
		}

		// 5. 응답 설정
		SearchSocialAccountSacRes searchSocialAccountSacRes = new SearchSocialAccountSacRes();
		searchSocialAccountSacRes.setUserList(socialAccountInfos);

		return searchSocialAccountSacRes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.member.user.service.UserSearchService#createSSOCredential(com.skplanet.storeplatform
	 * .sac.common.header.vo.SacRequestHeader,
	 * com.skplanet.storeplatform.sac.client.member.vo.user.CreateSSOCredentialSacReq)
	 */
	@Override
	public CreateSSOCredentialSacRes createSSOCredential(SacRequestHeader header, CreateSSOCredentialSacReq req) {

		/*
		 * 처리 도중 에러가 발생하더라도 Exception을 throw 시키지 않고 ssoCredential "" 값으로 내려준다.
		 */

		CreateSSOCredentialSacRes res = new CreateSSOCredentialSacRes();
		String ssoCredential;

		// ssoCredential 조회
		SearchManagementListRequest searchUserExtraRequest = new SearchManagementListRequest();
		searchUserExtraRequest.setCommonRequest(this.mcc.getSCCommonRequest(header));
		searchUserExtraRequest.setUserKey(req.getUserKey());

        ssoCredential = extraInfoService.getExtraInfoValue(req.getUserKey(), MemberConstants.USER_EXTRA_SYRUP_SSO_CREDENTIAL);

		LOGGER.info("{}, isSsoCredential : {}", req.getUserKey(), ssoCredential);
		if (ssoCredential == null) {
			try {
				// syrup pay ssoCredential 조회 연동
				SsoCredentialCreateEcReq ssoCredentialCreateEcReq = new SsoCredentialCreateEcReq();
				ssoCredentialCreateEcReq.setMctUserId(req.getUserKey());
				SsoCredentialCreateEcRes ssoCredentialCreateEcRes = this.syrupSCI.getSsoCredential(ssoCredentialCreateEcReq);
				ssoCredential = ssoCredentialCreateEcRes.getSsoCredential();

				if (StringUtils.isNotBlank(ssoCredential)) {
					// ssoCredential 저장
					LOGGER.info("{}, ssoCredential 저장 : {}", req.getUserKey(), ssoCredential);
                    extraInfoService.modifyExtraInfo(req.getUserKey(), MemberConstants.USER_EXTRA_SYRUP_SSO_CREDENTIAL, ssoCredential);
				}
			} catch (StorePlatformException e) {
				LOGGER.info("{}, {}, {}", req.getUserKey(), e.getErrorInfo().getCode(), e.getErrorInfo().getMessage());
			}
		}
		res.setSsoCredential(StringUtils.defaultString(ssoCredential, ""));
		return res;
	}

	/**
	 * <pre>
	 * 2.1.64.	배송지 정보 조회.
	 * </pre>
	 *
     * JPA버전으로 대체됨 com.skplanet.storeplatform.sac.member.repository.UserDeliveryRepository
	 * @param sacHeader
	 *            SacRequestHeader
	 * @param req
	 *            SearchDeliveryInfoSacReq
	 * @return SearchDeliveryInfoSacRes
	 */
	@Override
    @Deprecated
	public SearchDeliveryInfoSacRes searchDeliveryInfo(SacRequestHeader sacHeader, SearchDeliveryInfoSacReq req) {

		// 1. 응답 객체 셋팅
		SearchDeliveryInfoSacRes res = new SearchDeliveryInfoSacRes();
		SearchDeliveryInfoResponse scRes = null;
		List<DeliveryInfo> deliveryInfoList = new ArrayList<DeliveryInfo>();

		// 2. header, userKey 셋팅
		SearchDeliveryInfoRequest scReq = new SearchDeliveryInfoRequest();
		scReq.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));
		scReq.setUserKey(req.getUserKey());
		scReq.setDeliveryTypeCd(req.getDeliveryTypeCd());

		// 3. SC 배송지 조회 호출
		try {
			scRes = this.userSCI.searchDeliveryInfo(scReq);

			// 3-1. 조회값이 있으면 res 셋팅
			if (scRes.getSearchDeliveryInfoList() != null && scRes.getSearchDeliveryInfoList().size() > 0) {
				res.setUserKey(req.getUserKey());
				for (SearchDeliveryInfo tempDeliveryInfo : scRes.getSearchDeliveryInfoList()) {
					DeliveryInfo deliveryInfo = new DeliveryInfo();
					deliveryInfo.setDeliveryTypeCd(tempDeliveryInfo.getDeliveryTypeCd());
					deliveryInfo.setDeliveryNm(tempDeliveryInfo.getDeliveryNm());
					deliveryInfo.setReceiverNm(tempDeliveryInfo.getReceiverNm());
					deliveryInfo.setSenderNm(tempDeliveryInfo.getSenderNm());
					deliveryInfo.setZip(tempDeliveryInfo.getZip());
					deliveryInfo.setAddr(tempDeliveryInfo.getAddr());
					deliveryInfo.setDtlAddr(tempDeliveryInfo.getDtlAddr());
					deliveryInfo.setConnTelNo(tempDeliveryInfo.getConnTelNo());
					deliveryInfo.setDeliveryMsg(tempDeliveryInfo.getDeliveryMsg());
					deliveryInfo.setDeliverySeq(tempDeliveryInfo.getDeliverySeq());
					deliveryInfo.setRegDate(tempDeliveryInfo.getRegDate());
					deliveryInfo.setUseDate(tempDeliveryInfo.getUseDate());
					deliveryInfoList.add(deliveryInfo);
				}
				res.setDeliveryInfoList(deliveryInfoList);
			}
		} catch (StorePlatformException e) {
			// 3-2. 조회값이 없으면
			if (e.getErrorInfo().getCode().equals(MemberConstants.SC_ERROR_NO_DATA)) {
				// 3-2-1. userKey변환이력 조회 : 모바일 > ID회원 전환자중 모바일회원의 userKey로 접근할수 있기 때문에 변환 이력에서 조회
				SearchAfterUserKeyRequest scKeyReq = new SearchAfterUserKeyRequest();
				scKeyReq.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));
				scKeyReq.setUserKey(req.getUserKey());

				SearchAfterUserKeyResponse scKeyRes = this.userSCI.searchAfterUserKey(scKeyReq);

				// 3-2-1-1. userKey변환이력이 있으면 변환된 userKey로 SC 배송지 조회 호출후 res 셋팅
				if (scKeyRes != null) {
					scReq.setUserKey(scKeyRes.getUserKey());
					scRes = this.userSCI.searchDeliveryInfo(scReq);

					if (scRes.getSearchDeliveryInfoList() != null && scRes.getSearchDeliveryInfoList().size() > 0) {
						res.setUserKey(scKeyRes.getUserKey());
						for (SearchDeliveryInfo tempDeliveryInfo : scRes.getSearchDeliveryInfoList()) {
							DeliveryInfo deliveryInfo = new DeliveryInfo();
							deliveryInfo.setDeliveryTypeCd(tempDeliveryInfo.getDeliveryTypeCd());
							deliveryInfo.setDeliveryNm(tempDeliveryInfo.getDeliveryNm());
							deliveryInfo.setReceiverNm(tempDeliveryInfo.getReceiverNm());
							deliveryInfo.setSenderNm(tempDeliveryInfo.getSenderNm());
							deliveryInfo.setZip(tempDeliveryInfo.getZip());
							deliveryInfo.setAddr(tempDeliveryInfo.getAddr());
							deliveryInfo.setDtlAddr(tempDeliveryInfo.getDtlAddr());
							deliveryInfo.setConnTelNo(tempDeliveryInfo.getConnTelNo());
							deliveryInfo.setDeliveryMsg(tempDeliveryInfo.getDeliveryMsg());
							deliveryInfo.setDeliverySeq(tempDeliveryInfo.getDeliverySeq());
							deliveryInfo.setRegDate(tempDeliveryInfo.getRegDate());
							deliveryInfo.setUseDate(tempDeliveryInfo.getUseDate());
							deliveryInfoList.add(deliveryInfo);
						}
						res.setDeliveryInfoList(deliveryInfoList);
					}
				}
			}
		}

		return res;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.member.user.service.UserSearchService#searchGiftChargeInfo(com.skplanet.storeplatform
	 * .sac.common.header.vo.SacRequestHeader,
	 * com.skplanet.storeplatform.sac.client.member.vo.user.SearchGiftChargeInfoSacReq)
	 */
	@Override
	public SearchGiftChargeInfoSacRes searchGiftChargeInfo(SacRequestHeader header, SearchGiftChargeInfoSacReq req) {

		SearchGiftChargeInfoRequest searchGiftChargeInfoRequest = new SearchGiftChargeInfoRequest();
		searchGiftChargeInfoRequest.setCommonRequest(this.mcc.getSCCommonRequest(header));
		searchGiftChargeInfoRequest.setUserKey(req.getUserKey());
		SearchGiftChargeInfoResponse searchGiftChargeInfoResponse = this.userSCI
				.searchGiftChargeInfo(searchGiftChargeInfoRequest);

		List<GiftChargeInfoSac> giftChargeInfoSacList = new ArrayList<GiftChargeInfoSac>();
		GiftChargeInfoSac giftChargeInfoSac = null;
		SellerMbrSac sellerMbrSac = null;
		for (GiftChargeInfo giftChargeInfo : searchGiftChargeInfoResponse.getGiftChargeInfoList()) {
			giftChargeInfoSac = new GiftChargeInfoSac();
			giftChargeInfoSac.setBrandName(giftChargeInfo.getBrandNm());
			giftChargeInfoSac.setBrandId(giftChargeInfo.getBrandId());
			giftChargeInfoSac.setChargerId(giftChargeInfo.getChargerId());
			giftChargeInfoSac.setChargerName(giftChargeInfo.getChargerNm());
			giftChargeInfoSac.setRegDate(giftChargeInfo.getRegDt());
			giftChargeInfoSac.setUpdateDate(giftChargeInfo.getUpdDt());

			if (giftChargeInfo.getSellerMbr() != null) {
				sellerMbrSac = new SellerMbrSac();
				sellerMbrSac.setSellerKey(giftChargeInfo.getSellerMbr().getSellerKey());
				sellerMbrSac.setSellerId(giftChargeInfo.getSellerMbr().getSellerID());
				sellerMbrSac.setSellerName(giftChargeInfo.getSellerMbr().getSellerName());
				sellerMbrSac.setCharger(giftChargeInfo.getSellerMbr().getCharger());
				sellerMbrSac.setSellerCompany(giftChargeInfo.getSellerMbr().getSellerCompany());
				sellerMbrSac.setCeoName(giftChargeInfo.getSellerMbr().getCeoName());
				sellerMbrSac.setSellerNickName(giftChargeInfo.getSellerMbr().getSellerNickName());
				giftChargeInfoSac.setSellerInfo(sellerMbrSac);
			}
			giftChargeInfoSacList.add(giftChargeInfoSac);
		}

		SearchGiftChargeInfoSacRes res = new SearchGiftChargeInfoSacRes();
		res.setUserKey(searchGiftChargeInfoResponse.getUserKey());
		res.setGiftChargeInfoList(giftChargeInfoSacList);
		return res;
	}

	/**
	 * <pre>
	 * 2.1.78.	PayPlanet 회원 정보 조회.
	 * </pre>
	 *
	 * @param header SacRequestHeader
	 * @param req    DetailForPayPlanetSacReq
	 * @return DetailForPayPlanetSacRes
	 */
	@Override
	public DetailForPayPlanetSacRes detailForPayPlanet(SacRequestHeader header, DetailForPayPlanetSacReq req) {

		DetailForPayPlanetSacRes res = new DetailForPayPlanetSacRes();

		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySchUserKey = new KeySearch();
		keySchUserKey.setKeyType(MemberConstants.KEY_TYPE_INSD_DEVICE_ID);
		keySchUserKey.setKeyString(req.getDeviceKey());
		keySearchList.add(keySchUserKey);
		SearchExtentUserRequest searchExtentUserRequest = new SearchExtentUserRequest();
		searchExtentUserRequest.setCommonRequest(this.mcc.getSCCommonRequest(header));
		searchExtentUserRequest.setKeySearchList(keySearchList);
		searchExtentUserRequest.setUserInfoYn(MemberConstants.USE_Y);
		searchExtentUserRequest.setAgreementInfoYn(MemberConstants.USE_Y);
		SearchExtentUserResponse schUserRes = null;
		try{
			schUserRes = this.userSCI.searchExtentUser(searchExtentUserRequest);
		}catch(StorePlatformException e) {
			if (StringUtils.equals(e.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_USERKEY)){
				throw new StorePlatformException("SAC_MEM_0003", "deviceKey", req.getDeviceKey());
			}else{
				throw e;
			}

		}
		SearchDeviceRequest searchDeviceRequest = new SearchDeviceRequest();
		searchDeviceRequest.setCommonRequest(this.mcc.getSCCommonRequest(header));
		searchDeviceRequest.setKeySearchList(keySearchList);
		SearchDeviceResponse schDeviceRes = this.deviceSCI.searchDevice(searchDeviceRequest);

		UserInfo userInfo = new UserInfo();
		userInfo.setUserKey(StringUtil.setTrim(schUserRes.getUserMbr().getUserKey()));
		userInfo.setUserId(StringUtil.setTrim(schUserRes.getUserMbr().getUserID()));
		userInfo.setUserType(StringUtil.setTrim(schUserRes.getUserMbr().getUserType()));
		userInfo.setUserEmail(StringUtil.setTrim(schUserRes.getUserMbr().getUserEmail()));
		userInfo.setIsRecvEmail(StringUtil.setTrim(schUserRes.getUserMbr().getIsRecvEmail()));
		userInfo.setIsRealName(StringUtil.setTrim(schUserRes.getUserMbr().getIsRealName()));

		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setDeviceKey(StringUtil.setTrim(schDeviceRes.getUserMbrDevice().getDeviceKey()));
		deviceInfo.setDeviceId(StringUtil.setTrim(schDeviceRes.getUserMbrDevice().getDeviceID()));
		deviceInfo.setMdn(StringUtil.setTrim(schDeviceRes.getUserMbrDevice().getMdn()));
		deviceInfo.setDeviceTelecom(StringUtil.setTrim(schDeviceRes.getUserMbrDevice().getDeviceTelecom()));
		deviceInfo.setDeviceModelNo(StringUtil.setTrim(schDeviceRes.getUserMbrDevice().getDeviceModelNo()));
		deviceInfo.setSvcMangNum(StringUtil.setTrim(schDeviceRes.getUserMbrDevice().getSvcMangNum()));
		deviceInfo.setDeviceAccount(StringUtil.setTrim(schDeviceRes.getUserMbrDevice().getDeviceAccount()));

		res.setDeviceId(schDeviceRes.getUserMbrDevice().getDeviceID());
		res.setMdn(schDeviceRes.getUserMbrDevice().getMdn());
		res.setDeviceTelecom(schDeviceRes.getUserMbrDevice().getDeviceTelecom());
		res.setUserStatus(MemberConstants.MAIN_STATUS_NORMAL);
		res.setUserInfo(userInfo);
		res.setDeviceInfo(deviceInfo);
		res.setAgreementList(this.getListAgreementV2(schUserRes));

		return res;
	}
}
