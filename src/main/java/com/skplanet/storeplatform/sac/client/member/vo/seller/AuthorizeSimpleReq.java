/**
 * 
 */
package com.skplanet.storeplatform.sac.client.member.vo.seller;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 2.2.4. 판매자 회원 단순 인증 [REQUEST]
 * 
 * Updated on : 2014. 2. 9. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class AuthorizeSimpleReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 판매자 회원 ID. */
	@NotBlank
	private String sellerId;
	/** 판매자 회원 PW. */
	@NotBlank
	private String sellerPW;

	/**
	 * @return the sellerId
	 */
	public String getSellerId() {
		return this.sellerId;
	}

	/**
	 * @param sellerId
	 *            the sellerId to set
	 */
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	/**
	 * @return the sellerPW
	 */
	public String getSellerPW() {
		return this.sellerPW;
	}

	/**
	 * @param sellerPW
	 *            the sellerPW to set
	 */
	public void setSellerPW(String sellerPW) {
		this.sellerPW = sellerPW;
	}

}
