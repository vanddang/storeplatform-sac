/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.runtime.acl.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
*
* T_CN_TENANT 디비 맵핑용 Value Object
*
* Updated on : 2014. 2. 10.
* Updated by : 서대영, SK 플래닛.
*/
public class Tenant extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String tenantId;
	private String tenantNm;

	public Tenant() {
	}

	public Tenant(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getTenantId() {
		return this.tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getTenantNm() {
		return this.tenantNm;
	}
	public void setTenantNm(String tenantNm) {
		this.tenantNm = tenantNm;
	}

}
