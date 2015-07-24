/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.cache.vo;

import java.util.List;

/**
 * <p>
 * SyncPromotionEventResult
 * </p>
 * Updated on : 2015. 07. 24 Updated by : 정희원, SK 플래닛.
 */
public class SyncPromotionEventResult {
    private int updtCnt;
    private List<Integer> errorPromIdList;

    public SyncPromotionEventResult(int updtCnt) {
        this.updtCnt = updtCnt;
    }

    public SyncPromotionEventResult(int updtCnt, List<Integer> errorPromIdList) {
        this.updtCnt = updtCnt;
        this.errorPromIdList = errorPromIdList;
    }

    public int getUpdtCnt() {
        return updtCnt;
    }

    public void setUpdtCnt(int updtCnt) {
        this.updtCnt = updtCnt;
    }

    public List<Integer> getErrorPromIdList() {
        return errorPromIdList;
    }

    public void setErrorPromIdList(List<Integer> errorPromIdList) {
        this.errorPromIdList = errorPromIdList;
    }
}
