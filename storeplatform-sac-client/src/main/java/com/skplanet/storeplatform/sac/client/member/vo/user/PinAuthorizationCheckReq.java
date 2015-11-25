package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 2.1.60. 휴대기기 PIN 번호 인증 여부 확인 [REQUEST].
 * 
 * Updated on : 2015. 6. 26. Updated by : incross_bottangs, 카레즈
 */
public class PinAuthorizationCheckReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** PIN 인증 여부 확인 휴대폰 번호 */
	@NotBlank
	private String deviceId;

	/** PIN 인증 진위여부 확인을 위한 Signature */
	@NotBlank
	private String phoneSign;

	/** 인증 코드 생존 시간 */
	@NotBlank
	private String timeToLive;

	/**
	 * @return the deviceId
	 */
	public String getDeviceId() {
		return this.deviceId;
	}

	/**
	 * @return the phoneSign
	 */
	public String getPhoneSign() {
		return this.phoneSign;
	}

	/**
	 * @return the timeToLive
	 */
	public String getTimeToLive() {
		return this.timeToLive;
	}

	/**
	 * @param deviceId
	 *            the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @param phoneSign
	 *            the phoneSign to set
	 */
	public void setPhoneSign(String phoneSign) {
		this.phoneSign = phoneSign;
	}

	/**
	 * @param timeToLive
	 *            the timeToLive to set
	 */
	public void setTimeToLive(String timeToLive) {
		this.timeToLive = timeToLive;
	}
}
