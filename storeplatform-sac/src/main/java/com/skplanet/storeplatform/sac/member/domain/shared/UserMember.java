/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.domain.shared;

import com.google.common.base.Objects;
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
@Table(name = "TB_US_OUSERMBR")
public class UserMember {

    @Id
    private String insdUsermbrNo;

    private Date startDt;

    private Date endDt;

    private String mbrClasCd;

    private String mbrStatusMainCd;

    private String mbrStatusSubCd;

    private String entryDay;

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
    private String realnmAuthYn;

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

    @Transient
    private boolean fromNormal;

    @OneToMany(mappedBy = "id.member")
    private List<UserDevice> devices;

    @OneToMany(mappedBy = "member")
    private List<UserClauseAgree> clauseAgrees;

    @OneToMany(mappedBy = "member")
    private List<UserManagementItem> managementItems;

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

    public String getEntryDay() {
        return entryDay;
    }

    public void setEntryDay(String enterDay) {
        this.entryDay = enterDay;
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

    public String getRealnmAuthYn() {
        return realnmAuthYn;
    }

    public void setRealnmAuthYn(String smsRecvYn) {
        this.realnmAuthYn = smsRecvYn;
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

    public void setFromNormal(boolean fromNormal) {
        this.fromNormal = fromNormal;
    }

    public boolean isFromIdle() {
        return !fromNormal;
    }

    public List<UserDevice> getDevices() {
        return devices;
    }

    public void setDevices(List<UserDevice> devices) {
        this.devices = devices;
    }

    public List<UserClauseAgree> getClauseAgrees() {
        return clauseAgrees;
    }

    public void setClauseAgrees(List<UserClauseAgree> clauseAgrees) {
        this.clauseAgrees = clauseAgrees;
    }

    public List<UserManagementItem> getManagementItems() {
        return managementItems;
    }

    public void setManagementItems(List<UserManagementItem> managementItems) {
        this.managementItems = managementItems;
    }

    public boolean isAvailable() {
        return !MemberConstants.MAIN_STATUS_SECEDE.equals(mbrStatusMainCd);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserMember that = (UserMember) o;
        return Objects.equal(insdUsermbrNo, that.insdUsermbrNo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(insdUsermbrNo);
    }
}
