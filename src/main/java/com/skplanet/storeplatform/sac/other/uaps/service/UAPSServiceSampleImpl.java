/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.uaps.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.uaps.vo.OpmdChildRes;
import com.skplanet.storeplatform.external.client.uaps.vo.OpmdRes;

@Profile(value = { "dev", "local" })
@Service
public class UAPSServiceSampleImpl implements UAPSService {
	@Override
	public OpmdRes getOpmd(String opmdMdn) {

		OpmdRes opmdRes = new OpmdRes();

		return null;
	}

	@Override
	public OpmdChildRes getOpmdChild(String deviceId, int type) {
		// TODO Auto-generated method stub
		return null;
	}
}
