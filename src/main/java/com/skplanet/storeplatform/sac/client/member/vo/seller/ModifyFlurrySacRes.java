/**
 * 
 */
package com.skplanet.storeplatform.sac.client.member.vo.seller;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 2.2.34. Flurry 수정 [RESPONSE]
 * 
 * Updated on : 2014. 3. 4. Updated by : Rejoice, Burkhan
 */
public class ModifyFlurrySacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 판매자 회원 키. */
	private String sellerKey;

	/**
	 * @return the sellerKey
	 */
	public String getSellerKey() {
		return this.sellerKey;
	}

	/**
	 * @param sellerKey
	 *            the sellerKey to set
	 */
	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

}
