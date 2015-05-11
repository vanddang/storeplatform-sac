/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.cache.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * <p>
 * FreepassMetaParam
 * </p>
 * Updated on : 2015. 05. 08 Updated by : 이태균, IS-PLUS.
 */
public class VoucherMetaParam extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String langCd;
	private String channelId;
	private String tenantId;

	public VoucherMetaParam() {
	}

	public VoucherMetaParam(String channelId, String langCd, String tenantId) {
		this.channelId = channelId;
		this.langCd = langCd;
		this.tenantId = tenantId;
	}

	public String getLangCd() {
		return this.langCd;
	}

	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}

	public String getChannelId() {
		return this.channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getCacheKey() {
		return this.channelId + "_" + this.langCd + "_" + this.tenantId;
	}
}
