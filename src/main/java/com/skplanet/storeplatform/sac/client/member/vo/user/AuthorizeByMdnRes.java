package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE] 모바일 전용 회원 인증(MDN 인증).
 * 
 * Updated on : 2014. 1. 3. Updated by : 반범진. 지티소프트.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class AuthorizeByMdnRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 고유 Key.
	 */
	private String userKey;

	/**
	 * IDP 인증 Key.
	 */
	private String userAuthKey;

	/**
	 * 사용자 구분코드.
	 */
	private String userType;

	/**
	 * 기기 Key.
	 */
	private String deviceKey;

	/**
	 * 사용자 main 상태코드.
	 */
	private String userMainStatus;

	/**
	 * 사용자 sub 상태코드.
	 */
	private String userSubStatus;

	/**
	 * 로그인 상태코드.
	 */
	private String loginStatusCode;

	/**
	 * 직권중지 상태코드.
	 */
	private String stopStatusCode;

	/**
	 * 로그인 성공여부(Y/N).
	 */
	private String isLoginSuccess;

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
	 * @return userMainStatus
	 */
	public String getUserMainStatus() {
		return this.userMainStatus;
	}

	/**
	 * @param userMainStatus
	 *            String
	 */
	public void setUserMainStatus(String userMainStatus) {
		this.userMainStatus = userMainStatus;
	}

	/**
	 * @return userSubStatus
	 */
	public String getUserSubStatus() {
		return this.userSubStatus;
	}

	/**
	 * @param userSubStatus
	 *            String
	 */
	public void setUserSubStatus(String userSubStatus) {
		this.userSubStatus = userSubStatus;
	}

	/**
	 * @return loginStatusCode
	 */
	public String getLoginStatusCode() {
		return this.loginStatusCode;
	}

	/**
	 * @param loginStatusCode
	 *            String
	 */
	public void setLoginStatusCode(String loginStatusCode) {
		this.loginStatusCode = loginStatusCode;
	}

	/**
	 * @return stopStatusCode
	 */
	public String getStopStatusCode() {
		return this.stopStatusCode;
	}

	/**
	 * @param stopStatusCode
	 *            String
	 */
	public void setStopStatusCode(String stopStatusCode) {
		this.stopStatusCode = stopStatusCode;
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

	public String getUserAuthKey() {
		return this.userAuthKey;
	}

	public void setUserAuthKey(String userAuthKey) {
		this.userAuthKey = userAuthKey;
	}

}
