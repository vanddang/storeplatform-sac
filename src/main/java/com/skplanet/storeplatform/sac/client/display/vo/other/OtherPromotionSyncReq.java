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
import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p>
 * OtherPromotionSyncReq
 * </p>
 * Updated on : 2015. 07. 22 Updated by : 정희원, SK 플래닛.
 */
public class OtherPromotionSyncReq extends CommonInfo {

    private static final long serialVersionUID = 1L;

    @NotEmpty
    private String tenantId;
    private String key;

    public OtherPromotionSyncReq() {}

    public OtherPromotionSyncReq(String tenantId, String key) {
        this.tenantId = tenantId;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
