/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.payment.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.sac.client.purchase.payment.vo.PaymentPolicySacReq;
import com.skplanet.storeplatform.sac.client.purchase.payment.vo.PaymentPolicySacRes;
import com.skplanet.storeplatform.sac.purchase.order.service.PurchaseOrderPolicyService;

/**
 * 결제정책 Implements
 * 
 * Updated on : 2014-01-10 Updated by : 양주원, 엔텔스.
 */
@Service
public class PaymentPolicyServiceImpl implements PaymentPolicyService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PurchaseOrderPolicyService purchaseOrderPolicyService;

	/**
	 * 결제정책 조회 기능을 제공한다.
	 * 
	 * @param request
	 *            구매내역요청
	 * @return PaymentPolicySacRes
	 */
	@Override
	public PaymentPolicySacRes searchPaymentPolicy(PaymentPolicySacReq request) {

		this.logger.info("PaymentPolicySac Request Param : {}", request);

		// SAC Response VO
		PaymentPolicySacRes response = new PaymentPolicySacRes();

		String value = null;
		if ("01".equals(request.getType())) {
			value = this.purchaseOrderPolicyService.searchtMileageSavePaymentMethod(request.getTenantId(), null);
		}

		/**
		 * 정책조회 API 호출
		 */
		response.setValue(value);

		return response;
	}
}
