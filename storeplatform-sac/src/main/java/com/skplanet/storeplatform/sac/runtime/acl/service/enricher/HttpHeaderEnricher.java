/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.runtime.acl.service.enricher;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.skplanet.storeplatform.sac.common.constant.CommonConstants;
import com.skplanet.storeplatform.sac.runtime.acl.vo.HttpHeaders;

/**
*
* HTTP 헤더 객체 구성 서비스 구현체
*
* Created on 2014. 2. 10. by 서대영, SK 플래닛
* Updated on 2014. 5. 6. by 서대영, SK 플래닛 : requestUrl을 가져오는 출처 변경 (Spring Integration -> ServletRequest)
*/

public class HttpHeaderEnricher implements HttpHeaderEnrichIF {

	@Override
	public HttpHeaders enrichHeader(Map<String, Object> headers) {
		HttpHeaders httpHeaders = new HttpHeaders();

		// Spring Integration에서 headers 객체를 null로 보내는 것에 대한 방어 로직
		if (headers == null) {
			return httpHeaders;
		}

		// String accept = ((MediaType) headers.get(CommonConstants.HEADER_ACCEPT)).toString();
		// String acceptLanguage = (String) headers.get(CommonConstants.HEADER_ACCEPT_LANGUAGE);
		// String requestUrl = (String) headers.get(CommonConstants.HEADER_HTTP_REQUEST_URL);
		String requestUrl = this.getRequestUrl();
		String authKey = (String) headers.get(CommonConstants.HEADER_AUTH_KEY);
		String signature = (String) headers.get(CommonConstants.HEADER_AUTH_SIGNATURE);
		String timestamp = (String) headers.get(CommonConstants.HEADER_AUTH_TIMESTAMP);
		String nonce = (String) headers.get(CommonConstants.HEADER_AUTH_NONCE);
		String tenantId = (String) headers.get(CommonConstants.HEADER_TENANT_ID);
		String systemId = (String) headers.get(CommonConstants.HEADER_SYSTEM_ID);
		String interfaceId = (String) headers.get(CommonConstants.HEADER_INTERFACE_ID);
		String guid = (String) headers.get(CommonConstants.HEADER_GUID);
		// String remoteHost = (String) headers.get(CommonConstants.HEADER_REMOTE_HOST);
		String rempteHost = this.getRemoteHost();
		String remotePort = (String) headers.get(CommonConstants.HEADER_REMOTE_PORT);
		String servletPath = this.getServletPath();
		String queryString = this.getQueryString();

		// httpHeader.setAccept(accept);
		// httpHeader.setAcceptLanguage(acceptLanguage);
		httpHeaders.setRequestUrl(requestUrl);
		httpHeaders.setAuthKey(authKey);
		httpHeaders.setSignature(signature);
		httpHeaders.setTimestamp(timestamp);
		httpHeaders.setNonce(nonce);
		httpHeaders.setTenantId(tenantId);
		httpHeaders.setSystemId(systemId);
		httpHeaders.setInterfaceId(interfaceId);
		httpHeaders.setGuid(guid);
		httpHeaders.setRemoteHost(rempteHost);
		httpHeaders.setRemotePort(remotePort);
		httpHeaders.setServletPath(servletPath);
		httpHeaders.setQueryString(queryString);

		return httpHeaders;
	}


	private String getRequestUrl() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request.getRequestURL().toString();
	}

	private String getServletPath() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request.getServletPath();
	}

	private String getQueryString() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request.getQueryString();
	}

	private String getRemoteHost() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request.getRemoteAddr();
	}

}
