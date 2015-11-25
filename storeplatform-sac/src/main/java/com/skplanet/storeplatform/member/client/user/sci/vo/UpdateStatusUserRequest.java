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
import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;

/**
 * 사용자 상태변경 요청을 위한 Value Object
 * 
 * Updated on : 2013. 12. 10. Updated by : wisestone_mikepark
 */
public class UpdateStatusUserRequest extends CommonInfo implements Serializable {

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

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

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
	 * <pre>
	 * 검색조건 Value Object List.
	 * INSD_USERMBR_NO : 내부 사용자 키
	 * MBR_ID : 사용자 ID
	 * USERMBR_NO : 통합서비스 키
	 * INTG_SVC_NO : 통합서비스 관리번호
	 * INSD_DEVICE_ID : Device 내부키
	 * DEVICE_ID : Device 고유ID
	 * </pre>
	 */
	private List<KeySearch> keySearchList;

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

	/** 로그인 상태 코드. */
	private String loginStatusCode; // LOGIN_STATUS_CD

	/** 직권중지 상태 코드. */
	private String stopStatusCode; // OFAUTH_STOP_STATUS_CD

	/**
	 * 휴면계정유무.
	 */
	private String isDormant;

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
	 * 검색 조건을 리턴한다.
	 * 
	 * @return keySearch - 검색 조건 Value Object
	 */
	public List<KeySearch> getKeySearchList() {
		return this.keySearchList;
	}

	/**
	 * 검색조건을 설정한다.
	 * 
	 * @param keySearchList
	 *            the new key search list
	 */
	public void setKeySearchList(List<KeySearch> keySearchList) {
		this.keySearchList = keySearchList;
	}

	/**
	 * 사용자 메인 상태코드를 리턴한다.
	 * 
	 * @return userMainStatus - 사용자 메인 상태코드
	 */
	public String getUserMainStatus() {
		return this.userMainStatus;
	}

	/**
	 * 사용자 메인 상태코드를 설정한다.
	 * 
	 * @param userMainStatus
	 *            사용자 메인 상태코드
	 */
	public void setUserMainStatus(String userMainStatus) {
		this.userMainStatus = userMainStatus;
	}

	/**
	 * 사용자 서브 상태코드를 리턴한다.
	 * 
	 * @return userSubStatus - 사용자 서브 상태코드
	 */
	public String getUserSubStatus() {
		return this.userSubStatus;
	}

	/**
	 * 사용자 서브 상태코드를 설정한다.
	 * 
	 * @param userSubStatus
	 *            사용자 서브 상태코드
	 */
	public void setUserSubStatus(String userSubStatus) {
		this.userSubStatus = userSubStatus;
	}

	/**
	 * @return isDormant
	 */
	public String getIsDormant() {
		return this.isDormant;
	}

	/**
	 * @param isDormant
	 *            String
	 */
	public void setIsDormant(String isDormant) {
		this.isDormant = isDormant;
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
