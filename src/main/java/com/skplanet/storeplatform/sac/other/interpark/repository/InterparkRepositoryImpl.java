package com.skplanet.storeplatform.sac.other.interpark.repository;

import org.springframework.beans.factory.annotation.Autowired;

import com.skplanet.storeplatform.external.client.interpark.sci.InterparkSCI;
import com.skplanet.storeplatform.external.client.interpark.vo.AuthKeyReq;
import com.skplanet.storeplatform.external.client.interpark.vo.AuthKeyRes;

public class InterparkRepositoryImpl implements InterparkRepository {

	@Autowired
	private InterparkSCI sci;

	@Override
	public AuthKeyRes getAuthKey(AuthKeyReq req) {
		return this.sci.authKey(req);
	}

}
