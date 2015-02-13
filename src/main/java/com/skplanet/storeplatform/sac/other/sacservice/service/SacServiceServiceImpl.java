/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.sacservice.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.sac.other.sacservice.vo.SacService;

/**
 * SacServiceService 구현체
 *
 * Updated on 2014. 08. 08. by 서대영, SK플래닛 : 최초 작성
 */
@Service
public class SacServiceServiceImpl implements SacServiceService {
	
	@Autowired
	private SacServiceAbilityService abilityService;

	@Override
	public SacService getServiceActive(SacService svc) {
		boolean active = abilityService.isServiceEnabled(svc);
		svc.setActive(active);
		return svc;
	}
	
	@Override
	public SacService setServiceActive(SacService service) {
		return null;
	}

	@Override
	public List<SacService> getServiceActiveList(List<SacService> serviceList) {
		return abilityService.getServiceList(serviceList);
	}

	@Override
	public List<SacService> setServiceActiveList(List<SacService> service) {
		return Collections.emptyList();
	}

}
