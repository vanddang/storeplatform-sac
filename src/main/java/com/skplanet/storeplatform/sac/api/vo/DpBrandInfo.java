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
 * 전처리 브랜드 Value Object.
 * </pre>
 * 
 * Created on : 2014-01-02 Created by : 김형식, SK플래닛 Last Updated on : 2014-01-02 Last Updated by : 김형식, SK플래닛
 */
public class DpBrandInfo extends BrandCatalogProdImgInfo {
	private static final long serialVersionUID = 1L;
	private String brandId; // TB_DP_SHPG_BRAND.BRAND_ID
	private String brandNm; // TB_DP_SHPG_BRAND.BRAND_NM
	private String dpCatNo; // TB_DP_SHPG_BRAND.DP_CAT_NO
	private String brandImgPath; // TB_DP_SHPG_BRAND.BRAND_IMG_PATH
	private String txType; // 상품구분 브랜드 : bd, 카탈로그 : ct
	private String cudType; // CUD
	private String createBrandId; // 브랜드 생성 ID

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
	 * @return the brandNm
	 */
	public String getBrandNm() {
		return this.brandNm;
	}

	/**
	 * @param brandNm
	 *            the brandNm to set
	 */
	public void setBrandNm(String brandNm) {
		this.brandNm = brandNm;
	}

	/**
	 * @return the dpCatNo
	 */
	public String getDpCatNo() {
		return this.dpCatNo;
	}

	/**
	 * @param dpCatNo
	 *            the dpCatNo to set
	 */
	public void setDpCatNo(String dpCatNo) {
		this.dpCatNo = dpCatNo;
	}

	/**
	 * @return the brandImgPath
	 */
	public String getBrandImgPath() {
		return this.brandImgPath;
	}

	/**
	 * @param brandImgPath
	 *            the brandImgPath to set
	 */
	public void setBrandImgPath(String brandImgPath) {
		this.brandImgPath = brandImgPath;
	}

	/**
	 * @return the txType
	 */
	public String getTxType() {
		return this.txType;
	}

	/**
	 * @param txType
	 *            the txType to set
	 */
	public void setTxType(String txType) {
		this.txType = txType;
	}

	/**
	 * @return the cudType
	 */
	public String getCudType() {
		return this.cudType;
	}

	/**
	 * @param cudType
	 *            the cudType to set
	 */
	public void setCudType(String cudType) {
		this.cudType = cudType;
	}

	/**
	 * @return the createBrandId
	 */
	public String getCreateBrandId() {
		return this.createBrandId;
	}

	/**
	 * @param createBrandId
	 *            the createBrandId to set
	 */
	public void setCreateBrandId(String createBrandId) {
		this.createBrandId = createBrandId;
	}

}
