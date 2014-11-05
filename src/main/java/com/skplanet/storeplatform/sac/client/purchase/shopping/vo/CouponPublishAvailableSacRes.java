/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.purchase.shopping.vo;

import com.skplanet.storeplatform.sac.client.purchase.common.vo.PurchaseCommonSacRes;

/**
 * 쿠폰 발급가능여부 조회 응답 VO.
 * 
 * Updated on : 2014. 2. 6. Updated by : nTels_cswoo81, nTels.
 */
public class CouponPublishAvailableSacRes extends PurchaseCommonSacRes {

	private static final long serialVersionUID = 1L;

	private String statusCd;
	private String statusMsg;

	private int saleMonthLimit = 0; // 월 판매 가능 쿠폰 개수
	private int saleDayLimit = 0; // 일 판매 가능 쿠폰 개수
	private int saleMonthLimitPerson = 0; // Mdn별 월 판매 가능 쿠폰 개수
	private int saleDayLimitPerson = 0; // Mdn별 일 판매 가능 쿠폰 개수
	private int saleOnceLimit = 0; // 한번에 구매 가능한 쿠폰 개수

	/**
	 * @return the statusCd
	 */
	public String getStatusCd() {
		return this.statusCd;
	}

	/**
	 * @param statusCd
	 *            the statusCd to set
	 */
	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}

	/**
	 * @return the statusMsg
	 */
	public String getStatusMsg() {
		return this.statusMsg;
	}

	/**
	 * @param statusMsg
	 *            the statusMsg to set
	 */
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}

	/**
	 * @return the saleMonthLimit
	 */
	public int getSaleMonthLimit() {
		return this.saleMonthLimit;
	}

	/**
	 * @param saleMonthLimit
	 *            the saleMonthLimit to set
	 */
	public void setSaleMonthLimit(int saleMonthLimit) {
		this.saleMonthLimit = saleMonthLimit;
	}

	/**
	 * @return the saleDayLimit
	 */
	public int getSaleDayLimit() {
		return this.saleDayLimit;
	}

	/**
	 * @param saleDayLimit
	 *            the saleDayLimit to set
	 */
	public void setSaleDayLimit(int saleDayLimit) {
		this.saleDayLimit = saleDayLimit;
	}

	/**
	 * @return the saleMonthLimitPerson
	 */
	public int getSaleMonthLimitPerson() {
		return this.saleMonthLimitPerson;
	}

	/**
	 * @param saleMonthLimitPerson
	 *            the saleMonthLimitPerson to set
	 */
	public void setSaleMonthLimitPerson(int saleMonthLimitPerson) {
		this.saleMonthLimitPerson = saleMonthLimitPerson;
	}

	/**
	 * @return the saleDayLimitPerson
	 */
	public int getSaleDayLimitPerson() {
		return this.saleDayLimitPerson;
	}

	/**
	 * @param saleDayLimitPerson
	 *            the saleDayLimitPerson to set
	 */
	public void setSaleDayLimitPerson(int saleDayLimitPerson) {
		this.saleDayLimitPerson = saleDayLimitPerson;
	}

	/**
	 * @return the saleOnceLimit
	 */
	public int getSaleOnceLimit() {
		return this.saleOnceLimit;
	}

	/**
	 * @param saleOnceLimit
	 *            the saleOnceLimit to set
	 */
	public void setSaleOnceLimit(int saleOnceLimit) {
		this.saleOnceLimit = saleOnceLimit;
	}

}
