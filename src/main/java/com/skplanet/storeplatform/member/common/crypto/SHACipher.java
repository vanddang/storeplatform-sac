/*
 * Copyright (c) 2013 SK planet.
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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.skplanet.storeplatform.member.client.common.constant.Constant;
import com.skplanet.storeplatform.member.client.common.util.Utils;

/**
 * The Class SHACipher.
 */
public class SHACipher extends KeyCipher {

	/**
	 * Instantiates a new sHA cipher.
	 * 
	 * @param algorithm
	 *            the algorithm
	 * @param charset
	 *            the charset
	 * @param size
	 *            the size
	 */
	public SHACipher(String algorithm, String charset, int size) {
		super(algorithm, charset, size);
	}

	/**
	 * Instantiates a new sHA cipher.
	 */
	public SHACipher() {
		super(Constant.DEFAULT_HASH_ALGORITHM, Constant.DEFAULT_CHARSET, Constant.DEFAULT_HASH_SZ);
	}

	/**
	 * Generate salt key.
	 * 
	 * @return the string
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 */
	public String generateSaltKey() throws NoSuchAlgorithmException {
		return Utils.byteToBase64(this.generateKey());
	}

	/**
	 * Gets the md5.
	 * 
	 * @param password
	 *            the password
	 * @return the md5
	 * @throws UnsupportedEncodingException
	 *             the unsupported encoding exception
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 */
	public String getMD5(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("MD5");
		digest.reset();

		return Utils.byteToBase64(digest.digest(password.getBytes(this.charset)));
	}

	/**
	 * Gets the hash.
	 * 
	 * @param password
	 *            the password
	 * @return the hash
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 * @throws UnsupportedEncodingException
	 *             the unsupported encoding exception
	 */
	public String getHash(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {

		return this.getHash(this.getMD5(password), (String) null);
	}

	/**
	 * Gets the hash.
	 * 
	 * @param password
	 *            the password
	 * @param salt
	 *            the salt
	 * @return the hash
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 * @throws UnsupportedEncodingException
	 *             the unsupported encoding exception
	 */
	public String getHash(String password, byte[] salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		// this.getMD5(password);
		// password = this.getMD5(password);
		MessageDigest digest = MessageDigest.getInstance(this.algorithm);

		digest.reset();
		if (salt != null)
			digest.update(salt);

		return Utils.byteToBase64(digest.digest(password.getBytes(this.charset)));
	}

	/**
	 * Gets the hash.
	 * 
	 * @param password
	 *            the password
	 * @param salt
	 *            the salt
	 * @return the hash
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 * @throws UnsupportedEncodingException
	 *             the unsupported encoding exception
	 */
	public String getHash(String password, String salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest digest = MessageDigest.getInstance(this.algorithm);

		digest.reset();
		if (salt != null)
			digest.update(salt.getBytes());

		return Utils.byteToBase64(digest.digest(password.getBytes(this.charset)));
	}

	/**
	 * Compare.
	 * 
	 * @param plantext
	 *            the plantext
	 * @param hashtext
	 *            the hashtext
	 * @return true, if successful
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 * @throws UnsupportedEncodingException
	 *             the unsupported encoding exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public boolean compare(String plantext, String hashtext) throws NoSuchAlgorithmException,
			UnsupportedEncodingException, IOException {
		return this.compareKeyData(this.getHash(plantext), hashtext);
	}

	/**
	 * Compare.
	 * 
	 * @param plantext
	 *            the str plan text
	 * @param salt
	 *            the salt
	 * @param hashtext
	 *            the str hash text
	 * @return true, if successful
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 * @throws UnsupportedEncodingException
	 *             the unsupported encoding exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public boolean compare(String plantext, byte[] salt, String hashtext) throws NoSuchAlgorithmException,
			UnsupportedEncodingException, IOException {
		return this.compareKeyData(this.getHash(plantext, salt), hashtext);
	}

	/**
	 * Compare.
	 * 
	 * @param plantext
	 *            the plantext
	 * @param salt
	 *            the salt
	 * @param hashtext
	 *            the hashtext
	 * @return true, if successful
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 * @throws UnsupportedEncodingException
	 *             the unsupported encoding exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public boolean compare(String plantext, String salt, String hashtext) throws NoSuchAlgorithmException,
			UnsupportedEncodingException, IOException {
		return this.compareKeyData(this.getHash(plantext, salt), hashtext);
	}
}
