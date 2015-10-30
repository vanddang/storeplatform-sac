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
 * 판매자 전환 코드 정의 Class
 * 
 * Updated on : 2014. 1. 10 Updated by : wisestone_brian, wisestone
 */
public enum SellerChangeCode {

	/** 판매자 전환 코드 - 유료전환(무료개발자에서 유료개인개발자로 전환). */
	CHANGE_FREE_TO_PAY,
	/** 판매자 전환 코드 - 개인사업자가입(무료개발자에서 유료개인사업자로 전환). */
	CHANGE_FRRE_TO_BUSINESS,
	/** 판매자 전환 코드 - 법인사업자가입(무료개발자에서 유료법인사업자로 전환). */
	CHANGE_FREE_TO_CORP,
	/** 판매자 전환 코드 - 개인사업자전환(유료개인개발자에서 유료개인사업자로 전환). */
	CHANGE_PAY_TO_BUSINESS,
	/** 판매자 전환 코드 - 법인사업자전환(유료개인개발자에서 유료법인사업자로 전환). */
	CHANGE_PAY_TO_CORP,
	/** 판매자 전환 코드 - 사업자간전환(유료개인사업자에서 유료법인사업자로 전환). */
	CHANGE_BUSINESS_TO_CORP,
	/** 판매자 전환 코드 - 해외개발자전환(해외개발자 정산정보 입력 전환). */
	CHANGE_OVERSEA_TO_PAY,

	;

	/** 정의된 코드 아이디. */
	private final String codeID;

	/** 정의된 그룹코드 아이디. */
	private final String groupID = GroupCode.SELLER_CHANGE.getCode();

	/** 정의된 코드 순번. */
	private final int index;

	/**
	 * Instantiates a new main state code.
	 * 
	 */
	SellerChangeCode() {
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
