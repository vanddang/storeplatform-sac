package com.skplanet.storeplatform.sac.purchase.common.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * AES 128 알고리즘에 의한 데이터의 암호화 및 복호화 수행
 * 
 * Updated on : 2014. 3. 21. Updated by : 이승택, nTels.
 */
public class CryptoManager {

	/**
	 * 평문을 AES128 암호화 알고리즘으로 암호화한다
	 * 
	 * @param key
	 *            암호화키
	 * @param cleartext
	 *            암호화대상 평문 문자열
	 * @return 암호화된 문자열
	 */
	public static byte[] encryptToByteArray(String key, String cleartext) throws Exception {
		return encrypt(key.getBytes(), cleartext.getBytes("UTF-8"));
	}

	/**
	 * 평문을 AES128 암호화 알고리즘으로 암호화한다
	 * 
	 * @param key
	 *            암호화키 바이트 배열
	 * @param cleartext
	 *            암호화대상 평문 바이트 배열
	 * @return 암호화된 문자열의 바이트 배열
	 */
	private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		return cipher.doFinal(clear);
	}

	/**
	 * 암호문을 AES128 암호화 알고리즘으로 복호화한다
	 * 
	 * @param key
	 *            암호화키
	 * @param cleartext
	 *            복호화대상 암호문 문자열
	 * @return 복호화된 평문 문자열
	 */
	public static String decrypt(String key, String encrypted) throws Exception {
		byte[] result = decrypt(key.getBytes(), toByte(encrypted));
		return new String(result);
	}

	public static String decryptToByteArray(String key, byte[] enc) throws Exception {
		byte[] result = decrypt(key.getBytes(), enc);
		return new String(result, "UTF-8");
	}

	/**
	 * 암호문을 AES128 암호화 알고리즘으로 복호화한다
	 * 
	 * @param key
	 *            암호화키 바이트 배열
	 * @param cleartext
	 *            복호화대상 암호문 바이트 배열
	 * @return 복호화된 평문 문자열의 바이트 배열
	 */
	private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		return cipher.doFinal(encrypted);
	}

	/**
	 * 문자열을 바이트 배열로 변환
	 * 
	 * @param txt
	 *            문자열
	 * @return 바이트 배열
	 */
	public static byte[] toByte(String hexString) {
		int len = hexString.length() / 2;
		byte[] result = new byte[len];
		for (int i = 0; i < len; i++)
			result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
		return result;
	}

}
