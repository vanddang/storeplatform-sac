/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.idp.constant;

/**
 * IdpConstants class
 * 
 * Updated on : 2014. 1. 8. Updated by : Lee, Jung suk, incross.
 */
public final class IdpConstants {

	public static final String IDP_MOBILE_CHANGE = "US002101"; // 기기변경
	public static final String IDP_NUMBER_CHANGE = "US002103"; // 번호변경
	public static final String IDP_MOBILE_DISCARD = "US002104"; // 단말해지 or 명의변경
	public static final String IDP_ACCOUNT_ACCEPT = "US002105"; // 가입승인
	public static final String IDP_EMAIL_CHANGE = "US002106"; // 이메일변경승인

	public static final String IDP_EMAIL_AUTH_COMPLETE = "US000503"; // 이메일승인완료
	public static final String IDP_EMAIL_AUTH_NOT_COMPLETE = "US000502"; // 가가입

	public static final String IDP_MEMBER_PROFILE_MODIFY = "US001001"; // 회원 정보 수정
	public static final String IDP_ECG_JOINED_TSTORE = "US003001"; // 부가서비스가입
	public static final String IDP_ECG_SCEDED_TSTORE = "US003002"; // 부가서비스해지
	public static final String IDP_ECG_JOINED_TSTORE_LTE = "US003003"; // 부가서비스가입 LTE정액 요금제
	public static final String IDP_ECG_SCEDED_TSTORE_LTE = "US003004"; // 부가서비스해지 LTE정액 요금제

	public static final String IM_IDP_RESPONSE_SUCCESS_CODE = "100";
	public static final String IM_IDP_RESPONSE_FAIL_CODE = "109";
	public static final String IM_IDP_RESPONSE_SUCCESS_CODE_TEXT = "SP 프로비저닝 성공";
	public static final String IM_IDP_RESPONSE_FAIL_CODE_TEXT = "SP 프로비저닝 실패";

	public static final String IDP_RESPONSE_SUCCESS_CODE = "100"; // SP 프로비져닝 성공
	public static final String IDP_RESPONSE_FAIL_CODE = "109"; // SP 프로비져닝 실패(FAIL응답 받음)
	public static final String IDP_RESPONSE_NO_DATA = "600"; // SP 프로비져닝 실패(데이터 없음)

	public static final String IM_IDP_RESPONSE_FAIL_MEMBERSELECT_CODE = "9999N005";
	public static final String IM_IDP_RESPONSE_FAIL_MEMBERSELECT_CODE_TEXT = "은 존재 하지 않는 회원 입니다.";

	public static final String SUS_STATUS_RELEASE = "80"; // 직권 중지 해제
	public static final String SUS_STATUS_LOCK = "90"; // 직권 중지

	public static final String LOGIN_STATUS_RELEASE = "10"; // 로그인 가능
	public static final String LOGIN_STATUS_LOCK = "20"; // 로그인 제한

	/**
	 * DCD 연동 코드
	 * */
	public static final String DCD_ENTRY_JOIN = "4097"; // 가입
	public static final String DCD_ENTRY_SECEDE = "4099"; // 해지
	public static final String DCD_ENTRY_CHANGE_NUMBER = "4105"; // 번호변경
	public static final String DCD_ENTRY_CHANGE_MODEL = "4109"; // 기기변경

	/**
	 * 상품 DCD 연동 지원여부 코드
	 * */
	public static final String DCD_SUPPORT_NO = "PD005200"; // DCD 미지원
	public static final String DCD_SUPPORT_YES = "PD005201"; // DCD 지원

}
