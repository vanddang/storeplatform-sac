/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.product.service;

import com.skplanet.storeplatform.sac.client.product.vo.category.ProductCategoryRequest;
import com.skplanet.storeplatform.sac.client.product.vo.category.ProductCategoryResponse;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2013. 11. 28. Updated by : 이태희, SK 플래닛.
 */
public interface ProductCategoryService {
	/**
	 * <pre>
	 * 일반 카테고리 상품 리스트 조회.
	 * </pre>
	 * 
	 * @param productCategoryReqVO
	 *            Request VO
	 * @return 일반 카테고리 상품 리스트
	 */
	ProductCategoryResponse searchCategoryProductList(ProductCategoryRequest request);
}
