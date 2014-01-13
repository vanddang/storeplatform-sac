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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.external.client.uaps.sci.UAPSSCI;
import com.skplanet.storeplatform.external.client.uaps.vo.LimitSvcRes;
import com.skplanet.storeplatform.external.client.uaps.vo.OpmdChildRes;
import com.skplanet.storeplatform.external.client.uaps.vo.OpmdRes;
import com.skplanet.storeplatform.external.client.uaps.vo.UafmapRes;
import com.skplanet.storeplatform.external.client.uaps.vo.UserRes;

/**
 * 
 * UAPS 외부연동 Repository 구현체
 * 
 * Updated on : 2014. 1. 13. Updated by : 김현일, 인크로스.
 */
@Profile(value = { "stag", "real" })
@Component
public class UAPSRepositoryImpl implements UAPSRepository {

	@Autowired
	private UAPSSCI uapsSCI;

	@Override
	public OpmdRes getOpmd(String opmdMdn) {
		return this.uapsSCI.getOpmdInfo(opmdMdn);
	}

	@Override
	public OpmdChildRes getOpmdChild(String deviceId, String type) {
		return this.uapsSCI.getOpmdChildInfo(deviceId, type);
	}

	@Override
	public UserRes getMapping(String deviceId, String type) {
		return this.uapsSCI.getMappingInfo(deviceId, type);
	}

	@Override
	public UserRes getAuthorize(String custId, String deviceId, String type) {
		return this.uapsSCI.getAuthorizeInfo(custId, deviceId, type);
	}

	@Override
	public UafmapRes getDevice(String deviceId, String type) {
		return this.uapsSCI.getDeviceInfo(deviceId, type);
	}

	@Override
	public LimitSvcRes getLimitSvc(String deviceId, String type) {
		return this.uapsSCI.getLimitSvcInfo(deviceId, type);
	}

}
