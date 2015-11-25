package com.skplanet.storeplatform.sac.client.member.vo.user;

/**
 * [RESPONSE] 대표단말설정
 * 
 * Updated on : 2014. 1. 6. Updated by : 강신완, 부르칸.
 */
public class SetMainDeviceRes {
	private static final long serialVersionUID = 1L;

	private String deviceKey;

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
