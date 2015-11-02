package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 사용자 상품권 충전 정보.
 * 
 * Updated on : 2015. 10. 29. Updated by : 윤보영
 */
public class GiftChargeInfo extends CommonInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5338581759822504676L;

	/** 상품권충전순번. */
	private int seq;

	/** TenantId. */
	private String tenantId;

	/** 사용자 Key. */
	private String userKey;

	/** 판매자 Key. */
	private String sellerKey;

	/** 제휴사브랜드명. */
	private String brandNm;

	/** 제휴사회원아이디. */
	private String brandSiteId;

	/** 충전자이름. */
	private String chargerNm;

	/** 등록일시. */
	private String regDt;

	/** 수정일시. */
	private String updDt;

	/**
	 * @return the seq
	 */
	public int getSeq() {
		return this.seq;
	}

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @return the brandNm
	 */
	public String getBrandNm() {
		return this.brandNm;
	}

	/**
	 * @return the brandSiteId
	 */
	public String getBrandSiteId() {
		return this.brandSiteId;
	}

	/**
	 * @return the chargerNm
	 */
	public String getChargerNm() {
		return this.chargerNm;
	}

	/**
	 * @return the regDt
	 */
	public String getRegDt() {
		return this.regDt;
	}

	/**
	 * @return the updDt
	 */
	public String getUpdDt() {
		return this.updDt;
	}

	/**
	 * @param seq
	 *            the seq to set
	 */
	public void setSeq(int seq) {
		this.seq = seq;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @param brandNm
	 *            the brandNm to set
	 */
	public void setBrandNm(String brandNm) {
		this.brandNm = brandNm;
	}

	/**
	 * @param brandSiteId
	 *            the brandSiteId to set
	 */
	public void setBrandSiteId(String brandSiteId) {
		this.brandSiteId = brandSiteId;
	}

	/**
	 * @param chargerNm
	 *            the chargerNm to set
	 */
	public void setChargerNm(String chargerNm) {
		this.chargerNm = chargerNm;
	}

	/**
	 * @param regDt
	 *            the regDt to set
	 */
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	/**
	 * @param updDt
	 *            the updDt to set
	 */
	public void setUpdDt(String updDt) {
		this.updDt = updDt;
	}

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
}
