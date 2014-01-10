package com.skplanet.storeplatform.sac.other.interpark.repository;

import org.springframework.beans.factory.annotation.Autowired;

import com.skplanet.storeplatform.external.client.interpark.sci.InterparkSCI;
import com.skplanet.storeplatform.external.client.interpark.vo.AuthKeyReq;
import com.skplanet.storeplatform.external.client.interpark.vo.AuthKeyRes;
import com.skplanet.storeplatform.external.client.interpark.vo.Purchase;

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
