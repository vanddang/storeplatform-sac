package com.skplanet.storeplatform.sac.other.common;

import java.security.Key;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 * 암호화/복호화 처리 클래스<br>
 * AES/DES/DESede/TripleDES 알고리즘을 지원한다.
 * 
 * @version 1.0
 * @author Cho Gi Ho
 */
public class CryptUtils {

	public static final String AES = "AES";
	public static final String AES128_CTR = "AES128-CTR";
	public static final String AES128_CTR_NONCE = "AES128-CTR_NONCE"; // 클라이언트 발급용
	public static final String DES = "DES";
	public static final String DESede = "DESede";
	public static final String TripleDES = "TripleDES";
	private static final String BLOCK_ECB_PKCS5 = "/ECB/PKCS5Padding";

	private static final String AES128_CTR_IV_PARAM = "8558d09bee338fd8bb530006ab9e3351";
	private static final String AES128_CTR_KEY[] = { "090f340171803e19867c0d2b6e0eb7d4",
			"5fe1772b059d735a8ea7f5200e4c827a", "38d4fc0fbc8db67a4657c3c608798bc0", "c0cefc2b812c0f2066fa2176b98b6b80",
			"4306c4a95a1700a8070690068dce63a9", "2779a117c6ac4a23dc2f020a2f323ee1", "e27eea4057b1d7a8765d30c33fd5e1ce",
			"59efdc37f0f89d75dbf461e3de2fe39b", "891c5d61be40c91a35fdc78b5a2ed091", "02b8aa7297b3b41170d69a129ea37291" };

	// 클라이언트 발급용 (NONCE)
	public static final String AES128_CTR_IV_PARAM_NONCE = "5629be5d831597a704a8457fd854e3ac";
	public static final String AES128_CTR_KEY_NONCE[] = { "a7726713b46b951b5b837fa17a4ac2e1",
			"f18ddd60bc971b86b3d9ed8c067ab473", "f92e73cd4ad5d7fccc37ec18a88bfcfa", "65c46a0dfedc72aa1690ea62830baa79",
			"8cba76cebd2b18df722a984f54520af7", "b3334297f8c328148f50d8d6b588133e", "af9a001e89ca2ffc26a69531e4c6baab",
			"34bd96724d893c300217b878bed1c09e", "6d8e3bb8d04b8d291376b4790119a682", "96c2a368a4051896da425fe4fd0ae87e" };

	/**
	 * 암호화 알고리즘에 사용할 비밀키를 생성한다<br>
	 * 
	 * @param algorithm
	 *            암호화 알고리즘
	 * @param key
	 *            비밀키
	 * @return 생성된 SecretKey
	 * @throws Exception
	 */
	private static Key keygen(String algorithm, String key) throws Exception {

		Key secretKey = null;
		byte[] btKey;

		if (algorithm.equals(DES)) {
			// 64 bit
			btKey = StringUtils.leftPad(key, 8, "0").getBytes();
			KeySpec keySpec = new DESKeySpec(btKey);
			SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(algorithm);
			secretKey = secretKeyFactory.generateSecret(keySpec);
		} else if (algorithm.equals(DESede) || algorithm.equals(algorithm.equals(TripleDES))) {
			// 192bit ( 64bit*3)
			btKey = StringUtils.leftPad(key, 24, "0").getBytes();
			KeySpec keySpec = new DESedeKeySpec(btKey);
			SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(algorithm);
			secretKey = secretKeyFactory.generateSecret(keySpec);
		} else if (algorithm.equals(AES)) {
			// 128 bit
			btKey = StringUtils.leftPad(key, 16, "0").getBytes();
			KeyGenerator kgen = KeyGenerator.getInstance(algorithm);
			kgen.init(128);
			secretKey = new SecretKeySpec(btKey, algorithm);
		} else if (algorithm.equals(AES128_CTR)) {
			btKey = toBytesFromHexString(AES128_CTR_KEY[NumberUtils.toInt(key)]);
			secretKey = new SecretKeySpec(btKey, "AES");
		} else if (algorithm.equals(AES128_CTR_NONCE)) {
			btKey = toBytesFromHexString(AES128_CTR_KEY_NONCE[NumberUtils.toInt(key)]);
			secretKey = new SecretKeySpec(btKey, "AES");
		} else {
			// throw new BaseException("지원하지 않는 암호화 algorithm 입니다.(AES128-CTR,AES,DES,DESede,TripleDES 지원)", "02004");
		}

		return secretKey;
	}

