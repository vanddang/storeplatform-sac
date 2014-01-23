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
public class PaymentListRes extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<PaymentRes> paymentListRes; // 결제내역 리스트

	/**
	 * @return the paymentListRes
	 */
	public List<PaymentRes> getPaymentListRes() {
		return this.paymentListRes;
	}

	/**
	 * @param paymentListRes
	 *            the paymentListRes to set
	 */
	public void setPaymentListRes(List<PaymentRes> paymentListRes) {
		this.paymentListRes = paymentListRes;
	}

}
