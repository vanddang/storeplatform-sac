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

import org.apache.commons.lang3.StringUtils;
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
	
	@Autowired
	private SacServiceDataService dataSvc;
	
	/*
	 * <pre>
	 * Service 활성화 여부 체크 순서
	 * 1) 서비스 코드가 안 들어왔으면 false
	 * 2) 서비스 DB 데이터 없으면 false
	 * 3) 서비스가 ENABLED 상태가 아니면 false
	 * 4) 통신사가 부합하지 않으면 false (isSimoperatorMatched)
	 * 5) 모델이 부합하지 않으면 false (isModelMatched)
	 * </pre>
	 */
	@Override
	public boolean isServiceEnabled(SacService svc) {
		String serviceCd = svc.getServiceCd();
		if (StringUtils.isBlank(serviceCd)) {
			return false;
		}
			
		SacService svcFromDb = dataSvc.selectService(serviceCd);
		if (svcFromDb == null)
			return false;
		
		SacServiceStatus status = svcFromDb.getStatus();
		if (status != SacServiceStatus.ENABLED) {
			return false;
		}
		
		String simOperator = svc.getSimOperator();
		SacServiceAuthType simOperatorAuthType = svcFromDb.getSimOperatorAuthType();
		if (!isSimoperatorMatched(serviceCd, simOperator, simOperatorAuthType)) {
			return false;
		}
		
		String model = svc.getModel();
		SacServiceAuthType modelAuthType = svcFromDb.getModelAuthType();
		if (!isModelMatched(serviceCd, model, modelAuthType)) {
			return false;
		}
		
		return true;
	}
	
	/* Service와 통신사 간 맵핑 체크
	 * 1) 통신사 권한 타입이 null 이면 false
	 * 2) 통신사 권한 타입이 ALL 이면 true
	 * 3-1) 통신사 권한 타입이 WHITE 이면, 통신사 목록 안에 포함되어 있으면 true
	 * 3-2) 통신사 권한 타입이 BLACK 이면, 통신사 목록 안에 포함되어 있으면 false
	 */
	private boolean isSimoperatorMatched(String serviceCd, String simOperator, SacServiceAuthType authType) {	
		if (authType == null) {
			return false;
		}
		
		if (authType == SacServiceAuthType.ALL) {
			return true;
		}
		
		List<String> list = dataSvc.selectSimOperatorList(serviceCd);
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
	private boolean isModelMatched(String serviceCd, String model, SacServiceAuthType authType) {
		if (authType == null) {
			return false;
		}
		
		if (authType == SacServiceAuthType.ALL) {
			return true;
		}
		
		List<String> list = dataSvc.selectModelList(serviceCd);
		if (authType == SacServiceAuthType.WHITE) {
			return list.contains(model);
		} else if (authType == SacServiceAuthType.BLACK) {
			return !list.contains(model);
		}
		
		return false;
	}

}
