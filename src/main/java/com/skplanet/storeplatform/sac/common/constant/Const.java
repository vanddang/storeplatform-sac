/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.common.constant;

import java.util.HashMap;
import java.util.Map;

public class Const {
	/**
	 * Content-Type 고정값
	 */
	public static final String CONTENT_TYPE = "application/vnd.planet.profiles+xml";

	/**************************************************
	 * CommandExcute Return Map Key
	 *************************************************/
	public static final String CMD_EXE_XML_KEY = "XML";
	public static final String CMD_EXE_RDS_KEY = "RDS";
	public static final String CMD_EXE_ERROR_KEY = "ERROR";

	/**************************************************
	 * 공통 ResultIndex Key
	 *************************************************/
	/**
	 * 카테고리 정보
	 */
	public static final String RESULT_INDEX_CATEGORY = "category";
	/**
	 * 이벤트 상품 정보
	 */
	public static final String RESULT_INDEX_EVENT = "event";
	/**
	 * 상품 업데이트 이력
	 */
	public static final String RESULT_INDEX_UPDATE = "update";
	/**
	 * 상품 스크린 샷
	 */
	public static final String RESULT_INDEX_SCREENSHOT = "screenshot";
	/**
	 * 상품 스크린 샷 - 원본사이즈
	 */
	public static final String RESULT_INDEX_SCREENSHOT_ORI = "screenshot_ori";
	/**
	 * 오류처리 공통
	 */
	public static final String RESULT_INDEX_ERROR = "error";
	/**
	 * 프로모션
	 */
	public static final String RESULT_INDEX_PROMOTION = "promotion";
	/**
	 * 스페셜 팝업
	 */
	public static final String RESULT_INDEX_SPECIAL_POPUP = "special_popup";
	/**
	 * base
	 */
	public static final String RESULT_INDEX_BASE = "base";

	/**************************************************
	 * HTTP Servlet Parameter Key
	 *************************************************/
	public static final String HTTP_PARAM_KEY = "param";
	public static final String HTTP_REQUSTED_URL = "requestedUrl";
	public static final String HTTP_ETOKEN = "eToken";
	public static final String HTTP_PHONE_MODEL_CD = "PHONE_MODEL_CD";
	public static final String HTTP_MDN = "MDN";
	public static final String HTTP_OS_VER = "OS_VER";
	public static final String HTTP_OS_VER_ORG = "OS_VER_ORG";
	public static final String HTTP_SC_VER = "SC_VER";
	public static final String HTTP_OS_NAME = "OS_NAME";
	public static final String HTTP_BIRTH_DT = "BIRTH_DT";
	public static final String HTTP_USER_AUTH_KEY = "USER_AUTH_KEY";
	public static final String HTTP_MBR_NO = "MBR_NO";
	public static final String HTTP_MDN_CORP = "MDN_CORP";
	public static final String HTTP_DPI = "DPI";
	public static final String HTTP_NETWORK = "NETWORK";
	public static final String HTTP_SESSION_ID = "SESSION_ID";
	public static final String HTTP_IP_ADDR = "IP_ADDR";
	public static final String HTTP_UUID = "UUID";
	public static final String HTTP_IMEI = "IMEI";
	public static final String HTTP_ROOTING = "ROOTING";
	public static final String HTTP_DID = "DID";
	public static final String HTTP_CLIENT_NAME = "CLIENT_NAME";
	public static final String HTTP_CLIENT_VER = "CLIENT_VER";
	public static final String HTTP_CLIENT_PKG_NAME = "CLIENT_PKG_NAME";
	public static final String HTTP_CLIENT_PKG_VER = "CLIENT_PKG_VER";
	public static final String HTTP_RESOLUTION = "HTTP_RESOLUTION";
	public static final String HTTP_ACCEPT_SERVICES = "ACCEPT_SERVICES";
	public static final String HTTP_NEW_SHOPPING = "NEW_SHOPPING";
	public static final String HTTP_JOIN_CHNL_CD = "JOIN_CHNL_CD";
	public static final String HTTP_TOKEN = "token";
	public static final String HTTP_USER_AGENT = "ua";
	public static final String HTTP_AUTO_UPDATE = "IS_AUTO_UPDATE";
	public static final String HTTP_LOGIN_SESSION_ID = "LOGIN_SESSION_ID";
	public static final String HTTP_PRCHS_SESSION_ID = "PRCHS_SESSION_ID";

