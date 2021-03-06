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

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Coupon;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;

/**
 * 
 * FreepassDetailReq Value Object
 * 
 * 정액제 req VO
 * 
 * Updated on : 2015. 05. 11. Updated by : 이태균, I-S PLUS
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class VoucherDetailResV2 extends CommonInfo {

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
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @param couponList
	 *            the couponList to set
	 */
	public List<Coupon> getCouponList() {
		return this.couponList;
	}

	/**
	 * @return the couponList
	 */
	public void setCouponList(List<Coupon> couponList) {
		this.couponList = couponList;
	}

}
