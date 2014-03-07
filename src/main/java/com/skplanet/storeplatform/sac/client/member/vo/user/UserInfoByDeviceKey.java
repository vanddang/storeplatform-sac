/**
 * 
 */
package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * DeviceKey 별 회원 정보.
 * 
 * Updated on : 2014. 3. 07. Updated by : 강신완, 부르칸.
 */
public class UserInfoByDeviceKey extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String userName;

	private String userBirthday;

	private String deviceTelecom;

	private String deviceId;

	private String deviceModelName;

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserBirthday() {
		return this.userBirthday;
	}

	public void setUserBirthday(String userBirthday) {
		this.userBirthday = userBirthday;
	}

	public String getDeviceTelecom() {
		return this.deviceTelecom;
	}

	public void setDeviceTelecom(String deviceTelecom) {
		this.deviceTelecom = deviceTelecom;
	}

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceModelName() {
		return this.deviceModelName;
	}

	public void setDeviceModelName(String deviceModelName) {
		this.deviceModelName = deviceModelName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
