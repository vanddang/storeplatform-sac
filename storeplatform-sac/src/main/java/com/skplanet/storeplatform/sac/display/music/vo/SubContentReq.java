package com.skplanet.storeplatform.sac.display.music.vo;

/**
 * SubContentReq
 * <p/>
 * Updated on : 2014. 01. 24 Updated by : 정희원, SK 플래닛.
 */
public class SubContentReq {
    private String prodId;

    public SubContentReq() {}

    public SubContentReq(String prodId) {
        this.prodId = prodId;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }
}
