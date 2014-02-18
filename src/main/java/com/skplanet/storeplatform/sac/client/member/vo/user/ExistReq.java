package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST] 회원가입 여부 조회 (ID/MDN 기반)
 * 
 * Updated on : 2014. 1. 6. Updated by : 강신완, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ExistReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/* 사용자ID, Value : hkd */
	private String userId;
	/* 기기 ID(MDN, UUID, MacAddress), Value : 01011112222 */
	private String deviceId;
	/* 사용자 Key, Value : IW102158844420091030165015 */
	private String userKey;
	/* 기기 Key, Value : 01011112222 */
	private String deviceKey;

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getDeviceKey() {
		return this.deviceKey;
	}

	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
