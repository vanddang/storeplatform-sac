package com.skplanet.storeplatform.sac.client.display.vo.other;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.AppApk;

import java.util.List;

/**
 * Updated on : 2016-01-08. Updated by : 양해엽, SK Planet.
 */
public class OtherAppApkSignedHashRes extends CommonInfo {
    private static final long serialVersionUID = 1L;

    List<AppApk> appApkList;

    public OtherAppApkSignedHashRes() {
    }

    public OtherAppApkSignedHashRes(List<AppApk> appApkList) {
        this.appApkList = appApkList;
    }

    public List<AppApk> getAppApkList() {
        return appApkList;
    }

    public void setAppApkList(List<AppApk> appApkList) {
        this.appApkList = appApkList;
    }
}
