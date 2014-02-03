/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.music.vo;

import java.util.List;

/**
 * 음악 상세보기 요청 VO
 *
 * Updated on : 2014. 01. 07 Updated by : 정희원, SK 플래닛.
 */
public class MusicDetailParam {

    private String channelId;
    private String langCd;
    private String tenantId;
    private String deviceModelCd;
    private List<String> prodStatusCdList;

    public List<String> getProdStatusCdList() {
        return prodStatusCdList;
    }

    public void setProdStatusCdList(List<String> prodStatusCdList) {
        this.prodStatusCdList = prodStatusCdList;
    }

    public String getDeviceModelCd() {
        return deviceModelCd;
    }

    public void setDeviceModelCd(String deviceModelCd) {
        this.deviceModelCd = deviceModelCd;
    }

    public String getLangCd() {
        return langCd;
    }

    public void setLangCd(String langCd) {
        this.langCd = langCd;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}
