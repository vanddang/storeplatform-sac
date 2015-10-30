/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.cancel.sci;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.purchase.cancel.service.PurchaseCancelService;
import com.skplanet.storeplatform.purchase.client.cancel.sci.PurchaseCancelSCI;
import com.skplanet.storeplatform.purchase.client.cancel.vo.AutoPaymentScReq;
import com.skplanet.storeplatform.purchase.client.cancel.vo.AutoPaymentScRes;
import com.skplanet.storeplatform.purchase.client.cancel.vo.PurchaseCancelScReq;
import com.skplanet.storeplatform.purchase.client.cancel.vo.PurchaseCancelScRes;
import com.skplanet.storeplatform.purchase.client.cancel.vo.PurchaseScReq;
import com.skplanet.storeplatform.purchase.client.cancel.vo.PurchaseScRes;

/**
 * 구매 취소 SCI Controller.
 * 
 * Updated on : 2014. 1. 20. Updated by : nTels_cswoo81, nTels.
 */
@LocalSCI
public class PurchaseCancelSCIController implements PurchaseCancelSCI {

	@Autowired
	private PurchaseCancelService cancelService;

	@Override
	public PurchaseScRes getPurchase(PurchaseScReq purchaseScReq) {

		PurchaseScRes purchaseScRes = this.cancelService.getPurchase(purchaseScReq);

		return purchaseScRes;

	}

	@Override
	public PurchaseCancelScRes updatePurchaseCancel(PurchaseCancelScReq purchaseCancelScReq) {

		PurchaseCancelScRes purchaseCancelResSC = this.cancelService.updatePurchaseCancel(purchaseCancelScReq);

		return purchaseCancelResSC;
	}

	@Override
	public PurchaseCancelScRes updatePaymentError(PurchaseCancelScReq purchaseCancelScReq) {

		PurchaseCancelScRes purchaseCancelResSC = this.cancelService.updatePaymentError(purchaseCancelScReq);

		return purchaseCancelResSC;
	}

	@Override
	public AutoPaymentScRes getAutoPaymentInfo(@Validated AutoPaymentScReq autoPaymentScReq) {
		return this.cancelService.getAutoPaymentInfo(autoPaymentScReq);
	}

	/**
	 * 
	 * <pre>
	 * 현재일시 조회.
	 * </pre>
	 * 
	 * @return 현재일시
	 */
	@Override
	public String getNowDate() {

		return this.cancelService.getNowDate();
	}

	/**
	 * <pre>
	 * 특가쿠폰 사용 여부.
	 * </pre>
	 * 
	 * @param req
	 *            특가쿠폰 사용 여부 조회할 결제정보
	 * @return 특가쿠폰 사용 여부
	 */
	@Override
	public boolean useSpecialCoupon(PurchaseScReq req) {
		return this.cancelService.useSpecialCoupon(req);
	}

}
