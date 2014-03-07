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
 * OpenApi 상품 상세 정보 요청(By PackageName) Request Value Object.
 * 
 * Updated on : 2014. 03. 05. Updated by : 백승현, SK 플래닛.
 */
public class AppDetailByPackageNameSacReq extends CommonInfo {
	private static final long serialVersionUID = 1L;
	private String tenantId; // tenantId
	private String langCd; // 언어코드
	private String imageCd; // 원본 이미지 코드

	@NotBlank
	private String packageName; // 패키지명
	private String version; // 패키지버전

	private String prodId; // 상품ID

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

	/**
	 * @return the packageName
	 */
	public String getPackageName() {
		return this.packageName;
	}

	/**
	 * @param packageName
	 *            the packageName to set
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return this.version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

}
