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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.sac.other.sacservice.vo.SacService;
import com.skplanet.storeplatform.sac.other.sacservice.vo.SacServiceAuthType;
import com.skplanet.storeplatform.sac.other.sacservice.vo.SacServiceStatus;

/**
 * SacServiceAbilityServiceImpl 클래스
 *
 * Created on 2014. 08. 26. by 서대영, SK플래닛
 */
@Service
public class SacServiceAbilityServiceImpl implements SacServiceAbilityService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SacServiceAbilityServiceImpl.class);
	
	@Autowired
	private SacServiceDataService dataSvc;
	
	@Autowired
	private SacServiceAuthService authSvc;
	
	/*
	 * <pre>
	 * Service 활성화 여부 체크 순서
	 * ----- 서비스 자체 체크 -----
	 * 1) 서비스 코드가 안 들어왔으면 false
	 * 2) 서비스 DB 데이터 없으면 false
	 * 3) 서비스 상태가 ENABLED 아니면 false
	 * ----- 서비스 연관 체크 -----
	 * 4) 테넌트가 부합하지 않으면 false
	 * 5) 스케줄이 부합하지 않으면 false
	 * 6) 통신사가 부합하지 않으면 false
	 * 7) 모델이 부합하지 않으면 false
	 * </pre>
	 */
	@Override
	public boolean isServiceEnabled(SacService svc) {
		// ----- 서비스 자체 체크 -----
		String serviceCd = svc.getServiceCd();
		if (StringUtils.isBlank(serviceCd)) {
			return false;
		}
			
		SacService svcFromDb = dataSvc.selectService(serviceCd);
		if (svcFromDb == null)
			return false;
		
		LOGGER.debug("# svcFromDb : {}", svcFromDb.toString());
		
		SacServiceStatus status = svcFromDb.getStatus();
		if (status != SacServiceStatus.ENABLED) {
			return false;
		}
		
		// ----- 서비스 연관 체크 -----
		SacServiceAuthType tenantAuthType = svcFromDb.getTenantAuthType();
		String tenantId = svc.getTenantId();
		if (!authSvc.isTenantAuthorized(tenantAuthType, serviceCd, tenantId)) {
			return false;
		}
		
		SacServiceAuthType scheduleAuthType = svcFromDb.getScheduleAuthType();
		if (!authSvc.isScheduleAuthorized(scheduleAuthType, serviceCd)) {
			return false;
		}
		
		SacServiceAuthType simOperatorAuthType = svcFromDb.getSimOperatorAuthType();
		String simOperator = svc.getSimOperator();
		if (!authSvc.isSimOperatorAuthorized(simOperatorAuthType, serviceCd, simOperator)) {
			return false;
		}

		String model = svc.getModel();
		SacServiceAuthType modelAuthType = svcFromDb.getModelAuthType();
		if (!authSvc.isModelAuthorized(modelAuthType, serviceCd, model)) {
			return false;
		}
		
		return true;
	}
	
	public List<SacService> getServiceList(List<SacService> svcList) {
		List<String> svcCdList = new ArrayList<String>();
		for (SacService svc : svcList) {
			svcCdList.add(svc.getServiceCd());
		}
		List<SacService> svcListFromDb = dataSvc.selectServiceList(svcCdList);
		
		for (SacService svc : svcListFromDb) {
			boolean enabled = isServiceEnabled(svc);
			svc.setActive(enabled);
		}
		
		return svcListFromDb;
	}

}
