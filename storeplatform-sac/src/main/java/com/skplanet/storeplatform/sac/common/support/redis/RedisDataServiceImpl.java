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

import com.google.common.collect.Maps;
import com.skplanet.plandasj.Plandasj;
import com.skplanet.spring.data.plandasj.PlandasjConnectionFactory;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.display.cache.redisdata.PkgToAppInfoHandlerImpl;
import com.skplanet.storeplatform.sac.display.cache.redisdata.ProductBaseInfoHandlerImpl;
import com.skplanet.storeplatform.sac.display.cache.redisdata.SupportDeviceHandlerImpl;
import org.msgpack.MessagePack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * RedisDataServiceImpl
 * </p>
 * Updated on : 2015. 11. 03 Updated by : 정희원, SK 플래닛.
 */
@Service
public class RedisDataServiceImpl implements RedisDataService {

    private Map<Object, AbstractRedisDataHandler> handlerMap;
    private MessagePack messagePack;

    @Autowired(required = false)
    private PlandasjConnectionFactory connectionFactory;

    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    @PostConstruct
    public void init() {
        messagePack = new MessagePack();
        handlerMap = Maps.newConcurrentMap();

        List<?> handlerList = Arrays.asList(
                new ProductBaseInfoHandlerImpl()
                , new PkgToAppInfoHandlerImpl()
                , new SupportDeviceHandlerImpl()
        );

        for (Object o : handlerList) {
            AbstractRedisDataHandler h = (AbstractRedisDataHandler) o;
            h.init(messagePack);
            handlerMap.put(h.getResultTypeClass(), h);
        }
    }

    @Override
    public <T> T get(Class<T> t, Object key) {
        if(key == null || t == null)
            throw new IllegalArgumentException();

        AbstractRedisDataHandler<?,T> handler = findHandler(t);
        Plandasj client = getPlandasj();

        if(client == null)
            return handler._makeValue(key, commonDAO);

        T v;
        v = handler._load(key, client);
        if(v != null)
            return v;

        v = handler._makeValue(key, commonDAO);
        if(v != null) {
            handler._store(key, v, client);
        }

        return v;
    }

    @Override
    public <T> void evict(Class<T> t, Object key) {
        if(t == null || key == null)
            throw new IllegalArgumentException();

        AbstractRedisDataHandler<?,T> handler = findHandler(t);
        Plandasj client = getPlandasj();

        if(client != null)
            handler._evict(key, client);
    }

    @Override
    public <T> T load(Class<T> t, Object key) {
        if(key == null || t == null)
            throw new IllegalArgumentException();

        AbstractRedisDataHandler<?,T> handler = findHandler(t);
        Plandasj client = getPlandasj();

        T value = handler._makeValue(key, commonDAO);
        if(client == null)
            return value;

        if(value == null)
            return null;

        handler._store(key, value, client);
        return value;
    }

    private AbstractRedisDataHandler findHandler(Class clazz) {
        AbstractRedisDataHandler handler = handlerMap.get(clazz);
        if(handler == null)
            throw new RuntimeException(clazz.getName() + "유형의 데이터 핸들러가 존재하지 않습니다.");

        return handler;
    }

    private Plandasj getPlandasj() {
        if(connectionFactory == null)
            return null;

        return connectionFactory.getConnectionPool().getClient();
    }
}
