package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST] 휴대기기 삭제 기능을 제공한다
 * 
 * Updated on : 2014. 1. 6. Updated by : 강신완, 부르칸.
 */
public class RemoveDeviceListSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String deviceId;

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
}
