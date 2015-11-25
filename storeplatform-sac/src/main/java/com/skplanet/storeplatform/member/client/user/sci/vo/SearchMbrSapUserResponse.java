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
import java.util.Map;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.vo.CommonResponse;

/**
 * 복수 Sap사용자 상태정보 조회 응답 Value Object.
 * 
 * Updated on : 2015. 4. 13. Updated by : 반범진.
 */
public class SearchMbrSapUserResponse extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonResponse commonResponse;

	/**
	 * <pre>
	 * 사용자 상태정보 Value Object Map.
	 * key : userKey
	 * </pre>
	 */
	private Map<String, UserMbrStatus> userMbrStatusMap;

	/**
	 * 사용자 상태정보 Value Object Map을 리턴한다.
	 * 
	 * @return userMbrStatusMap - 사용자 상태정보 Value Object Map
	 */
	public Map<String, UserMbrStatus> getUserMbrStatusMap() {
		return this.userMbrStatusMap;
	}

	/**
	 * 사용자 상태정보 Value Object Map을 설정한다.
	 * 
	 * @param userMbrStatusMap
	 *            사용자 상태정보 Value Object Map
	 */
	public void setUserMbrStatusMap(Map<String, UserMbrStatus> userMbrStatusMap) {
		this.userMbrStatusMap = userMbrStatusMap;
	}

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

}
