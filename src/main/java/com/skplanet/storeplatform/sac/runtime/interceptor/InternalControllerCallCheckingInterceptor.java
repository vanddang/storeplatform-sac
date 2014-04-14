/**
 * 
 */
package com.skplanet.storeplatform.sac.runtime.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.skplanet.storeplatform.framework.core.util.StringUtils;

/**
 * Calss 설명
 * 
 * Updated on : 2014. 4. 14. Updated by : 홍占썸동, SK 占시뤄옙占쏙옙.
 */
public class InternalControllerCallCheckingInterceptor extends HandlerInterceptorAdapter {
	private static Logger LOGGER = LoggerFactory.getLogger(InternalControllerCallCheckingInterceptor.class);

	public InternalControllerCallCheckingInterceptor() {

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		if (StringUtils.equalsIgnoreCase(request.getServerName(), "LOCALHOST")) {
			LOGGER.info("VAILED REQUEST URL : [url : {}]", request.getRequestURL().toString());
			// return true;

		} else {
			LOGGER.info("INVAILED REQUEST URL: [url : {}]", request.getRequestURL().toString());
			// TODO : 현재 테스트 진행 중
			// throw new StorePlatformException("SAC_CMN_0003", request.getRequestURL().toString());
		}

		return true;
	}

}
