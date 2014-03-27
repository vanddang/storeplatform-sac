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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.purchase.product.count.vo.PurchaseProductCountSacReq;
import com.skplanet.storeplatform.sac.client.purchase.product.count.vo.PurchaseProductCountSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.purchase.product.count.service.PurchaseProductCountService;
import com.skplanet.storeplatform.sac.purchase.product.count.vo.PurchaseProductCountSacParam;

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
	 * 구매 취소(사용자) 요청.
	 * </pre>
	 * 
	 * @param sacRequestHeader
	 * @param purchaseCancelReq
	 * @return
	 */
	@RequestMapping(value = "/execute/v1", method = RequestMethod.POST)
	@ResponseBody
	public PurchaseProductCountSacRes executePurchaseProductCount(SacRequestHeader sacRequestHeader,
			@RequestBody @Validated PurchaseProductCountSacReq purchaseProductCountSacReq,
			@RequestHeader("x-sac-guid") String guid) {

		PurchaseProductCountSacParam purchaseProductCountSacParam = new PurchaseProductCountSacParam();
		purchaseProductCountSacParam.setGuid(guid);
		purchaseProductCountSacParam.setPerCount(purchaseProductCountSacReq.getPerCount());

		this.purchaseProductCountService.executePurchaseProductCount(purchaseProductCountSacParam);

		PurchaseProductCountSacRes purchaseProductCountSacRes = new PurchaseProductCountSacRes();

		return purchaseProductCountSacRes;

	}

}
