/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.interpark.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.other.vo.interpark.GetAuthKeyReq;
import com.skplanet.storeplatform.sac.client.other.vo.interpark.GetAuthKeyRes;
import com.skplanet.storeplatform.sac.other.interpark.service.InterparkService;

@Controller
@RequestMapping(value = "/other/interpark")
public class InterparkController {

	@Autowired
	private InterparkService service;

	@RequestMapping(value = "/getAuthKey", method = RequestMethod.GET)
	@ResponseBody
	public GetAuthKeyRes getAuthKey(GetAuthKeyReq Req) {
		return null;
	}
}
