/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.purchase.vo.history;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 구매내역 회원정보 변경요청.
 * 
 * Updated on : 2014. 2. 26. Updated by :Updated by : 조용진, 엔텔스.
 */
public class PurchaseUserInfoSacReq extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String tenantId; // Tenant ID
	private String systemId; // system ID
	@NotNull
	@NotEmpty
	private String userKey; // 내부사용자번호
	@NotNull
	@NotEmpty
	private String newUserKey; // 내부사용자번호
	@NotNull
	@NotEmpty
	private String deviceKey; // 내부디바이스ID
	private String newDeviceKey; // 내부디바이스ID

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the systemId
	 */
	public String getSystemId() {
		return this.systemId;
	}

	/**
	 * @param systemId
	 *            the systemId to set
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
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
	 * @return the newUserKey
	 */
	public String getNewUserKey() {
		return this.newUserKey;
	}

	/**
	 * @param newUserKey
	 *            the newUserKey to set
	 */
	public void setNewUserKey(String newUserKey) {
		this.newUserKey = newUserKey;
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

	/**
	 * @return the newDeviceKey
	 */
	public String getNewDeviceKey() {
		return this.newDeviceKey;
	}

	/**
	 * @param newDeviceKey
	 *            the newDeviceKey to set
	 */
	public void setNewDeviceKey(String newDeviceKey) {
		this.newDeviceKey = newDeviceKey;
	}

}
