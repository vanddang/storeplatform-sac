package com.skplanet.storeplatform.sac.display.related.vo;

import java.util.Date;

public class RelatedProduct {
    private Date stdDt;
    private String relaType;
    private String prodId;
    private String prodIdList;
    private String regId;
    private Date regDt;
    private String updId;
    private Date updDt;

    public Date getStdDt() {
        return stdDt;
    }

    public void setStdDt(Date stdDt) {
        this.stdDt = stdDt;
    }

    public String getRelaType() {
        return relaType;
    }

    public void setRelaType(String relaType) {
        this.relaType = relaType;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getProdIdList() {
        return prodIdList;
    }

    public void setProdIdList(String prodIdList) {
        this.prodIdList = prodIdList;
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

    public String[] getProdIdListAsArray() {
        return prodIdList == null ? new String[0] : prodIdList.split(",");
    }

}
