package com.skplanet.storeplatform.sac.client.display.vo.other;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 태그 목록 조회 Request Value Object.
 * 
 * Updated on : 2014. 2. 3. Updated by : 오승민, 인크로스.
 */
public class OtherTagReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * 상품 Id
	 */
	@NotBlank
	private String productId;

	/**
	 * @return the productId
	 */
	public String getProductId() {
		return this.productId;
	}

	/**
	 * @param productId
	 *            the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

}
