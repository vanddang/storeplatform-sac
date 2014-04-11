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

import com.skplanet.storeplatform.sac.display.cache.vo.AppMetaParam;
import com.skplanet.storeplatform.sac.display.cache.vo.MusicMetaParam;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

/**
 * <p>
 * DESC
 * </p>
 * Updated on : 2014. 04. 09 Updated by : 정희원, SK 플래닛.
 */
@Service
public class CacheEvictManagerImpl implements CacheEvictManager {

    @Override
    @CacheEvict(value = "sac:display:product:app", key = "#param.getCacheKey()")
    public void evictAppMeta(AppMetaParam param) {
        // TODO subContent, menuInfo도 함께
    }

    @Override
    @CacheEvict(value = {"sac:display:product:app","sac:display:subcontent","sac:display:menuinfo"}, allEntries = true)
    public void evictAllAppMeta() { }

    @Override
    @CacheEvict(value = "sac:display:product:music", key = "#param.getCacheKey()")
    public void evictMusicMeta(MusicMetaParam param) {

    }

    @Override
    @CacheEvict(value = "sac:display:product:music", allEntries = true)
    public void evictAllMusicMeta() {

    }
}
