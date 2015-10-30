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

import java.util.List;

/**
 * <p>
 * GetPromotionEventReq
 * </p>
 * Updated on : 2015. 08. 19 Updated by : 정희원, SK 플래닛.
 */
public class PromotionEventReq extends CommonInfo {
    private static final long serialVersionUID = 1L;

    private String tenantId;
    private List<Integer> promIdList;

    public PromotionEventReq() {}

    public PromotionEventReq(String tenantId, List<Integer> promIdList) {
        this.tenantId = tenantId;
        this.promIdList = promIdList;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public List<Integer> getPromIdList() {
        return promIdList;
    }

    public void setPromIdList(List<Integer> promIdList) {
        this.promIdList = promIdList;
    }
}
