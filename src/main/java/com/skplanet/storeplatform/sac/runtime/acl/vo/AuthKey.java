/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.runtime.acl.vo;

import java.util.Date;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 *
 * Updated on : 2014. 02. 14. Updated by : 임근대, SKP.
 */
public class AuthKey extends CommonInfo {

	private static final long serialVersionUID = 1L;

    /** 테넌트 ID */
	private String tenantId;
    /** AuthKey */
	private String authKey;
    /**
     * 키 타입 (테스트/상용)
     * {@link com.skplanet.storeplatform.sac.runtime.acl.vo.AuthKeyType}
     */
	private AuthKeyType authKeyType;
	private String authKeyTypeCd;
	private String authKeyTypeNm;

    /** 발급일 */
    private Date issuDt;
    /** 만료일 */
    private Date exprDt;
    /**
     * 상태
     * {@link com.skplanet.storeplatform.sac.runtime.acl.vo.AuthKeyStatus}
     */
    private AuthKeyStatus status;

    private String statusCd;
    /**
     * Auth 유형 (MAC/IP)
     * {@link com.skplanet.storeplatform.sac.runtime.acl.vo.AuthType}
     */
    private AuthType authType;
    private String authTypeCd;
    private String authTypeNm;

    /** secret */
    private String secret;
    /** 사용기한 체크 Y/N  */
	private String usableDateYn;

    public String getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getAuthKey() {
        return this.authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public AuthKeyType getAuthKeyType() {
        return this.authKeyType;
    }

    public void setAuthKeyType(AuthKeyType authKeyType) {
        this.authKeyType = authKeyType;
    }

    public String getAuthKeyTypeCd() {
        return this.authKeyTypeCd;
    }

    public void setAuthKeyTypeCd(String authKeyTypeCd) {
        this.authKeyTypeCd = authKeyTypeCd;
    }

    public String getAuthKeyTypeNm() {
        return this.authKeyTypeNm;
    }

    public void setAuthKeyTypeNm(String authKeyTypeNm) {
        this.authKeyTypeNm = authKeyTypeNm;
    }

    public Date getIssuDt() {
        return this.issuDt;
    }

    public void setIssuDt(Date issuDt) {
        this.issuDt = issuDt;
    }

    public Date getExprDt() {
        return this.exprDt;
    }

    public void setExprDt(Date exprDt) {
        this.exprDt = exprDt;
    }

    public AuthKeyStatus getStatus() {
        return this.status;
    }

    public void setStatus(AuthKeyStatus status) {
        this.status = status;
    }

    public String getStatusCd() {
        return this.statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }

    public String getSecret() {
        return this.secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getUsableDateYn() {
        return this.usableDateYn;
    }

    public void setUsableDateYn(String usableDateYn) {
        this.usableDateYn = usableDateYn;
    }

    public AuthType getAuthType() {
        return this.authType;
    }

    public void setAuthType(AuthType authType) {
        this.authType = authType;
    }

    public String getAuthTypeCd() {
        return this.authTypeCd;
    }

    public void setAuthTypeCd(String authTypeCd) {
        this.authTypeCd = authTypeCd;
    }

    public String getAuthTypeNm() {
        return this.authTypeNm;
    }

    public void setAuthTypeNm(String authTypeNm) {
        this.authTypeNm = authTypeNm;
    }


}
