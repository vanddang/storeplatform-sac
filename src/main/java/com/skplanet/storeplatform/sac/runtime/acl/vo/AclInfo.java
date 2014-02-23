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

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
  ACL 정보
 * Updated on : 2014. 02. 14. Updated by : 임근대, SKP.
 */
public class AclInfo extends CommonInfo {

	private static final long serialVersionUID = 1L;

    /** AuthKey */
    private String authKey;

    /**
     * 키 타입 (테스트/상용)
     * {@link com.skplanet.storeplatform.sac.runtime.acl.vo.AuthKeyType
     */
    private AuthKeyType authKeyType;

    /**
     * Auth 유형 (MAC/IP)
     * {@link com.skplanet.storeplatform.sac.runtime.acl.vo.AuthType}
     */
    private AuthType authType;
    private String authTypeCd;

    /**
     * AuthKey 상태
     * {@link com.skplanet.storeplatform.sac.runtime.acl.vo.AuthKeyStatus}
     */
    private AuthKeyStatus authKeyStatus;
    private String authKeyStatusCd;
    /** secret */
    private String secret;

    /** 사용기한 체크 (사용 가능 Y/N)  */
    private String usableDateYn;


    /** 테넌트 ID */
	private String tenantId;
	private String tenantNm;

    /**
     * 테넌트 상태
     * {@link com.skplanet.storeplatform.sac.runtime.acl.vo.TenantStatus}
     */
    private TenantStatus tenantStatus;
    private String tenantStatusCd;

    private String systemId;
    private String systemNm;

    /**
     * 시스템 상태
     * {@link com.skplanet.storeplatform.sac.runtime.acl.vo.TenantStatus}
     */
    private SystemStatus systemStatus;
    private String systemStatusCd;

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public AuthKeyType getAuthKeyType() {
        return authKeyType;
    }

    public void setAuthKeyType(AuthKeyType authKeyType) {
        this.authKeyType = authKeyType;
    }

    public AuthType getAuthType() {
        return authType;
    }

    public void setAuthType(AuthType authType) {
        this.authType = authType;
    }

    public String getAuthTypeCd() {
        return authTypeCd;
    }

    public void setAuthTypeCd(String authTypeCd) {
        this.authTypeCd = authTypeCd;
    }

    public AuthKeyStatus getAuthKeyStatus() {
        return authKeyStatus;
    }

    public void setAuthKeyStatus(AuthKeyStatus authKeyStatus) {
        this.authKeyStatus = authKeyStatus;
    }

    public String getAuthKeyStatusCd() {
        return authKeyStatusCd;
    }

    public void setAuthKeyStatusCd(String authKeyStatusCd) {
        this.authKeyStatusCd = authKeyStatusCd;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getUsableDateYn() {
        return usableDateYn;
    }

    public void setUsableDateYn(String usableDateYn) {
        this.usableDateYn = usableDateYn;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantNm() {
        return tenantNm;
    }

    public void setTenantNm(String tenantNm) {
        this.tenantNm = tenantNm;
    }

    public TenantStatus getTenantStatus() {
        return tenantStatus;
    }

    public void setTenantStatus(TenantStatus tenantStatus) {
        this.tenantStatus = tenantStatus;
    }

    public String getTenantStatusCd() {
        return tenantStatusCd;
    }

    public void setTenantStatusCd(String tenantStatusCd) {
        this.tenantStatusCd = tenantStatusCd;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getSystemNm() {
        return systemNm;
    }

    public void setSystemNm(String systemNm) {
        this.systemNm = systemNm;
    }

    public SystemStatus getSystemStatus() {
        return systemStatus;
    }

    public void setSystemStatus(SystemStatus systemStatus) {
        this.systemStatus = systemStatus;
    }

    public String getSystemStatusCd() {
        return systemStatusCd;
    }

    public void setSystemStatusCd(String systemStatusCd) {
        this.systemStatusCd = systemStatusCd;
    }
}
