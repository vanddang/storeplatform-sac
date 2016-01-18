package com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Updated on : 2016-01-08. Updated by : 양해엽, SK Planet.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AppApk {
    private String packageName;
    private String apkSignedKeyHash;

    public AppApk() {
    }

    public AppApk(String packageName, String apkSignedKeyHash) {
        this.packageName = packageName;
        this.apkSignedKeyHash = apkSignedKeyHash;
    }


    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getApkSignedKeyHash() {
        return apkSignedKeyHash;
    }

    public void setApkSignedKeyHash(String apkSignedKeyHash) {
        this.apkSignedKeyHash = apkSignedKeyHash;
    }
}