	public static final String HTTP_PARAM_RANGE_KEY = "range";
	public static final String HTTP_PARAM_FILTEREDBY = "filteredBy";
	public static final String HTTP_PARAM_ORDEREDBY = "orderedBy";
	public static final String HTTP_PARAM_TYPE = "type";
	public static final String HTTP_PARAM_BOOK_TYPE = "bookType";
	public static final String HTTP_PARAM_BASE_CHAPTER = "baseChapter";

	public static final String HTTP_PARAM_TOTAL_COUNT = "TOTAL_COUNT";
	public static final String HTTP_PARAM_START_ROW = "START_ROW";
	public static final String HTTP_PARAM_END_ROW = "END_ROW";

	public static final String HTTP_PARAM_PROD_ID = "PROD_ID";
	public static final String HTTP_PARAM_PKG_NM = "PKG_NM";
	public static final String HTTP_PARAM_TOP_CAT_CD = "TOP_CAT_CD";
	public static final String HTTP_PARAM_DP_CAT_NO = "DP_CAT_NO";
	public static final String HTTP_PARAM_PROD_STAT_CD = "PROD_STAT_CD";

	public static final String HTTP_PARAM_ERROR_CODE = "ERROR_CODE";
	public static final String HTTP_PARAM_ERROR_MSG = "ERROR_MSG";

	/**************************************************
	 * Type 정의 ( 향후 필요 시 XML or properties 파일로 관리 )
	 *************************************************/
	/**
	 * type - 이미지 URL
	 */
	public static final String IF_TYPE_IMG_URL = "image_url";

	/**************************************************
	 * 변수 정의
	 *************************************************/
	/**
	 * variable - Y
	 */
	public static final String VAR_YES = "Y";

	/**
	 * variable - N
	 */
	public static final String VAR_NO = "N";

	/**
	 * variable - 공백문자
	 */
	public static final String VAR_SPACE = " ";

	/**
	 * variable - 콤마(,)
	 */
	public static final String VAR_COMMA = ",";

	/**
	 * variable - caret(^)
	 */
	public static final String VAR_CARET = "^";

	/**
	 * variable - 세미콜론(;)
	 */
	public static final String VAR_SEMICOLON = ";";

	/**
	 * variable - 콜론(:)
	 */
	public static final String VAR_COLON = ":";

	/**
	 * variable - 하이픈(-)
	 */
	public static final String VAR_HYPHEN = "-";

	/**
	 * variable - 언더바(_)
	 */
	public static final String VAR_UNDER_BAR = "_";

	/**
	 * variable - euc-kr
	 */
	public static final String VAR_EUC_KR = "euc-kr";

	/**
	 * variable - utf-8
	 */
	public static final String VAR_UTF_8 = "utf-8";

	/**
	 * variable - 물음표(?)
	 */
	public static final String VAR_QMARK = "?";

	/**
	 * variable - 줄바꿈(\n)
	 */
	public static final String VAR_CR = "\n";

	/**
	 * variable - 등호(=)
	 */
	public static final String VAR_EQL = "=";

	/**
	 * variable - /
	 */
	public static final String VAR_SLASH = "/";

	/**
	 * variable - Ampersand(&)
	 */
	public static final String VAR_AMP = "&";

	/**
	 * variable - 플러스(+)
	 */
	public static final String VAR_PLUS = "+";

	/**
	 * variable - 도트(.)
	 */
	public static final String VAR_DOT = ".";

	/**
	 * variable - 통계 구분자
	 */
	public static final String VAR_STATICS = "{$[";

	/**
	 * variable - Double quotation
	 */
	public static final String VAR_DOUBLE_QUOTATION = "\"";

	/**
	 * variable - pipe(|)
	 */
	public static final String VAR_PIPE = "|";

	/**
	 * variable - 좌괄호(
	 */
	public static final String VAR_LEFT_PARENS = "(";

	/**
	 * variable - 우괄호)
	 */
	public static final String VAR_RIGHT_PARENS = ")";

	/**
	 * variable - tab
	 */
	public static final Object VAR_TAB = "\t";
	/**************************************************
	 * XML - Element
	 *************************************************/
	/**
	 * element - command
	 */
	public static final String XML_ELE_CMD = "command";

	/**
	 * element - request
	 */
	public static final String XML_ELE_REQ = "request";

	/**
	 * element - response
	 */
	public static final String XML_ELE_RES = "response";

	/**
	 * element - mapper
	 */
	public static final String XML_ELE_MAPPER = "mapper";

	/**
	 * element - profile
	 */
	public static final String XML_ELE_PROF = "profile";

	/**
	 * element - element
	 */
	public static final String XML_ELE_ELEMENT = "element";

