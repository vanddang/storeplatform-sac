package com.skplanet.storeplatform.sac.member.seller.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeRes;
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
@RequestMapping(value = "/dev/member/seller")
public class SellerController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SellerController.class);

	@Autowired
	private SellerService sellerService;

	/**
	 * <pre>
	 * 5.2.1. 판매자 회원 가입.
	 * </pre>
	 * 
	 * @param req
	 * @return CreateRes
	 */
	@RequestMapping(value = "/create/v1", method = RequestMethod.POST)
	public @ResponseBody
	CreateRes createSeller(SacRequestHeader header, @RequestBody CreateReq req) {
		return this.sellerService.createSeller(header, req);
	}

	/**
	 * <pre>
	 * 5.2.16. 판매자회원 계정 잠금.
	 * </pre>
	 * 
	 * @param LockAccountReq
	 * @return LockAccountRes : JSON
	 */
	@RequestMapping(value = "/lockAccount/v1", method = RequestMethod.POST)
	public @ResponseBody
	LockAccountRes lockAccount(SacRequestHeader header, @RequestBody LockAccountReq req) {
		LOGGER.debug("### 5.2.16. 판매자회원 계정 잠금 [lockAccount] START ###");
		LOGGER.debug("request param : {}", req.toString());
		return this.sellerService.lockAccount(header, req);
	}

	/**
	 * <pre>
	 * 5.2.3. 판매자 회원 인증.
	 * </pre>
	 * 
	 * @param AuthorizeReq
	 * @return AuthorizeRes : JSON
	 */
	@RequestMapping(value = "/authorize/v1", method = RequestMethod.POST)
	public @ResponseBody
	AuthorizeRes authorize(SacRequestHeader header, @RequestBody AuthorizeReq req) {
		LOGGER.debug("### 5.2.3. 판매자 회원 인증 [authorize] START ###");
		LOGGER.debug("request param : {}", req.toString());
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
	WithdrawRes withdraw(@RequestBody WithdrawReq req) throws Exception {
		LOGGER.debug("### 5.2.3. 판매자 회원 인증 [authorize] START ###");
		LOGGER.debug("request param : {}", req.toString());
		return this.sellerService.withdraw(req);
	}
}
