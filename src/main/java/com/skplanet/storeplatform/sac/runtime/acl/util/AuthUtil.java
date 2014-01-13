/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.runtime.acl.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * SAC 인증 관련 Util
 * Updated on : 2014. 01. 14. Updated by : 임근대, SK 플래닛.
 */
public class AuthUtil {

	public static final String ENCODING = "UTF-8";
	public static final String CUSTOM_HEADER_KEY_TENANTKEY = "x-sac-auth-tenant-key";
	public static final String CUSTOM_HEADER_KEY_TIMESTAMP = "x-sac-auth-timestamp";
	public static final String CUSTOM_HEADER_KEY_NONCE = "x-sac-auth-nonce";


	/** default window for timestamps is 5 minutes */
	private static final long timestampWindow = 5 * 60 * 1000L;
	// default window for timestamps is 5 minutes
    //public static final long DEFAULT_TIMESTAMP_WINDOW = 5 * 60 * 1000L;


	/**
	 * 인증을 위한 Custom Header x-auth-timestamp 값
	 *
	 * @return 요청 정보를 생성한 시점의 Timestamp.
	 */
	public static String getTimestamp() {
		return System.currentTimeMillis() / 1000 + "";
	}

	/**
	 * 인증을 위한 Custom Header x-auth-nonce 값
	 *
	 * @return replay attack 방지를 위해 동일한 timestamp 마다 유일한 랜덤 값
	 */
	public static String getNonce() {
		return System.nanoTime() + "";
	}

	/**
	 * URL Add Parameter
	 * @param url
	 * 		URL
	 * @param name
	 * 		Parameter Name
	 * @param value
	 * 		Parameter Value
	 * @return
	 * 		URL + '[?|&]' + 'paramKey=paramValue'
	 */
	public static String addParameter(String url, String name, String value) {
		int qpos = url.indexOf('?');
		int hpos = url.indexOf('#');
		char sep = qpos == -1 ? '?' : '&';
		String seg = sep + encodeUrl(name) + '=' + encodeUrl(value);
		return hpos == -1 ? url + seg : url.substring(0, hpos) + seg + url.substring(hpos);
	}

	/**
	 * Encode URL
	 * @param url
	 * 		URL
	 * @return
	 */
	public static String encodeUrl(String url) {
		try {
			return URLEncoder.encode(url, ENCODING);
		} catch (UnsupportedEncodingException uee) {
			throw new IllegalArgumentException(uee);
		}
	}

	/**
	 * Decode URL
	 * @param url
	 * 		URL
	 * @return
	 */
	public static String decodeUrl(String url) {
		try {
			return URLDecoder.decode(url, ENCODING);
		} catch (UnsupportedEncodingException uee) {
			throw new IllegalArgumentException(uee);
		}
	}

//	/**
//	 * 인증 Signature 생성을 위한 URL (timestamp, nonce 생성) 요청 URL + 인증을 위한 Custom
//	 * Header (x-sac-auth-tenant-key+x-sac-auth-timestamp+x-sac-auth-nonce)
//	 *
//	 * @param baseUrl
//	 *            요청 URL
//	 * @param tenantKey
//	 *            Tenant Key
//	 * @return 인증 Signature 생성을 위한 URL
//	 */
//	public static String getUrlForAuth(String baseUrl, String tenantKey) {
//		return getUrlForAuth(baseUrl, tenantKey, getTimestamp(), getNonce());
//	}

	/**
	 * 인증 Signature 생성을 위한 URL 요청 URL + 인증을 위한 Custom Header
	 * (x-sac-auth-tenant-key+x-sac-auth-timestamp+x-sac-auth-nonce)
	 *
	 * @param baseUrl
	 *            요청 URL
	 * @param tenantKey
	 *            Tenant Key
	 * @return 인증 Signature 생성을 위한 URL
	 */
	public static String getUrlForAuth(String baseUrl, String tenantKey, String timestamp, String nonce) {
		String urlForAuth = baseUrl;

		urlForAuth = addParameter(urlForAuth, CUSTOM_HEADER_KEY_TENANTKEY, tenantKey);
		urlForAuth = addParameter(urlForAuth, CUSTOM_HEADER_KEY_TIMESTAMP, timestamp);
		urlForAuth = addParameter(urlForAuth, CUSTOM_HEADER_KEY_NONCE, nonce);

		return urlForAuth;
	}




	/**
	 * return false if the timestamp is out of range or the nonce has been validated previously.
	 * @return
	 */
	public static boolean isValidTimestampAndNonce(String timestamp, String nonce) {
		boolean result = true;

		result = isValidTimestamp(timestamp);
		if(result)
			result = isValidNonce(timestamp, nonce);
		return result;
	}


	/**
	 * timestamp 유효성 체크
	 * @param timestamp
	 */
	private static boolean isValidTimestamp(String timestamp) {
		boolean result = true;
		long nTimestamp = 0l;
		try {
			nTimestamp = Long.parseLong(timestamp) * 1000L;
			long now = System.currentTimeMillis();
			long min = now - timestampWindow;
	        long max = now + timestampWindow;
			if (nTimestamp < min || max < nTimestamp) {
				result = false;
			}
		} catch (Exception e) {
			result = false;
		}

		return result;
	}


	/**
	 * nonce 유효성 체크
	 * @param nonce
	 * @return
	 */
	private static boolean isValidNonce(String timestamp, String nonce) {
		boolean result = true;
		return result;
	}
}
