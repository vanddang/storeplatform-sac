package com.skplanet.storeplatform.sac.client.display.vo.freepass;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 특정 상품에 적용할 정액제 리스트 조회 Request Value Object.
 * 
 * Updated on : 2014. 02. 26. Updated by : 서영배, GTSOFT.
 */
public class FreepassSpecificReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String kind; // 자유이용권 종류
	@NotNull
	@NotBlank
	private String productId; // 상품ID
	private int offset; // offset
	private int count; // count
	private String bannerImageCd; // 배너이미지
	private String thumbnailImageCd; // 썸네일이미지
	private String prodStatusCd; // 상품상태
	private String standardModelCd; // 상품상태
	private String prodRshpCd;

	// common req 전까지 임시
	private String tenantId; // 테넌트ID
	private String langCd; // 언어
	private String deviceModelCd; // 단말모델
	private String virtualDeviceModelNo; // android_standard2

	// topMenuId 복수개 허용
	private String[] arrKind; // kind 복수개

	private String deviceKey; // 디바이스키
	private String userKey; // 사용자고유키

	private String[] arrayProdGradeCd; // 상품등급코드 Array
	private String prodGradeCd; // 상품등급코드

	/**
	 * @return the kind
	 */
	public String getKind() {
		return this.kind;
	}

	/**
	 * @param kind
	 *            the kind to set
	 */
	public void setKind(String kind) {
		this.kind = kind;
	}

	/**
	 * @return the productId
	 */
	public String getProductId() {
		return this.productId;
	}

	/**
	 * @param productId
	 *            the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @return the offset
	 */
	public int getOffset() {
		return this.offset;
	}

	/**
	 * @param offset
	 *            the offset to set
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return this.count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @return the bannerImageCd
	 */
	public String getBannerImageCd() {
		return this.bannerImageCd;
	}

	/**
	 * @param bannerImageCd
	 *            the bannerImageCd to set
	 */
	public void setBannerImageCd(String bannerImageCd) {
		this.bannerImageCd = bannerImageCd;
	}

	/**
	 * @return the thumbnailImageCd
	 */
	public String getThumbnailImageCd() {
		return this.thumbnailImageCd;
	}

	/**
	 * @param thumbnailImageCd
	 *            the thumbnailImageCd to set
	 */
	public void setThumbnailImageCd(String thumbnailImageCd) {
		this.thumbnailImageCd = thumbnailImageCd;
	}

	/**
	 * @return the prodStatusCd
	 */
	public String getProdStatusCd() {
		return this.prodStatusCd;
	}

	/**
	 * @param prodStatusCd
	 *            the prodStatusCd to set
	 */
	public void setProdStatusCd(String prodStatusCd) {
		this.prodStatusCd = prodStatusCd;
	}

	/**
	 * @return the standardModelCd
	 */
	public String getStandardModelCd() {
		return this.standardModelCd;
	}

	/**
	 * @param standardModelCd
	 *            the standardModelCd to set
	 */
	public void setStandardModelCd(String standardModelCd) {
		this.standardModelCd = standardModelCd;
	}

	/**
	 * @return the prodRshpCd
	 */
	public String getProdRshpCd() {
		return this.prodRshpCd;
	}

	/**
	 * @param prodRshpCd
	 *            the prodRshpCd to set
	 */
	public void setProdRshpCd(String prodRshpCd) {
		this.prodRshpCd = prodRshpCd;
	}

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the langCd
	 */
	public String getLangCd() {
		return this.langCd;
	}

	/**
	 * @param langCd
	 *            the langCd to set
	 */
	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}

	/**
	 * @return the deviceModelCd
	 */
	public String getDeviceModelCd() {
		return this.deviceModelCd;
	}

	/**
	 * @param deviceModelCd
	 *            the deviceModelCd to set
	 */
	public void setDeviceModelCd(String deviceModelCd) {
		this.deviceModelCd = deviceModelCd;
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
	 * @return the arrKind
	 */
	public String[] getArrKind() {
		return this.arrKind;
	}

	/**
	 * @param arrKind
	 *            the arrKind to set
	 */
	public void setArrKind(String[] arrKind) {
		this.arrKind = arrKind;
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
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
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

	/**
	 * @return the prodGradeCd
	 */
	public String getProdGradeCd() {
		return this.prodGradeCd;
	}

	/**
	 * @param prodGradeCd
	 *            the prodGradeCd to set
	 */
	public void setProdGradeCd(String prodGradeCd) {
		this.prodGradeCd = prodGradeCd;
	}

}
