package com.skplanet.storeplatform.sac.client.display.vo.device;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Device;

/**
 * 상품 ID에 대한 단말 Provisioning Response Value Object.
 * 
 * Updated on : 2014. 2. 3. Updated by : 오승민, 인크로스.
 */
public class UseableDeviceSacRes extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * Common Response.
	 */
	private CommonResponse commonResponse;
	/**
	 * Product List.
	 */
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
