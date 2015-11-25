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

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Updated on : 2015-11-18. Updated by : 양해엽, SK Planet.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AppPermissionRes {

	private String usePermission;

	public AppPermissionRes() { }

	public AppPermissionRes(String usePermission) {
		this.usePermission = usePermission;
	}

	public String getUsePermission() {
		return usePermission;
	}

	public void setUsePermission(String usePermission) {
		this.usePermission = usePermission;
	}
}
