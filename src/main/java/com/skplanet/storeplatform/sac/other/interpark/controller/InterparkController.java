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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.other.vo.interpark.CreateOrderReq;
import com.skplanet.storeplatform.sac.client.other.vo.interpark.CreateOrderRes;
import com.skplanet.storeplatform.sac.client.other.vo.interpark.GetAuthKeyReq;
import com.skplanet.storeplatform.sac.client.other.vo.interpark.GetAuthKeyRes;

/**
 * 
 * Interpark Controller
 * 
 * Updated on : 2014. 1. 20. Updated by : 김현일, 인크로스.
 */
@Controller
@RequestMapping(value = "/other/interpark")
public class InterparkController {

	/**
	 * 
	 * <pre>
	 * 주문정보 전송.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @return CreateOrderRes
	 */
	@RequestMapping(value = "/createOrder/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateOrderRes createOrder(@RequestBody CreateOrderReq req) {
		CreateOrderRes createOrderRes = new CreateOrderRes();
		createOrderRes.setResultStatus("success");
		return createOrderRes;
	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @return GetAuthKeyRes
	 */
	@RequestMapping(value = "/getAuthKey/v1", method = RequestMethod.GET)
	@ResponseBody
	public GetAuthKeyRes getAuthKey(GetAuthKeyReq req) {
		GetAuthKeyRes getAuthKeyRes = new GetAuthKeyRes();
		getAuthKeyRes.setCertKey("DUMMY_0123456789");
		return getAuthKeyRes;
	}

}
