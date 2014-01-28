/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.download;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * ebook 상품 정보 조회(for download) Request Value Object.
 * 
 * Updated on : 2014. 01. 27. Updated by : 이태희.
 */
public class DownloadEbookReq extends CommonInfo {

	private static final long serialVersionUID = 1L;
	private String idType; // ID유형
	private String productId; // 상품ID
	private String deviceKey; // 디바이스키
	private String userKey; // 사용자고유키
	private String tenantId; // 테넌트ID
	private String languageCd; // 언어코드
	private String deviceModelCd; // 디바이스모델코드
	private String imageCd; // 이미지코드

	/**
	 * @return the idType
	 */
	public String getIdType() {
		return this.idType;
	}

	/**
	 * @param idType
	 *            the idType to set
	 */
	public void setIdType(String idType) {
		this.idType = idType;
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
	 * @return the languageCd
	 */
	public String getLanguageCd() {
		return this.languageCd;
	}

	/**
	 * @param languageCd
	 *            the languageCd to set
	 */
	public void setLanguageCd(String languageCd) {
		this.languageCd = languageCd;
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
	 * @return the imageCd
	 */
	public String getImageCd() {
		return this.imageCd;
	}

	/**
	 * @param imageCd
	 *            the imageCd to set
	 */
	public void setImageCd(String imageCd) {
		this.imageCd = imageCd;
	}
}
