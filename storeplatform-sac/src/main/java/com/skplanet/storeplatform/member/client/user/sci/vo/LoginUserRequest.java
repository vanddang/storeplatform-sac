/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;

/**
 * 사용자 로그인 요청을 위한 Value object
 * 
 * Updated on : 2013. 12. 10. Updated by : wisestone_mikepark
 */
public class LoginUserRequest extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/** 사용자 ID. */
	private String userID;

	/** 사용자 패스워드. */
	private String userPW;

	/** OneID 사용 여부. */
	private String isOneID;

	/** 로그인 성공 여부. */
	private String isSuccess;

	/** 모바일 회원 여부. */
	private String isMobile;

	/** 요청 IP 주소. */
	private String ipAddress;

	/** SC 버전. */
	private String scVersion;

	/** 로그인 상태 코드. */
	private String loginStatusCode; // LOGIN_STATUS_CD

	/** 자동로그인 여부. */
	private String isAutoLogin; // AUTO_LOGIN_YN

	/** 로그인 사유. */
	private String loginReason; // LOGIN_REASON

	/** 마지막 로그인 시간 업데이트 여부. */
	private String isUpdLastLoginDt;

	/** 기기_모델_명. */
	private String deviceModelNm;

	/** 기기_OS_명. */
	private String deviceOsNm;

	/** 기기_OS_버전. */
	private String deviceOsVersion;

    /** device 정보 */
    private DeviceInfo deviceInfo;

	/**
	 * 로그인 상태 코드를 리턴한다.
	 * 
	 * @return loginStatusCode - 로그인 상태 코드
	 */
	public String getLoginStatusCode() {
		return this.loginStatusCode;
	}

	/**
	 * 로그인 상태 코드를 설정한다.
	 * 
	 * @param loginStatusCode
	 *            로그인 상태 코드
	 */
	public void setLoginStatusCode(String loginStatusCode) {
		this.loginStatusCode = loginStatusCode;
	}

	/**
	 * 요청 IP 주소를 반환.
	 * 
	 * @return 요청 IP 주소 반환.
	 */
	public String getIpAddress() {
		return this.ipAddress;
	}

	/**
	 * 요청 IP 주소를 설정.
	 * 
	 * @param ipAddress
	 *            요청 IP 주소.
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * SC 버전을 반환.
	 * 
	 * @return SC 버전
	 */
	public String getScVersion() {
		return this.scVersion;
	}

	/**
	 * SC 버전을 설정.
	 * 
	 * @param scVersion
	 *            SC 버전
	 */
	public void setScVersion(String scVersion) {
		this.scVersion = scVersion;
	}

	/**
	 * 모바일 회원 여부 반환.
	 * 
	 * @return isMobile 모바일 회원 여부
	 */
	public String getIsMobile() {
		return this.isMobile;
	}

	/**
	 * 모바일 회원 여부 설정.
	 * 
	 * @param isMobile
	 *            모바일 회원 여부
	 */
	public void setIsMobile(String isMobile) {
		this.isMobile = isMobile;
	}

	/**
	 * Returns the serial version UID.
	 * 
	 * @return serialVersionUID - the serial version UID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 공통 요청 Value Object를 리턴한다.
	 * 
	 * @return commonRequest - 공통 요청 Value Object
	 */
	public CommonRequest getCommonRequest() {
		return this.commonRequest;
	}

	/**
	 * 공통 요청 Value Object를 설정한다.
	 * 
	 * @param commonRequest
	 *            공통 요청 Value Object
	 */
	public void setCommonRequest(CommonRequest commonRequest) {
		this.commonRequest = commonRequest;
	}

	/**
	 * 사용자 ID를 리턴한다.
	 * 
	 * @return userID - 사용자 ID
	 */
	public String getUserID() {
		return this.userID;
	}

	/**
	 * 사용자 ID를 설정한다.
	 * 
	 * @param userID
	 *            사용자 ID
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}

	/**
	 * 사용자 패스워드를 리턴한다.
	 * 
	 * @return userPW - 사용자 패스워드
	 */
	public String getUserPW() {
		return this.userPW;
	}

	/**
	 * 사용자 패스워드를 설정한다.
	 * 
	 * @param userPW
	 *            사용자 패스워드
	 */
	public void setUserPW(String userPW) {
		this.userPW = userPW;
	}

	/**
	 * OneID 사용여부를 리턴한다.
	 * 
	 * @return isOneID - OneID 사용여부
	 */
	public String getIsOneID() {
		return this.isOneID;
	}

	/**
	 * OneID 사용여부를 설정한다.
	 * 
	 * @param isOneID
	 *            OneID 사용여부
	 */
	public void setIsOneID(String isOneID) {
		this.isOneID = isOneID;
	}

	/**
	 * OneID로 로그인했을때 로그인 성공 여부를 리턴한다.
	 * 
	 * @return the checks if is success - 로그인 성공 여부
	 */
	public String getIsSuccess() {
		return this.isSuccess;
	}

	/**
	 * OneID로 로그인했을때 로그인 성공 여부를 설정한다.
	 * 
	 * @param isSuccess
	 *            로그인 성공 여부
	 */
	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	/**
	 * 자동로그인 여부를 리턴한다.
	 * 
	 * @return isAutoLogin - 자동로그인 여부
	 */
	public String getIsAutoLogin() {
		return this.isAutoLogin;
	}

	/**
	 * 자동로그인 여부를 설정한다.
	 * 
	 * @param isAutoLogin
	 *            자동로그인 여부
	 */
	public void setIsAutoLogin(String isAutoLogin) {
		this.isAutoLogin = isAutoLogin;
	}

	/**
	 * 로그인 사유를 리턴한다.
	 * 
	 * @return loginReason - 로그인 사유
	 */
	public String getLoginReason() {
		return this.loginReason;
	}

	/**
	 * 로그인 사유를 설정한다.
	 * 
	 * @param loginReason
	 *            로그인 사유
	 */
	public void setLoginReason(String loginReason) {
		this.loginReason = loginReason;
	}

	/**
	 * @return isUpdLastLoginDt
	 */
	public String getIsUpdLastLoginDt() {
		return this.isUpdLastLoginDt;
	}

	/**
	 * @param isUpdLastLoginDt
	 *            String
	 */
	public void setIsUpdLastLoginDt(String isUpdLastLoginDt) {
		this.isUpdLastLoginDt = isUpdLastLoginDt;
	}

	/**
	 * @return the deviceModelNm
	 */
	public String getDeviceModelNm() {
		return this.deviceModelNm;
	}

	/**
	 * @param deviceModelNm
	 *            the deviceModelNm to set
	 */
	public void setDeviceModelNm(String deviceModelNm) {
		this.deviceModelNm = deviceModelNm;
	}

	/**
	 * @return the deviceOsNm
	 */
	public String getDeviceOsNm() {
		return this.deviceOsNm;
	}

	/**
	 * @param deviceOsNm
	 *            the deviceOsNm to set
	 */
	public void setDeviceOsNm(String deviceOsNm) {
		this.deviceOsNm = deviceOsNm;
	}

	/**
	 * @return the deviceOsVersion
	 */
	public String getDeviceOsVersion() {
		return this.deviceOsVersion;
	}

	/**
	 * @param deviceOsVersion
	 *            the deviceOsVersion to set
	 */
	public void setDeviceOsVersion(String deviceOsVersion) {
		this.deviceOsVersion = deviceOsVersion;
	}

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    /*
         * (non-Javadoc)
         *
         * @see java.lang.Object#toString()
         */
	@Override
	public String toString() {
		return Utils.printKeyValues(this);
	}

}
