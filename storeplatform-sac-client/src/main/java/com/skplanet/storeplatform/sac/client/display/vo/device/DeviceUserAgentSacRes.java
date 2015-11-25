package com.skplanet.storeplatform.sac.client.display.vo.device;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Device;

/**
 * 단말 모델 정보 조회 (by UserAgent) Response Value Object.
 * 
 * Updated on : 2014. 03. 12. Updated by : 이태희.
 */
public class DeviceUserAgentSacRes extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private CommonResponse commonResponse;

	private List<Device> deviceList;

	/**
	 * @return the commonResponse
	 */
	public CommonResponse getCommonResponse() {
		return this.commonResponse;
	}

	/**
	 * @param commonResponse
	 *            the commonResponse to set
	 */
	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
	}

	/**
	 * @return the deviceList
	 */
	public List<Device> getDeviceList() {
		return this.deviceList;
	}

	/**
	 * @param deviceList
	 *            the deviceList to set
	 */
	public void setDeviceList(List<Device> deviceList) {
		this.deviceList = deviceList;
	}
}
