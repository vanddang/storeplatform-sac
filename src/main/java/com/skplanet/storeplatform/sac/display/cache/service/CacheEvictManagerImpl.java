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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

/**
 * <p>
 * CacheEvictManagerImpl
 * </p>
 * Updated on : 2014. 04. 09 Updated by : 정희원, SK 플래닛.
 */
@Service
public class CacheEvictManagerImpl implements CacheEvictManager {

    @Override
    @CacheEvict(value = "sac:display:product:app", key = "#param.getCacheKey()")
    public void evictAppMeta(AppMetaParam param) {

    }

    @Override
    @CacheEvict(value = "sac:display:subcontent", key = "#param.getCacheKey()")
    public void evictSubContent(SubContentParam param) {

    }

    @Override
    @CacheEvict(value = "sac:display:menuinfo", key = "#param.getCacheKey()")
    public void evictMenuInfo(MenuInfoParam param) {

    }

    @Override
    @CacheEvict(value = "sac:display:product:music", key = "#param.getCacheKey()")
    public void evictMusicMeta(MusicMetaParam param) {

    }

    @Override
    @CacheEvict(value = "sac:display:product:music", key = "#param.getCacheKey()")
    public void evictVodMeta(VodMetaParam param) {

    }

    @Override
    @CacheEvict(value = "sac:display:product:ebookcomic", key = "#param.getCacheKey()")
    public void evictEbookComicMeta(EbookComicMetaParam param) {

    }

    @Override
    @CacheEvict(value = "sac:display:product:webtoon", key = "#param.getCacheKey()")
    public void evictWebtoonMeta(WebtoonMetaParam param) {

    }

    @Override
    @CacheEvict(value = "sac:display:product:shopping", key = "#param.getCacheKey()")
    public void evictShoppingMeta(ShoppingMetaParam param) {

    }

    @Override
    @CacheEvict(value = "sac:display:product:freepass", key = "#param.getCacheKey()")
    public void evictFreepassMeta(FreepassMetaParam param) {

    }

    @Override
    @CacheEvict(value = {"sac:display:product:app","sac:display:subcontent","sac:display:menuinfo"}, allEntries = true)
    public void evictAllAppMeta() { }

    @Override
    @CacheEvict(value = "sac:display:product:music", allEntries = true)
    public void evictAllMusicMeta() {

    }

    @Override
    @CacheEvict(value = "sac:display:product:vod", allEntries = true)
    public void evictAllVodMeta() {

    }

    @Override
    @CacheEvict(value = "sac:display:product:ebookcomic", allEntries = true)
    public void evictAllEbookComicMeta() {

    }

    @Override
    @CacheEvict(value = "sac:display:product:webtoon", allEntries = true)
    public void evictAllWebtoonMeta() {

    }

    @Override
    @CacheEvict(value = "sac:display:product:shopping", allEntries = true)
    public void evictAllShoppingMeta() {

    }

    @Override
    @CacheEvict(value = "sac:display:product:freepass", allEntries = true)
    public void evictAllFreepassMeta() {

    }

    @Override
    @CacheEvict(value = {"sac:display:tmembershipdcrate", "sac:display:tmembershipdcrate:v2"}, allEntries = true)
    public void evictAllTmembershipDcRate() {

    }
}
