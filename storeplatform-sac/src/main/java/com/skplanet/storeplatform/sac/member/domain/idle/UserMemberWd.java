/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.domain.idle;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * <p>
 * UserMemberWd
 * </p>
 * Updated on : 2016. 01. 04 Updated by : 정희원, SK 플래닛.
 */
@Entity
@Table(name = "TB_US_OUSERMBR_WD", schema = "SPMBR")
public class UserMemberWd {

    @Id
    private Long seq;

    private String insdUsermbrNo;

    private Date startDt;

    private Date endDt;

    private String mbrStatusMainCd;

    private String mbrStatusSubCd;

    private String entryDay;

    private String bolterDay;

    // FIXME ...


    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public String getInsdUsermbrNo() {
        return insdUsermbrNo;
    }

    public void setInsdUsermbrNo(String insdUsermbrNo) {
        this.insdUsermbrNo = insdUsermbrNo;
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

    public String getMbrStatusMainCd() {
        return mbrStatusMainCd;
    }

    public void setMbrStatusMainCd(String mbrStatusMainCd) {
        this.mbrStatusMainCd = mbrStatusMainCd;
    }

    public String getMbrStatusSubCd() {
        return mbrStatusSubCd;
    }

    public void setMbrStatusSubCd(String mbrStatusSubCd) {
        this.mbrStatusSubCd = mbrStatusSubCd;
    }

    public String getEntryDay() {
        return entryDay;
    }

    public void setEntryDay(String entryDay) {
        this.entryDay = entryDay;
    }

    public String getBolterDay() {
        return bolterDay;
    }

    public void setBolterDay(String bolterDay) {
        this.bolterDay = bolterDay;
    }
}
