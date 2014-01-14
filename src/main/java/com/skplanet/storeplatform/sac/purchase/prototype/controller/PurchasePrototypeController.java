package com.skplanet.storeplatform.sac.purchase.prototype.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.purchase.client.prototype.vo.RequestPurchaseHistory;
import com.skplanet.storeplatform.sac.client.purchase.vo.prototype.MyPagePurchaseHistory;
import com.skplanet.storeplatform.sac.purchase.prototype.service.PurchasePrototypeService;

/**
 * 구매 처리 컨트롤러
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
	public MyPagePurchaseHistory searchPurchaseListTest(@RequestBody RequestPurchaseHistory paramVO) {
		this.log.debug("PurchasePrototypeController.searchPurchaseListTest,START,{}", paramVO);

		MyPagePurchaseHistory purchaseHistory = this.purchaseService.searchPurchaseList(paramVO);

		this.log.debug("PurchasePrototypeController.searchPurchaseListTest,END,{}", purchaseHistory.getHistory().size());
		return purchaseHistory;
	}
}
