package com.skplanet.storeplatform.member.client.user.sci.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 2.1.56. 가입 테넌트 정보 조회 [REQUEST].
 * 
 * Updated on : 2015. 5. 06. Updated by : 윤보영, 카레즈.
 */
public class ListTenantRequest extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 기기 ID.
	 */
	private String deviceId;

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
