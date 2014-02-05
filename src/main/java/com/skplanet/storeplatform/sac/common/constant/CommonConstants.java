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

/**
 *
 * SAC 공통 상수 클래스
 * 여러 파트에 걸쳐 사용되는 상수는 여기에 정의한다.
 *
 * Updated on : 2014. 1. 22.
 * Updated by : 서대영, SK 플래닛.
 */
public class CommonConstants {

	/**
	 * 요청 커스텀 헤더 상수 - 인증키
	 */
	public static final String HEADER_AUTH_KEY = "x-sac-auth-key";

	/**
	 * 요청 커스텀 헤더 상수 - 서명
	 */
	public static final String HEADER_AUTH_SIGNATURE = "x-sac-auth-signature";

	/**
	 * 요청 커스텀 헤더 상수 - 타임스탬프
	 */
	public static final String HEADER_AUTH_TIMESTAMP = "x-sac-auth-timestamp";

	/**
	 * 요청 커스텀 헤더 상수 - 난수
	 */
	public static final String HEADER_AUTH_NONCE = "x-sac-auth-nonce";

	/**
	 * 요청 커스텀 헤더 상수 - 시스템 아이디
	 */
	public static final String HEADER_SYSTEM_ID = "x-sac-system-id";

	/**
	 * 요청 커스텀 헤더 상수 - 인터페이스 아이디
	 */
	public static final String HEADER_INTERFACE_ID = "x-sac-interface-id";

	/**
	 * 요청 스탠다드 헤더 상수 - 지원 다국어
	 */
	public static final String HEADER_ACCEPT_LANGUATE = "accept-language";

	/**
	 * 요청 스탠다드 헤더 상수 - URL
	 */
	public static final String HEADER_HTTP_REQUEST_URL = "http_requestUrl";

}
