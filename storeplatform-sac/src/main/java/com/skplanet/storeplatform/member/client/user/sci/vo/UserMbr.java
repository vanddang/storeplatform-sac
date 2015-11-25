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
 * 사용자 기본정보 Value Object
 * 
 * Updated on : 2013. 12. 10. Updated by : wisestone_mikepark
 */
public class UserMbr extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 테넌트 ID. */
	private String tenantID;

	/** 테넌트의 시스템 ID. */
	private String systemID;

	/** 사용자 Key. */
	private String userKey;

	/** 외부(IDP)에서 할당된 사용자 Key. */
	private String imMbrNo; // IDP 통합서비스 키 USERMBR_NO

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

	/** 로그인 상태 코드. */
	private String loginStatusCode; // LOGIN_STATUS_CD

	/** 직권중지 상태 코드. */
	private String stopStatusCode; // OFAUTH_STOP_STATUS_CD

	/** 사용자 휴대기기 등록 대수. */
	private String deviceCount;

	/** 통합 서비스 관리번호. */
	private String imSvcNo; // INTG_SVC_NO : 통합서비스 관리번호

	/**
	 * 전환가입코드.
	 * 
	 * - 전환가입 : Y, 신규가입 : N, 변경가입 : C, 변경전환 : H
	 */
	private String isImChanged;

	/** OneID 전환 가입일. */
	private String imRegDate;

	/** 사용자 ID. */
	private String userID;

	/** 통신사 코드. */
	private String userTelecom;

	/** 연락처 국가 코드. */
	private String userPhoneCountry;

	/** 사용자 연락처. */
	private String userPhone;

	/** SMS 수신여부. */
	private String isRecvSMS;

	/** 사용자 이메일 주소. */
	private String userEmail;

	/** 이메일 수신 여부. */
	private String isRecvEmail;

	/** 사용자 이름. */
	private String userName;

	/** 사용자 성별. */
	private String userSex;

	/** 사용자 생년월일. */
	private String userBirthDay;

	/** 사용자 국가 코드. */
	private String userCountry;

	/** 사용자 언어 코드. */
	private String userLanguage;

	/** 등록일시. */
	private String regDate; // ENTRY_DAY

	/** OneID 이용동의 사이트 정보. */
	private String imSiteCode;

	/** 탈퇴일자. */
	private String secedeDate; // BOLTER_DAY 탈퇴일

	/** 탈퇴 유형 코드(탈퇴 GROUP CODE : US0107). */
	private String secedeTypeCode; // BOLTER_CASE_CD 탈퇴유형코드

	/** 탈퇴 사유 코드(탈퇴 GROUP CODE : US0104). */
	private String secedeReasonCode; // BOLTER_REASON_CD 탈퇴사유코드

	/** 탈퇴사유 설명. */
	private String secedeReasonMessage; // BOLTER_REASON_DESC 탈퇴사유설명

	/** 실명인증 여부. */
	private String isRealName;

	/** 법정대리인 동의여부. */
	private String isParent; // 법정대리인 동의여부(Y/N)

	/** 통합포인트 사용 여부. */
	private String isMemberPoint; // INTG_PONT_YN

	/** 원아이디 가입상태코드. */
	private String entryStatusCode;

	/** 사용자 업데이트 이메일. */
	private String userUpdEmail;

	/** 휴면계정유무. */
	private String isDormant;

	/**
	 * 통합포인트 사용여부(Y/N)를 리턴한다.
	 * 
	 * @return isMemberPoint - 통합포인트 사용여부(Y/N)
	 */
	public String getIsMemberPoint() {
		return this.isMemberPoint;
	}

	/**
	 * 통합포인트 사용여부(Y/N)를 설정한다.
	 * 
	 * @param isMemberPoint
	 *            통합포인트 사용여부(Y/N)
	 */
	public void setIsMemberPoint(String isMemberPoint) {
		this.isMemberPoint = isMemberPoint;
	}

	/**
	 * 법정대리인 동의여부(Y/N)를 리턴한다.
	 * 
	 * @return isParent - 법정대리인 동의여부(Y/N)
	 */
	public String getIsParent() {
		return this.isParent;
	}

	/**
	 * 법정대리인 동의여부(Y/N)를 설정한다.
	 * 
	 * @param isParent
	 *            법정대리인 동의여부(Y/N)
	 */
	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}

	/**
	 * 실명인증여부(Y/N)를 리턴한다.
	 * 
	 * @return isRealName - 실명인증여부(Y/N)
	 */
	public String getIsRealName() {
		return this.isRealName;
	}

	/**
	 * 실명인증여부(Y/N)를 설정한다.
	 * 
	 * @param isRealName
	 *            실명인증여부(Y/N)
	 */
	public void setIsRealName(String isRealName) {
		this.isRealName = isRealName;
	}

	/**
	 * 사용자 등록일자를 리턴한다.
	 * 
	 * @return regDate - 등록일자
	 */
	public String getRegDate() {
		return this.regDate;
	}

	/**
	 * 사용자 등록일자를 설정한다.
	 * 
	 * @param regDate
	 *            등록일자
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
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
	 * 로그인 상태 코드를 리턴한다.
	 * 
	 * @return loginStatusCode - 로그인 상태 코드
	 */
	public String getLoginStatusCode() {
		return this.loginStatusCode;
	}

	/**
	 * 로그인 상태 코드를 설정한다.
	 * 
	 * @param loginStatusCode
	 *            로그인 상태 코드
	 */
	public void setLoginStatusCode(String loginStatusCode) {
		this.loginStatusCode = loginStatusCode;
	}

	/**
	 * 직권중지 상태 코드를 리턴한다.
	 * 
	 * @return stopStatusCode - 직권중지 상태 코드
	 */
	public String getStopStatusCode() {
		return this.stopStatusCode;
	}

	/**
	 * 직권중지 상태 코드를 설정한다.
	 * 
	 * @param stopStatusCode
	 *            직권중지 상태 코드
	 */
	public void setStopStatusCode(String stopStatusCode) {
		this.stopStatusCode = stopStatusCode;
	}

	/**
	 * 통합서비스 관리번호를 리턴한다.
	 * 
	 * @return imSvcNo - 통합서비스 관리번호
	 */
	public String getImSvcNo() {
		return this.imSvcNo;
	}

	/**
	 * 통합서비스 관리번호를 설정한다.
	 * 
	 * @param imSvcNo
	 *            통합서비스 관리번호
	 */
	public void setImSvcNo(String imSvcNo) {
		this.imSvcNo = imSvcNo;
	}

	/**
	 * 전환가입코드를 리턴한다.
	 * 
	 * - 전환가입 : Y, 신규가입 : N, 변경가입 : C, 변경전환 : H
	 * 
	 * @return isImChanged - 전환가입코드
	 */
	public String getIsImChanged() {
		return this.isImChanged;
	}

	/**
	 * 전환가입코드를 설정한다.
	 * 
	 * - 전환가입 : Y, 신규가입 : N, 변경가입 : C, 변경전환 : H
	 * 
	 * @param isImChanged
	 *            전환가입코드
	 */
	public void setIsImChanged(String isImChanged) {
		this.isImChanged = isImChanged;
	}

	/**
	 * OneID 전환 가입일을 리턴한다.
	 * 
	 * @return imRegDate - OneID 전환 가입일
	 */
	public String getImRegDate() {
		return this.imRegDate;
	}

	/**
	 * OneID 전환 가입일을 설정한다.
	 * 
	 * @param imRegDate
	 *            OneID 전환 가입일
	 */
	public void setImRegDate(String imRegDate) {
		this.imRegDate = imRegDate;
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
	 * 통신사 코드를 리턴한다.
	 * 
	 * @return userTelecom - 통신사 코드
	 */
	public String getUserTelecom() {
		return this.userTelecom;
	}

	/**
	 * 통신사 코드를 설정한다.
	 * 
	 * @param userTelecom
	 *            통신사 코드
	 */
	public void setUserTelecom(String userTelecom) {
		this.userTelecom = userTelecom;
	}

	/**
	 * 사용자 연락처 국가코드를 리턴한다.
	 * 
	 * @return userPhoneCountry - 사용자 연락처 국가코드
	 */
	public String getUserPhoneCountry() {
		return this.userPhoneCountry;
	}

	/**
	 * 사용자 연락처 국가코드를 설정한다.
	 * 
	 * @param userPhoneCountry
	 *            사용자 연락처 국가코드
	 */
	public void setUserPhoneCountry(String userPhoneCountry) {
		this.userPhoneCountry = userPhoneCountry;
	}

	/**
	 * 사용자 휴대폰 연락처를 리턴한다.
	 * 
	 * @return userPhone - 사용자 휴대폰 연락처
	 */
	public String getUserPhone() {
		return this.userPhone;
	}

	/**
	 * 사용자 휴대폰 연락처를 설정한다.
	 * 
	 * @param userPhone
	 *            사용자 휴대폰 연락처
	 */
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	/**
	 * SMS 수신여부를 리턴한다.
	 * 
	 * @return isRecvSMS - SMS 수신여부
	 */
	public String getIsRecvSMS() {
		return this.isRecvSMS;
	}

	/**
	 * SMS 수신여부를 설정한다.
	 * 
	 * @param isRecvSMS
	 *            SMS 수신여부
	 */
	public void setIsRecvSMS(String isRecvSMS) {
		this.isRecvSMS = isRecvSMS;
	}

	/**
	 * 사용자 이메일 주소를 리턴한다.
	 * 
	 * @return userEmail - 사용자 이메일 주소
	 */
	public String getUserEmail() {
		return this.userEmail;
	}

	/**
	 * 사용자 이메일 주소를 설정한다.
	 * 
	 * @param userEmail
	 *            사용자 이메일 주소
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	/**
	 * 이메일 수신여부를 리턴한다.
	 * 
	 * @return isRecvEmail - 이메일 수신여부
	 */
	public String getIsRecvEmail() {
		return this.isRecvEmail;
	}

	/**
	 * 이메일 수신여부를 설정한다.
	 * 
	 * @param isRecvEmail
	 *            이메일 수신여부
	 */
	public void setIsRecvEmail(String isRecvEmail) {
		this.isRecvEmail = isRecvEmail;
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
	 * 사용자 성별을 리턴한다.
	 * 
	 * @return userSex - 사용자 성
	 */
	public String getUserSex() {
		return this.userSex;
	}

	/**
	 * 사용자 성별을 설정한다.
	 * 
	 * @param userSex
	 *            사용자 성
	 */
	public void setUserSex(String userSex) {
		this.userSex = userSex;
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
	 * 사용자 국가 코드를 리턴한다.
	 * 
	 * @return userCountry - 사용자 국가 코드
	 */
	public String getUserCountry() {
		return this.userCountry;
	}

	/**
	 * 사용자 국가 코드를 설정한다.
	 * 
	 * @param userCountry
	 *            사용자 국가 코드
	 */
	public void setUserCountry(String userCountry) {
		this.userCountry = userCountry;
	}

	/**
	 * 사용자 언어 코드를 리턴한다.
	 * 
	 * @return userLanguage - 사용자 언어 코드
	 */
	public String getUserLanguage() {
		return this.userLanguage;
	}

	/**
	 * 사용자 언어 코드를 설정한다.
	 * 
	 * @param userLanguage
	 *            사용자 언어 코드
	 */
	public void setUserLanguage(String userLanguage) {
		this.userLanguage = userLanguage;
	}

	/**
	 * OneID 이용동의 사이트 정보를 리턴한다.
	 * 
	 * @return imSiteCode - OneID 이용동의 사이트 정보
	 */
	public String getImSiteCode() {
		return this.imSiteCode;
	}

	/**
	 * OneID 이용동의 사이트 정보를 설정한다.
	 * 
	 * @param imSiteCode
	 *            OneID 이용동의 사이트 정보
	 */
	public void setImSiteCode(String imSiteCode) {
		this.imSiteCode = imSiteCode;
	}

	/**
	 * 탈퇴일자를 리턴한다.
	 * 
	 * @return secedeDate - 탈퇴일자
	 */
	public String getSecedeDate() {
		return this.secedeDate;
	}

	/**
	 * 탈퇴일자를 설정한다.
	 * 
	 * @param secedeDate
	 *            탈퇴일자
	 */
	public void setSecedeDate(String secedeDate) {
		this.secedeDate = secedeDate;
	}

	/**
	 * 탈퇴유형코드를 리턴한다.
	 * 
	 * @return secedeTypeCode - 탈퇴유형코드
	 */
	public String getSecedeTypeCode() {
		return this.secedeTypeCode;
	}

	/**
	 * 탈퇴유형코드를 설정한다.
	 * 
	 * @param secedeTypeCode
	 *            탈퇴유형코드
	 */
	public void setSecedeTypeCode(String secedeTypeCode) {
		this.secedeTypeCode = secedeTypeCode;
	}

	/**
	 * 탈퇴사유코드를 리턴한다.
	 * 
	 * @return secedeReasonCode - 탈퇴사유코드
	 */
	public String getSecedeReasonCode() {
		return this.secedeReasonCode;
	}

	/**
	 * 탈퇴사유코드를 설정한다.
	 * 
	 * @param secedeReasonCode
	 *            탈퇴사유코드
	 */
	public void setSecedeReasonCode(String secedeReasonCode) {
		this.secedeReasonCode = secedeReasonCode;
	}

	/**
	 * 탈퇴사유 상세정보를 리턴한다.
	 * 
	 * @return secedeReasonMessage - 탈퇴사유 상세정보
	 */
	public String getSecedeReasonMessage() {
		return this.secedeReasonMessage;
	}

	/**
	 * 탈퇴사유 상세정보를 설정한다.
	 * 
	 * @param secedeReasonMessage
	 *            탈퇴사유 상세정보
	 */
	public void setSecedeReasonMessage(String secedeReasonMessage) {
		this.secedeReasonMessage = secedeReasonMessage;
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
	 * 테넌트의 시스템 ID를 리턴한다.
	 * 
	 * @return systemID - 시스템 ID
	 */
	public String getSystemID() {
		return this.systemID;
	}

	/**
	 * 테넌트의 시스템 ID를 설정한다.
	 * 
	 * @param systemID
	 *            시스템 ID
	 */
	public void setSystemID(String systemID) {
		this.systemID = systemID;
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
	 * 등록된 휴대기기 갯수를 리턴한다.
	 * 
	 * @return deviceCount - 등록된 휴대기기 갯수
	 */
	public String getDeviceCount() {
		return this.deviceCount;
	}

	/**
	 * 등록된 휴대기기 갯수를 수정한다.
	 * 
	 * @param deviceCount
	 *            등록된 휴대기기 갯수
	 */
	public void setDeviceCount(String deviceCount) {
		this.deviceCount = deviceCount;
	}

	/**
	 * @return entryStatusCode
	 */
	public String getEntryStatusCode() {
		return this.entryStatusCode;
	}

	/**
	 * @param entryStatusCode
	 *            String
	 */
	public void setEntryStatusCode(String entryStatusCode) {
		this.entryStatusCode = entryStatusCode;
	}

	/**
	 * @return the userUpdEmail
	 */
	public String getUserUpdEmail() {
		return this.userUpdEmail;
	}

	/**
	 * @param userUpdEmail
	 *            the userUpdEmail to set
	 */
	public void setUserUpdEmail(String userUpdEmail) {
		this.userUpdEmail = userUpdEmail;
	}

	/**
	 * @return isDormant
	 */
	public String getIsDormant() {
		return this.isDormant;
	}

	/**
	 * @param isDormant
	 *            String
	 */
	public void setIsDormant(String isDormant) {
		this.isDormant = isDormant;
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
