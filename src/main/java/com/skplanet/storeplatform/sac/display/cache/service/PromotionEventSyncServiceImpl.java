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
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.skplanet.plandasj.Plandasj;
import com.skplanet.spring.data.plandasj.PlandasjConnectionFactory;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.display.cache.SacRedisKeys;
import com.skplanet.storeplatform.sac.display.cache.vo.PromotionEventWrapper;
import com.skplanet.storeplatform.sac.display.cache.vo.RawPromotionEvent;
import com.skplanet.storeplatform.sac.display.cache.vo.SyncPromotionEventResult;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    public SyncPromotionEventResult syncAllPromotionEvent() {

        if(connectionFactory == null)
            return new SyncPromotionEventResult(ERR_UPDT_CNT);

        Plandasj redis = connectionFactory.getConnectionPool().getClient();

        /*
         * promoEventSet - 등록된 모든 이벤트 키
         * promoEvent - 진행중 또는 예정 이벤트 목록
         * livePromoEvent - 진행중이거나 가장 최근에 시작할 이벤트. (이 데이터 구조의 도입으로 Lock을 걸 필요가 없어짐.)
         */

        // 모든 적재된 데이터 제거
        Stopwatch stopwatch = Stopwatch.createStarted();
        Collection<String> promoEventSet = redis.smembers(SacRedisKeys.promoEventSet());
        for (String promoEventKey : promoEventSet) {
            redis.del(SacRedisKeys.promoEvent(promoEventKey));
        }
        redis.del(SacRedisKeys.promoEventSet());

        logger.debug("Events in redis have removed. - {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));

        // 이벤트 데이터 적재
        List<RawPromotionEvent> promEventList = commonDAO.queryForList("PromotionEventMapper.getPromotionEventList", null, RawPromotionEvent.class);
        Multimap<String, RawPromotionEvent> onlineEventMap = LinkedHashMultimap.create();
        for (RawPromotionEvent e : promEventList) {
            onlineEventMap.put(e.getTenantId() + ":" + e.getPromTypeValue(), e);
        }
        logger.debug("Fetch events from db - {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));

        // 이벤트 등록
        int updtCnt = 0;
        List<Integer> errorPromId = Lists.newArrayList();

        for (Map.Entry<String, Collection<RawPromotionEvent>> events : onlineEventMap.asMap().entrySet()) {

            PromotionEventWrapper incommingEventWrapper = null;
            for (RawPromotionEvent rawEvent : events.getValue()) {

                PromotionEventWrapper wrapper = new PromotionEventWrapper(rawEvent);

                if(wrapper.hasError()) {
                    errorPromId.add(rawEvent.getPromId());
                    continue;   // for inner loop
                }

                if(incommingEventWrapper == null)
                    incommingEventWrapper = wrapper;

                redis.rpush(SacRedisKeys.promoEvent(events.getKey()), wrapper.getStr());

                ++updtCnt;
            }

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
        }

        int cntLiveRemoved = 0;
        Set<String> liveKeySet = redis.hkeys(SacRedisKeys.livePromoEvent());
        if(liveKeySet.removeAll(onlineEventMap.keySet())) {
            for(String remKey : liveKeySet) {
                redis.hdel(SacRedisKeys.livePromoEvent(), remKey);
                ++cntLiveRemoved;
            }
        }
        logger.debug("Unused or cancelled live events are removed. [{}]", cntLiveRemoved);

        logger.debug("Sync events to redis finished. - {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));

        stopwatch.stop();
        logger.debug("Event sync processing time = {}", stopwatch);

        // TODO 정상적으로 등록되었는지 검증. 갯수 카운팅

        return new SyncPromotionEventResult(updtCnt, errorPromId);
    }

    /**
     * TODO Wrapper로 처리
     * @param rawEvent
     * @return
     */
    private String convertRawPromotionEvent2Str(RawPromotionEvent rawEvent) {

        Object[] v = new Object[]{Integer.toHexString(rawEvent.hashCode()),
                rawEvent.getPromId(),
                rawEvent.getRateGrd1(),
                rawEvent.getRateGrd2(),
                rawEvent.getRateGrd3(),
                rawEvent.getAcmlMethodCd(),
                rawEvent.getAcmlDt()};

        return StringUtils.join(v, " ");
    }

    @Override
    public PromotionEventWrapper syncPromotionEvent(String tenantId, String key) {
        return null;
    }
}
