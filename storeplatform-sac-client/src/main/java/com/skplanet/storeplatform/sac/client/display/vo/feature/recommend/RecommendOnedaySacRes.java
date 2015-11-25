package com.skplanet.storeplatform.sac.client.display.vo.feature.recommend;

import java.util.List;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;

/**
 * ONEDAY 상품 조회 List Value Object.
 * 
 * Updated on : 2014. 2. 28. Updated by : 이승훈, nTels.
 */
public class RecommendOnedaySacRes {
	private CommonResponse commonResponse;
	private List<Product> productList;

	/**
	 * @return the productList
	 */
	public List<Product> getProductList() {
		return this.productList;
	}

	/**
	 * @param productList
	 *            the productList to set
	 */
	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	/**
	 * @return the commonResponse
	 */
	public CommonResponse getCommonResponse() {
		return this.commonResponse;
	}

	/**
	 * @param commonResponse
	 *            the commonResponse to set
	 */
	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
	}
}
