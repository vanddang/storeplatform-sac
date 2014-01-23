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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseRes;
import com.skplanet.storeplatform.sac.purchase.order.service.PurchaseOrderPolicyService;
import com.skplanet.storeplatform.sac.purchase.order.service.PurchaseOrderService;
import com.skplanet.storeplatform.sac.purchase.order.service.PurchaseOrderValidationService;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseOrder;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseOrderResult;

/**
 * 구매 처리 컨트롤러
 * 
 * Updated on : 2014. 1. 3. Updated by : 이승택, nTels.
 */
@Controller
@RequestMapping("/purchase/order")
public class PurchaseOrderController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PurchaseOrderService orderService;
	@Autowired
	private PurchaseOrderValidationService validationService;
	@Autowired
	private PurchaseOrderPolicyService policyService;

	/**
	 * 
	 * <pre>
	 * 구매 요청 처리: 무료구매 경우는 구매완료 처리, 유료구매 경우는 PayPlanet 결제Page 요청을 위한 처리.
	 * </pre>
	 * 
	 * @param req
	 *            구매요청 정보
	 * @return 구매요청 처리 결과: 무료구매완료 또는 결제page요청정보
	 */
	@RequestMapping(value = "/create/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreatePurchaseRes createPurchase(@RequestBody CreatePurchaseReq req) {
		this.logger.debug("PRCHS,INFO,CREATE,REQ,{}", req);

		// ------------------------------------------------------------------------------
		// 구매정보 개체 세팅

		PurchaseOrder purchaseOrderInfo = new PurchaseOrder(req);
		purchaseOrderInfo.setTenantId(req.getTenantId()); // 구매(선물발신) 테넌트 ID
		purchaseOrderInfo.setSystemId(req.getSystemId()); // 구매(선물발신) 시스템 ID
		purchaseOrderInfo.setUserKey(req.getInsdUsermbrNo()); // 구매(선물발신) 내부 회원 번호
		purchaseOrderInfo.setDeviceKey(req.getInsdDeviceId()); // 구매(선물발신) 내부 디바이스 ID
		purchaseOrderInfo.setRecvTenantId(req.getRecvTenantId()); // 선물수신 테넌트 ID
		purchaseOrderInfo.setRecvUserKey(req.getRecvInsdUsermbrNo()); // 선물수신 내부 회원 번호
		purchaseOrderInfo.setRecvDeviceKey(req.getRecvInsdDeviceId()); // 선물수신 내부 디바이스 ID
		purchaseOrderInfo.setPrchsReqPathCd(req.getPrchsReqPathCd()); // 구매 요청 경로 코드
		purchaseOrderInfo.setMid(req.getMid()); // 가맹점 ID
		purchaseOrderInfo.setAuthKey(req.getAuthKey()); // 가맹점 인증키
		purchaseOrderInfo.setResultUrl(req.getResultUrl()); // 결과처리 URL
		purchaseOrderInfo.setCurrencyCd(req.getCurrencyCd()); // 통화 코드
		purchaseOrderInfo.setTotAmt(req.getTotAmt()); // 총 결제 금액
		purchaseOrderInfo.setClientIp(req.getClientIp()); // 클라이언트 IP
		purchaseOrderInfo.setNetworkTypeCd(req.getNetworkTypeCd()); // 네트워크 타입 코드
		purchaseOrderInfo.setPrchsCaseCd(req.getPrchsCaseCd()); // 구매 유형 코드

		purchaseOrderInfo.setRealTotAmt(req.getTotAmt()); // 최종 결제 총 금액

		PurchaseOrderResult checkResult = null;

		// ------------------------------------------------------------------------------
		// 적합성 체크

		// 회원 적합성 체크
		checkResult = this.validationService.validateMember(purchaseOrderInfo);
		if (checkResult != null) { // TODO:: 에러 - 예외처리 - 종료
			;
		}

		// 상품 적합성 체크
		checkResult = this.validationService.validateProduct(purchaseOrderInfo);
		if (checkResult != null) { // TODO:: 에러 - 예외처리 - 종료
			;
		}

		// 구매 적합성 체크
		checkResult = this.validationService.validatePurchase(purchaseOrderInfo);
		if (checkResult != null) { // TODO:: 에러 - 예외처리 - 종료
			;
		}

		// ------------------------------------------------------------------------------
		// 제한정책 체크

		checkResult = this.policyService.checkPolicy(purchaseOrderInfo);
		if (checkResult != null) { // TODO:: 에러 - 예외처리 - 종료
			;
		}

		// ------------------------------------------------------------------------------
		// 진행 처리: 무료구매완료 처리 | 결제Page 요청 준비작업

		if (purchaseOrderInfo.getRealTotAmt() > 0) {
			purchaseOrderInfo.setResultType("payment");

			// 구매예약

			// 결제Page 정보 세팅
			this.orderService.setPaymentPageInfo(purchaseOrderInfo);

		} else {
			purchaseOrderInfo.setResultType("free");
			// 구매생성 (무료)
			this.orderService.freePurchase(purchaseOrderInfo);
		}

		// ------------------------------------------------------------------------------
		// 응답 세팅

		CreatePurchaseRes res = new CreatePurchaseRes();
		res.setResultType(purchaseOrderInfo.getResultType());
		res.setPrchsId(purchaseOrderInfo.getPrchsId());
		res.setPaymentPageUrl(purchaseOrderInfo.getPaymentPageUrl());
		res.setPaymentPageParam(purchaseOrderInfo.getPaymentPageParam());

		this.logger.debug("PRCHS,INFO,CREATE,RES,{}", res);
		return res;
	}
}
