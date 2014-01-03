package com.skplanet.storeplatform.sac.display.device.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.device.DeviceProfileReq;
import com.skplanet.storeplatform.sac.client.display.vo.device.DeviceProfileRes;
import com.skplanet.storeplatform.sac.display.device.service.DeviceProfileService;

@Controller
@RequestMapping("/display/device")
public class DeviceController {
	private static final Logger logger = LoggerFactory.getLogger(DeviceController.class);

	@Autowired
	private DeviceProfileService deviceProfileService;

	@RequestMapping(value = "/specific/detail/v1", method = RequestMethod.GET)
	@ResponseBody
	public DeviceProfileRes specificDetail(DeviceProfileReq request) {
		// logger.info("==================================::{}", name);
		return this.deviceProfileService.searchDeviceProfile(request);
	}

	@RequestMapping(value = "/service/policy/product/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public String servicePolicyProduct(@RequestParam("name") String name) {
		logger.info("==================================::{}", name);
		return name;
	}

	@RequestMapping(value = "/gift/product/get/v1", method = RequestMethod.GET)
	@ResponseBody
	public String giftProduct(@RequestParam("name") String name) {
		logger.info("==================================::{}", name);
		return name;
	}

	@RequestMapping(value = "/recommand/product/get/v1", method = RequestMethod.GET)
	@ResponseBody
	public String recommandProduct(@RequestParam("name") String name) {
		logger.info("==================================::{}", name);
		return name;
	}

	// @RequestMapping(value = "/echoPostWithResponseBody.do", method = RequestMethod.POST)
	// @ResponseBody
	// public UserCareerSearch echoPostWithResponseBody(@RequestBody UserCareerSearch userCareerSearch) {
	// logger.info(userCareerSearch.toString());
	//
	// return userCareerSearch;
	// }
	//
	// @RequestMapping(value = "/echoPostWithResponseBodyAndQueryString.do", method = RequestMethod.POST)
	// @ResponseBody
	// public UserCareerSearch echoPostWithResponseBodyAndQueryString(@RequestBody UserCareerSearch userCareerSearch,
	// @RequestParam("name") String name) {
	//
	// logger.info(userCareerSearch.toString());
	//
	// UserCareerSearch result = new UserCareerSearch();
	//
	// result.setId(userCareerSearch.getId() + "-" + name);
	//
	// result.setName(userCareerSearch.getName() + "-" + name);
	//
	// result.setDescription(userCareerSearch.getDescription() + "-" + name);
	//
	// return result;
	// }
}
