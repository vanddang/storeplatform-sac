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

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.skplanet.plandasj.Plandasj;
import com.skplanet.spring.data.plandasj.PlandasjConnectionFactory;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.common.support.featureswitch.FeatureKey;
import com.skplanet.storeplatform.sac.common.support.featureswitch.SacFeatureSwitchAccessor;
import com.skplanet.storeplatform.sac.common.support.redis.RedisSimpleAction;
import com.skplanet.storeplatform.sac.common.support.redis.RedisSimpleGetOrLoadHandler;
import com.skplanet.storeplatform.sac.common.util.ServicePropertyManager;
import com.skplanet.storeplatform.sac.display.cache.SacRedisKeys;
import com.skplanet.storeplatform.sac.display.cache.vo.*;
import com.skplanet.storeplatform.sac.display.common.DisplayCryptUtils;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.promotion.PromotionEventDataService;
import com.skplanet.storeplatform.sac.display.promotion.PromotionEventUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * UpdateProductInfoManagerImpl
 * </p>
 * Updated on : 2014. 06. 12 Updated by : 정희원, SK 플래닛.
 */
@Service
public class CachedExtraInfoManagerImpl implements CachedExtraInfoManager {

    /**
     * 이벤트 트랜지션 임계치.
     * - 임계치 이전에는 Reserved에서 조회하여 응답
     * - 임계치를 넘으면 liveMap에 반영해줌
     * - 단위시간당 임계치를 적용하려면 ttl 적용해야 함
     */
    private static final int TRANSITION_THRESHOLD = 100;

    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    @Autowired(required = false)
    private PlandasjConnectionFactory connectionFactory;

    @Autowired
    private PromotionEventSyncService promotionEventSyncService;

    @Autowired
    private SacFeatureSwitchAccessor featureSwitch;

    @Autowired
    private DisplayCommonService displayCommonService;

    @Autowired
    private PromotionEventDataService eventDataService;

    private static final Logger logger = LoggerFactory.getLogger(CachedExtraInfoManagerImpl.class);

    @Override
    @Cacheable(value = "sac:display:updateProductInfo:v4", key = "#param.getCacheKey()")
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
        final DisplayCryptUtils.SacSha1Mac sha1Mac = DisplayCryptUtils.createSha1Mac();

