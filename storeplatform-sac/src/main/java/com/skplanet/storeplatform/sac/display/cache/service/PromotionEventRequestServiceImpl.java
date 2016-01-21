package com.skplanet.storeplatform.sac.display.cache.service;

import com.skplanet.plandasj.Plandasj;
import com.skplanet.spring.data.plandasj.PlandasjConnectionFactory;
import com.skplanet.storeplatform.sac.common.support.featureswitch.FeatureKey;
import com.skplanet.storeplatform.sac.common.support.featureswitch.SacFeatureSwitchAccessor;
import com.skplanet.storeplatform.sac.display.cache.SacRedisKeys;
import com.skplanet.storeplatform.sac.display.cache.vo.GetPromotionEventParam;
import com.skplanet.storeplatform.sac.display.cache.vo.PromotionEvent;
import com.skplanet.storeplatform.sac.display.cache.vo.RawPromotionEvent;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.promotion.PromotionEventDataService;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class PromotionEventRequestServiceImpl implements PromotionEventRequestService{
    private static final Logger logger = LoggerFactory.getLogger(PromotionEventRequestServiceImpl.class);
    @Autowired
    private SacFeatureSwitchAccessor featureSwitch;

    @Autowired(required = false)
    private PlandasjConnectionFactory connectionFactory;

    @Autowired
    private PromotionEventDataService eventDataService;

    @Autowired
    private DisplayCommonService displayCommonService;

    @Autowired
    private PromotionEventSyncService promotionEventSyncService;

    @Override
    public PromotionEvent getPromotionEvent(GetPromotionEventParam param) {

        if(featureSwitch.get(FeatureKey.PROMO_EVENT_FORCE_DB) || param.isUseDb() || connectionFactory == null) {
            RawPromotionEvent rawPromotionEvent = eventDataService.getLivePromotionEventForUser(param.getTenantId(), param.getChnlId(), param.getMenuId(), param.getUserKey());
            return PromotionEventConverter.convert(rawPromotionEvent);
        }

        return promotionEventFromPlandas(param);
    }

    private PromotionEvent promotionEventFromPlandas(GetPromotionEventParam param) {
        String field1 = SacRedisKeys.promotionEventField(param.getTenantId(), param.getChnlId());
        String field2 = SacRedisKeys.promotionEventField(param.getTenantId(), toFirstDepthMenuId(param.getMenuId()));
        String field3 = SacRedisKeys.promotionEventField(param.getTenantId(), toSecondDepthMenuId(param.getMenuId()));

        Plandasj plandasj = connectionFactory.getConnectionPool().getClient();
        List<byte[]> datum = plandasj.hmget(SacRedisKeys.promotionEventKey().getBytes(), field1.getBytes(), field2.getBytes(), field3.getBytes());
        Date now = getNow(param);
        for (byte[] data : datum) {
            PromotionEvent promotionEvent = PromotionEventConverter.convert(data);

            if (data == null) {
                continue;
            }

            // 이벤트 지난 경우 다시 동기화
            if (now.compareTo(promotionEvent.getEndDt()) > 0) {
                promotionEventSyncService.syncPromotionInBackground(param.getTenantId(), promotionEvent.getTargetId());
                continue;
            }

            if (isValidEvent(promotionEvent, now, param.getTenantId(), param.getUserKey())) {
                return promotionEvent;
            }

        }
        return null;
    }

    private boolean isValidEvent(PromotionEvent promotionEvent, Date now, String tenantId, String userKey) {
        Plandasj plandasj = connectionFactory.getConnectionPool().getClient();

        if (promotionEvent == null) return false;

        if (!promotionEvent.isLiveFor(now)) return false;

        String key = SacRedisKeys.promotionEventTargetUserKey(tenantId, promotionEvent.getTargetId());
        if (promotionEvent.isNeedsUserTargetSync() && !plandasj.sismember(key, userKey)) {
            return false;
        }

        return true;
    }

    private String toFirstDepthMenuId(String menuId) {
        final int topMenuIdLength = 4;
        if (menuId == null) return null;

        if(menuId.length() > topMenuIdLength) {
            return menuId.substring(0, topMenuIdLength);
        }

        return menuId;
    }

    private String toSecondDepthMenuId(String menuId) {
        final int topMenuIdLength = 4;
        if (menuId == null) return null;
        if (menuId.length() <= topMenuIdLength) {
            return null;
        }
        return menuId;
    }

    private Date getNow(GetPromotionEventParam param) {
        Date now = param.getNowDt();
        if (now != null) {
            return DateUtils.truncate(now, Calendar.MILLISECOND);
        }

        if (featureSwitch.get(FeatureKey.PROMO_EVENT_USE_SYSTIME)) {
            return new Date();
        }

        return displayCommonService.getDbDateTime();
    }
}
