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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.skplanet.storeplatform.framework.integration.serviceactivator.BypassUrlSearcher;
import com.skplanet.storeplatform.sac.runtime.cache.service.InterfaceService;
import com.skplanet.storeplatform.sac.runtime.cache.vo.BypassInfo;

/**
 * 바이패스 URL를 조회하는 구현클래스
 * 
 * Updated on : 2013-09-01 Updated by : 최현식, 에이엔비.
 */
@Component
public class SacBypassUrlSearcher implements BypassUrlSearcher {

	@Autowired
	private InterfaceService interfaceService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.framework.integration.serviceactivator.BypassUrlSearcher#search(java.lang.String)
	 */
	@Override
	public String search(String interfaceId) {
		// TODO : 이전 Bypass 기능의 호환성을 위해서 유지. 현재는 사용되지 않는다.
		// SWA 문의 : 해당 구현체의 기능을 SacServiceUrlSearcher 구현으로 변경.
		// 해당 인터페스 구현체는 F/W 에서 @Autowired 사용되고 있음.
		Map<String, String> params = new HashMap<String, String>();
		params.put("interfaceId", interfaceId);

		BypassInfo bypassVO = this.interfaceService.searchBypassUrl(params);

		if (bypassVO == null)
			throw new RuntimeException("bypass not found");

		String protocol = bypassVO.getProtocolNm();
		String domain = bypassVO.getDomain();
		int port = Integer.parseInt(bypassVO.getPort());
		String path = bypassVO.getPath();
		UriComponents bypassUrl = UriComponentsBuilder.newInstance().scheme(protocol).host(domain).port(port)
				.path(path).build();

		return bypassUrl.toUriString();
	}
}
