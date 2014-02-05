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

	/** 판매자 회원 ID */
	@NotBlank
	private String sellerId;
	/** 판매자 회원 PW */
	@NotBlank
	private String sellerPW;
	/** 계정 잠금 해제 요청 ('Y'/'N') */
	@Pattern(regexp = "^Y|^N")
	private String releaseLock;
	/** 만료일시. */
	// @NotBlank
	// @Pattern(regexp = "^d")
	private String expireDate;
	/** IP 주소. */
	private String ipAddress;

	public String getSellerId() {
		return this.sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerPW() {
		return this.sellerPW;
	}

	public void setSellerPW(String sellerPW) {
		this.sellerPW = sellerPW;
	}

	public String getReleaseLock() {
		return this.releaseLock;
	}

	public void setReleaseLock(String releaseLock) {
		this.releaseLock = releaseLock;
	}

	public String getExpireDate() {
		return this.expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
}
