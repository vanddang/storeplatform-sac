package com.skplanet.storeplatform.sac.client.member.vo.common;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 약관동의 Value
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class MbrClauseAgreeList extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String extraAgreementID;
	private String extraAgreementVersion;
	private String isExtraAgreement;
	private String isMandatory;
	private String memberKey;
	private String regDate;
	private String tenantID;
	private String updateDate;

	public String getExtraAgreementID() {
		return this.extraAgreementID;
	}

	public void setExtraAgreementID(String extraAgreementID) {
		this.extraAgreementID = extraAgreementID;
	}

	public String getExtraAgreementVersion() {
		return this.extraAgreementVersion;
	}

	public void setExtraAgreementVersion(String extraAgreementVersion) {
		this.extraAgreementVersion = extraAgreementVersion;
	}

	public String getIsExtraAgreement() {
		return this.isExtraAgreement;
	}

	public void setIsExtraAgreement(String isExtraAgreement) {
		this.isExtraAgreement = isExtraAgreement;
	}

	public String getIsMandatory() {
		return this.isMandatory;
	}

	public void setIsMandatory(String isMandatory) {
		this.isMandatory = isMandatory;
	}

	public String getMemberKey() {
		return this.memberKey;
	}

	public void setMemberKey(String memberKey) {
		this.memberKey = memberKey;
	}

	public String getRegDate() {
		return this.regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getTenantID() {
		return this.tenantID;
	}

	public void setTenantID(String tenantID) {
		this.tenantID = tenantID;
	}

	public String getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
