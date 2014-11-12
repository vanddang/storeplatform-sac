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
import com.skplanet.storeplatform.sac.display.common.service.ProductExtraInfoService;
import com.skplanet.storeplatform.sac.display.localsci.sci.service.IapProductInfoService;
import com.skplanet.storeplatform.sac.display.localsci.sci.vo.IapProductInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import static com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants.*;

import java.util.Map;


/**
 * <p>
 * IapProductInfoSCIController
 * </p>
 * Updated on : 2014. 04. 16 Updated by : 정희원, SK 플래닛.
 */
@LocalSCI
public class IapProductInfoSCIController implements IapProductInfoSCI {

    @Autowired
    private IapProductInfoService iapProductInfoService;

    @Autowired
    private ProductExtraInfoService extraInfoService;

    @Override
    public IapProductInfoRes getIapProductInfo(@Validated IapProductInfoReq req) {
        IapProductInfo iapProductInfo = iapProductInfoService.getIapProductInfo(req.getPartProdId());
        if (iapProductInfo == null)
            return null;

        IapProductInfoRes res = new IapProductInfoRes();

        /*
         * SAP 상품 식별자 매핑 정보 조회
         * SAP Phase 1 기간에만 유지되는 로직임
         */
        // ----- Start -----
        String parentProdId = iapProductInfoService.getTenantProdId(req.getTenantId(), iapProductInfo.getParentProdId());
        if(parentProdId == null)
            return null;
        // -----  End  -----

        res.setParentProdId(parentProdId);
        res.setPartProdId(iapProductInfo.getPartProdId());
        res.setFullAid(iapProductInfo.getFullAid());
        res.setHasFullProdYn(iapProductInfo.getHasFullProdYn());
        res.setMenuId(iapProductInfo.getMenuId());
        res.setProdCase(iapProductInfo.getProdCase());
        res.setProdKind(iapProductInfo.getProdKind());
        res.setFullProdId(iapProductInfo.getFullProdId());
        res.setUsePeriod(iapProductInfo.getUsePeriod());

        Map infoMap = extraInfoService.getInfoAsJSON(res.getPartProdId(), EXINFO_S2S_INFO);
        if (infoMap != null) {
            if(infoMap.containsKey("s2sMonthlyFreepassYn"))
                res.setS2sMonthlyFreepassYn("" + infoMap.get("s2sMonthlyFreepassYn"));
            if(infoMap.containsKey("postbackUrl"))
                res.setPostbackUrl("" + infoMap.get("postbackUrl"));
        }

        return res;
    }
}
