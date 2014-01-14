package com.skplanet.storeplatform.sac.member.seller.service;

import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.LockAccountReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.LockAccountRes;

/**
 * 판매자 회원 가입/인증/탈퇴 기능 항목들.
 * 
 * Updated on : 2014. 1. 13. Updated by : 김경복, 부르칸.
 */
public interface SellerService {

	/** 판매자 회원 가입. */
	public CreateRes createSeller(CreateReq req);

	/** 판매자 회원 계정 잠금. */
	public LockAccountRes lockAccount(LockAccountReq req);

	/** 판매자 회원 인증. */
	public AuthorizeRes authorize(AuthorizeReq req);
}
