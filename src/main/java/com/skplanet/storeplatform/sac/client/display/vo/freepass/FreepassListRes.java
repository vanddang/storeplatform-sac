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

public class FreepassListRes extends CommonInfo {
	
	private static final long serialVersionUID = -7112036496004829217L;

	private CommonResponse commonResponse;
	private List<Coupon> couponList;
	
	public CommonResponse getCommonResponse() {
		return commonResponse;
	}
	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<Coupon> getCouponList() {
		return couponList;
	}
	public void setCouponList(List<Coupon> couponList) {
		this.couponList = couponList;
	}
	
}
