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

import com.skplanet.storeplatform.member.client.common.constant.Constant;

/**
 * 회원 그룹 코드 정의 Class
 * 
 * Created on : 2014. 1. 2 Created by : wisestone_brian, wisestone
 */
public enum GroupCode {

	/** 판매자 전환코드 - US0015. */
	SELLER_CHANGE(15),
	/** 판매자 구분코드 - US0101. */
	SELLER_CLASS(101),
	/** 회원메인상태 코드 - US0102. */
	MAIN_STATE(102),
	/** 회원서브상태 코드 - US0103. */
	SUB_STATE(103),
	/** 회원탈퇴사유 코드 - US0104. */
	WITHDRAW_REASON(104),
	/** 결재수단 코드 - US0105. */
	PAYMENT_METHOD(105),
	/** 약관동의 코드 - US0106. */
	POLICY_AGREEMENT(106),
	/** 사용자 탈퇴 유형 코드 - US0107. */
	USER_WITHDRAW_CLASS(107),
	/** 회원구분 코드 - US0108. */
	MEMBER_TYPE(108),
	/** 회원 부가속성 코드 - US0109. */
	MEMBER_MANAGEMENT(109),
	/** 판매자회원 노출화면 코드 - US0110. */
	SELLER_DISPLAY(110),
	/** 실명인증수단 코드 - US0111. */
	REALNAME_VERIFY(111),
	/** 인증요청 채널 - US0112. */
	VERIFY_CHANNEL(112),
	/** 판매자 유/무료 분류 코드 - US0113. */
	SELLER_CATEGORY(113),
	/** 기기 부가속성 코드 - US0114. */
	DEVICE_MANAGEMENT(114),
	/** 사용자 구분 코드 - US0115. */
	USER_TYPE(115),
	/** 기능제한 및 사용자 정책 코드 - US0117. */
	MEMBER_RESTRICT(117),

	/** 배치연동 처리상태 코드 - US0119. */
	BATCH_PROCESS(119),

	/** 기기변경 유형코드 - US0120. */
	DEVICE_CHANGE(120),

	;

	/** 정의된 코드 아이디. */
	private final String codeID;

	/** 회원 코드. */
	private final String codePrefix = Constant.CODE_PREFIX;

	/** 정의된 코드 순번. */
	private final int index;

	/**
	 * Instantiates a new main state code.
	 * 
	 * @param ordinal
	 *            the ordinal code
	 */
	GroupCode(int ordinal) {
		this.index = ordinal;
		this.codeID = this.codePrefix + String.format("%04d", this.index);
	}

	/**
	 * Instantiates a new main state code.
	 * 
	 */
	GroupCode() {
		this.index = this.ordinal();
		this.codeID = this.codePrefix + String.format("%04d", this.index);
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