	/**
	 * element - attribute
	 */
	public static final String XML_ELE_ATTRIBUTE = "attribute";

	/**
	 * element - attribute
	 */
	public static final String XML_ELE_PARAM = "param";

	/**************************************************
	 * XML - attribute
	 *************************************************/
	/**
	 * attribute - Element id
	 */
	public static final String XML_ATTR_ID = "id";

	/**
	 * attribute - 실행대상 Action 클래스
	 */
	public static final String XML_ATTR_ACTION = "action";

	/**
	 * attribute - 요청 protocol
	 */
	public static final String XML_ATTR_SECURE = "secure";

	/**
	 * attribute - FDS LOG
	 */
	public static final String XML_ATTR_FDS_LOG = "fdsLog";

	/**
	 * attribute - 하위 요소 수
	 */
	public static final String XML_ATTR_COUNT = "count";

	/**
	 * attribute - 속성명
	 */
	public static final String XML_ATTR_NAME = "name";

	/**
	 * attribute - alias
	 */
	public static final String XML_ATTR_ALIAS = "alias";

	/**
	 * attribute - 속성명
	 */
	public static final String XML_ATTR_PROPERTY = "property";

	/**
	 * attribute - Field Type
	 */
	public static final String XML_ATTR_TYPE = "type";

	/**
	 * attribute - 필수값 여부
	 */
	public static final String XML_ATTR_REQUIRED = "required";

	/**
	 * attribute - Charset 정의
	 */
	public static final String XML_ATTR_CHARSET = "charset";

	/**
	 * attribute - 암호화 여부
	 */
	public static final String XML_ATTR_ENCRYPT = "encrypt";

	/**
	 * attribute - 복호화 여부
	 */
	public static final String XML_ATTR_DECRYPTT = "decrypt";

	/**
	 * attribute - escape Html
	 */
	public static final String XML_ATTR_ESCAPEXML = "escapeXml";

	/**
	 * attribute - unescape Html
	 */
	public static final String XML_ATTR_UNESCAPEXML = "unescapeXml";

	/**
	 * attribute - resultindex (결과값 index )
	 */
	public static final String XML_ATTR_RESULTINDEX = "resultindex";

	/**
	 * attribute - 결과값 참조 키
	 */
	public static final String XML_ATTR_ENTRYKEY = "entryKey";

	/**
	 * attribute - 결과값 참조 index
	 */
	public static final String XML_ATTR_ENTRYINDEX = "entryIndex";

	/**
	 * attribute - 지정 값 정의여부
	 */
	public static final String XML_ATTR_SPECIFY = "specify";

	/**
	 * attribute - 기본 값
	 */
	public static final String XML_ATTR_DEFAULT_VAL = "defaultVal";

	/**
	 * attribute - CDATA 사용여부
	 */
	public static final String XML_ATTR_CDATA = "cdata";

	/**
	 * attribute - 날짜여부
	 */
	public static final String XML_ATTR_DATE = "date";

	/**
	 * attribute - 날짜여부 (ISO8601 UTC)
	 */
	public static final String XML_ATTR_UTC_DATE = "utcDate";

	/**
	 * attribute - separator
	 */
	public static final String XML_ATTR_SEPARATOR = "separator";

	/**
	 * attribute - secretAlgorism
	 */
	public static final String XML_ATTR_SECRETALGORISM = "secretAlgorism";

	/**
	 * attribute - secretKey
	 */
	public static final String XML_ATTR_SECRETKEY = "secretKey";

	/**************************************************
	 * XML - Tag 가공용 문자
	 *************************************************/
	public static final String XML_ALT = "<";
	public static final String XML_AGT = ">";
	public static final String XML_SLASH_AGT = "/>";

	public static final String XML_TAG_START = "<?>";
	public static final String XML_TAG_END = "</?>";

	public static final String XML_TAG_ACTION_START = "<action identifier=\"?\">";
	public static final String XML_TAG_ACTION_END = "</action>";

	public static final String XML_TAG_CDATA_BLOCK = "<![CDATA[?]]>";
	public static final String XML_TAG_DEC_BLOCK = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	public static final String XML_TAG_DESCRIPTION_BLOCK = "<description><![CDATA[?]]></description>";

	/**************************************************
	 * GiftCard 서버 응답코드
	 *************************************************/
	// 정상
	public static final String GIFT_RTN_CODE_00 = "00";

	// 유효하지 않은 PIN 번호
	public static final String GIFT_RTN_CODE_08 = "08";

