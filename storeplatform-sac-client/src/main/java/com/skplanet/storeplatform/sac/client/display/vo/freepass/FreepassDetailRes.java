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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;

/**
 * 
 * FreepassDetailReq Value Object
 * 
 * 정액제 req VO
 * 
 * Updated on : 2014. 2. 13. Updated by : 서영배, GTSOFT
 */
public class FreepassDetailRes extends CommonInfo {
	
	private static final long serialVersionUID = 1L;

	private CommonResponse commonResponse;
	private Coupon coupon;
	private List<Product> productList;
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
	 * @return the coupon
	 */
	public Coupon getCoupon() {
		return coupon;
	}
	/**
	 * @param coupon the coupon to set
	 */
	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}
	/**
	 * @return the productList
	 */
	public List<Product> getProductList() {
		return productList;
	}
	/**
	 * @param productList the productList to set
	 */
	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
