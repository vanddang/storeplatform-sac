package com.skplanet.storeplatform.sac.client.member.vo.common;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 법정대리인 Value
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class MbrLglAgent extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String sequence; // AUTH_SEQ

	/** The 법정대리인 동의여부. */
	private String isParent; // 법정대리인 동의여부(Y/N)

	/** The 법정대리인 인증방법코드. */
	private String parentRealNameMethod; // LGL_AGENT_AUTH_MTD_CD 법정대리인 인증방법코드, API : realNameMethod

	/** The 법정대리인 이름. */
	private String parentName; // LGL_AGENT_FLNM 법정대리인 이름, API : userName

	/** The 법정대리인 관계. */
	private String parentType; // LGL_AGENT_RSHP 법정대리인 관계, API : parentType

	/** The 동의 일시. */
	private String parentDate; // LGL_AGENT_AGREE_DT 동의 일시, API : realNameDate

	/** The 법정대리인 email. */
	private String parentEmail; // LGL_AGENT_EMAIL, API : parentEmail

	/** The 법정대리인 생년월일. */
	private String parentBirthDay; // LGL_AGENT_BIRTH, API : userBirthDay

	/** The 법정대리인 통신사 코드. */
	private String parentTelecom; // MNO_CD, API : userTelecom

	/** The 법정대리인 전화번호. */
	private String parentMsisdn; // LGL_AGENT_HP_NO, API : userPhone

	/** The 법정대리인 ci. */
	private String parentCI; // CI, API : userCI

	/** The 인증 일시. */
	private String parentRealNameDate; // REG_DT, API : 없음, 시스템 날짜

	/** The 법정대리인 실명인증사이트 코드. */
	private String parentRealNameSite; // AUTH_REQ_CHNL_CD 법정대리인 실명인증사이트 코드, API : realNameSite

	/** The 내부 회원 키. */
	private String memberKey; // INSD_SELLERMBR_NO 내부 사용자코드

	/** The 내국인 여부 */
	private String isDomestic;

	/** The 테넌트 ID */
	private String tenantId;

	/**
	 * @return the sequence
	 */
	public String getSequence() {
		return this.sequence;
	}

	/**
	 * @param sequence
	 *            the sequence to set
	 */
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

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
	 * @return the parentMsisdn
	 */
	public String getParentMsisdn() {
		return this.parentMsisdn;
	}

	/**
	 * @param parentMsisdn
	 *            the parentMsisdn to set
	 */
	public void setParentMsisdn(String parentMsisdn) {
		this.parentMsisdn = parentMsisdn;
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
	 * @return the memberKey
	 */
	public String getMemberKey() {
		return this.memberKey;
	}

	/**
	 * @param memberKey
	 *            the memberKey to set
	 */
	public void setMemberKey(String memberKey) {
		this.memberKey = memberKey;
	}

	/**
	 * @return the isDomestic
	 */
	public String getIsDomestic() {
		return this.isDomestic;
	}

	/**
	 * @param isDomestic
	 *            the isDomestic to set
	 */
	public void setIsDomestic(String isDomestic) {
		this.isDomestic = isDomestic;
	}

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

}
