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
 * Updated on : 2013. 11. 26. Updated by : 최현식과장, ANB Updated on : 2014. 05. 27. Updated by : 김상호, ANB
 */
public class InternalControllerCallCheckingInterceptor extends HandlerInterceptorAdapter {
	private static Logger LOGGER = LoggerFactory.getLogger(InternalControllerCallCheckingInterceptor.class);

	private String acceptRemoteAddr;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		if (this.isAccept(request)) {
			LOGGER.info("VAILED REQUEST URL : [url : {}]", request.getRequestURL().toString());
			return true;

		} else {
			LOGGER.info("INVAILED REQUEST URL: [url : {}]", request.getRequestURL().toString());
			throw new StorePlatformException("SYS_ERROR_INCORRECT_CALL", request.getRequestURL().toString());
		}

	}

	/**
	 * <pre>
	 * Internal call 을 통과 시킬 서버 목록을 셋팅.
	 * </pre>
	 * 
	 * @param acceptRemoteAddr
	 *            acceptRemoteAddr
	 */
	public void setAcceptRemoteAddr(String acceptRemoteAddr) {
		this.acceptRemoteAddr = acceptRemoteAddr;
	}

	/**
	 * <pre>
	 * Internal call 통과 여부.
	 * </pre>
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return boolean boolean
	 */
	private boolean isAccept(HttpServletRequest request) {

		String callServerName = request.getServerName();
		String remoteAddr = request.getRemoteAddr();

		if (StringUtils.equalsIgnoreCase(callServerName, "LOCALHOST")) {
			return true;
		}

		if (this.acceptRemoteAddr != null) {
			for (String serverName : this.acceptRemoteAddr.split(",")) {
				if (StringUtils.equalsIgnoreCase(remoteAddr, serverName)) {
					LOGGER.info("VAILED REMOTE ADDRESS [{}]", remoteAddr);
					return true;
				}
			}
			LOGGER.info("INVAILED REMOTE ADDRESS [{}]", remoteAddr);
		}

		return false;
	}

}
