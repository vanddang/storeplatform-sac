/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.other.vo;

/**
 * <p>
 * DESC
 * </p>
 * Updated on : 2015. 04. 24 Updated by : 정희원, SK 플래닛.
 */
public class VersionInfo {
    private String prodId;
    private Integer versionCode;

    public VersionInfo(String prodId, Integer versionCode) {
        this.prodId = prodId;
        this.versionCode = versionCode;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }
}
