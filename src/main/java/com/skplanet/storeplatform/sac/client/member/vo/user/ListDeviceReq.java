package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST] 휴대기기 목록 조회
 * 
 * Updated on : 2014. 1. 3. Updated by : 반범진. 지티소프트.
 */
public class ListDeviceReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 ID
	 */
	private String userId;

	/**
	 * 사용자 Key
	 */
	private String userKey;

	/**
	 * 기기 ID
	 */
	private String deviceId;

	/**
	 * 조회여부(모든기기/대표기기) ALL/PRIMARY
	 */
	private String searchType;

	/**
	 * 기기 Key
	 */
	private String deviceKey;

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserKey() {
		return this.userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceKey() {
		return this.deviceKey;
	}

	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	public String getSearchType() {
		return this.searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

}
