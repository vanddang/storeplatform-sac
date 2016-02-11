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

import com.skplanet.storeplatform.sac.client.member.vo.user.SearchDeviceSetInfoSacRes;
import com.skplanet.storeplatform.sac.common.util.DateUtils;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>
 * 휴대기기 설정 정보
 * </p>
 * Updated on : 2016. 01. 25 Updated by : 정희원, SK 플래닛.
 */
@Entity
@Table(name = "TB_US_OUSERMBR_DEVICE_SET")
public class UserDeviceSetting {

    @EmbeddedId
    private UserDevicePK id;

    @Column(updatable = false)
    private Date regDt;

    @Column(updatable = false)
    private String regId;

    private Date updDt;

    private String updId;

    private String systemId;

    /**
     * 자동 업데이트 여부
     */
    @Column(columnDefinition = "char(1)")
    private String autoUpdtYn;

    /**
     * 자동 업데이트 유형
     */
    @Column(columnDefinition = "char(1)")
    private String autoUpdtSetClsf;

    /**
     * 로그인 잠금 여부
     */
    @Column(columnDefinition = "char(1)")
    private String loginLockYn;

    /**
     * ?
     */
    @Column(columnDefinition = "char(1)")
    private String adultContentsLockYn;

    /**
     * 성인 컨텐츠 제한 여부
     */
    @Column(columnDefinition = "char(1)")
    private String adultContentsLimtYn;

    @Column(columnDefinition = "char(1)")
    private String wiFiAutoDwldYn;

    /**
     * WIFI 자동 업데이트 여부
     */
    @Column(columnDefinition = "char(1)")
    private String wiFiAutoUpdtYn;

    /**
     * 보안 비밀번호. 구 단말에서 로긴잠금, 성인컨텐츠 잠금에 사용
     */
    private String pinNo;

    /**
     * 인증 실패 횟수
     */
    private Integer authFailCnt;

    @Column(columnDefinition = "char(1)")
    private String authLockYn;

    /**
     * 실명인증 일시
     */
    private Date rnameAuthDate;

    /**
     * 실명인증 MDN
     */
    @Column(columnDefinition = "char(1)")
    private String rnameAuthMdn;

    @Transient
    private boolean useMarketPin = false;

    @Transient
    private Integer marketPinFailCnt = null;

    /**
     * 기본값을 응답한다.
     * @return
     */
    public static UserDeviceSetting createDefault() {
        UserDeviceSetting v = new UserDeviceSetting();
        v.setAutoUpdtYn("Y");
        v.setWiFiAutoUpdtYn("Y");
        v.setLoginLockYn("N");
        v.setAdultContentsLimtYn("Y");
        v.setWiFiAutoDwldYn("Y");
        v.setPinNo("N");

        return v;
    }

    public SearchDeviceSetInfoSacRes convertToResponse() {
        SearchDeviceSetInfoSacRes res = new SearchDeviceSetInfoSacRes();

        // 원래 처리하던 SQL문장의 주석 부분을 반영하여 아래와 같이 주석 처리함
        res.setAutoUpdateSet(autoUpdtSetClsf);
        res.setIsAdult(adultContentsLockYn);
        res.setIsPin(pinNo == null ? "N" : "Y");
        res.setIsPinClosed(authLockYn);
        res.setFailCnt(authFailCnt != null ? authFailCnt.toString() : null);

        //res.setIsPinRetry(null);    // 컬럼이 존재하지 않아 제거
        res.setIsAutoUpdate(autoUpdtYn);
        res.setIsAutoUpdateWifi(wiFiAutoUpdtYn);
        res.setIsLoginLock(loginLockYn);
        res.setIsAdultLock(adultContentsLimtYn);

        res.setIsDownloadWifiOnly(wiFiAutoDwldYn);
        res.setRealNameDate(DateUtils.format(rnameAuthDate));
        res.setRealNameMdn(rnameAuthMdn);

        return res;
    }

    public SearchDeviceSetInfoSacRes convertToResponseV2() {
        SearchDeviceSetInfoSacRes res = new SearchDeviceSetInfoSacRes();

        res.setIsPin(useMarketPin ? "Y" : "N");
        res.setIsAutoUpdate(autoUpdtYn);
        res.setAutoUpdateSet(autoUpdtSetClsf);
        res.setIsAutoUpdateWifi(wiFiAutoUpdtYn);
        res.setIsLoginLock(loginLockYn);

        res.setIsAdult(adultContentsLockYn);
        res.setFailCnt(marketPinFailCnt != null ? marketPinFailCnt.toString() : null);
        res.setIsAdultLock(adultContentsLimtYn);
        res.setIsDownloadWifiOnly(wiFiAutoDwldYn);

        return res;
    }

    @PrePersist
    public void prePersist() {
        regDt = new Date();
        updDt = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        updDt = new Date();
    }

    ////////// GETTERS & SETTERS

    public UserDevicePK getId() {
        return id;
    }

    public void setId(UserDevicePK id) {
        this.id = id;
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

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getAutoUpdtYn() {
        return autoUpdtYn;
    }

    public void setAutoUpdtYn(String autoUpdtYn) {
        this.autoUpdtYn = autoUpdtYn;
    }

    public String getAutoUpdtSetClsf() {
        return autoUpdtSetClsf;
    }

    public void setAutoUpdtSetClsf(String autoUpdtSetClsf) {
        this.autoUpdtSetClsf = autoUpdtSetClsf;
    }

    public String getLoginLockYn() {
        return loginLockYn;
    }

    public void setLoginLockYn(String loginLockYn) {
        this.loginLockYn = loginLockYn;
    }

    public String getAdultContentsLockYn() {
        return adultContentsLockYn;
    }

    public void setAdultContentsLockYn(String adultContentsLockYn) {
        this.adultContentsLockYn = adultContentsLockYn;
    }

    public String getAdultContentsLimtYn() {
        return adultContentsLimtYn;
    }

    public void setAdultContentsLimtYn(String adultContentsLimtYn) {
        this.adultContentsLimtYn = adultContentsLimtYn;
    }

    public String getWiFiAutoDwldYn() {
        return wiFiAutoDwldYn;
    }

    public void setWiFiAutoDwldYn(String wiFiAutoDwldYn) {
        this.wiFiAutoDwldYn = wiFiAutoDwldYn;
    }

    public String getWiFiAutoUpdtYn() {
        return wiFiAutoUpdtYn;
    }

    public void setWiFiAutoUpdtYn(String wiFiAutoUpdtYn) {
        this.wiFiAutoUpdtYn = wiFiAutoUpdtYn;
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

    public String getAuthLockYn() {
        return authLockYn;
    }

    public void setAuthLockYn(String authLockYn) {
        this.authLockYn = authLockYn;
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

    public boolean isUseMarketPin() {
        return useMarketPin;
    }

    public void setUseMarketPin(boolean useMarketPin) {
        this.useMarketPin = useMarketPin;
    }

    public Integer getMarketPinFailCnt() {
        return marketPinFailCnt;
    }

    public void setMarketPinFailCnt(Integer marketPinFailCnt) {
        this.marketPinFailCnt = marketPinFailCnt;
    }
}
