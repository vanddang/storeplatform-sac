package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.AgreementInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserInfo;

/**
 * [RESPONSE] 회원 정보 조회
 * 
 * Updated on : 2014. 1. 6. Updated by : 강신완, 부르칸.
 */
public class DetailRes extends CommonInfo {

	private static final long serialVersionUID = 1L;
	/* 실명인증여부 */
	private String isRealName;
	/* 휴대기기정보 리스트 */
	private List<DeviceInfo> deviceInfoList;
	/* 사용자 정보 */
	private List<UserInfo> userInfoList;
	/* 약관동의 리스트 */
	private List<AgreementInfo> agreementList;

	public String getIsRealName() {
		return this.isRealName;
	}

	public void setIsRealName(String isRealName) {
		this.isRealName = isRealName;
	}

	public List<DeviceInfo> getDeviceInfoList() {
		return this.deviceInfoList;
	}

	public void setDeviceInfoList(List<DeviceInfo> deviceInfoList) {
		this.deviceInfoList = deviceInfoList;
	}

	public List<UserInfo> getUserInfo() {
		return this.userInfoList;
	}

	public void setUserInfo(List<UserInfo> userInfo) {
		this.userInfoList = userInfo;
	}

	public List<AgreementInfo> getAgreementList() {
		return this.agreementList;
	}

	public void setAgreementList(List<AgreementInfo> agreementList) {
		this.agreementList = agreementList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
