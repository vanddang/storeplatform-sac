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

import com.skplanet.storeplatform.external.client.idp.vo.ImIdpReceiverM;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;

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

	private static final Logger LOGGER = LoggerFactory.getLogger(ImIdpServiceTest.class);
	/**
	 * ImIdpService 객체.
	 */
	@Autowired
	private ImIdpService service;

	private static ImIdpReceiverM receiverM;

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
	 * 2.1.2. 서비스 이용 동의.
	 * </pre>
	 */
	@Test
	public void testAgreeUser() {
		try {
			receiverM = this.service.agreeUser(new HashMap<String, Object>());
			LOGGER.debug("IDP_RECEIVE_HEADER -> \nCODE : {}, MESSAGE : {}", receiverM.getResponseHeader().getResult(),
					receiverM.getResponseHeader().getResult_text());
		} catch (StorePlatformException e) {
			LOGGER.debug("EC_IDP_ERROR -> \nCODE : {}, MESSAGE : {}", e.getErrorInfo().getCode(), e.getErrorInfo()
					.getMessage());
			throw e;
		}
	}

	/**
	 * <pre>
	 * 2.1.4. 개별약관해지요청.
	 * </pre>
	 */
	@Test
	public void testDiscardUser() {
		try {
			receiverM = this.service.discardUser(new HashMap<String, Object>());
			LOGGER.debug("IDP_RECEIVE_HEADER -> \nCODE : {}, MESSAGE : {}", receiverM.getResponseHeader().getResult(),
					receiverM.getResponseHeader().getResult_text());
		} catch (StorePlatformException e) {
			LOGGER.debug("EC_IDP_ERROR -> \nCODE : {}, MESSAGE : {}", e.getErrorInfo().getCode(), e.getErrorInfo()
					.getMessage());
			throw e;
		}
	}

	/**
	 * <pre>
	 * 2.1.6 공통프로파일조회요청 (For Server).
	 * </pre>
	 */
	@Test
	public void testUserInfoSearchServer() {
		try {
			receiverM = this.service.userInfoSearchServer("");
			LOGGER.debug("IDP_RECEIVE_HEADER -> \nCODE : {}, MESSAGE : {}", receiverM.getResponseHeader().getResult(),
					receiverM.getResponseHeader().getResult_text());
		} catch (StorePlatformException e) {
			LOGGER.debug("EC_IDP_ERROR -> \nCODE : {}, MESSAGE : {}", e.getErrorInfo().getCode(), e.getErrorInfo()
					.getMessage());
			throw e;
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
			receiverM = this.service.updateUserInfo(new HashMap<String, Object>());
			LOGGER.debug("IDP_RECEIVE_HEADER -> \nCODE : {}, MESSAGE : {}", receiverM.getResponseHeader().getResult(),
					receiverM.getResponseHeader().getResult_text());
		} catch (StorePlatformException e) {
			LOGGER.debug("EC_IDP_ERROR -> \nCODE : {}, MESSAGE : {}", e.getErrorInfo().getCode(), e.getErrorInfo()
					.getMessage());
			throw e;
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
			receiverM = this.service.updateAdditionalInfo(new HashMap<String, Object>());
			LOGGER.debug("IDP_RECEIVE_HEADER -> \nCODE : {}, MESSAGE : {}", receiverM.getResponseHeader().getResult(),
					receiverM.getResponseHeader().getResult_text());
		} catch (StorePlatformException e) {
			LOGGER.debug("EC_IDP_ERROR -> \nCODE : {}, MESSAGE : {}", e.getErrorInfo().getCode(), e.getErrorInfo()
					.getMessage());
			throw e;
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
			receiverM = this.service.modifyPwd(new HashMap<String, Object>());
			LOGGER.debug("IDP_RECEIVE_HEADER -> \nCODE : {}, MESSAGE : {}", receiverM.getResponseHeader().getResult(),
					receiverM.getResponseHeader().getResult_text());
		} catch (StorePlatformException e) {
			LOGGER.debug("EC_IDP_ERROR -> \nCODE : {}, MESSAGE : {}", e.getErrorInfo().getCode(), e.getErrorInfo()
					.getMessage());
			throw e;
		}
	}

	/**
	 * <pre>
	 * 2.1.20 로그인 상태 정보 변경 요청.
	 * </pre>
	 */
	@Test
	public void testSetLoginStatus() {
		try {
			receiverM = this.service.setLoginStatus("", "");
			LOGGER.debug("IDP_RECEIVE_HEADER -> \nCODE : {}, MESSAGE : {}", receiverM.getResponseHeader().getResult(),
					receiverM.getResponseHeader().getResult_text());
		} catch (StorePlatformException e) {
			LOGGER.debug("EC_IDP_ERROR -> \nCODE : {}, MESSAGE : {}", e.getErrorInfo().getCode(), e.getErrorInfo()
					.getMessage());
			throw e;
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
			receiverM = this.service.updateUserName(new HashMap<String, Object>());
			LOGGER.debug("IDP_RECEIVE_HEADER -> \nCODE : {}, MESSAGE : {}", receiverM.getResponseHeader().getResult(),
					receiverM.getResponseHeader().getResult_text());
		} catch (StorePlatformException e) {
			LOGGER.debug("EC_IDP_ERROR -> \nCODE : {}, MESSAGE : {}", e.getErrorInfo().getCode(), e.getErrorInfo()
					.getMessage());
			throw e;
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
			receiverM = this.service.updateGuardian(new HashMap<String, Object>());
			LOGGER.debug("IDP_RECEIVE_HEADER -> \nCODE : {}, MESSAGE : {}", receiverM.getResponseHeader().getResult(),
					receiverM.getResponseHeader().getResult_text());
		} catch (StorePlatformException e) {
			LOGGER.debug("EC_IDP_ERROR -> \nCODE : {}, MESSAGE : {}", e.getErrorInfo().getCode(), e.getErrorInfo()
					.getMessage());
			throw e;
		}
	}

	/** 2.1.26 RX 배포 Retry 요청. */
	/** 2.2.3 MDN 중복체크. */
	/** 2.2.4 MDN 조회 (SKT, non-SKT 구분). */

	/**
	 * <pre>
	 * 2.2.5 통합 ID 회원로그인.
	 * </pre>
	 */
	@Test
	public void testAuthForId() {
		try {
			receiverM = this.service.authForId("", "");
			LOGGER.debug("IDP_RECEIVE_HEADER -> \nCODE : {}, MESSAGE : {}", receiverM.getResponseHeader().getResult(),
					receiverM.getResponseHeader().getResult_text());
		} catch (StorePlatformException e) {
			LOGGER.debug("EC_IDP_ERROR -> \nCODE : {}, MESSAGE : {}", e.getErrorInfo().getCode(), e.getErrorInfo()
					.getMessage());
			throw e;
		}
	}

	/**
	 * <pre>
	 * 2.2.6. 통합 ID 서비스 가입리스트 조회.
	 * </pre>
	 */
	@Test
	public void testFindJoinServiceListIDP() {
		try {
			receiverM = this.service.findJoinServiceListIDP(new HashMap<String, Object>());
			LOGGER.debug("IDP_RECEIVE_HEADER -> \nCODE : {}, MESSAGE : {}", receiverM.getResponseHeader().getResult(),
					receiverM.getResponseHeader().getResult_text());
		} catch (StorePlatformException e) {
			LOGGER.debug("EC_IDP_ERROR -> \nCODE : {}, MESSAGE : {}", e.getErrorInfo().getCode(), e.getErrorInfo()
					.getMessage());
			throw e;
		}
	}

	/**
	 * <pre>
	 * 2.2.9 ID 가입여부 체크.
	 * </pre>
	 */
	@Test
	public void testCheckIdStatusIdpIm() {
		try {
			receiverM = this.service.checkIdStatusIdpIm("");
			LOGGER.debug("IDP_RECEIVE_HEADER -> \nCODE : {}, MESSAGE : {}", receiverM.getResponseHeader().getResult(),
					receiverM.getResponseHeader().getResult_text());
		} catch (StorePlatformException e) {
			LOGGER.debug("EC_IDP_ERROR -> \nCODE : {}, MESSAGE : {}", e.getErrorInfo().getCode(), e.getErrorInfo()
					.getMessage());
			throw e;
		}
	}

	/**
	 * <pre>
	 * 2.2.11 MDN 정보 조회 (SKT 가입자).
	 * </pre>
	 */
	@Test
	public void testGetMdnInfoIDP() {
		try {
			receiverM = this.service.getMdnInfoIDP("");
			LOGGER.debug("IDP_RECEIVE_HEADER -> \nCODE : {}, MESSAGE : {}", receiverM.getResponseHeader().getResult(),
					receiverM.getResponseHeader().getResult_text());
		} catch (StorePlatformException e) {
			LOGGER.debug("EC_IDP_ERROR -> \nCODE : {}, MESSAGE : {}", e.getErrorInfo().getCode(), e.getErrorInfo()
					.getMessage());
			throw e;
		}
	}

	/**
	 * <pre>
	 * 2.2.12 IM 통합회원 ID 찾기.
	 * </pre>
	 */
	@Test
	public void testFindUserIdByMdn() {
		try {
			receiverM = this.service.findUserIdByMdn(new HashMap<String, Object>());
			LOGGER.debug("IDP_RECEIVE_HEADER -> \nCODE : {}, MESSAGE : {}", receiverM.getResponseHeader().getResult(),
					receiverM.getResponseHeader().getResult_text());
		} catch (StorePlatformException e) {
			LOGGER.debug("EC_IDP_ERROR -> \nCODE : {}, MESSAGE : {}", e.getErrorInfo().getCode(), e.getErrorInfo()
					.getMessage());
			throw e;
		}
	}

	/** 2.2.13 이용동의 가능여부 조회. */

	/**
	 * <pre>
	 * 2.2.15. 기본 프로파일 조회(For Server).
	 * </pre>
	 */
	@Test
	public void testUserInfoIdpSearchServer() {
		try {
			receiverM = this.service.userInfoIdpSearchServer("");
			LOGGER.debug("IDP_RECEIVE_HEADER -> \nCODE : {}, MESSAGE : {}", receiverM.getResponseHeader().getResult(),
					receiverM.getResponseHeader().getResult_text());
		} catch (StorePlatformException e) {
			LOGGER.debug("EC_IDP_ERROR -> \nCODE : {}, MESSAGE : {}", e.getErrorInfo().getCode(), e.getErrorInfo()
					.getMessage());
			throw e;
		}
	}
}
