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
 * 회원메인상태 코드 정의 Class
 * 
 * Created on : 2014. 1. 2 Created by : wisestone_brian, wisestone
 */
public enum MainStateCode {

	/** 회원메인상태 - 정상. */
	NORMAL,
	/** 회원메인상태 - 탈퇴. */
	SECEDE,
	/** 회원메인상태 - 대기(가가입). */
	STANDBY,
	/** 회원메인상태 - 일시정지. */
	SUSPEND,
	/**
	 * 회원메인상태 - 전환 <- 사용하지 않음. CHANGE,
	 */

	;

	/** 정의된 코드 아이디. */
	private final String codeID;

	/** 정의된 그룹코드 아이디. */
	private final String groupID = GroupCode.MAIN_STATE.getCode();

	/** 정의된 코드 순번. */
	private final int index;

	/**
	 * Instantiates a new main state code.
	 * 
	 */
	MainStateCode() {
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
