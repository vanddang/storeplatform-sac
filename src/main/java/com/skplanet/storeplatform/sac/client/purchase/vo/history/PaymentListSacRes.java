package com.skplanet.storeplatform.sac.client.purchase.vo.history;

import java.io.Serializable;
import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 결제내역리스트 응답.
 * 
 * Updated on : 2014. 01. 21. Updated by : 조용진, 엔텔스.
 */
public class PaymentListSacRes extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<PaymentSacRes> PaymentListSacRes; // 결제내역 리스트

	/**
	 * @return the paymentListSacRes
	 */
	public List<PaymentSacRes> getPaymentListSacRes() {
		return this.PaymentListSacRes;
	}

	/**
	 * @param paymentListSacRes
	 *            the paymentListSacRes to set
	 */
	public void setPaymentListSacRes(List<PaymentSacRes> paymentListSacRes) {
		this.PaymentListSacRes = paymentListSacRes;
	}

}
