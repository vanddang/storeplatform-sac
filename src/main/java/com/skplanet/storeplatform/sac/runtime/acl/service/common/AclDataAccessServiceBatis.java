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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.runtime.acl.vo.Interface;
import com.skplanet.storeplatform.sac.runtime.acl.vo.Tenant;

/**
*
* ACL 관련 DB 접근 서비스
*
* Updated on : 2014. 2. 17.
* Updated by : 서대영, SK플래닛
*/
@Service
@Transactional
public class AclDataAccessServiceBatis implements AclDataAccessService {

	private final CommonDAO commonDAO;

	@Autowired
	public AclDataAccessServiceBatis(@Qualifier("sac") CommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}

	@Override
	public Interface selectInterfaceById(String interfaceId) {
		return this.commonDAO.queryForObject("Interface.detail", interfaceId, Interface.class);
	}

	@Override
	public Tenant selectTenantByAuthKey(String authKey) {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public String selectInterfaceStatus(String tenantId, String interfaceId) {
        // TODO Implementation.
        return null;
    }
}
