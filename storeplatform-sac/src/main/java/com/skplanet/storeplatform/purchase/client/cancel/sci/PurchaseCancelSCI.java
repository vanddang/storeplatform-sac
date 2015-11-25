/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.cancel.sci;

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.purchase.client.cancel.vo.AutoPaymentScReq;
import com.skplanet.storeplatform.purchase.client.cancel.vo.AutoPaymentScRes;
import com.skplanet.storeplatform.purchase.client.cancel.vo.PurchaseCancelScReq;
import com.skplanet.storeplatform.purchase.client.cancel.vo.PurchaseCancelScRes;
import com.skplanet.storeplatform.purchase.client.cancel.vo.PurchaseScReq;
import com.skplanet.storeplatform.purchase.client.cancel.vo.PurchaseScRes;

/**
 * 구매 취소 SCI
 * 
 * Updated on : 2014. 1. 20. Updated by : nTels_cswoo81, nTels.
 */
@SCI
public interface PurchaseCancelSCI {

	/**
	 * 
	 * <pre>
	 * 구매 취소 시 필요한 모든 구매 정보 조회.
	 * </pre>
	 * 
	 * @param purchaseDtlListScReq
	 *            purchaseDtlListScReq
	 * @return PurchaseDtlListScRes
	 */
	PurchaseScRes getPurchase(PurchaseScReq purchaseScReq);

	/**
	 * 
	 * <pre>
	 * 구매 취소 DB 처리.
	 * </pre>
	 * 
	 * @param purchaseCancelScReq
	 *            purchaseCancelScReq
	 * @return PurchaseCancelScRes
	 */
	PurchaseCancelScRes updatePurchaseCancel(PurchaseCancelScReq purchaseCancelScReq);

	/**
	 * 
	 * <pre>
	 * 구매 결제취소 실패 시 DB 처리.
	 * </pre>
	 * 
	 * @param purchaseCancelScReq
	 *            purchaseCancelScReq
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
	AutoPaymentScRes getAutoPaymentInfo(AutoPaymentScReq autoPaymentScReq);

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
