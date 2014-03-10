package com.skplanet.storeplatform.sac.client.display.vo.openapi;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * App 상세 정보 요청 Request Value Object.
 * 
 * Updated on : 2014. 3. 6. Updated by : 오승민, 인크로스.
 */
public class SellerAppDetailReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * 관리자 여부.
	 */
	@NotBlank
	private String admin;
	/**
	 * 판매자ID.
	 */
	private String sellerId;
	/**
	 * 판매자Key.
	 */
	private String sellerKey;
	/**
	 * AID.
	 */
	@NotBlank
	private String aid;

	/**
	 * @return the admin
	 */
	public String getAdmin() {
		return this.admin;
	}

	/**
	 * @param admin
	 *            the admin to set
	 */
	public void setAdmin(String admin) {
		this.admin = admin;
	}

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

	/**
	 * @return the aid
	 */
	public String getAid() {
		return this.aid;
	}

	/**
	 * @param aid
	 *            the aid to set
	 */
	public void setAid(String aid) {
		this.aid = aid;
	}

}
