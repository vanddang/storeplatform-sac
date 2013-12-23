/*****************************************************************************
 * SKT TStore Project ::: PARTNER Site :::: Shopping Coupon interface
 *****************************************************************************
 * 1.클래스 개요 : 쿠폰 시스템에서 넘어오는 파라메터 정보
 * 2.작   성  자 : Sun-taek Lim
 * 3.작 성 일 자 : 2013. 4. 2.
 * <pre>
 * 4.수 정 일 자 :
 *      . <날짜> : <수정 내용> (성명)
 *      . 2013. 4. 2.  : 최초 생성 (Sun-taek Lim)
 * @author Sun-taek Lim
 * </pre>
 * @version 1.0
 *****************************************************************************/

package com.skplanet.storeplatform.sac.api.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.sac.api.inf.ITX_TYPE_CODE.TX_TYPE_CODE;

public class CouponParameterInfo implements Serializable {

	static final long serialVersionUID = -2280833023185967952L;
	// 공통
	private String txType; // 상품구분 String Y bd 로 고정;;
	private String cudType; // 추가수정플래그 String 1 Y C:신규추가, U:변경;
	private String txId; // 트랜젝션아이디 String 20 Y 스토어용 트랜젝션 아이디;

	// 브랜드 추가
	private String brandCode; // 브랜드코드 String Y 쿠폰서버에서 생성한 브랜드 코드
	private String brandName; // 브랜드명 String Y 쿠폰서버에서 생성한 브랜드명;
	private String brandCategory; // 브랜드 카테고리 String Y 브랜드용 카테고리;
	private String brandImage; // 브랜드 이미지 String Y 브랜드 이미지 경로;

	// 카달로그 추가
	private String catalogCode; // 카탈로그 코드 String Y 쿠폰서버에서 생성한 카탈로그 코드;
	private String catalogName; // 카탈로그 명 String Y 쿠폰서버에서 생성한 카탈로그 명;
	private String catalogCategory; // 카탈로그 카테고리 String Y 카탈로그 용 카테고리;
	private String catalogDescription; // 카탈로그 설명 String Y 카탈로그 설명;
	private String catalogImage1; // 카탈로그 대표이미지 String Y 카탈로그 대표 이미지 경로;;
	private String catalogImage2; // 카탈로그 상세이미지 String Y 카탈로그 상세 이미지 경로;
	private String intro_text; // 한줄소개
	private String tag; // 태그

	// 상품추가 (XML : 쿠폰,ITEM 상품 정보 )
	private String rData;

	// 상품갱신
	private String couponCode; // 쿠폰코드 String Y ‘상품 추가’ API에서 전달한 쿠폰코드;
	private String coupnStatus; // 쿠폰상태 INT Y 0=전시, 1=미전시, 2=판매만료(전시 유지);
	private String itemCode; // 아이템코드
	private String upType; // 0=상품상태변경, 1=단품상태변경, 2=상품+단품상태 모두 변경

	private String couponCodes;

	public boolean checkTX_ID() {

		return this.txId == null || this.txId.length() != 22 ? false : true;
	}

	public boolean checkCUD_TYPE() {
		return this.cudType == null || this.cudType.length() != 1 ? false : true;
	}

	public boolean checkTX_TYPE() {

		if (this.txType == null || this.txType.length() != 2)
			return false;

		return TX_TYPE_CODE.get(this.txType) == null ? false : true;

	}

	public boolean checkBRAND_CODE() {

		return this.brandCode == null ? false : true;
	}

	public boolean checkBRAND_NAME() {
		return this.brandName == null ? false : true;
	}

	public boolean checkBRAND_CATEGORY() {
		return this.brandCategory == null ? false : true;
	}

	public boolean checkBRAND_IMAGE() {
		return this.brandImage == null ? false : true;
	}

	public boolean checkCATALOG_CODE() {
		return this.catalogCode == null ? false : true;
	}

	public boolean checkCATALOG_NAME() {
		return this.catalogName == null ? false : true;
	}

	public boolean checkCATALOG_CATEGORY() {
		return this.catalogCategory == null ? false : true;
	}

	public boolean checkCATALOG_DESC() {
		return this.catalogDescription == null ? false : true;
	}

	public boolean checkCATALOG_IMG1() {
		return this.catalogImage1 == null ? false : true;
	}

	public boolean checkCATALOG_IMG2() {
		return this.catalogImage2 == null ? false : true;
	}

	public boolean checkCOUPON_CODE() {

		return this.couponCode == null || this.couponCode.length() < 1 ? false : true;
	}

	public boolean checkCOUPON_STATUS() {

		return this.coupnStatus == null || this.coupnStatus.length() != 1 ? false : true;
	}

	public boolean checkITEM_CODE() {

		return this.itemCode == null ? false : true;
	}

