/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.runtime.acl.service.authorization;

import com.skplanet.storeplatform.sac.runtime.acl.vo.HttpHeaders;

/**
 *
 * 인가 서비스
 *
 * Updated on : 2014. 2. 10.
 * Updated by : 정희원, SK 플래닛
 */
public interface AuthorizeService {

	/**
	 * 1. Interface 유효성 확인
	 */
	void checkInterface(HttpHeaders httpHeaders);

	/**
	 * 2. Interface, Tenant 간 맵핑 확인
	 */
	void checkMapping(HttpHeaders httpHeaders);

}
