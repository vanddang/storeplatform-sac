package com.skplanet.storeplatform.sac.member.seller.service;

import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeSimpleReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeSimpleRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ConfirmReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ConfirmRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ConversionClassSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ConversionClassSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateChangeSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateChangeSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateFlurrySacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateFlurrySacRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.LockAccountReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.LockAccountRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyAccountInformationSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyAccountInformationSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyEmailSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyEmailSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyFlurrySacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyFlurrySacRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyInformationSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyInformationSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyPasswordSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyPasswordSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyRealNameSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyRealNameSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyWaitEmailSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyWaitEmailSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.RemoveAuthorizationKeySacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.RemoveAuthorizationKeySacRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.RemoveFlurrySacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.RemoveFlurrySacRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.WithdrawReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.WithdrawRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 판매자 회원 가입/인증/탈퇴 기능 항목들.
 * 
 * Updated on : 2014. 1. 13. Updated by : 김경복, 부르칸.
 */
public interface SellerService {

	/**
	 * <pre>
	 * 2.2.1. 판매자 회원 가입.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            CreateReq
	 * @return CreateRes
	 */
	public CreateRes regSeller(SacRequestHeader header, CreateReq req);

	/**
	 * <pre>
	 * 2.2.3. 판매자 회원 인증.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeReq
	 * @return AuthorizeRes
	 */
	public AuthorizeRes authorize(SacRequestHeader header, AuthorizeReq req);

	/**
	 * <pre>
	 * 2.2.4. 판매자 회원 단순 인증.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeReq
	 * @return AuthorizeSimpleRes
	 */
	public AuthorizeSimpleRes authorizeSimple(SacRequestHeader header, AuthorizeSimpleReq req);

	/**
	 * <pre>
	 * 2.2.11. 판매자 회원 기본정보 수정.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            ModifyInformationSacReq
	 * @return ModifyInformationSacRes
	 */
	public ModifyInformationSacRes modInformation(SacRequestHeader header, ModifyInformationSacReq req);

	/**
	 * <pre>
	 * 2.2.12. 판매자 회원 정산정보 수정.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            ModifyAccountInformationSacReq
	 * @return ModifyAccountInformationSacRes
	 */
	public ModifyAccountInformationSacRes modAccountInformation(SacRequestHeader header,
			ModifyAccountInformationSacReq req);

	/**
	 * <pre>
	 * 2.2.13. 판매자회원 이메일 수정.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            ModifyEmailSacReq
	 * @return ModifyEmailSacRes
	 */
	public ModifyEmailSacRes modEmail(SacRequestHeader header, ModifyEmailSacReq req);

	/**
	 * <pre>
	 * 2.2.14. 판매자회원 Password 수정.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            ModifyPasswordSacReq
	 * @return ModifyPasswordSacRes
	 */
	public ModifyPasswordSacRes modPassword(SacRequestHeader header, ModifyPasswordSacReq req);

	/**
	 * <pre>
	 * 2.2.15. 판매자 회원 계정 승인.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            ConfirmReq
	 * @return ConfirmRes
	 */
	public ConfirmRes confirm(SacRequestHeader header, ConfirmReq req);

	/**
	 * <pre>
	 * 2.2.16. 판매자 회원 전환 신청.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            ConversionClassSacReq
	 * @return ConversionClassSacRes
	 */
	public ConversionClassSacRes conversionClass(SacRequestHeader header, ConversionClassSacReq req);

	/**
	 * <pre>
	 * 2.2.17. 판매자 회원 계정 잠금.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            LockAccountReq
	 * @return LockAccountRes
	 */
	public LockAccountRes lockAccount(SacRequestHeader header, LockAccountReq req);

	/**
	 * <pre>
	 * 2.2.18. 판매자회원 실명 인증 정보 수정.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            ModifyRealNameSacReq
	 * @return ModifyRealNameSacRes
	 */
	public ModifyRealNameSacRes modRealName(SacRequestHeader header, ModifyRealNameSacReq req);

	/**
	 * <pre>
	 * 2.2.24. 판매자 회원 탈퇴.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            WithdrawReq
	 * @return WithdrawRes
	 */
	public WithdrawRes withdraw(SacRequestHeader header, WithdrawReq req);

	/**
	 * <pre>
	 * 판매자 회원 인증키 폐기.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            AbrogationAuthKeyReq
	 * @return AbrogationAuthKeyRes
	 */
	public RemoveAuthorizationKeySacRes remAuthorizationKey(SacRequestHeader header, RemoveAuthorizationKeySacReq req);

	/**
	 * <pre>
	 * 2.2.30. Flurry 삭제.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            RemoveFlurrySacReq
	 * @return RemoveFlurrySacRes
	 */
	public RemoveFlurrySacRes remFlurry(SacRequestHeader header, RemoveFlurrySacReq req);

	/**
	 * <pre>
	 * 2.2.32. Flurry 등록/수정.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            CreateFlurrySacRes
	 * @return CreateFlurrySacRes
	 */
	public CreateFlurrySacRes regFlurry(SacRequestHeader header, CreateFlurrySacReq req);

	/**
	 * <pre>
	 * 2.2.33. 가가입 이메일 수정.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            ModifyWaitEmailSacReq
	 * @return ModifyWaitEmailSacRes
	 */
	public ModifyWaitEmailSacRes modWaitEmail(SacRequestHeader header, ModifyWaitEmailSacReq req);

	/**
	 * <pre>
	 * 2.2.34. Flurry 단건 수정.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            ModifyFlurrySacReq
	 * @return ModifyFlurrySacRes
	 */
	public ModifyFlurrySacRes modFlurry(SacRequestHeader header, ModifyFlurrySacReq req);

	/**
	 * <pre>
	 * 2.2.35. 판매자회원 전환가입.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            CreateChangeSacReq
	 * @return CreateChangeSacRes
	 */
	public CreateChangeSacRes regChange(SacRequestHeader header, CreateChangeSacReq req);
}
