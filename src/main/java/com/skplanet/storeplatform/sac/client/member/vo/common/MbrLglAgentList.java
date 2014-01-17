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
public class MbrLglAgentList extends CommonInfo {

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
	private String parentMDN; // LGL_AGENT_HP_NO, API : userPhone

	/** The 법정대리인 ci. */
	private String parentCI; // CI, API : userCI

	/** The 인증 일시. */
	private String parentRealNameDate; // REG_DT, API : 없음, 시스템 날짜

	/** The 법정대리인 실명인증사이트 코드. */
	private String parentRealNameSite; // AUTH_REQ_CHNL_CD 법정대리인 실명인증사이트 코드, API : realNameSite

	/** The 내부 회원 키. */
	private String memberKey; // INSD_SELLERMBR_NO 내부 사용자코드

	public String getIsParent() {
		return this.isParent;
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}

	public String getMemberKey() {
		return this.memberKey;
	}

	public void setMemberKey(String memberKey) {
		this.memberKey = memberKey;
	}

	public String getParentBirthDay() {
		return this.parentBirthDay;
	}

	public void setParentBirthDay(String parentBirthDay) {
		this.parentBirthDay = parentBirthDay;
	}

	public String getParentCI() {
		return this.parentCI;
	}

	public void setParentCI(String parentCI) {
		this.parentCI = parentCI;
	}

	public String getParentDate() {
		return this.parentDate;
	}

	public void setParentDate(String parentDate) {
		this.parentDate = parentDate;
	}

	public String getParentEmail() {
		return this.parentEmail;
	}

	public void setParentEmail(String parentEmail) {
		this.parentEmail = parentEmail;
	}

	public String getParentMDN() {
		return this.parentMDN;
	}

	public void setParentMDN(String parentMDN) {
		this.parentMDN = parentMDN;
	}

	public String getParentName() {
		return this.parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getParentRealNameDate() {
		return this.parentRealNameDate;
	}

	public void setParentRealNameDate(String parentRealNameDate) {
		this.parentRealNameDate = parentRealNameDate;
	}

	public String getParentRealNameMethod() {
		return this.parentRealNameMethod;
	}

	public void setParentRealNameMethod(String parentRealNameMethod) {
		this.parentRealNameMethod = parentRealNameMethod;
	}

	public String getParentRealNameSite() {
		return this.parentRealNameSite;
	}

	public void setParentRealNameSite(String parentRealNameSite) {
		this.parentRealNameSite = parentRealNameSite;
	}

	public String getParentTelecom() {
		return this.parentTelecom;
	}

	public void setParentTelecom(String parentTelecom) {
		this.parentTelecom = parentTelecom;
	}

	public String getParentType() {
		return this.parentType;
	}

	public void setParentType(String parentType) {
		this.parentType = parentType;
	}

	public String getSequence() {
		return this.sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

}
