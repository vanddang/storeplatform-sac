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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.purchase.vo.order.NotifyPaymentSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.NotifyPaymentSacRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.PPNotifyPaymentSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.PPNotifyPaymentSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.purchase.order.service.PayPlanetService;

/**
 * 구매 처리 컨트롤러 - EC대응
 *
 * Updated on : 15. 4. 9. Updated by : 황민규, SK 플래닛.
 */
@Controller
@RequestMapping("/purchase/order/pp")
public class PPPurchaseOrderController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PurchaseOrderController purchaseOrderController;
	@Autowired
	private PayPlanetService payPlanetService;

	/**
	 *
	 * <pre>
	 * 결제 처리 결과 Notice.
	 * EC 대응 규격
	 * </pre>
	 *
	 * @param ppNotifyPaymentSacReq
	 *            결제 결과 정보
	 * @return 결제 결과 처리 응답
	 */
	@RequestMapping(value = { "/notifyPayment/v1" }, method = RequestMethod.POST)
	@ResponseBody
	public PPNotifyPaymentSacRes ppNotifyPayment(@RequestBody @Validated PPNotifyPaymentSacReq ppNotifyPaymentSacReq,
			SacRequestHeader sacRequestHeader) {
		this.logger.info("PRCHS,ORDER,SAC,PP,NOTIFY,REQ,{}", ppNotifyPaymentSacReq);

		String tenantId = sacRequestHeader.getTenantHeader().getTenantId();
		NotifyPaymentSacReq notifyPaymentSacReq = this.payPlanetService.notifyPayment(tenantId, ppNotifyPaymentSacReq);
		NotifyPaymentSacRes notifyPaymentSacRes = purchaseOrderController.notifyPayment(notifyPaymentSacReq,
				sacRequestHeader);

		PPNotifyPaymentSacRes ppNotifyPaymentSacRes = new PPNotifyPaymentSacRes();
		ppNotifyPaymentSacRes.setPrchsId(notifyPaymentSacRes.getPrchsId());
		ppNotifyPaymentSacRes.setCount(notifyPaymentSacRes.getCount());
		ppNotifyPaymentSacRes.setShippingUrl(notifyPaymentSacRes.getShippingUrl());
		return ppNotifyPaymentSacRes;
	}
}
