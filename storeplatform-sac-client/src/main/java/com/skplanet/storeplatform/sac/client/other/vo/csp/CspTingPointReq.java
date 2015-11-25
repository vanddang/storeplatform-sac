package com.skplanet.storeplatform.sac.client.other.vo.csp;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.validator.constraints.NotBlank;

/**
 * CSP Ting Point 연동 Value Object
 *
 * Updated on : 2015. 8. 12. Updated by : 심대진, 다모아 솔루션.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CspTingPointReq {

    private static final long serialVersionUID = 1L;

    /**
     * 사용자 Key.
     */
    @NotBlank
    private String userKey;
    /**
     * 사용자 단말 Key.
     */
    @NotBlank
    private String deviceKey;
    /**
     * 사용자 서비스 단말 번호.
     */
    @NotBlank
    private String svcMngNum;

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public String getSvcMngNum() {
        return svcMngNum;
    }

    public void setSvcMngNum(String svcMngNum) {
        this.svcMngNum = svcMngNum;
    }
}