	// 존재하지 않은 PIN 번호
	public static final String GIFT_RTN_CODE_10 = "10";

	// 이미 등록된 PIN 번호
	public static final String GIFT_RTN_CODE_11 = "11";

	// 등록 대기중인 PIN 번호
	public static final String GIFT_RTN_CODE_12 = "12";

	// PIN 유효기간 만료
	public static final String GIFT_RTN_CODE_16 = "16";

	// 폐기된 PIN 번호
	public static final String GIFT_RTN_CODE_17 = "17";

	/**************************************************
	 * IDP 응답 관련
	 *************************************************/
	// IDP 응답키
	public static final String IDP_RESULT = "result";

	// IDP 응답코드(성공)
	public static final String IDP_SUCCESS = "00000";

	// IDP 응답코드(기가입자)
	public static final String IDP_ALREADY_JOIN = "22100";

	// IDP sign_data
	public static final String IDP_SIGN_DATA = "sign_data";

	// 회원ID(MBR_ID)
	public static final String IDP_USER_ID = "user_id";

	// 회원번호(MBR_NO)
	public static final String IDP_USER_KEY = "user_key";

	// 서비스관리번호(SVC_MGMT_NUM)
	public static final String IDP_SVC_MNG_NUM = "svc_mng_num";

	// 실명인증 여부(is_rname_auth)
	public static final String IDP_RNAME_AUTH_YN = "is_rname_auth";

	// CI 값 (user_ci)
	public static final String IDP_USER_CI = "user_ci";

	// 등록단말정보(핸드폰번호,서비스관리번호,모델코드,통신사)
	public static final String IDP_USER_PHONE = "user_phone";

	// 모델코드(DEVICE_CODE)
	public static final String IDP_MODEL_ID = "model_id";

	// IDP 응답문구
	public static final String IDP_RESULT_TEXT = "result_text";

	// 등록된 사용자 아이디
	public static final String IDP_ID_LIST = "id_list";

	// 임시비밀번호
	public static final String IDP_TEMP_PASSWD = "temp_passwd";

	// 로그인 상태코드
	public static final String IDP_LOGIN_STATUS_CODE = "login_status_code";

	// IM 통합서비스번호
	public static final String IDP_IM_INT_SVC_NO = "im_int_svc_no";

	// IM ID 중복 사이트 정보
	public static final String IDP_DUP_SST = "dup_sst";

	/**************************************************
	 * 미지원 단말
	 *************************************************/
	public static final String NOT_SUPPORT_HP_CORP = "NSH";

	public static final String NOT_SUPPORT_HP_UACODE = "9999";

	public static final String NOT_SUPPORT_HP_MODEL_CD = "android_standard";

	public static final String NOT_SUPPORT_HP_MODEL_NM = "미지원표준단말";

	/**************************************************
	 * 통신사 정보
	 *************************************************/
	public static final String MCC_MNC_KT_02 = "450/02";
	public static final String MCC_MNC_KT_04 = "450/04";
	public static final String MCC_MNC_KT_08 = "450/08";

	public static final String MCC_MNC_SKT_03 = "450/03";
	public static final String MCC_MNC_SKT_05 = "450/05";

	public static final String MCC_MNC_LGT_06 = "450/06";

	public static final String SKT = "SKT";
	public static final String KT = "KTF";
	public static final String LGT = "LGT";
	public static final String ISB = "ISB";

	/**************************************************
	 * 통합회원
	 *************************************************/
	public static final String SSO_SST_CD_TSTORE = "41100";
	public static final String SSO_SST_CD_OCB = "42100";

	/**************************************************
	 * Cache Control
	 *************************************************/
	public static final int MAX_AGE_1DAY = 86400;
	public static final int MAX_AGE_1MIN = 60;
	public static final int MAX_AGE_5MIN = 300;
	public static final int MAX_AGE_10MIN = 600;

	/**************************************************
	 * 선물/추천하기 URL PREFIX
	 *************************************************/
	public static final String GIFT_URL_PREFIX = "GI";
	public static final String RECOM_URL_PREFIX = "RE";
	public static final String GIFT_URL_TYPE_CD = "PF000101";
	public static final String RECOM_URL_TYPE_CD = "PF000102";

	/**
	 * 추천 사유 중 상품명 최대 길이
	 */
	public static final int REASON_MAX_LENGTH_IN_BYTES = 24;

	/**************************************************
	 * 컬러링 연동 응답 관련
	 *************************************************/
	// 컬러링 응답키
	public static final String COLORRING_RESULT = "RESULT";

