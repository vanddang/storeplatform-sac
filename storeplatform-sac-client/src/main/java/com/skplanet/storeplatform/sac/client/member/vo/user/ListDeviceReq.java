package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST] 휴대기기 목록 조회.
 * 
 * Updated on : 2015. 12. 28. Updated by : 윤보영. 카레즈.
 * Updated on : 2016. 01. 18. Updated by : 윤보영. 카레즈.
 */
public class ListDeviceReq extends CommonInfo {

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
	 * 기기 ID.
	 */
	private String deviceId;

	/**
	 * 내부 기기 key.
	 */
	private String deviceKey;

	/**
	 * 대표기기 여부.
	 */
	private String isMainDevice;

    /**
     * MDN.
     */
    private String mdn;

    /**
     * 서비스망 번호.
     */
    private String svcMangNo;

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
	 * @return deviceId
	 */
	public String getDeviceId() {
		return this.deviceId;
	}

	/**
	 * @param deviceId
	 *            String
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
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

    public String getMdn() {
        return mdn;
    }

    public String getSvcMangNo() {
        return svcMangNo;
    }

    public void setSvcMangNo(String svcMangNo) {
        this.svcMangNo = svcMangNo;
    }

    public void setMdn(String mdn) {
        this.mdn = mdn;
    }
}
