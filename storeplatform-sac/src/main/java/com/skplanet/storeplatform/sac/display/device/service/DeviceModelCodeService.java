package com.skplanet.storeplatform.sac.display.device.service;

import com.skplanet.storeplatform.sac.client.display.vo.device.*;

/**
 * DeviceCode Service 인터페이스(CoreStoreBusiness)
 */

public interface DeviceModelCodeService {

	/**
	 * <pre>
	 * 단말 모델코드 조회 (by UaCd).
	 * </pre>
	 *
	 * @param req
	 * @return DeviceUaCdSacRes
	 */
	DeviceUaCdSacRes searchDeviceModelCode(DeviceUaCdSacReq req);

}
