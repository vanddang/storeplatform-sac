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

/**
 * <p>
 * SacRedisSimpleGetSetHandler
 * </p>
 * Updated on : 2015. 05. 13 Updated by : 정희원, SK 플래닛.
 */
public interface RedisSimpleGetOrLoadHandler<P,V> {

    /**
     * Redis에서 데이터를 조회하는 로직을 구현한다.
     * @param param 파라메터
     * @param redis redis client
     * @return 응답값
     */
    V load(P param, Plandasj redis);

    /**
     * Redis에 데이터를 적재하는 로직을 구현한다.
     *
     * @param param 파라메터. 적재시 키값 생성을 위해 참조
     * @param value 값
     * @param redis redis client
     */
    void store(P param, V value, Plandasj redis);

    /**
     * 파라메터에 맞는 객체를 생성한다. DB로 데이터를 조회하여 객체를 만들어 응답한다.
     * @param param 파라메터
     * @return 응답값
     */
    V makeValue(P param);

}
