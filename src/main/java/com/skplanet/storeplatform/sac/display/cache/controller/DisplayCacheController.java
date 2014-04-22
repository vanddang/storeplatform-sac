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

import com.skplanet.storeplatform.sac.display.cache.service.CacheEvictManager;
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
    private CacheEvictManager cacheEvictManager;

    @Autowired
    private TempProductInfoManager tempProductInfoManager;

    @RequestMapping(value = "/evict", method = RequestMethod.GET)
    public void evictAppMeta(@RequestParam(required = true) String prodId) {
        AppMetaParam param = new AppMetaParam();
        param.setChannelId(prodId);
        param.setTenantId("S01");
        param.setLangCd("ko");

        cacheEvictManager.evictAppMeta(param);
        // TODO 캐쉬에 족보(상품ID=상품유형)를 관리하고 있을까나?
    }

    @RequestMapping(value = "/evict/all", method = RequestMethod.GET)
    public void evictAllMeta(@RequestParam(required = true) String prodType) {
        if(prodType.equals("app")) {
            cacheEvictManager.evictAllAppMeta();
        }
        else if(prodType.equals("music")) {
            cacheEvictManager.evictAllMusicMeta();
        }
        else if(prodType.equals("shopping")) {
            cacheEvictManager.evictAllShoppingMeta();
        }
        else if(prodType.equals("freepass")) {
            cacheEvictManager.evictAllFreepassMeta();
        }
        else if(prodType.equals("vod")) {
            cacheEvictManager.evictAllVodMeta();
        }
        else if(prodType.equals("ebookcomic")) {
            cacheEvictManager.evictAllEbookComicMeta();
        }
        else if(prodType.equals("all")) {
            cacheEvictManager.evictAllAppMeta();
            cacheEvictManager.evictAllMusicMeta();
            cacheEvictManager.evictAllShoppingMeta();
            cacheEvictManager.evictAllFreepassMeta();
            cacheEvictManager.evictAllVodMeta();
            cacheEvictManager.evictAllEbookComicMeta();
        }

    }

    @RequestMapping(value = "/evict/oldApp", method = RequestMethod.GET)
    public void evictOldAppMeta() {
        this.tempProductInfoManager.evictAllOldAppMeta();
    }
}
