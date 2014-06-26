/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.purchase.vo.order;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 구매/결제 통합 구매이력 생성 요청 VO
 * 
 * Updated on : 2014. 6. 24. Updated by : 이승택, nTels.
 */
public class CreateCompletePurchaseSacReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private CreateCompletePurchaseInfoSac purchase;
	private List<PaymentInfo> paymentList;

	/**
	 * @return the purchase
	 */
	public CreateCompletePurchaseInfoSac getPurchase() {
		return this.purchase;
	}

	/**
	 * @param purchase
	 *            the purchase to set
	 */
	public void setPurchase(CreateCompletePurchaseInfoSac purchase) {
		this.purchase = purchase;
	}

	/**
	 * @return the paymentList
	 */
	public List<PaymentInfo> getPaymentList() {
		return this.paymentList;
	}

	/**
	 * @param paymentList
	 *            the paymentList to set
	 */
	public void setPaymentList(List<PaymentInfo> paymentList) {
		this.paymentList = paymentList;
	}

}
