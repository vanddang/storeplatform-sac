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

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * <p>
 * UserMember
 * </p>
 * Updated on : 2015. 12. 24 Updated by : 정희원, SK 플래닛.
 */
@Entity
@Table(name = "TB_US_OUSERMBR", schema = "SPMBR")
public class UserMember {

    @Id
    private String insdUsermbrNo;

    private Date startDt;

    private Date endDt;

    private String mbrClasCd;

    /// ...
    private String mbrId;

    private String mbrNm;

    private String birth;

    private Character sex;

    private String emailAddr;


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

    public String getMbrClasCd() {
        return mbrClasCd;
    }

    public void setMbrClasCd(String mbrClasCd) {
        this.mbrClasCd = mbrClasCd;
    }

    public String getMbrId() {
        return mbrId;
    }

    public void setMbrId(String mbrId) {
        this.mbrId = mbrId;
    }

    public String getMbrNm() {
        return mbrNm;
    }

    public void setMbrNm(String mbrNm) {
        this.mbrNm = mbrNm;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public Character getSex() {
        return sex;
    }

    public void setSex(Character sex) {
        this.sex = sex;
    }

    public String getEmailAddr() {
        return emailAddr;
    }

    public void setEmailAddr(String emailAddr) {
        emailAddr = emailAddr;
    }
}
