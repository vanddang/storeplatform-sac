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
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class SHACipherTest.
 * 
 * Updated on : 2014. 1. 23 Updated by : wisestone_brian, wisestone
 */
public class SHACipherTest {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The expection. */
	@Rule
	public ExpectedException expection = ExpectedException.none();

	/**
	 * Test get hash string byte array.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testGetHashStringByteArray() throws Exception {
		String strPassword = "test111";
		String strSalt = null;

		SHACipher cipher = new SHACipher();
		strSalt = cipher.generateSaltKey();

		String strHashText = cipher.getHash(strPassword, strSalt);
		assertNotNull(strHashText);
		this.logger.info("\r\n - Data Hash function test");
		this.logger.info("\r\n - strPassword : [{}]", strPassword);
		this.logger.info("\r\n - getMD5 : [{}]", cipher.getMD5(strPassword));
		this.logger.info("\r\n - MD5 to Hash256 : [{}]", cipher.getHash(cipher.getMD5(strPassword), (String) null));
		this.logger.info("\r\n - strSalt : [{}]", strSalt);
		this.logger.info("\r\n - strHashText : [{}]", strHashText);
		this.logger.info("\r\n - Hash Data compare test");
		this.logger.info("\r\n - strHashText : [{}]", strHashText);
		this.logger.info("\r\n - getHash with Salt : [{}]", cipher.getHash(strPassword, strSalt));
		this.logger.info("\r\n - getHash none Salt : [{}]", cipher.getHash(strPassword, (String) null));
		assertEquals(strHashText, cipher.getHash(strPassword, strSalt));
		assertNotSame(strHashText, cipher.getHash(strPassword, (String) null));
		assertTrue(cipher.compare(strPassword, strSalt, strHashText));
	}
}
