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

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.common.constant.CommonConstants;
import com.skplanet.storeplatform.sac.runtime.acl.service.common.AclDbAccessService;
import com.skplanet.storeplatform.sac.runtime.acl.util.AclUtils;
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
	private AclDbAccessService service;

	@Override
	public void validateInterface(Map<String, Object> headerMap) {
		String interfaceId = (String) headerMap.get(CommonConstants.HEADER_INTERFACE_ID);
		String requestUrl = (String) headerMap.get(CommonConstants.HEADER_HTTP_REQUEST_URL);

		Interface intf = this.service.select(interfaceId);

		if (intf == null) {
			throw new StorePlatformException("SAC_CMN_0001", interfaceId);
		}

		String urlFromDb = intf.getUrl();
		if (!StringUtils.equalsIgnoreCase(requestUrl, urlFromDb)) {
			throw new StorePlatformException("SAC_CMN_0002", requestUrl);
		}
	}

	@Override
	public void validateTimestamp(Map<String, Object> headerMap) {
		String requestTimestamp = (String) headerMap.get(CommonConstants.HEADER_AUTH_TIMESTAMP);
		if (!AclUtils.isTimeOut(requestTimestamp)) {
			throw new StorePlatformException("SAC_CMN_0003");
		}
	}

}
