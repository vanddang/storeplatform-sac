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
 * OtherTenantProductMappingRes
 * </p>
 * Updated on : 2015. 01. 19 Updated by : 정희원, SK 플래닛.
 */
public class OtherTenantProductMappingRes extends CommonInfo {
    private static final long serialVersionUID = 1L;

    public OtherTenantProductMappingRes() {}

    public OtherTenantProductMappingRes(String tenantId, String tstoreProdId) {
        this.tenantId = tenantId;
        this.tstoreProdId = tstoreProdId;
    }

    private String tenantId;
    private String tstoreProdId;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTstoreProdId() {
        return tstoreProdId;
    }

    public void setTstoreProdId(String tstoreProdId) {
        this.tstoreProdId = tstoreProdId;
    }
}
