/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.common.header.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 테넌트 관련 정보를 컨트롤러에서 제공하기 위한 Value Object
 * 
 * Updated on : 2014. 1. 13. Updated by : 서대영, SK 플래닛.
 */
public class TenantHeader extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String tenantId;

	private String systemId;

	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getSystemId() {
		return this.systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

}
