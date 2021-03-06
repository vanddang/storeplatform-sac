package com.skplanet.storeplatform.sac.member.common.constant;

/**
 * Calss 설명
 * 
 * Updated on : 2014. 1. 13. Updated by : 김경복, 부르칸.
 */
public final class IdpConstants {

	// 결과 코드
	/** 요청 수행 성공 (1000). */
	public static final String IDP_RES_CODE_OK = "1000";
	/** 결과 값 없음 (1001). */
	public static final String IDP_RES_CODE_NO_CONTENTS = "1001";
	/** 중복 요청으로 인한 서비스 이용불가 (1003). */
	public static final String IDP_RES_CODE_TIME_REQUEST = "1003";
	/** 정의되지 않은 요청 (2000). */
	public static final String IDP_RES_CODE_BAD_REQUEST = "2000";
	/** 잘못된 요청 데이터 (2001). */
	public static final String IDP_RES_CODE_BAD_REQUEST_DATA = "2001";
	/** 필수 요청 parameter 부족 (2002). */
	public static final String IDP_RES_CODE_REQUEST_DATA_DEFICIENCY = "2002";
	/** 데이터 제약 사항 오류 (2003). */
	public static final String IDP_RES_CODE_DATA_LENGTH_ERROR = "2003";
	/** SP 키 인증 오류 (2004). */
	public static final String IDP_RES_CODE_AUTH_KEY_FAIL = "2004";
	/** 등록되어 있지 않은 업체 (2005). */
	public static final String IDP_RES_CODE_UNAUTHORIZED_PVD = "2005";
	/** 등록되어 있지 않은 IP로 접근 (2006). */
	public static final String IDP_RES_CODE_UNAUTHORIZED_IP = "2006";
	/** 등록되어 있지 않은 사용자 (2007). */
	public static final String IDP_RES_CODE_UNAUTHORIZED = "2007";
	/** 변경 요청된 상태값과 현재의 상태값이 같습니다. (2031). */
	public static final String IDP_RES_CODE_STATUS_ALREAY_APPLY = "2031";
	/** 이미 서비스에 등록한 사용자 (2100). */
	public static final String IDP_RES_CODE_ALREADY_JOIN = "2100";
	/** 가 가입 상태인 사용자 (2101). */
	public static final String IDP_RES_CODE_WAIT_JOIN = "2101";
	/** 비밀번호 길이 제약 (2102). */
	public static final String IDP_RES_CODE_PASSWORD_LENGTH_ERROR = "2102";
	/** 비밀번호와 ID가 같음 (2103). */
	public static final String IDP_RES_CODE_PASSWORD_ID_ERROR = "2103";
	/** 비밀번호가 숫자로만 구성됨 (2104). */
	public static final String IDP_RES_CODE_PASSWORD_ONLY_NUMBER_ERROR = "2104";
	/** 비밀번호가 문자로만 구성됨 (2105). */
	public static final String IDP_RES_CODE_PASSWORD_ONLY_CHAR_ERROR = "2105";
	/** 비밀번호가 특수문자로만 구성됨 (2106). */
	public static final String IDP_RES_CODE_PASSWORD_ONLY_SPECIAL_CHAR_ERROR = "2106";
	/** 존재하지 않는 ID (2200). */
	public static final String IDP_RES_CODE_NOT_EXIST_ID = "2200";
	/** 비밀번호 불일치 (2201). */
	public static final String IDP_RES_CODE_WRONG_PASSWD = "2201";
	/** 휴대폰 인증 코드 불일치 (2202). */
	public static final String IDP_RES_CODE_MOBILE_AUTH_FAIL = "2202";
	/** 유무선 가입자로 무선전용 로그인 불가 (2203). */
	public static final String IDP_RES_CODE_MDN_AUTH_JOINALL = "2203";
	/** 무선 가입 상태 아님 (2204). */
	public static final String IDP_RES_CODE_MDN_AUTH_NOT_WIRELESS_JOIN = "2204";
	/** 실명 확인 실패 (2205). */
	public static final String IDP_RES_CODE_REALNAME_AUTH_FAIL = "2205";
	/** 실명 확인 불가 (2206). */
	public static final String IDP_RES_CODE_REALNAME_AUTH_ERROR = "2206";
	/** i-topping ID 인증 실패 (2207). */
	public static final String IDP_RES_CODE_NATEID_AUTH_FAIL = "2207";
	/** 워터마트 인증 실패 (2208). */
	public static final String IDP_RES_CODE_WATERMARK_AUTH_FAIL = "2208";
	/** 가입신청 상태이므로 로그인 실패 (2215). */
	public static final String IDP_RES_CODE_INVALID_USER_INFO = "2215";
	/** 무선 서비스에 가입되지 않은 MDN (2900). */
	public static final String IDP_RES_CODE_NOT_EXIST_MDN = "2900";
	/** 한신정 연동 실패 (4000). */
	public static final String IDP_RES_CODE_NICE_LINKAGE_FAIL = "4000";
	/** ICAS 연동 오류 (4100). */
	public static final String IDP_RES_CODE_ICAS_ERROR = "4100";
	/** UAPS 연동 오류 (4200). */
	public static final String IDP_RES_CODE_UAPS_ERROR = "4200";
	/** UAPS 연동 간 SKT 사용자 인증 실패 (4201). */
	public static final String IDP_RES_CODE_UAPS_NOT_SKT_USER = "4201";
	/** VSMS 연동 오류 (4300). */
	public static final String IDP_RES_CODE_VSMSC_ERROR = "4300";
	/** Nate 인증 연동 실패 (4500). */
	public static final String IDP_RES_CODE_NATE_LINKAGE_FAIL = "4500";
	/** 서버 or 로직 처리 오류 (Exception) (5000). */
	public static final String IDP_RES_CODE_INTERNAL_SERVER_ERROR = "5000";
	/** XML parsing 오류 (5001). */
	public static final String IDP_RES_CODE_XML_PARSING_ERROR = "5001";
	/** DB access 오류 (5002). */
	public static final String IDP_RES_CODE_DB_ERROR = "5002";
	/** 요청 수행 실패 (기타 오류) (5500). */
	public static final String IDP_RES_CODE_FAIL = "5500";
}
