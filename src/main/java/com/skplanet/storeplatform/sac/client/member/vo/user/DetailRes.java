package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.Agreement;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
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
public class DetailRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

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

	public String getIsChangeSubject() {
		return this.isChangeSubject;
	}

	public void setIsChangeSubject(String isChangeSubject) {
		this.isChangeSubject = isChangeSubject;
	}

	public String getPwRegDate() {
		return this.pwRegDate;
	}

	public void setPwRegDate(String pwRegDate) {
		this.pwRegDate = pwRegDate;
	}

	public String getUserKey() {
		return this.userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public MbrAuth getMbrAuth() {
		return this.mbrAuth;
	}

	public void setMbrAuth(MbrAuth mbrAuth) {
		this.mbrAuth = mbrAuth;
	}

	public MbrLglAgent getMbrLglAgent() {
		return this.mbrLglAgent;
	}

	public void setMbrLglAgent(MbrLglAgent mbrLglAgent) {
		this.mbrLglAgent = mbrLglAgent;
	}

	public List<DeviceInfo> getDeviceInfoList() {
		return this.deviceInfoList;
	}

	public void setDeviceInfoList(List<DeviceInfo> deviceInfoList) {
		this.deviceInfoList = deviceInfoList;
	}

	public UserInfo getUserInfo() {
		return this.userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public List<Agreement> getAgreementList() {
		return this.agreementList;
	}

	public void setAgreementList(List<Agreement> agreementList) {
		this.agreementList = agreementList;
	}

	public UserMbrPnsh getUserMbrPnsh() {
		return this.userMbrPnsh;
	}

	public void setUserMbrPnsh(UserMbrPnsh userMbrPnsh) {
		this.userMbrPnsh = userMbrPnsh;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
