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
public class EpubChannelReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String channelId; // 체널 ID

	private String langCd;
	private String tenantId;
	private String deviceModel;
	private String imgCd;
	private String orderedBy;


	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getLangCd() {
		return this.langCd;
	}

	public String getTenantId() {
		return this.tenantId;
	}

	public String getDeviceModel() {
		return this.deviceModel;
	}

	public String getChannelId() {
		return this.channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getImgCd() {
		return this.imgCd;
	}

	public void setImgCd(String imgCd) {
		this.imgCd = imgCd;
	}

	public String getOrderedBy() {
		return this.orderedBy;
	}

	public void setOrderedBy(String orderedBy) {
		this.orderedBy = orderedBy;
	}




}
