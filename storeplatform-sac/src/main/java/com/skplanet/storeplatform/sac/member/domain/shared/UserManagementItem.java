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
 * 사용자 관리 항목
 * </p>
 * Updated on : 2016. 01. 11 Updated by : 정희원, SK 플래닛.
 */
@Entity
@Table(name = "TB_US_OUSERMBR_MANG_ITEM_PTCR", schema = "SPMBR")
@IdClass(UserManagementItem.PK.class)
public class UserManagementItem {

    public static class PK implements Serializable {

        private UserMember member;

        private String mangItemCd;

        public PK() {}

        public PK(UserMember member, String mangItemCd) {
            this.member = member;
            this.mangItemCd = mangItemCd;
        }

        public UserMember getMember() {
            return member;
        }

        public void setMember(UserMember member) {
            this.member = member;
        }

        public String getMangItemCd() {
            return mangItemCd;
        }

        public void setMangItemCd(String mangItemCd) {
            this.mangItemCd = mangItemCd;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PK pk = (PK) o;
            return Objects.equal(member, pk.member) &&
                    Objects.equal(mangItemCd, pk.mangItemCd);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(member, mangItemCd);
        }
    }

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INSD_USERMBR_NO")
    private UserMember member;

    @Id
    private String mangItemCd;

    private String regResultValue;

    private Date regDt;

    private String regId;

    private Date updDt;

    private String updId;

    public UserMember getMember() {
        return member;
    }

    public void setMember(UserMember member) {
        this.member = member;
    }

    public String getMangItemCd() {
        return mangItemCd;
    }

    public void setMangItemCd(String mangItemCd) {
        this.mangItemCd = mangItemCd;
    }

    public String getRegResultValue() {
        return regResultValue;
    }

    public void setRegResultValue(String regResultValue) {
        this.regResultValue = regResultValue;
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

    @PrePersist
    public void prePersist() {
        this.regDt = new Date();
        this.updDt = this.regDt;
    }

    @PreUpdate
    public void preUpdate() {
        this.updDt = new Date();
    }
}
