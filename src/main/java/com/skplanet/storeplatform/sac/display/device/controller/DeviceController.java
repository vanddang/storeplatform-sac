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

import com.skplanet.storeplatform.sac.client.display.vo.device.DeviceProductProvisioningReq;
import com.skplanet.storeplatform.sac.client.display.vo.device.DeviceProductProvisioningRes;
import com.skplanet.storeplatform.sac.client.display.vo.device.DeviceProfileReq;
import com.skplanet.storeplatform.sac.client.display.vo.device.DeviceProfileRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.device.service.DeviceProductProvisioningService;
import com.skplanet.storeplatform.sac.display.device.service.DeviceProfileService;

/**
 * 특정 단말 조회 관련 Controller
 * 
 * Updated on : 2014. 1. 27. Updated by : 오승민, 인크로스
 */
@Controller
@RequestMapping("/display/device")
public class DeviceController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DeviceProfileService deviceProfileService;

	@Autowired
	private DeviceProductProvisioningService deviceProductProvisioningService;

	/**
	 * <pre>
	 * 특정 단말 조회.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return DeviceProfileRes
	 */
	@RequestMapping(value = "/specific/detail/v1", method = RequestMethod.GET)
	@ResponseBody
	public DeviceProfileRes searchSpecificDetail(DeviceProfileReq req, SacRequestHeader header) {
		return this.deviceProfileService.searchDeviceProfile(req, header);
	}

	/**
	 * <pre>
	 * 상품 ID에 대한 단말 Provisioning.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return DeviceProductProvisioningRes
	 */
	@RequestMapping(value = "/product/provisioning/get/v1", method = RequestMethod.GET)
	@ResponseBody
	public DeviceProductProvisioningRes searchProductProvisioning(DeviceProductProvisioningReq req,
			SacRequestHeader header) {
		return this.deviceProductProvisioningService.searchProductProvisioning(req, header);
	}

}
