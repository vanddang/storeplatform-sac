/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.meta.service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.display.meta.vo.CidPrice;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * ProductSubInfoManagerImpl
 * </p>
 * Updated on : 2015. 01. 02 Updated by : 정희원, SK 플래닛.
 */
@Service
public class ProductSubInfoManagerImpl implements ProductSubInfoManager {

    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    @Override
    public CidPrice getCidPrice(String tenantId, String cid) {
        Map<String, Object> req = new HashMap<String, Object>();
        req.put("tenantId", tenantId);
        req.put("cid", cid);
        List<CidPrice.CidPriceRaw> rawList = commonDAO.queryForList("ProductSubInfo.getCidPrice", req, CidPrice.CidPriceRaw.class);
        if(CollectionUtils.isEmpty(rawList))
            return null;

        CidPrice cidPrice = new CidPrice();
        for (CidPrice.CidPriceRaw prcRaw : rawList) {
            if(DisplayConstants.DP_USE_PERIOD_UNIT_CD_NONE.equals(prcRaw.getUsePeriodUnitCd())) {
                // 소장
                cidPrice.setProdId(prcRaw.getProdId());
                cidPrice.setProdAmt(prcRaw.getProdAmt());
            }
            else {
                // 대여
                cidPrice.setRentProdId(prcRaw.getProdId());
                cidPrice.setRendPeriodUnitCd(prcRaw.getUsePeriodUnitCd());
                cidPrice.setRentPeriod(prcRaw.getUsePeriod());
                cidPrice.setRentProdAmt(prcRaw.getProdAmt());
            }
        }
        return cidPrice;
    }
}