        return RedisSimpleAction.getOrLoad(connectionFactory, pkgNm,
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
                        String hashedPkgNm = sha1Mac.hashPkgNm(pkgNm);
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
        return RedisSimpleAction.getOrLoad(connectionFactory, param.getProdId(),
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
                        // FIXME 실행중 에러가 나면 원치 않는 동작을 할수도 있다.
                        String key = SacRedisKeys.prodBase(prodId);
                        redis.hset(key, "svcGrpCd", value.getSvcGrpCd());
                        redis.hset(key, "topMenuId", value.getTopMenuId());
                        redis.hset(key, "chnlId", value.getChnlId());

                        if(!Strings.isNullOrEmpty(value.getContentsTypeCd()))
                            redis.hset(key, "contentsTypeCd", value.getContentsTypeCd());
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
    public PromotionEvent getPromotionEvent(GetPromotionEventParam param) {

        // 조회할 대상들의 키를 생성한다.
        String tenantId = param.getTenantId();

        final String[] keys = PromotionEventUtils.makeKeys(param.getChnlId(), param.getMenuId());

        if(featureSwitch.get(FeatureKey.PROMO_EVENT_FORCE_DB) || connectionFactory == null) {
            RawPromotionEvent rawPromotionEvent = getRawPromotionEvent(tenantId, keys, true);
            return PromotionEventConverter.convert(rawPromotionEvent);
        }

        Date now = param.getNowDt();
        if(now == null) {
            if (featureSwitch.get(FeatureKey.PROMO_EVENT_USE_SYSTIME))
                now = new Date();
            else
                now = displayCommonService.getDbDateTime();
        }
        else
            now = DateUtils.truncate(now, Calendar.MILLISECOND);

        Plandasj client = connectionFactory.getConnectionPool().getClient();

        List<byte[]> liveEvents = PromotionEventRedisHelper.getLiveEventDatas(client, tenantId, keys);

        // 조회된 liveEvent 순서대로 유효한 이벤트 정보를 찾아 응답한다.
        for (int i = 0; i < liveEvents.size(); ++i) {

            byte[] data = liveEvents.get(i);
            String key = keys[i];

            // 해당하는 이벤트가 없는 경우
            if(data == null)
                continue;

            // 이벤트는 등록되었지만 liveEvent에 아직 적재되지 않은 경우
            String fullKey = tenantId + ":" + key;
            PromotionEvent event = PromotionEventConverter.convert(data);

            if(event == null) {
                SyncPromotionEventResult eventResult = promotionEventSyncService.syncPromotionEvent(tenantId, key);
                if(eventResult.getUpdtCnt() > 0) {
                    event = eventResult.getLiveEventMap().get(fullKey);
                }
                else
                    continue;
            }

            if (now.compareTo(event.getEndDt()) <= 0) {
                return event.isLiveFor(now) ? event : null;
            } else {
                // TODO jitter에 의한 의도하지 않은 이벤트 transition 처리 방안 => 임계치를 적용하여 처리함
                // TODO eventTransition:[beforePromId]:[afterPromId] => numeric
                String prevDatetimeKey = event.makeDatetimeKey();
                PromotionEvent incommingEvent = PromotionEventRedisHelper.getEventFromReserved(client, tenantId, key, now);

                if (incommingEvent != null) {

                    if(featureSwitch.get(FeatureKey.PROMO_EVENT_SKIP_TRANSITION_THRESHOLD))
                        PromotionEventRedisHelper.saveLiveEvent(client, fullKey, incommingEvent);
                    else {
                        if (PromotionEventRedisHelper.tryEventTransition(client, fullKey, prevDatetimeKey, event.makeDatetimeKey()) >= TRANSITION_THRESHOLD) {
                            PromotionEventRedisHelper.resetEventTransition(client, fullKey, prevDatetimeKey, event.makeDatetimeKey());
                            PromotionEventRedisHelper.saveLiveEvent(client, fullKey, incommingEvent);
                        }
                    }

                    return incommingEvent.isLiveFor(now) ? incommingEvent : null;
                } else {

                    // 더이상의 이벤트가 없는 경우
                    PromotionEventRedisHelper.addEventEndLog(client, fullKey, now);

                    if(featureSwitch.get(FeatureKey.PROMO_EVENT_SKIP_TRANSITION_THRESHOLD))
                        PromotionEventRedisHelper.removeLiveEvent(client, fullKey);
                    else {
                        if (PromotionEventRedisHelper.tryEventTransition(client, fullKey, prevDatetimeKey, null) >= TRANSITION_THRESHOLD) {
                            PromotionEventRedisHelper.resetEventTransition(client, fullKey, prevDatetimeKey, null);
                            PromotionEventRedisHelper.removeLiveEvent(client, fullKey);
                        }
                    }
                }
            }
        }

        return null;
    }

    /**
     * DB 에서 이벤트를 조회한다.
     * @param tenantId
     * @param keys
     * @return
     */
    @Override
    public RawPromotionEvent getRawPromotionEvent(String tenantId, String[] keys, boolean liveOnly) {

        List<RawPromotionEvent> rawEventList = eventDataService.getRawEventList(tenantId, Arrays.asList(keys), PromotionEventDataService.GET_RAW_EVENT_BY_ALL);
        if (rawEventList.size() == 0)
            return null;

        return rawEventList.get(0);
    }

    @Override
    public RawPromotionEvent getRawPromotionEvent(String tenantId, String chnlId, String menuId, boolean liveOnly) {
        return getRawPromotionEvent(tenantId, PromotionEventUtils.makeKeys(chnlId, menuId), liveOnly);
    }
}
