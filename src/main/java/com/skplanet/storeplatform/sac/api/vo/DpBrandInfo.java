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


public class DpBrandInfo extends BrandCatalogProdImgInfo {

	private String brandId; // TBL_DP_BRAND_INFO.BRAND_ID
	private String brandNm; // TBL_DP_BRAND_INFO.BRAND_NM
	private String dpCatNo; // TBL_DP_BRAND_INFO.DP_CAT_NO
	private String brandImgPath; // TBL_DP_BRAND_INFO.BRAND_IMG_PATH
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
