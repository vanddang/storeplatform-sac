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

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.idp.vo.IdpReceiverM;
import com.skplanet.storeplatform.external.client.idp.vo.ImIdpReceiverM;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.member.client.common.vo.MbrClauseAgree;
import com.skplanet.storeplatform.member.client.common.vo.MbrLglAgent;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbr;
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.client.member.vo.common.AgreementInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceExtraInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.MajorDeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByAgreementReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByAgreementRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByMdnReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByMdnRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateBySimpleReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateBySimpleRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.idp.constants.IdpConstants;
import com.skplanet.storeplatform.sac.member.common.idp.repository.IdpRepository;
import com.skplanet.storeplatform.sac.member.common.idp.service.IdpService;
import com.skplanet.storeplatform.sac.member.common.idp.service.ImIdpService;
import com.skplanet.storeplatform.sac.member.common.vo.Clause;

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
	private IdpService idpService;

	@Autowired
	private ImIdpService imIdpService;

	@Autowired
	private IdpRepository idpRepository;

	@Override
	public CreateByMdnRes createByMdn(SacRequestHeader sacHeader, CreateByMdnReq req) {

		/**
		 * 모번호 조회 (989 일 경우만)
		 */
		req.setDeviceId(this.mcc.getOpmdMdnInfo(req.getDeviceId()));

		/**
		 * 단말등록시 필요한 기본 정보 세팅.
		 */
		MajorDeviceInfo majorDeviceInfo = this.mcc.getDeviceBaseInfo(sacHeader.getDeviceHeader().getModel(), req.getDeviceTelecom(),
				req.getDeviceId(), req.getDeviceIdType());

		/**
		 * 필수 약관 동의여부 체크
		 */
		if (this.checkAgree(req.getAgreementList(), sacHeader.getTenantHeader().getTenantId())) {
			throw new StorePlatformException("SAC_MEM_1100");
		}

		IdpReceiverM join4WapInfo = null;

		try {

			/**
			 * (IDP 연동) 무선회원 가입 (cmd - joinForWap)
			 */
			join4WapInfo = this.idpService.join4Wap(req.getDeviceId(), this.mcc.convertDeviceTelecom(req.getDeviceTelecom()));

		} catch (StorePlatformException spe) {

			/**
			 * 가가입일 경우 처리.
			 */
			LOGGER.info("## errorCode : {}", spe.getErrorInfo().getCode());
			LOGGER.info("## errorMsg  : {}", spe.getErrorInfo().getMessage());
			if (StringUtils.equals(spe.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE + IdpConstants.IDP_RES_CODE_ALREADY_JOIN)) {

				/**
				 * (IDP 연동) 무선회원 해지
				 */
				LOGGER.info("## IDP 무선회원 해지 연동 Start =================");
				this.idpService.secedeUser4Wap(req.getDeviceId());

				/**
				 * 가가입 에러 발생.
				 */
				throw new StorePlatformException("SAC_MEM_1101", spe);

			}

			throw spe;

		}

		CreateUserRequest createUserRequest = new CreateUserRequest();

		/**
		 * 공통 정보 setting
		 */
		createUserRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));

		/**
		 * 이용약관 정보 setting
		 */
		createUserRequest.setMbrClauseAgreeList(this.getAgreementInfo(req.getAgreementList()));

		/**
		 * 법정대리인 setting.
		 */
		createUserRequest.setMbrLglAgent(this.getMbrLglAgent(req));

		/**
		 * SC 사용자 기본정보 setting
		 */
		UserMbr userMbr = new UserMbr();
		userMbr.setImMbrNo(join4WapInfo.getResponseBody().getUser_key()); // MBR_NO
		userMbr.setUserBirthDay(req.getOwnBirth()); // 사용자 생년월일
		userMbr.setIsRealName(MemberConstants.USE_N); // 실명인증 여부
		userMbr.setUserType(MemberConstants.USER_TYPE_MOBILE); // 모바일 회원
		userMbr.setUserMainStatus(MemberConstants.MAIN_STATUS_NORMAL); // 정상
		userMbr.setUserSubStatus(MemberConstants.SUB_STATUS_NORMAL); // 정상
		userMbr.setIsRecvEmail(MemberConstants.USE_N); // 이메일 수신 여부
		userMbr.setIsRecvSMS(req.getIsRecvSms()); // SMS 수신 여부
		userMbr.setUserID(req.getDeviceId()); // 회원 컴포넌트에서 새로운 MBR_ID 를 생성하여 넣는다.
		userMbr.setIsParent(req.getIsParent()); // 부모동의 여부
		userMbr.setRegDate(DateUtil.getToday("yyyyMMddHHmmss")); // 등록일시
		createUserRequest.setUserMbr(userMbr);
		LOGGER.info("## SC Request userMbr : {}", createUserRequest.getUserMbr().toString());

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
		String deviceKey = this.createDeviceSubmodule(req, sacHeader, createUserResponse.getUserKey(), majorDeviceInfo);

		/**
		 * 결과 세팅
		 */
		CreateByMdnRes response = new CreateByMdnRes();
		response.setUserKey(createUserResponse.getUserKey());
		response.setDeviceKey(deviceKey);
		return response;

	}

	@Override
	public CreateByAgreementRes createByAgreementId(SacRequestHeader sacHeader, CreateByAgreementReq req) {

		/**
		 * 필수 약관 동의여부 체크
		 */
		if (this.checkAgree(req.getAgreementList(), sacHeader.getTenantHeader().getTenantId())) {
			throw new StorePlatformException("SAC_MEM_1100");
		}

		/**
		 * 이용동의 가입 데이타 setting
		 */
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("cmd", "TXAgreeUserIDP");
		param.put("key_type", "2"); // 1=IM통합서비스번호, 2=IM통합ID
		param.put("key", req.getUserId());
		param.put("join_sst_list",
				MemberConstants.SSO_SST_CD_TSTORE + ",TAC001^TAC002^TAC003^TAC004^TAC005," + DateUtil.getToday() + "," + DateUtil.getTime());
		param.put("ocb_join_code", "N"); // 통합포인트 가입 여부 Y=가입, N=미가입

		/**
		 * (통합 IDP 연동) 이용동의 가입
		 */
		ImIdpReceiverM agreeUserInfo = this.imIdpService.agreeUser(param);

		CreateUserRequest createUserRequest = new CreateUserRequest();

		/**
		 * 공통 정보 setting
		 */
		createUserRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));

		/**
		 * 이용약관 정보 setting
		 */
		createUserRequest.setMbrClauseAgreeList(this.getAgreementInfo(req.getAgreementList()));

		/**
		 * 통합 ID 기본 프로파일 조회 (통합ID 회원) 프로파일 조회 - 이름, 생년월일
		 */
		ImIdpReceiverM profileInfo = this.imIdpService.userInfoIdpSearchServer(agreeUserInfo.getResponseBody().getIm_int_svc_no());

		/**
		 * SC 사용자 기본정보 setting
		 */
		UserMbr userMbr = new UserMbr();
		userMbr.setUserID(req.getUserId()); // 사용자 아이디
		userMbr.setUserEmail(agreeUserInfo.getResponseBody().getUser_email()); // 사용자 이메일
		userMbr.setUserPhone(agreeUserInfo.getResponseBody().getUser_tn()); // 사용자 전화번호 (AS-IS 로직 반영.)
		userMbr.setUserName(profileInfo.getResponseBody().getUser_name()); // 사용자 이름
		userMbr.setUserBirthDay(profileInfo.getResponseBody().getUser_birthday()); // 사용자 생년월일
		userMbr.setImMbrNo(agreeUserInfo.getResponseBody().getUser_key()); // MBR_NO
		userMbr.setImSvcNo(agreeUserInfo.getResponseBody().getIm_int_svc_no()); // OneID 통합서비스 관리번호
		userMbr.setIsRealName(MemberConstants.USE_N); // 실명인증 여부
		userMbr.setUserType(MemberConstants.USER_TYPE_ONEID); // One ID 회원
		userMbr.setUserMainStatus(MemberConstants.MAIN_STATUS_NORMAL); // 정상
		userMbr.setUserSubStatus(MemberConstants.SUB_STATUS_NORMAL); // 정상
		userMbr.setIsRecvEmail(MemberConstants.USE_N); // 이메일 수신 여부 (AI-IS 로직 반영).
		userMbr.setIsRecvSMS(req.getIsRecvSms()); // SMS 수신 여부
		userMbr.setIsParent(MemberConstants.USE_N); // 부모동의 여부 (AI-IS 로직 반영).
		userMbr.setRegDate(DateUtil.getToday("yyyyMMddHHmmss")); // 등록 일시
		createUserRequest.setUserMbr(userMbr);
		LOGGER.info("## SC Request userMbr : {}", createUserRequest.getUserMbr().toString());

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
	public CreateByAgreementRes createByAgreementDevice(SacRequestHeader sacHeader, CreateByAgreementReq req) {

		/**
		 * 모번호 조회 (989 일 경우만)
		 */
		req.setDeviceId(this.mcc.getOpmdMdnInfo(req.getDeviceId()));

		/**
		 * 단말등록시 필요한 기본 정보 세팅.
		 */
		MajorDeviceInfo majorDeviceInfo = this.mcc.getDeviceBaseInfo(sacHeader.getDeviceHeader().getModel(), req.getDeviceTelecom(),
				req.getDeviceId(), req.getDeviceIdType());

		/**
		 * 필수 약관 동의여부 체크
		 */
		if (this.checkAgree(req.getAgreementList(), sacHeader.getTenantHeader().getTenantId())) {
			throw new StorePlatformException("SAC_MEM_1100");
		}

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
		sbUserPhone.append(this.mcc.convertDeviceTelecom(majorDeviceInfo.getDeviceTelecom()));
		LOGGER.info("## sbUserPhone : {}", sbUserPhone.toString());

		/**
		 * (OneID 연동) 이용동의 가입
		 */
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("cmd", "TXAgreeUserIDP");
		param.put("key_type", "2"); // 1=IM통합서비스번호, 2=IM통합ID
		param.put("key", req.getUserId());
		param.put("user_mdn", sbUserPhone.toString());
		param.put("join_sst_list",
				MemberConstants.SSO_SST_CD_TSTORE + ",TAC001^TAC002^TAC003^TAC004^TAC005," + DateUtil.getToday() + "," + DateUtil.getTime());
		param.put("user_mdn_auth_key", this.idpRepository.makePhoneAuthKey(sbUserPhone.toString()));
		param.put("ocb_join_code", "N"); // 통합포인트 가입 여부 Y=가입, N=미가입
		LOGGER.info("## param : {}", param.entrySet());

		/**
		 * (통합 IDP) 이용동의 가입 요청
		 */
		ImIdpReceiverM agreeUserInfo = this.imIdpService.agreeUser(param);

		CreateUserRequest createUserRequest = new CreateUserRequest();

		/**
		 * 공통 정보 setting
		 */
		createUserRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));

		/**
		 * 이용약관 정보 setting
		 */
		createUserRequest.setMbrClauseAgreeList(this.getAgreementInfo(req.getAgreementList()));

		/**
		 * 통합 ID 기본 프로파일 조회 (통합ID 회원) 프로파일 조회 - 이름, 생년월일
		 */
		ImIdpReceiverM profileInfo = this.imIdpService.userInfoIdpSearchServer(agreeUserInfo.getResponseBody().getIm_int_svc_no());

		/**
		 * SC 사용자 기본정보 setting
		 */
		UserMbr userMbr = new UserMbr();
		userMbr.setUserID(req.getUserId()); // 사용자 아이디
		userMbr.setUserEmail(agreeUserInfo.getResponseBody().getUser_email()); // 사용자 이메일
		userMbr.setUserPhone(agreeUserInfo.getResponseBody().getUser_tn()); // 사용자 전화번호 (AS-IS 로직 반영.)
		userMbr.setUserName(profileInfo.getResponseBody().getUser_name()); // 사용자 이름
		userMbr.setUserBirthDay(profileInfo.getResponseBody().getUser_birthday()); // 사용자 생년월일
		userMbr.setImMbrNo(agreeUserInfo.getResponseBody().getUser_key()); // MBR_NO
		userMbr.setImSvcNo(agreeUserInfo.getResponseBody().getIm_int_svc_no()); // 통합 서비스 관리 번호
		userMbr.setIsRealName(MemberConstants.USE_N); // 실명인증 여부
		userMbr.setUserType(MemberConstants.USER_TYPE_ONEID); // One ID 회원
		userMbr.setUserMainStatus(MemberConstants.MAIN_STATUS_NORMAL); // 정상
		userMbr.setUserSubStatus(MemberConstants.SUB_STATUS_NORMAL); // 정상
		userMbr.setIsRecvEmail(MemberConstants.USE_N); // 이메일 수신 여부 (AI-IS 로직 반영).
		userMbr.setIsRecvSMS(req.getIsRecvSms()); // SMS 수신 여부
		userMbr.setIsParent(MemberConstants.USE_N); // 부모동의 여부 (AI-IS 로직 반영).
		userMbr.setRegDate(DateUtil.getToday("yyyyMMddHHmmss")); // 등록 일시
		createUserRequest.setUserMbr(userMbr);
		LOGGER.info("## SC Request userMbr : {}", createUserRequest.getUserMbr().toString());

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
		String deviceKey = this.createDeviceSubmodule(req, sacHeader, createUserResponse.getUserKey(), majorDeviceInfo);

		/**
		 * 결과 세팅
		 */
		CreateByAgreementRes response = new CreateByAgreementRes();
		response.setUserKey(createUserResponse.getUserKey());
		response.setDeviceKey(deviceKey);

		return response;

	}

	@Override
	public CreateBySimpleRes createBySimpleId(SacRequestHeader sacHeader, CreateBySimpleReq req) {

		/**
		 * IDP 중복 아이디 체크.
		 */
		this.idpService.checkDupID(this.getUrlEncode(req.getUserId()));

		/**
		 * IDP - 간편회원가입
		 */
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("user_id", this.getUrlEncode(req.getUserId()));
		param.put("user_passwd", this.getUrlEncode(req.getUserPw()));
		param.put("user_email", this.getUrlEncode(req.getUserEmail()));
		LOGGER.info("## param : {}", param.entrySet());

		/**
		 * IDP 간편회원 가입 연동 (cmd = simpleJoinApply)
		 */
		IdpReceiverM simpleJoinInfo = this.idpService.simpleJoin(param);

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
		userMbr.setImMbrNo(simpleJoinInfo.getResponseBody().getUser_key()); // MBR_NO
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
		LOGGER.info("## SC Request userMbr : {}", createUserRequest.getUserMbr().toString());

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
	public CreateBySimpleRes createBySimpleDevice(SacRequestHeader sacHeader, CreateBySimpleReq req) {

		/**
		 * IDP 중복 아이디 체크.
		 */
		this.idpService.checkDupID(this.getUrlEncode(req.getUserId()));

		/**
		 * 모번호 조회 (989 일 경우만)
		 */
		req.setDeviceId(this.mcc.getOpmdMdnInfo(req.getDeviceId()));

		/**
		 * 단말등록시 필요한 기본 정보 세팅.
		 */
		MajorDeviceInfo majorDeviceInfo = this.mcc.getDeviceBaseInfo(sacHeader.getDeviceHeader().getModel(), req.getDeviceTelecom(),
				req.getDeviceId(), req.getDeviceIdType());

		/**
		 * 통합 IDP 연동을 위한.... Phone 정보 세팅.
		 */
		StringBuffer sbUserPhone = new StringBuffer();
		sbUserPhone.append(req.getDeviceId());
		sbUserPhone.append(",");
		sbUserPhone.append(ObjectUtils.toString(majorDeviceInfo.getSvcMangNum()));
		sbUserPhone.append(",");
		sbUserPhone.append(majorDeviceInfo.getUacd());
		sbUserPhone.append(",");
		sbUserPhone.append(this.mcc.convertDeviceTelecom(majorDeviceInfo.getDeviceTelecom()));
		LOGGER.info("## sbUserPhone : {}", sbUserPhone.toString());

		/**
		 * IDP - 간편회원가입 setting
		 */
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("user_id", this.getUrlEncode(req.getUserId()));
		param.put("user_passwd", this.getUrlEncode(req.getUserPw()));
		param.put("user_email", this.getUrlEncode(req.getUserEmail()));
		param.put("user_phone", sbUserPhone.toString());
		param.put("phone_auth_key", this.idpRepository.makePhoneAuthKey(sbUserPhone.toString()));
		LOGGER.info("## param : {}", param.entrySet());

		/**
		 * IDP 간편 회원가입 연동
		 */
		IdpReceiverM simpleJoinInfo = this.idpService.simpleJoin(param);

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
		userMbr.setImMbrNo(simpleJoinInfo.getResponseBody().getUser_key()); // MBR_NO
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
		LOGGER.info("## SC Request userMbr : {}", createUserRequest.getUserMbr().toString());

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
		String deviceKey = this.createDeviceSubmodule(req, sacHeader, createUserResponse.getUserKey(), majorDeviceInfo);

		/**
		 * 결과 세팅
		 */
		CreateBySimpleRes response = new CreateBySimpleRes();
		response.setUserKey(createUserResponse.getUserKey());
		response.setDeviceKey(deviceKey);

		return response;
	}

	/**
	 * <pre>
	 * 필수 약관 동의 정보를 체크 한다.
	 * </pre>
	 * 
	 * @param agreementList
	 *            요청 약관 동의 정보
	 * @param tenantId
	 *            테넌트 아이디
	 * @return boolean
	 */
	private boolean checkAgree(List<AgreementInfo> agreementList, String tenantId) {

		/**
		 * DB 약관 목록 조회 sorting
		 */
		List<Clause> dbAgreementList = this.mcc.getMandAgreeList(tenantId);
		if (dbAgreementList.size() == 0) {
			LOGGER.info("## 체크할 필수 약관이 존재 하지 않습니다.");
			return false;
		}
		Comparator<Clause> dbComparator = new Comparator<Clause>() {
			@Override
			public int compare(Clause value1, Clause value2) {
				return value1.getClauseItemCd().compareTo(value2.getClauseItemCd());
			}
		};
		Collections.sort(dbAgreementList, dbComparator);

		// sorting data setting
		StringBuffer sortDbAgreeInfo = new StringBuffer();
		for (Clause sortInfo : dbAgreementList) {
			sortDbAgreeInfo.append(sortInfo.getClauseItemCd());
		}
		LOGGER.info("## 필수약관목록 : {}", sortDbAgreeInfo);

		/**
		 * 요청 약관 목록 조회 sorting
		 */
		Comparator<AgreementInfo> comparator = new Comparator<AgreementInfo>() {
			@Override
			public int compare(AgreementInfo o1, AgreementInfo o2) {
				return o1.getExtraAgreementId().compareTo(o2.getExtraAgreementId());
			}
		};
		Collections.sort(agreementList, comparator);

		// sorting data setting
		StringBuffer sortAgreeInfo = new StringBuffer();
		for (AgreementInfo info : agreementList) {

			/**
			 * 약관 동의한것만 비교대상으로 세팅.
			 */
			if (StringUtils.equals(info.getIsExtraAgreement(), MemberConstants.USE_Y)) {

				/**
				 * 필수 약관에 포함되는 것만 비교대상으로 세팅.
				 */
				for (Clause sortInfo : dbAgreementList) {
					if (StringUtils.equals(sortInfo.getClauseItemCd(), info.getExtraAgreementId())) {
						sortAgreeInfo.append(info.getExtraAgreementId());
					}
				}

			}
		}
		LOGGER.info("## 요청약관목록 : {}", sortAgreeInfo);

		/**
		 * 정렬된 DB 약관 목록과 요청 약관 목록을 비교한다.
		 */
		if (!StringUtils.equals(sortDbAgreeInfo.toString(), sortAgreeInfo.toString())) {
			return true;
		} else {
			return false;
		}

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
			mbrClauseAgree.setRegDate(DateUtil.getToday());
			mbrClauseAgreeList.add(mbrClauseAgree);
		}

		LOGGER.info("## SC Request 이용약관 정보 : {}", mbrClauseAgreeList.toString());

		return mbrClauseAgreeList;
	}

	/**
	 * <pre>
	 * SC 법정대리인 정보 setting.
	 * </pre>
	 * 
	 * @param req
	 *            CreateByMdnReq
	 * @return MbrLglAgent
	 */
	private MbrLglAgent getMbrLglAgent(CreateByMdnReq req) {

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
			mbrLglAgent.setParentRealNameSite(req.getParentRealNameSite()); // 법정대리인 실명인증사이트 코드
			LOGGER.info("## SC Request 법정대리인 정보 : {}", mbrLglAgent.toString());

		} else {

			LOGGER.info("## SC Request 법정대리인 정보 없음 처리.");
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
	 */
	private String createDeviceSubmodule(Object obj, SacRequestHeader sacHeader, String userKey, MajorDeviceInfo majorDeviceInfo) {

		DeviceInfo deviceInfo = new DeviceInfo();

		/**
		 * Request Type 별로 분기하여 단말정보를 setting 한다. (가입처리에 대한 모든 휴대기기 등록을 모듈화)
		 */
		if (obj instanceof CreateByMdnReq) {

			/**
			 * 모바일 전용 회원 가입
			 */
			LOGGER.info("======================= ## CreateByMdnReq");
			CreateByMdnReq req = (CreateByMdnReq) obj;
			deviceInfo.setDeviceId(req.getDeviceId()); // 기기 ID
			deviceInfo.setDeviceIdType(req.getDeviceIdType()); // 기기 ID 타입
			deviceInfo.setJoinId(req.getJoinId()); // 가입 채널 코드
			deviceInfo.setDeviceTelecom(majorDeviceInfo.getDeviceTelecom()); // 이동 통신사
			deviceInfo.setDeviceNickName(majorDeviceInfo.getDeviceNickName()); // 단말명
			deviceInfo.setDeviceModelNo(sacHeader.getDeviceHeader().getModel()); // 단말 모델
			deviceInfo.setDeviceAccount(req.getDeviceAccount()); // 기기 계정 (Gmail)
			deviceInfo.setNativeId(req.getNativeId()); // 기기고유 ID (imei)
			deviceInfo.setNativeId(req.getNativeId()); // 기기 IMEI
			deviceInfo.setIsRecvSms(req.getIsRecvSms()); // SMS 수신 여부
			deviceInfo.setIsPrimary(MemberConstants.USE_Y); // 대표폰 여부
			deviceInfo.setIsAuthenticated(MemberConstants.USE_Y); // 인증 여부
			deviceInfo.setAuthenticationDate(DateUtil.getToday()); // 인증 일시
			deviceInfo.setIsUsed(MemberConstants.USE_Y); // 사용여부
			deviceInfo.setUserDeviceExtraInfo(this.getDeviceExtra(req.getDeviceExtraInfoList(), majorDeviceInfo)); // 단말부가정보

		} else if (obj instanceof CreateByAgreementReq) {

			/**
			 * 약관동의 가입
			 */
			LOGGER.info("======================= ## CreateByAgreementReq");
			CreateByAgreementReq req = (CreateByAgreementReq) obj;
			deviceInfo.setDeviceId(req.getDeviceId()); // 기기 ID
			deviceInfo.setDeviceIdType(req.getDeviceIdType()); // 기기 ID 타입
			deviceInfo.setJoinId(req.getJoinId()); // 가입 채널 코드
			deviceInfo.setDeviceTelecom(majorDeviceInfo.getDeviceTelecom()); // 이동 통신사
			deviceInfo.setDeviceNickName(majorDeviceInfo.getDeviceNickName()); // 단말명
			deviceInfo.setDeviceModelNo(sacHeader.getDeviceHeader().getModel()); // 단말 모델
			deviceInfo.setDeviceAccount(req.getDeviceAccount()); // 기기 계정 (Gmail)
			deviceInfo.setNativeId(req.getNativeId()); // 기기고유 ID (imei)
			deviceInfo.setIsRecvSms(req.getIsRecvSms()); // SMS 수신 여부
			deviceInfo.setIsPrimary(MemberConstants.USE_Y); // 대표폰 여부
			deviceInfo.setIsAuthenticated(MemberConstants.USE_Y); // 인증 여부
			deviceInfo.setAuthenticationDate(DateUtil.getToday("yyyyMMddHHmmss")); // 인증 일시
			deviceInfo.setIsUsed(MemberConstants.USE_Y); // 사용여부
			deviceInfo.setUserDeviceExtraInfo(this.getDeviceExtra(req.getDeviceExtraInfoList(), majorDeviceInfo)); // 단말부가정보

		} else if (obj instanceof CreateBySimpleReq) {

			/**
			 * 간편 가입
			 */
			LOGGER.info("======================= ## CreateBySimpleReq");
			CreateBySimpleReq req = (CreateBySimpleReq) obj;
			deviceInfo.setDeviceId(req.getDeviceId()); // 기기 ID
			deviceInfo.setDeviceIdType(req.getDeviceIdType()); // 기기 ID 타입
			deviceInfo.setJoinId(req.getJoinId()); // 가입 채널 코드
			deviceInfo.setDeviceTelecom(majorDeviceInfo.getDeviceTelecom()); // 이동 통신사
			deviceInfo.setDeviceNickName(majorDeviceInfo.getDeviceNickName()); // 단말명
			deviceInfo.setDeviceModelNo(sacHeader.getDeviceHeader().getModel()); // 단말 모델
			deviceInfo.setDeviceAccount(req.getDeviceAccount()); // 기기 계정 (Gmail)
			deviceInfo.setNativeId(req.getNativeId()); // 기기고유 ID (imei)
			deviceInfo.setIsRecvSms(req.getIsRecvSms()); // SMS 수신 여부
			deviceInfo.setIsPrimary(MemberConstants.USE_Y); // 대표폰 여부
			deviceInfo.setIsAuthenticated(MemberConstants.USE_Y); // 인증 여부
			deviceInfo.setAuthenticationDate(DateUtil.getToday("yyyyMMddHHmmss")); // 인증 일시
			deviceInfo.setIsUsed(MemberConstants.USE_Y); // 사용여부
			deviceInfo.setUserDeviceExtraInfo(this.getDeviceExtra(req.getDeviceExtraInfoList(), majorDeviceInfo)); // 단말부가정보

		}

		try {

			/**
			 * 휴대기기 등록 모듈 호출.
			 */
			LOGGER.info("## 휴대기기 등록 정보 : {}", deviceInfo.toString());
			String deviceKey = this.mcc.insertDeviceInfo(sacHeader.getTenantHeader().getSystemId(), sacHeader.getTenantHeader().getTenantId(),
					userKey, deviceInfo);
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
	private List<DeviceExtraInfo> getDeviceExtra(List<DeviceExtraInfo> deviceExtraInfoList, MajorDeviceInfo majorDeviceInfo) {

		LOGGER.info("## 세팅 전 deviceExtraInfoList : {}", deviceExtraInfoList.toString());

		/**
		 * UA 코드 추가.
		 */
		if (!StringUtils.equals(ObjectUtils.toString(majorDeviceInfo.getUacd()), "")) {
			LOGGER.info("## UA 코드 추가.");
			DeviceExtraInfo uacd = new DeviceExtraInfo();
			uacd.setExtraProfile(MemberConstants.DEVICE_EXTRA_UACD);
			uacd.setExtraProfileValue(majorDeviceInfo.getUacd());
			deviceExtraInfoList.add(uacd);
		}

		/**
		 * OMD UA 코드 추가.
		 */
		if (!StringUtils.equals(ObjectUtils.toString(majorDeviceInfo.getOmdUacd()), "")) {
			LOGGER.info("## OMD UA 코드 추가.");
			DeviceExtraInfo omdUacd = new DeviceExtraInfo();
			omdUacd.setExtraProfile(MemberConstants.DEVICE_EXTRA_OMDUACD);
			omdUacd.setExtraProfileValue(majorDeviceInfo.getOmdUacd());
			deviceExtraInfoList.add(omdUacd);
		}

		LOGGER.info("## 세팅후 deviceExtraInfoList : {}", deviceExtraInfoList.toString());

		return deviceExtraInfoList;

	}

	/**
	 * <pre>
	 * URL Encode.
	 * </pre>
	 * 
	 * @param value
	 *            String
	 * @return String
	 */
	private String getUrlEncode(String value) {
		try {
			return URLEncoder.encode(value, "UTF-8");
		} catch (Exception e) {
			throw new StorePlatformException("SAC_MEM_0004", e);
		}
	}

}
