/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.internal.display.localsci.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

import java.util.Map;

/**
 * <p>
 * PromotionEventRes
 * </p>
 * Updated on : 2015. 08. 19 Updated by : 정희원, SK 플래닛.
 */
public class PromotionEventRes extends CommonInfo {
    private static final long serialVersionUID = 1L;

    /**
     * 프로모션ID별 프로모션 정보 맵
     */
    private Map<Integer, PromotionEventItem> promotionEventMap;

    public Map<Integer, PromotionEventItem> getPromotionEventMap() {
        return promotionEventMap;
    }

    public void setPromotionEventMap(Map<Integer, PromotionEventItem> promotionEventMap) {
        this.promotionEventMap = promotionEventMap;
    }
}
