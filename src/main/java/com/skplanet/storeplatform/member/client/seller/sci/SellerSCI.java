/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.client.seller.sci;

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CheckDuplicationSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CheckDuplicationSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CheckPasswordReminderSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CheckPasswordReminderSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CreateSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CreateSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CreateSubSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CreateSubSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.LoginSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.LoginSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.RemoveLoginInfoRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.RemoveLoginInfoResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.RemoveSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.RemoveSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.RemoveSubSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.RemoveSubSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.ResetPasswordSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.ResetPasswordSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchAccountSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchAccountSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchAgreementListSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchAgreementListSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchIDSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchIDSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchLoginInfoRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchLoginInfoResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchMbrSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchMbrSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchPwdHintListAllRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchPwdHintListAllResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchPwdHintListRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchPwdHintListResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSubSellerListRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSubSellerListResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSubSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSubSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateAccountSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateAccountSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateAgreementSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateAgreementSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateLoginInfoRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateLoginInfoResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateNewPasswordSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateNewPasswordSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdatePasswordSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdatePasswordSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateRealNameSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateRealNameSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateStatusSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateStatusSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpgradeSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpgradeSellerResponse;

/**
 * 판매자 기능을 제공하는 Interface
 * 
 * Updated on : 2013. 12. 10. Updated by : wisestone_mikepark
 */
@SCI
public interface SellerSCI {

	/**
	 * <pre>
	 * 판매자회원 가입을 처리하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param createSellerRequest
	 *            판매자회원 가입 요청 Value Object
	 * @return createSellerResponse - 판매자회원 가입 응답 Value Object
	 */
	public CreateSellerResponse createSeller(CreateSellerRequest createSellerRequest);

	/**
	 * <pre>
	 * 판매자회원 로그인을 처리하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param loginSellerRequest
	 *            판매자회원 로그인 요청 Value Object
	 * @return loginSellerResponse - 판매자회원 로그인 응답 Value Object
	 */
	public LoginSellerResponse loginSeller(LoginSellerRequest loginSellerRequest);

	/**
	 * <pre>
	 * 판매자회원 기본정보만(판매자테이블) 조회를 처리하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchMbrSellerRequest
	 *            판매자회원 기본정보만(판매자테이블) 조회 요청 Value Object
	 * @return searchMbrSellerResponse - 판매자회원 기본정보만 조회 응답 Value Object
	 */
	public SearchMbrSellerResponse searchMbrSeller(SearchMbrSellerRequest searchMbrSellerRequest);

	/**
	 * <pre>
	 * 판매자회원 기본정보 조회를 처리하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchSellerRequest
	 *            판매자회원 기본정보 조회 요청 Value Object
	 * @return searchSellerResponse - 판매자회원 기본정보 조회 응답 Value Object
	 */
	public SearchSellerResponse searchSeller(SearchSellerRequest searchSellerRequest);

	/**
	 * <pre>
	 * 판매자회원 정산정보 조회를 처리하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchAccountSellerRequest
	 *            판매자회원 정산정보 조회 요청 Value Object
	 * @return searchAccountSellerResponse - 판매자회원 정산정보 조회 응답 Value Object
	 */
	public SearchAccountSellerResponse searchAccountSeller(SearchAccountSellerRequest searchAccountSellerRequest);

	/**
	 * <pre>
	 * 판매자회원 기본정보 수정을 처리하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updateSellerRequest
	 *            판매자회원 기본정보 수정 요청 Value Object
	 * @return updateSellerResponse - 판매자회원 기본정보 수정 응답 Value Object
	 */
	public UpdateSellerResponse updateSeller(UpdateSellerRequest updateSellerRequest);

	/**
	 * <pre>
	 * 판매자회원 정산정보 수정을 처리하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updateAccountSellerRequest
	 *            판매자회원 정산정보 수정 요청 Value Object
	 * @return updateAccountSellerResponse - 판매자회원 정산정보 수정 응답 Value Object
	 */
	public UpdateAccountSellerResponse updateAccountSeller(UpdateAccountSellerRequest updateAccountSellerRequest);

	/**
	 * <pre>
	 * 판매자회원 ID 찾기를 처리하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchIDSellerRequest
	 *            판매자회원 ID 찾기 요청 Value Object
	 * @return searchIDSellerResponse - 판매자회원 ID 찾기 응답 Value Object
	 */
	public SearchIDSellerResponse searchIDSeller(SearchIDSellerRequest searchIDSellerRequest);

