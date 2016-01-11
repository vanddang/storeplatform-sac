/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.client.user.sci;

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.member.client.common.vo.*;
import com.skplanet.storeplatform.member.client.user.sci.vo.*;

/**
 * 사용자 기능을 제공하는 Interface
 * 
 * Updated on : 2016. 1. 5. Updated by : 최진호, 보고지티.
 */
@SCI
public interface UserSCI {

	/**
	 * <pre>
	 * 회원가입을 처리하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param createUserRequest
	 *            회원 가입 요청 Value Object
	 * @return createUserResponse - 회원 가입 응답 Value Object
	 */
	public CreateUserResponse create(CreateUserRequest createUserRequest);

	/**
	 * <pre>
	 * 사용자 존재여부 확인.
	 * </pre>
	 * 
	 * @param checkDuplicationRequest
	 *            사용자 존재여부 확인 요청 Value Object
	 * @return checkDuplicationResponse - 사용자 존재여부 확인 응답 Value Object
	 */
	public CheckDuplicationResponse checkDuplication(CheckDuplicationRequest checkDuplicationRequest);

	/**
	 * <pre>
	 * ID와 비밀번호 또는 device ID로 로그인하는 기능을 제공한다.
	 * OneID에서 로그인을 관리할 경우, 로그인개수와 성공여부를 등록한다.
	 * </pre>
	 * 
	 * @param loginUserRequest
	 *            로그인 요청 Value Object
	 * @return loginUserResponse - 로그인 응답 Value Object
	 */
	public LoginUserResponse updateLoginUser(LoginUserRequest loginUserRequest);

	/**
	 * <pre>
	 * 회원탈퇴를 처리하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param removeUserRequest
	 *            회원 탈퇴 요청 Value Object
	 * @return removeUserResponse - 회원 탈퇴 응답 Value Object
	 */
	public RemoveUserResponse remove(RemoveUserRequest removeUserRequest);

	/**
	 * <pre>
	 * 약관동의 정보를 등록 또는 수정하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updateAgreementRequest
	 *            약관동의 정보 수정 요청 Value Object
	 * @return updateAgreementResponse - 약관동의 정보 수정 응답 Value Object
	 */
	public UpdateAgreementResponse updateAgreement(UpdateAgreementRequest updateAgreementRequest);

	/**
	 * <pre>
	 * 실명인증 정보를 수정하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updateRealNameRequest
	 *            실명인증 정보 수정 요청 Value Object
	 * @return updateRealNameResponse - 실명인증 정보 수정 응답 Value Object
	 */
	public UpdateRealNameResponse updateRealName(UpdateRealNameRequest updateRealNameRequest);

	/**
	 * <pre>
	 * 사용자 회원 기본정보를 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchUserRequest
	 *            회원 기본정보 조회 요청 Value Object
	 * @return searchUserResponse - 회원 기본정보 조회 응답 Value Object
	 */
	public SearchUserResponse searchUser(SearchUserRequest searchUserRequest);

