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

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.skplanet.storeplatform.framework.integration.bean.RuntimeContextHolder;
import com.skplanet.storeplatform.framework.integration.enricher.ServiceUrlSearcher;

@Component
public class SacServiceUrlSearcher implements ServiceUrlSearcher {

	@Value("#{propertiesForSac['skp.common.controller.host']}")
	private String localHost;
	@Value("#{propertiesForSac['skp.common.controller.servletPath']}")
	private String servletPath;
	@Value("#{propertiesForSac['component.external.baseUrl']}")
	private String externalBaseUrl;
	@Value("#{propertiesForSac['skp.common.server.rootPath']}")
	private String rootPath;

	@Override
	public String search(Map<String, Object> headerMap) {
		String interfaceId = RuntimeContextHolder.getRuntimeContext().getInterfaceId();
		String requestUrl = (String) headerMap.get("http_requestUrl");
		String prefix = interfaceId.substring(1, 3);
		UriComponents from = UriComponentsBuilder.fromHttpUrl(requestUrl).build();
		UriComponentsBuilder to;
		if (prefix.equals("05")) {
			to = UriComponentsBuilder.fromHttpUrl(this.externalBaseUrl).query(from.getQuery());
		} else {
			to = UriComponentsBuilder.fromHttpUrl(this.localHost).port(from.getPort());
			List<String> rootPathList = UriComponentsBuilder.newInstance().path(this.rootPath).build()
					.getPathSegments();
			List<String> pathSegments = from.getPathSegments();

			if (!CollectionUtils.isEmpty(rootPathList) && !CollectionUtils.isEmpty(pathSegments)) {
				if (rootPathList.size() <= pathSegments.size()) {
					for (int i = 0; i < rootPathList.size(); i++) {
						if (!StringUtils.equals(rootPathList.get(i), pathSegments.get(i))) {
							throw new RuntimeException("root path not match");
						}
					}
					for (String path : rootPathList) {
						to.path("/" + path);
					}
					to.path("/" + this.servletPath);
					if (rootPathList.size() < pathSegments.size()) {
						for (int i = rootPathList.size(); i < pathSegments.size(); i++) {
							to.path("/" + pathSegments.get(i));
						}
					}
				} else {
					throw new RuntimeException("root path not match");
				}
			} else {
				to.path("/" + this.servletPath + from.getPath());
			}
		}
		return to.build().toUriString();
	}
}
