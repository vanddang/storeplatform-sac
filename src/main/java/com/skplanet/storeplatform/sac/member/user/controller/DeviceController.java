package com.skplanet.storeplatform.sac.member.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceRes;
import com.skplanet.storeplatform.sac.member.user.service.DeviceService;

/**
 * 휴대기기관련 Controller
 * 
 * Updated on : 2014. 1. 6. Updated by : 반범진, 지티소프트.
 */
@RequestMapping(value = "/member/user")
@Controller
public class DeviceController {

	private static final Logger logger = LoggerFactory
			.getLogger(UserJoinController.class);

	@Autowired
	private DeviceService deviceService;

	@RequestMapping(value = "/listDevice/v1", method = RequestMethod.GET)
	@ResponseBody
	public ListDeviceRes listDevice(ListDeviceReq req) {

		String queryString = "";
		ListDeviceRes res = this.deviceService.listDevice(queryString);

		return res;
	}

	@RequestMapping(value = "/createDevice/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateDeviceRes createDevice(CreateDeviceReq req) {

		CreateDeviceRes res = this.deviceService.createDevice(req);

		return res;
	}
}
