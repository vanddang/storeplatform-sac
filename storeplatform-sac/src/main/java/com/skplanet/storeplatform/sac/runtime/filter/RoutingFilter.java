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

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UrlPathHelper;

import com.skplanet.storeplatform.sac.common.constant.CommonConstants;
import com.skplanet.storeplatform.sac.runtime.bypass.service.BypassService;

/**
 * 바이패스 인터셉터 
 *   
 * Created on 2015.7.10 by 서대영
 */
@Component
public class RoutingFilter implements Filter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RoutingFilter.class);
	
	private static UrlPathHelper URL_PATH_HELPER = new UrlPathHelper();
	
	@Autowired
	private BypassService srvc;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		
		String interfaceId = request.getHeader(CommonConstants.HEADER_INTERFACE_ID);
		String requestUri = request.getRequestURI();
		
		if (CommonConstants.EXPRESS_INTERFACE_ID.equals(interfaceId)) {
			LOGGER.info("{}({}) is routing to the express line.", interfaceId, requestUri);
			chain.doFilter(request, res);
		} else if (srvc.isBypass(interfaceId)) {
			LOGGER.info("{}({}) is routing to the bypass line.", interfaceId, requestUri);
			RequestDispatcher dispatcher = request.getRequestDispatcher(getBypassPath(request));
			dispatcher.forward(request, res);
		} else {
			LOGGER.info("{}({}) is routing to the internal line.", interfaceId, requestUri);
			chain.doFilter(request, res);
		}
	}
	
	private String getBypassPath(HttpServletRequest request) {
		String requestUri = URL_PATH_HELPER.getRequestUri(request);
		String lookupPath = URL_PATH_HELPER.getLookupPathForRequest(request);
		String servletPath = StringUtils.substringBefore(requestUri, lookupPath);
		String bypassPath = servletPath + "/bypass";
		return bypassPath;
	}
	
	@Override
	public void destroy() {
	}

}
