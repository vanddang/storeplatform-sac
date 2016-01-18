package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * [RESPONSE] 심플 인증(간편 인증) v2.
 *
 * Updated on : 2016. 1. 18. Updated by : 최진호, 보고지티.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AuthorizeSimpleByMdnV2Res extends CommonInfo implements Serializable {

    /** */
    private static final long serialVersionUID = 1L;

    /** 사용자 고유 Key. */
    private String userKey;

    /** 기기 Key. */
    private String deviceKey;

    /** 로그인 성공유무(Y/N). */
    private String isLoginSuccess;

    /**
     * @return userKey
     */
    public String getUserKey() {
        return this.userKey;
    }

    /**
     * @param userKey
     *            String
     */
    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    /**
     * @return deviceKey
     */
    public String getDeviceKey() {
        return this.deviceKey;
    }

    /**
     * @param deviceKey
     *            deviceKey
     */
    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    /**
     * @return isLoginSuccess
     */
    public String getIsLoginSuccess() {
        return this.isLoginSuccess;
    }

    /**
     * @param isLoginSuccess
     *            String
     */
    public void setIsLoginSuccess(String isLoginSuccess) {
        this.isLoginSuccess = isLoginSuccess;
    }

}
