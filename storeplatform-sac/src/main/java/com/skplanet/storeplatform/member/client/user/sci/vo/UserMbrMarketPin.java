package com.skplanet.storeplatform.member.client.user.sci.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;

import java.util.List;

/**
 * market pin 정보 Value Object.
 * 
 * Updated on : 2016. 01. 27. Updated by : 반범진.
 */
public class UserMbrMarketPin extends CommonInfo {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 사용자 Key. */
	private String userKey; // INSD_USERMBR_NO
	/** PIN 번호. */
	private String pinNo; // PIN_NO
	/** 인증_실패_횟수. */
	private String authFailCnt; // AUTH_FAIL_CNT

	/**
	 * @return the pinNo
	 */
	public String getPinNo() {
		return this.pinNo;
	}

	/**
	 * @param pinNo
	 *            the pinNo to set
	 */
	public void setPinNo(String pinNo) {
		this.pinNo = pinNo;
	}

	/**
	 * @return the userKey
	 */
	public String getUserKey() {
		return userKey;
	}

	/**
	 * @param userKey
	 *            the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return the authFailCnt
	 */
	public String getAuthFailCnt() {
		return authFailCnt;
	}

	/**
	 * @param authFailCnt
	 *            the authFailCnt to set
	 */
	public void setAuthFailCnt(String authFailCnt) {
		this.authFailCnt = authFailCnt;
	}
}
