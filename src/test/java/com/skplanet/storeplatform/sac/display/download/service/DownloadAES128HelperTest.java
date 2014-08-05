/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.download.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <p>
 * DESC
 * </p>
 * Updated on : 2014. 08. 04 Updated by : 양해엽, SK 플래닛.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class DownloadAES128HelperTest {

    private static final Logger logger = LoggerFactory.getLogger(DownloadAES128HelperTest.class);

    @Autowired
    private DownloadAES128Helper downloadAES128Helper;


    @Before
    public void init() {


    }

    @Test
    public void testDecryption() {

    	int keyIdx = 23;
    	String token = "e262a158604416cae248d9a64cdecbc8bb6fc9fdc75462d116b5e842e344184f478f3dd72ce3fb19dc298912cf70d773f9e4e00fe96d17bbc309f577cd3a07507f1280653e54e2d8c397ac972c8f6d739e5319841e8f8cce48279588885f79283daac5f876284a9828d48447048d896c57ed8e9c6e46c4a7e8f0cf4f7ea0716dc0e083b88d5c4f160217d5829dc57c4fa6e4255bccca29f43165158620bed914ee3c06fd675e6bda4765f5dee98a2f20cfd11046c945dbbd0221381c9b6a72c9ee9e743ff3b220e887e0d7c811eb474c40712a44e059e8c5763fa4e5f0bcf53705b1018b530f9e764ed58f8ac791ab738bd714f511c1abdc6765828f3044aa5151587a0c6043df3351ec70194268f2cad904b51543d08a0303d35a4591929c62198f15358151c262ff35c51e86660a7194491f22b9e8f824fd33cb4f4be9d00ea13daca2c800b96ececbeffc6f548211cee48dd2effe98e68e6585484554468290ce1c1d249b6b28984a8efb694587a3d6090c9dc0c611236de49d923d9147d0bedc82e20f6f692128eee75a7679849e268117827c0cdb7175b03d7d028ddff5f717910b2fd02acd860dd65e867a4b68602f505d4bc40bce60d28bb05c665e5a9d32ec7f64f94ea313a3fa9379d7088cd777d6fe1e9da1746a71275fe64e2290234e2f08868fb439f53a83d631a67cd1848547bc7a28edc06f471c7d2287040bb92829b199abf0ff141b21d392e5af650e349c8cdf31a704b4946cb2b26a91e6cb2d0279640387911b44185c9068684090af6487bdf924d07cd0de9ab27dc0aa9da6c82c6f9092c3202248a9cdc37e82f6f33b9ffed1ee6523760017631a86ef68eb23eed88afebbea7b993800d8ab1999bf53ab310f2205f8b292a5d94cbb4fa545d70158a9bbdc42ed6af2c7964ee5e84c05fa3eef405d35ee9f83ffdcb497276507a62111e46298afc633e233d52274384d18e5b994a8ce0efda00e60cc7da7818ce9b6b7";

    	byte [] data = this.downloadAES128Helper.convertBytes(token);

    	byte [] decryption = this.downloadAES128Helper.decryption(keyIdx, data);

    	try {

			String result = new String(decryption, "UTF-8");

			logger.info(String.format("%s", result));

		} catch (Exception e) {
			e.printStackTrace();
		}
    }

}
