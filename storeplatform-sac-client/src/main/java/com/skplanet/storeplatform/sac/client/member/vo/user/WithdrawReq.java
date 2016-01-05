package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST] 회원 탈퇴 기능을 제공한다.
 *
 * Updated on : 2016. 1. 5. Updated by : 최진호, 보고지티.
 */
public class WithdrawReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/* 기기 ID(MSISDN, uuId) */
	private String deviceId;
	/* 사용자 아이디 */
	private String userId;
	/* IDP 인증 Key */
	private String userAuthToken;

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserAuthToken() {
		return this.userAuthToken;
	}

	public void setUserAuthToken(String userAuthToken) {
		this.userAuthToken = userAuthToken;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
