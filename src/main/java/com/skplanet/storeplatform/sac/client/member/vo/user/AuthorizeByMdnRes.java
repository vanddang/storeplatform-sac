package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE] 모바일 전용 회원 인증(MDN 인증)
 * 
 * Updated on : 2014. 1. 3. Updated by : 반범진. 지티소프트.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class AuthorizeByMdnRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 고유 Key
	 */
	private String userKey;

	/**
	 * 사용자 구분코드
	 */
	private String userType;

	/**
	 * 기기 Key
	 */
	private String deviceKey;

	/**
	 * 사용자 main 상태코드
	 */
	private String userMainStatus;

	/**
	 * 사용자 sub 상태코드
	 */
	private String userSubStatus;

	/**
	 * 로그인 상태코드
	 */
	private String loginStatusCode;

	/**
	 * 직권중지 상태코드
	 */
	private String stopStatusCode;

	public String getUserKey() {
		return this.userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserMainStatus() {
		return this.userMainStatus;
	}

	public void setUserMainStatus(String userMainStatus) {
		this.userMainStatus = userMainStatus;
	}

	public String getUserSubStatus() {
		return this.userSubStatus;
	}

	public void setUserSubStatus(String userSubStatus) {
		this.userSubStatus = userSubStatus;
	}

	public String getLoginStatusCode() {
		return this.loginStatusCode;
	}

	public void setLoginStatusCode(String loginStatusCode) {
		this.loginStatusCode = loginStatusCode;
	}

	public String getStopStatusCode() {
		return this.stopStatusCode;
	}

	public void setStopStatusCode(String stopStatusCode) {
		this.stopStatusCode = stopStatusCode;
	}

	public String getDeviceKey() {
		return this.deviceKey;
	}

	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

}
