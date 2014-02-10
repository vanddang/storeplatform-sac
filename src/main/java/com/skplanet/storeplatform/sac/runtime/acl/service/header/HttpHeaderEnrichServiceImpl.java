/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.runtime.acl.service.header;

import java.util.Map;

import com.skplanet.storeplatform.sac.common.constant.CommonConstants;
import com.skplanet.storeplatform.sac.runtime.acl.vo.HttpHeaders;

/**
*
* HTTP 헤더 객체 구성 서비스 구현체
*
* Updated on : 2014. 2. 10.
* Updated by : 서대영, SK 플래닛
*/
public class HttpHeaderEnrichServiceImpl implements HttpHeaderEnrichService {

	@Override
	public HttpHeaders validateHeader(Map<String, Object> headerMap) {
		String accept = (String) headerMap.get(CommonConstants.HEADER_ACCEPT);
		String acceptLanguage = (String) headerMap.get(CommonConstants.HEADER_ACCEPT_LANGUATE);
		String requestUrl = (String) headerMap.get(CommonConstants.HEADER_HTTP_REQUEST_URL);
		String authKey = (String) headerMap.get(CommonConstants.HEADER_AUTH_KEY);
		String signature = (String) headerMap.get(CommonConstants.HEADER_AUTH_SIGNATURE);
		String timestamp = (String) headerMap.get(CommonConstants.HEADER_AUTH_TIMESTAMP);
		String nonce = (String) headerMap.get(CommonConstants.HEADER_AUTH_NONCE);
		String systemId = (String) headerMap.get(CommonConstants.HEADER_SYSTEM_ID);
		String interfaceId = (String) headerMap.get(CommonConstants.HEADER_INTERFACE_ID);
		String guid = (String) headerMap.get(CommonConstants.HEADER_GUID);

		HttpHeaders httpHeader = new HttpHeaders();

		httpHeader.setAccept(accept);
		httpHeader.setAcceptLanguage(acceptLanguage);
		httpHeader.setRequestUrl(requestUrl);
		httpHeader.setAuthKey(authKey);
		httpHeader.setSignature(signature);
		httpHeader.setTimestamp(timestamp);
		httpHeader.setNonce(nonce);
		httpHeader.setSystemId(systemId);
		httpHeader.setInterfaceId(interfaceId);
		httpHeader.setGuid(guid);

		return httpHeader;
	}

}
