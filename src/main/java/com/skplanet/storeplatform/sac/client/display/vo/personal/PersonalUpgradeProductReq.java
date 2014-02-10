package com.skplanet.storeplatform.sac.client.display.vo.personal;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 업데이트 대상 목록 조회 Request Value Object.
 * 
 * Updated on : 2014. 2. 10. Updated by : 오승민, 인크로스.
 */
public class PersonalUpgradeProductReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String memberType;
	private String packageInfo;
	private String deviceKey;
	private String userKey;

	/**
	 * @return the memberType
	 */
	public String getMemberType() {
		return this.memberType;
	}

	/**
	 * @param memberType
	 *            the memberType to set
	 */
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	/**
	 * @return the packageInfo
	 */
	public String getPackageInfo() {
		return this.packageInfo;
	}

	/**
	 * @param packageInfo
	 *            the packageInfo to set
	 */
	public void setPackageInfo(String packageInfo) {
		this.packageInfo = packageInfo;
	}

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

}
