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


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * RawPromotionEvent
 * </p>
 * Updated on : 2015. 07. 17 Updated by : 정희원, SK 플래닛.
 */
public class RawPromotionEvent {
    private String tenantId;
    private Date startDt;
    private Date endDt;
    private String promTypeCd;
    private String promTypeValue;
    private Integer promId;
    private String promTitle;
    private String acmlMethodCd;
    private Integer acmlLimt;
    private Date acmlDate;
    private Integer rateGrd1;
    private Integer rateGrd2;
    private Integer rateGrd3;
    private Integer amtGrd1;
    private Integer amtGrd2;
    private Integer amtGrd3;
    private String regId;
    private Date regDt;
    private String updId;
    private Date updDt;
    private Integer promAcmlLimt;
    private String promForceCloseCd;
    private Date promForceCloseDt;
    private String eventIssuChrgpers;
    private String eventIssuDept;
    private String settTargetYn;
    private String developerTargetSett;
    private String storeSaleCognYn;
    private Date promAcmlLimtLastCheckDate;
    private Integer promAcmlNowAmt;
    private String targetUserKind;
    private Integer payMethodVdtyDt;
    private String targetUserFileName;
    private String linkKeyGameInfoSeq;


    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
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

    public Integer getPromId() {
        return promId;
    }

    public void setPromId(Integer promId) {
        this.promId = promId;
    }

    public String getPromTitle() {
        return promTitle;
    }

    public void setPromTitle(String promTitle) {
        this.promTitle = promTitle;
    }

    public String getAcmlMethodCd() {
        return acmlMethodCd;
    }

    public void setAcmlMethodCd(String acmlMethodCd) {
        this.acmlMethodCd = acmlMethodCd;
    }

    public Integer getAcmlLimt() {
        return acmlLimt;
    }

    public void setAcmlLimt(Integer acmlLimt) {
        this.acmlLimt = acmlLimt;
    }

    public Date getAcmlDate() {
        return acmlDate;
    }

    public void setAcmlDate(Date acmlDate) {
        this.acmlDate = acmlDate;
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

    public Integer getAmtGrd1() {
        return amtGrd1;
    }

    public void setAmtGrd1(Integer amtGrd1) {
        this.amtGrd1 = amtGrd1;
    }

    public Integer getAmtGrd2() {
        return amtGrd2;
    }

    public void setAmtGrd2(Integer amtGrd2) {
        this.amtGrd2 = amtGrd2;
    }

    public Integer getAmtGrd3() {
        return amtGrd3;
    }

    public void setAmtGrd3(Integer amtGrd3) {
        this.amtGrd3 = amtGrd3;
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

    public Integer getPromAcmlLimt() {
        return promAcmlLimt;
    }

    public void setPromAcmlLimt(Integer promAcmlLimt) {
        this.promAcmlLimt = promAcmlLimt;
    }

    public String getPromForceCloseCd() {
        return promForceCloseCd;
    }

    public void setPromForceCloseCd(String promForceCloseCd) {
        this.promForceCloseCd = promForceCloseCd;
    }

    public Date getPromForceCloseDt() {
        return promForceCloseDt;
    }

    public void setPromForceCloseDt(Date promForceCloseDt) {
        this.promForceCloseDt = promForceCloseDt;
    }

    public String getEventIssuChrgpers() {
        return eventIssuChrgpers;
    }

    public void setEventIssuChrgpers(String eventIssuChrgpers) {
        this.eventIssuChrgpers = eventIssuChrgpers;
    }

    public String getEventIssuDept() {
        return eventIssuDept;
    }

    public void setEventIssuDept(String eventIssuDept) {
        this.eventIssuDept = eventIssuDept;
    }

    public String getSettTargetYn() {
        return settTargetYn;
    }

    public void setSettTargetYn(String settTargetYn) {
        this.settTargetYn = settTargetYn;
    }

    public String getDeveloperTargetSett() {
        return developerTargetSett;
    }

    public void setDeveloperTargetSett(String developerTargetSett) {
        this.developerTargetSett = developerTargetSett;
    }

    public String getStoreSaleCognYn() {
        return storeSaleCognYn;
    }

    public void setStoreSaleCognYn(String storeSaleCognYn) {
        this.storeSaleCognYn = storeSaleCognYn;
    }

    public Date getPromAcmlLimtLastCheckDate() {
        return promAcmlLimtLastCheckDate;
    }

    public void setPromAcmlLimtLastCheckDate(Date promAcmlLimtLastCheckDate) {
        this.promAcmlLimtLastCheckDate = promAcmlLimtLastCheckDate;
    }

    public Integer getPromAcmlNowAmt() {
        return promAcmlNowAmt;
    }

    public void setPromAcmlNowAmt(Integer promAcmlNowAmt) {
        this.promAcmlNowAmt = promAcmlNowAmt;
    }

    public String getTargetUserKind() {
        return targetUserKind;
    }

    public void setTargetUserKind(String targetUserKind) {
        this.targetUserKind = targetUserKind;
    }

    public Integer getPayMethodVdtyDt() {
        return payMethodVdtyDt;
    }

    public void setPayMethodVdtyDt(Integer payMethodVdtyDt) {
        this.payMethodVdtyDt = payMethodVdtyDt;
    }

    public String getTargetUserFileName() {
        return targetUserFileName;
    }

    public void setTargetUserFileName(String targetUserFileName) {
        this.targetUserFileName = targetUserFileName;
    }

    public String getLinkKeyGameInfoSeq() {
        return linkKeyGameInfoSeq;
    }

    public void setLinkKeyGameInfoSeq(String linkKeyGameInfoSeq) {
        this.linkKeyGameInfoSeq = linkKeyGameInfoSeq;
    }
}
