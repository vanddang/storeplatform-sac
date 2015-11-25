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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skplanet.storeplatform.member.client.common.constant.Constant;
import com.skplanet.storeplatform.member.client.common.util.Utils;

// TODO: Auto-generated Javadoc
/**
 * The Class AESCipherTest.
 */
public class AESCipherTest {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The expection. */
	@Rule
	public ExpectedException expection = ExpectedException.none();

	/**
	 * Test aes cipher.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testAESCipher() throws Exception {
		this.testGenerateSecretKey();
	}

	/**
	 * Test aes cipher string.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testAESCipherString() throws Exception {
		AESCipher cipher = null;
		KeyCipher key = null;
		String sKey = null;

		key = new KeyCipher(Constant.DEFAULT_CRYPTO_ALGORITHM, Constant.DEFAULT_CHARSET, Constant.DEFAULT_CRYPTO_SZ);
		sKey = Utils.byteToBase64(key.generateKey());
		cipher = new AESCipher(sKey);

		assertNotNull(cipher.secureKey);
		assertEquals(sKey, Utils.byteToBase64(cipher.secureKey.getEncoded()));
		this.logger.info("\r\n - Test for equality");
		this.logger.info("\r\n - sKey : [{}] ", sKey);
		this.logger.info("\r\n - secureKey : [{}]", Utils.byteToBase64(cipher.secureKey.getEncoded()));
	}

	/**
	 * Test generate secret key.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testGenerateSecretKey() throws Exception {
		AESCipher cipher = null;

		cipher = new AESCipher(Constant.DEFAULT_CRYPTO_ALGORITHM, Constant.DEFAULT_CHARSET, Constant.DEFAULT_CRYPTO_SZ);

		assertNotNull(cipher.secureKey);
		this.logger.info("\r\n - Key not null test");
		this.logger.info("\r\n - key : [{}]", Utils.byteToBase64(cipher.secureKey.getEncoded()));
	}

	/**
	 * Test aes encode and decode.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testAESEncodeAndDecode() throws Exception {
		String strMessage = "TstoreRefactoring";
		AESCipher cipher = null;
		String strEncode = null;

		cipher = new AESCipher(Constant.DEFAULT_CRYPTO_ALGORITHM, Constant.DEFAULT_CHARSET, Constant.DEFAULT_CRYPTO_SZ);
		strEncode = cipher.encode(strMessage);
		assertNotNull(strEncode);
		this.logger.info("\r\n - Data encrypt and decrypt test");
		this.logger.info("\r\n - strEncode : [{}]", strEncode);
		assertEquals(strMessage, cipher.decode(strEncode));
		this.logger.info("\r\n - strDecode : [{}]", cipher.decode(strEncode));
		this.logger.info("\r\n - Message   : [{}]", strMessage);
		this.logger.info("\r\n - Data compare test");
		assertTrue(cipher.compareKeyData(strMessage, cipher.decode(strEncode)));
		assertTrue(cipher.compare(strEncode, strMessage));
	}
}
