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
 * T_CN_TENANT 디비 맵핑용 Value Object
 * <p/>
 * Updated on : 2014. 2. 10.
 * Updated by : 서대영, SK 플래닛.
 */
public class Tenant extends CommonInfo {

    private static final long serialVersionUID = 1L;

    /**
     * Tenant ID
     */
    private String tenantId;
    /**
     * Tenant Name
     */
    private String tenantNm;

    private TenantStatus status;
    /**
     * Authentication Type {@link com.skplanet.storeplatform.sac.runtime.acl.vo.AuthType}
     */
    private AuthType authType;

    public Tenant() {
    }

    public Tenant(String tenantId) {
        this.tenantId = tenantId;
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

    public AuthType getAuthType() {
        return authType;
    }

    public void setAuthType(AuthType authType) {
        this.authType = authType;
    }

    public TenantStatus getStatus() {
        return status;
    }

    public void setStatus(TenantStatus status) {
        this.status = status;
    }
}
