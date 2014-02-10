package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE] ID 기반 회원 인증 (One ID, IDP 회원).
 * 
 * Updated on : 2014. 1. 3. Updated by : 반범진. 지티소프트.
 */
@JsonSerialize(include = Inclusion.NON_EMPTY)
public class AuthorizeByIdRes extends CommonInfo {

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
	 * 기기 Key.
	 */
	private String deviceKey;

	/**
	 * 사용자 구분코드.
	 */
	private String userType;

	/**
	 * 사용자 main 상태코드.
	 */
	private String userMainStatus;

	/**
	 * 사용자 sub 상태코드.
	 */
	private String userSubStatus;

	/**
	 * 가입사이트 코드.
	 */
	private String joinSiteCd;

	/**
	 * 가입사이트 코드 값.
	 */
	private String joinSiteNm;

	/**
	 * 로그인 상태코드.
	 */
	private String loginStatusCode;

	/**
	 * 직권중지 상태코드.
	 */
	private String stopStatusCode;

	/**
	 * 통합아이디 관리번호.
	 */
	private String imIntSvcNo;

	/**
	 * 로그인 실패 카운트.
	 */
	private String loginFailCount;

	/**
	 * 로그인 성공유무(Y/N).
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
	 * @return userAuthKey
	 */
	public String getUserAuthKey() {
		return this.userAuthKey;
	}

	/**
	 * @param userAuthKey
	 *            String
	 */
	public void setUserAuthKey(String userAuthKey) {
		this.userAuthKey = userAuthKey;
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
	 * @return joinSiteCd
	 */
	public String getJoinSiteCd() {
		return this.joinSiteCd;
	}

	/**
	 * @param joinSiteCd
	 *            String
	 */
	public void setJoinSiteCd(String joinSiteCd) {
		this.joinSiteCd = joinSiteCd;
	}

	/**
	 * @return joinSiteNm
	 */
	public String getJoinSiteNm() {
		return this.joinSiteNm;
	}

	/**
	 * @param joinSiteNm
	 *            String
	 */
	public void setJoinSiteNm(String joinSiteNm) {
		this.joinSiteNm = joinSiteNm;
	}

	/**
	 * @return imIntSvcNo
	 */
	public String getImIntSvcNo() {
		return this.imIntSvcNo;
	}

	/**
	 * @param imIntSvcNo
	 *            String
	 */
	public void setImIntSvcNo(String imIntSvcNo) {
		this.imIntSvcNo = imIntSvcNo;
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
