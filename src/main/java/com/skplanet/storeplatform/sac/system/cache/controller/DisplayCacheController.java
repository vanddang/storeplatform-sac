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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.cache.process.GlobalCacheProcessor;
import com.skplanet.storeplatform.sac.display.cache.service.CacheEvictHelperComponent;
import com.skplanet.storeplatform.sac.display.common.ProductType;

/**
 * <p>
 * DisplayCacheController
 * </p>
 * Created on 2014. 04. 03 by 정희원, SK 플래닛.
 * Updated on 2014. 05. 20 by 서대영, SK 플래닛. : Method 추가 (disableCache(), isUseCache())
 */
@Controller
@RequestMapping(value = "/system/cache")
public class DisplayCacheController {

    @Autowired
    private CacheEvictHelperComponent cacheEvictHelperComponent;

    @Autowired
    private ApplicationContext applicationContext;

    @RequestMapping(value = "/evictProductMeta", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> evictAppMeta(@RequestParam(required = true) String prodType, @RequestParam(required = true) String prodId) {
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

        Map<String, String> res = new HashMap<String, String>();
        res.put("prodType", prodType);
        res.put("prodId", prodId);
        return res;
    }

    @RequestMapping(value = "/enableCache", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> enableCache() {
        GlobalCacheProcessor globalCacheProcessor = (GlobalCacheProcessor) this.applicationContext.getBean("globalCacheProcessor");
        globalCacheProcessor.setUseCache(true);

        Map<String, String> res = new HashMap<String, String>();
        res.put("message", "Global cache enabled.");

        return res;
    }

    @RequestMapping(value = "/disableCache", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> disableCache() {
        GlobalCacheProcessor globalCacheProcessor = (GlobalCacheProcessor) this.applicationContext.getBean("globalCacheProcessor");
        globalCacheProcessor.setUseCache(false);

        Map<String, String> res = new HashMap<String, String>();
        res.put("message", "Global cache disabled.");

        return res;
    }

    @RequestMapping(value = "/isUseCache", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Boolean> isUseCache() {
    	Map<String, Boolean> res = new HashMap<String, Boolean>();
    	GlobalCacheProcessor globalCacheProcessor = (GlobalCacheProcessor) this.applicationContext.getBean("globalCacheProcessor");

        if (globalCacheProcessor.isUseCache()) {
        	res.put("isUseCache", true);
        } else {
        	res.put("isUseCache", false);
        }

        return res;
    }


}
