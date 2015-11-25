/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.common.service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * ProductExtraInfoServiceImpl
 * </p>
 * Updated on : 2014. 10. 07 Updated by : 정희원, SK 플래닛.
 */
@Service
public class ProductExtraInfoServiceImpl implements ProductExtraInfoService {

    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    @Override
    public String getInfo(String prodId, String infoClsfCd) {
        Map<String, Object> req = new HashMap<String, Object>();
        req.put("prodId", prodId);
        req.put("infoClsfCd", infoClsfCd);

        return commonDAO.queryForObject("ProductExtraInfoService.getInfo", req, String.class);
    }

    @Override
    public Map<String, Object> getInfoAsJSON(String prodId, String infoClsfCd) {
        String info = getInfo(prodId, infoClsfCd);
        if(info == null)
            return null;

        Map value;
        try {
            value = (Map)(new JSONParser().parse(info));
        }
        catch (ParseException e) {
            value = new HashMap(1);
            value.put("_info", info);
        }

        return value;
    }
}
