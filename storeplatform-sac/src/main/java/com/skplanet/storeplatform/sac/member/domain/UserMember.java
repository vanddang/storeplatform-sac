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

import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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

    private String mbrStatusMainCd;

    private String mbrStatusSubCd;

    private Integer deviceRegCnt;

    private String enterDay;

    private String bolterDay;

    private String bolterReasonCd;

    private String bolterReasonDesc;

    private Date updDt;

    private String mbrId;

    private String mbrNm;

    private String birth;

    @Column(columnDefinition = "char(1)")
    private String sex;

    private String emailAddr;

    @Column(columnDefinition = "char(1)")
    private String emailRecvYn;

    @Column(columnDefinition = "char(1)")
    private String smsRecvYn;

    @Column(columnDefinition = "char(1)")
    private String lglAgentAgreeYn;

    private String nationCd;

    private String langCd;

    private String systemId;

    private String bolterCaseCd;

    private String loginStatusCd;

    private String updEmailAddr;

    private Date lastLoginDt;

    private Date lastPrchsDt;

    private String idpOneidCd;

    @OneToMany(mappedBy = "member")
    private List<UserDevice> devices;

    @PrePersist
    public void prePersist() {
        this.updDt = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.updDt = new Date();
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

    public String getMbrClasCd() {
        return mbrClasCd;
    }

    public void setMbrClasCd(String mbrClasCd) {
        this.mbrClasCd = mbrClasCd;
    }

    public String getMbrStatusMainCd() {
        return mbrStatusMainCd;
    }

    public void setMbrStatusMainCd(String mbrStatusMainCd) {
        this.mbrStatusMainCd = mbrStatusMainCd;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmailAddr() {
        return emailAddr;
    }

    public void setEmailAddr(String emailAddr) {
        emailAddr = emailAddr;
    }

    public String getMbrStatusSubCd() {
        return mbrStatusSubCd;
    }

    public void setMbrStatusSubCd(String mbrStatusSubCd) {
        this.mbrStatusSubCd = mbrStatusSubCd;
    }

    public Integer getDeviceRegCnt() {
        return deviceRegCnt;
    }

    public void setDeviceRegCnt(Integer deviceRegCnt) {
        this.deviceRegCnt = deviceRegCnt;
    }

    public String getEnterDay() {
        return enterDay;
    }

    public void setEnterDay(String enterDay) {
        this.enterDay = enterDay;
    }

    public String getBolterDay() {
        return bolterDay;
    }

    public void setBolterDay(String bolterDay) {
        this.bolterDay = bolterDay;
    }

    public String getBolterReasonCd() {
        return bolterReasonCd;
    }

    public void setBolterReasonCd(String bolterReasonCd) {
        this.bolterReasonCd = bolterReasonCd;
    }

    public String getBolterReasonDesc() {
        return bolterReasonDesc;
    }

    public void setBolterReasonDesc(String bolterReasonDesc) {
        this.bolterReasonDesc = bolterReasonDesc;
    }

    public Date getUpdDt() {
        return updDt;
    }

    public void setUpdDt(Date updDt) {
        this.updDt = updDt;
    }

    public String getEmailRecvYn() {
        return emailRecvYn;
    }

    public void setEmailRecvYn(String emailRecvYn) {
        this.emailRecvYn = emailRecvYn;
    }

    public String getSmsRecvYn() {
        return smsRecvYn;
    }

    public void setSmsRecvYn(String smsRecvYn) {
        this.smsRecvYn = smsRecvYn;
    }

    public String getLglAgentAgreeYn() {
        return lglAgentAgreeYn;
    }

    public void setLglAgentAgreeYn(String lglAgentAgreeYn) {
        this.lglAgentAgreeYn = lglAgentAgreeYn;
    }

    public String getNationCd() {
        return nationCd;
    }

    public void setNationCd(String nationCd) {
        this.nationCd = nationCd;
    }

    public String getLangCd() {
        return langCd;
    }

    public void setLangCd(String langCd) {
        this.langCd = langCd;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getBolterCaseCd() {
        return bolterCaseCd;
    }

    public void setBolterCaseCd(String bolterCaseCd) {
        this.bolterCaseCd = bolterCaseCd;
    }

    public String getLoginStatusCd() {
        return loginStatusCd;
    }

    public void setLoginStatusCd(String loginStatusCd) {
        this.loginStatusCd = loginStatusCd;
    }

    public String getUpdEmailAddr() {
        return updEmailAddr;
    }

    public void setUpdEmailAddr(String updEmailAddr) {
        this.updEmailAddr = updEmailAddr;
    }

    public Date getLastLoginDt() {
        return lastLoginDt;
    }

    public void setLastLoginDt(Date lastLoginDt) {
        this.lastLoginDt = lastLoginDt;
    }

    public Date getLastPrchsDt() {
        return lastPrchsDt;
    }

    public void setLastPrchsDt(Date lastPrchsDt) {
        this.lastPrchsDt = lastPrchsDt;
    }

    public String getIdpOneidCd() {
        return idpOneidCd;
    }

    public void setIdpOneidCd(String idpOneidCd) {
        this.idpOneidCd = idpOneidCd;
    }

    public List<UserDevice> getDevices() {
        return devices;
    }

    public void setDevices(List<UserDevice> devices) {
        this.devices = devices;
    }

    public boolean isAvailable() {
        return !MemberConstants.MAIN_STATUS_SECEDE.equals(mbrStatusMainCd);
    }
}
