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

import org.springframework.validation.annotation.Validated;

import com.skplanet.storeplatform.sac.client.display.vo.device.UseableDeviceSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.device.UseableDeviceSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 이용 가능 단말 조회.
 * 
 * Updated on : 2014-03-07 Updated by : 이석희, 아이에스 플러스
 */
public interface UseableDeviceService {

	/**
	 * <pre>
	 * 이용 가능 단말 조회.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return UseableDeviceSacRes
	 */
	public UseableDeviceSacRes searchUseableDeviceList(@Validated UseableDeviceSacReq req, SacRequestHeader header);
}
