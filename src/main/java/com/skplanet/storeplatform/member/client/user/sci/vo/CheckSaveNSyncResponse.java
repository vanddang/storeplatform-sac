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
import com.skplanet.storeplatform.member.client.common.vo.CommonResponse;

/**
 * 변동성 대상 체크 응답 Value Object.
 * 
 * Updated on : 2014. 1. 3 Updated by : wisestone_brian, wisestone
 */
public class CheckSaveNSyncResponse extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 응답 Value Object. */
	private CommonResponse commonResponse;

	/** 사용자 Key. */
	private String userKey;

	/** 외부(IDP)에서 할당된 사용자 Key. */
	private String imMbrNo; // IDP 통합서비스 키 USERMBR_NO

	/** 휴대기기 Key. */
	private String deviceKey;

	/** 휴대기기 ID(MDN/UUID/MAC). */
	private String deviceID;

	/** 이전 휴대기기 ID(MDN/UUID/MAC). */
	private String preDeviceID; // PRE_DEVICE_ID

	/** SKT 서비스관리번호. */
	private String svcMangNo;

	/**
	 * 변동성대상여부.
	 * 
	 * 변동성대상 : Y, 변동성대상아님 : N.
	 */
	private String isSaveNSync;

	/**
	 * 정상여부.
	 * 
	 * 정상상태 : Y, 탈퇴상태 : N.
	 */
	private String isActive;

	/** 수정 유형 코드(수정 GROUP CODE : US0120). */
	private String changeCaseCode; // CHG_CASE_CD 수정 유형 코드

	/**
	 * 사용자 서브 상태 코드.
	 * 
	 * @see com.skplanet.storeplatform.member.common.code.SubStateCode
	 */
	private String userSubStatus;

	/**
	 * 공통 응답 Value Object를 리턴한다.
	 * 
	 * @return CommonResponse - 공통 응답 Value Object
	 */
	public CommonResponse getCommonResponse() {
		return this.commonResponse;
	}

	/**
	 * 공통 응답 Value Object를 설정한다.
	 * 
	 * @param commonResponse
	 *            공통 응답 Value Object
	 */
	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
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
	 * 외부(IDP/OneID)에서 할당된 회원 Key를 리턴한다.
	 * 
	 * @return imMbrNo - 외부(IDP/OneID)에서 할당된 회원 Key
	 */
	public String getImMbrNo() {
		return this.imMbrNo;
	}

	/**
	 * 외부(IDP/OneID)에서 할당된 회원 Key를 설정한다.
	 * 
	 * @param imMbrNo
	 *            외부(IDP/OneID)에서 할당된 회원 Key
	 */
	public void setImMbrNo(String imMbrNo) {
		this.imMbrNo = imMbrNo;
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
	 * 이전 휴대기기 ID(MDN/UUID/MAC) 정보를 리턴한다.
	 * 
	 * @return preDeviceID - 이전 휴대기기 ID(MDN/UUID/MAC) 정보
	 */
	public String getPreDeviceID() {
		return this.preDeviceID;
	}

	/**
	 * 이전 휴대기기 ID(MDN/UUID/MAC) 정보를 설정한다.
	 * 
	 * @param preDeviceID
	 *            이전 휴대기기 ID(MDN/UUID/MAC) 정보
	 */
	public void setPreDeviceID(String preDeviceID) {
		this.preDeviceID = preDeviceID;
	}

	/**
	 * 변동성대상여부를 리턴한다.
	 * 
	 * @return isSaveNSync - 변동성대상여부
	 */
	public String getIsSaveNSync() {
		return this.isSaveNSync;
	}

	/**
	 * 변동성대상여부를 설정한다.
	 * 
	 * @param isSaveNSync
	 *            변동성대상여부
	 */
	public void setIsSaveNSync(String isSaveNSync) {
		this.isSaveNSync = isSaveNSync;
	}

	/**
	 * 정상여부를 리턴한다.
	 * 
	 * @return isActive - 정상여부
	 */
	public String getIsActive() {
		return this.isActive;
	}

	/**
	 * 정상여부를 설정한다.
	 * 
	 * @param isActive
	 *            정상여부
	 */
	public void setIsActive(String isActive) {
		this.isActive = isActive;
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
	 * <pre>
	 * SKT 서비스관리번호를 리턴한다.
	 * </pre>
	 * 
	 * @return svcMangNo
	 */
	public String getSvcMangNo() {
		return this.svcMangNo;
	}

	/**
	 * <pre>
	 * SKT 서비스관리번호를 설정한다.
	 * </pre>
	 * 
	 * @param svcMangNo
	 *            String
	 */
	public void setSvcMangNo(String svcMangNo) {
		this.svcMangNo = svcMangNo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.framework.core.common.vo.CommonInfo#toString()
	 */
	@Override
	public String toString() {
		return Utils.printKeyValues(this);
	}
}
