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
import com.google.common.collect.Multimap;
import com.skplanet.plandasj.Plandasj;
import com.skplanet.spring.data.plandasj.PlandasjConnectionFactory;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.common.support.redis.RedisSimpleAction;
import com.skplanet.storeplatform.sac.common.support.redis.RedisSimpleGetOrLoadHandler;
import com.skplanet.storeplatform.sac.common.util.ServicePropertyManager;
import com.skplanet.storeplatform.sac.display.cache.SacRedisKeys;
import com.skplanet.storeplatform.sac.display.cache.vo.*;
import com.skplanet.storeplatform.sac.display.common.DisplayCryptUtils;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * UpdateProductInfoManagerImpl
 * </p>
 * Updated on : 2014. 06. 12 Updated by : 정희원, SK 플래닛.
 */
@Service
public class CachedExtraInfoManagerImpl implements CachedExtraInfoManager {

    public static final String LOCK_KEY = "SyncPromotionEvent";
    public static final String LIVE_EVENT_EXIST = "-";

    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    @Autowired(required = false)
    private PlandasjConnectionFactory connectionFactory;

    private static final Logger logger = LoggerFactory.getLogger(CachedExtraInfoManagerImpl.class);

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmm");

    @Override
    @Cacheable(value = "sac:display:updateProductInfo:v3", key = "#param.getCacheKey()")
    public UpdateProduct getUpdateProductInfo(UpdateProductParam param) {

        Map<String, Object> updateTargetMap = new HashMap<String, Object>();

        updateTargetMap.put("imageCd", DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
        updateTargetMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
        updateTargetMap.put("contentsTypeCd", DisplayConstants.DP_EPISODE_CONTENT_TYPE_CD);
        updateTargetMap.put("svcGrpCd", DisplayConstants.DP_APP_PROD_SVC_GRP_CD);
        updateTargetMap.put("rshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);

        updateTargetMap.put("tenantId", param.getTenantId());
        updateTargetMap.put("epsdId", param.getEpsdId());
        updateTargetMap.put("subContentsId", param.getSubContentsId());
        updateTargetMap.put("langCd", param.getLangCd());

        return commonDAO.queryForObject("CachedExtraInfoManager.getUpdateProductInfo", updateTargetMap, UpdateProduct.class);
    }

    @Override
    public String getProdIdByPkgNm(String pkgNm) {

        return RedisSimpleAction.getOrLoad(pkgNm,
                new RedisSimpleGetOrLoadHandler<String, String>() {
                    @Override
                    public String load(String pkgNm, Plandasj redis) {
                        return redis.get(SacRedisKeys.pkg2prod(pkgNm));
                    }

                    @Override
                    public void store(String pkgNm, String value, Plandasj redis) {
                        String key = SacRedisKeys.pkg2prod(pkgNm);
                        redis.set(key, value);
                        redis.expire(key, 60 * 60 * 12);

                        redis.sadd(SacRedisKeys.pkgsInProd(value), pkgNm);
                    }

                    @Override
                    public String makeValue(String pkgNm) {
                        String hashedPkgNm = DisplayCryptUtils.hashPkgNm(pkgNm);
                        Map<String, Object> req = new HashMap<String, Object>();
                        req.put("tenantIds", ServicePropertyManager.getSupportTenantList());
                        req.put("hashedPkgNm", hashedPkgNm);
                        String prodId = commonDAO.queryForObject("CachedExtraInfoManager.getProdIdByPkgNm", req, String.class);

                        return prodId;
                    }
                });
    }

    @Override
    public void evictPkgsInProd(String prodId) {
        Plandasj c = connectionFactory.getConnectionPool().getClient();
        Set<String> pkgs = c.smembers(SacRedisKeys.pkgsInProd(prodId));
        for(String pkg : pkgs) {
            c.del(SacRedisKeys.pkg2prod(pkg));
        }
    }

    @Override
    public ProductBaseInfo getProductBaseInfo(GetProductBaseInfoParam param) {
        return RedisSimpleAction.getOrLoad(param.getProdId(),
                new RedisSimpleGetOrLoadHandler<String, ProductBaseInfo>() {
                    @Override
                    public ProductBaseInfo load(String prodId, Plandasj redis) {
                        Map<String, String> data = redis.hgetAll(SacRedisKeys.prodBase(prodId));
                        if(data.isEmpty())
                            return null;

                        ProductBaseInfo info = new ProductBaseInfo();
                        info.setTopMenuId(data.get("topMenuId"));
                        info.setChnlId(data.get("chnlId"));
                        info.setMetaClsfCd(data.get("metaClsfCd"));
                        info.setSvcGrpCd(data.get("svcGrpCd"));
                        info.setSvcTpCd(data.get("svcTpCd"));
                        info.setContentsTypeCd(data.get("contentsTypeCd"));
                        info.setPartParentClsfCd(data.get("partParentClsfCd"));
                        info.setCatId(data.get("catId"));

                        return info;
                    }

                    @Override
                    public void store(String prodId, ProductBaseInfo value, Plandasj redis) {
                        String key = SacRedisKeys.prodBase(prodId);
                        redis.hset(key, "svcGrpCd", value.getSvcGrpCd());
                        redis.hset(key, "contentsTypeCd", value.getContentsTypeCd());
                        redis.hset(key, "topMenuId", value.getTopMenuId());
                        redis.hset(key, "chnlId", value.getChnlId());

                        if(!Strings.isNullOrEmpty(value.getSvcTpCd()))
                            redis.hset(key, "svcTpCd", value.getSvcTpCd());
                        if(!Strings.isNullOrEmpty(value.getMetaClsfCd()))
                            redis.hset(key, "metaClsfCd", value.getMetaClsfCd());
                        if(!Strings.isNullOrEmpty(value.getPartParentClsfCd()))
                            redis.hset(key, "partParentClsfCd", value.getPartParentClsfCd());
                        if(!Strings.isNullOrEmpty(value.getCatId()))
                            redis.hset(key, "catId", value.getCatId());
                    }

                    @Override
                    public ProductBaseInfo makeValue(String prodId) {
                        HashMap<String, Object> req = new HashMap<String, Object>();
                        req.put("prodId", prodId);
                        return commonDAO.queryForObject("CachedExtraInfoManager.getProductBaseInfo", req, ProductBaseInfo.class);
                    }
                });
    }

    @Override
    public int syncPromotionEvent() {

        if(connectionFactory == null)
            return -1;

        Plandasj redis = connectionFactory.getConnectionPool().getClient();

        /*
         * promoEventSet - 등록된 모든 이벤트 키
         * promoEvent - 진행중 또는 예정 이벤트 목록
         * livePromoEvent - 진행중인 이벤트. 이 데이터 구조의 도입으로 Lock을 걸 필요가 없어짐.
         */

        // 모든 적재된 데이터 제거
        Stopwatch stopwatch = Stopwatch.createStarted();
        Collection<String> promoEventSet = redis.smembers(SacRedisKeys.promoEventSet());
        for (String promoEventKey : promoEventSet) {
            redis.del(SacRedisKeys.promoEvent(promoEventKey));
        }
        redis.del(SacRedisKeys.promoEventSet());

        logger.debug("Exist events are removed - {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));

        // 이벤트 데이터 적재
        List<RawPromotionEvent> promEventList = commonDAO.queryForList("CachedExtraInfoManager.getPromotionEventList", null, RawPromotionEvent.class);
        Multimap<String, RawPromotionEvent> eventMap = LinkedHashMultimap.create();
        for (RawPromotionEvent e : promEventList) {
            eventMap.put(e.getTenantId() + ":" + e.getPromTypeValue(), e);
        }
        logger.debug("Fetch events from db - {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));

        // 이벤트 등록
        int updtCnt = 0;

        for (Map.Entry<String, Collection<RawPromotionEvent>> events : eventMap.asMap().entrySet()) {

            for (RawPromotionEvent event : events.getValue()) {

                String strEvent = convertRawPromotionEvent2Str(event);
                redis.rpush(SacRedisKeys.promoEvent(events.getKey()),
                        event.getEventKey() + ":" + strEvent);

                ++updtCnt;
            }

            redis.hsetnx(SacRedisKeys.livePromoEvent(), events.getKey(), LIVE_EVENT_EXIST);
            redis.sadd(SacRedisKeys.promoEventSet(), events.getKey());
        }
        logger.debug("Regist events to redis - {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));

        stopwatch.stop();
        logger.debug("Event sync processing time = {}", stopwatch);

        // TODO 정상적으로 등록되었는지 검증. 갯수 카운팅

        return updtCnt;
    }

    private String convertRawPromotionEvent2Str(RawPromotionEvent obj) {

        Object[] v = new Object[]{Integer.toHexString(obj.hashCode()),
                obj.getPromId(),
                obj.getRateGrd1(),
                obj.getRateGrd2(),
                obj.getRateGrd3(),
                obj.getAcmlMethodCd(),
                obj.getAcmlDt()};

        return StringUtils.join(v, " ");
    }

    @Override
    public PromotionEvent getPromotionEvent(GetPromotionEventParam param) {

        if(connectionFactory == null)
            return getPromotionEventFromDb(param);

        Date now = param.getNowDt();
        if(now == null)
            now = new Date();

        Plandasj client = connectionFactory.getConnectionPool().getClient();

        // 조회할 대상들의 키를 생성한다.
        String tenantId = param.getTenantId(),
                menuOrTopMenuId = "",
                topMenuId = "";
        if(!Strings.isNullOrEmpty(param.getMenuId())) {

            menuOrTopMenuId = param.getMenuId();
            if(param.getMenuId().length() > 4) {
                topMenuId = menuOrTopMenuId.substring(0, 4);
            }
        }

        final String[] keys = new String[]{param.getProdId(), menuOrTopMenuId, topMenuId};

        List<String> liveEvents = client.hmget(SacRedisKeys.livePromoEvent(),
                                                tenantId + ":" + keys[0],
                                                tenantId + ":" + keys[1],
                                                tenantId + ":" + keys[2]);

        // 조회된 liveEvent 순서대로 유효한 이벤트 정보를 찾아 응답한다.
        for (int i = 0; i < liveEvents.size(); ++i) {

            String str = liveEvents.get(i);
            String key = keys[i];

            // 해당하는 이벤트가 없는 경우
            if(str == null)
                continue;

            PromotionEventWrapper eventWrapper;

            // 이벤트는 등록되었지만 liveEvent에 아직 적재되지 않은 경우
            if(str.equals(LIVE_EVENT_EXIST)) {
                eventWrapper = getNextEvent(client, tenantId, key, now);
                if(eventWrapper == null)
                    continue;

                client.hset(SacRedisKeys.livePromoEvent(), tenantId + ":" + key, eventWrapper.getStr());
            }
            else {
                eventWrapper = new PromotionEventWrapper(str);
            }

            if(eventWrapper.hasError()) {
                PromotionEventWrapper incommingEvent = syncSpecificEvent(tenantId, key);
                if(incommingEvent == null)
                    continue;

                eventWrapper = incommingEvent;
            }

            if (now.before(eventWrapper.getEndDt())) {
                return eventWrapper.isLive(now) ? eventWrapper.getPromotionEvent() : null;
            } else {
                eventWrapper = getNextEvent(client, tenantId, key, now);

                if(eventWrapper != null) {
                    client.hset(SacRedisKeys.livePromoEvent(), tenantId + ":" + key, eventWrapper.getStr());
                    return eventWrapper.isLive(now) ? eventWrapper.getPromotionEvent() : null;
                }
                else {
                    client.hdel(SacRedisKeys.livePromoEvent(), key);
                }
            }
        }

        return null;
    }

    /**
     * 유효한 다음 이벤트를 조회한다.
     * 만료된 이벤트는 그대로 남겨놓는다. (동기화 처리시 정리작업이 수행됨)
     * @param client
     * @param tenantId
     * @param key
     * @return 이벤트 래퍼 객체 응답. 유효한 이벤트를 찾을 수 없는 경우 null
     */
    private PromotionEventWrapper getNextEvent(Plandasj client, String tenantId, String key, Date now) {

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
     * 특정 이벤트를 동기화 처리하고 라이브 이벤트를 응답한다.
     * @param tenantId 테넌트ID
     * @param key 식별자
     * @return 라이브 이벤트
     */
    private PromotionEventWrapper syncSpecificEvent(String tenantId, String key) {
        return null;
    }

    @Override
    public Map<String, PromotionEvent> getPromotionEventMap(boolean liveOnly) {
        if(connectionFactory == null)
            return null; // TODO Impl.

        Plandasj redis = connectionFactory.getConnectionPool().getClient();

        return null;
    }

    /**
     * DB 에서 이벤트를 조회한다.
     * @param param
     * @return
     */
    private PromotionEvent getPromotionEventFromDb(GetPromotionEventParam param) {
        throw new RuntimeException("아직 구현되지 않았습니다.");
    }
}
