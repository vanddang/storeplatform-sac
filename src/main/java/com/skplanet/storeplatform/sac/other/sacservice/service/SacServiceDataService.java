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
 * SacServiceDataService 인터페이스
 *
 * Created on 2014. 06. 02. by 서대영, SK플래닛
 */
public interface SacServiceDataService {

	/**
	 * 한 서비스의 On/Off 값을 조회한다.
	 */
	SacService getServiceActive(SacService service);

	/**
	 * 한 서비스의 On/Off 값을 수정한다.
	 */
	SacService setServiceActive(SacService service);

	/**
	 * 어려 서비스의 On/Off 값을 조회온다.
	 */
	void getServiceActiveList(List<SacService> service);

	/**
	 * 어려 서비스의 On/Off 값을 수정한다.
	 */
	void setServiceActiveList(List<SacService> service);

}
