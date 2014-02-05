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

import org.springframework.integration.annotation.Headers;

import com.skplanet.storeplatform.sac.runtime.acl.service.validation.RequestValidateService;

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

	/**
	 * Request를 검증한다. (Interface 및 Timestamp 검사)
	 */
	@Override
	public void validate(@Headers Map<String, Object> headerMap) {
		// Step 1) Interface 검증
		this.validator.validateInterface(headerMap);
		// Step 2) Timestamp 검사
		this.validator.validateTimestamp(headerMap);
	}

	/**
	 * Tenant를 인증한다. (등록된 Tenant인지 확인)
	 */
	@Override
	public void authenticate(@Headers Map<String, Object> headerMap) {
		// TODO 임근대M 구현
	}

	/**
	 * Interface를 인가한다. (호출하는 API에 권한이 있는지 확인)
	 */
	@Override
	public void authorize(@Headers Map<String, Object> headerMap) {
		// TODO 정희원M
	}

}
