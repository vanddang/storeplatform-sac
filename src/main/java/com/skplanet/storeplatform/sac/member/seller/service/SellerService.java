package com.skplanet.storeplatform.sac.member.seller.service;

import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.LockAccountReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.LockAccountRes;

public interface SellerService {

	/** 판매자 회원 가입 */
	public CreateRes createSeller();

	/** 판매자 회원 계정 잠금 */
	public LockAccountRes updateStatusSeller(LockAccountReq request);
}
