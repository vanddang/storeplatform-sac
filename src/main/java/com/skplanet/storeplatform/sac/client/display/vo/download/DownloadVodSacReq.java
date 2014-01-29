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
public class DownloadVodSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;
	private String tenantId; // tenantId
	private String systemId; // 시스템Id
	private String deviceModelCd; // 단말모델코드
	private String langCd; // 언어코드
	@NotEmpty(message = "필수 파라미터 입니다.")
	private String category; // 상품 유형
	@NotEmpty(message = "필수 파라미터 입니다.")
	private String productId; // 상품Id
	@NotEmpty(message = "필수 파라미터 입니다.")
	private String idType; // Id 유형
	@NotEmpty(message = "필수 파라미터 입니다.")
	private String deviceKey; // device Key
	@NotEmpty(message = "필수 파라미터 입니다.")
	private String userKey; // 사용자 Key
	private String imgCd; // 이미지 코드
	private String dummy; // dummy data check

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
	 * ID 유형.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getIdType() {
		return this.idType;
	}

	/**
	 * 
	 * <pre>
	 * ID 유형.
	 * </pre>
	 * 
	 * @param idType
	 *            idType
	 */
	public void setIdType(String idType) {
		this.idType = idType;
	}

	/**
	 * 
	 * <pre>
	 * device Key.
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
	 * device Key.
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
	 * 사용자 key.
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
	 * 사용자 key.
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
	 * 
	 * <pre>
	 * 이미지 코드.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getImgCd() {
		return this.imgCd;
	}

	/**
	 * 
	 * <pre>
	 * 이미지 코드.
	 * </pre>
	 * 
	 * @param imgCd
	 *            imgCd
	 */
	public void setImgCd(String imgCd) {
		this.imgCd = imgCd;
	}

}
