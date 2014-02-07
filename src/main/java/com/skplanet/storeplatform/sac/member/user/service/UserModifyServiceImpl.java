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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.idp.vo.IDPReceiverM;
import com.skplanet.storeplatform.external.client.idp.vo.ImIDPReceiverM;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.member.client.common.vo.MbrAuth;
import com.skplanet.storeplatform.member.client.common.vo.MbrLglAgent;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateRealNameRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateRealNameResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbr;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateRealNameReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateRealNameRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateTermsAgreementReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateTermsAgreementRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyEmailReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyEmailRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyPasswordReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyPasswordRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyTermsAgreementReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyTermsAgreementRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.idp.repository.IDPRepository;
import com.skplanet.storeplatform.sac.member.common.idp.service.IDPService;
import com.skplanet.storeplatform.sac.member.common.idp.service.ImIDPService;

/**
 * 회원 정보 수정 서비스 (CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 1. 24. Updated by : 심대진, 다모아 솔루션.
 */
@Service
public class UserModifyServiceImpl implements UserModifyService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserModifyServiceImpl.class);

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
	public ModifyRes modify(SacRequestHeader sacHeader, ModifyReq req) {

		ModifyRes response = new ModifyRes();

		/**
		 * TODO userAuthKey 없을경우 판단하여 SC만 업데이트 처리 할것..~!!!
		 */
		if (StringUtils.equals(req.getUserAuthKey(), "")) {
			throw new StorePlatformException("TODO UserAuthKey 없을때 로직 미구현됨..... SC 컴포넌트만 업데이트 하는걸로.....해야함... ");
		}

		/**
		 * 회원 정보 조회.
		 */
		UserInfo userInfo = this.mcc.getUserBaseInfo("userKey", req.getUserKey(), sacHeader);

		/**
		 * 통합서비스번호 존재 유무로 통합회원인지 기존회원인지 판단한다. (UserType보다 더 신뢰함.) 회원 타입에 따라서 [통합IDP, 기존IDP] 연동처리 한다.
		 */
		LOGGER.info("## 사용자 타입  : {}", userInfo.getUserType());
		LOGGER.info("## 통합회원번호 : {}", StringUtils.isNotEmpty(userInfo.getImSvcNo()));
		if (StringUtils.isNotEmpty(userInfo.getImSvcNo())) {

			LOGGER.info("## ====================================================");
			LOGGER.info("## One ID 통합회원 [{}]", userInfo.getUserName());
			LOGGER.info("## ====================================================");

			/**
			 * 통합 IDP 연동 정보 setting.
			 */
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("user_auth_key", req.getUserAuthKey()); // IDP 인증키
			param.put("key_type", "1"); // updateUserInfo 메서드에 하드코딩 되어 있음.
			param.put("key", userInfo.getImSvcNo()); // 통합서비스 관리번호
			param.put("user_type", "1"); // 가입자 유형코드 (1:개인)
			param.put("is_biz_auth", "N");
			param.put("udt_type_cd", "4"); // 업데이트 구분 코드 (1:TN, 2:EM, 3:TN+EM, 4:부가정보)

			param.put("user_calendar", req.getUserCalendar()); // 양력1, 음력2
			param.put("user_zipcode", req.getUserZip()); // 우편번호
			param.put("user_address", req.getUserAddress()); // 주소
			param.put("user_address2", req.getUserDetailAddress()); // 상세주소

			/**
			 * 통합IDP 회원정보 수정 연동 (cmd - TXUpdateUserInfoIDP)
			 */
			this.imIdpService.updateUserInfo(param);

			/**
			 * 통합IDP 회원정보 조회 연동 (cmd - (cmd - findCommonProfileForServerIDP))
			 */
			ImIDPReceiverM profileInfo = this.imIdpService.userInfoIdpSearchServer(userInfo.getImSvcNo());
			LOGGER.info("## IDP searchUserInfo Code : {}", profileInfo.getResponseHeader().getResult());
			LOGGER.info("## IDP searchUserInfo Text : {}", profileInfo.getResponseHeader().getResult_text());
			LOGGER.info("## IDP searchUserInfo Text : {}", profileInfo.getResponseBody().getUser_sex());
			LOGGER.info("## IDP searchUserInfo Text : {}", profileInfo.getResponseBody().getUser_calendar());
			LOGGER.info("## IDP searchUserInfo Text : {}", profileInfo.getResponseBody().getUser_birthday());
			LOGGER.info("## IDP searchUserInfo Text : {}", profileInfo.getResponseBody().getUser_zipcode());
			LOGGER.info("## IDP searchUserInfo Text : {}", profileInfo.getResponseBody().getUser_address());
			LOGGER.info("## IDP searchUserInfo Text : {}", profileInfo.getResponseBody().getUser_address2());

			/**
			 * SC 회원 수정.
			 */
			String userKey = this.updateUser(sacHeader, req);
			response.setUserKey(userKey);

		} else {

			LOGGER.info("## ====================================================");
			LOGGER.info("## 기존 IDP 회원 [{}]", userInfo.getUserName());
			LOGGER.info("## ====================================================");

			/**
			 * IDP 연동 정보 setting.
			 */
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("user_auth_key", req.getUserAuthKey()); // IDP 인증키
			param.put("key_type", "2");
			param.put("key", userInfo.getImMbrNo()); // MBR_NO
			param.put("user_sex", req.getUserSex()); // 성별
			param.put("user_birthday", req.getUserBirthDay()); // 생년월일
			param.put("user_calendar", req.getUserCalendar()); // 양력1, 음력2
			param.put("user_zipcode", req.getUserZip()); // 우편번호
			param.put("user_address", req.getUserAddress()); // 주소
			param.put("user_address2", req.getUserDetailAddress()); // 상세주소
			param.put("user_tel", req.getUserPhone()); // 사용자 연락처

			/**
			 * IDP 회원정보 수정 연동 (cmd - modifyProfile)
			 */
			this.idpService.modifyProfile(param);

			/**
			 * IDP 회원정보 조회 연동 (cmd - findCommonProfileForServer)
			 */
			IDPReceiverM searchUserInfo = this.idpService.searchUserCommonInfo("3", userInfo.getImMbrNo());
			LOGGER.info("## IDP searchUserInfo Code : {}", searchUserInfo.getResponseHeader().getResult());
			LOGGER.info("## IDP searchUserInfo Text : {}", searchUserInfo.getResponseHeader().getResult_text());
			LOGGER.info("## IDP searchUserInfo Text : {}", searchUserInfo.getResponseBody().getUser_sex());
			LOGGER.info("## IDP searchUserInfo Text : {}", searchUserInfo.getResponseBody().getUser_calendar());
			LOGGER.info("## IDP searchUserInfo Text : {}", searchUserInfo.getResponseBody().getUser_birthday());
			LOGGER.info("## IDP searchUserInfo Text : {}", searchUserInfo.getResponseBody().getUser_zipcode());
			LOGGER.info("## IDP searchUserInfo Text : {}", searchUserInfo.getResponseBody().getUser_address());
			LOGGER.info("## IDP searchUserInfo Text : {}", searchUserInfo.getResponseBody().getUser_address2());
			LOGGER.info("## IDP searchUserInfo Text : {}", searchUserInfo.getResponseBody().getUser_tel());

			/**
			 * SC 회원 수정.
			 */
			String userKey = this.updateUser(sacHeader, req);
			response.setUserKey(userKey);

		}

		return response;
	}

	@Override
	public ModifyPasswordRes modifyPassword(SacRequestHeader sacHeader, ModifyPasswordReq req) {

		ModifyPasswordRes response = new ModifyPasswordRes();

		return response;
	}

	@Override
	public ModifyEmailRes modifyEmail(SacRequestHeader sacHeader, ModifyEmailReq req) {

		ModifyEmailRes response = new ModifyEmailRes();

		return response;
	}

	@Override
	public CreateTermsAgreementRes createTermsAgreement(SacRequestHeader sacHeader, CreateTermsAgreementReq req) {

		CreateTermsAgreementRes response = new CreateTermsAgreementRes();

		return response;
	}

	@Override
	public ModifyTermsAgreementRes modifyTermsAgreement(SacRequestHeader sacHeader, ModifyTermsAgreementReq req) {

		ModifyTermsAgreementRes response = new ModifyTermsAgreementRes();

		return response;
	}

	@Override
	public CreateRealNameRes createRealName(SacRequestHeader sacHeader, CreateRealNameReq req) {

		/**
		 * 회원 정보 조회.
		 */
		UserInfo userInfo = this.mcc.getUserBaseInfo("userKey", req.getUserKey(), sacHeader);

		/**
		 * 통합서비스번호 존재 유무로 통합회원인지 기존회원인지 판단한다. (UserType보다 더 신뢰함.) 회원 타입에 따라서 [통합IDP, 기존IDP] 연동처리 한다.
		 * 
		 * 통합회원일경우만 처리한다.
		 */
		LOGGER.info("## 사용자 타입  : {}", userInfo.getUserType());
		LOGGER.info("## 통합회원번호 : {}", StringUtils.isNotEmpty(userInfo.getImSvcNo()));
		if (StringUtils.isNotEmpty(userInfo.getImSvcNo())) {

			/**
			 * TODO userAuthKey 없을경우 판단하여 SC만 업데이트 처리 할것..~!!!
			 */
			if (StringUtils.equals(req.getUserAuthKey(), "")) {
				throw new StorePlatformException("TODO UserAuthKey 없을때 로직 미구현됨..... SC 컴포넌트만 업데이트 하는걸로.....해야함... ");
			}

			LOGGER.info("## ====================================================");
			LOGGER.info("## One ID 통합회원일 경우만 통합 IDP 연동을 한다.");
			LOGGER.info("## ====================================================");

			/**
			 * 실명인증 대상 여부에 따라 분기 처리. (OWN=본인, PARENT=법정대리인)
			 */
			if (StringUtils.equals(req.getIsOwn(), MemberConstants.AUTH_TYPE_OWN)) { // 본인

				/**
				 * 통합IDP 회원정보 조회 연동 (cmd - findCommonProfileForServerIDP)
				 * 
				 * TODO 인증여부, 생년월일, CI 등...비교후에 같지 않으면 에러 발생한다....왜...???
				 * 
				 */
				ImIDPReceiverM profileInfo = this.imIdpService.userInfoIdpSearchServer(userInfo.getImSvcNo());
				LOGGER.info("## IDP searchUserInfo is_rname_auth : {}", profileInfo.getResponseBody().getIs_rname_auth()); // 비교대상
				LOGGER.info("## IDP searchUserInfo user_birthday : {}", profileInfo.getResponseBody().getUser_birthday()); // 비교대상
				LOGGER.info("## IDP searchUserInfo ci            : {}", profileInfo.getResponseBody().getUser_ci()); // 비교대상

				LOGGER.info("## IDP searchUserInfo di            : {}", profileInfo.getResponseBody().getUser_di()); // DI

				// 인증유형코드
				// 1: 휴대폰 인증
				// 2: 이메일 즉시인증,
				// 3: 이메일 후인증,
				// 4: KMC 기인증
				LOGGER.info("## IDP searchUserInfo Text : {}", profileInfo.getResponseBody().getAuth_type());

				// 가입자이름 (법인일경우가입한사람의이름)
				LOGGER.info("## IDP searchUserInfo Text : {}", profileInfo.getResponseBody().getUser_name());

				// 이메일인증여부
				// (이메일로발송한인증번호에대해확인완료)
				// 승인=Y, 미승인=N
				LOGGER.info("## IDP searchUserInfo Text : {}", profileInfo.getResponseBody().getIs_email_auth());

				/**
				 * 통합IDP 실명인증 변경 연동 (cmd = TXUpdateUserNameIDP)
				 * 
				 * is_rname_auth, user_birthday, user_ci 가 동일해야만 업데이트
				 * 
				 * 실명인증을 받았다 하더라도 개명 등의 이유로 이름은 변경될 수 있다.
				 */
				HashMap requestUserMap = new HashMap();
				// String rname_auth_mns_code=(auth_type!=null && auth_type.length()>1 ? auth_type.substring(1):""); //
				// KMC, IPIN

				// sn_auth_key

				String snAuthKey = this.idpRepository.makeSnAuthKey(userInfo.getUserName(), userInfo.getUserId());
				ImIDPReceiverM updateUserNameInfo = this.imIdpService.updateUserName(userInfo.getImSvcNo(), req.getUserName(), req.getUserBirthDay(), snAuthKey, req.getUserAuthKey(), "1", req.getUserCi(), req.getUserDi(), requestUserMap);

			} else { // 법정대리인

			}

		}

		/**
		 * SC 실명인증 연동.
		 */
		// String userKey = this.updateRealName(sacHeader, req);

		/**
		 * 결과 정보 setting.
		 */
		CreateRealNameRes response = new CreateRealNameRes();
		// response.setUserKey(userKey);
		response.setUserKey("");

		return response;
	}

	/**
	 * <pre>
	 * 사용자 기본정보 수정.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param req
	 *            Request Value Object
	 * @return String (userKey)
	 */
	private String updateUser(SacRequestHeader sacHeader, ModifyReq req) {

		UpdateUserRequest updateUserRequest = new UpdateUserRequest();

		/**
		 * 공통 정보 setting.
		 */
		updateUserRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));

		/**
		 * 사용자 기본정보 setting.
		 */
		updateUserRequest.setUserMbr(this.getUserMbr(req));

		/**
		 * SC 사용자 회원 기본정보 수정 요청.
		 */
		UpdateUserResponse updateUserResponse = this.userSCI.updateUser(updateUserRequest);
		if (updateUserResponse.getUserKey() == null || StringUtils.equals(updateUserResponse.getUserKey(), "")) {
			throw new StorePlatformException("SAC_MEM_0002", "userKey");
		}

		/**
		 * 결과 세팅
		 */
		return updateUserResponse.getUserKey();

	}

	/**
	 * <pre>
	 * 사용자 기본정보 setting..
	 * </pre>
	 * 
	 * @param req
	 *            ModifyReq
	 * @return UserMbr
	 */
	private UserMbr getUserMbr(ModifyReq req) {

		UserMbr userMbr = new UserMbr();
		userMbr.setUserKey(req.getUserKey());

		/**
		 * 사용자 연락처 (Sync 대상)
		 */
		if (!StringUtils.equals(req.getUserPhone(), "")) {
			userMbr.setUserPhone(req.getUserPhone());
		}

		/**
		 * SMS 수신 여부
		 */
		if (!StringUtils.equals(req.getIsRecvSms(), "")) {
			userMbr.setIsRecvSMS(req.getIsRecvSms());
		}

		/**
		 * 이메일 수신여부
		 */
		if (!StringUtils.equals(req.getIsRecvEmail(), "")) {
			userMbr.setIsRecvEmail(req.getIsRecvEmail());
		}

		/**
		 * 사용자 성별 (Sync 대상)
		 */
		if (!StringUtils.equals(req.getUserSex(), "")) {
			userMbr.setUserSex(req.getUserSex());
		}

		/**
		 * 사용자 생년월일 (Sync 대상)
		 */
		if (!StringUtils.equals(req.getUserBirthDay(), "")) {
			userMbr.setUserBirthDay(req.getUserBirthDay());
		}

		LOGGER.info("## SC Request 사용자 기본정보 : {}", userMbr.toString());

		return userMbr;
	}

	/**
	 * <pre>
	 * 실명인증 연동 처리.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param req
	 *            Request Value Object
	 * @return String (userKey)
	 */
	private String updateRealName(SacRequestHeader sacHeader, CreateRealNameReq req) {

		/**
		 * 실명인증 setting.
		 */
		UpdateRealNameRequest updateRealNameRequest = new UpdateRealNameRequest();
		updateRealNameRequest = new UpdateRealNameRequest();
		updateRealNameRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));
		updateRealNameRequest.setIsOwn(req.getIsOwn());
		updateRealNameRequest.setIsRealName(req.getIsRealName());
		updateRealNameRequest.setUserKey(req.getUserKey());

		/**
		 * 실명인증 대상 여부에 따라 분기 처리. (OWN=본인, PARENT=법정대리인)
		 */
		if (StringUtils.equals(req.getIsOwn(), MemberConstants.AUTH_TYPE_OWN)) {

			/**
			 * 실명인증 (본인)
			 */
			MbrAuth mbrAuth = new MbrAuth();
			mbrAuth.setTenantID(sacHeader.getTenantHeader().getTenantId()); // 테넌트 아이디
			mbrAuth.setBirthDay(req.getUserBirthDay()); // 생년월일
			mbrAuth.setTelecom(req.getDeviceTelecom()); // 이동 통신사
			mbrAuth.setPhone(req.getUserPhone()); // 사용자 휴대폰
			mbrAuth.setName(req.getUserName()); // 사용자 이름
			mbrAuth.setSex(req.getUserSex()); // 사용자 성별
			mbrAuth.setCi(req.getUserCi()); // CI
			mbrAuth.setDi(req.getUserDi()); // DI
			mbrAuth.setRealNameSite(req.getRealNameSite()); // 실명인증 사이트 코드
			mbrAuth.setRealNameDate(req.getRealNameDate()); // 실명인증 일시
			mbrAuth.setRealNameMethod(req.getRealNameMethod()); // 실명인증 수단코드

			updateRealNameRequest.setUserMbrAuth(mbrAuth);

		} else {

			/**
			 * 실명인증 (법정대리인)
			 */
			MbrLglAgent mbrLglAgent = new MbrLglAgent();
			mbrLglAgent.setParentBirthDay(req.getUserBirthDay()); // 법정대리인 생년월일
			mbrLglAgent.setParentMDN(req.getUserPhone()); // 법정대리인 전화번호
			mbrLglAgent.setParentTelecom(req.getDeviceTelecom()); // 법정대리인 이동 통신사
			mbrLglAgent.setParentCI(req.getUserCi()); // 법정대리인 CI
			mbrLglAgent.setParentName(req.getUserName()); // 법정대리인 이름
			mbrLglAgent.setParentType(req.getParentType()); // 법정대리인 관계코드
			mbrLglAgent.setParentEmail(req.getParentEmail()); // 법정대리인 이메일
			mbrLglAgent.setParentRealNameDate(req.getRealNameDate()); // 법정대리인 실명인증 일시
			mbrLglAgent.setParentRealNameSite(req.getRealNameSite()); // 법정대리인 실명인증 사이트 코드
			mbrLglAgent.setParentRealNameMethod(req.getRealNameMethod()); // 법정대리인 실명인증 수단코드

			updateRealNameRequest.setMbrLglAgent(mbrLglAgent);

		}

		/**
		 * SC 실명인증정보 수정 연동.
		 */
		UpdateRealNameResponse updateRealNameResponse = this.userSCI.updateRealName(updateRealNameRequest);
		if (updateRealNameResponse.getUserKey() == null || StringUtils.equals(updateRealNameResponse.getUserKey(), "")) {
			throw new StorePlatformException("SAC_MEM_0002", "userKey");
		}

		return updateRealNameResponse.getUserKey();

	}

}
