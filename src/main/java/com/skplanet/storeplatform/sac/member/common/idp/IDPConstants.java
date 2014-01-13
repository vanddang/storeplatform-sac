///*
// * COPYRIGHT(c) SK telecom 2009
// * This software is the proprietary information of SK telecom.
// *
// * Revision History
// * Author       Date            Description
// * --------     ----------      ------------------
// * ?            ?               ?
// * nefer        2009.12.08      move to omp_common
// *
// */
//package com.skplanet.storeplatform.sac.member.common.idp;
//
//public interface IDPConstants {
//
//	/* IDP 요청 URL */
//	public static final String IDP_REQ_URL_JOIN = "/web/Join.api"; // omp.dev.idp.requrl.join
//	public static final String IDP_REQ_URL_REALNAME_AUTH = "/web/RealNameAuth.api"; // omp.dev.idp.requrl.realname.auth
//	public static final String IDP_REQ_URL_NATEID_AUTH = "/web/AuthNateID.api"; // omp.dev.idp.requrl.nate.auth
//	public static final String IDP_REQ_URL_MOBILE_AUTH = "/web/MobileAuth.api"; // omp.dev.idp.requrl.mobile.auth
//	public static final String IDP_REQ_URL_WATERMARK_AUTH = "/web/WatermarkAuth.api"; // omp.dev.idp.requrl.watermark.auth
//	public static final String IDP_REQ_URL_USER_AUTH = "/web/Auth.api"; // omp.dev.idp.requrl.user.auth
//	public static final String IDP_REQ_URL_USER_INFO_SEARCH = "/web/Search.api"; // omp.dev.idp.requrl.userinfo.search
//	public static final String IDP_REQ_URL_USER_INFO_MODIFY = "/web/Modify.api"; // omp.dev.idp.requrl.userinfo.modify
//	public static final String IDP_REQ_URL_SECEDE = "/web/Secede.api"; // omp.dev.idp.requrl.secede
//	public static final String IDP_REQ_URL_MOBILE_SEND = "/web/MobileAuth.api";
//	public static final String IDP_REQ_URL_CREATE_TOKEN = "/web/SpToken.api";
//	public static final String IDP_REQ_URL_MAPPING_TOKEN = "/web/TokenMapping.api";
//	public static final String IDP_REQ_URL_SEARCH_TOKEN = "/web/SpTokenCheck.api";
//	public static final String IDP_REQ_URL_DELETE_TOKEN = "/web/MappingDel.api";
//
//	/* IDP 요청 COMMAND */
//	public static final String IDP_REQ_CMD_ALEADY_JOIN_CHECK = "aleadyJoinCheck"; // omp.dev.idp.reqcmd.aleady.joincheck
//	public static final String IDP_REQ_CMD_DUPLICATE_ID_CHECK = "duplicateIDCheck"; // omp.dev.idp.reqcmd.duplicate.idcheck
//	public static final String IDP_REQ_CMD_REALNAME_AUTH = "realNameAuth"; // omp.dev.idp.reqcmd.realname.auth
//	public static final String IDP_REQ_CMD_NATEID_AUTH = "authNateID"; // omp.dev.idp.reqcmd.nateid.auth
//	public static final String IDP_REQ_CMD_MOBILE_AUTHCODE_SEND = "mobileAuthcodeSend"; // omp.dev.idp.reqcmd.mobile.authcode.send
//	public static final String IDP_REQ_CMD_MOBILE_AUTH = "mobileAuth"; // omp.dev.idp.reqcmd.mobile.auth
//	public static final String IDP_REQ_CMD_WATERMARK_AUTH_IMAGE = "watermarkAuthImage"; // omp.dev.idp.reqcmd.watermark.auth.image
//	public static final String IDP_REQ_CMD_WATERMARK_AUTH = "watermarkAuth"; // omp.dev.idp.reqcmd.watermark.auth
//	public static final String IDP_REQ_CMD_JOIN_FOR_EMAIL = "joinApplyForEmail"; // omp.dev.idp.reqcmd.join.email
//	public static final String IDP_REQ_CMD_JOIN_FOR_SN = "joinApplyForSN"; // omp.dev.idp.reqcmd.join.sn
//	public static final String IDP_REQ_CMD_JOIN_COMPLETE = "joinComplete"; // omp.dev.idp.reqcmd.join.complete
//	public static final String IDP_REQ_CMD_JOIN_REQEST = "join"; // omp.dev.idp.reqcmd.reqest
//	public static final String IDP_REQ_CMD_AUTH_FOR_ID = "authForId"; // omp.dev.idp.reqcmd.auth.id
//	public static final String IDP_REQ_CMD_AUTH_FOR_PWD = "authForPasswd"; // omp.dev.idp.reqcmd.auth.pwd
//	public static final String IDP_REQ_CMD_AUTH_FOR_MDN = "authForMDN"; // omp.dev.idp.reqcmd.auth.mdn
//	public static final String IDP_REQ_CMD_FIND_COMMON_PROFILE_FOR_BROWSER = "findCommonProfile"; // omp.dev.idp.reqcmd.find.commonprofile
//	public static final String IDP_REQ_CMD_FIND_COMMON_PROFILE_FOR_SERVER = "findCommonProfileForServer"; // omp.dev.idp.reqcmd.find.commonprofile.server
//	public static final String IDP_REQ_CMD_FIND_SPECIAL_PROFILE = "findSpecialProfile"; // omp.dev.idp.reqcmd.find.specialprofile
//	public static final String IDP_REQ_CMD_FIND_ID = "findID"; // omp.dev.idp.reqcmd.find.id
//	public static final String IDP_REQ_CMD_FIND_PWD = "findPasswd"; // omp.dev.idp.reqcmd.get.temppwd
//	public static final String IDP_REQ_CMD_MODIFY_AUTH_INFO = "modifyAuthInfo"; // omp.dev.idp.reqcmd.modify.authinfo
//	public static final String IDP_REQ_CMD_MODIFY_PROFILE = "modifyProfile"; // omp.dev.idp.reqcmd.modify.profile
//	public static final String IDP_REQ_CMD_SECEDE_USER = "secedeUser"; // omp.dev.idp.reqcmd.secede.user
//	public static final String IDP_REQ_CMD_AUTH_FOR_WAP = "authForWap";
//	public static final String IDP_REQ_CMD_SECEDE_FOR_WAP = "secedeForWap"; // omp.dev.idp.reqcmd.secede.wap
//	public static final String IDP_REQ_CMD_JOIN_FOR_WAP = "joinForWap";
//	public static final String IDP_REQ_CMD_ALEADY_JOIN_MDN = "aleadyJoinCheckForMdn";
//	public static final String IDP_REQ_CMD_AUTH_FOR_SSO = "authForSSO"; // omp.dev.idp.reqcmd.auth.sso
//	public static final String IDP_REQ_CMD_LOGOUT_FOR_SSO = "logoutForSSO"; // omp.dev.idp.reqcmd.logout.sso
//	public static final String IDP_REQ_CMD_GET_EMAIL_AUTH = "getEmailAuthLink";
//	public static final String IDP_REQ_CMD_MODIFY_EMAIL = "modifyUserEmail";
//	public static final String IDP_REQ_CMD_JOIN_SUP_SERVICE = "joinSupServiceRequest";
//	public static final String IDP_REQ_CMD_SECEDE_SUP_SERVICE = "secedeSupServiceRequest";
//	public static final String IDP_REQ_CMD_TMAP_SERVICE_AVALIBLE_CHECK = "tmapServiceAvalibleCheck";
//	public static final String IDP_REQ_CMD_TMAP_SERVICE_CHECK = "tmapServiceCheck";
//	public static final String IDP_REQ_CMD_FIND_MODEL_ID = "findModelId";
//	public static final String IDP_REQ_CMD_MOBILE_SEND = "mobileSend";
//	public static final String IDP_REQ_CMD_JOIN_SUP_SERVICE_REQUEST = "joinSupServiceRequest";// 부가서비스 가입
//	public static final String IDP_REQ_CMD_SECEDE_SUP_SERVICE_REQUEST = "secedeSupServiceRequest";// 부가서비스 해지
//	public static final String IDP_REQ_CMD_FIND_BILL = "findBill";// skt가입자 요금제 조회
//	public static final String IDP_REQ_CMD_CREATE_TOKEN = "spToken";
//	public static final String IDP_REQ_CMD_MAPPING_TOKEN = "tokenMapping";
//	public static final String IDP_REQ_CMD_SEARCH_TOKEN = "spTokenCheck";
//	public static final String IDP_REQ_CMD_DELETE_TOKEN = "mappingDel";
//
//	/*
//	 * IDP 통합 회원 고도화 추가
//	 */
//	public static final String IDP_REQ_CMD_OTHER_CHANNEL_ID_AUTH = "authIntegratedPasswd";
//	public static final String IDP_REQ_CMD_OTHER_CHANNEL_LIST = "findJoinServiceList";
//	public static final String IDP_REQ_CMD_OTHER_CHANNEL_REGIST = "joinApproveRequest";
//
//	public static final String IDP_CONN_HTTP_METHOD_GET = "GET";
//	public static final String IDP_CONN_HTTP_METHOD_POST = "POST";
//
//	public static final String IDP_PARAM_RESP_TYPE_NAMEVALUE = "1"; // default
//	public static final String IDP_PARAM_RESP_TYPE_XML = "2";
//
//	public static final String IDP_PARAM_RESP_FLOW_RESPONSE = "resp"; // default
//	public static final String IDP_PARAM_RESP_FLOW_REDIRECT = "redt";
//
//	public static final String IDP_PARAM_KEY_TYPE_EMAIL = "1"; // default
//	public static final String IDP_PARAM_KEY_TYPE_SN = "2";
//
//	public static final String IDP_PARAM_KEY_AUTH_TYPE_NATIVE = "1"; // default
//	public static final String IDP_PARAM_KEY_AUTH_TYPE_FOREIGN = "2";
//
//	public static final String IDP_PARAM_KEY_USER_MDN_TYPE_SKT = "SKT"; // default
//	public static final String IDP_PARAM_KEY_USER_MDN_TYPE_KTF = "KTF";
//	public static final String IDP_PARAM_KEY_USER_MDN_TYPE_LGT = "LGT";
//
//	public static final String IDP_PARAM_KEY_WATERMARK_AUTH_INCLISION = "1"; // default
//	public static final String IDP_PARAM_KEY_WATERMARK_AUTH_NON_INCLISION = "2";
//
//	public static final String IDP_PARAM_KEY_USER_TYPE_INDIVIDUAL = "1"; // default
//	public static final String IDP_PARAM_KEY_USER_TYPE_FOREIGNER = "2";
//	public static final String IDP_PARAM_KEY_USER_TYPE_JURIDICAL = "3";
//
//	public static final String IDP_PARAM_KEY_AGE_LIMIT_UNDER = "1";
//	public static final String IDP_PARAM_KEY_AGE_LIMIT_OVER = "2"; // default
//
//	public static final String IDP_PARAM_KEY_SEX_FEMALE = "f"; // default
//	public static final String IDP_PARAM_KEY_SEX_MALE = "m";
//
//	public static final String IDP_PARAM_KEY_AUTH_CHANGE_KEY_TYPE_EMAIL = "1"; // default
//	public static final String IDP_PARAM_KEY_AUTH_CHANGE_KEY_TYPE_PWD = "2";
//
//	public static final String IDP_PARAM_KEY_USER_INFO_CHANGE_KEY_TYPE_ID = "1"; // default
//	public static final String IDP_PARAM_KEY_USER_INFO_CHANGE_KEY_TYPE_USERKEY = "2";
//
//	public static final String IDP_PARAM_KEY_SECEDE_KEY_TYPE_ID = "1"; // default
//	public static final String IDP_PARAM_KEY_SECEDE_KEY_TYPE_USERKEY = "2";
//
//	public static final String IDP_PARAM_KEY_TYPE_ID = "1"; // default
//	public static final String IDP_PARAM_KEY_TYPE_EAMIL = "2";
//	public static final String IDP_PARAM_KEY_TYPE_USERKEY = "3";
//
//	// idp response 에 포함되어 있는 코드
//	/** ICAS 가입자 */
//	public static final String IDP_PARAM_KEY_JOIN_TYPE_ICAS = "icas";
//	/** IDP 통합 가입자 */
//	public static final String IDP_PARAM_KEY_JOIN_TYPE_IDP = "idp";
//	/** 서비스 가입자 */
//	public static final String IDP_PARAM_KEY_JOIN_TYPE_SVC = "svc";
//
//	public static final String IDP_PARAM_KEY_STATUS_JOIN = "J";
//	public static final String IDP_PARAM_KEY_STATUS_JOIN_STANDBY = "W";
//
//	public static final String IDP_PARAM_KEY_USER_CALENADR_SOLAR = "1";
//	public static final String IDP_PARAM_KEY_USER_CALENADR_LUNAR = "2";
//
//	public static final String IDP_PARAM_KEY_RNAME_AUTH = "Y";
//	public static final String IDP_PARAM_KEY_NON_RNAME_AUTH = "N";
//
//	public static final String IDP_PARAM_KEY_FOREIGN = "Y";
//	public static final String IDP_PARAM_KEY_NON_FOREIGN = "N";
//
//	// 조회 쿼리 조건 KEY
//	public static final String IDP_PARAM_KEY_QUERY_KEY_TYPE_ID = "1"; // default
//	public static final String IDP_PARAM_KEY_QUERY_KEY_TYPE_EMAIL = "2";
//	public static final String IDP_PARAM_KEY_QUERY_KEY_TYPE_USER_KEY = "3";
//
//	public static final String IDP_PARAM_KEY_QUERY_ID_KEY_TYPE_USERKEY = "1"; // default
//	public static final String IDP_PARAM_KEY_QUERY_ID_KEY_TYPE_EMAIL = "2";
//	/**
//	 * IDP API 항목 삭제
//	 */
//	@Deprecated
//	public static final String IDP_PARAM_KEY_QUERY_ID_KEY_TYPE_SN = "3";
//
//	public static final String IDP_PARAM_KEY_QUERY_PWD_KEY_TYPE_EMAIL = "2";
//	public static final String IDP_PARAM_KEY_QUERY_PWD_KEY_TYPE_ID = "3";
//	public static final String IDP_PARAM_KEY_QUERY_PWD_KEY_TYPE_USERKEY = "1"; // default
//
//	// 결과 코드
//	/** 요청 수행 성공 (1000) */
//	public static final String IDP_RES_CODE_OK = "1000";
//	/** 결과 값 없음 (1001) */
//	public static final String IDP_RES_CODE_NO_CONTENTS = "1001";
//	/** 중복 요청으로 인한 서비스 이용불가 (1003) */
//	public static final String IDP_RES_CODE_TIME_REQUEST = "1003";
//	/** 정의되지 않은 요청 (2000) */
//	public static final String IDP_RES_CODE_BAD_REQUEST = "2000";
//	/** 잘못된 요청 데이터 (2001) */
//	public static final String IDP_RES_CODE_BAD_REQUEST_DATA = "2001";
//	/** 필수 요청 parameter 부족 (2002) */
//	public static final String IDP_RES_CODE_REQUEST_DATA_DEFICIENCY = "2002";
//	/** 데이터 제약 사항 오류 (2003) */
//	public static final String IDP_RES_CODE_DATA_LENGTH_ERROR = "2003";
//	/** SP 키 인증 오류 (2004) */
//	public static final String IDP_RES_CODE_AUTH_KEY_FAIL = "2004";
//	/** 등록되어 있지 않은 업체 (2005) */
//	public static final String IDP_RES_CODE_UNAUTHORIZED_PVD = "2005";
//	/** 등록되어 있지 않은 IP로 접근 (2006) */
//	public static final String IDP_RES_CODE_UNAUTHORIZED_IP = "2006";
//	/** 등록되어 있지 않은 사용자 (2007) */
//	public static final String IDP_RES_CODE_UNAUTHORIZED = "2007";
//	/** 이미 서비스에 등록한 사용자 (2100) */
//	public static final String IDP_RES_CODE_ALREADY_JOIN = "2100";
//	/** 가 가입 상태인 사용자 (2101) */
//	public static final String IDP_RES_CODE_WAIT_JOIN = "2101";
//	/** 비밀번호 길이 제약 (2102) */
//	public static final String IDP_RES_CODE_PASSWORD_LENGTH_ERROR = "2102";
//	/** 비밀번호와 ID가 같음 (2103) */
//	public static final String IDP_RES_CODE_PASSWORD_ID_ERROR = "2103";
//	/** 비밀번호가 숫자로만 구성됨 (2104) */
//	public static final String IDP_RES_CODE_PASSWORD_ONLY_NUMBER_ERROR = "2104";
//	/** 비밀번호가 문자로만 구성됨 (2105) */
//	public static final String IDP_RES_CODE_PASSWORD_ONLY_CHAR_ERROR = "2105";
//	/** 비밀번호가 특수문자로만 구성됨 (2106) */
//	public static final String IDP_RES_CODE_PASSWORD_ONLY_SPECIAL_CHAR_ERROR = "2106";
//	/** 존재하지 않는 ID (2200) */
//	public static final String IDP_RES_CODE_NOT_EXIST_ID = "2200";
//	/** 비밀번호 불일치 (2201) */
//	public static final String IDP_RES_CODE_WRONG_PASSWD = "2201";
//	/** 휴대폰 인증 코드 불일치 (2202) */
//	public static final String IDP_RES_CODE_MOBILE_AUTH_FAIL = "2202";
//	/** 유무선 가입자로 무선전용 로그인 불가 (2203) */
//	public static final String IDP_RES_CODE_MDN_AUTH_JOINALL = "2203";
//	/** 무선 가입 상태 아님 (2204) */
//	public static final String IDP_RES_CODE_MDN_AUTH_NOT_WIRELESS_JOIN = "2204";
//	/** 실명 확인 실패 (2205) */
//	public static final String IDP_RES_CODE_REALNAME_AUTH_FAIL = "2205";
//	/** 실명 확인 불가 (2206) */
//	public static final String IDP_RES_CODE_REALNAME_AUTH_ERROR = "2206";
//	/** i-topping ID 인증 실패 (2207) */
//	public static final String IDP_RES_CODE_NATEID_AUTH_FAIL = "2207";
//	/** 워터마트 인증 실패 (2208) */
//	public static final String IDP_RES_CODE_WATERMARK_AUTH_FAIL = "2208";
//	/** 가입신청 상태이므로 로그인 실패 (2215) */
//	public static final String IDP_RES_CODE_INVALID_USER_INFO = "2215";
//	/** 한신정 연동 실패 (4000) */
//	public static final String IDP_RES_CODE_NICE_LINKAGE_FAIL = "4000";
//	/** ICAS 연동 오류 (4100) */
//	public static final String IDP_RES_CODE_ICAS_ERROR = "4100";
//	/** UAPS 연동 오류 (4200) */
//	public static final String IDP_RES_CODE_UAPS_ERROR = "4200";
//	/** UAPS 연동 간 SKT 사용자 인증 실패 (4201) */
//	public static final String IDP_RES_CODE_UAPS_NOT_SKT_USER = "4201";
//	/** VSMS 연동 오류 (4300) */
//	public static final String IDP_RES_CODE_VSMSC_ERROR = "4300";
//	/** Nate 인증 연동 실패 (4500) */
//	public static final String IDP_RES_CODE_NATE_LINKAGE_FAIL = "4500";
//	/** 서버 or 로직 처리 오류 (Exception) (5000) */
//	public static final String IDP_RES_CODE_INTERNAL_SERVER_ERROR = "5000";
//	/** XML parsing 오류 (5001) */
//	public static final String IDP_RES_CODE_XML_PARSING_ERROR = "5001";
//	/** DB access 오류 (5002) */
//	public static final String IDP_RES_CODE_DB_ERROR = "5002";
//	/** 요청 수행 실패 (기타 오류) (5500) */
//	public static final String IDP_RES_CODE_FAIL = "5500";
//
//	// 결과 값
//	// public static final String IDP_RES_EXIST = "1";
//	// public static final String IDP_RES_NO_EXIST = "0";
//	// 연동 코드 : NA00003492 (임시)티스토어정액제 / NA00003493 (실제)티스토어정액제
//	// public static final String IDP_TSTORE_FLAT_RATE_PROD_CD = "NA00003492";// TSTORE 정액제 상품코드
//	// 연동 코드 : NA00003492 (임시)티스토어정액제 / NA00003493 (실제)티스토어정액제
//	// public static final String IDP_TSTORE_FLAT_RATE_PROD_CD_TEMP = "NA00003492";// TSTORE 정액제 상품코드
//	// public static final String IDP_TSTORE_FLAT_RATE_PROD_CD_REAL = "NA00003493";// TSTORE 정액제 상품코드
// }
