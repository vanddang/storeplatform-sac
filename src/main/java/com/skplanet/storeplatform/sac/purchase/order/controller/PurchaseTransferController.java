/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.purchase.vo.order.PurchaseTransferSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.PurchaseTransferSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.purchase.common.util.PurchaseCommonUtils;
import com.skplanet.storeplatform.sac.purchase.order.service.PurchaseTransferService;

/**
 * SAP 구매내역 이관 컨트롤러
 * 
 * Updated on : 2015. 2. 4. Updated by : 양주원, nTels.
 */
@Controller
@RequestMapping("/purchase/order")
public class PurchaseTransferController {

	@Autowired
	private PurchaseCommonUtils purchaseCommonUtils;

	@Autowired
	private PurchaseTransferService purchaseTransferService;

	/**
	 * 
	 * <pre>
	 * SAP 구매내역 이관처리.
	 * </pre>
	 * 
	 * @param request
	 *            구매이관요청 정보
	 * @return CreatePurchaseSacRes
	 */
	@RequestMapping(value = "/transfer/v1", method = RequestMethod.POST)
	@ResponseBody
	public PurchaseTransferSacRes createPurchaseTransfer(@RequestBody @Validated PurchaseTransferSacReq request,
			SacRequestHeader sacRequestHeader) {

		this.purchaseCommonUtils.setHeader(request, sacRequestHeader);

		return this.purchaseTransferService.createPurchaseTransfer(request);
	}

}
