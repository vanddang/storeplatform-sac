package com.skplanet.storeplatform.sac.other.interpark.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.external.client.interpark.sci.InterparkSCI;
import com.skplanet.storeplatform.external.client.interpark.vo.AuthKeyReq;
import com.skplanet.storeplatform.external.client.interpark.vo.AuthKeyRes;
import com.skplanet.storeplatform.external.client.interpark.vo.Purchase;

/**
 * 
 * Interpark Repository 구현체
 * 
 * Updated on : 2014. 1. 20. Updated by : 김현일, 인크로스.
 */
@Component
public class InterparkRepositoryImpl implements InterparkRepository {

	@Autowired
	private InterparkSCI sci;

	@Override
	public void createOrderByEC(Purchase req) {
		this.sci.order(req);
	}

	@Override
	public AuthKeyRes getAuthKeyFromEC(AuthKeyReq req) {
		return this.sci.authKey(req);
	}

}
