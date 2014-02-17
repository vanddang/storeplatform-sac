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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skplanet.storeplatform.sac.runtime.acl.service.common.AclDataAccessService;
import com.skplanet.storeplatform.sac.runtime.acl.vo.HttpHeaders;
import com.skplanet.storeplatform.sac.runtime.acl.vo.Tenant;

/**
 *
 * 인증
 *
 * Updated on : 2014. 2.105. Updated by : 임근대, SK 플래닛.
 */
// @Service
public class AuthenticationServiceImpl implements AuthenticateService {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

	// @Autowired
	private AclDataAccessService service;


	@Override
	public void authenticate(HttpHeaders header) {
		// TODO : Tenant 인증 처리
		String authKey = header.getAuthKey();

		Tenant tenant = this.service.selectTenantByAuthKey(authKey);
		logger.debug("tenant={}", tenant);


	}
}
