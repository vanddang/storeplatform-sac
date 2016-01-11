package com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Updated on : 2016-01-08. Updated by : 양해엽, SK Planet.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AppApk {
    private String packageName;
    private String apkSignedHash;

    public AppApk() {
    }

    public AppApk(String packageName, String apkSignedHash) {
        this.packageName = packageName;
        this.apkSignedHash = apkSignedHash;
    }


    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getApkSignedHash() {
        return apkSignedHash;
    }

    public void setApkSignedHash(String apkSignedHash) {
        this.apkSignedHash = apkSignedHash;
    }
}
