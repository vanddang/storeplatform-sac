/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.order.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 쇼핑 특가상품 구매된 건수 조회 응답 VO
 * 
 * Updated on : 2014. 3. 4. Updated by : 이승택, nTels.
 */
public class SearchShoppingSpecialCountScRes extends CommonInfo {
	private static final long serialVersionUID = 201403041L;

	private int dayCount; // 당일 구매 건수
	private int dayUserCount; // 구매자의 당일 구매 건수
	private int monthCount; // 당월 구매 건수
	private int monthUserCount; // 구매자의 당월 구매 건수

	/**
	 * @return the dayCount
	 */
	public int getDayCount() {
		return this.dayCount;
	}

	/**
	 * @param dayCount
	 *            the dayCount to set
	 */
	public void setDayCount(int dayCount) {
		this.dayCount = dayCount;
	}

	/**
	 * @return the dayUserCount
	 */
	public int getDayUserCount() {
		return this.dayUserCount;
	}

	/**
	 * @param dayUserCount
	 *            the dayUserCount to set
	 */
	public void setDayUserCount(int dayUserCount) {
		this.dayUserCount = dayUserCount;
	}

	/**
	 * @return the monthCount
	 */
	public int getMonthCount() {
		return this.monthCount;
	}

	/**
	 * @param monthCount
	 *            the monthCount to set
	 */
	public void setMonthCount(int monthCount) {
		this.monthCount = monthCount;
	}

	/**
	 * @return the monthUserCount
	 */
	public int getMonthUserCount() {
		return this.monthUserCount;
	}

	/**
	 * @param monthUserCount
	 *            the monthUserCount to set
	 */
	public void setMonthUserCount(int monthUserCount) {
		this.monthUserCount = monthUserCount;
	}

}
