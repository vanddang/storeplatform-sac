/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.cancel.service;

import com.skplanet.storeplatform.purchase.client.cancel.vo.AutoPaymentScReq;
import com.skplanet.storeplatform.purchase.client.cancel.vo.AutoPaymentScRes;
import com.skplanet.storeplatform.purchase.client.cancel.vo.PurchaseCancelScReq;
import com.skplanet.storeplatform.purchase.client.cancel.vo.PurchaseCancelScRes;
import com.skplanet.storeplatform.purchase.client.cancel.vo.PurchaseScReq;
import com.skplanet.storeplatform.purchase.client.cancel.vo.PurchaseScRes;

/**
 * 구매 취소 관련 Service Interface
 * 
 * Updated on : 2014. 1. 20. Updated by : nTels_cswoo81, nTels.
 */
public interface PurchaseCancelService {

	/**
	 * 
	 * <pre>
	 * getPurchaseDtlList.
	 * </pre>
	 * 
	 * @param purchaseScReq
	 *            purchaseScReq
	 * @return PurchaseScRes
	 */
	PurchaseScRes getPurchase(PurchaseScReq purchaseScReq);

	/**
	 * <pre>
	 * 구매 취소 DB 업데이트.
	 * </pre>
	 * 
	 * @param purchaseCancelScReq
	 *            구매 취소 Param
	 * @return PurchaseCancelScRes
	 */
	PurchaseCancelScRes updatePurchaseCancel(PurchaseCancelScReq purchaseCancelScReq);

	/**
	 * <pre>
	 * 구매 결제취소 실패 시 DB 업데이트.
	 * </pre>
	 * 
	 * @param purchaseCancelScReq
	 *            구매 취소 Param
	 * @return PurchaseCancelScRes
	 */
	PurchaseCancelScRes updatePaymentError(PurchaseCancelScReq purchaseCancelScReq);

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param autoPaymentInfoScReq
	 *            autoPaymentInfoScReq
	 * @return AutoPaymentInfoScRes
	 */
	AutoPaymentScRes getAutoPaymentInfo(AutoPaymentScReq autoPaymentInfoScReq);

	/**
	 * 
	 * <pre>
	 * 현재일시 조회.
	 * </pre>
	 * 
	 * @return 현재일시
	 */
	String getNowDate();

	/**
	 * <pre>
	 * 특가쿠폰 사용 여부.
	 * </pre>
	 * 
	 * @param req
	 *            특가쿠폰 사용 여부 조회할 결제정보
	 * @return 특가쿠폰 사용 여부
	 */
	boolean useSpecialCoupon(PurchaseScReq req);
}
