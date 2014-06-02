/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.runtime.extend;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.framework.integration.enricher.ServiceUrlSearcher;
import com.skplanet.storeplatform.sac.common.constant.CommonConstants;
import com.skplanet.storeplatform.sac.runtime.acl.service.common.AclDataAccessService;
import com.skplanet.storeplatform.sac.runtime.acl.vo.Interface;
import com.skplanet.storeplatform.sac.runtime.extend.url.SacExternalUrlBuilder;
import com.skplanet.storeplatform.sac.runtime.extend.url.SacInternalUrlBuilder;

/**
 * ServiceUrlSearcher 구현체
 *
 * <pre>
 * Created on 2014. 1. 16. by 김현일, 인크로스.
 * Updated on 2014. 05. 29. by 서대영, SK 플래닛 : bypass 여부 획득 방법 변경 (Properties File -> DB Cache)
 * </pre>
 */
@Component
public class SacServiceUrlSearcher implements ServiceUrlSearcher {

	private static final Logger LOGGER = LoggerFactory.getLogger(SacServiceUrlSearcher.class);

	@Autowired
	private AclDataAccessService aclDataService;

	@Autowired
	private SacInternalUrlBuilder intUrlBuilder;

	@Autowired
	private SacExternalUrlBuilder extUrlBuilder;

	@Override
	public String search(Map<String, Object> headerMap) {
		// 요청 객체 획득
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

		// 요청 서블릿 컨텍스트 Path
		String requestContextPath = request.getContextPath();
		// 요청 URI
		String requestURI = request.getRequestURI();
		// 내부 요청 URI
		String innerRequestURI = StringUtils.removeStart(requestURI, requestContextPath);

		// External Component는 SAC에서 Bypass 대상이나 우선 프로퍼티로 해당 기능이 가능하게 구현.
		// 1. 해당 인터페이스의 bypass유무가 'Y' 인 대상.
		// 2. 인터페이스 Route에 등록된 Bypass 정보를 기준으로
		// 3. 컴포넌트 정보와, Bypass의 URL 정보를 조합하여 return URL을 생성.
		UriComponentsBuilder to;

		// Bypass 이면 EC URL을 Properties에서 가져오고 그외에는 내부 서블릿 URL 호출
		if (this.isBypass(headerMap)) {
			to = this.extUrlBuilder.buildUrl(innerRequestURI);
		} else {
			to = this.intUrlBuilder.buildUrl(innerRequestURI, requestContextPath);
		}

		if (StringUtils.isNotBlank(request.getQueryString())) {
			to.query(request.getQueryString());
		}

		String requestUrl = this.getRequestURL(request);
		String serviceUrl = to.build(false).toUriString();

		LOGGER.warn("#######################################################################################################");
		LOGGER.warn("Request URL : " + requestUrl);
		LOGGER.warn("Service URL : " + serviceUrl);
		LOGGER.warn("#######################################################################################################");

		return serviceUrl;
	}

	/**
	 * Bypass 여부 조회
	 */
	private boolean isBypass(Map<String, Object> headers) {
		String interfaceId = (String) headers.get(CommonConstants.HEADER_INTERFACE_ID);
		Interface intf = this.aclDataService.selectInterfaceById(interfaceId);
		if (intf != null) {
			return intf.isBypass();
		} else {
			return false;
		}
	}

	private String getRequestURL(HttpServletRequest request) {
	    StringBuffer requestURL = request.getRequestURL();
	    String queryString = request.getQueryString();

	    if (queryString == null) {
	        return requestURL.toString();
	    } else {
	        return requestURL.append('?').append(queryString).toString();
	    }
	}


}
