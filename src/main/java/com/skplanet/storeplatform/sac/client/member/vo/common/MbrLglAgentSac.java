/**
 * 
 */
package com.skplanet.storeplatform.sac.client.member.vo.common;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Calss 설명
 * 
 * Updated on : 2014. 3. 24. Updated by : Rejoice, Burkhan
 */
public class MbrLglAgentSac extends CommonInfo {

	private static final long serialVersionUID = 1L;
	/** 법정대리인 동의여부 (Y/N) . */
	private String isParent;
	/** 법정대리인 생년월일 . */
	private String parentBirthDay;
	/** 법정대리인 CI . */
	private String parentCI;
	/** 동의일시 . */
	private String parentDate;
	/** 법정대리인 Email . */
	private String parentEmail;
	/** 법정대리인 전화번호 . */
	private String parentMDN;
	/** 법정대리인 이름 . */
	private String parentName;
	/** 인증일시 . */
	private String parentRealNameDate;
	/** 법정대리인 인증방법코드 . */
	private String parentRealNameMethod;
	/** 법정대리인 실명인증사이트 코드 . */
	private String parentRealNameSite;
	/** 법정대리인 통신사 코드 . */
	private String parentTelecom;
	/** 법정대리인 관계 . */
	private String parentType;

	/**
	 * @return the isParent
	 */
	public String getIsParent() {
		return this.isParent;
	}

	/**
	 * @param isParent
	 *            the isParent to set
	 */
	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}

	/**
	 * @return the parentBirthDay
	 */
	public String getParentBirthDay() {
		return this.parentBirthDay;
	}

	/**
	 * @param parentBirthDay
	 *            the parentBirthDay to set
	 */
	public void setParentBirthDay(String parentBirthDay) {
		this.parentBirthDay = parentBirthDay;
	}

	/**
	 * @return the parentCI
	 */
	public String getParentCI() {
		return this.parentCI;
	}

	/**
	 * @param parentCI
	 *            the parentCI to set
	 */
	public void setParentCI(String parentCI) {
		this.parentCI = parentCI;
	}

	/**
	 * @return the parentDate
	 */
	public String getParentDate() {
		return this.parentDate;
	}

	/**
	 * @param parentDate
	 *            the parentDate to set
	 */
	public void setParentDate(String parentDate) {
		this.parentDate = parentDate;
	}

	/**
	 * @return the parentEmail
	 */
	public String getParentEmail() {
		return this.parentEmail;
	}

	/**
	 * @param parentEmail
	 *            the parentEmail to set
	 */
	public void setParentEmail(String parentEmail) {
		this.parentEmail = parentEmail;
	}

	/**
	 * @return the parentMDN
	 */
	public String getParentMDN() {
		return this.parentMDN;
	}

	/**
	 * @param parentMDN
	 *            the parentMDN to set
	 */
	public void setParentMDN(String parentMDN) {
		this.parentMDN = parentMDN;
	}

	/**
	 * @return the parentName
	 */
	public String getParentName() {
		return this.parentName;
	}

	/**
	 * @param parentName
	 *            the parentName to set
	 */
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	/**
	 * @return the parentRealNameDate
	 */
	public String getParentRealNameDate() {
		return this.parentRealNameDate;
	}

	/**
	 * @param parentRealNameDate
	 *            the parentRealNameDate to set
	 */
	public void setParentRealNameDate(String parentRealNameDate) {
		this.parentRealNameDate = parentRealNameDate;
	}

	/**
	 * @return the parentRealNameMethod
	 */
	public String getParentRealNameMethod() {
		return this.parentRealNameMethod;
	}

	/**
	 * @param parentRealNameMethod
	 *            the parentRealNameMethod to set
	 */
	public void setParentRealNameMethod(String parentRealNameMethod) {
		this.parentRealNameMethod = parentRealNameMethod;
	}

	/**
	 * @return the parentRealNameSite
	 */
	public String getParentRealNameSite() {
		return this.parentRealNameSite;
	}

	/**
	 * @param parentRealNameSite
	 *            the parentRealNameSite to set
	 */
	public void setParentRealNameSite(String parentRealNameSite) {
		this.parentRealNameSite = parentRealNameSite;
	}

	/**
	 * @return the parentTelecom
	 */
	public String getParentTelecom() {
		return this.parentTelecom;
	}

	/**
	 * @param parentTelecom
	 *            the parentTelecom to set
	 */
	public void setParentTelecom(String parentTelecom) {
		this.parentTelecom = parentTelecom;
	}

	/**
	 * @return the parentType
	 */
	public String getParentType() {
		return this.parentType;
	}

	/**
	 * @param parentType
	 *            the parentType to set
	 */
	public void setParentType(String parentType) {
		this.parentType = parentType;
	}

}
