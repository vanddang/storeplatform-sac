/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.menu.controller;

import com.skplanet.storeplatform.sac.client.display.vo.menu.IntegratedMenuListRes;
import com.skplanet.storeplatform.sac.client.display.vo.menu.MenuIntegrationListReq;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.menu.service.IntegratedMenuService;
import com.skplanet.storeplatform.sac.display.menu.vo.IntegratedMenuList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * IntegratedMenuController
 * </p>
 * Updated on : 2014. 10. 20 Updated by : 정희원, SK 플래닛.
 */
@Controller
public class IntegratedMenuController {

    @Autowired
    private IntegratedMenuService integratedMenuService;

    @RequestMapping(value = "/display/menu/integration/list/v1", method = RequestMethod.GET)
    @ResponseBody
    public IntegratedMenuListRes listIntegratedMenu(MenuIntegrationListReq req, SacRequestHeader requestHeader) {
        String tenantId = requestHeader.getTenantHeader().getTenantId();
        String langCd = requestHeader.getTenantHeader().getLangCd();
        String systemId = requestHeader.getTenantHeader().getSystemId();
        String menuKey = req.getUpMenuId();

        IntegratedMenuList integratedMenuList = integratedMenuService.getIntegratedMenuList(tenantId, systemId, langCd, menuKey);

        return new IntegratedMenuListRes(integratedMenuList.getFeaturedMenuList(), integratedMenuList.getCategoryMenuList());
    }
}
