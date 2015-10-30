/*
 * Copyright (c) 2014 SK planet.
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
import com.skplanet.storeplatform.member.client.common.vo.MbrOneID;

/**
 * 사용자 존재여부 확인 응답 Value Object.
 * 
 * Updated on : 2014. 1. 3 Updated by : wisestone_brian, wisestone
 */
public class CheckDuplicationResponse extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 응답 Value Object. */
	private CommonResponse commonResponse;

	/** ID/Email 존재 여부. Example : Y/N */
	private String isRegistered;

	/** (if isRegistered = Y) 사용자 ID. */
	private String userID;

	/** 사용자 정보 Value Object. */
	private UserMbr userMbr;

	/** 미동의 사용자 관리 Value Object. */
	private MbrOneID mbrOneID;

	/**
	 * 미동의 사용자 관리 Value Object를 리턴한다.
	 * 
	 * @return mbrOneID - 미동의 사용자 관리 Value Object
	 */
	public MbrOneID getMbrOneID() {
		return this.mbrOneID;
	}

	/**
	 * 미동의 사용자 관리 Value Object를 설정한다.
	 * 
	 * @param mbrOneID
	 *            미동의 사용자 관리 Value Object
	 */
	public void setMbrOneID(MbrOneID mbrOneID) {
		this.mbrOneID = mbrOneID;
	}

	/**
	 * 공통 응답 Value Object를 리턴한다.
	 * 
	 * @return CommonResponse - 공통 응답 Value Object
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

	/**
	 * Returns the serial version UID.
	 * 
	 * @return serialVersionUID - the serial version UID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 사용자 정보 Value Object를 리턴한다.
	 * 
	 * @return userMbr - 사용자 정보 Value Object
	 */
	public UserMbr getUserMbr() {
		return this.userMbr;
	}

	/**
	 * 사용자 정보 Value Object를 설정한다.
	 * 
	 * @param userMbr
	 *            사용자 정보 Value Object
	 */
	public void setUserMbr(UserMbr userMbr) {
		this.userMbr = userMbr;
	}

	/**
	 * ID/Email 존재 여부 (Y/N)를 리턴한다.
	 * 
	 * @return isRegistered - ID/Email 존재 여부 (Y/N).
	 */
	public String getIsRegistered() {
		return this.isRegistered;
	}

	/**
	 * ID/Email 존재 여부 (Y/N)를 설정한다.
	 * 
	 * @param isRegistered
	 *            ID/Email 존재 여부 (Y/N).
	 */
	public void setIsRegistered(String isRegistered) {
		this.isRegistered = isRegistered;
	}

	/**
	 * 검색결과에 의해 사용자가 존재하면 사용자 Key를 리턴한다.
	 * 
	 * @return userID - 사용자 ID
	 */
	public String getUserID() {
		return this.userID;
	}

	/**
	 * 사용자 Key를 설정한다.
	 * 
	 * @param userID
	 *            사용자 ID
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.framework.core.common.vo.CommonInfo#toString()
	 */
	@Override
	public String toString() {
		return Utils.printKeyValues(this);
	}

}
