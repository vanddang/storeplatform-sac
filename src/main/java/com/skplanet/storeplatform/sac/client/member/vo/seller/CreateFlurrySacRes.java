package com.skplanet.storeplatform.sac.client.member.vo.seller;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 2.2.32. Flurry 등록/수정 [RESPONSE]
 * 
 * Updated on : 2014. 2. 27. Updated by : Rejoice, Burkhan
 */
public class CreateFlurrySacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 판매자 회원 Key. */
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
