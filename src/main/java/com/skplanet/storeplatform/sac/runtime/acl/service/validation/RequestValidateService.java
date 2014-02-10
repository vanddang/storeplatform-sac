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

import com.skplanet.storeplatform.sac.runtime.acl.vo.HttpHeaders;

/**
*
* 요청 검증 서비스 구현체
*
* Updated on : 2014. 2. 5.
* Updated by : 서대영, SK 플래닛
*/
public interface RequestValidateService {

	/**
	 * <pre>
	 * 요청 헤더를 검증한다
	 * </pre>
	 * @param headerMap
	 */
	void validateHeaders(HttpHeaders header);

	/**
	 * <pre>
	 * 요청 시간을 검증한다.
	 * </pre>
	 * @param headerMap
	 */
	void validateTimestamp(HttpHeaders header);

	/**
	 * <pre>
	 * 인터페이스를 검증한다.
	 * </pre>
	 * @param headerMap
	 */
	void validateInterface(HttpHeaders header);

	/**
	 * <pre>
	 * 시세틈를 검증한다.
	 * </pre>
	 * @param headerMap
	 */
	void validateService(HttpHeaders header);

}
