package com.skplanet.storeplatform.sac.display.app.vo;

import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;

/**
 * 앱 상품 상세 Request
 *
 * Updated on : 2014. 01. 06. Updated by : 정희원, SK 플래닛.
 */
public class AppDetailParam {

    private String tenantId;
    private String langCd;
    private String channelId;
    private String deviceModelCd;
    private String userKey;
    private String deviceKey;

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public String getDeviceModelCd() {
        return deviceModelCd;
    }

    public void setDeviceModelCd(String deviceModelCd) {
        this.deviceModelCd = deviceModelCd;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getLangCd() {
        return langCd;
    }

    public void setLangCd(String langCd) {
        this.langCd = langCd;
    }

    public String getStandardModel() {
        return DisplayConstants.DP_ANDROID_STANDARD2_NM;
    }
}
