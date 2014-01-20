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

/**
 * 
 * Interpark Service
 * 
 * Updated on : 2014. 1. 20. Updated by : 김현일, 인크로스.
 */
public interface InterparkService {

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @return boolean
	 */
	boolean createOrder(Purchase req);

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @return AuthKeyRes
	 */
	AuthKeyRes getAuthKey(AuthKeyReq req);

}