	/**
	 * 암호화 알고리즘에 따른 암호화
	 * 
	 * @param algorithm
	 *            암호화 알고리즘
	 * @param key
	 *            비밀키
	 * @param target
	 *            암호화 대상 문자열
	 * 
	 * @return 암호화 된 문자열
	 */
	public static String encrypt(String algorithm, String key, String target) {

		String sEncrypt = "";
		try {
			Key secretKey = keygen(algorithm, key);
			Cipher cipher;
			if (algorithm.equals(AES128_CTR)) {
				cipher = Cipher.getInstance("AES/CTR/NoPadding");
				IvParameterSpec ivSpec = new IvParameterSpec(toBytesFromHexString(AES128_CTR_IV_PARAM));
				cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
			} else if (algorithm.equals(AES128_CTR_NONCE)) {
				cipher = Cipher.getInstance("AES/CTR/NoPadding");
				IvParameterSpec ivSpec = new IvParameterSpec(toBytesFromHexString(AES128_CTR_IV_PARAM_NONCE));
				cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
			} else {
				cipher = Cipher.getInstance(algorithm + BLOCK_ECB_PKCS5);
				cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			}

			sEncrypt = toHexString(cipher.doFinal(target.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sEncrypt;
	}

	/**
	 * 암호화 알고리즘에 따른 복호화
	 * 
	 * @param algorithm
	 *            암호화 알고리즘
	 * @param key
	 *            비밀키
	 * @param target
	 *            복호화 대상 문자열
	 * 
	 * @return 복호화 된 문자열
	 */
	public static String decrypt(String algorithm, String key, String target) {

		String sEncrypt = "";
		try {
			Key secretKey = keygen(algorithm, key);
			Cipher cipher;
			if (algorithm.equals(AES128_CTR)) {
				cipher = Cipher.getInstance("AES/CTR/NoPadding");
				IvParameterSpec ivSpec = new IvParameterSpec(toBytesFromHexString(AES128_CTR_IV_PARAM));
				cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
			} else if (algorithm.equals(AES128_CTR_NONCE)) {
				cipher = Cipher.getInstance("AES/CTR/NoPadding");
				IvParameterSpec ivSpec = new IvParameterSpec(toBytesFromHexString(AES128_CTR_IV_PARAM_NONCE));
				cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
			} else {
				cipher = Cipher.getInstance(algorithm + BLOCK_ECB_PKCS5);
				cipher.init(Cipher.DECRYPT_MODE, secretKey);
			}
			sEncrypt = new String(cipher.doFinal(toBytesFromHexString(target)));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sEncrypt;
	}

	/**
	 * <p>
	 * 16진수 문자열을 바이트 배열로 변환한다.
	 * </p>
	 * <p>
	 * 문자열의 2자리가 하나의 byte로 바뀐다.
	 * </p>
	 * 
	 * <pre>
	 * CryptUtils.toBytesFromHexString(null)     		   = null
	 * CryptUtils.toBytesFromHexString(&quot;0E1F4E&quot;) = [0x0e, 0xf4, 0x4e]
	 * CryptUtils.toBytesFromHexString(&quot;48414e&quot;) = [0x48, 0x41, 0x4e]
	 * </pre>
	 * 
	 * @param digits
	 *            16진수 문자열
	 * @return byte 배열
	 * @throws IllegalArgumentException
	 * @throws NumberFormatException
	 */
	public static byte[] toBytesFromHexString(String digits) throws IllegalArgumentException, NumberFormatException {
		if (digits == null) {
			return null;
		}
		int length = digits.length();
		if (length % 2 == 1) {
			throw new IllegalArgumentException("For input string: \"" + digits + "\"");
		}
		length = length / 2;
		byte[] bytes = new byte[length];
		for (int i = 0; i < length; i++) {
			int index = i * 2;
			bytes[i] = (byte) (Short.parseShort(digits.substring(index, index + 2), 16));
		}
		return bytes;
	}

	/**
	 * <p>
	 * unsigned byte(바이트) 배열을 16진수 문자열로 바꾼다.
	 * </p>
	 * 
	 * <pre>
	 * CryptUtils.toHexString(null)                 = null
	 * CryptUtils.toHexString([(byte)1, (byte)255]) = &quot;01ff&quot;
	 * </pre>
	 * 
	 * @param bytes
	 *            unsigned byte's array
	 * @return 16진수 문자열
	 */
	public static String toHexString(byte[] bytes) {
		if (bytes == null) {
			return null;
		}

		StringBuffer result = new StringBuffer();
		for (byte b : bytes) {
			result.append(Integer.toString((b & 0xF0) >> 4, 16));
			result.append(Integer.toString(b & 0x0F, 16));
		}
		return result.toString();
	}

	public static String getNonceKey(String index) {
		return AES128_CTR_KEY_NONCE[NumberUtils.toInt(index)];
	}

	public static String getNonceIv() {
		return AES128_CTR_IV_PARAM_NONCE;
	}
}
