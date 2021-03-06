package com.skplanet.storeplatform.sac.member.common.constant;

/**
 * Calss 설명
 * 
 * Updated on : 2014. 2. 25. Updated by : Rejoice, Burkhan
 */
public final class ImIdpConstants {

	/** 요청 수행 성공 (1000). */
	public static final String IDP_RES_CODE_OK = "1000X000";
	/** 결과 값 없음 (1001). */
	public static final String IDP_RES_CODE_NO_CONTENTS = "1001X000";

	public static final String IDP_RES_CODE_SYSTEM_ERROR = "1002X000";

	public static final String IDP_RES_CODE_SYSTEM_CHECK = "1002E100";

	/** 중복 요청으로 인한 서비스 이용불가 (1003). */
	public static final String IDP_RES_CODE_TIME_REQUEST = "1003X000";

	public static final String IDP_RES_CODE_OPERATION_MODE_CHECK = "1005N001";

	public static final String IDP_RES_CODE_OPERATION_MODE_CHECK_B = "1005X000";

	/** 정의되지 않은 요청 (2000). */
	public static final String IDP_RES_CODE_BAD_REQUEST = "2000X000";

	public static final String IDP_RES_CODE_BAD_API = "2000N002";

	/** 잘못된 요청 데이터 (2001). */
	public static final String IDP_RES_CODE_BAD_REQUEST_DATA = "2001X000";

	public static final String IDP_RES_CODE_DATA_FORMAT = "2001E017";

	/** 필수 요청 parameter 부족 (2002). */
	public static final String IDP_RES_CODE_REQUEST_DATA_DEFICIENCY = "2002X000";

	public static final String IDP_RES_CODE_REQUEST_DATA_ERROR = "2002E057";

	/** 데이터 제약 사항 오류 (2003). */
	public static final String IDP_RES_CODE_DATA_LENGTH_ERROR = "2003X000";
	/** SP 키 인증 오류 (2004). */
	public static final String IDP_RES_CODE_AUTH_KEY_FAIL = "2004X000";
	/** 등록되어 있지 않은 업체 (2005). */
	public static final String IDP_RES_CODE_UNAUTHORIZED_PVD = "2005X000";

	public static final String IDP_RES_CODE_SERVICE_SITE_CODE_ERROR = "2005E034";

	/** 등록되어 있지 않은 IP로 접근 (2006). */
	public static final String IDP_RES_CODE_UNAUTHORIZED_IP = "2006X000";
	/** 등록되어 있지 않은 사용자 (2007). */
	public static final String IDP_RES_CODE_UNAUTHORIZED_USER = "2007X000";

	public static final String IDP_RES_CODE_USE_AGREE_ERROR = "2007E024";

	public static final String IDP_RES_CODE_SP_PROVISIONING_ERROR = "2008X000";

	public static final String IDP_RES_CODE_SP_PROVISIONING_NOT_APPROVED = "2009X000";

	public static final String IDP_RES_CODE_ONLY_MOBILE_PHONE = "2010X000";

	public static final String IDP_RES_CODE_NOT_SUPPORTED_PHONE = "2011X000";

	public static final String IDP_RES_CODE_SP_CONFIG_ERROR = "2012X000";

	public static final String IDP_RES_CODE_AUTH_SIGNATURE_FAIL = "2013X000";

	public static final String IDP_RES_CODE_CONFIG_TELECOM_FAIL = "2014X000";

	public static final String IDP_RES_CODE_CONFIG_MEMBER_FAIL = "2015X000";

	public static final String IDP_RES_CODE_UNAUTHORIZED_SERVICESITE_IP = "2016E070";

	public static final String IDP_RES_CODE_SP_IP_VERIFY_ERROR = "2016X000";

	public static final String IDP_RES_CODE_EMAIL_AUTH_EXCEED = "2017X000";

	public static final String IDP_RES_CODE_XSS_SQL_INJECTION = "2018X000";

	public static final String IDP_RES_CODE_USER_AUTH_KEY_ERROR = "2019X000";

	public static final String IDP_RES_CODE_REALNM_AUTH_FAIL = "2020X000";

	public static final String IDP_RES_CODE_PHONE_AUTH_FAIL = "2021X000";

	public static final String IDP_RES_CODE_PHONE_OWNER_AUTH_FAIL = "2022X000";

	public static final String IDP_RES_CODE_REALNM_DATA_ERROR = "2023X000";

	public static final String IDP_RES_CODE_PHONE_STATUS_HALT = "2024X000";

	public static final String IDP_RES_CODE_PROCESS_NOT_PERMISSION = "2026X000"; // 처리 권한이 없는 SP입니다

	public static final String IDP_RES_CODE_USER_STOP = "2025E073"; // 계정 잠금 상태
	public static final String IDP_RES_CODE_USER_NOT_CHANGE = "2025X000"; // 변경 가능한 회원상태가 아닙

	/** 변경 요청된 상태값과 현재의 상태값이 같습니다. (2031). */
	public static final String IDP_RES_CODE_STATUS_ALREAY_APPLY = "2031X000";

	/** 이미 서비스에 등록한 사용자 (2100). */
	public static final String IDP_RES_CODE_ALREADY_JOIN = "2100X000";

	/** 직권중지 상태 (2403). */
	public static final String IDP_RES_CODE_SUSPEND = "2403X000";
	/** 직권중지 상태 (2403). */
	public static final String IDP_RES_CODE_SUSPEND_02 = "2403N021";

