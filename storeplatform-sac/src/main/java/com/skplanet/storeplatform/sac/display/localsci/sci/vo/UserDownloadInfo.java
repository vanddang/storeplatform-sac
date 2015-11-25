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

import java.util.Date;

/**
 * <p>
 * UserDownloadInfo
 * </p>
 * Updated on : 2015. 03. 16 Updated by : 정희원, SK 플래닛.
 */
public class UserDownloadInfo {

    private String latestTenantId;
    private Date latestDownloadDate;
    private String prodId;

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
