package com.skplanet.storeplatform.sac.client.display.vo.personal;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 특정 단말 조회 Request Value Object.
 * 
 * Updated on : 2014. 2. 3. Updated by : 오승민, 인크로스.
 */
public class PersonalAutoUpgradeReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * 모델명
	 */
	private String deviceModelNo;

	public String getDeviceModelNo() {
		return this.deviceModelNo;
	}

	public void setDeviceModelNo(String deviceModelNo) {
		this.deviceModelNo = deviceModelNo;
	}
}
