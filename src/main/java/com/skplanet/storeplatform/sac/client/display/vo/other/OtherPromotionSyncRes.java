/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.other;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * <p>
 * OtherPromotionSyncRes
 * </p>
 * Updated on : 2015. 07. 22 Updated by : 정희원, SK 플래닛.
 */
public class OtherPromotionSyncRes extends CommonInfo {

    private static final long serialVersionUID = 1L;

    private Integer updtCnt;

    public OtherPromotionSyncRes() {}

    public OtherPromotionSyncRes(Integer updtCnt) {
        this.updtCnt = updtCnt;
    }

    public Integer getUpdtCnt() {
        return updtCnt;
    }

    public void setUpdtCnt(Integer updtCnt) {
        this.updtCnt = updtCnt;
    }
}
