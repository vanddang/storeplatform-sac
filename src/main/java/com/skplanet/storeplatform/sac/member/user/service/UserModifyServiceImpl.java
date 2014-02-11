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

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.idp.vo.IdpReceiverM;
import com.skplanet.storeplatform.external.client.idp.vo.ImIdpReceiverM;
import com.skplanet.storeplatform.external.client.idp.vo.ImIdpReceiverM.ResponseBody;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.common.vo.MbrAuth;
import com.skplanet.storeplatform.member.client.common.vo.MbrLglAgent;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserResponse;
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
import com.skplanet.storeplatform.sac.member.common.idp.repository.IdpRepository;
import com.skplanet.storeplatform.sac.member.common.idp.service.IdpService;
import com.skplanet.storeplatform.sac.member.common.idp.service.ImIdpService;

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
	private IdpService idpService;

	@Autowired
	private ImIdpService imIdpService;

	@Autowired
	private IdpRepository idpRepository;

	@Override
	public ModifyRes modify(SacRequestHeader sacHeader, ModifyReq req) {

		ModifyRes response = new ModifyRes();

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
			 * userAuthKey 가 정의된 값과 같은지 비교하여 연동 여부를 판단한다.
			 */
			if (this.mcc.isIdpConnect(req.getUserAuthKey())) {

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
				 * 통합IDP 회원정보 조회 연동 (cmd - findCommonProfileForServerIDP))
				 */
				ImIdpReceiverM profileInfo = this.imIdpService.userInfoIdpSearchServer(userInfo.getImSvcNo());
				LOGGER.info("## IDP searchUserInfo Code : {}", profileInfo.getResponseHeader().getResult());
				LOGGER.info("## IDP searchUserInfo Text : {}", profileInfo.getResponseHeader().getResult_text());
				LOGGER.info("## IDP searchUserInfo Text : {}", profileInfo.getResponseBody().getUser_sex());
				LOGGER.info("## IDP searchUserInfo Text : {}", profileInfo.getResponseBody().getUser_calendar());
				LOGGER.info("## IDP searchUserInfo Text : {}", profileInfo.getResponseBody().getUser_birthday());
				LOGGER.info("## IDP searchUserInfo Text : {}", profileInfo.getResponseBody().getUser_zipcode());
				LOGGER.info("## IDP searchUserInfo Text : {}", profileInfo.getResponseBody().getUser_address());
				LOGGER.info("## IDP searchUserInfo Text : {}", profileInfo.getResponseBody().getUser_address2());

			}

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
			 * userAuthKey 가 정의된 값과 같은지 비교하여 연동 여부를 판단한다.
			 */
			if (this.mcc.isIdpConnect(req.getUserAuthKey())) {

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
				IdpReceiverM searchUserInfo = this.idpService.searchUserCommonInfo("3", userInfo.getImMbrNo());
				LOGGER.info("## IDP searchUserInfo Code : {}", searchUserInfo.getResponseHeader().getResult());
				LOGGER.info("## IDP searchUserInfo Text : {}", searchUserInfo.getResponseHeader().getResult_text());
				LOGGER.info("## IDP searchUserInfo Text : {}", searchUserInfo.getResponseBody().getUser_sex());
				LOGGER.info("## IDP searchUserInfo Text : {}", searchUserInfo.getResponseBody().getUser_calendar());
				LOGGER.info("## IDP searchUserInfo Text : {}", searchUserInfo.getResponseBody().getUser_birthday());
				LOGGER.info("## IDP searchUserInfo Text : {}", searchUserInfo.getResponseBody().getUser_zipcode());
				LOGGER.info("## IDP searchUserInfo Text : {}", searchUserInfo.getResponseBody().getUser_address());
				LOGGER.info("## IDP searchUserInfo Text : {}", searchUserInfo.getResponseBody().getUser_address2());
				LOGGER.info("## IDP searchUserInfo Text : {}", searchUserInfo.getResponseBody().getUser_tel());
			}

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
		 * userAuthKey 가 정의된 값과 같은지 비교하여 연동 여부를 판단한다.
		 */
		if (this.mcc.isIdpConnect(req.getUserAuthKey())) {

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

				LOGGER.info("## ====================================================");
				LOGGER.info("## One ID 통합회원일 경우만 통합 IDP 연동을 한다.");
				LOGGER.info("## ====================================================");

				/**
				 * 실명인증 대상 여부에 따라 분기 처리. (OWN=본인, PARENT=법정대리인)
				 */
				if (StringUtils.equals(req.getIsOwn(), MemberConstants.AUTH_TYPE_OWN)) { // 본인

					/**
					 * 통합IDP 회원정보 조회 연동 (cmd - findCommonProfileForServerIDP)
					 */
					ImIdpReceiverM profileInfo = this.imIdpService.userInfoIdpSearchServer(userInfo.getImSvcNo());

					/**
					 * OnedID 조회 정보와 Request 로 받은 정보와 비교 로직 수행.
					 */
					String oneIdRealNameType = this.compareRealName(sacHeader, req, profileInfo);

					/**
					 * is_rname_auth, user_birthday, user_ci 가 동일해야만 업데이트
					 * 
					 * 실명인증을 받았다 하더라도 개명 등의 이유로 이름은 변경될 수 있다.
					 */
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("key", userInfo.getImSvcNo());
					param.put("user_auth_key", req.getUserAuthKey());
					param.put("user_name", req.getUserName());
					param.put("user_birthday", req.getUserBirthDay());
					param.put("user_sex", req.getUserSex()); // 성별 (M=남자, F=여자, N:미확인)
					param.put("sn_auth_key", this.idpRepository.makeSnAuthKey(req.getUserName(), req.getUserBirthDay()));
					param.put("rname_auth_mns_code", "1"); // 실명인증 수단 코드 1: 휴대폰, 2: 아이핀, 9:기타
					param.put("rname_auth_mbr_code", "10"); // 실명인증 회원 코드 10 :내국인, 20: 외국인
					param.put("rname_auth_type_cd", oneIdRealNameType); // 실명 인증 유형 코드 R=회원 개명 E=CI 기보유
					param.put("user_ci", req.getUserCi());
					param.put("user_di", req.getUserDi());
					param.put("rname_auth_date", req.getRealNameDate());
					LOGGER.info("### param : {}", param.toString());

					/**
					 * 통합IDP 실명인증 본인 연동 (cmd = TXUpdateUserNameIDP)
					 */
					if (StringUtils.isNotEmpty(oneIdRealNameType)) {
						this.imIdpService.updateUserName(param);
					}

				} else { // 법정대리인

					Map<String, Object> param = new HashMap<String, Object>();
					param.put("key", userInfo.getImSvcNo());
					param.put("user_auth_key", req.getUserAuthKey());
					param.put("parent_type", "0"); // 법정대리인관계코드 (0:부, 1:모, 2:기타)
					param.put("parent_rname_auth_type", "1"); // 법정대리인실명인증수단코드 (1:휴대폰 본인인증, , 3:IPIN, 6:이메일 (외국인 법정대리인
															  // 인증))
					param.put("parent_rname_auth_key", req.getUserCi()); // 법정대리인 실명인증 값 (CI) [외국인은 null 로....]
					param.put("parent_name", req.getUserName());
					param.put("parent_birthday", req.getUserBirthDay());
					param.put("parent_email", req.getParentEmail());
					param.put("parent_approve_date", req.getRealNameDate());
					LOGGER.info("### param : {}", param.toString());

					/**
					 * 통합IDP 실명인증 법정대리인 연동 (cmd = TXUpdateGuardianInfoIDP)
					 */
					this.imIdpService.updateGuardian(param);

				}

			} // 통합회원 비교

		} // userAuthKey 비교

		/**
		 * SC 실명인증 연동.
		 */
		String userKey = this.updateRealName(sacHeader, req);

		/**
		 * 결과 정보 setting.
		 */
		CreateRealNameRes response = new CreateRealNameRes();
		response.setUserKey(userKey);

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
		updateRealNameRequest.setIsRealName(MemberConstants.USE_Y);
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

	/**
	 * <pre>
	 * 실명인증여부, 생년월일, CI 정보 IDP 결과또는 DB 와 정보가 동일한지 비교함.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param req
	 *            Request Value Object
	 * @param profileInfo
	 *            (통합IDP 회원 정보 조회 응답 결과)
	 * @return String 결과 타입
	 */
	private String compareRealName(SacRequestHeader sacHeader, CreateRealNameReq req, ImIdpReceiverM profileInfo) {

		ResponseBody idpResult = profileInfo.getResponseBody();

		String oneIdRealNameType = "";

		/**
		 * 통합IDP 조회결과 is_rname_auth 여부가 Y or N 에 따라 분기 처리.
		 */
		if (StringUtils.equals(idpResult.getIs_rname_auth(), MemberConstants.USE_Y)) {

			LOGGER.info("####### IDP 실명인증 CI    : {}", idpResult.getUser_ci());
			LOGGER.info("####### IDP 실명인증 Birth : {}", req.getUserBirthDay());

			// One ID CI, birthday 비교
			if (StringUtils.isNotEmpty(idpResult.getUser_ci())) { // 기 등록 된 CI가 존재하는 경우는 CI + 생년월일(social_date) 비교
				if (!StringUtils.equals(req.getUserCi(), idpResult.getUser_ci()) && !StringUtils.equals(req.getUserBirthDay(),
						idpResult.getUser_birthday())) {
					throw new StorePlatformException("SAC_MEM_1400");
				}
			} else { // 생년월일(social_date) 비교
				if (!StringUtils.equals(req.getUserBirthDay(), idpResult.getUser_birthday())) {
					throw new StorePlatformException("SAC_MEM_1401");
				}
			}

			if (StringUtils.equals(req.getUserName(), idpResult.getUser_name())) {
				if (StringUtils.isEmpty(idpResult.getUser_ci())) { // CI 기보유
					oneIdRealNameType = "E";
				}
			} else { // 개명
				LOGGER.info("## 개명..........");
				oneIdRealNameType = "R";
			}

		} else { // 최초 인증

			SearchUserRequest searchUserRequest = new SearchUserRequest();
			searchUserRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));

			/**
			 * 검색 조건 setting
			 */
			List<KeySearch> keySearchList = new ArrayList<KeySearch>();
			KeySearch keySchUserKey = new KeySearch();
			keySchUserKey.setKeyType(MemberConstants.KEY_TYPE_INSD_USERMBR_NO);
			keySchUserKey.setKeyString(req.getUserKey());
			keySearchList.add(keySchUserKey);
			searchUserRequest.setKeySearchList(keySearchList);

			/**
			 * 회원 정보조회 (실명 인증정보)
			 */
			SearchUserResponse schUserRes = this.userSCI.searchUser(searchUserRequest);

			if (schUserRes.getMbrAuth().getSequence() != null || StringUtils.equals(schUserRes.getMbrAuth().getSequence(), "")) {
				LOGGER.info("####### DB 실명인증 CI    : {}", schUserRes.getMbrAuth().getCi());
				LOGGER.info("####### DB 실명인증 Birth : {}", schUserRes.getMbrAuth().getBirthDay());

				// One ID CI, birthday 비교
				if (StringUtils.isNotEmpty(schUserRes.getMbrAuth().getCi())) { // 기 등록 된 CI가 존재하는 경우는 CI +
																			   // 생년월일(social_date)
					// 비교
					if (!StringUtils.equals(req.getUserCi(), schUserRes.getMbrAuth().getCi()) && !StringUtils.equals(req.getUserBirthDay(),
							schUserRes.getMbrAuth().getBirthDay())) {
						throw new StorePlatformException("SAC_MEM_1400");
					}
				} else { // 생년월일(social_date) 비교
					if (!StringUtils.equals(req.getUserBirthDay(), schUserRes.getMbrAuth().getBirthDay())) {
						throw new StorePlatformException("SAC_MEM_1401");
					}
				}
			}

			oneIdRealNameType = "R";

		}

		LOGGER.info("### oneIdRealNameType : {}", oneIdRealNameType);

		return oneIdRealNameType;
	}
}
