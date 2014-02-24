package com.skplanet.storeplatform.sac.client.purchase.vo.history;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 결제내역 응답.
 * 
 * Updated on : 2014. 01. 21. Updated by : 조용진, 엔텔스.
 */
public class PaymentSacRes extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String paymentMtdCd; // 결제방법코드
	private String paymentDt; // 결제일시
	private Integer paymentAmt; // 결제금액

	/**
	 * @return the paymentMtdCd
	 */
	public String getPaymentMtdCd() {
		return this.paymentMtdCd;
	}

	/**
	 * @param paymentMtdCd
	 *            the paymentMtdCd to set
	 */
	public void setPaymentMtdCd(String paymentMtdCd) {
		this.paymentMtdCd = paymentMtdCd;
	}

	/**
	 * @return the paymentDt
	 */
	public String getPaymentDt() {
		return this.paymentDt;
	}

	/**
	 * @param paymentDt
	 *            the paymentDt to set
	 */
	public void setPaymentDt(String paymentDt) {
		this.paymentDt = paymentDt;
	}

	/**
	 * @return the paymentAmt
	 */
	public Integer getPaymentAmt() {
		return this.paymentAmt;
	}

	/**
	 * @param paymentAmt
	 *            the paymentAmt to set
	 */
	public void setPaymentAmt(Integer paymentAmt) {
		this.paymentAmt = paymentAmt;
	}

}
