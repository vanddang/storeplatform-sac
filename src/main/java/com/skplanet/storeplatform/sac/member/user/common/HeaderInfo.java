/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.user.common;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.sac.client.member.vo.common.HeaderVo;

/**
 * Header 정보
 * 
 * Updated on : 2014. 1. 2. Updated by : 심대진, 다모아 솔루션.
 */
@Component
public class HeaderInfo {

	private static final Logger logger = LoggerFactory.getLogger(HeaderInfo.class);

	public HeaderVo getHeader(Map<String, Object> headers) {

		logger.info("Headers  : {}", headers.entrySet());

		HeaderVo headerVo = new HeaderVo();

		/**
		 * Standard Header
		 */
		headerVo.setAccept((String) headers.get("accept"));
		headerVo.setAcceptLanguage((String) headers.get("accept-language"));
		headerVo.setContentLength((String) headers.get("content-length"));
		headerVo.setContentType((String) headers.get("content-type"));

		/**
		 * Custom Header
		 */
		headerVo.setxSacAuthTenantKey((String) headers.get("x-sac-auth-tenant-key"));
		headerVo.setxSacAuthSignature((String) headers.get("x-sac-auth-signature"));
		headerVo.setxSacAuthTimestamp((String) headers.get("x-sac-auth-timestamp"));
		headerVo.setxSacAuthNonce((String) headers.get("x-sac-auth-nonce"));
		headerVo.setxSacInterfaceId((String) headers.get("x-sac-interface_id"));
		headerVo.setxSacGuid((String) headers.get("x-sac-guid"));
		headerVo.setxSacDeviceInfo((String) headers.get("x-sac-device-info"));
		headerVo.setxSacNetworkInfo((String) headers.get("x-sac-network-info"));

		return headerVo;
	}
}
