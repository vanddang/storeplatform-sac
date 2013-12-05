/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.integration.api.test.signature;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;

import org.junit.Test;

/**
 * 
 * 인증키 테스트
 * 
 * Updated on : 2013. 11. 18. Updated by : 김현일, 인크로스.
 */
public class SignatureTest {

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @throws NoSuchAlgorithmException
	 *             NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 *             InvalidKeyException
	 * @throws UnsupportedEncodingException
	 *             UnsupportedEncodingException
	 */
	@Test
	public void hmac() throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
		String key = Signature.calculateMD5(UUID.randomUUID().toString());
		// key, (상용/테스트 구분 + systemID + 만료시간 + reply attack)
		String data = "data";
		String date = new Date().toString();
		// 로직 요소.
		// 1. systemID(access Key ID) 로 해당 유저의 ScretKey를 가져오는 부분
		// 2. 유저의 정보를 위와같이 조합후 hash 값을 비교
		// 3. reply attack을 막기위해서 요청 시간을 다시 비교 ( 정책적 요소 필요 ) 최소, 최대.
		// 4. 암호화 / 복호화에 따른 성능 저하...
		// StopWatch sw = new StopWatch();
		// sw.start();
		// for (int i = 0; i < 20000; i++) {
		System.out.println(Signature.calculateRFC2104HMAC(key, data + date));
		// }
		// sw.stop();
		// System.out.println(sw.getTotalTimeSeconds());

	}

}
