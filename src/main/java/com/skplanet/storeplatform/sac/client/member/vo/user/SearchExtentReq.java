package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST] 회원정보조회 정보조회범위
 * 
 * Updated on : 2014. 1. 27. Updated by : 강신완. 지티소프트.
 */
public class SearchExtentReq extends CommonInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/* 사용자 부가 정보 */
	private String userInfoExtraYn;

	/* 단말정보 */
	private String deviceInfoYn;

	/* 단말부가정보 */
	private String deviceInfoExtraYn;

	/* 약관동의정보 */
	private String agreementInfoYn;

	/* 실명인증정보 */
	private String mbrAuthInfoYn;

	/* 법정대리인정보 */
	private String mbrLglAgentInfoYn;

	public String getUserInfoExtraYn() {
		return this.userInfoExtraYn;
	}

	public void setUserInfoExtraYn(String userInfoExtraYn) {
		this.userInfoExtraYn = userInfoExtraYn;
	}

	public String getDeviceInfoYn() {
		return this.deviceInfoYn;
	}

	public void setDeviceInfoYn(String deviceInfoYn) {
		this.deviceInfoYn = deviceInfoYn;
	}

	public String getDeviceInfoExtraYn() {
		return this.deviceInfoExtraYn;
	}

	public void setDeviceInfoExtraYn(String deviceInfoExtraYn) {
		this.deviceInfoExtraYn = deviceInfoExtraYn;
	}

	public String getAgreementInfoYn() {
		return this.agreementInfoYn;
	}

	public void setAgreementInfoYn(String agreementInfoYn) {
		this.agreementInfoYn = agreementInfoYn;
	}

	public String getMbrAuthInfoYn() {
		return this.mbrAuthInfoYn;
	}

	public void setMbrAuthInfoYn(String mbrAuthInfoYn) {
		this.mbrAuthInfoYn = mbrAuthInfoYn;
	}

	public String getMbrLglAgentInfoYn() {
		return this.mbrLglAgentInfoYn;
	}

	public void setMbrLglAgentInfoYn(String mbrLglAgentInfoYn) {
		this.mbrLglAgentInfoYn = mbrLglAgentInfoYn;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
