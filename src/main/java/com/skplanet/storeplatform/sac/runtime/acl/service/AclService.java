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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.annotation.Header;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.sac.runtime.acl.service.authentication.AuthenticateService;
import com.skplanet.storeplatform.sac.runtime.acl.service.authorization.AuthorizeService;
import com.skplanet.storeplatform.sac.runtime.acl.vo.HttpHeaders;

/**
 *
 * Access Control Layer
 *
 * Updated on : 2014. 2. 5.
 * Updated by : 서대영/임근대/정희원, SK 플래닛
 */
@Service
public class AclService {

	@Autowired
	private AuthenticateService authenticateService;

	@Autowired
	private AuthorizeService authorizationService;

	@Value("#{propertiesForSac['skp.runtime.acl.authentication']}")
	private boolean authentication;

	@Value("#{propertiesForSac['skp.runtime.acl.authorization']}")
	private boolean authorization;

	public void setAuthentication(boolean authentication) {
		this.authentication = authentication;
	}

	public void setAuthorization(boolean authorization) {
		this.authorization = authorization;
	}

	/**
	 * Tenant를 인증한다. (등록된 Tenant인지 확인)
	 */
	public boolean authenticate(@Header("httpHeaders") HttpHeaders headers) {
		if (!this.authentication) return true;

		// Step 1) Tenant 인증
		this.authenticateService.authenticate(headers);

		return true;
	}

	/**
	 * Interface를 인가한다. (호출하는 API에 권한이 있는지 확인)
	 */
	public boolean authorize(@Header("httpHeaders") HttpHeaders headers) {
		if (!this.authorization) return true;

		// 1. Interface 유효성 확인
		this.authorizationService.checkInterface(headers);
    	// 2. Interface, Tenant 간 맵핑 확인
		this.authorizationService.checkMapping(headers);

		return true;
	}

}
