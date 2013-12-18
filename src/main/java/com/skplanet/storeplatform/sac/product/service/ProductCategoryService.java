package com.skplanet.storeplatform.sac.product.service;

import com.skplanet.storeplatform.sac.client.product.vo.category.ProductCategoryRequestVO;
import com.skplanet.storeplatform.sac.client.product.vo.category.ProductCategoryResponseVO;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2013. 11. 28. Updated by : 이태희, SK 플래닛.
 */
public interface ProductCategoryService {
	/**
	 * <pre>
	 * 일반/특정 상품 카테고리 리스트 조회.
	 * </pre>
	 * 
	 * @param productCategoryReqVO
	 *            일반/특정 상품 카테고리 Value Object
	 * @return 일반/특정 상품 카테고리 리스트
	 */
	ProductCategoryResponseVO searchCategoryProductList(ProductCategoryRequestVO requestVO);
}
