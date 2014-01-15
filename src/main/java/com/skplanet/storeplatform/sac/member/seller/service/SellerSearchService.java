package com.skplanet.storeplatform.sac.member.seller.service;

import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailAccountInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailAccountInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DuplicateByIdEmailReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DuplicateByIdEmailRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListWithdrawalReasonRes;

public interface SellerSearchService {

	/** 판매자 회원 ID/Email 중복조회 */
	public DuplicateByIdEmailRes duplicateByIdEmail(DuplicateByIdEmailReq req);

	/**
	 * 판매자 회원 기본정보조회.
	 * 
	 * @throws Exception
	 */
	public DetailInformationRes detailInformation(DetailInformationReq req) throws Exception;

	/**
	 * 판매자 회원 정산정보조회.
	 * 
	 * @throws Exception
	 */
	public DetailAccountInformationRes detailAccountInformation(DetailAccountInformationReq req) throws Exception;

	/**
	 * 탈퇴 사유 목록 조회.
	 * 
	 * @throws Exception
	 */
	public ListWithdrawalReasonRes listWithdrawalReason() throws Exception;

}
