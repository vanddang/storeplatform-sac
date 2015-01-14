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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.sac.other.sacservice.util.SacServiceUtils;
import com.skplanet.storeplatform.sac.other.sacservice.vo.SacServiceAuthType;
import com.skplanet.storeplatform.sac.other.sacservice.vo.SacServiceSchedule;

/**
 * SacServiceAuthService 구현체
 *
 * Created on 2015. 01. 8. by 서대영, SK플래닛
 */
@Service
public class SacServiceAuthServiceImpl implements SacServiceAuthService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SacServiceAuthServiceImpl.class);
	
	@Autowired
	private SacServiceDataService dataSvc;
	
	/*
	 * Service와 Tenant 간 맵핑 체크
	 * 1) 테넌트 권한 타입이 null 이면 false
	 * 2) 테넌트 권한 타입이 ALL 이면 true
	 * 3-1) 테넌트 권한 타입이 WHITE 이면, 테넌트 목록 안에 포함되어 있으면 true
	 * 3-2) 테넌트 권한 타입이 BLACK 이면, 테넌트 목록 안에 포함되어 있으면 false
	 */
	@Override
	public boolean isTenantAuthorized(SacServiceAuthType authType, String serviceCd, String tenantId) {
		if (authType == null) {
			return false;
		}
		
		if (authType == SacServiceAuthType.ALL) {
			return true;
		}
		
		List<String> list = dataSvc.selectTenantList(serviceCd);
		LOGGER.debug("# Tenant List : {}", list);
		if (authType == SacServiceAuthType.WHITE) {
			return list.contains(tenantId);
		} else if (authType == SacServiceAuthType.BLACK) {
			return !list.contains(tenantId);
		}
		
		return false;
	}

	/*
	 * Service와 Schedule 간 맵핑 체크
	 * 1) 스케줄 권한 타입이 null 이면 false
	 * 2) 스케줄 권한 타입이 ALL 이면 true
	 * 3-1) 스케줄 권한 타입이 WHITE 이면, 스케줄 목록 안에 포함되어 있으면 true
	 * 3-2) 스케줄 권한 타입이 BLACK 이면, 스케줄 목록 안에 포함되어 있으면 false
	 */
	@Override
	public boolean isScheduleAuthorized(SacServiceAuthType authType, String serviceCd) {
		if (authType == null) {
			return false;
		}
		
		if (authType == SacServiceAuthType.ALL) {
			return true;
		}
		
		List<SacServiceSchedule> list = dataSvc.selectScheduleList(serviceCd);
		LOGGER.debug("# Schedule List : {}", list);
		if (authType == SacServiceAuthType.WHITE) {
			return SacServiceUtils.containsSchedule(list);
		} else if (authType == SacServiceAuthType.BLACK) {
			return !SacServiceUtils.containsSchedule(list);
		}
		
		return false;
	}
	
	/* Service와 통신사 간 맵핑 체크
	 * 1) 통신사 권한 타입이 null 이면 false
	 * 2) 통신사 권한 타입이 ALL 이면 true
	 * 3-1) 통신사 권한 타입이 WHITE 이면, 통신사 목록 안에 포함되어 있으면 true
	 * 3-2) 통신사 권한 타입이 BLACK 이면, 통신사 목록 안에 포함되어 있으면 false
	 */
	@Override
	public boolean isSimOperatorAuthorized(SacServiceAuthType authType, String serviceCd, String simOperator) {	
		if (authType == null) {
			return false;
		}
		
		if (authType == SacServiceAuthType.ALL) {
			return true;
		}
		
		List<String> list = dataSvc.selectSimOperatorList(serviceCd);
		LOGGER.debug("# Sim Operator List : {}", list);
		if (authType == SacServiceAuthType.WHITE) {
			return list.contains(simOperator);
		} else if (authType == SacServiceAuthType.BLACK) {
			return !list.contains(simOperator);
		}
		
		return false;
	}
	
	/*
	 * Service와 Model 간 맵핑 체크
	 * 1) 모델 권한 타입이 null 이면 false
	 * 2) 모델 권한 타입이 ALL 이면 true
	 * 3-1) 모델 권한 타입이 WHITE 이면, 모델 목록 안에 포함되어 있으면 true
	 * 3-2) 모델 권한 타입이 BLACK 이면, 모델 목록 안에 포함되어 있으면 false
	 */
	@Override
	public boolean isModelAuthorized(SacServiceAuthType authType, String serviceCd, String model) {
		if (authType == null) {
			return false;
		}
		
		if (authType == SacServiceAuthType.ALL) {
			return true;
		}
		
		List<String> list = dataSvc.selectModelList(serviceCd);
		LOGGER.debug("# Model List : {}", list);
		if (authType == SacServiceAuthType.WHITE) {
			return list.contains(model);
		} else if (authType == SacServiceAuthType.BLACK) {
			return !list.contains(model);
		}
		
		return false;
	}
	
	

}
