package com.skplanet.storeplatform.sac.client.internal.member.user.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 2.1.13. 회원 한도 요금제 사용여부 업데이트 [RESPONSE].
 * 
 * Updated on : 2015. 5. 12. Updated by : Rejoice, Burkhan
 */
public class UpdateLimitChargeYnSacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;
	/** . */
	private String userKey;
	/** . */
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
