package com.skplanet.storeplatform.sac.client.member.vo.user;

/**
 * [REQUEST] 휴대기기 목록 조회.
 *
 * Updated on : 2015. 12. 29. Updated by : 최진호, 보고지티.
 */
public class ListDeviceRemoveDeviceIdReq {

    private static final long serialVersionUID = 1L;

    /**
     * 사용자 ID.
     */
    private String userId;

    /**
     * 사용자 Key.
     */
    private String userKey;

    /**
     * 내부 기기 key.
     */
    private String deviceKey;

    /**
     * 대표기기 여부.
     */
    private String isMainDevice;

    /**
     * @return userId
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * @param userId
     *            String
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

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
     * @return isMainDevice
     */
    public String getIsMainDevice() {
        return this.isMainDevice;
    }

    /**
     * @param isMainDevice
     *            String
     */
    public void setIsMainDevice(String isMainDevice) {
        this.isMainDevice = isMainDevice;
    }

    /**
     * @return deviceKey
     */
    public String getDeviceKey() {
        return this.deviceKey;
    }

    /**
     * @param deviceKey
     *            String
     */
    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

}
