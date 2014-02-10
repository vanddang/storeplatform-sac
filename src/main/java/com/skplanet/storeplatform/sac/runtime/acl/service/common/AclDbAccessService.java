/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.runtime.acl.service.common;

import com.skplanet.storeplatform.sac.runtime.acl.vo.Interface;
import com.skplanet.storeplatform.sac.runtime.acl.vo.Tenant;

public interface AclDbAccessService {

	/**
	 * <pre>
	 * InterfaceID로 Interface 정보 조회
	 * </pre>
	 * @param interfaceId
	 * 인터페이스 아이디
	 * @return
	 * 인터페이스 객체
	 */
	Interface select(String interfaceId);

	/**
	 * <pre>
	 * AuthKey로 Tenant 정보 조회
	 * </pre>
	 * @param authKey
	 * 인증키
	 * @return
	 * Tenant 객체
	 */
	Tenant selectByAuthKey(String authKey);

}
