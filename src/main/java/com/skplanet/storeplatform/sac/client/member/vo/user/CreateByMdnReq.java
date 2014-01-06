package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.AgreementInfo;

/**
 * [REQUEST] 모바일 전용 회원 가입 (MDN 회원 가입)
 * 
 * Updated on : 2014. 1. 3. Updated by : 심대진, 다모아 솔루션.
 */
public class CreateByMdnReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 기기 ID (mdn, uuid)
	 */
	@NotNull(message = "아놔짬뽕나...")
	private String deviceId;

	@NotEmpty(message = "아놔 짬뽕나...")
	private String deviceTelecom;

	private String joinId;

	private String deviceModelNo;

	private List<AgreementInfo> agreementList;

	private String ownBirth;

	private String parentType;

	private String realNameMethod;

	private String parentName;

	private String parentCI;

	private String parentEmail;

	private String parentTelecom;

	private String parentMdn;

	private String parentResident;

	private String parentBirth;

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceTelecom() {
		return this.deviceTelecom;
	}

	public void setDeviceTelecom(String deviceTelecom) {
		this.deviceTelecom = deviceTelecom;
	}

	public String getJoinId() {
		return this.joinId;
	}

	public void setJoinId(String joinId) {
		this.joinId = joinId;
	}

	public String getDeviceModelNo() {
		return this.deviceModelNo;
	}

	public void setDeviceModelNo(String deviceModelNo) {
		this.deviceModelNo = deviceModelNo;
	}

	public List<AgreementInfo> getAgreementList() {
		return this.agreementList;
	}

	public void setAgreementList(List<AgreementInfo> agreementList) {
		this.agreementList = agreementList;
	}

	public String getOwnBirth() {
		return this.ownBirth;
	}

	public void setOwnBirth(String ownBirth) {
		this.ownBirth = ownBirth;
	}

	public String getParentType() {
		return this.parentType;
	}

	public void setParentType(String parentType) {
		this.parentType = parentType;
	}

	public String getRealNameMethod() {
		return this.realNameMethod;
	}

	public void setRealNameMethod(String realNameMethod) {
		this.realNameMethod = realNameMethod;
	}

	public String getParentName() {
		return this.parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getParentCI() {
		return this.parentCI;
	}

	public void setParentCI(String parentCI) {
		this.parentCI = parentCI;
	}

	public String getParentEmail() {
		return this.parentEmail;
	}

	public void setParentEmail(String parentEmail) {
		this.parentEmail = parentEmail;
	}

	public String getParentTelecom() {
		return this.parentTelecom;
	}

	public void setParentTelecom(String parentTelecom) {
		this.parentTelecom = parentTelecom;
	}

	public String getParentMdn() {
		return this.parentMdn;
	}

	public void setParentMdn(String parentMdn) {
		this.parentMdn = parentMdn;
	}

	public String getParentResident() {
		return this.parentResident;
	}

	public void setParentResident(String parentResident) {
		this.parentResident = parentResident;
	}

	public String getParentBirth() {
		return this.parentBirth;
	}

	public void setParentBirth(String parentBirth) {
		this.parentBirth = parentBirth;
	}

}
