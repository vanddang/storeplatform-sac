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

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.sac.client.purchase.common.vo.PurchaseCommonSacReq;

/**
 * 쿠폰 발급가능여부 조회 요청 VO.
 * 
 * Updated on : 2014. 2. 6. Updated by : nTels_cswoo81, nTels.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CouponStockSacReq extends PurchaseCommonSacReq {

	private static final long serialVersionUID = 1L;

	@NotBlank
	private String couponCode;
	@NotBlank
	private String itemCode;
	@NotBlank
	private String mdn;

	private String prodId;

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

	/**
	 * @return the prodId
	 */
	public String getProdId() {
		return this.prodId;
	}

	/**
	 * @param prodId
	 *            the prodId to set
	 */
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

}
