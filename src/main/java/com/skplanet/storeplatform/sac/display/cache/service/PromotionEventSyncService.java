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

import com.skplanet.storeplatform.sac.display.cache.vo.RawPromotionEvent;
import com.skplanet.storeplatform.sac.display.cache.vo.SyncPromotionEventResult;

import java.util.List;

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

    /**
     *
     * @param tenantId
     * @param keys
     * @param liveOnly true인 경우 현재시간(DB) 기준으로 유효한 것만, false인 경우 진행중인것과 예정인 이벤트 모두
     * @return
     */
    List<RawPromotionEvent> getRawEventList(String tenantId, List<String> keys, boolean liveOnly);

    List<RawPromotionEvent> getRawEventList(String tenantId, List<Integer> promIdList);

}
