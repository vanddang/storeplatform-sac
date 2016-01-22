package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;

/**
 * [REQUEST] 휴대기기 등록.
 * 
 * Updated on : 2014. 1. 6. Updated by : 반범진. 지티소프트.
 */
public class CreateDeviceReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 Key.
	 */
	private String userKey;

	/**
	 * 사용자 단말 정보.
	 */
	private DeviceInfo deviceInfo;

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
	 * @return deviceInfo
	 */
	public DeviceInfo getDeviceInfo() {
		return this.deviceInfo;
	}

	/**
	 * @param deviceInfo
	 *            DeviceInfo
	 */
	public void setDeviceInfo(DeviceInfo deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

}
