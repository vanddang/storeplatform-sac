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

import com.skplanet.storeplatform.sac.client.display.vo.menu.MenuIntegrationListReq;
import com.skplanet.storeplatform.sac.client.display.vo.menu.MenuIntegrationListRes;
import com.skplanet.storeplatform.sac.client.product.vo.MenuDetail;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.cache.service.PanelCardInfoManager;
import com.skplanet.storeplatform.sac.display.menu.service.MenuIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 * MenuIntegrationController
 * </p>
 * Updated on : 2014. 10. 20 Updated by : 정희원, SK 플래닛.
 */
@Controller
public class MenuIntegrationController {

    @Autowired
    private MenuIntegrationService menuIntegrationService;

    @RequestMapping(value = "/display/menu/integration/list/v1", method = RequestMethod.GET)
    @ResponseBody
    public MenuIntegrationListRes listIntegration(MenuIntegrationListReq req, SacRequestHeader requestHeader) {
        String tenantId = requestHeader.getTenantHeader().getTenantId();
        String langCd = requestHeader.getTenantHeader().getLangCd();
        String systemId = requestHeader.getTenantHeader().getSystemId();
        String menuKey = req.getUpMenuId();

        List<MenuDetail> menuDetailList = menuIntegrationService.selectMenuIntegrationList(tenantId, systemId, langCd, menuKey);
//        if(CollectionUtils.isEmpty(menuDetailList))
//            throw new StorePlatformException("");

        return new MenuIntegrationListRes(menuDetailList);
    }
}
