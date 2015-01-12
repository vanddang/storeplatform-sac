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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.purchase.payment.vo.PaymentPolicySacReq;
import com.skplanet.storeplatform.sac.client.purchase.payment.vo.PaymentPolicySacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.purchase.common.util.PurchaseCommonUtils;
import com.skplanet.storeplatform.sac.purchase.payment.service.PaymentPolicyService;

/**
 * 결제정책 Controller
 * 
 * Updated on : 2014-01-10 Updated by : 양주원, 엔텔스.
 */
@Controller
@RequestMapping(value = "/purchase/payment")
public class PaymentPolicyController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PaymentPolicyService paymentPolicyService;

	@Autowired
	private PurchaseCommonUtils purchaseCommonUtils;

	/**
	 * 결제정책 조회 기능을 제공한다.
	 * 
	 * @param request
	 *            결제정책요청
	 * @param requestHeader
	 *            공통헤더정보
	 * @return PaymentPolicySacRes
	 */
	@RequestMapping(value = "/policy/search/v1", method = RequestMethod.POST)
	@ResponseBody
	public PaymentPolicySacRes searchPaymentPolicy(@RequestBody @Validated PaymentPolicySacReq request,
			SacRequestHeader sacRequestHeader) {

		this.purchaseCommonUtils.setHeader(request, sacRequestHeader);

		return this.paymentPolicyService.searchPaymentPolicy(request);
	}

}
