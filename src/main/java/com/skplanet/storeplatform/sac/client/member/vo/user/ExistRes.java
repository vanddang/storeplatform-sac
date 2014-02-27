package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE] 회원가입 여부 조회 (ID/MDN 기반)
 * 
 * Updated on : 2014. 1. 6. Updated by : 강신완, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ExistRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/* 사용자 Key, Value : IW102158844420091030165015 */
	private String userKey;
	/*
	 * 사용자 구분 코드, Value : US011501(기기사용자) US011502(IDP사용자) US011503(OneId사용자)
	 * null(Tstore 회원 아님)
	 */
	private String userType;
	/* 회원 아이디, Value : 모바일 회원이거나 Tstore, 회원이 아닐경우 null */
	private String userId;
	/* 실명인증 여부 Value : Y/N, Tstore회원이 아닐 경우 null */
	private String isRealName;
	/* 14세 미만 법정대리인 동의 여부 Value : Y/N, Tstore회원이 아닐 경우 null */
	private String agencyYn;
	/* 회원 이메일 정보 Value : 없을 경우 null */
	private String userEmail;
	/* 회원 메인 상태 코드 */
	private String userMainStatus;
	/* 회원 서브 상태 코드 */
	private String userSubStatus;

	/* Exception */
	private String code;
	private String message;
	private String hostName;

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

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIsRealName() {
		return this.isRealName;
	}

	public void setIsRealName(String isRealName) {
		this.isRealName = isRealName;
	}

	public String getAgencyYn() {
		return this.agencyYn;
	}

	public void setAgencyYn(String agencyYn) {
		this.agencyYn = agencyYn;
	}

	public String getUserEmail() {
		return this.userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getHostName() {
		return this.hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

}
