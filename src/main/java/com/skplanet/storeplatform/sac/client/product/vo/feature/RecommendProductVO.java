/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.product.vo.feature;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;

/**
 * Feature 상품 리스트 조회 Default Value Object.
 * 
 * Updated on : 2013. 12. 20. Updated by : 서영배, GTSOFT.
 */
public class RecommendProductVO {

	private Product product;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
