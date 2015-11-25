package com.skplanet.storeplatform.sac.client.display.vo.other;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * T Membership 할인율 조회 Request Value Object.
 * 
 * Updated on : 2014. 3. 31. Updated by : 윤주영, SK 플래닛.
 */
public class OtherTMembershipReq extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3395843648586247349L;

	@NotBlank
	private String productId; // 상품 Id

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
