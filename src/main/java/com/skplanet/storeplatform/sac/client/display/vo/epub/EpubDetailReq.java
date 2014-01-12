/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.epub;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 이북/코믹 채널 상세 조회 Request Value Object.
 *
 * Updated on : 2014. 01. 07. Updated by : 임근대, SK 플래닛.
 */
public class EpubDetailReq extends CommonInfo {

	private static final long serialVersionUID = 1030336138926751912L;

	private String channelld; // 체널 ID

	public String getChannelld() {
		return this.channelld;
	}

	public void setChannelld(String channelld) {
		this.channelld = channelld;
	}

	@Override
	public String toString() {
		return "EpubDetailReq [channelld=" + this.channelld + "]";
	}

}
