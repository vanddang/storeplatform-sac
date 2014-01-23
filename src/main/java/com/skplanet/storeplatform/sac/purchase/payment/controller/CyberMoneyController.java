/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.payment.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.external.client.payplanet.sci.CyberMoneySCI;

/**
 * Cyber Money Controller.
 * 
 * Updated on : 2014. 1. 8. Updated by : nTels_cswoo81, nTels.
 */
@Controller
@RequestMapping("/purchase/payment/cyberMoney")
public class CyberMoneyController {

	private static final Logger logger = LoggerFactory.getLogger(CyberMoneyController.class);

	@Autowired
	private CyberMoneySCI cyberMoneySCI;

	@RequestMapping(value = "/get/v1", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getCyberMoney(@RequestBody Map<String, Object> reqMap) throws Exception {

		logger.debug("CyberMoneyController.getCyberMoney param reqMap : {}", reqMap);

		Map<String, Object> resMap = this.cyberMoneySCI.getCyberMoney(reqMap);

		logger.debug("CyberMoneyController.getCyberMoney return resMap : {}", resMap);

		return resMap;
	}

}
