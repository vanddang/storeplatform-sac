/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.payment.vo;

import com.skplanet.storeplatform.purchase.client.common.vo.PurchaseCommonScRes;

/**
 * Class 설명
 * 
 * Updated on : 2014. 2. 26. Updated by : nTels_cswoo81, nTels.
 */
public class CheckBillingForSktCarrierScRes extends PurchaseCommonScRes {

	private static final long serialVersionUID = 1L;

	private String month;
	private Double totAmt;
	private Double limitAmt;

	/**
	 * @return the month
	 */
	public String getMonth() {
		return this.month;
	}

	/**
	 * @param month
	 *            the month to set
	 */
	public void setMonth(String month) {
		this.month = month;
	}

	/**
	 * @return the totAmt
	 */
	public Double getTotAmt() {
		return this.totAmt;
	}

	/**
	 * @param totAmt
	 *            the totAmt to set
	 */
	public void setTotAmt(Double totAmt) {
		this.totAmt = totAmt;
	}

	/**
	 * @return the limitAmt
	 */
	public Double getLimitAmt() {
		return this.limitAmt;
	}

	/**
	 * @param limitAmt
	 *            the limitAmt to set
	 */
	public void setLimitAmt(Double limitAmt) {
		this.limitAmt = limitAmt;
	}

}
