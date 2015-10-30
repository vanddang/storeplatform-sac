package com.skplanet.storeplatform.sac.client.internal.display.localsci.vo;

import java.util.List;

/**
 * ProductInfoSacRes Value Object
 * 
 * 구매 내역 조회 시 필요한 상품 메타 정보 조회 VO
 * 
 * Updated on : 2014. 2. 25. Updated by : 오승민, 인크로스
 */
public class ProductInfoSacRes {
	List<ProductInfo> productList;

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
