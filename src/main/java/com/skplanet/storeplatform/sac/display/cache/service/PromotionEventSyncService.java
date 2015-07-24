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

import com.skplanet.storeplatform.sac.display.cache.vo.PromotionEventWrapper;
import com.skplanet.storeplatform.sac.display.cache.vo.SyncPromotionEventResult;

/**
 * <p>
 * DESC
 * </p>
 * Updated on : 2015. 07. 24 Updated by : 정희원, SK 플래닛.
 */
public interface PromotionEventSyncService {

    /**
     * 모든 프로모션 이벤트들을 동기화 한다.
     * @return
     */
    SyncPromotionEventResult syncAllPromotionEvent();

    /**
     * 특정 프로모션 이벤트를 동기화 한다.
     * @param tenantId
     * @param key
     * @return
     */
    PromotionEventWrapper syncPromotionEvent(String tenantId, String key);

}
