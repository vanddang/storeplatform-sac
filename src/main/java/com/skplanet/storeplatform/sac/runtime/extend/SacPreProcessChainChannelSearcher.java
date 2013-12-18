package com.skplanet.storeplatform.sac.runtime.extend;

import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.framework.integration.router.PreProcessChainChannelSearcher;

/**
 * 
 * Calss 설명
 * 
 * Updated on : 2013. 11. 28. Updated by : 김현일, 인크로스.
 */
@Component
public class SacPreProcessChainChannelSearcher implements PreProcessChainChannelSearcher {

	@Override
	public String search(String interfaceId) {

		return DEFAULT_PRE_PROCESS_CHANNEL;
	}

}
