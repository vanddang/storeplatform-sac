/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.interpark.service;

import com.skplanet.storeplatform.external.client.interpark.vo.AuthKeyReq;
import com.skplanet.storeplatform.external.client.interpark.vo.AuthKeyRes;
import com.skplanet.storeplatform.external.client.interpark.vo.Purchase;

public interface InterparkService {

	boolean createOrder(Purchase req);

	AuthKeyRes getAuthKey(AuthKeyReq req);

}
