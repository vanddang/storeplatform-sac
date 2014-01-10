package com.skplanet.storeplatform.sac.member.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 사용자 공통 코드/상수 정의
 * 
 * Updated on : 2014. 1. 9. Updated by : 심대진, 다모아 솔루션.
 */
public enum MemberUserCode {

	/**
	 * 회원 분류 코드 : 모바일회원
	 */
	USER_TYPE_MOBILE("US011501"),
	/**
	 * 회원 분류 코드 : IDP회원
	 */
	USER_TYPE_IDP("US011502"),
	/**
	 * 회원 분류 코드 : OneID회원
	 */
	USER_TYPE_ONEID("US011503"),

	/**
	 * 회원 상태 메인 코드 : 모바일전용회원
	 */
	USER_MAIN_STATUS_1("US010201"),
	/**
	 * 회원 상태 메인 코드 : 가가입승인대기
	 */
	USER_MAIN_STATUS_2("US010202"),
	/**
	 * 회원 상태 메인 코드 : 정상
	 */
	USER_MAIN_STATUS_3("US010203"),
	/**
	 * 회원 상태 메인 코드 : 일시정지
	 */
	USER_MAIN_STATUS_4("US010204"),
	/**
	 * 회원 상태 메인 코드 : 자의탈퇴
	 */
	USER_MAIN_STATUS_5("US010205"),
	/**
	 * 회원 상태 메인 코드 : 이메일변경승인대기
	 */
	USER_MAIN_STATUS_6("US010206"),
	/**
	 * 회원 상태 메인 코드 : 가입승인 만료
	 */
	USER_MAIN_STATUS_7("US010207"),
	/**
	 * 회원 상태 메인 코드 : 직권탈퇴
	 */
	USER_MAIN_STATUS_8("US010208"),

	/**
	 * 회원 상태 서브 코드 : 신청
	 */
	USER_SUB_STATUS_1("US010301"),
	/**
	 * 회원 상태 서브 코드 : 완료
	 */
	USER_SUB_STATUS_2("US010302"),
	/**
	 * 회원 상태 서브 코드 : 거절
	 */
	USER_SUB_STATUS_3("US010303"),
	/**
	 * 회원 상태 서브 코드 : 전환신청
	 */
	USER_SUB_STATUS_4("US010304"),
	/**
	 * 회원 상태 서브 코드 : 전환완료
	 */
	USER_SUB_STATUS_5("US010305"),
	/**
	 * 회원 상태 서브 코드 : 전환거절
	 */
	USER_SUB_STATUS_6("US010306"),
	/**
	 * 회원 상태 서브 코드 : 탈퇴신청
	 */
	USER_SUB_STATUS_7("US010307"),
	/**
	 * 회원 상태 서브 코드 : 탈퇴완료
	 */
	USER_SUB_STATUS_8("US010308"),
	/**
	 * 회원 상태 서브 코드 : 재신청
	 */
	USER_SUB_STATUS_9("US010309"),
	/**
	 * 회원 상태 서브 코드 : 승인대기
	 */
	USER_SUB_STATUS_10("US010310"),
	/**
	 * 회원 상태 서브 코드 : 일시정지
	 */
	USER_SUB_STATUS_11("US010311"),

	;

	/**
	 * value
	 */
	private String value;

	private MemberUserCode(String value) {
		this.value = value;
	}

	/**
	 * 
	 * @return
	 */
	public String getValue() {
		return this.value;
	}

	private static final Map<String, MemberUserCode> map = new HashMap<String, MemberUserCode>();

	static {
		map.put(MemberUserCode.USER_TYPE_MOBILE.getValue(), MemberUserCode.USER_TYPE_MOBILE);
		map.put(MemberUserCode.USER_TYPE_IDP.getValue(), MemberUserCode.USER_TYPE_IDP);
		map.put(MemberUserCode.USER_TYPE_ONEID.getValue(), MemberUserCode.USER_TYPE_ONEID);
		map.put(MemberUserCode.USER_MAIN_STATUS_1.getValue(), MemberUserCode.USER_MAIN_STATUS_1);
		map.put(MemberUserCode.USER_MAIN_STATUS_2.getValue(), MemberUserCode.USER_MAIN_STATUS_2);
		map.put(MemberUserCode.USER_MAIN_STATUS_3.getValue(), MemberUserCode.USER_MAIN_STATUS_3);
		map.put(MemberUserCode.USER_MAIN_STATUS_4.getValue(), MemberUserCode.USER_MAIN_STATUS_4);
		map.put(MemberUserCode.USER_MAIN_STATUS_5.getValue(), MemberUserCode.USER_MAIN_STATUS_5);
		map.put(MemberUserCode.USER_MAIN_STATUS_6.getValue(), MemberUserCode.USER_MAIN_STATUS_6);
		map.put(MemberUserCode.USER_MAIN_STATUS_7.getValue(), MemberUserCode.USER_MAIN_STATUS_7);
		map.put(MemberUserCode.USER_MAIN_STATUS_8.getValue(), MemberUserCode.USER_MAIN_STATUS_8);
		map.put(MemberUserCode.USER_SUB_STATUS_1.getValue(), MemberUserCode.USER_SUB_STATUS_1);
		map.put(MemberUserCode.USER_SUB_STATUS_2.getValue(), MemberUserCode.USER_SUB_STATUS_2);
		map.put(MemberUserCode.USER_SUB_STATUS_3.getValue(), MemberUserCode.USER_SUB_STATUS_3);
		map.put(MemberUserCode.USER_SUB_STATUS_4.getValue(), MemberUserCode.USER_SUB_STATUS_4);
		map.put(MemberUserCode.USER_SUB_STATUS_5.getValue(), MemberUserCode.USER_SUB_STATUS_5);
		map.put(MemberUserCode.USER_SUB_STATUS_6.getValue(), MemberUserCode.USER_SUB_STATUS_6);
		map.put(MemberUserCode.USER_SUB_STATUS_7.getValue(), MemberUserCode.USER_SUB_STATUS_7);
		map.put(MemberUserCode.USER_SUB_STATUS_8.getValue(), MemberUserCode.USER_SUB_STATUS_8);
		map.put(MemberUserCode.USER_SUB_STATUS_9.getValue(), MemberUserCode.USER_SUB_STATUS_9);
		map.put(MemberUserCode.USER_SUB_STATUS_10.getValue(), MemberUserCode.USER_SUB_STATUS_10);
		map.put(MemberUserCode.USER_SUB_STATUS_11.getValue(), MemberUserCode.USER_SUB_STATUS_11);
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static MemberUserCode nameOf(String value) {
		return map.get(value);
	}

}
