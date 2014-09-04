package com.skplanet.storeplatform.sac.display.download.service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.List;
import java.util.Random;

/**
 * DownloadAES128Helper Component
 *
 * Updated on : 2014. 02. 21. Updated by : 이태희.
 */
@Component
public class DownloadAES128Helper {
	@Value("#{propertiesForSac['display.forDownload.encrypt.key'].split(',')}")
	private List<String> sacKey;

	private static final String DIGEST_MD5 = "MD5";
	private static final String DIGEST_MD5_HASH_KEY = "spdl-hash-key";
	private static final String DIGEST_SHA1 = "SHA-1";
	private static final String ALGORITHM = "AES";
	private static final String TRANSFORMATION = "AES/CTR/NoPadding";

	public int getRandomKeyIndex() {

		Random random = new Random();
		return (random.nextInt(this.sacKey.size()));
	}

	public byte[] encryption(int keyIdx, byte[] data) {

		byte[] rtnByte = null;

		byte[] key = this.convertBytes(this.sacKey.get(keyIdx));
		byte[] iv = this.generateIV(key);

		IvParameterSpec ivSpec = new IvParameterSpec(iv);
	    SecretKeySpec spec = new SecretKeySpec(key, ALGORITHM);

	    try {
    	    Cipher cipher = Cipher.getInstance(TRANSFORMATION);
    	    cipher.init(Cipher.ENCRYPT_MODE, spec, ivSpec);
    	    rtnByte = cipher.doFinal(data);
	    } catch (Exception e) {
			throw new StorePlatformException("SAC_DSP_0018");
		}

		return rtnByte;
	}

	public byte[] decryption(int keyIdx, byte[] data) {

		byte[] rtnByte = null;

		byte[] key = this.convertBytes(this.sacKey.get(keyIdx));
		byte[] iv = this.generateIV(key);

		IvParameterSpec ivSpec = new IvParameterSpec(iv);
	    SecretKeySpec spec = new SecretKeySpec(key, ALGORITHM);

	    try {
    	    Cipher cipher = Cipher.getInstance(TRANSFORMATION);
    	    cipher.init(Cipher.DECRYPT_MODE, spec, ivSpec);
    	    rtnByte = cipher.doFinal(data);
	    } catch (Exception e) {
			throw new StorePlatformException("SAC_DSP_0019");
		}

		return rtnByte;
	}


	/**
	 * <pre>
	 * String to Byte.
	 * </pre>
	 *
	 * @param hex
	 *            hex
	 * @return byte[]
	 */
	public byte[] convertBytes(String hex) {
		int byteSize = hex.length() / 2;
		byte[] b = new byte[byteSize];

		for (int i = 0; i < hex.length() / 2; i++) {
			b[i] = (byte) (Integer.parseInt(hex.substring(i * 2, (i * 2) + 2), 16));
		}
		return b;
	}

	/**
	 * <pre>
	 * byte to String.
	 * </pre>
	 *
	 * @param b
	 *            b
	 * @return String
	 */
	public String toHexString(byte[] b) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < b.length; i++) {
			sb.append(Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}

	/**
	 * <pre>
	 * getDigest.
	 * </pre>
	 *
	 * @param b
	 *            b
	 * @return byte[]
	 */
	public byte[] getDigest(byte[] b) {
		MessageDigest md = null;

		try {
			md = MessageDigest.getInstance(DIGEST_SHA1);
		} catch (Exception e) {
			throw new StorePlatformException("SAC_DSP_0020");
		}
		return md.digest(b);
	}

	/**
	 *
	 * <pre>
	 * generateIV
	 * </pre>
	 * @param key
	 * @return
	 */
	public byte [] generateIV(byte [] key) {
		MessageDigest md = null;

		try {
    		md = MessageDigest.getInstance(DIGEST_MD5);
    		md.update(DIGEST_MD5_HASH_KEY.getBytes());
		} catch (Exception e) {
			throw new StorePlatformException("SAC_DSP_0020");
		}

		return md.digest(key);
	}

}
