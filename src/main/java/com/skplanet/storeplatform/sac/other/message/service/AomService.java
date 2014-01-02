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

import com.skplanet.storeplatform.external.client.message.vo.AomReq;

public interface AomService {
	public Map<String, String> send(AomReq aomReq);
}
