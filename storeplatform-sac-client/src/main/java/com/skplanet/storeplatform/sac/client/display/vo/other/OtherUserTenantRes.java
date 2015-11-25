/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.other;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * <p>
 * OtherUserTenantRes
 * </p>
 * Updated on : 2015. 04. 30 Updated by : 임근대, SK 플래닛.
 */
public class OtherUserTenantRes extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String tenantId;


	public OtherUserTenantRes(String tenantId) {
		super();
		this.tenantId = tenantId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

}
