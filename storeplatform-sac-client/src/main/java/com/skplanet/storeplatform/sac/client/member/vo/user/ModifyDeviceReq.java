package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;

import javax.validation.constraints.Pattern;

/**
 * [REQUEST] 휴대기기 수정.
 * 
 * Updated on : 2014. 1. 21. Updated by : 반범진. 지티소프트.
 */
public class ModifyDeviceReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 Key.
	 */
	@NotEmpty
	private String userKey;

	/**
	 * 사용자 단말 정보.
	 */
	private DeviceInfo deviceInfo;

	/**
	 * 단말 변경 케이스 코드.
	 * 	- US012015 : SMS 수신동의 변경
	 	- US012014 : gmail 정보 변경
	 	- US012013 : imei 정보 변경
	 */
	@Pattern(regexp = "^US012013|^US012014|^US012015")
	private String chgCaseCd;

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

	/**
	 * @return chgCaseCd
	 */
	public String getChgCaseCd() {
		return chgCaseCd;
	}

	/**
	 * @param chgCaseCd
	 *            String
	 */
	public void setChgCaseCd(String chgCaseCd) {
		this.chgCaseCd = chgCaseCd;
	}
}
