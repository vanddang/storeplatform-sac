package com.skplanet.storeplatform.sac.client.internal.display.localsci.vo;

import java.util.List;

/**
 * ProductInfoSacRes Value Object
 * 
 * DCD 지원 상품 조회 VO
 * 
 * Updated on : 2014. 2. 27. Updated by : 이석희, 아이에스 플러스
 */
public class DcdSupportProductRes {

	List<ProductInfo> productList; // 상품 리스트

	/**
	 * @return the productList
	 */
	public List<ProductInfo> getProductList() {
		return this.productList;
	}

	/**
	 * @param productList
	 *            the productList to set
	 */
	public void setProductList(List<ProductInfo> productList) {
		this.productList = productList;
	}

}
