package com.skplanet.storeplatform.sac.other.interpark.repository;

import com.skplanet.storeplatform.external.client.interpark.vo.AuthKeyReq;
import com.skplanet.storeplatform.external.client.interpark.vo.AuthKeyRes;
import com.skplanet.storeplatform.external.client.interpark.vo.Purchase;

public interface InterparkRepository {

	void createOrderByEC(Purchase req);

	AuthKeyRes getAuthKeyFromEC(AuthKeyReq req);

}
