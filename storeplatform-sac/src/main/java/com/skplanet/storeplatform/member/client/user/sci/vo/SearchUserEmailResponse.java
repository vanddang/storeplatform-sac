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
import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.vo.CommonResponse;

/**
 * 이메일 사용자 정보 조회 응답 Value Object
 * 
 * Updated on : 2014. 2. 19 Updated by : wisestone_dinga, wisestone
 */
public class SearchUserEmailResponse extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonResponse commonResponse;

	/** 사용자 정보 Value Object List. */
	private List<UserMbr> userMbrList;

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
	 * 사용자 정보 Value Object List를 리턴한다.
	 * 
	 * @return userMbrList - 사용자 정보 Value Object List
	 */
	public List<UserMbr> getUserMbrList() {
		return this.userMbrList;
	}

	/**
	 * 사용자 정보 Value Object List를 설정한다.
	 * 
	 * @param userMbrList
	 *            사용자 정보 Value Object List
	 */
	public void setUserMbr(List<UserMbr> userMbrList) {
		this.userMbrList = userMbrList;
	}

}
