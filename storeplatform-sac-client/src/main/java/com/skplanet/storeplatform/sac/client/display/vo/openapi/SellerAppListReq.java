package com.skplanet.storeplatform.sac.client.display.vo.openapi;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 태그 목록 조회 Request Value Object.
 * 
 * Updated on : 2014. 2. 3. Updated by : 오승민, 인크로스.
 */
public class SellerAppListReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * 관리자여부
	 */
	@NotBlank
	@Pattern(regexp = "^Y|^N")
	private String admin;

	/**
	 * 판매자ID
	 */
	private String sellerId;
	/**
	 * 판매자Key
	 */
	private String sellerKey;
	/**
	 * 검색조건
	 */
	@NotBlank
	@Pattern(regexp = "^APP|^AID")
	private String searchType;
	/**
	 * 검색할 값
	 */
	@NotBlank
	private String searchValue;

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
	 * @return the searchType
	 */
	public String getSearchType() {
		return this.searchType;
	}

	/**
	 * @param searchType
	 *            the searchType to set
	 */
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	/**
	 * @return the searchValue
	 */
	public String getSearchValue() {
		return this.searchValue;
	}

	/**
	 * @param searchValue
	 *            the searchValue to set
	 */
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

}
