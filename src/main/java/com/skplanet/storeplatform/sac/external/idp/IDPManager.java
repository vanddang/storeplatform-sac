/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.external.idp;

import com.skplanet.storeplatform.sac.external.idp.constant.IDPManagerConstants;

/**
 * IDPManager
 * 
 * Updated on : 2013. 12. 30. Updated by : Jeon.ByungYoul, SK planet.
 */
public class IDPManager implements IDPManagerConstants {

	/** OMP 서비스 도메인 */
	public static String OMP_SERVICE_DOMAIN = "";
	/** OMP 서비스 URL */
	public static String OMP_SERVICE_URL = "";
	/** OMP 서비스 URL (HTTPS) */
	public static String OMP_SERVICE_URL_HTTPS = "";
	/** OMP 서비스 URL IP */
	public static String OMP_SERVICE_URL_IP = "";

	/** IDP 요청 도메인 (HTTP) */
	public static String IDP_REQUEST_URL = "";
	/** IDP 요청 도메인 (HTTPS) */
	public static String IDP_REQUEST_URL_HTTPS = ""; //

	public static String IDP_REQUEST_SERVER_URL = "";
	public static String IDP_REQUEST_SERVER_URL_HTTPS = "";

	/** IDP에 등록한 OMP Association Key */
	public static String IDP_REQ_OMP_ASSOC_KEY = "";
	/** IDP로 부터 발급된 Service ID */
	public static String IDP_REQ_OMP_SERVICE_ID = "";
	/** IDP 인증 후 서비스 리턴 URL */
	// 로그인, SSO 인증
	public static String IDP_REDT_URL_USER_AUTH = "";
	/** IDP SSO 로그아웃 처리 후 리턴 URL */
	// 로그 아웃
	public static String IDP_REDT_URL_USER_LOGOUT = "";
	// 회원가입
	public static String IDP_REDT_URL_USER_REGIST_AUTH = "";
	// 회원정보 변경
	public static String IDP_REDT_URL_USER_MODIFY_PROFILE_AUTH = "";
	// 이메일 변경
	public static String IDP_REDT_URL_USER_MODIFY_EMAIL_AUTH = "";
	// 패스워드 변경
	public static String IDP_REDT_URL_USER_MODIFY_PWD_AUTH = "";

	/*
	 * IDP 통합 회원 고도화 추가
	 */
	// 타채널 아이디 인증
	public static String IDP_REDT_URL_OTHER_CHANNEL_ID_AUTH = "";
	// 타채널 가입 리스트 조회
	public static String IDP_REDT_URL_OTHER_CHANNEL_LIST = "";

	// -------------------------------------------------
	// 기본 API
	// -------------------------------------------------

}
