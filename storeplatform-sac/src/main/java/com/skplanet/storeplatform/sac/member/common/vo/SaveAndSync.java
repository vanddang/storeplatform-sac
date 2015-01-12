/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.common.vo;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Save & Sync 관련 Value Object.
 * 
 * Updated on : 2014. 3. 7. Updated by : 심대진, 다모아 솔루션.
 */
public class SaveAndSync extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 변동성 대상 유뮤 (Y or N)
	 */
	private String isSaveAndSyncTarget = "";

	/**
	 * 변동성 사용자 Key
	 */
	private String userKey = "";

	/**
	 * 변동성 휴대기기 Key
	 */
	private String deviceKey = "";

	/**
	 * @return the isSaveAndSyncTarget
	 */
	public String getIsSaveAndSyncTarget() {
		return this.isSaveAndSyncTarget;
	}

	/**
	 * @param isSaveAndSyncTarget
	 *            the isSaveAndSyncTarget to set
	 */
	public void setIsSaveAndSyncTarget(String isSaveAndSyncTarget) {
		this.isSaveAndSyncTarget = isSaveAndSyncTarget;
	}

	/**
	 * @return the userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return the deviceKey
	 */
	public String getDeviceKey() {
		return this.deviceKey;
	}

	/**
	 * @param deviceKey
	 *            the deviceKey to set
	 */
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
