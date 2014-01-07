/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.common;

import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
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
		headerVo.setAccept(ObjectUtils.toString(headers.get("accept")));
		headerVo.setAcceptLanguage(ObjectUtils.toString(headers.get("accept-language")));
		headerVo.setContentLength(ObjectUtils.toString(headers.get("content-length")));
		headerVo.setContentType(ObjectUtils.toString(headers.get("content-type")));

		/**
		 * Custom Header
		 */
		headerVo.setxSacAuthTenantKey(ObjectUtils.toString(headers.get("x-sac-auth-tenant-key")));
		headerVo.setxSacAuthSignature(ObjectUtils.toString(headers.get("x-sac-auth-signature")));
		headerVo.setxSacAuthTimestamp(ObjectUtils.toString(headers.get("x-sac-auth-timestamp")));
		headerVo.setxSacAuthNonce(ObjectUtils.toString(headers.get("x-sac-auth-nonce")));
		headerVo.setxSacInterfaceId(ObjectUtils.toString(headers.get("x-sac-interface_id")));
		headerVo.setxSacGuid(ObjectUtils.toString(headers.get("x-sac-guid")));
		headerVo.setxSacDeviceInfo(ObjectUtils.toString(headers.get("x-sac-device-info")));
		headerVo.setxSacNetworkInfo(ObjectUtils.toString(headers.get("x-sac-network-info")));

		return headerVo;
	}
}