	/**
	 * <pre>
	 * 사용자 회원 기본정보를 수정하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updateUserRequest
	 *            회원 기본정보 수정 요청 Value Object
	 * @return updateUserResponse - 회원 기본정보 수정 응답 Value Object
	 */
	public UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest);

	/**
	 * <pre>
	 * 회원 상태정보를 변경하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updateStatusUserRequest
	 *            회원 상태정보 변경 요청 Value Object
	 * 
	 * @return updateStatusUserResponse - 회원 상태정보 변경 응답 Value Object
	 */
	public UpdateStatusUserResponse updateStatus(UpdateStatusUserRequest updateStatusUserRequest);

	/**
	 * <pre>
	 * 회원 비밀번호를 변경하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updatePasswordUserRequest
	 *            비밀번호 변경 Value Object
	 * @return updatePasswordUserResponse - 비밀번호 변경 응답 Value Object
	 */
	public UpdatePasswordUserResponse updatePasswordUser(UpdatePasswordUserRequest updatePasswordUserRequest);

	/**
	 * <pre>
	 * 회원 비밀번호를 초기화하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param resetPasswordUserRequest
	 *            비밀번호 초기화 요청 Value Object
	 * @return resetPasswordUserResponse - 비밀번호 초기화 응답 Value Object
	 */
	public ResetPasswordUserResponse updateResetPasswordUser(ResetPasswordUserRequest resetPasswordUserRequest);

	/**
	 * <pre>
	 * 사용자 부가정보 목록을 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchManagementListRequest
	 *            사용자 부가정보 목록 조회 요청 Value Object
	 * @return searchManagementListResponse - 사용자 부가정보 목록 조회 응답 Value Object
	 */
	public SearchManagementListResponse searchManagementList(SearchManagementListRequest searchManagementListRequest);

	/**
	 * <pre>
	 * 사용자 약관동의 목록을 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchAgreementListRequest
	 *            사용자 약관동의 목록 조회 요청 Value Object
	 * @return searchAgreementListResponse - 사용자 약관동의 목록 조회 응답 Value Object
	 */
	public SearchAgreementListResponse searchAgreementList(SearchAgreementListRequest searchAgreementListRequest);

	/**
	 * <pre>
	 * 사용자 부가정보를 등록/수정하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updateManagementRequest
	 *            사용자 부가정보 등록/수정 요청 Value Object
	 * @return updateManagementResponse - 사용자 부가정보 등록/수정 응답 Value Object
	 */
	public UpdateManagementResponse updateManagement(UpdateManagementRequest updateManagementRequest);

	/**
	 * <pre>
	 * 사용자 부가정보 삭제하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param removeManagementRequest
	 *            사용자 부가정보 삭제 요청 Value Object
	 * @return removeManagementResponse - 사용자 부가정보 삭제 응답 Value Object
	 */
	public RemoveManagementResponse removeManagement(RemoveManagementRequest removeManagementRequest);

	/**
	 * <pre>
	 * 제한 정책 목록을 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchPolicyRequest
	 *            제한 정책 목록 조회 요청 Value Object
	 * @return searchPolicyResponse - 제한 정책 목록 조회 응답 Value Object
	 * @deprecated use {@link com.skplanet.storeplatform.sac.member.user.service.LimitTargetService#searchPolicyList(SearchPolicyRequest)} instead.
	 */
	@Deprecated
	public SearchPolicyResponse searchPolicyList(SearchPolicyRequest searchPolicyRequest);

	/**
	 * <pre>
	 * 제한 정책정보를 등록/수정하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updatePolicyRequest
	 *            제한 정책정보 등록/수정 요청 Value Object
	 * @return updatePolicyResponse - 제한 정책정보 등록/수정 응답 Value Object
	 * @deprecated use {@link com.skplanet.storeplatform.sac.member.user.service.LimitTargetService#updatePolicy(UpdatePolicyRequest)} instead.
	 */
	@Deprecated
	public UpdatePolicyResponse updatePolicy(UpdatePolicyRequest updatePolicyRequest);

	/**
	 * <pre>
	 * 제한 정책정보 이력을 수정하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updatePolicyRequest
	 *            UpdatePolicyRequest
	 * @return updatePolicyResponse - UpdatePolicyResponse
	 * @deprecated use {@link com.skplanet.storeplatform.sac.member.user.service.LimitTargetService#updatePolicyHistory(UpdatePolicyRequest)} instead.
	 */
	@Deprecated
	public UpdatePolicyResponse updatePolicyHistory(UpdatePolicyRequest updatePolicyRequest);

	/**
	 * <pre>
	 * 제한 정책정보를 등록하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updatePolicyRequest
	 *            UpdatePolicyRequest
	 * @return updatePolicyResponse - UpdatePolicyResponse
	 * @deprecated use {@link com.skplanet.storeplatform.sac.member.user.service.LimitTargetService#insertPolicy(UpdatePolicyRequest)} instead.
	 */
	@Deprecated
	public UpdatePolicyResponse insertPolicy(UpdatePolicyRequest updatePolicyRequest);

	/**
	 * <pre>
	 * 제한 정책정보 삭제하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param removePolicyRequest
	 *            제한 정책정보 삭제 요청 Value Object
	 * @return removePolicyResponse - 제한 정책정보 삭제 응답 Value Object
	 * @deprecated use {@link com.skplanet.storeplatform.sac.member.user.service.LimitTargetService#removePolicy(RemovePolicyRequest)} instead.
	 */
	@Deprecated
	public RemovePolicyResponse removePolicy(RemovePolicyRequest removePolicyRequest);

	/**
	 * <pre>
	 * OCB 목록을 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchMemberPointRequest
	 *            OCB 목록 조회 요청 Value Object
	 * @return searchOCBResponse - OCB 목록 조회 응답 Value Object
	 */
	public SearchMemberPointResponse searchMemberPointList(SearchMemberPointRequest searchMemberPointRequest);

	/**
	 * <pre>
	 * OCB를 등록/수정하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updateMemberPointRequest
	 *            OCB 등록/수정 요청 Value Object
	 * @return updateOCBResponse - OCB 등록/수정 응답 Value Object
	 */
	public UpdateMemberPointResponse updateMemberPoint(UpdateMemberPointRequest updateMemberPointRequest);

	/**
	 * <pre>
	 * OCB 삭제하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param removeMemberPointRequest
	 *            OCB 삭제 요청 Value Object
	 * @return removeOCBResponse - OCB 삭제 응답 Value Object
	 */
	public RemoveMemberPointResponse removeMemberPoint(RemoveMemberPointRequest removeMemberPointRequest);

	/**
	 * <pre>
	 * 미동의 사이트 조회.
	 * </pre>
	 * 
	 * @param searchAgreeSiteRequest
	 *            미동의 사이트 조회 요청 Value Object
	 * @return SearchAgreeSiteResponse - 미동의 사이트 조회 응답 Value Object
	 */
	public SearchAgreeSiteResponse searchAgreeSite(SearchAgreeSiteRequest searchAgreeSiteRequest);

	/**
	 * <pre>
	 * 사용자키 변환추적 조회.
	 * </pre>
	 * 
	 * @param searchUserkeyTrackRequest
	 *            사용자키 변환추적 요청 Value Object
	 * @return SearchUserkeyTrackResponse - 사용자키 변환추적 응답 Value Object
	 */
	public SearchUserkeyTrackResponse searchUserkeyTrack(SearchUserkeyTrackRequest searchUserkeyTrackRequest);

	/**
	 * <pre>
	 * 사용자회원_ONEID 삭제.
	 * </pre>
	 * 
	 * @param removeMbrOneIDRequest
	 *            사용자회원_ONEID 삭제 요청 Value Object
	 * @return RemoveMbrOneIDResponse - 사용자회원_ONEID 삭제 응답 Value Object
	 */
	public RemoveMbrOneIDResponse removeMbrOneID(RemoveMbrOneIDRequest removeMbrOneIDRequest);

	/**
	 * <pre>
	 * 유통망 추천앱 회원 정보 등록하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updateUserMbrSegmentRequest
	 *            유통망 추천앱 회원 정보 등록 요청 Value Object
	 * @return UpdateUserMbrSegmentResponse - 유통망 추천앱 회원 정보 등록 응답 Value Object
	 */
	public UpdateUserMbrSegmentResponse updateUserMbrSegment(UpdateUserMbrSegmentRequest updateUserMbrSegmentRequest);

	/**
	 * <pre>
	 * 유통망 추천앱 비회원 정보 등록하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updateNonMbrSegmentRequest
	 *            유통망 추천앱 비회원 정보 등록 요청 Value Object
	 * @return UpdateNonMbrSegmentResponse - 유통망 추천앱 비회원 정보 등록 응답 Value Object
	 */
	public UpdateNonMbrSegmentResponse updateNonMbrSegment(UpdateNonMbrSegmentRequest updateNonMbrSegmentRequest);

	/**
	 * <pre>
	 * 휴대기기 기기변경이력 정보 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchChangedDeviceRequest
	 *            휴대기기 기기변경이력 정보 조회 요청 Value Object
	 * @return SearchChangedDeviceResponse - 휴대기기 기기변경이력 정보 조회 응답 Value Object
	 */
	public SearchChangedDeviceResponse searchChangedDevice(SearchChangedDeviceRequest searchChangedDeviceRequest);

	/**
	 * <pre>
	 * 휴대기기 기기변경이력 정보 등록하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param createChangedDeviceRequest
	 *            휴대기기 기기변경이력 정보 등록 요청 Value Object
	 * @return CreateChangedDeviceResponse - 휴대기기 기기변경이력 정보 등록 응답 Value Object
	 */
	public CreateChangedDeviceResponse createChangedDevice(CreateChangedDeviceRequest createChangedDeviceRequest);

	/**
	 * <pre>
	 * 이메일 사용자 정보 조회.
	 * </pre>
	 * 
	 * @param searchUserEmailRequest
	 *            이메일 사용자 정보 조회 요청 Value Object
	 * @return SearchUserEmailResponse - 이메일 사용자 정보 조회 응답 Value Object
	 */
	public SearchUserEmailResponse searchUserEmail(SearchUserEmailRequest searchUserEmailRequest);

	/**
	 * <pre>
	 * 단말 OS별 누적 가입자수 조회.
	 * </pre>
	 * 
	 * @param searchDeviceOSNumberRequest
	 *            단말 OS별 누적 가입자수 조회 요청 Value Object
	 * @return SearchDeviceOSNumberResponse - 단말 OS별 누적 가입자수 조회 응답 Value Object
	 */
	public SearchDeviceOSNumberResponse searchDeviceOSNumber(SearchDeviceOSNumberRequest searchDeviceOSNumberRequest);

	/**
	 * <pre>
	 * 복수 사용자 상태정보 조회.
	 * </pre>
	 * 
	 * @param searchMbrUserRequest
	 *            복수 사용자 상태정보 조회 요청 Value Object
	 * @return SearchMbrUserResponse - 복수 사용자 상태정보 조회 응답 Value Object
	 */
	public SearchMbrUserResponse searchMbrUser(SearchMbrUserRequest searchMbrUserRequest);

	/**
	 * <pre>
	 * 복수 Sap사용자 상태정보 조회.
	 * </pre>
	 * 
	 * @param searchMbrSapUserRequest
	 *            복수 사용자 상태정보 조회 요청 Value Object
	 * @return SearchMbrSapUserResponse - 복수 사용자 상태정보 조회 응답 Value Object
	 */
	public SearchMbrSapUserResponse searchMbrSapUser(SearchMbrSapUserRequest searchMbrSapUserRequest);

	/**
	 * <pre>
	 * 사용자 실명인증 정보를 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchRealNameRequest
	 *            실명인증 조회 요청 Value Object
	 * @return SearchRealNameResponse - 실명인증 조회 응답 Value Object
	 */
	public SearchRealNameResponse searchRealName(SearchRealNameRequest searchRealNameRequest);

	/**
	 * <pre>
	 * 제한 정책key 정보를 수정하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updatePolicyKeyRequest
	 *            제한 정책정보 key 수정 요청 Value Object
	 * @return updatePolicyKeyResponse - 제한 정책정보 key 수정 응답 Value Object
	 */
	public UpdatePolicyKeyResponse updatePolicyKey(UpdatePolicyKeyRequest updatePolicyKeyRequest);

	/**
	 * <pre>
	 * 제한 정책 Value 정보를 수정하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updatePolicyValueRequest
	 *            제한 정책정보 value 수정 요청 Value Object
	 * @return updatePolicyValueResponse - 제한 정책정보 Value 수정 응답 Value Object
	 */
	public UpdatePolicyValueResponse updatePolicyValue(UpdatePolicyValueRequest updatePolicyValueRequest);

	/**
	 * <pre>
	 * 복수 사용자 기기정보 조회.
	 * </pre>
	 * 
	 * @param searchMbrDeviceRequest
	 *            복수 사용자 기기정보 조회 요청 Value Object
	 * @return SearchMbrDeviceResponse - 복수 사용자 기기정보 조회 응답 Value Object
	 */
	public SearchMbrDeviceResponse searchMbrDevice(SearchMbrDeviceRequest searchMbrDeviceRequest);

	/**
	 * <pre>
	 * 조회 구분별 사용자 정보 조회.
	 * </pre>
	 * 
	 * @param searchExtentUserRequest
	 *            조회 구분별 사용자 정보 조회 요청 Value Object
	 * @return searchExtentUserResponse - 조회 구분별 사용자 정보 조회 응답 Value Object
	 */
	public SearchExtentUserResponse searchExtentUser(SearchExtentUserRequest searchExtentUserRequest);

	/**
	 * <pre>
	 * 회원 segment 정보 조회.
	 * </pre>
	 * 
	 * @param searchUserSegmentRequest
	 *            회원 segment 정보 조회 요청 Value Object
	 * @return searchUserSegmentResponse - 회원 segment 정보 조회 응답 Value Object
	 */
	public SearchUserSegmentResponse searchUserSegment(SearchUserSegmentRequest searchUserSegmentRequest);

	/**
	 * <pre>
	 * 회원 부가속성 정보 조회.
	 * </pre>
	 * 
	 * @param searchUserExtraInfoRequest
	 *            회원 부가속성 정보 조회 요청 Value Object
	 * @return searchUserExtraInfoResponse - 회원 부가속성 정보 조회 응답 Value Object
	 */
	public SearchUserExtraInfoResponse searchUserExtraInfo(SearchUserExtraInfoRequest searchUserExtraInfoRequest);

	/**
	 * <pre>
	 * 심플 인증(간편인증).
	 * </pre>
	 * 
	 * @param simpleLoginRequest
	 *            - 심플인증 요청 Value Object
	 * @return simpleLoginResponse - 심플인증 응답 Value Object
	 */
	public SimpleLoginResponse simpleLogin(SimpleLoginRequest simpleLoginRequest);

	/**
	 * <pre>
	 * 회원 가입여부 리스트 조회.
	 * </pre>
	 * 
	 * @param existListRequest
	 *            - 회원 가입여부 리스트 조회 요청 Value Object
	 * @return existListResponse - 회원 가입여부 리스트 조회 응답 Value Object
	 */
	public ExistListResponse existList(ExistListRequest existListRequest);

	/**
	 * <pre>
	 * SC Tlog 남기도록 요청.
	 * </pre>
	 * 
	 * @param tlogRequest
	 *            SC Tlog 요청 Value Object
	 * @return TlogResponse SC Tlog 응답 Value Object
	 */
	public TlogResponse tlog(TlogRequest tlogRequest);

	/**
	 * <pre>
	 * 회원 부가 정보 조회.
	 * </pre>
	 * 
	 * @param searchManagementRequest
	 *            SearchManagementRequest
	 * @return SearchManagementResponse
	 */
	public SearchManagementResponse searchManagement(SearchManagementRequest searchManagementRequest);

	/**
	 * <pre>
	 * 실명인증 정보를 초기화 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updateRealNameRequest
	 *            실명인증 정보 수정 요청 Value Object
	 * @return updateRealNameResponse - 실명인증 정보 수정 응답 Value Object
	 */
	public UpdateRealNameResponse initRealName(UpdateRealNameRequest updateRealNameRequest);

	/**
	 * <pre>
	 * 정상인 회원의 테넌트 리스트를 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param listTenantRequest
	 *            - 기기ID 조회 요청 Value Object
	 * @return ListTenantResponse - 테넌트 리스트 응답 Value Object
	 */
	public ListTenantResponse searchTenantList(ListTenantRequest listTenantRequest);

	/**
	 * <pre>
	 * 정상인 회원정보를 휴면 DB로 이동한다.
	 * </pre>
	 * 
	 * @param moveUserInfoRequest
	 *            - 회원키 Value Object
	 * @return moveUserInfoResponse - 처리 회원키 Value Object
	 */
	public MoveUserInfoResponse executeMoveUserMbr(MoveUserInfoRequest moveUserInfoRequest);

	/**
	 * <pre>
	 * 소셜 이력 등록 기능을 제공한다.
	 * </pre>
	 * 
	 * @param createSocialAccountRequest
	 *            - 소셜계정 등록 요청 Value Object
	 * @return createSocialAccountRequest - 소셜계정 등록 응답 Value Object
	 */
	public CreateSocialAccountResponse createSocialAccount(CreateSocialAccountRequest createSocialAccountRequest);

	/**
	 * <pre>
	 * 소셜계정 이력 조회 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchSocialAccountRequest
	 *            - 소셜계정 이력 조회 요청 Value Object
	 * @return searchSocialAccountResponse- 소셜계정 이력 조회 응답 Value Object
	 */
	public SearchSocialAccountResponse searchSocialAccount(SearchSocialAccountRequest searchSocialAccountRequest);

	/**
	 * <pre>
	 * 휴면상태해제 사용자의 최종접속일자 컬럼 업데이트.
	 * </pre>
	 * 
	 * @param moveUserInfoRequest
	 *            - 회원키 Value Object
	 */
	public void updateActiveMoveUserLastLoginDt(MoveUserInfoRequest moveUserInfoRequest);

	/**
	 * <pre>
	 * 휴면계정 분리보관 예정 사용자 조회.
	 * </pre>
	 * 
	 * @param searchDeActivateUserRequest
	 *            SearchDeActivateUserRequest
	 * @return SearchDeActivateUserResponse
	 */
	public SearchDeActivateUserResponse searchDeActivateUser(SearchDeActivateUserRequest searchDeActivateUserRequest);

	/**
	 * <pre>
	 * 배송지 등록/수정.
	 * </pre>
	 * 
	 * @param createDeliveryInfoRequest
	 *            배송지 등록/수정 요청 Value Object
	 * @return CreateDeliveryInfoResponse - 배송지 등록/수정 응답 Value Object
	 */
	public CreateDeliveryInfoResponse createDeliveryInfo(CreateDeliveryInfoRequest createDeliveryInfoRequest);

	/**
	 * <pre>
	 * 배송지 삭제.
	 * </pre>
	 * 
	 * @param removeDeliveryInfoRequest
	 *            배송지 삭제 요청 Value Object
	 * @return RemoveDeliveryInfoResponse - 배송지 삭제 응답 Value Object
	 */
	public RemoveDeliveryInfoResponse removeDeliveryInfo(RemoveDeliveryInfoRequest removeDeliveryInfoRequest);

	/**
	 * <pre>
	 * 배송지 정보 조회.
	 * </pre>
	 * 
	 * @param searchDeliveryInfoRequest
	 *            배송지 정보 조회 요청 Value Object
	 * @return SearchDeliveryInfoResponse - 배송지 정보 조회 응답 Value Object
	 */
	public SearchDeliveryInfoResponse searchDeliveryInfo(SearchDeliveryInfoRequest searchDeliveryInfoRequest);

	/**
	 * <pre>
	 * 변경된 사용자키 조회.
	 * </pre>
	 * 
	 * @param searchAfterUserKeyRequest
	 *            변경된 사용자키 조회 요청 Value Object
	 * @return SearchAfterUserKeyResponse - 변경된 사용자키 조회 응답 Value Object
	 */
	public SearchAfterUserKeyResponse searchAfterUserKey(SearchAfterUserKeyRequest searchAfterUserKeyRequest);

	/**
	 * <pre>
	 * 배송지정보 이관 기능을 제공한다.
	 * </pre>
	 * 
	 * @param transferDeliveryRequest
	 *            TransferDeliveryRequest
	 * @return TransferDeliveryResponse
	 */
	public TransferDeliveryResponse transferDelivery(TransferDeliveryRequest transferDeliveryRequest);

	/**
	 * <pre>
	 * 회원 상품권 충전 정보를 등록 한다.
	 * </pre>
	 * 
	 * @param createGiftChargeInfoRequest
	 *            CreateGiftChargeInfoRequest
	 * @return CreateGiftChargeInfoResponse
	 */
	public CreateGiftChargeInfoResponse createGiftChargeInfo(CreateGiftChargeInfoRequest createGiftChargeInfoRequest);

	/**
	 * <pre>
	 * 상품권 충전 정보 조회.
	 * </pre>
	 * 
	 * @param searchGiftChargeInfoRequest
	 *            SearchGiftChargeInfoRequest
	 * @return SearchGiftChargeInfoResponse
	 */
	public SearchGiftChargeInfoResponse searchGiftChargeInfo(SearchGiftChargeInfoRequest searchGiftChargeInfoRequest);

	/**
	 * <pre>
	 * 상품권 충전소 정보 이관 기능을 제공한다.
	 * </pre>
	 * 
	 * @param transferGiftChrgInfoRequest
	 *            TransferGiftChrgInfoRequest
	 * @return TransferGiftChrgInfoResponse
	 */
	public TransferGiftChrgInfoResponse transferGiftChrgInfo(TransferGiftChrgInfoRequest transferGiftChrgInfoRequest);

	/**
	 * <pre>
	 * ID로 PW를 확인 기능을 제공한다.
	 * </pre>
	 *
	 * @param chkUserPwdRequest
	 *            CheckUserPwdRequest
	 * @return CheckUserPwdResponse
	 */
	public CheckUserPwdResponse checkUserPwd(CheckUserPwdRequest chkUserPwdRequest);

	/**
	 * <pre>
	 * userAuthToken의 유효성을 검증하는 기능을 제공한다.
	 * </pre>
	 *
	 * @param chkUserAuthTkReqeust
	 *            CheckUserAuthTokenRequest
	 * @return CheckUserAuthTokenResponse
	 */
	public CheckUserAuthTokenResponse checkUserAuthToken(CheckUserAuthTokenRequest chkUserAuthTkReqeust);

	/**
	 * <pre>
	 * userAuthToken을 생성 하는 기능을 제공한다.
	 * </pre>
	 *
	 * @param createUserAuthTokenRequest
	 *            CreateUserAuthTokenRequest
	 * @return CreateUserAuthTokenResponse
	 */
	public CreateUserAuthTokenResponse createUserAuthToken(CreateUserAuthTokenRequest createUserAuthTokenRequest);

	/**
	 * <pre>
	 * ID를 변경하는 기능을 제공한다.
	 * </pre>
	 *
	 * @param modifyIdRequest
	 *            ModifyIdRequest
	 * @return ModifyIdResponse
	 */
	public ModifyIdResponse modifyId(ModifyIdRequest modifyIdRequest);

}
