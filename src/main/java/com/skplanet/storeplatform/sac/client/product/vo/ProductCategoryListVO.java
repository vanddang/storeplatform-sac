/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 import java.util.List;

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;
lose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.product.vo;

import java.util.List;

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;

/**
 * 일반/특정 상품 카테고리 리스트 조회 List Value Object.
 * 
 * Updated on : 2013. 11. 28. Updated by : 이태희, SK 플래닛.
 */
@ProtobufMapping(ProductCategoryProto.ProductCategoryList.class)
public class ProductCategoryListVO {
	private List<ProductCategoryVO> productCategory; // 일반/특정 상품 카테고리 리스트

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return List<ProductCategoryVO>
	 */
	public List<ProductCategoryVO> getProductCategory() {
		return this.productCategory;
	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param productCategory
	 *            productCategory
	 */
	public void setProductCategory(List<ProductCategoryVO> productCategory) {
		this.productCategory = productCategory;
	}
}
