/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.common.support.redis;

/**
 * <p>
 * RedisDataService
 * </p>
 * Updated on : 2015. 11. 03 Updated by : 정희원, SK 플래닛.
 */
public interface RedisDataService {
    /**
     * 데이터를 조회합니다.
     * @param <T>
     * @param t
     * @param key
     * @return
     */
    <T> T get(Class<T> t, Object key);

    /**
     * 데이터를 제거합니다.
     * @param t
     * @param key
     */
    <T> void evict(Class<T> t, Object key);

    /**
     * 데이터를 적재합니다.
     * @param <T>
     * @param t
     * @param key
     */
    <T> T load(Class<T> t, Object key);

}
