package com.skplanet.storeplatform.sac.runtime.filter;

import javax.servlet.http.HttpServletRequest;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.framework.integration.filter.AbstractStorePlatformRequestFilter#getFdsLoggerName()
	 */
	@Override
	protected String getFdsLoggerName() {
		return "TLOG_SAC_LOGGER";
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
