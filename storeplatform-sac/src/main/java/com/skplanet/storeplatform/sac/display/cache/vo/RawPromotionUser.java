package com.skplanet.storeplatform.sac.display.cache.vo;

import java.util.Date;

public class RawPromotionUser {
    private Integer promId;
    private String userKey;
    private String deviceKeyList;
    private String regId;
    private Date regDt;
    private String updId;
    private Date updDt;

    public Integer getPromId() {
        return promId;
    }

    public void setPromId(Integer promId) {
        this.promId = promId;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getDeviceKeyList() {
        return deviceKeyList;
    }

    public void setDeviceKeyList(String deviceKeyList) {
        this.deviceKeyList = deviceKeyList;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public Date getRegDt() {
        return regDt;
    }

    public void setRegDt(Date regDt) {
        this.regDt = regDt;
    }

    public String getUpdId() {
        return updId;
    }

    public void setUpdId(String updId) {
        this.updId = updId;
    }

    public Date getUpdDt() {
        return updDt;
    }

    public void setUpdDt(Date updDt) {
        this.updDt = updDt;
    }
}
