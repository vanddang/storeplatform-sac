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
import com.skplanet.storeplatform.member.client.common.vo.MbrAuth;

/**
 * 실명인증 조회 응답 Value Object
 * 
 * Updated on : 2014. 2. 25 Updated by : wisestone_brian, wisestone
 */
public class SearchRealNameResponse extends CommonInfo implements Serializable {
	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 응답 Value Object. */
	private CommonResponse commonResponse;

	/** 사용자 Key. */
	private String userKey;

	/** 사용자 인증정보 Value Object. */
	private MbrAuth mbrAuth;

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

	/**
	 * 사용자 Key를 리턴한다.
	 * 
	 * @return userKey - 사용자 Key
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * 사용자 Key를 설정한다.
	 * 
	 * @param userKey
	 *            사용자 Key
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * 사용자 인증정보 Value Object를 리턴한다.
	 * 
	 * @return userMbrAuth - 사용자 인증정보 Value Object
	 */
	public MbrAuth getMbrAuth() {
		return this.mbrAuth;
	}

	/**
	 * 사용자 인증정보 Value Object를 설정한다.
	 * 
	 * @param mbrAuth
	 *            사용자 인증정보 Value Object
	 */
	public void setMbrAuth(MbrAuth mbrAuth) {
		this.mbrAuth = mbrAuth;
	}

	/**
	 * Returns the serial version UID.
	 * 
	 * @return serialVersionUID - the serial version UID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
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
