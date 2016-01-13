package com.skplanet.storeplatform.sac.client.display.vo.other;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Updated on : 2016-01-08. Updated by : 양해엽, SK Planet.
 */
public class OtherAppApkSignedHashReq {
    @NotBlank
    private String packageName;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
