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

import com.skplanet.storeplatform.sac.client.display.vo.device.DeviceModelListSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.device.DeviceModelListSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import org.springframework.validation.annotation.Validated;

/**
 * 단말 리스트 정보 조회.
 */
public interface DeviceModelListService {

	/**
	 * <pre>
	 * 단말 리스트 정보 조회.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return DeviceModelListSacRes
	 */
	public DeviceModelListSacRes searchDeviceList(@Validated DeviceModelListSacReq req, SacRequestHeader header);
}
