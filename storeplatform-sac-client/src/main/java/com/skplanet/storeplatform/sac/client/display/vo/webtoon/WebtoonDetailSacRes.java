package com.skplanet.storeplatform.sac.client.display.vo.webtoon;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;

/**
 * 웹툰 상품 상세 조회 Request Value Object.
 * 
 * Updated on : 2014. 2. 19. Updated by : 조준일, nTels.
 */
public class WebtoonDetailSacRes extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private CommonResponse commonResponse; // 공통 Response

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
