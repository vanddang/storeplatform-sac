package com.skplanet.storeplatform.sac.client.display.vo.openapi;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;

/**
 * 개발 APP 목록 요청(회원 ID 기반) Response Value Object.
 * 
 * Updated on : 2014. 3. 6. Updated by : 오승민, 인크로스.
 */
public class SellerIdAppListRes extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * Common Response.
	 */
	private CommonResponse commonResponse;

	/**
	 * 상품 List.
	 */
	private List<Product> productList;

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

}
