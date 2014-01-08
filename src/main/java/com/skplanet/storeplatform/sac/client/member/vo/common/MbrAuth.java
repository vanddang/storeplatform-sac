package com.skplanet.storeplatform.sac.client.member.vo.common;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 실명인증 Value
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class MbrAuth extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String birthDay;
	private String ci;
	private String di;
	private String isRealName;
	private String memberCategory;
	private String memberKey;
	private String name;
	private String phone;
	private String realNameDate;
	private String realNameMethod;
	private String realNameSite;
	private String sequence;
	private String sex;
	private String telecom;
	private String tenantID;
	private String updateDate;

	public String getBirthDay() {
		return this.birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public String getCi() {
		return this.ci;
	}

	public void setCi(String ci) {
		this.ci = ci;
	}

	public String getDi() {
		return this.di;
	}

	public void setDi(String di) {
		this.di = di;
	}

	public String getIsRealName() {
		return this.isRealName;
	}

	public void setIsRealName(String isRealName) {
		this.isRealName = isRealName;
	}

	public String getMemberCategory() {
		return this.memberCategory;
	}

	public void setMemberCategory(String memberCategory) {
		this.memberCategory = memberCategory;
	}

	public String getMemberKey() {
		return this.memberKey;
	}

	public void setMemberKey(String memberKey) {
		this.memberKey = memberKey;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRealNameDate() {
		return this.realNameDate;
	}

	public void setRealNameDate(String realNameDate) {
		this.realNameDate = realNameDate;
	}

	public String getRealNameMethod() {
		return this.realNameMethod;
	}

	public void setRealNameMethod(String realNameMethod) {
		this.realNameMethod = realNameMethod;
	}

	public String getRealNameSite() {
		return this.realNameSite;
	}

	public void setRealNameSite(String realNameSite) {
		this.realNameSite = realNameSite;
	}

	public String getSequence() {
		return this.sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTelecom() {
		return this.telecom;
	}

	public void setTelecom(String telecom) {
		this.telecom = telecom;
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

}
