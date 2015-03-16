/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.runtime.common.service;

import com.skplanet.storeplatform.sac.runtime.acl.vo.Interface;
import com.skplanet.storeplatform.sac.runtime.common.vo.Bypass;
import com.skplanet.storeplatform.sac.runtime.common.vo.Component;

/**
* RouteDataService 인터페이스
*
* Created on 2014. 06. 13. by 서대영, SK 플래닛
*/
public interface RoutingDataService {

	/**
	 * 신 버젼
	 */
	Bypass selectBypassByInterface(String interfaceId, String tenantId);

	/**
	 * 구 버젼 : 15/03/04 패치 이 후에 제거 요망
	 */
	Bypass selectBypassByInterface(Interface interfac);

	/**
	 * 컴포넌트 정보 조회 (Remote SCI 용)
	 */
	Component selectComponent(String componentId, String tenantId);

}
