/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.runtime.acl.service;

import java.util.Map;

/**
*
* Access Control Layer 구현체
*
* Updated on : 2014. 2. 5.
* Updated by : 서대영/임근대/정희원, SK 플래닛
*/
public interface AclServiceTobe {

	void validate(Map<String, Object> headerMap);

	void authenticate(Map<String, Object> headerMap);

	void authorize(Map<String, Object> headerMap);

}
