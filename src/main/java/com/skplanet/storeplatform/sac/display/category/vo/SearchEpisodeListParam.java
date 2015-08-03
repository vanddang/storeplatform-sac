/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

package com.skplanet.storeplatform.sac.display.category.vo;

/**
 * Updated on : 2015-07-31. Updated by : 양해엽, SK Planet.
 */
public class SearchEpisodeListParam {
	private String tenantId;
	private String channelId;

	public SearchEpisodeListParam(String tenantId, String channelId) {

		this.tenantId = tenantId;
		this.channelId = channelId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
}
