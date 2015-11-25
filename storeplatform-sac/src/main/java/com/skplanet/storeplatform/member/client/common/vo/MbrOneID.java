/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.client.common.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;

/**
 * 미동의 사용자 관리 Value Object
 * 
 * Updated on : 2013. 12. 20. Updated by : wisestone_mikepark
 */
public class MbrOneID extends CommonInfo implements Serializable {
	// TB_US_USERMBR_ONEID
	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** Tenant ID. */
	private String tenantID; // TENANT_ID

	/** 내부 회원 키. */
	private String userKey; // INSD_USERMBR_NO 내부 사용자코드

	/** 통합 서비스 번호. */
	private String intgSvcNumber; // INTG_SVC_NO

	/** 통합 유형 코드. */
	private String intgMbrCaseCode; // INTG_MBR_CASE_CD

	/** 가입 상태 코드. */
	private String entryStatusCode; // MBR_ENTRY_STATUS_CD

	/** 로그인 상태 코드. */
	private String loginStatusCode; // LOGIN_STATUS_CD

	/** 직권중지 상태 코드. */
	private String stopStatusCode; // OFAUTH_STOP_STATUS_CD

	/** 가입자 유형 코드. */
	private String memberCaseCode; // MBR_CASE_CD

	/** 실명 인증 여부. */
	private String isRealName; // IDCT_YN

	/** 가입 서비스 사이트 코드. */
	private String intgSiteCode; // ENTRY_SVC_SITE_CD

	/** 가입일시. */
	private String entryDate; // ENTRY_DT

	/** 등록일시. */
	private String regDate; // REG_DT

	/** 수정일시. */
	private String updateDate; // UPD_DT

	/** 사용자 ID. */
	private String userID; // MNO_ID

	/** 통합포인트 사용 여부. */
	private String isMemberPoint; // INTG_PONT_YN

	/** CI 존재 여부(Y/N). */
	private String isCi; // CI_YN

	/**
	 * CI 존재 여부(Y/N)를 리턴한다.
	 * 
	 * @return isCi - CI 존재 여부(Y/N)
	 */
	public String getIsCi() {
		return this.isCi;
	}

	/**
	 * CI 존재 여부(Y/N)를 설정한다.
	 * 
	 * @param isCi
	 *            CI 존재 여부(Y/N)
	 */
	public void setIsCi(String isCi) {
		this.isCi = isCi;
	}

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
	 * 통합 서비스 번호를 리턴한다.
	 * 
	 * @return intgSvcNumber - 통합 서비스 번호
	 */
	public String getIntgSvcNumber() {
		return this.intgSvcNumber;
	}

	/**
	 * 통합 서비스 번호를 설정한다.
	 * 
	 * @param intgSvcNumber
	 *            통합 서비스 번호
	 */
	public void setIntgSvcNumber(String intgSvcNumber) {
		this.intgSvcNumber = intgSvcNumber;
	}

	/**
	 * 통합 유형 코드를 리턴한다.
	 * 
	 * @return intgMbrCaseCode - 통합 유형 코드
	 */
	public String getIntgMbrCaseCode() {
		return this.intgMbrCaseCode;
	}

	/**
	 * 통합 유형 코드를 설정한다.
	 * 
	 * @param intgMbrCaseCode
	 *            통합 유형 코드
	 */
	public void setIntgMbrCaseCode(String intgMbrCaseCode) {
		this.intgMbrCaseCode = intgMbrCaseCode;
	}

	/**
	 * 가입 상태 코드를 리턴한다.
	 * 
	 * @return entryStatusCode - 가입 상태 코드
	 */
	public String getEntryStatusCode() {
		return this.entryStatusCode;
	}

	/**
	 * 가입 상태 코드를 설정한다.
	 * 
	 * @param entryStatusCode
	 *            가입 상태 코드
	 */
	public void setEntryStatusCode(String entryStatusCode) {
		this.entryStatusCode = entryStatusCode;
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
	 * 가입자 유형 코드를 리턴한다.
	 * 
	 * @return memberCaseCode - 가입자 유형 코드
	 */
	public String getMemberCaseCode() {
		return this.memberCaseCode;
	}

	/**
	 * 가입자 유형 코드를 설정한다.
	 * 
	 * @param memberCaseCode
	 *            가입자 유형 코드
	 */
	public void setMemberCaseCode(String memberCaseCode) {
		this.memberCaseCode = memberCaseCode;
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
	 * 가입 서비스 사이트 코드를 리턴한다.
	 * 
	 * @return intgSiteCode - 가입 서비스 사이트 코드
	 */
	public String getIntgSiteCode() {
		return this.intgSiteCode;
	}

	/**
	 * 가입 서비스 사이트 코드를 설정한다.
	 * 
	 * @param intgSiteCode
	 *            가입 서비스 사이트 코드
	 */
	public void setIntgSiteCode(String intgSiteCode) {
		this.intgSiteCode = intgSiteCode;
	}

	/**
	 * 가입일시를 리턴한다.
	 * 
	 * @return entryDate - 가입일시
	 */
	public String getEntryDate() {
		return this.entryDate;
	}

	/**
	 * 가입일시를 설정한다.
	 * 
	 * @param entryDate
	 *            가입일시
	 */
	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}

	/**
	 * 등록일시를 리턴한다.
	 * 
	 * @return regDate - 등록일시
	 */
	public String getRegDate() {
		return this.regDate;
	}

	/**
	 * 등록일시를 설정한다.
	 * 
	 * @param regDate
	 *            등록일시
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	/**
	 * 수정일시를 리턴한다.
	 * 
	 * @return updateDate - 수정일시
	 */
	public String getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * 수정일시를 설정한다.
	 * 
	 * @param updateDate
	 *            수정일시
	 */
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * 사용자 ID를 리턴한다.
	 * 
	 * @return userID - 사용자 ID
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
