package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.Agreement;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.GradeInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrAuth;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrLglAgent;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserMbrPnsh;

/**
 * [RESPONSE] 회원 정보 조회
 * 
 * Updated on : 2014. 1. 6. Updated by : 강신완, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class DetailV2Res extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String code;

	private String message;

	/* 사용자 변동성 여부 */
	private String isChangeSubject;

	/* 비밀번호 변경일자 */
	private String pwRegDate;

	/* 사용자 키 */
	private String userKey;

	/* 사용자 인증정보 */
	private MbrAuth mbrAuth;

	/* 법정대리인 정보 */
	private MbrLglAgent mbrLglAgent;

	/* 휴대기기정보 리스트 */
	private List<DeviceInfo> deviceInfoList;

	/* 사용자 정보 */
	private UserInfo userInfo;

	/* 약관동의정보 리스트 */
	private List<Agreement> agreementList;

	/* 징계정보 */
	private UserMbrPnsh userMbrPnsh;

	/** 사용자 회원 등급정보. */
	private GradeInfo gradeInfo;

	/**
	 * @return the code
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the isChangeSubject
	 */
	public String getIsChangeSubject() {
		return this.isChangeSubject;
	}

	/**
	 * @param isChangeSubject
	 *            the isChangeSubject to set
	 */
	public void setIsChangeSubject(String isChangeSubject) {
		this.isChangeSubject = isChangeSubject;
	}

	/**
	 * @return the pwRegDate
	 */
	public String getPwRegDate() {
		return this.pwRegDate;
	}

	/**
	 * @param pwRegDate
	 *            the pwRegDate to set
	 */
	public void setPwRegDate(String pwRegDate) {
		this.pwRegDate = pwRegDate;
	}

	/**
	 * @return the userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return the mbrAuth
	 */
	public MbrAuth getMbrAuth() {
		return this.mbrAuth;
	}

	/**
	 * @param mbrAuth
	 *            the mbrAuth to set
	 */
	public void setMbrAuth(MbrAuth mbrAuth) {
		this.mbrAuth = mbrAuth;
	}

	/**
	 * @return the mbrLglAgent
	 */
	public MbrLglAgent getMbrLglAgent() {
		return this.mbrLglAgent;
	}

	/**
	 * @param mbrLglAgent
	 *            the mbrLglAgent to set
	 */
	public void setMbrLglAgent(MbrLglAgent mbrLglAgent) {
		this.mbrLglAgent = mbrLglAgent;
	}

	/**
	 * @return the deviceInfoList
	 */
	public List<DeviceInfo> getDeviceInfoList() {
		return this.deviceInfoList;
	}

	/**
	 * @param deviceInfoList
	 *            the deviceInfoList to set
	 */
	public void setDeviceInfoList(List<DeviceInfo> deviceInfoList) {
		this.deviceInfoList = deviceInfoList;
	}

	/**
	 * @return the userInfo
	 */
	public UserInfo getUserInfo() {
		return this.userInfo;
	}

	/**
	 * @param userInfo
	 *            the userInfo to set
	 */
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	/**
	 * @return the agreementList
	 */
	public List<Agreement> getAgreementList() {
		return this.agreementList;
	}

	/**
	 * @param agreementList
	 *            the agreementList to set
	 */
	public void setAgreementList(List<Agreement> agreementList) {
		this.agreementList = agreementList;
	}

	/**
	 * @return the userMbrPnsh
	 */
	public UserMbrPnsh getUserMbrPnsh() {
		return this.userMbrPnsh;
	}

	/**
	 * @param userMbrPnsh
	 *            the userMbrPnsh to set
	 */
	public void setUserMbrPnsh(UserMbrPnsh userMbrPnsh) {
		this.userMbrPnsh = userMbrPnsh;
	}

	/**
	 * @return the gradeInfo
	 */
	public GradeInfo getGradeInfo() {
		return this.gradeInfo;
	}

	/**
	 * @param gradeInfo
	 *            the gradeInfo to set
	 */
	public void setGradeInfo(GradeInfo gradeInfo) {
		this.gradeInfo = gradeInfo;
	}

}
