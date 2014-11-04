package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [Request] PayPlanet에 InApp용으로 제공되는 T Store 회원인증.
 * 
 * Updated on : 2014. 11. 3. Updated by : 반범진, SK 플래닛.
 */
public class AuthorizeForInAppSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 트랜잭션 번호.
	 */
	@NotEmpty(message = "파라미터가 존재하지 않습니다.")
	private String trxNo;
	/**
	 * 기기 ID (msisdn).
	 */
	@NotEmpty(message = "파라미터가 존재하지 않습니다.")
	private String deviceId;
	/**
	 * 통신사.
	 */
	@NotEmpty(message = "파라미터가 존재하지 않습니다.")
	private String deviceTelecom;
	/**
	 * 기기고유 ID (imei).
	 */
	@NotEmpty(message = "파라미터가 존재하지 않습니다.")
	private String nativeId;
	/**
	 * 가입자 식별 모듈 ID (USIM 일련번호).
	 */
	@NotEmpty(message = "파라미터가 존재하지 않습니다.")
	private String simSerialNo;
	/**
	 * InApp 상품 ID.
	 */
	@NotEmpty(message = "파라미터가 존재하지 않습니다.")
	private String prodId;

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
	 * @return prodId
	 */
	public String getProdId() {
		return this.prodId;
	}

	/**
	 * @param prodId
	 *            String
	 */
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

}
