package com.skplanet.storeplatform.sac.display.other.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Updated on : 2016-01-08. Updated by : 양해엽, SK Planet.
 */
public class AppApkInfo extends CommonInfo {
    private static final long serialVersionUID = 1L;

    private String apkPkgNm;
    private String apkSignedKeyHash;

    public AppApkInfo() {
    }

    public AppApkInfo(String apkPkgNm, String apkSignedKeyHash) {
        this.apkPkgNm = apkPkgNm;
        this.apkSignedKeyHash = apkSignedKeyHash;
    }

    public String getApkPkgNm() {
        return apkPkgNm;
    }

    public void setApkPkgNm(String apkPkgNm) {
        this.apkPkgNm = apkPkgNm;
    }

    public String getApkSignedKeyHash() {
        return apkSignedKeyHash;
    }

    public void setApkSignedKeyHash(String apkSignedKeyHash) {
        this.apkSignedKeyHash = apkSignedKeyHash;
    }
}
