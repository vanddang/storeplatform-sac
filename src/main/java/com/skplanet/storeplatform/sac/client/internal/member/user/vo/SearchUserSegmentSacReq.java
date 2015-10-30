package com.skplanet.storeplatform.sac.client.internal.member.user.vo;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 2.1.9. 회원 segment 정보 조회 [REQUEST].
 * 
 * Updated on : 2014. 9. 22. Updated by : Rejoice, Burkhan
 */
public class SearchUserSegmentSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 회원 키.
	 */
	@NotBlank
	private String userKey;

	/**
	 * 디바이스 키.
	 */
	private String deviceKey;

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

}
