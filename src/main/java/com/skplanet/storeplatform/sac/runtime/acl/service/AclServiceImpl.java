/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.runtime.acl.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.runtime.acl.vo.AclAuthKeyInfo;
import com.skplanet.storeplatform.sac.runtime.acl.vo.AclAuthInfo;

/**
 * 
 * Calss 설명
 * 
 * Updated on : 2013. 11. 26. Updated by : 김현일, 인크로스.
 */
@Service
@Transactional
public class AclServiceImpl implements AclService {

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.integration.v2.acl.service.AclService#searchAclAuthKey(java.util.Map)
	 */
	@Override
	public AclAuthKeyInfo searchAclAuthKey(Map<String, String> params) {
		return this.commonDAO.queryForObject("Acl.searchAclAuthKey", params, AclAuthKeyInfo.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.integration.v2.acl.service.AclService#searchAclAuth(java.util.Map)
	 */
	@Override
	public AclAuthInfo searchAclAuth(Map<String, String> params) {
		return this.commonDAO.queryForObject("Acl.searchAclAuth", params, AclAuthInfo.class);
	}
}
