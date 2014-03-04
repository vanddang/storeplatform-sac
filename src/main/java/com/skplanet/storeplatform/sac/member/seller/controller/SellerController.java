package com.skplanet.storeplatform.sac.member.seller.controller;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.member.vo.seller.AbrogationAuthKeyReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AbrogationAuthKeyRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeSimpleReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ConfirmReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ConfirmRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ConversionClassSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ConversionClassSacRes;
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
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyInformationSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyInformationSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyPasswordSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyPasswordSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyRealNameSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyRealNameSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyWaitEmailSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyWaitEmailSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.RemoveFlurrySacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.RemoveFlurrySacRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.WithdrawReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.WithdrawRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.seller.service.SellerService;

/**
 * 판매자 회원 등록/수정/인증/탈퇴 기능
 * 
 * Updated on : 2014. 1. 7. Updated by : 김경복, 부르칸.
 */
@Controller
@RequestMapping(value = "/member/seller")
public class SellerController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SellerController.class);

	@Autowired
	private SellerService sellerService;

	/**
	 * Data Binding.
	 */
	ObjectMapper objMapper = new ObjectMapper();

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
	@RequestMapping(value = "/create/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateRes createSeller(SacRequestHeader header, @RequestBody @Validated CreateReq req) {
		return this.sellerService.createSeller(header, req);
	}

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
	@RequestMapping(value = "/authorize/v1", method = RequestMethod.POST)
	@ResponseBody
	public AuthorizeRes authorize(SacRequestHeader header, @RequestBody @Validated AuthorizeReq req) {
		return this.sellerService.authorize(header, req);
	}

	/**
	 * <pre>
	 * 2.2.4. 판매자 회원 단순 인증.
	 * </pre>
	 * 
	 * @param AuthorizeReq
	 * @return AuthorizeRes : JSON
	 */
	@RequestMapping(value = "/authorizeSimple/v1", method = RequestMethod.POST)
	@ResponseBody
	public AuthorizeRes authorizeSimple(SacRequestHeader header, @RequestBody @Validated AuthorizeSimpleReq req) {
		return this.sellerService.authorizeSimple(header, req);
	}

	/**
	 * <pre>
	 * 2.2.11. 판매자 회원 기본정보 수정.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            ModifyInformationSacReq
	 * @return ModifyInformationRes
	 */
	@RequestMapping(value = "/modifyInformation/v1", method = RequestMethod.POST)
	@ResponseBody
	public ModifyInformationSacRes modifyInformation(SacRequestHeader header,
			@RequestBody @Validated ModifyInformationSacReq req) {
		return this.sellerService.modifyInformation(header, req);
	}

	/**
	 * <pre>
	 * 2.2.12. 판매자회원 정산 정보 수정.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            ModifyAccountInformationSacReq
	 * @return ModifyAccountInformationRes
	 */
	@RequestMapping(value = "/modifyAccountInformation/v1", method = RequestMethod.POST)
	@ResponseBody
	public ModifyAccountInformationSacRes modifyAccountInformation(SacRequestHeader header,
			@RequestBody @Validated ModifyAccountInformationSacReq req) {
		return this.sellerService.modifyAccountInformation(header, req);
	}

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
	@RequestMapping(value = "/modifyEmail/v1", method = RequestMethod.POST)
	@ResponseBody
	public ModifyEmailSacRes modifyEmail(SacRequestHeader header, @RequestBody @Validated ModifyEmailSacReq req) {
		return this.sellerService.modifyEmail(header, req);
	}

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
	@RequestMapping(value = "/modifyPassword/v1", method = RequestMethod.POST)
	@ResponseBody
	public ModifyPasswordSacRes modifyPassword(SacRequestHeader header, @RequestBody @Validated ModifyPasswordSacReq req) {
		return this.sellerService.modifyPassword(header, req);
	}

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
	@RequestMapping(value = "/confirm/v1", method = RequestMethod.POST)
	@ResponseBody
	public ConfirmRes confirm(SacRequestHeader header, @RequestBody @Validated ConfirmReq req) {
		return this.sellerService.confirm(header, req);
	}

	/**
	 * <pre>
	 * 2.2.16. 판매자 회원 전환 신청.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            ConversionClassSacReq
	 * @return ConversionClassResSacRes
	 */
	@RequestMapping(value = "/conversionClass/v1", method = RequestMethod.POST)
	@ResponseBody
	public ConversionClassSacRes conversionClass(SacRequestHeader header,
			@RequestBody @Validated ConversionClassSacReq req) {
		return this.sellerService.conversionClass(header, req);
	}

	/**
	 * <pre>
	 * 2.2.17. 판매자회원 계정 잠금.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            LockAccountReq
	 * @return LockAccountRes
	 */
	@RequestMapping(value = "/lockAccount/v1", method = RequestMethod.POST)
	@ResponseBody
	public LockAccountRes lockAccount(SacRequestHeader header, @RequestBody @Validated LockAccountReq req) {
		return this.sellerService.lockAccount(header, req);
	}

	/**
	 * <pre>
	 * 2.2.18. 판매자회원 실명 인증 정보 수정
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            ModifyRealNameSacReq
	 * @return ModifyRealNameSacRes
	 */
	@RequestMapping(value = "/modifyRealName/v1", method = RequestMethod.POST)
	@ResponseBody
	public ModifyRealNameSacRes modifyRealName(SacRequestHeader header, @RequestBody @Validated ModifyRealNameSacReq req) {
		return this.sellerService.modifyRealName(header, req);
	}

	/**
	 * <pre>
	 * 2.2.24. 판매자 회원 탈퇴.
	 * </pre>
	 * 
	 * @param WithdrawReq
	 * @return WithdrawRes : JSON
	 */
	@RequestMapping(value = "/withdraw/v1", method = RequestMethod.POST)
	@ResponseBody
	public WithdrawRes withdraw(SacRequestHeader header, @RequestBody @Validated WithdrawReq req) {
		return this.sellerService.withdraw(header, req);
	}

	/**
	 * <pre>
	 * 판매자 회원 인증키 폐기.
	 * </pre>
	 * 
	 * @param header
	 * @param req
	 */
	@RequestMapping(value = "/removeAuthorizationKey/v1", method = RequestMethod.POST)
	@ResponseBody
	public AbrogationAuthKeyRes abrogationAuthKey(SacRequestHeader header,
			@RequestBody @Validated AbrogationAuthKeyReq req) {
		return this.sellerService.abrogationAuthKey(header, req);
	}

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
	@RequestMapping(value = "/removeFlurry/v1", method = RequestMethod.POST)
	@ResponseBody
	public RemoveFlurrySacRes removeFlurry(SacRequestHeader header, @RequestBody @Validated RemoveFlurrySacReq req) {
		return this.sellerService.removeFlurry(header, req);
	}

	/**
	 * <pre>
	 * 2.2.32. Flurry 등록/수정.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            CreateFlurrySacReq
	 * @return CreateFlurrySacRes
	 */
	@RequestMapping(value = "/createFlurry/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateFlurrySacRes createFlurry(SacRequestHeader header, @RequestBody @Validated CreateFlurrySacReq req) {
		return this.sellerService.createFlurrySacRes(header, req);
	}

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
	@RequestMapping(value = "/modifyWaitEmail/v1", method = RequestMethod.POST)
	@ResponseBody
	public ModifyWaitEmailSacRes modifyWaitEmail(SacRequestHeader header,
			@RequestBody @Validated ModifyWaitEmailSacReq req) {
		return this.sellerService.modifyWaitEmailSacReq(header, req);
	}

}
