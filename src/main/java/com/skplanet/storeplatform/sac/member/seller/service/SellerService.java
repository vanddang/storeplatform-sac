package com.skplanet.storeplatform.sac.member.seller.service;

import com.skplanet.storeplatform.sac.client.member.vo.seller.AbrogationAuthKeyReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AbrogationAuthKeyRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateAuthKeyReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateAuthKeyRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.LockAccountReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.LockAccountRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.WithdrawReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.WithdrawRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 판매자 회원 가입/인증/탈퇴 기능 항목들.
 * 
 * Updated on : 2014. 1. 13. Updated by : 김경복, 부르칸.
 */
public interface SellerService {

	/** 판매자 회원 가입. */
	public CreateRes createSeller(SacRequestHeader header, CreateReq req);

	/** 판매자 회원 계정 잠금. */
	public LockAccountRes lockAccount(SacRequestHeader header, LockAccountReq req);

	/** 판매자 회원 인증. */
	public AuthorizeRes authorize(SacRequestHeader header, AuthorizeReq req);

	/**
	 * 판매자 회원 탈퇴.
	 * 
	 * @throws Exception
	 */
	public WithdrawRes withdraw(SacRequestHeader header, WithdrawReq req) throws Exception;

	/**
	 * 판매자 회원 인증키 생성/연장.
	 * 
	 * @throws Exception
	 */
	public CreateAuthKeyRes createAuthKey(SacRequestHeader header, CreateAuthKeyReq req) throws Exception;

	/**
	 * 판매자 회원 인증키 폐기.
	 * 
	 * @throws Exception
	 */
	public AbrogationAuthKeyRes abrogationAuthKey(SacRequestHeader header, AbrogationAuthKeyReq req) throws Exception;
}
