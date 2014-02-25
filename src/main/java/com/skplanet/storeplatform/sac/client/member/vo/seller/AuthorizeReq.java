/**
 * 
 */
package com.skplanet.storeplatform.sac.client.member.vo.seller;

import javax.validation.constraints.Pattern;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 2.2.3. 판매자 회원 인증 [REQUEST]
 * 
 * Updated on : 2014. 1. 9. Updated by : 김경복, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class AuthorizeReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 판매자 회원 ID. */
	@NotBlank
	private String sellerId;
	/** 판매자 회원 PW. */
	@NotBlank
	private String sellerPW;
	/** 계정 잠금 해제 요청 ('Y'/'N'). */
	private String releaseLock;
	/** 만료일시. */
	@NotBlank
	@Pattern(regexp = "^\\d*")
	private String expireDate;
	/** IP 주소. */
	private String ipAddress;

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

	/**
	 * @return the releaseLock
	 */
	public String getReleaseLock() {
		return this.releaseLock;
	}

	/**
	 * @param releaseLock
	 *            the releaseLock to set
	 */
	public void setReleaseLock(String releaseLock) {
		this.releaseLock = releaseLock;
	}

	/**
	 * @return the expireDate
	 */
	public String getExpireDate() {
		return this.expireDate;
	}

	/**
	 * @param expireDate
	 *            the expireDate to set
	 */
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	/**
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return this.ipAddress;
	}

	/**
	 * @param ipAddress
	 *            the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

}
