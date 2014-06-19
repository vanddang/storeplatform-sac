/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.cancel.service;

import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelDetailSacParam;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelDetailSacResult;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelSacParam;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelSacResult;

/**
 * 구매 취소 Service Interface.
 * 
 * Updated on : 2014. 1. 16. Updated by : nTels_cswoo81, nTels.
 */
public interface PurchaseCancelService {

	/**
	 * <pre>
	 * 구매 리스트 취소.
	 * </pre>
	 * 
	 * @param purchaseCancelSacParam
	 *            purchaseCancelSacParam
	 * @return PurchaseCancelSacResult
	 */
	PurchaseCancelSacResult cancelPurchaseList(PurchaseCancelSacParam purchaseCancelSacParam);

	/**
	 * <pre>
	 * 구매 취소.
	 * Transaction을 위해 함수명 execute* 적용.
	 * </pre>
	 * 
	 * @param purchaseCommonSacParam
	 *            purchaseCommonSacParam
	 * @param purchaseCancelDetailSacParam
	 *            purchaseCancelDetailSacParam
	 * @return PurchaseCancelDetailSacResult
	 */
	PurchaseCancelDetailSacResult executePurchaseCancel(PurchaseCancelSacParam purchaseCancelSacParam,
			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam);

	/**
	 * <pre>
	 * T CASH 구매 취소.
	 * Transaction을 위해 함수명 execute* 적용.
	 * </pre>
	 * 
	 * @param purchaseCommonSacParam
	 *            purchaseCommonSacParam
	 * @param purchaseCancelDetailSacParam
	 *            purchaseCancelDetailSacParam
	 * @return PurchaseCancelDetailSacResult
	 */
	PurchaseCancelDetailSacResult executePurchaseCancelForTCash(PurchaseCancelSacParam purchaseCancelSacParam,
			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam);

	/**
	 * 
	 * <pre>
	 * 망상 구매 취소 Service.
	 * </pre>
	 * 
	 * @param purchaseCancelSacParam
	 *            purchaseCancelSacParam
	 * @param purchaseCancelDetailSacParam
	 *            purchaseCancelDetailSacParam
	 * @return PurchaseCancelDetailSacResult
	 */
	PurchaseCancelDetailSacResult cancelPurchaseForPaymentError(PurchaseCancelSacParam purchaseCancelSacParam,
			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam);

}
