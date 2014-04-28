/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.system.cache.controller;

import com.skplanet.storeplatform.sac.display.cache.service.CacheEvictHelperComponent;
import com.skplanet.storeplatform.sac.display.cache.service.CacheEvictManager;
import com.skplanet.storeplatform.sac.display.cache.service.CacheSupportService;
import com.skplanet.storeplatform.sac.display.cache.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * DisplayCacheController
 * </p>
 * Updated on : 2014. 04. 03 Updated by : 정희원, SK 플래닛.
 */
@Controller
@RequestMapping(value = "/system/cache")
public class DisplayCacheController {

    @Autowired
    private CacheEvictHelperComponent cacheEvictHelperComponent;

    @RequestMapping(value = "/evictProductMeta", method = RequestMethod.GET)
    public void evictAppMeta(@RequestParam(required = true) String prodType, @RequestParam(required = true) String prodId) {
        String[] prodIdList = prodId.split(" ");

        if(prodId.toLowerCase().equals("all")) {
            this.cacheEvictHelperComponent.evictProductMetaAll(prodType);
            return;
        }

        this.cacheEvictHelperComponent.evictProductMeta(prodType, Arrays.asList(prodIdList));

    }


}
