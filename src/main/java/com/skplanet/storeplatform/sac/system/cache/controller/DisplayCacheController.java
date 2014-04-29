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
import com.skplanet.storeplatform.sac.display.common.ProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;

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
        if(prodId.equals("all")) {
            this.cacheEvictHelperComponent.evictProductMetaAll(ProductType.forName(prodType));
        }
        else {
            String[] prodIdList = prodId.split(" ");

            ProductType productType;
            try {
                productType = ProductType.forName(prodType);
                this.cacheEvictHelperComponent.evictProductMeta(productType, Arrays.asList(prodIdList));
            }
            catch(IllegalStateException e) {
                //throw new StorePlatformException("");
            }
        }
    }
}
