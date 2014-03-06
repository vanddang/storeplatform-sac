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
 * PKG Name 기반 상품 정보 조회 Request Value Object.
 * 
 * Updated on : 2014. 03. 06. Updated by : 이태희.
 */
public class SalesAppSacReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	@NotBlank
	private String salesStatus;

	@NotBlank
	private String packName;

	@NotBlank
	private String uaCode;

	@NotBlank
	private String osVersion;

	private String tenantId;

	/**
	 * @return the salesStatus
	 */
	public String getSalesStatus() {
		return this.salesStatus;
	}

	/**
	 * @param salesStatus
	 *            the salesStatus to set
	 */
	public void setSalesStatus(String salesStatus) {
		this.salesStatus = salesStatus;
	}

	/**
	 * @return the packName
	 */
	public String getPackName() {
		return this.packName;
	}

	/**
	 * @param packName
	 *            the packName to set
	 */
	public void setPackName(String packName) {
		this.packName = packName;
	}

	/**
	 * @return the uaCode
	 */
	public String getUaCode() {
		return this.uaCode;
	}

	/**
	 * @param uaCode
	 *            the uaCode to set
	 */
	public void setUaCode(String uaCode) {
		this.uaCode = uaCode;
	}

	/**
	 * @return the osVersion
	 */
	public String getOsVersion() {
		return this.osVersion;
	}

	/**
	 * @param osVersion
	 *            the osVersion to set
	 */
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
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
}
