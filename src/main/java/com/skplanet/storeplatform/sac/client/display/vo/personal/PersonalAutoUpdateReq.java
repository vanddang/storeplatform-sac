package com.skplanet.storeplatform.sac.client.display.vo.personal;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 자동 업데이트 목록 조회 Request Value Object.
 * 
 * Updated on : 2014. 2. 3. Updated by : 오승민, 인크로스.
 */
public class PersonalAutoUpdateReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	@NotBlank
	private String packageInfo;
	private Integer updLimitCnt = 0;
	@NotBlank
	private String deviceKey;
	@NotBlank
	private String userKey;

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
	 * @return the updLimitCnt
	 */
	public Integer getUpdLimitCnt() {
		return this.updLimitCnt;
	}

	/**
	 * @param updLimitCnt
	 *            the updLimitCnt to set
	 */
	public void setUpdLimitCnt(Integer updLimitCnt) {
		this.updLimitCnt = updLimitCnt;
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
