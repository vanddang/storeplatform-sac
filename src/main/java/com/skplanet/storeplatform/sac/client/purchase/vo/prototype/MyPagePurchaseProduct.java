/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.purchase.vo.prototype;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * [Prototype] 구매내역 조회 - 구매&상품 묶음 VO
 * 
 * Updated on : 2014. 1. 16. Updated by : 이승택, nTels.
 */
public class MyPagePurchaseProduct extends CommonInfo {
	private static final long serialVersionUID = 201311131L;
	private MyPageProduct product;
	private MyPagePurchase purchase;

	/**
	 */
	public MyPagePurchaseProduct() {
	}

	/**
	 * @param purchase
	 *            purchase
	 * @param product
	 *            product
	 */
	public MyPagePurchaseProduct(MyPagePurchase purchase, MyPageProduct product) {
		this.purchase = purchase;
		this.product = product;
	}

	/**
	 * @return the product
	 */
	public MyPageProduct getProduct() {
		return this.product;
	}

	/**
	 * @param product
	 *            the product to set
	 */
	public void setProduct(MyPageProduct product) {
		this.product = product;
	}

	/**
	 * @return the purchase
	 */
	public MyPagePurchase getPurchase() {
		return this.purchase;
	}

	/**
	 * @param purchase
	 *            the purchase to set
	 */
	public void setPurchase(MyPagePurchase purchase) {
		this.purchase = purchase;
	}

}
