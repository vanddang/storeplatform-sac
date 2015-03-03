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

/**
 * <p>
 * CacheEvictManager
 * </p>
 * Updated on : 2014. 04. 09 Updated by : 정희원, SK 플래닛.
 */
public interface CacheEvictManager {

    /**
     * 캐싱된 메타정보를 Evict한다.
     * @param param
     */
    void evictAppMeta(AppMetaParam param);

    void evictSubContent(SubContentParam param);

    void evictMenuInfo(MenuInfoParam param);

    void evictMusicMeta(MusicMetaParam param);

    void evictVodMeta(VodMetaParam param);

    void evictEbookComicMeta(EbookComicMetaParam param);

    void evictWebtoonMeta(WebtoonMetaParam param);

    void evictShoppingMeta(ShoppingMetaParam param);

    void evictFreepassMeta(FreepassMetaParam param);

    void evictProductStats(ProductStatsParam param);

    /**
     * 앱 메타 캐쉬 정보를 모두 Evict한다.
     */
    void evictAllAppMeta();

    void evictAllMusicMeta();

    void evictAllVodMeta();

    void evictAllEbookComicMeta();

    void evictAllWebtoonMeta();

    void evictAllShoppingMeta();

    void evictAllFreepassMeta();

    void evictAllTmembershipDcRate();

    void evictAllUpdateProduct();

    void evictAllProductStats();

    /**
     * DeviceProfile 캐쉬 정보를 모두 Evict 한다.
     *
     */
    void evictAllDeviceProfile();
}
