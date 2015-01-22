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
import com.skplanet.storeplatform.sac.display.common.ProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.skplanet.storeplatform.sac.display.common.ProductType.*;

/**
 * <p>
 * CacheEvictHelperComponentImpl
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

    // FIXME 서비스에 추가될 테넌트 추가
    private final String[] TENANT_LIST = new String[]{"S01","S02","S03"};

    @Override
    public void evictProductMeta(ProductType prodType, List<String> prodIdList) {
        String[] langList = SERVICE_LANG.split(",");
        List<String> supportDeviceList = null;
        List<String> menuList = null;

        if(prodType == null)
            throw new IllegalStateException("prodType cannot be null.");

        for(String _prodId : prodIdList) {
            if(prodType == App) {
                supportDeviceList = cacheSupportService.getSupportDeviceList(_prodId);
                menuList = cacheSupportService.getMenuList(_prodId);
            }
            for(String tenant : TENANT_LIST) {
                for(String langCd : langList) {
                    if(prodType == App) {
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
                    else if(prodType == Music) {
                        this.cacheEvictManager.evictMusicMeta(new MusicMetaParam(tenant, langCd, _prodId));
                    }
                    else if(prodType == Shopping) {
                        this.cacheEvictManager.evictShoppingMeta(new ShoppingMetaParam(_prodId, langCd, tenant));
                    }
                    else if(prodType == Freepass) {
                        this.cacheEvictManager.evictFreepassMeta(new FreepassMetaParam(_prodId, langCd, tenant));
                    }
                    else if(prodType == Vod) {
                        this.cacheEvictManager.evictVodMeta(new VodMetaParam(_prodId, langCd, tenant));
                    }
                    else if(prodType == EbookComic) {
                        this.cacheEvictManager.evictEbookComicMeta(new EbookComicMetaParam(_prodId, langCd, tenant));
                    }
                    else if(prodType == Webtoon) {
                        this.cacheEvictManager.evictWebtoonMeta(new WebtoonMetaParam(_prodId, langCd, tenant));
                    }
                }
            }
        }
    }

    @Override
    public void evictProductMeta(ProductType prodType, String prodId) {
        this.evictProductMeta(prodType, Arrays.asList(prodId));
    }

    @Override
    public void evictProductMetaAll(ProductType prodType) {

        if(prodType == null)
            throw new IllegalStateException("prodType cannot be null.");

        if(prodType == App) {
            cacheEvictManager.evictAllAppMeta();
        }
        else if(prodType == Music) {
            cacheEvictManager.evictAllMusicMeta();
        }
        else if(prodType == Shopping) {
            cacheEvictManager.evictAllShoppingMeta();
        }
        else if(prodType == Freepass) {
            cacheEvictManager.evictAllFreepassMeta();
        }
        else if(prodType == Vod) {
            cacheEvictManager.evictAllVodMeta();
        }
        else if(prodType == EbookComic) {
            cacheEvictManager.evictAllEbookComicMeta();
        }
        else if(prodType == Webtoon) {
            cacheEvictManager.evictAllWebtoonMeta();
        }
    }

    @Override
    public void evictProductMetaByBrand(String brandId) {
        List<String> list = cacheSupportService.getCatalogListByBrand(brandId);
        this.evictProductMeta(Shopping, list);
    }
}
