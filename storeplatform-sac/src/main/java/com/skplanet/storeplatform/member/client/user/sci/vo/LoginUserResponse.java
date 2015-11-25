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
import com.skplanet.storeplatform.member.client.common.vo.CommonResponse;

// TODO: Auto-generated Javadoc
/**
 * 사용자 로그인 응답을 위한 Value object
 * 
 * Updated on : 2013. 12. 10. Updated by : wisestone_mikepark
 */
public class LoginUserResponse extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Returns the serial version UID.
	 * 
	 * @return serialVersionUID - the serial version UID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/** 공통 응답 Value Object. */
	private CommonResponse commonResponse;

	/**
	 * 공통 응답 Value Object를 리턴한다.
	 * 
	 * @return commonResponse - 공통 응답 Value Object
	 */
	public CommonResponse getCommonResponse() {
		return this.commonResponse;
	}

	/**
	 * 공통 응답 Value Object를 설정한다.
	 * 
	 * @param commonResponse
	 *            공통 응답 Value Object
	 */
	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
	}

	/** 사용자 Key. */
	private String userKey;

	/**
	 * 사용자 메인 상태 코드.
	 * 
	 * @see com.skplanet.storeplatform.member.common.code.MainStateCode
	 */
	private String userMainStatus;

	/**
	 * 사용자 서브 상태 코드.
	 * 
	 * @see com.skplanet.storeplatform.member.common.code.SubStateCode
	 */
	private String userSubStatus;

	/** 로그인 성공 여부. */
	private String isLoginSuccess;

	/** 로그인 실패 횟수. */
	private int loginFailCount;

	/** 로그인 상태 코드. */
	private String loginStatusCode; // LOGIN_STATUS_CD

	/** 직권중지 상태 코드. */
	private String stopStatusCode; // OFAUTH_STOP_STATUS_CD

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
	 * 직권중지 상태 코드를 리턴한다.
	 * 
	 * @return stopStatusCode - 직권중지 상태 코드
	 */
	public String getStopStatusCode() {
		return this.stopStatusCode;
	}

	/**
	 * 직권중지 상태 코드를 설정한다.
	 * 
	 * @param stopStatusCode
	 *            직권중지 상태 코드
	 */
	public void setStopStatusCode(String stopStatusCode) {
		this.stopStatusCode = stopStatusCode;
	}

	/**
	 * 사용자 Key를 리턴한다.
	 * 
	 * @return userKey - 사용자 Key
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * 사용자 Key를 리턴한다.
	 * 
	 * @param userKey
	 *            사용자 Key
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * 사용자 메인상태 정보를 리턴한다.
	 * 
	 * @return userMainStatus - 사용자 메인상태 정보
	 */
	public String getUserMainStatus() {
		return this.userMainStatus;
	}

	/**
	 * 사용자 메인상태 정보를 설정한다.
	 * 
	 * @param userMainStatus
	 *            사용자 메인상태 정보
	 */
	public void setUserMainStatus(String userMainStatus) {
		this.userMainStatus = userMainStatus;
	}

	/**
	 * 사용자 서브상태 정보를 리턴한다.
	 * 
	 * @return userSubStatus - 사용자 서브상태 정보
	 */
	public String getUserSubStatus() {
		return this.userSubStatus;
	}

	/**
	 * 사용자 서브상태 정보를 설정한다.
	 * 
	 * @param userSubStatus
	 *            사용자 서브상태 정보
	 */
	public void setUserSubStatus(String userSubStatus) {
		this.userSubStatus = userSubStatus;
	}

	/**
	 * 로그인 성공 여부(Y/N)을 리턴한다.
	 * 
	 * @return isLoginSuccess - 로그인 성공 여부(Y/N)
	 */
	public String getIsLoginSuccess() {
		return this.isLoginSuccess;
	}

	/**
	 * 로그인 성공 여부(Y/N)을 설정한다.
	 * 
	 * @param isLoginSuccess
	 *            로그인 성공 여부(Y/N)
	 */
	public void setIsLoginSuccess(String isLoginSuccess) {
		this.isLoginSuccess = isLoginSuccess;
	}

	/**
	 * 로그인 실패 횟수를 리턴한다.
	 * 
	 * @return loginFailCount - 로그인 실패 횟수
	 */
	public int getLoginFailCount() {
		return this.loginFailCount;
	}

	/**
	 * 로그인 실패 횟수를 설정한다.
	 * 
	 * @param loginFailCount
	 *            로그인 실패 횟수
	 */
	public void setLoginFailCount(int loginFailCount) {
		this.loginFailCount = loginFailCount;
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
