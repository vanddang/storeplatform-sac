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

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.skplanet.storeplatform.framework.integration.bean.RuntimeContextHolder;
import com.skplanet.storeplatform.framework.integration.enricher.ServiceUrlSearcher;

@Component
public class SacServiceUrlSearcher implements ServiceUrlSearcher {

	@Value("#{propertiesForSac['skp.common.controller.host']}")
	private String localHost;
	@Value("#{propertiesForSac['skp.common.controller.rootPath']}")
	private String rootPath;
	@Value("#{propertiesForSac['component.external.baseUrl']}")
	private String externalBaseUrl;

	@Override
	public String search(Map<String, Object> headerMap) {
		String interfaceId = RuntimeContextHolder.getRuntimeContext().getInterfaceId();
		String requestUrl = (String) headerMap.get("http_requestUrl");
		String prefix = interfaceId.substring(1, 3);
		UriComponents from = UriComponentsBuilder.fromHttpUrl(requestUrl).build();
		String httpUrl = "";
		if (prefix.equals("05")) {
			httpUrl = this.externalBaseUrl;
		} else {
			httpUrl = new StringBuilder().append(this.localHost).append(this.rootPath).append(from.getPath())
					.toString();
		}
		UriComponents to = UriComponentsBuilder.fromHttpUrl(httpUrl).query(from.getQuery()).build();
		return to.toUriString();
	}
}
