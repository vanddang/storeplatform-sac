/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

package com.skplanet.storeplatform.sac.client.display.vo.app;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Updated on : 2015-11-18. Updated by : 양해엽, SK Planet.
 */
public class AppPermissionReq extends CommonInfo {
	private static final long serialVersionUID = -1L;

	@NotNull @NotBlank
	private String prodId;

	@NotNull @NotBlank
	private String subContentsId;


	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getSubContentsId() {
		return subContentsId;
	}

	public void setSubContentsId(String subContentsId) {
		this.subContentsId = subContentsId;
	}
}
