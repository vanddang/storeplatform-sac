/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.runtime.acl.service.authentication;

import com.skplanet.storeplatform.sac.runtime.acl.vo.HttpHeaders;

/**
 *
 * 인증
 *
 * Updated on : 2014. 2.105. Updated by : 임근대, SK 플래닛.
 */
public interface AuthenticateService {

	/**
	 * 인증 처리 구현
	 *
	 * @param headerMap
	 */
	void authenticate(HttpHeaders header);

}
