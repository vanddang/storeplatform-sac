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
 * MemberInfo
 * </p>
 * Updated on : 2014. 07. 09 Updated by : 정희원, SK 플래닛.
 */
public class MemberInfo {

    private String userKey;
    private String deviceKey;

    public MemberInfo(String userKey, String deviceKey) {
        this.userKey = userKey;
        this.deviceKey = deviceKey;
    }

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
}
