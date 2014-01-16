/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.device.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.device.DeviceProfileReq;
import com.skplanet.storeplatform.sac.client.display.vo.device.DeviceProfileRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.device.service.DeviceProfileService;

@Controller
@RequestMapping("/display/device")
public class DeviceController {
	private static final Logger logger = LoggerFactory.getLogger(DeviceController.class);

	@Autowired
	private DeviceProfileService deviceProfileService;

	@RequestMapping(value = "/specific/detail/v1", method = RequestMethod.GET)
	@ResponseBody
	public DeviceProfileRes specificDetail(DeviceProfileReq request, SacRequestHeader header) {
		// logger.info("==================================::{}", name);
		return this.deviceProfileService.searchDeviceProfile(request, header);
	}

}
