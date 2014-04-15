/**
 * 
 */
package com.skplanet.storeplatform.sac.runtime.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.StringUtils;

/**
 * 내부 컨트롤러콜 체커 인터셉터
 * 
 * Updated on : 2013. 11. 26. Updated by : 최현식과장, ANB
 */
public class InternalControllerCallCheckingInterceptor extends HandlerInterceptorAdapter {
	private static Logger LOGGER = LoggerFactory.getLogger(InternalControllerCallCheckingInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		if (StringUtils.equalsIgnoreCase(request.getServerName(), "LOCALHOST")) {
			LOGGER.info("VAILED REQUEST URL : [url : {}]", request.getRequestURL().toString());
			return true;

		} else {
			LOGGER.info("INVAILED REQUEST URL: [url : {}]", request.getRequestURL().toString());
			throw new StorePlatformException("SYS_ERROR_INCORRECT_CALL", request.getRequestURL().toString());
		}

	}

}
