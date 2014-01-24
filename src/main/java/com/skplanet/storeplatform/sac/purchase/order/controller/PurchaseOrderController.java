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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.purchase.client.common.vo.Prchs;
import com.skplanet.storeplatform.purchase.client.common.vo.PrchsDtl;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.NotifyPaymentReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.NotifyPaymentRes;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
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

		// ------------------------------------------------------------------------------
		// 적합성 체크

		PurchaseOrderResult checkResult = this.validationService.validate(purchaseOrderInfo);
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
			this.orderService.reservePurchase(purchaseOrderInfo);

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

	/**
	 * 
	 * <pre>
	 * Pay Planet 측으로부터 결제 진행 결과 응답 받음.
	 * </pre>
	 * 
	 * @param notifyParam
	 *            결제 결과 정보
	 * @return 결제 결과 처리 응답
	 */
	@RequestMapping(value = "/notifyPayment/v1", method = RequestMethod.POST)
	@ResponseBody
	public NotifyPaymentRes notifyPayment(@RequestBody NotifyPaymentReq notifyParam) {

		if ("0".equals(notifyParam.getCode()) == false) {
			return new NotifyPaymentRes("0", "SUCCESS");
		}

		String mctSpareParam = notifyParam.getMctSpareParam();
		String[] arSpareParam = null;
		String[] arParamKeyValue = null;
		Map<String, String> spareParamMap = new HashMap<String, String>();

		if (StringUtils.isNotEmpty(mctSpareParam)) {
			arSpareParam = mctSpareParam.split("&");

			for (String param : arSpareParam) {
				arParamKeyValue = param.split("=");
				spareParamMap.put(arParamKeyValue[0], arParamKeyValue[1]);
			}
		}

		String prchsId = notifyParam.getOrderId();
		String tenantId = spareParamMap.get("tenantId");
		String systemId = spareParamMap.get("systemId");
		String useUserKey = spareParamMap.get("useUserKey");
		String networkTypeCd = spareParamMap.get("networkTypeCd");
		String currencyCd = spareParamMap.get("currencyCd");

		// 구매 예약 건 조회
		PrchsDtl prchsDtl = this.orderService.searchReservedPurchaseDetail(tenantId, prchsId, useUserKey);
		if (prchsDtl == null) {
			return new NotifyPaymentRes("1", "FAIL");
		}

		// ------------------------------------------------------------------------------
		// 구매 후 처리: 쇼핑상품 쿠폰 발급요청, 씨네21, 인터파크, 이메일 등등

		// TODO::

		// ------------------------------------------------------------------------------
		// 구매 확정 및 결제 내역 저장

		Prchs prchs = new Prchs();
		prchs.setTenantId(tenantId);
		prchs.setPrchsId(prchsId);
		prchs.setInsdUsermbrNo(useUserKey);
		prchs.setNetworkTypeCd(networkTypeCd);
		prchs.setCurrencyCd(currencyCd);

		// 구매 확정: 구매상세 내역 상태변경 & 구매 내역 저장 & (선물 경우)발송 상세 내역 저장
		this.orderService.confirmPurchase(prchs);

		// 결제 내역 저장
		prchs.setPrchsDt(prchsDtl.getPrchsDt());
		prchs.setTotAmt(prchsDtl.getTotAmt());
		if (PurchaseConstants.PRCHS_CASE_GIFT_CD.equals(prchsDtl.getPrchsCaseCd())) { // 선물경우, 발신자 기준
			prchs.setInsdUsermbrNo(prchsDtl.getSendInsdUsermbrNo());
			prchs.setInsdDeviceId(prchsDtl.getSendInsdDeviceId());
		}
		this.orderService.createPayment(prchs, notifyParam);

		// ------------------------------------------------------------------------------
		// 응답

		return new NotifyPaymentRes("0", "SUCCESS");
	}
}
