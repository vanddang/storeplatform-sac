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

import com.skplanet.storeplatform.sac.display.cache.service.CacheEvictManager;
import com.skplanet.storeplatform.sac.display.cache.service.CacheSupportService;
import com.skplanet.storeplatform.sac.display.cache.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
    private CacheEvictManager cacheEvictManager;

    @Autowired
    private CacheSupportService cacheSupportService;

    @Value("#{propertiesForSac['skp.common.service.language']}")
    private String SERVICE_LANG;

    private final String[] TENANT_LIST = new String[]{"S01"};

    @RequestMapping(value = "/evictProductMeta", method = RequestMethod.GET)
    public void evictAppMeta(@RequestParam(required = true) String prodType, @RequestParam(required = true) String prodId) {
        String[] prodIdList = prodId.split(" ");
        String[] langList = SERVICE_LANG.split(",");
        List<String> supportDeviceList = null;
        List<String> menuList = null;

        if(prodId.toLowerCase().equals("all")) {
            this.evictAllMeta(prodType);
            return;
        }

        for(String _prodId : prodIdList) {
            if(prodType.equals("app")) {
                supportDeviceList = cacheSupportService.getSupportDeviceList(_prodId);
                menuList = cacheSupportService.getMenuList(_prodId);
            }
            for(String tenant : TENANT_LIST) {
                for(String langCd : langList) {
                    if(prodType.equals("app")) {
                        this.cacheEvictManager.evictAppMeta(new AppMetaParam(_prodId, langCd, tenant));

                        if(supportDeviceList != null) {
                            for (String deviceModel : supportDeviceList) {
                                this.cacheEvictManager.evictSubContent(new SubContentParam(_prodId, deviceModel));
                            }
                        }

                        if (menuList != null) {
                            for (String menuId : menuList) {
                                this.cacheEvictManager.evictMenuInfo(new MenuInfoParam(_prodId, menuId, langCd));
                            }
                        }
                    }
                    else if(prodType.equals("music")) {
                        this.cacheEvictManager.evictMusicMeta(new MusicMetaParam(_prodId, langCd, tenant));
                    }
                    else if(prodType.equals("shopping")) {
                        this.cacheEvictManager.evictShoppingMeta(new ShoppingMetaParam(_prodId, langCd, tenant));
                    }
                    else if(prodType.equals("freepass")) {
                        this.cacheEvictManager.evictFreepassMeta(new FreepassMetaParam(_prodId, langCd, tenant));
                    }
                    else if(prodType.equals("vod")) {
                        this.cacheEvictManager.evictVodMeta(new VodMetaParam(_prodId, langCd, tenant));
                    }
                    else if(prodType.equals("ebookcomic")) {
                        this.cacheEvictManager.evictEbookComicMeta(new EbookComicMetaParam(_prodId, langCd, tenant));
                    }
                    else if(prodType.equals("webtoon")) {
                        this.cacheEvictManager.evictWebtoonMeta(new WebtoonMetaParam(_prodId, langCd, tenant));
                    }
                }
            }
        }
    }

    private void evictAllMeta(@RequestParam(required = true) String prodType) {
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
        else if(prodType.equals("webtoon")) {
            cacheEvictManager.evictAllWebtoonMeta();
        }
        else if(prodType.equals("all")) {
            cacheEvictManager.evictAllAppMeta();
            cacheEvictManager.evictAllMusicMeta();
            cacheEvictManager.evictAllShoppingMeta();
            cacheEvictManager.evictAllFreepassMeta();
            cacheEvictManager.evictAllVodMeta();
            cacheEvictManager.evictAllEbookComicMeta();
            cacheEvictManager.evictAllWebtoonMeta();
        }

    }

}
