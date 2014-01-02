/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.runtime.extend;

import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.framework.integration.router.ServiceActivatorChannelSearcher;

/**
 * 서비스 액티베이터 채널 조회자 클래스
 * 
 * Updated on : 2013-09-01 Updated by : 최현식, 에이엔비.
 */
@Component
public class SacServiceActivatorChannelSearcher implements ServiceActivatorChannelSearcher {

	@Override
	public String search(String interfaceId) {
		return "bypassChannel";
	}
}
