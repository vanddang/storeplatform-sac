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

import com.skplanet.storeplatform.member.client.common.vo.RemoveMemberPointRequest;
import com.skplanet.storeplatform.member.client.common.vo.RemoveMemberPointResponse;
import com.skplanet.storeplatform.member.client.common.vo.RemovePolicyRequest;
import com.skplanet.storeplatform.member.client.common.vo.RemovePolicyResponse;
import com.skplanet.storeplatform.member.client.common.vo.SearchMemberPointRequest;
import com.skplanet.storeplatform.member.client.common.vo.SearchMemberPointResponse;
import com.skplanet.storeplatform.member.client.common.vo.SearchPolicyRequest;
import com.skplanet.storeplatform.member.client.common.vo.SearchPolicyResponse;
import com.skplanet.storeplatform.member.client.common.vo.UpdateMbrOneIDRequest;
import com.skplanet.storeplatform.member.client.common.vo.UpdateMbrOneIDResponse;
import com.skplanet.storeplatform.member.client.common.vo.UpdateMemberPointRequest;
import com.skplanet.storeplatform.member.client.common.vo.UpdateMemberPointResponse;
import com.skplanet.storeplatform.member.client.common.vo.UpdatePolicyRequest;
import com.skplanet.storeplatform.member.client.common.vo.UpdatePolicyResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.CheckDuplicationRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CheckDuplicationResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateChangedDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateChangedDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateDeliveryInfoRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateDeliveryInfoResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateSocialAccountRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateSocialAccountResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.ExistListRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.ExistListResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.ListTenantRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.ListTenantResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.LoginUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.LoginUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.MoveUserInfoRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.MoveUserInfoResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveDeliveryInfoRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveDeliveryInfoResponse;
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
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeliveryInfoRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeliveryInfoResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceOSNumberRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceOSNumberResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchExtentUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchExtentUserResponse;
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
import com.skplanet.storeplatform.member.client.user.sci.vo.TlogRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.TlogResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.TransferDeliveryRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.TransferDeliveryResponse;
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

/**
 * <pre>
 * 회원서비스인터페이스.
 * </pre>
 * 
 * Created on : 2013-09-01 Created by :
 */

public interface UserService {

	/**
	 * <pre>
	 * 회원 가입.
	 * </pre>
	 * 
	 * @param createUserRequest
	 *            회원 가입 요청 Value Object
	 * 
	 * @return CreateUserResponse - 회원 가입 응답 Value Object
	 */
	public CreateUserResponse create(CreateUserRequest createUserRequest);

	/**
	 * <pre>
	 * ID/EMAIL 존재여부 확인.
	 * </pre>
	 * 
	 * @param checkDuplicationRequest
	 *            사용자회원 ID/이메일 존재여부 확인 요청 Value Object.
	 * @return CheckDuplicationResponse - 사용자회원 ID/이메일 존재여부 확인 응답 Value Object.
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
	 * @return LoginUserResponse - 로그인 응답 Value Object
	 */
	public LoginUserResponse updateLoginUser(LoginUserRequest loginUserRequest);

	/**
	 * <pre>
	 * 회원탈퇴를 처리하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param removeUserRequest
	 *            사용자 탈퇴 요청을 위한 value object
	 * @return RemoveUserResponse - 사용자 탈퇴에 대한 응답을 위한 value object
	 */
	public RemoveUserResponse remove(RemoveUserRequest removeUserRequest);

	/**
	 * <pre>
	 * 사용자 약관동의 등록/수정.
	 * </pre>
	 * 
	 * @param updateRightRequest
	 *            사용자 약관동의 등록/수정 요청 Value Object
	 * @return UpdateAgreementResponse - 사용자 약관동의 등록/수정 응답 Value Object
	 */
	public UpdateAgreementResponse updateAgreement(UpdateAgreementRequest updateRightRequest);

	/**
	 * <pre>
	 * 사용자 실명인증 정보 수정.
	 * </pre>
	 * 
	 * @param updateRealNameRequest
	 *            사용자 실명인증 정보 수정 요청 Value Object
	 * @return UpdateRealNameResponse - 사용자 실명인증 정보 수정 응답 Value Object
	 */
	public UpdateRealNameResponse updateRealName(UpdateRealNameRequest updateRealNameRequest);

	/**
	 * <pre>
	 * 사용자 기본정보 조회.
	 * </pre>
	 * 
	 * @param searchUserRequest
	 *            사용자 회원 기본정보 조회 요청 Value Object
	 * @return SearchUserResponse - 사용자 회원 기본정보 조회 응답 Value Object
	 */
	public SearchUserResponse searchUser(SearchUserRequest searchUserRequest);

