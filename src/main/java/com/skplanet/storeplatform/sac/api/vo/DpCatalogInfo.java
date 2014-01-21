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
/**
 * <pre>
 * 전처리 카탈로그 Value Object
 * </pre>
 *
 * Created on : 2014-01-02
 * Created by : 김형식, SK플래닛
 * Last Updated on : 2014-01-02
 * Last Updated by : 김형식, SK플래닛
 */
public class DpCatalogInfo extends BrandCatalogProdImgInfo {

	// TBL_DP_CATALOG_INFO
	private String catalogId; // INSERT_TB_DP_SHPG_CATALOG.CATALOG_ID
	private String dpCatNo; // INSERT_TB_DP_SHPG_CATALOG.DP_CAT_NO
	private String brandId; // INSERT_TB_DP_SHPG_CATALOG.BRAND_ID
	private String catalogDesc; // INSERT_TB_DP_SHPG_CATALOG_DESC.CATALOG_DESC
	private String catalogNm; // INSERT_TB_DP_SHPG_CATALOG_DESC.CATALOG_NM
	// private String catalogStatCd;
	private String topImgPath; // TBL_DP_CATALOG_INFO.TOP_IMG_PATH
	private String dtlImgPath; // TBL_DP_CATALOG_INFO.DTL_IMG_PATH
	private String txType; // 상품구분 브랜드 : bd, 카탈로그 : ct
	private String cudType; // CUD
	private String introText; // 한줄설명 INSERT_TB_DP_SHPG_CATALOG_DESC.
	private String createCatalogId; // 카탈로그ID 생성
	private String createBrandId; // 카탈로그ID 생성
	private String catalogTag; // 카탈로그태그

	public String getCatalogId() {
		return this.catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getDpCatNo() {
		return this.dpCatNo;
	}

	public void setDpCatNo(String dpCatNo) {
		this.dpCatNo = dpCatNo;
	}

	public String getBrandId() {
		return this.brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getCatalogDesc() {
		return this.catalogDesc;
	}

	public void setCatalogDesc(String catalogDesc) {
		this.catalogDesc = catalogDesc;
	}

	public String getCatalogNm() {
		return this.catalogNm;
	}

	public void setCatalogNm(String catalogNm) {
		this.catalogNm = catalogNm;
	}

	public String getTopImgPath() {
		return this.topImgPath;
	}

	public void setTopImgPath(String topImgPath) {
		this.topImgPath = topImgPath;
	}

	public String getDtlImgPath() {
		return this.dtlImgPath;
	}

	public void setDtlImgPath(String dtlImgPath) {
		this.dtlImgPath = dtlImgPath;
	}

	// public String getCatalogStatCd() {
	// return catalogStatCd;
	// }
	// public void setCatalogStatCd(String catalogStatCd) {
	// this.catalogStatCd = catalogStatCd;
	// }
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

	public String getIntroText() {
		return this.introText;
	}

	public void setIntroText(String introText) {
		this.introText = introText;
	}

	public String getCreateCatalogId() {
		return this.createCatalogId;
	}

	public void setCreateCatalogId(String createCatalogId) {
		this.createCatalogId = createCatalogId;
	}

	public String getCreateBrandId() {
		return this.createBrandId;
	}

	public void setCreateBrandId(String createBrandId) {
		this.createBrandId = createBrandId;
	}

	public String getCatalogTag() {
		return this.catalogTag;
	}

	public void setCatalogTag(String catalogTag) {
		this.catalogTag = catalogTag;
	}

}
