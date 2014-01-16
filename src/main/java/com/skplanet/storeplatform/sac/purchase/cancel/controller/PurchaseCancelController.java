/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.cancel.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.purchase.cancel.vo.PurchaseCancelReq;
import com.skplanet.storeplatform.sac.client.purchase.cancel.vo.PurchaseCancelRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelParam;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelResult;

/**
 * 구매 취소 Controller.
 * 
 * Updated on : 2014. 1. 16. Updated by : nTels_cswoo81, nTels.
 */
@Controller
@RequestMapping(value = "/purchase/cancel")
public class PurchaseCancelController {

	private static final Logger logger = LoggerFactory.getLogger(PurchaseCancelController.class);

	@RequestMapping(value = "/v1", method = RequestMethod.POST)
	@ResponseBody
	public PurchaseCancelRes cancelPurchase(SacRequestHeader sacRequestHeader,
			@RequestBody PurchaseCancelReq purchaseCancelReq) {

		logger.debug("PurchaseCancelController.cancelPurchase request : {}", purchaseCancelReq);

		PurchaseCancelParam purchaseCancelParam = new PurchaseCancelParam();
		PurchaseCancelResult purchaseCancelResult = new PurchaseCancelResult();
		PurchaseCancelRes purchaseCancelRes = new PurchaseCancelRes();

		logger.debug("PurchaseCancelController.cancelPurchase response : {}", purchaseCancelRes);

		return null;
	}
}
