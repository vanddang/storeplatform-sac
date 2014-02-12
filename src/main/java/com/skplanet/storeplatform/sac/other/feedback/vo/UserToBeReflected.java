/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.feedback.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 변경될 회언 정보를 저장할 Value Object
 *
 * Updated on : 2014. 2. 12.
 * Updated by : 서대영, SK 플래닛.
 */
public class UserToBeReflected extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String tenantId;
	private String oldUserId;
	private String newUserId;
	private String oldUserKey;
	private String newUserKey;

	public String getTenantId() {
		return this.tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getOldUserId() {
		return this.oldUserId;
	}
	public void setOldUserId(String oldUserId) {
		this.oldUserId = oldUserId;
	}
	public String getNewUserId() {
		return this.newUserId;
	}
	public void setNewUserId(String newUserId) {
		this.newUserId = newUserId;
	}
	public String getOldUserKey() {
		return this.oldUserKey;
	}
	public void setOldUserKey(String oldUserKey) {
		this.oldUserKey = oldUserKey;
	}
	public String getNewUserKey() {
		return this.newUserKey;
	}
	public void setNewUserKey(String newUserKey) {
		this.newUserKey = newUserKey;
	}

}

