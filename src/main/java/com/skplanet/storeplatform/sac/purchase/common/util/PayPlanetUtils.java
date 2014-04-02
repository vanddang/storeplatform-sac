/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.common.util;

import java.net.URLEncoder;

import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;

/**
 * Class 설명
 * 
 * Updated on : 2014. 3. 14. Updated by : nTels_cswoo81, nTels.
 */
@Component
public class PayPlanetUtils {
	private static final String ENCRYPT_ENCODING = "UTF-8";

	public static String makeToken(String authKey, String orderId, String amtPurchase, String mid) {

		String token;

		try {

			StringBuffer sb = new StringBuffer(128);
			sb.append(authKey).append(orderId).append(amtPurchase).append(mid);

			token = sb.toString();

			token = MD5Utils.digestInHexFormat(token);

		} catch (Exception e) {
			throw new StorePlatformException("SAC_PUR_9911");
		}

		return token;

	}

	/**
	 * 
	 * <pre>
	 * Pay Planet 과의 연동 시 필요한 토큰 값을 생성할 문자열을 가변 인자로 받아 토큰을 생성한다.
	 * </pre>
	 * 
	 * @param strings
	 *            토큰 값을 생성할 문자열의 가변 인자
	 * @return 생성된 토큰 값
	 */
	public static String makeTokenByStringVars(String... strings) {
		StringBuffer sb = new StringBuffer(128);
		for (String str : strings) {
			sb.append(str);
		}

		String token = null;
		try {
			token = MD5Utils.digestInHexFormat(sb.toString());
		} catch (Exception e) {
			throw new StorePlatformException("SAC_PUR_9911");
		}

		return token;
	}

	/**
	 * 
	 * <pre>
	 * Pay Planet 과의 연동 시 필요한 암호화 작업을 한다 : URLEncoder.encode() 처리 함.
	 * </pre>
	 * 
	 * @param plain
	 *            암호화할 문자열
	 * @param key
	 *            암호화 시 사용할 키
	 * @return 암호화된 문자열
	 */
	public static String encrypt(String plain, String key) {
		return encrypt(plain, key, true);
	}

	/**
	 * 
	 * <pre>
	 * Pay Planet 과의 연동 시 필요한 암호화 작업을 한다.
	 * </pre>
	 * 
	 * @param plain
	 *            암호화할 문자열
	 * @param key
	 *            암호화 시 사용할 키
	 * @param urlEncode
	 *            URLEncoder.encode() 처리 여부
	 * @return 암호화된 문자열
	 */
	public static String encrypt(String plain, String key, boolean urlEncode) {
		String encData = null;

		try {
			encData = Seed128Utils.encrypt(plain, key, ENCRYPT_ENCODING);

			if (urlEncode) {
				encData = URLEncoder.encode(encData, ENCRYPT_ENCODING);
			}

		} catch (Exception e) {
			throw new StorePlatformException("SAC_PUR_9912");
		}

		return encData;
	}

}
