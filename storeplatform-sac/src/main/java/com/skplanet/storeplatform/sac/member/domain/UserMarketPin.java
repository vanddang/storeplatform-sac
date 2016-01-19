package com.skplanet.storeplatform.sac.member.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "TB_US_OUSERMBR_PIN")
public class UserMarketPin {

    @Id
    private String insdUsermbrNo;

    private String pinNo;
    private Date rnameAuthDate;
    private String rnameAuthMdn;
    private Integer authFailCnt;

    public UserMarketPin() {}

    public UserMarketPin(String insdUsermbrNo, String pinNo, Integer authFailCnt) {
        this.insdUsermbrNo = insdUsermbrNo;
        this.pinNo = pinNo;
        this.authFailCnt = authFailCnt;
    }

    public String getInsdUsermbrNo() {
        return insdUsermbrNo;
    }

    public void setInsdUsermbrNo(String insdUsermbrNo) {
        this.insdUsermbrNo = insdUsermbrNo;
    }

    public String getPinNo() {
        return pinNo;
    }

    public void setPinNo(String pinNo) {
        this.pinNo = pinNo;
    }

    public Date getRnameAuthDate() {
        return rnameAuthDate;
    }

    public void setRnameAuthDate(Date rnameAuthDate) {
        this.rnameAuthDate = rnameAuthDate;
    }

    public String getRnameAuthMdn() {
        return rnameAuthMdn;
    }

    public void setRnameAuthMdn(String rnameAuthMdn) {
        this.rnameAuthMdn = rnameAuthMdn;
    }

    public Integer getAuthFailCnt() {
        return authFailCnt;
    }

    public void setAuthFailCnt(Integer authFailCnt) {
        this.authFailCnt = authFailCnt;
    }

    //public void createUserPin()
}
