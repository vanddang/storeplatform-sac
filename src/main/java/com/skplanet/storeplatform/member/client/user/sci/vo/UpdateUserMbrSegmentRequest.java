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

/**
 * 유통망 추천앱 회원 정보 등록 요청 Value Object
 * 
 * Updated on : 2014. 02. 13. Updated by : 황정택, 와이즈스톤.
 */
public class UpdateUserMbrSegmentRequest extends CommonInfo implements Serializable {
	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/** 유통망 추천앱 회원 정보 Value Object. */
	private UserMbrSegment userMbrSegment;

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
	 * 유통망 추천앱 회원 정보 Value Object를 리턴한다.
	 * 
	 * @return userMbrSegment - 유통망 추천앱 회원 정보 Value Object
	 */
	public UserMbrSegment getUserMbrSegment() {
		return this.userMbrSegment;
	}

	/**
	 * 유통망 추천앱 회원 정보 Value Object를 설정한다.
	 * 
	 * @param userMbrSegment
	 *            유통망 추천앱 회원 정보 Value Object
	 */
	public void setUserMbrSegment(UserMbrSegment userMbrSegment) {
		this.userMbrSegment = userMbrSegment;
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
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Utils.printKeyValues(this);
	}
}
