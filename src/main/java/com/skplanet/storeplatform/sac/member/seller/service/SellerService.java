package com.skplanet.storeplatform.sac.member.seller.service;

import com.skplanet.storeplatform.sac.client.member.vo.seller.AbrogationAuthKeyReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AbrogationAuthKeyRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ConfirmReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ConfirmRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateAuthKeyReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateAuthKeyRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.LockAccountReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.LockAccountRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyAccountInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyAccountInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.WithdrawReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.WithdrawRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 판매자 회원 가입/인증/탈퇴 기능 항목들.
 * 
 * Updated on : 2014. 1. 13. Updated by : 김경복, 부르칸.
 */
public interface SellerService {

	/** 5.2.1. 판매자 회원 가입. */
	public CreateRes createSeller(SacRequestHeader header, CreateReq req) throws Exception;

	/** 5.2.3. 판매자 회원 인증. */
	public AuthorizeRes authorize(SacRequestHeader header, AuthorizeReq req) throws Exception;

	/** 5.2.10. 판매자 회원 기본정보 수정 */
	public ModifyInformationRes modifyInformation(SacRequestHeader header, ModifyInformationReq req) throws Exception;

	/** 5.2.11. 판매자 회원 정산정보 수정 */
	public ModifyAccountInformationRes modifyAccountInformation(SacRequestHeader header, ModifyAccountInformationReq req)
			throws Exception;

	/** 5.2.16. 판매자 회원 계정 잠금. */
	public LockAccountRes lockAccount(SacRequestHeader header, LockAccountReq req) throws Exception;

	/** 5.2.14. 판매자 회원 계정 승인. */
	public ConfirmRes confirm(SacRequestHeader header, ConfirmReq req) throws Exception;

	/**
	 * 5.2.24. 판매자 회원 탈퇴.
	 * 
	 * @throws Exception
	 */
	public WithdrawRes withdraw(SacRequestHeader header, WithdrawReq req) throws Exception;

	/**
	 * 5.2.26. 판매자 회원 인증키 생성/연장.
	 * 
	 * @throws Exception
	 */
	public CreateAuthKeyRes createAuthKey(SacRequestHeader header, CreateAuthKeyReq req) throws Exception;

	/**
	 * 5.2.28. 판매자 회원 인증키 폐기.
	 * 
	 * @throws Exception
	 */
	public AbrogationAuthKeyRes abrogationAuthKey(SacRequestHeader header, AbrogationAuthKeyReq req) throws Exception;
}
