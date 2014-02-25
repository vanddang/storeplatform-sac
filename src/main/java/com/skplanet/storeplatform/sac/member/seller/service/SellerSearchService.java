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
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListPasswordReminderQuestionReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListPasswordReminderQuestionRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListWithdrawalReasonRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.SearchAuthKeyReq;
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

	/** 판매자 회원 기본정보조회. */
	public DetailInformationRes detailInformation(SacRequestHeader header, DetailInformationReq req);

	/** 판매자 회원 기본정보조회 App. */
	public DetailInformationForProductRes detailInformationApp(SacRequestHeader header,
			DetailInformationForProductReq req);

	/** 판매자 회원 정산정보조회. */
	public DetailAccountInformationRes detailAccountInformation(SacRequestHeader header, DetailAccountInformationReq req);

	/** 탈퇴 사유 목록 조회. */
	public ListWithdrawalReasonRes listWithdrawalReason(SacRequestHeader header, String language);

	/** 판매자 회원 ID 찾기. */
	public SearchIdRes searchId(SacRequestHeader header, SearchIdReq req);

	/** Password 보안 질문 조회. */
	public ListPasswordReminderQuestionRes listPasswordReminderQuestion(SacRequestHeader header,
			ListPasswordReminderQuestionReq req);

	/** Password 보안 질문 조회All. */
	public ListPasswordReminderQuestionRes listPasswordReminderQuestionAll(SacRequestHeader header, String language);

	/** Password 보안 질문 확인. */
	public CheckPasswordReminderQuestionRes checkPasswordReminderQuestion(SacRequestHeader header,
			CheckPasswordReminderQuestionReq req);

	/** 판매자 회원 Password 찾기. */
	public SearchPasswordRes searchPassword(SacRequestHeader header, SearchPasswordReq req);

	/** 판매자 회원 인증키 조회. */
	public DetailInformationRes searchAuthKey(SacRequestHeader header, SearchAuthKeyReq req);

	/** 나라별 해외은행 정보 조회. */
	public ListBanksByCountryRes listBanksByCountry(SacRequestHeader header);

}
