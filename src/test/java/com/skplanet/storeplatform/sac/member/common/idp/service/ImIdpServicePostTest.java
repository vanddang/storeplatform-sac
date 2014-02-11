package com.skplanet.storeplatform.sac.member.common.idp.service;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class ImIdpServicePostTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ImIdpServicePostTest.class);
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
	 * 2.1.4. 개별약관해지요청.
	 * </pre>
	 */
	@Test
	public void testDiscardUser() {
		try {
			this.service.discardUser(new HashMap<String, Object>());
		} catch (StorePlatformException e) {
			LOGGER.debug("EC_IDP_ERROR -> \nCODE : {}, MESSAGE : {}", e.getErrorInfo().getCode(), e.getErrorInfo()
					.getMessage());
		}
	}

	/**
	 * <pre>
	 * 2.1.7 공통프로파일정보수정요청.
	 * </pre>
	 */
	@Test
	public void testUpdateUserInfo() {
		try {
			this.service.updateUserInfo(new HashMap<String, Object>());
		} catch (StorePlatformException e) {
			LOGGER.debug("EC_IDP_ERROR -> \nCODE : {}, MESSAGE : {}", e.getErrorInfo().getCode(), e.getErrorInfo()
					.getMessage());
		}
	}

	/**
	 * <pre>
	 * 2.1.8 부가프로파일정보수정요청.
	 * </pre>
	 */
	@Test
	public void testUpdateAdditionalInfo() {
		try {
			this.service.updateAdditionalInfo(new HashMap<String, Object>());
		} catch (StorePlatformException e) {
			LOGGER.debug("EC_IDP_ERROR -> \nCODE : {}, MESSAGE : {}", e.getErrorInfo().getCode(), e.getErrorInfo()
					.getMessage());
		}
	}

	/**
	 * <pre>
	 * 2.1.9 비밀번호변경요청.
	 * </pre>
	 */
	@Test
	public void testModifyPwd() {
		try {
			this.service.modifyPwd(new HashMap<String, Object>());
		} catch (StorePlatformException e) {
			LOGGER.debug("EC_IDP_ERROR -> \nCODE : {}, MESSAGE : {}", e.getErrorInfo().getCode(), e.getErrorInfo()
					.getMessage());
		}
	}

	/**
	 * <pre>
	 * 2.1.24 실명변경 요청.
	 * </pre>
	 */
	@Test
	public void testUpdateUserName() {
		try {
			this.service.updateUserName(new HashMap<String, Object>());
		} catch (StorePlatformException e) {
			LOGGER.debug("EC_IDP_ERROR -> \nCODE : {}, MESSAGE : {}", e.getErrorInfo().getCode(), e.getErrorInfo()
					.getMessage());
		}
	}

	/**
	 * <pre>
	 * 2.1.25 법정대리인 동의정보 변경 요청.
	 * </pre>
	 */
	@Test
	public void testUpdateGuardian() {
		try {
			this.service.updateGuardian(new HashMap<String, Object>());
		} catch (StorePlatformException e) {
			LOGGER.debug("EC_IDP_ERROR -> \nCODE : {}, MESSAGE : {}", e.getErrorInfo().getCode(), e.getErrorInfo()
					.getMessage());
		}
	}

}
