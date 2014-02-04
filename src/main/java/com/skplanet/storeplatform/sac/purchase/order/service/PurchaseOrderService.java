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

import com.skplanet.storeplatform.purchase.client.common.vo.Prchs;
import com.skplanet.storeplatform.purchase.client.common.vo.PrchsDtl;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.NotifyPaymentSacReq;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseOrder;

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
	 * 무료구매 처리.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매요청 정보
	 */
	public void freePurchase(PurchaseOrder purchaseOrderInfo);

	/**
	 * 
	 * <pre>
	 * 유료구매 - 구매예약.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매요청 정보
	 */
	public void reservePurchase(PurchaseOrder purchaseOrderInfo);

	/**
	 * 
	 * <pre>
	 * 유료구매 - 구매예약 정보 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            테넌트 ID
	 * @param prchsId
	 *            구매 ID
	 * @param useUserKey
	 *            내부 회원 NO
	 */
	public PrchsDtl searchReservedPurchaseDetail(String tenantId, String prchsId, String useUserKey);

	/**
	 * 
	 * <pre>
	 * 유료구매 - 구매확정: 구매상세 내역 상태변경 & 구매 내역 저장 & (선물 경우)발송 상세 내역 저장.
	 * </pre>
	 * 
	 * @param prchs
	 *            구매 정보
	 */
	public void confirmPurchase(Prchs prchs);

	/**
	 * 
	 * <pre>
	 * 결제 내역 생성.
	 * </pre>
	 * 
	 * @param prchs
	 *            구매정보
	 * @param notifyParam
	 *            결제정보
	 */
	public void createPayment(Prchs prchs, NotifyPaymentSacReq notifyParam);

	/**
	 * 
	 * <pre>
	 * 유료구매 - 결제Page 준비작업.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매요청 정보
	 */
	public void setPaymentPageInfo(PurchaseOrder purchaseOrderInfo);
}
