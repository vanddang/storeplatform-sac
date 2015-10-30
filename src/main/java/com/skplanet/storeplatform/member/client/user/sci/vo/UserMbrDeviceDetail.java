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

// TODO: Auto-generated Javadoc
/**
 * 사용자 휴대기기 부가속성 및 기기상세(관리항목) Value Object.
 */
public class UserMbrDeviceDetail extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 휴대기기 부가속성 및 기기상세(관리항목) 코드. */
	private String extraProfile;

	/** 휴대기기 부가속성 및 기기상세(관리항목) 값. */
	private String extraProfileValue;

	/** 사용자 Key. */
	private String userKey;

	/** 휴대기기 Key. */
	private String deviceKey;

	/** 휴대기기 ID. OGG용, 추후 삭제 예정, 2014.03.20. */
	private String deviceID;

	/** 테넌트 ID. */
	private String tenantID;

	/** 휴대기기 등록 일시. */
	private String regDate;

	/** 휴대기기 등록 ID. */
	private String regID;

	/** Systen ID. */
	private String systemID;

	/** 휴대기기 수정 일시. */
	private String updateDate;

	/** 현재 일시. */
	private String nowDate;

	/** 등록일시 + 7일. */
	private String regDatePlus7;

	/** 수정일시 + 7 일. */
	private String updateDatePlue7;

	/**
	 * 사용자 휴대기기 부가속성 및 기기상세(관리항목) 코드를 리턴한다.
	 * 
	 * @return extraProfile - 사용자 휴대기기 부가속성 및 기기상세(관리항목) 코드
	 */
	public String getExtraProfile() {
		return this.extraProfile;
	}

	/**
	 * 사용자 휴대기기 부가속성 및 기기상세(관리항목) 설정를 리턴한다.
	 * 
	 * @param extraProfile
	 *            사용자 휴대기기 부가속성 및 기기상세(관리항목) 코드
	 */
	public void setExtraProfile(String extraProfile) {
		this.extraProfile = extraProfile;
	}

	/**
	 * 사용자 휴대기기 부가속성 및 기기상세(관리항목)을 리턴한다.
	 * 
	 * @return extraProfileValue - 사용자 휴대기기 부가속성 및 기기상세(관리항목)
	 */
	public String getExtraProfileValue() {
		return this.extraProfileValue;
	}

	/**
	 * 사용자 휴대기기 부가속성 및 기기상세(관리항목)을 설정한다.
	 * 
	 * @param extraProfileValue
	 *            사용자 휴대기기 부가속성 및 기기상세(관리항목)
	 */
	public void setExtraProfileValue(String extraProfileValue) {
		this.extraProfileValue = extraProfileValue;
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
	 * 사용자 휴대기기 Key를 리턴한다.
	 * 
	 * @return deviceKey - 사용자 휴대기기 Key
	 */
	public String getDeviceKey() {
		return this.deviceKey;
	}

	/**
	 * 사용자 휴대기기 Key를 설정한다.
	 * 
	 * @param deviceKey
	 *            사용자 휴대기기 Key
	 */
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	/**
	 * 사용자 휴대기기의 등록일을 리턴한다.
	 * 
	 * @return regDate - 사용자 휴대기기의 등록일자
	 */
	public String getRegDate() {
		return this.regDate;
	}

	/**
	 * 사용자 휴대기기의 등록일을 설정한다.
	 * 
	 * @param regDate
	 *            사용자 휴대기기의 등록일자
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	/**
	 * 휴대기기 등록자를 리턴한다.
	 * 
	 * @return regID - 휴대기기 등록자 ID
	 */
	public String getRegID() {
		return this.regID;
	}

	/**
	 * 휴대기기 등록자를 설정한다.
	 * 
	 * @param regID
	 *            휴대기기 등록자 ID
	 */
	public void setRegID(String regID) {
		this.regID = regID;
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
	 * <pre>
	 * 부가 속성을 등록한 SystemID를 리턴한다.
	 * </pre>
	 * 
	 * @return systemID
	 */
	public String getSystemID() {
		return this.systemID;
	}

	/**
	 * <pre>
	 * 부가 속성을 등록한 SystemID를 설정한다.
	 * </pre>
	 * 
	 * @param systemID
	 *            String
	 */
	public void setSystemID(String systemID) {
		this.systemID = systemID;
	}

	/**
	 * @return the updateDate
	 */
	public String getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * @param updateDate
	 *            the updateDate to set
	 */
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the nowDate
	 */
	public String getNowDate() {
		return this.nowDate;
	}

	/**
	 * @param nowDate
	 *            the nowDate to set
	 */
	public void setNowDate(String nowDate) {
		this.nowDate = nowDate;
	}

	/**
	 * @return the regDatePlus7
	 */
	public String getRegDatePlus7() {
		return this.regDatePlus7;
	}

	/**
	 * @param regDatePlus7
	 *            the regDatePlus7 to set
	 */
	public void setRegDatePlus7(String regDatePlus7) {
		this.regDatePlus7 = regDatePlus7;
	}

	/**
	 * @return the updateDatePlue7
	 */
	public String getUpdateDatePlue7() {
		return this.updateDatePlue7;
	}

	/**
	 * @param updateDatePlue7
	 *            the updateDatePlue7 to set
	 */
	public void setUpdateDatePlue7(String updateDatePlue7) {
		this.updateDatePlue7 = updateDatePlue7;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Utils.printKeyValues(this);
	}

}
