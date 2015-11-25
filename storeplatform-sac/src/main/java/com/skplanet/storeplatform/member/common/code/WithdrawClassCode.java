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
 * 회원탈퇴유형 코드 정의 Class
 * 
 * Created on : 2014. 1. 2 Created by : wisestone_brian, wisestone
 */
public enum WithdrawClassCode {

	/** 탈퇴유형 - ID 전환. */
	CHANGE_ID,
	/** 탈퇴유형 - 사용자선택. */
	USER_SELECTED,
	/** 탈퇴유형 - 프로비저닝. */
	PROVISIONING,
	/** 탈퇴유형 - 단말이동. */
	USER_DEVICE,
	/** 탈퇴유형 - 가입승인만료. */
	JOIN_AGREE_EXPIRED,

	;

	/** 정의된 코드 아이디. */
	private final String codeID;

	/** 정의된 그룹코드 아이디. */
	private final String groupID = GroupCode.USER_WITHDRAW_CLASS.getCode();

	/** 정의된 코드 순번. */
	private final int index;

	/**
	 * Instantiates a new main state code.
	 * 
	 */
	WithdrawClassCode() {
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
