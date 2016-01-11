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

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.primitives.Doubles;
import com.skplanet.storeplatform.sac.client.member.vo.common.Agreement;
import com.skplanet.storeplatform.sac.common.util.DateUtils;

import javax.persistence.*;
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

    public static class PK implements Serializable {
        private UserMember member;
        private String clauseId;

        public String getClauseId() {
            return clauseId;
        }

        public void setClauseId(String clauseId) {
            this.clauseId = clauseId;
        }

        public UserMember getMember() {
            return member;
        }

        public void setMember(UserMember member) {
            this.member = member;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PK pk = (PK) o;
            return Objects.equal(member, pk.member) &&
                    Objects.equal(clauseId, pk.clauseId);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(member, clauseId);
        }
    }

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INSD_USERMBR_NO")
    private UserMember member;

    @Id
    private String clauseId;

    private Date regDt;

    private Date updDt;

    @Column(columnDefinition = "char(1)")
    private String agreeYn;

    @Column(columnDefinition = "char(1)")
    private String mandAgreeYn;

    private String clauseVer;

    public UserMember getMember() {
        return member;
    }

    public void setMember(UserMember member) {
        this.member = member;
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

    public String getAgreeYn() {
        return agreeYn;
    }

    public void setAgreeYn(String agreeYn) {
        this.agreeYn = agreeYn;
    }

    public String getMandAgreeYn() {
        return mandAgreeYn;
    }

    public void setMandAgreeYn(String mandAgreeYn) {
        this.mandAgreeYn = mandAgreeYn;
    }

    public String getClauseVer() {
        return clauseVer;
    }

    public void setClauseVer(String clauseVer) {
        this.clauseVer = clauseVer;
    }

    public String getRegDtStr() {
        return DateUtils.format(regDt);
    }

    public String getUpdDtStr() {
        return DateUtils.format(updDt);
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

    ////////// Business //////////
    public Agreement convertToAgreement() {
        Agreement agree = new Agreement();
        agree.setExtraAgreementId(clauseId);
        agree.setExtraAgreementVersion(clauseVer);
        agree.setIsExtraAgreement(agreeYn);
        agree.setIsMandatory(mandAgreeYn);
        agree.setRegDate(getRegDtStr());
        agree.setUpdateDate(getUpdDtStr());
        return agree;
    }

    /**
     * targetVer와 비교해서 더 높은 버전을 지정한다
     * @param targetVer 비교 버전
     */
    public void setMaxClauseVer(String targetVer) {
        if(Strings.isNullOrEmpty(targetVer))
            return;

        Double selfVer = Doubles.tryParse(clauseVer),
                tgtVer = Doubles.tryParse(targetVer);

        if (selfVer != null && tgtVer != null)
            clauseVer = (selfVer < tgtVer ? tgtVer : selfVer).toString();
        else if (selfVer == null && tgtVer == null)
            clauseVer = null;
        else
            clauseVer = (selfVer != null ? selfVer : tgtVer).toString();
    }
}
