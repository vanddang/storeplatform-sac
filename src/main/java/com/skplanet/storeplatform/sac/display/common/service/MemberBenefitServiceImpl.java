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
import com.skplanet.storeplatform.sac.display.common.vo.MileageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * MemberBenefitServiceImpl
 * </p>
 * Updated on : 2014. 07. 25 Updated by : 정희원, SK 플래닛.
 */
@Service
public class MemberBenefitServiceImpl implements MemberBenefitService {

    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    @Override
    public MileageInfo getMileageInfo(String tenantId, String topMenuId, String chnlId) {
        Map<String, Object> req = new HashMap<String, Object>();
        req.put("tenantId", tenantId);
        req.put("topMenuId", topMenuId);
        req.put("chnlId", chnlId);

        MileageInfo mileageInfo = commonDAO.queryForObject("MemberBenefit.getMileageInfo", req, MileageInfo.class);

        if(mileageInfo != null)
            return mileageInfo;
        else
            return new MileageInfo();
    }
}
