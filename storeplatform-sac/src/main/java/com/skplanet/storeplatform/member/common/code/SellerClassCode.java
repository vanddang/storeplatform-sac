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
 * 판매자 유형 코드 정의 Class
 * 
 * Created on : 2014. 1. 2 Created by : wisestone_brian, wisestone
 */
public enum SellerClassCode {

	/** 판매자 구분 코드 - 개인판매자. */
	PRIVATE_SELLER,
	/** 판매자 구분 코드 - 개인사업자. */
	PRIVATE_BUSINESS_SELLER,
	/** 판매자 구분 코드 - 법인사업자. */
	CORPORATION_SELLER

	;

	/** 정의된 코드 아이디. */
	private final String codeID;

	/** 정의된 그룹코드 아이디. */
	private final String groupID = GroupCode.SELLER_CLASS.getCode();

	/** 정의된 코드 순번. */
	private final int index;

	/**
	 * Instantiates a new main state code.
	 * 
	 */
	SellerClassCode() {
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
