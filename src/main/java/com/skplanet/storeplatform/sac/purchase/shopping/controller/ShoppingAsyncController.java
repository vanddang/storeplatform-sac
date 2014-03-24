/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.shopping.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.external.client.shopping.vo.BizCouponReq;
import com.skplanet.storeplatform.external.client.shopping.vo.BizCouponRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.purchase.common.util.PurchaseCommonUtils;
import com.skplanet.storeplatform.sac.purchase.shopping.service.ShoppingAsyncService;
//import com.skplanet.storeplatform.external.client.shopping.vo.BizCouponReq;
//import com.skplanet.storeplatform.external.client.shopping.vo.BizCouponRes;

/**
 * 쿠폰발급비동기응답 Controller
 * 
 * Updated on : 2014-01-10 Updated by : nTels_yjw, nTels.
 */
@Controller
@RequestMapping(value = "/purchase/shopping")
public class ShoppingAsyncController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ShoppingAsyncService shoppongAsyncService;

	@Autowired
	private PurchaseCommonUtils purchaseCommonUtils;

	/**
	 * 쿠폰발급비동기응답.
	 * 
	 * @param request
	 *            쿠폰정보
	 * @param requestHeader
	 *            공통헤더정보
	 * @return BizCouponRes
	 */

	@RequestMapping(value = "/couponAsync/v1", method = RequestMethod.POST)
	@ResponseBody
	public BizCouponRes getShoppingAsync(@RequestBody @Validated BizCouponReq request, SacRequestHeader sacRequestHeader) {

		this.logger.debug("######################################");
		this.logger.debug("######################################");
		this.logger.debug("######################################");
		this.logger.debug("######################################");
		this.logger.debug("######################################");

		// this.purchaseCommonUtils.setHeader(request, sacRequestHeader);

		return this.shoppongAsyncService.getShoppingAsync(request);
	}

}
