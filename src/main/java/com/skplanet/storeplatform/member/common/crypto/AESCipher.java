/*
 * Copyright (c) 2014 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.common.crypto;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.skplanet.storeplatform.member.client.common.constant.Constant;
import com.skplanet.storeplatform.member.client.common.util.Utils;

// TODO: Auto-generated Javadoc
/**
 * The Class AESCipher.
 */
public class AESCipher extends KeyCipher {

	/** The secure key. */
	SecretKey secureKey;

	/**
	 * Instantiates a new aES cipher.
	 * 
	 * @param algorithm
	 *            the algorithm
	 * @param charset
	 *            the charset
	 * @param size
	 *            the size
	 * @throws InvalidAlgorithmParameterException
	 *             Invalid Algorithm Parameter Exception
	 * @throws NoSuchPaddingException
	 *             No Such Padding Exception
	 * @throws NoSuchAlgorithmException
	 *             No Such Algorithm Exception
	 * @throws InvalidKeyException
	 *             Invalid Key Exception
	 */
	public AESCipher(String algorithm, String charset, int size) throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidAlgorithmParameterException {
		super(algorithm, charset, size);
		this.generateSecretKey();
	}

	/**
	 * Instantiates a new aES cipher.
	 * 
	 * @param key
	 *            the key
	 * @throws InvalidKeyException
	 *             the invalid key exception
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 * @throws NoSuchPaddingException
	 *             the no such padding exception
	 * @throws InvalidAlgorithmParameterException
	 *             the invalid algorithm parameter exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public AESCipher(String key) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, IOException {
		super(Constant.DEFAULT_CRYPTO_ALGORITHM, Constant.DEFAULT_CHARSET, Constant.DEFAULT_CRYPTO_SZ);
		this.secureKey = new SecretKeySpec(Utils.base64ToByte(key), this.algorithm);
	}

	/**
	 * Generate secret key.
	 * 
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 * @throws NoSuchPaddingException
	 *             the no such padding exception
	 * @throws InvalidKeyException
	 *             the invalid key exception
	 * @throws InvalidAlgorithmParameterException
	 *             the invalid algorithm parameter exception
	 */
	public void generateSecretKey() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException {
		this.secureKey = new SecretKeySpec(this.generateKey(), this.algorithm);
	}

	/**
	 * AES encrypt.
	 * 
	 * @param str
	 *            the str
	 * @return the byte[]
	 * @throws NoSuchPaddingException
	 *             No Such Padding Exception
	 * @throws NoSuchAlgorithmException
	 *             No Such Algorithm Exception
	 * @throws InvalidAlgorithmParameterException
	 *             Invalid Algorithm Parameter Exception
	 * @throws InvalidKeyException
	 *             Invalid Key Exception
	 * @throws UnsupportedEncodingException
	 *             Unsupported Encoding Exception
	 * @throws BadPaddingException
	 *             Bad Padding Exception
	 * @throws IllegalBlockSizeException
	 *             Illegal BlockSize Exception
	 */
	public byte[] encrypt(String str) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException,
			UnsupportedEncodingException {
		Cipher c = Cipher.getInstance(Constant.DEFAULT_CRYPTO_FORMAT);
		c.init(Cipher.ENCRYPT_MODE, this.secureKey, new IvParameterSpec(this.secureKey.getEncoded()));

		byte[] encrypted = c.doFinal(str.getBytes(this.charset));

		return (encrypted);
	}

	/**
	 * AES encode.
	 * 
	 * @param str
	 *            the str
	 * @return the byte[]
	 * @throws NoSuchPaddingException
	 *             No Such Padding Exception
	 * @throws NoSuchAlgorithmException
	 *             No Such Algorithm Exception
	 * @throws InvalidAlgorithmParameterException
	 *             Invalid Algorithm Parameter Exception
	 * @throws InvalidKeyException
	 *             Invalid Key Exception
	 * @throws UnsupportedEncodingException
	 *             Unsupported Encoding Exception
	 * @throws BadPaddingException
	 *             Bad Padding Exception
	 * @throws IllegalBlockSizeException
	 *             Illegal BlockSize Exception
	 */
	public String encode(String str) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException,
			UnsupportedEncodingException {
		return (Utils.byteToBase64(this.encrypt(str)));
	}

	/**
	 * AES decrypt.
	 * 
	 * @param str
	 *            the str
	 * @return the byte[]
	 * @throws NoSuchPaddingException
	 *             No Such Padding Exception
	 * @throws NoSuchAlgorithmException
	 *             No Such Algorithm Exception
	 * @throws InvalidAlgorithmParameterException
	 *             Invalid Algorithm Parameter Exception
	 * @throws InvalidKeyException
	 *             Invalid Key Exception
	 * @throws IOException
	 *             IO Exception
	 * @throws BadPaddingException
	 *             Bad Padding Exception
	 * @throws IllegalBlockSizeException
	 *             Illegal BlockSize Exception
	 */
	public byte[] decrypt(String str) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IOException, IllegalBlockSizeException, BadPaddingException {
		Cipher c = Cipher.getInstance(Constant.DEFAULT_CRYPTO_FORMAT);
		c.init(Cipher.DECRYPT_MODE, this.secureKey, new IvParameterSpec(this.secureKey.getEncoded()));

		byte[] decrypted = Utils.base64ToByte(str);
		return c.doFinal(decrypted);
	}

	/**
	 * AES decode.
	 * 
	 * @param str
	 *            the str
	 * @return the byte[]
	 * @throws NoSuchPaddingException
	 *             No Such Padding Exception
	 * @throws NoSuchAlgorithmException
	 *             No Such Algorithm Exception
	 * @throws InvalidAlgorithmParameterException
	 *             Invalid Algorithm Parameter Exception
	 * @throws InvalidKeyException
	 *             Invalid Key Exception
	 * @throws UnsupportedEncodingException
	 *             Unsupported Encoding Exception
	 * @throws BadPaddingException
	 *             Bad Padding Exception
	 * @throws IllegalBlockSizeException
	 *             Illegal BlockSize Exception
	 * @throws IOException
	 *             IO Exception
	 */
	public String decode(String str) throws InvalidKeyException, UnsupportedEncodingException,
			NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException, IOException {
		return (new String(this.decrypt(str), this.charset));
	}

	/**
	 * Compare.
	 * 
	 * @param encrypttext
	 *            the encrypttext
	 * @param plaintext
	 *            the plaintext
	 * @return true, if successful
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 * @throws UnsupportedEncodingException
	 *             the unsupported encoding exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws InvalidKeyException
	 *             the invalid key exception
	 * @throws NoSuchPaddingException
	 *             the no such padding exception
	 * @throws InvalidAlgorithmParameterException
	 *             the invalid algorithm parameter exception
	 * @throws IllegalBlockSizeException
	 *             the illegal block size exception
	 * @throws BadPaddingException
	 *             the bad padding exception
	 */
	public boolean compare(String encrypttext, String plaintext) throws NoSuchAlgorithmException,
			UnsupportedEncodingException, IOException, InvalidKeyException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		return this.compareKeyData(this.decode(encrypttext), plaintext);
	}
}
