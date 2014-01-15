package com.skplanet.storeplatform.sac.external.idp.service;

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

import com.skplanet.storeplatform.sac.member.common.idp.repository.IDPRepository;

/**
 * IDP - Email 중복 가입 체크, EC 로컬 서버가 8210 포트로 떠 있을 때만 성공
 * 
 * Updated on : 2014. 1. 13. Updated by : 김경복, 부르칸.
 */
@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class AlredyJoinCheckByEmailTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(AlredyJoinCheckByEmailTest.class);

	// @Autowired
	// private IDPService idpService;
	@Autowired
	private IDPRepository repository;

	/**
	 * <pre>
	 * Email 중복 가입 체크.
	 * </pre>
	 */
	@Test
	public void alredyJoinCheckByEmail() {
		try {
			// IDPReceiverM receiverM = this.repository.alredyJoinCheckByEmail("asdad@adwd.com");
			// IDPReceiverM receiverM = this.idpService.alredyJoinCheckByEmail("abcdefg@caswd.com");
			// assertThat(receiverM.getResponseHeader().getResult(), notNullValue());
			// LOGGER.debug("result code : {}", receiverM.getResponseHeader().getResult());
			// LOGGER.debug("result message : {}", receiverM.getResponseHeader().getResult_text());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
