package com.skplanet.storeplatform.sac.runtime.extend;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.skplanet.storeplatform.framework.integration.serviceactivator.InterfaceIdSearcher;
import com.skplanet.storeplatform.sac.runtime.acl.constant.AclConstant;
import com.skplanet.storeplatform.sac.runtime.cache.service.InterfaceService;
import com.skplanet.storeplatform.sac.runtime.cache.vo.InterfaceInfo;

/**
 * 인터페이스ID를 조회하는 클래스
 * 
 * Updated on : 2013-09-01 Updated by : 최현식, 에이엔비.
 */
@Component
public class SacInterfaceIdSearcher implements InterfaceIdSearcher {

	@Autowired
	private InterfaceService interfaceService;

	@Override
	public String search(MessageHeaders headers) {

		String url = (String) headers.get(AclConstant.HEADER_HTTP_REQUEST_URL);
		String path = UriComponentsBuilder.fromHttpUrl(url).build().getPath();
		Map<String, String> params = new HashMap<String, String>();
		params.put("url", path);

		InterfaceInfo interfaceVO = this.interfaceService.searchDetail(params);

		if (interfaceVO == null)
			throw new RuntimeException("interfaceId not found");

		return interfaceVO.getInterfaceId();
	}
}
