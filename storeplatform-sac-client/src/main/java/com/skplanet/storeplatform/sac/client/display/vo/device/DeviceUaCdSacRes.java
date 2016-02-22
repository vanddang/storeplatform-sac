package com.skplanet.storeplatform.sac.client.display.vo.device;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 단말 모델코드 조회 (by UaCd) Response Value Object.
 */
public class DeviceUaCdSacRes extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String deviceModelNo;

	/**
	 * @return the deviceModelNo
	 */
	public String getDeviceModelNo() {
		return this.deviceModelNo;
	}

	/**
	 * @param deviceModelNo
	 *            the deviceModelNo to set
	 */
	public void setDeviceModelNo(String deviceModelNo) {
		this.deviceModelNo = deviceModelNo;
	}
}
