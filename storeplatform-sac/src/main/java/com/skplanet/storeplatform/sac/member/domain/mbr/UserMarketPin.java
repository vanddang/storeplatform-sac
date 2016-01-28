package com.skplanet.storeplatform.sac.member.domain.mbr;

import com.skplanet.storeplatform.sac.member.domain.shared.UserMember;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TB_US_OUSERMBR_PIN")
public class UserMarketPin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INSD_USERMBR_NO")
    private UserMember member;

    private String pinNo;
    private Integer authFailCnt;

    public UserMarketPin() {}

    public UserMarketPin(UserMember member, String pinNo, Integer authFailCnt) {
        this.member = member;
        this.pinNo = pinNo;
        this.authFailCnt = authFailCnt;
    }

    public String getPinNo() {
        return pinNo;
    }

    public void setPinNo(String pinNo) {
        this.pinNo = pinNo;
    }

    public Integer getAuthFailCnt() {
        return authFailCnt;
    }

    public void setAuthFailCnt(Integer authFailCnt) {
        this.authFailCnt = authFailCnt;
    }

    public UserMember getMember() {
        return member;
    }

    public void setMember(UserMember member) {
        this.member = member;
    }

    @PrePersist
    public void prePersist() {
        this.setAuthFailCnt(0);
    }
}
