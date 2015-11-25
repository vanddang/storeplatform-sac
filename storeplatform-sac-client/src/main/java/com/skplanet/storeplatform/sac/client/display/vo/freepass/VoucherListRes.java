/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.freepass;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Coupon;

/**
 * 
 * VoucherListRes Value Object
 * 
 * 정액제 req VO
 * 
 * Updated on : 2015. 4. 30. Updated by : 이태균, IS PLUS.
 */
public class VoucherListRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private CommonResponse commonResponse;
	private List<Coupon> couponList;

	/**
	 * @return the commonResponse
	 */
	public CommonResponse getCommonResponse() {
		return this.commonResponse;
	}

	/**
	 * @param commonResponse
	 *            the commonResponse to set
	 */
	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
	}

	/**
	 * @return the couponList
	 */
	public List<Coupon> getCouponList() {
		return this.couponList;
	}

	/**
	 * @param couponList
	 *            the couponList to set
	 */
	public void setCouponList(List<Coupon> couponList) {
		this.couponList = couponList;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
