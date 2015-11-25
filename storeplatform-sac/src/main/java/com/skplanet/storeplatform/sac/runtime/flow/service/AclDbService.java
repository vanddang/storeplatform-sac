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

import java.util.Map;

import com.skplanet.storeplatform.sac.runtime.acl.vo.AclAuthKeyInfo;
import com.skplanet.storeplatform.sac.runtime.acl.vo.AclAuthInfo;

/**
 * 
 * Calss 설명
 * 
 * Updated on : 2013. 11. 26. Updated by : 김현일, 인크로스.
 */
public interface AclDbService {

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param params
	 *            params
	 * @return AclAuthKeyVO
	 */
	public AclAuthKeyInfo searchAclAuthKey(Map<String, String> params);

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param params
	 *            params
	 * @return AclAuthVO
	 */
	public AclAuthInfo searchAclAuth(Map<String, String> params);

}
