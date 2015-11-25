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

import com.skplanet.storeplatform.sac.other.sacservice.vo.SacServiceAuthType;

/**
 * SacServiceAuthService 인터페이스
 *
 * Created on 2015. 01. 8. by 서대영, SK플래닛
 */
public interface SacServiceAuthService {
	
	boolean isTenantAuthorized(SacServiceAuthType authType, String serviceCd, String tenant);
	
	boolean isScheduleAuthorized(SacServiceAuthType authType, String serviceCd);
	
	boolean isSimOperatorAuthorized(SacServiceAuthType authType, String serviceCd, String simOperator);
	
	boolean isModelAuthorized(SacServiceAuthType authType, String serviceCd, String model);
	
}
