package com.skplanet.storeplatform.sac.display.device.service;

import com.skplanet.storeplatform.sac.client.display.vo.device.DeviceChangeSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.device.DeviceUserAgentSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.device.DeviceUserAgentSacRes;

/**
 * DeviceChange Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 03. 12. Updated by : 이태희
 */

public interface DeviceChangeService {
	/**
	 * <pre>
	 * 단말 모델 정보 조회 (운영자 관리).
	 * </pre>
	 * 
	 * @return DeviceChangeSacRes
	 */
	DeviceChangeSacRes searchDeviceChangeModelList();

	/**
	 * <pre>
	 * 단말 모델 정보 조회 (by UserAgent).
	 * </pre>
	 * 
	 * @param deviceReq
	 *            deviceReq
	 * @return DeviceUserAgentSacRes
	 */
	DeviceUserAgentSacRes searchDeviceUserAgentList(DeviceUserAgentSacReq deviceReq);
}
