package com.skplanet.storeplatform.sac.client.member.vo.seller;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 2.2.33. 가가입 이메일 수정 [RESPONSE]
 * 
 * Updated on : 2014. 3. 3. Updated by : Rejoice, Burkhan
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ModifyWaitEmailSacRes extends CommonInfo {

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
