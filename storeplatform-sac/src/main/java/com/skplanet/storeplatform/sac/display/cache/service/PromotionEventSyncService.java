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

import com.skplanet.storeplatform.sac.display.cache.vo.PromotionEvent;
import com.skplanet.storeplatform.sac.display.cache.vo.SyncPromotionEventResult;

import java.util.Set;

/**
 * <p>
 * PromotionEventSyncService
 * </p>
 * Updated on : 2015. 07. 24 Updated by : 정희원, SK 플래닛.
 */
public interface PromotionEventSyncService {

    /**
     * 모든 프로모션 이벤트들을 동기화 한다.
     * @param tenantId 대상이 되는 테넌트ID
     * @param key 동기화 대상이 되는 메뉴ID 또는 상품ID
     * @param forceUpdate 강제로 업데이트 하는 경우 true
     * @return 결과 객체
     */
    SyncPromotionEventResult syncPromotionEvent(String tenantId, String key, boolean forceUpdate);

    SyncPromotionEventResult syncPromotionEvent(String tenantId, String key);

    // 새로 작성한 프로모션 코드
    Thread syncPromotionInBackground(final String tenantId, final String promTypeValue);
    PromotionEvent syncPromotion(String tenantId, String promTypeValue);
    // tenantId 기준 전체 프로모션 데이터를 동기화 하므로 자주 사용되지 않도록 주의
    Set<PromotionEvent> syncPromotion(String tenantId);
}
