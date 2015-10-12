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
import com.skplanet.storeplatform.sac.display.cache.vo.PromotionEvent;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.skplanet.storeplatform.sac.display.cache.service.PromotionEventConverter.convert;

/**
 * <p>
 * PromotionEventRedisHelper
 * TODO 사용이 지난 키 관리는 어떻게 해야 하는가?
 * </p>
 * Updated on : 2015. 07. 27 Updated by : 정희원, SK 플래닛.
 */
public class PromotionEventRedisHelper {

    private PromotionEventRedisHelper() {}

    //////////////////// 라이브 이벤트 관리 ////////////////////

    /**
     * 라이브 이벤트를 조회한다.
     * 한번에 여러 키로 조회 가능하며 데이터 적재 여부를 판단하기 때문에 저장된 상태 그대로 응답한다.
     * @param client
     * @param tenantId
     * @param keys
     * @return
     */
    public static List<byte[]> getLiveEventDatas(Plandasj client, String tenantId, String[] keys) {

        Preconditions.checkArgument(keys.length == 3);

        return client.hmget(SacRedisKeys.livePromoEvent().getBytes(),
                (tenantId + ":" + keys[0]).getBytes(),
                (tenantId + ":" + keys[1]).getBytes(),
                (tenantId + ":" + keys[2]).getBytes());
    }

    public static PromotionEvent getLiveEvent(Plandasj client, String fullKey) {

        byte[] data = client.hget(SacRedisKeys.livePromoEvent().getBytes(), fullKey.getBytes());
        return convert(data);
    }

    /**
     * 라이브 이벤트를 초기화 한다.
     * @param client
     * @param fullKey
     * @return 신규로 등록하는 이벤트인 경우 true, 기존재하는 경우 false
     */
    public static boolean initLiveEvent(Plandasj client, String fullKey, PromotionEvent event) {
        return client.hsetnx(SacRedisKeys.livePromoEvent().getBytes(), fullKey.getBytes(), convert(event)) == 1;
    }

    public static void removeLiveEvent(Plandasj client, String fullKey) {
        client.hdel(SacRedisKeys.livePromoEvent().getBytes(), fullKey.getBytes());
    }

    public static void saveLiveEvent(Plandasj client, String fullKey, PromotionEvent event) {
        client.hset(SacRedisKeys.livePromoEvent().getBytes(), fullKey.getBytes(), convert(event));
    }

    /**
     * Drop된 데이터들을 찾아 라이브 이벤트에서 제거한다.
     * @param redis
     * @param tenantId
     * @param key
     * @param fetchedKeys
     * @return 제거된 갯수
     */
    public static int removeDroppedEventFromLiveEvents(Plandasj redis, String tenantId, String key, Set<String> fetchedKeys) {
        final String fullKeyFilter = tenantId + ":" + StringUtils.defaultString(key);
        int cntLiveRemoved = 0;
        Set<String> liveKeySet = redis.hkeys(SacRedisKeys.livePromoEvent());
        liveKeySet = Sets.filter(liveKeySet, new Predicate<String>() {
            @Override
            public boolean apply(String fullKey) {
                return StringUtils.isNotEmpty(fullKey) && fullKey.startsWith(fullKeyFilter);
            }
        });

        liveKeySet.removeAll(fetchedKeys);

        for (String remKey : liveKeySet) {
            redis.hdel(SacRedisKeys.livePromoEvent(), remKey);
            ++cntLiveRemoved;
        }

        return cntLiveRemoved;
    }

    //////////////////// 예정 이벤트 관리 ////////////////////
    public static void addReservedEvent(Plandasj client, String fullKey, PromotionEvent event) {
        // TODO 프로세스가 동시에 진행되는 경우 데이터가 중복 등록될 수 있음. (서비스시 문제는 없어보임)
        client.rpush(SacRedisKeys.promoEvent(fullKey).getBytes(), convert(event));
    }
    /**
     * 유효한 다음 이벤트를 조회한다.
     * @param client
     * @param tenantId
     * @param key
     * @param now 기준일시
     * @return 이벤트. 유효한 이벤트가 없는 경우 null
     */
    public static PromotionEvent getEventFromReserved(Plandasj client, String tenantId, String key, Date now) {
        List<byte[]> allEvents = client.lrange(SacRedisKeys.promoEvent(tenantId, key).getBytes(), 0, -1);

        if(allEvents.size() == 0) {
            return null;
        }

        for (byte[] data : allEvents) {
            PromotionEvent event = convert(data);

            if(event == null)
                continue;

            // 이벤트중이거나 예정인 것을 응답
            if (now.after(event.getEndDt()))
                continue;

            return event;
        }

        return null;
    }

    //////////////////// 기타 ////////////////////
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
