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
 * RedisSimpleHandler
 * </p>
 * Updated on : 2015. 07. 01 Updated by : 정희원, SK 플래닛.
 */
public interface RedisSimpleHandler<P,V> extends RedisSimpleGetOrLoadHandler {

    /**
     * 적재된 데이터를 evict시키는 방법을 정의한다.
     * @param param
     * @param redis
     */
    void evict(P param, Plandasj redis);
}
