/*
 * Copyright (c) 2014 SK planet.
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
 * 휴대기기 기기변경이력 정보 Value Object
 * 
 * Updated on : 2014. 02. 17. Updated by : 황정택, 와이즈스톤.
 */
public class ChangedDeviceLog extends CommonInfo implements Serializable {
	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 휴대기기 Key. */
	private String deviceKey;

	/** 휴대기기 ID(MDN/UUID/MAC). */
	private String deviceID;

	/** 테넌트 ID. */
	private String tenantID;

	/** 사용자 Key. */
	private String userKey;

	/** 기기변경 여부. */
	private String isChanged;

	/** IDP 연동 메시지. */
	private String messageIDP;

	/** 수정 유형 코드(수정 GROUP CODE : US0021). */
	private String changeCaseCode; // CHG_CD 수정 유형 코드

	/** 기기 코드. */
	private String deviceCode; // DEVICE_CD

	/** 휴대기기 통합 관리 번호. */
	private String svcMangNum;

	/** 기존값. */
	private String preData;

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
	 * 기기변경 여부(Y/N)를 리턴한다.
	 * 
	 * @return isChanged - 기기변경 여부(Y/N)
	 */
	public String getIsChanged() {
		return this.isChanged;
	}

	/**
	 * 기기변경 여부(Y/N)를 설정한다.
	 * 
	 * @param isChanged
	 *            기기변경 여부(Y/N)
	 */
	public void setIsChanged(String isChanged) {
		this.isChanged = isChanged;
	}

	/**
	 * IDP 연동 메시지를 리턴한다.
	 * 
	 * @return messageIDP - IDP 연동 메시지
	 */
	public String getMessageIDP() {
		return this.messageIDP;
	}

	/**
	 * IDP 연동 메시지를 설정한다.
	 * 
	 * @param messageIDP
	 *            IDP 연동 메시지
	 */
	public void setMessageIDP(String messageIDP) {
		this.messageIDP = messageIDP;
	}

	/**
	 * 수정 유형 코드를 리턴한다.
	 * 
	 * @return changeCaseCode - 수정 유형 코드
	 */
	public String getChangeCaseCode() {
		return this.changeCaseCode;
	}

	/**
	 * 수정 유형 코드를 설정한다.
	 * 
	 * @param changeCaseCode
	 *            수정 유형 코드
	 */
	public void setChangeCaseCode(String changeCaseCode) {
		this.changeCaseCode = changeCaseCode;
	}

	/**
	 * 기기 코드를 리턴한다.
	 * 
	 * @return deviceCode - 기기 코드
	 */
	public String getDeviceCode() {
		return this.deviceCode;
	}

	/**
	 * 기기 코드를 설정한다.
	 * 
	 * @param deviceCode
	 *            기기 코드
	 */
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	/**
	 * 휴대기기 통합관리번호를 리턴한다.
	 * 
	 * @return svcMangNum - 통합관리번호
	 */
	public String getSvcMangNum() {
		return this.svcMangNum;
	}

	/**
	 * 휴대기기 통합관리번호를 설정한다.
	 * 
	 * @param svcMangNum
	 *            통합관리번호
	 */
	public void setSvcMangNum(String svcMangNum) {
		this.svcMangNum = svcMangNum;
	}

	/**
	 * 기존값을 리턴한다.
	 * 
	 * @return preData - 기존값
	 */
	public String getPreData() {
		return this.preData;
	}

	/**
	 * 기존값을 설정한다.
	 * 
	 * @param preData
	 *            기존값
	 */
	public void setPreData(String preData) {
		this.preData = preData;
	}

	/**
	 * Returns the serial version UID.
	 * 
	 * @return serialVersionUID - the serial version UID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
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
