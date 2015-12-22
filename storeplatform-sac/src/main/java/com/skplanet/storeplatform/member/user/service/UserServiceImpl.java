/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import com.skplanet.pdp.sentinel.shuttle.TLogSentinelShuttle;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil.ShuttleSetter;
import com.skplanet.storeplatform.member.client.common.constant.Constant;
import com.skplanet.storeplatform.member.client.common.util.Utils;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.CommonResponse;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.common.vo.LimitTarget;
import com.skplanet.storeplatform.member.client.common.vo.MbrAuth;
import com.skplanet.storeplatform.member.client.common.vo.MbrClauseAgree;
import com.skplanet.storeplatform.member.client.common.vo.MbrLglAgent;
import com.skplanet.storeplatform.member.client.common.vo.MbrMangItemPtcr;
import com.skplanet.storeplatform.member.client.common.vo.MbrOneID;
import com.skplanet.storeplatform.member.client.common.vo.MbrPwd;
import com.skplanet.storeplatform.member.client.common.vo.MemberPoint;
import com.skplanet.storeplatform.member.client.common.vo.RemoveMemberPointRequest;
import com.skplanet.storeplatform.member.client.common.vo.RemoveMemberPointResponse;
import com.skplanet.storeplatform.member.client.common.vo.RemovePolicyRequest;
import com.skplanet.storeplatform.member.client.common.vo.RemovePolicyResponse;
import com.skplanet.storeplatform.member.client.common.vo.SearchMemberPointRequest;
import com.skplanet.storeplatform.member.client.common.vo.SearchMemberPointResponse;
import com.skplanet.storeplatform.member.client.common.vo.SearchPolicyRequest;
import com.skplanet.storeplatform.member.client.common.vo.SearchPolicyResponse;
import com.skplanet.storeplatform.member.client.common.vo.UpdateMemberPointRequest;
import com.skplanet.storeplatform.member.client.common.vo.UpdateMemberPointResponse;
import com.skplanet.storeplatform.member.client.common.vo.UpdatePolicyRequest;
import com.skplanet.storeplatform.member.client.common.vo.UpdatePolicyResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SellerMbr;
import com.skplanet.storeplatform.member.client.user.sci.vo.ChangedDeviceLog;
import com.skplanet.storeplatform.member.client.user.sci.vo.CheckDuplicationRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CheckDuplicationResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateChangedDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateChangedDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateDeliveryInfoRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateDeliveryInfoResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateGiftChargeInfoRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateGiftChargeInfoResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateSocialAccountRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateSocialAccountResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.DeviceMbrStatus;
import com.skplanet.storeplatform.member.client.user.sci.vo.DeviceSystemStats;
import com.skplanet.storeplatform.member.client.user.sci.vo.ExistListRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.ExistListResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.GiftChargeInfo;
import com.skplanet.storeplatform.member.client.user.sci.vo.Grade;
import com.skplanet.storeplatform.member.client.user.sci.vo.ListTenantRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.ListTenantResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.LoginUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.LoginUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.MoveUserInfoRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.MoveUserInfoResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.NonMbrSegment;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveDeliveryInfoRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveDeliveryInfoResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveManagementRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveManagementResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveMbrOneIDRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveMbrOneIDResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.ResetPasswordUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.ResetPasswordUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchAfterUserKeyRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchAfterUserKeyResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchAgreeSiteRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchAgreeSiteResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchAgreementListRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchAgreementListResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchChangedDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchChangedDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeActivateUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeActivateUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeliveryInfo;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeliveryInfoRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeliveryInfoResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceListRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceListResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceOSNumberRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceOSNumberResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchExtentUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchExtentUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchGiftChargeInfoRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchGiftChargeInfoResponse;
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
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchRealNameRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchRealNameResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchSocialAccountRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchSocialAccountResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserEmailRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserEmailResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserExtraInfoRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserExtraInfoResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserSegmentRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserSegmentResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserkeyTrackRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserkeyTrackResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SimpleLoginRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SimpleLoginResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SocialAccount;
import com.skplanet.storeplatform.member.client.user.sci.vo.TlogRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.TlogResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.TransferDeliveryRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.TransferDeliveryResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.TransferGiftChrgInfoRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.TransferGiftChrgInfoResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateAgreementRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateAgreementResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateManagementRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateManagementResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateNonMbrSegmentRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateNonMbrSegmentResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdatePasswordUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdatePasswordUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdatePolicyKeyRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdatePolicyKeyResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdatePolicyValueRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdatePolicyValueResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateRealNameRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateRealNameResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateStatusUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateStatusUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateUserMbrSegmentRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateUserMbrSegmentResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbr;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrDevice;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrDeviceDetail;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrInfo;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrPnsh;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrSegment;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrStatus;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserkeyTrack;
import com.skplanet.storeplatform.member.common.code.DeviceManagementCode;
import com.skplanet.storeplatform.member.common.code.MainStateCode;
import com.skplanet.storeplatform.member.common.code.SubStateCode;
import com.skplanet.storeplatform.member.common.code.UserTypeCode;
import com.skplanet.storeplatform.member.common.vo.ExistLimitWordMemberID;
import com.skplanet.storeplatform.member.user.vo.SearchUserKey;
import com.skplanet.storeplatform.member.user.vo.UserMbrLoginLog;
import com.skplanet.storeplatform.member.user.vo.UserMbrRetrieveUserMbrPwd;

/**
 * 사용자 기능 implementation.
 * 
 * Updated on : 2013. 12. 10. Updated by : wisestone_mikepark
 */
@Service
public class UserServiceImpl implements UserService {

	/** The Constant logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	/** The common dao. */
	@Autowired
	@Qualifier("scMember")
	private CommonDAO commonDAO;

	/** idle dao. */
	@Autowired
	@Qualifier("scIdleMember")
	private CommonDAO idleDAO;

	/** The message source accessor. */
	@Autowired
	@Qualifier("scMember")
	private MessageSourceAccessor messageSourceAccessor;

