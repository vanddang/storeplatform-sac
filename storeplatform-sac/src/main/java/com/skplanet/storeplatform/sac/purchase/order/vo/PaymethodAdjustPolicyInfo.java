/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.order.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 결제수단 재정의 정책 정보
 * 
 * Updated on : 2015. 2. 25. Updated by : 이승택, nTels.
 */
public class PaymethodAdjustPolicyInfo extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String paymethodCode; // 결제수단 코드 (P/P 코드)
	private double availAmt; // 결제가능 금액
	private int availPer; // 결제가능 퍼센트

	/**
	 * @return the paymethodCode
	 */
	public String getPaymethodCode() {
		return this.paymethodCode;
	}

	/**
	 * @param paymethodCode
	 *            the paymethodCode to set
	 */
	public void setPaymethodCode(String paymethodCode) {
		this.paymethodCode = paymethodCode;
	}

	/**
	 * @return the availAmt
	 */
	public double getAvailAmt() {
		return this.availAmt;
	}

	/**
	 * @param availAmt
	 *            the availAmt to set
	 */
	public void setAvailAmt(double availAmt) {
		this.availAmt = availAmt;
	}

	/**
	 * @return the availPer
	 */
	public int getAvailPer() {
		return this.availPer;
	}

	/**
	 * @param availPer
	 *            the availPer to set
	 */
	public void setAvailPer(int availPer) {
		this.availPer = availPer;
	}
}
