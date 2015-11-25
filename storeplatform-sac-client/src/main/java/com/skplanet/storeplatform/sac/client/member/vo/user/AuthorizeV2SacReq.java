package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [Request] PayPlanet에 제공되는 3사(SKT/KT/U+) 회원인증.
 * 
 * Updated on : 2015. 01. 29. Updated by : 반범진, SK 플래닛.
 */
public class AuthorizeV2SacReq extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 기기 ID (msisdn).
	 */
	@NotEmpty
	private String deviceId;

	/**
	 * 트랜잭션 번호.
	 */
	private String trxNo;

	/**
	 * 통신사.
	 */
	private String deviceTelecom;

	/**
	 * 기기고유 ID (imei).
	 */
	private String nativeId;

	/**
	 * 가입자 식별 모듈 ID (USIM 일련번호).
	 */
	private String simSerialNo;

	/**
	 * 테넌트 아이디.
	 */
	private String tenantId;

	/**
	 * @return deviceId
	 */
	public String getDeviceId() {
		return this.deviceId;
	}

	/**
	 * @param deviceId
	 *            String
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return trxNo
	 */
	public String getTrxNo() {
		return this.trxNo;
	}

	/**
	 * @param trxNo
	 *            String
	 */
	public void setTrxNo(String trxNo) {
		this.trxNo = trxNo;
	}

	/**
	 * @return deviceTelecom
	 */
	public String getDeviceTelecom() {
		return this.deviceTelecom;
	}

	/**
	 * @param deviceTelecom
	 *            String
	 */
	public void setDeviceTelecom(String deviceTelecom) {
		this.deviceTelecom = deviceTelecom;
	}

	/**
	 * @return nativeId
	 */
	public String getNativeId() {
		return this.nativeId;
	}

	/**
	 * @param nativeId
	 *            String
	 */
	public void setNativeId(String nativeId) {
		this.nativeId = nativeId;
	}

	/**
	 * @return simSerialNo
	 */
	public String getSimSerialNo() {
		return this.simSerialNo;
	}

	/**
	 * @param simSerialNo
	 *            String
	 */
	public void setSimSerialNo(String simSerialNo) {
		this.simSerialNo = simSerialNo;
	}

	/**
	 * @return tenantId
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            String
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

}
