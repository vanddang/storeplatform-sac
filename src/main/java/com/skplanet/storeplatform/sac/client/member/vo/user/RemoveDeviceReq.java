package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST] 휴대기기 삭제 기능을 제공한다
 * 
 * Updated on : 2014. 1. 6. Updated by : 강신완, 부르칸.
 */
public class RemoveDeviceReq extends CommonInfo {

	private static final long serialVersionUID = 1L;
	/* 사용자 Key */
	private String userKey;

	/* 기기 Key */
	private String deviceKey;

	/* 기기 ID */
	private String deviceId;

	/* 사용자 권한 Key */
	private String userAuthKey;

	/* 사용자 아이디 */
	private String userId;

	public String getUserKey() {
		return this.userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

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

	public String getUserAuthKey() {
		return this.userAuthKey;
	}

	public void setUserAuthKey(String userAuthKey) {
		this.userAuthKey = userAuthKey;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
