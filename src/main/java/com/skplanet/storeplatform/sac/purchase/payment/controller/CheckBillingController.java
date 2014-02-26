/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.purchase.payment.vo.CheckBillingForSktCarrierSacReq;
import com.skplanet.storeplatform.sac.client.purchase.payment.vo.CheckBillingForSktCarrierSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.purchase.common.util.ConvertVO;
import com.skplanet.storeplatform.sac.purchase.payment.service.CheckBillingService;
import com.skplanet.storeplatform.sac.purchase.payment.vo.CheckBillingForSktCarrierSacParam;
import com.skplanet.storeplatform.sac.purchase.payment.vo.CheckBillingForSktCarrierSacResult;

/**
 * Class 설명
 * 
 * Updated on : 2014. 2. 26. Updated by : nTels_cswoo81, nTels.
 */
@Controller
@RequestMapping(value = "/purchase/payment/checkBilling")
public class CheckBillingController {

	@Autowired
	private CheckBillingService checkBillingService;

	@RequestMapping(value = "/sktCarrier/v1", method = RequestMethod.POST)
	@ResponseBody
	public CheckBillingForSktCarrierSacRes checkBillingForSktCarrier(SacRequestHeader sacRequestHeader,
			@RequestBody @Validated CheckBillingForSktCarrierSacReq checkBillingForSktCarrierSacReq) {

		CheckBillingForSktCarrierSacParam checkBillingForSktCarrierSacParam = this
				.convertReqForCheckBillingForSktCarrier(sacRequestHeader, checkBillingForSktCarrierSacReq);

		CheckBillingForSktCarrierSacResult checkBillingForSktCarrierSacResult = this.checkBillingService
				.checkBillingForSktCarrier(checkBillingForSktCarrierSacParam);

		return this.convertResForCheckBillingForSktCarrier(checkBillingForSktCarrierSacResult);

	}

	private CheckBillingForSktCarrierSacParam convertReqForCheckBillingForSktCarrier(SacRequestHeader sacRequestHeader,
			CheckBillingForSktCarrierSacReq checkBillingForSktCarrierSacRes) {

		CheckBillingForSktCarrierSacParam checkBillingForSktCarrierSacParam = new CheckBillingForSktCarrierSacParam();

		ConvertVO.convertPurchaseCommonSacReq(sacRequestHeader, checkBillingForSktCarrierSacRes,
				checkBillingForSktCarrierSacParam);

		return checkBillingForSktCarrierSacParam;
	}

	private CheckBillingForSktCarrierSacRes convertResForCheckBillingForSktCarrier(
			CheckBillingForSktCarrierSacResult checkBillingForSktCarrierSacResult) {

		CheckBillingForSktCarrierSacRes checkBillingForSktCarrierSacRes = new CheckBillingForSktCarrierSacRes();

		checkBillingForSktCarrierSacRes.setMonth(checkBillingForSktCarrierSacResult.getMonth());
		checkBillingForSktCarrierSacRes.setTotAmt(checkBillingForSktCarrierSacResult.getTotAmt());

		return checkBillingForSktCarrierSacRes;
	}

}
