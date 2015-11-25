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

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.sac.client.purchase.common.vo.PurchaseCommonSacReq;

/**
 * 쿠폰 발급가능여부 조회 요청 VO.
 * 
 * Updated on : 2014. 2. 6. Updated by : nTels_cswoo81, nTels.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CouponPublishAvailableSacV2Req extends PurchaseCommonSacReq {

	private static final long serialVersionUID = 1L;

	@NotBlank
	private String couponCode;
	@NotBlank
	private String itemCode;
	@NotNull
	@Min(1)
	private Integer itemCount;
	@NotBlank
	private String mdn;

	private String prodId;

	@NotBlank
	@Pattern(regexp = "^Y|^N")
	private String giftFlag;

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

	/**
	 * @return the giftFlag
	 */
	public String getGiftFlag() {
		return this.giftFlag;
	}

	/**
	 * @param giftFlag
	 *            the giftFlag to set
	 */
	public void setGiftFlag(String giftFlag) {
		this.giftFlag = giftFlag;
	}

}
