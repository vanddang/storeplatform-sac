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

import com.skplanet.storeplatform.sac.runtime.acl.service.authorizaiton.RequestAuthorizationService;
import org.springframework.integration.annotation.Header;

import com.skplanet.storeplatform.sac.runtime.acl.service.authentication.AuthenticateService;
import com.skplanet.storeplatform.sac.runtime.acl.service.validation.RequestValidateService;
import com.skplanet.storeplatform.sac.runtime.acl.vo.HttpHeaders;

/**
 *
 * Access Control Layer 구현체
 *
 * Updated on : 2014. 2. 5.
 * Updated by : 서대영/임근대/정희원, SK 플래닛
 */
// @Service
public class AclServiceTobeImpl implements AclServiceTobe {

	// @Autowired
	private RequestValidateService validator;
	private AuthenticateService authenticateService;
    private RequestAuthorizationService authorizationService;

	/**
	 * Request를 검증한다. (Interface 및 Timestamp 검사)
	 */
	@Override
	public void validate(@Header("httpHeaders") HttpHeaders header) {
		// Step 1) 필수 헤더 검사
		this.validator.validateHeaders(header);
		// Step 2) 요청 시간 검사
		this.validator.validateTimestamp(header);
		// Step 3) 인터페이스 검증
		this.validator.validateInterface(header);
		// Step 4) 서비스 검증
		this.validator.validateService(header);
	}

	/**
	 * Tenant를 인증한다. (등록된 Tenant인지 확인)
	 */
	@Override
	public void authenticate(@Header("httpHeaders") HttpHeaders header) {
		// Step 1) Tenant 인증
		this.authenticateService.authenticate(header);
	}

	/**
	 * Interface를 인가한다. (호출하는 API에 권한이 있는지 확인)
	 */
	@Override
	public void authorize(@Header("httpHeaders") HttpHeaders header) {
		this.authorizationService.authorize(header);
	}

}
