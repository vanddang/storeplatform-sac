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

import java.util.Date;

/**
 * <p>
 * UserDownloadInfoRes
 * </p>
 * Updated on : 2015. 03. 11 Updated by : 정희원, SK 플래닛.
 */
public class UserDownloadInfoRes extends CommonInfo {
    private static final long serialVersionUID = 1L;

    private String latestTenantId;
    @Deprecated
    private Date latestDownloadDate;
    @Deprecated
    private String prodId;

    public UserDownloadInfoRes(String latestTenantId) {
        this.latestTenantId = latestTenantId;
    }

    public String getLatestTenantId() {
        return latestTenantId;
    }

    public void setLatestTenantId(String latestTenantId) {
        this.latestTenantId = latestTenantId;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public Date getLatestDownloadDate() {
        return latestDownloadDate;
    }

    public void setLatestDownloadDate(Date latestDownloadDate) {
        this.latestDownloadDate = latestDownloadDate;
    }
}
