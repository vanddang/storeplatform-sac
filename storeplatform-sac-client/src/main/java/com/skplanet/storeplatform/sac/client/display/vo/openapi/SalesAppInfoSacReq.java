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
 * Updated on : 2014. 03. 10. Updated by : 이태희.
 */
public class SalesAppInfoSacReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	@NotBlank
	private String packName;

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
}
