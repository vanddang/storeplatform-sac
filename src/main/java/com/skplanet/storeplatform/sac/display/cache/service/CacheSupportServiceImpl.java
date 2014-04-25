/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.cache.service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * CacheSupportServiceImpl
 * </p>
 * Updated on : 2014. 04. 25 Updated by : 정희원, SK 플래닛.
 */
@Service
public class CacheSupportServiceImpl implements CacheSupportService {
    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    @Override
    public List<String> getSupportDeviceList(String prodId) {
        return this.commonDAO.queryForList("CacheSupport.getSupportDeviceList", prodId, String.class);
    }

    @Override
    public List<String> getMenuList(String prodId) {
        return this.commonDAO.queryForList("CacheSupport.getMenuList", prodId, String.class);
    }
}
