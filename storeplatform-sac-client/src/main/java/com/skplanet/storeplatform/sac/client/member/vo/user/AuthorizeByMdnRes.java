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
	private String userKey = "";

	/**
	 * 사용자 구분코드.
	 */
	private String userType = "";

	/**
	 * 기기 Key.
	 */
	private String deviceKey = "";

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
	 * 로그인 성공여부(Y/N).
	 */
	private String isLoginSuccess = "";

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public String getUserMainStatus() {
        return userMainStatus;
    }

    public void setUserMainStatus(String userMainStatus) {
        this.userMainStatus = userMainStatus;
    }

    public String getUserSubStatus() {
        return userSubStatus;
    }

    public void setUserSubStatus(String userSubStatus) {
        this.userSubStatus = userSubStatus;
    }

    public String getLoginStatusCode() {
        return loginStatusCode;
    }

    public void setLoginStatusCode(String loginStatusCode) {
        this.loginStatusCode = loginStatusCode;
    }

    public String getIsLoginSuccess() {
        return isLoginSuccess;
    }

    public void setIsLoginSuccess(String isLoginSuccess) {
        this.isLoginSuccess = isLoginSuccess;
    }
}
