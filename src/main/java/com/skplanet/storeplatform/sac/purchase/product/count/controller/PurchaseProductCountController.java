/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.product.count.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.purchase.product.count.vo.PurchaseProductCountSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.purchase.product.count.service.PurchaseProductCountService;
import com.skplanet.storeplatform.sac.purchase.product.count.vo.PurchaseProductCountSacParam;
import com.skplanet.storeplatform.sac.purchase.product.count.vo.PurchaseProductCountSacResult;

/**
 * 구매 상품 건수 처리 Controller.
 * 
 * Updated on : 2014. 1. 16. Updated by : nTels_cswoo81, nTels.
 */
@Controller
@RequestMapping(value = "/purchase/product/count")
public class PurchaseProductCountController {

	@Autowired
	private PurchaseProductCountService purchaseProductCountService;

	/**
	 * 
	 * <pre>
	 * 구매 상품 건수 업데이트를 실행한다.
	 * </pre>
	 * 
	 * @param sacRequestHeader
	 * @param perCnt
	 * @param guid
	 * @return
	 */
	@RequestMapping(value = "/execute/v1", method = { RequestMethod.GET })
	@ResponseBody
	public PurchaseProductCountSacRes executePurchaseProductCount(SacRequestHeader sacRequestHeader,
			@RequestHeader("x-sac-guid") String guid,
			@RequestParam(value = "perCnt", required = false, defaultValue = "10") Integer perCnt) {

		PurchaseProductCountSacParam purchaseProductCountSacParam = new PurchaseProductCountSacParam();
		purchaseProductCountSacParam.setGuid(guid);
		purchaseProductCountSacParam.setPerCount(perCnt);

		PurchaseProductCountSacResult purchaseProductCountSacResult = this.purchaseProductCountService
				.executePurchaseProductCount(purchaseProductCountSacParam);

		PurchaseProductCountSacRes purchaseProductCountSacRes = new PurchaseProductCountSacRes();
		purchaseProductCountSacRes.setTotCnt(purchaseProductCountSacResult.getTotCnt());
		purchaseProductCountSacRes.setSuccessCnt(purchaseProductCountSacResult.getSuccessCnt());
		purchaseProductCountSacRes.setFailCnt(purchaseProductCountSacResult.getFailCnt());

		return purchaseProductCountSacRes;

	}

}
