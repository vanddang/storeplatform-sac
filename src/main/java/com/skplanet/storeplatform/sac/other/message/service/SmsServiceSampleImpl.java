/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.message.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.message.vo.SmsReq;

@Profile(value = { "dev", "local" })
@Service
public class SmsServiceSampleImpl implements SmsService {
	@Override
	public Map<String, String> send(SmsReq smsReq) {
		Map<String, String> result = new HashMap<String, String>();
		result.put("resultSatus", "success");
		return result;
	}
}
