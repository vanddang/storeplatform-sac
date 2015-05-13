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

import com.skplanet.spring.data.plandasj.PlandasjConnectionFactory;

/**
 * <p>
 * DESC
 * </p>
 * Updated on : 2015. 05. 13 Updated by : 정희원, SK 플래닛.
 */
public class SacRedisInitializer {

    public SacRedisInitializer(PlandasjConnectionFactory plandasjConnectionFactory) {
        if(plandasjConnectionFactory != null)
            RedisSimpleAction.client = plandasjConnectionFactory.getConnectionPool().getClient();
    }

}
