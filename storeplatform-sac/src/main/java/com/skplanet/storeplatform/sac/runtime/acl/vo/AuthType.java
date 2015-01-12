package com.skplanet.storeplatform.sac.runtime.acl.vo;

import org.apache.commons.lang3.StringUtils;

/**
 * Tenant 인증 유형
 * Updated on : 2014. 02. 13. Updated by : 임근대, SKP.
 */
public enum AuthType {
    /** MAC 인증 */
    MAC("MAC"),
    /** IP 체크 */
    IP("IP");

    private final String code;

    AuthType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static AuthType fromCode(String code) {
        for (AuthType status : values()) {
            if (StringUtils.equalsIgnoreCase(status.getCode(), code)) {
                return status;
            }
        }
        return null;
    }
}