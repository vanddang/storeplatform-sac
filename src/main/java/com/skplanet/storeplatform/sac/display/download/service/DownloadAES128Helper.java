package com.skplanet.storeplatform.sac.display.download.service;

import java.util.List;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;

@Component
public class DownloadAES128Helper {
	@Value("#{propertiesForSac['display.forDownload.encrypt.key'].split(',')}")
	private List<String> SAC_KEY;

	@Value("#{propertiesForSac['display.forDownload.encrypt.dl.iv']}")
	private String SAC_DL_IV;

	private int SAC_RANDOM_NUMBER;

	/**
	 * <pre>
	 * AES128-CTR 암호화.
	 * </pre>
	 * 
	 * @param b
	 *            b
	 * @return byte[]
	 */
	public byte[] encryption(byte[] b) {
		byte[] row = null;
		byte[] iv = null;
		byte[] rtnStrByte = null;

		IvParameterSpec ivSpec = null;
		SecretKeySpec key = null;
		Cipher cipher = null;

		try {
			Random random = new Random();
			int randomNumber = random.nextInt(20);
			this.setSAC_RANDOM_NUMBER(randomNumber);

			row = this.convertBytes(this.SAC_KEY.get(randomNumber));
			iv = this.convertBytes(this.SAC_DL_IV);

			ivSpec = new IvParameterSpec(iv);
			key = new SecretKeySpec(row, "AES");

			cipher = Cipher.getInstance("AES/CTR/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
			rtnStrByte = cipher.doFinal(b);
		} catch (Exception e) {
			throw new StorePlatformException("SAC_DSP_0001", "데이터 암호화 ", e);
		}

		return rtnStrByte;

	}

	/**
	 * <pre>
	 * AES128-CTR 복호화.
	 * </pre>
	 * 
	 * @param b
	 *            b
	 * @return byte[]
	 */
	public byte[] decryption(byte[] b) {
		byte[] row = null;
		byte[] iv = null;
		byte[] rtnStrByte = null;

		IvParameterSpec ivSpec = null;
		SecretKeySpec key = null;
		Cipher cipher = null;

		try {
			row = this.convertBytes(this.SAC_KEY.get(this.getSAC_RANDOM_NUMBER()));
			iv = this.convertBytes(this.SAC_DL_IV);

			ivSpec = new IvParameterSpec(iv);
			key = new SecretKeySpec(row, "AES");

			cipher = Cipher.getInstance("AES/CTR/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
			rtnStrByte = cipher.doFinal(b);
		} catch (Exception e) {
			throw new StorePlatformException("SAC_DSP_0001", "데이터 복호화 ", e);
		}

		return rtnStrByte;
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
	 * @return the sAC_RANDOM_NUMBER
	 */
	public int getSAC_RANDOM_NUMBER() {
		return this.SAC_RANDOM_NUMBER;
	}

	/**
	 * @param sAC_RANDOM_NUMBER
	 *            the sAC_RANDOM_NUMBER to set
	 */
	public void setSAC_RANDOM_NUMBER(int sAC_RANDOM_NUMBER) {
		this.SAC_RANDOM_NUMBER = sAC_RANDOM_NUMBER;
	}
}
