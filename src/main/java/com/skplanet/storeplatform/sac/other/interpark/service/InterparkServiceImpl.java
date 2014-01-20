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

import org.springframework.beans.factory.annotation.Autowired;

import com.skplanet.storeplatform.external.client.interpark.vo.AuthKeyReq;
import com.skplanet.storeplatform.external.client.interpark.vo.AuthKeyRes;
import com.skplanet.storeplatform.external.client.interpark.vo.Purchase;
import com.skplanet.storeplatform.sac.other.interpark.repository.InterparkRepository;

/**
 * 
 * Interpark Service 구현체
 * 
 * Updated on : 2014. 1. 20. Updated by : 김현일, 인크로스.
 */
// @Service
public class InterparkServiceImpl implements InterparkService {

	@Autowired
	private InterparkRepository repository;

	@Override
	public boolean createOrder(Purchase req) {
		try {
			this.repository.createOrderByEC(req);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public AuthKeyRes getAuthKey(AuthKeyReq req) {
		return this.repository.getAuthKeyFromEC(req);
	}

}
