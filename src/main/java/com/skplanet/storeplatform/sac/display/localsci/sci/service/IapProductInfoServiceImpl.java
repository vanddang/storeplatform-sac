/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.localsci.sci.service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.display.localsci.sci.vo.IapProductInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * IapProductInfoServiceImpl
 * </p>
 * Updated on : 2014. 04. 16 Updated by : 정희원, SK 플래닛.
 */
@Service
public class IapProductInfoServiceImpl implements IapProductInfoService {

    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    private static final String TENANT_TSTORE = "S01";

    @Override
    public IapProductInfo getIapProductInfo(String partProdId) {
        Map<String, Object> req = new HashMap<String, Object>();
        req.put("partProdId", partProdId);
        return commonDAO.queryForObject("IapProductInfo.getIapProductInfo", req, IapProductInfo.class);
    }

    @Override
    public String getTenantProdId(String tenantId, String prodId) {

        Map map = commonDAO.queryForObject("IapProductInfo.getSapProdMapg", prodId, Map.class);

        if (map == null) {
            if(TENANT_TSTORE.equals(tenantId))
                return prodId;
            else
                return null;
        }

        return (String)map.get(tenantId + "_PROD_ID");
    }
}
