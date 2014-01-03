package com.skplanet.storeplatform.sac.other.interpark.repository;

import com.skplanet.storeplatform.external.client.interpark.vo.AuthKeyReq;
import com.skplanet.storeplatform.external.client.interpark.vo.AuthKeyRes;

public interface InterparkRepository {

	AuthKeyRes getAuthKey(AuthKeyReq req);

}
