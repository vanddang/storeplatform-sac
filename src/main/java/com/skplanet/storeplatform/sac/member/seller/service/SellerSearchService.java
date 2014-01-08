package com.skplanet.storeplatform.sac.member.seller.service;

import com.skplanet.storeplatform.sac.client.member.vo.seller.DuplicateByIdEmailReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DuplicateByIdEmailRes;

public interface SellerSearchService {

	/** 판매자 회원 ID/Email 중복조회 */
	public DuplicateByIdEmailRes duplicateByIdEmail(DuplicateByIdEmailReq req);
}
