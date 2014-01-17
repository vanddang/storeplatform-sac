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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.external.client.uaps.vo.OpmdChildRes;
import com.skplanet.storeplatform.external.client.uaps.vo.OpmdRes;
import com.skplanet.storeplatform.external.client.uaps.vo.UserRes;
import com.skplanet.storeplatform.sac.other.uaps.repository.UAPSRepository;

/**
 * 
 * UAPS 조회 Service 구현체
 * 
 * Updated on : 2014. 1. 13. Updated by : 김현일, 인크로스.
 */
@Service
@Transactional
public class UAPSServiceImpl implements UAPSService {

	@Autowired
	private UAPSRepository uapsRepository;

	@Override
	public OpmdRes getOpmd(String opmdMdn) {
		return this.uapsRepository.getOpmd(opmdMdn);
	}

	@Override
	public OpmdChildRes getOpmdChild(String deviceId, String type) {
		return this.uapsRepository.getOpmdChild(deviceId, type);
	}

	@Override
	public UserRes getMapping(String deviceId, String type) {
		return this.uapsRepository.getMapping(deviceId, type);
	}
}
