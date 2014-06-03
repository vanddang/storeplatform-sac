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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.sac.other.sacservice.vo.SacService;

/**
 * SacServiceDataService 클래스
 *
 * Created on 2014. 06. 02. by 서대영, SK플래닛 : DB 셋업이 될 때 까지 임시 메모리 Map으로 구현
 */
@Service
public class SacServiceDataServiceImpl implements SacServiceDataService {

	private final Map<String, Boolean> dataSvc;

	public SacServiceDataServiceImpl() {
		this.dataSvc = new HashMap<String, Boolean>();
		this.dataSvc.put("GAME_CASH_FLAT_RATE", true);
		this.dataSvc.put("SERVICE_SAMPLE", false);
	}

	@Override
	public void getServiceActive(SacService service) {
		String serviceId = service.getServiceCd();
		if (this.dataSvc.get(serviceId) != null) {
			boolean active = this.dataSvc.get(serviceId);
			service.setActive(active);
		}
	}

	@Override
	public void setServiceActive(SacService service) {
		String serviceId = service.getServiceCd();
		boolean active = service.isActive();
		this.dataSvc.put(serviceId, active);
		service.setApplied(true);
	}

	@Override
	public void getServiceActiveList(List<SacService> service) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setServiceActiveList(List<SacService> service) {
		// TODO Auto-generated method stub
	}

}
