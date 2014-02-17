/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.runtime.acl.service.validation;

import org.apache.commons.lang3.StringUtils;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.runtime.acl.service.common.AclDataAccessService;
import com.skplanet.storeplatform.sac.runtime.acl.util.AclUtils;
import com.skplanet.storeplatform.sac.runtime.acl.vo.HttpHeaders;
import com.skplanet.storeplatform.sac.runtime.acl.vo.Interface;

/**
 *
 * 요청 검증 서비스 구현체
 *
 * Updated on : 2014. 2. 5.
 * Updated by : 서대영, SK 플래닛
 */
// @Service
public class RequestValidateServiceImpl implements RequestValidateService {

	// @Autowired
	private AclDataAccessService service;

	@Override
	public void validateHeaders(HttpHeaders header) {
		String accept = header.getAccept();
		String authKey = header.getAuthKey();
		String systemId = header.getSystemId();
		String interfaceId = header.getInterfaceId();
		String guid = header.getGuid();

		if (StringUtils.isBlank(accept))
			throw new StorePlatformException("SAC_CMN_0004", accept);
		if (StringUtils.isBlank(authKey))
			throw new StorePlatformException("SAC_CMN_0004", authKey);
		if (StringUtils.isBlank(systemId))
			throw new StorePlatformException("SAC_CMN_0004", systemId);
		if (StringUtils.isBlank(interfaceId))
			throw new StorePlatformException("SAC_CMN_0004", interfaceId);
		if (StringUtils.isBlank(guid))
			throw new StorePlatformException("SAC_CMN_0004", guid);

	}

	@Override
	public void validateTimestamp(HttpHeaders header) {
		String requestTimestamp = header.getInterfaceId();
		if (!AclUtils.isTimeOut(requestTimestamp)) {
			throw new StorePlatformException("SAC_CMN_0003");
		}
	}


	@Override
	public void validateInterface(HttpHeaders header) {
		String interfaceId = header.getInterfaceId();
		String requestUrl = header.getRequestUrl();

		Interface intf = this.service.selectInterfaceById(interfaceId);

		if (intf == null) {
			throw new StorePlatformException("SAC_CMN_0001", interfaceId);
		}

		String urlFromDb = intf.getUrl();
		if (!StringUtils.equalsIgnoreCase(requestUrl, urlFromDb)) {
			throw new StorePlatformException("SAC_CMN_0002", requestUrl);
		}
	}

	@Override
	public void validateService(HttpHeaders header) {
		// TODO Auto-generated method stub

	}

}
