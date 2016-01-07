/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>
 * 서비스 제한 정책 Entity
 * </p>
 * Updated on : 2016. 1. 6 Updated by : 임근대, SK 플래닛.
 */
@Entity
@Table(name = "TB_US_LIMT_TARGET")
public class LimitTarget {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LIMIT_TARGET_SEQ")
    @SequenceGenerator(name = "LIMIT_TARGET_SEQ",
            sequenceName="SQ_US_LIMT_TARGET",
            allocationSize=20)
    Integer seq;

    String tenantId;
    String limtPolicyCd;
    Date startDt;
    Date endDt;
    String policyApplyValue;
    String limtPolicyKey;
    String preLimtAmt;
    Character pmtType;
    Character useYn;
    String limtAmt;
    String insDt;
    String lineMangStatus;

    Date regDt;
    String regId;
    Date updDt;
    String updId;

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

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

    public String getPolicyApplyValue() {
        return policyApplyValue;
    }

    public void setPolicyApplyValue(String policyApplyValue) {
        this.policyApplyValue = policyApplyValue;
    }

    public Date getRegDt() {
        return regDt;
    }

    public void setRegDt(Date regDt) {
        this.regDt = regDt;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public Date getUpdDt() {
        return updDt;
    }

    public void setUpdDt(Date updDt) {
        this.updDt = updDt;
    }

    public String getUpdId() {
        return updId;
    }

    public void setUpdId(String updId) {
        this.updId = updId;
    }

    public String getLimtPolicyKey() {
        return limtPolicyKey;
    }

    public void setLimtPolicyKey(String limtPolicyKey) {
        this.limtPolicyKey = limtPolicyKey;
    }

    public String getPreLimtAmt() {
        return preLimtAmt;
    }

    public void setPreLimtAmt(String preLimtAmt) {
        this.preLimtAmt = preLimtAmt;
    }

    public Character getPmtType() {
        return pmtType;
    }

    public void setPmtType(Character pmtType) {
        this.pmtType = pmtType;
    }

    public Character getUseYn() {
        return useYn;
    }

    public void setUseYn(Character useYn) {
        this.useYn = useYn;
    }

    public String getLimtAmt() {
        return limtAmt;
    }

    public void setLimtAmt(String limtAmt) {
        this.limtAmt = limtAmt;
    }

    public String getInsDt() {
        return insDt;
    }

    public void setInsDt(String insDt) {
        this.insDt = insDt;
    }

    public String getLineMangStatus() {
        return lineMangStatus;
    }

    public void setLineMangStatus(String lineMangStatus) {
        this.lineMangStatus = lineMangStatus;
    }

    public String getLimtPolicyCd() {
        return limtPolicyCd;
    }

    public void setLimtPolicyCd(String limtPolicyCd) {
        this.limtPolicyCd = limtPolicyCd;
    }

    @PrePersist
    public void prePersist() {
        this.regDt = new Date();
        this.updDt = new Date();

    }

    @PreUpdate
    public void preUpdate() {
        this.updDt = new Date();
    }

}
