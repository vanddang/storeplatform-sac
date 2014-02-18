/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.product.service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.display.product.vo.ParentAppInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * DESC
 * </p>
 * Updated on : 2014. 02. 17 Updated by : 정희원, SK 플래닛.
 */
@Service
public class ProductStatusServiceImpl implements ProductStatusService {

    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    @Override
    public ParentAppInfo selectParentInfo(String tenantId, String partProdId) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("tenantId", tenantId);
        param.put("partProdId", partProdId);

        return commonDAO.queryForObject("ProductStatus.selectParentProductStatus", param, ParentAppInfo.class);
    }
}
