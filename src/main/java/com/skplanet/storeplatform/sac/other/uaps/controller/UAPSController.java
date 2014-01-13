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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.external.client.uaps.vo.OpmdChildRes;
import com.skplanet.storeplatform.external.client.uaps.vo.OpmdRes;
import com.skplanet.storeplatform.sac.other.uaps.service.UAPSService;

/**
 * 
 * UAPS 조회 Controller.
 * 
 * Updated on : 2014. 1. 13. Updated by : 김현일, 인크로스.
 */
@Controller
@RequestMapping(value = "/other/uaps")
public class UAPSController {

	@Autowired
	private UAPSService uapsService;

	/**
	 * 
	 * <pre>
	 * 자번호를 이용하여 모번호 정보 조회.
	 * </pre>
	 * 
	 * @param opmdMdn
	 *            opmdMdn
	 * @return OpmdRes
	 */
	@RequestMapping(value = "/getOpmd/v1", method = RequestMethod.GET)
	@ResponseBody
	public OpmdRes getOpmd(@RequestParam String opmdMdn) {
		return this.uapsService.getOpmd(opmdMdn);
	}

	/**
	 * 
	 * <pre>
	 * 모번호를 이용하여 자번호 리스트를 조회.
	 * </pre>
	 * 
	 * @param deviceId
	 *            deviceId
	 * @param type
	 *            type
	 * @return OpmdChildRes
	 */
	@RequestMapping(value = "/getOpmdChild/v1", method = RequestMethod.GET)
	@ResponseBody
	public OpmdChildRes getOpmdChild(@RequestParam String deviceId, @RequestParam String type) {
		return this.uapsService.getOpmdChild(deviceId, type);
	}
}
