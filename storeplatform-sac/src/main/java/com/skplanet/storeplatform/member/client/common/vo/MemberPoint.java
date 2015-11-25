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

// TODO: Auto-generated Javadoc
/**
 * OKCashBag Value Object
 * 
 * Updated on : 2013. 12. 20. Updated by : wisestone_mikepark
 */
public class MemberPoint extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** Tenant ID. */
	private String tenantID; // TENANT_ID

	/** 내부 회원 키. */
	private String userKey; // INSD_USERMBR_NO 내부 사용자코드

	/** 인증방법 코드. */
	private String authMethodCode; // OCB_AUTH_MTD_CD

	/** 사용여부 (Y/N). */
	private String isUsed; // USE_YN

	/** 카드 번호. */
	private String cardNumber; // OCB_NO

	/** 사용시작 일시. */
	private String startDate; // USE_START_DT

	/** 사용종료 일시. */
	private String endDate; // USE_END_DT

	/** 등록 ID. */
	private String regID; // REG_ID

	/** 등록일시. */
	private String regDate; // REG_DT

	/** 수정 ID. */
	private String updateID; // UPD_ID

	/** 수정일시. */
	private String updateDate; // UPD_DT

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
	 * 인증방법 코드를 리턴한다.
	 * 
	 * @return authMethodCode - 인증방법 코드
	 */
	public String getAuthMethodCode() {
		return this.authMethodCode;
	}

	/**
	 * 인증방법 코드를 설정한다.
	 * 
	 * @param authMethodCode
	 *            인증방법 코드
	 */
	public void setAuthMethodCode(String authMethodCode) {
		this.authMethodCode = authMethodCode;
	}

	/**
	 * OCB 사용여부 (Y/N)를 리턴한다.
	 * 
	 * @return isUsed - OCB 사용여부 (Y/N)
	 */
	public String getIsUsed() {
		return this.isUsed;
	}

	/**
	 * OCB 사용여부 (Y/N)를 설정한다.
	 * 
	 * @param isUsed
	 *            OCB 사용여부 (Y/N)
	 */
	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}

	/**
	 * OCB 카드 번호를 리턴한다.
	 * 
	 * @return cardNumber - OCB 카드 번호
	 */
	public String getCardNumber() {
		return this.cardNumber;
	}

	/**
	 * OCB 카드 번호를 설정한다.
	 * 
	 * @param cardNumber
	 *            OCB 카드 번호
	 */
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	/**
	 * 사용시작 일시를 리턴한다.
	 * 
	 * @return startDate - 사용시작 일시
	 */
	public String getStartDate() {
		return this.startDate;
	}

	/**
	 * 사용시작 일시를 설정한다.
	 * 
	 * @param startDate
	 *            사용시작 일시
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * 사용종료 일시를 리턴한다.
	 * 
	 * @return endDate - 사용종료 일시
	 */
	public String getEndDate() {
		return this.endDate;
	}

	/**
	 * 사용종료 일시를 설정한다.
	 * 
	 * @param endDate
	 *            사용종료 일시
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * 등록 ID를 리턴한다.
	 * 
	 * @return regID - 등록 ID
	 */
	public String getRegID() {
		return this.regID;
	}

	/**
	 * 등록 ID를 설정한다.
	 * 
	 * @param regID
	 *            등록 ID
	 */
	public void setRegID(String regID) {
		this.regID = regID;
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
	 * 수정 ID를 리턴한다.
	 * 
	 * @return updateID - 수정 ID
	 */
	public String getUpdateID() {
		return this.updateID;
	}

	/**
	 * 수정 ID를 설정한다.
	 * 
	 * @param updateID
	 *            수정 ID
	 */
	public void setUpdateID(String updateID) {
		this.updateID = updateID;
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
