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
 * FreepassDetailReq Value Object
 * 
 * 정액제 req VO
 * 
 * Updated on : 2014. 2. 13. Updated by : 서영배, GTSOFT
 */
public class FreepassListRes extends CommonInfo {
	
	private static final long serialVersionUID = -7112036496004829217L;

	private CommonResponse commonResponse;
	private List<Coupon> couponList;
	/**
	 * @return the commonResponse
	 */
	public CommonResponse getCommonResponse() {
		return commonResponse;
	}
	/**
	 * @param commonResponse the commonResponse to set
	 */
	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
	}
	/**
	 * @return the couponList
	 */
	public List<Coupon> getCouponList() {
		return couponList;
	}
	/**
	 * @param couponList the couponList to set
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
