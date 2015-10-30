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

/**
 * 복수 사용자 기기정보 조회 요청 Value Object
 * 
 * Updated on : 2014. 1. 6 Updated by : wisestone_brian, wisestone
 */
public class SearchMbrDeviceRequest extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/** 사용자 키, 휴대기기 키 List. */
	private List<UserDeviceKey> userDeviceKeyList;

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
	 * 사용자 키, 휴대기기 키 List를 리턴한다.
	 * 
	 * @return userDeviceKeyList - 사용자 키, 휴대기기 키 List
	 */
	public List<UserDeviceKey> getDeviceKeyList() {
		return this.userDeviceKeyList;
	}

	/**
	 * 사용자 키, 휴대기기 키 List를 리턴한다.
	 * 
	 * @param userDeviceKeyList
	 *            사용자 키, 휴대기기 키 List
	 */
	public void setDeviceKeyList(List<UserDeviceKey> userDeviceKeyList) {
		this.userDeviceKeyList = userDeviceKeyList;
	}

}
