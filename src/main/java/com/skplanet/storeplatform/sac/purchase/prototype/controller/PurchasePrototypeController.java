package com.skplanet.storeplatform.sac.purchase.prototype.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.purchase.vo.prototype.CheckPurchaseReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.prototype.CheckPurchaseRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.prototype.MyPagePurchaseHistoryReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.prototype.MyPagePurchaseHistoryRes;
import com.skplanet.storeplatform.sac.purchase.prototype.service.PurchasePrototypeService;

/**
 * 구매 Prototype 처리 컨트롤러
 * 
 * Updated on : 2014. 1. 3. Updated by : 이승택, nTels.
 */
@Controller
@RequestMapping("/purchase/prototype")
public class PurchasePrototypeController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PurchasePrototypeService purchaseService;

	/**
	 * <pre>
	 * MyPage 구매내역 조회.
	 * </pre>
	 * 
	 * @param paramVO
	 *            구매내역 조회조건
	 * @return MyPage 구매내역
	 */
	@RequestMapping(value = "/list/v1", method = RequestMethod.POST)
	@ResponseBody
	public MyPagePurchaseHistoryRes searchPurchaseListTest(@RequestBody MyPagePurchaseHistoryReq req) {
		this.log.debug("PurchasePrototypeController.searchPurchaseListTest,START,{}", req);

		// 구매내역 조회
		MyPagePurchaseHistoryRes res = this.purchaseService.searchPurchaseList(req);

		this.log.debug("PurchasePrototypeController.searchPurchaseListTest,END,{}", res.getHistory().size());
		return res;
	}

	/**
	 * <pre>
	 * 기구매 체크.
	 * </pre>
	 * 
	 * @param paramVO
	 *            기구매체크 조건
	 * @return MyPage 구매내역
	 */
	@RequestMapping(value = "/check/v1", method = RequestMethod.POST)
	@ResponseBody
	public CheckPurchaseRes checkPurchase(@RequestBody CheckPurchaseReq req) {
		this.log.debug("PurchasePrototypeController.checkPurchase,START,{}", req);

		// 기구매체크
		CheckPurchaseRes res = this.purchaseService.checkPurchase(req);

		this.log.debug("PurchasePrototypeController.checkPurchase,END,{}");
		return res;
	}
}
