/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.product.controller;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.display.vo.product.ParentStatusRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.product.service.ProductStatusService;
import com.skplanet.storeplatform.sac.display.product.vo.ParentAppInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * DESC
 * </p>
 * Updated on : 2014. 02. 18 Updated by : 정희원, SK 플래닛.
 */
@Controller
@RequestMapping("/display")
public class ProductStatusController {

    @Autowired
    private ProductStatusService productStatusService;

    @RequestMapping(value = "/product/parentStatus/get/v1", method = RequestMethod.GET)
    @ResponseBody
    public ParentStatusRes parentStatus(@RequestParam String partProdId, SacRequestHeader header) {
        ParentAppInfo parentAppInfo = productStatusService.selectParentInfo(header.getTenantHeader().getTenantId(), partProdId);

        if(parentAppInfo == null)
            throw new StorePlatformException("SAC_DSP_0009");

        ParentStatusRes parentStatusRes = new ParentStatusRes();
        parentStatusRes.setParentStatus(parentAppInfo.getParentStatus());
        parentStatusRes.setParentProdId(parentAppInfo.getParentProdId());

        return parentStatusRes;
    }

}
