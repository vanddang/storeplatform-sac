/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.device.service;

import com.skplanet.storeplatform.sac.client.display.vo.device.DeviceProfileReq;
import com.skplanet.storeplatform.sac.client.display.vo.device.DeviceProfileRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 특정 단말 정보 조회(CoreStoreBusiness).
 * 
 * Updated on : 2013-12-18 Updated by : 오승민, 인크로스
 */
public interface DeviceProfileService {

	/**
	 * <pre>
	 * 특정 상품 조회 API.
	 * </pre>
	 * 
	 * @param requestVO
	 *            requestVO
	 * @param header
	 *            header
	 * @return DeviceProfileRes
	 */
	public DeviceProfileRes searchDeviceProfile(DeviceProfileReq requestVO, SacRequestHeader header);
}
