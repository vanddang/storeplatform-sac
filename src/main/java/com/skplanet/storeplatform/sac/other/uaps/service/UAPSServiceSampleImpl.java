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

import java.util.Arrays;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.uaps.vo.OpmdChildRes;
import com.skplanet.storeplatform.external.client.uaps.vo.OpmdRes;

/**
 * 
 * Calss 설명
 * 
 * Updated on : 2014. 1. 7. Updated by : 홍길동, SK 플래닛.
 */
@Profile(value = { "dev", "local" })
@Service
public class UAPSServiceSampleImpl implements UAPSService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.other.uaps.service.UAPSService#getOpmd(java.lang.String)
	 */
	@Override
	public OpmdRes getOpmd(String opmdMdn) {
		OpmdRes opmdRes = new OpmdRes();
		opmdRes.setOpmdMdn(opmdMdn);
		opmdRes.setPauseYN("N");
		opmdRes.setMobileMdn("0101231234");
		opmdRes.setMobileSvcMngNum("1823942405");
		return opmdRes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.other.uaps.service.UAPSService#getOpmdChild(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public OpmdChildRes getOpmdChild(String deviceId, String type) {
		OpmdChildRes opmdChildRes = new OpmdChildRes();
		opmdChildRes.setOpmdMdnList(Arrays.asList(new String[] { "98765432101", "98765432102", "98765432103" }));
		return opmdChildRes;
	}
}
