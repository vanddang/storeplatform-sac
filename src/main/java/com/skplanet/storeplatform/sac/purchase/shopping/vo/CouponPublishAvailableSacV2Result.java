/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.shopping.vo;

import com.skplanet.storeplatform.sac.purchase.common.vo.PurchaseCommonSacResult;

/**
 * 쿠폰 발급가능여부 조회 Result VO.
 * 
 * Updated on : 2014. 2. 6. Updated by : nTels_cswoo81, nTels.
 */
public class CouponPublishAvailableSacV2Result extends PurchaseCommonSacResult {

	private static final long serialVersionUID = 1L;

	private int maxCount;
	private int maxMonth;
	private int maxMonthMdn;
	private int maxDay;
	private int maxDayMdn;

	private int buyMaxLimit;
	private int buyMaxLimitForGift;

	private int curCount;
	private int curMonth;
	private int curMonthMdn;
	private int curDay;
	private int curDayMdn;

	/**
	 * @return the maxCount
	 */
	public int getMaxCount() {
		return this.maxCount;
	}

	/**
	 * @param maxCount
	 *            the maxCount to set
	 */
	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

	/**
	 * @return the maxMonth
	 */
	public int getMaxMonth() {
		return this.maxMonth;
	}

	/**
	 * @param maxMonth
	 *            the maxMonth to set
	 */
	public void setMaxMonth(int maxMonth) {
		this.maxMonth = maxMonth;
	}

	/**
	 * @return the maxMonthMdn
	 */
	public int getMaxMonthMdn() {
		return this.maxMonthMdn;
	}

	/**
	 * @param maxMonthMdn
	 *            the maxMonthMdn to set
	 */
	public void setMaxMonthMdn(int maxMonthMdn) {
		this.maxMonthMdn = maxMonthMdn;
	}

	/**
	 * @return the maxDay
	 */
	public int getMaxDay() {
		return this.maxDay;
	}

	/**
	 * @param maxDay
	 *            the maxDay to set
	 */
	public void setMaxDay(int maxDay) {
		this.maxDay = maxDay;
	}

	/**
	 * @return the maxDayMdn
	 */
	public int getMaxDayMdn() {
		return this.maxDayMdn;
	}

	/**
	 * @param maxDayMdn
	 *            the maxDayMdn to set
	 */
	public void setMaxDayMdn(int maxDayMdn) {
		this.maxDayMdn = maxDayMdn;
	}

	/**
	 * @return the buyMaxLimit
	 */
	public int getBuyMaxLimit() {
		return this.buyMaxLimit;
	}

	/**
	 * @param buyMaxLimit
	 *            the buyMaxLimit to set
	 */
	public void setBuyMaxLimit(int buyMaxLimit) {
		this.buyMaxLimit = buyMaxLimit;
	}

	/**
	 * @return the curCount
	 */
	public int getCurCount() {
		return this.curCount;
	}

	/**
	 * @param curCount
	 *            the curCount to set
	 */
	public void setCurCount(int curCount) {
		this.curCount = curCount;
	}

	/**
	 * @return the curMonth
	 */
	public int getCurMonth() {
		return this.curMonth;
	}

	/**
	 * @param curMonth
	 *            the curMonth to set
	 */
	public void setCurMonth(int curMonth) {
		this.curMonth = curMonth;
	}

	/**
	 * @return the curMonthMdn
	 */
	public int getCurMonthMdn() {
		return this.curMonthMdn;
	}

	/**
	 * @param curMonthMdn
	 *            the curMonthMdn to set
	 */
	public void setCurMonthMdn(int curMonthMdn) {
		this.curMonthMdn = curMonthMdn;
	}

	/**
	 * @return the curDay
	 */
	public int getCurDay() {
		return this.curDay;
	}

	/**
	 * @param curDay
	 *            the curDay to set
	 */
	public void setCurDay(int curDay) {
		this.curDay = curDay;
	}

	/**
	 * @return the curDayMdn
	 */
	public int getCurDayMdn() {
		return this.curDayMdn;
	}

	/**
	 * @param curDayMdn
	 *            the curDayMdn to set
	 */
	public void setCurDayMdn(int curDayMdn) {
		this.curDayMdn = curDayMdn;
	}

	/**
	 * @return the buyMaxLimitForGift
	 */
	public int getBuyMaxLimitForGift() {
		return this.buyMaxLimitForGift;
	}

	/**
	 * @param buyMaxLimitForGift
	 *            the buyMaxLimitForGift to set
	 */
	public void setBuyMaxLimitForGift(int buyMaxLimitForGift) {
		this.buyMaxLimitForGift = buyMaxLimitForGift;
	}

}
