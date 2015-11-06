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
import org.msgpack.MessagePack;

/**
 * <p>
 * AbstractRedisDataHandler
 * </p>
 * Updated on : 2015. 11. 05 Updated by : 정희원, SK 플래닛.
 */
public abstract class AbstractRedisDataHandler<K, V> implements RedisDataHandler<K, V> {

    protected MessagePack messagePack;

    public void init(MessagePack messagePack) {
        this.messagePack = messagePack;
        Class resultTypeClass = getResultTypeClass();
        if(resultTypeClass == null)
            throw new IllegalStateException("getResultTypeClass()의 값은 null이 될 수 없습니다.");

        this.messagePack.register(resultTypeClass);
    }

    protected K validateAndCastKey(Object key) {
        if(key == null)
            throw new IllegalArgumentException();

        return (K) key;
    }

    public V _load(Object key, Plandasj redis) {
        K _key = validateAndCastKey(key);

        return load(_key, redis);
    }

    public void _store(Object key, V value, Plandasj redis) {
        K _key = validateAndCastKey(key);
        store(_key, value, redis);
    }

    public V _makeValue(Object key, CommonDAO commonDAO) {
        K _key = validateAndCastKey(key);
        return makeValue(_key, commonDAO);
    }

    public void _evict(Object key, Plandasj redis) {
        K _key = validateAndCastKey(key);
        evict(_key, redis);
    }

    @Override
    public void evictWithFilter(String filterStr, Plandasj redis) {
        throw new IllegalAccessError("아직 구현되지 않은 기능입니다.");
    }
}
