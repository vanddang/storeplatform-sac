package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [Request] U+ Store 회원의 회원인증.
 * 
 * Updated on : 2015. 01. 20. Updated by : 반범진, SK 플래닛.
 */
public class AuthorizeForUplusStoreSacReq extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 트랜잭션 번호.
	 */
	@NotEmpty
	private String trxNo;

	/**
	 * 기기 ID (msisdn).
	 */
	@NotEmpty
	private String deviceId;

	/**
	 * 통신사.
	 */
	@NotEmpty
	private String deviceTelecom;

	/**
	 * 기기고유 ID (imei).
	 */
	@NotEmpty
	private String nativeId;

	/**
	 * 가입자 식별 모듈 ID (USIM 일련번호).
	 */
	private String simSerialNo;

	/**
	 * 접속 단말 구분코드.
	 */
	private String deviceType;

	/**
	 * 기타 정보.
	 */
	private Object extraInfo;

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
	 * @return nativeId nativeId
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
	 * @return deviceType
	 */
	public String getDeviceType() {
		return this.deviceType;
	}

	/**
	 * @param deviceType
	 *            String
	 */
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * @return extraInfo
	 */
	public Object getExtraInfo() {
		return this.extraInfo;
	}

	/**
	 * @param extraInfo
	 *            Object
	 */
	public void setExtraInfo(Object extraInfo) {
		this.extraInfo = extraInfo;
	}
}
