package com.skplanet.storeplatform.sac.display.common.vo;

/**
 * MenuItemReq
 * Updated on : 2014. 01. 24 Updated by : 정희원, SK 플래닛.
 */
public class MenuItemReq {
    private String prodId;
    private String langCd;

    public MenuItemReq() {}
    public MenuItemReq(String prodId, String langCd) {
        this.prodId = prodId;
        this.langCd = langCd;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getLangCd() {
        return langCd;
    }

    public void setLangCd(String langCd) {
        this.langCd = langCd;
    }
}
