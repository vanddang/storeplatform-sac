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
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.idp.sci.IdpSCI;
import com.skplanet.storeplatform.external.client.idp.sci.ImIdpSCI;
import com.skplanet.storeplatform.external.client.idp.vo.AuthForIdEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.FindCommonProfileForServerEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.ModifyAuthInfoEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.ModifyProfileEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.CheckIdPwdAuthEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.ModifyPwdEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.UpdateGuardianEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.UpdateUserInfoEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.UpdateUserNameEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.UserInfoIdpSearchServerEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.UserInfoIdpSearchServerEcRes;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.common.vo.MbrAuth;
import com.skplanet.storeplatform.member.client.common.vo.MbrClauseAgree;
import com.skplanet.storeplatform.member.client.common.vo.MbrLglAgent;
import com.skplanet.storeplatform.member.client.common.vo.MbrOneID;
import com.skplanet.storeplatform.member.client.common.vo.MbrPwd;
import com.skplanet.storeplatform.member.client.common.vo.UpdateMbrOneIDRequest;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateAgreementRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdatePasswordUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateRealNameRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateRealNameResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbr;
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.client.member.vo.common.AgreementInfo;
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
	private IdpSCI idpSCI;

	@Autowired
	private ImIdpSCI imIdpSCI;

	@Override
	public ModifyRes mod(SacRequestHeader sacHeader, ModifyReq req) {

		ModifyRes response = new ModifyRes();

		/**
		 * 회원 정보 조회.
		 */
		UserInfo userInfo = this.mcc.getUserBaseInfo("userKey", req.getUserKey(), sacHeader);

		/**
		 * 통합서비스번호 존재 유무로 통합회원인지 기존회원인지 판단한다. (UserType보다 더 신뢰함.) 회원 타입에 따라서
		 * [통합IDP, 기존IDP] 연동처리 한다.
		 */
		LOGGER.debug("## 사용자 타입  : {}", userInfo.getUserType());
		LOGGER.debug("## 통합회원번호 : {}", StringUtils.isNotEmpty(userInfo.getImSvcNo()));
		if (StringUtils.isNotEmpty(userInfo.getImSvcNo())) {

			LOGGER.debug("## ====================================================");
			LOGGER.debug("## One ID 통합회원 [{}]", userInfo.getUserName());
			LOGGER.debug("## ====================================================");

			/**
			 * userAuthKey 가 정의된 값과 같은지 비교하여 연동 여부를 판단한다.
			 */
			if (this.mcc.isIdpConnect(req.getUserAuthKey())) {

				/**
				 * 통합IDP 회원정보 수정 연동 (cmd - TXUpdateUserInfoIDP)
				 */
				UpdateUserInfoEcReq updateUserInfoEcReq = new UpdateUserInfoEcReq();
				updateUserInfoEcReq.setUserAuthKey(req.getUserAuthKey()); // IDP 인증키
				updateUserInfoEcReq.setKey(userInfo.getImSvcNo()); // 통합서비스 관리번호
				updateUserInfoEcReq.setUserType("1"); // 가입자 유형코드 (1:개인)
				updateUserInfoEcReq.setIsBizAuth(MemberConstants.USE_N);
				updateUserInfoEcReq.setUdtTypeCd("4"); // 업데이트 구분 코드 (1:TN, 2:EM, 3:TN+EM, 4:부가정보)
				if (!StringUtils.equals(req.getUserCalendar(), "")) {
					updateUserInfoEcReq.setUserCalendar(req.getUserCalendar()); // 양력1, 음력2
				}
				if (!StringUtils.equals(req.getUserZip(), "")) {
					updateUserInfoEcReq.setUserZipcode(req.getUserZip()); // 우편번호
				}
				if (!StringUtils.equals(req.getUserAddress(), "")) {
					updateUserInfoEcReq.setUserAddress(req.getUserAddress()); // 주소
				}
				if (!StringUtils.equals(req.getUserDetailAddress(), "")) {
					updateUserInfoEcReq.setUserAddress2(req.getUserDetailAddress()); // 상세주소
				}
				this.imIdpSCI.updateUserInfo(updateUserInfoEcReq);

				/**
				 * 통합IDP 회원정보 조회 연동 (cmd - findCommonProfileForServerIDP))
				 */
				UserInfoIdpSearchServerEcReq userInfoIdpSearchServerEcReq = new UserInfoIdpSearchServerEcReq();
				userInfoIdpSearchServerEcReq.setKey(userInfo.getImSvcNo()); // 통합서비스 관리번호
				this.imIdpSCI.userInfoIdpSearchServer(userInfoIdpSearchServerEcReq);

			}

			/**
			 * SC 회원 수정.
			 */
			String userKey = this.modUser(sacHeader, req);
			response.setUserKey(userKey);

		} else {

			LOGGER.debug("## ====================================================");
			LOGGER.debug("## 기존 IDP 회원 [{}]", userInfo.getUserName());
			LOGGER.debug("## ====================================================");

			/**
			 * userAuthKey 가 정의된 값과 같은지 비교하여 연동 여부를 판단한다.
			 */
			if (this.mcc.isIdpConnect(req.getUserAuthKey())) {

				/**
				 * IDP 회원정보 수정 연동 (cmd - modifyProfile)
				 */
				ModifyProfileEcReq modifyProfileEcReq = new ModifyProfileEcReq();
				modifyProfileEcReq.setUserAuthKey(req.getUserAuthKey()); // IDP 인증키
				modifyProfileEcReq.setKeyType("2");
				modifyProfileEcReq.setKey(userInfo.getImMbrNo()); // MBR_NO
				if (!StringUtils.equals(req.getUserSex(), "")) {
					modifyProfileEcReq.setUserSex(req.getUserSex()); // 성별
				}
				if (!StringUtils.equals(req.getUserBirthDay(), "")) {
					modifyProfileEcReq.setUserBirthday(req.getUserBirthDay()); // 생년월일
				}
				if (!StringUtils.equals(req.getUserCalendar(), "")) {
					modifyProfileEcReq.setUserCalendar(req.getUserCalendar()); // 양력1, 음력2
				}
				if (!StringUtils.equals(req.getUserZip(), "")) {
					modifyProfileEcReq.setUserZipcode(req.getUserZip()); // 우편번호
				}
				if (!StringUtils.equals(req.getUserAddress(), "")) {
					modifyProfileEcReq.setUserAddress(req.getUserAddress()); // 주소
				}
				if (!StringUtils.equals(req.getUserDetailAddress(), "")) {
					modifyProfileEcReq.setUserAddress2(req.getUserDetailAddress()); // 상세주소
				}
				if (!StringUtils.equals(req.getUserPhone(), "")) {
					modifyProfileEcReq.setUserTel(req.getUserPhone()); // 사용자 연락처
				}
				this.idpSCI.modifyProfile(modifyProfileEcReq);

				/**
				 * IDP 회원정보 조회 연동 (cmd - findCommonProfileForServer)
				 */
				FindCommonProfileForServerEcReq findCommonProfileForServerEcReq = new FindCommonProfileForServerEcReq();
				findCommonProfileForServerEcReq.setKeyType("3");
				findCommonProfileForServerEcReq.setKey(userInfo.getImMbrNo()); // MBR_NO
				this.idpSCI.findCommonProfileForServer(findCommonProfileForServerEcReq);

			}

			/**
			 * SC 회원 수정.
			 */
			String userKey = this.modUser(sacHeader, req);
			response.setUserKey(userKey);

		}

		return response;
	}

	@Override
	public ModifyPasswordRes modPassword(SacRequestHeader sacHeader, ModifyPasswordReq req) {

		/**
		 * 회원 정보 조회.
		 */
		UserInfo userInfo = this.mcc.getUserBaseInfo("userKey", req.getUserKey(), sacHeader);

		/**
		 * 통합서비스번호 존재 유무로 통합회원인지 기존회원인지 판단한다. (UserType보다 더 신뢰함.) 회원 타입에 따라서
		 * [통합IDP, 기존IDP] 연동처리 한다.
		 */
		LOGGER.debug("## 사용자 타입  : {}", userInfo.getUserType());
		LOGGER.debug("## 통합회원번호 : {}", StringUtils.isNotEmpty(userInfo.getImSvcNo()));
		if (StringUtils.isNotEmpty(userInfo.getImSvcNo())) {

			LOGGER.debug("## ====================================================");
			LOGGER.debug("## One ID 통합회원 [{}]", userInfo.getUserName());
			LOGGER.debug("## ====================================================");

			/**
			 * userAuthKey 가 정의된 값과 같은지 비교하여 연동 여부를 판단한다.
			 */
			if (this.mcc.isIdpConnect(req.getUserAuthKey())) {

				/**
				 * 통합IDP 패스워드 확인 연동 (cmd - authIntegratedSPPW))
				 */
				CheckIdPwdAuthEcReq checkIdPwdAuthEcReq = new CheckIdPwdAuthEcReq();
				checkIdPwdAuthEcReq.setUserId(userInfo.getUserId());
				checkIdPwdAuthEcReq.setUserPasswd(req.getOldPassword());
				this.imIdpSCI.checkIdPwdAuth(checkIdPwdAuthEcReq);

				/**
				 * 통합IDP 비밀번호 변경 연동(cmd - TXUpdateUserPwdIDP)
				 */
				ModifyPwdEcReq modifyPwdEcReq = new ModifyPwdEcReq();
				modifyPwdEcReq.setKey(req.getUserAuthKey()); // IDP 인증 Key
				modifyPwdEcReq.setKey(userInfo.getImSvcNo()); // 통합 서비스 관리번호
				modifyPwdEcReq.setUserPasswd(req.getNewPassword()); // 신규 비밀번호
				modifyPwdEcReq.setUserPasswdType("1"); // 비밀번호유형코드 (1: 정상, 2: 임시)
				modifyPwdEcReq.setUserPasswdModifyDate(DateUtil.getToday()); // 비밀번호변경일자 (YYYYMMDD)
				this.imIdpSCI.modifyPwd(modifyPwdEcReq);

			}

		} else {

			LOGGER.debug("## ====================================================");
			LOGGER.debug("## 기존 IDP 회원 [{}]", userInfo.getUserName());
			LOGGER.debug("## ====================================================");

			/**
			 * userAuthKey 가 정의된 값과 같은지 비교하여 연동 여부를 판단한다.
			 */
			if (this.mcc.isIdpConnect(req.getUserAuthKey())) {

				/**
				 * IDP 패스워드 확인 연동 (cmd - authForId)
				 */
				AuthForIdEcReq authForIdEcReq = new AuthForIdEcReq();
				authForIdEcReq.setUserId(userInfo.getUserId());
				authForIdEcReq.setUserPasswd(req.getOldPassword());
				this.idpSCI.authForId(authForIdEcReq);

				/**
				 * IDP 비밀번호 변경 연동 (cmd - modifyAuthInfo)
				 */
				ModifyAuthInfoEcReq modifyAuthInfoEcReq = new ModifyAuthInfoEcReq();
				modifyAuthInfoEcReq.setUserAuthKey(req.getUserAuthKey()); // IDP 연동 Key
				modifyAuthInfoEcReq.setKeyType("2"); // 변경할 인증 정보의 type (1=Email, 2=password, default=1)
				modifyAuthInfoEcReq.setUserKey(userInfo.getImMbrNo()); // MBR_NO
				modifyAuthInfoEcReq.setPreKey(req.getOldPassword()); // 기존 패스워드
				modifyAuthInfoEcReq.setKey(req.getNewPassword()); // 신규 패스워드
				this.idpSCI.modifyAuthInfo(modifyAuthInfoEcReq);

			}

		}

		/**
		 * SC 회원 비밀번호 변경 요청.
		 */
		this.modPasswordUser(sacHeader, req, userInfo.getUserId());

		/**
		 * 결과 setting.
		 */
		ModifyPasswordRes response = new ModifyPasswordRes();
		response.setUserKey(req.getUserKey());

		return response;
	}

	@Override
	public ModifyEmailRes modEmail(SacRequestHeader sacHeader, ModifyEmailReq req) {

		/**
		 * TODO 정확한 로직 미정의로 수정가능성 있음...... (현재는 사용자 이메일정보만 무조건 업데이트함.)
		 * 
		 * TODO 정확한 로직 미정의로 수정가능성 있음...... (현재는 사용자 이메일정보만 무조건 업데이트함.)
		 * 
		 * TODO 정확한 로직 미정의로 수정가능성 있음...... (현재는 사용자 이메일정보만 무조건 업데이트함.)
		 */

		/**
		 * 사용자 기본정보 setting.
		 */
		UpdateUserRequest updateUserRequest = new UpdateUserRequest();
		updateUserRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));
		UserMbr userMbr = new UserMbr();
		userMbr.setUserKey(req.getUserKey()); // 사용자 Key
		userMbr.setUserEmail(req.getNewEmail()); // 신규 이메일
		updateUserRequest.setUserMbr(userMbr);

		/**
		 * SC 사용자 회원 기본정보 수정 요청.
		 */
		UpdateUserResponse updateUserResponse = this.userSCI.updateUser(updateUserRequest);
		if (updateUserResponse.getUserKey() == null || StringUtils.equals(updateUserResponse.getUserKey(), "")) {
			throw new StorePlatformException("SAC_MEM_0002", "userKey");
		}

		/**
		 * 결과 setting.
		 */
		ModifyEmailRes response = new ModifyEmailRes();
		response.setUserKey(updateUserResponse.getUserKey());

		return response;
	}

	@Override
	public CreateTermsAgreementRes regTermsAgreement(SacRequestHeader sacHeader, CreateTermsAgreementReq req) {

		/**
		 * SC Store 약관동의 등록/수정 연동.
		 */
		this.modAgreement(sacHeader, req.getUserKey(), req.getAgreementList());

		/**
		 * 결과 setting.
		 */
		CreateTermsAgreementRes response = new CreateTermsAgreementRes();
		response.setUserKey(req.getUserKey());

		return response;
	}

	@Override
	public ModifyTermsAgreementRes modTermsAgreement(SacRequestHeader sacHeader, ModifyTermsAgreementReq req) {

		/**
		 * SC Store 약관동의 등록/수정 연동.
		 */
		this.modAgreement(sacHeader, req.getUserKey(), req.getAgreementList());

		/**
		 * 결과 setting.
		 */
		ModifyTermsAgreementRes response = new ModifyTermsAgreementRes();
		response.setUserKey(req.getUserKey());

		return response;
	}

	@Override
	public CreateRealNameRes regRealName(SacRequestHeader sacHeader, CreateRealNameReq req) {

		/**
		 * userAuthKey 가 정의된 값과 같은지 비교하여 연동 여부를 판단한다.
		 */
		if (this.mcc.isIdpConnect(req.getUserAuthKey())) {

			/**
			 * 회원 정보 조회.
			 */
			UserInfo userInfo = this.mcc.getUserBaseInfo("userKey", req.getUserKey(), sacHeader);

			/**
			 * 통합서비스번호 존재 유무로 통합회원인지 기존회원인지 판단한다. (UserType보다 더 신뢰함.) 회원 타입에 따라서
			 * [통합IDP, 기존IDP] 연동처리 한다.
			 * 
			 * 통합회원일경우만 처리한다.
			 */
			LOGGER.debug("## 사용자 타입  : {}", userInfo.getUserType());
			LOGGER.debug("## 통합회원번호 : {}", StringUtils.isNotEmpty(userInfo.getImSvcNo()));
			if (StringUtils.isNotEmpty(userInfo.getImSvcNo())) {

				LOGGER.debug("## ====================================================");
				LOGGER.debug("## One ID 통합회원일 경우만 통합 IDP 연동을 한다.");
				LOGGER.debug("## ====================================================");

				/**
				 * 실명인증 대상 여부에 따라 분기 처리. (OWN=본인, PARENT=법정대리인)
				 */
				if (StringUtils.equals(req.getIsOwn(), MemberConstants.AUTH_TYPE_OWN)) { // 본인

					/**
					 * 통합IDP 회원정보 조회 연동 (cmd - findCommonProfileForServerIDP))
					 */
					UserInfoIdpSearchServerEcReq userInfoIdpSearchServerEcReq = new UserInfoIdpSearchServerEcReq();
					userInfoIdpSearchServerEcReq.setKey(userInfo.getImSvcNo()); // 통합서비스 관리번호
					UserInfoIdpSearchServerEcRes userInfoIdpSearchServerEcRes = this.imIdpSCI.userInfoIdpSearchServer(userInfoIdpSearchServerEcReq);

					/**
					 * OnedID 조회 정보와 Request 로 받은 정보와 비교 로직 수행.
					 */
					String oneIdRealNameType = this.compareRealName(sacHeader, req, userInfoIdpSearchServerEcRes);

					if (StringUtils.isNotEmpty(oneIdRealNameType)) {

						/**
						 * is_rname_auth, user_birthday, user_ci 가 동일해야만 업데이트
						 * 
						 * 실명인증을 받았다 하더라도 개명 등의 이유로 이름은 변경될 수 있다.
						 * 
						 * 통합IDP 실명인증 본인 연동 (cmd = TXUpdateUserNameIDP)
						 */
						UpdateUserNameEcReq updateUserNameEcReq = new UpdateUserNameEcReq();
						updateUserNameEcReq.setIsRnameAuth(MemberConstants.USE_Y); // 실명인증 유무
						updateUserNameEcReq.setKey(userInfo.getImSvcNo());
						updateUserNameEcReq.setUserAuthKey(req.getUserAuthKey());
						updateUserNameEcReq.setUserName(req.getUserName()); // 이름
						updateUserNameEcReq.setUserBirthday(req.getUserBirthDay()); // 생년월일
						updateUserNameEcReq.setUserSex(req.getUserSex()); // 성별 (M=남자, F=여자, N:미확인)
						// 실명인증 수단 코드 1: 휴대폰, 2: 아이핀, 9:기타
						updateUserNameEcReq.setRnameAuthMnsCode(this.convertRealNameMethod(req.getRealNameMethod(), req.getIsOwn()));
						// 실명인증 회원 코드 10 : 내국인, 20 : 외국인
						updateUserNameEcReq.setRnameAuthMbrCode(this.convertResident(req.getResident()));
						updateUserNameEcReq.setRnameAuthTypeCd(oneIdRealNameType); // 실명 인증 유형 코드 R=회원 개명 E=CI 기보유
						updateUserNameEcReq.setUserCi(req.getUserCi());
						updateUserNameEcReq.setUserDi(req.getUserDi());
						updateUserNameEcReq.setRnameAuthDate(req.getRealNameDate());
						this.imIdpSCI.updateUserName(updateUserNameEcReq);

						/**
						 * OneID 실명인증 정보 업데이트.
						 */
						this.modOneIdInfo(sacHeader, userInfo.getImSvcNo());
					}

				} else if (StringUtils.equals(req.getIsOwn(), MemberConstants.AUTH_TYPE_PARENT)) { // 법정대리인

					if (StringUtils.isBlank(req.getUserBirthDay())) {
						throw new StorePlatformException("SAC_MEM_0002", "userBirthDay");
					}

					if (StringUtils.isBlank(req.getParentBirthDay())) {
						throw new StorePlatformException("SAC_MEM_0002", "parentBirthDay");
					}

					/**
					 * 법정대리인 나이 유효성 체크.
					 */
					this.mcc.checkParentBirth(req.getUserBirthDay(), req.getParentBirthDay());

					try {

						/**
						 * 통합IDP 실명인증 법정대리인 연동 (cmd = TXUpdateGuardianInfoIDP)
						 */
						UpdateGuardianEcReq updateGuardianEcReq = new UpdateGuardianEcReq();
						updateGuardianEcReq.setKey(userInfo.getImSvcNo());
						updateGuardianEcReq.setUserAuthKey(req.getUserAuthKey());
						// 법정대리인관계코드 (0:부, 1:모, 2:기타)
						updateGuardianEcReq.setParentType(this.convertParentType(req.getParentType()));
						updateGuardianEcReq.setParentRnameAuthKey(req.getUserCi()); // 법정대리인 실명인증 값 (CI) [외국인은 null
																					// 로....]
						// 법정대리인실명인증수단코드 1:휴대폰 본인인증, , 3:IPIN, 6:이메일 (외국인 법정대리인 인증)
						updateGuardianEcReq.setParentRnameAuthType(this.convertRealNameMethod(req.getRealNameMethod(), req.getIsOwn()));
						// 법정대리인동의여부 Y=동의, N=미동의 (Y만 가능)
						updateGuardianEcReq.setIsParentApprove(MemberConstants.USE_Y);
						updateGuardianEcReq.setParentName(req.getUserName());
						updateGuardianEcReq.setParentBirthday(req.getParentBirthDay()); // 법정대리인 생년월일
						updateGuardianEcReq.setParentEmail(req.getParentEmail());
						if (req.getRealNameDate().length() == 14) {
							// 법정대리인동의일자(YYYYMMDD)
							updateGuardianEcReq.setParentApproveDate(DateUtil.changeDateStringtoEight(req.getRealNameDate()));
						} else {
							updateGuardianEcReq.setParentApproveDate(req.getRealNameDate());
						}
						this.imIdpSCI.updateGuardian(updateGuardianEcReq);

						/**
						 * OneID 실명인증 정보 업데이트.
						 */
						this.modOneIdInfo(sacHeader, userInfo.getImSvcNo());

					} catch (StorePlatformException spe) {

						if (StringUtils.equals(spe.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE + "2402X000")) {

							/**
							 * IDP 에러 [[ 2402X000 : 법정대리인 동의된 회원입니다.]] ==> Skip
							 * 처리 한다.
							 * 
							 * 2014.02.21 임재호K 협의. (IDP의 실명인증과 SAC의 실명인증은 별개로
							 * 본다.)
							 */
							LOGGER.debug("## >> Skip 처리 한다.");
							LOGGER.debug("## >> Error Code : {}", spe.getErrorInfo().getCode());
							LOGGER.debug("## >> Error Msg  : {}", spe.getErrorInfo().getMessage());

						} else {

							throw spe;

						}

					}

				} else { // 법인

					/**
					 * 법인 단말일 경우 Skip.
					 */
					LOGGER.debug("## >> 법인 단말일 경우는 Skip.");

				}

			} // 통합회원 비교

		} // userAuthKey 비교

		/**
		 * SC 실명인증 연동.
		 */
		String userKey = this.modRealName(sacHeader, req);

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
	private String modUser(SacRequestHeader sacHeader, ModifyReq req) {

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

		LOGGER.debug("## SC Request 사용자 기본정보 : {}", userMbr);

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
	private String modRealName(SacRequestHeader sacHeader, CreateRealNameReq req) {

		/**
		 * 실명인증 대상 여부에 따라 분기 처리. (OWN=본인, PARENT=법정대리인, CORP=법인)
		 */
		if (StringUtils.equals(req.getIsOwn(), MemberConstants.AUTH_TYPE_OWN)) {

			/**
			 * 실명인증 기본 setting.
			 */
			UpdateRealNameRequest updateRealNameRequest = new UpdateRealNameRequest();
			updateRealNameRequest = new UpdateRealNameRequest();
			updateRealNameRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));
			updateRealNameRequest.setIsOwn(req.getIsOwn());
			updateRealNameRequest.setIsRealName(MemberConstants.USE_Y);
			updateRealNameRequest.setUserKey(req.getUserKey());

			/**
			 * 실명인증 (본인)
			 */
			MbrAuth mbrAuth = new MbrAuth();
			mbrAuth.setIsRealName(MemberConstants.USE_Y); // 실명인증 여부
			mbrAuth.setTenantID(sacHeader.getTenantHeader().getTenantId()); // 테넌트 아이디
			mbrAuth.setBirthDay(req.getUserBirthDay()); // 생년월일
			mbrAuth.setTelecom(req.getDeviceTelecom()); // 이동 통신사
			mbrAuth.setPhone(req.getUserPhone()); // 사용자 휴대폰
			mbrAuth.setName(req.getUserName()); // 사용자 이름
			mbrAuth.setSex(req.getUserSex()); // 사용자 성별
			mbrAuth.setCi(req.getUserCi()); // CI
			mbrAuth.setDi(req.getUserDi()); // DI
			mbrAuth.setRealNameSite(sacHeader.getTenantHeader().getSystemId()); // 실명인증 사이트 코드
			mbrAuth.setRealNameDate(req.getRealNameDate()); // 실명인증 일시
			mbrAuth.setRealNameMethod(req.getRealNameMethod()); // 실명인증 수단코드
			mbrAuth.setIsDomestic(this.convertIsDomestic(req.getResident())); // 내외국인 구분 (Y : 내국인, N : 외국인)
			updateRealNameRequest.setUserMbrAuth(mbrAuth);

			/**
			 * SC 실명인증정보 수정 연동.
			 */
			UpdateRealNameResponse updateRealNameResponse = this.userSCI.updateRealName(updateRealNameRequest);
			if (updateRealNameResponse.getUserKey() == null || StringUtils.equals(updateRealNameResponse.getUserKey(), "")) {
				throw new StorePlatformException("SAC_MEM_0002", "userKey");
			}

			return updateRealNameResponse.getUserKey();

		} else if (StringUtils.equals(req.getIsOwn(), MemberConstants.AUTH_TYPE_PARENT)) { // 법정대리인

			if (StringUtils.isBlank(req.getUserBirthDay())) {
				throw new StorePlatformException("SAC_MEM_0002", "userBirthDay");
			}

			if (StringUtils.isBlank(req.getParentBirthDay())) {
				throw new StorePlatformException("SAC_MEM_0002", "parentBirthDay");
			}

			/**
			 * 법정대리인 나이 유효성 체크.
			 */
			this.mcc.checkParentBirth(req.getUserBirthDay(), req.getParentBirthDay());

			/**
			 * 실명인증 기본 setting.
			 */
			UpdateRealNameRequest updateRealNameRequest = new UpdateRealNameRequest();
			updateRealNameRequest = new UpdateRealNameRequest();
			updateRealNameRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));
			updateRealNameRequest.setIsOwn(req.getIsOwn());
			updateRealNameRequest.setIsRealName(MemberConstants.USE_Y);
			updateRealNameRequest.setUserKey(req.getUserKey());

			/**
			 * 실명인증 (법정대리인)
			 */
			MbrLglAgent mbrLglAgent = new MbrLglAgent();
			mbrLglAgent.setIsParent(MemberConstants.USE_Y); // 법정대리인 여부
			mbrLglAgent.setParentBirthDay(req.getParentBirthDay()); // 법정대리인 생년월일
			mbrLglAgent.setParentMDN(req.getUserPhone()); // 법정대리인 전화번호
			mbrLglAgent.setParentTelecom(req.getDeviceTelecom()); // 법정대리인 이동 통신사
			mbrLglAgent.setParentCI(req.getUserCi()); // 법정대리인 CI
			mbrLglAgent.setParentName(req.getUserName()); // 법정대리인 이름
			mbrLglAgent.setParentType(req.getParentType()); // 법정대리인 관계코드
			mbrLglAgent.setParentEmail(req.getParentEmail()); // 법정대리인 이메일
			mbrLglAgent.setParentDate(req.getRealNameDate()); // 법정대리인 실명인증 일시
			mbrLglAgent.setParentRealNameSite(sacHeader.getTenantHeader().getSystemId()); // 법정대리인 실명인증 사이트 코드
			mbrLglAgent.setParentRealNameMethod(req.getRealNameMethod()); // 법정대리인 실명인증 수단코드
			mbrLglAgent.setIsDomestic(this.convertIsDomestic(req.getResident())); // 내외국인 구분 (Y : 내국인, N : 외국인)
			updateRealNameRequest.setMbrLglAgent(mbrLglAgent);

			/**
			 * SC 실명인증정보 수정 연동.
			 */
			UpdateRealNameResponse updateRealNameResponse = this.userSCI.updateRealName(updateRealNameRequest);
			if (updateRealNameResponse.getUserKey() == null || StringUtils.equals(updateRealNameResponse.getUserKey(), "")) {
				throw new StorePlatformException("SAC_MEM_0002", "userKey");
			}

			return updateRealNameResponse.getUserKey();

		} else { // 법인

			/**
			 * 실명인증 기본 setting.
			 */
			UpdateRealNameRequest updateRealNameRequest = new UpdateRealNameRequest();
			updateRealNameRequest = new UpdateRealNameRequest();
			updateRealNameRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));
			updateRealNameRequest.setIsOwn(MemberConstants.AUTH_TYPE_OWN);
			updateRealNameRequest.setIsRealName(MemberConstants.USE_Y);
			updateRealNameRequest.setUserKey(req.getUserKey());

			/**
			 * 실명인증 (본인)
			 */
			MbrAuth mbrAuth = new MbrAuth();
			mbrAuth.setIsRealName(MemberConstants.USE_Y); // 실명인증 여부
			mbrAuth.setCi(" "); // CI 값은 필수인데... ' ' 공백으로 넣기로 SC와 협의함.
			mbrAuth.setBirthDay(req.getUserBirthDay()); // 생년월일
			mbrAuth.setIsDomestic(this.convertIsDomestic(req.getResident())); // 내외국인 구분 (Y : 내국인, N : 외국인)
			mbrAuth.setSex(req.getUserSex()); // 사용자 성별
			updateRealNameRequest.setUserMbrAuth(mbrAuth);

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

	/**
	 * <pre>
	 * 실명인증여부, 생년월일, CI 정보 IDP 결과또는 DB 와 정보가 동일한지 비교함.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param req
	 *            Request Value Object
	 * @param idpResult
	 *            (통합IDP 회원 정보 조회 응답 결과)
	 * @return String 결과 타입
	 */
	private String compareRealName(SacRequestHeader sacHeader, CreateRealNameReq req, UserInfoIdpSearchServerEcRes idpResult) {

		String oneIdRealNameType = "";

		/**
		 * 통합IDP 조회결과 is_rname_auth 여부가 Y or N 에 따라 분기 처리.
		 */
		if (StringUtils.equals(idpResult.getIsRnameAuth(), MemberConstants.USE_Y)) {

			LOGGER.debug("####### IDP 실명인증 CI    : {}", idpResult.getUserCi());
			LOGGER.debug("####### IDP 실명인증 Birth : {}", req.getUserBirthDay());

			// One ID CI, birthday 비교
			if (StringUtils.isNotEmpty(idpResult.getUserCi())) { // 기 등록 된 CI가 존재하는 경우는 CI + 생년월일(social_date) 비교
				if (!StringUtils.equals(req.getUserCi(), idpResult.getUserCi())
						&& !StringUtils.equals(req.getUserBirthDay(), idpResult.getUserBirthday())) {
					throw new StorePlatformException("SAC_MEM_1400");
				}
			} else { // 생년월일(social_date) 비교
				if (!StringUtils.equals(req.getUserBirthDay(), idpResult.getUserBirthday())) {
					throw new StorePlatformException("SAC_MEM_1401");
				}
			}

			if (StringUtils.equals(req.getUserName(), idpResult.getUserName())) {
				if (StringUtils.isEmpty(idpResult.getUserCi())) { // CI 기보유
					oneIdRealNameType = "E";
				}
			} else { // 개명
				LOGGER.debug("## 개명..........");
				oneIdRealNameType = "R";
			}

			// /**
			// * 타사이트에서 실명인증을 하고 t-Store 에서 개명 신청을 시도할 경우.
			// */
			// if (StringUtils.equals(oneIdRealNameType, "R")) {
			// if (!StringUtils.equals(idpResult.getModifySstCode(), MemberConstants.SSO_SST_CD_TSTORE)) {
			// throw new StorePlatformException("SAC_MEM_1402", idpResult.getModifySstCode());
			// }
			// }

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

			/**
			 * 인증 정보가 존재하면....
			 */
			if (schUserRes.getMbrAuth().getSequence() != null || StringUtils.equals(schUserRes.getMbrAuth().getSequence(), "")) {
				LOGGER.debug("####### DB 실명인증 CI    : {}", schUserRes.getMbrAuth().getCi());
				LOGGER.debug("####### DB 실명인증 Birth : {}", schUserRes.getMbrAuth().getBirthDay());

				// One ID CI, birthday 비교
				if (StringUtils.isNotEmpty(schUserRes.getMbrAuth().getCi())) { // 기 등록 된 CI가 존재하는 경우는 CI +
																				// 생년월일(social_date)
					// 비교
					if (!StringUtils.equals(req.getUserCi(), schUserRes.getMbrAuth().getCi())
							&& !StringUtils.equals(req.getUserBirthDay(), schUserRes.getMbrAuth().getBirthDay())) {
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

		LOGGER.debug("### oneIdRealNameType : {}", oneIdRealNameType);

		return oneIdRealNameType;
	}

	/**
	 * <pre>
	 * 약관동의 정보를 등록 또는 수정하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param userKey
	 *            사용자 Key
	 * @param agreementList
	 *            약관 동의 정보 리스트
	 */
	private void modAgreement(SacRequestHeader sacHeader, String userKey, List<AgreementInfo> agreementList) {

		/**
		 * 약관 맵핑정보 세팅.
		 */
		this.mcc.getClauseMappingInfo(sacHeader.getTenantHeader().getTenantId(), agreementList);

		UpdateAgreementRequest updateAgreementRequest = new UpdateAgreementRequest();
		updateAgreementRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));
		updateAgreementRequest.setUserKey(userKey);

		/**
		 * 약관 동의 리스트 setting.
		 */
		List<MbrClauseAgree> mbrClauseAgreeList = new ArrayList<MbrClauseAgree>();
		for (AgreementInfo info : agreementList) {
			MbrClauseAgree mbrClauseAgree = new MbrClauseAgree();
			mbrClauseAgree.setExtraAgreementID(info.getExtraAgreementId());
			mbrClauseAgree.setExtraAgreementVersion(info.getExtraAgreementVersion());
			mbrClauseAgree.setIsExtraAgreement(info.getIsExtraAgreement());
			mbrClauseAgree.setIsMandatory(info.getMandAgreeYn());
			mbrClauseAgree.setRegDate(DateUtil.getToday("yyyyMMddHHmmss"));
			mbrClauseAgreeList.add(mbrClauseAgree);
		}
		updateAgreementRequest.setMbrClauseAgreeList(mbrClauseAgreeList);

		/**
		 * SC 약관동의 정보를 등록 또는 수정 연동.
		 */
		this.userSCI.updateAgreement(updateAgreementRequest);

	}

	/**
	 * <pre>
	 * 실명인증 수단코드를 통합 IDP 규격에 맞게 Converting.
	 * </pre>
	 * 
	 * @param realNameMethod
	 *            실명인증 수단 코드 (US011101 : 휴대폰 인증, US011102 : IPIN 인증)
	 * @param isOwn
	 *            실명인증 대상 여부. (OWN=본인, PARENT=법정대리인)
	 * @return 통합 IDP 코드[본인] (1 : 휴대폰, 2 : 아이핀, 9 : 기타)
	 * 
	 *         통합 IDP 코드[법정대리인] (1 : 휴대폰, 3 : 아이핀, 6 : 이메일(외국인 법정대리인 인증))
	 */
	private String convertRealNameMethod(String realNameMethod, String isOwn) {

		if (StringUtils.equals(isOwn, MemberConstants.AUTH_TYPE_OWN)) { // 본인

			if (StringUtils.equals(realNameMethod, MemberConstants.REAL_NAME_AUTH_MOBILE)) {
				return "1"; // 휴대폰 인증
			} else if (StringUtils.equals(realNameMethod, MemberConstants.REAL_NAME_AUTH_IPIN)) {
				return "2"; // IPIN 인증
			} else {
				return "9"; // 기타
			}

		} else { // 법정대리인

			if (StringUtils.equals(realNameMethod, MemberConstants.REAL_NAME_AUTH_MOBILE)) {
				return "1"; // 휴대폰 인증
			} else if (StringUtils.equals(realNameMethod, MemberConstants.REAL_NAME_AUTH_IPIN)) {
				return "3"; // IPIN 인증
			} else {
				return "6"; // 이메일(외국인 법정대리인 인증)
			}

		}
	}

	/**
	 * <pre>
	 * 실명인증 회원코드를 통합 IDP 규격에 맞게 Converting.
	 * </pre>
	 * 
	 * @param resident
	 *            실명인증 회원코드 (local : 내국인, foreign : 외국인)
	 * @return 통합 IDP 코드 (10 : 내국인, 20 : 외국인)
	 */
	private String convertResident(String resident) {
		if (StringUtils.equals(resident, "local")) {
			return "10"; // 내국인
		} else if (StringUtils.equals(resident, "foreign")) {
			return "20"; // 외국인
		} else {
			return "";
		}
	}

	/**
	 * <pre>
	 * SC에 사용하는 정보로 내외국인 여부 Converting.
	 * </pre>
	 * 
	 * @param resident
	 *            실명인증 회원코드 (local : 내국인, foreign : 외국인)
	 * @return 내외국인 여부 (Y or N)
	 */
	private String convertIsDomestic(String resident) {
		if (StringUtils.equals(resident, "local")) {
			return MemberConstants.USE_Y; // 내국인
		} else if (StringUtils.equals(resident, "foreign")) {
			return MemberConstants.USE_N; // 외국인
		} else {
			return "";
		}
	}

	/**
	 * <pre>
	 * 법정대리인관계코드를 통합 IDP 규격에 맞게 Converting.
	 * </pre>
	 * 
	 * @param parentType
	 *            법정대리인관계코드 (US011801 : 아버지, US011802 : 어머니, US011803 : 기타)
	 * @return 통합 IDP 코드 (0 : 아버지, 1 : 어머니, 2 : 기타)
	 */
	private String convertParentType(String parentType) {
		if (StringUtils.equals(parentType, MemberConstants.PARENT_TYPE_FATHER)) {
			return "0"; // 아버지
		} else if (StringUtils.equals(parentType, MemberConstants.PARENT_TYPE_MOTHER)) {
			return "1"; // 어머니
		} else {
			return "2"; // 기타
		}
	}

	/**
	 * <pre>
	 * SC 회원 비밀번호 변경 요청.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param req
	 *            Request Value Object
	 * @param userId
	 *            사용자 아이디
	 */
	private void modPasswordUser(SacRequestHeader sacHeader, ModifyPasswordReq req, String userId) {

		UpdatePasswordUserRequest updatePasswordUserRequest = new UpdatePasswordUserRequest();
		updatePasswordUserRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));

		MbrPwd mbrPwd = new MbrPwd();
		mbrPwd.setMemberID(userId); // 사용자 아이디
		mbrPwd.setMemberKey(req.getUserKey()); // 사용자 Key
		/**
		 * 신규번호만 있을시에는 업데이트, 신규번호 + 기존번호 둘다 존재시에는 SC 에서 패스워드 비교를 한다.
		 * 
		 * IDP 에서 이미 패스워드 검증을 하기 때문에 신규번호만 넣고 업데이트함.
		 */
		mbrPwd.setMemberPW(req.getNewPassword()); // 신규 비밀번호
		mbrPwd.setPwRegDate(DateUtil.getToday("yyyyMMddHHmmss")); // 비밀번호 변경 일시
		updatePasswordUserRequest.setMbrPwd(mbrPwd);

		/**
		 * SC 회원 비밀번호 변경 요청.
		 */
		this.userSCI.updatePasswordUser(updatePasswordUserRequest);

	}

	/**
	 * <pre>
	 * T-store 미동의 회원 정보 업데이트.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param imSvcNo
	 *            OneID 통합서비스 관리번호
	 */
	private void modOneIdInfo(SacRequestHeader sacHeader, String imSvcNo) {

		try {

			/**
			 * 미동의 회원 정보 업데이트.
			 */
			UpdateMbrOneIDRequest updateMbrOneIDRequest = new UpdateMbrOneIDRequest();
			updateMbrOneIDRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));
			MbrOneID mbrOneID = new MbrOneID();
			mbrOneID.setIntgSvcNumber(imSvcNo); // OneID 통합서비스 관리번호
			mbrOneID.setIsRealName(MemberConstants.USE_Y); // 실명인증 여부
			mbrOneID.setIsCi(MemberConstants.USE_Y); // CI 존재 여부(Y/N)
			mbrOneID.setUpdateDate(DateUtil.getToday("yyyyMMddHHmmss")); // 업데이트 날짜

			updateMbrOneIDRequest.setMbrOneID(mbrOneID);
			this.userSCI.createAgreeSite(updateMbrOneIDRequest);

		} catch (StorePlatformException spe) {

			LOGGER.info("미동의 회원정보 업데이트 실패 [{}]", imSvcNo);
			throw spe;

		}

	}
}