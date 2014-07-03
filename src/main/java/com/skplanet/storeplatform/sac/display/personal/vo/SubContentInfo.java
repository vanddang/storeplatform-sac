/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.personal.vo;

/**
 * <p>
 * DESC
 * </p>
 * Updated on : 2014. 07. 01 Updated by : 정희원, SK 플래닛.
 */
public class SubContentInfo {

    private String prodId;
    private String apkPkgNm;
    private Integer apkVer;
    private String subContentsId;

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getApkPkgNm() {
        return apkPkgNm;
    }

    public void setApkPkgNm(String apkPkgNm) {
        this.apkPkgNm = apkPkgNm;
    }

    public Integer getApkVer() {
        return apkVer;
    }

    public void setApkVer(Integer apkVer) {
        this.apkVer = apkVer;
    }

    public String getSubContentsId() {
        return subContentsId;
    }

    public void setSubContentsId(String subContentsId) {
        this.subContentsId = subContentsId;
    }
}