	// 컬러링 응답코드(성공)
	public static final String COLORRING_SUCCESS = "0000";

	/**************************************************
	 * FDS 로그 관련
	 *************************************************/
	public static final String FDS_PARAM_LOG_ID = "LOG_ID";
	public static final String FDS_PARAM_SVC_MGNT_NUM = "SVC_MGMT_NUM";
	public static final String FDS_PARAM_MBR_ID = "MBR_ID";
	public static final String FDS_PARAM_PROD_NM = "PROD_NM";
	public static final String FDS_PARAM_PROD_AMT = "PROD_AMT";
	public static final String FDS_PARAM_PAY_METHOD = "PAY_METHOD";
	public static final String FDS_PARAM_RCP_MDN = "RCP_MDN";
	public static final String FDS_PARAM_RCP_MBR_ID = "RCP_MBR_ID";
	public static final String FDS_PARAM_RCP_MBR_NO = "RCP_MBR_NO";
	public static final String FDS_PARAM_LINK_SYSTEM_RES_CODE = "LINK_SYSTEM_RESULT_CODE";
	public static final String FDS_PARAM_LINK_SYSTEM_RES_MSG = "LINK_SYSTEM_RESULT_MSG";
	public static final String FDS_PARAM_TIME_OUT = "TIME_OUT";

	// 로그 ID (FDS001)
	public static final String FDS_LOG_ID_LOGIN = "FDS00101"; // 로그인
	public static final String FDS_LOG_ID_GIFT_CHECK = "FDS00108"; // 선물 조건 체크
	public static final String FDS_LOG_ID_POINT_CHECK = "FDS00114"; // 수단별 결제가능 여부 체크

	// 구매 인입 경로
	public static final String FDS_INFLOW_CHNL_PRCHS = "FDS00201"; // 구매
	public static final String FDS_INFLOW_CHNL_GIFT = "FDS00202"; // 선물

	// 연동 시스템
	public static final String FDS_LINK_SYSTEM_IDP = "IDP";
	public static final String FDS_LINK_SYSTEM_DOTORI = "CYWORLD";
	public static final String FDS_LINK_SYSTEM_OCB = "OCB";
	public static final String FDS_LINK_SYSTEM_TMEMBERSHIP = "R2K";
	public static final String FDS_LINK_SYSTEM_CULTURE = "CULTURELAND";

	// 결제 수단
	public static final String FDS_PAY_METHOD_TCASH = "OR000607";
	public static final String FDS_PAY_METHOD_DOTORI = "OR000604";
	public static final String FDS_PAY_METHOD_OCB = "OR000603";
	public static final String FDS_PAY_METHOD_TMEMBERSHIP = "OR000616";
	public static final String FDS_PAY_METHOD_CULTURE = "OR000610";

	// Mobile Client POC
	public static final String FDS_POC_SHOPCLIENT = "OR000405";

	// 성공/실패
	public static final String FDS_RESULT_SUCC = "SUCC";
	public static final String FDS_RESULT_FAIL = "FAIL";

	// 통신사
	public static final String FDS_COMP_KTF = "KT";
	public static final String FDS_COMP_LGT = "U+";

	/**************************************************
	 * Cookie extInfo PRIFIX
	 *************************************************/
	public static final String EXT_PRIFIX_IDP = "idp";
	public static final String EXT_PRIFIX_LOGIN_SESSION = "login";
	public static final String EXT_PRIFIX_PRCHS_SESSION = "purchase";

	/**************************************************
	 * Common lib
	 *************************************************/
	// 성공
	public static final String REF_COMM_SUCCESS = "0000";

	// 오류코드
	public static final String REF_COMM_ERROR_CODE = "errorCode";

	// IDP RESULT_CODE Mapping
	public static final Map<String, String> IDP_RESULT_CODE_MAP = new HashMap<String, String>();
	static {
		IDP_RESULT_CODE_MAP.put("0000", "00000"); // 요청성공
		IDP_RESULT_CODE_MAP.put("2001", "25500"); // Transaction ID가 미존재
		IDP_RESULT_CODE_MAP.put("2002", "25500"); // 발송시간 초과(인증번호 발송후 3분이 경과)
		IDP_RESULT_CODE_MAP.put("2003", "22021"); // 인증 대기 상태 아님
		IDP_RESULT_CODE_MAP.put("2004", "22202"); // 인증번호 불일치
		IDP_RESULT_CODE_MAP.put("2005", "21003"); // 인증번호가 발송 되었습니다.잠시 후 다시 시도해 주시기 바랍니다.
	}

}
