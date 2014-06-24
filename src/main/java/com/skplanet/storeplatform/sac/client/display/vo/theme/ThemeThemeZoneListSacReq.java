/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.theme;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 테마존 상품 조회 Input Value Object.
 * 
 * Updated on : 2014. 2. 21. Updated by : 이승훈, 엔텔스.
 */
public class ThemeThemeZoneListSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;
	private Integer offset = 1; // offset
	private Integer count = 20; // count
	private String tenantId; // 테넌트ID

	@NotBlank
	private String themeZoneId; // 테마존 ID

	private String langCd; // 언어코드
	private String deviceModelCd; // 디바이스 모델 코드
	private String topMenuId; // TOP 메뉴 아이디
	private String bnrMenuId; // 배너 메뉴 아이디
	private String prodCharge; // 상품의 유료/무료 구분
	private String prodGradeCd; // 상품 등급 코드
	private String[] arrayProdGradeCd; // 상품등급코드 Array
	private String b2bProd; // B2B 상품 구분

	private String anyDeviceModelCd; // 가상단말명
	// TB_CM_DEVICE 가능여부
	private String ebookSprtYn; // eBook 상품 지원여부
	private String comicSprtYn; // Comic 상품 지원여부
	private String musicSprtYn; // 음악 상품 지원여부
	private String videoDrmSprtYn; // VOD 상품 DRM 지원 여부
	private String sdVideoSprtYn; // VOD 상품 SD 지원 여부
	private String shpgSprtYn; // 쇼핑상품 지원여부

    public String getShpgSprtYn() {
        return shpgSprtYn;
    }

    public void setShpgSprtYn(String shpgSprtYn) {
        this.shpgSprtYn = shpgSprtYn;
    }

    // TODO osm1021 dummy data가 필요없어지면 삭제할것
	private String dummy;

	public Integer getOffset() {
		return this.offset;
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

	public String getThemeZoneId() {
		return this.themeZoneId;
	}

	public void setThemeZoneId(String themeZoneId) {
		this.themeZoneId = themeZoneId;
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

	public String getTopMenuId() {
		return this.topMenuId;
	}

	public void setTopMenuId(String topMenuId) {
		this.topMenuId = topMenuId;
	}

	public String getBnrMenuId() {
		return this.bnrMenuId;
	}

	public void setBnrMenuId(String bnrMenuId) {
		this.bnrMenuId = bnrMenuId;
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

	public String getB2bProd() {
		return this.b2bProd;
	}

	public void setB2bProd(String b2bProd) {
		this.b2bProd = b2bProd;
	}

	public String getDummy() {
		return this.dummy;
	}

	public void setDummy(String dummy) {
		this.dummy = dummy;
	}

	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String[] getArrayProdGradeCd() {
		return this.arrayProdGradeCd;
	}

	public void setArrayProdGradeCd(String[] arrayProdGradeCd) {
		this.arrayProdGradeCd = arrayProdGradeCd;
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
	 * @return the ebookSprtYn
	 */
	public String getEbookSprtYn() {
		return this.ebookSprtYn;
	}

	/**
	 * @param ebookSprtYn
	 *            the ebookSprtYn to set
	 */
	public void setEbookSprtYn(String ebookSprtYn) {
		this.ebookSprtYn = ebookSprtYn;
	}

	/**
	 * @return the comicSprtYn
	 */
	public String getComicSprtYn() {
		return this.comicSprtYn;
	}

	/**
	 * @param comicSprtYn
	 *            the comicSprtYn to set
	 */
	public void setComicSprtYn(String comicSprtYn) {
		this.comicSprtYn = comicSprtYn;
	}

	/**
	 * @return the musicSprtYn
	 */
	public String getMusicSprtYn() {
		return this.musicSprtYn;
	}

	/**
	 * @param musicSprtYn
	 *            the musicSprtYn to set
	 */
	public void setMusicSprtYn(String musicSprtYn) {
		this.musicSprtYn = musicSprtYn;
	}

	/**
	 * @return the videoDrmSprtYn
	 */
	public String getVideoDrmSprtYn() {
		return this.videoDrmSprtYn;
	}

	/**
	 * @param videoDrmSprtYn
	 *            the videoDrmSprtYn to set
	 */
	public void setVideoDrmSprtYn(String videoDrmSprtYn) {
		this.videoDrmSprtYn = videoDrmSprtYn;
	}

	/**
	 * @return the sdVideoSprtYn
	 */
	public String getSdVideoSprtYn() {
		return this.sdVideoSprtYn;
	}

	/**
	 * @param sdVideoSprtYn
	 *            the sdVideoSprtYn to set
	 */
	public void setSdVideoSprtYn(String sdVideoSprtYn) {
		this.sdVideoSprtYn = sdVideoSprtYn;
	}

}
