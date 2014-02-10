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
import com.skplanet.storeplatform.sac.member.common.idp.repository.IdpRepository;
import com.skplanet.storeplatform.sac.member.common.util.TestConvertMapperUtils;

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

	private static final Logger LOGGER = LoggerFactory.getLogger(IdpServiceTest.class);

	@Autowired
	private IdpService service;

	@Autowired
	private IdpRepository repository;

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

	/**
	 * <pre>
	 * 2.1.2. 서비스 중복 가입 체크. (이메일 기준).
	 * </pre>
	 */
	@Test
	public void testAlredyJoinCheckByEmail() {
		try {
			receiverM = this.service.alredyJoinCheckByEmail("abc@abc.com");
		} catch (StorePlatformException e) {
			LOGGER.debug("ErrorInfo : \n{}", e.getErrorInfo().toString());
			throw e;
		}
	}

	/**
	 * <pre>
	 * 2.1.13. 아이디 중복 체크.
	 * </pre>
	 */
	@Test
	public void testCheckDupID() {
		try {
			receiverM = this.service.checkDupID("abc");
		} catch (StorePlatformException e) {
			LOGGER.debug("ErrorInfo : \n{}", e.getErrorInfo().toString());
			throw e;
		}
	}

	/**
	 * <pre>
	 * 2.1.8. 자동 가입 방지 Image 발급.
	 * </pre>
	 */
	@Test
	public void testWarterMarkImageUrl() {
		try {
			receiverM = this.service.warterMarkImageUrl();
		} catch (StorePlatformException e) {
			LOGGER.debug("ErrorInfo : \n{}", e.getErrorInfo().toString());
			throw e;
		}
	}

	/**
	 * <pre>
	 * 2.1.9. 자동 가입 방지 인증.
	 * [POST]
	 * </pre>
	 */
	@Test
	public void testWarterMarkAuth() {
		try {
			receiverM = this.service.warterMarkAuth("", "", "");
		} catch (StorePlatformException e) {
			LOGGER.debug("ErrorInfo : \n{}", e.getErrorInfo().toString());
			throw e;
		}

	}

	/**
	 * <pre>
	 * 2.1.10. 휴대폰 단말 기종 조회 및 업데이트.
	 * </pre>
	 */
	@Test
	public void testDeviceCompare() {
		try {
			receiverM = this.service.deviceCompare("01088870008");
		} catch (StorePlatformException e) {
			LOGGER.debug("ErrorInfo : \n{}", e.getErrorInfo().toString());
			throw e;
		}
	}

	/**
	 * <pre>
	 * 2.2.7 간편 회원 가입.
	 * </pre>
	 */
	@Test
	public void testSimpleJoin() {
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("user_id", "abcd1234");
			param.put("user_passwd", "1234");
			param.put("user_email", "asdf@asdf.com");
			String user_phone = "01089891234,7890098877,SSLU,SKT";
			param.put("user_phone", user_phone);
			param.put("phone_auth_key", this.repository.makePhoneAuthKey(user_phone));
			receiverM = this.service.simpleJoin(param);
		} catch (StorePlatformException e) {
			LOGGER.debug("ErrorInfo : \n{}", e.getErrorInfo().toString());
			throw e;
		}
	}

	/**
	 * <pre>
	 * 2.3.1. 유선 회원의 로그인.
	 * </pre>
	 */
	@Test
	public void testUserAuthForId() {
		try {
			receiverM = this.service.userAuthForId("abc", "1234");
		} catch (StorePlatformException e) {
			LOGGER.debug("ErrorInfo : \n{}", e.getErrorInfo().toString());
			throw e;
		}
	}

	/**
	 * <pre>
	 * 2.3.2. 유선 회원의 비밀번호 확인.
	 * </pre>
	 */
	@Test
	public void testAuthPwd() {
		try {
			receiverM = this.service.authPwd("abc", "1234");
		} catch (StorePlatformException e) {
			LOGGER.debug("ErrorInfo : \n{}", e.getErrorInfo().toString());
			throw e;
		}
	}

	/**
	 * <pre>
	 * 2.4.2. 기본 Profile 조회 (For SP Server).
	 * </pre>
	 */
	@Test
	public void testSearchUserCommonInfo() {
		try {
			// 1=id, 2=Email, 3=User Key, default=1
			receiverM = this.service.searchUserCommonInfo("3", "01088870008");
		} catch (StorePlatformException e) {
			LOGGER.debug("ErrorInfo : \n{}", e.getErrorInfo().toString());
			throw e;
		}
	}

	/**
	 * <pre>
	 * 2.4.3. 특정 Profile 조회 (For SO Server).
	 * </pre>
	 */
	@Test
	public void testSearchSpecialProfile() {
		try {
			// 1=id, 2=Email, 3=User Key, default=1
			receiverM = this.service.searchSpecialProfile("3", "01088870008");
		} catch (StorePlatformException e) {
			LOGGER.debug("ErrorInfo : \n{}", e.getErrorInfo().toString());
			throw e;
		}
	}

	/**
	 * <pre>
	 * 2.5.1. 인증 정보 변경 API.
	 * </pre>
	 */
	@Test
	public void testModifyAuthInfo() {
		try {
			// 1=id, 2=Email, 3=User Key, default=1
			receiverM = this.service.modifyAuthInfo("adaw32edwad2", "2", "wad00001@wad.com");
		} catch (StorePlatformException e) {
			LOGGER.debug("ErrorInfo : \n{}", e.getErrorInfo().toString());
			throw e;
		}
	}

	/**
	 * <pre>
	 * 2.5.2. 회원 정보 변경.
	 * </pre>
	 */
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
			throw e;
		}
	}

	/**
	 * <pre>
	 * 2.6.1. 회원 해지.
	 * </pre>
	 */
	@Test
	public void testSecedeUser() {
		try {
			receiverM = this.service.secedeUser("", "", "");
		} catch (StorePlatformException e) {
			LOGGER.debug("ErrorInfo : \n{}", e.getErrorInfo().toString());
			throw e;
		}
	}

	/**
	 * 3. MDN 기반서비스 API.
	 */

	/**
	 * <pre>
	 * 3.1.1. MDN/Password 중복 가입 체크.
	 * </pre>
	 */
	@Test
	public void testAleadyJoinCheckForMdn() {
		try {
			receiverM = this.service.aleadyJoinCheckForMdn("01088870008");
		} catch (StorePlatformException e) {
			LOGGER.debug("ErrorInfo : \n{}", e.getErrorInfo().toString());
			throw e;
		}
	}

	/**
	 * <pre>
	 * 3.2.1. 모바일 회원가입.
	 * </pre>
	 */
	@Test
	public void testJoin4Wap() {
		try {
			receiverM = this.service.join4Wap("01088870008", "SKT");
		} catch (StorePlatformException e) {
			LOGGER.debug("ErrorInfo : \n{}", e.getErrorInfo().toString());
			throw e;
		}
	}

	/**
	 * <pre>
	 * 3.3.1. 무선 회원 인증 (For SP Server).
	 * </pre>
	 */
	@Test
	public void testAuthForWap() {
		try {
			receiverM = this.service.authForWap("01088870008");
		} catch (StorePlatformException e) {
			LOGGER.debug("ErrorInfo : \n{}", e.getErrorInfo().toString());
			throw e;
		}
	}

	/**
	 * <pre>
	 * 3.4.1 무선 회원 Profile 조회 (For SP Server).
	 * </pre>
	 */
	@Test
	public void testFindProfileForWap() {
		try {
			receiverM = this.service.findProfileForWap("01088870008");
		} catch (StorePlatformException e) {
			LOGGER.debug("ErrorInfo : \n{}", e.getErrorInfo().toString());
			throw e;
		}
	}

	/**
	 * <pre>
	 * 3.6.1. 무선 회원 해지 (For SP Server).
	 * </pre>
	 */
	@Test
	public void testSecedeUser4Wap() {
		try {
			receiverM = this.service.secedeUser4Wap("01088870009");
		} catch (StorePlatformException e) {
			LOGGER.debug("ErrorInfo : \n{}", e.getErrorInfo().toString());
			throw e;
		}
	}

	/**
	 * 4. 부가서비스 서비스 API.
	 */

	/**
	 * <pre>
	 * 4.1.1. 부가서비스 가입.
	 * </pre>
	 */
	@Test
	public void testJoinSupService() {
		try {
			receiverM = this.service.joinSupService("", "", "");
		} catch (StorePlatformException e) {
			LOGGER.debug("ErrorInfo : \n{}", e.getErrorInfo().toString());
			throw e;
		}
	}

	/**
	 * <pre>
	 * 4.2.1. 부가서비스 해지.
	 * </pre>
	 */
	@Test
	public void testSecedeSupService() {
		try {
			receiverM = this.service.secedeSupService("", "", "");
		} catch (StorePlatformException e) {
			LOGGER.debug("ErrorInfo : \n{}", e.getErrorInfo().toString());
			throw e;
		}
	}

	/**
	 * <pre>
	 * 4.3.2. 부가서비스 가입 여부 조회.
	 * </pre>
	 */
	@Test
	public void testServiceSubscriptionCheck() {
		try {
			receiverM = this.service.serviceSubscriptionCheck("", "");
		} catch (StorePlatformException e) {
			LOGGER.debug("ErrorInfo : \n{}", e.getErrorInfo().toString());
			throw e;
		}
	}

}
