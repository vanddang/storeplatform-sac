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
 * RedisSimpleAction
 * TODO 응답값이 null인 것을 특정 시간내에 임계치 이상 조회할 경우 별도로 관리하여 DB의 액세스를 줄인다.
 * TODO 사전 적재 기능을 만들어 sp-admin에서 이를 조회하고 관리하게 한다.
 * </p>
 * Updated on : 2015. 05. 13 Updated by : 정희원, SK 플래닛.
 */
public class RedisSimpleAction {
    static Plandasj client = null;

    /**
     * 데이터를 조회한다.
     * 먼저 Redis에서 조회하고 존재하지 않는 경우 값을 생성하여 Redis에 적재해준다.
     * Redis가 사용 가능하지 않은 상황에서도 동작을 보장한다.
     *
     * @param param 파라메터
     * @param handler 데이터의 조회, 적재, 값 생성 처리기
     * @param <P> 파라메터 VO
     * @param <V> 응답값 VO
     * @return 응답값
     */
    public static <P,V> V getOrLoad(P param, RedisSimpleGetOrLoadHandler<P, V> handler) {
        if(handler == null)
            throw new IllegalArgumentException();

        if(client == null)
            return handler.makeValue(param);

        V v;
        v = handler.load(param, client);
        if(v != null)
            return v;

        v = handler.makeValue(param);
        if(v != null) {
            handler.store(param, v, client);
        }

        return v;
    }
}
