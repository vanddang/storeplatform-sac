/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.system.monitor.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 모니터링 컨트롤러
 *
 * Created on : 2014. 4. 28.
 * Created by : 서대영, SK 플래닛.
 */
@Controller
@RequestMapping(value = "/system/monitor")
public class MonitorController {

	@RequestMapping(value = "/request", method = RequestMethod.GET)
	@ResponseBody
	public String request(HttpServletRequest request) {
		return "SAC is okay...";
	}

}
