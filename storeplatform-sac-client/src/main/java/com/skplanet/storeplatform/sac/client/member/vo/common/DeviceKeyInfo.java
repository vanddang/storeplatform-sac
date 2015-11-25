package com.skplanet.storeplatform.sac.client.member.vo.common;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 디바이스 키 공통 VO
 * 
 * Updated on : 2015. 4. 9. Updated by : 반범진.
 */
public class DeviceKeyInfo extends CommonInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 기기 키.
	 */
	private String deviceKey;

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

}
