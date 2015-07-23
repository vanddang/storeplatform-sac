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

import com.skplanet.storeplatform.sac.display.cache.vo.*;

import java.util.Map;

/**
 * <p>
 * 캐시된 이런저런 정보
 * </p>
 * Updated on : 2014. 06. 12 Updated by : 정희원, SK 플래닛.
 */
public interface CachedExtraInfoManager {

    UpdateProduct getUpdateProductInfo(UpdateProductParam param);

    /**
     * 패키지명으로 상품ID 조회.
     * TODO 자동업데이트시에도 이 메소드를 이용하도록 하여야 한다.
     * @param pkgNm 패키지명
     * @return 상품ID
     */
    String getProdIdByPkgNm(String pkgNm);

    /**
     * 상품ID에 할당된 패키지명 정보를 evict합니다.
     * @param prodId 상품ID
     */
    void evictPkgsInProd(String prodId);

    /**
     * <pre>
     * 상품 기본정보를 조회한다.
     * 기본정보의 정의: 상품 종류 식별에 필요한 데이터. 테넌트 종속적이지 않음. 메타 처리에 필요한 부가 속성
     * </pre>
     * @return
     */
    ProductBaseInfo getProductBaseInfo(GetProductBaseInfoParam param);

    /**
     * DB와 프로모션 데이터를 동기화 합니다.
     * @return 반영된 데이터 갯수
     */
    int syncPromotionEvent();

    /**
     * 프로모션 이벤트를 조회한다.
     * @param param 파라메터
     * @return 응답
     */
    PromotionEvent getPromotionEvent(GetPromotionEventParam param);

    /**
     * 현재 Redis에 등록되어 있는 모든 캐시 정보를 조회한다.
     * @return key는 prodId 또는 menuId이다.
     * @param liveOnly 현재 진행중인 것만 조회
     */
    Map<String, PromotionEvent> getPromotionEventMap(boolean liveOnly);
}
