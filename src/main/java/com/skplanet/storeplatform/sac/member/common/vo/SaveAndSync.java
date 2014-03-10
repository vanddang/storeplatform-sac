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
	 * 사용자 이전 Key
	 */
	private String preUserKey = "";

	/**
	 * 휴대기기 이전 Key
	 */
	private String preDeviceKey = "";

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
	 * @return the preUserKey
	 */
	public String getPreUserKey() {
		return this.preUserKey;
	}

	/**
	 * @param preUserKey
	 *            the preUserKey to set
	 */
	public void setPreUserKey(String preUserKey) {
		this.preUserKey = preUserKey;
	}

	/**
	 * @return the preDeviceKey
	 */
	public String getPreDeviceKey() {
		return this.preDeviceKey;
	}

	/**
	 * @param preDeviceKey
	 *            the preDeviceKey to set
	 */
	public void setPreDeviceKey(String preDeviceKey) {
		this.preDeviceKey = preDeviceKey;
	}

}
