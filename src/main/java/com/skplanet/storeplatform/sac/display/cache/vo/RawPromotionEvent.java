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

import com.google.common.base.Objects;

import java.util.Date;

/**
 * <p>
 * RawPromotionEvent
 * </p>
 * Updated on : 2015. 07. 17 Updated by : 정희원, SK 플래닛.
 */
public class RawPromotionEvent {

    private String tenantId;
    private String promTypeCd;
    private String promTypeValue;
    private String eventKey;

    private Integer promId;
    private String acmlMethodCd;
    private String acmlDt;
    private Integer rateGrd1;
    private Integer rateGrd2;
    private Integer rateGrd3;

    private Date startDt;
    private Date endDt;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getPromTypeCd() {
        return promTypeCd;
    }

    public void setPromTypeCd(String promTypeCd) {
        this.promTypeCd = promTypeCd;
    }

    public String getPromTypeValue() {
        return promTypeValue;
    }

    public void setPromTypeValue(String promTypeValue) {
        this.promTypeValue = promTypeValue;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public Integer getPromId() {
        return promId;
    }

    public void setPromId(Integer promId) {
        this.promId = promId;
    }

    public String getAcmlMethodCd() {
        return acmlMethodCd;
    }

    public void setAcmlMethodCd(String acmlMethodCd) {
        this.acmlMethodCd = acmlMethodCd;
    }

    public String getAcmlDt() {
        return acmlDt;
    }

    public void setAcmlDt(String acmlDt) {
        this.acmlDt = acmlDt;
    }

    public Integer getRateGrd1() {
        return rateGrd1;
    }

    public void setRateGrd1(Integer rateGrd1) {
        this.rateGrd1 = rateGrd1;
    }

    public Integer getRateGrd2() {
        return rateGrd2;
    }

    public void setRateGrd2(Integer rateGrd2) {
        this.rateGrd2 = rateGrd2;
    }

    public Integer getRateGrd3() {
        return rateGrd3;
    }

    public void setRateGrd3(Integer rateGrd3) {
        this.rateGrd3 = rateGrd3;
    }

    public Date getStartDt() {
        return startDt;
    }

    public void setStartDt(Date startDt) {
        this.startDt = startDt;
    }

    public Date getEndDt() {
        return endDt;
    }

    public void setEndDt(Date endDt) {
        this.endDt = endDt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RawPromotionEvent that = (RawPromotionEvent) o;

        if (eventKey != null ? !eventKey.equals(that.eventKey) : that.eventKey != null) return false;
        if (promId != null ? !promId.equals(that.promId) : that.promId != null) return false;
        if (acmlMethodCd != null ? !acmlMethodCd.equals(that.acmlMethodCd) : that.acmlMethodCd != null) return false;
        if (acmlDt != null ? !acmlDt.equals(that.acmlDt) : that.acmlDt != null) return false;
        if (rateGrd1 != null ? !rateGrd1.equals(that.rateGrd1) : that.rateGrd1 != null) return false;
        if (rateGrd2 != null ? !rateGrd2.equals(that.rateGrd2) : that.rateGrd2 != null) return false;
        return !(rateGrd3 != null ? !rateGrd3.equals(that.rateGrd3) : that.rateGrd3 != null);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(promId, rateGrd1, rateGrd2, rateGrd3, acmlMethodCd, acmlDt);
    }
}
