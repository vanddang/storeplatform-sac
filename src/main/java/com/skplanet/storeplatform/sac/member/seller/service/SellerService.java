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
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyAccountInformationSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyAccountInformationSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyInformationSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyInformationSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.WithdrawReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.WithdrawRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 판매자 회원 가입/인증/탈퇴 기능 항목들.
 * 
 * Updated on : 2014. 1. 13. Updated by : 김경복, 부르칸.
 */
public interface SellerService {

	/** 2.2.1. 판매자 회원 가입. */
	public CreateRes createSeller(SacRequestHeader header, CreateReq req);

	/** 2.2.3. 판매자 회원 인증. */
	public AuthorizeRes authorize(SacRequestHeader header, AuthorizeReq req);

	/** 2.2.10. 판매자 회원 기본정보 수정 */
	public ModifyInformationSacRes modifyInformation(SacRequestHeader header, ModifyInformationSacReq req);

	/** 2.2.11. 판매자 회원 정산정보 수정 */
	public ModifyAccountInformationSacRes modifyAccountInformation(SacRequestHeader header,
			ModifyAccountInformationSacReq req);

	/** 2.2.16. 판매자 회원 계정 잠금. */
	public LockAccountRes lockAccount(SacRequestHeader header, LockAccountReq req);

	/** 2.2.14. 판매자 회원 계정 승인. */
	public ConfirmRes confirm(SacRequestHeader header, ConfirmReq req);

	/** 2.2.24. 판매자 회원 탈퇴. */
	public WithdrawRes withdraw(SacRequestHeader header, WithdrawReq req);

	/** 2.2.26. 판매자 회원 인증키 생성/연장. */
	public CreateAuthKeyRes createAuthKey(SacRequestHeader header, CreateAuthKeyReq req);

	/** 2.2.28. 판매자 회원 인증키 폐기. */
	public AbrogationAuthKeyRes abrogationAuthKey(SacRequestHeader header, AbrogationAuthKeyReq req);
}
