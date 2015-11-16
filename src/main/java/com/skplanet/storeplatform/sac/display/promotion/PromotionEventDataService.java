/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.promotion;

import com.skplanet.storeplatform.sac.display.cache.vo.RawPromotionEvent;

import java.util.List;

/**
 * <p>
 * PromotionEventDataService
 * 프로모션 이벤트 처리에 필요한 DB Access를 담당합니다
 * </p>
 * Updated on : 2015. 10. 22 Updated by : 정희원, SK 플래닛.
 */
public interface PromotionEventDataService {

    /**
     * 모두
     */
    int GET_RAW_EVENT_BY_ALL = 0;
    /**
     * 진행중이거나 예정인 이벤트
     */
    int GET_RAW_EVENT_BY_READY = 1;
    /**
     * 라이브 상태의 이벤트
     */
    int GET_RAW_EVENT_BY_LIVE = 2;

    /**
     * 사용자에 해당하는 라이브 프로모션 이벤트를 조회한다.
     * @param tenantId
     * @param chnlId
     *@param userKey  @return
     */
    RawPromotionEvent getLivePromotionEventForUser(String tenantId, String chnlId, String menuId, String userKey);

    RawPromotionEvent getLivePromotionEventForUser(String tenantId, String iapProdId, String chnlId, String menuId, String userKey);

    /**
     * 프로모션 참여자 목록 조회
     * @param promId
     * @return
     */
    List<String> getPromotionUserList(int promId);

    /**
     *
     * @param tenantId
     * @param keys
     * @param filterCode GET_RAW_EVENT_BY_ALL, GET_RAW_EVENT_BY_READY, GET_RAW_EVENT_BY_LIVE
     * @return
     */
    List<RawPromotionEvent> getRawEventList(String tenantId, List<String> keys, int filterCode);

    List<RawPromotionEvent> getRawEventList(String tenantId, List<Integer> promIdList);

}
