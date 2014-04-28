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

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.runtime.acl.vo.AuthKey;
import com.skplanet.storeplatform.sac.runtime.acl.vo.Interface;
import com.skplanet.storeplatform.sac.runtime.acl.vo.System;
import com.skplanet.storeplatform.sac.runtime.acl.vo.Tenant;

/**
 * ACL 관련 DB 접근 서비스
 *
 * Updated on : 2014. 2. 17.
 * Updated by : 서대영, SK플래닛
 */
@Service
public class AclDataAccessServiceBatis implements AclDataAccessService {

    private final CommonDAO commonDAO;

    @Autowired
    public AclDataAccessServiceBatis(@Qualifier("sac") CommonDAO commonDAO) {
        this.commonDAO = commonDAO;
    }

    // @Cacheable(value = "sac:runtime:selectInterfaceById")
    @Override
    public Interface selectInterfaceById(String interfaceId) {
        return this.commonDAO.queryForObject("Interface.detail", interfaceId, Interface.class);
    }

    /* (non-Javadoc)
     * @see com.skplanet.storeplatform.sac.runtime.acl.service.common.AclDbAccessService#selectAuthInfoByAuthKey(java.lang.String)
     */
    // @Cacheable(value = "sac:runtime:selectAuthKey")
    @Override
    public AuthKey selectAuthKey(String authKey) {
        return this.commonDAO.queryForObject("AuthKey.selectAuthKey", authKey, AuthKey.class);
    }

    // @Cacheable(value = "sac:runtime:selectTenant")
    @Override
    public Tenant selectTenant(String tenantId) {
        return this.commonDAO.queryForObject("AclTenant.selectTenant", tenantId, Tenant.class);
    }

    // @Cacheable(value = "sac:runtime:selectSystem")
    @Override
    public System selectSystem(String systemId) {
        return this.commonDAO.queryForObject("System.selectSystem", systemId, System.class);
    }

    // @Cacheable(value = "sac:runtime:selectSystemByIp")
    @Override
    public System selectSystemByIp(System system) {
        return this.commonDAO.queryForObject("System.selectSystemByIp", system, System.class);
    }

    // @Cacheable(value = "sac:runtime:selectUsableInterface")
    @Override
    public String selectUsableInterface(String authKey, String interfaceId) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("authKey", authKey);
        param.put("interfaceId", interfaceId);

        return this.commonDAO.queryForObject("Interface.selectUsableInterface", param, String.class);
    }

    @CacheEvict(value = {
    		"sac:runtime:selectInterfaceById",
    		"sac:runtime:selectAuthKey",
    		"sac:runtime:selectTenant",
    		"sac:runtime:selectSystem",
    		"sac:runtime:selectSystemByIp",
    		"sac:runtime:selectUsableInterface"},
    		allEntries = true)
    @Override
    public void flushCache() {
    }

}
