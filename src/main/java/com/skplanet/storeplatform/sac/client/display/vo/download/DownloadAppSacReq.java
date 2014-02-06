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

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Download 앱 상품 정보 조회 Value Object.
 * 
 * Updated on : 2014. 01. 21. Updated by : 이석희, 인크로스.
 */
public class DownloadAppSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;
	private String tenantId; // tenantId
	private String systemId; // 시스템Id
	private String deviceModelCd; // 단말모델코드
	private String langCd; // 언어코드
	private String osVersion; // os 버전
	private String lcdSize; // lcd 크기
	private String category; // 상품 유형

	@NotEmpty(message = "필수 파라미터 입니다.")
	private String filteredBy; // 조회유형
	private String productId; // 상품Id
	private String packageName; // 패키지명
	@NotEmpty(message = "필수 파라미터 입니다.")
	private String deviceKey; // device key
	@NotEmpty(message = "필수 파라미터 입니다.")
	private String userKey; // 판매자 회원번호
	private String dummy; // dummy data check
	private String imageCd; // 이미지 코드

	/**
	 * 
	 * <pre>
	 * tenantId.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * 
	 * <pre>
	 * tenantId.
	 * </pre>
	 * 
	 * @param tenantId
	 *            tenantId
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * 
	 * <pre>
	 * 시스템Id.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getSystemId() {
		return this.systemId;
	}

	/**
	 * 
	 * <pre>
	 * 시스템Id.
	 * </pre>
	 * 
	 * @param systemId
	 *            systemId
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	/**
	 * 
	 * <pre>
	 * 단말모델코드.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getDeviceModelCd() {
		return this.deviceModelCd;
	}

	/**
	 * 
	 * <pre>
	 * 단말모델코드.
	 * </pre>
	 * 
	 * @param deviceModelCd
	 *            deviceModelCd
	 */
	public void setDeviceModelCd(String deviceModelCd) {
		this.deviceModelCd = deviceModelCd;
	}

	/**
	 * 
	 * <pre>
	 * 언어코드.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getLangCd() {
		return this.langCd;
	}

	/**
	 * 
	 * <pre>
	 * 언어코드.
	 * </pre>
	 * 
	 * @param langCd
	 *            langCd
	 */
	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}

	/**
	 * @return the lcdSize
	 */
	public String getLcdSize() {
		return this.lcdSize;
	}

	/**
	 * @param lcdSize
	 *            the lcdSize to set
	 */
	public void setLcdSize(String lcdSize) {
		this.lcdSize = lcdSize;
	}

	/**
	 * 
	 * <pre>
	 * os 버전.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getOsVersion() {
		return this.osVersion;
	}

	/**
	 * 
	 * <pre>
	 * 언어코드.
	 * </pre>
	 * 
	 * @param osVersion
	 *            osVersion
	 */
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	/**
	 * 
	 * <pre>
	 * 상품 유형.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getCategory() {
		return this.category;
	}

	/**
	 * 
	 * <pre>
	 * 상품 유형.
	 * </pre>
	 * 
	 * @param category
	 *            category
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * 
	 * <pre>
	 * 조회 유형.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getFilteredBy() {
		return this.filteredBy;
	}

	/**
	 * 
	 * <pre>
	 * 조회 유형.
	 * </pre>
	 * 
	 * @param filteredBy
	 *            filteredBy
	 */
	public void setFilteredBy(String filteredBy) {
		this.filteredBy = filteredBy;
	}

	/**
	 * 
	 * <pre>
	 * 상품 ID.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getProductId() {
		return this.productId;
	}

	/**
	 * 
	 * <pre>
	 * 상품 ID.
	 * </pre>
	 * 
	 * @param productId
	 *            productId
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * 
	 * <pre>
	 * 패키지 명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getPackageName() {
		return this.packageName;
	}

	/**
	 * 
	 * <pre>
	 * 패키지 명.
	 * </pre>
	 * 
	 * @param packageName
	 *            packageName
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	/**
	 * 
	 * <pre>
	 * device key.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getDeviceKey() {
		return this.deviceKey;
	}

	/**
	 * 
	 * <pre>
	 * device key.
	 * </pre>
	 * 
	 * @param deviceKey
	 *            deviceKey
	 */
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	/**
	 * 
	 * <pre>
	 * 사용자 Key.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * 
	 * <pre>
	 * 사용자 Key.
	 * </pre>
	 * 
	 * @param userKey
	 *            userKey
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * 
	 * <pre>
	 * dummy check.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getDummy() {
		return this.dummy;
	}

	/**
	 * 
	 * <pre>
	 * dummy check.
	 * </pre>
	 * 
	 * @param dummy
	 *            dummy
	 */
	public void setDummy(String dummy) {
		this.dummy = dummy;
	}

	/**
	 * @return the imgCd
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
