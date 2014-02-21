/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.order.dummy.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * Dummy 회원정보
 * 
 * Updated on : 2014. 1. 16. Updated by : 이승택, nTels.
 */
public class DummyMember extends CommonInfo {

	private static final long serialVersionUID = 201401101L;

	private String tenantId; // 테넌트 ID
	private String systemId; // 시스템 ID
	private String userKey; // 내부 회원 번호
	private String userId; // 회원 ID
	private String deviceModelCd; // 디바이스 모델 코드
	private String userTypeCd; // 사용자 구분 코드: US011501-기기 사용자, US011502-IDP 사용자, US011503-OneID 사용자, null-Tstore 회원 아님
	private String userStatusCd; // 회원상태코드: US010701-정상, US010702-탈퇴, US010703-대기(가가입), US010704-가입, US010705-전환,
								 // US010706 : 탈퇴 - US010707-승인대기
	private String deviceKey; // 내부 디바이스 ID
	private String deviceId; // MDN, MAC 등등
	private String telecom; // 통신사
	private int age; // 연령
	private boolean bLogin; // 로그인 여부

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the systemId
	 */
	public String getSystemId() {
		return this.systemId;
	}

	/**
	 * @param systemId
	 *            the systemId to set
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	/**
	 * @return the userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the deviceKey
	 */
	public String getDeviceKey() {
		return this.deviceKey;
	}

	/**
	 * @param deviceKey
	 *            the deviceKey to set
	 */
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	/**
	 * @return the deviceId
	 */
	public String getDeviceId() {
		return this.deviceId;
	}

	/**
	 * @param deviceId
	 *            the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return the deviceModelCd
	 */
	public String getDeviceModelCd() {
		return this.deviceModelCd;
	}

	/**
	 * @param deviceModelCd
	 *            the deviceModelCd to set
	 */
	public void setDeviceModelCd(String deviceModelCd) {
		this.deviceModelCd = deviceModelCd;
	}

	/**
	 * @return the userTypeCd
	 */
	public String getUserTypeCd() {
		return this.userTypeCd;
	}

	/**
	 * @param userTypeCd
	 *            the userTypeCd to set
	 */
	public void setUserTypeCd(String userTypeCd) {
		this.userTypeCd = userTypeCd;
	}

	/**
	 * @return the userStatusCd
	 */
	public String getUserStatusCd() {
		return this.userStatusCd;
	}

	/**
	 * @param userStatusCd
	 *            the userStatusCd to set
	 */
	public void setUserStatusCd(String userStatusCd) {
		this.userStatusCd = userStatusCd;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return this.age;
	}

	/**
	 * @return the telecom
	 */
	public String getTelecom() {
		return this.telecom;
	}

	/**
	 * @param telecom
	 *            the telecom to set
	 */
	public void setTelecom(String telecom) {
		this.telecom = telecom;
	}

	/**
	 * @param age
	 *            the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * @return the bLogin
	 */
	public boolean getbLogin() {
		return this.bLogin;
	}

	/**
	 * @param bLogin
	 *            the bLogin to set
	 */
	public void setbLogin(boolean bLogin) {
		this.bLogin = bLogin;
	}

}
