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

	/** 사용자 정보. */
	private String userInfoYn;

	/** 단말정보. */
	private String deviceInfoYn;

	/** 약관동의정보. */
	private String agreementInfoYn;

	/** 실명인증정보. */
	private String mbrAuthInfoYn;

	/** 법정대리인정보. */
	private String mbrLglAgentInfoYn;

	/** 사용자 징계 정보. */
	private String mbrPnshInfoYn;

	/** 등급 정보. */
	private String gradeInfoYn;

	/**
	 * @return the userInfoYn
	 */
	public String getUserInfoYn() {
		return this.userInfoYn;
	}

	/**
	 * @param userInfoYn
	 *            the userInfoYn to set
	 */
	public void setUserInfoYn(String userInfoYn) {
		this.userInfoYn = userInfoYn;
	}

	/**
	 * @return the deviceInfoYn
	 */
	public String getDeviceInfoYn() {
		return this.deviceInfoYn;
	}

	/**
	 * @param deviceInfoYn
	 *            the deviceInfoYn to set
	 */
	public void setDeviceInfoYn(String deviceInfoYn) {
		this.deviceInfoYn = deviceInfoYn;
	}

	/**
	 * @return the agreementInfoYn
	 */
	public String getAgreementInfoYn() {
		return this.agreementInfoYn;
	}

	/**
	 * @param agreementInfoYn
	 *            the agreementInfoYn to set
	 */
	public void setAgreementInfoYn(String agreementInfoYn) {
		this.agreementInfoYn = agreementInfoYn;
	}

	/**
	 * @return the mbrAuthInfoYn
	 */
	public String getMbrAuthInfoYn() {
		return this.mbrAuthInfoYn;
	}

	/**
	 * @param mbrAuthInfoYn
	 *            the mbrAuthInfoYn to set
	 */
	public void setMbrAuthInfoYn(String mbrAuthInfoYn) {
		this.mbrAuthInfoYn = mbrAuthInfoYn;
	}

	/**
	 * @return the mbrLglAgentInfoYn
	 */
	public String getMbrLglAgentInfoYn() {
		return this.mbrLglAgentInfoYn;
	}

	/**
	 * @param mbrLglAgentInfoYn
	 *            the mbrLglAgentInfoYn to set
	 */
	public void setMbrLglAgentInfoYn(String mbrLglAgentInfoYn) {
		this.mbrLglAgentInfoYn = mbrLglAgentInfoYn;
	}

	/**
	 * @return the mbrPnshInfoYn
	 */
	public String getMbrPnshInfoYn() {
		return this.mbrPnshInfoYn;
	}

	/**
	 * @param mbrPnshInfoYn
	 *            the mbrPnshInfoYn to set
	 */
	public void setMbrPnshInfoYn(String mbrPnshInfoYn) {
		this.mbrPnshInfoYn = mbrPnshInfoYn;
	}

	/**
	 * @return the gradeInfoYn
	 */
	public String getGradeInfoYn() {
		return this.gradeInfoYn;
	}

	/**
	 * @param gradeInfoYn
	 *            the gradeInfoYn to set
	 */
	public void setGradeInfoYn(String gradeInfoYn) {
		this.gradeInfoYn = gradeInfoYn;
	}
}
