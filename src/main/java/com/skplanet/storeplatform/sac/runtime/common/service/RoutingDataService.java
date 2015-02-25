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

import com.skplanet.storeplatform.sac.runtime.common.vo.Bypass;

/**
* RouteDataService 인터페이스
*
* Created on 2014. 06. 13. by 서대영, SK 플래닛
*/
public interface RoutingDataService {

	Bypass selectBypassByInterface(String interfaceId, String tenantId);

}
