/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

package com.skplanet.storeplatform.sac.purchase.order.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationListForProductSacRes;

/**
 * 판매자 정보와 판매자 구분코드.
 *
 * Updated on : 15. 4. 13. Updated by : 황민규, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SellerMbrAppSacParam extends DetailInformationListForProductSacRes.SellerMbrInfoSac.SellerMbrAppSac {

	/** 판매자 구분 코드 . */
	private String sellerClass;

	/**
	 * Gets seller class.
	 *
	 * @return the seller class
	 */
	public String getSellerClass() {
		return sellerClass;
	}

	/**
	 * Sets seller class.
	 *
	 * @param sellerClass
	 *            the seller class
	 */
	public void setSellerClass(String sellerClass) {
		this.sellerClass = sellerClass;
	}
}
