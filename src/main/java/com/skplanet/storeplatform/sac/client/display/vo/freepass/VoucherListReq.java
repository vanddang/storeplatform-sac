/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.freepass;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 정액제 리스트 조회 Request Value Object.
 * 
 * Updated on : 2015. 4. 30. Updated by : 이태균, IS PLUS.
 */
public class VoucherListReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String topMenuId; // 최상위메뉴ID
	@NotNull
	@NotBlank
	private String kind; // 자유이용권 종류
	private String productId; // 상품ID
	private String menuId; // 메뉴ID
	private String channelId; // 채널상품ID
	private int offset; // offset
	private int count; // count
	private String bannerImageCd; // 배너이미지
	private String thumbnailImageCd; // 썸네일이미지
	private String prodStatusCd; // 상품상태
	private String standardModelCd; // 상품상태
	private String prodRshpCd;
	private String cmpxProdGradeCd; // 이용권등급코드;
	private String[] arrayCmpxProdGradeCd; // 이용권등급코드 Array
	private String prodGradeCd; // 상품이용등급코드;
	private String[] arrayProdGradeCd; // 상품이용등급코드 Array
	private String prodGrdExtraCd; // 19+ 상품여부

	// common req 전까지 임시
	private String tenantId; // 테넌트ID
	private String langCd; // 언어
	private String deviceModelCd; // 단말모델
	private String virtualDeviceModelNo; // android_standard2

	// topMenuId 복수개 허용
	private String[] arrTopMenuId; // topMenuId 복수개

	/**
	 * @return the topMenuId
	 */
	public String getTopMenuId() {
		return this.topMenuId;
	}

	/**
	 * @param topMenuId
	 *            the topMenuId to set
	 */
	public void setTopMenuId(String topMenuId) {
		this.topMenuId = topMenuId;
	}

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
	 * @return the menuId
	 */
	public String getMenuId() {
		return this.menuId;
	}

	/**
	 * @param menuId
	 *            the menuId to set
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * @return the channelId
	 */
	public String getChannelId() {
		return this.channelId;
	}

	/**
	 * @param channelId
	 *            the channelId to set
	 */
	public void setChannelId(String channelId) {
		this.channelId = channelId;
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
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	 * @return the thumnailImageCd
	 */
	public String getThumbnailImageCd() {
		return this.thumbnailImageCd;
	}

	/**
	 * @param thumnailImageCd
	 *            the thumnailImageCd to set
	 */
	public void setThumbnailImageCd(String thumnailImageCd) {
		this.thumbnailImageCd = thumnailImageCd;
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
	 * @return the arrTopMenuId
	 */
	public String[] getArrTopMenuId() {
		return this.arrTopMenuId;
	}

	/**
	 * @param arrTopMenuId
	 *            the arrTopMenuId to set
	 */
	public void setArrTopMenuId(String[] arrTopMenuId) {
		this.arrTopMenuId = arrTopMenuId;
	}

	/**
	 * @param the
	 *            prodGradeCd
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

	/**
	 * @param the
	 *            arrayProdGradeCd
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
	 * @param the
	 *            cmpxProdGradeCd
	 */
	public String getCmpxProdGradeCd() {
		return this.cmpxProdGradeCd;
	}

	/**
	 * @param cmpxProdGradeCd
	 *            the cmpxProdGradeCd to set
	 */
	public void setCmpxProdGradeCd(String cmpxProdGradeCd) {
		this.cmpxProdGradeCd = cmpxProdGradeCd;
	}

	/**
	 * @param the
	 *            arrayCmpxProdGradeCd
	 */
	public String[] getArrayCmpxProdGradeCd() {
		return this.arrayCmpxProdGradeCd;
	}

	/**
	 * @param arrayCmpxProdGradeCd
	 *            the arrayCmpxProdGradeCd to set
	 */
	public void setArrayCmpxProdGradeCd(String[] arrayCmpxProdGradeCd) {
		this.arrayCmpxProdGradeCd = arrayCmpxProdGradeCd;
	}

	/**
	 * @param the
	 *            prodGrdExtraCd
	 */
	public String getProdGrdExtraCd() {
		return this.prodGrdExtraCd;
	}

	/**
	 * @param prodGrdExtraCd
	 *            the prodGrdExtraCd to set
	 */
	public void setProdGrdExtraCd(String prodGrdExtraCd) {
		this.prodGrdExtraCd = prodGrdExtraCd;
	}

}
