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
 * 회원서브상태 코드 정의 Class
 * 
 * Created on : 2014. 1. 2 Created by : wisestone_brian, wisestone
 */
public enum SubStateCode {

	/** 회원서브상태 - 정상 US010301. */
	NORMAL,
	/** 회원서브상태 - 탈퇴신청 US010302 . */
	WITHDRAW_REQUEST,
	/** 회원서브상태 - 탈퇴완료 US010303. */
	WITHDRAW,
	/** 회원서브상태 - 가입승인만료 US010304. */
	JOIN_AGREE_EXPIRED,
	/** 회원서브상태 - 정산정보승인 대기 US010305 . */
	ACCT_AGREE_STANDBY,
	/** 회원서브상태 - 이메일변경 승인대기 US010306. */
	EMAIL_CHANGE_AGREE_STANDBY,
	/*
	 * /** 회원서브상태 - 로그인 제한 <- 사용안함. LOGIN_RESTRICT,
	 */
	/** 회원서브상태 - 계정잠금 US010307. */
	ACCOUNT_LOCKED,
	/*
	 * /** 회원서브상태 - 직권중지 <- 사용안함. FORCING_SUSPENDED,
	 */
	/** 회원서브상태 - 7일간 이용정지 US010308. */
	SEVEN_DAYS_SUSPENDED,
	/** 회원서브상태 - 30일 이용정지 US010309. */
	THIRTY_DAYS_SUSPENDED,
	/** 회원서브상태 - 영구 이용정지 US010310. */
	PERMANENTLY_SUSPENDED,
	/** 회원서브상태 - 전환신청 US010311. */
	CHANGE_REQUEST,
	/** 회원서브상태 - 전환재신청 US010312. */
	CHANGE_REAPPLY,
	/** 회원서브상태 - 전환거절 US010313. */
	CHANGE_CANCEL,

	/** 회원서브상태 - 변동처리대상 US010314. */
	CHANGE_MANAGEMENT,
	/** 회원서브상태 - 가입거절 US010315. */
	JOIN_REJECTED,
	/** 회원서브상태 - 탈퇴거절 US010316. */
	WITHDRAW_CANCELED,
	/** 회원서브상태 - ID재사용 US010317. */
	REUSE_MEMBER_ID,
	/** 회원서브상태 - 가입재신청 US010318. */
	JOIN_REJOIN,
	/** 회원서브상태 - 준회원 US010319. */
	MEMBER_FREE, ;

	/** 정의된 코드 아이디. */
	private final String codeID;

	/** 정의된 그룹코드 아이디. */
	private final String groupID = GroupCode.SUB_STATE.getCode();

	/** 정의된 코드 순번. */
	private final int index;

	/**
	 * Instantiates a new main state code.
	 * 
	 */
	SubStateCode() {
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
