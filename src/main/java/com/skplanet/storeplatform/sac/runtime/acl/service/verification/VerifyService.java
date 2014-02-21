/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.runtime.acl.service.verification;

import com.skplanet.storeplatform.sac.runtime.acl.vo.HttpHeaders;

/**
*
* 요청 검증 서비스 구현체
*
* Updated on : 2014. 2. 5.
* Updated by : 서대영, SK 플래닛
*/
public interface VerifyService {

	/**
	 * <pre>
	 * 요청 헤더를 검증한다
	 * </pre>
	 * @param headerMap
	 */
	void verifyHeaders(HttpHeaders header);

	/**
	 * <pre>
	 * 요청 시간을 검증한다.
	 * </pre>
	 * @param headerMap
	 */
	void verifyTimestamp(HttpHeaders header);

}
