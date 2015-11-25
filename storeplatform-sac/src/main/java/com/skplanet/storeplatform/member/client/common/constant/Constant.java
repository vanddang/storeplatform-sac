/*
 * Copyright (c) 2014 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.client.common.constant;

// TODO: Auto-generated Javadoc
/**
 * The Class Constant.
 */
public class Constant {

	/** 회원 코드. */
	public final static String CODE_PREFIX = "US";

	/** The Constant DEFAULT_HASH_ALGORITHM. */
	public final static String DEFAULT_RANDOM_ALGORITHM = "SHA1PRNG";

	/** The Constant DEFAULT_HASH_ALGORITHM. */
	public final static String DEFAULT_HASH_ALGORITHM = "SHA-256";

	/** The Constant DEFAULT_CRYPTO_ALGORITHM. */
	public final static String DEFAULT_CRYPTO_ALGORITHM = "AES";

	/** The Constant DEFAULT_CRYPTO_FORMAT. */
	public final static String DEFAULT_CRYPTO_FORMAT = "AES/CBC/PKCS5Padding";

	/** The Constant DEFAULT_CHARSET. */
	public final static String DEFAULT_CHARSET = "UTF-8";

	/** The Constant DEFAULT_KEY_SZ. */
	public final static int DEFAULT_HASH_SZ = 8;

	/** The Constant DEFAULT_KEY_LENGTH. */
	public final static int DEFAULT_CRYPTO_SZ = 16;

	/** The Constant DEFAULT_NODATA. */
	public final static String DEFAULT_NODATA = "NODATA";

	/** The Constant DEFAULT_STRINGTYPE. */
	public final static String DEFAULT_STRINGTYPE = "java.lang.String";

	/** API에 사용되는 공통 구분 코드. */
	public final static String SEARCH_TYPE_ID = "USER_ID"; /* 회원 ID. */
	public final static String SEARCH_TYPE_EMAIL = "EMAIL_ADDR"; /* 회원 EMAIL. */
	public final static String SEARCH_TYPE_USER_KEY = "INSD_USERMBR_NO"; /* 회원 내부키. */
	public final static String SEARCH_TYPE_USER_ID = "MBR_ID"; /* 회원 아이디. */
	public final static String SEARCH_TYPE_ONEID_KEY = "INTG_SVC_NO"; /* ONEID 통합서비스 관리번호. */
	public final static String SEARCH_TYPE_IDP_KEY = "USERMBR_NO"; /* IDP 통합서비스 키. */
	public final static String SEARCH_TYPE_DEVICE_KEY = "INSD_DEVICE_ID"; /* Device 내부키. */
	public final static String SEARCH_TYPE_DEVICE_ID = "DEVICE_ID"; /* Device 고유ID. */
	public final static String SEARCH_TYPE_SVC_MANG_NO = "SVC_MANG_NO"; /* Device 서비스 관리 번호. */
	public final static String REAL_NAME_TYPE_OWN = "OWN"; /* 실명인증 대상 : 본인. */
	public final static String REAL_NAME_TYPE_PARANT = "PARENT"; /* 실명인증 대상 : 법정대리인. */

	/** API에 사용되는 로그인 상태 구분 코드. */
	public final static String LOGIN_STATUS_CODE = "10"; /* LOGIN_STATUS. */
	public final static String STOP_STATUS_CODE = "80"; /* STOP_STATUS. */

	/** API에 사용되는 공통 구분 코드. */
	public final static String TYPE_YN_Y = "Y"; /* YES. */
	public final static String TYPE_YN_N = "N"; /* NO. */

	/** The Constant SAC_URL. */
	public static final String SAC_URL = "http://localhost:8080";

	/** The Constant MEDIA_TYPE_APP_XPROTOBUF. */
	public static final String MEDIA_TYPE_APP_XPROTOBUF = "application/x-protobuf; charset=UTF-8";

	public static final String HEADER_STORE_AUTH_INFO = "x-store-auth-info";

	public static final String HEADER_STORE_AUTH_INFO_NAME = "";

	/** 유휴회원 - 회원 상태 정상 처리 코드. */
	public static final String USERMBR_MOVE_TYPE_ACTIVATE = "US012301";
	/** 유휴회원 - 회원 상태 휴면 처리 코드. */
	public static final String USERMBR_MOVE_TYPE_DORMANT = "US012302";

	/** 사용자구분코드. */
	public static final String USER_TYPE_MOBILE = "US011501"; // 기기사용자
	public static final String USER_TYPE_IDPID = "US011502"; // IDP사용자
	public static final String USER_TYPE_ONEID = "US011503"; // OneID사용자

	public static final String SELLER_DOCUMENT_PATH = "/data/mem/";

	/** 휴면계정 처리 에러 코드 - (IDP계정)변경 요청된 상태값과 현재의 상태값이 같습니다. */
	public static final String EC_IDP_2031 = "2031";
	/** 휴면계정 처리 에러 코드 - (One ID계정)변경 요청된 상태값과 현재의 상태값이 같습니다. */
	public static final String EC_IDP_2031X000 = "2031X000";

	/** 기본 배송지 코드. */
	public static final String DELIVERY_BASE_CD = "US014601";
	/** 최근 배송지 코드. */
	public static final String DELIVERY_RECENTLY_CD = "US014602";

}
