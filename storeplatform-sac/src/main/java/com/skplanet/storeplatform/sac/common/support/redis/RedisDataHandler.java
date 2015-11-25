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

import com.skplanet.plandasj.Plandasj;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;

/**
 * <p>
 * RedisDataHandler - 데이터를 처리하는 방법
 * </p>
 * Updated on : 2015. 11. 03 Updated by : 정희원, SK 플래닛.
 */
interface RedisDataHandler<K, V> {

    /**
     * 응답 데이터 유형을 조회한다.
     * 초기화시 MessagePack에 처리할 데이터 유형을 등록하는데 쓰인다
     * @return
     */
    Class getResultTypeClass();

    /**
     * Redis에서 데이터를 조회하는 로직을 구현한다.
     * @param key 식별자
     * @param redis redis client
     * @return 응답값
     */
    V load(K key, Plandasj redis);

    /**
     * Redis에 데이터를 적재하는 로직을 구현한다.
     *
     * @param key 식별자. 적재시 키값 생성을 위해 참조
     * @param value 값
     * @param redis redis client
     */
    void store(K key, V value, Plandasj redis);

    /**
     * 파라메터에 맞는 객체를 생성한다. DB로 데이터를 조회하여 객체를 만들어 응답한다.
     * @param key 식별자
     * @param commonDAO sac common DAO
     * @return 응답값
     */
    V makeValue(K key, CommonDAO commonDAO);

    /**
     * 데이터를 제거하는 로직을 구현한다.
     * @param key
     * @param redis
     */
    void evict(K key, Plandasj redis);

    /**
     * 값을 비교하여 일치하는 데이터들을 제거한다.
     * @param filterStr
     * @param redis
     */
    void evictWithFilter(String filterStr, Plandasj redis);
}
