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

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.display.vo.device.*;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.device.service.DeviceChangeService;
import com.skplanet.storeplatform.sac.display.device.service.DeviceProductProvisioningService;
import com.skplanet.storeplatform.sac.display.device.service.DeviceProfileService;
import com.skplanet.storeplatform.sac.display.device.service.UseableDeviceService;
import com.skplanet.storeplatform.sac.display.device.service.DeviceModelListService;
import com.skplanet.storeplatform.sac.display.device.service.DeviceModelCodeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 특정 단말 조회 관련 Controller
 * 
 * Updated on : 2014. 1. 27. Updated by : 오승민, 인크로스
 */
@Controller
@RequestMapping("/display/device")
public class DeviceController {
	@Autowired
	private DeviceProfileService deviceProfileService;

	@Autowired
	private DeviceProductProvisioningService deviceProductProvisioningService;

	@Autowired
	private UseableDeviceService useableDeviceService;

	@Autowired
	private DeviceChangeService deviceChangeService;
	
	@Autowired
	private DeviceModelListService deviceModelListService;

	@Autowired
	private DeviceModelCodeService deviceModelCodeService;

	@InitBinder("useableDeviceSacReq")
	public void initUseableDeviceSacReqBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new UseableDeviceSacReqValidator());
	}

	/**
	 * 특정 단말 정보 조회.
	 */
	@RequestMapping(value = "/specific/detail/v1", method = RequestMethod.GET)
	@ResponseBody
	public DeviceProfileRes searchSpecificDetail(@Validated DeviceProfileReq req, SacRequestHeader header) {
		return this.deviceProfileService.getDeviceProfile(req.getDeviceModelNo(), header.getTenantHeader().getLangCd());
	}

	/**
	 * 상품 ID에 대한 단말 Provisioning.
	 */
	@RequestMapping(value = "/product/provisioning/get/v1", method = RequestMethod.GET)
	@ResponseBody
	public DeviceProductProvisioningRes searchProductProvisioning(@Validated DeviceProductProvisioningReq req,
			SacRequestHeader header) {
		List<String> prodIdList = Arrays.asList(StringUtils.split(req.getList(), "+"));
		if (prodIdList.size() > DisplayConstants.DP_DEVICE_PROVISIONG_PARAMETER_LIMIT) {
			throw new StorePlatformException("SAC_DSP_0004", "list",
					DisplayConstants.DP_DEVICE_PROVISIONG_PARAMETER_LIMIT);
		}
		return this.deviceProductProvisioningService.searchProductProvisioning(req, header, prodIdList);
	}

	/**
	 * 이용 가능 단말 조회.
	 */
	@RequestMapping(value = "/useableDevice/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public UseableDeviceSacRes searchUseableDeviceList(@Validated UseableDeviceSacReq useableDeviceSacReq,
			SacRequestHeader header) {
		return this.useableDeviceService.searchUseableDeviceList(useableDeviceSacReq, header);
	}

	/**
	 * 단말 모델 정보 조회 (운영자 관리)
	 */
	@RequestMapping(value = "/changeModel/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public DeviceChangeSacRes searchDeviceChangeModelList() {
		return this.deviceChangeService.searchDeviceChangeModelList();
	}

	/**
	 * 단말 모델 정보 조회 (by UserAgent).
	 */
	@RequestMapping(value = "/userAgent/list/v1", method = RequestMethod.POST)
	@ResponseBody
	public DeviceUserAgentSacRes searchDeviceChangeModelList(@RequestBody @Validated DeviceUserAgentSacReq deviceReq) {
		return this.deviceChangeService.searchDeviceUserAgentList(deviceReq);
	}
	
	/**
	 * 단말 리스트 정보 조회.
	 */
	@RequestMapping(value = "/deviceModel/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public DeviceModelListSacRes searchDeviceList(@Validated DeviceModelListSacReq deviceModelListSacReq,
			SacRequestHeader header) {
		return this.deviceModelListService.searchDeviceList(deviceModelListSacReq, header);
	}

	/**
	 * 단말 모델코드 조회 (by UaCd).
	 */
	@RequestMapping(value = "/uaCd/modelCode/v1", method = RequestMethod.POST)
	@ResponseBody
	public DeviceUaCdSacRes searchDeviceModelCode(@RequestBody @Validated DeviceUaCdSacReq req) {
		return this.deviceModelCodeService.searchDeviceModelCode(req);
	}

}
