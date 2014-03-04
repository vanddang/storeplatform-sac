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

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 결제 처리된 결제수단 정보
 * 
 * Updated on : 2014. 2. 28. Updated by : 이승택, nTels.
 */
public class PaymentInfo extends CommonInfo {
	private static final long serialVersionUID = 201402281L;

	private String tid; // PG사 거래번호
	private String paymentMtdCd; // 결제수단 코드
	private String paymentDt; // 결제 일시 (승인 일시)
	private double paymentAmt; // 결제 금액
	private String moid; // PG사 관리 결제수단 ID
	private String applNum; // 승인번호
	private String billKey; // 빌링키

	/**
	 * @return the tid
	 */
	public String getTid() {
		return this.tid;
	}

	/**
	 * @param tid
	 *            the tid to set
	 */
	public void setTid(String tid) {
		this.tid = tid;
	}

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
	public double getPaymentAmt() {
		return this.paymentAmt;
	}

	/**
	 * @param paymentAmt
	 *            the paymentAmt to set
	 */
	public void setPaymentAmt(double paymentAmt) {
		this.paymentAmt = paymentAmt;
	}

	/**
	 * @return the moid
	 */
	public String getMoid() {
		return this.moid;
	}

	/**
	 * @param moid
	 *            the moid to set
	 */
	public void setMoid(String moid) {
		this.moid = moid;
	}

	/**
	 * @return the applNum
	 */
	public String getApplNum() {
		return this.applNum;
	}

	/**
	 * @param applNum
	 *            the applNum to set
	 */
	public void setApplNum(String applNum) {
		this.applNum = applNum;
	}

	/**
	 * @return the billKey
	 */
	public String getBillKey() {
		return this.billKey;
	}

	/**
	 * @param billKey
	 *            the billKey to set
	 */
	public void setBillKey(String billKey) {
		this.billKey = billKey;
	}

}
