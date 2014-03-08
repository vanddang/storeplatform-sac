package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * Tenant 연동 Amqp Info(단말 추가)
 * 
 * Updated on : 2014. 3. 8. Updated by : 이현, 다모아솔루션.
 */
public class CreateDeviceAmqpSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/* 회원번호 */
	@NotBlank
	private String mbrNo;

	/* 통신사코드 */
	@NotBlank
	private String mnoCd;

	/* 내부기기번호 */
	@NotBlank
	private String deviceKey;

	/* 기기 아이디 */
	@NotBlank
	private String deviceId;

	/**
	 * @return the mbrNo
	 */
	public String getMbrNo() {
		return this.mbrNo;
	}

	/**
	 * @param mbrNo
	 *            the mbrNo to set
	 */
	public void setMbrNo(String mbrNo) {
		this.mbrNo = mbrNo;
	}

	/**
	 * @return the mnoCd
	 */
	public String getMnoCd() {
		return this.mnoCd;
	}

	/**
	 * @param mnoCd
	 *            the mnoCd to set
	 */
	public void setMnoCd(String mnoCd) {
		this.mnoCd = mnoCd;
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
