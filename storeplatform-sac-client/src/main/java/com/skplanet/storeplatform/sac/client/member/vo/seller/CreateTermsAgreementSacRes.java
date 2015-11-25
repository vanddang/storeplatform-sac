package com.skplanet.storeplatform.sac.client.member.vo.seller;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 2.2.36. 판매자 약관 동의 등록/수정 [RESPONSE].
 * 
 * Updated on : 2015. 3. 4. Updated by : Rejoice, Burkhan
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class CreateTermsAgreementSacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 판매자 Key. */
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
