/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.integration.api.test.signature;

import java.security.MessageDigest;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 암호화하기 위한 서명 클래스
 * 
 * Updated on : 2013. 11. 18. Updated by : 김현일, 인크로스.
 */
public class Signature {

	private static final Logger LOGGER = LoggerFactory.getLogger(Signature.class);

	/**
	 * 
	 */
	private static final String HMAC_SHA1 = "HmacSHA1";

	/**
	 * 
	 */
	private static final String MD5 = "md5";

	/**
	 * 
	 */
	private static final String CHARSET = "UTF-8";

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param key
	 *            key
	 * @param data
	 *            data
	 * @return String
	 */
	public static String calculateRFC2104HMAC(String key, String data) {
		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), HMAC_SHA1);
		try {
			Mac mac = Mac.getInstance(HMAC_SHA1);
			mac.init(keySpec);
			return encodeBase64(mac.doFinal(key.getBytes()));
		} catch (Exception e) {
			LOGGER.warn("{}", e.getMessage());
			throw new SignatureException(e);
		}
	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param key
	 *            key
	 * @return String
	 */
	public static String calculateMD5(String key) {
		try {
			MessageDigest md = MessageDigest.getInstance(MD5);
			byte[] hash = md.digest(key.getBytes(CHARSET));
			return toHex(hash);
		} catch (Exception e) {
			LOGGER.warn("{}", e.getMessage());
			throw new SignatureException(e);
		}
	}

	/**
	 * 
	 * <pre>
	 * byte를 base64 인코딩.
	 * </pre>
	 * 
	 * @param rawData
	 *            rawData
	 * @return String
	 */
	private static String encodeBase64(byte[] rawData) {
		byte[] encode = Base64.encodeBase64(rawData);
		return new String(encode);
	}

	/**
	 * 
	 * <pre>
	 * md5 hex String으로 변환.
	 * </pre>
	 * 
	 * @param hash
	 *            hash
	 * @return String
	 */
	private static String toHex(byte[] hash) {
		return new String(Hex.encodeHex(hash));
	}
}
