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
import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;

/**
 * 사용자 휴대기기 정보 Value Object
 * 
 * Updated on : 2013. 12. 6. Updated by : 황정택, 와이즈스톤.
 */
public class UserMbrDevice extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 시작 일시. */
	private String startDate;

	/** 종료 일시. */
	private String endDate;

	/** 수정 일시. */
	private String updateDate;

	/** 휴대기기 Key. */
	private String deviceKey;

	/** 휴대기기 ID. */
	private String deviceID;

	/** 휴대기기 mdn. */
	private String mdn;

	/** 사용자 Key. */
	private String userKey;

	/** 휴대기기 모델 코드. */
	private String deviceModelNo;

	/** 휴대기기 통합 관리 번호. */
	private String svcMangNum;

	/** 휴대기기 통신사 코드. */
	private String deviceTelecom;

	/** 대표기기 여부. */
	private String isPrimary;

	/** 인증 날짜. */
	private String authenticationDate;

	/** SMS 수신 여부. */
	private String isRecvSMS;

	/** 휴대기기 고유 ID, imei. */
	private String nativeID;

	/** 휴대기기 계정. */
	private String deviceAccount;

	/** 가입 채널 코드. */
	private String joinId;

	/** 휴대기기 사용 여부. */
	private String isUsed; // USE_YN : Y/N

	/** 수정 유형 코드(수정 GROUP CODE : US0120). */
	private String changeCaseCode; // CHG_CASE_CD 수정 유형 코드

	/** 기기 부가속성 및 관리항목(기기상세) 정보 Value Object List. */
	private List<UserMbrDeviceDetail> userMbrDeviceDetail;

	/** 휴면 휴대기기 유무. */
	private String isDormant;

	/** 휴대기기 usim 번호. */
	private String deviceSimNm;

	/** 마지막 로그인 일자. */
	private String lastloginDt;

	/** 휴대기기 보유자 ID. */
	private String userID;

	/**
	 * 시작일자를 리턴한다.
	 * 
	 * @return startDate - 시작일자
	 */
	public String getStartDate() {
		return this.startDate;
	}

	/**
	 * 시작일자를 설정한다.
	 * 
	 * @param startDate
	 *            시작일자
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * 종료일을 리턴한다.
	 * 
	 * @return endDate - 종료일
	 */
	public String getEndDate() {
		return this.endDate;
	}

	/**
	 * 종료일을 설정한다.
	 * 
	 * @param endDate
	 *            종료일
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * 휴대기기 부가속성 및 기기상세(관리항목) 정보 Value Object List를 리턴한다.
	 * 
	 * @return userMbrDeviceDetail - 휴대기기 부가속성 및 기기상세(관리항목) 정보 Value Object List
	 */
	public List<UserMbrDeviceDetail> getUserMbrDeviceDetail() {
		return this.userMbrDeviceDetail;
	}

	/**
	 * 휴대기기 부가속성 및 기기상세(관리항목) 정보 Value Object List를 리턴한다.
	 * 
	 * @param userMbrDeviceDetail
	 *            휴대기기 부가속성 및 기기상세(관리항목) 정보 Value Object List
	 */
	public void setUserMbrDeviceDetail(List<UserMbrDeviceDetail> userMbrDeviceDetail) {
		this.userMbrDeviceDetail = userMbrDeviceDetail;
	}

	/**
	 * 휴대기기 사용여부(Y/N)를 리턴한다.
	 * 
	 * @return isUsed - 휴대기기 사용여부(Y/N)
	 */
	public String getIsUsed() {
		return this.isUsed;
	}

	/**
	 * 휴대기기 사용여부(Y/N)를 설정한다.
	 * 
	 * @param isUsed
	 *            휴대기기 사용여부(Y/N)
	 */
	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}

	/**
	 * 휴대기기 정보 수정일을 리턴한다.
	 * 
	 * @return updateDate - 휴대기기 정보 수정일
	 */
	public String getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * 휴대기기 정보 수정일을 설정한다.
	 * 
	 * @param updateDate
	 *            휴대기기 정보 수정일
	 */
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
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
	 * 대표기기 여부(Y/N)를 리턴한다.
	 * 
	 * @return isPrimary - 대표기기 여부(Y/N)
	 */
	public String getIsPrimary() {
		return this.isPrimary;
	}

	/**
	 * 대표기기 여부(Y/N)를 설정한다.
	 * 
	 * @param isPrimary
	 *            대표기기 여부(Y/N)
	 */
	public void setIsPrimary(String isPrimary) {
		this.isPrimary = isPrimary;
	}

	/**
	 * 휴대기기 인증일을 리턴한다.
	 * 
	 * @return authenticationDate - 휴대기기 인증일자
	 */
	public String getAuthenticationDate() {
		return this.authenticationDate;
	}

	/**
	 * 휴대기기 인증일을 설정한다.
	 * 
	 * @param authenticationDate
	 *            휴대기기 인증일자
	 */
	public void setAuthenticationDate(String authenticationDate) {
		this.authenticationDate = authenticationDate;
	}

	/**
	 * SMS 수신여부(Y/N)를 리턴한다.
	 * 
	 * @return isRecvSMS - SMS 수신여부(Y/N)
	 */
	public String getIsRecvSMS() {
		return this.isRecvSMS;
	}

	/**
	 * SMS 수신여부(Y/N)를 설정한다.
	 * 
	 * @param isRecvSMS
	 *            SMS 수신여부(Y/N)
	 */
	public void setIsRecvSMS(String isRecvSMS) {
		this.isRecvSMS = isRecvSMS;
	}

	/**
	 * 휴대기기 고유 ID를 리턴한다.
	 * 
	 * @return nativeID - 휴대기기 고유 ID
	 */
	public String getNativeID() {
		return this.nativeID;
	}

	/**
	 * 휴대기기 고유 ID를 설정한다.
	 * 
	 * @param nativeID
	 *            휴대기기 고유 ID
	 */
	public void setNativeID(String nativeID) {
		this.nativeID = nativeID;
	}

	/**
	 * 휴대기기 계정을 리턴한다.
	 * 
	 * @return deviceAccount - 휴대기기 계정
	 */
	public String getDeviceAccount() {
		return this.deviceAccount;
	}

	/**
	 * 휴대기기 계정을 설정한다.
	 * 
	 * @param deviceAccount
	 *            휴대기기 계정
	 */
	public void setDeviceAccount(String deviceAccount) {
		this.deviceAccount = deviceAccount;
	}

	/**
	 * 가입 테넌트의 시스템 ID를 리턴한다.
	 * 
	 * @return joinId - 가입 테넌트의 시스템 ID
	 */
	public String getJoinId() {
		return this.joinId;
	}

	/**
	 * 가입 테넌트의 시스템 ID를 설정한다.
	 * 
	 * @param joinId
	 *            가입 테넌트의 시스템 ID
	 */
	public void setJoinId(String joinId) {
		this.joinId = joinId;
	}

	/**
	 * Returns the serial version UID.
	 * 
	 * @return serialVersionUID - the serial version UID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	 * 사용자 Key를 리턴한다.
	 * 
	 * @return userKey - 사용자 Key
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * 사용자 Key를 리턴한다.
	 * 
	 * @param userKey
	 *            사용자 Key
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
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
	 * <pre>
	 * 휴면계정유무를 리턴한다.
	 * </pre>
	 * 
	 * @return isDormant - 휴면계정유무
	 */
	public String getIsDormant() {
		return this.isDormant;
	}

	/**
	 * <pre>
	 * 휴면계정유무를 설정한다.
	 * </pre>
	 * 
	 * @param isDormant
	 *            휴면계정유무
	 */
	public void setIsDormant(String isDormant) {
		this.isDormant = isDormant;
	}

	/**
	 * <pre>
	 * 휴대기기 MDN을 리턴한다.
	 * </pre>
	 * 
	 * @return
	 */
	public String getMdn() {
		return this.mdn;
	}

	/**
	 * <pre>
	 * 휴대기기 MDN을 설정한다.
	 * </pre>
	 * 
	 * @param mdn
	 */
	public void setMdn(String mdn) {
		this.mdn = mdn;
	}

	/**
	 * <pre>
	 * 휴대기기 usim번호를 리턴한다.
	 * </pre>
	 *
	 * @return deviceSimNm
	 */
	public String getDeviceSimNm() {
		return deviceSimNm;
	}

	/**
	 * <pre>
	 * 휴대기기 usim번호를 설정한다.
	 * </pre>
	 *
	 * @param deviceSimNm
	 */
	public void setDeviceSimNm(String deviceSimNm) {
		this.deviceSimNm = deviceSimNm;
	}

	/**
	 * <pre>
	 * 휴대기기 마지막 로그인일자를 리턴한다.
	 * </pre>
	 *
	 * @return lastloginDt
	 */
	public String getLastloginDt() {
		return lastloginDt;
	}

	/**
	 * <pre>
	 * 휴대기기 마지막 로그인 일자를 설정한다.
	 * </pre>
	 *
	 * @param lastloginDt
	 */
	public void setLastloginDt(String lastloginDt) {
		this.lastloginDt = lastloginDt;
	}

	/**
	 * <pre>
	 * 휴대기기 보유자 ID를 리턴한다..
	 * </pre>
	 *
	 * @return userID
	 */
	public String getUserID() {
		return userID;
	}

	/**
	 * <pre>
	 * 휴대기기 보유자 ID를 설정한다.
	 * </pre>
	 *
	 * @param userID
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}
}
