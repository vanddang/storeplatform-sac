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

import com.skplanet.plandasj.Plandasj;
import com.skplanet.spring.data.plandasj.PlandasjConnectionFactory;
import com.skplanet.storeplatform.sac.common.util.ServicePropertyManager;
import com.skplanet.storeplatform.sac.display.cache.SacRedisKeys;
import com.skplanet.storeplatform.sac.display.cache.vo.*;
import com.skplanet.storeplatform.sac.display.common.ProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

    @Autowired(required = false)
    private PlandasjConnectionFactory connectionFactory;

    @Autowired
    private CachedExtraInfoManager cachedExtraInfoManager;

    @Value("#{propertiesForSac['skp.common.service.language']}")
    private String SERVICE_LANG;

    @Override
    public List<String> evictProductMeta(ProductType prodType, List<String> prodIdList) {
        String[] langList = SERVICE_LANG.split(",");
        List<String> supportDeviceList = null;
        List<String> menuList = null;
        List<String> executedProd = new ArrayList<String>(prodIdList.size());
        Plandasj c = connectionFactory.getConnectionPool().getClient();

        for(String _prodId : prodIdList) {

            ProductBaseInfo productBaseInfo = cachedExtraInfoManager.getProductBaseInfo(new GetProductBaseInfoParam(_prodId));
            if(productBaseInfo == null)
                continue;

            ProductType prodTp = productBaseInfo.getProductType();

            if(prodTp == App) {
                supportDeviceList = cacheSupportService.getSupportDeviceList(_prodId);
                menuList = cacheSupportService.getMenuList(_prodId);

                cachedExtraInfoManager.evictPkgsInProd(_prodId);
                c.del(SacRedisKeys.pkgsInProd(_prodId));
                c.del(SacRedisKeys.sprtdev(_prodId));
            }

            // del productBaseInfo
            c.del(SacRedisKeys.prodBase(_prodId));

            for(String tenant : ServicePropertyManager.getSupportTenantList()) {
                for(String langCd : langList) {
                    if(prodTp == App) {
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
                    else if(prodTp == Music) {
                        this.cacheEvictManager.evictMusicMeta(new MusicMetaParam(tenant, langCd, _prodId));
                    }
                    else if(prodTp == Shopping) {
                        this.cacheEvictManager.evictShoppingMeta(new ShoppingMetaParam(_prodId, langCd, tenant));
                    }
                    else if(prodTp == Freepass) {
                        this.cacheEvictManager.evictFreepassMeta(new FreepassMetaParam(_prodId, langCd, tenant));
                    }
                    else if(prodTp == Vod || prodTp == VodTv || prodTp == VodMovie) {
                        this.cacheEvictManager.evictVodMeta(new VodMetaParam(_prodId, langCd, tenant));
                    }
                    else if(prodTp == EbookComic || prodTp == Ebook || prodTp == Comic) {
                        this.cacheEvictManager.evictEbookComicMeta(new EbookComicMetaParam(_prodId, langCd, tenant));
                    }
                    else if(prodTp == Webtoon) {
                        this.cacheEvictManager.evictWebtoonMeta(new WebtoonMetaParam(_prodId, langCd, tenant));
                    }
                    else if(prodTp == Voucher) {
                        this.cacheEvictManager.evictVoucherMeta(new VoucherMetaParam(_prodId, langCd, tenant));
                    }
                }
            }

            this.cacheEvictManager.evictProductStats(new ProductStatsParam(_prodId));

            executedProd.add(_prodId);
        }

        return executedProd;
    }

    @Override
    public void evictProductMeta(ProductType prodType, String prodId) {
        this.evictProductMeta(prodType, Arrays.asList(prodId));
    }

    @Override
    public void evictProductMetaAll(ProductType prodType) {

        if(prodType == null)
            throw new IllegalStateException("prodType cannot be null.");

        /* 전체 Evict시 동일한 종류의 캐시 이름을 가지는 Ebook, Comic, VodTv, VodMovie는 처리하지 않음. */
        // FIXME all evict 처리시 productBasicInfo 의 evict가 처리되어야 함
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
        else if(prodType == Voucher) {
            cacheEvictManager.evictAllVoucherMeta();
        }
    }

    @Override
    public void evictProductMetaByBrand(String brandId) {
        List<String> list = cacheSupportService.getCatalogListByBrand(brandId);
        this.evictProductMeta(Shopping, list);
    }
}
