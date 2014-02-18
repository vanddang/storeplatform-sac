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

import com.skplanet.storeplatform.sac.runtime.acl.vo.AuthKey;
import com.skplanet.storeplatform.sac.runtime.acl.vo.Interface;
import com.skplanet.storeplatform.sac.runtime.acl.vo.Tenant;

/**
*
* ACL 관련 DB 접근 서비스
*
* Updated on : 2014. 2. 5.
* Updated by : 서대영, SK플래닛
*/
public interface AclDataAccessService {

	/**
	 * <pre>
	 * InterfaceID로 Interface 정보 조회
	 * </pre>
	 * @param interfaceId
	 * 인터페이스 아이디
	 * @return
	 * 인터페이스 객체
	 */
	Interface selectInterfaceById(String interfaceId);

	/**
	 * <pre>
	 * AuthKey로 Tenant 정보 조회
	 * </pre>
	 * @param authKey
	 * 인증키
	 * @return
	 * Tenant 객체
	 */
	Tenant selectTenantByAuthKey(String authKey);

	/**
	 * <pre>
	 * Header AuthKey 로 DB AuthKey 정보 조회
	 * </pre>
	 * @param authKey
	 * 		인증키
	 * @return
	 * 		AuthKey
	 */
	AuthKey selectAuthKey(String authKey);

    /**
     * <pre>
     * 테넌트가 해당 인터페이스를 사용 가능한지 여부 조회
     * </pre>
     *
     * @param authKey 테넌트ID
     * @param interfaceId 인터페이스ID
     * @return 인터페이스 상태. null인 경우 사용 불가.
     */
    String selectUsableInterface(String authKey, String interfaceId);

}
