/*****************************************************************************
 * SKT TStore Project ::: PARTNER Site :::: Shopping Coupon interface
 *****************************************************************************
 * 1.클래스 개요 :
 * 2.작   성  자 : Sun-taek Lim
 * 3.작 성 일 자 : 2013. 4. 23.
 * <pre>
 * 4.수 정 일 자 :
 *      . <날짜> : <수정 내용> (성명)
 *      . 2013. 4. 23.  : 최초 생성 (Sun-taek Lim)
 * @author Sun-taek Lim
 * </pre>
 * @version 1.0
 *****************************************************************************/

package com.skplanet.storeplatform.sac.api.vo;

import java.io.Serializable;

public class CouponResponseInfo implements Serializable {

	static final long serialVersionUID = -554248210111444466L;

	// 브랜드 추가
	private String brandCode; // 브랜드코드 String Y 쿠폰서버에서 생성한 브랜드 코드
	private String brandName; // 브랜드명 String Y 쿠폰서버에서 생성한 브랜드명;
	private String brandCategory; // 브랜드 카테고리 String Y 브랜드용 카테고리;
	private String brandImage; // 브랜드 이미지 String Y 브랜드 이미지 경로;
	private String txType; // 상품구분 String Y bd 로 고정;;
	private String cudType; // 추가수정플래그 String 1 Y C:신규추가, U:변경;
	private String txId; // 트랜젝션아이디 String 20 Y 스토어용 트랜젝션 아이디;

	// 카달로그 추가
	private String catalogCode; // 카탈로그 코드 String Y 쿠폰서버에서 생성한 카탈로그 코드;
	private String catalogName; // 카탈로그 명 String Y 쿠폰서버에서 생성한 카탈로그 명;
	private String catalogCategory; // 카탈로그 카테고리 String Y 카탈로그 용 카테고리;
	private String catalogDescription; // 카탈로그 설명 String Y 카탈로그 설명;
	private String catalogImage1; // 카탈로그 대표이미지 String Y 카탈로그 대표 이미지 경로;;
	private String catalogImage2; // 카탈로그 상세이미지 String Y 카탈로그 상세 이미지 경로;

	// 상품추가 (XML : 쿠폰,ITEM 상품 정보 )
	private String rData;

	private String rCode;
	private String rMsg;
	private String couponCode;

	// 특가상품
	private String specialYN; // 특가상품유무

	private String eventName; // 이벤트명
	private String eventStartDate; // 이벤트 시작일
	private String eventEndDate; // 이벤트 종료일
	private String eventDcRate; // 이벤트 할인율

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
	 * @return the rCode
	 */
	public String getRCode() {
		return this.rCode;
	}

	/**
	 * @param code
	 *            the rCode to set
	 */
	public void setRCode(String code) {
		this.rCode = code;
	}

	/**
	 * @return the rMsg
	 */
	public String getRMsg() {
		return this.rMsg;
	}

	/**
	 * @param msg
	 *            the rMsg to set
	 */
	public void setRMsg(String msg) {
		this.rMsg = msg;
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
	 * @return the specialYN
	 */
	public String getSpecialYN() {
		return this.specialYN;
	}

	/**
	 * @param specialYN
	 *            the specialYN to set
	 */
	public void setSpecialYN(String specialYN) {
		this.specialYN = specialYN;
	}

	/**
	 * @return the eventName
	 */
	public String getEventName() {
		return this.eventName;
	}

	/**
	 * @param eventName
	 *            the eventName to set
	 */
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	/**
	 * @return the eventStartDate
	 */
	public String getEventStartDate() {
		return this.eventStartDate;
	}

	/**
	 * @param eventStartDate
	 *            the eventStartDate to set
	 */
	public void setEventStartDate(String eventStartDate) {
		this.eventStartDate = eventStartDate;
	}

	/**
	 * @return the eventEndDate
	 */
	public String getEventEndDate() {
		return this.eventEndDate;
	}

	/**
	 * @param eventEndDate
	 *            the eventEndDate to set
	 */
	public void setEventEndDate(String eventEndDate) {
		this.eventEndDate = eventEndDate;
	}

	/**
	 * @return the eventDcRate
	 */
	public String getEventDcRate() {
		return this.eventDcRate;
	}

	/**
	 * @param eventDcRate
	 *            the eventDcRate to set
	 */
	public void setEventDcRate(String eventDcRate) {
		this.eventDcRate = eventDcRate;
	}

}
