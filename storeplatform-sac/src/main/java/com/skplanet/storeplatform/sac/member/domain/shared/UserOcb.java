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

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * UserOcb
 * </p>
 * Updated on : 2016. 01. 19 Updated by : 정희원, SK 플래닛.
 */
@Entity
@Table(name = "TB_US_OUSERMBR_OCB")
@IdClass(UserOcb.PK.class)
public class UserOcb {

    public static class PK implements Serializable {
        private UserMember member;
        private String useYn;
        private String ocbNo;
        private Date useStartDt;

        public UserMember getMember() {
            return member;
        }

        public void setMember(UserMember member) {
            this.member = member;
        }

        public String getUseYn() {
            return useYn;
        }

        public void setUseYn(String useYn) {
            this.useYn = useYn;
        }

        public String getOcbNo() {
            return ocbNo;
        }

        public void setOcbNo(String ocbNo) {
            this.ocbNo = ocbNo;
        }

        public Date getUseStartDt() {
            return useStartDt;
        }

        public void setUseStartDt(Date useStartDt) {
            this.useStartDt = useStartDt;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PK pk = (PK) o;
            return Objects.equal(member, pk.member) &&
                    Objects.equal(useYn, pk.useYn) &&
                    Objects.equal(ocbNo, pk.ocbNo) &&
                    Objects.equal(useStartDt, pk.useStartDt);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(member, useYn, ocbNo, useStartDt);
        }
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "INSD_USERMBR_NO")
    private UserMember member;

    @Id
    @Column(columnDefinition = "char(1)")
    private String useYn;

    @Id
    private String ocbNo;

    @Id
    private Date useStartDt;

    private String ocbAuthMtdCd;

    private Date useEndDt;

    private String regId;

    private Date regDt;

    private String updId;

    private Date updDt;

    @PrePersist
    public void prePersist() {
        regDt = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        updDt = new Date();
    }

    public UserMember getMember() {
        return member;
    }

    public void setMember(UserMember member) {
        this.member = member;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getOcbNo() {
        return ocbNo;
    }

    public void setOcbNo(String ocbNo) {
        this.ocbNo = ocbNo;
    }

    public Date getUseStartDt() {
        return useStartDt;
    }

    public void setUseStartDt(Date useStartDt) {
        this.useStartDt = useStartDt;
    }

    public String getOcbAuthMtdCd() {
        return ocbAuthMtdCd;
    }

    public void setOcbAuthMtdCd(String ocbAuthMtdCd) {
        this.ocbAuthMtdCd = ocbAuthMtdCd;
    }

    public Date getUseEndDt() {
        return useEndDt;
    }

    public void setUseEndDt(Date useEndDt) {
        this.useEndDt = useEndDt;
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
}
