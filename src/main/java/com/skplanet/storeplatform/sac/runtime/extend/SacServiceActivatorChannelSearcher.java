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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.framework.integration.router.ServiceActivatorChannelSearcher;
import com.skplanet.storeplatform.sac.runtime.cache.service.InterfaceService;
import com.skplanet.storeplatform.sac.runtime.cache.vo.InterfaceVO;

/**
 * 서비스 액티베이터 채널 조회자 클래스
 * 
 * Updated on : 2013-09-01 Updated by : 최현식, 에이엔비.
 */
@Component
public class SacServiceActivatorChannelSearcher implements ServiceActivatorChannelSearcher {

	@Autowired
	private InterfaceService interfaceService;

	@Override
	public String search(String interfaceId) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("interfaceId", interfaceId);
		InterfaceVO interfaceVO = this.interfaceService.searchDetail(params);

		String channelName = this.interfaceService.searchChannelName(interfaceVO.getBypassYn(), params);

		if (StringUtils.isEmpty(channelName))
			throw new RuntimeException("channelName not found");

		return channelName;

	}

}
