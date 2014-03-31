package com.skplanet.storeplatform.sac.runtime.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.framework.integration.filter.AbstractStorePlatformRequestFilter#getGUID()
	 */
	@Override
	protected String getGUID(HttpServletRequest request) {
		return null;
	}

}
