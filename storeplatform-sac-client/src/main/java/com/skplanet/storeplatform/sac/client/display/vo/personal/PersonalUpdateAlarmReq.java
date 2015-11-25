package com.skplanet.storeplatform.sac.client.display.vo.personal;

import javax.validation.constraints.Pattern;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Application Update 알림 설정 Request Value Object.
 * 
 * Updated on : 2014. 2. 3. Updated by : 오승민, 인크로스.
 */
public class PersonalUpdateAlarmReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String deviceKey;
	private String userKey;
	private String alarmOnYn;
	private String list;
	@Pattern(regexp = "^package|^pid")
	private String type;

	/**
	 * @return the deviceKey
	 */
	public String getDeviceKey() {
		return this.deviceKey;
	}

	/**
	 * @param deviceKey
	 *            the deviceKey to set
	 */
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	/**
	 * @return the userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return the alarmOnYn
	 */
	public String getAlarmOnYn() {
		return this.alarmOnYn;
	}

	/**
	 * @param alarmOnYn
	 *            the alarmOnYn to set
	 */
	public void setAlarmOnYn(String alarmOnYn) {
		this.alarmOnYn = alarmOnYn;
	}

	/**
	 * @return the list
	 */
	public String getList() {
		return this.list;
	}

	/**
	 * @param list
	 *            the list to set
	 */
	public void setList(String list) {
		this.list = list;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

}
