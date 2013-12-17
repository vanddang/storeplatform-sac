/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.product.vo.category;

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;
import com.skplanet.storeplatform.sac.client.product.vo.ProductCategoryProto;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.ProductVO;

/**
 * 일반/특정 상품 카테고리 리스트 조회 Default Value Object.
 * 
 * Updated on : 2013. 11. 28. Updated by : 이태희, SK 플래닛.
 */
@ProtobufMapping(ProductCategoryProto.ProductCategory.class)
public class ProductCategoryVO {
	private ProductVO product;

	public ProductVO getProduct() {
		return this.product;
	}

	public void setProduct(ProductVO product) {
		this.product = product;
	}
}
