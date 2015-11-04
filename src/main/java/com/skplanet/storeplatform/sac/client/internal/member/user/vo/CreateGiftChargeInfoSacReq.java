package com.skplanet.storeplatform.sac.client.internal.member.user.vo;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * SAC Internal [REQUEST] 상품권 충전 정보 등록.
 * 
 * Updated on : 2015. 10. 28. Updated by : 윤보영.
 */
public class CreateGiftChargeInfoSacReq extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 회원 키.
	 */
	@NotEmpty
	private String userKey;

	/**
	 * 판매자 키.
	 */
	@NotEmpty
	private String sellerKey;

	/**
	 * 제휴사 브랜드명.
	 */
	@NotEmpty
	private String brandName;

	/**
	 * 충전자 아이디.
	 */
	@NotEmpty
	private String chargerId;

	/**
	 * 충전자 이름.
	 */
	@NotEmpty
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
	 * @param chargerName
	 *            the chargerName to set
	 */
	public void setChargerName(String chargerName) {
		this.chargerName = chargerName;
	}

	/**
	 * @return the chargerId
	 */
	public String getChargerId() {
		return this.chargerId;
	}

	/**
	 * @param chargerId
	 *            the chargerId to set
	 */
	public void setChargerId(String chargerId) {
		this.chargerId = chargerId;
	}
}
