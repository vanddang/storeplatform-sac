/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.order.service;

import java.util.List;

import com.skplanet.storeplatform.purchase.client.order.vo.PrchsDtlMore;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreateCompletePurchaseSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.NotifyPaymentSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.VerifyOrderSacRes;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseOrderInfo;
import com.skplanet.storeplatform.sac.purchase.order.vo.VerifyOrderInfo;

/**
 * 
 * 구매 서비스 인터페이스
 * 
 * Updated on : 2014. 1. 3. Updated by : 이승택, nTels.
 */
public interface PurchaseOrderService {

	/**
	 * 
	 * <pre>
	 * 상품 구매요청 처리.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매요청 정보
	 * @return 생성된 구매이력 건수
	 */
	public int processPurchase(PurchaseOrderInfo purchaseOrderInfo);

	/**
	 * 
	 * <pre>
	 * 비과금 구매 처리.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매요청 정보
	 * @return 생성된 구매이력 건수
	 */
	public int processFreeChargePurchase(PurchaseOrderInfo purchaseOrderInfo);

	/**
	 * 
	 * <pre>
	 * Biz 쿠폰 발급 요청 처리.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매요청 정보
	 * @return 생성된 구매이력 건수
	 */
	public int processBizPurchase(PurchaseOrderInfo purchaseOrderInfo);

	/**
	 * 
	 * <pre>
	 * 구매인증.
	 * </pre>
	 * 
	 * @param verifyOrderInfo
	 *            구매인증 요청 정보
	 */
	public VerifyOrderSacRes verifyPurchaseOrder(VerifyOrderInfo verifyOrderInfo);

	/**
	 * 
	 * <pre>
	 * 유료구매 - 구매확정(구매상세내역 상태변경, 구매내역 저장, 결제내역 저장).
	 * </pre>
	 * 
	 * @param notifyPaymentReq
	 *            결제결과 정보
	 * @param tenantId
	 *            테넌트 ID
	 * @return 구매예약했던 구매이력 목록
	 */
	public List<PrchsDtlMore> confirmPurchase(NotifyPaymentSacReq notifyPaymentReq, String tenantId);

	/**
	 * 
	 * <pre>
	 * IAP 구매/결제 통합 구매이력 생성 요청.
	 * </pre>
	 * 
	 * @param req
	 *            구매/결제 통합 구매이력 생성 요청 VO
	 * @param tenantId
	 *            테넌트 ID
	 * @return 생성된 구매ID
	 */
	public String completeIapPurchase(CreateCompletePurchaseSacReq req, String tenantId);
}
