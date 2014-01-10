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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.external.client.interpark.vo.AuthKeyReq;
import com.skplanet.storeplatform.external.client.interpark.vo.AuthKeyRes;
import com.skplanet.storeplatform.external.client.interpark.vo.Purchase;
import com.skplanet.storeplatform.sac.client.other.vo.interpark.CreateOrderReq;
import com.skplanet.storeplatform.sac.client.other.vo.interpark.CreateOrderRes;
import com.skplanet.storeplatform.sac.client.other.vo.interpark.GetAuthKeyReq;
import com.skplanet.storeplatform.sac.client.other.vo.interpark.GetAuthKeyRes;
import com.skplanet.storeplatform.sac.other.interpark.service.InterparkService;

@Controller
@RequestMapping(value = "/other/interpark")
public class InterparkController {

	@Autowired
	private InterparkService service;

	@RequestMapping(value = "/createOrder/v1", method = RequestMethod.GET)
	@ResponseBody
	public CreateOrderRes createOrder(@RequestBody CreateOrderReq req) {
		Purchase purchase = this.convert(req);
		boolean isSuccess = this.service.createOrder(purchase);
		if (isSuccess) {
			return new CreateOrderRes("success");
		} else {
			return new CreateOrderRes("failure");
		}
	}

	private Purchase convert(CreateOrderReq i) {
		Purchase o = new Purchase();
		o.setRevOrdNo(i.getRevOrdNo());
		o.setOrdDt(i.getOrdDts());
		o.setPrdNo(i.getPrdNo());
		o.setItemNo(i.getItemNo());
		o.setPrice(i.getPrice());
		o.setQty(i.getQty());
		o.setFlag(i.getFlag());
		return o;
	}

	@RequestMapping(value = "/getAuthKey/v1", method = RequestMethod.GET)
	@ResponseBody
	public GetAuthKeyRes getAuthKey(@ModelAttribute GetAuthKeyReq req) {
		AuthKeyReq authKeyReq = this.convert(req);
		AuthKeyRes authKeyRes = this.service.getAuthKey(authKeyReq);
		GetAuthKeyRes res = this.convert(authKeyRes);
		return res;
	}

	private AuthKeyReq convert(GetAuthKeyReq i) {
		AuthKeyReq o = new AuthKeyReq();
		o.setType(i.getType());
		o.setEbFileNo(i.getEbFileNo());
		o.setRevOrdNo(i.getRevOrdNo());
		return o;
	}

	private GetAuthKeyRes convert(AuthKeyRes i) {
		GetAuthKeyRes o = new GetAuthKeyRes();
		o.setCertKey(i.getCertKey());
		return o;
	}

}
