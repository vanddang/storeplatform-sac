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
	private Integer updLimitCnt;
	@NotBlank
	private String deviceId;

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
	 * @return the deviceId
	 */
	public String getDeviceId() {
		return this.deviceId;
	}

	/**
	 * @param deviceId
	 *            the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

}
