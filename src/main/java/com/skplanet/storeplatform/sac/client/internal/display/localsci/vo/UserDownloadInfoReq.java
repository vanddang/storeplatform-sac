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

import javax.validation.constraints.NotNull;

/**
 * <p>
 * UserDownloadInfoReq
 * </p>
 * Updated on : 2015. 03. 11 Updated by : 정희원, SK 플래닛.
 */
public class UserDownloadInfoReq extends CommonInfo {
    private static final long serialVersionUID = 1L;

    @NotNull
    private String mdn;

    @Deprecated
    private String aid;

    @NotNull
    private String partProdId;

    @NotNull
    private String tenantId;

    public UserDownloadInfoReq() {}

    public UserDownloadInfoReq(String mdn, String partProdId, String tenantId) {
        this.mdn = mdn;
        this.partProdId = partProdId;
        this.tenantId = tenantId;
    }

    public String getMdn() {
        return mdn;
    }

    public void setMdn(String mdn) {
        this.mdn = mdn;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getPartProdId() {
        return partProdId;
    }

    public void setPartProdId(String partProdId) {
        this.partProdId = partProdId;
    }
}
