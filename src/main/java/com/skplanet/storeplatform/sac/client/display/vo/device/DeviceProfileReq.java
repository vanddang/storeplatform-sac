package com.skplanet.storeplatform.sac.client.display.vo.device;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class DeviceProfileReq extends CommonInfo {
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
