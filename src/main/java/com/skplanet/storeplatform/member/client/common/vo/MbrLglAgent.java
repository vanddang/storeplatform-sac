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
 * 법정 대리인 Value Object
 * 
 * Updated on : 2013. 12. 10. Updated by : wisestone_jloveonly
 */
public class MbrLglAgent extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** Sequence number. */
	private String sequence; // AUTH_SEQ

	/** 법정대리인 동의여부 (Y/N). */
	private String isParent; // 법정대리인 동의여부(Y/N)

	/** 법정대리인 인증방법코드. */
	private String parentRealNameMethod; // LGL_AGENT_AUTH_MTD_CD 법정대리인 인증방법코드, API : realNameMethod

	/** 법정대리인 이름. */
	private String parentName; // LGL_AGENT_FLNM 법정대리인 이름, API : userName

	/** 법정대리인 관계. */
	private String parentType; // LGL_AGENT_RSHP 법정대리인 관계, API : parentType

	/** 동의일시.ex (YYYYMMDDHH24MISS) ("20130122162853") 2013-01-22 16:28:53. */
	private String parentDate; // LGL_AGENT_AGREE_DT 동의 일시, API : realNameDate

	/** 법정대리인 Email. */
	private String parentEmail; // LGL_AGENT_EMAIL, API : parentEmail

	/** 법정대리인 생년월일. */
	private String parentBirthDay; // LGL_AGENT_BIRTH, API : userBirthDay

	/** 법정대리인 통신사 코드. */
	private String parentTelecom; // MNO_CD, API : userTelecom

	/** 법정대리인 전화번호. */
	private String parentMDN; // LGL_AGENT_HP_NO, API : userPhone

	/** 법정대리인 CI. */
	private String parentCI; // CI, API : userCI

	/** 인증일시. */
	private String parentRealNameDate; // REG_DT, API : 없음, 시스템 날짜

	/** 법정대리인 실명인증사이트 코드. */
	private String parentRealNameSite; // SYSTEM_ID 법정대리인 실명인증사이트 코드, API : realNameSite

	/** 내부 회원 키. */
	private String memberKey; // INSD_SELLERMBR_NO 내부 사용자코드

	/** 테넌트 ID. */
	private String tenantID;

	/** 내국인 여부. */
	private String isDomestic; // 내국인인지여부

	/**
	 * 내국인 여부를 리턴한다.
	 * 
	 * @return isDomestic - 내국인 여부
	 */
	public String getIsDomestic() {
		return this.isDomestic;
	}

	/**
	 * 내국인 여부를 설정한다.
	 * 
	 * @param isDomestic
	 *            내국인 여부
	 */
	public void setIsDomestic(String isDomestic) {
		this.isDomestic = isDomestic;
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
	 * 법정대리인 동의여부 (Y/N)를 리턴한다.
	 * 
	 * @return isParent - 법정대리인 동의여부 (Y/N)
	 */
	public String getIsParent() {
		return this.isParent;
	}

	/**
	 * 법정대리인 동의여부 (Y/N)를 설정한다.
	 * 
	 * @param isParent
	 *            법정대리인 동의여부 (Y/N)
	 */
	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}

	/**
	 * Sequence number를 리턴한다.
	 * 
	 * @return sequence - Sequence number
	 */
	public String getSequence() {
		return this.sequence;
	}

	/**
	 * Sequence number를 설정한다.
	 * 
	 * @param sequence
	 *            Sequence number
	 */
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	/**
	 * 법정대리인 인증방법코드를 리턴한다.
	 * 
	 * @return parentRealNameMethod - 법정대리인 인증방법코드
	 */
	public String getParentRealNameMethod() {
		return this.parentRealNameMethod;
	}

	/**
	 * 법정대리인 인증방법코드를 설정한다.
	 * 
	 * @param parentRealNameMethod
	 *            법정대리인 인증방법코드
	 */
	public void setParentRealNameMethod(String parentRealNameMethod) {
		this.parentRealNameMethod = parentRealNameMethod;
	}

	/**
	 * 법정대리인 이름을 리턴한다.
	 * 
	 * @return parentName - 법정대리인 이름
	 */
	public String getParentName() {
		return this.parentName;
	}

	/**
	 * 법정대리인 이름을 설정한다.
	 * 
	 * @param parentName
	 *            법정대리인 이름
	 */
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	/**
	 * 법정대리인 관계를 리턴한다.
	 * 
	 * @return parentType - 법정대리인 관계
	 */
	public String getParentType() {
		return this.parentType;
	}

	/**
	 * 법정대리인 관계를 설정한다.
	 * 
	 * @param parentType
	 *            법정대리인 관계
	 */
	public void setParentType(String parentType) {
		this.parentType = parentType;
	}

	/**
	 * 동의일시를 리턴한다.
	 * 
	 * @return parentDate - 동의일시
	 */
	public String getParentDate() {
		return this.parentDate;
	}

	/**
	 * 동의일시를 설정한다.
	 * 
	 * @param parentDate
	 *            동의일시
	 */
	public void setParentDate(String parentDate) {
		this.parentDate = parentDate;
	}

	/**
	 * 법정대리인 Email을 리턴한다.
	 * 
	 * @return parentEmail - 법정대리인 Email
	 */
	public String getParentEmail() {
		return this.parentEmail;
	}

	/**
	 * 법정대리인 Email을 설정한다.
	 * 
	 * @param parentEmail
	 *            법정대리인 Email
	 */
	public void setParentEmail(String parentEmail) {
		this.parentEmail = parentEmail;
	}

	/**
	 * 법정대리인 생년월일을 리턴한다.
	 * 
	 * @return parentBirthDay - 법정대리인 생년월일
	 */
	public String getParentBirthDay() {
		return this.parentBirthDay;
	}

	/**
	 * 법정대리인 생년월일을 설정한다.
	 * 
	 * @param parentBirthDay
	 *            법정대리인 생년월일
	 */
	public void setParentBirthDay(String parentBirthDay) {
		this.parentBirthDay = parentBirthDay;
	}

	/**
	 * 법정대리인 통신사 코드를 리턴한다.
	 * 
	 * @return parentTelecom - 법정대리인 통신사 코드
	 */
	public String getParentTelecom() {
		return this.parentTelecom;
	}

	/**
	 * 법정대리인 통신사 코드를 설정한다.
	 * 
	 * @param parentTelecom
	 *            법정대리인 통신사 코드
	 */
	public void setParentTelecom(String parentTelecom) {
		this.parentTelecom = parentTelecom;
	}

	/**
	 * 법정대리인 전화번호를 리턴한다.
	 * 
	 * @return parentMDN - 법정대리인 전화번호
	 */
	public String getParentMDN() {
		return this.parentMDN;
	}

	/**
	 * 법정대리인 전화번호를 설정한다.
	 * 
	 * @param parentMDN
	 *            법정대리인 전화번호
	 */
	public void setParentMDN(String parentMDN) {
		this.parentMDN = parentMDN;
	}

	/**
	 * 법정대리인 CI를 리턴한다.
	 * 
	 * @return parentCI - 법정대리인 CI
	 */
	public String getParentCI() {
		return this.parentCI;
	}

	/**
	 * 법정대리인 CI를 설정한다.
	 * 
	 * @param parentCI
	 *            법정대리인 CI
	 */
	public void setParentCI(String parentCI) {
		this.parentCI = parentCI;
	}

	/**
	 * 인증일시를 리턴한다.
	 * 
	 * @return parentRealNameDate - 인증일시
	 */
	public String getParentRealNameDate() {
		return this.parentRealNameDate;
	}

	/**
	 * 인증일시를 설정한다.
	 * 
	 * @param parentRealNameDate
	 *            인증일시
	 */
	public void setParentRealNameDate(String parentRealNameDate) {
		this.parentRealNameDate = parentRealNameDate;
	}

	/**
	 * 법정대리인 실명인증사이트 코드를 리턴한다.
	 * 
	 * @return parentRealNameSite - 법정대리인 실명인증사이트 코드
	 */
	public String getParentRealNameSite() {
		return this.parentRealNameSite;
	}

	/**
	 * 법정대리인 실명인증사이트 코드를 설정한다.
	 * 
	 * @param parentRealNameSite
	 *            법정대리인 실명인증사이트 코드
	 */
	public void setParentRealNameSite(String parentRealNameSite) {
		this.parentRealNameSite = parentRealNameSite;
	}

	/**
	 * 내부 회원 키를 리턴한다.
	 * 
	 * @return memberKey - 내부 회원 키
	 */
	public String getMemberKey() {
		return this.memberKey;
	}

	/**
	 * 내부 회원 키를 설정한다.
	 * 
	 * @param memberKey
	 *            내부 회원 키
	 */
	public void setMemberKey(String memberKey) {
		this.memberKey = memberKey;
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
