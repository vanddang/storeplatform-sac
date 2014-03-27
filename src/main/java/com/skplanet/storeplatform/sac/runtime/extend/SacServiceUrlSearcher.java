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
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.framework.integration.enricher.ServiceUrlSearcher;

/**
 * 
 * ServiceUrlSearcher 구현체
 * 
 * Updated on : 2014. 1. 16. Updated by : 김현일, 인크로스.
 */
@Component
public class SacServiceUrlSearcher implements ServiceUrlSearcher {

	/**
	 * 내부 요청 서블릿 Host.
	 */
	@Value("#{propertiesForSac['skp.common.inner.servlet.host']}")
	private String innerServletHost;
	/**
	 * 내부 요청 서블릿 Path.
	 */
	@Value("#{propertiesForSac['skp.common.inner.servlet.path']}")
	private String innerServletPath;
	/**
	 * 임시 external URL -> 추후 Component 테이블의 URL 조합이 됨.
	 */
	@Value("#{propertiesForSac['component.external.baseUrl']}")
	private String externalBaseUrl;

	@Resource(name = "propertiesForSac")
	private Properties properties;

	@Override
	public String search(Map<String, Object> headerMap) {
		// 요청 객체 획득
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();

		// 요청 서블릿 컨텍스트 Path
		String requestContextPath = request.getContextPath();
		// 요청 URI
		String requestURI = request.getRequestURI();
		// 내부 요청 URI
		String innerRequestURI = StringUtils.removeStart(requestURI, requestContextPath);
		// 요청 Method
		String requestMethod = request.getMethod();
		//
		int localPort = request.getLocalPort();

		// External Component는 SAC에서 Bypass 대상이나 우선 프로퍼티로 해당 기능이 가능하게 구현.
		// 1. 해당 인터페이스의 bypass유무가 'Y' 인 대상.
		// 2. 인터페이스 Route에 등록된 Bypass 정보를 기준으로
		// 3. 컴포넌트 정보와, Bypass의 URL 정보를 조합하여 return URL을 생성.
		String bypassPath = this.properties.getProperty(innerRequestURI, "");

		UriComponentsBuilder to;

		// Bypass 이면.
		if (StringUtils.isNotEmpty(bypassPath)) {
			to = UriComponentsBuilder.fromHttpUrl(this.externalBaseUrl).path(bypassPath);
		} else {
			// 그외는 내부 서블릿 URL 호출.
			to = UriComponentsBuilder.fromHttpUrl(this.innerServletHost).port(localPort).path(requestContextPath)
					.path(this.innerServletPath).path(innerRequestURI);
		}
		if (requestMethod.equals("GET")) {
			// 쿼리 Decoding 후 Controller로 전달.
			if (StringUtils.isNotBlank(request.getQueryString())) {
				try {
					String queryString = URLDecoder.decode(request.getQueryString(), "UTF-8");
					to.query(queryString);
				} catch (UnsupportedEncodingException e) {
					throw new StorePlatformException("GET URL Decode시 오류가 발생하였습니다.", e);
				}
			}
		}
		return to.build().toUriString();
	}
}
