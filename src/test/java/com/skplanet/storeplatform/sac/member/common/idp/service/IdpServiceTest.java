package com.skplanet.storeplatform.sac.member.common.idp.service;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * IDP API Service
 * 
 * Updated on : 2014. 2. 7. Updated by : 김경복, 부르칸
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class IdpServiceTest {

	@Autowired
	private IdpService service;

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Before
	public void before() {

	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@After
	public void after() {

	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test
	public void testAlredyJoinCheckByEmail() {
		fail("Not yet implemented");
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test
	public void testCheckDupID() {
		fail("Not yet implemented");
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test
	public void testWarterMarkImageUrl() {
		fail("Not yet implemented");
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test
	public void testWarterMarkAuth() {
		fail("Not yet implemented");
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test
	public void testDeviceCompare() {
		fail("Not yet implemented");
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test
	public void testSimpleJoin() {
		fail("Not yet implemented");
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test
	public void testUserAuthForId() {
		fail("Not yet implemented");
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test
	public void testAuthPwd() {
		fail("Not yet implemented");
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test
	public void testSearchUserCommonInfo() {
		fail("Not yet implemented");
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test
	public void testSearchSpecialProfile() {
		fail("Not yet implemented");
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test
	public void testModifyAuthInfo() {
		fail("Not yet implemented");
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test
	public void testModifyProfile() {
		fail("Not yet implemented");
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test
	public void testSecedeUser() {
		fail("Not yet implemented");
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test
	public void testAleadyJoinCheckForMdn() {
		fail("Not yet implemented");
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test
	public void testJoin4Wap() {
		fail("Not yet implemented");
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test
	public void testAuthForWap() {
		fail("Not yet implemented");
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test
	public void testFindProfileForWap() {
		fail("Not yet implemented");
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test
	public void testSecedeUser4Wap() {
		fail("Not yet implemented");
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test
	public void testJoinSupService() {
		fail("Not yet implemented");
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test
	public void testSecedeSupService() {
		fail("Not yet implemented");
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test
	public void testServiceSubscriptionCheck() {
		fail("Not yet implemented");
	}

}
