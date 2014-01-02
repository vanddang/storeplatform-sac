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

import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.message.vo.AomReq;

@Profile(value = { "stag", "real" })
@Service
public class AomServiceImpl implements AomService {
	@Override
	public Map<String, String> send(AomReq aomReq) {
		// TODO Auto-generated method stub
		return null;
	}
}
