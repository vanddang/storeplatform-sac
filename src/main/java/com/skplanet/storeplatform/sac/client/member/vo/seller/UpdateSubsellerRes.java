/**
 * 
 */
package com.skplanet.storeplatform.sac.client.member.vo.seller;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE]서브계정 수정.
 * 
 * Updated on : 2014. 3. 20. Updated by : 김다슬, 인크로스.
 */
public class UpdateSubsellerRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 서브계정 키 (INSD_SELLERMBR_NO). */
	private String subSellerKey;

	/**
	 * @return the subSellerKey
	 */
	public String getSubSellerKey() {
		return this.subSellerKey;
	}

	/**
	 * @param subSellerKey
	 *            the subSellerKey to set
	 */
	public void setSubSellerKey(String subSellerKey) {
		this.subSellerKey = subSellerKey;
	}

}
