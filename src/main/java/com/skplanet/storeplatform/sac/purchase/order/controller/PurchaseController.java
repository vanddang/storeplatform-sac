package com.skplanet.storeplatform.sac.purchase.order.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseRes;
import com.skplanet.storeplatform.sac.purchase.order.service.PurchaseService;
import com.skplanet.storeplatform.sac.purchase.order.vo.PrePurchaseInfo;

/**
 * 구매 처리 컨트롤러
 * 
 * Updated on : 2014. 1. 3. Updated by : 이승택, nTels.
 */
@Controller
@RequestMapping("/purchase/order")
public class PurchaseController {
	private static final Logger logger = LoggerFactory.getLogger(PurchaseController.class);

	@Autowired
	private PurchaseService purchaseService;

	/**
	 * 
	 * <pre>
	 * 구매 요청 처리: 무료구매 경우는 구매완료 처리, 유료구매 경우는 PayPlanet 결제Page 요청을 위한 처리.
	 * </pre>
	 * 
	 * @param createPurchaseReq
	 *            구매요청 정보
	 * @return 구매요청 처리 결과: 무료구매완료 또는 결제page요청정보
	 */
	@RequestMapping(value = "/create/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreatePurchaseRes createPurchase(@RequestBody CreatePurchaseReq req) {
		logger.debug("PRCHS,INFO,CREATE,REQ,{}", req);

		PrePurchaseInfo purchaseInfo = new PrePurchaseInfo(req);
		purchaseInfo.setTenantId(req.getTenantId());
		purchaseInfo.setSystemId(req.getSystemId());
		purchaseInfo.setInsdUsermbrNo(req.getInsdUsermbrNo());
		purchaseInfo.setInsdDeviceId(req.getInsdDeviceId());
		purchaseInfo.setRecvTenantId(req.getRecvTenantId());
		purchaseInfo.setRecvInsdUsermbrNo(req.getRecvInsdUsermbrNo());
		purchaseInfo.setRecvInsdDeviceId(req.getRecvInsdDeviceId());

		// ------------------------------------------------------------------------------
		// 구매 전처리

		this.purchaseService.checkPurchase(purchaseInfo);

		// ------------------------------------------------------------------------------
		// 진행 처리: 무료구매완료 처리 | 결제Page 요청 준비작업

		if (purchaseInfo.getRealTotAmt() > 0) {
			purchaseInfo.setResultType("payment");
			this.purchaseService.setPaymentPageInfo(purchaseInfo);

		} else {
			purchaseInfo.setResultType("free");
			this.purchaseService.freePurchase(purchaseInfo);
		}

		// ------------------------------------------------------------------------------
		// 응답 세팅

		CreatePurchaseRes res = new CreatePurchaseRes();
		res.setResultType(purchaseInfo.getResultType());
		res.setPaymentPageUrl(purchaseInfo.getPaymentPageUrl());
		res.setPaymentPageParam(purchaseInfo.getPaymentPageParam());

		logger.debug("PRCHS,INFO,CREATE,RES,{}", res);
		return res;
	}
}
