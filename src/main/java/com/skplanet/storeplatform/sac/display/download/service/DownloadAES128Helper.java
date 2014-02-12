package com.skplanet.storeplatform.sac.display.download.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
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
	final private int KEY_SIZE = 16; // 128bit

	@Value("#{propertiesForSac['display.forDownload.encrypt.key'].split(',')}")
	private List<String> SAC_KEY;

	@Value("#{propertiesForSac['display.forDownload.encrypt.dl.iv']}")
	private String SAC_DL_IV;

	private int RANDOM_NUMBER;

	public byte[] genRandomKey() {
		final byte[] key = new byte[this.KEY_SIZE];
		SecureRandom sr = new SecureRandom();
		sr.nextBytes(key);
		return key;
	}

	public String toHexString(byte[] b) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < b.length; i++) {
			sb.append(Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}

	public byte[] convertBytes(String hex) {
		return new java.math.BigInteger(hex, 16).toByteArray();
	}

	public byte[] getDigest(byte[] b) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		return md.digest(b);
	}

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
			this.setRANDOM_NUMBER(randomNumber);

			row = this.convertBytes(this.SAC_KEY.get(randomNumber));
			iv = this.convertBytes(this.SAC_DL_IV);

			System.out.println(row);

			ivSpec = new IvParameterSpec(iv);
			key = new SecretKeySpec(row, "AES");

			System.out.println(key);

			cipher = Cipher.getInstance("AES/CTR/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
			rtnStrByte = cipher.doFinal(b);
		} catch (Exception e) {
			throw new StorePlatformException("SAC_DSP_0001", "데이터 암호화 ", e);
		}

		return rtnStrByte;

	}

	public byte[] decryption(byte[] b) {
		byte[] row = null;
		byte[] iv = null;
		byte[] rtnStrByte = null;

		IvParameterSpec ivSpec = null;
		SecretKeySpec key = null;
		Cipher cipher = null;

		try {
			row = this.convertBytes(this.SAC_KEY.get(this.getRANDOM_NUMBER()));
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
	 * @return the rANDOM_NUMBER
	 */
	public int getRANDOM_NUMBER() {
		return this.RANDOM_NUMBER;
	}

	/**
	 * @param rANDOM_NUMBER
	 *            the rANDOM_NUMBER to set
	 */
	public void setRANDOM_NUMBER(int rANDOM_NUMBER) {
		this.RANDOM_NUMBER = rANDOM_NUMBER;
	}
}
