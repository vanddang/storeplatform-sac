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

    private static final Logger logger = LoggerFactory.getLogger(PromotionEventSyncServiceImpl.class);
    private static final int ERR_UPDT_CNT = -1;

    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    @Autowired(required = false)
    private PlandasjConnectionFactory connectionFactory;

    @Override
    public SyncPromotionEventResult syncPromotionEvent(final String tenantId, final String key, final boolean forceUpdate) {

        if(connectionFactory == null)
            return new SyncPromotionEventResult(ERR_UPDT_CNT, 0, null, new HashMap<String, PromotionEventWrapper>());

        final Plandasj client = connectionFactory.getConnectionPool().getClient();

        /*
         * promoEventSet - 등록된 모든 이벤트 키
         * promoEvent - 진행중 또는 예정 이벤트 목록
         * livePromoEvent - 진행중이거나 가장 최근에 시작할 이벤트. (이 데이터 구조의 도입으로 Lock을 걸 필요가 없어짐.)
         */

        final Stopwatch stopwatch = Stopwatch.createStarted();

        PromotionEventRedisHelper.removeEventAndSet(client, tenantId, key);
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

                PromotionEventRedisHelper.addReservedEvent(client, events.getKey(), wrapper);

                ++updtCnt;
            }
            ///// Inner loop end

            // 유효한 event가 없는 경우
            if(incommingEventWrapper == null) {
                PromotionEventRedisHelper.removeLiveEvent(client, events.getKey());
                continue; // for outer loop
            }

            // 강제 업데이트 모드인 경우
            if(forceUpdate) {
                PromotionEventRedisHelper.saveLiveEvent(client, events.getKey(), incommingEventWrapper);
                continue;
            }

            // 라이브에 기등록된 이벤트라면 incommingEvent와 비교한다
            if(!PromotionEventRedisHelper.initLiveEvent(client, events.getKey(), incommingEventWrapper)) {
                PromotionEventWrapper currentEventWrapper = PromotionEventRedisHelper.getLiveEvent(client, events.getKey());

                if(currentEventWrapper.hasError()) {
                    PromotionEventRedisHelper.saveLiveEvent(client, events.getKey(), incommingEventWrapper);
                }
                else {
                    // TODO hashCode가 지금 로직으로는 유효하게 작동하지 않는 문제가 있다.
                    try {
                        if(currentEventWrapper.getPromotionEvent().hashCode() != incommingEventWrapper.getPromotionEvent().hashCode())
                            PromotionEventRedisHelper.saveLiveEvent(client, events.getKey(), incommingEventWrapper);
                    }
                    catch (RuntimeException e) {
                        PromotionEventRedisHelper.saveLiveEvent(client, events.getKey(), incommingEventWrapper);
                    }
                }
            }

            client.sadd(SacRedisKeys.promoEventSet(), events.getKey());
            liveMap.put(events.getKey(), incommingEventWrapper);
        }

        int cntLiveRemoved = PromotionEventRedisHelper.removeDroppedEventFromLiveEvents(client, tenantId, key, fetchedEventMap.keySet());
        logger.debug("Unused or cancelled live events are removed. [{}]", cntLiveRemoved);

        logger.debug("Sync events to redis finished. - {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));

        stopwatch.stop();
        logger.debug("Event sync processing time = {}", stopwatch);

        checkSyncedData();

        return new SyncPromotionEventResult(updtCnt, cntLiveRemoved, errorPromId, liveMap);
    }

    /**
     * DB에서 이벤트 데이터를 조회해온다.
     * 응답은 Map형태인데, 키는 tenantId+key가 조합된 문자열이며, 값은 예정된 이벤트들(하나의 키에 여러 이벤트가 예약되어 있을수 있음)이다.
     * @param tenantId
     * @param key
     * @return
     */
    private Multimap<String, RawPromotionEvent> fetchEventDataFromDb(String tenantId, String key) {

        List<RawPromotionEvent> promEventList = getRawEventList(tenantId, key != null ? Arrays.asList(key) : null, false);

        Multimap<String, RawPromotionEvent> onlineEventMap = LinkedHashMultimap.create();
        for (RawPromotionEvent e : promEventList) {
            onlineEventMap.put(e.getTenantId() + ":" + e.getPromTypeValue(), e);
        }

        return onlineEventMap;
    }

    @Override
    public List<RawPromotionEvent> getRawEventList(String tenantId, List<String> keyList, boolean liveOnly) {

        Map<String, Object> req = Maps.newHashMap();

        if(!Strings.isNullOrEmpty(tenantId))
            req.put("tenantId", tenantId);

        if(keyList != null)
            req.put("keyList", keyList);

        req.put("liveOnly", liveOnly);

        return commonDAO.queryForList("PromotionEventMapper.getPromotionEventList", req, RawPromotionEvent.class);
    }

    @Override
    public SyncPromotionEventResult syncPromotionEvent(String tenantId, String key) {
        return syncPromotionEvent(tenantId, key, false);
    }

    /**
     * TODO 동기화 처리된 데이터의 유효성 검증
     */
    private void checkSyncedData() {}
}
