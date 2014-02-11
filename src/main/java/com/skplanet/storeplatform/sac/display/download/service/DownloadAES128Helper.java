package com.skplanet.storeplatform.sac.display.download.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;

@Component
public class DownloadAES128Helper {
	final private int KEY_SIZE = 16; // 128bit

	@Value("#{propertiesForSac['display.forDownload.encrypt.key." + DisplayConstants.DP_FORDOWNLOAD_ENCRYPT_KEY + "']}")
	private String SAC_KEY;

	@Value("#{propertiesForSac['display.forDownload.encrypt.dl.iv']}")
	private String SAC_DL_IV;

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
		byte[] rtnStrByte = null;
		byte[] row = this.convertBytes(this.SAC_KEY);
		byte[] iv = this.convertBytes(this.SAC_DL_IV);

		IvParameterSpec ivSpec = new IvParameterSpec(iv);
		SecretKeySpec key = new SecretKeySpec(row, "AES");
		Cipher cipher;

		try {
			cipher = Cipher.getInstance("AES/CTR/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
			rtnStrByte = cipher.doFinal(b);
		} catch (Exception e) {
			throw new StorePlatformException("SAC_DSP_0001", "데이터 암호화 ", e);
		}

		return rtnStrByte;

	}

	public byte[] decryption(byte[] b) {
		byte[] rtnStrByte = null;
		byte[] row = this.convertBytes(this.SAC_KEY);
		byte[] iv = this.convertBytes(this.SAC_DL_IV);

		IvParameterSpec ivSpec = new IvParameterSpec(iv);
		SecretKeySpec key = new SecretKeySpec(row, "AES");
		Cipher cipher;

		try {
			cipher = Cipher.getInstance("AES/CTR/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
			rtnStrByte = cipher.doFinal(b);
		} catch (Exception e) {
			throw new StorePlatformException("SAC_DSP_0001", "데이터 복호화 ", e);
		}

		return rtnStrByte;
	}

	/**
	 * @param args
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		String json = "{\"expired\": \"{YYYYMMDDThhmmssZ}\",\"data\": {\"title\": \"{title}\",\"topCatCd\": \"\",\"catCd\": \"\",\"packetFee\": \"{paid|free}\",\"prodFee\": \"{paid|free}\",\"productId\": \"{product ID}\",\"purchaseId\": \"{purchase ID}\",\"purchaseDate\": \"{YYYYMMDDThhmmssZ}\",\"subContents\" : [{\"scid\": \"{SCID}\",\"path\": \"{NAS path}\"}],\"applyDrm\": \"Y\",\"expirationDate\": \"{YYYYMMDDThhmmssZ}\",\"extrainfo\" : {\"pcPlay\": \"y\",\"bpCode\" : \"00\",\"certKey\":\"xxxxxxx\"},\"userKey\": \"{user ID}\",\"deviceKey\": {\"key\": \"{Device Key}\",\"type\": \"{msisdn|uuid|mac}\",\"subKey\": \"{msisdn | UUID | MAC}\",}}}";
		byte[] digest = this.getDigest(json.getBytes());
		this.toHexString(digest);
		System.out.println("digest: " + this.toHexString(digest));

		try {
			byte[] encrypt = this.encryption(json.getBytes());
			byte[] decrypt = this.decryption(encrypt);
			String j = new String(decrypt, "UTF-8");
			System.out.print(j);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
