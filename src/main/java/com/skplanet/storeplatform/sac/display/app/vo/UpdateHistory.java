package com.skplanet.storeplatform.sac.display.app.vo;

import java.util.Date;

/**
 * UpdateHistory
 * Updated on : 2014. 01. 23 Updated by : 정희원, SK 플래닛.
 */
public class UpdateHistory {

    private Date prodUpdDt;
    private String updtText;

    public Date getProdUpdDt() {
        return prodUpdDt;
    }

    public void setProdUpdDt(Date prodUpdDt) {
        this.prodUpdDt = prodUpdDt;
    }

    public String getUpdtText() {
        return updtText;
    }

    public void setUpdtText(String updtText) {
        this.updtText = updtText;
    }
}
