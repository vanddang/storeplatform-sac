package com.skplanet.storeplatform.sac.client.display.vo.openapi;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 개발 APP 목록 요청(회원 ID 기반) Request Value Object.
 * 
 * Updated on : 2014. 3. 6. Updated by : 오승민, 인크로스.
 */
public class SellerIdAppListReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

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
	private String searchType;
	/**
	 * 검색할 값
	 */
	@NotBlank
	private String searchValue;

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
