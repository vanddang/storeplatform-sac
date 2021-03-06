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

import java.util.List;

/**
 * <pre>
 * 전처리 카탈로그 Value Object.
 * </pre>
 * 
 * Created on : 2014-01-02 Created by : 김형식, SK플래닛 Last Updated on : 2014-01-02 Last Updated by : 김형식, SK플래닛
 */
public class DpCatalogInfo extends BrandCatalogProdImgInfo {
	private static final long serialVersionUID = 1L;
	// TB_DP_SHPG_CATALOG
	private String catalogId; // INSERT_TB_DP_SHPG_CATALOG.CATALOG_ID
	private String dpCatNo; // INSERT_TB_DP_SHPG_CATALOG.DP_CAT_NO
	private String brandId; // INSERT_TB_DP_SHPG_CATALOG.BRAND_ID
	private String catalogDesc; // INSERT_TB_DP_SHPG_CATALOG_DESC.CATALOG_DESC
	private String catalogNm; // INSERT_TB_DP_SHPG_CATALOG_DESC.CATALOG_NM
	// private String catalogStatCd;
	private String topImgPath; // 
	private String dtlImgPath; // 
	private String txType; // 상품구분 브랜드 : bd, 카탈로그 : ct
	private String cudType; // CUD
	private String introText; // 한줄설명 INSERT_TB_DP_SHPG_CATALOG_DESC.
	private String createCatalogId; // 카탈로그ID 생성
	private String createBrandId; // 카탈로그ID 생성
	private String catalogTag; // 카탈로그태그
	private List<String> fileNameList;
	private List<byte[]> isList;
	private String catalogVodUrl;	//카탈로그 VOD URL
	private String catalogVodThumbnail; //카탈로그 VOD 썸네일 이미지
	private List<String> fileDtlImgPathList; //카탈로그 상세 이미지 정보
	/**
	 * @return the catalogId
	 */
	public String getCatalogId() {
		return this.catalogId;
	}

	/**
	 * @param catalogId
	 *            the catalogId to set
	 */
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
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
	 * @return the catalogDesc
	 */
	public String getCatalogDesc() {
		return this.catalogDesc;
	}

	/**
	 * @param catalogDesc
	 *            the catalogDesc to set
	 */
	public void setCatalogDesc(String catalogDesc) {
		this.catalogDesc = catalogDesc;
	}

	/**
	 * @return the catalogNm
	 */
	public String getCatalogNm() {
		return this.catalogNm;
	}

	/**
	 * @param catalogNm
	 *            the catalogNm to set
	 */
	public void setCatalogNm(String catalogNm) {
		this.catalogNm = catalogNm;
	}

	/**
	 * @return the topImgPath
	 */
	public String getTopImgPath() {
		return this.topImgPath;
	}

	/**
	 * @param topImgPath
	 *            the topImgPath to set
	 */
	public void setTopImgPath(String topImgPath) {
		this.topImgPath = topImgPath;
	}

	/**
	 * @return the dtlImgPath
	 */
	public String getDtlImgPath() {
		return this.dtlImgPath;
	}

	/**
	 * @param dtlImgPath
	 *            the dtlImgPath to set
	 */
	public void setDtlImgPath(String dtlImgPath) {
		this.dtlImgPath = dtlImgPath;
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
	 * @return the introText
	 */
	public String getIntroText() {
		return this.introText;
	}

	/**
	 * @param introText
	 *            the introText to set
	 */
	public void setIntroText(String introText) {
		this.introText = introText;
	}

	/**
	 * @return the createCatalogId
	 */
	public String getCreateCatalogId() {
		return this.createCatalogId;
	}

	/**
	 * @param createCatalogId
	 *            the createCatalogId to set
	 */
	public void setCreateCatalogId(String createCatalogId) {
		this.createCatalogId = createCatalogId;
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

	/**
	 * @return the catalogTag
	 */
	public String getCatalogTag() {
		return this.catalogTag;
	}

	/**
	 * @param catalogTag
	 *            the catalogTag to set
	 */
	public void setCatalogTag(String catalogTag) {
		this.catalogTag = catalogTag;
	}

	/**
	 * @return the fileNameList
	 */
	public List<String> getFileNameList() {
		return this.fileNameList;
	}

	/**
	 * @param fileNameList
	 *            the fileNameList to set
	 */
	public void setFileNameList(List<String> fileNameList) {
		this.fileNameList = fileNameList;
	}

	/**
	 * @return the isList
	 */
	public List<byte[]> getIsList() {
		return this.isList;
	}

	/**
	 * @param isList
	 *            the isList to set
	 */
	public void setIsList(List<byte[]> isList) {
		this.isList = isList;
	}

	/**
	 * @return the catalogVodUrl
	 */
	public String getCatalogVodUrl() {
		return catalogVodUrl;
	}

	/**
	 * @param catalogVodUrl the catalogVodUrl to set
	 */
	public void setCatalogVodUrl(String catalogVodUrl) {
		this.catalogVodUrl = catalogVodUrl;
	}

	/**
	 * @return the catalogVodThumbnail
	 */
	public String getCatalogVodThumbnail() {
		return catalogVodThumbnail;
	}

	/**
	 * @param catalogVodThumbnail the catalogVodThumbnail to set
	 */
	public void setCatalogVodThumbnail(String catalogVodThumbnail) {
		this.catalogVodThumbnail = catalogVodThumbnail;
	}

	/**
	 * @return the fileDtlImgPathList
	 */
	public List<String> getFileDtlImgPathList() {
		return fileDtlImgPathList;
	}

	/**
	 * @param fileDtlImgPathList the fileDtlImgPathList to set
	 */
	public void setFileDtlImgPathList(List<String> fileDtlImgPathList) {
		this.fileDtlImgPathList = fileDtlImgPathList;
	}

}
