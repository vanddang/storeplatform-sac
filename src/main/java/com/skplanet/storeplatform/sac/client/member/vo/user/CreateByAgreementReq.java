package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.AgreementInfo;

/**
 * [REQUEST] ID 회원 약관 동의 가입 (One ID 회원)
 * 
 * Updated on : 2014. 1. 3. Updated by : 심대진, 다모아 솔루션.
 */
public class CreateByAgreementReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String deviceId;

	private String userId;

	private String deviceTelecom;

	private String joinId;

	private String embededYn;

	private String deviceModelNo;

	private List<AgreementInfo> agreementList;

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getEmbededYn() {
		return this.embededYn;
	}

	public void setEmbededYn(String embededYn) {
		this.embededYn = embededYn;
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

}
