/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.other.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.display.other.vo.PartProduct;

/**
 * <p>
 * OtherPartProductServiceImpl
 * </p>
 * Updated on : 2014. 03. 25 Updated by : 정희원, SK 플래닛.
 */
@Service
public class OtherPartProductServiceImpl implements OtherPartProductService {

    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    @Override
    public List<PartProduct> getPartProductList(String aid, String partProdId, String tenantId, String langCd) {
        Map<String, String> req = new HashMap<String, String>();
        req.put("aid", aid);
        req.put("partProdId", partProdId);
        req.put("tenantId", tenantId);
        req.put("langCd", langCd);
        return this.commonDAO.queryForList("OtherPartProduct.getPartProductList", req, PartProduct.class);
    }
}
