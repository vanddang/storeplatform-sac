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

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * DESC
 * FIXME 모든 필드들이 기술되지 않았음
 * </p>
 * Updated on : 2015. 12. 24 Updated by : 정희원, SK 플래닛.
 */
@Entity
@Table(name = "TB_US_OUSERMBR_DEVICE", schema = "SPMBR")
@IdClass(UserDevice.PK.class)
public class UserDevice {

    public static class PK implements Serializable {

        private UserMember member;

        /**
         * 내부 생성 디바이스 식별자
         */
        private String insdDeviceId;

        public UserMember getMember() {
            return member;
        }

        public void setMember(UserMember member) {
            this.member = member;
        }

        public String getInsdDeviceId() {
            return insdDeviceId;
        }

        public void setInsdDeviceId(String insdDeviceId) {
            this.insdDeviceId = insdDeviceId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            PK pk = (PK) o;

            if (!member.equals(pk.member)) return false;
            return insdDeviceId.equals(pk.insdDeviceId);

        }

        @Override
        public int hashCode() {
            int result = member.hashCode();
            result = 31 * result + insdDeviceId.hashCode();
            return result;
        }
    }

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INSD_USERMBR_NO")   // 컬럼 이름 규칙에 따라 의도하지 않은 이름이 지정되므로 명시적으로 지정함
    private UserMember member;

    /**
     * 내부 생성 디바이스 식별자
     */
    @Id
    private String insdDeviceId;

    /**
     * 시작 일시
     * - 가입 일시 및 기기 변경 일시와 동일
     */
    private Date startDt;

    /**
     * 종료 일시
     * - 원래는 무제한 날짜로 세팅되다가 기기 정보가 수정되면 해당 시점으로 수정됨
     */
    private Date endDt;

    /**
     * SC내부에서 생성한 고유ID
     */
    private String deviceId;

    private String deviceModelCd;

    private String mnoCd;

    private String mdn;

    /**
     * 대표 기기 여부
     */
    @Column(columnDefinition = "char(1)")
    private String repDeviceYn;

    /**
     * 인증 여부
     */
    @Column(columnDefinition = "char(1)")
    private String authYn;

    /**
     * 인증 일자
     */
    private Date authDt;

    /**
     * SMS 수신 여부
     */
    @Column(columnDefinition = "char(1)")
    private String smsRecvYn;

    private String deviceNatvId;

    private String deviceSimNm;

    private String entryChnlCd;

    /**
     * 서비스 관리 번호
     */
    private String svcMangNo;

    private Date regDt;

    private Date updDt;

    private String chgCaseCd;

    private Date lastLoginDt;

    ////////// GETTER & SETTER //////////
    public UserMember getMember() {
        return member;
    }

    public void setMember(UserMember userMember) {
        this.member = userMember;
    }

    public String getInsdDeviceId() {
        return insdDeviceId;
    }

    public void setInsdDeviceId(String insdDeviceId) {
        this.insdDeviceId = insdDeviceId;
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

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceModelCd() {
        return deviceModelCd;
    }

    public void setDeviceModelCd(String deviceModelCd) {
        this.deviceModelCd = deviceModelCd;
    }

    public String getMnoCd() {
        return mnoCd;
    }

    public void setMnoCd(String mnoCd) {
        this.mnoCd = mnoCd;
    }

    public String getMdn() {
        return mdn;
    }

    public void setMdn(String mdn) {
        this.mdn = mdn;
    }

    public String getRepDeviceYn() {
        return repDeviceYn;
    }

    public void setRepDeviceYn(String repDeviceYn) {
        this.repDeviceYn = repDeviceYn;
    }

    public String getAuthYn() {
        return authYn;
    }

    public void setAuthYn(String authYn) {
        this.authYn = authYn;
    }

    public String getSmsRecvYn() {
        return smsRecvYn;
    }

    public void setSmsRecvYn(String smsRecvYn) {
        this.smsRecvYn = smsRecvYn;
    }

    public Date getAuthDt() {
        return authDt;
    }

    public void setAuthDt(Date authDt) {
        this.authDt = authDt;
    }

    public Date getUpdDt() {
        return updDt;
    }

    public void setUpdDt(Date updDt) {
        this.updDt = updDt;
    }

    public String getDeviceNatvId() {
        return deviceNatvId;
    }

    public void setDeviceNatvId(String deviceNatvId) {
        this.deviceNatvId = deviceNatvId;
    }

    public String getDeviceSimNm() {
        return deviceSimNm;
    }

    public void setDeviceSimNm(String deviceSimNm) {
        this.deviceSimNm = deviceSimNm;
    }

    public String getEntryChnlCd() {
        return entryChnlCd;
    }

    public void setEntryChnlCd(String entryChnlCd) {
        this.entryChnlCd = entryChnlCd;
    }

    public String getSvcMangNo() {
        return svcMangNo;
    }

    public void setSvcMangNo(String svcMangNo) {
        this.svcMangNo = svcMangNo;
    }

    public Date getRegDt() {
        return regDt;
    }

    public void setRegDt(Date regDt) {
        this.regDt = regDt;
    }

    public String getChgCaseCd() {
        return chgCaseCd;
    }

    public void setChgCaseCd(String chgCaseCd) {
        this.chgCaseCd = chgCaseCd;
    }

    public Date getLastLoginDt() {
        return lastLoginDt;
    }

    public void setLastLoginDt(Date lastLoginDt) {
        this.lastLoginDt = lastLoginDt;
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
