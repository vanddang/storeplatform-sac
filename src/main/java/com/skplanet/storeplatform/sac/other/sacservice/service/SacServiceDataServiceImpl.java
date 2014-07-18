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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.sac.other.sacservice.vo.SacService;

/**
 * SacServiceDataService 클래스
 *
 * Created on 2014. 06. 02. by 서대영, SK플래닛 : DB 셋업이 될 때 까지 임시 메모리 Map으로 구현
 * Updated on 2014. 07. 16. by 서대영, SK플래닛 : 마일리지 지원 여부 추가
 */
@Service
public class SacServiceDataServiceImpl implements SacServiceDataService {

	private final Map<String, Boolean> dataSvc;

	@Autowired
	private SacServiceSimService simSvc;

	public void setSimSvc(SacServiceSimService simSvc) {
		this.simSvc = simSvc;
	}

	public SacServiceDataServiceImpl() {
		this.dataSvc = new HashMap<String, Boolean>();
        this.dataSvc.put("tstore.gamecash.flatrate", true); // 게임 캐쉬 정액제 지원
        this.dataSvc.put("tstore.mileage", true); // 마일리지 지원
		this.dataSvc.put("SERVICE_SAMPLE", false);
	}

	@Override
	public SacService getServiceActive(SacService service) {
		String serviceId = service.getServiceCd();
		if (StringUtils.equals(serviceId, "tstore.gamecash.flatrate")) {
			return this.getServiceActiveForGamecash(service);
		} else {
			return this.getServiceActiveForGeneral(service);
		}
	}

	private SacService getServiceActiveForGamecash(SacService service) {
		if (this.simSvc.belongsToSkt(service.getSimOperator())) {
			String serviceId = service.getServiceCd();
			if (this.dataSvc.get(service.getServiceCd()) != null) {
				boolean active = this.dataSvc.get(serviceId);
				service.setActive(active);
			}
		} else {
			// SKT가 아니면 무조건 Service Off
			service.setActive(false);
		}
		return service;
	}

	private SacService getServiceActiveForGeneral(SacService service) {
		String serviceId = service.getServiceCd();
		if (this.dataSvc.get(service.getServiceCd()) != null) {
			boolean active = this.dataSvc.get(serviceId);
			service.setActive(active);
		}
		return service;
	}

	@Override
	public SacService setServiceActive(SacService service) {
		String serviceId = service.getServiceCd();
		boolean active = service.isActive();
		this.dataSvc.put(serviceId, active);
		service.setApplied(true);
		return service;
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
