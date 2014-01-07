package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * [REQUEST] 부가서비스 가입 & 가입 조회 공통 VO
 * 
 * Updated on : 2014. 1. 6. Updated by : 김다슬, 인크로스.
 */
public class AdditionalServicInfo extends CommonInfo {
	/**
	 * 기기 ID (mdn)
	 */
	private String deviceId;

	/**
	 * 부가서비스 코드
	 */
	private String svcCode;

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getSvcCode() {
		return this.svcCode;
	}

	public void setSvcCode(String svcCode) {
		this.svcCode = svcCode;
	}
}
