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

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Sets;
import com.skplanet.plandasj.Plandasj;
import com.skplanet.storeplatform.sac.display.cache.SacRedisKeys;
import com.skplanet.storeplatform.sac.display.cache.vo.PromotionEventWrapper;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/**
 * <p>
 * PromotionEventRedisHelper
 * </p>
 * Updated on : 2015. 07. 27 Updated by : 정희원, SK 플래닛.
 */
public class PromotionEventRedisHelper {

    private PromotionEventRedisHelper() {}

    public static List<String> getLiveEventStrs(Plandasj client, String tenantId, String[] keys) {

        Preconditions.checkArgument(keys.length == 3);

        return client.hmget(SacRedisKeys.livePromoEvent(),
                tenantId + ":" + keys[0],
                tenantId + ":" + keys[1],
                tenantId + ":" + keys[2]);
    }

    public static PromotionEventWrapper getLiveEvent(Plandasj client, String fullKey) {

        String liveEventStr = client.hget(SacRedisKeys.livePromoEvent(), fullKey);
        return new PromotionEventWrapper(liveEventStr);
    }

    /**
     * 라이브 이벤트를 초기화 한다.
     * @param client
     * @param fullKey
     * @return 신규로 등록하는 이벤트인 경우 true, 기존재하는 경우 false
     */
    public static boolean initLiveEvent(Plandasj client, String fullKey, PromotionEventWrapper wrapper) {
        return client.hsetnx(SacRedisKeys.livePromoEvent(), fullKey, wrapper.getStr()) == 1;
    }

    public static void removeLiveEvent(Plandasj client, String fullKey) {
        client.hdel(SacRedisKeys.livePromoEvent(), fullKey);
    }

    public static void saveLiveEvent(Plandasj client, String fullKey, PromotionEventWrapper wrapper) {
        client.hset(SacRedisKeys.livePromoEvent(), fullKey, wrapper.getStr());
    }

    public static void addReservedEvent(Plandasj client, String fullKey, PromotionEventWrapper wrapper) {
        // TODO 프로세스가 동시에 진행되는 경우 데이터가 중복 등록될 수 있음. (서비스시 문제는 없어보임)
        client.rpush(SacRedisKeys.promoEvent(fullKey), wrapper.getStr());
    }
    /**
     * 유효한 다음 이벤트를 조회한다.
     * @param client
     * @param tenantId
     * @param key
     * @param now 기준일시
     * @return 이벤트. 유효한 이벤트가 없는 경우 null
     */
    public static PromotionEventWrapper getEventFromReserved(Plandasj client, String tenantId, String key, Date now) {

        List<String> allEvents = client.lrange(SacRedisKeys.promoEvent(tenantId, key), 0, -1);
        if(allEvents.size() == 0) {
            return null;
        }

        for (String str : allEvents) {
            PromotionEventWrapper eventWrapper = new PromotionEventWrapper(str);

            if(eventWrapper.hasError())
                continue;

            // 이벤트중이거나 예정인 것을 응답
            if (now.after(eventWrapper.getEndDt()))
                continue;

            return eventWrapper;
        }

        return null;
    }

    /**
     * promoEventSet에 추가한다.
     * @param client
     * @param fullKey
     */
    public static void addEventSet(Plandasj client, String fullKey) {
        client.sadd(SacRedisKeys.promoEventSet(), fullKey);
    }

    /**
     * 대상이 되는 promoEvent, promoEventSet을 지운다.
     * @param redis
     * @param tenantId 대상이 되는 tenantId
     * @param key 대상이 되는 이벤트 키(메뉴 또는 상품)
     */
    public static void removeEventAndSet(Plandasj redis, String tenantId, String key) {

        String remTarget = StringUtils.defaultString(tenantId) + ":" + StringUtils.defaultString(key);

        if (tenantId != null && key != null) {
            redis.srem(SacRedisKeys.promoEventSet(), remTarget);
            redis.del(SacRedisKeys.promoEvent(remTarget));
        }
        else {
            // 전체 삭제
            Collection<String> promoEventSet = redis.smembers(SacRedisKeys.promoEventSet());

            boolean clearMode = (tenantId == null && key == null);
            for (String promoEventKey : promoEventSet) {
                if(promoEventKey.contains(remTarget)) {
                    redis.del(SacRedisKeys.promoEvent(promoEventKey));

                    if(!clearMode)
                        redis.srem(SacRedisKeys.promoEventSet(), promoEventKey);
                }
            }

            if(clearMode)
                redis.del(SacRedisKeys.promoEventSet());
        }
    }

    /**
     * Drop된 데이터들을 찾아 라이브 이벤트에서 제거한다.
     * @param redis
     * @param tenantId
     *@param key
     * @param fetchedKeys  @return 제거된 갯수
     */
    public static int removeDroppedEventFromLiveEvents(Plandasj redis, String tenantId, String key, Set<String> fetchedKeys) {

        final String fullKeyFilter = tenantId + ":" + StringUtils.defaultString(key);
        int cntLiveRemoved = 0;
        Set<String> liveKeySet = redis.hkeys(SacRedisKeys.livePromoEvent());
        liveKeySet = Sets.filter(liveKeySet, new Predicate<String>() {
            @Override
            public boolean apply(String fullKey) {
                return fullKey.startsWith(fullKeyFilter);
            }
        });

        liveKeySet.removeAll(fetchedKeys);

        for (String remKey : liveKeySet) {
            redis.hdel(SacRedisKeys.livePromoEvent(), remKey);
            ++cntLiveRemoved;
        }

        return cntLiveRemoved;
    }

    public static int tryEventTransition(Plandasj redis, String fullKey, String prevDatetimeKey, String nextDatetimeKey) {
        return redis.hincrBy(SacRedisKeys.livePromoEventTransition(), fullKey + ":" + prevDatetimeKey + "-" + StringUtils.defaultString(nextDatetimeKey, "END"), 1).intValue();
    }

    public static void resetEventTransition(Plandasj redis, String fullKey, String prevDatetimeKey, String nextDatetimeKey) {
        redis.hdel(SacRedisKeys.livePromoEventTransition(), fullKey + ":" + prevDatetimeKey + "-" + StringUtils.defaultString(nextDatetimeKey, "END"));
    }

    public static void addEventEndLog(Plandasj redis, String fullKey, Date now) {

        String svrNm, instanceNm = System.getProperty("instanceName", "N/A");
        try {
            svrNm = InetAddress.getLocalHost().getHostName();
        } catch(UnknownHostException e) {
            svrNm = "UnknownHost";
        }

        Object[] vars = new Object[]{fullKey, new DateTime(now), now.getTime(), svrNm, instanceNm};
        redis.lpush(SacRedisKeys.livePromoEventEndLog(), StringUtils.join(vars, " "));
    }
}