	/**
	 * <pre>
	 * 판매자회원 상태를 변경하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updateStatusSellerRequest
	 *            판매자회원 상태 변경 요청 Value Object
	 * @return updateStatusSellerResponse - 판매자회원 상태 변경 응답 Value Object
	 */
	public UpdateStatusSellerResponse updateStatusSeller(UpdateStatusSellerRequest updateStatusSellerRequest);

	/**
	 * <pre>
	 * 판매자회원 ID/EMAIL 존재여부를 확인하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param checkDuplicationSellerRequest
	 *            판매자회원 ID/EMAIL 존재여부 확인 요청 Value Object
	 * @return checkDuplicationSellerResponse - 판매자회원 ID/EMAIL 존재여부 확인 응답 Value Object
	 */
	public CheckDuplicationSellerResponse checkDuplicationSeller(
			CheckDuplicationSellerRequest checkDuplicationSellerRequest);

	/**
	 * <pre>
	 * 판매자회원 비밀번호 보안질문을 확인하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param checkPasswordReminderSellerRequest
	 *            판매자회원 비밀번호 보안질문 확인 요청 Value Object
	 * @return checkPasswordReminderSellerResponse - 판매자회원 비밀번호 보안질문 확인 응답 Value Object
	 */
	public CheckPasswordReminderSellerResponse checkPasswordReminderSeller(
			CheckPasswordReminderSellerRequest checkPasswordReminderSellerRequest);

	/**
	 * <pre>
	 * 판매자회원 비밀번호를 초기화하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param resetPasswordSellerRequest
	 *            비밀번호 초기화 요청 Value Object
	 * @return resetPasswordSellerResponse - 비밀번호 초기화 응답 Value Object
	 */
	public ResetPasswordSellerResponse resetPasswordSeller(ResetPasswordSellerRequest resetPasswordSellerRequest);

	/**
	 * <pre>
	 * 판매자회원 비밀번호를 변경하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updatePasswordSellerRequest
	 *            비밀번호 변경 Value Object
	 * @return updatePasswordSellerResponse - 비밀번호 변경 응답 Value Object
	 */
	public UpdatePasswordSellerResponse updatePasswordSeller(UpdatePasswordSellerRequest updatePasswordSellerRequest);

	/**
	 * <pre>
	 * 판매자회원 새로운 비밀번호를 변경하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updateNewPasswordSellerRequest
	 *            새로운 비밀번호 변경 Value Object
	 * @return updateNewPasswordSellerResponse - 새로운 비밀번호 변경 응답 Value Object
	 */
	public UpdateNewPasswordSellerResponse updateNewPasswordSeller(
			UpdateNewPasswordSellerRequest updateNewPasswordSellerRequest);

	//
	// /**
	// * <pre>
	// * 판매자회원 약관동의 정보를 등록 또는 수정하는 기능을 제공한다.
	// * </pre>
	// *
	// * @param updateAgreementSellerRequest
	// * 약관동의 정보 수정 요청 Value Object
	// * @return updateAgreementSellerResponse - 약관동의 정보 수정 응답 Value Object
	// */
	// public UpdateAgreementSellerResponse updateAgreementSeller(UpdateAgreementSellerRequest
	// updateAgreementSellerRequest);

	/**
	 * <pre>
	 * 판매자회원 실명인증 정보를 수정하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updateRealNameSellerRequest
	 *            실명인증 정보 수정 요청 Value Object
	 * @return updateRealNameSellerResponse - 실명인증 정보 수정 응답 Value Object
	 */
	public UpdateRealNameSellerResponse updateRealNameSeller(UpdateRealNameSellerRequest updateRealNameSellerRequest);

	/**
	 * <pre>
	 * 판매자회원 전환신청을 등록하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param upgradeSellerRequest
	 *            전환신청 요청 Value Object
	 * @return upgradeSellerResponse - 전환신청 응답 Value Object
	 */
	public UpgradeSellerResponse upgradeSeller(UpgradeSellerRequest upgradeSellerRequest);

	/**
	 * <pre>
	 * 판매자회원 탈퇴를 처리하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param removeSellerRequest
	 *            판매자회원 탈퇴 요청 Value Object
	 * @return removeSellerResponse - 판매자회원 탈퇴 응답 Value Object
	 */
	public RemoveSellerResponse removeSeller(RemoveSellerRequest removeSellerRequest);

	/**
	 * <pre>
	 * 서브계정 등록을 처리하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param createSubSellerRequest
	 *            서브계정 등록 요청 Value Object
	 * @return createSubSellerResponse - 서브계정 등록 응답 Value Object
	 */
	public CreateSubSellerResponse createSubSeller(CreateSubSellerRequest createSubSellerRequest);

