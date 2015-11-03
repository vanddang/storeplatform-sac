/**
 * 
 */
package com.skplanet.storeplatform.member.client.user.sci.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;

/**
 * 상품권 충전 정보 조회 요청 Value Object
 * 
 * Updated on : 2015. 11. 11. Updated by : 반범진.
 */
public class SearchGiftChargeInfoRequest extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/**
	 * 사용자 Key.
	 */
	private String userKey;

	/**
	 * 판매자 키.
	 */
	private String sellerKey;

	/**
	 * 제휴사 브랜드명.
	 */
	private String brandName;

	/**
	 * 제휴사 회원 아이디.
	 */
	private String brandSiteId;

	/**
	 * 충전자 이름.
	 */
	private String chargerName;

	/**
	 * @return the userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @return the sellerKey
	 */
	public String getSellerKey() {
		return this.sellerKey;
	}

	/**
	 * @return the brandName
	 */
	public String getBrandName() {
		return this.brandName;
	}

	/**
	 * @return the brandSiteId
	 */
	public String getBrandSiteId() {
		return this.brandSiteId;
	}

	/**
	 * @return the chargerName
	 */
	public String getChargerName() {
		return this.chargerName;
	}

	/**
	 * @param userKey
	 *            the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @param sellerKey
	 *            the sellerKey to set
	 */
	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

	/**
	 * @param brandName
	 *            the brandName to set
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	/**
	 * @param brandSiteId
	 *            the brandSiteId to set
	 */
	public void setBrandSiteId(String brandSiteId) {
		this.brandSiteId = brandSiteId;
	}

	/**
	 * @param chargerName
	 *            the chargerName to set
	 */
	public void setChargerName(String chargerName) {
		this.chargerName = chargerName;
	}

	/**
	 * @return commonRequest
	 */
	public CommonRequest getCommonRequest() {
		return this.commonRequest;
	}

	/**
	 * @param commonRequest
	 *            CommonRequest
	 */
	public void setCommonRequest(CommonRequest commonRequest) {
		this.commonRequest = commonRequest;
	}

}
