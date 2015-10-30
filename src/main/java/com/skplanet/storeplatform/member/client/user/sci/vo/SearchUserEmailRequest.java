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
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;

/**
 * 이메일 사용자 정보 조회 요청 Value Object
 * 
 * Updated on : 2014. 2. 19 Updated by : wisestone_dinga, wisestone
 */
public class SearchUserEmailRequest extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/** 사용자 이메일 주소. */
	private String userEmail;

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
	 * 사용자 이메일 주소를 리턴한다.
	 * 
	 * @return userEmail - 사용자 이메일 주소
	 */
	public String getUserEmail() {
		return this.userEmail;
	}

	/**
	 * 사용자 이메일 주소를 설정한다.
	 * 
	 * @param userEmail
	 *            사용자 이메일 주소
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

}
