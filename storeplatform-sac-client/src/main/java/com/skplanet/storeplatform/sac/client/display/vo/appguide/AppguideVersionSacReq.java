/*
 * Copyright (c) 2014 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.appguide;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * App guide Version 조회 Request Value Object.
 * 
 * Updated on : 2014. 03. 06. Updated by : 윤주영, SK 플래닛.
 */
public class AppguideVersionSacReq extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4414638220618742393L;

	@NotNull
	@NotBlank
	private String packageName;

	@NotNull
	@NotBlank
	private String osVersion;

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

}
