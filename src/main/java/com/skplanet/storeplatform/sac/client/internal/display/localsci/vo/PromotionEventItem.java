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

/**
 * <p>
 * PromotionEventItem
 * </p>
 * Updated on : 2015. 08. 19 Updated by : 정희원, SK 플래닛.
 */
public class PromotionEventItem extends CommonInfo {
    private static final long serialVersionUID = 1L;

    /**
     * 개인 적립 한도
     */
    private Integer privateAcmlLimit;

    public Integer getPrivateAcmlLimit() {
        return privateAcmlLimit;
    }

    public void setPrivateAcmlLimit(Integer privateAcmlLimit) {
        this.privateAcmlLimit = privateAcmlLimit;
    }
}
