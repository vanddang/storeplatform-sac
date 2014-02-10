/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.runtime.acl.service.enricher;

import java.util.Map;

import com.skplanet.storeplatform.sac.runtime.acl.vo.HttpHeaders;

public interface HttpHeaderEnrichService {

	/**
	 * <pre>
	 * Integration Header로 부터 Http Header헤더 객체를 만든다.
	 * </pre>
	 * @param headerMap
	 */
	HttpHeaders validateHeader(Map<String, Object> headerMap);

}
