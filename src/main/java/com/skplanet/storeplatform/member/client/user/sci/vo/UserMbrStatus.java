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
import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 사용자 상태정보 Value Object
 * 
 * Updated on : 2013. 12. 10. Updated by : wisestone_mikepark
 */
public class UserMbrStatus extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 테넌트 ID. */
	private String tenantID;

	/** 사용자 Key. */
	private String userKey;

	/**
	 * 사용자 구분 코드. ex) US011501(모바일회원) , US011502(IDP 회원) , US011503(OneID 회원)
	 * 
	 * @see com.skplanet.storeplatform.member.common.code.UserTypeCode
	 */
	private String userType;

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

	/** 사용자 ID. */
	private String userID;

	/** Device Id List. */
	private List<String> deviceIDList;

	/** Device key List. */
	private List<String> deviceKeyList;

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
	 * 사용자 Key를 리턴한다.
	 * 
	 * @return userKey - 사용자 Key
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * 사용자 Key를 설정한다.
	 * 
	 * @param userKey
	 *            사용자 Key
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

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
	 * Device Id List를 리턴한다.
	 * 
	 * @return deviceIDList
	 */
	public List<String> getDeviceIDList() {
		return this.deviceIDList;
	}

	/**
	 * Device Id List를 설정한다.
	 * 
	 * @param deviceIDList
	 *            Device Id List
	 */
	public void setDeviceIDList(List<String> deviceIDList) {
		this.deviceIDList = deviceIDList;
	}

	/**
	 * @return the deviceKeyList
	 */
	public List<String> getDeviceKeyList() {
		return this.deviceKeyList;
	}

	/**
	 * @param deviceKeyList
	 *            the deviceKeyList to set
	 */
	public void setDeviceKeyList(List<String> deviceKeyList) {
		this.deviceKeyList = deviceKeyList;
	}

}
