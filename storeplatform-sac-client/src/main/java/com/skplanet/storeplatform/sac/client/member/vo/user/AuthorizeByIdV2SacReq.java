package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceExtraInfo;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

/**
 * [REQUEST] ID 기반 회원 인증 V2.
 * ID기반(Tstore ID / Social ID)회원의 인증 기능을 제공한다. [OneStore 단말을 위한 신규규격]
 * Updated on : 2016. 1. 4. Updated by : 반범진.
 */
public class AuthorizeByIdV2SacReq extends CommonInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 아이디.
	 */
	@NotEmpty
	private String userId;

	/**
	 * 사용자 구분 코드.
	 */
	@NotEmpty
	@Pattern(regexp = "^US011502|^US011504|^US011505|^US011506")
	private String userType;

	/**
	 * 사용자 인증 토큰.
	 */
	@NotEmpty
	private String userAuthToken;

	/**
	 * 기기 ID.
	 */
	@NotEmpty
	private String deviceId;

	/**
	 * 기기 MDN.
	 */
	private String mdn;

	/**
	 * 이동 통신사.
	 */
	@NotEmpty
	private String deviceTelecom;

	/**
	 * 기기 고유 번호.
	 */
	private String nativeId;

	/**
	 * 가입자 식별 모듈 ID (USIM 일련번호).
	 */
	private String simSerialNo;

	/**
	 * 접속 아이피.
	 */
	private String deviceIp;

	/**
	 * 사용자 이메일.
	 */
	private String userEmail = "";

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
	 * @return userId
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * @param userId
	 *            String
	 */
	public void setUserId(String userId) {
		this.userId = userId;
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
	 * @return ipAddress
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
	 * @return userType
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * @param userType
	 *            String
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * @return userAuthToken
	 */
	public String getUserAuthToken() {
		return userAuthToken;
	}

	/**
	 * @param userAuthToken
	 *            String
	 */
	public void setUserAuthToken(String userAuthToken) {
		this.userAuthToken = userAuthToken;
	}

	/**
	 * @return mdn
	 */
	public String getMdn() {
		return mdn;
	}

	/**
	 * @param mdn
	 *            String
	 */
	public void setMdn(String mdn) {
		this.mdn = mdn;
	}

	/**
	 * @return simSerialNo
	 */
	public String getSimSerialNo() {
		return simSerialNo;
	}

	/**
	 * @param simSerialNo
	 *            String
	 */
	public void setSimSerialNo(String simSerialNo) {
		this.simSerialNo = simSerialNo;
	}

	/**
	 * @return deviceIp
	 */
	public String getDeviceIp() {
		return deviceIp;
	}

	/**
	 * @param deviceIp
	 *            String
	 */
	public void setDeviceIp(String deviceIp) {
		this.deviceIp = deviceIp;
	}

	/**
	 * @return userEmail
	 */
	public String getUserEmail() {
		return userEmail;
	}

	/**
	 * @param userEmail String
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
}
