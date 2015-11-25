/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;

/**
 * 휴대기기 상태정보 Value Object
 * 
 * Updated on : 2013. 12. 10. Updated by : wisestone_mikepark
 */
public class DeviceMbrStatus extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 테넌트 ID. */
	private String tenantID;

	/** 휴대기기 Key. */
	private String deviceKey;

	/** 휴대기기 ID(MDN/UUID/MAC). */
	private String deviceID;

	/** 실명인증 여부 (Y/N). */
	private String isRealName;

	/** 실명인증 회원명. */
	private String authName; // TB_US_MBR_AUTH.MBR_NM

	/** 실명인증 생년월일. */
	private String authBirthDay; // TB_US_MBR_AUTH.BIRTH

	/** 사용자 이름. */
	private String userName; // TB_US_USERMBR.MBR_NM

	/** 사용자 생년월일. */
	private String userBirthDay; // TB_US_USERMBR.BIRTH

	/** 휴대기기 모델 코드. */
	private String deviceModelNo;

	/** 휴대기기 통신사 코드. */
	private String deviceTelecom;

	/** 외부 회원키. */
	private String imMbrNo;

	/**
	 * 사용자 구분 코드. ex) US011501(모바일회원) , US011502(IDP 회원) , US011503(OneID 회원)
	 * 
	 * @see com.skplanet.storeplatform.member.common.code.UserTypeCode
	 */
	private String userType;

	/** 사용자 ID. */
	private String userID;

	/**
	 * 사용자 구분 코드를 리턴한다.
	 * 
	 * @return the userType - 사용자 구분 코드
	 * 
	 * @see com.skplanet.storeplatform.member.common.code.UserTypeCode
	 */
	public String getUserType() {
		return this.userType;
	}

	/**
	 * 사용자 구분 코드를 설정한다.
	 * 
	 * @param userType
	 *            사용자 구분 코드
	 * 
	 * @see com.skplanet.storeplatform.member.common.code.UserTypeCode
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * 사용자 ID를 리턴한다.
	 * 
	 * @return userID
	 */
	public String getUserID() {
		return this.userID;
	}

	/**
	 * 사용자 ID를 설정한다.
	 * 
	 * @param userID
	 *            사용자 ID
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}

	/**
	 * 사용자 메인 상태 코드.
	 * 
	 * @see com.skplanet.storeplatform.member.common.code.MainStateCode
	 */
	private String userMainStatus;

	/**
	 * 사용자 서브 상태 코드.
	 * 
	 * @see com.skplanet.storeplatform.member.common.code.SubStateCode
	 */
	private String userSubStatus;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Utils.printKeyValues(this);
	}

	/**
	 * 테넌트 ID를 리턴한다.
	 * 
	 * @return tenantID - 테넌트 ID
	 */
	public String getTenantID() {
		return this.tenantID;
	}

	/**
	 * 테넌트 ID를 설정한다.
	 * 
	 * @param tenantID
	 *            테넌트 ID
	 */
	public void setTenantID(String tenantID) {
		this.tenantID = tenantID;
	}

	/**
	 * 휴대기기 Key를 리턴한다.
	 * 
	 * @return deviceKey - 휴대기기 Key
	 */
	public String getDeviceKey() {
		return this.deviceKey;
	}

	/**
	 * 휴대기기 Key를 리턴한다.
	 * 
	 * @param deviceKey
	 *            휴대기기 Key
	 */
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	/**
	 * 휴대기기 ID(MDN/UUID/MAC) 정보를 리턴한다.
	 * 
	 * @return deviceID - 휴대기기 ID(MDN/UUID/MAC) 정보
	 */
	public String getDeviceID() {
		return this.deviceID;
	}

	/**
	 * 휴대기기 ID(MDN/UUID/MAC) 정보를 설정한다.
	 * 
	 * @param deviceID
	 *            휴대기기 ID(MDN/UUID/MAC) 정보
	 */
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	/**
	 * 실명인증 여부 (Y/N)를 리턴한다.
	 * 
	 * @return isRealName - 실명인증 여부 (Y/N)
	 */
	public String getIsRealName() {
		return this.isRealName;
	}

	/**
	 * 실명인증 여부 (Y/N)를 설정한다.
	 * 
	 * @param isRealName
	 *            실명인증 여부 (Y/N)
	 */
	public void setIsRealName(String isRealName) {
		this.isRealName = isRealName;
	}

	/**
	 * 실명인증 회원명을 리턴한다.
	 * 
	 * @return authName - 실명인증 회원명
	 */
	public String getAuthName() {
		return this.authName;
	}

	/**
	 * 실명인증 회원명을 설정한다.
	 * 
	 * @param authName
	 *            실명인증 회원명
	 */
	public void setAuthName(String authName) {
		this.authName = authName;
	}

	/**
	 * 실명인증 생년월일을 리턴한다.
	 * 
	 * @return authBirthDay - 실명인증 생년월일
	 */
	public String getAuthBirthDay() {
		return this.authBirthDay;
	}

	/**
	 * 실명인증 생년월일을 설정한다.
	 * 
	 * @param authBirthDay
	 *            실명인증 생년월일
	 */
	public void setAuthBirthDay(String authBirthDay) {
		this.authBirthDay = authBirthDay;
	}

	/**
	 * 휴대기기 모델정보를 리턴한다.
	 * 
	 * @return deviceModelNo - 휴대기기 모델정보
	 */
	public String getDeviceModelNo() {
		return this.deviceModelNo;
	}

	/**
	 * 휴대기기 모델정보를 리턴한다.
	 * 
	 * @param deviceModelNo
	 *            휴대기기 모델정보
	 */
	public void setDeviceModelNo(String deviceModelNo) {
		this.deviceModelNo = deviceModelNo;
	}

	/**
	 * 휴대기기 통신사 정보를 리턴한다.
	 * 
	 * @return deviceTelecom - 휴대기기 통신사 정보
	 */
	public String getDeviceTelecom() {
		return this.deviceTelecom;
	}

	/**
	 * 휴대기기 통신사 정보를 설정한다.
	 * 
	 * @param deviceTelecom
	 *            휴대기기 통신사 정보
	 */
	public void setDeviceTelecom(String deviceTelecom) {
		this.deviceTelecom = deviceTelecom;
	}

	/**
	 * 사용자 메인 상태코드를 리턴한다.
	 * 
	 * @return userMainStatus - 사용자 메인 상태코드
	 * 
	 * @see com.skplanet.storeplatform.member.common.code.MainStateCode
	 */
	public String getUserMainStatus() {
		return this.userMainStatus;
	}

	/**
	 * 사용자 메인 상태코드를 설정한다.
	 * 
	 * @param userMainStatus
	 *            사용자 메인 상태코드
	 * 
	 * @see com.skplanet.storeplatform.member.common.code.MainStateCode
	 */
	public void setUserMainStatus(String userMainStatus) {
		this.userMainStatus = userMainStatus;
	}

	/**
	 * 사용자 서브 상태코드를 리턴한다.
	 * 
	 * @return userSubStatus - 사용자 서브 상태코드
	 * 
	 * @see com.skplanet.storeplatform.member.common.code.SubStateCode
	 */
	public String getUserSubStatus() {
		return this.userSubStatus;
	}

	/**
	 * 사용자 서브 상태코드를 설정한다.
	 * 
	 * @param userSubStatus
	 *            사용자 서브 상태코드
	 * 
	 * @see com.skplanet.storeplatform.member.common.code.SubStateCode
	 */
	public void setUserSubStatus(String userSubStatus) {
		this.userSubStatus = userSubStatus;
	}

	/**
	 * 사용자 이름을 리턴한다.
	 * 
	 * @return userName - 사용자 이름
	 */
	public String getUserName() {
		return this.userName;
	}

	/**
	 * 사용자 이름을 설정한다.
	 * 
	 * @param userName
	 *            사용자 이름
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 사용자 생년월일을 리턴한다.
	 * 
	 * @return userBirthDay - 사용자 생년월일
	 */
	public String getUserBirthDay() {
		return this.userBirthDay;
	}

	/**
	 * 사용자 생년월일을 설정한다.
	 * 
	 * @param userBirthDay
	 *            사용자 생년월일
	 */
	public void setUserBirthDay(String userBirthDay) {
		this.userBirthDay = userBirthDay;
	}

	/**
	 * @return imMbrNo
	 */
	public String getImMbrNo() {
		return this.imMbrNo;
	}

	/**
	 * @param imMbrNo
	 *            String
	 */
	public void setImMbrNo(String imMbrNo) {
		this.imMbrNo = imMbrNo;
	}

}
