package com.skplanet.storeplatform.sac.member.common.idp.service;

import java.util.HashMap;
import java.util.Map;

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

import com.skplanet.storeplatform.external.client.idp.vo.IdpReceiverM;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.member.common.util.TestConvertMapperUtils;

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class IdpServicePostTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(IdpServicePostTest.class);

	@Autowired
	private IdpService service;

	private static IdpReceiverM receiverM;

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Before
	public void before() {
		// Return 객체 생성
		receiverM = new IdpReceiverM();
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@After
	public void after() {
		// Debug
		LOGGER.debug("[IDP-REPONSE] : \n{}", TestConvertMapperUtils.convertObjectToJson(receiverM));
	}

	@Test
	public void testWarterMarkAuth() {
		try {
			receiverM = this.service.warterMarkAuth("adad", "12dad", "adwd2d2");
		} catch (StorePlatformException e) {
			LOGGER.debug("ErrorInfo : \n{}", e.getErrorInfo().toString());
		}
	}

	@Test
	public void testUserAuthForId() {
		try {
			receiverM = this.service.userAuthForId("sacsimpleuser020691", "abcd1234");
		} catch (StorePlatformException e) {
			LOGGER.debug("ErrorInfo : \n{}", e.getErrorInfo().toString());
		}
	}

	@Test
	public void testAuthPwd() {
		try {
			receiverM = this.service.authPwd("abc", "1234");
		} catch (StorePlatformException e) {
			LOGGER.debug("ErrorInfo : \n{}", e.getErrorInfo().toString());
		}
	}

	@Test
	public void testModifyAuthInfo() {
		try {
			// 1=id, 2=Email, 3=User Key, default=1
			receiverM = this.service.modifyAuthInfo("adaw32edwad2", "2", "wad00001@wad.com");
		} catch (StorePlatformException e) {
			LOGGER.debug("ErrorInfo : \n{}", e.getErrorInfo().toString());
		}
	}

	@Test
	public void testModifyProfile() {
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("user_auth_key", ""); // IDP 인증키
			param.put("key_type", "2");
			param.put("key", ""); // MBR_NO
			param.put("user_sex", ""); // 성별
			param.put("user_birthday", ""); // 생년월일
			param.put("user_calendar", ""); // 양력1, 음력2
			param.put("user_zipcode", ""); // 우편번호
			param.put("user_address", ""); // 주소
			param.put("user_address2", ""); // 상세주소
			param.put("user_tel", ""); // 사용자 연락처
			receiverM = this.service.modifyProfile(param);
		} catch (StorePlatformException e) {
			LOGGER.debug("ErrorInfo : \n{}", e.getErrorInfo().toString());
		}
	}

	@Test
	public void testSecedeUser() {
		try {
			receiverM = this.service.secedeUser("", "", "");
		} catch (StorePlatformException e) {
			LOGGER.debug("ErrorInfo : \n{}", e.getErrorInfo().toString());
		}
	}

	@Test
	public void testJoin4Wap() {
		try {
			receiverM = this.service.join4Wap("01088870008", "SKT");
		} catch (StorePlatformException e) {
			LOGGER.debug("ErrorInfo : \n{}", e.getErrorInfo().toString());
		}
	}

	@Test
	public void testAuthForWap() {
		try {
			receiverM = this.service.authForWap("01088870008");
		} catch (StorePlatformException e) {
			LOGGER.debug("ErrorInfo : \n{}", e.getErrorInfo().toString());
		}
	}

}
