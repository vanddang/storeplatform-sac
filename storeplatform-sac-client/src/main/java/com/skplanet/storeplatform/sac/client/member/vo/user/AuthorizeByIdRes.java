package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE] ID 기반 회원 인증 (One ID, IDP 회원).
 * 
 * Updated on : 2014. 1. 3. Updated by : 반범진. 지티소프트.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class AuthorizeByIdRes extends CommonInfo {

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
	 * 사용자 main 상태코드.
	 */
	private String userMainStatus = "";

	/**
	 * 사용자 sub 상태코드.
	 */
	private String userSubStatus = "";

	/**
	 * 로그인 상태코드.
	 */
	private String loginStatusCode = "";

	/**
	 * 로그인 실패 카운트.
	 */
	private String loginFailCount = "";

	/**
	 * 로그인 성공유무(Y/N).
	 */
	private String isLoginSuccess = "";

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
	 * @return loginFailCount
	 */
	public String getLoginFailCount() {
		return this.loginFailCount;
	}

	/**
	 * @param loginFailCount
	 *            String
	 */
	public void setLoginFailCount(String loginFailCount) {
		this.loginFailCount = loginFailCount;
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

}
