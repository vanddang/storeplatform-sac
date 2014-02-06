package com.skplanet.storeplatform.sac.client.display.vo.feature.recommend;

import java.util.List;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;

/**
 * TODAY 상품 조회 List Value Object.
 * 
 * Updated on : 2014. 2. 6. Updated by : 조준일, nTels.
 */
public class RecommendTodaySacRes {
	private CommonResponse commonResponse;
	private List<Product> productList;

	public List<Product> getProductList() {
		return this.productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public CommonResponse getCommonResponse() {
		return this.commonResponse;
	}

	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
	}
}
