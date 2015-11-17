package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;

/**
 * 회원 상품권 충전 정보 등록 Value Object
 * 
 * Updated on : 2015. 10. 29. Updated by : 윤보영.
 */
public class CreateGiftChargeInfoRequest extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/**
	 * 회원 키.
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
	 * 제휴사 브랜드 아이디.
	 */
	private String brandId;

	/**
	 * 충전자 아이디.
	 */
	private String chargerId;

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
	 * @return the commonRequest
	 */
	public CommonRequest getCommonRequest() {
		return this.commonRequest;
	}

	/**
	 * @param commonRequest
	 *            the commonRequest to set
	 */
	public void setCommonRequest(CommonRequest commonRequest) {
		this.commonRequest = commonRequest;
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

	/**
	 * @return the brandId
	 */
	public String getBrandId() {
		return this.brandId;
	}

	/**
	 * @param brandId
	 *            the brandId to set
	 */
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
}
