/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.cache.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * <p>
 * SubContentParam
 * </p>
 * Updated on : 2014. 04. 25 Updated by : 정희원, SK 플래닛.
 */
public class SubContentParam extends CommonInfo {
    private static final long serialVersionUID = 1L;

    private String channelId;
    private String deviceModel;

    public SubContentParam() {}

    public SubContentParam(String channelId, String deviceModel) {
        this.channelId = channelId;
        this.deviceModel = deviceModel;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getCacheKey() {
        return channelId + "_" + deviceModel;
    }
}
