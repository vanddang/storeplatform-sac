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

import com.google.common.base.Stopwatch;
import com.google.common.base.Strings;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.skplanet.plandasj.Plandasj;
import com.skplanet.spring.data.plandasj.PlandasjConnectionFactory;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.display.cache.SacRedisKeys;
import com.skplanet.storeplatform.sac.display.cache.vo.PromotionEventWrapper;
import com.skplanet.storeplatform.sac.display.cache.vo.RawPromotionEvent;
import com.skplanet.storeplatform.sac.display.cache.vo.SyncPromotionEventResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * PromotionEventSyncServiceImpl
 * </p>
 * Updated on : 2015. 07. 24 Updated by : 정희원, SK 플래닛.
 */
@Service
public class PromotionEventSyncServiceImpl implements PromotionEventSyncService {

    private static final String LIVE_EVENT_EXIST = "-";
    private static final Logger logger = LoggerFactory.getLogger(PromotionEventSyncServiceImpl.class);
    private static final int ERR_UPDT_CNT = -1;

    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    @Autowired(required = false)
    private PlandasjConnectionFactory connectionFactory;

    @Override
    public SyncPromotionEventResult syncPromotionEvent(final String tenantId, final String key) {

        if(connectionFactory == null)
            return new SyncPromotionEventResult(ERR_UPDT_CNT, null, new HashMap<String, PromotionEventWrapper>());

        final Plandasj redis = connectionFactory.getConnectionPool().getClient();

        /*
         * promoEventSet - 등록된 모든 이벤트 키
         * promoEvent - 진행중 또는 예정 이벤트 목록
         * livePromoEvent - 진행중이거나 가장 최근에 시작할 이벤트. (이 데이터 구조의 도입으로 Lock을 걸 필요가 없어짐.)
         */

        final Stopwatch stopwatch = Stopwatch.createStarted();

        removeEventAndSet(redis, tenantId, key);
        logger.debug("Events in redis have removed. - {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));

        Multimap<String, RawPromotionEvent> fetchedEventMap = fetchEventDataFromDb(tenantId, key);
        logger.debug("Fetch events from db - {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));

        // 이벤트 등록
        int updtCnt = 0;
        List<Integer> errorPromId = Lists.newArrayList();
        Map<String, PromotionEventWrapper> liveMap = Maps.newHashMap();

        for (Map.Entry<String, Collection<RawPromotionEvent>> events : fetchedEventMap.asMap().entrySet()) {

            PromotionEventWrapper incommingEventWrapper = null;

            ///// Inner loop start
            for (RawPromotionEvent rawEvent : events.getValue()) {

                PromotionEventWrapper wrapper = new PromotionEventWrapper(rawEvent);

                if(wrapper.hasError()) {
                    errorPromId.add(rawEvent.getPromId());
                    continue;   // for inner loop
                }

                if(incommingEventWrapper == null)
                    incommingEventWrapper = wrapper;

                // TODO 프로세스가 동시에 진행되는 경우 데이터가 중복 등록될 수 있음. (서비스시 문제는 없어보임)
                redis.rpush(SacRedisKeys.promoEvent(events.getKey()), wrapper.getStr());

                ++updtCnt;
            }
            ///// Inner loop end

            // 유효한 event가 없는 경우
            if(incommingEventWrapper == null) {
                redis.hdel(SacRedisKeys.livePromoEvent(), events.getKey());
                continue; // for outer loop
            }

            Long rtnSetLive = redis.hsetnx(SacRedisKeys.livePromoEvent(), events.getKey(), LIVE_EVENT_EXIST);
            // 라이브에 기등록된 이벤트라면 incommingEvent와 비교한다
            if(rtnSetLive > 0) {
                String liveEventStr = redis.hget(SacRedisKeys.livePromoEvent(), events.getKey());
                PromotionEventWrapper currentEventWrapper = new PromotionEventWrapper(liveEventStr);

                if(currentEventWrapper.hasError()) {
                    redis.hset(SacRedisKeys.livePromoEvent(), events.getKey(), incommingEventWrapper.getStr());
                }
                else {
                    if(!(currentEventWrapper.getStartDt().equals(incommingEventWrapper.getStartDt()) ||
                            currentEventWrapper.getEndDt().equals(incommingEventWrapper.getEndDt()))) {
                        redis.hset(SacRedisKeys.livePromoEvent(), events.getKey(), incommingEventWrapper.getStr());
                    }
                }
            }

            redis.sadd(SacRedisKeys.promoEventSet(), events.getKey());
            liveMap.put(events.getKey(), incommingEventWrapper);
        }

        int cntLiveRemoved = removeDroppedEventFromLiveEvents(redis, fetchedEventMap.keySet());
        logger.debug("Unused or cancelled live events are removed. [{}]", cntLiveRemoved);

        logger.debug("Sync events to redis finished. - {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));

        stopwatch.stop();
        logger.debug("Event sync processing time = {}", stopwatch);

        checkSyncedData();

        return new SyncPromotionEventResult(updtCnt, errorPromId, liveMap);
    }

    /**
     * Drop된 데이터들을 찾아 라이브 이벤트에서 제거한다.
     * @param redis
     * @param fetchedKeys
     * @return 제거된 갯수
     */
    private int removeDroppedEventFromLiveEvents(Plandasj redis, Set<String> fetchedKeys) {

        int cntLiveRemoved = 0;
        Set<String> liveKeySet = redis.hkeys(SacRedisKeys.livePromoEvent());

        if(liveKeySet.removeAll(fetchedKeys)) {
            for(String remKey : liveKeySet) {
                redis.hdel(SacRedisKeys.livePromoEvent(), remKey);
                ++cntLiveRemoved;
            }
        }

        return cntLiveRemoved;
    }

    /**
     * DB에서 이벤트 데이터를 조회해온다.
     * 응답은 Map형태인데, 키는 tenantId+key가 조합된 문자열이며, 값은 예정된 이벤트들(하나의 키에 여러 이벤트가 예약되어 있을수 있음)이다.
     * @param tenantId
     * @param key
     * @return
     */
    private Multimap<String, RawPromotionEvent> fetchEventDataFromDb(String tenantId, String key) {

        Map<String, Object> req = Maps.newHashMap();
        if(!Strings.isNullOrEmpty(tenantId))
            req.put("tenantId", tenantId);
        if(!Strings.isNullOrEmpty(key))
            req.put("key", key);

        List<RawPromotionEvent> promEventList = commonDAO.queryForList("PromotionEventMapper.getPromotionEventList", req, RawPromotionEvent.class);

        Multimap<String, RawPromotionEvent> onlineEventMap = LinkedHashMultimap.create();
        for (RawPromotionEvent e : promEventList) {
            onlineEventMap.put(e.getTenantId() + ":" + e.getPromTypeValue(), e);
        }

        return onlineEventMap;
    }

    /**
     * 대상이 되는 promoEvent, promoEventSet을 지운다.
     * @param redis
     * @param tenantId 대상이 되는 tenantId
     * @param key 대상이 되는 이벤트 키(메뉴 또는 상품)
     */
    private void removeEventAndSet(Plandasj redis, String tenantId, String key) {

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
     * TODO 동기화 처리된 데이터의 유효성 검증
     */
    private void checkSyncedData() {}
}
