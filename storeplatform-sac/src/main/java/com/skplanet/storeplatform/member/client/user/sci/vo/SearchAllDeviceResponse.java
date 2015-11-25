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
import com.skplanet.storeplatform.member.client.common.vo.CommonResponse;

/**
 * 이전 휴대기기 개별 조회 응답 Value Object
 * 
 * Updated on : 2014. 1. 6 Updated by : wisestone_brian, wisestone
 */
public class SearchAllDeviceResponse extends CommonInfo implements Serializable {
	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Returns the serial version UID.
	 * 
	 * @return serialVersionUID - the serial version UID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/** 공통 응답 Value Object. */
	private CommonResponse commonResponse;

	/** 사용자 Key. */
	private String userKey;

	/** 사용자 ID. */
	private String userID;

	/** 휴대기기 Key. */
	private String deviceKey;

	/** 사용자 휴대기기 Value Object. */
	private UserMbrDevice userMbrDevice;

	/**
	 * 공통 응답 Value Object를 리턴한다.
	 * 
	 * @return commonResponse - 공통 응답 Value Object
	 */
	public CommonResponse getCommonResponse() {
		return this.commonResponse;
	}

	/**
	 * 공통 응답 Value Object를 설정한다.
	 * 
	 * @param commonResponse
	 *            공통 응답 Value Object
	 */
	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
	}

	/**
	 * 사용자 Key를 리턴한다.
	 * 
	 * @return userKey - 사용자 Key
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * 사용자 Key를 설정한다.
	 * 
	 * @param userKey
	 *            사용자 Key
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * 사용자 ID를 리턴한다.
	 * 
	 * @return userID - 사용자 ID
	 */
	public String getUserID() {
		return this.userID;
	}

	/**
	 * 사용자 ID를 설정한다.
	 * 
	 * @param userID
	 *            사용자 ID
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}

	/**
	 * 사용자 휴대기기 Key를 리턴한다.
	 * 
	 * @return deviceKey - 사용자 휴대기기 Key
	 */
	public String getDeviceKey() {
		return this.deviceKey;
	}

	/**
	 * 사용자 휴대기기 Key를 설정한다.
	 * 
	 * @param deviceKey
	 *            사용자 휴대기기 Key
	 */
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	/**
	 * 사용자 휴대기기 Value Object를 리턴한다.
	 * 
	 * @return userMbrDevice - 사용자 휴대기기 Value Object
	 */
	public UserMbrDevice getUserMbrDevice() {
		return this.userMbrDevice;
	}

	/**
	 * 사용자 휴대기기 Value Object를 설정한다.
	 * 
	 * @param userMbrDevice
	 *            사용자 휴대기기 Value Object
	 */
	public void setUserMbrDevice(UserMbrDevice userMbrDevice) {
		this.userMbrDevice = userMbrDevice;
	}
}
