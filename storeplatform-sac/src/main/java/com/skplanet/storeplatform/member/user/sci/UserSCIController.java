/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.user.sci;

import com.skplanet.pdp.sentinel.shuttle.TLogSentinelShuttle;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil.ShuttleSetter;
import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.member.client.common.constant.Constant;
import com.skplanet.storeplatform.member.client.common.vo.*;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.*;
import com.skplanet.storeplatform.member.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 사용자 기능을 제공하는 Controller
 * 
 * Updated on : 2016. 1. 5. Updated by : 최진호, 보고지티.
 */
@LocalSCI
public class UserSCIController implements UserSCI {

	/** The Constant logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(UserSCIController.class);

	/** The service. */
	@Autowired
	private UserService service;

	/** The message source accessor. */
	@Autowired
	@Qualifier("scMember")
	private MessageSourceAccessor messageSourceAccessor;

	/*
	 * <pre> 사용자 가입 기능을 제공한다. </pre>
	 * 
	 * @param CreateUserRequest 가입 요청 Value Object
	 * 
	 * @return CreateUserResponse - 가입 응답 Value Object
	 * 
	 * @see com.skplanet.storeplatform.member.client.user.sci.UserSCI#create(com. skplanet.storeplatform.member.client.
	 * user.sci.vo.CreateUserRequest)
	 */
	@Override
	public CreateUserResponse create(CreateUserRequest createUserRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("사용자 컨트롤러 - 사용자 가입");
		LOGGER.debug("==================================================================================\n\n\n\n\n");
		LOGGER.debug("### createUserRequest 찍음 데이터 : {}", createUserRequest);

		// TLog
		new TLogUtil().set(new ShuttleSetter() {
			@Override
			public void customize(TLogSentinelShuttle shuttle) {
				shuttle.log_id("TL_SC_MEM_0004");
			}
		});

		CreateUserResponse createUserResponse;

		// 입력 파라미터가 없음
		if (createUserRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (createUserRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 필수 파라미터 > userMbr
		UserMbr userMbr = createUserRequest.getUserMbr();
		if (userMbr == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// 필수 파라미터 > userMbr, userID, userType, userMainStatus, userSubStatus
		if (userMbr.getUserID() == null || userMbr.getUserID().length() <= 0 // userID
				|| userMbr.getUserType() == null || userMbr.getUserType().length() <= 0 // userType
				|| userMbr.getUserMainStatus() == null || userMbr.getUserMainStatus().length() <= 0 // userMainStatus
				|| userMbr.getUserSubStatus() == null || userMbr.getUserSubStatus().length() <= 0 // userSubStatus
		) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// TLog
		final String tlogUserID = userMbr.getUserID();

		new TLogUtil().set(new ShuttleSetter() {
			@Override
			public void customize(TLogSentinelShuttle shuttle) {
				shuttle.mbr_id(tlogUserID).usermbr_no("");
			}
		});

		// 필수 파라미터 > mbrAuth
		if (createUserRequest.getMbrAuth() != null) {

			// 필수 파라미터 없음, ci
			if (createUserRequest.getMbrAuth().getCi() == null || createUserRequest.getMbrAuth().getCi().length() <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			}

			// 수정 또는 추가할 항목이 없음
			if (createUserRequest.getMbrAuth() == null || this.allFieldIsNull(createUserRequest.getMbrAuth())) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.editInputItemNotFound", ""));
			}

			// 필수 파라미터 > mbrAuth > isDomestic
			if (createUserRequest.getMbrAuth().getIsDomestic() != null) {
				if (!createUserRequest.getMbrAuth().getIsDomestic().equals(Constant.TYPE_YN_Y)
						&& !createUserRequest.getMbrAuth().getIsDomestic().equals(Constant.TYPE_YN_N)
						&& !createUserRequest.getMbrAuth().getIsDomestic().equals(" ")) {
					throw new StorePlatformException(this.getMessage("response.ResultCode.wrongKeyType", ""));
				}
			}
		}

		// 필수 파라미터 > mbrLglAgent
		if (createUserRequest.getMbrLglAgent() != null) {

			// 필수 파라미터 없음, parentName(법정대리인 이름)
			if (createUserRequest.getMbrLglAgent().getParentName() == null
					|| createUserRequest.getMbrLglAgent().getParentName().length() <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			}

			// 수정 또는 추가할 항목이 없음
			if (createUserRequest.getMbrLglAgent() == null || this.allFieldIsNull(createUserRequest.getMbrLglAgent())) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.editInputItemNotFound", ""));

			}

			// 필수 파라미터 > mbrLglAgent > isDomestic
			if (createUserRequest.getMbrLglAgent().getIsDomestic() != null) {
				if (!createUserRequest.getMbrLglAgent().getIsDomestic().equals(Constant.TYPE_YN_Y)
						&& !createUserRequest.getMbrLglAgent().getIsDomestic().equals(Constant.TYPE_YN_N)) {
					throw new StorePlatformException(this.getMessage("response.ResultCode.wrongKeyType", ""));
				}
			}
		}

		// 필수 파라미터 > mbrMangItemPtcrList(관리항목, 부가속성)
		if (createUserRequest.getMbrMangItemPtcrList() != null) {
			List<MbrMangItemPtcr> mbrMangItemPtcrList = createUserRequest.getMbrMangItemPtcrList();
			for (MbrMangItemPtcr mbrMangItemPtcr : mbrMangItemPtcrList) {
				if (mbrMangItemPtcr.getExtraProfile() == null
						|| mbrMangItemPtcr.getExtraProfile().length() <= 0 // 관리항목 코드
						|| mbrMangItemPtcr.getExtraProfileValue() == null
						|| mbrMangItemPtcr.getExtraProfileValue().length() <= 0 // 관리항목 값
				) {
					throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
				}
			}
		}

		// 필수 파라미터 > mbrClauseAgreeList(사용자 약관정보)
		if (createUserRequest.getMbrClauseAgreeList() != null) {
			List<MbrClauseAgree> mbrClauseAgreeList = createUserRequest.getMbrClauseAgreeList();
			for (MbrClauseAgree mbrClauseAgree : mbrClauseAgreeList) {
				if (mbrClauseAgree.getExtraAgreementID() == null
						|| mbrClauseAgree.getExtraAgreementID().length() <= 0 // 이용약관 ID
						|| mbrClauseAgree.getIsExtraAgreement() == null
						|| mbrClauseAgree.getIsExtraAgreement().length() <= 0 // 이용약관 동의 여부
				) {
					throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
				}
			}
		}

		try {

			createUserResponse = this.service.create(createUserRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}
		return createUserResponse;

	}

	/*
	 * <pre> ID/EMAIL 존재여부 확인하는 기능을 제공한다. </pre>
	 * 
	 * @param CheckDuplicationRequest ID/EMAIL 존재여부 확인 요청 Value Object
	 * 
	 * @return CheckDuplicationResponse - ID/EMAIL 존재여부 확인 응답 Value Object
	 * 
	 * @see com.skplanet.storeplatform.member.client.user.sci.UserSCI#checkDuplication
	 * (com.skplanet.storeplatform.member. client. user.sci.vo.CheckDuplicationRequest)
	 */
	@Override
	public CheckDuplicationResponse checkDuplication(CheckDuplicationRequest checkDuplicationRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("사용자 컨트롤러 - ID/EMAIL 존재여부 확인");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### checkDuplication 찍음 데이터 : {}", checkDuplicationRequest);

		CheckDuplicationResponse checkDuplicationResponse;

		// 입력 파라미터가 없음
		if (checkDuplicationRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// 공통 파라미터 없음
		if (checkDuplicationRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 필수 파라미터, keySearchList
		if (checkDuplicationRequest.getKeySearchList() == null
				|| checkDuplicationRequest.getKeySearchList().size() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// 필수 파라미터 in List, keySearchList
		List<KeySearch> keySearchList = checkDuplicationRequest.getKeySearchList();
		for (KeySearch keySearch : keySearchList) {

			// 필수 파라미터
			if (keySearch.getKeyType() == null || keySearch.getKeyType().length() <= 0 // keyType
					|| keySearch.getKeyString() == null || keySearch.getKeyString().length() <= 0 // keyString
			) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			}

			// 잘못된 키값
			if (!keySearch.getKeyType().equalsIgnoreCase(Constant.SEARCH_TYPE_USER_ID)
					&& !keySearch.getKeyType().equalsIgnoreCase(Constant.SEARCH_TYPE_USER_KEY)
					&& !keySearch.getKeyType().equalsIgnoreCase(Constant.SEARCH_TYPE_ONEID_KEY)
					&& !keySearch.getKeyType().equalsIgnoreCase(Constant.SEARCH_TYPE_IDP_KEY)
					&& !keySearch.getKeyType().equalsIgnoreCase(Constant.SEARCH_TYPE_DEVICE_KEY)
					&& !keySearch.getKeyType().equalsIgnoreCase(Constant.SEARCH_TYPE_DEVICE_ID)
					&& !keySearch.getKeyType().equalsIgnoreCase(Constant.SEARCH_TYPE_EMAIL)
					&& !keySearch.getKeyType().equalsIgnoreCase(Constant.SEARCH_TYPE_MDN)
                    && !keySearch.getKeyType().equalsIgnoreCase(Constant.SEARCH_TYPE_SVC_MANG_NO)) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.wrongKeyType", ""));
			}
		}

		try {

			checkDuplicationResponse = this.service.checkDuplication(checkDuplicationRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}

		return checkDuplicationResponse;

	}

	/*
	 * <pre> ID와 비밀번호 또는 device ID로 로그인하는 기능을 제공한다. OneID에서 로그인을 관리할 경우, 로그인개수와 성공여부를 등록한다. </pre>
	 * 
	 * @param LogInUserRequest 로그인 요청 Value Object
	 * 
	 * @return LogInUserResponse - 로그인 응답 Value Object
	 * 
	 * @see com.skplanet.storeplatform.member.client.user.sci.UserSCI#loginUser(com
	 * .skplanet.storeplatform.member.client. user.sci.vo.loginUserRequest)
	 */
	@Override
	public LoginUserResponse updateLoginUser(LoginUserRequest loginUserRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("사용자 컨트롤러 - 로그인");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### 받은 데이터 loginUser : {}", loginUserRequest);

		// TLog
		new TLogUtil().set(new ShuttleSetter() {
			@Override
			public void customize(TLogSentinelShuttle shuttle) {
				shuttle.log_id("TL_SC_MEM_0001");
			}
		});

		LoginUserResponse loginUserResponse = null;

		// 입력 파라미터가 없음
		if (loginUserRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (loginUserRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 필수 파라미터, userID, isMobile
		if (loginUserRequest.getUserID() == null || loginUserRequest.getUserID().length() <= 0 // userID
				|| loginUserRequest.getIsMobile() == null || loginUserRequest.getIsMobile().length() <= 0 // isMobile
		) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// 입력값 제한, isMobile : Y/N
		if (!loginUserRequest.getIsMobile().equals(Constant.TYPE_YN_Y)
				&& !loginUserRequest.getIsMobile().equals(Constant.TYPE_YN_N)) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// 값이 존재하는 경우에만 체크
		if (loginUserRequest.getIsAutoLogin() != null && loginUserRequest.getIsAutoLogin().length() > 0) {
			// 입력값 제한, isAutoLogin : Y/N
			if (!loginUserRequest.getIsAutoLogin().equals(Constant.TYPE_YN_Y)
					&& !loginUserRequest.getIsAutoLogin().equals(Constant.TYPE_YN_N)) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			}
		}

		// TLog
		final String tlogIP = loginUserRequest.getIpAddress();
		final String tlogSystemID = loginUserRequest.getCommonRequest().getSystemID();
		new TLogUtil().set(new ShuttleSetter() {
			@Override
			public void customize(TLogSentinelShuttle shuttle) {
				shuttle.system_id(tlogSystemID).device_ip(tlogIP);
			}
		});

		try {

			loginUserResponse = this.service.updateLoginUser(loginUserRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}

		return loginUserResponse;
	}

	/*
	 * <pre> 사용자를 탈퇴하는 기능을 제공한다. </pre>
	 * 
	 * @param RemoveUserRequest 사용자 탈퇴 요청 Value Object
	 * 
	 * @return RemoveUserResponse - 사용자 탈퇴 응답 Value Object
	 * 
	 * @see com.skplanet.storeplatform.member.client.user.sci.UserSCI#remove(com. skplanet.storeplatform.member.client.
	 * user.sci.vo.RemoveUserRequest)
	 */
	@Override
	public RemoveUserResponse remove(RemoveUserRequest removeUserRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("사용자 컨트롤러 - 회원 탈퇴");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### 받은 데이터 removeUserRequest : {}", removeUserRequest);

		RemoveUserResponse removeUserResponse = null;

		// TLog
		new TLogUtil().set(new ShuttleSetter() {
			@Override
			public void customize(TLogSentinelShuttle shuttle) {
				shuttle.log_id("TL_SC_MEM_0007");
			}
		});

		// 입력 파라미터가 없음
		if (removeUserRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (removeUserRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 사용자키 없음
		if (removeUserRequest.getUserKey() == null || removeUserRequest.getUserKey().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.userKeyNotFound", ""));
		}

		// TLog
		final String insdusermbrno = removeUserRequest.getUserKey();

		new TLogUtil().set(new ShuttleSetter() {
			@Override
			public void customize(TLogSentinelShuttle shuttle) {
				shuttle.insd_usermbr_no(insdusermbrno);
			}
		});

		try {

			removeUserResponse = this.service.remove(removeUserRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}

		return removeUserResponse;
	}

	/**
	 * <pre>
	 * 회원 정보를 delete하는 기능을 제공한다.
	 * 정상회원가입후 휴대기기 등록 오류 발생시 롤백개념으로 사용한다.
	 * </pre>
	 *
	 * @param deleteUserRequest 회원 탈퇴 요청 Value Object
	 * @return DeleteUserResponse - 회원 탈퇴 응답 Value Object
	 */
	@Override
	public DeleteUserResponse delete(DeleteUserRequest deleteUserRequest) {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("사용자 컨트롤러 - 회원정보 delete");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### 받은 데이터 deleteUserRequest : {}", deleteUserRequest);

		// 입력 파라미터가 없음
		if (deleteUserRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (deleteUserRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 사용자키 없음
		if (StringUtils.isBlank(deleteUserRequest.getUserKey())) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.userKeyNotFound", ""));
		}

		DeleteUserResponse deleteUserResponse = null;
		try {
			deleteUserResponse = this.service.delete(deleteUserRequest);
		} catch (StorePlatformException ex) {
			throw ex;
		}

		return deleteUserResponse;
	}
	/**
	 * <pre>
	 * 사용자 약관동의 등록/수정.
	 * </pre>
	 * 
	 * @param updateAgreementRequest
	 *            사용자 약관동의 등록/수정 요청 Value Object
	 * @return UpdateAgreementResponse - 사용자 약관동의 등록/수정 응답 Value Object
	 */
	@Override
	public UpdateAgreementResponse updateAgreement(UpdateAgreementRequest updateAgreementRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("사용자 컨트롤러 - 약관동의 등록/수정");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### 받은 데이터 updateAgreementRequest : {}", updateAgreementRequest);

		UpdateAgreementResponse updateAgreementResponse = null;

		// 입력 파라미터가 없음
		if (updateAgreementRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (updateAgreementRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (updateAgreementRequest.getCommonRequest().getTenantID() == null
				|| updateAgreementRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터 없음, userKey
		if (updateAgreementRequest.getUserKey() == null || updateAgreementRequest.getUserKey().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// 수정 또는 추가할 약관 없음
		if (updateAgreementRequest.getMbrClauseAgreeList() == null
				|| updateAgreementRequest.getMbrClauseAgreeList().size() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.editInputItemNotFound", ""));
		}

		// 필수 파라미터 없음, mbrClauseAgreeList
		List<MbrClauseAgree> mbrClauseAgreeList = updateAgreementRequest.getMbrClauseAgreeList();
		for (MbrClauseAgree mbrClauseAgree : mbrClauseAgreeList) {
			if (mbrClauseAgree.getExtraAgreementID() == null || mbrClauseAgree.getExtraAgreementID().length() <= 0
					|| mbrClauseAgree.getIsExtraAgreement() == null
					|| mbrClauseAgree.getIsExtraAgreement().length() <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			}
		}

		try {

			updateAgreementResponse = this.service.updateAgreement(updateAgreementRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }

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
		LOGGER.debug("컨트롤러 성인인증 정보 수정");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		UpdateRealNameResponse updateRealNameResponse = null;

		// 입력 파라미터가 없음
		if (updateRealNameRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (updateRealNameRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 사용자키 없음
		if (updateRealNameRequest.getUserKey() == null || updateRealNameRequest.getUserKey().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.userKeyNotFound", ""));
		}

		// 필수 파라미터 없음, isOwn
		if (updateRealNameRequest.getIsOwn() == null || updateRealNameRequest.getIsOwn().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// isOwn : OWN or PARENT
		if (!updateRealNameRequest.getIsOwn().equalsIgnoreCase(Constant.REAL_NAME_TYPE_OWN)
				&& !updateRealNameRequest.getIsOwn().equalsIgnoreCase(Constant.REAL_NAME_TYPE_PARANT)) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// 실명인증 대상이 본인인 경우
		if (updateRealNameRequest.getIsOwn().equalsIgnoreCase(Constant.REAL_NAME_TYPE_OWN)) {
			// TLog
			new TLogUtil().set(new ShuttleSetter() {
				@Override
				public void customize(TLogSentinelShuttle shuttle) {
					shuttle.log_id("TL_SC_MEM_0002");
				}
			});

			// 실명인증 데이터
			if (updateRealNameRequest.getMbrAuth() == null) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			}

			// 필수 파라미터 없음, ci
			if (updateRealNameRequest.getMbrAuth().getCi() == null
					|| updateRealNameRequest.getMbrAuth().getCi().length() <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			}

			// 필수 파라미터 > mbrAuth > isDomestic
			if (updateRealNameRequest.getMbrAuth().getIsDomestic() != null) {
				if (!updateRealNameRequest.getMbrAuth().getIsDomestic().equals(Constant.TYPE_YN_Y)
						&& !updateRealNameRequest.getMbrAuth().getIsDomestic().equals(Constant.TYPE_YN_N)
						&& !updateRealNameRequest.getMbrAuth().getIsDomestic().equals(" ")) {
					throw new StorePlatformException(this.getMessage("response.ResultCode.wrongKeyType", ""));
				}
			}

			// 수정 또는 추가할 항목이 없음
			if (updateRealNameRequest.getMbrAuth() == null || this.allFieldIsNull(updateRealNameRequest.getMbrAuth())) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.editInputItemNotFound", ""));
			}

			// TLog
			final String tlogUserKey = updateRealNameRequest.getUserKey();
			final String tlogSystemID = updateRealNameRequest.getCommonRequest().getSystemID();
			final String tlogMNO = updateRealNameRequest.getMbrAuth().getTelecom();

			new TLogUtil().set(new ShuttleSetter() {
				@Override
				public void customize(TLogSentinelShuttle shuttle) {
					shuttle.insd_usermbr_no(tlogUserKey).system_id(tlogSystemID).mno_type(tlogMNO);
				}
			});

		}

		// 실명인증 대상이 법정대리인(부모)인 경우
		if (updateRealNameRequest.getIsOwn().equalsIgnoreCase(Constant.REAL_NAME_TYPE_PARANT)) {

			// 실명인증 데이터
			if (updateRealNameRequest.getMbrLglAgent() == null) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			}

			// 필수 파라미터 없음, parentName(법정대리인 이름)
			if (updateRealNameRequest.getMbrLglAgent().getParentName() == null
					|| updateRealNameRequest.getMbrLglAgent().getParentName().length() <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			}

			// 필수 파라미터 > mbrLglAgent > isDomestic
			if (updateRealNameRequest.getMbrLglAgent().getIsDomestic() != null) {
				if (!updateRealNameRequest.getMbrLglAgent().getIsDomestic().equals(Constant.TYPE_YN_Y)
						&& !updateRealNameRequest.getMbrLglAgent().getIsDomestic().equals(Constant.TYPE_YN_N)) {
					throw new StorePlatformException(this.getMessage("response.ResultCode.wrongKeyType", ""));
				}
			}

			// 수정 또는 추가할 항목이 없음
			if (updateRealNameRequest.getMbrLglAgent() == null
					|| this.allFieldIsNull(updateRealNameRequest.getMbrLglAgent())) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.editInputItemNotFound", ""));
			}

		}

		try {

			updateRealNameResponse = this.service.updateRealName(updateRealNameRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}

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
		LOGGER.debug("사용자 컨트롤러 - 사용자회원 기본정보 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### 받은 데이터 searchUserRequest : {}", searchUserRequest);

		SearchUserResponse searchUserResponse = null;

		// 입력 파라미터가 없음
		if (searchUserRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (searchUserRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 필수 파라미터, keySearchList
		if (searchUserRequest.getKeySearchList() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// 필수 파라미터 in List, keySearch
		List<KeySearch> keySearchList = searchUserRequest.getKeySearchList();
		for (KeySearch keySearch : keySearchList) {

			// 필수 파라미터
			if (keySearch.getKeyType() == null || keySearch.getKeyType().length() <= 0
					|| keySearch.getKeyString() == null || keySearch.getKeyString().length() <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			}

			// 잘못된 키값
			if (!keySearch.getKeyType().equalsIgnoreCase(Constant.SEARCH_TYPE_USER_KEY)
					&& !keySearch.getKeyType().equalsIgnoreCase(Constant.SEARCH_TYPE_USER_ID)
					&& !keySearch.getKeyType().equalsIgnoreCase(Constant.SEARCH_TYPE_DEVICE_KEY)
					&& !keySearch.getKeyType().equalsIgnoreCase(Constant.SEARCH_TYPE_DEVICE_ID)
					&& !keySearch.getKeyType().equalsIgnoreCase(Constant.SEARCH_TYPE_MDN)) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.wrongKeyType", ""));
			}
		}

		try {

			searchUserResponse = this.service.searchUser(searchUserRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}

		return searchUserResponse;

	}

	/**
	 * <pre>
	 * 회원 상태정보 변경 서비스.
	 * </pre>
	 * 
	 * @param updateStatusUserRequest
	 *            회원 상태정보 변경 요청 Value Object
	 * @return UpdateStatusUserResponse - 회원 상태정보 변경 응답 Value Object
	 */
	@Override
	public UpdateStatusUserResponse updateStatus(UpdateStatusUserRequest updateStatusUserRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("사용자 컨트롤러 - 회원 상태정보 변경");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### 받은 데이터 updateStatusUserRequest : {}", updateStatusUserRequest);

		UpdateStatusUserResponse updateStatusUserResponse = null;

		// 입력 파라미터가 없음
		if (updateStatusUserRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (updateStatusUserRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 필수 파라미터, keySearchList
		if (updateStatusUserRequest.getKeySearchList() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// 필수 파라미터 in List, keySearch
		List<KeySearch> keySearchList = updateStatusUserRequest.getKeySearchList();
		for (KeySearch keySearch : keySearchList) {

			// 필수 파라미터
			if (keySearch.getKeyType() == null || keySearch.getKeyType().length() <= 0
					|| keySearch.getKeyString() == null || keySearch.getKeyString().length() <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			}

			// 잘못된 키값
			if (!keySearch.getKeyType().equalsIgnoreCase(Constant.SEARCH_TYPE_USER_KEY)
					&& !keySearch.getKeyType().equalsIgnoreCase(Constant.SEARCH_TYPE_USER_ID)
					&& !keySearch.getKeyType().equalsIgnoreCase(Constant.SEARCH_TYPE_IDP_KEY)
					&& !keySearch.getKeyType().equalsIgnoreCase(Constant.SEARCH_TYPE_ONEID_KEY)
					&& !keySearch.getKeyType().equalsIgnoreCase(Constant.SEARCH_TYPE_DEVICE_KEY)
					&& !keySearch.getKeyType().equalsIgnoreCase(Constant.SEARCH_TYPE_DEVICE_ID)
					&& !keySearch.getKeyType().equalsIgnoreCase(Constant.SEARCH_TYPE_MDN)) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.wrongKeyType", ""));
			}
		}

		// 수정 또는 추가할 상태 없음, userMainStatus, userSubStatus, loginStatusCode, stopStatusCode
		if ((updateStatusUserRequest.getUserMainStatus() == null || updateStatusUserRequest.getUserMainStatus()
				.length() <= 0) // userMainStatus
				&& (updateStatusUserRequest.getUserSubStatus() == null || updateStatusUserRequest.getUserSubStatus()
						.length() <= 0) // userSubStatus
				&& (updateStatusUserRequest.getLoginStatusCode() == null || updateStatusUserRequest
						.getLoginStatusCode().length() <= 0) // loginStatusCode
				&& (updateStatusUserRequest.getStopStatusCode() == null || updateStatusUserRequest.getStopStatusCode()
						.length() <= 0) // stopStatusCode
		) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.editInputItemNotFound", ""));
		}

		try {

			updateStatusUserResponse = this.service.updateStatus(updateStatusUserRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}

		return updateStatusUserResponse;

	}

	/**
	 * <pre>
	 * 모든 필드가 값이 없는지 여부 반환.
	 * 모두 널인 경우 true
	 * </pre>
	 * 
	 * @param obj
	 *            체크할 객체
	 * @return boolean
	 */
	private boolean allFieldIsNull(Object obj) {

		Field[] fields = obj.getClass().getDeclaredFields();

		for (Field field : fields) {

			try {
				field.setAccessible(true);
				String value;
				try {
					value = (String) field.get(obj);
				} catch (ClassCastException e) {
					value = "";
				}
				// 값이 있으면
				if (value != null && !value.equals(""))
					return false;

			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

		}

		// 모든 필드가 널이거나 ""인 경우
		return true;
	}

	/**
	 * <pre>
	 *  메시지 프로퍼티에서 메시지 참조.
	 * </pre>
	 * 
	 * @param code
	 *            코드값
	 * @param fail
	 *            실패할 경우 메시지
	 * @return String 코드값에 해당하는 메시지
	 */
	private String getMessage(String code, String fail) {
		String msg = this.messageSourceAccessor.getMessage(code, null, fail, LocaleContextHolder.getLocale());
		LOGGER.debug(msg);
		return msg;
	}

	/**
	 * <pre>
	 * 회원 정보 변경 서비스.
	 * </pre>
	 * 
	 * @param updateUserRequest
	 *            회원 정보 변경 요청 Value Object
	 * 
	 * @return UpdateUserResponse - 회원 정보 변경 응답 Value Object
	 */
	@Override
	public UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("사용자 컨트롤러 - 회원 정보 변경");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### 받은 데이터 updateUserRequest : {}", updateUserRequest);

		UpdateUserResponse updateUserResponse = null;

		// 입력 파라미터가 없음
		if (updateUserRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (updateUserRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		// if (updateUserRequest.getCommonRequest().getTenantID() == null
		// || updateUserRequest.getCommonRequest().getTenantID().length() <= 0) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		// }

		// 사용자키 없음
		if (updateUserRequest.getUserMbr().getUserKey() == null
				|| updateUserRequest.getUserMbr().getUserKey().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.userKeyNotFound", ""));
		}

		// 존재하는 경우 필수
		if (updateUserRequest.getMbrLglAgent() != null) {

			// 2014.03.14 필수로 변경됨.
			// 필수 파라미터, parentName(법정대리인 이름)
			if (updateUserRequest.getMbrLglAgent().getParentName() == null
					|| updateUserRequest.getMbrLglAgent().getParentName().length() <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			}

			// 수정 또는 추가할 항목이 없음
			if (updateUserRequest.getMbrLglAgent() == null || this.allFieldIsNull(updateUserRequest.getMbrLglAgent())) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.editInputItemNotFound", ""));

			}

			// 필수 파라미터 > mbrLglAgent > isDomestic
			if (updateUserRequest.getMbrLglAgent().getIsDomestic() != null) {
				if (!updateUserRequest.getMbrLglAgent().getIsDomestic().equals(Constant.TYPE_YN_Y)
						&& !updateUserRequest.getMbrLglAgent().getIsDomestic().equals(Constant.TYPE_YN_N)) {
					throw new StorePlatformException(this.getMessage("response.ResultCode.wrongKeyType", ""));
				}
			}

		}

		try {

			updateUserResponse = this.service.updateUser(updateUserRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}

		return updateUserResponse;
	}

	/**
	 * <pre>
	 * 사용자 회원 비밀번호 변경.
	 * </pre>
	 * 
	 * @param updatePasswordUserRequest
	 *            사용자회원 비밀번호 변경 요청 Value Object
	 * @return UpdatePasswordUserResponse - 사용자회원 비밀번호 변경 응답 Value Object
	 * @deprecated
	 */
	@Override
	public UpdatePasswordUserResponse updatePasswordUser(UpdatePasswordUserRequest updatePasswordUserRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("사용자 컨트롤러 - 사용자 회원 비밀번호 변경");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		UpdatePasswordUserResponse updatePasswordUserResponse = null;

		// 입력 파라미터가 없음
		if (updatePasswordUserRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (updatePasswordUserRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 필수 파라미터, pwRegDate
		if (updatePasswordUserRequest.getMbrPwd().getPwRegDate() == null
				|| updatePasswordUserRequest.getMbrPwd().getPwRegDate().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// 필수 파라미터, userKey
		if (updatePasswordUserRequest.getMbrPwd().getMemberKey() == null
				|| updatePasswordUserRequest.getMbrPwd().getMemberKey().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// 필수 파라미터, oldPW
		if (updatePasswordUserRequest.getMbrPwd().getOldPW() == null
				|| updatePasswordUserRequest.getMbrPwd().getOldPW().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// 필수 파라미터, memberPW
		if (updatePasswordUserRequest.getMbrPwd().getMemberPW() == null
				|| updatePasswordUserRequest.getMbrPwd().getMemberPW().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {

			updatePasswordUserResponse = this.service.updatePasswordUser(updatePasswordUserRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}

		return updatePasswordUserResponse;

	}

	/**
	 * <pre>
	 * 회원 비밀번호를 변경하는 기능을 제공한다.
	 * </pre>
	 *
	 * @param modifyUserPwdRequest
	 *            비밀번호 변경 Value Object
	 * @return ModifyUserPwdResponse - 비밀번호 변경 응답 Value Object
	 */
	public ModifyUserPwdResponse modifyUserPwd(ModifyUserPwdRequest modifyUserPwdRequest){

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("사용자 컨트롤러 - 사용자 회원 비밀번호 변경");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		ModifyUserPwdResponse modifyUserPwdResponse = null;

		// 입력 파라미터가 없음
		if (modifyUserPwdRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (modifyUserPwdRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 필수 파라미터, userKey
		if (modifyUserPwdRequest.getUserKey() == null || modifyUserPwdRequest.getUserKey().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// 필수 파라미터, oldPassWord
		if (modifyUserPwdRequest.getOldPassword() == null || modifyUserPwdRequest.getOldPassword().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// 필수 파라미터, newPassWord
		if (modifyUserPwdRequest.getNewPassword() == null || modifyUserPwdRequest.getNewPassword().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {

			modifyUserPwdResponse = this.service.modifyUserPwd(modifyUserPwdRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}

		return modifyUserPwdResponse;

	}

	/**
	 * <pre>
	 * 사용자 회원 비밀번호 초기화.
	 * </pre>
	 * 
	 * @param resetPasswordUserRequest
	 *            사용자 회원 비밀번호 초기화 요청 value object
	 * @return ResetPasswordUserResponse - 사용자 회원 비밀번호 초기화 응답 value object
	 */
	@Override
	public ResetPasswordUserResponse updateResetPasswordUser(ResetPasswordUserRequest resetPasswordUserRequest) {

		ResetPasswordUserResponse resetPasswordUserResponse = null;

		// 입력 파라미터가 없음
		if (resetPasswordUserRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (resetPasswordUserRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		try {

			resetPasswordUserResponse = this.service.updateResetPasswordUser(resetPasswordUserRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}

		return resetPasswordUserResponse;

	}

	/**
	 * <pre>
	 * 사용자 부가정보 목록 조회.
	 * </pre>
	 * 
	 * @param searchManagementListRequest
	 *            사용자 부가정보 목록 조회 요청 Value Object
	 * @return SearchManagementListResponse - 사용자 부가정보 목록 조회 응답 Value Object
	 */
	@Override
    @Deprecated
	public SearchManagementListResponse searchManagementList(SearchManagementListRequest searchManagementListRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("컨트롤러 - 사용자 부가정보 목록 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchManagementListResponse searchManagementListResponse;

		// 입력 파라미터가 없음
		if (searchManagementListRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (searchManagementListRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (searchManagementListRequest.getCommonRequest().getTenantID() == null
				|| searchManagementListRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 사용자키 없음
		if (searchManagementListRequest.getUserKey() == null || searchManagementListRequest.getUserKey().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.userKeyNotFound", ""));
		}

		try {

			searchManagementListResponse = this.service.searchManagementList(searchManagementListRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}

		return searchManagementListResponse;

	}

	/**
	 * <pre>
	 * 사용자 약관동의 목록 조회.
	 * </pre>
	 * 
	 * @param searchAgreementListRequest
	 *            사용자 약관동의 목록 요청 Value Object
	 * @return SearchAgreementListResponse - 사용자 약관동의 목록 응답 Value Object
	 */
	@Override
    @Deprecated
	public SearchAgreementListResponse searchAgreementList(SearchAgreementListRequest searchAgreementListRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("컨트롤러 - 사용자 약관동의 목록 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchAgreementListResponse searchAgreementListResponse = null;

		// 입력 파라미터가 없음
		if (searchAgreementListRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (searchAgreementListRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (searchAgreementListRequest.getCommonRequest().getTenantID() == null
				|| searchAgreementListRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 사용자키 없음
		if (searchAgreementListRequest.getUserKey() == null || searchAgreementListRequest.getUserKey().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.userKeyNotFound", ""));
		}

		try {

			searchAgreementListResponse = this.service.searchAgreementList(searchAgreementListRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }

		return searchAgreementListResponse;

	}

	/**
	 * <pre>
	 * 사용자 부가정보 등록/수정.
	 * </pre>
	 * 
	 * @param updateManagementRequest
	 *            사용자 부가정보 및 관리항목 정보수정 요청 Value Object
	 * @return UpdateManagementResponse - 사용자 부가정보 및 관리항목 정보수정 응답 Value Object
	 */
	@Override
    @Deprecated
	public UpdateManagementResponse updateManagement(UpdateManagementRequest updateManagementRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("컨트롤러 - 사용자 부가정보 등록/수정");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		UpdateManagementResponse updateManagementResponse = null;

		// 입력 파라미터가 없음
		if (updateManagementRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (updateManagementRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (updateManagementRequest.getCommonRequest().getTenantID() == null
				|| updateManagementRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 사용자키 없음
		if (updateManagementRequest.getUserKey() == null || updateManagementRequest.getUserKey().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.userKeyNotFound", ""));
		}

		// 필수 파라미터, mbrMangItemPtcr
		if (updateManagementRequest.getMbrMangItemPtcr() == null
				|| updateManagementRequest.getMbrMangItemPtcr().size() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// 필수 파라미터 in List, mbrMangItemPtcr
		List<MbrMangItemPtcr> mbrMangItemPtcrList = updateManagementRequest.getMbrMangItemPtcr();
		for (MbrMangItemPtcr mbrMangItemPtcr : mbrMangItemPtcrList) {
			if (mbrMangItemPtcr.getExtraProfile() == null || mbrMangItemPtcr.getExtraProfile().length() <= 0 // 관리항목 코드
					|| mbrMangItemPtcr.getExtraProfileValue() == null
			// || mbrMangItemPtcr.getExtraProfileValue().length() <= 0 // 관리항목 값
			) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			}
		}

		try {

			updateManagementResponse = this.service.updateManagement(updateManagementRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }

		return updateManagementResponse;
	}

	/**
	 * <pre>
	 * 사용자 부가정보 삭제하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param removeManagementRequest
	 *            사용자 부가정보 삭제 요청 Value Object
	 * @return removeManagementResponse - 사용자 부가정보 삭제 응답 Value Object
	 */
	@Override
    @Deprecated
	public RemoveManagementResponse removeManagement(RemoveManagementRequest removeManagementRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("컨트롤러 - 사용자 부가정보 삭제");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		RemoveManagementResponse removeManagementResponse = null;

		// 입력 파라미터가 없음
		if (removeManagementRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (removeManagementRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (removeManagementRequest.getCommonRequest().getTenantID() == null
				|| removeManagementRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 사용자키 없음
		if (removeManagementRequest.getUserKey() == null || removeManagementRequest.getUserKey().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.userKeyNotFound", ""));
		}

		// 필수 파라미터, mbrMangItemPtcr
		if (removeManagementRequest.getMbrMangItemPtcr() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// 필수 파라미터 in List, mbrMangItemPtcr > extraProfile(관리항목 코드)
		List<MbrMangItemPtcr> mbrMangItemPtcrList = removeManagementRequest.getMbrMangItemPtcr();
		for (MbrMangItemPtcr mbrMangItemPtcr : mbrMangItemPtcrList) {
			if (mbrMangItemPtcr.getExtraProfile() == null || mbrMangItemPtcr.getExtraProfile().length() <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			}
		}

		try {

			removeManagementResponse = this.service.removeManagement(removeManagementRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }

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
		LOGGER.debug("컨트롤러 - 제한 정책 목록을 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchPolicyResponse searchPolicyResponse = null;

		// 입력 파라미터가 없음
		if (searchPolicyRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (searchPolicyRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (searchPolicyRequest.getCommonRequest().getTenantID() == null
				|| searchPolicyRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터
		if (searchPolicyRequest.getLimitPolicyCodeList() == null
				&& searchPolicyRequest.getLimitPolicyCodeList().size() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// 필수 파라미터
		if (searchPolicyRequest.getLimitPolicyKey() == null && searchPolicyRequest.getLimitPolicyKey().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {

			searchPolicyResponse = this.service.searchPolicyList(searchPolicyRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }

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
		LOGGER.debug("컨트롤러 - 제한 정책 등록/수정");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		UpdatePolicyResponse updatePolicyResponse = null;

		// 입력 파라미터가 없음
		if (updatePolicyRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (updatePolicyRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (updatePolicyRequest.getCommonRequest().getTenantID() == null
				|| updatePolicyRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터 1
		if (updatePolicyRequest.getLimitTargetList() == null || updatePolicyRequest.getLimitTargetList().size() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		for (LimitTarget limitTarget : updatePolicyRequest.getLimitTargetList()) {
			if (limitTarget.getLimitPolicyCode() == null || limitTarget.getLimitPolicyCode().length() <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			}
			if (limitTarget.getRegID() == null || limitTarget.getRegID().length() <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			}
			if (limitTarget.getLimitPolicyKey() == null || limitTarget.getLimitPolicyKey().length() <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			}

		}

		try {

			updatePolicyResponse = this.service.updatePolicy(updatePolicyRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }

		return updatePolicyResponse;

	}

	/**
	 * <pre>
	 * 제한 정책정보 이력을 수정하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updatePolicyRequest
	 *            UpdatePolicyRequest
	 * 
	 * @return updatePolicyResponse - UpdatePolicyResponse
	 */
	@Override
	public UpdatePolicyResponse updatePolicyHistory(UpdatePolicyRequest updatePolicyRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("컨트롤러 - 제한 정책정보 이력을 수정");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		UpdatePolicyResponse updatePolicyResponse = null;

		// 입력 파라미터가 없음
		if (updatePolicyRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (updatePolicyRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (updatePolicyRequest.getCommonRequest().getTenantID() == null
				|| updatePolicyRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터 1
		if (updatePolicyRequest.getLimitTargetList() == null || updatePolicyRequest.getLimitTargetList().size() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		for (LimitTarget limitTarget : updatePolicyRequest.getLimitTargetList()) {
			if (limitTarget.getLimitPolicyCode() == null || limitTarget.getLimitPolicyCode().length() <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			}
			if (limitTarget.getRegID() == null || limitTarget.getRegID().length() <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			}
			if (limitTarget.getLimitPolicyKey() == null || limitTarget.getLimitPolicyKey().length() <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			}

		}

		try {

			updatePolicyResponse = this.service.updatePolicyHistory(updatePolicyRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }

		return updatePolicyResponse;

	}

	/**
	 * <pre>
	 * 제한 정책정보를 저장하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updatePolicyRequest
	 *            UpdatePolicyRequest
	 * 
	 * @return updatePolicyResponse - UpdatePolicyResponse
	 */
	@Override
	public UpdatePolicyResponse insertPolicy(UpdatePolicyRequest updatePolicyRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("컨트롤러 - 제한 정책정보를 저장");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		UpdatePolicyResponse updatePolicyResponse = null;

		// 입력 파라미터가 없음
		if (updatePolicyRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (updatePolicyRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (updatePolicyRequest.getCommonRequest().getTenantID() == null
				|| updatePolicyRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터 1
		if (updatePolicyRequest.getLimitTargetList() == null || updatePolicyRequest.getLimitTargetList().size() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		for (LimitTarget limitTarget : updatePolicyRequest.getLimitTargetList()) {
			if (limitTarget.getLimitPolicyCode() == null || limitTarget.getLimitPolicyCode().length() <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			}
			if (limitTarget.getRegID() == null || limitTarget.getRegID().length() <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			}
			if (limitTarget.getLimitPolicyKey() == null || limitTarget.getLimitPolicyKey().length() <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			}

		}

		try {

			updatePolicyResponse = this.service.insertPolicy(updatePolicyRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }

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
		LOGGER.debug("컨트롤러 - 제한 정책 삭제");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		RemovePolicyResponse removePolicyResponse = null;

		// 입력 파라미터가 없음
		if (removePolicyRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (removePolicyRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (removePolicyRequest.getCommonRequest().getTenantID() == null
				|| removePolicyRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터 1
		if (removePolicyRequest.getLimitTargetList() == null || removePolicyRequest.getLimitTargetList().size() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		for (LimitTarget limitTarget : removePolicyRequest.getLimitTargetList()) {
			if (limitTarget.getLimitPolicyCode() == null || limitTarget.getLimitPolicyCode().length() <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			}
			if (limitTarget.getLimitPolicyKey() == null || limitTarget.getLimitPolicyKey().length() <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			}

		}

		try {

			removePolicyResponse = this.service.removePolicy(removePolicyRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }

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
	public SearchMemberPointResponse searchMemberPointList(SearchMemberPointRequest searchMemberPointRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("컨트롤러 - OCB 목록을 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchMemberPointResponse searchMemberPointResponse = null;

		// 입력 파라미터가 없음
		if (searchMemberPointRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (searchMemberPointRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 필수 파라미터
		if (searchMemberPointRequest.getUserKey() == null && searchMemberPointRequest.getUserKey().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {

			searchMemberPointResponse = this.service.searchOCBList(searchMemberPointRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}

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
	public UpdateMemberPointResponse updateMemberPoint(UpdateMemberPointRequest updateMemberPointRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("컨트롤러 - OCB 등록/수정");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		UpdateMemberPointResponse updateMemberPointResponse = null;

		// 입력 파라미터가 없음
		if (updateMemberPointRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (updateMemberPointRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (updateMemberPointRequest.getCommonRequest().getTenantID() == null
				|| updateMemberPointRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터 1
		if (updateMemberPointRequest.getMemberPoint() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}
		if (updateMemberPointRequest.getMemberPoint().getUserKey() == null
				|| updateMemberPointRequest.getMemberPoint().getCardNumber() == null
				|| updateMemberPointRequest.getMemberPoint().getStartDate() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {

			updateMemberPointResponse = this.service.updateOCB(updateMemberPointRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }

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
	public RemoveMemberPointResponse removeMemberPoint(RemoveMemberPointRequest removeMemberPointRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("컨트롤러 - OCB 삭제");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		RemoveMemberPointResponse removeMemberPointResponse = null;

		// 입력 파라미터가 없음
		if (removeMemberPointRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (removeMemberPointRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (removeMemberPointRequest.getCommonRequest().getTenantID() == null
				|| removeMemberPointRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터
		if (removeMemberPointRequest.getMemberPointList() == null
				|| removeMemberPointRequest.getMemberPointList().size() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// 필수 파라미터
		if (removeMemberPointRequest.getMemberPointList() != null) {
			List<MemberPoint> memberPointList = removeMemberPointRequest.getMemberPointList();
			for (MemberPoint memberPoint : memberPointList) {
				if (memberPoint.getUserKey() == null || memberPoint.getUserKey().length() <= 0 // userkey
						|| memberPoint.getCardNumber() == null || memberPoint.getCardNumber().length() <= 0 // card
				) {
					throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
				}
			}
		} else {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {

			removeMemberPointResponse = this.service.removeOCB(removeMemberPointRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }

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
		LOGGER.debug("컨트롤러 - 사용자 부가정보 목록 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchAgreeSiteResponse searchAgreeSiteResponse = null;

		// 입력 파라미터가 없음
		if (searchAgreeSiteRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (searchAgreeSiteRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (searchAgreeSiteRequest.getCommonRequest().getTenantID() == null
				|| searchAgreeSiteRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 사용자키 없음
		if (searchAgreeSiteRequest.getImSvcNo() == null || searchAgreeSiteRequest.getImSvcNo().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {

			searchAgreeSiteResponse = this.service.searchAgreeSite(searchAgreeSiteRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }

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
		LOGGER.debug("컨트롤러 - 사용자키 변환추적 요청 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchUserkeyTrackResponse searchUserkeyTrackResponse = null;

		// 입력 파라미터가 없음
		if (searchUserkeyTrackRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (searchUserkeyTrackRequest.getDeviceID() == null || searchUserkeyTrackRequest.getDeviceID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {

			searchUserkeyTrackResponse = this.service.searchUserkeyTrack(searchUserkeyTrackRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }

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
		LOGGER.debug("사용자 컨트롤러 - 사용자회원_ONEID 삭제");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### removeMbrOneIDRequest : {}", removeMbrOneIDRequest);

		RemoveMbrOneIDResponse removeMbrOneIDResponse = null;

		// 입력 파라미터가 없음
		if (removeMbrOneIDRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (removeMbrOneIDRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (removeMbrOneIDRequest.getCommonRequest().getTenantID() == null
				|| removeMbrOneIDRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터, imSvcNo
		if (removeMbrOneIDRequest.getImSvcNo() == null || removeMbrOneIDRequest.getImSvcNo().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {

			removeMbrOneIDResponse = this.service.removeMbrOneID(removeMbrOneIDRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }

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
		LOGGER.debug("컨트롤러 - 유통망 추천앱 회원 정보 등록");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		UpdateUserMbrSegmentResponse updateUserMbrSegmentResponse;

		// 입력 파라미터가 없음
		if (updateUserMbrSegmentRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (updateUserMbrSegmentRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (updateUserMbrSegmentRequest.getCommonRequest().getTenantID() == null
				|| updateUserMbrSegmentRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터 1
		if (updateUserMbrSegmentRequest.getUserMbrSegment() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}
		// 필수 파라미터 2
		if (updateUserMbrSegmentRequest.getUserMbrSegment().getSvcMangNum() == null
				|| updateUserMbrSegmentRequest.getUserMbrSegment().getDeviceID() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {
			updateUserMbrSegmentResponse = this.service.updateUserMbrSegment(updateUserMbrSegmentRequest);
		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }

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
		LOGGER.debug("컨트롤러 - 유통망 추천앱 비회원 정보 등록");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		UpdateNonMbrSegmentResponse updateNonMbrSegmentResponse;

		// 입력 파라미터가 없음
		if (updateNonMbrSegmentRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (updateNonMbrSegmentRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (updateNonMbrSegmentRequest.getCommonRequest().getTenantID() == null
				|| updateNonMbrSegmentRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터 1
		if (updateNonMbrSegmentRequest.getNonMbrSegment() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}
		// 필수 파라미터 2
		if (updateNonMbrSegmentRequest.getNonMbrSegment().getSvcMangNum() == null
				|| updateNonMbrSegmentRequest.getNonMbrSegment().getDeviceID() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {
			updateNonMbrSegmentResponse = this.service.updateNonMbrSegment(updateNonMbrSegmentRequest);
		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }

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
    @Deprecated
	public SearchChangedDeviceResponse searchChangedDevice(SearchChangedDeviceRequest searchChangedDeviceRequest) {
		SearchChangedDeviceResponse searchChangedDeviceResponse;

		// 입력 파라미터가 없음
		if (searchChangedDeviceRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (searchChangedDeviceRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 필수 파라미터
		if (searchChangedDeviceRequest.getUserKey() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {
			searchChangedDeviceResponse = this.service.searchChangedDevice(searchChangedDeviceRequest);
		} catch (StorePlatformException ex) {
			throw ex;
		}

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
		CreateChangedDeviceResponse createChangedDeviceResponse;

		// 입력 파라미터가 없음
		if (createChangedDeviceRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (createChangedDeviceRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (createChangedDeviceRequest.getCommonRequest().getTenantID() == null
				|| createChangedDeviceRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터
		if (createChangedDeviceRequest.getChangedDeviceLog() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}
		// 필수 파라미터
		if (createChangedDeviceRequest.getChangedDeviceLog().getUserKey() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {
			createChangedDeviceResponse = this.service.createChangedDevice(createChangedDeviceRequest);
		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }

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
		LOGGER.debug("사용자 컨트롤러 - 이메일 사용자 정보 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### searchUserEmailRequest : {}", searchUserEmailRequest);

		SearchUserEmailResponse searchUserEmailResponse;

		// 입력 파라미터가 없음
		if (searchUserEmailRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (searchUserEmailRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 필수 파라미터, userEmail
		if (searchUserEmailRequest.getUserEmail() == null || searchUserEmailRequest.getUserEmail().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {

			searchUserEmailResponse = this.service.searchUserEmail(searchUserEmailRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}

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
		LOGGER.debug("사용자 컨트롤러 - 단말 OS별 누적 가입자수 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### searchDeviceOSNumberRequest : {}", searchDeviceOSNumberRequest);

		SearchDeviceOSNumberResponse searchDeviceOSNumberResponse;

		// 입력 파라미터가 없음
		if (searchDeviceOSNumberRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// 공통 파라미터 없음
		if (searchDeviceOSNumberRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (searchDeviceOSNumberRequest.getCommonRequest().getTenantID() == null
				|| searchDeviceOSNumberRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		try {

			searchDeviceOSNumberResponse = this.service.searchDeviceOSNumber(searchDeviceOSNumberRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }

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
		LOGGER.debug("사용자 컨트롤러 - 복수 사용자 상태정보 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### searchMbrUserRequest : {}", searchMbrUserRequest);

		SearchMbrUserResponse searchMbrUserResponse;

		// 입력 파라미터가 없음
		if (searchMbrUserRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// 공통 파라미터 없음
		if (searchMbrUserRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		try {

			searchMbrUserResponse = this.service.searchMbrUser(searchMbrUserRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}

		return searchMbrUserResponse;

	}

	@Override
	public SearchMbrSapUserResponse searchMbrSapUser(SearchMbrSapUserRequest searchMbrSapUserRequest) {

		SearchMbrSapUserResponse searchMbrSapUserResponse = null;

		// 입력 파라미터가 없음
		if (searchMbrSapUserRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// 공통 파라미터 없음
		if (searchMbrSapUserRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (searchMbrSapUserRequest.getCommonRequest().getTenantID() == null
				|| searchMbrSapUserRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		try {

			searchMbrSapUserResponse = this.service.searchMbrSapUser(searchMbrSapUserRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}

		return searchMbrSapUserResponse;

	}

	/**
	 * <pre>
	 * 사용자 실명인증 정보 조회.
	 * </pre>
	 * 
	 * @param searchRealNameRequest
	 *            사용자 실명인증 정보 조회 요청 Value Object
	 * @return SearchRealNameResponse - 사용자 실명인증 정보 조회 응답 Value Object
	 */
	@Override
	public SearchRealNameResponse searchRealName(SearchRealNameRequest searchRealNameRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("사용자 컨트롤러 - 사용자회원 실명인증 정보 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### 받은 데이터 searchUserRequest : {}", searchRealNameRequest);

		SearchRealNameResponse searchRealNameResponse = null;

		// 입력 파라미터가 없음
		if (searchRealNameRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (searchRealNameRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 필수 파라미터, UserKey
		if (searchRealNameRequest.getUserKey() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {

			searchRealNameResponse = this.service.searchRealName(searchRealNameRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }

		return searchRealNameResponse;

	}

	/**
	 * <pre>
	 * 제한 정책 Key 정보를 등록/수정하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updatePolicyKeyRequest
	 *            제한 정책Key 수정 요청 Value Object
	 * @return updatePolicyKeyResponse - 제한 정책Key 수정 응답 Value Object
	 */
	@Override
	public UpdatePolicyKeyResponse updatePolicyKey(UpdatePolicyKeyRequest updatePolicyKeyRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("컨트롤러 - 제한 정책 Key 수정");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		UpdatePolicyKeyResponse updatePolicyKeyResponse = null;

		// 입력 파라미터가 없음
		if (updatePolicyKeyRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (updatePolicyKeyRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (updatePolicyKeyRequest.getCommonRequest().getTenantID() == null
				|| updatePolicyKeyRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터 1
		if (updatePolicyKeyRequest.getNewLimitPolicyKey() == null
				|| updatePolicyKeyRequest.getOldLimitPolicyKey() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {

			updatePolicyKeyResponse = this.service.updatePolicyKey(updatePolicyKeyRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }

		return updatePolicyKeyResponse;

	}

	/**
	 * <pre>
	 * 제한 정책 Value 정보를 등록/수정하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updatePolicyValueRequest
	 *            제한 정책 Value 수정 요청 Value Object
	 * @return updatePolicyValueResponse - 제한 정책 Value 수정 응답 Value Object
	 */
	@Override
	public UpdatePolicyValueResponse updatePolicyValue(UpdatePolicyValueRequest updatePolicyValueRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("컨트롤러 - 제한 정책 Value 수정");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		UpdatePolicyValueResponse updatePolicyValueResponse = null;

		// 입력 파라미터가 없음
		if (updatePolicyValueRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (updatePolicyValueRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (updatePolicyValueRequest.getCommonRequest().getTenantID() == null
				|| updatePolicyValueRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터 1
		if (updatePolicyValueRequest.getNewApplyValue() == null || updatePolicyValueRequest.getOldApplyValue() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {

			updatePolicyValueResponse = this.service.updatePolicyValue(updatePolicyValueRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }

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
		LOGGER.debug("사용자 컨트롤러 - 복수 사용자 기기정보 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### searchMbrDeviceRequest : {}", searchMbrDeviceRequest);

		SearchMbrDeviceResponse searchMbrDeviceResponse;

		// 입력 파라미터가 없음
		if (searchMbrDeviceRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// 공통 파라미터 없음
		if (searchMbrDeviceRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 필수 파라미터 > userDeviceKeyList
		List<UserDeviceKey> userDeviceKeyList = searchMbrDeviceRequest.getDeviceKeyList();
		if (userDeviceKeyList == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		for (UserDeviceKey userDeviceKey : userDeviceKeyList) {
			if (userDeviceKey.getDeviceKey() == null || userDeviceKey.getDeviceKey().length() <= 0
					|| userDeviceKey.getUserKey() == null || userDeviceKey.getUserKey().length() <= 0)
				throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {

			searchMbrDeviceResponse = this.service.searchMbrDevice(searchMbrDeviceRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}

		return searchMbrDeviceResponse;
	}

	/**
	 * <pre>
	 * 조회 구분별 사용자 정보 조회.
	 * </pre>
	 * 
	 * @param searchExtentUserRequest
	 *            조회 구분별 사용자 정보 조회 요청 Value Object
	 * @return searchExtentUserResponse - 조회 구분별 사용자 정보 조회 응답 Value Object
	 */
	@Override
	public SearchExtentUserResponse searchExtentUser(SearchExtentUserRequest searchExtentUserRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("사용자 컨트롤러 - 사용자회원 조회 구분별 사용자 정보 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### 받은 데이터 searchUserRequest : {}", searchExtentUserRequest);

		SearchExtentUserResponse searchExtentUserResponse = null;

		// 입력 파라미터가 없음
		if (searchExtentUserRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (searchExtentUserRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 필수 파라미터, keySearchList
		if (searchExtentUserRequest.getKeySearchList() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// 필수 파라미터 in List, keySearch
		List<KeySearch> keySearchList = searchExtentUserRequest.getKeySearchList();
		for (KeySearch keySearch : keySearchList) {

			// 필수 파라미터
			if (keySearch.getKeyType() == null || keySearch.getKeyType().length() <= 0
					|| keySearch.getKeyString() == null || keySearch.getKeyString().length() <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			}

			// 잘못된 키값
			if (!keySearch.getKeyType().equalsIgnoreCase(Constant.SEARCH_TYPE_USER_KEY)
					&& !keySearch.getKeyType().equalsIgnoreCase(Constant.SEARCH_TYPE_USER_ID)
					&& !keySearch.getKeyType().equalsIgnoreCase(Constant.SEARCH_TYPE_DEVICE_KEY)
					&& !keySearch.getKeyType().equalsIgnoreCase(Constant.SEARCH_TYPE_DEVICE_ID)
					&& !keySearch.getKeyType().equalsIgnoreCase(Constant.SEARCH_TYPE_MDN)) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.wrongKeyType", ""));
			}
		}

		try {

			searchExtentUserResponse = this.service.searchExtentUser(searchExtentUserRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}

		return searchExtentUserResponse;

	}

	/**
	 * <pre>
	 * 회원 segment 정보 조회 기능을 제공 한다.
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
		LOGGER.debug("사용자 컨트롤러 - 회원 segment 정보 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### 받은 데이터 searchUserRequest : {}", searchUserSegmentRequest);

		SearchUserSegmentResponse searchUserSegmentResponse = null;

		// 입력 파라미터가 없음
		if (searchUserSegmentRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (searchUserSegmentRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 필수 파라미터, userKey
		if (searchUserSegmentRequest.getUserKey() == null || searchUserSegmentRequest.getUserKey().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {
			// 회원 segment 정보 조회
			searchUserSegmentResponse = this.service.searchUserSegment(searchUserSegmentRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}

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
		LOGGER.debug("사용자 컨트롤러 - 회원 부가속성 정보 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### 받은 데이터 searchUserExtraInfoRequest : {}", searchUserExtraInfoRequest);

		SearchUserExtraInfoResponse searchUserExtraInfoResponse = null;

		// 입력 파라미터가 없음
		if (searchUserExtraInfoRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (searchUserExtraInfoRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		try {
			// 회원 segment 정보 조회
			searchUserExtraInfoResponse = this.service.searchUserExtraInfo(searchUserExtraInfoRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}

		return searchUserExtraInfoResponse;
	}

	/**
	 * <pre>
	 * 심플 인증(간편 인증)v1
	 *  - deviceId를 이용하여 DB조회 인증후 로그인이력 저장.
	 * </pre>
	 * 
	 * @param simpleLoginRequest
	 *            - 심플인증 요청 Value Object
	 * @return simpleLoginResponse - 심플인증 응답 Value Object
	 */
	@Override
	public SimpleLoginResponse simpleLogin(SimpleLoginRequest simpleLoginRequest) {

		LOGGER.debug("\n\n\n\n\n==================================================================================");
		LOGGER.debug("사용자 컨트롤러 - 심플 인증(간편 인증) v1");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### 받은 데이터 simpleLoginRequest : {}", simpleLoginRequest);

		SimpleLoginResponse simpleLoginResponse = null;

		// 입력 파라미터가 없음
		if (simpleLoginRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (simpleLoginRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 필수 파라미터 없음 mdn과 deviceId 모두 유효하지 않으면
		if ( (simpleLoginRequest.getDeviceID() == null || simpleLoginRequest.getDeviceID().length() <= 0)
				&& (simpleLoginRequest.getMdn() == null || simpleLoginRequest.getMdn().length() <= 0)){
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {

			simpleLoginResponse = this.service.simpleLogin(simpleLoginRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}

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
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("사용자 컨트롤러 - 회원 가입여부 리스트 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### 받은 데이터 existListRequest : {}", existListRequest);

		ExistListResponse existListResponse = null;

		// 입력 파라미터가 없음
		if (existListRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (existListRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 필수 파라미터 없음
		if (existListRequest.getDeviceIdList() == null || existListRequest.getDeviceIdList().size() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {
			//
			existListResponse = this.service.existList(existListRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}

		return existListResponse;
	}

	@Override
	public TlogResponse tlog(TlogRequest tlogRequest) {

		// 입력 파라미터가 없음
		if (tlogRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (tlogRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (tlogRequest.getCommonRequest().getTenantID() == null
				|| tlogRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터 없음
		if (StringUtils.isBlank(tlogRequest.getTlogInfo().getTlogID())) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		return this.service.tlog(tlogRequest);
	}

	@Override
	public SearchManagementResponse searchManagement(SearchManagementRequest searchManagementRequest) {

		// 입력 파라미터가 없음
		if (searchManagementRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (searchManagementRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		SearchManagementResponse searchManagementResponse = null;

		try {
			searchManagementResponse = this.service.searchManagement(searchManagementRequest);
		} catch (StorePlatformException ex) {
			throw ex;
		}

		return searchManagementResponse;
	}

	/**
	 * <pre>
	 * 사용자 실명인증 초기화.
	 * </pre>
	 * 
	 * @param updateRealNameRequest
	 *            사용자 실명인증 정보 수정 요청 Value Object
	 * @return UpdateRealNameResponse - 사용자 실명인증 정보 수정 응답 Value Object
	 */
	@Override
	public UpdateRealNameResponse initRealName(UpdateRealNameRequest updateRealNameRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("사용자 컨트롤러 - 사용자회원 실명인증 정보 초기화");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### 받은 데이터 updateRealNameRequest : {}", updateRealNameRequest);

		UpdateRealNameResponse updateRealNameResponse = null;

		// 입력 파라미터가 없음
		if (updateRealNameRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (updateRealNameRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 필수 파라미터, UserKey
		if (updateRealNameRequest.getUserKey() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {
			updateRealNameResponse = this.service.executeInitRealName(updateRealNameRequest);
		} catch (StorePlatformException ex) {
			throw ex;
		}

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
	@Override
	public ListTenantResponse searchTenantList(ListTenantRequest listTenantRequest) {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("사용자 컨트롤러 - 가입 테넌트 정보 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### client에서 받은값 : {}", listTenantRequest);

		ListTenantResponse listTenantResponse = null;

		// 입력 파라미터가 없음
		if (listTenantRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// deviceId 없음
		if (listTenantRequest.getDeviceId() == null || listTenantRequest.getDeviceId().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {

			listTenantResponse = this.service.searchTenantList(listTenantRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}

		return listTenantResponse;
	}

	/**
	 * <pre>
	 * 정상인 회원정보를 휴면 DB로 이동한다.
	 * </pre>
	 * 
	 * @param moveUserInfoRequest
	 *            - 회원키 Value Object
	 * @return moveUserInfoResponse - 처리 회원키 Value Object
	 */
	@Override
	public MoveUserInfoResponse executeMoveUserMbr(MoveUserInfoRequest moveUserInfoRequest) {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("사용자 컨트롤러 - 회원 정보 정상/휴면 처리");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### 받은 데이터 : {}", moveUserInfoRequest);

		MoveUserInfoResponse moveUserInfoResponse = null;

		// 입력 파라미터가 없음
		if (moveUserInfoRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (moveUserInfoRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (moveUserInfoRequest.getCommonRequest().getTenantID() == null
				|| moveUserInfoRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터, UserKey
		if (moveUserInfoRequest.getUserKey() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// 필수 파라미터, MoveType
		if (moveUserInfoRequest.getMoveType() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {
			// 유휴회원 이관
			moveUserInfoResponse = this.service.executeMoveUserMbr(moveUserInfoRequest);

			// 유휴 회원 이관 성공 이력 추가
			this.service.insertUserMbrTransHis(moveUserInfoResponse, Constant.TYPE_YN_Y);
		} catch (StorePlatformException ex) {

			// 유휴 회원 이관 실패 이력 추가
			moveUserInfoResponse = new MoveUserInfoResponse();
			moveUserInfoResponse.setTenantID(moveUserInfoRequest.getCommonRequest().getTenantID());
			moveUserInfoResponse.setUserKey(moveUserInfoRequest.getUserKey());
			moveUserInfoResponse.setTransCd(moveUserInfoRequest.getMoveType());
			this.service.insertUserMbrTransHis(moveUserInfoResponse, Constant.TYPE_YN_N);

			throw ex;
		}

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
		LOGGER.debug("사용자 컨트롤러 - 소셜 이력 등록");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### 받은 데이터 createSocialAccountRequest : {}", createSocialAccountRequest);

		CreateSocialAccountResponse createSocialAccountResponse = null;

		// 입력 파라미터가 없음
		if (createSocialAccountRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (createSocialAccountRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (createSocialAccountRequest.getCommonRequest().getTenantID() == null
				|| createSocialAccountRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터 : UserKey, socialAcctType, socialAcctIntId
		if (createSocialAccountRequest.getUserKey() == null || createSocialAccountRequest.getSocialAcctIntId() == null
				|| createSocialAccountRequest.getSocialAcctType() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {
			createSocialAccountResponse = this.service.createSocialAccount(createSocialAccountRequest);
		} catch (StorePlatformException ex) {
			throw ex;
		}

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
		LOGGER.debug("사용자 컨트롤러 - 소셜계정 이력 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### 받은 데이터 searchSocialAccountRequest : {}", searchSocialAccountRequest);

		SearchSocialAccountResponse searchSocialAccountResponse = null;

		// 입력 파라미터가 없음
		if (searchSocialAccountRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (searchSocialAccountRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (searchSocialAccountRequest.getCommonRequest().getTenantID() == null
				|| searchSocialAccountRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터 : userKey
		if (searchSocialAccountRequest.getUserKey() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {
			searchSocialAccountResponse = this.service.searchSocialAccount(searchSocialAccountRequest);
		} catch (StorePlatformException ex) {
			throw ex;
		}

		return searchSocialAccountResponse;
	}

	@Override
	public void updateActiveMoveUserLastLoginDt(MoveUserInfoRequest moveUserInfoRequest) {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("사용자 컨트롤러 - 휴면상태해제 사용자 최종접속일자 UPDATE");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### 받은 데이터 : {}", moveUserInfoRequest);

		// 입력 파라미터가 없음
		if (moveUserInfoRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (moveUserInfoRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (moveUserInfoRequest.getCommonRequest().getTenantID() == null
				|| moveUserInfoRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터, UserKey
		if (moveUserInfoRequest.getUserKey() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {
			this.service.updateActiveMoveUserLastLoginDt(moveUserInfoRequest);
		} catch (StorePlatformException ex) {
			throw ex;
		}

	}

	@Override
	public SearchDeActivateUserResponse searchDeActivateUser(SearchDeActivateUserRequest searchDeActivateUserRequest) {

		SearchDeActivateUserResponse searchDeActivateUserResponse = null;

		// 입력 파라미터가 없음
		if (searchDeActivateUserRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (searchDeActivateUserRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (searchDeActivateUserRequest.getCommonRequest().getTenantID() == null
				|| searchDeActivateUserRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터 : userKey, weekAgo
		if (searchDeActivateUserRequest.getUserKey() == null || searchDeActivateUserRequest.getWeekAgo() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {
			searchDeActivateUserResponse = this.service.searchDeActivateUser(searchDeActivateUserRequest);
		} catch (StorePlatformException ex) {
			throw ex;
		}

		return searchDeActivateUserResponse;
	}

	/**
	 * <pre>
	 * 배송지 등록/수정.
	 * </pre>
	 * 
	 * @param createDeliveryInfoRequest
	 *            배송지 등록/수정 요청 Value Object
	 * @return CreateDeliveryInfoResponse - 배송지 등록/수정 응답 Value Object
	 */
	@Override
	public CreateDeliveryInfoResponse createDeliveryInfo(CreateDeliveryInfoRequest createDeliveryInfoRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("사용자 컨트롤러 - 배송지 등록/수정");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### client에서 받은값 : {}", createDeliveryInfoRequest);

		CreateDeliveryInfoResponse createDeliveryInfoResponse = null;

		// 입력 파라미터가 없음
		if (createDeliveryInfoRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (createDeliveryInfoRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 사용자 key 없음
		if (createDeliveryInfoRequest.getUserKey() == null || createDeliveryInfoRequest.getUserKey().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// 배송지 시퀀스가 없으면(등록요청이라면) 배송지 타입은 필수 값
		if (createDeliveryInfoRequest.getDeliverySeq() == null
				|| createDeliveryInfoRequest.getDeliverySeq().length() <= 0) {
			if (createDeliveryInfoRequest.getDeliveryTypeCd() == null
					|| createDeliveryInfoRequest.getDeliveryTypeCd().length() <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			}
		}

		try {

			createDeliveryInfoResponse = this.service.createDeliveryInfo(createDeliveryInfoRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}

		return createDeliveryInfoResponse;

	}

	/**
	 * <pre>
	 * 배송지 삭제.
	 * </pre>
	 * 
	 * @param removeDeliveryInfoRequest
	 *            배송지 삭제 요청 Value Object
	 * @return RemoveDeliveryInfoResponse - 배송지 삭제 응답 Value Object
	 */
	@Override
	public RemoveDeliveryInfoResponse removeDeliveryInfo(RemoveDeliveryInfoRequest removeDeliveryInfoRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("사용자 컨트롤러 - 배송지 삭제");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### client에서 받은값 : {}", removeDeliveryInfoRequest);

		RemoveDeliveryInfoResponse rmoveDeliveryInfoResponse = null;

		// 입력 파라미터가 없음
		if (removeDeliveryInfoRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (removeDeliveryInfoRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 사용자 key 없음
		if (removeDeliveryInfoRequest.getUserKey() == null || removeDeliveryInfoRequest.getUserKey().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// 배송지 시퀀스 없음
		if (removeDeliveryInfoRequest.getDeliverySeq() == null
				|| removeDeliveryInfoRequest.getDeliverySeq().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {

			rmoveDeliveryInfoResponse = this.service.removeDeliveryInfo(removeDeliveryInfoRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}

		return rmoveDeliveryInfoResponse;

	}

	/**
	 * <pre>
	 * 배송지 정보 조회.
	 * </pre>
	 * 
	 * @param searchDeliveryInfoRequest
	 *            - 배송지 정보 조회 요청 Value Object
	 * @return SearchUserDeliveryResponse - 배송지 정보 조회 응답 Value Object
	 */
	@Override
    @Deprecated
	public SearchDeliveryInfoResponse searchDeliveryInfo(SearchDeliveryInfoRequest searchDeliveryInfoRequest) {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("사용자 컨트롤러 - 배송지 정보 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### client에서 받은값 : {}", searchDeliveryInfoRequest);

		SearchDeliveryInfoResponse searchDeliveryInfoResponse = null;

		// 입력 파라미터가 없음
		if (searchDeliveryInfoRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (searchDeliveryInfoRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 사용자 key 없음
		if (searchDeliveryInfoRequest.getUserKey() == null || searchDeliveryInfoRequest.getUserKey().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {

			searchDeliveryInfoResponse = this.service.searchDeliveryInfo(searchDeliveryInfoRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}

		return searchDeliveryInfoResponse;
	}

	/**
	 * <pre>
	 * 변경된 사용자키 조회.
	 * </pre>
	 * 
	 * @param searchAfterUserKeyRequest
	 *            - 변경된 사용자키 조회 요청 Value Object
	 * @return SearchAfterUserKeyResponse - 변경된 사용자키 조회 응답 Value Object
	 */
	@Override
	public SearchAfterUserKeyResponse searchAfterUserKey(SearchAfterUserKeyRequest searchAfterUserKeyRequest) {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("사용자 컨트롤러 - 변경된 사용자키 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### client에서 받은값 : {}", searchAfterUserKeyRequest);

		SearchAfterUserKeyResponse searchAfterUserKeyResponse = null;

		// 입력 파라미터가 없음
		if (searchAfterUserKeyRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (searchAfterUserKeyRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 사용자 key 없음
		if (searchAfterUserKeyRequest.getUserKey() == null || searchAfterUserKeyRequest.getUserKey().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {

			searchAfterUserKeyResponse = this.service.searchAfterUserKey(searchAfterUserKeyRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}

		return searchAfterUserKeyResponse;
	}

	@Override
	public TransferDeliveryResponse transferDelivery(TransferDeliveryRequest transferDeliveryRequest) {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("사용자 컨트롤러 - 배송지정보 이관 ");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		TransferDeliveryResponse transferDeliveryResponse = null;

		// 입력 파라미터가 없음
		if (transferDeliveryRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (transferDeliveryRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 필수 파라미터, userKey
		if (transferDeliveryRequest.getUserKey() == null || transferDeliveryRequest.getPreUserKey() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {
			transferDeliveryResponse = this.service.excuteTransferDelivery(transferDeliveryRequest);
		} catch (StorePlatformException ex) {
			throw ex;
		}

		return transferDeliveryResponse;
	}

	@Override
	public CreateGiftChargeInfoResponse createGiftChargeInfo(CreateGiftChargeInfoRequest createGiftChargeInfoRequest) {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("사용자 컨트롤러 - 상품권 충전소정보 저장 ");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		CreateGiftChargeInfoResponse createGiftChargeInfoResponse = null;

		// 입력 파라미터가 없음
		if (createGiftChargeInfoRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (createGiftChargeInfoRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 필수 파라미터, 회원 키, 판매자 키, 제휴사 브랜드명, 제휴사 회원 아이디, 충전자 이름
		if (StringUtils.isEmpty(createGiftChargeInfoRequest.getUserKey())
				|| StringUtils.isEmpty(createGiftChargeInfoRequest.getSellerKey())
				|| StringUtils.isEmpty(createGiftChargeInfoRequest.getBrandName())
				|| StringUtils.isEmpty(createGiftChargeInfoRequest.getChargerId())
				|| StringUtils.isEmpty(createGiftChargeInfoRequest.getChargerName())) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {
			createGiftChargeInfoResponse = this.service.createGiftChargeInfo(createGiftChargeInfoRequest);
		} catch (StorePlatformException ex) {
			throw ex;
		}

		return createGiftChargeInfoResponse;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.member.client.user.sci.UserSCI#searchGiftChargeInfo(com.skplanet.storeplatform.member
	 * .client.user.sci.vo.SearchGiftChargeInfoRequest)
	 */
	@Override
	public SearchGiftChargeInfoResponse searchGiftChargeInfo(SearchGiftChargeInfoRequest searchGiftChargeInfoRequest) {

		SearchGiftChargeInfoResponse searchGiftChargeInfoResponse = null;

		// 입력 파라미터가 없음
		if (searchGiftChargeInfoRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (searchGiftChargeInfoRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (searchGiftChargeInfoRequest.getCommonRequest().getTenantID() == null
				|| searchGiftChargeInfoRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터, userKey
		if (searchGiftChargeInfoRequest.getUserKey() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {
			searchGiftChargeInfoResponse = this.service.searchGiftChargeInfo(searchGiftChargeInfoRequest);
		} catch (StorePlatformException ex) {
			throw ex;
		}

		return searchGiftChargeInfoResponse;
	}

	@Override
	public TransferGiftChrgInfoResponse transferGiftChrgInfo(TransferGiftChrgInfoRequest transferGiftChrgInfoRequest) {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("사용자 컨트롤러 - 상품권 충전소 정보 이관 ");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		TransferGiftChrgInfoResponse transferGiftChrgInfoResponse = null;

		// 입력 파라미터가 없음
		if (transferGiftChrgInfoRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (transferGiftChrgInfoRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 필수 파라미터, userKey
		if (transferGiftChrgInfoRequest.getUserKey() == null || transferGiftChrgInfoRequest.getPreUserKey() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {
			transferGiftChrgInfoResponse = this.service.excuteTransferGiftChrgInfo(transferGiftChrgInfoRequest);
		} catch (StorePlatformException ex) {
			throw ex;
		}

		return transferGiftChrgInfoResponse;
	}

	@Override
	public CheckUserPwdResponse checkUserPwd(CheckUserPwdRequest chkUserPwdRequest){

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("사용자 컨트롤러 - ID,PW로 PW 일치 확인 ");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		CheckUserPwdResponse checkUserPwdResponse = null;

		// 입력 파라미터가 없음
		if (chkUserPwdRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (chkUserPwdRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 필수 파라미터
		if (chkUserPwdRequest.getUserKey() == null || chkUserPwdRequest.getUserPw() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {
			checkUserPwdResponse = this.service.checkUserPwd(chkUserPwdRequest);
		} catch (StorePlatformException ex) {
			throw ex;
		}

		return checkUserPwdResponse;

	}

	@Override
	public CheckUserAuthTokenResponse checkUserAuthToken(CheckUserAuthTokenRequest chkUserAuthTkReqeust){

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("사용자 컨트롤러 - userAuthToken의 유효성 체크 ");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		CheckUserAuthTokenResponse checkUserAuthTkResponse = null;

		// 입력 파라미터가 없음
		if (chkUserAuthTkReqeust == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (chkUserAuthTkReqeust.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 필수 파라미터
		if (chkUserAuthTkReqeust.getUserKey() == null || chkUserAuthTkReqeust.getUserAuthToken() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {
			checkUserAuthTkResponse = this.service.checkUserAuthToken(chkUserAuthTkReqeust);
		} catch (StorePlatformException ex) {
			throw ex;
		}

		return checkUserAuthTkResponse;

	}

	@Override
	public CreateUserAuthTokenResponse createUserAuthToken(CreateUserAuthTokenRequest createUserAuthTokenRequest){

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("사용자 컨트롤러 - userAuthToken 생성 ");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		CreateUserAuthTokenResponse createUserAuthTokenResponse = null;

		// 입력 파라미터가 없음
		if (createUserAuthTokenRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (createUserAuthTokenRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 필수 파라미터
		if (createUserAuthTokenRequest.getUserKey() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {
			createUserAuthTokenResponse = this.service.createUserAuthToken(createUserAuthTokenRequest);
		} catch (StorePlatformException ex) {
			throw ex;
		}

		return createUserAuthTokenResponse;

	}

	@Override
	public ModifyIdResponse modifyId(ModifyIdRequest modifyIdRequest){

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("사용자 컨트롤러 - ID 변경");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		ModifyIdResponse modifyIdResponse = null;

		// 입력 파라미터가 없음
		if (modifyIdRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (modifyIdRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 필수 파라미터
		if (modifyIdRequest.getUserKey() == null || modifyIdRequest.getUserId() == null
				|| modifyIdRequest.getUserType() == null || modifyIdRequest.getUserAuthToken() == null
				|| modifyIdRequest.getNewUserId() == null || modifyIdRequest.getNewUserType() == null
				|| modifyIdRequest.getNewUserEmail() ==  null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {
			modifyIdResponse = this.service.modifyId(modifyIdRequest);
		} catch (StorePlatformException ex) {
			throw ex;
		}

		return modifyIdResponse;

	}

}
