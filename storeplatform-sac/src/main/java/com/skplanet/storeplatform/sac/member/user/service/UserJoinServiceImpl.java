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
import java.util.Random;

import com.skplanet.storeplatform.member.client.common.vo.*;
import com.skplanet.storeplatform.member.client.user.sci.vo.*;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceTelecomInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.*;
import com.skplanet.storeplatform.sac.member.common.util.ValidationCheckUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.csp.vo.GetCustomerEcRes;
import com.skplanet.storeplatform.external.client.csp.vo.GetMvnoEcRes;
import com.skplanet.storeplatform.external.client.idp.sci.IdpSCI;
import com.skplanet.storeplatform.external.client.idp.sci.ImIdpSCI;
import com.skplanet.storeplatform.external.client.idp.vo.CheckDupIdEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.JoinForWapEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.JoinForWapEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.SimpleJoinEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.SimpleJoinEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.AgreeUserEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.AgreeUserEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.UserInfoIdpSearchServerEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.UserInfoIdpSearchServerEcRes;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.client.member.vo.common.AgreementInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceExtraInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.MajorDeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserInfo;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;

/**
 * 회원 가입 서비스 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013. 12. 31. Updated by : 심대진, 다모아 솔루션.
 */
@Service
public class UserJoinServiceImpl implements UserJoinService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserJoinServiceImpl.class);

	@Autowired
	private MemberCommonComponent mcc;

	@Autowired
	private UserSCI userSCI;

	@Autowired
	private IdpSCI idpSCI;

	@Autowired
	private ImIdpSCI imIdpSCI;

	@Autowired
	private DeviceService deviceService;

	@Override
	public CreateByMdnRes regByMdn(SacRequestHeader sacHeader, CreateByMdnReq req) {

        /**
         * MDN 회원 기가입 체크
         */
        try {
            String keyType = null;
            if (StringUtils.equals(req.getDeviceIdType(), MemberConstants.DEVICE_ID_TYPE_MSISDN)) {
                /**
                 * 모번호 조회 (989 일 경우만)
                 */
                req.setDeviceId(this.mcc.getOpmdMdnInfo(req.getDeviceId()));
                keyType = "mdn";
            } else {
                keyType = "deviceId";
            }

            UserInfo userInfo = this.mcc.getUserBaseInfo(keyType, req.getDeviceId(), sacHeader);
            if (userInfo != null) {
                throw new StorePlatformException("SAC_MEM_1101", req.getDeviceIdType());
            }
        } catch (StorePlatformException ex) {
            if (StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_DATA)
                    || StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_USERKEY)) {
                // SKIP
            }else{
                throw ex;
            }
        }

        /**
         * 통신사 / MVNO 체크
         */
        if (StringUtils.equals(req.getDeviceIdType(), MemberConstants.DEVICE_ID_TYPE_MSISDN)) {

            // 01. MVNO 처리
            if(StringUtils.equals(req.getDeviceTelecom(), MemberConstants.DEVICE_TELECOM_SKT)
                    || StringUtils.equals(req.getDeviceTelecom(), MemberConstants.DEVICE_TELECOM_KT)
                    || StringUtils.equals(req.getDeviceTelecom(), MemberConstants.DEVICE_TELECOM_LGT)){

                // TODO [EC] MVNO 연동 처리 작업 필요
                String isMvno = MemberConstants.USE_N;
                String deviceKey = "";

                if(System.getProperty("spring.profiles.active", "local").equals("local")) {
                    if(req.getDeviceId().contains("+")){
                        isMvno = MemberConstants.USE_Y;
                    }
                }else {
                    // TODO [EC] MVNO 조회 연동

                }

                LOGGER.info("{} 휴대기기 MVNO 단말 여부 : {}", req.getDeviceId(), isMvno);
                if(StringUtils.equals(isMvno, MemberConstants.USE_Y)){
                    // 02-01. SKM 만 가입 처리
                    if(StringUtils.equals(req.getDeviceTelecom(), MemberConstants.DEVICE_TELECOM_SKT)){
                        // 통신사 SKM 설정
                        req.setDeviceTelecom(MemberConstants.DEVICE_TELECOM_SKM);
                    }else{
                        LOGGER.info("{} 휴대기기 타사 MVNO로 가입 실패 처리", req.getDeviceId());
                        throw new StorePlatformException("SAC_MEM_1203");
                    }
                }
            }
        }

		/**
		 * 법정대리인 나이 유효성 체크.
		 */
		if (StringUtils.equals(req.getIsParent(), MemberConstants.USE_Y)) {

            if (StringUtils.isBlank(req.getOwnBirth())) {
                throw new StorePlatformException("SAC_MEM_0002", "ownBirth");
            }

            if (StringUtils.isBlank(req.getParentBirthDay())) {
                throw new StorePlatformException("SAC_MEM_0002", "parentBirthDay");
            }

            this.mcc.checkParentBirth(req.getOwnBirth(), req.getParentBirthDay());
        }

		/**
		 * 약관 맵핑정보 세팅.
		 */
		List<AgreementInfo> agreementInfoList = this.mcc.getClauseMappingInfo(req.getAgreementList());

		CreateUserRequest createUserRequest = new CreateUserRequest();

		/**
		 * 공통 정보 setting
		 */
		createUserRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));

		/**
		 * 이용약관 정보 setting
		 */
		createUserRequest.setMbrClauseAgreeList(this.getAgreementInfo(agreementInfoList));

		/**
		 * 법정대리인 setting.
		 */
		createUserRequest.setMbrLglAgent(this.getMbrLglAgent(sacHeader, req));

		/**
		 * SC 사용자 기본정보 setting
		 */
		UserMbr userMbr = new UserMbr();
		userMbr.setUserBirthDay(req.getOwnBirth()); // 사용자 생년월일
		userMbr.setIsRealName(MemberConstants.USE_N); // 실명인증 여부
		userMbr.setUserType(MemberConstants.USER_TYPE_MOBILE); // 모바일 회원
		userMbr.setUserMainStatus(MemberConstants.MAIN_STATUS_NORMAL); // 정상
		userMbr.setUserSubStatus(MemberConstants.SUB_STATUS_NORMAL); // 정상
		userMbr.setIsRecvEmail(MemberConstants.USE_N); // 이메일 수신 여부
		userMbr.setUserID(req.getDeviceId()); // 회원 컴포넌트에서 새로운 MBR_ID 를 생성하여 넣는다.
		userMbr.setIsParent(req.getIsParent()); // 부모동의 여부
		userMbr.setRegDate(DateUtil.getToday("yyyyMMddHHmmss")); // 등록일시
		createUserRequest.setUserMbr(userMbr);

		/**
		 * SC 사용자 가입요청
		 */
		CreateUserResponse createUserResponse = this.userSCI.create(createUserRequest);
		if (createUserResponse.getUserKey() == null || StringUtils.equals(createUserResponse.getUserKey(), "")) {
			throw new StorePlatformException("SAC_MEM_0002", "userKey");
		}

        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setUserKey(createUserResponse.getUserKey());
        deviceInfo.setDeviceIdType(req.getDeviceIdType()); // 기기 ID 타입

        if(StringUtils.equals(req.getDeviceIdType(), MemberConstants.DEVICE_ID_TYPE_MSISDN)){
            deviceInfo.setDeviceId("");
            deviceInfo.setMdn(req.getDeviceId()); // MDN 번호
        }else {
            deviceInfo.setDeviceId(req.getDeviceId()); // 기기 ID
        }
        deviceInfo.setJoinId(req.getJoinId()); // 가입 채널 코드
        deviceInfo.setDeviceTelecom(req.getDeviceTelecom()); // 이동 통신사
        deviceInfo.setDeviceAccount(req.getDeviceAccount()); // 기기 계정 (Gmail)
        deviceInfo.setNativeId(req.getNativeId()); // 기기 IMEI
        deviceInfo.setIsRecvSms(req.getIsRecvSms()); // SMS 수신 여부
        deviceInfo.setIsPrimary(MemberConstants.USE_Y); // 대표폰 여부
        deviceInfo.setDeviceExtraInfoList(req.getDeviceExtraInfoList()); // 단말부가정보

        String deviceKey = null;
        try{
            deviceKey = this.deviceService.regDeviceInfo(sacHeader, deviceInfo);
        }catch(StorePlatformException e){
            // 휴대기기 등록 실패하면 회원정보 롤백처리(delete)
            DeleteUserRequest deleteUserRequest = new DeleteUserRequest();
            deleteUserRequest.setCommonRequest(mcc.getSCCommonRequest(sacHeader));
            deleteUserRequest.setUserKey(createUserResponse.getUserKey());
            userSCI.delete(deleteUserRequest);
            throw e;
        }

		/**
		 * 결과 세팅
		 */
		CreateByMdnRes response = new CreateByMdnRes();
		response.setUserKey(createUserResponse.getUserKey());
		response.setDeviceKey(deviceKey);
		return response;

	}

	@Override
	public CreateByAgreementRes regByAgreementId(SacRequestHeader sacHeader, CreateByAgreementReq req) {

		/**
		 * 약관 맵핑정보 세팅.
		 */
		List<AgreementInfo> agreementInfoList = this.mcc.getClauseMappingInfo(req.getAgreementList());

		/**
		 * (통합 IDP 연동) 이용동의 가입 (cmd = TXAgreeUserIDP)
		 */
		AgreeUserEcReq agreeUserEcReq = new AgreeUserEcReq();
		agreeUserEcReq.setKey(req.getUserId());
		agreeUserEcReq.setKeyType("2"); // 1=IM통합서비스번호, 2=IM통합ID
		agreeUserEcReq.setJoinSstList(MemberConstants.SSO_SST_CD_TSTORE + ","
				+ this.mcc.getClauseMappingForOneId(req.getAgreementList()) + "," + DateUtil.getToday() + ","
				+ DateUtil.getTime());
		agreeUserEcReq.setOcbJoinCode(MemberConstants.USE_N); // 통합포인트 가입 여부 Y=가입, N=미가입
		agreeUserEcReq.setModifyReqDate(DateUtil.getToday("yyyyMMdd"));
		agreeUserEcReq.setModifyReqTime(DateUtil.getToday("hhmmss"));
		AgreeUserEcRes agreeUserEcRes = this.imIdpSCI.agreeUser(agreeUserEcReq);

		LOGGER.info("joinSstList : {}", agreeUserEcRes.getJoinSstList());

		CreateUserRequest createUserRequest = new CreateUserRequest();

		/**
		 * 공통 정보 setting
		 */
		createUserRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));

		/**
		 * 이용약관 정보 setting
		 */
		createUserRequest.setMbrClauseAgreeList(this.getAgreementInfo(agreementInfoList));

		/**
		 * 통합 ID 기본 프로파일 조회 (통합ID 회원) 프로파일 조회 - 이름, 생년월일 (cmd = findCommonProfileForServerIDP)
		 */
		UserInfoIdpSearchServerEcReq userInfoIdpSearchServerEcReq = new UserInfoIdpSearchServerEcReq();
		userInfoIdpSearchServerEcReq.setKey(agreeUserEcRes.getImIntSvcNo()); // 통합 서비스 관리번호
		UserInfoIdpSearchServerEcRes userInfoIdpSearchServerEcRes = this.imIdpSCI
				.userInfoIdpSearchServer(userInfoIdpSearchServerEcReq);

		/**
		 * SC 사용자 기본정보 setting
		 */
		UserMbr userMbr = new UserMbr();
		userMbr.setUserID(req.getUserId()); // 사용자 아이디
		userMbr.setUserEmail(agreeUserEcRes.getUserEmail()); // 사용자 이메일
		userMbr.setUserName(userInfoIdpSearchServerEcRes.getUserName()); // 사용자 이름
		userMbr.setUserBirthDay(userInfoIdpSearchServerEcRes.getUserBirthday()); // 사용자 생년월일
		userMbr.setIsRealName(MemberConstants.USE_N); // 실명인증 여부
		userMbr.setUserType(MemberConstants.USER_TYPE_ONEID); // One ID 회원
		userMbr.setUserMainStatus(MemberConstants.MAIN_STATUS_NORMAL); // 정상
		userMbr.setUserSubStatus(MemberConstants.SUB_STATUS_NORMAL); // 정상
		userMbr.setIsRecvEmail(MemberConstants.USE_N); // 이메일 수신 여부 (AI-IS 로직 반영).
		userMbr.setIsRecvSMS(req.getIsRecvSms()); // SMS 수신 여부
		userMbr.setIsParent(MemberConstants.USE_N); // 부모동의 여부 (AI-IS 로직 반영).
		userMbr.setRegDate(DateUtil.getToday("yyyyMMddHHmmss")); // 등록 일시
		createUserRequest.setUserMbr(userMbr);

		/**
		 * SC 사용자 가입요청
		 */
		CreateUserResponse createUserResponse = this.userSCI.create(createUserRequest);
		if (createUserResponse.getUserKey() == null || StringUtils.equals(createUserResponse.getUserKey(), "")) {
			throw new StorePlatformException("SAC_MEM_0002", "userKey");
		}

		/**
		 * 결과 세팅
		 */
		CreateByAgreementRes response = new CreateByAgreementRes();
		response.setUserKey(createUserResponse.getUserKey());

		return response;

	}

	@Override
	public CreateByAgreementRes regByAgreementDevice(SacRequestHeader sacHeader, CreateByAgreementReq req) {

		/**
		 * 모번호 조회 (989 일 경우만)
		 */
		req.setDeviceId(this.mcc.getOpmdMdnInfo(req.getDeviceId()));

		/**
		 * 단말등록시 필요한 기본 정보 세팅.
		 */
		MajorDeviceInfo majorDeviceInfo = this.mcc.getDeviceBaseInfo(sacHeader.getDeviceHeader().getModel(),
				req.getDeviceTelecom(), req.getDeviceId(), req.getDeviceIdType(), true);

		/**
		 * 약관 맵핑정보 세팅.
		 */
		List<AgreementInfo> agreementInfoList = this.mcc.getClauseMappingInfo(req.getAgreementList());

		/**
		 * 통합 IDP 연동을 위한.... Phone 정보 세팅.
		 */
		StringBuffer sbUserPhone = new StringBuffer();
		sbUserPhone.append(req.getDeviceId());
		sbUserPhone.append(",");
		sbUserPhone.append(ObjectUtils.toString(majorDeviceInfo.getSvcMangNum()));
		sbUserPhone.append(",");
		sbUserPhone.append(ObjectUtils.toString(majorDeviceInfo.getUacd()));
		sbUserPhone.append(",");
		sbUserPhone.append(this.mcc.convertDeviceTelecom(req.getDeviceTelecom()));
		LOGGER.debug("## sbUserPhone : {}", sbUserPhone.toString());

		/**
		 * (통합 IDP) 이용동의 가입 요청 (cmd = TXAgreeUserIDP)
		 */
		AgreeUserEcReq agreeUserEcReq = new AgreeUserEcReq();
		agreeUserEcReq.setKey(req.getUserId());
		agreeUserEcReq.setKeyType("2"); // 1=IM통합서비스번호, 2=IM통합ID
		agreeUserEcReq.setUserMdn(sbUserPhone.toString());
		agreeUserEcReq.setJoinSstList(MemberConstants.SSO_SST_CD_TSTORE + ","
				+ this.mcc.getClauseMappingForOneId(req.getAgreementList()) + "," + DateUtil.getToday() + ","
				+ DateUtil.getTime());
		agreeUserEcReq.setOcbJoinCode(MemberConstants.USE_N); // 통합포인트 가입 여부 Y=가입, N=미가입
		agreeUserEcReq.setModifyReqDate(DateUtil.getToday("yyyyMMdd"));
		agreeUserEcReq.setModifyReqTime(DateUtil.getToday("hhmmss"));
		AgreeUserEcRes agreeUserEcRes = this.imIdpSCI.agreeUser(agreeUserEcReq);

		LOGGER.info("joinSstList : {}", agreeUserEcRes.getJoinSstList());

		CreateUserRequest createUserRequest = new CreateUserRequest();

		/**
		 * 공통 정보 setting
		 */
		createUserRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));

		/**
		 * 이용약관 정보 setting
		 */
		createUserRequest.setMbrClauseAgreeList(this.getAgreementInfo(agreementInfoList));

		/**
		 * 통합 ID 기본 프로파일 조회 (통합ID 회원) 프로파일 조회 - 이름, 생년월일 (cmd = findCommonProfileForServerIDP)
		 */
		UserInfoIdpSearchServerEcReq userInfoIdpSearchServerEcReq = new UserInfoIdpSearchServerEcReq();
		userInfoIdpSearchServerEcReq.setKey(agreeUserEcRes.getImIntSvcNo()); // 통합 서비스 관리번호
		UserInfoIdpSearchServerEcRes userInfoIdpSearchServerEcRes = this.imIdpSCI
				.userInfoIdpSearchServer(userInfoIdpSearchServerEcReq);

		/**
		 * SC 사용자 기본정보 setting
		 */
		UserMbr userMbr = new UserMbr();
		userMbr.setUserID(req.getUserId()); // 사용자 아이디
		userMbr.setUserEmail(agreeUserEcRes.getUserEmail()); // 사용자 이메일
		userMbr.setUserName(userInfoIdpSearchServerEcRes.getUserName()); // 사용자 이름
		userMbr.setUserBirthDay(userInfoIdpSearchServerEcRes.getUserBirthday()); // 사용자 생년월일
		userMbr.setIsRealName(MemberConstants.USE_N); // 실명인증 여부
		userMbr.setUserType(MemberConstants.USER_TYPE_ONEID); // One ID 회원
		userMbr.setUserMainStatus(MemberConstants.MAIN_STATUS_NORMAL); // 정상
		userMbr.setUserSubStatus(MemberConstants.SUB_STATUS_NORMAL); // 정상
		userMbr.setIsRecvEmail(MemberConstants.USE_N); // 이메일 수신 여부 (AI-IS 로직 반영).
		userMbr.setIsRecvSMS(req.getIsRecvSms()); // SMS 수신 여부
		userMbr.setIsParent(MemberConstants.USE_N); // 부모동의 여부 (AI-IS 로직 반영).
		userMbr.setRegDate(DateUtil.getToday("yyyyMMddHHmmss")); // 등록 일시
		createUserRequest.setUserMbr(userMbr);

		/**
		 * SC 사용자 가입요청
		 */
		CreateUserResponse createUserResponse = this.userSCI.create(createUserRequest);
		if (createUserResponse.getUserKey() == null || StringUtils.equals(createUserResponse.getUserKey(), "")) {
			throw new StorePlatformException("SAC_MEM_0002", "userKey");
		}

		/**
		 * 휴대기기 등록.
		 */
		String deviceKey = this.regDeviceSubmodule(req, sacHeader, createUserResponse.getUserKey(), majorDeviceInfo);

		/**
		 * 결과 세팅
		 */
		CreateByAgreementRes response = new CreateByAgreementRes();
		response.setUserKey(createUserResponse.getUserKey());
		response.setDeviceKey(deviceKey);

		return response;

	}

	@Override
	public CreateBySimpleRes regBySimpleId(SacRequestHeader sacHeader, CreateBySimpleReq req) {

		/**
		 * IDP 중복 아이디 체크. (cmd = duplicateIDCheck)
		 */
		CheckDupIdEcReq checkDupIdEcReq = new CheckDupIdEcReq();
		checkDupIdEcReq.setUserId(req.getUserId());
		this.idpSCI.checkDupId(checkDupIdEcReq);

		/**
		 * IDP 간편회원 가입 연동 (cmd = simpleJoinApply)
		 */
		SimpleJoinEcReq simpleJoinEcReq = new SimpleJoinEcReq();
		simpleJoinEcReq.setUserId(req.getUserId()); // 사용자 아이디
		simpleJoinEcReq.setUserPasswd(req.getUserPw()); // 사용자 패스워드
		simpleJoinEcReq.setUserEmail(req.getUserEmail()); // 사용자 이메일

		SimpleJoinEcRes simpleJoinEcRes = this.idpSCI.simpleJoin(simpleJoinEcReq);

		CreateUserRequest createUserRequest = new CreateUserRequest();

		/**
		 * 공통 정보 setting
		 */
		createUserRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));

		/**
		 * SC 사용자 기본정보 setting
		 */
		UserMbr userMbr = new UserMbr();
		userMbr.setUserID(req.getUserId()); // 사용자 아이디
		userMbr.setIsRealName(MemberConstants.USE_N); // 실명인증 여부
		userMbr.setUserType(MemberConstants.USER_TYPE_IDPID); // IDP 회원
		userMbr.setUserMainStatus(MemberConstants.MAIN_STATUS_NORMAL); // 정상
		userMbr.setUserSubStatus(MemberConstants.SUB_STATUS_NORMAL); // 정상
		userMbr.setUserEmail(req.getUserEmail()); // 사용자 이메일
		userMbr.setIsRecvEmail(MemberConstants.USE_N); // 이메일 수신 여부 (AI-IS 로직 반영).
		userMbr.setIsRecvSMS(req.getIsRecvSms()); // SMS 수신 여부
		userMbr.setIsParent(MemberConstants.USE_N); // 부모 동의 여부 (AI-IS 로직 반영).
		userMbr.setRegDate(DateUtil.getToday("yyyyMMddHHmmss")); // 등록 일시
		createUserRequest.setUserMbr(userMbr);

		/**
		 * SC 사용자 가입요청
		 */
		CreateUserResponse createUserResponse = this.userSCI.create(createUserRequest);
		if (createUserResponse.getUserKey() == null || StringUtils.equals(createUserResponse.getUserKey(), "")) {
			throw new StorePlatformException("SAC_MEM_0002", "userKey");
		}

		/**
		 * 결과 세팅
		 */
		CreateBySimpleRes response = new CreateBySimpleRes();
		response.setUserKey(createUserResponse.getUserKey());

		return response;
	}

	@Override
	public CreateBySimpleRes regBySimpleDevice(SacRequestHeader sacHeader, CreateBySimpleReq req) {

		/**
		 * IDP 중복 아이디 체크. (cmd = duplicateIDCheck)
		 */
		CheckDupIdEcReq checkDupIdEcReq = new CheckDupIdEcReq();
		checkDupIdEcReq.setUserId(req.getUserId());
		this.idpSCI.checkDupId(checkDupIdEcReq);

		/**
		 * 모번호 조회 (989 일 경우만)
		 */
		req.setDeviceId(this.mcc.getOpmdMdnInfo(req.getDeviceId()));

		/**
		 * 단말등록시 필요한 기본 정보 세팅.
		 */
		MajorDeviceInfo majorDeviceInfo = this.mcc.getDeviceBaseInfo(sacHeader.getDeviceHeader().getModel(),
				req.getDeviceTelecom(), req.getDeviceId(), req.getDeviceIdType(), true);

		/**
		 * IDP 연동을 위한.... Phone 정보 세팅.
		 */
		StringBuffer sbUserPhone = new StringBuffer();
		sbUserPhone.append(req.getDeviceId());
		sbUserPhone.append(",");
		sbUserPhone.append(ObjectUtils.toString(majorDeviceInfo.getSvcMangNum()));
		sbUserPhone.append(",");
		sbUserPhone.append(majorDeviceInfo.getUacd());
		sbUserPhone.append(",");
		sbUserPhone.append(this.mcc.convertDeviceTelecom(req.getDeviceTelecom()));
		LOGGER.debug("## sbUserPhone : {}", sbUserPhone.toString());

		/**
		 * IDP 간편회원 가입 연동 (cmd = simpleJoinApply)
		 */
		SimpleJoinEcReq simpleJoinEcReq = new SimpleJoinEcReq();
		simpleJoinEcReq.setUserId(req.getUserId()); // 사용자 아이디
		simpleJoinEcReq.setUserPasswd(req.getUserPw()); // 사용자 패스워드
		simpleJoinEcReq.setUserEmail(req.getUserEmail()); // 사용자 이메일
		simpleJoinEcReq.setUserPhone(sbUserPhone.toString());
		SimpleJoinEcRes simpleJoinEcRes = this.idpSCI.simpleJoin(simpleJoinEcReq);

		CreateUserRequest createUserRequest = new CreateUserRequest();

		/**
		 * 공통 정보 setting
		 */
		createUserRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));

		/**
		 * SC 사용자 기본정보 setting
		 */
		UserMbr userMbr = new UserMbr();
		userMbr.setUserID(req.getUserId()); // 사용자 아이디
		userMbr.setIsRealName(MemberConstants.USE_N); // 실명인증 여부
		userMbr.setUserType(MemberConstants.USER_TYPE_IDPID); // IDP 회원
		userMbr.setUserMainStatus(MemberConstants.MAIN_STATUS_NORMAL); // 정상
		userMbr.setUserSubStatus(MemberConstants.SUB_STATUS_NORMAL); // 정상
		userMbr.setUserEmail(req.getUserEmail()); // 사용자 이메일
		userMbr.setIsRecvEmail(MemberConstants.USE_N); // 이메일 수신 여부 (AI-IS 로직 반영).
		userMbr.setIsRecvSMS(req.getIsRecvSms()); // SMS 수신 여부
		userMbr.setIsParent(MemberConstants.USE_N); // 부모 동의 여부 (AI-IS 로직 반영).
		userMbr.setRegDate(DateUtil.getToday("yyyyMMddHHmmss")); // 등록 일시
		createUserRequest.setUserMbr(userMbr);

		/**
		 * SC 사용자 가입요청
		 */
		CreateUserResponse createUserResponse = this.userSCI.create(createUserRequest);
		if (createUserResponse.getUserKey() == null || StringUtils.equals(createUserResponse.getUserKey(), "")) {
			throw new StorePlatformException("SAC_MEM_0002", "userKey");
		}

		/**
		 * 휴대기기 등록.
		 */
		String deviceKey = this.regDeviceSubmodule(req, sacHeader, createUserResponse.getUserKey(), majorDeviceInfo);

		/**
		 * 결과 세팅
		 */
		CreateBySimpleRes response = new CreateBySimpleRes();
		response.setUserKey(createUserResponse.getUserKey());
		response.setDeviceKey(deviceKey);

		return response;
	}

	@Override
	public CreateSaveAndSyncRes regSaveAndSync(SacRequestHeader sacHeader, CreateSaveAndSyncReq req) {

		/**
		 * 모번호 조회 (989 일 경우만)
		 */
		req.setDeviceId(this.mcc.getOpmdMdnInfo(req.getDeviceId()));

		/**
		 * 회원 기가입 여부 체크
		 */
		this.checkAlreadyJoin(sacHeader, req.getDeviceId());

		/**
		 * 회원 가입 IMEI, MAC & MSISDN
		 */
		return this.regSaveAndSyncUserJoin(sacHeader, req);

	}

	/**
	 * <pre>
	 * ID기반(Social ID) 회원 가입.
	 * </pre>
	 *
	 * @param sacHeader SacRequestHeader
	 * @param req       CreateByIdSacReq
	 * @return CreateByIdSacRes
	 */
	@Override
	public CreateByIdSacRes createById(SacRequestHeader sacHeader, CreateByIdSacReq req) {

		// userId 중복체크
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySchUserKey = new KeySearch();
		keySchUserKey.setKeyType(MemberConstants.KEY_TYPE_MBR_ID);
		keySchUserKey.setKeyString(req.getUserId());
		keySearchList.add(keySchUserKey);
		SearchExtentUserRequest searchExtentUserRequest = new SearchExtentUserRequest();
		CommonRequest commonRequest = new CommonRequest();
		searchExtentUserRequest.setCommonRequest(commonRequest);
		searchExtentUserRequest.setKeySearchList(keySearchList);
		searchExtentUserRequest.setUserInfoYn(MemberConstants.USE_Y);
		searchExtentUserRequest.setUserType(req.getUserType());
		try{
			this.userSCI.searchExtentUser(searchExtentUserRequest);
			throw new StorePlatformException("SAC_MEM_1104");
		} catch (StorePlatformException ex) {
			if (!StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_USERKEY)) {
				throw ex;
			}
		}

		String socialUserNo = null;
		try{
			if (StringUtils.equals(req.getUserType(), MemberConstants.USER_TYPE_FACEBOOK)){
				socialUserNo = this.mcc.facebookAuthenticate(req.getUserAuthToken());
			}else if (StringUtils.equals(req.getUserType(), MemberConstants.USER_TYPE_GOOGLE)){
				socialUserNo = this.mcc.googleAuthenticate(req.getUserAuthToken());
			}else if (StringUtils.equals(req.getUserType(), MemberConstants.USER_TYPE_NAVER)){
				socialUserNo = this.mcc.naverAuthenticate(req.getUserAuthToken());
			}
			if(StringUtils.isBlank(socialUserNo)){
				throw new StorePlatformException("SAC_MEM_1204");
			}
		}catch(StorePlatformException e){
			throw new StorePlatformException("SAC_MEM_1204");
		}

		// 모번호 조회
		if(StringUtils.equals(req.getDeviceTelecom(), MemberConstants.DEVICE_TELECOM_SKT)
				&& StringUtils.isNotBlank(req.getMdn())){
			req.setMdn(this.mcc.getOpmdMdnInfo(req.getMdn()));
		}

		/* 서비스관리번호 조회 */
		DeviceTelecomInfo deviceTelecomInfo = null;
		if(StringUtils.isNotBlank(req.getMdn())
				&& (StringUtils.equals(MemberConstants.DEVICE_TELECOM_SKT, req.getDeviceTelecom())
				|| StringUtils.equals(MemberConstants.DEVICE_TELECOM_KT, req.getDeviceTelecom())
				|| StringUtils.equals(MemberConstants.DEVICE_TELECOM_LGT, req.getDeviceTelecom())
		)){
			deviceTelecomInfo = this.mcc.getSvcMangNo(req.getMdn(), req.getDeviceTelecom(), req.getNativeId(), req.getSimSerialNo());
			if(deviceTelecomInfo == null){ // 타사 연동시 EC_SYS_ERROR 이나 타사 시스템오류 발생시
				throw new StorePlatformException("SAC_MEM_1106", this.mcc.convertDeviceTelecom(req.getDeviceTelecom()));
			}
		}

		// csp imei 비교
		if(StringUtils.equals(req.getDeviceTelecom(), MemberConstants.DEVICE_TELECOM_SKT)
				|| StringUtils.equals(req.getDeviceTelecom(), MemberConstants.DEVICE_TELECOM_SKM)){
			if(StringUtils.equals(req.getIsNativeIdAuth(), MemberConstants.USE_Y)){
				if(!StringUtils.equals(req.getNativeId(), deviceService.getIcasImei(req.getMdn()))){
					throw new StorePlatformException("SAC_MEM_1503");
				}
			}
		}

		// 법정대리인 나이 유효성 체크.
		MbrLglAgent mbrLglAgent = null;
		if (StringUtils.equals(req.getIsParent(), MemberConstants.USE_Y)) {

			if (StringUtils.isBlank(req.getOwnBirth())) {
				throw new StorePlatformException("SAC_MEM_0002", "ownBirth");
			}

			if (StringUtils.isBlank(req.getParentBirthDay())) {
				throw new StorePlatformException("SAC_MEM_0002", "parentBirthDay");
			}

			this.mcc.checkParentBirth(req.getOwnBirth(), req.getParentBirthDay());

			// 법정 대리인 정보 setting
			mbrLglAgent = new MbrLglAgent();
			mbrLglAgent.setIsParent(req.getIsParent()); // 법정대리인 동의 여부
			mbrLglAgent.setParentRealNameMethod(req.getParentRealNameMethod()); // 법정대리인 인증방법코드
			mbrLglAgent.setParentName(req.getParentName()); // 법정대리인 이름
			mbrLglAgent.setParentType(req.getParentType()); // 법정대리인 관계
			mbrLglAgent.setParentDate(req.getParentDate()); // 법정대리인 동의일시
			mbrLglAgent.setParentEmail(req.getParentEmail()); // 법정대리인 Email
			mbrLglAgent.setParentBirthDay(req.getParentBirthDay()); // 법정대리인 생년월일
			mbrLglAgent.setParentTelecom(req.getParentTelecom()); // 법정대리인 통신사 코드
			mbrLglAgent.setParentMDN(req.getParentPhone()); // 법정대리인 전화번호
			mbrLglAgent.setParentCI(req.getParentCi()); // 법정대리인 CI
			mbrLglAgent.setParentRealNameDate(req.getParentRealNameDate()); // 법정대리인 인증 일시
			mbrLglAgent.setParentRealNameSite(sacHeader.getTenantHeader().getSystemId()); // 법정대리인 실명인증사이트 코드
			if (StringUtils.isBlank(req.getParentIsDomestic())) {
				mbrLglAgent.setIsDomestic(MemberConstants.USE_Y); // 내외국인 여부
			} else {
				mbrLglAgent.setIsDomestic(req.getParentIsDomestic()); // 내외국인 여부
			}
		}

		// 약관 유효성 체크
		List<AgreementInfo> agreementInfoList = this.mcc.getClauseMappingInfo(req.getAgreementList());

		// SC 사용자 기본정보 setting
		UserMbr userMbr = new UserMbr();
		userMbr.setUserID(req.getUserId());
		userMbr.setUserType(req.getUserType());
		userMbr.setIsRealName(MemberConstants.USE_N); // 실명인증 여부
		userMbr.setUserMainStatus(MemberConstants.MAIN_STATUS_NORMAL); // 정상
		userMbr.setUserSubStatus(MemberConstants.SUB_STATUS_NORMAL); // 정상
		userMbr.setUserEmail(req.getUserEmail());
		userMbr.setIsRecvEmail(MemberConstants.USE_N); // 이메일 수신 여부
		userMbr.setIsParent(req.getIsParent()); // 부모동의 여부
		userMbr.setRegDate(DateUtil.getToday("yyyyMMddHHmmss")); // 등록일시
		userMbr.setUserBirthDay(req.getOwnBirth()); // 사용자 생년월일

		// 사용자 가입요청
		CreateUserRequest createUserRequest = new CreateUserRequest();
		createUserRequest.setUserMbr(userMbr);
		createUserRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));
		createUserRequest.setMbrClauseAgreeList(this.getAgreementInfo(agreementInfoList));
		createUserRequest.setMbrLglAgent(mbrLglAgent);

		// social 회원번호 부가속성으로 저장
		List<MbrMangItemPtcr> mbrMangItemPtcrList = new ArrayList<MbrMangItemPtcr>();
		MbrMangItemPtcr mbrMangItemPtcr = new MbrMangItemPtcr();
		mbrMangItemPtcr.setExtraProfile(MemberConstants.USER_EXTRA_SOCIL_MEMBER_NO);
		mbrMangItemPtcr.setExtraProfileValue(socialUserNo);
		mbrMangItemPtcrList.add(mbrMangItemPtcr);
		createUserRequest.setMbrMangItemPtcrList(mbrMangItemPtcrList);

		CreateUserResponse createUserResponse = this.userSCI.create(createUserRequest);

		// 휴대기기 등록 요청
		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setUserKey(createUserResponse.getUserKey());
		deviceInfo.setDeviceId(req.getDeviceId());
		deviceInfo.setMdn(req.getMdn());
		deviceInfo.setDeviceTelecom(req.getDeviceTelecom());
		deviceInfo.setNativeId(req.getNativeId());
		deviceInfo.setSimSerialNo(req.getSimSerialNo());
		deviceInfo.setIsRecvSms(req.getIsRecvSms());
		deviceInfo.setIsPrimary(MemberConstants.USE_Y);
		deviceInfo.setDeviceExtraInfoList(req.getDeviceExtraInfoList());
		if(deviceTelecomInfo != null && StringUtils.isNotBlank(deviceTelecomInfo.getSvcMangNum())){
			deviceInfo.setSvcMangNum(deviceTelecomInfo.getSvcMangNum());
		}
		String deviceKey = null;
		try{
			deviceKey = this.deviceService.regDeviceInfo(sacHeader, deviceInfo);
		}catch(StorePlatformException e){
			// 휴대기기 등록 실패하면 회원정보 롤백처리(delete)
			DeleteUserRequest deleteUserRequest = new DeleteUserRequest();
			deleteUserRequest.setCommonRequest(mcc.getSCCommonRequest(sacHeader));
			deleteUserRequest.setUserKey(createUserResponse.getUserKey());
			userSCI.delete(deleteUserRequest);
			throw e;
		}

		CreateByIdSacRes response = new CreateByIdSacRes();
		response.setUserKey(createUserResponse.getUserKey());
		response.setDeviceKey(deviceKey);
		return response;
	}

	@Override
	public CreateByMdnV2SacRes regByMdnV2(SacRequestHeader sacHeader, CreateByMdnV2SacReq req) {

		// 모번호 조회
		if(StringUtils.equals(req.getDeviceTelecom(), MemberConstants.DEVICE_TELECOM_SKT)){
			req.setMdn(this.mcc.getOpmdMdnInfo(req.getMdn()));
		}

		// MDN으로 모바일회원 기가입 체크
		if(this.deviceService.srhDevice(sacHeader, MemberConstants.KEY_TYPE_AUTHORIZE_MDN, req.getMdn(), null) != null){
			throw new StorePlatformException("SAC_MEM_1104");
		}

		/* 서비스관리번호 조회 */
		DeviceTelecomInfo deviceTelecomInfo = null;
		if(StringUtils.equals(MemberConstants.DEVICE_TELECOM_SKT, req.getDeviceTelecom())
				|| StringUtils.equals(MemberConstants.DEVICE_TELECOM_KT, req.getDeviceTelecom())
				|| StringUtils.equals(MemberConstants.DEVICE_TELECOM_LGT, req.getDeviceTelecom())
		){
			deviceTelecomInfo = this.mcc.getSvcMangNo(req.getMdn(), req.getDeviceTelecom(), req.getNativeId(), req.getSimSerialNo());
			if(deviceTelecomInfo == null){ // 타사 연동시 EC_SYS_ERROR 이나 타사 시스템오류 발생시
				throw new StorePlatformException("SAC_MEM_1106", this.mcc.convertDeviceTelecom(req.getDeviceTelecom()));
			}
		}

		// SKT 통신사 csp imei 비교
		if(StringUtils.equals(req.getDeviceTelecom(), MemberConstants.DEVICE_TELECOM_SKT)){
			if(StringUtils.equals(req.getIsNativeIdAuth(), MemberConstants.USE_Y)){
				if(!StringUtils.equals(req.getNativeId(), deviceService.getIcasImei(req.getMdn()))){
					throw new StorePlatformException("SAC_MEM_1503");
				}
			}
		}

		// 법정대리인 나이 유효성 체크.
		MbrLglAgent mbrLglAgent = null;
		if (StringUtils.equals(req.getIsParent(), MemberConstants.USE_Y)) {

			if (StringUtils.isBlank(req.getOwnBirth())) {
				throw new StorePlatformException("SAC_MEM_0002", "ownBirth");
			}

			if (StringUtils.isBlank(req.getParentBirthDay())) {
				throw new StorePlatformException("SAC_MEM_0002", "parentBirthDay");
			}

			this.mcc.checkParentBirth(req.getOwnBirth(), req.getParentBirthDay());

			// 법정대리인 setting.
			mbrLglAgent = new MbrLglAgent();
			mbrLglAgent.setIsParent(req.getIsParent()); // 법정대리인 동의 여부
			mbrLglAgent.setParentRealNameMethod(req.getParentRealNameMethod()); // 법정대리인 인증방법코드
			mbrLglAgent.setParentName(req.getParentName()); // 법정대리인 이름
			mbrLglAgent.setParentType(req.getParentType()); // 법정대리인 관계
			mbrLglAgent.setParentDate(req.getParentDate()); // 법정대리인 동의일시
			mbrLglAgent.setParentEmail(req.getParentEmail()); // 법정대리인 Email
			mbrLglAgent.setParentBirthDay(req.getParentBirthDay()); // 법정대리인 생년월일
			mbrLglAgent.setParentTelecom(req.getParentTelecom()); // 법정대리인 통신사 코드
			mbrLglAgent.setParentMDN(req.getParentPhone()); // 법정대리인 전화번호
			mbrLglAgent.setParentCI(req.getParentCi()); // 법정대리인 CI
			mbrLglAgent.setParentRealNameDate(req.getParentRealNameDate()); // 법정대리인 인증 일시
			mbrLglAgent.setParentRealNameSite(sacHeader.getTenantHeader().getSystemId()); // 법정대리인 실명인증사이트 코드
			if (StringUtils.isBlank(req.getParentIsDomestic())) {
				mbrLglAgent.setIsDomestic(MemberConstants.USE_Y); // 내외국인 여부
			} else {
				mbrLglAgent.setIsDomestic(req.getParentIsDomestic()); // 내외국인 여부
			}
		}

		// 약관 맵핑정보 세팅.
		List<AgreementInfo> agreementInfoList = this.mcc.getClauseMappingInfo(req.getAgreementList());

		// SC 사용자 기본정보 setting
		UserMbr userMbr = new UserMbr();
		userMbr.setIsRealName(MemberConstants.USE_N); // 실명인증 여부
		userMbr.setUserType(MemberConstants.USER_TYPE_MOBILE); // 모바일 회원
		userMbr.setUserMainStatus(MemberConstants.MAIN_STATUS_NORMAL); // 정상
		userMbr.setUserSubStatus(MemberConstants.SUB_STATUS_NORMAL); // 정상
		userMbr.setIsRecvEmail(MemberConstants.USE_N); // 이메일 수신 여부
		userMbr.setUserID(req.getDeviceId()); // 회원 컴포넌트에서 새로운 MBR_ID 를 생성하여 넣는다.
		userMbr.setIsParent(req.getIsParent()); // 부모동의 여부
		userMbr.setRegDate(DateUtil.getToday("yyyyMMddHHmmss")); // 등록일시
		userMbr.setUserBirthDay(req.getOwnBirth()); // 사용자 생년월일

		CreateUserRequest createUserRequest = new CreateUserRequest();
		createUserRequest.setUserMbr(userMbr);
		createUserRequest.setMbrLglAgent(mbrLglAgent);
		createUserRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));
		createUserRequest.setMbrClauseAgreeList(this.getAgreementInfo(agreementInfoList));

		// SC 사용자 가입요청
		CreateUserResponse createUserResponse = this.userSCI.create(createUserRequest);
		if (createUserResponse.getUserKey() == null || StringUtils.equals(createUserResponse.getUserKey(), "")) {
			throw new StorePlatformException("SAC_MEM_0002", "userKey");
		}

		// 휴대기기 등록요청
		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setUserKey(createUserResponse.getUserKey());
		deviceInfo.setDeviceId(req.getDeviceId());
		deviceInfo.setMdn(req.getMdn());
		deviceInfo.setDeviceTelecom(req.getDeviceTelecom());
		deviceInfo.setSimSerialNo(req.getSimSerialNo());
		deviceInfo.setNativeId(req.getNativeId());
		deviceInfo.setIsRecvSms(req.getIsRecvSms());
		deviceInfo.setIsPrimary(MemberConstants.USE_Y);
		if(deviceTelecomInfo != null && StringUtils.isNotBlank(deviceTelecomInfo.getSvcMangNum())){
			deviceInfo.setSvcMangNum(deviceTelecomInfo.getSvcMangNum());
		}
		deviceInfo.setDeviceExtraInfoList(req.getDeviceExtraInfoList());
		String deviceKey = null;
		try{
			deviceKey = this.deviceService.regDeviceInfo(sacHeader, deviceInfo);
		}catch(StorePlatformException e){
			// // 휴대기기 등록 실패하면 회원정보 롤백처리(delete)
			DeleteUserRequest deleteUserRequest = new DeleteUserRequest();
			deleteUserRequest.setCommonRequest(mcc.getSCCommonRequest(sacHeader));
			deleteUserRequest.setUserKey(createUserResponse.getUserKey());
			userSCI.delete(deleteUserRequest);
			throw e;
		}

		CreateByMdnV2SacRes response = new CreateByMdnV2SacRes();
		response.setUserKey(createUserResponse.getUserKey());
		response.setDeviceKey(deviceKey);
		return response;

	}

	/**
	 * <pre>
	 * SC 이용약관 정보 setting.
	 * </pre>
	 * 
	 * @param agreementList
	 *            List<AgreementInfo>
	 * @return List<MbrClauseAgree>
	 */
	private List<MbrClauseAgree> getAgreementInfo(List<AgreementInfo> agreementList) {

		List<MbrClauseAgree> mbrClauseAgreeList = new ArrayList<MbrClauseAgree>();
		for (AgreementInfo info : agreementList) {
			MbrClauseAgree mbrClauseAgree = new MbrClauseAgree();
			mbrClauseAgree.setExtraAgreementID(info.getExtraAgreementId());
			mbrClauseAgree.setExtraAgreementVersion(info.getExtraAgreementVersion());
			mbrClauseAgree.setIsExtraAgreement(info.getIsExtraAgreement());
			mbrClauseAgree.setIsMandatory(info.getMandAgreeYn());
			mbrClauseAgree.setRegDate(DateUtil.getToday());
			mbrClauseAgreeList.add(mbrClauseAgree);
		}

		LOGGER.debug("## SC Request 이용약관 정보 : {}", mbrClauseAgreeList);

		return mbrClauseAgreeList;
	}

	/**
	 * <pre>
	 * SC 법정대리인 정보 setting.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param req
	 *            CreateByMdnReq
	 * @return MbrLglAgent
	 */
	private MbrLglAgent getMbrLglAgent(SacRequestHeader sacHeader, CreateByMdnReq req) {

		MbrLglAgent mbrLglAgent = new MbrLglAgent();
		if (StringUtils.equals(req.getIsParent(), MemberConstants.USE_Y)) {

			mbrLglAgent.setIsParent(req.getIsParent()); // 법정대리인 동의 여부
			mbrLglAgent.setParentRealNameMethod(req.getParentRealNameMethod()); // 법정대리인 인증방법코드
			mbrLglAgent.setParentName(req.getParentName()); // 법정대리인 이름
			mbrLglAgent.setParentType(req.getParentType()); // 법정대리인 관계
			mbrLglAgent.setParentDate(req.getParentDate()); // 법정대리인 동의일시
			mbrLglAgent.setParentEmail(req.getParentEmail()); // 법정대리인 Email
			mbrLglAgent.setParentBirthDay(req.getParentBirthDay()); // 법정대리인 생년월일
			mbrLglAgent.setParentTelecom(req.getParentTelecom()); // 법정대리인 통신사 코드
			mbrLglAgent.setParentMDN(req.getParentPhone()); // 법정대리인 전화번호
			mbrLglAgent.setParentCI(req.getParentCi()); // 법정대리인 CI
			mbrLglAgent.setParentRealNameDate(req.getParentRealNameDate()); // 법정대리인 인증 일시
			mbrLglAgent.setParentRealNameSite(sacHeader.getTenantHeader().getSystemId()); // 법정대리인 실명인증사이트 코드
			if (StringUtils.equals(req.getParentIsDomestic(), "")) {
				mbrLglAgent.setIsDomestic(MemberConstants.USE_Y); // 내외국인 여부
			} else {
				mbrLglAgent.setIsDomestic(req.getParentIsDomestic()); // 내외국인 여부
			}
			LOGGER.debug("## SC Request 법정대리인 정보 : {}", mbrLglAgent);

		} else {

			LOGGER.debug("## SC Request 법정대리인 정보 없음 처리.");
			mbrLglAgent = null;

		}

		return mbrLglAgent;

	}

	/**
	 * <pre>
	 * 휴대기기 등록 submodule 호출.
	 * 필수 값 - (UA 코드, OMD UA 코드, 이동 통신사, 단말 모델, 단말명, SKT 회원관리번호)
	 * </pre>
	 * 
	 * @param obj
	 *            (CreateByMdnReq, CreateByAgreementReq, CreateBySimpleReq)
	 * @param sacHeader
	 *            SacRequestHeader
	 * @param userKey
	 *            사용자 등록키
	 * @param majorDeviceInfo
	 *            단말 주요 정보
	 * @return Device Key
	 */
	private String regDeviceSubmodule(Object obj, SacRequestHeader sacHeader, String userKey,
			MajorDeviceInfo majorDeviceInfo) {

		DeviceInfo deviceInfo = new DeviceInfo();

		/**
		 * Request Type 별로 분기하여 단말정보를 setting 한다. (가입처리에 대한 모든 휴대기기 등록을 모듈화)
		 */
		if (obj instanceof CreateByMdnReq) {

			/**
			 * 모바일 전용 회원 가입
			 */
			LOGGER.debug("======================= ## CreateByMdnReq");
			CreateByMdnReq req = (CreateByMdnReq) obj;
			deviceInfo.setUserKey(userKey);
			deviceInfo.setDeviceIdType(req.getDeviceIdType()); // 기기 ID 타입

            if(StringUtils.equals(req.getDeviceIdType(), MemberConstants.DEVICE_ID_TYPE_MSISDN)){
                deviceInfo.setDeviceId("");
                deviceInfo.setMdn(req.getDeviceId()); // MDN 번호
            }else {
                deviceInfo.setDeviceId(req.getDeviceId()); // 기기 ID
            }
            deviceInfo.setJoinId(req.getJoinId()); // 가입 채널 코드
			deviceInfo.setDeviceTelecom(majorDeviceInfo.getDeviceTelecom()); // 이동 통신사
			deviceInfo.setDeviceModelNo(majorDeviceInfo.getDeviceModelNo());// 단말 모델
			deviceInfo.setDeviceAccount(req.getDeviceAccount()); // 기기 계정 (Gmail)
			deviceInfo.setNativeId(req.getNativeId()); // 기기고유 ID (imei)
			deviceInfo.setNativeId(req.getNativeId()); // 기기 IMEI
			deviceInfo.setIsRecvSms(req.getIsRecvSms()); // SMS 수신 여부
			deviceInfo.setIsPrimary(MemberConstants.USE_Y); // 대표폰 여부
			deviceInfo.setSvcMangNum(majorDeviceInfo.getSvcMangNum()); // SKT 통합 서비스 관리번호
			deviceInfo.setDeviceExtraInfoList(this.getDeviceExtra(req.getDeviceExtraInfoList(), majorDeviceInfo)); // 단말부가정보

		} else if (obj instanceof CreateByAgreementReq) {

			/**
			 * 약관동의 가입
			 */
			LOGGER.debug("======================= ## CreateByAgreementReq");
			CreateByAgreementReq req = (CreateByAgreementReq) obj;
			deviceInfo.setUserKey(userKey);
			deviceInfo.setDeviceId(req.getDeviceId()); // 기기 ID
			deviceInfo.setDeviceIdType(req.getDeviceIdType()); // 기기 ID 타입
			deviceInfo.setJoinId(req.getJoinId()); // 가입 채널 코드
			deviceInfo.setDeviceTelecom(majorDeviceInfo.getDeviceTelecom()); // 이동 통신사
			deviceInfo.setDeviceModelNo(majorDeviceInfo.getDeviceModelNo()); // 단말 모델
			deviceInfo.setDeviceAccount(req.getDeviceAccount()); // 기기 계정 (Gmail)
			deviceInfo.setNativeId(req.getNativeId()); // 기기고유 ID (imei)
			deviceInfo.setIsRecvSms(req.getIsRecvSms()); // SMS 수신 여부
			deviceInfo.setIsPrimary(MemberConstants.USE_Y); // 대표폰 여부
			deviceInfo.setSvcMangNum(majorDeviceInfo.getSvcMangNum()); // SKT 통합 서비스 관리번호
			deviceInfo.setDeviceExtraInfoList(this.getDeviceExtra(req.getDeviceExtraInfoList(), majorDeviceInfo)); // 단말부가정보

		} else if (obj instanceof CreateBySimpleReq) {

			/**
			 * 간편 가입
			 */
			LOGGER.debug("======================= ## CreateBySimpleReq");
			CreateBySimpleReq req = (CreateBySimpleReq) obj;
			deviceInfo.setUserKey(userKey);
			deviceInfo.setDeviceId(req.getDeviceId()); // 기기 ID
			deviceInfo.setDeviceIdType(req.getDeviceIdType()); // 기기 ID 타입
			deviceInfo.setJoinId(req.getJoinId()); // 가입 채널 코드
			deviceInfo.setDeviceTelecom(majorDeviceInfo.getDeviceTelecom()); // 이동 통신사
			deviceInfo.setDeviceModelNo(majorDeviceInfo.getDeviceModelNo()); // 단말 모델
			deviceInfo.setDeviceAccount(req.getDeviceAccount()); // 기기 계정 (Gmail)
			deviceInfo.setNativeId(req.getNativeId()); // 기기고유 ID (imei)
			deviceInfo.setIsRecvSms(req.getIsRecvSms()); // SMS 수신 여부
			deviceInfo.setIsPrimary(MemberConstants.USE_Y); // 대표폰 여부
			deviceInfo.setSvcMangNum(majorDeviceInfo.getSvcMangNum()); // SKT 통합 서비스 관리번호
			deviceInfo.setDeviceExtraInfoList(this.getDeviceExtra(req.getDeviceExtraInfoList(), majorDeviceInfo)); // 단말부가정보

		} else if (obj instanceof CreateSaveAndSyncReq) {

			/**
			 * Save & Sync 가입
			 */
			LOGGER.debug("======================= ## CreateSaveAndSyncReq");
			CreateSaveAndSyncReq req = (CreateSaveAndSyncReq) obj;
			deviceInfo.setUserKey(userKey);
			deviceInfo.setDeviceId(req.getDeviceId()); // 기기 ID
			deviceInfo.setDeviceIdType(req.getDeviceIdType()); // 기기 ID 타입
			deviceInfo.setJoinId(""); // 가입 채널 코드
			deviceInfo.setDeviceTelecom(majorDeviceInfo.getDeviceTelecom()); // 이동 통신사
			deviceInfo.setDeviceModelNo(majorDeviceInfo.getDeviceModelNo()); // 단말 모델
			deviceInfo.setDeviceAccount(""); // 기기 계정 (Gmail)
			deviceInfo.setNativeId(""); // 기기고유 ID (imei)
			deviceInfo.setIsRecvSms(req.getIsRecvSms()); // SMS 수신 여부
			deviceInfo.setIsPrimary(MemberConstants.USE_Y); // 대표폰 여부
			deviceInfo.setSvcMangNum(majorDeviceInfo.getSvcMangNum()); // SKT 통합 서비스 관리번호
			deviceInfo.setDeviceExtraInfoList(this.getDeviceExtra(req.getDeviceExtraInfoList(), majorDeviceInfo)); // 단말부가정보

		} else {

			LOGGER.debug("======================= ## Not Found Request VO Type");

		}

		try {

			/**
			 * 휴대기기 등록 모듈 호출.
			 */
			LOGGER.debug("## 휴대기기 등록 정보 : {}", deviceInfo);
			String deviceKey = this.deviceService.regDeviceInfo(sacHeader, deviceInfo);

			if (deviceKey == null || StringUtils.equals(deviceKey, "")) {
				throw new StorePlatformException("SAC_MEM_0002", "deviceKey");
			}

			return deviceKey;

		} catch (Exception e) {
			throw new StorePlatformException("SAC_MEM_1102", e);
		}

	}

	/**
	 * <pre>
	 * 단말 부가정보 setting.
	 * </pre>
	 * 
	 * @param deviceExtraInfoList
	 *            List<DeviceExtraInfo>
	 * @param majorDeviceInfo
	 *            MajorDeviceInfo
	 * @return List<DeviceExtraInfo>
	 */
	private List<DeviceExtraInfo> getDeviceExtra(List<DeviceExtraInfo> deviceExtraInfoList,
			MajorDeviceInfo majorDeviceInfo) {

		LOGGER.debug("## 세팅 전 deviceExtraInfoList : {}", deviceExtraInfoList);

		/**
		 * 단말 부가 정보 없을 경우 처리.
		 */
		if (deviceExtraInfoList == null) {
			deviceExtraInfoList = new ArrayList<DeviceExtraInfo>();
		}

		/**
		 * UA 코드 추가.
		 */
		if (!StringUtils.equals(ObjectUtils.toString(majorDeviceInfo.getUacd()), "")) {
			LOGGER.debug("## UA 코드 추가.");
			DeviceExtraInfo uacd = new DeviceExtraInfo();
			uacd.setExtraProfile(MemberConstants.DEVICE_EXTRA_UACD);
			uacd.setExtraProfileValue(majorDeviceInfo.getUacd());
			deviceExtraInfoList.add(uacd);
		}

		/**
		 * OMD UA 코드 추가.
		 */
		if (!StringUtils.equals(ObjectUtils.toString(majorDeviceInfo.getOmdUacd()), "")) {
			LOGGER.debug("## OMD UA 코드 추가.");
			DeviceExtraInfo omdUacd = new DeviceExtraInfo();
			omdUacd.setExtraProfile(MemberConstants.DEVICE_EXTRA_OMDUACD);
			omdUacd.setExtraProfileValue(majorDeviceInfo.getOmdUacd());
			deviceExtraInfoList.add(omdUacd);
		}

		LOGGER.debug("## 세팅후 deviceExtraInfoList : {}", deviceExtraInfoList);

		return deviceExtraInfoList;

	}

	/**
	 * <pre>
	 * 모바일 회원 루팅여부, IMEI ICAS와 비교.
	 * </pre>
	 * 
	 * @param req
	 *            Request Value Object
	 */
	private void checkDeviceInfo(CreateByMdnReq req) {

		if (StringUtils.equals(req.getDeviceTelecom(), MemberConstants.DEVICE_TELECOM_SKT)) {

			/**
			 * 자사(SKT)일 경우. (IMEI 비교)
			 */
			if (!StringUtils.equals(req.getNativeId(), "")) {

				LOGGER.debug("## >> IMEI ICAS 와 비교.....");

				String icasImei = null;
				if (!StringUtils.equals(this.mcc.getMappingInfo(req.getDeviceId(), "mdn").getMvnoCD(), "0")) { // MVNO

					GetMvnoEcRes mvnoRes = this.mcc.getMvService(req.getDeviceId());
					icasImei = mvnoRes.getImeiNum();

				} else {

					GetCustomerEcRes costomerRes = this.mcc.getCustomer(req.getDeviceId());
					icasImei = costomerRes.getImeiNum();

				}

				LOGGER.info("ICAS 연동 [icasImei={}]", icasImei);
				if (!StringUtils.equals(icasImei, req.getNativeId())) {
					throw new StorePlatformException("SAC_MEM_1503");
				}

			} else {

				LOGGER.debug("## >> IMEI 존재 하지 않아 ICAS 와 비교 Skip.....");

			}

		} else {

			/**
			 * 타사(KTF,LGT)일경우. (ROOTING 단말 체크)
			 */
			LOGGER.debug("## >> 타사(KTF, LGT)일 경우는 [ROOTING 단말 체크-테넌트 로직으로 넘어감] Skip........");

		}

	}

	/**
	 * <pre>
	 * 회원 기가입 여부 체크.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param deviceId
	 *            기기 ID (MSISDN or MAC Address)
	 */
	private void checkAlreadyJoin(SacRequestHeader sacHeader, String deviceId) {

		/** 검색조건 정보 setting. */
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySchUserKey = new KeySearch();
		if (ValidationCheckUtils.isDeviceId(deviceId)){
			keySchUserKey.setKeyType(MemberConstants.KEY_TYPE_DEVICE_ID);
		} else {
			keySchUserKey.setKeyType(MemberConstants.KEY_TYPE_MDN);
		}
		keySchUserKey.setKeyString(deviceId); // 모번호 조회가 된 deviceId 여야 한다..
		keySearchList.add(keySchUserKey);

		/** 회원 조회 연동. */
		CheckDuplicationRequest chkDupReq = new CheckDuplicationRequest();
		chkDupReq.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));
		chkDupReq.setKeySearchList(keySearchList);
		CheckDuplicationResponse chkDupRes = this.userSCI.checkDuplication(chkDupReq);

		/** 이미 가입된 회원이면 오류 처리. */
		if (org.apache.commons.lang3.StringUtils.equals(chkDupRes.getIsRegistered(), "Y")) {
			LOGGER.debug("## 메인 상태 : {}", chkDupRes.getUserMbr().getUserMainStatus());
			LOGGER.debug("## 서브 상태 : {}", chkDupRes.getUserMbr().getUserSubStatus());
			throw new StorePlatformException("SAC_MEM_1104");
		}

		LOGGER.debug("## 회원 조회시 [ 검색결과 없음 OR 사용자키 또는 회원키 없음 ] 일 경우 Skip.....");

	}

	/**
	 * <pre>
	 * Save & Sync 회원 가입
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param req
	 *            Request Value Object
	 * @return Response Value Object
	 */
	private CreateSaveAndSyncRes regSaveAndSyncUserJoin(SacRequestHeader sacHeader, CreateSaveAndSyncReq req) {

		String userKey = null; // 사용자 Key
		String deviceKey = null; // 휴대기기 Key
		String deviceTelecom = MemberConstants.DEVICE_TELECOM_SKT; // 이동통신사

		/** 1. 사용자 가입처리. */
		/** 1-1. SC 가입 (공통 Request, 약관동의 Request) setting. */
		CreateUserRequest createUserRequest = new CreateUserRequest();
		createUserRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));
		createUserRequest.setMbrClauseAgreeList(this.getAgreementInfo(
				this.mcc.getClauseMappingInfo(req.getAgreementList())));

		/** 1-2. SC 사용자 기본정보 setting - IDP 연동을 하지 않으므로 MBR_NO 가 없음. */
		UserMbr userMbr = new UserMbr();
		userMbr.setIsRealName(MemberConstants.USE_N); // 실명인증 여부
		userMbr.setUserType(MemberConstants.USER_TYPE_MOBILE); // 모바일 회원
		userMbr.setUserMainStatus(MemberConstants.MAIN_STATUS_WATING); // 가가입
		userMbr.setUserSubStatus(MemberConstants.SUB_STATUS_JOIN_APPLY_WATING); // 가입승인 대기
		userMbr.setIsRecvEmail(MemberConstants.USE_N); // 이메일 수신 여부
		userMbr.setIsRecvSMS(MemberConstants.USE_N); // SMS 수신 여부
		userMbr.setUserID(req.getDeviceId()); // 회원 컴포넌트에서 새로운 MBR_ID 를 생성하여 넣는다.
		userMbr.setIsParent(MemberConstants.USE_N); // 부모동의 여부
		userMbr.setRegDate(DateUtil.getToday("yyyyMMddHHmmss")); // 등록일시
		createUserRequest.setUserMbr(userMbr);

		/** 1-3. SC 사용자 가입요청 */
		CreateUserResponse createUserResponse = this.userSCI.create(createUserRequest);
		if (createUserResponse.getUserKey() == null || StringUtils.equals(createUserResponse.getUserKey(), "")) {
			throw new StorePlatformException("SAC_MEM_0002", "userKey");
		}
		userKey = createUserResponse.getUserKey();

		/** 2. 휴대기기 등록처리. */
		/** 2-1. 휴대기기 등록을 위한 정보 셋팅 */
		DeviceInfo deviceInfo = new DeviceInfo();

		// local에서는 외부연동이 안되므로 svcMangNum 하드코딩
		if (System.getProperty("spring.profiles.active", "local").equals("local")) {
			HashMap<String, String> mdnMap = new HashMap<String, String>();
			mdnMap.put("01029088625", "svcNoTest29088625"); // SKT
			mdnMap.put("01029088624", "svcNoTest29088624"); // SKT
			mdnMap.put("01029088623", "svcNoTest29088623"); // SKT
			mdnMap.put("01029088622", "svcNoTest29088622"); // SKT
			mdnMap.put("01029088621", "svcNoTest29088621"); // SKT
			if (mdnMap.get(req.getDeviceId()) != null) {
				deviceInfo.setSvcMangNum(mdnMap.get(req.getDeviceId()));
			} else {
				throw new StorePlatformException("정상적으로 svc_mang_no가 조회되지 않았습니다.");
			}
		}

		deviceInfo.setUserKey(createUserResponse.getUserKey());
		deviceInfo.setDeviceId(req.getDeviceId());
		deviceInfo.setDeviceTelecom(deviceTelecom);
		deviceInfo.setIsRecvSms(req.getIsRecvSms());
		deviceInfo.setIsPrimary(MemberConstants.USE_Y);
		deviceInfo.setDeviceExtraInfoList(req.getDeviceExtraInfoList());

		try {
			/** 2-2. 휴대기기 등록. */
			LOGGER.debug("## 휴대기기 등록 정보 : {}", deviceInfo);
			deviceKey = this.deviceService.regDeviceInfo(sacHeader, deviceInfo);
		} catch (StorePlatformException e) {
			// // 휴대기기 등록 실패하면 회원정보 롤백처리(delete)
			DeleteUserRequest deleteUserRequest = new DeleteUserRequest();
			deleteUserRequest.setCommonRequest(mcc.getSCCommonRequest(sacHeader));
			deleteUserRequest.setUserKey(userKey);
			userSCI.delete(deleteUserRequest);
			throw e;
		}

		/** 3. 결과 세팅 */
		CreateSaveAndSyncRes response = new CreateSaveAndSyncRes();
		response.setUserKey(userKey);
		response.setDeviceKey(deviceKey);

		return response;

	}

	/**
	 * <pre>
	 * Save & Sync MSISDN 신규 가입.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param req
	 *            Request Value Object
	 * @return 사용자 Key
	 * @deprecated
	 */
	private String regSaveAndSyncMsisdnUser(SacRequestHeader sacHeader, CreateSaveAndSyncReq req) {

		/**
		 * (IDP 연동) 무선회원 가입 (cmd - joinForWap)
		 */
		JoinForWapEcReq joinForWapEcReq = new JoinForWapEcReq();
		joinForWapEcReq.setUserMdn(req.getDeviceId());
		joinForWapEcReq.setMdnCorp(MemberConstants.NM_DEVICE_TELECOM_SKT);
		JoinForWapEcRes joinForWapEcRes = this.idpSCI.joinForWap(joinForWapEcReq);

		/**
		 * SC 가입 (공통 Request, 약관동의 Request) setting.
		 */
		CreateUserRequest createUserRequest = new CreateUserRequest();
		createUserRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));
		createUserRequest.setMbrClauseAgreeList(this.getAgreementInfo(req.getAgreementList()));

		/**
		 * SC 사용자 기본정보 setting
		 */
		UserMbr userMbr = new UserMbr();
		userMbr.setIsRealName(MemberConstants.USE_N); // 실명인증 여부
		userMbr.setUserType(MemberConstants.USER_TYPE_MOBILE); // 모바일 회원
		userMbr.setUserMainStatus(MemberConstants.MAIN_STATUS_NORMAL); // 정상
		userMbr.setUserSubStatus(MemberConstants.SUB_STATUS_NORMAL); // 정상
		userMbr.setIsRecvEmail(MemberConstants.USE_N); // 이메일 수신 여부
		userMbr.setIsRecvSMS(MemberConstants.USE_N); // SMS 수신 여부
		userMbr.setUserID(req.getDeviceId()); // 회원 컴포넌트에서 새로운 MBR_ID 를 생성하여 넣는다.
		userMbr.setIsParent(MemberConstants.USE_N); // 부모동의 여부
		userMbr.setRegDate(DateUtil.getToday("yyyyMMddHHmmss")); // 등록일시
		createUserRequest.setUserMbr(userMbr);

		/**
		 * SC 사용자 가입요청
		 */
		CreateUserResponse createUserResponse = this.userSCI.create(createUserRequest);
		if (createUserResponse.getUserKey() == null || StringUtils.equals(createUserResponse.getUserKey(), "")) {
			throw new StorePlatformException("SAC_MEM_0002", "userKey");
		}

		return createUserResponse.getUserKey();

	}

	/**
	 * <pre>
	 * Save & Sync MAC 가가입.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param req
	 *            Request Value Object
	 * @return 사용자 Key
	 * @deprecated
	 */
	private String regSaveAndSyncMacUser(SacRequestHeader sacHeader, CreateSaveAndSyncReq req) {

		/**
		 * SC 가입 (공통 Request, 약관동의 Request) setting.
		 */
		CreateUserRequest createUserRequest = new CreateUserRequest();
		createUserRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));
		createUserRequest.setMbrClauseAgreeList(this.getAgreementInfo(req.getAgreementList()));

		/**
		 * SC 사용자 기본정보 setting
		 */
		UserMbr userMbr = new UserMbr();

		/**
		 * MAC 가입시에 IDP 연동을 하지 않으므로 MBR_NO 가 없다. (정의된 값을 넣기로 김덕중 과장님 결정.) [MAC-yyyyMMdd] MBR_NO (26)
		 */
		userMbr.setIsRealName(MemberConstants.USE_N); // 실명인증 여부
		userMbr.setUserType(MemberConstants.USER_TYPE_MOBILE); // 모바일 회원
		userMbr.setUserMainStatus(MemberConstants.MAIN_STATUS_WATING); // 가가입
		userMbr.setUserSubStatus(MemberConstants.SUB_STATUS_JOIN_APPLY_WATING); // 가입승인 대기
		userMbr.setIsRecvEmail(MemberConstants.USE_N); // 이메일 수신 여부
		userMbr.setIsRecvSMS(MemberConstants.USE_N); // SMS 수신 여부
		userMbr.setUserID(req.getDeviceId()); // 회원 컴포넌트에서 새로운 MBR_ID 를 생성하여 넣는다.
		userMbr.setIsParent(MemberConstants.USE_N); // 부모동의 여부
		userMbr.setRegDate(DateUtil.getToday("yyyyMMddHHmmss")); // 등록일시
		createUserRequest.setUserMbr(userMbr);

		/**
		 * SC 사용자 가입요청
		 */
		CreateUserResponse createUserResponse = this.userSCI.create(createUserRequest);
		if (createUserResponse.getUserKey() == null || StringUtils.equals(createUserResponse.getUserKey(), "")) {
			throw new StorePlatformException("SAC_MEM_0002", "userKey");
		}

		return createUserResponse.getUserKey();

	}

	/**
	 * <pre>
	 *  MAC 가입시에 IDP 연동을 하지 않으므로 MBR_NO 가 없다. (정의된 값을 넣기로 김덕중 과장님 결정).
	 * </pre>
	 * 
	 * @param deviceType
	 *            String
	 * @return fix MBR_NO {MAC}{yyyyMMddHHmmssSSS}{난수} (21자리)
	 * @deprecated
	 */
	private String getFixMbrNo(String deviceType) {
		StringBuffer fixMbrNo = new StringBuffer();
		if (StringUtils.equals(deviceType, MemberConstants.DEVICE_ID_TYPE_MACADDRESS)) {
			fixMbrNo.append("MAC");
		} else {
			fixMbrNo.append("IMEI");
		}
		fixMbrNo.append(DateUtil.getToday("yyyyMMddHHmmssSSS"));
		Random random = new Random();
		fixMbrNo.append(String.valueOf(random.nextInt(10)));
		return fixMbrNo.toString();
	}

	/**
	 * <pre>
	 * 이용동의 사이트 코드 여부.
	 * </pre>
	 * 
	 * @param agreeUserEcRes
	 *            이용동의가입 응답 Object
	 * @return
	 */
	private String ocbJoinCodeYn(AgreeUserEcRes agreeUserEcRes) {

		String ocbJoinCodeYn = MemberConstants.USE_N;

		/**
		 * Ok Cashbeg 코드 확인한다.
		 * 
		 * ec) 41100,null,20130923,212917,tstore000001741|90300,null,20130917, 113426,null
		 */
		LOGGER.debug("## >> joinSstList : {}", agreeUserEcRes.getJoinSstList());
		String[] joinSstList = agreeUserEcRes.getJoinSstList().replaceAll(" ", "").split("\\|");
		for (String joinInfo : joinSstList) {

			/**
			 * 이용동의 사이트 코드만 추출.
			 */
			String joinSstCode = joinInfo.split(",")[0];
			LOGGER.debug("## >> 이용동의 사이트 코드 : {}", joinSstCode);
			if (StringUtils.equals(joinSstCode, MemberConstants.SSO_SST_CD_OCB_WEB)) {
				ocbJoinCodeYn = MemberConstants.USE_Y;
			}

		}

		return ocbJoinCodeYn;

	}

}