	/**
	 * @return the brandCode
	 */
	public String getBrandCode() {
		return this.brandCode;
	}

	/**
	 * @param brandCode
	 *            the brandCode to set
	 */
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	/**
	 * @return the brandName
	 */
	public String getBrandName() {
		return this.brandName;
	}

	/**
	 * @param brandName
	 *            the brandName to set
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	/**
	 * @return the brandCategory
	 */
	public String getBrandCategory() {
		return this.brandCategory;
	}

	/**
	 * @param brandCategory
	 *            the brandCategory to set
	 */
	public void setBrandCategory(String brandCategory) {
		this.brandCategory = brandCategory;
	}

	/**
	 * @return the brandImage
	 */
	public String getBrandImage() {
		return this.brandImage;
	}

	/**
	 * @param brandImage
	 *            the brandImage to set
	 */
	public void setBrandImage(String brandImage) {
		this.brandImage = brandImage;
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
	 * @return the txId
	 */
	public String getTxId() {
		return this.txId;
	}

	/**
	 * @param txId
	 *            the txId to set
	 */
	public void setTxId(String txId) {
		this.txId = txId;
	}

	/**
	 * @return the catalogCode
	 */
	public String getCatalogCode() {
		return this.catalogCode;
	}

	/**
	 * @param catalogCode
	 *            the catalogCode to set
	 */
	public void setCatalogCode(String catalogCode) {
		this.catalogCode = catalogCode;
	}

	/**
	 * @return the catalogName
	 */
	public String getCatalogName() {
		return this.catalogName;
	}

	/**
	 * @param catalogName
	 *            the catalogName to set
	 */
	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	/**
	 * @return the catalogCategory
	 */
	public String getCatalogCategory() {
		return this.catalogCategory;
	}

	/**
	 * @param catalogCategory
	 *            the catalogCategory to set
	 */
	public void setCatalogCategory(String catalogCategory) {
		this.catalogCategory = catalogCategory;
	}

	/**
	 * @return the catalogDescription
	 */
	public String getCatalogDescription() {
		return this.catalogDescription;
	}

	/**
	 * @param catalogDescription
	 *            the catalogDescription to set
	 */
	public void setCatalogDescription(String catalogDescription) {
		this.catalogDescription = catalogDescription;
	}

	/**
	 * @return the catalogImage1
	 */
	public String getCatalogImage1() {
		return this.catalogImage1;
	}

	/**
	 * @param catalogImage1
	 *            the catalogImage1 to set
	 */
	public void setCatalogImage1(String catalogImage1) {
		this.catalogImage1 = catalogImage1;
	}

	/**
	 * @return the catalogImage2
	 */
	public String getCatalogImage2() {
		return this.catalogImage2;
	}

	/**
	 * @param catalogImage2
	 *            the catalogImage2 to set
	 */
	public void setCatalogImage2(String catalogImage2) {
		this.catalogImage2 = catalogImage2;
	}

	/**
	 * @return the rData
	 */
	public String getRData() {
		return this.rData;
	}

	/**
	 * @param data
	 *            the rData to set
	 */
	public void setRData(String data) {
		this.rData = data;
	}

	/**
	 * @return the couponCode
	 */
	public String getCouponCode() {
		return this.couponCode;
	}

	/**
	 * @param couponCode
	 *            the couponCode to set
	 */
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	/**
	 * @return the coupnStatus
	 */
	public String getCoupnStatus() {
		return this.coupnStatus;
	}

	/**
	 * @param coupnStatus
	 *            the coupnStatus to set
	 */
	public void setCoupnStatus(String coupnStatus) {
		this.coupnStatus = coupnStatus;
	}

	/**
	 * @return the itemCode
	 */
	public String getItemCode() {
		return this.itemCode;
	}

	/**
	 * @param itemCode
	 *            the itemCode to set
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * @return the intro_text
	 */
	public String getIntro_text() {
		return this.intro_text;
	}

	/**
	 * @param intro_text
	 *            the intro_text to set
	 */
	public void setIntro_text(String intro_text) {
		this.intro_text = intro_text;
	}

	/**
	 * @return the upType
	 */
	public String getUpType() {
		return this.upType;
	}

	/**
	 * @param upType
	 *            the upType to set
	 */
	public void setUpType(String upType) {
		this.upType = upType;
	}

	/**
	 * @return the couponCodes
	 */
	public String getCouponCodes() {
		return this.couponCodes;
	}

	/**
	 * @param couponCodes
	 *            the couponCodes to set
	 */
	public void setCouponCodes(String couponCodes) {
		this.couponCodes = couponCodes;
	}

	/**
	 * @return the tag
	 */
	public String getTag() {
		return this.tag;
	}

	/**
	 * @param tag
	 *            the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

}
