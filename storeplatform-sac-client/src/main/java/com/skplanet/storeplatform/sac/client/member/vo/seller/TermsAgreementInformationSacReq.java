package com.skplanet.storeplatform.sac.client.member.vo.seller;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 2.2.37. 판매자 약관 동의 조회 [REQUEST].
 * 
 * Updated on : 2015. 3. 4. Updated by : Rejoice, Burkhan
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class TermsAgreementInformationSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 판매자 Key. */
	@NotBlank
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
