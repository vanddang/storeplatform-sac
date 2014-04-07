/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.cache.controller;

import com.skplanet.storeplatform.sac.display.cache.service.ProductInfoManager;
import com.skplanet.storeplatform.sac.display.cache.service.TempProductInfoManager;
import com.skplanet.storeplatform.sac.display.cache.vo.AppMetaParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 * DESC
 * </p>
 * Updated on : 2014. 04. 03 Updated by : 정희원, SK 플래닛.
 */
@Controller
@RequestMapping(value = "/display/cache")
public class DisplayCacheController {

    @Autowired
    private ProductInfoManager productInfoManager;

    @Autowired
    private TempProductInfoManager tempProductInfoManager;

    @RequestMapping(value = "/evict", method = RequestMethod.GET)
    public void evictAppMeta(@RequestParam(required = true) String channelId) {
        AppMetaParam param = new AppMetaParam();
        param.setChannelId(channelId);
        param.setTenantId("S01");
        param.setLangCd("ko");

        productInfoManager.evictAppMeta(param);
    }

    @RequestMapping(value = "/evict/oldApp", method = RequestMethod.GET)
    public void evictOldAppMeta() {
        this.tempProductInfoManager.evictAllOldAppMeta();
    }
}
