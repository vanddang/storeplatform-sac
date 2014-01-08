package com.skplanet.storeplatform.sac.member.seller.service;

import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DuplicateByIdEmailReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DuplicateByIdEmailRes;

public interface SellerSearchService {

	/** 판매자 회원 ID/Email 중복조회 */
	public DuplicateByIdEmailRes duplicateByIdEmail(DuplicateByIdEmailReq req);

	/**
	 * <pre>
	 * 판매자회원 기본 정보 조회
	 * </pre>
	 * 
	 * @param headerVo
	 * @param req
	 * @return
	 */
	public DetailInformationRes detailInformation(DetailInformationReq req);

}
