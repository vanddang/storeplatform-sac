/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.user.controller;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * 모든 jUnit 테스트 케이스를 실행한다.
 * 
 * Updated on : 2014. 2. 20. Updated by : 심대진, 다모아 솔루션.
 */
@ActiveProfiles(value = "local")
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@RunWith(Suite.class)
@Suite.SuiteClasses({
		/** 회원 정보 수정 테스트 */
		ModifyTest.class,

		/** 비밀번호 수정 테스트 */
		ModifyPasswordTest.class,

		/** 이메일 주소 수정 테스트 */
		ModifyEmailTest.class,

		/** Store 약관동의 테스트 */
		CreateTermsAgreementTest.class,
		ModifyTermsAgreementTest.class,

		/** 실명인증정보 등록 테스트 */
		CreateRealNameTest.class,

		/** OCB 정보 테스트 */
		CreateOcbInformationTest.class,
		GetOcbInformationTest.class,
		RemoveOcbInformationTest.class,

		/** DeviceID를 이용하여 회원 정보 조회 테스트 */
		DetailByDeviceIdTest.class,

		/** 회원 계정잠금 테스트 */
		LockAccountTest.class
})
// @Ignore
public class AllJunitTest_Simdaejin {

	@BeforeClass
	public static void 전체Junit테스트START() {

		System.out.println(">> START");

	}

	@AfterClass
	public static void 전체Junit테스트END() {

		System.out.println(">> END");

	}

}
