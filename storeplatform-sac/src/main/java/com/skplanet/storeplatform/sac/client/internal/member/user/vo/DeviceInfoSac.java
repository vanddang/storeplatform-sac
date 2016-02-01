package com.skplanet.storeplatform.sac.client.internal.member.user.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 회원 정보.
 *
 * Updated on : 2016. 2. 1. Updated by : 윤보영, 카레즈.
 */
public class DeviceInfoSac extends CommonInfo {

    /**
     * 기기 ID
     */
    private String deviceId;

    /**
     * MDN
     */
    private String mdn;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getMdn() {
        return mdn;
    }

    public void setMdn(String mdn) {
        this.mdn = mdn;
    }
}
