package com.skplanet.storeplatform.sac.member.seller.controller;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.api.util.StringUtil;
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
import com.skplanet.storeplatform.sac.member.seller.service.SellerService;

/**
 * 판매자 회원 등록/수정/탈퇴 기능
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
	 * 5.2.1. 판매자 회원 가입.
	 * </pre>
	 * 
	 * @param req
	 * @return CreateRes
	 * @throws Exception
	 */
	@RequestMapping(value = "/create/v1", method = RequestMethod.POST)
	public @ResponseBody
	CreateRes createSeller(SacRequestHeader header, @RequestBody @Validated CreateReq req) throws Exception {

		return this.sellerService.createSeller(header, req);
	}

	/**
	 * <pre>
	 * 5.2.16. 판매자회원 계정 잠금.
	 * </pre>
	 * 
	 * @param LockAccountReq
	 * @return LockAccountRes : JSON
	 * @throws Exception
	 */
	@RequestMapping(value = "/lockAccount/v1", method = RequestMethod.POST)
	public @ResponseBody
	LockAccountRes lockAccount(SacRequestHeader header, @RequestBody @Validated LockAccountReq req, BindingResult result)
			throws Exception {
		return this.sellerService.lockAccount(header, req);
	}

	/**
	 * <pre>
	 * 5.2.3. 판매자 회원 인증.
	 * </pre>
	 * 
	 * @param AuthorizeReq
	 * @return AuthorizeRes : JSON
	 * @throws Exception
	 */
	@RequestMapping(value = "/authorize/v1", method = RequestMethod.POST)
	public @ResponseBody
	AuthorizeRes authorize(SacRequestHeader header, @RequestBody @Validated AuthorizeReq req) throws Exception {
		return this.sellerService.authorize(header, req);
	}

	/**
	 * <pre>
	 * 5.2.24. 판매자 회원 탈퇴.
	 * </pre>
	 * 
	 * @param WithdrawReq
	 * @return WithdrawRes : JSON
	 * @throws Exception
	 */
	@RequestMapping(value = "/withdraw/v1", method = RequestMethod.POST)
	public @ResponseBody
	WithdrawRes withdraw(SacRequestHeader header, @RequestBody @Validated WithdrawReq req) throws Exception {
		LOGGER.debug("### 5.2.3. 판매자 회원 인증 [authorize] START ###");
		LOGGER.debug("Request : {}", this.objMapper.writeValueAsString(req));

		String secedeReasonCode = StringUtil.nvl(req.getSecedeReasonCode(), "");
		String secedeReasonMessage = StringUtil.nvl(req.getSecedeReasonMessage(), "");
		String sellerKey = StringUtil.nvl(req.getSellerKey(), "");

		if (secedeReasonCode.equals("") | secedeReasonMessage.equals("") | sellerKey.equals("")) {
			throw new Exception("필수 파라미터 미존재");
		}

		return this.sellerService.withdraw(header, req);
	}

	/**
	 * <pre>
	 * 판매자 회원 인증키 생성/연장.
	 * </pre>
	 * 
	 * @param header
	 * @param req
	 * @throws Exception
	 */
	@RequestMapping(value = "/createAuthorizationKey/v1", method = RequestMethod.POST)
	public @ResponseBody
	CreateAuthKeyRes createAuthKey(SacRequestHeader header, @RequestBody @Validated CreateAuthKeyReq req)
			throws Exception {

		LOGGER.debug("Request : {}", this.objMapper.writeValueAsString(req));
		String sellerKey = StringUtil.nvl(req.getSellerKey(), "");
		String ipAddress = StringUtil.nvl(req.getIpAddress(), "");
		if (sellerKey.equals("") || ipAddress.equals("")) {
			throw new Exception("필수 파라미터 미존재");
		}

		return this.sellerService.createAuthKey(header, req);
	}

	/**
	 * <pre>
	 * 판매자 회원 인증키 폐기.
	 * </pre>
	 * 
	 * @param header
	 * @param req
	 * @throws Exception
	 */
	@RequestMapping(value = "/removeAuthorizationKey/v1", method = RequestMethod.POST)
	public @ResponseBody
	AbrogationAuthKeyRes abrogationAuthKey(SacRequestHeader header, @RequestBody @Validated AbrogationAuthKeyReq req)
			throws Exception {

		LOGGER.debug("Request : {}", this.objMapper.writeValueAsString(req));
		String sellerKey = StringUtil.nvl(req.getSellerKey(), "");
		if (sellerKey.equals("")) {
			throw new Exception("필수 파라미터 미존재");
		}

		return this.sellerService.abrogationAuthKey(header, req);
	}

}
