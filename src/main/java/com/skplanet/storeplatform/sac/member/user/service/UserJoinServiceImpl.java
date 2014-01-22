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
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.external.client.idp.vo.IDPReceiverM;
import com.skplanet.storeplatform.external.client.idp.vo.ImIDPReceiverM;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
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
import com.skplanet.storeplatform.sac.member.common.idp.constants.IDPConstants;
import com.skplanet.storeplatform.sac.member.common.idp.repository.IDPRepository;
import com.skplanet.storeplatform.sac.member.common.idp.service.IDPService;
import com.skplanet.storeplatform.sac.member.common.idp.service.ImIDPService;
import com.skplanet.storeplatform.sac.member.common.vo.Clause;

/**
 * 회원 가입 서비스 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013. 12. 31. Updated by : 심대진, 다모아 솔루션.
 */
@Service
@Transactional
public class UserJoinServiceImpl implements UserJoinService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserJoinServiceImpl.class);

	@Autowired
	private MemberCommonComponent mcc;

	@Autowired
	private UserSCI userSCI;

	@Autowired
	private IDPService idpService;

	@Autowired
	private ImIDPService imIdpService;

	@Autowired
	private IDPRepository idpRepository;

	@Override
	public CreateByMdnRes createByMdn(SacRequestHeader sacHeader, CreateByMdnReq req) throws Exception {

		CreateByMdnRes response = new CreateByMdnRes();

		/**
		 * 모번호 조회 (989 일 경우만)
		 */
		req.setDeviceId(this.mcc.getOpmdMdnInfo(req.getDeviceId()));

		/**
		 * 단말등록시 필요한 기본 정보 세팅.
		 */
		MajorDeviceInfo majorDeviceInfo = this.mcc.getDeviceBaseInfo(sacHeader.getDeviceHeader().getModel(), req.getDeviceTelecom(), req.getDeviceId(), req.getDeviceIdType());

		/**
		 * 필수 약관 동의여부 체크
		 */
		if (this.checkAgree(req.getAgreementList(), sacHeader.getTenantHeader().getTenantId())) {
			LOGGER.error("## 필수 약관 미동의");
			throw new RuntimeException("회원 가입 실패 - 필수 약관 미동의");
		}

		/**
		 * (IDP 연동) 무선회원 가입
		 */
		IDPReceiverM join4WapInfo = this.idpService.join4Wap(req.getDeviceId(), this.mcc.convertDeviceTelecom(req.getDeviceTelecom()));
		LOGGER.info("## join4Wap - Result Code : {}", join4WapInfo.getResponseHeader().getResult());
		LOGGER.info("## join4Wap - Result Text : {}", join4WapInfo.getResponseHeader().getResult_text());

		/**
		 * 무선회원 연동 성공 여부에 따라 분기
		 */
		if (StringUtils.equals(join4WapInfo.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_OK)) { // 정상가입

			LOGGER.info("## IDP 연동 성공 ==============================================");

			CreateUserRequest createUserRequest = new CreateUserRequest();

			/**
			 * 공통 정보 setting
			 */
			createUserRequest.setCommonRequest(this.getCommonRequest(sacHeader));

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
			userMbr.setUserID(req.getDeviceId()); // 회원 컴포넌트에서 새로운 MBR_ID 를 생성하여 넣는다.
			userMbr.setUserTelecom(req.getDeviceTelecom()); // 이동 통신사
			userMbr.setIsParent(req.getIsParent()); // 부모동의 여부
			userMbr.setRegDate(DateUtil.getToday("yyyyMMddHHmmss")); // 등록일시
			createUserRequest.setUserMbr(userMbr);
			LOGGER.info("## SC Request userMbr : {}", createUserRequest.getUserMbr().toString());

			/**
			 * SC 사용자 가입요청
			 */
			CreateUserResponse createUserResponse = this.userSCI.create(createUserRequest);
			LOGGER.info("## ResponseCode   : {}", createUserResponse.getCommonResponse().getResultCode());
			LOGGER.info("## ResponseMsg    : {}", createUserResponse.getCommonResponse().getResultMessage());
			LOGGER.info("## UserKey        : {}", createUserResponse.getUserKey());

			if (!StringUtils.equals(createUserResponse.getCommonResponse().getResultCode(), MemberConstants.RESULT_SUCCES)) {

				LOGGER.info("## 사용자 회원 가입 실패 ===========================");
				throw new RuntimeException("사용자 회원 가입 실패");

			}

			/**
			 * 휴대기기 등록.
			 */
			String deviceKey = this.createDeviceSubmodule(req, sacHeader, createUserResponse.getUserKey(), majorDeviceInfo);

			/**
			 * 결과 세팅
			 */
			response.setUserKey(createUserResponse.getUserKey());
			response.setDeviceKey(deviceKey);

		} else if (StringUtils.equals(join4WapInfo.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_ALREADY_JOIN)) { // 기가입
			LOGGER.info("## (기가입 상태) 이미 서비스에 등록한 MDN");

			/**
			 * (IDP 연동) 무선회원 해지
			 */
			LOGGER.info("## IDP 무선회원 해지 연동 Start =================");
			IDPReceiverM secedeUser4WapInfo = this.idpService.secedeUser4Wap(req.getDeviceId());
			LOGGER.info("## secedeUser4Wap - Result Code : {}", secedeUser4WapInfo.getResponseHeader().getResult());
			LOGGER.info("## secedeUser4Wap - Result Text : {}", secedeUser4WapInfo.getResponseHeader().getResult_text());

			throw new RuntimeException("IDP 무선회원 가입 실패");

		} else { // 기타

			LOGGER.info("## IDP 무선회원 가입 연동 실패");
			throw new RuntimeException("IDP 무선회원 가입 실패");

		}

		return response;

	}

	@Override
	public CreateByAgreementRes createByAgreementId(SacRequestHeader sacHeader, CreateByAgreementReq req) throws Exception {

		CreateByAgreementRes response = new CreateByAgreementRes();

		/**
		 * 필수 약관 동의여부 체크
		 */
		if (this.checkAgree(req.getAgreementList(), sacHeader.getTenantHeader().getTenantId())) {
			LOGGER.error("## 필수 약관 미동의");
			throw new RuntimeException("회원 가입 실패 - 필수 약관 미동의");
		}

		/**
		 * 이용동의 가입 데이타 setting
		 */
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("cmd", "TXAgreeUserIDP");
		param.put("key_type", "2"); // 1=IM통합서비스번호, 2=IM통합ID
		param.put("key", req.getUserId());
		param.put("join_sst_list", MemberConstants.SSO_SST_CD_TSTORE + ",TAC001^TAC002^TAC003^TAC004^TAC005," + DateUtil.getToday() + "," + DateUtil.getTime());
		param.put("ocb_join_code", "N"); // 통합포인트 가입 여부 Y=가입, N=미가입

		/**
		 * (통합 IDP 연동) 이용동의 가입
		 */
		ImIDPReceiverM agreeUserInfo = this.imIdpService.agreeUser(param);
		LOGGER.info("## Im Result Code   : {}", agreeUserInfo.getResponseHeader().getResult());
		LOGGER.info("## Im Result Text   : {}", agreeUserInfo.getResponseHeader().getResult_text());

		/**
		 * (통합 IDP 연동) 이용동의 가입 성공시....
		 * 
		 * TODO 통합 IDP 연동 가능한 테스트 ID가 없어서 무조건 성공으로 하드코딩함.
		 */
		if (StringUtils.equals(agreeUserInfo.getResponseHeader().getResult(), "1000X000")) {

			LOGGER.info("## IDP 연동 성공 ==============================================");

			LOGGER.info("## Im user_key      : {}", agreeUserInfo.getResponseBody().getUser_key());
			LOGGER.info("## Im im_int_svc_no : {}", agreeUserInfo.getResponseBody().getIm_int_svc_no());
			LOGGER.info("## Im user_tn       : {}", agreeUserInfo.getResponseBody().getUser_tn());
			LOGGER.info("## Im user_email    : {}", agreeUserInfo.getResponseBody().getUser_email());

			CreateUserRequest createUserRequest = new CreateUserRequest();

			/**
			 * 공통 정보 setting
			 */
			createUserRequest.setCommonRequest(this.getCommonRequest(sacHeader));

			/**
			 * 이용약관 정보 setting
			 */
			createUserRequest.setMbrClauseAgreeList(this.getAgreementInfo(req.getAgreementList()));

			/**
			 * 통합 ID 기본 프로파일 조회 (통합ID 회원) 프로파일 조회 - 이름, 생년월일
			 */
			ImIDPReceiverM profileInfo = this.imIdpService.userInfoIdpSearchServer(agreeUserInfo.getResponseBody().getIm_int_svc_no());
			LOGGER.info("## Im Result Code   : {}", profileInfo.getResponseHeader().getResult());
			LOGGER.info("## Im Result Text   : {}", profileInfo.getResponseHeader().getResult_text());

			/**
			 * SC 사용자 기본정보 setting
			 */
			UserMbr userMbr = new UserMbr();
			userMbr.setUserID(req.getUserId()); // 사용자 아이디
			userMbr.setUserEmail(agreeUserInfo.getResponseBody().getUser_email()); // 사용자 이메일
			userMbr.setUserPhone(agreeUserInfo.getResponseBody().getUser_tn()); // 사용자 전화번호
			userMbr.setUserName(profileInfo.getResponseBody().getUser_name()); // 사용자 이름
			userMbr.setUserBirthDay(profileInfo.getResponseBody().getUser_birthday()); // 사용자 생년월일
			userMbr.setImMbrNo(agreeUserInfo.getResponseBody().getUser_key()); // MBR_NO
			userMbr.setImSvcNo(agreeUserInfo.getResponseBody().getIm_int_svc_no()); // OneID 통합서비스 관리번호
			userMbr.setIsRealName(MemberConstants.USE_N); // 실명인증 여부
			userMbr.setUserType(MemberConstants.USER_TYPE_ONEID); // One ID 회원
			userMbr.setUserMainStatus(MemberConstants.MAIN_STATUS_NORMAL); // 정상
			userMbr.setUserSubStatus(MemberConstants.SUB_STATUS_NORMAL); // 정상
			userMbr.setIsRecvEmail(MemberConstants.USE_N); // 이메일 수신 여부 (AI-IS 로직 반영).
			userMbr.setIsParent(MemberConstants.USE_N); // 부모동의 여부 (AI-IS 로직 반영).
			userMbr.setRegDate(DateUtil.getToday("yyyyMMddHHmmss")); // 등록 일시
			createUserRequest.setUserMbr(userMbr);
			LOGGER.info("## SC Request userMbr : {}", createUserRequest.getUserMbr().toString());

			/**
			 * SC 사용자 가입요청
			 */
			CreateUserResponse createUserResponse = this.userSCI.create(createUserRequest);
			LOGGER.info("## ResponseCode   : {}", createUserResponse.getCommonResponse().getResultCode());
			LOGGER.info("## ResponseMsg    : {}", createUserResponse.getCommonResponse().getResultMessage());
			LOGGER.info("## UserKey        : {}", createUserResponse.getUserKey());

			if (!StringUtils.equals(createUserResponse.getCommonResponse().getResultCode(), MemberConstants.RESULT_SUCCES)) {

				LOGGER.info("## OneID 약관동의 가입 실패 ===========================");
				throw new RuntimeException("사용자 회원 가입 실패");

			}

			/**
			 * 결과 세팅
			 */
			response.setUserKey(createUserResponse.getUserKey());

		} else {

			LOGGER.error("## 통합 서비스 이용동의 가입 실패!!");
			throw new RuntimeException("통합 서비스 이용동의 가입 실패");

		}

		return response;

	}

	@Override
	public CreateByAgreementRes createByAgreementDevice(SacRequestHeader sacHeader, CreateByAgreementReq req) throws Exception {

		CreateByAgreementRes response = new CreateByAgreementRes();

		/**
		 * 모번호 조회 (989 일 경우만)
		 */
		req.setDeviceId(this.mcc.getOpmdMdnInfo(req.getDeviceId()));

		/**
		 * 단말등록시 필요한 기본 정보 세팅.
		 */
		MajorDeviceInfo majorDeviceInfo = this.mcc.getDeviceBaseInfo(sacHeader.getDeviceHeader().getModel(), req.getDeviceTelecom(), req.getDeviceId(), req.getDeviceIdType());

		/**
		 * 필수 약관 동의여부 체크
		 */
		if (this.checkAgree(req.getAgreementList(), sacHeader.getTenantHeader().getTenantId())) {
			LOGGER.error("## 필수 약관 미동의");
			throw new RuntimeException("회원 가입 실패 - 필수 약관 미동의");
		}

		/**
		 * 통합 IDP 연동을 위한.... Phone 정보 세팅.
		 */
		StringBuffer sbUserPhone = new StringBuffer();
		sbUserPhone.append(req.getDeviceId());
		sbUserPhone.append(",");
		sbUserPhone.append(ObjectUtils.toString(majorDeviceInfo.getImMngNum()));
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
		param.put("join_sst_list", MemberConstants.SSO_SST_CD_TSTORE + ",TAC001^TAC002^TAC003^TAC004^TAC005," + DateUtil.getToday() + "," + DateUtil.getTime());
		param.put("user_mdn_auth_key", this.idpRepository.makePhoneAuthKey(sbUserPhone.toString()));
		param.put("ocb_join_code", "N"); // 통합포인트 가입 여부 Y=가입, N=미가입
		LOGGER.info("## param : {}", param.entrySet());
		ImIDPReceiverM agreeUserInfo = this.imIdpService.agreeUser(param);
		LOGGER.info("## Im Result Code   : {}", agreeUserInfo.getResponseHeader().getResult());
		LOGGER.info("## Im Result Text   : {}", agreeUserInfo.getResponseHeader().getResult_text());

		/**
		 * 이용동의 가입 성공시
		 * 
		 * TODO 하드코딩 바꾸기.
		 */
		if (StringUtils.equals(agreeUserInfo.getResponseHeader().getResult(), "1000X000")) {

			LOGGER.info("## IDP 연동 성공 ==============================================");

			LOGGER.info("## Im user_key      : {}", agreeUserInfo.getResponseBody().getUser_key());
			LOGGER.info("## Im im_int_svc_no : {}", agreeUserInfo.getResponseBody().getIm_int_svc_no());
			LOGGER.info("## Im user_tn       : {}", agreeUserInfo.getResponseBody().getUser_tn());
			LOGGER.info("## Im user_email    : {}", agreeUserInfo.getResponseBody().getUser_email());

			CreateUserRequest createUserRequest = new CreateUserRequest();

			/**
			 * 공통 정보 setting
			 */
			createUserRequest.setCommonRequest(this.getCommonRequest(sacHeader));

			/**
			 * 이용약관 정보 setting
			 */
			createUserRequest.setMbrClauseAgreeList(this.getAgreementInfo(req.getAgreementList()));

			/**
			 * 통합 ID 기본 프로파일 조회 (통합ID 회원) 프로파일 조회 - 이름, 생년월일
			 */
			ImIDPReceiverM profileInfo = this.imIdpService.userInfoIdpSearchServer(agreeUserInfo.getResponseBody().getIm_int_svc_no());
			LOGGER.info("## Im Result Code   : {}", profileInfo.getResponseHeader().getResult());
			LOGGER.info("## Im Result Text   : {}", profileInfo.getResponseHeader().getResult_text());

			/**
			 * SC 사용자 기본정보 setting
			 */
			UserMbr userMbr = new UserMbr();
			userMbr.setUserID(req.getUserId()); // 사용자 아이디
			userMbr.setUserEmail(agreeUserInfo.getResponseBody().getUser_email()); // 사용자 이메일
			userMbr.setUserPhone(agreeUserInfo.getResponseBody().getUser_tn()); // 사용자 전화번호
			userMbr.setUserName(profileInfo.getResponseBody().getUser_name()); // 사용자 이름
			userMbr.setUserBirthDay(profileInfo.getResponseBody().getUser_birthday()); // 사용자 생년월일
			userMbr.setImMbrNo(agreeUserInfo.getResponseBody().getUser_key()); // MBR_NO
			userMbr.setImSvcNo(agreeUserInfo.getResponseBody().getIm_int_svc_no()); // 통합 서비스 관리 번호
			userMbr.setIsRealName(MemberConstants.USE_N); // 실명인증 여부
			userMbr.setUserType(MemberConstants.USER_TYPE_ONEID); // One ID 회원
			userMbr.setUserMainStatus(MemberConstants.MAIN_STATUS_NORMAL); // 정상
			userMbr.setUserSubStatus(MemberConstants.SUB_STATUS_NORMAL); // 정상
			userMbr.setUserTelecom(majorDeviceInfo.getDeviceTelecom()); // 이동 통신사
			userMbr.setIsRecvEmail(MemberConstants.USE_N); // Email 수신여부 (AI-IS 로직 반영).
			userMbr.setIsParent(MemberConstants.USE_N); // 부모동의 여부 (AI-IS 로직 반영).
			userMbr.setRegDate(DateUtil.getToday("yyyyMMddHHmmss")); // 등록 일시
			createUserRequest.setUserMbr(userMbr);
			LOGGER.info("## SC Request userMbr : {}", createUserRequest.getUserMbr().toString());

			/**
			 * SC 사용자 가입요청
			 */
			CreateUserResponse createUserResponse = this.userSCI.create(createUserRequest);
			LOGGER.info("## ResponseCode   : {}", createUserResponse.getCommonResponse().getResultCode());
			LOGGER.info("## ResponseMsg    : {}", createUserResponse.getCommonResponse().getResultMessage());
			LOGGER.info("## UserKey        : {}", createUserResponse.getUserKey());

			if (!StringUtils.equals(createUserResponse.getCommonResponse().getResultCode(), MemberConstants.RESULT_SUCCES)) {

				LOGGER.info("## OneID 약관동의 가입 실패 ===========================");
				throw new RuntimeException("사용자 회원 가입 실패");

			}

			/**
			 * 휴대기기 등록.
			 */
			String deviceKey = this.createDeviceSubmodule(req, sacHeader, createUserResponse.getUserKey(), majorDeviceInfo);

			/**
			 * 결과 세팅
			 */
			response.setUserKey(createUserResponse.getUserKey());
			response.setDeviceKey(deviceKey);

		} else {

			LOGGER.error("## 통합 서비스 이용동의 가입 실패!!");
			throw new RuntimeException("통합 서비스 이용동의 가입 실패");

		}

		return response;

	}

	@Override
	public CreateBySimpleRes createBySimpleId(SacRequestHeader sacHeader, CreateBySimpleReq req) throws Exception {

		CreateBySimpleRes response = new CreateBySimpleRes();

		/**
		 * IDP 중복 아이디 체크및 6개월 이내 동일 가입요청 체크.
		 */
		this.checkDuplicateId(req.getUserId());

		/**
		 * IDP - 간편회원가입
		 */
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("user_id", URLEncoder.encode(req.getUserId(), "UTF-8"));
		param.put("user_passwd", URLEncoder.encode(req.getUserPw(), "UTF-8"));
		param.put("user_email", URLEncoder.encode(req.getUserEmail(), "UTF-8"));
		LOGGER.info("## param : {}", param.entrySet());

		/**
		 * IDP 간편회원 가입 연동
		 */
		IDPReceiverM simpleJoinInfo = this.idpService.simpleJoin(param);
		LOGGER.info("## Im Result Code   : {}", simpleJoinInfo.getResponseHeader().getResult());
		LOGGER.info("## Im Result Text   : {}", simpleJoinInfo.getResponseHeader().getResult_text());
		if (StringUtils.equals(simpleJoinInfo.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_OK)) { // 정상가입

			LOGGER.info("## IDP 간편가입 연동 성공 ==============================================");
			LOGGER.info("## MBR_NO : {}", simpleJoinInfo.getResponseBody().getUser_key());

			CreateUserRequest createUserRequest = new CreateUserRequest();

			/**
			 * 공통 정보 setting
			 */
			createUserRequest.setCommonRequest(this.getCommonRequest(sacHeader));

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
			userMbr.setIsRecvEmail(MemberConstants.USE_N); // 이메일 수신 여부 (AI-IS 로직 반영).
			userMbr.setIsParent(MemberConstants.USE_N); // 부모 동의 여부 (AI-IS 로직 반영).
			userMbr.setRegDate(DateUtil.getToday("yyyyMMddHHmmss")); // 등록 일시
			createUserRequest.setUserMbr(userMbr);
			LOGGER.info("## SC Request userMbr : {}", createUserRequest.getUserMbr().toString());

			/**
			 * SC 사용자 가입요청
			 */
			CreateUserResponse createUserResponse = this.userSCI.create(createUserRequest);
			LOGGER.info("## ResponseCode   : {}", createUserResponse.getCommonResponse().getResultCode());
			LOGGER.info("## ResponseMsg    : {}", createUserResponse.getCommonResponse().getResultMessage());
			LOGGER.info("## UserKey        : {}", createUserResponse.getUserKey());

			if (!StringUtils.equals(createUserResponse.getCommonResponse().getResultCode(), MemberConstants.RESULT_SUCCES)) {

				LOGGER.info("## 간편 가입 실패 ===========================");
				throw new RuntimeException("사용자 회원 가입 실패");

			}

			/**
			 * 결과 세팅
			 */
			response.setUserKey(createUserResponse.getUserKey());

		} else {

			LOGGER.info("## IDP - 간편회원가입 실패~!!!");
			throw new RuntimeException("IDP - 간편회원가입 실패");

		}

		return response;
	}

	@Override
	public CreateBySimpleRes createBySimpleDevice(SacRequestHeader sacHeader, CreateBySimpleReq req) throws Exception {

		CreateBySimpleRes response = new CreateBySimpleRes();

		/**
		 * IDP 중복 아이디 체크및 6개월 이내 동일 가입요청 체크.
		 */
		this.checkDuplicateId(req.getUserId());

		/**
		 * 모번호 조회 (989 일 경우만)
		 */
		req.setDeviceId(this.mcc.getOpmdMdnInfo(req.getDeviceId()));

		/**
		 * 단말등록시 필요한 기본 정보 세팅.
		 */
		MajorDeviceInfo majorDeviceInfo = this.mcc.getDeviceBaseInfo(sacHeader.getDeviceHeader().getModel(), req.getDeviceTelecom(), req.getDeviceId(), req.getDeviceIdType());

		/**
		 * 통합 IDP 연동을 위한.... Phone 정보 세팅.
		 */
		StringBuffer sbUserPhone = new StringBuffer();
		sbUserPhone.append(req.getDeviceId());
		sbUserPhone.append(",");
		sbUserPhone.append(ObjectUtils.toString(majorDeviceInfo.getImMngNum()));
		sbUserPhone.append(",");
		sbUserPhone.append(majorDeviceInfo.getUacd());
		sbUserPhone.append(",");
		sbUserPhone.append(this.mcc.convertDeviceTelecom(majorDeviceInfo.getDeviceTelecom()));
		LOGGER.info("## sbUserPhone : {}", sbUserPhone.toString());

		/**
		 * IDP - 간편회원가입 setting
		 */
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("user_id", URLEncoder.encode(req.getUserId(), "UTF-8"));
		param.put("user_passwd", URLEncoder.encode(req.getUserPw(), "UTF-8"));
		param.put("user_email", URLEncoder.encode(req.getUserEmail(), "UTF-8"));
		param.put("user_phone", sbUserPhone.toString());
		param.put("phone_auth_key", this.idpRepository.makePhoneAuthKey(sbUserPhone.toString()));
		LOGGER.info("## param : {}", param.entrySet());

		/**
		 * IDP 간편 회원가입 연동
		 */
		IDPReceiverM simpleJoinInfo = this.idpService.simpleJoin(param);
		LOGGER.info("## Im Result Code   : {}", simpleJoinInfo.getResponseHeader().getResult());
		LOGGER.info("## Im Result Text   : {}", simpleJoinInfo.getResponseHeader().getResult_text());
		if (StringUtils.equals(simpleJoinInfo.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_OK)) { // 정상가입

			LOGGER.info("## IDP 간편가입 연동 성공 ==============================================");
			LOGGER.info("## MBR_NO : {}", simpleJoinInfo.getResponseBody().getUser_key());

			CreateUserRequest createUserRequest = new CreateUserRequest();

			/**
			 * 공통 정보 setting
			 */
			createUserRequest.setCommonRequest(this.getCommonRequest(sacHeader));

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
			userMbr.setUserTelecom(majorDeviceInfo.getDeviceTelecom()); // 이동 통신사
			userMbr.setIsRecvEmail(MemberConstants.USE_N); // 이메일 수신 여부 (AI-IS 로직 반영).
			userMbr.setIsParent(MemberConstants.USE_N); // 부모 동의 여부 (AI-IS 로직 반영).
			userMbr.setRegDate(DateUtil.getToday("yyyyMMddHHmmss")); // 등록 일시
			createUserRequest.setUserMbr(userMbr);
			LOGGER.info("## SC Request userMbr : {}", createUserRequest.getUserMbr().toString());

			/**
			 * SC 사용자 가입요청
			 */
			CreateUserResponse createUserResponse = this.userSCI.create(createUserRequest);
			LOGGER.info("## ResponseCode   : {}", createUserResponse.getCommonResponse().getResultCode());
			LOGGER.info("## ResponseMsg    : {}", createUserResponse.getCommonResponse().getResultMessage());
			LOGGER.info("## UserKey        : {}", createUserResponse.getUserKey());

			if (!StringUtils.equals(createUserResponse.getCommonResponse().getResultCode(), MemberConstants.RESULT_SUCCES)) {

				LOGGER.info("## 간편 가입 실패 ===========================");
				throw new RuntimeException("사용자 회원 가입 실패");

			}

			/**
			 * 휴대기기 등록.
			 */
			String deviceKey = this.createDeviceSubmodule(req, sacHeader, createUserResponse.getUserKey(), majorDeviceInfo);

			/**
			 * 결과 세팅
			 */
			response.setUserKey(createUserResponse.getUserKey());
			response.setDeviceKey(deviceKey);

		} else {

			LOGGER.info("## IDP - 간편회원가입 실패~!!!");
			throw new RuntimeException("IDP - 간편회원가입 실패");

		}

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
	 * @throws Exception
	 *             Exception
	 */
	private boolean checkAgree(List<AgreementInfo> agreementList, String tenantId) throws Exception {

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
		LOGGER.info("## DB 필수약관목록 : {}", sortDbAgreeInfo);

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
			if (StringUtils.equals(info.getIsExtraAgreement(), MemberConstants.USE_Y)) { // 약관 동의한것만 비교대상으로 세팅
				sortAgreeInfo.append(info.getExtraAgreementId());
			}
		}
		LOGGER.info("## DB 요청약관목록 : {}", sortAgreeInfo);

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
	 * SC 공통정보 setting.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            SacRequestHeader
	 * @return CommonRequest
	 */
	private CommonRequest getCommonRequest(SacRequestHeader sacHeader) {

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(sacHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(sacHeader.getTenantHeader().getTenantId());
		LOGGER.info("## SC Request 공통 정보 : {}", commonRequest.toString());

		return commonRequest;
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
	 * @throws Exception
	 *             Exception
	 */
	private String createDeviceSubmodule(Object obj, SacRequestHeader sacHeader, String userKey, MajorDeviceInfo majorDeviceInfo) throws Exception {

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
			deviceInfo.setDeviceTelecom(req.getDeviceTelecom()); // 이동 통신사
			deviceInfo.setDeviceModelNo(sacHeader.getDeviceHeader().getModel()); // 단말 모델
			deviceInfo.setDeviceId(req.getDeviceId()); // 기기 ID
			deviceInfo.setDeviceIdType(req.getDeviceIdType()); // 기기 ID 타입
			deviceInfo.setJoinId(req.getJoinId()); // 가입 채널 코드
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
			deviceInfo.setDeviceTelecom(req.getDeviceTelecom()); // 이동 통신사
			deviceInfo.setDeviceModelNo(sacHeader.getDeviceHeader().getModel()); // 단말 모델
			deviceInfo.setDeviceId(req.getDeviceId()); // 기기 ID
			deviceInfo.setDeviceIdType(req.getDeviceIdType()); // 기기 ID 타입
			deviceInfo.setJoinId(req.getJoinId()); // 가입 채널 코드
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
			deviceInfo.setDeviceTelecom(req.getDeviceTelecom()); // 이동 통신사
			deviceInfo.setDeviceModelNo(sacHeader.getDeviceHeader().getModel()); // 단말 모델
			deviceInfo.setDeviceId(req.getDeviceId()); // 기기 ID
			deviceInfo.setDeviceIdType(req.getDeviceIdType()); // 기기 ID 타입
			deviceInfo.setJoinId(req.getJoinId()); // 가입 채널 코드
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
			String deviceKey = this.mcc.insertDeviceInfo(sacHeader.getTenantHeader().getSystemId(), sacHeader.getTenantHeader().getTenantId(), userKey, deviceInfo);
			LOGGER.info("## 휴대기기 등록 DeviceKey : {}", deviceKey);
			return deviceKey;

		} catch (Exception e) {
			throw new RuntimeException("## 휴대기기 등록 실패 ================");
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
		 * SKT 회원관리번호 추가.
		 */
		if (!StringUtils.equals(ObjectUtils.toString(majorDeviceInfo.getImMngNum()), "")) {
			LOGGER.info("## SKT 회원관리번호 추가.");
			DeviceExtraInfo imMngNum = new DeviceExtraInfo();
			imMngNum.setExtraProfile(MemberConstants.DEVICE_EXTRA_IMMNGNUM);
			imMngNum.setExtraProfileValue(majorDeviceInfo.getImMngNum());
			deviceExtraInfoList.add(imMngNum);
		}

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
	 * IDP 중복 아이디 체크및 6개월 이내 동일 가입요청 체크.
	 * </pre>
	 * 
	 * @param userId
	 *            유저 아이디
	 * @throws Exception
	 *             Exception
	 */
	private void checkDuplicateId(String userId) throws Exception {

		LOGGER.info("## ID 중복확인 =================================");
		/**
		 * (IDP 연동) IDP - ID 중복확인
		 */
		IDPReceiverM checkDupIdInfo = this.idpService.checkDupID(URLEncoder.encode(userId, "UTF-8"));
		LOGGER.info("## checkDupID - Result Code : {}", checkDupIdInfo.getResponseHeader().getResult());
		LOGGER.info("## checkDupID - Result Text : {}", checkDupIdInfo.getResponseHeader().getResult_text());

		if (StringUtils.equals(checkDupIdInfo.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_OK)) {

			/**
			 * 해지 후 6개월이내 동일한 ID로 가입요청 불가
			 * 
			 * TODO 테이블이 이관되어있지 않아서 확인 요청함. 처리 완료되면 SC 연동하여 처리 할것. (SC API 요청해야함.)
			 */
			// if (count > 0) {
			//
			// throw new RuntimeException("해지 후 6개월이내 동일한 ID로 가입요청 불가");
			//
			// }

		} else {

			throw new RuntimeException(checkDupIdInfo.getResponseHeader().getResult_text());

		}

	}

}
