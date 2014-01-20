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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.skplanet.storeplatform.framework.integration.enricher.ServiceUrlSearcher;

/**
 * 
 * ServiceUrlSearcher 구현체
 * 
 * Updated on : 2014. 1. 16. Updated by : 김현일, 인크로스.
 */
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

	@Resource(name = "propertiesForSac")
	private Properties properties;

	@Override
	public String search(Map<String, Object> headerMap) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String requestUrl = (String) headerMap.get("http_requestUrl");
		String requestMethod = (String) headerMap.get("http_requestMethod");
		UriComponents from = UriComponentsBuilder.fromHttpUrl(requestUrl).build();
		UriComponentsBuilder to;

		String bypassPath = this.properties.getProperty(from.getPath(), "");

		if (StringUtils.isNotEmpty(bypassPath)) {
			to = UriComponentsBuilder.fromHttpUrl(this.externalBaseUrl).path(bypassPath);
		} else {
			to = UriComponentsBuilder.fromHttpUrl(this.localHost);
			List<String> rootPathList = UriComponentsBuilder.newInstance().path(this.rootPath).build()
					.getPathSegments();
			List<String> pathSegments = from.getPathSegments();

			if (!CollectionUtils.isEmpty(rootPathList) && !CollectionUtils.isEmpty(pathSegments)) {
				if (rootPathList.size() <= pathSegments.size()) {
					for (int i = 0; i < rootPathList.size(); i++) {
						if (!StringUtils.equals(rootPathList.get(i), pathSegments.get(i))) {
							throw new RuntimeException("SAC 서버 루트 Path가 불일치하여 오류가 발생하였습니다.");
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
					throw new RuntimeException("SAC 서버 루트 Path가 설정 파일보다 Depth");
				}
			} else {
				to.path("/" + this.servletPath + from.getPath());
			}
		}
		// 컨트롤러 전송시에는 한글이 깨지면 안됨으로 위에서 Decoding을 시킴.
		if (StringUtils.equals(requestMethod, "GET")) {
			if (!StringUtils.isEmpty(request.getQueryString())) {
				String queryString = null;
				try {
					queryString = URLDecoder.decode(request.getQueryString(), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException("Controller URL 생성시 Decoding 오류가 발생하였습니다.", e);
				}
				to.query(queryString);
			}
		}
		return to.build().toUriString();
	}
}
