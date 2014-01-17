package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE] ID 기반 회원 인증 (One ID, IDP 회원)
 * 
 * Updated on : 2014. 1. 3. Updated by : 반범진. 지티소프트.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class AuthorizeByIdRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 고유 Key
	 */
	private String userKey;

	/**
	 * IDP 인증 Key
	 */
	private String userAuthKey;

	/**
	 * 사용자 구분코드
	 */
	private String userType;

	/**
	 * 사용자 main 상태코드
	 */
	private String userMainStatus;

	/**
	 * 사용자 sub 상태코드
	 */
	private String userSubStatus;

	/**
	 * 가입사이트 코드
	 */
	private String joinSiteCd;

	/**
	 * 가입사이트 코드 값
	 */
	private String joinSiteNm;

	/**
	 * 로그인 상태코드
	 */
	private String loginStatusCode;

	/**
	 * 직권중지 상태코드
	 */
	private String stopStatusCode;

	/**
	 * 통합아이디 관리번호
	 */
	private String imIntSvcNo;

	public String getUserKey() {
		return this.userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public String getUserAuthKey() {
		return this.userAuthKey;
	}

	public void setUserAuthKey(String userAuthKey) {
		this.userAuthKey = userAuthKey;
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

	public String getJoinSiteCd() {
		return this.joinSiteCd;
	}

	public void setJoinSiteCd(String joinSiteCd) {
		this.joinSiteCd = joinSiteCd;
	}

	public String getJoinSiteNm() {
		return this.joinSiteNm;
	}

	public void setJoinSiteNm(String joinSiteNm) {
		this.joinSiteNm = joinSiteNm;
	}

	public String getImIntSvcNo() {
		return this.imIntSvcNo;
	}

	public void setImIntSvcNo(String imIntSvcNo) {
		this.imIntSvcNo = imIntSvcNo;
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

}
