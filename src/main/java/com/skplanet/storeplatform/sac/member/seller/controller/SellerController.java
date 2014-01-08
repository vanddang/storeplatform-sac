package com.skplanet.storeplatform.sac.member.seller.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.LockAccountReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.LockAccountRes;
import com.skplanet.storeplatform.sac.member.seller.service.SellerService;

/**
 * 판매자
 * 
 * Updated on : 2014. 1. 7. Updated by : 김경복, 부르칸.
 */
@Controller
// @RequestMapping(value="/member/seller")
public class SellerController {

	private static final Logger logger = LoggerFactory.getLogger(SellerController.class);

	@Autowired
	SellerService sellerService;

	// @RequestMapping(value="" , method = RequestMethod.POST)
	public @ResponseBody
	CreateRes createSeller() {
		logger.debug("createSeller , {}");
		return null;
	}

	/**
	 * <pre>
	 * 판매자회원 계정 잠금
	 * </pre>
	 * 
	 * @return LockAccountRes
	 * @throws Exception
	 */
	// @RequestMapping(value = "/lockAccount/v1", method = RequestMethod.POST)
	public @ResponseBody
	LockAccountRes lockAccount(@RequestBody LockAccountReq lockAccountReq) {
		return this.sellerService.updateStatusSeller(lockAccountReq);
	}
}
