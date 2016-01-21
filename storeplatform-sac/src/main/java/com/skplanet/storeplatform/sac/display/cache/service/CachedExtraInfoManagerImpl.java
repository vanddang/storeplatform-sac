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

import com.skplanet.plandasj.Plandasj;
import com.skplanet.spring.data.plandasj.PlandasjConnectionFactory;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.common.support.featureswitch.FeatureKey;
import com.skplanet.storeplatform.sac.common.support.featureswitch.SacFeatureSwitchAccessor;
import com.skplanet.storeplatform.sac.common.support.redis.RedisDataService;
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

    @Autowired
    private RedisDataService dataService;

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
    public void evictPkgsInProd(String prodId) {
        Plandasj c = connectionFactory.getConnectionPool().getClient();
        Set<String> pkgs = c.smembers(SacRedisKeys.pkgsInProd(prodId));
        for(String pkg : pkgs) {
            c.del(SacRedisKeys.pkg2prod(pkg));
        }
    }

    @Override
    public ProductBaseInfo getProductBaseInfo(GetProductBaseInfoParam param) {
        return dataService.get(ProductBaseInfo.class, param.getProdId());
    }

    ////////// 프로모션 이벤트에 대한 부분은 차후 독립 예정 //////////
    @Override
    public PromotionEvent getPromotionEvent(GetPromotionEventParam param) {

        // 조회할 대상들의 키를 생성한다.
        String tenantId = param.getTenantId(),
                userKey = param.getUserKey();

        final String[] keys = PromotionEventUtils.makeKeys(param.getChnlId(), param.getMenuId());

        if(featureSwitch.get(FeatureKey.PROMO_EVENT_FORCE_DB) || param.isUseDb() || connectionFactory == null) {
            RawPromotionEvent rawPromotionEvent = eventDataService.getLivePromotionEventForUser(tenantId, param.getChnlId(), param.getMenuId(), userKey);
            return PromotionEventConverter.convert(rawPromotionEvent);
        }

        Date now = getNow(param);

        Plandasj client = connectionFactory.getConnectionPool().getClient();

        List<byte[]> liveEvents = PromotionEventRedisHelper.getLiveEventDatas(client, tenantId, keys);

        // 조회된 liveEvent 순서대로 유효한 이벤트 정보를 찾아 응답한다.
        for (int i = 0; i < liveEvents.size(); ++i) {

            // PromotionEvent 변경 후에는 먼저 동기화가 수행되어야 한다.
            // FIXME 기존 데이터도 사용이 가능 해야 한다. "해시코드:키" 를 이용하는 방법에 문제가 있음!
            // 데이터 조회 후 해시코드를 확인하여 다른 경우 재 동기화 하는 방법을 활용

            byte[] data = liveEvents.get(i);
            if(data == null)
                continue;

            String key = keys[i];
            PromotionEvent event = PromotionEventConverter.convert(data);

            // 정상적인 데이터가 아닌 경우 다시 동기화 처리를 수행한다
            if(event == null) {
                SyncPromotionEventResult eventResult = promotionEventSyncService.syncPromotionEvent(tenantId, key, false);
                if(eventResult.getUpdtCnt() > 0) {
                    event = eventResult.getLiveEventMap().get(tenantId + ":" + key);
                }
                else
                    continue;
            }

            // now > liveEvent.endDt 이면 다음 이벤트를 설정
            if (now.compareTo(event.getEndDt()) > 0) {
                event = findNextIncommingEvent(client, tenantId, key, event, now);
            }

            ///// 유효한 이벤트인지 판단 /////
            if(event == null)
                continue;

            if(!event.isLiveFor(now))
                continue;

            if(event.getUserTargetTp().equals("DP01400001"))
                return event;

            if(StringUtils.isNotEmpty(userKey)) {
                if(PromotionEventRedisHelper.checkTargetUsers(client, event, userKey))
                    return event;
            }
        }   ///// END OF FOR LOOP

        return null;
    }

    /**
     * 현재 시간을 구한다.
     * @param param
     * @return
     */
    private Date getNow(GetPromotionEventParam param) {
        Date now = param.getNowDt();
        if(now == null) {
            if (featureSwitch.get(FeatureKey.PROMO_EVENT_USE_SYSTIME))
                now = new Date();
            else
                now = displayCommonService.getDbDateTime();
        }
        else
            now = DateUtils.truncate(now, Calendar.MILLISECOND);

        return now;
    }

    private PromotionEvent findNextIncommingEvent(Plandasj client, String tenantId, String key, PromotionEvent event, Date now) {
        // TODO jitter에 의한 의도하지 않은 이벤트 transition 처리 방안 => 임계치를 적용하여 처리함
        // TODO eventTransition:[beforePromId]:[afterPromId] => numeric

        String prevDatetimeKey = event.makeDatetimeKey(),
                       fullKey = tenantId + ":" + key;

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

            return incommingEvent;
        }

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

        return null;
    }

}
