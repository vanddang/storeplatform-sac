
/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.runtime.filter;

import com.skplanet.storeplatform.sac.common.constant.CommonConstants;
import com.skplanet.storeplatform.sac.runtime.acl.service.AclService;
import com.skplanet.storeplatform.sac.runtime.acl.vo.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 *
 * ACL Filter
 *
 * Updated on : 2015. 7. 9
 * Updated by : 임근대, SK 플래닛 : Internal Call 제거를 위해 Spring Integration -> Spring MVC 전환
 */
@Component
public class AclFilter implements Filter {

	private static final UrlPathHelper urlPathHelper = new UrlPathHelper();

	@Autowired
	AclService aclService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		final HttpServletRequest httpRequest = (HttpServletRequest) request;

		HttpHeaders httpHeaders = getHttpHeaders(httpRequest);

		//인증 & 인가
		if (aclService.authenticate(httpHeaders) && aclService.authorize(httpHeaders)) {
			filterChain.doFilter(request, response);
		}
	}

	private HttpHeaders getHttpHeaders(HttpServletRequest request) {
		HttpHeaders httpHeaders = new HttpHeaders();
		//헤더 설정
		// RequestUrl : 도메인을 포함한 Full URL 이 필요
		String requestUrl = request.getRequestURL().toString();
		
		String authKey = request.getHeader(CommonConstants.HEADER_AUTH_KEY);
		String signature = request.getHeader(CommonConstants.HEADER_AUTH_SIGNATURE);
		String timestamp = request.getHeader(CommonConstants.HEADER_AUTH_TIMESTAMP);
		String nonce = request.getHeader(CommonConstants.HEADER_AUTH_NONCE);
		String tenantId = request.getHeader(CommonConstants.HEADER_TENANT_ID);
		String systemId = request.getHeader(CommonConstants.HEADER_SYSTEM_ID);
		String interfaceId = request.getHeader(CommonConstants.HEADER_INTERFACE_ID);

		//ACL에서 사용 안함
		String guid = request.getHeader(CommonConstants.HEADER_GUID);
		// String remoteHost = (String) headers.get(CommonConstants.HEADER_REMOTE_HOST);
		String rempteHost = request.getRemoteAddr();
		String remotePort = request.getHeader(CommonConstants.HEADER_REMOTE_PORT);
		String queryString = request.getQueryString();

		String servletPath = urlPathHelper.getLookupPathForRequest(request);

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

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {

	}
}
