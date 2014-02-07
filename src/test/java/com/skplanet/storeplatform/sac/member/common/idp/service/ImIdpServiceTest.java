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
 * OneID API Service
 * 
 * Updated on : 2014. 2. 7. Updated by : 김경복, 부르칸
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class ImIdpServiceTest {

	/**
	 * ImIdpService 객체.
	 */
	@Autowired
	private ImIdpService service;

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
	public void testAgreeUser() {
		fail("Not yet implemented");
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test
	public void testDiscardUser() {
		fail("Not yet implemented");
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test
	public void testUserInfoSearchServer() {
		fail("Not yet implemented");
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test
	public void testUpdateUserInfo() {
		fail("Not yet implemented");
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test
	public void testUpdateAdditionalInfo() {
		fail("Not yet implemented");
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test
	public void testModifyPwd() {
		fail("Not yet implemented");
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test
	public void testSetLoginStatus() {
		fail("Not yet implemented");
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test
	public void testUpdateUserName() {
		fail("Not yet implemented");
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test
	public void testUpdateGuardian() {
		fail("Not yet implemented");
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test
	public void testAuthForId() {
		fail("Not yet implemented");
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test
	public void testFindJoinServiceListIDP() {
		fail("Not yet implemented");
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test
	public void testCheckIdStatusIdpIm() {
		fail("Not yet implemented");
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test
	public void testGetMdnInfoIDP() {
		fail("Not yet implemented");
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test
	public void testFindUserIdByMdn() {
		fail("Not yet implemented");
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test
	public void testUserInfoIdpSearchServer() {
		fail("Not yet implemented");
	}
}
