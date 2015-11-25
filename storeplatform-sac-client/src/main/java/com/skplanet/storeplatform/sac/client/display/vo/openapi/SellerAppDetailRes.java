package com.skplanet.storeplatform.sac.client.display.vo.openapi;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;

/**
 * App 상세 정보 요청 Response Value Object.
 * 
 * Updated on : 2014. 3. 6. Updated by : 오승민, 인크로스.
 */
public class SellerAppDetailRes extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * Common Response.
	 */
	private CommonResponse commonResponse;

	private Product product;

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
	 * @return the product
	 */
	public Product getProduct() {
		return this.product;
	}

	/**
	 * @param product
	 *            the product to set
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

}
