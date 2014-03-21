package com.skplanet.storeplatform.sac.member.seller.service;

import com.skplanet.storeplatform.sac.client.member.vo.seller.CheckPasswordReminderQuestionReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CheckPasswordReminderQuestionRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailAccountInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailAccountInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailInformationForProductReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailInformationForProductRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DuplicateByIdEmailReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DuplicateByIdEmailRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListBanksByCountryRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListPasswordReminderQuestionAllRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListPasswordReminderQuestionReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListPasswordReminderQuestionRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListWithdrawalReasonRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.SearchAuthKeyReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.SearchAuthKeyRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.SearchIdReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.SearchIdRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.SearchPasswordReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.SearchPasswordRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 판매자 회원 조회관련 기능 항목들
 * 
 * Updated on : 2014. 1. 24. Updated by : 김경복, 부르칸
 */
public interface SellerSearchService {

	/**
	 * <pre>
	 * 2.2.2. 판매자 회원 ID/Email 중복 조회.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            DuplicateByIdEmailReq
	 * @return DuplicateByIdEmailRes
	 */
	public DuplicateByIdEmailRes duplicateByIdEmail(SacRequestHeader header, DuplicateByIdEmailReq req);

	/**
	 * <pre>
	 * 판매자회원 기본정보조회.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeaders
	 * @param req
	 *            DetailInformationReq
	 * @return DetailInformationRes
	 */
	public DetailInformationRes detailInformation(SacRequestHeader header, DetailInformationReq req);

	/**
	 * <pre>
	 * 판매자회원 기본정보조회 App.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeaders
	 * @param req
	 *            DetailInformationForProductReq
	 * @return DetailInformationForProductRes
	 */
	public DetailInformationForProductRes detailInformationApp(SacRequestHeader header,
			DetailInformationForProductReq req);

	/**
	 * <pre>
	 * 판매자회원 정산정보조회.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeaders
	 * @param req
	 *            DetailAccountInformationReq
	 * @return DetailAccountInformationRes
	 */
	public DetailAccountInformationRes detailAccountInformation(SacRequestHeader header, DetailAccountInformationReq req);

	/**
	 * <pre>
	 * 탈퇴 사유 목록 조회.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeaders
	 * @return ListWithdrawalReasonRes
	 */
	public ListWithdrawalReasonRes listWithdrawalReason(SacRequestHeader header);

	/**
	 * <pre>
	 * 판매자 ID 찾기.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeaders
	 * @param req
	 *            SearchIdReq
	 * @return SearchIdRes
	 */
	public SearchIdRes searchId(SacRequestHeader header, SearchIdReq req);

	/**
	 * <pre>
	 * Password 보안 질문 조회.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            ListPasswordReminderQuestionReq
	 * @return ListPasswordReminderQuestionRes
	 */
	public ListPasswordReminderQuestionRes listPasswordReminderQuestion(SacRequestHeader header,
			ListPasswordReminderQuestionReq req);

	/**
	 * <pre>
	 * Password 보안 질문 조회 All.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @return ListPasswordReminderQuestionRes
	 */
	public ListPasswordReminderQuestionAllRes listPasswordReminderQuestionAll(SacRequestHeader header);

	/**
	 * <pre>
	 * Password 보안 질문 확인.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            CheckPasswordReminderQuestionReq
	 * @return CheckPasswordReminderQuestionRes
	 */
	public CheckPasswordReminderQuestionRes checkPasswordReminderQuestion(SacRequestHeader header,
			CheckPasswordReminderQuestionReq req);

	/**
	 * <pre>
	 * 판매자 Password 찾기.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            SearchPasswordReq
	 * @return SearchPasswordRes
	 */
	public SearchPasswordRes searchPassword(SacRequestHeader header, SearchPasswordReq req);

	/**
	 * <pre>
	 * 2.2.27.	판매자 회원 인증키 조회.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            SearchAuthKeyReq
	 * @return SearchAuthKeyRes
	 */
	public SearchAuthKeyRes searchAuthKey(SacRequestHeader header, SearchAuthKeyReq req);

	/**
	 * <pre>
	 * 나라별 해외 은행 정보 조회.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @return ListBanksByCountryRes
	 */
	public ListBanksByCountryRes listBanksByCountry(SacRequestHeader header);

}
