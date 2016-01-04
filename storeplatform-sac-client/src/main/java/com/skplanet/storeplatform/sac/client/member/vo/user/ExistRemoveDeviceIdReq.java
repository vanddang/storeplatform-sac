package com.skplanet.storeplatform.sac.client.member.vo.user;

/**
 * [REQUEST] 회원가입 여부 조회
 *
 * Updated on : 2015. 12. 28. Updated by : 최진호, 보고지티.
 */
public class ExistRemoveDeviceIdReq {

    private static final long serialVersionUID = 1L;

    /* 사용자ID */
    private String userId;
    /* 사용자 키 */
    private String userKey;
    /* 기기 Key */
    private String deviceKey;

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserKey() {
        return this.userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getDeviceKey() {
        return this.deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