	/**
	 * <pre>
	 * 사용자회원 기본정보 수정.
	 * </pre>
	 * 
	 * @param updateUserRequest
	 *            사용자회원 기본정보 수정 요청 Value Object
	 * @return UpdateUserResponse - 사용자회원 기본정보 수정 응답을 위한 Value Object
	 */
	public UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest);

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
	public UpdateStatusUserResponse updateStatus(UpdateStatusUserRequest updateStatusUserRequest);

	/**
	 * <pre>
	 * 사용자 회원 비밀번호 변경하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updatePasswordUserRequest
	 *            사용자회원 비밀번호 변경 요청 Value Object
	 * @return UpdatePasswordUserResponse - 사용자회원 비밀번호 변경 응답 Value Object
	 */
	public UpdatePasswordUserResponse updatePasswordUser(UpdatePasswordUserRequest updatePasswordUserRequest);

	/**
	 * <pre>
	 * 사용자 회원 비밀번호 초기화하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param resetPasswordUserRequest
	 *            사용자회원 비밀번호 변경 요청 Value Object
	 * @return ResetPasswordUserResponse - 사용자회원 비밀번호 변경 응답 Value Object
	 */
	public ResetPasswordUserResponse updateResetPasswordUser(ResetPasswordUserRequest resetPasswordUserRequest);

	/**
	 * <pre>
	 * 사용자 부가정보 목록 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchManagementListRequest
	 *            사용자 부가정보 목록 조회 요청 Value Object
	 * @return SearchManagementListResponse - 사용자 부가정보 목록 조회 응답 Value Object
	 */
	public SearchManagementListResponse searchManagementList(SearchManagementListRequest searchManagementListRequest);

	/**
	 * <pre>
	 * 사용자 약관동의 목록 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchAgreementListRequest
	 *            사용자 약관동의 목록 요청 Value Object
	 * @return SearchAgreementListResponse - 사용자 약관동의 목록 응답 Value Object
	 */
	public SearchAgreementListResponse searchAgreementList(SearchAgreementListRequest searchAgreementListRequest);

	/**
	 * <pre>
	 * 사용자 부가정보 등록/수정하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updateManagementRequest
	 *            사용자 부가정보 및 관리항목 정보수정 요청 Value Object
	 * @return UpdateManagementResponse - 사용자 부가정보 및 관리항목 정보수정 응답 Value Object
	 */
	public UpdateManagementResponse updateManagement(UpdateManagementRequest updateManagementRequest);

	/**
	 * <pre>
	 * 사용자 부가정보 삭제하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param removeManagementRequest
	 *            사용자 관리항목 및 부가정보 삭제 요청 Value Object
	 * @return RemoveManagementResponse - 사용자 관리항목 및 부가정보 삭제 응답 Value Object
	 */
	public RemoveManagementResponse removeManagement(RemoveManagementRequest removeManagementRequest);

	/**
	 * <pre>
	 * OneID 사용자 등록을 처리하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updateMbrOneIDRequest
	 *            OneID 사용자 등록 요청 Value Object
	 * @return createMbrOneIDResponse - OneID 사용자 등록 응답 Value Object
	 */
	public UpdateMbrOneIDResponse createMbrOneID(UpdateMbrOneIDRequest updateMbrOneIDRequest);

	/**
	 * <pre>
	 * 제한 정책 목록을 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchPolicyRequest
	 *            제한 정책 목록 조회 요청 Value Object
	 * @return searchPolicyResponse - 제한 정책 목록 조회 응답 Value Object
	 */
	public SearchPolicyResponse searchPolicyList(SearchPolicyRequest searchPolicyRequest);

	/**
	 * <pre>
	 * 제한 정책정보를 등록/수정하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updatePolicyRequest
	 *            제한 정책정보 등록/수정 요청 Value Object
	 * @return updatePolicyResponse - 제한 정책정보 등록/수정 응답 Value Object
	 */
	public UpdatePolicyResponse updatePolicy(UpdatePolicyRequest updatePolicyRequest);

	/**
	 * <pre>
	 * 제한 정책정보 히스토리를 업데이트하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updatePolicyRequest
	 *            UpdatePolicyRequest
	 * @return UpdatePolicyResponse
	 */
	public UpdatePolicyResponse updatePolicyHistory(UpdatePolicyRequest updatePolicyRequest);

	/**
	 * <pre>
	 * 제한 정책정보를 등록하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updatePolicyRequest
	 *            UpdatePolicyRequest
	 * @return UpdatePolicyResponse
	 */
	public UpdatePolicyResponse insertPolicy(UpdatePolicyRequest updatePolicyRequest);

	/**
	 * <pre>
	 * 제한 정책정보 삭제하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param removePolicyRequest
	 *            제한 정책정보 삭제 요청 Value Object
	 * @return removePolicyResponse - 제한 정책정보 삭제 응답 Value Object
	 */
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
	public SearchMemberPointResponse searchOCBList(SearchMemberPointRequest searchMemberPointRequest);

	/**
	 * <pre>
	 * OCB를 등록/수정하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updateMemberPointRequest
	 *            OCB 등록/수정 요청 Value Object
	 * @return updateOCBResponse - OCB 등록/수정 응답 Value Object
	 */
	public UpdateMemberPointResponse updateOCB(UpdateMemberPointRequest updateMemberPointRequest);

	/**
	 * <pre>
	 * OCB 삭제하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param removeMemberPointRequest
	 *            OCB 삭제 요청 Value Object
	 * @return removeOCBResponse - OCB 삭제 응답 Value Object
	 */
	public RemoveMemberPointResponse removeOCB(RemoveMemberPointRequest removeMemberPointRequest);

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
	 * 복수 SAP사용자 상태정보 조회.
	 * </pre>
	 * 
	 * @param searchMbrSapUserRequest
	 *            복수 SAP사용자 상태정보 조회 요청 Value Object
	 * @return SearchMbrSapUserResponse - 복수 SAP사용자 상태정보 조회 응답 Value Object
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
	 * 제한 정책 value 정보를 수정하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updatePolicyValueRequest
	 *            제한 정책정보 Value 수정 요청 Value Object
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
	 * SC Tlog 요청.
	 * </pre>
	 * 
	 * @param tlogRequest
	 *            SC tlog 요청 Value Object
	 * @return TlogResponse SC tlog 응답 Value Object
	 */
	public TlogResponse tlog(TlogRequest tlogRequest);

	/**
	 * <pre>
	 * 회원 부가 정보 조회..
	 * </pre>
	 * 
	 * @param searchManagementRequest
	 *            SearchManagementRequest
	 * @return SearchManagementRequest
	 */
	public SearchManagementResponse searchManagement(SearchManagementRequest searchManagementRequest);

	/**
	 * <pre>
	 * 사용자 실명인증 정보 초기화.
	 * </pre>
	 * 
	 * @param updateRealNameRequest
	 *            사용자 실명인증 정보 수정 요청 Value Object
	 * @return UpdateRealNameResponse - 사용자 실명인증 정보 수정 응답 Value Object
	 */
	public UpdateRealNameResponse executeInitRealName(UpdateRealNameRequest updateRealNameRequest);

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
	 * 유휴회원 이관 이력 저장.
	 * </pre>
	 * 
	 * @param moveUserInfoResponse
	 *            - 유휴회원 정보
	 * @param resultYn
	 *            - 결과
	 */
	public void insertUserMbrTransHis(MoveUserInfoResponse moveUserInfoResponse, String resultYn);

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
	 *            - 사용자 정보 VO
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
	 *            CreateDeliveryInfoRequest
	 * @return CreateDeliveryInfoResponse
	 */
	public CreateDeliveryInfoResponse createDeliveryInfo(CreateDeliveryInfoRequest createDeliveryInfoRequest);

	/**
	 * <pre>
	 * 배송지 삭제.
	 * </pre>
	 * 
	 * @param removeDeliveryInfoRequest
	 *            RemoveDeliveryInfoRequest
	 * @return RemoveDeliveryInfoResponse
	 */
	public RemoveDeliveryInfoResponse removeDeliveryInfo(RemoveDeliveryInfoRequest removeDeliveryInfoRequest);

	/**
	 * <pre>
	 * 배송지 정보 조회.
	 * </pre>
	 * 
	 * @param searchDeliveryInfoRequest
	 *            SearchUserDeliveryRequest
	 * @return SearchDeliveryInfoResponse
	 */
	public SearchDeliveryInfoResponse searchDeliveryInfo(SearchDeliveryInfoRequest searchDeliveryInfoRequest);

	/**
	 * <pre>
	 * 변경된 사용자키 조회.
	 * </pre>
	 * 
	 * @param searchAfterUserKeyRequest
	 *            searchAfterUserKeyRequest
	 * @return SearchAfterUserKeyResponse
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
	public TransferDeliveryResponse excuteTransferDelivery(TransferDeliveryRequest transferDeliveryRequest);

}
