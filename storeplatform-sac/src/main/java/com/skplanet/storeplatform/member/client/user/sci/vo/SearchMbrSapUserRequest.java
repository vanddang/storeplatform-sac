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
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.SearchMbrSapUserInfo;

/**
 * 복수 Sap사용자 상태정보 조회 요청 Value Object.
 * 
 * Updated on : 2015. 4. 13. Updated by : 반범진.
 */
public class SearchMbrSapUserRequest extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/** 사용자 Key List. */
	private List<SearchMbrSapUserInfo> userKeyList;

	/**
	 * 사용자 Key List를 리턴한다.
	 * 
	 * @return userKeyList - 사용자 Key List
	 */
	public List<SearchMbrSapUserInfo> getUserKeyList() {
		return this.userKeyList;
	}

	/**
	 * 사용자 Key List를 설정한다.
	 * 
	 * @param userKeyList
	 *            사용자 Key List
	 */
	public void setUserKeyList(List<SearchMbrSapUserInfo> userKeyList) {
		this.userKeyList = userKeyList;
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

}
