/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.shopping;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * <pre>
 * 쇼핑 Value Object
 * </pre>
 * 
 * Created on : 2014-01-02 Created by : 김형식, SK플래닛 Last Updated on : 2014-01-02 Last Updated by : 김형식, SK플래닛
 */

public class ShoppingReq extends CommonInfo {

	private static final long serialVersionUID = 1L;
	private String tenantId; // 테넌트ID
	private String systemId; // 시스템ID
	private String listId; // 리스트아이디
	private String imageCd; // 이미지코드
	private String menuId; // 메뉴아이디
	private String osVersion; // os 버전
	private String prodCharge; // 유료/무료 구분
	private String[] arrayProdGradeCd; // 상품등급코드 Array
	private String prodGradeCd; // 상품등급코드
	private String b2bProd; // B2B 상품 구분
	private String orderedBy; // 정렬순서
	private String stdDt; // 기준일자
	private String langCd; // 언어코드
	private String deviceModelCd; // 디바이스 모델 코드
	private String dummy; // 더비 구분값
	private String planId; // 기획전ID
	private String brandId; // 브랜드샵 ID
	private String themeId; // 테마 ID
	private String exceptProdId; // 제외할 메뉴ID – 지정하면 응답에서 해당 메뉴 제외
	private String prodId; // 상품 ID (카탈로그)
	private String partProdId; // 상품 ID( 에피소드)
	private String specialProdId; // 특가상품 ID
	private String deviceKey; // 디바이스키
	private String userKey; // 사용자고유키
	private String virtualDeviceModelNo; // android_standard2

	private Integer offset; // offset
	private Integer count; // count

	private String bannerImgCd; // 배너이미지코드
	private String promotionImgCd; // 프로모션이미지코드

	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getSystemId() {
		return this.systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getListId() {
		return this.listId;
	}

	public void setListId(String listId) {
		this.listId = listId;
	}

	public String getImageCd() {
		return this.imageCd;
	}

	public void setImageCd(String imageCd) {
		this.imageCd = imageCd;
	}

	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * @return the osVersion
	 */
	public String getOsVersion() {
		return this.osVersion;
	}

	/**
	 * @param osVersion
	 *            the osVersion to set
	 */
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public String getProdCharge() {
		return this.prodCharge;
	}

	public void setProdCharge(String prodCharge) {
		this.prodCharge = prodCharge;
	}

	public String getProdGradeCd() {
		return this.prodGradeCd;
	}

	public void setProdGradeCd(String prodGradeCd) {
		this.prodGradeCd = prodGradeCd;
	}

	/**
	 * @return the arrayProdGradeCd
	 */
	public String[] getArrayProdGradeCd() {
		return this.arrayProdGradeCd;
	}

	/**
	 * @param arrayProdGradeCd
	 *            the arrayProdGradeCd to set
	 */
	public void setArrayProdGradeCd(String[] arrayProdGradeCd) {
		this.arrayProdGradeCd = arrayProdGradeCd;
	}

	public String getB2bProd() {
		return this.b2bProd;
	}

	public void setB2bProd(String b2bProd) {
		this.b2bProd = b2bProd;
	}

	public String getOrderedBy() {
		return this.orderedBy;
	}

	public void setOrderedBy(String orderedBy) {
		this.orderedBy = orderedBy;
	}

	public String getStdDt() {
		return this.stdDt;
	}

	public void setStdDt(String stdDt) {
		this.stdDt = stdDt;
	}

	public String getLangCd() {
		return this.langCd;
	}

	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}

	public String getDeviceModelCd() {
		return this.deviceModelCd;
	}

	public void setDeviceModelCd(String deviceModelCd) {
		this.deviceModelCd = deviceModelCd;
	}

	public String getDummy() {
		return this.dummy;
	}

	public void setDummy(String dummy) {
		this.dummy = dummy;
	}

	/**
	 * @return the planId
	 */
	public String getPlanId() {
		return this.planId;
	}

	/**
	 * @param planId
	 *            the planId to set
	 */
	public void setPlanId(String planId) {
		this.planId = planId;
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

	public Integer getOffset() {
		return this.offset;
	}

	/**
	 * @return the exceptProdId
	 */
	public String getExceptProdId() {
		return this.exceptProdId;
	}

	/**
	 * @param exceptProdId
	 *            the exceptProdId to set
	 */
	public void setExceptProdId(String exceptProdId) {
		this.exceptProdId = exceptProdId;
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
	 * @return the partProdId
	 */
	public String getPartProdId() {
		return this.partProdId;
	}

	/**
	 * @param partProdId
	 *            the partProdId to set
	 */
	public void setPartProdId(String partProdId) {
		this.partProdId = partProdId;
	}

	/**
	 * @return the specialProdId
	 */
	public String getSpecialProdId() {
		return this.specialProdId;
	}

	/**
	 * @param specialProdId
	 *            the specialProdId to set
	 */
	public void setSpecialProdId(String specialProdId) {
		this.specialProdId = specialProdId;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	/**
	 * @return the deviceKey
	 */
	public String getDeviceKey() {
		return this.deviceKey;
	}

	/**
	 * @param deviceKey
	 *            the deviceKey to set
	 */
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	/**
	 * @return the userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return the virtualDeviceModelNo
	 */
	public String getVirtualDeviceModelNo() {
		return this.virtualDeviceModelNo;
	}

	/**
	 * @param virtualDeviceModelNo
	 *            the virtualDeviceModelNo to set
	 */
	public void setVirtualDeviceModelNo(String virtualDeviceModelNo) {
		this.virtualDeviceModelNo = virtualDeviceModelNo;
	}

	/**
	 * @return the bannerImgCd
	 */
	public String getBannerImgCd() {
		return this.bannerImgCd;
	}

	/**
	 * @param bannerImgCd
	 *            the bannerImgCd to set
	 */
	public void setBannerImgCd(String bannerImgCd) {
		this.bannerImgCd = bannerImgCd;
	}

	/**
	 * @return the promotionImgCd
	 */
	public String getPromotionImgCd() {
		return this.promotionImgCd;
	}

	/**
	 * @param promotionImgCd
	 *            the promotionImgCd to set
	 */
	public void setPromotionImgCd(String promotionImgCd) {
		this.promotionImgCd = promotionImgCd;
	}

}