	/** 가 가입 상태인 사용자 (2101). */
	public static final String IDP_RES_CODE_WAIT_JOIN = "2101X000";
	/** 비밀번호 길이 제약 (2102). */
	public static final String IDP_RES_CODE_PASSWORD_LENGTH_ERROR = "2102X000";
	/** 비밀번호와 ID가 같음 (2103). */
	public static final String IDP_RES_CODE_PASSWORD_ID_ERROR = "2103X000";
	/** 비밀번호가 숫자로만 구성됨 (2104). */
	public static final String IDP_RES_CODE_PASSWORD_ONLY_NUMBER_ERROR = "2104X000";
	/** 비밀번호가 문자로만 구성됨 (2105). */
	public static final String IDP_RES_CODE_PASSWORD_ONLY_CHAR_ERROR = "2105X000";
	/** 비밀번호가 특수문자로만 구성됨 (2106). */
	public static final String IDP_RES_CODE_PASSWORD_ONLY_SPECIAL_CHAR_ERROR = "2106X000";
	/** 존재하지 않는 ID (2200). */
	public static final String IDP_RES_CODE_NOT_EXIST_ID = "2200X000";
	/** 비밀번호 불일치 (2201). */
	public static final String IDP_RES_CODE_WRONG_PASSWD = "2201X000";
	/** 휴대폰 인증 코드 불일치 (2202). */
	public static final String IDP_RES_CODE_MOBILE_AUTH_FAIL = "2202X000";
	/** 유무선 가입자로 무선전용 로그인 불가 (2203). */
	public static final String IDP_RES_CODE_MDN_AUTH_JOINALL = "2203X000";
	/** 무선 가입 상태 아님 (2204). */
	public static final String IDP_RES_CODE_MDN_AUTH_NOT_WIRELESS_JOIN = "2204X000";
	/** 실명 확인 실패 (2205). */
	public static final String IDP_RES_CODE_REALNAME_AUTH_FAIL = "2205X000";
	/** 실명 확인 불가 (2206). */
	public static final String IDP_RES_CODE_REALNAME_AUTH_ERROR = "2206X000";
	/** 주민번호 구성 오류 (2207). */
	public static final String IDP_RES_CODE_REALNM_DATA_FAIL = "2207X000";
	/** 워터마트 인증 실패 (2208). */
	public static final String IDP_RES_CODE_USER_DATA_NOT_EXIST = "2208X000";
	/** 가입신청 상태이므로 로그인 실패 (2215). */
	public static final String IDP_RES_CODE_INVALID_USER_INFO = "2215X000";
	/** 로그인 제한 상태 (2222). */
	public static final String IDP_RES_CODE_LOGIN_RESTRICT = "2222X000";
	/** 한신정 연동 실패 (4000). */
	public static final String IDP_RES_CODE_NICE_LINKAGE_FAIL = "4000X000";
	/** ICAS 연동 오류 (4100). */
	public static final String IDP_RES_CODE_ICAS_ERROR = "4100X000";
	/** UAPS 연동 오류 (4200). */
	public static final String IDP_RES_CODE_UAPS_ERROR = "4200X000";
	/** UAPS 연동 간 SKT 사용자 인증 실패 (4201). */
	public static final String IDP_RES_CODE_UAPS_NOT_SKT_USER = "4201X000";
	/** VSMS 연동 오류 (4300). */
	public static final String IDP_RES_CODE_VSMSC_ERROR = "4300X000";
	/** Nate 인증 연동 실패 (4500). */
	public static final String IDP_RES_CODE_NATE_LINKAGE_FAIL = "4500X000";
	/** 서버 or 로직 처리 오류 (Exception) (5000). */
	public static final String IDP_RES_CODE_INTERNAL_SERVER_ERROR = "5000X000";
	/** XML parsing 오류 (5001). */
	public static final String IDP_RES_CODE_XML_PARSING_ERROR = "5001X000";
	/** DB access 오류 (5002). */
	public static final String IDP_RES_CODE_DB_ERROR = "5002X000";
	/** 요청 수행 실패 (기타 오류) (5500). */
	public static final String IDP_RES_CODE_FAIL = "5500X000";
	// 결과 값
	public static final String IDP_RES_EXIST = "1";
	public static final String IDP_RES_NO_EXIST = "0";

	/** 서비스사이트코드. */
	public static final String SSO_SST_CD_NATE_WEB = "10100";
	public static final String SSO_SST_CD_CYWORLD_WEB = "10200";
	public static final String SSO_SST_CD_11ST_WEB = "20100";
	public static final String SSO_SST_CD_MELON_WEB = "30100";
	public static final String SSO_SST_CD_TSTORE_WEB = "41100";
	public static final String SSO_SST_CD_TCLOUD_WEB = "41200";
	public static final String SSO_SST_CD_TMAP_WEB = "41300";
	public static final String SSO_SST_CD_IM_DEV_CENTER = "40100";
	public static final String SSO_SST_CD_CONTEXT_PORTAL = "40200";
	public static final String SSO_SST_CD_NOP = "40300";
	public static final String SSO_SST_CD_OCB_WEB = "42100";
	public static final String OCB_SST_CD = "40300";

	public static final String SSO_SST_NATE = "Nate";
	public static final String SSO_SST_CYWORLD = "Cyworld";
	public static final String SSO_SST_11ST = "11번가";
	public static final String SSO_SST_MELON = "멜론";
	public static final String SSO_SST_TSTORE = "Tstore";
	public static final String SSO_SST_TCLOUD = "Tcloud";
	public static final String SSO_SST_TMAP = "Tmap";
	public static final String SSO_SST_DEVCENTER = "통합개발자 센터";
	public static final String SSO_SST_C_PORTAL = "Context Portal";
	public static final String SSO_SST_NOP = "NOP";
}
