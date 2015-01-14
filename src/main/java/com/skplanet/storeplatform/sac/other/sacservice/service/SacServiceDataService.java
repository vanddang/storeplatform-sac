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

import com.skplanet.storeplatform.sac.other.sacservice.vo.SacService;
import com.skplanet.storeplatform.sac.other.sacservice.vo.SacServiceSchedule;

/**
 * SacServiceDataService 인터페이스
 *
 * Created on 2014. 08. 26. by 서대영, SK플래닛
 * Updated on 2015. 01. 09. by 서대영, SK플래닛 : selectTenantList(), selectScheduleList() 추가 
 */
public interface SacServiceDataService {

	SacService selectService(String serviceCd);

	List<String> selectSimOperatorList(String serviceCd);
	
	List<String> selectModelList(String serviceCd);
	
	List<String> selectTenantList(String serviceCd);

	List<SacServiceSchedule> selectScheduleList(String serviceCd);
	
	void flushCache();

	
}
