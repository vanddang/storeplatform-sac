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
 * OtherAppVersionRes
 * </p>
 * Updated on : 2015. 04. 29 Updated by : 정희원, SK 플래닛.
 */
public class OtherAppMapgVersionRes extends CommonInfo {

    private static final long serialVersionUID = 1L;

    private String prodId;
    private String packageName;
    private Integer verCd;
    private String ver;

    public OtherAppMapgVersionRes(String prodId, String packageName, Integer verCd, String ver) {
        this.prodId = prodId;
        this.packageName = packageName;
        this.verCd = verCd;
        this.ver = ver;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Integer getVerCd() {
        return verCd;
    }

    public void setVerCd(Integer verCd) {
        this.verCd = verCd;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }
}
