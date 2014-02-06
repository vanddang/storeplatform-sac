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

import com.skplanet.storeplatform.sac.purchase.common.vo.PurchaseCommonSacParam;

/**
 * 쿠폰 발급가능여부 조회 Parameter VO.
 * 
 * Updated on : 2014. 2. 6. Updated by : nTels_cswoo81, nTels.
 */
public class CouponPublishAvailableSacParam extends PurchaseCommonSacParam {

	private static final long serialVersionUID = 1L;

	private String couponCode;
	private String itemCode;
	private Integer itemCount;
	private String mdn;

	/**
	 * @return the couponCode
	 */
	public String getCouponCode() {
		return this.couponCode;
	}

	/**
	 * @param couponCode
	 *            the couponCode to set
	 */
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	/**
	 * @return the itemCode
	 */
	public String getItemCode() {
		return this.itemCode;
	}

	/**
	 * @param itemCode
	 *            the itemCode to set
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * @return the itemCount
	 */
	public Integer getItemCount() {
		return this.itemCount;
	}

	/**
	 * @param itemCount
	 *            the itemCount to set
	 */
	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}

	/**
	 * @return the mdn
	 */
	public String getMdn() {
		return this.mdn;
	}

	/**
	 * @param mdn
	 *            the mdn to set
	 */
	public void setMdn(String mdn) {
		this.mdn = mdn;
	}

}
