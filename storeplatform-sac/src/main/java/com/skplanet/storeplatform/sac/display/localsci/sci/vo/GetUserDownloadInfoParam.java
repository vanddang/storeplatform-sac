/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.localsci.sci.vo;

/**
 * <p>
 * GetUserDownloadInfoParam
 * </p>
 * Updated on : 2015. 03. 16 Updated by : 정희원, SK 플래닛.
 */
public class GetUserDownloadInfoParam {
    private String mdn;
    private String partProdId;
    private String tenantId;

    public GetUserDownloadInfoParam(String mdn, String partProdId, String tenantId) {
        this.mdn = mdn;
        this.partProdId = partProdId;
        this.tenantId = tenantId;
    }

    public String getMdn() {
        return mdn;
    }

    public String getTenantId() {
        return tenantId;
    }

    public String getPartProdId() {
        return partProdId;
    }
}
