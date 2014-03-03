/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.banner;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 배너 리스트 조회 Request Value Object.
 * 
 * Updated on : 2014. 02. 21. Updated by : 이태희.
 */
public class BannerSacReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	@NotBlank
	private String bnrMenuId; // 배너 메뉴 ID

	private String mobileWebExpoYn; // 모바일 웹 배너 여부

	private String bnrExpoMenuId; // 테마배너 여부

	private String imgSizeCd; // 배너 이미지 코드

	private String tenantId; // 테넌트ID

	private String langCd; // 언어코드

	private String deviceModelCd; // 단말모델코드

	private String anyDeviceModelCd; // 가상 프로비저닝 단말모델코드

	private String prodId; // 프로비저닝을 위한 상품ID

	private String topMenuId; // 프로비저닝을 위한 탑메뉴ID

	private String stdDt; // 배치완료 기준일시

	private String recommendId; // 추천ID

	private String brandShopNo; // 브랜드샵 번호

	private String themeId; // 테마추천ID

	/**
	 * @return the bnrMenuId
	 */
	public String getBnrMenuId() {
		return this.bnrMenuId;
	}

	/**
	 * @param bnrMenuId
	 *            the bnrMenuId to set
	 */
	public void setBnrMenuId(String bnrMenuId) {
		this.bnrMenuId = bnrMenuId;
	}

	/**
	 * @return the mobileWebExpoYn
	 */
	public String getMobileWebExpoYn() {
		return this.mobileWebExpoYn;
	}

	/**
	 * @param mobileWebExpoYn
	 *            the mobileWebExpoYn to set
	 */
	public void setMobileWebExpoYn(String mobileWebExpoYn) {
		this.mobileWebExpoYn = mobileWebExpoYn;
	}

	/**
	 * @return the bnrExpoMenuId
	 */
	public String getBnrExpoMenuId() {
		return this.bnrExpoMenuId;
	}

	/**
	 * @param bnrExpoMenuId
	 *            the bnrExpoMenuId to set
	 */
	public void setBnrExpoMenuId(String bnrExpoMenuId) {
		this.bnrExpoMenuId = bnrExpoMenuId;
	}

	/**
	 * @return the imgSizeCd
	 */
	public String getImgSizeCd() {
		return this.imgSizeCd;
	}

	/**
	 * @param imgSizeCd
	 *            the imgSizeCd to set
	 */
	public void setImgSizeCd(String imgSizeCd) {
		this.imgSizeCd = imgSizeCd;
	}

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the langCd
	 */
	public String getLangCd() {
		return this.langCd;
	}

	/**
	 * @param langCd
	 *            the langCd to set
	 */
	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}

	/**
	 * @return the deviceModelCd
	 */
	public String getDeviceModelCd() {
		return this.deviceModelCd;
	}

	/**
	 * @param deviceModelCd
	 *            the deviceModelCd to set
	 */
	public void setDeviceModelCd(String deviceModelCd) {
		this.deviceModelCd = deviceModelCd;
	}

	/**
	 * @return the anyDeviceModelCd
	 */
	public String getAnyDeviceModelCd() {
		return this.anyDeviceModelCd;
	}

	/**
	 * @param anyDeviceModelCd
	 *            the anyDeviceModelCd to set
	 */
	public void setAnyDeviceModelCd(String anyDeviceModelCd) {
		this.anyDeviceModelCd = anyDeviceModelCd;
	}

	/**
	 * @return the prodId
	 */
	public String getProdId() {
		return this.prodId;
	}

	/**
	 * @param prodId
	 *            the prodId to set
	 */
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	/**
	 * @return the topMenuId
	 */
	public String getTopMenuId() {
		return this.topMenuId;
	}

	/**
	 * @param topMenuId
	 *            the topMenuId to set
	 */
	public void setTopMenuId(String topMenuId) {
		this.topMenuId = topMenuId;
	}

	/**
	 * @return the stdDt
	 */
	public String getStdDt() {
		return this.stdDt;
	}

	/**
	 * @param stdDt
	 *            the stdDt to set
	 */
	public void setStdDt(String stdDt) {
		this.stdDt = stdDt;
	}

	/**
	 * @return the recommendId
	 */
	public String getRecommendId() {
		return this.recommendId;
	}

	/**
	 * @param recommendId
	 *            the recommendId to set
	 */
	public void setRecommendId(String recommendId) {
		this.recommendId = recommendId;
	}

	/**
	 * @return the brandShopNo
	 */
	public String getBrandShopNo() {
		return this.brandShopNo;
	}

	/**
	 * @param brandShopNo
	 *            the brandShopNo to set
	 */
	public void setBrandShopNo(String brandShopNo) {
		this.brandShopNo = brandShopNo;
	}

	/**
	 * @return the themeId
	 */
	public String getThemeId() {
		return this.themeId;
	}

	/**
	 * @param themeId
	 *            the themeId to set
	 */
	public void setThemeId(String themeId) {
		this.themeId = themeId;
	}
}
