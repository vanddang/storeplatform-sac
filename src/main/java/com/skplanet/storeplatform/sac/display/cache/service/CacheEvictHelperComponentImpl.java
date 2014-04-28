/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.cache.service;

import com.skplanet.storeplatform.sac.display.cache.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * DESC
 * </p>
 * Updated on : 2014. 04. 28 Updated by : 정희원, SK 플래닛.
 */
@Component
public class CacheEvictHelperComponentImpl implements CacheEvictHelperComponent {

    @Autowired
    private CacheEvictManager cacheEvictManager;

    @Autowired
    private CacheSupportService cacheSupportService;

    @Value("#{propertiesForSac['skp.common.service.language']}")
    private String SERVICE_LANG;

    private final String[] TENANT_LIST = new String[]{"S01"};

    @Override
    public void evictProductMeta(String prodType, List<String> prodIdList) {
        String[] langList = SERVICE_LANG.split(",");
        List<String> supportDeviceList = null;
        List<String> menuList = null;

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

    @Override
    public void evictProductMeta(String prodType, String prodId) {
        this.evictProductMeta(prodType, Arrays.asList(prodId));
    }

    @Override
    public void evictProductMetaAll(String prodType) {

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
