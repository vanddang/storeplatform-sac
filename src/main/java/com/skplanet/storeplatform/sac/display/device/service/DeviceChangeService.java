package com.skplanet.storeplatform.sac.display.device.service;

import com.skplanet.storeplatform.sac.client.display.vo.device.DeviceChangeSacRes;

/**
 * DeviceChange Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 03. 11. Updated by : 이태희
 */

public interface DeviceChangeService {
	/**
	 * <pre>
	 * 단말 UserAgent 관리 정보 조회.
	 * </pre>
	 * 
	 * @param header
	 *            header
	 * @param salesAppReq
	 *            salesAppReq
	 * @return SalesAppSacRes
	 */
	DeviceChangeSacRes searchDeviceChangeModelList();
}
