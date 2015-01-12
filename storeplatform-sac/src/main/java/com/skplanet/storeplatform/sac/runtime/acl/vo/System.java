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
 * System Value Object
 * <p/>
 * Updated on : 2014. 2. 18. Updated by : 임근대, SK 플래닛.
 */
public class System extends CommonInfo {

    private static final long serialVersionUID = 1L;

    private String systemId;
    private String tenantId;
    private SystemStatus status;
    private String systemCd;
    private String systemNm;
    private String statusCd;

    /**
     * ip 인증을 위한 파라미터
     */
    private String ip;


    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public SystemStatus getStatus() {
        return status;
    }

    public void setStatus(SystemStatus status) {
        this.status = status;
    }

    public String getSystemCd() {
        return systemCd;
    }

    public void setSystemCd(String systemCd) {
        this.systemCd = systemCd;
    }

    public String getSystemNm() {
        return systemNm;
    }

    public void setSystemNm(String systemNm) {
        this.systemNm = systemNm;
    }

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}