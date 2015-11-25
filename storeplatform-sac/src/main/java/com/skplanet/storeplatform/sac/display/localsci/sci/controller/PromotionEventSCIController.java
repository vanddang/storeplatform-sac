/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.localsci.sci.controller;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.PromotionEventSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PromotionEventItem;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PromotionEventReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PromotionEventRes;
import com.skplanet.storeplatform.sac.display.cache.service.PromotionEventSyncService;
import com.skplanet.storeplatform.sac.display.cache.vo.RawPromotionEvent;
import com.skplanet.storeplatform.sac.display.promotion.PromotionEventDataService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * PromotionEventSCIController
 * TODO memDb 기반으로 변경
 * </p>
 * Updated on : 2015. 08. 19 Updated by : 정희원, SK 플래닛.
 */
@LocalSCI
public class PromotionEventSCIController implements PromotionEventSCI {

    @Autowired
    private PromotionEventDataService eventDataService;

    @Override
    public PromotionEventRes getPromotionEvent(PromotionEventReq req) {
        Preconditions.checkNotNull(req);
        Preconditions.checkNotNull(req.getTenantId());
        Preconditions.checkNotNull(req.getPromIdList());

        Map<Integer, PromotionEventItem> eventMap = Maps.newHashMap();
        List<RawPromotionEvent> rawEventList = eventDataService.getRawEventList(req.getTenantId(), req.getPromIdList());

        for (RawPromotionEvent event : rawEventList) {

            PromotionEventItem item = new PromotionEventItem();
            item.setPrivateAcmlLimit(event.getAcmlLimt());
            eventMap.put(event.getPromId(), item);
        }

        PromotionEventRes res = new PromotionEventRes();
        res.setPromotionEventMap(eventMap);

        return res;
    }
}
