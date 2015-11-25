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
 * GetVersionInfoByPkgParam
 * </p>
 * Updated on : 2015. 04. 24 Updated by : 정희원, SK 플래닛.
 */
public class GetVersionInfoByPkgParam {
    private String apkPkgNm;
    private String deviceModelCd;
    private String osVersion;

    public GetVersionInfoByPkgParam(String apkPkgNm, String deviceModelCd, String osVersion) {
        this.apkPkgNm = apkPkgNm;
        this.deviceModelCd = deviceModelCd;
        this.osVersion = osVersion;
    }

    public String getApkPkgNm() {
        return apkPkgNm;
    }

    public void setApkPkgNm(String apkPkgNm) {
        this.apkPkgNm = apkPkgNm;
    }

    public String getDeviceModelCd() {
        return deviceModelCd;
    }

    public void setDeviceModelCd(String deviceModelCd) {
        this.deviceModelCd = deviceModelCd;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }
}
