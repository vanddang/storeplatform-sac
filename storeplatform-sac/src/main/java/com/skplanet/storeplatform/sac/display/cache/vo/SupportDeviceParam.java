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

/**
 * <p>
 * SupportDeviceParam
 * </p>
 * Updated on : 2015. 11. 05 Updated by : 정희원, SK 플래닛.
 */
public class SupportDeviceParam {
    private String prodId;
    private String deviceModelCd;

    public SupportDeviceParam(String prodId, String deviceModelCd) {
        this.prodId = prodId;
        this.deviceModelCd = deviceModelCd;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getDeviceModelCd() {
        return deviceModelCd;
    }

    public void setDeviceModelCd(String deviceModelCd) {
        this.deviceModelCd = deviceModelCd;
    }
}
