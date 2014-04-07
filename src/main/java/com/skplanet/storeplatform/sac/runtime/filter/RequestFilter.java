package com.skplanet.storeplatform.sac.runtime.filter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.skplanet.storeplatform.framework.core.cache.process.GlobalCacheProcessor;
import com.skplanet.storeplatform.framework.core.util.log.TLogThreadHolder;
import com.skplanet.storeplatform.framework.integration.filter.AbstractStorePlatformRequestFilter;

/**
 * Thread Context Filter
 * 
 * Updated on : 2014-01-06 Updated by : 김상호, 에이엔비.
 */
public class RequestFilter extends AbstractStorePlatformRequestFilter {

	@Override
	protected String getContextId() {
		return "SAC";
	}

	@Override
	protected void initExtraRequestFilter(HttpServletRequest request, HttpServletResponse response) {
		/* ShuttleThreadHolder 에 기본 Logger 를 지정 한다. */
		TLogThreadHolder.setShuttleLogger(LoggerFactory.getLogger("TLOG_SAC_LOGGER"));
	}

	@Override
	protected Map<String, String> getReqeustHeader(HttpServletRequest request) {

		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(this.filterConfig
				.getServletContext());

		Map<String, String> headerMap = new HashMap<String, String>();

		/* GlobalCacheProcessor useCache 가 false 일 경우 request header 내 'x-sac-use-cache' 에 false 를 셋팅 한다. */
		GlobalCacheProcessor globalCacheProcessor = (GlobalCacheProcessor) ac.getBean("globalCacheProcessor");

		if (globalCacheProcessor != null) {
			if (!globalCacheProcessor.isUseCache()) {
				headerMap.put("x-sac-use-cache", "false");
			}
		}

		return headerMap;
	}

}
