/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.localsci.sci.controller;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.IapProductInfoSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.IapProductInfoReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.IapProductInfoRes;
import com.skplanet.storeplatform.sac.display.localsci.sci.service.IapProductInfoService;
import com.skplanet.storeplatform.sac.display.localsci.sci.vo.IapProductInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;


/**
 * <p>
 * DESC
 * </p>
 * Updated on : 2014. 04. 16 Updated by : 정희원, SK 플래닛.
 */
@LocalSCI
public class IapProductInfoSCIController implements IapProductInfoSCI {

    @Autowired
    private IapProductInfoService iapProductInfoService;

    @Override
    public IapProductInfoRes getIapProductInfo(@Validated IapProductInfoReq req) {
        IapProductInfo iapProductInfo = iapProductInfoService.getIapProductInfo(req.getPartProdId());
        IapProductInfoRes res = null;
        if (iapProductInfo != null) {
            res = new IapProductInfoRes();
            res.setParentProdId(iapProductInfo.getParentProdId());
            res.setPartProdId(iapProductInfo.getPartProdId());
            res.setFullAid(iapProductInfo.getFullAid());
            res.setHasFullProdYn(iapProductInfo.getHasFullProdYn());
            res.setPartProductType(iapProductInfo.getPartProductType());
            res.setMenuId(iapProductInfo.getMenuId());
        }

        return res;
    }
}