	/**
	 * <pre>
	 * 서브계정 목록을 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchSubListSellerRequest
	 *            서브계정 목록 조회 요청 Value Object
	 * @return searchSubListSellerRequest - 서브계정 목록 조회 응답 Value Object
	 */
	public SearchSubSellerListResponse searchSubSellerList(SearchSubSellerListRequest searchSubListSellerRequest);

	/**
	 * <pre>
	 * 서브계정 상세정보를 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchSubSellerRequest
	 *            서브계정 상세정보 조회 요청 Value Object
	 * @return searchSubSellerResponse - 서브계정 상세정보 조회 응답 Value Object
	 */
	public SearchSubSellerResponse searchSubSeller(SearchSubSellerRequest searchSubSellerRequest);

	/**
	 * <pre>
	 * 서브계정 삭제를 처리하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param removeSubSellerRequest
	 *            서브계정 삭제 요청 Value Object
	 * @return removeSubSellerResponse - 서브계정 삭제 응답 Value Object
	 */
	public RemoveSubSellerResponse removeSubSeller(RemoveSubSellerRequest removeSubSellerRequest);

	//
	// /**
	// * <pre>
	// * 판매자 약관동의 목록 조회.
	// * </pre>
	// *
	// * @param searchAgreementListSellerRequest
	// * 판매자 약관동의 목록 조회 요청 Value Object
	// * @return searchAgreementListSeller - 판매자 약관동의 목록 조회 응답 Value Object
	// */
	// public SearchAgreementListSellerResponse searchAgreementListSeller(
	// SearchAgreementListSellerRequest searchAgreementListSellerRequest);

	/**
	 * <pre>
	 * 비밀번호 힌트 목록을 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchPwdHintListRequest
	 *            비밀번호 힌트 목록 조회 요청 Value Object
	 * @return searchPwdHintListResponse - 비밀번호 힌트 목록 조회 응답 Value Object
	 */
	public SearchPwdHintListResponse searchPwdHintList(SearchPwdHintListRequest searchPwdHintListRequest);

	/**
	 * <pre>
	 * 로그인 정보 등록/수정을 처리하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updateLoginInfoRequest
	 *            로그인 정보 등록/수정 요청 Value Object
	 * @return updateLoginInfoResponse - 로그인 정보 등록/수정 응답 Value Object
	 */
	public UpdateLoginInfoResponse updateLoginInfo(UpdateLoginInfoRequest updateLoginInfoRequest);

	/**
	 * <pre>
	 * 로그인 정보를 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchLoginInfoRequest
	 *            로그인 정보 조회 요청 Value Object
	 * @return searchLoginInfoResponse - 로그인 정보 조회 응답 Value Object
	 */
	public SearchLoginInfoResponse searchLoginInfo(SearchLoginInfoRequest searchLoginInfoRequest);

	/**
	 * <pre>
	 * 로그인 정보 삭제 처리하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param removeLoginInfoRequest
	 *            로그인 정보 삭제 요청 Value Object
	 * @return removeLoginInfoResponse - 로그인 정보 삭제 응답 Value Object
	 */
	public RemoveLoginInfoResponse removeLoginInfo(RemoveLoginInfoRequest removeLoginInfoRequest);

	/**
	 * <pre>
	 * 전체 비밀번호 힌트 목록을 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchPwdHintListAllRequest
	 *            전체 비밀번호 힌트 목록 조회 요청 Value Object
	 * @return searchPwdHintListAllResponse - 전체 비밀번호 힌트 목록 조회 응답 Value Object
	 */
	public SearchPwdHintListAllResponse searchPwdHintListAll(SearchPwdHintListAllRequest searchPwdHintListAllRequest);

	/**
	 * <pre>
	 * 판매자 약관동의 등록/수정.
	 * </pre>
	 * 
	 * @param updateAgreementSellerRequest
	 *            판매자 약관동의 등록/수정 요청 Value Object
	 * @return UpdateAgreementSellerResponse - 판매자 약관동의 등록/수정 응답 Value Object
	 */
	public UpdateAgreementSellerResponse updateAgreementSeller(UpdateAgreementSellerRequest updateAgreementSellerRequest);

	/**
	 * <pre>
	 * 판매자 약관동의 목록 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchAgreementListSellerRequest
	 *            판매자 약관동의 목록 요청 Value Object
	 * @return SearchAgreementListSellerResponse - 판매자 약관동의 목록 응답 Value Object
	 */
	public SearchAgreementListSellerResponse searchAgreementListSeller(
			SearchAgreementListSellerRequest searchAgreementListSellerRequest);
}
