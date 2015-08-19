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
    private String datetimeKey;

    private Integer promId;
    private String acmlMethodCd;
    private Integer acmlLimt;
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

    public String getDatetimeKey() {
        return datetimeKey;
    }

    public void setDatetimeKey(String datetimeKey) {
        this.datetimeKey = datetimeKey;
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

    public Integer getAcmlLimt() {
        return acmlLimt;
    }

    public void setAcmlLimt(Integer acmlLimt) {
        this.acmlLimt = acmlLimt;
    }
}
