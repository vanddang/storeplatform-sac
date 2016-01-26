package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * [RESPONSE] ID 기반 회원 인증 V2.
 * 
 * Updated on : 2016. 1. 4. Updated by : 반범진.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class AuthorizeByIdV2SacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 고유 Key.
	 */
	private String userKey = "";

	/**
	 * 기기 Key.
	 */
	private String deviceKey = "";

	/**
	 * 사용자 구분코드.
	 */
	private String userType = "";

	/**
	 * 사용자 상태코드.
	 */
	private String userStatus = "";

	/**
	 * 사용자 인증 토큰 유효성 여부(Y/N).
	 */
	private String isLoginSuccess = "";

	/**
	 * 단말 등록여부(Y/N).
	 */
	private String isRegDevice = "";

	/**
	 * @return userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            String
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return userType
	 */
	public String getUserType() {
		return this.userType;
	}

	/**
	 * @param userType
	 *            String
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * @return deviceKey
	 */
	public String getDeviceKey() {
		return this.deviceKey;
	}

	/**
	 * @param deviceKey
	 *            String
	 */
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	/**
	 * @return isLoginSuccess
	 */
	public String getIsLoginSuccess() {
		return this.isLoginSuccess;
	}

	/**
	 * @param isLoginSuccess
	 *            String
	 */
	public void setIsLoginSuccess(String isLoginSuccess) {
		this.isLoginSuccess = isLoginSuccess;
	}

	/**
	 * @return userStatus
	 */
	public String getUserStatus() {
		return userStatus;
	}

	/**
	 * @param userStatus
	 *            String
	 */
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	/**
	 * @return isRegDevice
	 */
	public String getIsRegDevice() {
		return isRegDevice;
	}

	/**
	 * @param isRegDevice
	 *            String
	 */
	public void setIsRegDevice(String isRegDevice) {
		this.isRegDevice = isRegDevice;
	}
}
