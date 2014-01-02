package com.skplanet.storeplatform.sac.runtime.extend;

import org.apache.commons.lang3.StringUtils;
import org.springframework.integration.MessageHeaders;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.framework.integration.serviceactivator.InterfaceIdSearcher;

/**
 * 인터페이스ID를 조회하는 클래스
 * 
 * Updated on : 2013-09-01 Updated by : 최현식, 에이엔비.
 */
@Component
public class SacInterfaceIdSearcher implements InterfaceIdSearcher {

	private final String defaultInterfaceId = "I02000001";

	@Override
	public String search(MessageHeaders headers) {

		String interfaceId = (String) headers.get("x-sac-interface-id");

		if (StringUtils.isEmpty(interfaceId))
			return this.defaultInterfaceId;

		if (interfaceId.length() != 9)
			throw new RuntimeException("인터페이스 ID Length 오류.");

		return interfaceId;
	}
}
