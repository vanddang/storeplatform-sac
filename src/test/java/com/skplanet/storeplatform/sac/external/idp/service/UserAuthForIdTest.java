package com.skplanet.storeplatform.sac.external.idp.service;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.skplanet.storeplatform.external.client.idp.vo.IdpReceiverM;
import com.skplanet.storeplatform.sac.member.common.idp.service.IdpService;

/**
 * IDP - 회원인증, EC 로컬 서버가 8210 포트로 떠 있을 때만 성공
 * 
 * Updated on : 2014. 1. 13. Updated by : 김경복, 부르칸.
 */
@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class UserAuthForIdTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserAuthForIdTest.class);

	@Autowired
	private IdpService idpService;

	/**
	 * <pre>
	 * 회원 인증.
	 * </pre>
	 */
	@Test
	public void userAuthForId() {
		try {

			IdpReceiverM receiverM = this.idpService.userAuthForId("abcdefg@caswd.com", "awdad1232");
			assertThat(receiverM.getResponseHeader().getResult(), notNullValue());
			LOGGER.debug("result code : {}", receiverM.getResponseHeader().getResult());
			LOGGER.debug("result message : {}", receiverM.getResponseHeader().getResult_text());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
