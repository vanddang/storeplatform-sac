package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST] 단말 AOM 지원여부 확인
 * 
 * Updated on : 2014. 1. 27. Updated by : 강신완, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SupportAomReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/* 기기 ID(MDN, UUID, MacAddress), Value : 01011112222 */
	private String deviceId;
	/* 사용자 Key, Value : IW102158844420091030165015 */
	private String userKey;

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
