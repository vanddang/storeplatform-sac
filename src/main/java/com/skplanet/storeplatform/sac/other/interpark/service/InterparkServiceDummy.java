/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.interpark.service;

import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.interpark.vo.AuthKeyReq;
import com.skplanet.storeplatform.external.client.interpark.vo.AuthKeyRes;
import com.skplanet.storeplatform.external.client.interpark.vo.Purchase;

/**
 * 
 * Interpakr Service Dummy
 * 
 * Updated on : 2014. 1. 20. Updated by : 김현일, 인크로스.
 */
@Service
public class InterparkServiceDummy implements InterparkService {

	@Override
	public boolean createOrder(Purchase req) {
		return true;
	}

	@Override
	public AuthKeyRes getAuthKey(AuthKeyReq req) {
		AuthKeyRes res = new AuthKeyRes();
		res.setCertKey("DUMMY_0123456789");
		return res;
	}

}
