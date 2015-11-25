/*
 * Copyright (c) 2014 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.common.code;

/**
 * 사용자 구분 코드 정의 Class
 * 
 * Created on : 2014. 1. 2 Created by : wisestone_brian, wisestone
 */
public enum UserTypeCode {

	/** 사용자 구분 코드 - 모바일회원. */
	MOBILE_USER,
	/** 사용자 구분 코드 - IDP 회원. */
	IDP_USER,
	/** 사용자 구분 코드 - OneID 회원. */
	ONEID_USER,

	;

	/** 정의된 코드 아이디. */
	private final String codeID;

	/** 정의된 그룹코드 아이디. */
	private final String groupID = GroupCode.USER_TYPE.getCode();

	/** 정의된 코드 순번. */
	private final int index;

	/**
	 * Instantiates a new main state code.
	 * 
	 */
	UserTypeCode() {
		this.index = this.ordinal() + 1;
		this.codeID = this.groupID + String.format("%02d", this.index);
	}

	/**
	 * Gets the code.
	 * 
	 * @return the code
	 */
	public String getCode() {
		return this.codeID;
	}

}
