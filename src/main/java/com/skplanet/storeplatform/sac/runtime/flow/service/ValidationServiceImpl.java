/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.runtime.flow.service;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.annotation.Headers;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.skplanet.storeplatform.framework.core.exception.ErrorMessageBuilder;
import com.skplanet.storeplatform.sac.runtime.acl.constant.AclConstant;
import com.skplanet.storeplatform.sac.runtime.acl.service.AclService;
import com.skplanet.storeplatform.sac.runtime.flow.exception.ValidException;
import com.skplanet.storeplatform.sac.runtime.flow.exception.code.ValidErrorCode;
import com.skplanet.storeplatform.sac.runtime.flow.vo.HeaderInfo;

/**
 * 
 * Calss 설명
 * 
 * Updated on : 2013. 11. 26. Updated by : 김현일, 인크로스.
 */
@Service
public class ValidationServiceImpl implements ValidationService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AclService aclService;

	@Value("#{propertiesForSac['skp.common.service.acl']}")
	private boolean aclYn;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.integration.v2.flow.service.ValidationService#checkHeader(java.util.Map)
	 */
	@Override
	public HeaderInfo checkHeader(@Headers Map<String, Object> headerMap) {

		if (!this.aclYn)
			return new HeaderInfo();

		this.logger.info("메서드 실행 (checkHeader : " + headerMap + ")");
		// 헤더 분석
		String authInfo = (String) headerMap.get(AclConstant.HEADER_AUTH_INFO);

		if (StringUtils.isEmpty(authInfo))
			throw new ValidException(ErrorMessageBuilder.create().code(ValidErrorCode.NULL_ARGUMENT.name())
					.arguments("x-store-auth-info", "null").build());

		Map<String, String> authMap = this.string2Map(authInfo);
		String authKey = String.valueOf(authMap.get(AclConstant.HEADER_AUTHKEY));
		String ist = String.valueOf(authMap.get(AclConstant.HEADER_IST));
		String requestUrl = String.valueOf(headerMap.get(AclConstant.HEADER_HTTP_REQUEST_URL));
		this.logger.info("메서드 실행 (checkHeader : " + authKey + ")");
		this.logger.info("메서드 실행 (checkHeader : " + ist + ")");
		if (StringUtils.isEmpty(authKey)) {
			throw new ValidException(ErrorMessageBuilder.create().code(ValidErrorCode.NULL_ARGUMENT.name())
					.arguments("authKey", "null").build());
		}
		if (StringUtils.isEmpty(ist)) {
			throw new ValidException(ErrorMessageBuilder.create().code(ValidErrorCode.NULL_ARGUMENT.name())
					.arguments("ist", "null").build());
		}
		HeaderInfo headerInfo = new HeaderInfo();
		headerInfo.setAuthKey(authKey);
		headerInfo.setIst(ist);
		headerInfo.setPath(UriComponentsBuilder.fromHttpUrl(requestUrl).build().getPath());
		return headerInfo;
	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param source
	 *            source
	 * @return Map<String, String>
	 */
	private Map<String, String> string2Map(String source) {
		Map<String, String> mapData = new HashMap<String, String>(); // 문자열을 Map 변환하기 위한 변수
		if (StringUtils.isEmpty(source))
			return mapData;
		String rawString = null; // Key=Value 저장하기 위한 임시 변수
		int iIndex = -1; // Key=Value 구조 여부 확인용 임시 변수
		StringTokenizer st = new StringTokenizer(source, ";");
		while (st.hasMoreElements()) {
			rawString = st.nextToken().trim();
			if ((iIndex = rawString.indexOf("=")) > -1) {
				mapData.put(rawString.substring(0, iIndex), rawString.substring(iIndex + 1));
			}
		}
		return mapData;
	}
}
