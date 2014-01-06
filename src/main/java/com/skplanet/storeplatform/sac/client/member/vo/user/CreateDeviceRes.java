package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE] 휴대기기 등록
 * 
 * Updated on : 2014. 1. 6. Updated by : 반범진. 지티소프트.
 */
public class CreateDeviceRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 기기 Key
	 */
	private String deviceKey;

	/**
	 * 기기 ID(Mdn, uuId, macaddress)
	 */
	private String deviceId;

	/**
	 * 사용자 Key
	 */
	private String userKey;

	public String getDeviceKey() {
		return this.deviceKey;
	}

	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getUserKey() {
		return this.userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

}
