package com.skplanet.storeplatform.sac.client.display.vo.personal;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;

/**
 * 자동 업데이트 목록 조회 Response Value Object.
 * 
 * Updated on : 2014. 2. 3. Updated by : 오승민, 인크로스.
 */
public class PersonalAutoUpdateRes extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private CommonResponse commonResponse;
	private List<Product> productList;

	public CommonResponse getCommonResponse() {
		return this.commonResponse;
	}

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
