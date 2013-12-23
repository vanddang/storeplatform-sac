/*****************************************************************************
 * SKT TStore Project ::: PARTNER Site :::: BackGround Job
 *****************************************************************************
 * 1.클래스 개요 :
 * 2.작   성  자 : Kang Hyun Gu
 * 3.작 성 일 자 : 2013. 4. 08.
 * <pre>
 * 4.수 정 일 자 :
 *      . <날짜> : <수정 내용> (성명)
 *      . 2013. 4. 08.  : 최초 생성 (Kang Hyun Gu)
 * @author Kang Hyun Gu
 * </pre>
 * @version 1.0
 *****************************************************************************/

package com.skplanet.storeplatform.sac.api.vo;


public class DpCatalogInfo extends BrandCatalogProdImgInfo {

	// TBL_DP_CATALOG_INFO
	private String catalogId; // TBL_DP_CATALOG_INFO.CATALOG_ID
	private String dpCatNo; // TBL_DP_CATALOG_INFO.DP_CAT_NO
	private String brandId; // TBL_DP_CATALOG_INFO.BRAND_ID
	private String catalogDesc; // TBL_DP_CATALOG_INFO.CATALOG_DESC
	private String catalogNm; // TBL_DP_CATALOG_INFO.CATALOG_NM
	// private String catalogStatCd;
	private String topImgPath; // TBL_DP_CATALOG_INFO.TOP_IMG_PATH
	private String dtlImgPath; // TBL_DP_CATALOG_INFO.DTL_IMG_PATH
	private String txType; // 상품구분 브랜드 : bd, 카탈로그 : ct
	private String cudType; // CUD
	private String introText; // 한줄설명
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
