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
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * 메시지 인증 코드 (Message Authentication Code) 생성
 * <h3>HMAC-SHA1</h3>
 * <ul><li><b>HMAC : </b>The Keyed-Hash Message Authentication Code(Checksum)</li>
 * <li><b>SHA1 : </b>Secure Hash Algorithm (160bit)</li>
 * </ul>
 * Updated on : 2014. 01. 14. Updated by : 임근대, SK 플래닛.
 */
public class HmacSha1Util {
	private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

	private static final String ENCODING = "UTF-8";

	/**
	 * HMAC-SHA1 Signature 생성
	 * Computes HMAC signature.
	 *
	 * @param data
	 * @param secretKey
	 * 		The signing key.
	 * @return
	 * 		The Base64-encoded HMAC signature.
	 * @throws SignatureException
	 */
	public static String getSignature(String data, String secretKey) throws SignatureException {
		String result;
		try {

			secretKey = percentEncode(secretKey);
			result = computeSignature(data, secretKey);
		} catch (Exception e) {
			throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
		}
		return result;
	}

	/**
	 * Compute Signature
	 * @param data
	 * @param secretKey
	 * 		The signing key.
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 */
	private static String computeSignature(String data, String secretKey)
			throws UnsupportedEncodingException, NoSuchAlgorithmException,
			InvalidKeyException {
		String result;
		// signingKey
		SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes(ENCODING), HMAC_SHA1_ALGORITHM);

		// Mac Instance
		Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
		mac.init(signingKey);

		// compute the hmac on input data bytes
		byte[] rawHmac = mac.doFinal(data.getBytes(ENCODING));

		// base64-encode the hmac
		result = new String(Base64.encodeBase64(rawHmac));
		return result;
	}

	/**
	 * Signature 유효성 체크
	 * @param signature
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static boolean isValidSignature(String signature, String data, String key)
			throws Exception {
		try {
			byte[] expected = getSignature(data, key).getBytes(ENCODING);
			byte[] actual = signature.getBytes(ENCODING);//Base64.encodeBase64(signature.getBytes(ENCODING));//decodeBase64(signature);
			//System.out.println("sig="+sig+", signature="+signature);

			return equals(expected, actual);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	/**
	 * Byte 비교
	 * @param a
	 * @param b
	 * @return
	 */
    public static boolean equals(byte[] a, byte[] b) {
        if (a == null)
            return b == null;
        else if (b == null)
            return false;
        else if (b.length <= 0)
            return a.length <= 0;
        byte diff = (byte) ((a.length == b.length) ? 0 : 1);
        int j = 0;
        for (int i = 0; i < a.length; ++i) {
            diff |= a[i] ^ b[j];
            j = (j + 1) % b.length;
        }
        return diff == 0;
    }

	/**
	 * Percent-encoding in a URI
	 * @param s
	 * @return
	 */
	public static String percentEncode(String s) {
		if (s == null) {
			return "";
		}
		try {
			return URLEncoder.encode(s, ENCODING)
					// OAuth encodes some characters differently:
					.replace("+", "%20").replace("*", "%2A")
					.replace("%7E", "~");
			// This could be done faster with more hand-crafted code.
		} catch (UnsupportedEncodingException wow) {
			throw new RuntimeException(wow.getMessage(), wow);
		}
	}



}