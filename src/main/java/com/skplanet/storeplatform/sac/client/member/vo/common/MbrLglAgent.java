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

	private String isParent;
	private String memberKey;
	private String parentBirthDay;
	private String parentCI;
	private String parentDate;
	private String parentEmail;
	private String parentMDN;
	private String parentName;
	private String parentRealNameDate;
	private String parentRealNameMethod;
	private String parentRealNameSite;
	private String parentTelecom;
	private String parentType;
	private String sequence;

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
