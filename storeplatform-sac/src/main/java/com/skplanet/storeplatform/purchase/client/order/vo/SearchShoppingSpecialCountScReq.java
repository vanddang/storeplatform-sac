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
 * 쇼핑 특가상품 구매된 건수 조회 요청 VO
 * 
 * Updated on : 2014. 3. 4. Updated by : 이승택, nTels.
 */
public class SearchShoppingSpecialCountScReq extends CommonInfo {
	private static final long serialVersionUID = 201403041L;

	private String tenantId;
	private String userKey;
	private String deviceKey;
	private String specialCouponId;
	private double specialCouponAmt;

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return the deviceKey
	 */
	public String getDeviceKey() {
		return this.deviceKey;
	}

	/**
	 * @param deviceKey
	 *            the deviceKey to set
	 */
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	/**
	 * @return the specialCouponId
	 */
	public String getSpecialCouponId() {
		return this.specialCouponId;
	}

	/**
	 * @param specialCouponId
	 *            the specialCouponId to set
	 */
	public void setSpecialCouponId(String specialCouponId) {
		this.specialCouponId = specialCouponId;
	}

	/**
	 * @return the specialCouponAmt
	 */
	public double getSpecialCouponAmt() {
		return this.specialCouponAmt;
	}

	/**
	 * @param specialCouponAmt
	 *            the specialCouponAmt to set
	 */
	public void setSpecialCouponAmt(double specialCouponAmt) {
		this.specialCouponAmt = specialCouponAmt;
	}

}
