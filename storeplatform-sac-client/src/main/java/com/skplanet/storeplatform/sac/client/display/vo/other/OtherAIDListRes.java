package com.skplanet.storeplatform.sac.client.display.vo.other;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;

/**
 * AID로 상품ID 조회 Request Value Object.
 * 
 * Updated on : 2014. 3. 14. Updated by : 백승현, 인크로스.
 */
public class OtherAIDListRes extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * Common Response.
	 */
	private CommonResponse commonResponse;

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
