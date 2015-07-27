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

import java.util.List;

/**
 * <p>
 * OtherPromotionSyncRes
 * </p>
 * Updated on : 2015. 07. 22 Updated by : 정희원, SK 플래닛.
 */
public class OtherPromotionSyncRes extends CommonInfo {

    private static final long serialVersionUID = 1L;

    /**
     * 반영된 데이터 갯수
     */
    private Integer updtCnt;

    /**
     * 처리시 에러가 발생된 프로모션Id
     */
    private List<Integer> errorPromIdList;

    public OtherPromotionSyncRes() {}

    public OtherPromotionSyncRes(Integer updtCnt, List<Integer> errorPromIdList) {
        this.updtCnt = updtCnt;
        this.errorPromIdList = errorPromIdList;
    }

    public Integer getUpdtCnt() {
        return updtCnt;
    }

    public void setUpdtCnt(Integer updtCnt) {
        this.updtCnt = updtCnt;
    }

    public List<Integer> getErrorPromIdList() {
        return errorPromIdList;
    }

    public void setErrorPromIdList(List<Integer> errorPromIdList) {
        this.errorPromIdList = errorPromIdList;
    }

    public String getErrorYn() {
        return (updtCnt == -1 || errorPromIdList.size() > 0) ? "Y" : "N";
    }
}
