/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.openapi;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * OpenApi 게임센터 상품 지원 여부 조회 Request Value Object.
 * 
 * Updated on : 2014. 03. 12. Updated by : 백승현, 인크로스.
 */
public class SupportGameCenterSacReq extends CommonInfo {
	private static final long serialVersionUID = 1L;
	private String tenantId; // tenantId
	private String langCd; // 언어코드

	@NotBlank
	private String aidList; // App 상품 AID 리스트
	@NotBlank
	private String userKey; // userKey
	@NotBlank
	private String deviceKey; // deviceKey
	@NotBlank
	private String deviceModelNo; // 단말 모델명.

	private String[] arrayAId;

	private String[] arrayProductId;

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
	 * @return the aidList
	 */
	public String getAidList() {
		return this.aidList;
	}

	/**
	 * @param aidList
	 *            the aidList to set
	 */
	public void setAidList(String aidList) {
		this.aidList = aidList;
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
	 * @return the deviceModelNo
	 */
	public String getDeviceModelNo() {
		return this.deviceModelNo;
	}

	/**
	 * @param deviceModelNo
	 *            the deviceModelNo to set
	 */
	public void setDeviceModelNo(String deviceModelNo) {
		this.deviceModelNo = deviceModelNo;
	}

	/**
	 * @return the arrayProductId
	 */
	public String[] getArrayProductId() {
		return this.arrayProductId;
	}

	/**
	 * @param arrayProductId
	 *            the arrayProductId to set
	 */
	public void setArrayProductId(String[] arrayProductId) {
		this.arrayProductId = arrayProductId;
	}

	/**
	 * @return the arrayAId
	 */
	public String[] getArrayAId() {
		return this.arrayAId;
	}

	/**
	 * @param arrayAId
	 *            the arrayAId to set
	 */
	public void setArrayAId(String[] arrayAId) {
		this.arrayAId = arrayAId;
	}
}
