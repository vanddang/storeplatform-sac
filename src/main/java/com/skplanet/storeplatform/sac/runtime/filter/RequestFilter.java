package com.skplanet.storeplatform.sac.runtime.filter;

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

}
