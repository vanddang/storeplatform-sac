/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.user.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 사용자회원 로그인 로그 Value Object
 * 
 * Updated on : 2014. 01. 21. Updated by : wisestone_dinga
 */
public class UserMbrLoginLog extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 테넌트 ID. */
	private String tenantID;

	/** 테넌트의 시스템 ID. */
	private String systemID;

	/** 사용자 Key. */
	private String userKey;

	/** 접속_일시. */
	private String connDate;

	/** 접속_IP. */
	private String connIp;

	/** SC_버전. */
	private String scVersion;

	/** 자동로그인 여부. */
	private String isAutoLogin; // AUTO_LOGIN_YN

	/** 로그인 사유. */
	private String loginReason; // LOGIN_REASON

	/** 기기_모델_명. */
	private String deviceModelNm;

	/** 기기_OS_명. */
	private String deviceOsNm;

	/** 기기_OS_버전. */
	private String deviceOsVersion;

	/**
	 * Tenant ID를 리턴한다.
	 * 
	 * @return tenantID - Tenant ID
	 */
	public String getTenantID() {
		return this.tenantID;
	}

	/**
	 * Tenant ID를 설정한다.
	 * 
	 * @param tenantID
	 *            Tenant ID
	 */
	public void setTenantID(String tenantID) {
		this.tenantID = tenantID;
	}

	/**
	 * System ID를 리턴한다.
	 * 
	 * @return systemID - System ID
	 */
	public String getSystemID() {
		return this.systemID;
	}

	/**
	 * System ID를 설정한다.
	 * 
	 * @param systemID
	 *            System ID
	 */
	public void setSystemID(String systemID) {
		this.systemID = systemID;
	}

	/**
	 * 내부 회원 키를 리턴한다.
	 * 
	 * @return userKey - 내부 회원 키
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * 내부 회원 키를 설정한다.
	 * 
	 * @param userKey
	 *            내부 회원 키
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * 접속_일시를 리턴한다.
	 * 
	 * @return connDate - 접속_일시
	 */
	public String getConnDate() {
		return this.connDate;
	}

	/**
	 * 접속_일시를 설정한다.
	 * 
	 * @param connDate
	 *            접속_일시
	 */
	public void setConnDate(String connDate) {
		this.connDate = connDate;
	}

	/**
	 * 접속 IP 주소를 리턴한다.
	 * 
	 * @return connIp - 접속_IP
	 */
	public String getConnIp() {
		return this.connIp;
	}

	/**
	 * 접속 IP 주소를 설정한다.
	 * 
	 * @param connIp
	 *            접속_IP
	 */
	public void setConnIp(String connIp) {
		this.connIp = connIp;
	}

	/**
	 * SC_버전를 리턴한다.
	 * 
	 * @return scVersion - SC_버전
	 */
	public String getScVersion() {
		return this.scVersion;
	}

	/**
	 * SC_버전를 설정한다.
	 * 
	 * @param scVersion
	 *            SC_버전
	 */
	public void setScVersion(String scVersion) {
		this.scVersion = scVersion;
	}

	/**
	 * 자동로그인 여부를 리턴한다.
	 * 
	 * @return isAutoLogin - 자동로그인 여부
	 */
	public String getIsAutoLogin() {
		return this.isAutoLogin;
	}

	/**
	 * 자동로그인 여부를 설정한다.
	 * 
	 * @param isAutoLogin
	 *            자동로그인 여부
	 */
	public void setIsAutoLogin(String isAutoLogin) {
		this.isAutoLogin = isAutoLogin;
	}

	/**
	 * 로그인 사유를 리턴한다.
	 * 
	 * @return loginReason - 로그인 사유
	 */
	public String getLoginReason() {
		return this.loginReason;
	}

	/**
	 * 로그인 사유를 설정한다.
	 * 
	 * @param loginReason
	 *            로그인 사유
	 */
	public void setLoginReason(String loginReason) {
		this.loginReason = loginReason;
	}

	/**
	 * @return the deviceModelNm
	 */
	public String getDeviceModelNm() {
		return this.deviceModelNm;
	}

	/**
	 * @param deviceModelNm
	 *            the deviceModelNm to set
	 */
	public void setDeviceModelNm(String deviceModelNm) {
		this.deviceModelNm = deviceModelNm;
	}

	/**
	 * @return the deviceOsNm
	 */
	public String getDeviceOsNm() {
		return this.deviceOsNm;
	}

	/**
	 * @param deviceOsNm
	 *            the deviceOsNm to set
	 */
	public void setDeviceOsNm(String deviceOsNm) {
		this.deviceOsNm = deviceOsNm;
	}

	/**
	 * @return the deviceOsVersion
	 */
	public String getDeviceOsVersion() {
		return this.deviceOsVersion;
	}

	/**
	 * @param deviceOsVersion
	 *            the deviceOsVersion to set
	 */
	public void setDeviceOsVersion(String deviceOsVersion) {
		this.deviceOsVersion = deviceOsVersion;
	}

}