	/*
	 * <pre> 회원 가입 </pre>
	 * 
	 * @param CreateUserRequest 회원 가입 요청 Value Object
	 * 
	 * @return CreateUserResponse - 회원 가입 응답 Value Object
	 * 
	 * @see com.skplanet.storeplatform.member.user.service.UserService#loginUser(
	 * com.skplanet.storeplatform.member.client .user.sci.vo.CreateUserRequest)
	 */
	@Override
	public CreateUserResponse create(CreateUserRequest createUserRequest) {

		// TO DO.
		// ACTION 1-1. userID가 존재하는 경우 Fail 반환
		// ACTION 1-2. userID가 제한된 단어인 경우 Fail 반환
		// ACTION 2-1. 내부 사용자회원 번호 생성
		// ACTION 2-2. 무선사용자 회원가입의 경우 MBR_ID 생성
		// ACTION 3. 사용자 회원 추가
		// ACTION 3-1. 사용자 회원 비밀번호 추가
		// ACTION 4. 법정대리인 정보 추가
		// ACTION 5. 실명인증 정보 추가
		// ACTION 6. 사용자 관리항목(부가속성) 정보 추가
		// ACTION 7. 약관동의 목록정보 추가
		// ACTION 8. 결과 반환

		LOGGER.debug(">>>> >>> UserServiceImpl create : {}", createUserRequest);

		CreateUserResponse createUserResponse = new CreateUserResponse();
		Integer row = 0;
		String generatedUserKey = "";

		UserMbr usermbr = new UserMbr();
		usermbr.setUserID(createUserRequest.getUserMbr().getUserID());

		if (!createUserRequest.getUserMbr().getUserType().equals(UserTypeCode.MOBILE_USER.getCode())) {
			ExistLimitWordMemberID existLimitWordMemberID = this.commonDAO.queryForObject("User.checkExistLimitWord",
					usermbr, ExistLimitWordMemberID.class);
			LOGGER.debug("### existLimitWordMemberID : {}", existLimitWordMemberID);

			// ACTION 1-1. userID가 존재하는 경우 Fail 반환

			if (existLimitWordMemberID != null && existLimitWordMemberID.getExistWordID() != null
					&& existLimitWordMemberID.getExistWordID().length() > 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.duplicatedMemberID", ""));
			}

			// ACTION 1-2. userID가 제한된 단어인 경우 Fail 반환
			if (existLimitWordMemberID != null && existLimitWordMemberID.getLimitWordID() != null
					&& existLimitWordMemberID.getLimitWordID().length() > 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.restrictedWordMemberID", ""));
			}
		}

		// ACTION 2-1. 내부 사용자회원 번호 생성
		usermbr = createUserRequest.getUserMbr();

		int seq = this.commonDAO.queryForObject("User.getUserSequence", null, Integer.class);
		generatedUserKey = "US" + Utils.getLocalDateTime() + Utils.leftPadStringWithChar(Integer.toString(seq), 7, '0');

		usermbr.setSystemID(createUserRequest.getCommonRequest().getSystemID());
		usermbr.setUserKey(generatedUserKey);
		usermbr.setRegDate(Utils.getLocalDateTimeinYYYYMMDD());
		usermbr.setLoginStatusCode(createUserRequest.getUserMbr().getLoginStatusCode());
		usermbr.setStopStatusCode(createUserRequest.getUserMbr().getStopStatusCode());

		// ACTION 2-2. 무선사용자 회원가입의 경우 MBR_ID 생성
		if (createUserRequest.getUserMbr().getUserType().equals(UserTypeCode.MOBILE_USER.getCode())) {
			String generatedUserID = "ID" + usermbr.getSystemID()
					+ Utils.leftPadStringWithChar(Integer.toString(seq), 12, '0');
			createUserRequest.getUserMbr().setUserID(generatedUserID);
			usermbr.setUserID(generatedUserID);
		}

		LOGGER.debug(">>>> >>> UserServiceImpl before create : {}", usermbr);

		// 로그인, 직권중지 상태값이 없는 경우 일괄 정상코드 설정
		if (usermbr.getLoginStatusCode() == null || usermbr.getLoginStatusCode().length() <= 0) {
			usermbr.setLoginStatusCode(Constant.LOGIN_STATUS_CODE);
		}
		if (usermbr.getStopStatusCode() == null || usermbr.getStopStatusCode().length() <= 0) {
			usermbr.setStopStatusCode(Constant.STOP_STATUS_CODE);
		}

		// ACTION 3. 사용자 회원 추가
		row = (Integer) this.commonDAO.insert("User.createUser", usermbr);
		LOGGER.debug("### 0 row : {}", row);
		if (row == 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
		}

		// ACTION 3-1. 사용자 회원 비밀번호 추가
		MbrPwd mbrPwd = new MbrPwd();
		mbrPwd.setMemberKey(generatedUserKey);

		// 사용자 패스워드 추가
		row = (Integer) this.commonDAO.insert("User.insertPassword", mbrPwd);
		LOGGER.debug("### row 0: {}", row);
		if (row == 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
		}

		// ACTION 4. 법정대리인 정보 추가
		if (createUserRequest.getMbrLglAgent() != null) {

			MbrLglAgent mbrLglAgent = createUserRequest.getMbrLglAgent();
			mbrLglAgent.setMemberKey(generatedUserKey);
			row = (Integer) this.commonDAO.insert("User.updateAgentRealName", mbrLglAgent);
			LOGGER.debug("### User.updateAgentRealName row : {}", row);

			if (row == 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}

			// 법정대리인 동의여부 필드에 값(Y/N)이 존재하는 경우
			if (createUserRequest.getMbrLglAgent().getIsParent() != null) {
				if (createUserRequest.getMbrLglAgent().getIsParent().equalsIgnoreCase(Constant.TYPE_YN_Y)
						|| createUserRequest.getMbrLglAgent().getIsParent().equalsIgnoreCase(Constant.TYPE_YN_N)) {

					// 법정대리인 동의여부 수정 LGL_AGENT_AGREE_YN
					UserMbr usermbrLglAgentYN = new UserMbr();
					usermbrLglAgentYN.setSystemID(usermbr.getSystemID());
					usermbrLglAgentYN.setUserKey(usermbr.getUserKey());
					usermbrLglAgentYN.setIsParent(createUserRequest.getMbrLglAgent().getIsParent());

					row = this.commonDAO.update("User.updateLglAgentAgreeYN", usermbrLglAgentYN);
					LOGGER.debug("### User.updateLglAgentAgreeYN row : {}", row);

					if (row == 0) {
						throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
					}
				}
			}

		}

		// ACTION 5. 실명인증 정보 추가
		if (createUserRequest.getMbrAuth() != null) {
			MbrAuth mbrAuth = new MbrAuth();
			mbrAuth = createUserRequest.getMbrAuth();
			mbrAuth.setMemberKey(generatedUserKey);
			row = (Integer) this.commonDAO.insert("User.updateOwnRealName", mbrAuth);
			LOGGER.debug("### 2  row : {}", row);

			if (row == 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}

			// 실명인증여부 필드에 값(Y/N)이 존재하는 경우
			if (createUserRequest.getMbrAuth().getIsRealName() != null) {
				if (createUserRequest.getMbrAuth().getIsRealName().equalsIgnoreCase(Constant.TYPE_YN_Y)
						|| createUserRequest.getMbrAuth().getIsRealName().equalsIgnoreCase(Constant.TYPE_YN_N)) {

					// 실명인증 동의정보 있음
					// 실명_인증_여부 수정 REALNM_AUTH_YN -> Y
					UserMbr usermbrIsRealNameAuthYN = new UserMbr();
					usermbrIsRealNameAuthYN.setSystemID(usermbr.getSystemID());
					usermbrIsRealNameAuthYN.setUserKey(usermbr.getUserKey());
					usermbrIsRealNameAuthYN.setIsRealName(createUserRequest.getMbrAuth().getIsRealName());

					// 2014-08-29 vanddang 실명인증시 tb_us_usermbr 테이블의 MBR_NM, BIRTH, SEX 정보까지 업데이트
					usermbrIsRealNameAuthYN.setUserName(createUserRequest.getMbrAuth().getName());
					usermbrIsRealNameAuthYN.setUserBirthDay(createUserRequest.getMbrAuth().getBirthDay());
					usermbrIsRealNameAuthYN.setUserSex(createUserRequest.getMbrAuth().getSex());

					row = this.commonDAO.update("User.updateRealNameAuthYN", usermbrIsRealNameAuthYN);
					LOGGER.debug("### User.updateRealNameAuthYN row : {}", row);

					if (row == 0) {
						throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
					}

				}
			}

			// TLog
			final String tlogUserKey = generatedUserKey;
			final String tlogSystemID = createUserRequest.getCommonRequest().getSystemID();
			final String tlogMNO = createUserRequest.getMbrAuth().getTelecom();

			new TLogUtil().log(new ShuttleSetter() {
				@Override
				public void customize(TLogSentinelShuttle shuttle) {
					shuttle.log_id("TL_SC_MEM_0002").insd_usermbr_no(tlogUserKey).system_id(tlogSystemID)
							.mno_type(tlogMNO).result_code("SUCC");
				}
			});

		}

		// ACTION 6. 사용자 관리항목(부가속성) 정보 추가
		if (createUserRequest.getMbrMangItemPtcrList() != null) {
			List<MbrMangItemPtcr> mbrMangItemPtcrList = createUserRequest.getMbrMangItemPtcrList();
			for (MbrMangItemPtcr mbrMangItemPtcr : mbrMangItemPtcrList) {
				mbrMangItemPtcr.setUserKey(generatedUserKey);
				mbrMangItemPtcr.setUserID(createUserRequest.getUserMbr().getUserID());

				row = this.commonDAO.update("User.updateManagement", mbrMangItemPtcr);
				LOGGER.debug("###  3 row : {}", row);

				if (row == 0) {
					throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
				}
			}
		}

		// ACTION 7. 약관동의 목록정보 추가
		if (createUserRequest.getMbrClauseAgreeList() != null) {
			List<MbrClauseAgree> mbrClauseAgreeList = createUserRequest.getMbrClauseAgreeList();
			for (int i = 0; i < mbrClauseAgreeList.size(); i++) {
				MbrClauseAgree mbrClauseAgree = mbrClauseAgreeList.get(i);
				mbrClauseAgree.setMemberKey(generatedUserKey);
				row = this.commonDAO.update("User.updateAgreement", mbrClauseAgree);
				LOGGER.debug("###  4 row : {}", row);

				if (row == 0) {
					throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
				}
			}
		}

		// ACTION 8. 결과 반환
		createUserResponse.setUserKey(generatedUserKey);
		createUserResponse.setUserID(createUserRequest.getUserMbr().getUserID());
		createUserResponse.setUserMainStatus(createUserRequest.getUserMbr().getUserMainStatus());
		createUserResponse.setUserSubStatus(createUserRequest.getUserMbr().getUserSubStatus());
		createUserResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));

		final String tlogUserKey = usermbr.getUserKey();
		final String tlogUserID = usermbr.getUserID();

		new TLogUtil().set(new ShuttleSetter() {
			@Override
			public void customize(TLogSentinelShuttle shuttle) {
				shuttle.insd_usermbr_no(tlogUserKey).mbr_id(tlogUserID).usermbr_no("");
			}
		});

		return createUserResponse;
	}

	/**
	 * <pre>
	 * ID/EMAIL 존재여부 확인.
	 * </pre>
	 * 
	 * @param checkDuplicationRequest
	 *            사용자회원 ID/이메일 존재여부 확인 요청 Value Object.
	 * @return CheckDuplicationResponse - 사용자회원 ID/이메일 존재여부 확인 응답 Value Object.
	 */
	@Override
	public CheckDuplicationResponse checkDuplication(CheckDuplicationRequest checkDuplicationRequest) {

		// TO DO.
		// ACTION 1. 회원인경우 회원정보 리턴
		// ACTION 2-1. 회원은 아니지만 keyType 통합서비스 관리 번호인 경우
		// ACTION 2-2. 회원은 아니지만 keyType UserID인 경우
		// ACTION 3. 나머지는 등록 안됨으로 리턴

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("서비스 - ID/EMAIL 존재여부 확인");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### checkDuplicationRequest : {}", checkDuplicationRequest);

		CheckDuplicationResponse checkDuplicationResponse = new CheckDuplicationResponse();
		checkDuplicationResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));

		SearchUserKey searchUserKey = new SearchUserKey();
		searchUserKey.setKeySearchList(checkDuplicationRequest.getKeySearchList());
		searchUserKey.setTenantID(checkDuplicationRequest.getCommonRequest().getTenantID());

		boolean isDeviceRequest = false;
		List<KeySearch> keySearchList = checkDuplicationRequest.getKeySearchList();
		for (KeySearch keySearch : keySearchList) {
			if (keySearch.getKeyType().equals(Constant.SEARCH_TYPE_DEVICE_KEY)
					|| keySearch.getKeyType().equals(Constant.SEARCH_TYPE_DEVICE_ID)
					|| keySearch.getKeyType().equals(Constant.SEARCH_TYPE_MDN)) {
				isDeviceRequest = true;
			}
		}

		String userKey = null;
		String isDormant = Constant.TYPE_YN_N; // 휴면계정유무
		CommonDAO dao = this.commonDAO;
		// 휴대기기 정보로 조회
		if (isDeviceRequest) {
			userKey = dao.queryForObject("User.getUserKeyByKeySearchListD", searchUserKey, String.class);
		} else {
			userKey = dao.queryForObject("User.getUserKeyByKeySearchList", searchUserKey, String.class);
		}

		// 휴면DB 조회
		if (StringUtils.isBlank(userKey)) {
			dao = this.idleDAO;
			if (isDeviceRequest) {
				userKey = dao.queryForObject("User.getUserKeyByKeySearchListD", searchUserKey, String.class);
			} else {
				userKey = dao.queryForObject("User.getUserKeyByKeySearchList", searchUserKey, String.class);
			}
			if (StringUtils.isNotBlank(userKey)) {
				isDormant = Constant.TYPE_YN_Y;
			}
		}

		// ACTION 1. 회원인경우 회원정보 리턴
		if (userKey != null && userKey.length() > 0) {
			UserMbr userMbr = new UserMbr();
			userMbr.setUserKey(userKey);
			userMbr.setTenantID(checkDuplicationRequest.getCommonRequest().getTenantID());
			userMbr = dao.queryForObject("User.getUserDetail", userMbr, UserMbr.class);
			userMbr.setIsDormant(isDormant); // 휴면계정유무
			checkDuplicationResponse.setUserMbr(userMbr);
			checkDuplicationResponse.setIsRegistered(Constant.TYPE_YN_Y);
			checkDuplicationResponse.setUserID(userMbr.getUserID());
		} else {
			checkDuplicationResponse.setIsRegistered(Constant.TYPE_YN_N);
		}

		// ACTION 2. 나머지는 등록 안됨으로 리턴
		return checkDuplicationResponse;
	}

	/*
	 * <pre> ID와 비밀번호 또는 device ID로 로그인하는 기능을 제공한다. </pre>
	 * 
	 * @param loginUserRequest 로그인 요청 Value Object
	 * 
	 * @return LoginUserResponse - 로그인 응답 Value Object
	 * 
	 * @see com.skplanet.storeplatform.member.user.service.UserService#loginUser(
	 * com.skplanet.storeplatform.member.client .user.sci.vo.LoginUserRequest)
	 */
	@Override
	public LoginUserResponse updateLoginUser(LoginUserRequest loginUserRequest) {

		LoginUserResponse loginUserResponse = new LoginUserResponse();
		UserMbrRetrieveUserMbrPwd userMbrRetrieveUserMbrPwd = new UserMbrRetrieveUserMbrPwd();
		String isDormant = Constant.TYPE_YN_N;

		// 회원타입별 회원정보 조회
		if (StringUtils.equals(loginUserRequest.getIsMobile(), Constant.TYPE_YN_Y)) { // 모바일 회원

			// 휴대기기 정보 조회
			UserMbrDevice tempDevice = this.commonDAO.queryForObject("User.getUserIDByDeviceID", loginUserRequest,
					UserMbrDevice.class);

			if (tempDevice == null) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.userKeyNotFound", ""));
			}

			userMbrRetrieveUserMbrPwd.setTenantID(loginUserRequest.getCommonRequest().getTenantID());
			userMbrRetrieveUserMbrPwd.setUserID(tempDevice.getDeviceNickName()); // 조회된 deviceNickName이 mbr_id 이다...
			userMbrRetrieveUserMbrPwd = this.commonDAO.queryForObject("User.getUserMbrRetrievePWD",
					userMbrRetrieveUserMbrPwd, UserMbrRetrieveUserMbrPwd.class);

			if (userMbrRetrieveUserMbrPwd == null) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.userKeyNotFound", ""));
			}

			// TLog
			final String tlogUserKey = userMbrRetrieveUserMbrPwd.getUserKey();
			final String tlogDeviceID = loginUserRequest.getUserID();
			final String tlogImSvcNo = tempDevice.getSvcMangNum();
			final String tlogMNO = tempDevice.getDeviceTelecom();
			final String tlogIEMI = tempDevice.getNativeID();
			final String tlogMODEL = tempDevice.getDeviceModelNo();
			String isSKTelecom = "N"; // 자사폰여부
			if (StringUtils.isNotBlank(tempDevice.getDeviceTelecom())
					&& StringUtils.equals(tempDevice.getDeviceTelecom(), "US001201")) {
				isSKTelecom = "Y";
			}
			final String tlogCompanyOwnPhoneYn = isSKTelecom;
			final String tlogImMbrNo = userMbrRetrieveUserMbrPwd.getImMbrNo();

			new TLogUtil().set(new ShuttleSetter() {
				@Override
				public void customize(TLogSentinelShuttle shuttle) {
					shuttle.device_id(tlogDeviceID).insd_usermbr_no(tlogUserKey).svc_mng_no(tlogImSvcNo)
							.company_own_phone_yn(tlogCompanyOwnPhoneYn).mno_type(tlogMNO).usermbr_no(tlogImMbrNo)
							.imei(tlogIEMI).phone_model(tlogMODEL);
				}
			});

		} else { // 아이디 회원

			userMbrRetrieveUserMbrPwd.setTenantID(loginUserRequest.getCommonRequest().getTenantID());
			userMbrRetrieveUserMbrPwd.setUserID(loginUserRequest.getUserID());

			// TODO. 동일한 userId가 2개 존재한경우 에러가 발생한다. 1건만 조회하도록 수정할지는 추후에 확인 필요.
			userMbrRetrieveUserMbrPwd = this.commonDAO.queryForObject("User.getUserMbrRetrievePWD",
					userMbrRetrieveUserMbrPwd, UserMbrRetrieveUserMbrPwd.class);

			if (userMbrRetrieveUserMbrPwd == null) {
				// 휴면DB조회
				userMbrRetrieveUserMbrPwd = new UserMbrRetrieveUserMbrPwd();
				userMbrRetrieveUserMbrPwd.setTenantID(loginUserRequest.getCommonRequest().getTenantID());
				userMbrRetrieveUserMbrPwd.setUserID(loginUserRequest.getUserID());
				userMbrRetrieveUserMbrPwd = this.idleDAO.queryForObject("User.getUserMbrRetrievePWD",
						userMbrRetrieveUserMbrPwd, UserMbrRetrieveUserMbrPwd.class);
				if (userMbrRetrieveUserMbrPwd == null) {
					throw new StorePlatformException(this.getMessage("response.ResultCode.userKeyNotFound", ""));
				} else {
					isDormant = Constant.TYPE_YN_Y;
				}
			}

			// TLog
			final String tlogUserID = loginUserRequest.getUserID();
			final String tlogUserKey = userMbrRetrieveUserMbrPwd.getUserKey();
			final String tlogImMbrNo = userMbrRetrieveUserMbrPwd.getImMbrNo();
			new TLogUtil().set(new ShuttleSetter() {
				@Override
				public void customize(TLogSentinelShuttle shuttle) {
					shuttle.mbr_id(tlogUserID).insd_usermbr_no(tlogUserKey).usermbr_no(tlogImMbrNo);
				}
			});

			// 원아이디 유무
			if (StringUtils.equals(userMbrRetrieveUserMbrPwd.getUserType(), UserTypeCode.ONEID_USER.getCode())) {
				final String tlogOneId = loginUserRequest.getUserID();
				new TLogUtil().set(new ShuttleSetter() {
					@Override
					public void customize(TLogSentinelShuttle shuttle) {
						shuttle.one_id(tlogOneId);
					}
				});
			}
		}

		// 로그인 성공유무에 따른 이력 저장
		if (StringUtils.equals(loginUserRequest.getIsSuccess(), Constant.TYPE_YN_Y)) { // 로그인 성공

			// 로그인 성공 이력 저장
			UserMbrLoginLog userMbrLoginLog = new UserMbrLoginLog();
			userMbrLoginLog.setTenantID(loginUserRequest.getCommonRequest().getTenantID());
			userMbrLoginLog.setSystemID(loginUserRequest.getCommonRequest().getSystemID());
			userMbrLoginLog.setUserKey(userMbrRetrieveUserMbrPwd.getUserKey());

			// TLOG - TL_SC_MEM_0001 (deviceIp) 항목에는 Ip를 남기고
			// tb_us_usermbr_login_log의 connIp 에는 deviceId를 남기 도록 처리.
			// 현재 호출하는 모든 부분의 userId에 deviceId가 넘어오고 있음.
			if (StringUtils.equals(loginUserRequest.getIsMobile(), Constant.TYPE_YN_Y)) { // 모바일 회원
				if (StringUtils.isNotBlank(loginUserRequest.getUserID())) {
					userMbrLoginLog.setConnIp(loginUserRequest.getUserID());
				}
			} else {
				if (StringUtils.isNotBlank(loginUserRequest.getIpAddress())) {
					userMbrLoginLog.setConnIp(loginUserRequest.getIpAddress());
				}
			}

			if (StringUtils.isNotBlank(loginUserRequest.getScVersion())) {
				userMbrLoginLog.setScVersion(loginUserRequest.getScVersion());
			}

			// 2014.02.28 자동로그인 여부 필드 추가
			if (StringUtils.isNotBlank(loginUserRequest.getIsAutoLogin())) {
				userMbrLoginLog.setIsAutoLogin(loginUserRequest.getIsAutoLogin());
			}
			// 2014.04.25 로그인 사유 추가
			if (StringUtils.isNotBlank(loginUserRequest.getLoginReason())) {
				userMbrLoginLog.setLoginReason(loginUserRequest.getLoginReason());
			}

			if (StringUtils.isNotBlank(loginUserRequest.getDeviceModelNm())) {
				userMbrLoginLog.setDeviceModelNm(loginUserRequest.getDeviceModelNm());
			}

			if (StringUtils.isNotBlank(loginUserRequest.getDeviceOsNm())) {
				userMbrLoginLog.setDeviceOsNm(loginUserRequest.getDeviceOsNm());
			}

			if (StringUtils.isNotBlank(loginUserRequest.getDeviceOsVersion())) {
				userMbrLoginLog.setDeviceOsVersion(loginUserRequest.getDeviceOsVersion());
			}

			int row = 0;

			try {
				row = (Integer) this.commonDAO.insert("User.insertLoginLog", userMbrLoginLog);
			} catch (Exception e) {
				// 중복요청으로 conn_dt가 동일하게 들어가 중복키 에러가 발생할 수 있으므로, Exception을 무시한다.
				row = 0;
			}

			// 마지막 로그인 일자 업데이트 유무
			if (StringUtils.isNotBlank(loginUserRequest.getIsUpdLastLoginDt())
					&& StringUtils.equals(loginUserRequest.getIsUpdLastLoginDt(), Constant.TYPE_YN_Y)) {

				// DA팀의 TB_US_USERMBR.LAST_LOGIN_DT의 update 빈도 줄여 달라는 요청으로 조건 추가
				String todayAlreadyUpdatedLastLoginDt = this.commonDAO.queryForObject(
						"User.selectAlreadyLastLoginDtUpdateYn", userMbrLoginLog, String.class);

				if (todayAlreadyUpdatedLastLoginDt == null) {
					this.commonDAO.update("User.updateLastLoginDt", userMbrLoginLog);
				}
			}

			// 로그인 실패카운트 초기화
			if (userMbrRetrieveUserMbrPwd.getFailCnt() > 0) {
				MbrPwd mbrPwd = new MbrPwd();
				mbrPwd.setTenantID(loginUserRequest.getCommonRequest().getTenantID());
				mbrPwd.setMemberKey(userMbrRetrieveUserMbrPwd.getUserKey());
				this.commonDAO.update("User.updateLoginSuccess", mbrPwd);
			}

			loginUserResponse.setUserMainStatus(userMbrRetrieveUserMbrPwd.getUserMainStatus());
			loginUserResponse.setUserSubStatus(userMbrRetrieveUserMbrPwd.getUserSubStatus());
			loginUserResponse.setIsLoginSuccess(Constant.TYPE_YN_Y);
			loginUserResponse.setLoginFailCount(0);
			loginUserResponse.setLoginStatusCode(userMbrRetrieveUserMbrPwd.getLoginStatusCode());
			loginUserResponse.setStopStatusCode(userMbrRetrieveUserMbrPwd.getStopStatusCode());

		} else { // 로그인 실패

			// 2.1.5.ID 기반 회원 인증API에서 비밀번호 불일치인 경우만 해당.
			MbrPwd mbrPwd = new MbrPwd();
			mbrPwd.setTenantID(loginUserRequest.getCommonRequest().getTenantID());
			mbrPwd.setMemberKey(userMbrRetrieveUserMbrPwd.getUserKey());
			if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
				this.commonDAO.update("User.updateLoginFail", mbrPwd);
			} else { // 휴면계정이 비밀번호 불일치시 복구처리가 되지 않는 경우
				this.idleDAO.update("User.updateLoginFail", mbrPwd);
			}

			loginUserResponse.setUserMainStatus(userMbrRetrieveUserMbrPwd.getUserMainStatus());
			loginUserResponse.setUserSubStatus(userMbrRetrieveUserMbrPwd.getUserSubStatus());
			loginUserResponse.setIsLoginSuccess(Constant.TYPE_YN_N);
			loginUserResponse.setLoginFailCount(userMbrRetrieveUserMbrPwd.getFailCnt() + 1);
			loginUserResponse.setLoginStatusCode(userMbrRetrieveUserMbrPwd.getLoginStatusCode());
			loginUserResponse.setStopStatusCode(userMbrRetrieveUserMbrPwd.getStopStatusCode());

		}

		loginUserResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));

		return loginUserResponse;

	}

	/**
	 * <pre>
	 * 회원탈퇴를 처리하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param removeUserRequest
	 *            사용자 탈퇴 요청을 위한 value object
	 * @return RemoveUserResponse 사용자 탈퇴에 대한 응답을 위한 value object
	 */
	@Override
	public RemoveUserResponse remove(RemoveUserRequest removeUserRequest) {

		// TO DO.
		// ACTION 1. 존재하지 않는 회원키는 에러코드 반환
		// ACTION 2. 회원 이력 테이블 insert.
		// ACTION 3. 회원 속성 탈퇴정보 update.(휴대기기 보유 카운트 초기화)

		// 아래는 배치로 이동
		// - ACTION 4. 회원 개인식별 탈퇴 정보 저장
		// - ACTION 5. 회원 탈퇴 정보 저장
		// - ACTION 6. 휴대기기 전체 목록 삭제

		// ACTION 6-1. 휴대기기 전체 목록 조회
		// ACTION 6-2. 휴대기기 삭제 요청
		// ACTION 6-2-1. 휴대기기 이력 테이블 insert.
		// ACTION 6-2-2. 휴대기기 속성의 USE_YN, AUTH_YN 을 N 으로 update.
		// ACTION 6-2-3. 휴대기기 부가속성의 도토리사용여부를 N 으로 업데이트.(US011409 -> N)

		// 아래는 배치로 이동
		// - ACTION 7. 회원 정보 삭제

		LOGGER.debug(">>>> >>> UserServiceImpl remove : {}", removeUserRequest);
		String isDormant = StringUtils.isBlank(removeUserRequest.getIsDormant()) ? Constant.TYPE_YN_N : removeUserRequest
				.getIsDormant();
		CommonDAO dao = null;
		if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
			dao = this.commonDAO;
		} else {
			dao = this.idleDAO;
		}

		RemoveUserResponse removeUserResponse = new RemoveUserResponse();

		UserMbr usermbr = new UserMbr();
		usermbr.setTenantID(removeUserRequest.getCommonRequest().getTenantID());
		usermbr.setUserKey(removeUserRequest.getUserKey());

		String isRegistered = null;
		isRegistered = dao.queryForObject("User.isRegisteredKey", usermbr, String.class);
		LOGGER.debug("### isRegisteredKey : {}", isRegistered);

		// ACTION 1. 존재하지 않는 회원키는 에러코드 반환
		if (isRegistered == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.userKeyNotFound", ""));
		}

		int row = 0;
		if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
			// ACTION 2. 회원 이력 테이블 insert.
			row = dao.update("User.insertUpdateStatusHistory", usermbr);
			LOGGER.debug("### insertUpdateStatusHistory row : {}", row);
			if (row <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}
		}

		usermbr = new UserMbr();
		usermbr.setTenantID(removeUserRequest.getCommonRequest().getTenantID());
		usermbr.setUserKey(removeUserRequest.getUserKey());
		usermbr.setSecedeReasonCode(removeUserRequest.getSecedeReasonCode());
		usermbr.setSecedeReasonMessage(removeUserRequest.getSecedeReasonMessage());
		usermbr.setUserMainStatus(MainStateCode.SECEDE.getCode()); // 탈퇴 MAIN CODE, US010202
		usermbr.setUserSubStatus(SubStateCode.WITHDRAW.getCode()); // 탈퇴완료 SUB CODE, US010303
		usermbr.setSecedeDate(Utils.getLocalDateTimeinYYYYMMDD());

		// old source removeuser

		// ACTION 4. 회원 개인식별 탈퇴 정보 저장
		// row = this.commonDAO.update("User.removeUserToUsermbrPrsnStWd", usermbr);
		// LOGGER.debug("### removeUser row : {}", row);
		// if (row <= 0) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
		// }

		// ACTION 5. 회원 탈퇴 정보 저장
		// row = this.commonDAO.update("User.removeUserToUsermbrWd", usermbr);
		// LOGGER.debug("### removeUser row : {}", row);
		// if (row <= 0) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
		// }

		// ACTION 6. 휴대기기 전체 목록 삭제
		List<KeySearch> keySearchList;
		KeySearch keySearch;
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_USER_KEY);
		keySearch.setKeyString(removeUserRequest.getUserKey());
		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		SearchDeviceListRequest searchDeviceListRequest;
		searchDeviceListRequest = new SearchDeviceListRequest();
		searchDeviceListRequest.setCommonRequest(removeUserRequest.getCommonRequest());
		searchDeviceListRequest.setKeySearchList(keySearchList);
		searchDeviceListRequest.setIsMainDevice(Constant.TYPE_YN_N);

		// ACTION 6-1. 휴대기기 전체 목록 조회
		UserMbr userMbr = null;
		CommonRequest commonRequest = new CommonRequest();

		commonRequest.setTenantID(searchDeviceListRequest.getCommonRequest().getTenantID());

		boolean isDeviceRequest = false;
		List<KeySearch> keySearchList2 = searchDeviceListRequest.getKeySearchList();
		for (KeySearch keySearch2 : keySearchList2) {
			if (keySearch2.getKeyType().equals(Constant.SEARCH_TYPE_DEVICE_KEY)
					|| keySearch2.getKeyType().equals(Constant.SEARCH_TYPE_DEVICE_ID)) {
				isDeviceRequest = true;
			}
		}

		// 휴대기기 정보로 조회
		if (isDeviceRequest) {
			userMbr = dao.queryForObject("Device.searchDeviceListD", searchDeviceListRequest, UserMbr.class);
		} else {
			userMbr = dao.queryForObject("Device.searchDeviceList", searchDeviceListRequest, UserMbr.class);
		}

		if (userMbr != null && userMbr.getUserID() != null) {

			commonRequest.setSystemID(userMbr.getUserID());
			searchDeviceListRequest.setCommonRequest(commonRequest);

			SearchDeviceListResponse searchDeviceListResponse = null;

			// 휴대기기 정보로 조회
			if (isDeviceRequest) {
				searchDeviceListResponse = dao.queryForObject("Device.searchDeviceList2D", searchDeviceListRequest,
						SearchDeviceListResponse.class);
			} else {
				searchDeviceListResponse = dao.queryForObject("Device.searchDeviceList2", searchDeviceListRequest,
						SearchDeviceListResponse.class);
			}

			LOGGER.debug("### searchDeviceListResponse : {}", searchDeviceListResponse);

			// ACTION 3. 회원 속성 탈퇴정보 update.(휴대기기 보유 카운트 초기화)
			row = dao.update("User.removeUser", usermbr);
			LOGGER.debug("### removeUser row : {}", row);
			if (row <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}

			List<UserMbrDevice> userMbrDeviceList = searchDeviceListResponse.getUserMbrDevice();
			List<String> deviceKeyList = new ArrayList<String>();
			if (userMbrDeviceList != null && userMbrDeviceList.size() > 0) {
				for (UserMbrDevice userMbrDevice : userMbrDeviceList) {
					if (userMbrDevice.getDeviceKey() != null)
						deviceKeyList.add(userMbrDevice.getDeviceKey());
				}
			}

			// ACTION 6-2. 휴대기기 삭제 요청
			RemoveDeviceRequest removeDeviceRequest = new RemoveDeviceRequest();
			removeDeviceRequest.setCommonRequest(removeUserRequest.getCommonRequest());
			removeDeviceRequest.setUserKey(removeUserRequest.getUserKey());
			removeDeviceRequest.setDeviceKey(deviceKeyList);

			for (int i = 0; i < deviceKeyList.size(); i++) {

				UserMbrDevice userMbrDevice = new UserMbrDevice();
				userMbrDevice.setUserKey(removeUserRequest.getUserKey());
				userMbrDevice.setDeviceKey(deviceKeyList.get(i));
				userMbrDevice.setIsUsed(Constant.TYPE_YN_N);

				if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
					// ACTION 6-2-1. 휴대기기 이력 테이블 insert.
					row = dao.update("Device.insertUpdateDeviceHistory", userMbrDevice);
					if (row <= 0) {
						throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
					}
					LOGGER.debug("### insertUpdateDeviceHistory row : {}", row);
				}

				// ACTION 6-2-2. 휴대기기 속성의 USE_YN, AUTH_YN 을 N 으로 update.
				row = dao.update("Device.removeDevice", userMbrDevice);
				if (row <= 0) {
					throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
				}
				LOGGER.debug("### removeDevice row : {}", row);

				// ACTION 6-2-3. 휴대기기 부가속성의 도토리사용여부를 N 으로 업데이트.(US011409 -> N)
				UserMbrDeviceDetail userMbrDeviceDetail = new UserMbrDeviceDetail();
				userMbrDeviceDetail.setUserKey(removeUserRequest.getUserKey());
				userMbrDeviceDetail.setDeviceKey(deviceKeyList.get(i));
				userMbrDeviceDetail.setExtraProfile(DeviceManagementCode.DOTORI_YN.getCode()); // 도토리 인증여부 CD_ID,
																							   // "US011409"
				userMbrDeviceDetail.setExtraProfileValue(Constant.TYPE_YN_N);

				LOGGER.debug("### DeviceManagementCode.DOTORI_YN.getCode() : {}",
						DeviceManagementCode.DOTORI_YN.getCode());

				row = dao.update("Device.removeDotori", userMbrDeviceDetail);
				// 예외처리 삭제 : 토토리 관리항목이 없는 경우가 있음.
				// if (row <= 0) {
				// throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
				// }
				LOGGER.debug("### removeDotori row : {}", row);
			}
		}

		// ACTION 7. 회원 정보 삭제
		// 회원상태만 Update하고 데이터는 배치에서 삭제한다.
		// Modified by Brian Ahn : 2014.01.20
		// row = this.commonDAO.delete("User.removeUserInUsermbr", usermbr);
		// LOGGER.debug("### removeUser row : {}", row);
		// if (row <= 0) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.removeError", ""));
		// }

		removeUserResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));
		return removeUserResponse;
	}

	/**
	 * <pre>
	 * 사용자 약관동의 등록/수정.
	 * </pre>
	 * 
	 * @param updateRightRequest
	 *            사용자 약관동의 등록/수정 요청 Value Object
	 * @return UpdateAgreementResponse - 사용자 약관동의 등록/수정 응답 Value Object
	 */
	@Override
	public UpdateAgreementResponse updateAgreement(UpdateAgreementRequest updateRightRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("사용자 서비스 - 사용자 약관동의 등록/수정");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### updateRightRequest : {}", updateRightRequest);

		String isDormant = StringUtils.isBlank(updateRightRequest.getIsDormant()) ? Constant.TYPE_YN_N : updateRightRequest
				.getIsDormant();
		CommonDAO dao = null;
		if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
			dao = this.commonDAO;
		} else {
			dao = this.idleDAO;
		}

		UpdateAgreementResponse updateAgreementResponse = new UpdateAgreementResponse();
		updateAgreementResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));

		// 요청
		List<MbrClauseAgree> mbrClauseAgreeList = updateRightRequest.getMbrClauseAgreeList();
		for (int i = 0; i < mbrClauseAgreeList.size(); i++) {
			MbrClauseAgree mbrClauseAgree = mbrClauseAgreeList.get(i);
			mbrClauseAgree.setTenantID(updateRightRequest.getCommonRequest().getTenantID());
			mbrClauseAgree.setMemberKey(updateRightRequest.getUserKey());

			Integer row = dao.update("User.updateAgreement", mbrClauseAgree);
			LOGGER.debug("### row : {}", row);
			if (row <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}
		}

		return updateAgreementResponse;
	}

	/**
	 * <pre>
	 * 사용자 성인인증 정보 수정.
	 * </pre>
	 * 
	 * @param updateRealNameRequest
	 *            사용자 성인인증 정보 수정 요청 Value Object
	 * @return UpdateRealNameResponse - 사용자 성인인증 정보 수정 응답 Value Object
	 */
	@Override
	public UpdateRealNameResponse updateRealName(UpdateRealNameRequest updateRealNameRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("서비스 - 성인인증 정보 수정");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		String isDormant = StringUtils.isBlank(updateRealNameRequest.getIsDormant()) ? Constant.TYPE_YN_N : updateRealNameRequest
				.getIsDormant();
		CommonDAO dao = null;
		if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
			dao = this.commonDAO;
		} else {
			dao = this.idleDAO;
		}
		Integer row = (Integer) dao.insert("User.updateRealName", updateRealNameRequest);
		LOGGER.debug("### row : {}", row);

		if (row <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
		}

		// 실명인증 대상이 본인인 경우
		if (updateRealNameRequest.getIsOwn() != null
				&& updateRealNameRequest.getIsOwn().equalsIgnoreCase(Constant.REAL_NAME_TYPE_OWN)) {

			if (updateRealNameRequest.getMbrAuth().getIsRealName() != null) {

				if (updateRealNameRequest.getMbrAuth().getIsRealName().equalsIgnoreCase(Constant.TYPE_YN_Y)
						|| updateRealNameRequest.getMbrAuth().getIsRealName().equalsIgnoreCase(Constant.TYPE_YN_N)) {

					// 성별 파라미터 M/F 인지 체크
					if (StringUtils.isNotBlank(updateRealNameRequest.getMbrAuth().getSex())) {
						if (!this.checkValidateUserSexValue(updateRealNameRequest.getMbrAuth().getSex())) {
							updateRealNameRequest.getMbrAuth().setSex(null);
						}
					}

					if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
						// 회원 정보 수정 이전 기존 정보 회원 이력 테이블에 저장.
						this.insertUserMbrHistory(updateRealNameRequest.getUserKey());
					}

					// 실명 인증 정보 등록 시 기존에 법정 대리인 동의 여부가 'Y' 인 경우 'N'으로 업데이트
					UserMbr usermbr = new UserMbr();
					usermbr.setSystemID(updateRealNameRequest.getCommonRequest().getSystemID());
					usermbr.setUserKey(updateRealNameRequest.getUserKey());
					UserMbrInfo userInfo = dao.queryForObject("User.selectUsermbr", usermbr, UserMbrInfo.class);

					if (userInfo != null) {
						if (StringUtils.equals(userInfo.getLglAgentAgreeYn(), Constant.TYPE_YN_Y)) {
							// 법정대리인 동의여부 수정 LGL_AGENT_AGREE_YN
							UserMbr usermbrLglAgentYN = new UserMbr();
							usermbrLglAgentYN.setSystemID(usermbr.getSystemID());
							usermbrLglAgentYN.setUserKey(usermbr.getUserKey());
							usermbrLglAgentYN.setIsParent(Constant.TYPE_YN_N);

							dao.update("User.updateLglAgentAgreeYN", usermbrLglAgentYN);
						}
					}

					// 실명_인증_여부 수정 REALNM_AUTH_YN -> Y
					UserMbr usermbrIsRealNameAuthYN = new UserMbr();
					usermbrIsRealNameAuthYN.setSystemID(updateRealNameRequest.getCommonRequest().getSystemID());
					usermbrIsRealNameAuthYN.setUserKey(updateRealNameRequest.getUserKey());
					usermbrIsRealNameAuthYN.setIsRealName(updateRealNameRequest.getMbrAuth().getIsRealName());

					// 2014-08-01 vanddang 실명인증시 tb_us_ousermbr 테이블의 MBR_NM, BIRTH, SEX 정보까지 업데이트
					usermbrIsRealNameAuthYN.setUserName(updateRealNameRequest.getMbrAuth().getName());
					usermbrIsRealNameAuthYN.setUserBirthDay(updateRealNameRequest.getMbrAuth().getBirthDay());
					usermbrIsRealNameAuthYN.setUserSex(updateRealNameRequest.getMbrAuth().getSex());
					row = dao.update("User.updateRealNameAuthYN", usermbrIsRealNameAuthYN);
					LOGGER.debug("### User.updateRealNameAuthYN row : {}", row);
					if (row == 0) {
						throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
					}
				}

			}
		} else {

			if (updateRealNameRequest.getMbrLglAgent().getIsParent() != null) {

				if (updateRealNameRequest.getMbrLglAgent().getIsParent().equalsIgnoreCase(Constant.TYPE_YN_Y)
						|| updateRealNameRequest.getMbrLglAgent().getIsParent().equalsIgnoreCase(Constant.TYPE_YN_N)) {

					if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
						// 회원 정보 수정 이전 기존 정보 회원 이력 테이블에 저장.
						this.insertUserMbrHistory(updateRealNameRequest.getUserKey());
					}

					// 실명인증 대상이 법정대리인인 경우
					// 법정대리인 동의여부 수정 LGL_AGENT_AGREE_YN -> Y
					UserMbr usermbrIsRealNameAuthYN = new UserMbr();
					usermbrIsRealNameAuthYN.setSystemID(updateRealNameRequest.getCommonRequest().getSystemID());
					usermbrIsRealNameAuthYN.setUserKey(updateRealNameRequest.getUserKey());
					usermbrIsRealNameAuthYN.setIsParent(updateRealNameRequest.getMbrLglAgent().getIsParent());

					row = dao.update("User.updateLglAgentAgreeYN", usermbrIsRealNameAuthYN);
					LOGGER.debug("### User.updateLglAgentAgreeYN row : {}", row);
					if (row == 0) {
						throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
					}
				}
			}

		}

		UpdateRealNameResponse updateRealNameResponse = new UpdateRealNameResponse();
		updateRealNameResponse.setUserKey(updateRealNameRequest.getUserKey());
		updateRealNameResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));
		return updateRealNameResponse;

	}

	/**
	 * <pre>
	 * 사용자 기본정보 조회.
	 * </pre>
	 * 
	 * @param searchUserRequest
	 *            사용자 회원 기본정보 조회 요청 Value Object
	 * @return SearchUserResponse - 사용자 회원 기본정보 조회 응답 Value Object
	 */
	@Override
	public SearchUserResponse searchUser(SearchUserRequest searchUserRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("사용자 서비스 - 사용자회원 기본정보 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### searchUserRequest : {}", searchUserRequest.toString());

		// 조회
		SearchUserResponse searchUserResponse = null;
		boolean isDeviceRequest = false;
		String isDormant = Constant.TYPE_YN_N; // 휴면계정유무
		List<KeySearch> keySearchList = searchUserRequest.getKeySearchList();
		for (KeySearch keySearch : keySearchList) {
			if (keySearch.getKeyType().equals(Constant.SEARCH_TYPE_DEVICE_KEY)
					|| keySearch.getKeyType().equals(Constant.SEARCH_TYPE_DEVICE_ID)
					|| keySearch.getKeyType().equals(Constant.SEARCH_TYPE_MDN)) {
				isDeviceRequest = true;
			}
		}

		// 휴대기기 정보로 조회
		searchUserRequest.setIsDormant(Constant.TYPE_YN_N);
		if (isDeviceRequest) {
			searchUserResponse = this.commonDAO.queryForObject("User.searchUserD", searchUserRequest,
					SearchUserResponse.class);
		} else {
			searchUserResponse = this.commonDAO.queryForObject("User.searchUser", searchUserRequest,
					SearchUserResponse.class);
		}

		if (searchUserResponse == null) {
			// 휴면DB 조회
			searchUserRequest.setIsDormant(Constant.TYPE_YN_Y);
			if (isDeviceRequest) {
				searchUserResponse = this.idleDAO.queryForObject("User.searchUserD", searchUserRequest,
						SearchUserResponse.class);
			} else {
				searchUserResponse = this.idleDAO.queryForObject("User.searchUser", searchUserRequest,
						SearchUserResponse.class);
			}

			if (searchUserResponse != null)
				isDormant = Constant.TYPE_YN_Y;
		}

		// SearchUserResponse searchUserResponse = this.commonDAO.queryForObject("User.joinTest", searchUserRequest,
		// SearchUserResponse.class);

		if (searchUserResponse == null || searchUserResponse.getUserMbr() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.userKeyNotFound", ""));
		}

		// userKey
		if (searchUserResponse.getUserMbr() != null) {
			searchUserResponse.setUserKey(searchUserResponse.getUserMbr().getUserKey());
			searchUserResponse.getUserMbr().setIsDormant(isDormant); // 휴면계정유무
		}

		// 징계정보
		if (searchUserResponse.getUserMbrPnsh() == null) {
			UserMbrPnsh userMbrPnsh = new UserMbrPnsh();
			userMbrPnsh.setIsRestricted(Constant.TYPE_YN_N);
			searchUserResponse.setUserMbrPnsh(userMbrPnsh);
		} else {
			searchUserResponse.getUserMbrPnsh().setIsRestricted(Constant.TYPE_YN_Y);
		}

		// 실명인증 : 법정대리인
		if (searchUserResponse.getMbrLglAgent() == null) {
			MbrLglAgent mbrLglAgent = new MbrLglAgent();
			mbrLglAgent.setIsParent(Constant.TYPE_YN_N);
			searchUserResponse.setMbrLglAgent(mbrLglAgent);
		} else {
			searchUserResponse.getMbrLglAgent().setIsParent(Constant.TYPE_YN_Y);
		}

		// 실명인증 : 본인
		if (searchUserResponse.getMbrAuth() == null) {
			MbrAuth mbrAuth = new MbrAuth();
			mbrAuth.setIsRealName(Constant.TYPE_YN_N);
			searchUserResponse.setMbrAuth(mbrAuth);
		} else {
			searchUserResponse.getMbrAuth().setIsRealName(Constant.TYPE_YN_Y);
		}

		LOGGER.debug("### DB에서 받아온 결과 : {}", searchUserResponse);

		searchUserResponse.setIsChangeSubject(Constant.TYPE_YN_N);
		searchUserResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));
		return searchUserResponse;

	}

	/**
	 * <pre>
	 * 사용자회원 기본정보 수정.
	 * </pre>
	 * 
	 * @param updateUserRequest
	 *            사용자회원 기본정보 수정 요청 Value Object
	 * @return UpdateUserResponse - 사용자회원 기본정보 수정 응답을 위한 Value Object
	 */
	@Override
	public UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest) {

		// TO DO.
		// ID 있나없나 확인.
		// ACTION 1. 회원 정보 update.
		UpdateUserResponse updateUserResponse = new UpdateUserResponse();
		Integer row = 0;

		UserMbr usermbr = updateUserRequest.getUserMbr();
		usermbr.setSystemID(updateUserRequest.getCommonRequest().getSystemID());
		usermbr.setUserKey(updateUserRequest.getUserMbr().getUserKey());

		// 휴면계정유무
		String isDormant = StringUtils.isBlank(updateUserRequest.getIsDormant()) ? Constant.TYPE_YN_N : updateUserRequest
				.getIsDormant();
		CommonDAO dao = null;
		if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
			dao = this.commonDAO;
		} else {
			dao = this.idleDAO;
		}

		String isRegistered = null;
		MbrOneID mbrOneID = null;
		mbrOneID = dao.queryForObject("User.isRegisteredKeyAndMbrNo", usermbr, MbrOneID.class);
		if (mbrOneID == null) {
			isRegistered = null;
		} else {
			isRegistered = mbrOneID.getUserKey();
		}

		if (isRegistered != null) {
			if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
				// 회원 이력 테이블 insert.
				row = dao.update("User.insertUpdateStatusHistory", usermbr);
				LOGGER.debug("### updateuser - insertUpdateStatusHistory row : {}", row);
				if (row <= 0) {
					throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
				}
			}

			// 성별 파라미터 M/F 인지 체크
			if (StringUtils.isNotBlank(usermbr.getUserSex())) {
				if (!this.checkValidateUserSexValue(usermbr.getUserSex())) {
					usermbr.setUserSex(null);
				}
			}

			row = (Integer) dao.insert("User.updateUser", usermbr);

			LOGGER.debug("### row : {}", row);
			if (row == 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}

			// check 법정대리인
			if (updateUserRequest.getMbrLglAgent() != null) {
				MbrLglAgent mbrLglAgent = new MbrLglAgent();
				mbrLglAgent = updateUserRequest.getMbrLglAgent();
				mbrLglAgent.setMemberKey(updateUserRequest.getUserMbr().getUserKey());
				row = (Integer) dao.insert("User.updateAgentRealName", mbrLglAgent);
				LOGGER.debug("### 1 row : {}", row);
				if (row == 0) {
					throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
				}

				if (updateUserRequest.getMbrLglAgent().getIsParent() != null) {

					if (updateUserRequest.getMbrLglAgent().getIsParent().equalsIgnoreCase(Constant.TYPE_YN_Y)
							|| updateUserRequest.getMbrLglAgent().getIsParent().equalsIgnoreCase(Constant.TYPE_YN_N)) {

						// 법정대리인 동의정보 있음
						// 법정대리인 동의여부 수정 LGL_AGENT_AGREE_YN -> Y
						UserMbr usermbrLglAgentYN = new UserMbr();
						usermbrLglAgentYN.setSystemID(usermbr.getSystemID());
						usermbrLglAgentYN.setUserKey(usermbr.getUserKey());
						usermbrLglAgentYN.setIsParent(updateUserRequest.getMbrLglAgent().getIsParent());

						row = dao.update("User.updateLglAgentAgreeYN", usermbrLglAgentYN);
						LOGGER.debug("### User.updateLglAgentAgreeYN row : {}", row);
						if (row == 0) {
							throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError",
									""));
						}

					}

				}

			}

			// check 실명인증
			if (updateUserRequest.getMbrAuth() != null) {
				// TLog
				new TLogUtil().set(new ShuttleSetter() {
					@Override
					public void customize(TLogSentinelShuttle shuttle) {
						shuttle.log_id("TL_SC_MEM_0002");
					}
				});

				MbrAuth mbrAuth = new MbrAuth();
				mbrAuth = updateUserRequest.getMbrAuth();
				mbrAuth.setMemberKey(updateUserRequest.getUserMbr().getUserKey());
				row = (Integer) dao.insert("User.updateOwnRealName", mbrAuth);
				LOGGER.debug("### 2  row : {}", row);
				if (row == 0) {
					throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
				}

				if (updateUserRequest.getMbrAuth().getIsRealName() != null) {

					if (updateUserRequest.getMbrAuth().getIsRealName().equalsIgnoreCase(Constant.TYPE_YN_Y)
							|| updateUserRequest.getMbrAuth().getIsRealName().equalsIgnoreCase(Constant.TYPE_YN_N)) {

						// 실명인증 동의정보 있음
						// 실명_인증_여부 수정 REALNM_AUTH_YN -> Y
						UserMbr usermbrIsRealNameAuthYN = new UserMbr();
						usermbrIsRealNameAuthYN.setSystemID(usermbr.getSystemID());
						usermbrIsRealNameAuthYN.setTenantID(usermbr.getTenantID());
						usermbrIsRealNameAuthYN.setUserKey(usermbr.getUserKey());
						usermbrIsRealNameAuthYN.setIsRealName(updateUserRequest.getMbrAuth().getIsRealName());
						// 2014-08-29 vanddang 실명인증시 tb_us_usermbr 테이블의 MBR_NM, BIRTH, SEX 정보까지 업데이트
						if (updateUserRequest.getMbrAuth().getIsRealName().equalsIgnoreCase(Constant.TYPE_YN_Y)) {
							usermbrIsRealNameAuthYN.setUserName(updateUserRequest.getMbrAuth().getName());
							usermbrIsRealNameAuthYN.setUserBirthDay(updateUserRequest.getMbrAuth().getBirthDay());
							usermbrIsRealNameAuthYN.setUserSex(updateUserRequest.getMbrAuth().getSex());

							// 성별 파라미터 M/F 인지 체크
							if (StringUtils.isNotBlank(updateUserRequest.getMbrAuth().getSex())) {
								if (this.checkValidateUserSexValue(updateUserRequest.getMbrAuth().getSex())) {
									usermbrIsRealNameAuthYN.setUserSex(updateUserRequest.getMbrAuth().getSex());
								}
							}
						}

						row = dao.update("User.updateRealNameAuthYN", usermbrIsRealNameAuthYN);
						LOGGER.debug("### User.updateRealNameAuthYN row : {}", row);
						if (row == 0) {
							throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError",
									""));
						}
					}

				}

				// TLog
				final String tlogUserKey = updateUserRequest.getUserMbr().getUserKey();
				final String tlogSystemID = updateUserRequest.getCommonRequest().getSystemID();
				final String tlogMNO = updateUserRequest.getMbrAuth().getTelecom();

				new TLogUtil().set(new ShuttleSetter() {
					@Override
					public void customize(TLogSentinelShuttle shuttle) {
						shuttle.insd_usermbr_no(tlogUserKey).system_id(tlogSystemID).mno_type(tlogMNO);
					}
				});

			}

			// check 부가속성
			if (updateUserRequest.getMbrMangItemPtcrList() != null) {
				List<MbrMangItemPtcr> mbrMangItemPtcrList = updateUserRequest.getMbrMangItemPtcrList();
				for (MbrMangItemPtcr mbrMangItemPtcr : mbrMangItemPtcrList) {
					mbrMangItemPtcr.setUserKey(updateUserRequest.getUserMbr().getUserKey());
					row = dao.update("User.updateManagement", mbrMangItemPtcr);
					LOGGER.debug("###  3 row : {}", row);
					if (row == 0) {
						throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
					}
				}
			}

			// check 이용약관
			if (updateUserRequest.getMbrClauseAgree() != null) {
				List<MbrClauseAgree> mbrClauseAgreeList = updateUserRequest.getMbrClauseAgree();
				for (int i = 0; i < mbrClauseAgreeList.size(); i++) {
					MbrClauseAgree mbrClauseAgree = mbrClauseAgreeList.get(i);
					mbrClauseAgree.setMemberKey(updateUserRequest.getUserMbr().getUserKey());
					row = dao.update("User.updateAgreement", mbrClauseAgree);
					LOGGER.debug("###  4 row : {}", row);
					if (row == 0) {
						throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
					}
				}
			}

		} else {
			// FAIL 처리
			throw new StorePlatformException(this.getMessage("response.ResultCode.userKeyNotFound", ""));
		}

		// ACTION 2
		updateUserResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));
		updateUserResponse.setUserKey(updateUserRequest.getUserMbr().getUserKey());
		return updateUserResponse;
	}

	/**
	 * <pre>
	 * 회원 상태정보 변경 서비스.
	 * </pre>
	 * 
	 * @param updateStatusUserRequest
	 *            회원 상태정보 변경 요청 Value Object
	 * 
	 * @return UpdateStatusUserResponse - 회원 상태정보 변경 응답 Value Object
	 */
	@Override
	public UpdateStatusUserResponse updateStatus(UpdateStatusUserRequest updateStatusUserRequest) {

		// TO DO.
		// FAIL 처리 : ID 있나없나 확인.
		// ACTION 1. 회원 이력 테이블 insert.
		// ACTION 2. 회원 속성의 Main, Sub 상태정보를 update.

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("사용자 서비스 - 회원 상태정보 변경 요청");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		UpdateStatusUserResponse updateStatusUserResponse = new UpdateStatusUserResponse();

		String userKey;
		UserMbr usermbr;
		Integer row;

		// FAIL 처리 : ID 있나없나 확인.
		userKey = null;
		String isDormant = StringUtils.isBlank(updateStatusUserRequest.getIsDormant()) ? Constant.TYPE_YN_N : updateStatusUserRequest
				.getIsDormant();
		CommonDAO dao = null;
		if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
			dao = this.commonDAO;
		} else {
			dao = this.idleDAO;
		}

		boolean isDeviceRequest = false;
		List<KeySearch> keySearchList = updateStatusUserRequest.getKeySearchList();
		for (KeySearch keySearch : keySearchList) {
			if (keySearch.getKeyType().equals(Constant.SEARCH_TYPE_DEVICE_KEY)
					|| keySearch.getKeyType().equals(Constant.SEARCH_TYPE_DEVICE_ID)
					|| keySearch.getKeyType().equals(Constant.SEARCH_TYPE_MDN)) {
				isDeviceRequest = true;
			}
		}

		// 휴대기기 정보로 조회
		if (isDeviceRequest) {
			userKey = dao.queryForObject("User.getUserKeyInUpdateStatusD", updateStatusUserRequest, String.class);
		} else {
			userKey = dao.queryForObject("User.getUserKeyInUpdateStatus", updateStatusUserRequest, String.class);
		}

		if (userKey == null || userKey.length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.userKeyNotFound", ""));
		}

		usermbr = new UserMbr();
		usermbr.setTenantID(updateStatusUserRequest.getCommonRequest().getTenantID());
		usermbr.setUserKey(userKey);
		usermbr.setUserMainStatus(updateStatusUserRequest.getUserMainStatus());
		usermbr.setUserSubStatus(updateStatusUserRequest.getUserSubStatus());
		usermbr.setLoginStatusCode(updateStatusUserRequest.getLoginStatusCode());
		usermbr.setStopStatusCode(updateStatusUserRequest.getStopStatusCode());
		if (StringUtils.equals(MainStateCode.SECEDE.getCode(), updateStatusUserRequest.getUserMainStatus())) {
			usermbr.setSecedeDate(Utils.getLocalDateTimeinYYYYMMDD());
		}

		// ACTION 1. 회원 이력 테이블 insert.
		if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
			row = dao.update("User.insertUpdateStatusHistory", usermbr);
			LOGGER.debug("### updateStatus row : {}", row);
			if (row <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}
		}

		// ACTION 2. 회원 속성의 Main, Sub 상태정보를 update.
		row = dao.update("User.updateStatus", usermbr);
		LOGGER.debug("### updateStatus row : {}", row);
		if (row <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
		}

		updateStatusUserResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));
		return updateStatusUserResponse;
	}

	/**
	 * <pre>
	 * 에러 메시지.
	 * </pre>
	 * 
	 * @param resultCode
	 *            에러코드
	 * @param resultMessage
	 *            에러메세지
	 * @return CommonResponse 결과 메세지
	 */
	private CommonResponse getErrorResponse(String resultCode, String resultMessage) {
		CommonResponse commonResponse;
		commonResponse = new CommonResponse();
		commonResponse.setResultCode(this.getMessage(resultCode, ""));
		commonResponse.setResultMessage(this.getMessage(resultMessage, ""));
		return commonResponse;
	}

	/**
	 * <pre>
	 *  메시지 프로퍼티에서 메시지 참조.
	 * </pre>
	 * 
	 * @param code
	 *            fail 코드
	 * @param fail
	 *            에러메세지
	 * @return String 결과 메세지
	 */
	private String getMessage(String code, String fail) {
		String msg = this.messageSourceAccessor.getMessage(code, null, fail, LocaleContextHolder.getLocale());
		LOGGER.debug(msg);
		return msg;
	}

	/**
	 * <pre>
	 * 사용자 회원 비밀번호 변경하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updatePasswordUserRequest
	 *            사용자회원 비밀번호 변경 요청 Value Object
	 * @return UpdatePasswordUserResponse - 사용자회원 비밀번호 변경 응답 Value Object
	 */
	@Override
	public UpdatePasswordUserResponse updatePasswordUser(UpdatePasswordUserRequest updatePasswordUserRequest) {

		String isDormant = StringUtils.isBlank(updatePasswordUserRequest.getIsDormant()) ? Constant.TYPE_YN_N : updatePasswordUserRequest
				.getIsDormant();
		CommonDAO dao = null;
		if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
			dao = this.commonDAO;
		} else {
			dao = this.idleDAO;
		}
		// userID가 존재하는지 여부 확인
		String isRegistered = null;
		UserMbr usermbr = new UserMbr();
		usermbr.setTenantID(updatePasswordUserRequest.getCommonRequest().getTenantID());
		usermbr.setUserID(updatePasswordUserRequest.getMbrPwd().getMemberID());
		isRegistered = dao.queryForObject("User.isRegisteredUserID", usermbr, String.class);
		if (isRegistered == null || isRegistered.length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.userKeyNotFound", ""));
		}

		updatePasswordUserRequest.getMbrPwd().setTenantID(updatePasswordUserRequest.getCommonRequest().getTenantID());

		LOGGER.debug("### tenantID : {}", updatePasswordUserRequest.getMbrPwd().getTenantID());
		LOGGER.debug("### memberID : {}", updatePasswordUserRequest.getMbrPwd().getMemberID());

		Integer row = dao.update("User.updatePasswordUser", updatePasswordUserRequest.getMbrPwd());
		LOGGER.debug("### updateStatus row : {}", row);
		if (row == 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
		}

		UpdatePasswordUserResponse updatePasswordUserResponse = new UpdatePasswordUserResponse();
		updatePasswordUserResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));
		updatePasswordUserResponse.setUserKey(isRegistered);
		return updatePasswordUserResponse;

	}

	/**
	 * <pre>
	 * 사용자 회원 비밀번호 초기화하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param resetPasswordUserRequest
	 *            사용자회원 비밀번호 변경 요청 Value Object
	 * @return ResetPasswordUserResponse - 사용자회원 비밀번호 변경 응답 Value Object
	 */
	@Override
	public ResetPasswordUserResponse updateResetPasswordUser(ResetPasswordUserRequest resetPasswordUserRequest) {

		// userID가 존재하는지 여부 확인
		String isRegistered = null;
		UserMbr usermbr = new UserMbr();
		usermbr.setTenantID(resetPasswordUserRequest.getCommonRequest().getTenantID());
		usermbr.setUserID(resetPasswordUserRequest.getMbrPwd().getMemberID());
		isRegistered = this.commonDAO.queryForObject("User.isRegisteredUserID", usermbr, String.class);
		if (isRegistered == null || isRegistered.length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.userKeyNotFound", ""));
		}

		MbrPwd mbrPwd = resetPasswordUserRequest.getMbrPwd();
		mbrPwd.setTenantID(resetPasswordUserRequest.getCommonRequest().getTenantID());

		Integer row = this.commonDAO.update("User.resetPasswordUser", mbrPwd);
		LOGGER.debug("### updateStatus row : {}", row);
		if (row == 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
		}

		ResetPasswordUserResponse resetPasswordUserResponse = new ResetPasswordUserResponse();
		resetPasswordUserResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));
		return resetPasswordUserResponse;

	}

	/**
	 * <pre>
	 * 사용자 부가정보 목록 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchManagementListRequest
	 *            사용자 부가정보 목록 조회 요청 Value Object
	 * @return SearchManagementListResponse - 사용자 부가정보 목록 조회 응답 Value Object
	 */
	@Override
	public SearchManagementListResponse searchManagementList(SearchManagementListRequest searchManagementListRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("서비스 - 사용자 부가정보 목록 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### searchManagementListRequest : {}", searchManagementListRequest);

		UserMbr usermbr = new UserMbr();
		usermbr.setTenantID(searchManagementListRequest.getCommonRequest().getTenantID());
		usermbr.setSystemID(searchManagementListRequest.getCommonRequest().getSystemID());
		usermbr.setUserKey(searchManagementListRequest.getUserKey());

		String isRegistered = null;
		String isDormant = Constant.TYPE_YN_N;
		isRegistered = this.commonDAO.queryForObject("User.isRegisteredKey", usermbr, String.class);

		if (isRegistered == null) {
			isRegistered = this.idleDAO.queryForObject("User.isRegisteredKey", usermbr, String.class);
			isDormant = Constant.TYPE_YN_Y;
		}
		LOGGER.debug("### isRegisteredKey : {}", isRegistered);

		// ACTION 1. 존재하지 않는 회원키는 에러코드 반환
		if (isRegistered == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.userKeyNotFound", ""));
		}

		SearchManagementListResponse searchManagementListResponse;

		if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
			searchManagementListResponse = this.commonDAO.queryForObject("User.searchManagementList",
					searchManagementListRequest, SearchManagementListResponse.class);
		} else {
			searchManagementListResponse = this.idleDAO.queryForObject("User.searchManagementList",
					searchManagementListRequest, SearchManagementListResponse.class);
		}

		if (searchManagementListResponse == null || searchManagementListResponse.getMbrMangItemPtcrList() == null
				|| searchManagementListResponse.getMbrMangItemPtcrList().size() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		}

		searchManagementListResponse.setUserKey(searchManagementListRequest.getUserKey());
		searchManagementListResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));

		return searchManagementListResponse;

	}

	/**
	 * <pre>
	 * 사용자 약관동의 목록 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchAgreementListRequest
	 *            사용자 약관동의 목록 요청 Value Object
	 * @return SearchAgreementListResponse - 사용자 약관동의 목록 응답 Value Object
	 */
	@Override
	public SearchAgreementListResponse searchAgreementList(SearchAgreementListRequest searchAgreementListRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("서비스 - 사용자 약관동의 목록 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### searchAgreementListRequest : {}", searchAgreementListRequest);

		String isDormant = StringUtils.isBlank(searchAgreementListRequest.getIsDormant()) ? Constant.TYPE_YN_N : searchAgreementListRequest
				.getIsDormant();
		CommonDAO dao = null;
		if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
			dao = this.commonDAO;
		} else {
			dao = this.idleDAO;
		}
		UserMbr usermbr = new UserMbr();
		usermbr.setTenantID(searchAgreementListRequest.getCommonRequest().getTenantID());
		usermbr.setSystemID(searchAgreementListRequest.getCommonRequest().getSystemID());
		usermbr.setUserKey(searchAgreementListRequest.getUserKey());

		String isRegistered = null;
		isRegistered = dao.queryForObject("User.isRegisteredKey", usermbr, String.class);
		LOGGER.debug("### isRegisteredKey : {}", isRegistered);

		// ACTION 1. 존재하지 않는 회원키는 에러코드 반환
		if (isRegistered == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.userKeyNotFound", ""));
		}

		SearchAgreementListResponse searchAgreementListResponse;
		searchAgreementListResponse = dao.queryForObject("User.searchAgreementList", searchAgreementListRequest,
				SearchAgreementListResponse.class);

		if (searchAgreementListResponse == null || searchAgreementListResponse.getMbrClauseAgreeList() == null
				|| searchAgreementListResponse.getMbrClauseAgreeList().size() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		}

		searchAgreementListResponse.setUserKey(searchAgreementListRequest.getUserKey());
		searchAgreementListResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));
		return searchAgreementListResponse;

	}

	/**
	 * <pre>
	 * 사용자 부가정보 등록/수정하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updateManagementRequest
	 *            사용자 부가정보 및 관리항목 정보수정 요청 Value Object
	 * @return UpdateManagementResponse - 사용자 부가정보 및 관리항목 정보수정 응답 Value Object
	 */
	@Override
	public UpdateManagementResponse updateManagement(UpdateManagementRequest updateManagementRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("서비스 - 사용자 부가정보 등록/수정");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### updateManagementRequest : {}", updateManagementRequest);

		UpdateManagementResponse updateManagementResponse = null;

		List<MbrMangItemPtcr> mbrMangItemPtcrList = updateManagementRequest.getMbrMangItemPtcr();
		for (MbrMangItemPtcr mbrMangItemPtcr : mbrMangItemPtcrList) {
			mbrMangItemPtcr.setTenantID(updateManagementRequest.getCommonRequest().getTenantID());
			mbrMangItemPtcr.setUserKey(updateManagementRequest.getUserKey());
			Integer row = this.commonDAO.update("User.updateManagement", mbrMangItemPtcr);
			LOGGER.debug("### row : {}", row);
			if (row <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}
		}

		updateManagementResponse = new UpdateManagementResponse();
		updateManagementResponse.setUserKey(updateManagementRequest.getUserKey());
		updateManagementResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));
		return updateManagementResponse;

	}

	/**
	 * <pre>
	 * 사용자 부가정보 삭제하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param removeManagementRequest
	 *            사용자 관리항목 및 부가정보 삭제 요청 Value Object
	 * @return RemoveManagementResponse - 사용자 관리항목 및 부가정보 삭제 응답 Value Object
	 */
	@Override
	public RemoveManagementResponse removeManagement(RemoveManagementRequest removeManagementRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("서비스 - 사용자 부가정보 삭제");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### removeManagementRequest : {}", removeManagementRequest);

		RemoveManagementResponse removeManagementResponse = null;

		List<MbrMangItemPtcr> mbrMangItemPtcrList = removeManagementRequest.getMbrMangItemPtcr();
		for (MbrMangItemPtcr mbrMangItemPtcr : mbrMangItemPtcrList) {
			mbrMangItemPtcr.setTenantID(removeManagementRequest.getCommonRequest().getTenantID());
			mbrMangItemPtcr.setUserKey(removeManagementRequest.getUserKey());
			Integer row = this.commonDAO.delete("User.removeManagement", mbrMangItemPtcr);
			LOGGER.debug("### row : {}", row);
			if (row <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.removeError", ""));
			}
		}

		removeManagementResponse = new RemoveManagementResponse();
		removeManagementResponse.setUserKey(removeManagementRequest.getUserKey());
		removeManagementResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));
		return removeManagementResponse;

	}

	/**
	 * <pre>
	 * 제한 정책 목록을 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchPolicyRequest
	 *            제한 정책 목록 조회 요청 Value Object
	 * @return searchPolicyResponse - 제한 정책 목록 조회 응답 Value Object
	 */
	@Override
	public SearchPolicyResponse searchPolicyList(SearchPolicyRequest searchPolicyRequest) {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("서비스 - 제한 정책 목록 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### searchPolicyRequest : {}", searchPolicyRequest);

		SearchPolicyResponse searchPolicyResponse = new SearchPolicyResponse();

		List<LimitTarget> limitTargetList = (List<LimitTarget>) this.commonDAO.queryForList("User.retrievePolicyList",
				searchPolicyRequest);

		if (limitTargetList == null || limitTargetList.size() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		}

		searchPolicyResponse.setLimitTargetList(limitTargetList);
		searchPolicyResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));
		return searchPolicyResponse;
	}

	/**
	 * <pre>
	 * 제한 정책정보를 등록/수정하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updatePolicyRequest
	 *            제한 정책정보 등록/수정 요청 Value Object
	 * @return updatePolicyResponse - 제한 정책정보 등록/수정 응답 Value Object
	 */
	@Override
	public UpdatePolicyResponse updatePolicy(UpdatePolicyRequest updatePolicyRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("서비스 - 제한 정책정보 등록");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### updatePolicyRequest : {}", updatePolicyRequest);

		UpdatePolicyResponse updatePolicyResponse = new UpdatePolicyResponse();
		Integer row = 0;

		List<LimitTarget> limitTargetList = updatePolicyRequest.getLimitTargetList();
		List<LimitTarget> limitCodeList;
		limitCodeList = new ArrayList<LimitTarget>();

		for (int i = 0; i < limitTargetList.size(); i++) {
			LimitTarget limitTarget = limitTargetList.get(i);
			limitTarget.setTenantID(updatePolicyRequest.getCommonRequest().getTenantID());

			/*
			 * if (limitTarget.getLimitTargetNo() == null) { int seq =
			 * this.commonDAO.queryForObject("User.getLimitSequence", null, Integer.class); String tempKey =
			 * Integer.toString(seq); limitTarget.setLimitTargetNo(tempKey); }
			 */

			LOGGER.debug(">>>> >>> UserServiceImpl before updatePolicy : {}", limitTarget);
			row = this.commonDAO.update("User.updatePolicy", limitTarget);
			LOGGER.debug("### row : {}", row);
			if (row <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}
			limitCodeList.add(limitTarget);
		}

		updatePolicyResponse.setLimitTargetList(limitCodeList);
		updatePolicyResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));
		return updatePolicyResponse;
	}

	/**
	 * <pre>
	 * 제한 정책정보 삭제하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param removePolicyRequest
	 *            제한 정책정보 삭제 요청 Value Object
	 * @return removePolicyResponse - 제한 정책정보 삭제 응답 Value Object
	 */
	@Override
	public RemovePolicyResponse removePolicy(RemovePolicyRequest removePolicyRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("서비스 - 제한 정책정보 삭제");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### removePolicy : {}", removePolicyRequest);

		RemovePolicyResponse removePolicyResponse = new RemovePolicyResponse();
		Integer row = 0;

		List<LimitTarget> limitTargetList = removePolicyRequest.getLimitTargetList();
		List<String> limitCodeList;
		limitCodeList = new ArrayList<String>();

		for (int i = 0; i < limitTargetList.size(); i++) {
			LimitTarget limitTargetNo = limitTargetList.get(i);
			limitTargetNo.setTenantID(removePolicyRequest.getCommonRequest().getTenantID());

			LOGGER.debug(">>>> >>> UserServiceImpl before removePolicy : {}", limitTargetNo);
			row = this.commonDAO.delete("User.removePolicy", limitTargetNo);
			LOGGER.debug("### row : {}", row);
			if (row <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.removeError", ""));
			}
			limitCodeList.add(limitTargetNo.getLimitPolicyCode());
		}

		removePolicyResponse.setLimitPolicyCodeList(limitCodeList);
		removePolicyResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));
		return removePolicyResponse;
	}

	/**
	 * <pre>
	 * OCB 목록을 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchMemberPointRequest
	 *            OCB 목록 조회 요청 Value Object
	 * @return searchOCBResponse - OCB 목록 조회 응답 Value Object
	 */
	@Override
	public SearchMemberPointResponse searchOCBList(SearchMemberPointRequest searchMemberPointRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("서비스 - OCB 목록 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### searchPolicyRequest : {}", searchMemberPointRequest);

		SearchMemberPointResponse searchMemberPointResponse = null;

		LOGGER.debug(">>>> >>> UserServiceImpl before searchOCBList : {}", searchMemberPointRequest);
		searchMemberPointResponse = this.commonDAO.queryForObject("User.searchOCBList", searchMemberPointRequest,
				SearchMemberPointResponse.class);

		if (searchMemberPointResponse == null || searchMemberPointResponse.getMemberPointList() == null
				|| searchMemberPointResponse.getMemberPointList().size() <= 0) {
			searchMemberPointResponse = this.idleDAO.queryForObject("User.searchOCBList", searchMemberPointRequest,
					SearchMemberPointResponse.class);
		}

		if (searchMemberPointResponse == null || searchMemberPointResponse.getMemberPointList() == null
				|| searchMemberPointResponse.getMemberPointList().size() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		}

		searchMemberPointResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));
		return searchMemberPointResponse;
	}

	/**
	 * <pre>
	 * OCB를 등록/수정하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updateMemberPointRequest
	 *            OCB 등록/수정 요청 Value Object
	 * @return updateOCBResponse - OCB 등록/수정 응답 Value Object
	 */
	@Override
	public UpdateMemberPointResponse updateOCB(UpdateMemberPointRequest updateMemberPointRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("서비스 - OCB 등록");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### updateOCBRequest : {}", updateMemberPointRequest);

		UpdateMemberPointResponse updateMemberPointResponse = new UpdateMemberPointResponse();
		Integer row = 0;

		MemberPoint memberPoint = updateMemberPointRequest.getMemberPoint();
		memberPoint.setTenantID(updateMemberPointRequest.getCommonRequest().getTenantID());

		row = this.commonDAO.update("User.removeOCBAll", memberPoint);
		LOGGER.debug("### row : {}", row);

		LOGGER.debug(">>>> >>> UserServiceImpl before updateOCB : {}", memberPoint);
		row = (Integer) this.commonDAO.insert("User.updateOCB", memberPoint);
		LOGGER.debug("### row : {}", row);
		if (row <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
		}

		updateMemberPointResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));
		updateMemberPointResponse.setUserKey(updateMemberPointRequest.getMemberPoint().getUserKey());

		return updateMemberPointResponse;
	}

	/**
	 * <pre>
	 * OCB 삭제하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param removeMemberPointRequest
	 *            OCB 삭제 요청 Value Object
	 * @return removeOCBResponse - OCB 삭제 응답 Value Object
	 */
	@Override
	public RemoveMemberPointResponse removeOCB(RemoveMemberPointRequest removeMemberPointRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("서비스 - OCB 삭제");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### removePolicy : {}", removeMemberPointRequest);

		RemoveMemberPointResponse removeMemberPointResponse = new RemoveMemberPointResponse();
		Integer row = 0;

		List<MemberPoint> memberPointList = removeMemberPointRequest.getMemberPointList();
		for (int i = 0; i < memberPointList.size(); i++) {
			MemberPoint memberPoint = memberPointList.get(i);
			memberPoint.setTenantID(removeMemberPointRequest.getCommonRequest().getTenantID());

			LOGGER.debug(">>>> >>> UserServiceImpl before removeOCB : {}", memberPoint);
			row = this.commonDAO.update("User.removeOCB", memberPoint);
			LOGGER.debug("### row : {}", row);
			if (row <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.removeError", ""));
			}
		}

		removeMemberPointResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));
		return removeMemberPointResponse;
	}

	/**
	 * <pre>
	 * 미동의 사이트 조회.
	 * </pre>
	 * 
	 * @param searchAgreeSiteRequest
	 *            미동의 사이트 조회 요청 Value Object
	 * @return SearchAgreeSiteResponse - 미동의 사이트 조회 응답 Value Object
	 */
	@Override
	public SearchAgreeSiteResponse searchAgreeSite(SearchAgreeSiteRequest searchAgreeSiteRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("서비스 - 미동의 사이트 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### searchAgreeSiteRequest : {}", searchAgreeSiteRequest);

		String isDormant = StringUtils.isBlank(searchAgreeSiteRequest.getIsDormant()) ? Constant.TYPE_YN_N : searchAgreeSiteRequest
				.getIsDormant();
		CommonDAO dao = null;
		if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
			dao = this.commonDAO;
		} else {
			dao = this.idleDAO;
		}
		SearchAgreeSiteResponse searchAgreeSiteResponse;

		MbrOneID mbrOneID = new MbrOneID();
		mbrOneID.setIntgSvcNumber(searchAgreeSiteRequest.getImSvcNo());
		mbrOneID.setTenantID(searchAgreeSiteRequest.getCommonRequest().getTenantID());
		mbrOneID = dao.queryForObject("User.getOneIDDetail", mbrOneID, MbrOneID.class);

		if (mbrOneID == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		}

		searchAgreeSiteResponse = new SearchAgreeSiteResponse();
		searchAgreeSiteResponse.setMbrOneID(mbrOneID);
		searchAgreeSiteResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));
		return searchAgreeSiteResponse;
	}

	/**
	 * <pre>
	 * 사용자키 변환추적 조회.
	 * </pre>
	 * 
	 * @param searchUserkeyTrackRequest
	 *            사용자키 변환추적 요청 Value Object
	 * @return SearchUserkeyTrackResponse - 사용자키 변환추적 응답 Value Object
	 */
	@Override
	public SearchUserkeyTrackResponse searchUserkeyTrack(SearchUserkeyTrackRequest searchUserkeyTrackRequest) {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("사용자키 변환추적 요청");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### searchUserkeyTrack : {}", searchUserkeyTrackRequest);

		SearchUserkeyTrackResponse searchUserkeyTrackResponse;

		UserkeyTrack userkeyTrack = null;
		userkeyTrack = this.commonDAO.queryForObject("User.getUserkeyTrack", searchUserkeyTrackRequest.getDeviceID(),
				UserkeyTrack.class);

		if (userkeyTrack == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		}

		searchUserkeyTrackResponse = new SearchUserkeyTrackResponse();
		searchUserkeyTrackResponse.setUserkeyTrack(userkeyTrack);
		searchUserkeyTrackResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));
		return searchUserkeyTrackResponse;
	}

	/**
	 * <pre>
	 * 사용자회원_ONEID 삭제.
	 * </pre>
	 * 
	 * @param removeMbrOneIDRequest
	 *            사용자회원_ONEID 삭제 요청 Value Object
	 * @return RemoveMbrOneIDResponse - 사용자회원_ONEID 삭제 응답 Value Object
	 */
	@Override
	public RemoveMbrOneIDResponse removeMbrOneID(RemoveMbrOneIDRequest removeMbrOneIDRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("서비스 - 사용자회원_ONEID 삭제");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### removeMbrOneIDRequest : {}", removeMbrOneIDRequest);
		String isDormant = StringUtils.isBlank(removeMbrOneIDRequest.getIsDormant()) ? Constant.TYPE_YN_N : removeMbrOneIDRequest
				.getIsDormant();
		RemoveMbrOneIDResponse removeMbrOneIDResponse = new RemoveMbrOneIDResponse();
		Integer row = 0;

		if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
			row = this.commonDAO.delete("User.removeMbrOneID", removeMbrOneIDRequest);
		} else {
			row = this.idleDAO.delete("User.removeMbrOneID", removeMbrOneIDRequest);
		}

		LOGGER.debug("### row : {}", row);
		if (row <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.removeError", ""));
		}

		removeMbrOneIDResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));
		return removeMbrOneIDResponse;
	}

	/**
	 * <pre>
	 * 유통망 추천앱 회원 정보 등록하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updateUserMbrSegmentRequest
	 *            유통망 추천앱 회원 정보 등록 요청 Value Object
	 * @return UpdateUserMbrSegmentResponse - 유통망 추천앱 회원 정보 등록 응답 Value Object
	 */
	@Override
	public UpdateUserMbrSegmentResponse updateUserMbrSegment(UpdateUserMbrSegmentRequest updateUserMbrSegmentRequest) {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("서비스 - 유통망 추천앱 회원 정보 등록");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### updateGameCenterRequest : {}", updateUserMbrSegmentRequest);

		UpdateUserMbrSegmentResponse updateUserMbrSegmentResponse = new UpdateUserMbrSegmentResponse();
		Integer row = 0;

		UserMbrSegment userMbrSegment = updateUserMbrSegmentRequest.getUserMbrSegment();

		LOGGER.debug(">>>> >>> userMbrSegment : {}", userMbrSegment);
		row = (Integer) this.commonDAO.insert("User.createUserMbrSegment", userMbrSegment);
		LOGGER.debug("### row : {}", row);
		if (row <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
		}

		updateUserMbrSegmentResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));
		return updateUserMbrSegmentResponse;
	}

	/**
	 * <pre>
	 * 유통망 추천앱 비회원 정보 등록하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updateNonMbrSegmentRequest
	 *            유통망 추천앱 비회원 정보 등록 요청 Value Object
	 * @return UpdateNonMbrSegmentResponse - 유통망 추천앱 비회원 정보 등록 응답 Value Object
	 */
	@Override
	public UpdateNonMbrSegmentResponse updateNonMbrSegment(UpdateNonMbrSegmentRequest updateNonMbrSegmentRequest) {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("서비스 - 유통망 추천앱 회원 정보 등록");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### updateNonMbrSegmentRequest : {}", updateNonMbrSegmentRequest);

		UpdateNonMbrSegmentResponse updateNonMbrSegmentResponse = new UpdateNonMbrSegmentResponse();
		Integer row = 0;

		NonMbrSegment nonMbrSegment = updateNonMbrSegmentRequest.getNonMbrSegment();
		nonMbrSegment.setIsProcessed("N");
		nonMbrSegment.setCheckCount(0);

		LOGGER.debug(">>>> >>> nonMbrSegment : {}", nonMbrSegment);
		row = (Integer) this.commonDAO.insert("User.createNonMbrSegment", nonMbrSegment);
		LOGGER.debug("### row : {}", row);
		if (row <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
		}

		updateNonMbrSegmentResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));
		return updateNonMbrSegmentResponse;
	}

	/**
	 * <pre>
	 * 휴대기기 기기변경이력 정보 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchChangedDeviceRequest
	 *            휴대기기 기기변경이력 정보 조회 요청 Value Object
	 * @return SearchChangedDeviceResponse - 휴대기기 기기변경이력 정보 조회 응답 Value Object
	 */
	@Override
	public SearchChangedDeviceResponse searchChangedDevice(SearchChangedDeviceRequest searchChangedDeviceRequest) {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("서비스 - 휴대기기 기기변경이력 정보 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### searchChangedDeviceRequest : {}", searchChangedDeviceRequest);

		SearchChangedDeviceResponse searchChangedDeviceResponse = new SearchChangedDeviceResponse();
		ChangedDeviceLog changedDeviceLog = null;
		changedDeviceLog = this.commonDAO.queryForObject("User.searchIDPChangedDevice", searchChangedDeviceRequest,
				ChangedDeviceLog.class); // 20040601
										 // 수정
		if (changedDeviceLog == null) {
			changedDeviceLog = this.idleDAO.queryForObject("User.searchIDPChangedDevice", searchChangedDeviceRequest,
					ChangedDeviceLog.class);
		}
		/*
		 * if (Utils.getLocalDateTimeinYYYYMMDD().compareTo("20140531") > 0) { changedDeviceLog =
		 * this.commonDAO.queryForObject("User.searchChangedDevice", searchChangedDeviceRequest,
		 * ChangedDeviceLog.class); } else { changedDeviceLog =
		 * this.commonDAO.queryForObject("User.searchIDPChangedDevice", searchChangedDeviceRequest,
		 * ChangedDeviceLog.class); }
		 */

		if (changedDeviceLog == null) {
			changedDeviceLog = new ChangedDeviceLog();
			changedDeviceLog.setIsChanged("N");
			// query id -> key
			String isRegistered = null;
			isRegistered = this.commonDAO.queryForObject("User.searchIDPChangedDevice2", searchChangedDeviceRequest,
					String.class);
			if (isRegistered == null) {
				isRegistered = this.idleDAO.queryForObject("User.searchIDPChangedDevice2", searchChangedDeviceRequest,
						String.class);
			}
			LOGGER.debug("### searchIDPChangedDevice2 : {}", isRegistered);
			// if (isRegistered != null)
			changedDeviceLog.setDeviceKey(isRegistered);
			searchChangedDeviceResponse.setChangedDeviceLog(changedDeviceLog);
			searchChangedDeviceResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
					"response.ResultMessage.success"));

			return searchChangedDeviceResponse;
		}

		changedDeviceLog.setIsChanged("Y");
		searchChangedDeviceResponse.setChangedDeviceLog(changedDeviceLog);
		searchChangedDeviceResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));

		return searchChangedDeviceResponse;
	}

	/**
	 * <pre>
	 * 휴대기기 기기변경이력 정보 등록하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param createChangedDeviceRequest
	 *            휴대기기 기기변경이력 정보 등록 요청 Value Object
	 * @return CreateChangedDeviceResponse - 휴대기기 기기변경이력 정보 등록 응답 Value Object
	 */
	@Override
	public CreateChangedDeviceResponse createChangedDevice(CreateChangedDeviceRequest createChangedDeviceRequest) {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("서비스 - 휴대기기 기기변경이력 정보 등록");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### createChangedDeviceRequest : {}", createChangedDeviceRequest);

		CreateChangedDeviceResponse createChangedDeviceResponse = new CreateChangedDeviceResponse();
		Integer row = 0;

		ChangedDeviceLog changedDeviceLog = createChangedDeviceRequest.getChangedDeviceLog();
		changedDeviceLog.setTenantID(createChangedDeviceRequest.getCommonRequest().getTenantID());

		LOGGER.debug(">>>> >>> UserServiceImpl before createChangedDeviceRequest : {}", changedDeviceLog);
		row = (Integer) this.commonDAO.insert("User.createChangedDevice", changedDeviceLog);
		LOGGER.debug("### row : {}", row);
		if (row <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
		}

		createChangedDeviceResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));

		return createChangedDeviceResponse;
	}

	/**
	 * <pre>
	 * 이메일 사용자 정보 조회.
	 * </pre>
	 * 
	 * @param searchUserEmailRequest
	 *            이메일 사용자 정보 조회 요청 Value Object
	 * @return SearchUserEmailResponse - 이메일 사용자 정보 조회 응답 Value Object
	 */
	@Override
	public SearchUserEmailResponse searchUserEmail(SearchUserEmailRequest searchUserEmailRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("서비스 - 이메일 사용자 정보 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### searchUserEmailRequest : {}", searchUserEmailRequest);

		List<UserMbr> userMbrList = (ArrayList) this.commonDAO.queryForList("User.searchUserEmail",
				searchUserEmailRequest);

		// 휴면DB 조회
		List<UserMbr> userMbrSpidleList = (ArrayList) this.idleDAO.queryForList("User.searchUserEmail",
				searchUserEmailRequest);

		if (userMbrSpidleList != null && userMbrSpidleList.size() > 0) {
			if (userMbrList == null)
				userMbrList = new ArrayList<UserMbr>();

			userMbrList.addAll(userMbrSpidleList);
		}

		if (userMbrList == null || userMbrList.size() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		}

		SearchUserEmailResponse searchUserEmailResponse = new SearchUserEmailResponse();
		searchUserEmailResponse.setUserMbr(userMbrList);
		searchUserEmailResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));
		return searchUserEmailResponse;
	}

	/**
	 * <pre>
	 * 단말 OS별 누적 가입자수 조회.
	 * </pre>
	 * 
	 * @param searchDeviceOSNumberRequest
	 *            단말 OS별 누적 가입자수 조회 요청 Value Object
	 * @return SearchDeviceOSNumberResponse - 단말 OS별 누적 가입자수 조회 응답 Value Object
	 */
	@Override
	public SearchDeviceOSNumberResponse searchDeviceOSNumber(SearchDeviceOSNumberRequest searchDeviceOSNumberRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("서비스 - 단말 OS별 누적 가입자수 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### searchDeviceOSNumberRequest : {}", searchDeviceOSNumberRequest);

		List<DeviceSystemStats> deviceSystemStatsList = (ArrayList) this.commonDAO.queryForList(
				"User.searchDeviceOSNumber", searchDeviceOSNumberRequest.getCommonRequest().getTenantID());

		if (deviceSystemStatsList == null || deviceSystemStatsList.size() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		}

		HashMap<String, List<DeviceSystemStats>> deviceSystemStatsMap = new HashMap();

		for (DeviceSystemStats deviceSystemStats : deviceSystemStatsList) {
			String modelName = deviceSystemStats.getModelName();

			List<DeviceSystemStats> listInMap = deviceSystemStatsMap.get(modelName);
			if (listInMap == null) {
				listInMap = new ArrayList<DeviceSystemStats>();
				deviceSystemStatsMap.put(modelName, listInMap);
			}

			listInMap.add(deviceSystemStats);
		}

		SearchDeviceOSNumberResponse searchDeviceOSNumberResponse = new SearchDeviceOSNumberResponse();
		searchDeviceOSNumberResponse.setDeviceSystemStatsMap(deviceSystemStatsMap);
		searchDeviceOSNumberResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));
		return searchDeviceOSNumberResponse;

	}

	/**
	 * <pre>
	 * 복수 사용자 상태정보 조회.
	 * </pre>
	 * 
	 * @param searchMbrUserRequest
	 *            복수 사용자 상태정보 조회 요청 Value Object
	 * @return SearchMbrUserResponse - 복수 사용자 상태정보 조회 응답 Value Object
	 */
	@Override
	public SearchMbrUserResponse searchMbrUser(SearchMbrUserRequest searchMbrUserRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("서비스 - 복수 사용자 상태정보 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### searchMbrUserRequest : {}", searchMbrUserRequest);

		List<UserMbrStatus> userMbrStatusList = (ArrayList) this.commonDAO.queryForList("User.searchMbrUser",
				searchMbrUserRequest);

		List<UserMbrStatus> userMbrStatusSpidlList = (ArrayList) this.idleDAO.queryForList("User.searchMbrUser",
				searchMbrUserRequest);

		if (userMbrStatusSpidlList != null && userMbrStatusSpidlList.size() > 0) {
			if (userMbrStatusList == null)
				userMbrStatusList = new ArrayList<UserMbrStatus>();

			userMbrStatusList.addAll(userMbrStatusSpidlList);
		}

		if (userMbrStatusList == null || userMbrStatusList.size() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		}

		HashMap userMbrStatusMap = new HashMap();

		for (UserMbrStatus userMbrStatus : userMbrStatusList) {
			String userKey = userMbrStatus.getUserKey();

			if (userMbrStatusMap.get(userKey) != null)
				continue;

			if (userMbrStatus.getDeviceIDList() == null) {
				userMbrStatus.setDeviceIDList(new ArrayList<String>());
			}

			userMbrStatusMap.put(userKey, userMbrStatus);
		}

		SearchMbrUserResponse searchMbrUserResponse = new SearchMbrUserResponse();
		searchMbrUserResponse.setUserMbrStatusMap(userMbrStatusMap);
		searchMbrUserResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));
		return searchMbrUserResponse;

	}

	@Override
	public SearchMbrSapUserResponse searchMbrSapUser(SearchMbrSapUserRequest searchMbrSapUserRequest) {

		List<UserMbrStatus> userMbrStatusList = (ArrayList) this.commonDAO.queryForList("User.searchMbrSapUser",
				searchMbrSapUserRequest);

		List<UserMbrStatus> userMbrStatusSpidlList = (ArrayList) this.idleDAO.queryForList("User.searchMbrSapUser",
				searchMbrSapUserRequest);

		if (userMbrStatusSpidlList != null && userMbrStatusSpidlList.size() > 0) {
			if (userMbrStatusList == null)
				userMbrStatusList = new ArrayList<UserMbrStatus>();

			userMbrStatusList.addAll(userMbrStatusSpidlList);
		}

		if (userMbrStatusList == null || userMbrStatusList.size() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		}

		HashMap userMbrStatusMap = new HashMap();

		for (UserMbrStatus userMbrStatus : userMbrStatusList) {
			String userKey = userMbrStatus.getUserKey();

			if (userMbrStatusMap.get(userKey) != null)
				continue;

			if (userMbrStatus.getDeviceIDList() == null) {
				userMbrStatus.setDeviceIDList(new ArrayList<String>());
			}

			userMbrStatusMap.put(userKey, userMbrStatus);
		}

		SearchMbrSapUserResponse searchMbrSapUserResponse = new SearchMbrSapUserResponse();
		searchMbrSapUserResponse.setUserMbrStatusMap(userMbrStatusMap);
		searchMbrSapUserResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));
		return searchMbrSapUserResponse;

	}

	/**
	 * <pre>
	 * 사용자 실명인증 정보를 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchRealNameRequest
	 *            실명인증 조회 요청 Value Object
	 * @return SearchRealNameResponse - 실명인증 조회 응답 Value Object
	 */
	@Override
	public SearchRealNameResponse searchRealName(SearchRealNameRequest searchRealNameRequest) {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("실명인증 조회 요청");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### searchUserkeyTrack : {}", searchRealNameRequest);
		String isDormant = StringUtils.isBlank(searchRealNameRequest.getIsDormant()) ? Constant.TYPE_YN_N : searchRealNameRequest
				.getIsDormant();
		SearchRealNameResponse searchRealNameResponse;

		MbrAuth mbrAuth = null;
		if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
			mbrAuth = this.commonDAO.queryForObject("User.getRealNameAll", searchRealNameRequest, MbrAuth.class);
		} else {
			mbrAuth = this.idleDAO.queryForObject("User.getRealNameAll", searchRealNameRequest, MbrAuth.class);
		}

		searchRealNameResponse = new SearchRealNameResponse();

		if (mbrAuth != null) {
			mbrAuth.setIsRealName("Y");
			searchRealNameResponse.setUserKey(mbrAuth.getMemberKey());
		}

		searchRealNameResponse.setMbrAuth(mbrAuth);
		searchRealNameResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));
		return searchRealNameResponse;
	}

	/**
	 * <pre>
	 * 제한 정책key 정보를 수정하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updatePolicyKeyRequest
	 *            제한 정책정보 key 수정 요청 Value Object
	 * @return updatePolicyKeyResponse - 제한 정책정보 key 수정 응답 Value Object
	 */
	@Override
	public UpdatePolicyKeyResponse updatePolicyKey(UpdatePolicyKeyRequest updatePolicyKeyRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("서비스 - 제한 정책 key 수정");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### updatePolicyKeyRequest : {}", updatePolicyKeyRequest);

		UpdatePolicyKeyResponse updatePolicyKeyResponse = new UpdatePolicyKeyResponse();
		Integer row = this.commonDAO.update("User.updatePolicyKey", updatePolicyKeyRequest);

		LOGGER.debug("### row : {}", row);

		updatePolicyKeyResponse.setUpdateCount(row);

		updatePolicyKeyResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));
		return updatePolicyKeyResponse;
	}

	/**
	 * <pre>
	 * 제한 정책 value 정보를 수정하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updatePolicyValueRequest
	 *            제한 정책정보 Value 수정 요청 Value Object
	 * @return updatePolicyValueResponse - 제한 정책정보 Value 수정 응답 Value Object
	 */
	@Override
	public UpdatePolicyValueResponse updatePolicyValue(UpdatePolicyValueRequest updatePolicyValueRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("서비스 - 제한 정책 Value 수정");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### updatePolicyValueRequest : {}", updatePolicyValueRequest);
		UpdatePolicyValueResponse updatePolicyValueResponse = new UpdatePolicyValueResponse();
		Integer row = this.commonDAO.update("User.updatePolicyValue", updatePolicyValueRequest);
		;
		LOGGER.debug("### row : {}", row);

		updatePolicyValueResponse.setUpdateCount(row);

		updatePolicyValueResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));
		return updatePolicyValueResponse;
	}

	/**
	 * <pre>
	 * 복수 사용자 기기정보 조회.
	 * </pre>
	 * 
	 * @param searchMbrDeviceRequest
	 *            복수 사용자 기기정보 조회 요청 Value Object
	 * @return SearchMbrDeviceResponse - 복수 사용자 기기정보 조회 응답 Value Object
	 */
	@Override
	public SearchMbrDeviceResponse searchMbrDevice(SearchMbrDeviceRequest searchMbrDeviceRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("서비스 - 복수 사용자 기기정보 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### searchMbrDeviceRequest : {}", searchMbrDeviceRequest);

		List<DeviceMbrStatus> deviceMbrStatusList = (ArrayList) this.commonDAO.queryForList("User.searchMbrDevice",
				searchMbrDeviceRequest);

		List<DeviceMbrStatus> deviceMbrStatusSpidlList = (ArrayList) this.idleDAO.queryForList("User.searchMbrDevice",
				searchMbrDeviceRequest);

		if (deviceMbrStatusSpidlList != null && deviceMbrStatusSpidlList.size() > 0) {
			if (deviceMbrStatusList == null)
				deviceMbrStatusList = new ArrayList<DeviceMbrStatus>();
			deviceMbrStatusList.addAll(deviceMbrStatusSpidlList);
		}

		if (deviceMbrStatusList == null || deviceMbrStatusList.size() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		}

		HashMap deviceMbrStatusMap = new HashMap();

		for (DeviceMbrStatus deviceMbrStatus : deviceMbrStatusList) {
			String deviceKey = deviceMbrStatus.getDeviceKey();
			deviceMbrStatusMap.put(deviceKey, deviceMbrStatus);
		}

		SearchMbrDeviceResponse searchMbrDeviceResponse = new SearchMbrDeviceResponse();
		searchMbrDeviceResponse.setDeviceMbrStatusMap(deviceMbrStatusMap);
		searchMbrDeviceResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));
		return searchMbrDeviceResponse;
	}

	/**
	 * <pre>
	 * 조회 구분별 사용자 정보 조회.
	 * </pre>
	 * 
	 * @param searchExtentUserRequest
	 *            조회 구분별 사용자 정보 조회 요청 Value Object
	 * @return SearchExtentUserResponse - 조회 구분별 사용자 정보 조회 응답 Value Object
	 */
	@SuppressWarnings("unchecked")
	@Override
	public SearchExtentUserResponse searchExtentUser(SearchExtentUserRequest searchExtentUserRequest) {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("사용자 서비스 - 사용자회원 기본정보 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### searchUserRequest : {}", searchExtentUserRequest.toString());

		// 조회
		SearchExtentUserResponse searchExtentUserResponse = null;

		CommonDAO dao = this.commonDAO;
		String isDormant = Constant.TYPE_YN_N;
		UserMbr resultUserMbr = null;
		UserMbrPnsh resultUserMbrPnsh = null;
		MbrLglAgent resultMbrLglAgent = null;
		MbrAuth resultMbrAuth = null;
		Grade resultGrade = null;
		List<MbrMangItemPtcr> resultMbrMangItemPtcrList = null;
		List<MbrClauseAgree> resultMbrClauseAgreeList = null;

		boolean isDeviceRequest = false;
		List<KeySearch> keySearchList = searchExtentUserRequest.getKeySearchList();
		for (KeySearch keySearch : keySearchList) {
			if (keySearch.getKeyType().equals(Constant.SEARCH_TYPE_DEVICE_KEY)
					|| keySearch.getKeyType().equals(Constant.SEARCH_TYPE_DEVICE_ID)
					|| keySearch.getKeyType().equals(Constant.SEARCH_TYPE_MDN)) {
				isDeviceRequest = true;
			}
		}

		HashMap<String, Object> map = null;

		// 휴대기기 정보로 조회
		if (isDeviceRequest) {
			map = dao.queryForObject("User.searchExtentUserD", searchExtentUserRequest, HashMap.class);
		} else {
			map = dao.queryForObject("User.searchExtentUser", searchExtentUserRequest, HashMap.class);
		}

		if (map == null) {
			// 휴면DB 조회
			dao = this.idleDAO;
			if (isDeviceRequest) {
				map = dao.queryForObject("User.searchExtentUserD", searchExtentUserRequest, HashMap.class);
			} else {
				map = dao.queryForObject("User.searchExtentUser", searchExtentUserRequest, HashMap.class);
			}

			if (map == null) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.userKeyNotFound", ""));
			} else {
				isDormant = Constant.TYPE_YN_Y;
			}

		}

		String userKey = (String) map.get("USERKEY");
		// 생성
		searchExtentUserResponse = new SearchExtentUserResponse();
		searchExtentUserResponse.setPwRegDate(StringUtils.defaultString((String) map.get("PWREGDATE")));
		searchExtentUserResponse.setTotalDeviceCount(String.valueOf(map.get("TOTALDEVICECOUNT")));
		// userKey
		searchExtentUserResponse.setUserKey(userKey);

		// 사용자 정보 조회 유무
		if (StringUtils.equals(Constant.TYPE_YN_Y, searchExtentUserRequest.getUserInfoYn())) {
			UserMbr userMbr = new UserMbr();
			userMbr.setUserKey(userKey);
			resultUserMbr = dao.queryForObject("User.getUserDetail", userMbr, UserMbr.class);
			if (resultUserMbr != null) {
				resultUserMbr.setIsDormant(isDormant);
			}
			// TODO 여기까지!!
			resultMbrMangItemPtcrList = dao.queryForList("User.getManagementItemList", userMbr, MbrMangItemPtcr.class);

			// 회원 정보 중 성별, 생년월일, 이름 정보 설정 우선순위 (1.인증정보 , 2.회원정보)
			if (resultUserMbr != null
					&& !StringUtils.equals(Constant.TYPE_YN_Y, searchExtentUserRequest.getMbrAuthInfoYn())) {
				if (StringUtils.equals(Constant.TYPE_YN_Y, resultUserMbr.getIsRealName())) {
					// 실명인증 정보 조회
					resultMbrAuth = dao.queryForObject("User.getRealNameOwn", userMbr, MbrAuth.class);
					if (resultMbrAuth != null) {
						String birth = StringUtils.defaultString(resultMbrAuth.getBirthDay());
						// 생년월일이 숫자 + YYYYMMDD 일경우만 응답 설정
						if (StringUtils.isNumeric(birth) && birth.length() == 8) {
							resultUserMbr.setUserBirthDay(resultMbrAuth.getBirthDay());
						}
						resultUserMbr.setUserName(StringUtils.defaultString(resultMbrAuth.getName()));
						resultUserMbr.setUserSex(StringUtils.defaultString(resultMbrAuth.getSex()));
					}
				}
			}
		}

		if (resultUserMbr == null) {
			resultUserMbr = new UserMbr();
			resultMbrMangItemPtcrList = new ArrayList<MbrMangItemPtcr>();
		}

		searchExtentUserResponse.setUserMbr(resultUserMbr);
		searchExtentUserResponse.setMbrMangItemPtcrList(resultMbrMangItemPtcrList);

		// 약관정보
		if (StringUtils.equals(Constant.TYPE_YN_Y, searchExtentUserRequest.getAgreementInfoYn())) {
			UserMbr userMbr = new UserMbr();
			userMbr.setUserKey(userKey);
			userMbr.setTenantID(searchExtentUserRequest.getCommonRequest().getTenantID());
			resultMbrClauseAgreeList = dao.queryForList("User.getClauseAgreeList", userMbr, MbrClauseAgree.class);
		}

		if (resultMbrClauseAgreeList == null) {
			resultMbrClauseAgreeList = new ArrayList<MbrClauseAgree>();
		}
		searchExtentUserResponse.setMbrClauseAgreeList(resultMbrClauseAgreeList);

		if (StringUtils.equals(Constant.TYPE_YN_Y, searchExtentUserRequest.getMbrPnshInfoYn())) {
			UserMbrPnsh userMbrPnsh = new UserMbrPnsh();
			userMbrPnsh.setUserKey(userKey);
			userMbrPnsh.setIsDormant(isDormant); // 휴면계정인경우 null 리턴

			resultUserMbrPnsh = dao.queryForObject("User.getUserPunish", userMbrPnsh, UserMbrPnsh.class);
		}

		if (resultUserMbrPnsh == null) {
			resultUserMbrPnsh = new UserMbrPnsh();
			resultUserMbrPnsh.setIsRestricted(Constant.TYPE_YN_N);
		} else {
			resultUserMbrPnsh.setIsRestricted(Constant.TYPE_YN_Y);
		}
		searchExtentUserResponse.setUserMbrPnsh(resultUserMbrPnsh);

		// 실명인증 : 법정대리인
		if (StringUtils.equals(Constant.TYPE_YN_Y, searchExtentUserRequest.getMbrLglAgentInfoYn())) {
			UserMbr userMbr = new UserMbr();
			userMbr.setUserKey(userKey);
			resultMbrLglAgent = dao.queryForObject("User.getRealNameParent", userMbr, MbrLglAgent.class);
		}

		if (resultMbrLglAgent == null) {
			resultMbrLglAgent = new MbrLglAgent();
			resultMbrLglAgent.setIsParent(Constant.TYPE_YN_N);
		} else {
			resultMbrLglAgent.setIsParent(Constant.TYPE_YN_Y);
		}
		searchExtentUserResponse.setMbrLglAgent(resultMbrLglAgent);

		// 실명인증 : 본인
		if (StringUtils.equals(Constant.TYPE_YN_Y, searchExtentUserRequest.getMbrAuthInfoYn())) {
			UserMbr userMbr = new UserMbr();
			userMbr.setUserKey(userKey);
			resultMbrAuth = dao.queryForObject("User.getRealNameOwn", userMbr, MbrAuth.class);
		}

		if (resultMbrAuth == null) {
			resultMbrAuth = new MbrAuth();
			resultMbrAuth.setIsRealName(Constant.TYPE_YN_N);
		} else {
			resultMbrAuth.setIsRealName(Constant.TYPE_YN_Y);
		}
		searchExtentUserResponse.setMbrAuth(resultMbrAuth);

		// 사용자 회원 등급(휴면DB 이관대상 테이블 아님)
		if (StringUtils.equals(Constant.TYPE_YN_Y, searchExtentUserRequest.getGradeInfoYn())) {
			UserMbr userMbr = new UserMbr();
			userMbr.setUserKey(userKey);
			resultGrade = this.commonDAO.queryForObject("User.getUserGrade", userMbr, Grade.class);

			// 회원 등급 미존재시 Default : gold
			if (resultGrade == null || StringUtils.equals(resultGrade.getUserGradeCd(), "silver")) {
				if (resultGrade == null)
					resultGrade = new Grade();
				resultGrade.setUserGradeCd("gold");
			}
			searchExtentUserResponse.setGrade(resultGrade);
		}

		LOGGER.debug("### DB에서 받아온 결과 : {}", searchExtentUserResponse);

		searchExtentUserResponse.setIsChangeSubject(Constant.TYPE_YN_N);
		searchExtentUserResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));
		return searchExtentUserResponse;
	}

	/**
	 * <pre>
	 * 회원 segment 정보 조회.
	 * </pre>
	 * 
	 * @param searchUserSegmentRequest
	 *            회원 segment 정보 조회 요청 Value Object
	 * @return searchUserSegmentResponse - 회원 segment 정보 조회 응답 Value Object
	 */
	@Override
	public SearchUserSegmentResponse searchUserSegment(SearchUserSegmentRequest searchUserSegmentRequest) {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("사용자 서비스 - 회원 segment 정보 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### searchUserSegmentRequest : {}", searchUserSegmentRequest.toString());

		// 조회
		SearchUserSegmentResponse searchUserSegmentResponse = null;

		UserMbr resultUserMbr = null;
		MbrAuth resultMbrAuth = null;
		String isDormant = Constant.TYPE_YN_N;

		// 사용자 정보 조회
		UserMbr userMbr = new UserMbr();
		userMbr.setUserKey(searchUserSegmentRequest.getUserKey());
		userMbr.setTenantID(searchUserSegmentRequest.getCommonRequest().getTenantID());
		resultUserMbr = this.commonDAO.queryForObject("User.getUserDetail", userMbr, UserMbr.class);

		if (resultUserMbr == null) {
			resultUserMbr = this.idleDAO.queryForObject("User.getUserDetail", userMbr, UserMbr.class);
			isDormant = Constant.TYPE_YN_Y;
		}

		if (resultUserMbr == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		}

		if (StringUtils.isNotBlank(searchUserSegmentRequest.getDeviceKey())) {
			searchUserSegmentRequest.setIsDormant(isDormant);
			if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
				searchUserSegmentResponse = this.commonDAO.queryForObject("User.searchUserSegmentD",
						searchUserSegmentRequest, SearchUserSegmentResponse.class);
			} else {
				searchUserSegmentResponse = this.idleDAO.queryForObject("User.searchUserSegmentD",
						searchUserSegmentRequest, SearchUserSegmentResponse.class);
			}
		} else {
			if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
				searchUserSegmentResponse = this.commonDAO.queryForObject("User.searchUserSegment",
						searchUserSegmentRequest, SearchUserSegmentResponse.class);
			} else {
				searchUserSegmentResponse = this.idleDAO.queryForObject("User.searchUserSegment",
						searchUserSegmentRequest, SearchUserSegmentResponse.class);
			}
		}

		String birth = "";
		birth = StringUtils.defaultString(resultUserMbr.getUserBirthDay());
		// 생년월일이 숫자 + YYYYMMDD 일경우만 응답 설정
		if (StringUtils.isNumeric(birth) && birth.length() == 8) {
			searchUserSegmentResponse.setUserBirthDay(resultUserMbr.getUserBirthDay());
		}
		searchUserSegmentResponse.setUserSex(StringUtils.defaultString(resultUserMbr.getUserSex()));

		if (StringUtils.equals(Constant.TYPE_YN_Y, resultUserMbr.getIsRealName())) {
			// 실명인증 정보 조회
			if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
				resultMbrAuth = this.commonDAO.queryForObject("User.getRealNameOwn", userMbr, MbrAuth.class);
			} else {
				resultMbrAuth = this.idleDAO.queryForObject("User.getRealNameOwn", userMbr, MbrAuth.class);
			}

			if (resultMbrAuth != null) {
				birth = StringUtils.defaultString(resultMbrAuth.getBirthDay());
				// 생년월일이 숫자 + YYYYMMDD 일경우만 응답 설정
				if (StringUtils.isNumeric(birth) && birth.length() == 8) {
					searchUserSegmentResponse.setUserBirthDay(resultMbrAuth.getBirthDay());
				}
				searchUserSegmentResponse.setUserSex(StringUtils.defaultString(resultMbrAuth.getSex()));
			}
		}

		LOGGER.debug("### DB에서 받아온 결과 : {}", searchUserSegmentResponse);

		searchUserSegmentResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));

		return searchUserSegmentResponse;
	}

	/**
	 * <pre>
	 * 회원 부가속성 정보 조회.
	 * </pre>
	 * 
	 * @param searchUserExtraInfoRequest
	 *            회원 부가속성 정보 조회 요청 Value Object
	 * @return searchUserExtraInfoResponse - 회원 부가속성 정보 조회 응답 Value Object
	 */
	@Override
	public SearchUserExtraInfoResponse searchUserExtraInfo(SearchUserExtraInfoRequest searchUserExtraInfoRequest) {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("사용자 서비스 - 회원 부가속성 정보 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### searchUserExtraInfoRequest : {}", searchUserExtraInfoRequest.toString());

		// 조회
		SearchUserExtraInfoResponse searchUserExtraInfoResponse = null;

		int selectCnt = searchUserExtraInfoRequest.getUserKeyList().size();
		List<MbrMangItemPtcr> mbrMangItemPtcrList = null;
		List<MbrMangItemPtcr> mbrMangItemPtcrSpidlList = null;

		if (selectCnt < 100) {
			mbrMangItemPtcrList = (ArrayList) this.commonDAO.queryForList("User.searchUserExtraInfo",
					searchUserExtraInfoRequest);

			mbrMangItemPtcrSpidlList = (ArrayList) this.idleDAO.queryForList("User.searchUserExtraInfo",
					searchUserExtraInfoRequest);

			if (mbrMangItemPtcrSpidlList != null && mbrMangItemPtcrSpidlList.size() > 0) {
				if (mbrMangItemPtcrList == null)
					mbrMangItemPtcrList = new ArrayList<MbrMangItemPtcr>();

				mbrMangItemPtcrList.addAll(mbrMangItemPtcrSpidlList);
			}
		} else {

			mbrMangItemPtcrList = new ArrayList<MbrMangItemPtcr>();

			// 100개씩 조회를 위해 List 분할
			HashMap<Integer, List> distributedList = Utils.distributeListToSameCapacity(100,
					searchUserExtraInfoRequest.getUserKeyList());

			for (int i = 0; i < distributedList.size(); i++) {

				searchUserExtraInfoRequest.setUserKeyList(distributedList.get(i));

				List<MbrMangItemPtcr> multMbrMangItemPtcrList = (ArrayList) this.commonDAO.queryForList(
						"User.searchUserExtraInfo", searchUserExtraInfoRequest);

				List<MbrMangItemPtcr> multMbrMangItemPtcrSpidlList = (ArrayList) this.idleDAO.queryForList(
						"User.searchUserExtraInfo", searchUserExtraInfoRequest);

				if (multMbrMangItemPtcrSpidlList != null && multMbrMangItemPtcrSpidlList.size() > 0) {
					if (multMbrMangItemPtcrList == null)
						multMbrMangItemPtcrList = new ArrayList<MbrMangItemPtcr>();

					multMbrMangItemPtcrList.addAll(multMbrMangItemPtcrSpidlList);
				}

				if (multMbrMangItemPtcrList != null && multMbrMangItemPtcrList.size() > 0) {
					mbrMangItemPtcrList.addAll(multMbrMangItemPtcrList);
				}
			}
		}

		if (mbrMangItemPtcrList == null || mbrMangItemPtcrList.size() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		}

		HashMap userExtraInfoMap = new HashMap();
		List<MbrMangItemPtcr> resultMbrMangItemPtcrList = null;

		for (MbrMangItemPtcr basseMangItemPtcr : mbrMangItemPtcrList) {
			String userKey = basseMangItemPtcr.getUserKey();
			resultMbrMangItemPtcrList = new ArrayList<MbrMangItemPtcr>();
			for (MbrMangItemPtcr mangItemPtcr : mbrMangItemPtcrList) {
				if (StringUtils.equals(userKey, mangItemPtcr.getUserKey())) {
					resultMbrMangItemPtcrList.add(mangItemPtcr);
				}
			}
			userExtraInfoMap.put(userKey, resultMbrMangItemPtcrList);
		}

		searchUserExtraInfoResponse = new SearchUserExtraInfoResponse();
		searchUserExtraInfoResponse.setSearchUserExtraInfoMap(userExtraInfoMap);

		LOGGER.debug("### DB에서 받아온 결과 : {}", searchUserExtraInfoResponse);

		searchUserExtraInfoResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));

		return searchUserExtraInfoResponse;
	}

	/**
	 * <pre>
	 * 심플 인증(간편인증).
	 * </pre>
	 * 
	 * @param simpleLoginRequest
	 *            - 심플인증 요청 Value Object
	 * @return simpleLoginResponse - 심플인증 응답 Value Object
	 */
	@Override
	public SimpleLoginResponse simpleLogin(SimpleLoginRequest simpleLoginRequest) {

		SimpleLoginResponse simpleLoginResponse = (SimpleLoginResponse) this.commonDAO.queryForObject(
				"User.simpleLogin", simpleLoginRequest);

		if (simpleLoginResponse == null) { // 로그인 실패
			// 휴면DB 조회
			simpleLoginResponse = (SimpleLoginResponse) this.idleDAO.queryForObject("User.simpleLogin",
					simpleLoginRequest);
		}

		if (simpleLoginResponse == null) {
			simpleLoginResponse = new SimpleLoginResponse();
			simpleLoginResponse.setIsLoginSuccess(Constant.TYPE_YN_N);
		} else { // 로그인 성공
			UserMbrLoginLog userMbrLoginLog = new UserMbrLoginLog();
			userMbrLoginLog.setTenantID(simpleLoginRequest.getCommonRequest().getTenantID());
			userMbrLoginLog.setSystemID(simpleLoginRequest.getCommonRequest().getSystemID());
			userMbrLoginLog.setUserKey(simpleLoginResponse.getUserKey());
			userMbrLoginLog.setIsAutoLogin(Constant.TYPE_YN_Y);

			if (simpleLoginRequest.getConnIp() != null) {
				userMbrLoginLog.setConnIp(simpleLoginRequest.getConnIp());
			}
			if (simpleLoginRequest.getScVersion() != null) {
				userMbrLoginLog.setScVersion(simpleLoginRequest.getScVersion());
			}

			if (StringUtils.isNotBlank(simpleLoginRequest.getDeviceModelNm())) {
				userMbrLoginLog.setDeviceModelNm(simpleLoginRequest.getDeviceModelNm());
			}

			if (StringUtils.isNotBlank(simpleLoginRequest.getDeviceOsNm())) {
				userMbrLoginLog.setDeviceOsNm(simpleLoginRequest.getDeviceOsNm());
			}

			if (StringUtils.isNotBlank(simpleLoginRequest.getDeviceOsVersion())) {
				userMbrLoginLog.setDeviceOsVersion(simpleLoginRequest.getDeviceOsVersion());
			}

			// 로그인 이력 저장
			int row = 0;
			try {
				row = (Integer) this.commonDAO.insert("User.insertLoginLog", userMbrLoginLog);
			} catch (Exception e) {
				// 중복요청으로 conn_dt가 동일하게 들어가 중복키 에러가 발생할 수 있으므로, Exception을 무시한다.
				row = 0;
			}
			LOGGER.debug("### row : {}", row);

			simpleLoginResponse.setIsLoginSuccess(Constant.TYPE_YN_Y);

		}
		simpleLoginResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));

		return simpleLoginResponse;
	}

	/**
	 * <pre>
	 * 회원 가입여부 리스트 조회.
	 * </pre>
	 * 
	 * @param existListRequest
	 *            - 회원 가입여부 리스트 조회 요청 Value Object
	 * @return existListResponse - 회원 가입여부 리스트 조회 응답 Value Object
	 */
	@Override
	public ExistListResponse existList(ExistListRequest existListRequest) {

		ExistListResponse existListResponse = null;

		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		for (String deviceId : existListRequest.getDeviceIdList()) {
			dataMap.put(deviceId, "");
		}

		List<UserMbrDevice> userMbrDevices = (List<UserMbrDevice>) this.commonDAO.queryForList("User.searchExistList",
				existListRequest);

		List<UserMbrDevice> userMbrSpidlDevices = (List<UserMbrDevice>) this.idleDAO.queryForList(
				"User.searchExistList", existListRequest);

		if (userMbrSpidlDevices != null && userMbrSpidlDevices.size() > 0) {
			if (userMbrDevices == null)
				userMbrDevices = new ArrayList<UserMbrDevice>();

			userMbrDevices.addAll(userMbrSpidlDevices);
		}

		if (userMbrDevices == null || userMbrDevices.size() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		}

		// 검색결과 UserKey 담기
		for (UserMbrDevice userMbrDevice : userMbrDevices) {
			for (String key : dataMap.keySet()) {
				if (StringUtils.equals(key, userMbrDevice.getDeviceID())) {
					dataMap.put(userMbrDevice.getDeviceID(), userMbrDevice.getUserKey());
				}
			}
		}

		// 검색 결과 DeviceId 와 요청 DeviceId 응답값으로 합치기
		List<UserMbrDevice> resultDeviceList = new ArrayList<UserMbrDevice>();
		for (Entry<String, Object> entry : dataMap.entrySet()) {
			UserMbrDevice userMbrDevice = new UserMbrDevice();
			userMbrDevice.setDeviceID(entry.getKey());
			userMbrDevice.setUserKey((String) entry.getValue());
			resultDeviceList.add(userMbrDevice);
		}

		existListResponse = new ExistListResponse();

		existListResponse.setDeviceIdList(resultDeviceList);
		LOGGER.debug("### DB에서 받아온 결과 : {}", resultDeviceList);

		existListResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));

		return existListResponse;
	}

	@Override
	public TlogResponse tlog(TlogRequest tlogRequest) {

		TlogResponse tlogResponse = new TlogResponse();

		final String tlogID = tlogRequest.getTlogInfo().getTlogID();
		final String systemId = tlogRequest.getCommonRequest().getSystemID();

		if (StringUtils.equals("TL_SC_MEM_0006", tlogID)) {
			final String userKey = tlogRequest.getTlogInfo().getUserKey();
			final String deviceKey = tlogRequest.getTlogInfo().getDeviceKey();
			final String deviceId = tlogRequest.getTlogInfo().getDeviceId();
			final String userMbrNoPre = tlogRequest.getTlogInfo().getUsermbrNoPre();
			final String userMbrNoPost = tlogRequest.getTlogInfo().getUsermbrNoPost();

			new TLogUtil().set(new ShuttleSetter() {
				@Override
				public void customize(TLogSentinelShuttle shuttle) {
					shuttle.log_id(tlogID).insd_usermbr_no(userKey).insd_device_id(deviceKey).device_id(deviceId)
							.usermbr_no_pre(userMbrNoPre).usermbr_no_post(userMbrNoPost).request_system_id(systemId);
				}
			});

		}

		return tlogResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SearchManagementResponse searchManagement(SearchManagementRequest searchManagementRequest) {

		SearchManagementResponse searchManagementResponse = new SearchManagementResponse();

		List<MbrMangItemPtcr> mbrMangItemPtcrList = (List<MbrMangItemPtcr>) this.commonDAO.queryForList(
				"User.selectManagementInfo", searchManagementRequest);

		if (mbrMangItemPtcrList == null || mbrMangItemPtcrList.size() <= 0) {
			mbrMangItemPtcrList = (List<MbrMangItemPtcr>) this.idleDAO.queryForList("User.selectManagementInfo",
					searchManagementRequest);
		}

		if (mbrMangItemPtcrList == null || mbrMangItemPtcrList.size() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		}

		searchManagementResponse.setMbrMangItemPtcrList(mbrMangItemPtcrList);
		searchManagementResponse.setUserKey(searchManagementRequest.getUserKey());
		searchManagementResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));

		return searchManagementResponse;
	}

	/**
	 * <pre>
	 * 사용자 성인인증 정보 초기화.
	 * </pre>
	 * 
	 * @param updateRealNameRequest
	 *            사용자 성인인증 정보 수정 요청 Value Object
	 * @return UpdateRealNameResponse - 사용자 성인인증 정보 수정 응답 Value Object
	 */
	@Override
	public UpdateRealNameResponse executeInitRealName(UpdateRealNameRequest updateRealNameRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("서비스 - 성인인증 정보 초기화");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		UserMbr usermbr = new UserMbr();
		usermbr.setTenantID(updateRealNameRequest.getCommonRequest().getTenantID());
		usermbr.setUserKey(updateRealNameRequest.getUserKey());

		// 성인인증 정보 제거
		Integer row = this.commonDAO.delete("User.removeUserMbrAuth", usermbr);
		LOGGER.debug("### removeUserMbrAuth row : {}", row);

		// 회원 이력 테이블 insert.
		row = this.commonDAO.update("User.insertUpdateStatusHistory", usermbr);
		LOGGER.debug("### insertUpdateStatusHistory row : {}", row);
		if (row <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.editInputItemNotFound", ""));
		}

		// 성인인증 초기화 update
		row = this.commonDAO.update("User.updateInitRealName", updateRealNameRequest);
		if (row <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
		}

		UpdateRealNameResponse updateRealNameResponse = new UpdateRealNameResponse();
		updateRealNameResponse.setUserKey(updateRealNameRequest.getUserKey());
		updateRealNameResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));

		return updateRealNameResponse;
	}

	/**
	 * <pre>
	 * 정상인 회원의 테넌트 리스트를 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param listTenantRequest
	 *            - 기기ID 조회 요청 Value Object
	 * @return ListTenantResponse - 테넌트 리스트 응답 Value Object
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ListTenantResponse searchTenantList(ListTenantRequest listTenantRequest) {
		LOGGER.debug("### searchTenantList : {}", listTenantRequest.toString());

		List<String> tenantList = (List<String>) this.commonDAO
				.queryForList("User.searchTenantList", listTenantRequest);

		List<String> tenantSpidlList = (List<String>) this.idleDAO.queryForList("User.searchTenantList",
				listTenantRequest);

		if (tenantSpidlList != null && tenantSpidlList.size() > 0) {
			if (tenantList == null)
				tenantList = new ArrayList<String>();

			tenantList.addAll(tenantSpidlList);
		}

		if (tenantList == null || tenantList.size() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		}

		ListTenantResponse listTenantResponse = new ListTenantResponse();
		listTenantResponse.setTenantList(tenantList);

		LOGGER.debug("### DB에서 받아온 결과 : {}", listTenantResponse);

		return listTenantResponse;
	}

	/**
	 * <pre>
	 * 정상인 회원정보를 휴면 DB로 이동한다.
	 * 성능이슈로 인해서 procedure로 변경 이전 자바 버전 복원 필요시 (Revision #112195 참조)
	 * </pre>
	 * 
	 * @param moveUserInfoRequest
	 *            - 회원키 Value Object
	 * @return moveUserInfoResponse - 처리 회원키 Value Object
	 */
	@Override
	public MoveUserInfoResponse executeMoveUserMbr(MoveUserInfoRequest moveUserInfoRequest) {
		LOGGER.debug("### moveUserInfoRequest : {}", moveUserInfoRequest.toString());
		UserMbr usermbr = new UserMbr();
		usermbr.setTenantID(moveUserInfoRequest.getCommonRequest().getTenantID());
		usermbr.setUserKey(moveUserInfoRequest.getUserKey());
		String moveType = moveUserInfoRequest.getMoveType();

		CommonDAO selectDao = null;

		if (Constant.USERMBR_MOVE_TYPE_ACTIVATE.equals(moveType)) { // 정상 처리
			selectDao = this.idleDAO;
		} else if (Constant.USERMBR_MOVE_TYPE_DORMANT.equals(moveType)) { // 휴면 처리
			selectDao = this.commonDAO;
		}

		// TB_US_USERMBR에서 대상 유저가 존재하는지 확인
		UserMbrInfo userInfo = null;
		if (selectDao != null) {
			userInfo = (UserMbrInfo) selectDao.queryForObject("User.selectUsermbr", usermbr);
		}

		if (userInfo == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		}

		Map<String, String> params = new HashMap<String, String>();
		params.put("tenantID", usermbr.getTenantID());
		params.put("userKey", usermbr.getUserKey());

		if (Constant.USERMBR_MOVE_TYPE_ACTIVATE.equals(moveType)) { // 정상 처리
			params.put("moveType", "1");
		} else if (Constant.USERMBR_MOVE_TYPE_DORMANT.equals(moveType)) { // 휴면 처리
			params.put("moveType", "2");
		}

		MoveUserInfoResponse moveUserInfoResponse = new MoveUserInfoResponse();
		moveUserInfoResponse.setTenantID(userInfo.getTenantId());
		moveUserInfoResponse.setUserKey(userInfo.getInsdUserMbrNo());
		moveUserInfoResponse.setTransCd(moveType);
		moveUserInfoResponse.setUserMbrNo(userInfo.getUserMbrNo());
		moveUserInfoResponse.setIntgSvcNo(userInfo.getIntgSvcNo());
		moveUserInfoResponse.setMbrId(userInfo.getMbrId());
		moveUserInfoResponse.setMbrClasCd(userInfo.getMbrClasCd());
		moveUserInfoResponse.setEmailAddr(userInfo.getEmailAddr());

		// IDP 연동 결과는 Controller에서 로그 저장시에 직접 셋팅.
		// moveUserInfoResponse.setIdpResultYn(idpResultYn);

		try {
			this.commonDAO.queryForObject("User.callMoveUserProcedure", params);
		} catch (Exception e) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""),
					moveUserInfoResponse);
		}

		// 유휴회원 상태 해제(정상) 처리 일 경우 면서, 모바일 회원인 경우만 넘겨준다.
		if (Constant.USERMBR_MOVE_TYPE_ACTIVATE.equals(moveType)
				&& Constant.USER_TYPE_MOBILE.equals(userInfo.getMbrClasCd())) {
			if (StringUtils.isNotBlank(userInfo.getEmailAddr())) {
				List<KeySearch> keySearchList;
				KeySearch keySearch = new KeySearch();
				keySearch.setKeyType(Constant.SEARCH_TYPE_USER_KEY);
				keySearch.setKeyString(moveUserInfoRequest.getUserKey());
				keySearchList = new ArrayList<KeySearch>();
				keySearchList.add(keySearch);

				SearchDeviceListRequest searchDeviceListRequest;
				searchDeviceListRequest = new SearchDeviceListRequest();
				searchDeviceListRequest.setCommonRequest(moveUserInfoRequest.getCommonRequest());
				searchDeviceListRequest.getCommonRequest().setSystemID(userInfo.getMbrId());
				searchDeviceListRequest.setKeySearchList(keySearchList);
				searchDeviceListRequest.setIsMainDevice("N"); // N으로 셋팅 할 경우 대표기기 조건 셋팅 안함.

				SearchDeviceListResponse searchDeviceListResponse = this.commonDAO.queryForObject(
						"Device.searchDeviceList2", searchDeviceListRequest, SearchDeviceListResponse.class);

				List<UserMbrDevice> userMbrDeviceList = searchDeviceListResponse.getUserMbrDevice();
				if (userMbrDeviceList != null && userMbrDeviceList.size() > 0) {
					moveUserInfoResponse.setUserMbrDevice(userMbrDeviceList.get(0));
				}
			}
		}

		LOGGER.debug("### moveUserInfoResponse : {}", moveUserInfoResponse);
		return moveUserInfoResponse;
	}

	/**
	 * <pre>
	 * 소셜 이력 등록 기능을 제공한다.
	 * </pre>
	 * 
	 * @param createSocialAccountRequest
	 *            - 소셜계정 등록 요청 Value Object
	 * @return createSocialAccountRequest - 소셜계정 등록 응답 Value Object
	 */
	@Override
	public CreateSocialAccountResponse createSocialAccount(CreateSocialAccountRequest createSocialAccountRequest) {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("서비스 - 소셜 이력 등록 ");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		// 소셜계정 이력 insert
		Integer row = (Integer) this.commonDAO.insert("User.insertSocialHis", createSocialAccountRequest);
		LOGGER.debug("### row : {}", row);

		if (row <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
		}

		CreateSocialAccountResponse createSocialAccountResponse = new CreateSocialAccountResponse();
		createSocialAccountResponse.setUserKey(createSocialAccountRequest.getUserKey());
		createSocialAccountResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));

		return createSocialAccountResponse;
	}

	/**
	 * <pre>
	 * 소셜계정 이력 조회 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchSocialAccountRequest
	 *            - 소셜계정 이력 조회 요청 Value Object
	 * @return searchSocialAccountResponse- 소셜계정 이력 조회 응답 Value Object
	 */
	@Override
	public SearchSocialAccountResponse searchSocialAccount(SearchSocialAccountRequest searchSocialAccountRequest) {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("서비스 - 소셜계정 이력 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		// 소셜계정 이력 insert
		List<SocialAccount> socialAccountList = (List<SocialAccount>) this.commonDAO.queryForList(
				"User.searchSocialAccountHis", searchSocialAccountRequest);

		if (socialAccountList == null || socialAccountList.size() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		}

		SearchSocialAccountResponse searchSocialAccountResponse = new SearchSocialAccountResponse();
		searchSocialAccountResponse.setSocialAccountList(socialAccountList);
		searchSocialAccountResponse.setUserKey(searchSocialAccountRequest.getUserKey());
		searchSocialAccountResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));

		return searchSocialAccountResponse;
	}

	/**
	 * <pre>
	 * 유휴 회원 이관 이력을 남긴다.
	 * </pre>
	 * 
	 * @param moveUserInfoResponse
	 *            MoveUserInfoResponse
	 * @param resultYn
	 *            String
	 */
	@Override
	public void insertUserMbrTransHis(MoveUserInfoResponse moveUserInfoResponse, String resultYn) {
		LOGGER.debug("### insertUserMbrTransHis - resultYn : {}", resultYn);
		LOGGER.debug("### moveUserInfoRequest : {}", moveUserInfoResponse.toString());

		Map<String, String> params = new HashMap<String, String>();
		params.put("tenantID", moveUserInfoResponse.getTenantID());
		params.put("insdUserMbrNo", moveUserInfoResponse.getUserKey());
		params.put("resultYn", resultYn);
		params.put("transCd", moveUserInfoResponse.getTransCd());
		params.put("regId", "SAC"); // SAC로 등
		params.put("intgSvcNo", moveUserInfoResponse.getIntgSvcNo());
		params.put("userMbrNo", moveUserInfoResponse.getUserMbrNo());
		params.put("idpResultYn", moveUserInfoResponse.getIdpResultYn());

		if (moveUserInfoResponse.getIdpErrCd() != null) {
			params.put("idpErrCd", moveUserInfoResponse.getIdpErrCd().replace("EC_IDP_", ""));
		}

		// tb_us_usermbr_trans_his 남김 (정상)
		this.commonDAO.insert("User.insertUserMbrTransHis", params);
	}

	/**
	 * <pre>
	 * 회원 정보 수정 이전 기존 정보 회원 이력 테이블에 저장.
	 * </pre>
	 * 
	 * @param insdUserMbrNo
	 *            userKey
	 * @return userMbr
	 */
	private UserMbr insertUserMbrHistory(String insdUserMbrNo) {
		UserMbr usermbr = new UserMbr();
		usermbr.setUserKey(insdUserMbrNo);

		this.commonDAO.update("User.insertUpdateStatusHistory", usermbr);
		return usermbr;
	}

	/**
	 * <pre>
	 * userSex 파라미터 체크.
	 * </pre>
	 * 
	 * @param userSex
	 *            성별
	 * @return 결과값
	 */
	private boolean checkValidateUserSexValue(String userSex) {
		if ("M".equals(userSex) || "F".equals(userSex)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * <pre>
	 * 휴면상태해제 사용자의 최종접속일자 컬럼 업데이트.
	 * </pre>
	 * 
	 * @param moveUserInfoRequest
	 *            - 사용자 정보 VO
	 */
	@Override
	public void updateActiveMoveUserLastLoginDt(MoveUserInfoRequest moveUserInfoRequest) {
		UserMbrLoginLog userMbrLoginLog = new UserMbrLoginLog();
		userMbrLoginLog.setTenantID(moveUserInfoRequest.getCommonRequest().getTenantID());
		userMbrLoginLog.setUserKey(moveUserInfoRequest.getUserKey());

		// DA팀의 TB_US_USERMBR.LAST_LOGIN_DT의 update 빈도 줄여 달라는 요청으로 조건 추가
		String todayAlreadyUpdatedLastLoginDt = this.commonDAO.queryForObject("User.selectAlreadyLastLoginDtUpdateYn",
				userMbrLoginLog, String.class);

		if (todayAlreadyUpdatedLastLoginDt == null) {
			this.commonDAO.update("User.updateLastLoginDt", userMbrLoginLog);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.member.user.service.UserService#searchDeActivateUser(com.skplanet.storeplatform.member
	 * .client.user.sci.vo.SearchDeActivateUserRequest)
	 */
	@Override
	public SearchDeActivateUserResponse searchDeActivateUser(SearchDeActivateUserRequest searchDeActivateUserRequest) {

		UserMbr userMbr = this.commonDAO.queryForObject("User.searchDeActivateUser", searchDeActivateUserRequest,
				UserMbr.class);

		if (userMbr == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		}

		SearchDeActivateUserResponse searchDeActivateUserResponse = new SearchDeActivateUserResponse();
		searchDeActivateUserResponse.setUserKey(userMbr.getUserKey());
		searchDeActivateUserResponse.setUserMbr(userMbr);
		searchDeActivateUserResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));

		return searchDeActivateUserResponse;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.member.user.service.UserService#updatePolicyHistory(com.skplanet.storeplatform.member
	 * .client.common.vo.UpdatePolicyRequest)
	 */
	@Override
	public UpdatePolicyResponse updatePolicyHistory(UpdatePolicyRequest updatePolicyRequest) {
		UpdatePolicyResponse updatePolicyResponse = new UpdatePolicyResponse();
		Integer row = 0;

		List<LimitTarget> limitTargetList = updatePolicyRequest.getLimitTargetList();
		List<LimitTarget> limitCodeList;
		limitCodeList = new ArrayList<LimitTarget>();

		for (int i = 0; i < limitTargetList.size(); i++) {
			LimitTarget limitTarget = limitTargetList.get(i);
			limitTarget.setTenantID(updatePolicyRequest.getCommonRequest().getTenantID());
			row = this.commonDAO.update("User.updatePolicyHistory", limitTarget);
			if (row <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}
			limitCodeList.add(limitTarget);
		}

		updatePolicyResponse.setLimitTargetList(limitCodeList);
		updatePolicyResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));
		return updatePolicyResponse;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.member.user.service.UserService#insertPolicy(com.skplanet.storeplatform.member.client
	 * .common.vo.UpdatePolicyRequest)
	 */
	@Override
	public UpdatePolicyResponse insertPolicy(UpdatePolicyRequest updatePolicyRequest) {
		UpdatePolicyResponse updatePolicyResponse = new UpdatePolicyResponse();
		Integer row = 0;

		List<LimitTarget> limitTargetList = updatePolicyRequest.getLimitTargetList();
		List<LimitTarget> limitCodeList;
		limitCodeList = new ArrayList<LimitTarget>();

		for (int i = 0; i < limitTargetList.size(); i++) {
			LimitTarget limitTarget = limitTargetList.get(i);
			limitTarget.setTenantID(updatePolicyRequest.getCommonRequest().getTenantID());
			row = this.commonDAO.update("User.insertPolicy", limitTarget);
			if (row <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}
			limitCodeList.add(limitTarget);
		}

		updatePolicyResponse.setLimitTargetList(limitCodeList);
		updatePolicyResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));
		return updatePolicyResponse;
	}

	/**
	 * <pre>
	 * 배송지 등록/수정.
	 * </pre>
	 * 
	 * @param createDeliveryInfoRequest
	 *            CreateDeliveryInfoRequest
	 * @return CreateDeliveryInfoResponse
	 */
	@Override
	public CreateDeliveryInfoResponse createDeliveryInfo(CreateDeliveryInfoRequest createDeliveryInfoRequest) {

		CreateDeliveryInfoResponse createDeliveryInfoResponse = new CreateDeliveryInfoResponse();

		// 1. 배송지 시퀀스가 없으면 등록 있으면 수정
		if (createDeliveryInfoRequest.getDeliverySeq() == null
				|| createDeliveryInfoRequest.getDeliverySeq().length() <= 0) {

			// 회원 여부 조회
			SearchExtentUserRequest searchExtentUserRequest = new SearchExtentUserRequest();
			List<KeySearch> keySearchList = new ArrayList<KeySearch>();
			KeySearch keySearch = new KeySearch();

			keySearch.setKeyType(Constant.SEARCH_TYPE_USER_KEY);
			keySearch.setKeyString(createDeliveryInfoRequest.getUserKey());
			keySearchList.add(keySearch);

			searchExtentUserRequest.setKeySearchList(keySearchList);
			searchExtentUserRequest.setCommonRequest(createDeliveryInfoRequest.getCommonRequest());
			this.searchExtentUser(searchExtentUserRequest);

			// 배송지 조회를 위한 셋팅
			SearchDeliveryInfoRequest searchSCRequest = new SearchDeliveryInfoRequest();
			searchSCRequest.setCommonRequest(createDeliveryInfoRequest.getCommonRequest());
			searchSCRequest.setUserKey(createDeliveryInfoRequest.getUserKey());
			searchSCRequest.setDeliveryTypeCd(createDeliveryInfoRequest.getDeliveryTypeCd());
			List<SearchDeliveryInfo> searchedList = null;

			// 1-1. 배송지 타입에 따른 기본/최근 배송지 등록
			if (createDeliveryInfoRequest.getDeliveryTypeCd().equals(Constant.DELIVERY_BASE_CD)) {
				// 1-1-1. 기본 배송지 갯수 조회
				searchedList = (List<SearchDeliveryInfo>) this.commonDAO.queryForList("User.searchDeliveryInfoList",
						searchSCRequest);
				// 1-1-2. 없다면 기본 배송지 등록 - regDt만 sysdate로, 있으면 Exception
				if (searchedList != null) {
					if (searchedList.size() == 0) {
						int insert = (Integer) this.commonDAO.insert("User.insertDeliveryInfo",
								createDeliveryInfoRequest);
						if (insert <= 0) {
							throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError",
									""));
						}
					} else {
						throw new StorePlatformException(this.getMessage("response.ResultCode.exceedMaxCount", ""));
					}
				}
			} else {
				// 1-2-1. 최근 배송지 갯수 조회
				searchedList = (List<SearchDeliveryInfo>) this.commonDAO.queryForList("User.searchDeliveryInfoList",
						searchSCRequest);
				// 1-2-2. 2개 이상이면 useDt가 가장 나중 것을 삭제
				if (searchedList != null) {
					if (searchedList.size() > 1) {
						// 배송지 삭제를 위한 셋팅
						RemoveDeliveryInfoRequest removeSCRequest = new RemoveDeliveryInfoRequest();
						removeSCRequest.setCommonRequest(createDeliveryInfoRequest.getCommonRequest());
						removeSCRequest.setUserKey(createDeliveryInfoRequest.getUserKey());
						removeSCRequest.setDeliverySeq(searchedList.get(searchedList.size() - 1).getDeliverySeq());
						int delete = this.commonDAO.delete("User.removeDeliveryInfo", removeSCRequest);
						if (delete <= 0) {
							throw new StorePlatformException(this.getMessage("response.ResultCode.removeError", ""));
						}
					}
				}
				// 1-2-3. 최근 배송지 등록 - regDt, useDt를 sysdate로
				int insert = (Integer) this.commonDAO.insert("User.insertDeliveryInfo", createDeliveryInfoRequest);
				if (insert <= 0) {
					throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
				}
			}

		} else {
			int row = this.commonDAO.update("User.updateDeliveryInfo", createDeliveryInfoRequest);
			if (row <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}
		}

		createDeliveryInfoResponse.setUserKey(createDeliveryInfoRequest.getUserKey());
		createDeliveryInfoResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));

		return createDeliveryInfoResponse;

	}

	/**
	 * <pre>
	 * 배송지 삭제.
	 * </pre>
	 * 
	 * @param removeDeliveryInfoRequest
	 *            RemoveDeliveryInfoRequest
	 * @return RemoveDeliveryInfoResponse
	 */
	@Override
	public RemoveDeliveryInfoResponse removeDeliveryInfo(RemoveDeliveryInfoRequest removeDeliveryInfoRequest) {

		RemoveDeliveryInfoResponse removeDeliveryInfoResponse = new RemoveDeliveryInfoResponse();

		// 해당 배송지 정보 유무를 조회
		SearchDeliveryInfoRequest searchDeliveryInfoRequest = new SearchDeliveryInfoRequest();
		searchDeliveryInfoRequest.setCommonRequest(removeDeliveryInfoRequest.getCommonRequest());
		searchDeliveryInfoRequest.setUserKey(removeDeliveryInfoRequest.getUserKey());
		this.searchDeliveryInfo(searchDeliveryInfoRequest);

		int row = this.commonDAO.delete("User.removeDeliveryInfo", removeDeliveryInfoRequest);

		if (row <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.removeError", ""));
		}

		removeDeliveryInfoResponse.setUserKey(removeDeliveryInfoRequest.getUserKey());
		removeDeliveryInfoResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));

		return removeDeliveryInfoResponse;

	}

	/**
	 * <pre>
	 * 배송지 정보 조회.
	 * </pre>
	 * 
	 * @param searchDeliveryInfoRequest
	 *            SearchUserDeliveryRequest
	 * @return SearchDeliveryInfoResponse
	 */
	@Override
	public SearchDeliveryInfoResponse searchDeliveryInfo(SearchDeliveryInfoRequest searchDeliveryInfoRequest) {

		SearchDeliveryInfoResponse searchDeliveryInfoResponse = new SearchDeliveryInfoResponse();

		List<SearchDeliveryInfo> searchDeliveryInfoList = (List<SearchDeliveryInfo>) this.commonDAO.queryForList(
				"User.searchDeliveryInfoList", searchDeliveryInfoRequest);

		if (searchDeliveryInfoList == null || searchDeliveryInfoList.size() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		}

		searchDeliveryInfoResponse.setSearchDeliveryInfoList(searchDeliveryInfoList);
		searchDeliveryInfoResponse.setUserKey(searchDeliveryInfoRequest.getUserKey());
		searchDeliveryInfoResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));

		return searchDeliveryInfoResponse;

	}

	/**
	 * <pre>
	 * 변경된 사용자키 조회.
	 * </pre>
	 * 
	 * @param searchAfterUserKeyRequest
	 *            searchAfterUserKeyRequest
	 * @return SearchAfterUserKeyResponse
	 */
	@Override
	public SearchAfterUserKeyResponse searchAfterUserKey(SearchAfterUserKeyRequest searchAfterUserKeyRequest) {

		SearchAfterUserKeyResponse searchAfterUserKeyResponse = new SearchAfterUserKeyResponse();

		searchAfterUserKeyResponse.setUserKey((String) this.commonDAO.queryForObject("User.searchAfterUserKey",
				searchAfterUserKeyRequest));

		if (searchAfterUserKeyResponse.getUserKey() == null || searchAfterUserKeyResponse.getUserKey().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		}

		searchAfterUserKeyResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));

		return searchAfterUserKeyResponse;
	}

	@Override
	public TransferDeliveryResponse excuteTransferDelivery(TransferDeliveryRequest transferDeliveryRequest) {
		TransferDeliveryResponse transferDeliveryResponse = new TransferDeliveryResponse();
		Integer row = 0;

		// 모바일 회원(preUserKey) 배송지 정보 조회
		SearchDeliveryInfoRequest searchDeliveryInfo = new SearchDeliveryInfoRequest();
		searchDeliveryInfo.setCommonRequest(new CommonRequest());
		searchDeliveryInfo.getCommonRequest().setTenantID(transferDeliveryRequest.getCommonRequest().getTenantID());
		searchDeliveryInfo.setUserKey(transferDeliveryRequest.getPreUserKey());

		@SuppressWarnings("unchecked")
		List<SearchDeliveryInfo> preUserDeliveryList = (List<SearchDeliveryInfo>) this.commonDAO.queryForList(
				"User.searchDeliveryInfoList", searchDeliveryInfo);

		// 모바일 회원(preUserKey) 배송지 정보가 존재할 경우
		if (preUserDeliveryList != null && preUserDeliveryList.size() > 0) {
			// ID 회원 배송지 정보 조회
			searchDeliveryInfo.setUserKey(transferDeliveryRequest.getUserKey());
			@SuppressWarnings("unchecked")
			List<SearchDeliveryInfo> userDeliveryList = (List<SearchDeliveryInfo>) this.commonDAO.queryForList(
					"User.searchDeliveryInfoList", searchDeliveryInfo);

			// ID 회원 배송지정보가 없을 경우 모바일 회원 배송지 정보 이관
			if (userDeliveryList == null || userDeliveryList.size() <= 0) {
				for (SearchDeliveryInfo transferDelivery : preUserDeliveryList) {
					transferDelivery.setTenantId(transferDeliveryRequest.getCommonRequest().getTenantID());
					transferDelivery.setUserKey(transferDeliveryRequest.getUserKey());

					row = this.commonDAO.update("User.insertTransferDelivery", transferDelivery);
					if (row <= 0) {
						throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
					}
				}
				transferDeliveryResponse.setUserKey(transferDeliveryRequest.getUserKey());
			}
		}

		transferDeliveryResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));

		return transferDeliveryResponse;
	}

	@Override
	public CreateGiftChargeInfoResponse createGiftChargeInfo(CreateGiftChargeInfoRequest createGiftChargeInfoRequest) {
		CreateGiftChargeInfoResponse createGiftChargeInfoResponse = new CreateGiftChargeInfoResponse();
		Integer row = 0;

		SearchGiftChargeInfoRequest searchGiftChargeInfoRequest = new SearchGiftChargeInfoRequest();
		searchGiftChargeInfoRequest.setCommonRequest(createGiftChargeInfoRequest.getCommonRequest());
		searchGiftChargeInfoRequest.setUserKey(createGiftChargeInfoRequest.getUserKey());
		searchGiftChargeInfoRequest.setSellerKey(createGiftChargeInfoRequest.getSellerKey());
		searchGiftChargeInfoRequest.setBrandId(createGiftChargeInfoRequest.getBrandId());
		searchGiftChargeInfoRequest.setChargerId(createGiftChargeInfoRequest.getChargerId());

		// 중복체크 항목 : tenantId, userKey, sellerKey, 제휴사 브랜드 ID , 제휴사 회원 ID
		GiftChargeInfo giftChargeInfo = (GiftChargeInfo) this.commonDAO.queryForObject("User.searchGiftChargeInfo",
				searchGiftChargeInfoRequest);

		// 신규 등록 시
		if (giftChargeInfo == null) {
			// 01-01. 브랜드에 매핑되는 제휴사 아이디 5개 초과 여부 체크
			@SuppressWarnings("unchecked")
			List<GiftChargeInfo> giftChargeInfoList = (List<GiftChargeInfo>) this.commonDAO.queryForList(
					"User.searchGiftChargeInfoList", searchGiftChargeInfoRequest);

			if (giftChargeInfoList != null && giftChargeInfoList.size() >= 5) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.exceedMaxCount", ""));
			}

			// 01-02. 상품권 충전 정보 등록
			row = this.commonDAO.update("User.insertGiftChargeInfo", createGiftChargeInfoRequest);
			if (row <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}

		} else {// 수정 시
			// 02-01. 상품권 충전 정보 수정
			row = this.commonDAO.update("User.updateGiftChargeInfo", createGiftChargeInfoRequest);
			if (row <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}
		}

		createGiftChargeInfoResponse.setUserKey(createGiftChargeInfoRequest.getUserKey());
		createGiftChargeInfoResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));

		return createGiftChargeInfoResponse;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.member.user.service.UserService#searchGiftChargeInfo(com.skplanet.storeplatform.member
	 * .client.user.sci.vo.SearchGiftChargeInfoRequest)
	 */
	@Override
	public SearchGiftChargeInfoResponse searchGiftChargeInfo(SearchGiftChargeInfoRequest searchGiftChargeInfoRequest) {

		List<GiftChargeInfo> giftChargeInfoList = (List<GiftChargeInfo>) this.commonDAO.queryForList(
				"User.searchGiftChargeInfoList", searchGiftChargeInfoRequest);

		if (giftChargeInfoList == null || giftChargeInfoList.size() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		}

		// 판매자 정보 셋팅
		for (GiftChargeInfo giftChargeInfo : giftChargeInfoList) {
			SellerMbr sellerMbr = (SellerMbr) this.commonDAO.queryForObject("Seller.searchSellerMbr",
					giftChargeInfo.getSellerKey());
			if (sellerMbr != null) {
				giftChargeInfo.setSellerMbr(sellerMbr);
			}
		}

		SearchGiftChargeInfoResponse searchGiftChargeInfoResponse = new SearchGiftChargeInfoResponse();
		searchGiftChargeInfoResponse.setUserKey(searchGiftChargeInfoRequest.getUserKey());
		searchGiftChargeInfoResponse.setGiftChargeInfoList(giftChargeInfoList);
		searchGiftChargeInfoResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));

		return searchGiftChargeInfoResponse;
	}

	@Override
	public TransferGiftChrgInfoResponse excuteTransferGiftChrgInfo(
			TransferGiftChrgInfoRequest transferGiftChrgInfoRequest) {
		TransferGiftChrgInfoResponse transferGiftChrgInfoResponse = new TransferGiftChrgInfoResponse();
		Integer row = 0;

		// 모바일 회원(preUserKey) 상품권 충전소 정보 조회
		SearchGiftChargeInfoRequest userGiftChrgInfoReq = new SearchGiftChargeInfoRequest();
		userGiftChrgInfoReq.setCommonRequest(new CommonRequest());
		userGiftChrgInfoReq.getCommonRequest()
				.setTenantID(transferGiftChrgInfoRequest.getCommonRequest().getTenantID());
		userGiftChrgInfoReq.setUserKey(transferGiftChrgInfoRequest.getPreUserKey());

		@SuppressWarnings("unchecked")
		List<GiftChargeInfo> preUserGiftChargeInfoList = (List<GiftChargeInfo>) this.commonDAO.queryForList(
				"User.searchGiftChargeInfoList", userGiftChrgInfoReq);

		// 모바일 회원(preUserKey) 상품권 충전소 정보가 존재할 경우
		if (preUserGiftChargeInfoList != null && preUserGiftChargeInfoList.size() > 0) {
			// ID 회원(userKey) 상품권 충전소 정보 조회
			userGiftChrgInfoReq.setUserKey(transferGiftChrgInfoRequest.getUserKey());
			@SuppressWarnings("unchecked")
			List<GiftChargeInfo> userGiftChargeInfoList = (List<GiftChargeInfo>) this.commonDAO.queryForList(
					"User.searchGiftChargeInfoList", userGiftChrgInfoReq);

			// ID 회원 상품권 충전소 정보가 없을 경우 모바일 회원 상품권 충전소 정보 이관
			if (userGiftChargeInfoList == null || userGiftChargeInfoList.size() <= 0) {
				for (GiftChargeInfo preUserGiftCharInfo : preUserGiftChargeInfoList) {
					preUserGiftCharInfo.setTenantId(transferGiftChrgInfoRequest.getCommonRequest().getTenantID());
					preUserGiftCharInfo.setUserKey(transferGiftChrgInfoRequest.getUserKey());

					row = this.commonDAO.update("User.insertTransferGiftChargeInfo", preUserGiftCharInfo);
					if (row <= 0) {
						throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
					}
				}
				transferGiftChrgInfoResponse.setUserKey(transferGiftChrgInfoRequest.getUserKey());
			}
		}

		transferGiftChrgInfoResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));

		return transferGiftChrgInfoResponse;
	}

}
