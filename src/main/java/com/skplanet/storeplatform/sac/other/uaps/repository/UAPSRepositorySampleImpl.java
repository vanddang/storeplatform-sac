/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.uaps.repository;

import java.util.Arrays;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.external.client.uaps.vo.LimitSvcRes;
import com.skplanet.storeplatform.external.client.uaps.vo.OpmdChildRes;
import com.skplanet.storeplatform.external.client.uaps.vo.OpmdRes;
import com.skplanet.storeplatform.external.client.uaps.vo.UafmapRes;
import com.skplanet.storeplatform.external.client.uaps.vo.UserRes;

/**
 * 
 * UAPS 외부연동 Repository Sample
 * 
 * Updated on : 2014. 1. 13. Updated by : 김현일, 인크로스.
 */
@Profile(value = { "dev", "local" })
@Component
public class UAPSRepositorySampleImpl implements UAPSRepository {

	@Override
	public OpmdRes getOpmd(String opmdMdn) {
		OpmdRes opmdRes = new OpmdRes();
		opmdRes.setOpmdMdn(opmdMdn);
		opmdRes.setPauseYN("N");
		opmdRes.setMobileMdn("0101231234");
		opmdRes.setMobileSvcMngNum("1823942405");
		return opmdRes;
	}

	@Override
	public OpmdChildRes getOpmdChild(String deviceId, String type) {
		OpmdChildRes opmdChildRes = new OpmdChildRes();
		opmdChildRes.setOpmdMdnList(Arrays.asList(new String[] { "98765432101", "98765432102", "98765432103" }));
		return opmdChildRes;
	}

	@Override
	public UserRes getMapping(String deviceId, String type) {
		UserRes userRes = new UserRes();
		userRes.setResultCode(0);
		userRes.setCharge("");
		userRes.setClientID("");
		userRes.setClientIDYN("Y");
		userRes.setImsi("");
		userRes.setMdn("");
		userRes.setMin("");
		userRes.setServiceCD(new String[] {});
		userRes.setSvcMngNum("");
		userRes.setServiceID("");
		userRes.setPauseYN("Y");
		userRes.setPauseReceiveYN("Y");
		userRes.setPpsYN("Y");
		userRes.setChildSvcNum("");
		userRes.setMvnoCD("");
		userRes.setDeviceModel("");
		return userRes;
	}

	@Override
	public UserRes getAuthorize(String custId, String deviceId, String type) {
		return null;
	}

	@Override
	public UafmapRes getDevice(String deviceId, String type) {
		return null;
	}

	@Override
	public LimitSvcRes getLimitSvc(String deviceId, String type) {
		return null;
	}

}
