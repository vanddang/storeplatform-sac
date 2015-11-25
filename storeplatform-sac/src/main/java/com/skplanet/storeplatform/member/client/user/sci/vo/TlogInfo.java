/*
 * Copyright (c) 2014 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * SC Tlog Value Object.
 * 
 * Updated on : 2015. 3. 18.
 */
public class TlogInfo extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Tlog ID.
	 */
	private String tlogID;

	/**
	 * 사용자키.
	 */
	private String userKey;

	/**
	 * 디바이스키.
	 */
	private String deviceKey;

	/**
	 * 휴대기기 번호.
	 */
	private String deviceId;

	/**
	 * 변경전 usermbr_no.
	 */
	private String usermbrNoPre;

	/**
	 * 변경후 usermbr_no.
	 */
	private String usermbrNoPost;

	/**
	 * @return tlogID
	 */
	public String getTlogID() {
		return this.tlogID;
	}

	/**
	 * @param tlogID
	 *            String
	 */
	public void setTlogID(String tlogID) {
		this.tlogID = tlogID;
	}

	/**
	 * @return userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            String
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return deviceKey
	 */
	public String getDeviceKey() {
		return this.deviceKey;
	}

	/**
	 * @param deviceKey
	 *            String
	 */
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	/**
	 * @return deviceId
	 */
	public String getDeviceId() {
		return this.deviceId;
	}

	/**
	 * @param deviceId
	 *            String
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return usermbrNoPre
	 */
	public String getUsermbrNoPre() {
		return this.usermbrNoPre;
	}

	/**
	 * @param usermbrNoPre
	 *            String
	 */
	public void setUsermbrNoPre(String usermbrNoPre) {
		this.usermbrNoPre = usermbrNoPre;
	}

	/**
	 * @return usermbrNoPost
	 */
	public String getUsermbrNoPost() {
		return this.usermbrNoPost;
	}

	/**
	 * @param usermbrNoPost
	 *            String
	 */
	public void setUsermbrNoPost(String usermbrNoPost) {
		this.usermbrNoPost = usermbrNoPost;
	}

}
