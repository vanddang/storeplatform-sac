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

/**
 * SacServiceAbilityService 인터페이스
 *
 * Created on 2014. 08. 26. by 서대영, SK플래닛
 */
public interface SacServiceAbilityService {

	boolean isServiceEnabled(SacService svc);
	
	List<SacService> getServiceList(List<SacService> svcList);
	
}
