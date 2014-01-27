package com.skplanet.storeplatform.sac.member.common.idp.constants;

public final class ImIDPConstants {

	public static final String IDP_CHECK_SERVER_DEV = "OMP10000";
	public static final String IDP_CHECK_SERVER_STAG = "OMP10000";
	public static final String IDP_CHECK_SERVER_REAL = "OMPCBT01";

	public static final String IDP_PARAM_OPERATION_MODE_DEV = "dev";
	public static final String IDP_PARAM_OPERATION_MODE_TEST = "test";
	public static final String IDP_PARAM_OPERATION_MODE_STAG = "staging";
	public static final String IDP_PARAM_OPERATION_MODE_REAL = "real";

	/** API URL. */
	public static final String IDP_REQ_URL_JOIN = "/web/IMJoin.api";
	public static final String IDP_REQ_URL_SECEDE = "/web/IMSecede.api";
	public static final String IDP_REQ_URL_USER_INFO_SEARCH = "/web/IMSearch.api";
	public static final String IDP_REQ_URL_USER_INFO_MODIFY = "/web/IMModify.api";
	public static final String IDP_REQ_URL_USER_AUTH = "/web/IMAuth.api";
	public static final String IDP_REQ_URL_IM_CHECK = "/web/IMHealthCheck.api";
	public static final String IDP_REQ_URL_ID_VALID = "/web/IMSetValid.api";
	public static final String IDP_REQ_URL_IMID_CHECK = "/web/IntegrationAuth.api";
	public static final String IDP_REQ_URL_MOBILE_AUTH = "/web/IMMobileAuth.api";

	/** API(TX) COMMAND. */
	/** 2.1. IDP 정책 설계 API(TX). */
	public static final String IDP_REQ_CMD_AGREE_USER = "TXAgreeUserIDP"; // 서비스 이용동의
	// 개별약관 해지
	public static final String IDP_REQ_CMD_DISAGREE_USER = "TXDisagreeUserIDP";
	// TODO 공통프로파일조회요청 (For Server)
	public static final String IDP_REQ_CMD_FIND_COMMON_PROFILE_FOR_SERVER = "TXGetUserInfoForServerIDP";
	// TODO 공통프로파일 수정
	public static final String IDP_REQ_CMD_MODIFY_PROFILE = "TXUpdateUserInfoIDP";
	// 부가프로파일 수정
	public static final String IDP_REQ_CMD_MODIFY_ADDITIONAL = "TXUpdateAdditionalUserInfoIDP";
	// TODO 비밀번호 변경
	public static final String IDP_REQ_CMD_MODIFY_PWD = "TXUpdateUserPwdIDP";
	// TODO 인증 E-mail 재발송요청
	// public static final String IDP_REQ_CMD_CONFIRM_EMAIL_RESEND = "TXResendConfirmEmailIDP";
	// TXGetCountryCodeIDP ?? 국가 정보 조회 요청
	// TODO 로그인 상태 정보 변경
	public static final String IDP_REQ_CMD_SET_LOGIN_STATUS = "TXSetLoginConditionIDP";
	// TXSetFlagLocalUserIdIDP ID 전환불가 설정/해제 요청
	// TODO 실명변경 Y : 해지 가능, N : 해지 불가) 요청
	public static final String IDP_REQ_CMD_UPDATE_USER_NAME = "TXUpdateUserNameIDP";
	// TODO 법정대리인 동의정보 변경 요청
	public static final String IDP_REQ_CMD_UPDATE_GUARDIAN = "TXUpdateGuardianInfoIDP";
	// TXRequestRetryIDP RX 배포 Retry 요청
	// TXGetSerivceInfoIDP ???!!!

	/** 2.2. SP 요구사항 API. */
	// TODO
	// duplicateMDNCheckIDP MDN 중복체크
	// minToMdnIDP MDN 조회 (SKT, non-SKT 구분)
	// 통합 ID 회원로그인
	public static final String IDP_REQ_CMD_AUTH_FOR_ID = "authForIdIDP";
	// 통합 ID 서비스가입리스트조회
	public static final String IDP_REQ_CMD_FIND_JOIN_SERVICE_LIST = "findJoinServiceListIDP";
	// MDN 정보 조회 (SKT 가입자)
	public static final String IDP_REQ_CMD_GET_MDN_INFO_IDP = "getMdnInfoIDP";// IMDN 정보 조회 (SKT 가입자)
	// TODO IM통합회원 ID 찾기
	public static final String IDP_REQ_CMD_FIND_USERID_BY_MDN = "findUserIdByMdn";
	// chkExistAgreeUserIDP 이용동의 가능여부 조회
	// 기본 프로파일 조회(for Server)
	public static final String IDP_REQ_CMD_FIND_COMMON_IDP_PROFILE_FOR_SERVER = "findCommonProfileForServerIDP";// 기본프로파일조회(for
	// joinIDCheckChangedIDP ID 가입여부 체크
	// ID 가입여부 체크
	public static final String IDP_REQ_CMD_ID_STATUS_IDP_IM = "joinIDCheckIDP";

	/** 2.3. IDP 정책 설계 API(RX). */
	// public static final String IDP_REQ_RX_CMD_CREATE_USER = "RXCreateUserIDP";
	// public static final String IDP_REQ_RX_CMD_CREATE_USERID = "RXCreateUserIdIDP";
	// public static final String IDP_REQ_RX_CMD_ACTIVE_USERID = "RXActivateUserIdIDP";
	// public static final String IDP_REQ_RX_CMD_DELETE_USERID = "RXDeleteUserIdIDP";
	// public static final String IDP_REQ_RX_CMD_CHECK_DELETE_USERID = "RXPreCheckDeleteUserIDP";
	// public static final String IDP_REQ_RX_CMD_UPDATE_DISAGREE_USERID = "RXUpdateDisagreeUserIDP";
	// public static final String IDP_REQ_RX_CMD_UPDATE_USERINFO = "RXUpdateUserInfoIDP";
	// public static final String IDP_REQ_RX_CMD_INVALID_TEL_NO = "RXInvalidUserTelNoIDP";
	// public static final String IDP_REQ_RX_CMD_LOGIN_CONDITION = "RXSetLoginConditionIDP";

	/** API RESP_TYPE. */
	public static final String IDP_PARAM_RESP_TYPE_NAMEVALUE = "1"; // default
	public static final String IDP_PARAM_RESP_TYPE_XML = "2"; // xml

	/** API RESP_FLOW_TYPE. */
	public static final String IDP_PARAM_RESP_FLOW_RESPONSE = "resp"; // default
	public static final String IDP_PARAM_RESP_FLOW_REDIRECT = "redt";

	public static final String IDP_PARAM_KEY_TYPE_IM_SERVICE_NO = "1";
	public static final String IDP_PARAM_KEY_TYPE_IM_ID = "2";

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

	public static final String IDP_RES_CODE_USER_STOP = "2025E073";// 계정 잠금 상태
	public static final String IDP_RES_CODE_USER_NOT_CHANGE = "2025X000";// 변경 가능한 회원상태가 아닙

	/** 이미 서비스에 등록한 사용자 (2100). */
	public static final String IDP_RES_CODE_ALREADY_JOIN = "2100X000";

	/** 직권중지 상태 (2403). */
	public static final String IDP_RES_CODE_SUSPEND = "2403X000";

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
	/** 로그인 제한 상태 (2222) */
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
}
