package com.skplanet.storeplatform.sac.runtime.extend;

import com.skplanet.storeplatform.sac.runtime.acl.constant.AclConstant;
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
        if (AclConstant.IID_FORCE_BIZ.equals(interfaceId)) {
            return "messageRouteChannel";
        }
        else {
            return DEFAULT_PRE_PROCESS_CHANNEL;
        }
	}

}
