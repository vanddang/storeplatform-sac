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
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * UserClauseAgree
 * </p>
 * Updated on : 2015. 12. 16 Updated by : 정희원, SK 플래닛.
 */
@Entity
@Table(name = "TB_US_OUSERMBR_CLAUSE_AGREE", schema = "SPMBR")
@IdClass(UserClauseAgree.PK.class)
public class UserClauseAgree {

    @Id
    private String insdUsermbrNo;

    @Id
    private String clauseId;

    private Date regDt;

    private Date updDt;

    private Character agreeYn;

    private Character mandAgreeYn;

    private String clauseVer;

    public static class PK implements Serializable {
        private String insdUsermbrNo;
        private String clauseId;

        public String getClauseId() {
            return clauseId;
        }

        public void setClauseId(String clauseId) {
            this.clauseId = clauseId;
        }

        public String getInsdUsermbrNo() {
            return insdUsermbrNo;
        }

        public void setInsdUsermbrNo(String insdUsermbrNo) {
            this.insdUsermbrNo = insdUsermbrNo;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            PK pk = (PK) o;

            if (!insdUsermbrNo.equals(pk.insdUsermbrNo)) return false;
            return clauseId.equals(pk.clauseId);

        }

        @Override
        public int hashCode() {
            int result = insdUsermbrNo.hashCode();
            result = 31 * result + clauseId.hashCode();
            return result;
        }
    }

    public String getInsdUsermbrNo() {
        return insdUsermbrNo;
    }

    public void setInsdUsermbrNo(String insdUsermbrNo) {
        this.insdUsermbrNo = insdUsermbrNo;
    }

    public String getClauseId() {
        return clauseId;
    }

    public void setClauseId(String clauseId) {
        this.clauseId = clauseId;
    }

    public Date getRegDt() {
        return regDt;
    }

    public void setRegDt(Date regDt) {
        this.regDt = regDt;
    }

    public Date getUpdDt() {
        return updDt;
    }

    public void setUpdDt(Date updDt) {
        this.updDt = updDt;
    }

    public Character getAgreeYn() {
        return agreeYn;
    }

    public void setAgreeYn(Character agreeYn) {
        this.agreeYn = agreeYn;
    }

    public Character getMandAgreeYn() {
        return mandAgreeYn;
    }

    public void setMandAgreeYn(Character mandAgreeYn) {
        this.mandAgreeYn = mandAgreeYn;
    }

    public String getClauseVer() {
        return clauseVer;
    }

    public void setClauseVer(String clauseVer) {
        this.clauseVer = clauseVer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserClauseAgree that = (UserClauseAgree) o;

        if (!insdUsermbrNo.equals(that.insdUsermbrNo)) return false;
        return clauseId.equals(that.clauseId);

    }

    @Override
    public int hashCode() {
        int result = insdUsermbrNo.hashCode();
        result = 31 * result + clauseId.hashCode();
        return result;
    }
}
