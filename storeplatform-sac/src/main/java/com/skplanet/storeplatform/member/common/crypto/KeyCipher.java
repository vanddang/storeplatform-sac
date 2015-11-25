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
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import com.skplanet.storeplatform.member.client.common.constant.Constant;

// TODO: Auto-generated Javadoc
/**
 * The Class KeyCipher.
 */
public class KeyCipher {

	/** The algorithm. */
	String algorithm;

	/** The charset. */
	String charset;

	/** The keyLenghth. */
	int keyLength;

	/**
	 * Instantiates a new key cipher.
	 * 
	 * @param algorithm
	 *            the algorithm
	 * @param charset
	 *            the charset
	 * @param size
	 *            the size
	 */
	public KeyCipher(String algorithm, String charset, int size) {
		this.setAlgorithm(algorithm);
		this.setKeyLength(size);
		this.setCharset(charset);
	}

	/**
	 * Sets the key length.
	 * 
	 * @param size
	 *            the new key length
	 */
	public void setKeyLength(int size) {
		this.keyLength = size;
	}

	/**
	 * Sets the algorithm.
	 * 
	 * @param algorithm
	 *            the new algorithm
	 */
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	/**
	 * Sets the charset.
	 * 
	 * @param charset
	 *            the new charset
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}

	/**
	 * Generate key.
	 * 
	 * @return the byte[]
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 */
	public byte[] generateKey() throws NoSuchAlgorithmException {
		return this.generateKey(this.keyLength);
	}

	/**
	 * Generate key.
	 * 
	 * @param size
	 *            the size
	 * @return the byte[]
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 */
	public byte[] generateKey(int size) throws NoSuchAlgorithmException {
		byte[] bKey = new byte[size];

		SecureRandom random = this.generateRandom(Constant.DEFAULT_RANDOM_ALGORITHM);
		random.nextBytes(bKey);

		return bKey;
	}

	/**
	 * Generate random.
	 * 
	 * @param rand
	 *            the rand
	 * @return the secure random
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 */
	public SecureRandom generateRandom(String rand) throws NoSuchAlgorithmException {
		SecureRandom random = SecureRandom.getInstance(rand);

		return random;
	}

	/**
	 * Compare key data.
	 * 
	 * @param key
	 *            the key
	 * @param data
	 *            the data
	 * @return true, if successful
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public boolean compareKeyData(String key, String data) throws IOException {
		return key.equals(data);
	}
}
