/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.uaps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.external.client.uaps.vo.OpmdChildRes;
import com.skplanet.storeplatform.external.client.uaps.vo.OpmdRes;
import com.skplanet.storeplatform.sac.other.uaps.service.UAPSService;

/**
 * 
 * Calss 설명 (E/C로 Bypass예정)
 * 
 * Updated on : 2014. 1. 2. Updated by : 김현일, 인크로스
 */
@Controller
@RequestMapping(value = "/other/uaps")
public class UAPSController {

	@Autowired
	private UAPSService uapsService;

	@RequestMapping(value = "/getOpmd", method = RequestMethod.GET)
	@ResponseBody
	public OpmdRes getOpmd(String opmdMdn) {
		return this.uapsService.getOpmd(opmdMdn);
	}

	@RequestMapping(value = "/getOpmdChild", method = RequestMethod.GET)
	public OpmdChildRes getOpmdChild(String deviceId, int type) {
		return this.uapsService.getOpmdChild(deviceId, type);
	}

}
