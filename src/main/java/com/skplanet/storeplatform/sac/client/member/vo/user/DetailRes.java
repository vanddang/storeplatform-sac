package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceExtraInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrAuth;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrClauseAgreeList;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrLglAgent;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserExtraInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserInfo;

/**
 * [RESPONSE] 회원 정보 조회
 * 
 * Updated on : 2014. 1. 6. Updated by : 강신완, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class DetailRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/* 사용자 인증정보 */
	private List<MbrAuth> mbrAuthList;
	/* 법정대리인 정보 */
	private List<MbrLglAgent> mbrLglAgentList;
	/* 휴대기기정보 리스트 */
	private List<DeviceInfo> deviceInfoList;
	/* 휴대기기 부가정보 리스트 */
	private List<DeviceExtraInfo> deviceExtraInfoList;
	/* 사용자 정보 */
	private List<UserInfo> userInfoList;
	/* 사용자 부가정보 */
	private List<UserExtraInfo> userExtraInfoList;
	/* 약관동의정보 리스트 */
	private List<MbrClauseAgreeList> mbrClauseAgreeList;

	public List<MbrAuth> getMbrAuthList() {
		return this.mbrAuthList;
	}

	public void setMbrAuthList(List<MbrAuth> mbrAuthList) {
		this.mbrAuthList = mbrAuthList;
	}

	public List<MbrLglAgent> getMbrLglAgentList() {
		return this.mbrLglAgentList;
	}

	public void setMbrLglAgentList(List<MbrLglAgent> mbrLglAgentList) {
		this.mbrLglAgentList = mbrLglAgentList;
	}

	public List<DeviceInfo> getDeviceInfoList() {
		return this.deviceInfoList;
	}

	public void setDeviceInfoList(List<DeviceInfo> deviceInfoList) {
		this.deviceInfoList = deviceInfoList;
	}

	public List<DeviceExtraInfo> getDeviceExtraInfoList() {
		return this.deviceExtraInfoList;
	}

	public void setDeviceExtraInfoList(List<DeviceExtraInfo> deviceExtraInfoList) {
		this.deviceExtraInfoList = deviceExtraInfoList;
	}

	public List<UserInfo> getUserInfoList() {
		return this.userInfoList;
	}

	public void setUserInfoList(List<UserInfo> userInfoList) {
		this.userInfoList = userInfoList;
	}

	public List<UserExtraInfo> getUserExtraInfoList() {
		return this.userExtraInfoList;
	}

	public void setUserExtraInfoList(List<UserExtraInfo> userExtraInfoList) {
		this.userExtraInfoList = userExtraInfoList;
	}

	public List<MbrClauseAgreeList> getMbrClauseAgreeList() {
		return this.mbrClauseAgreeList;
	}

	public void setMbrClauseAgreeList(List<MbrClauseAgreeList> mbrClauseAgreeList) {
		this.mbrClauseAgreeList = mbrClauseAgreeList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
