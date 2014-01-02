/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

package com.skplanet.storeplatform.sac.api.vo;

public class DpBrandInfo extends BrandCatalogProdImgInfo {

	private String brandId; // TB_DP_SHPG_BRAND.BRAND_ID
	private String brandNm; // TB_DP_SHPG_BRAND.BRAND_NM
	private String dpCatNo; // TB_DP_SHPG_BRAND.DP_CAT_NO
	private String brandImgPath; // TB_DP_SHPG_BRAND.BRAND_IMG_PATH
	private String txType; // 상품구분 브랜드 : bd, 카탈로그 : ct
	private String cudType; // CUD
	private String createBrandId; // 브랜드 생성 ID

	public String getBrandId() {
		return this.brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getBrandNm() {
		return this.brandNm;
	}

	public void setBrandNm(String brandNm) {
		this.brandNm = brandNm;
	}

	public String getDpCatNo() {
		return this.dpCatNo;
	}

	public void setDpCatNo(String dpCatNo) {
		this.dpCatNo = dpCatNo;
	}

	public String getBrandImgPath() {
		return this.brandImgPath;
	}

	public void setBrandImgPath(String brandImgPath) {
		this.brandImgPath = brandImgPath;
	}

	public String getTxType() {
		return this.txType;
	}

	public void setTxType(String txType) {
		this.txType = txType;
	}

	public String getCudType() {
		return this.cudType;
	}

	public void setCudType(String cudType) {
		this.cudType = cudType;
	}

	public String getCreateBrandId() {
		return this.createBrandId;
	}

	public void setCreateBrandId(String createBrandId) {
		this.createBrandId = createBrandId;
	}

}
