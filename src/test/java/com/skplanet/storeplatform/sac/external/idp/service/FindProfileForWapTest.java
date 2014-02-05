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

import com.skplanet.storeplatform.external.client.idp.vo.IDPReceiverM;
import com.skplanet.storeplatform.sac.member.common.idp.service.IDPService;

/**
 * IDP - 모바일 회원 조회, EC 로컬 서버가 8210 포트로 떠 있을 때만 성공
 * 
 * Updated on : 2014. 1. 9. Updated by : 김경복, 부르칸.
 */
@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class FindProfileForWapTest {

	private static final Logger logger = LoggerFactory.getLogger(FindProfileForWapTest.class);

	@Autowired
	private IDPService idpService;

	@Test
	public void findProfileForWap() {

		IDPReceiverM receiverM = this.idpService.findProfileForWap("01088870008");
		assertThat(receiverM.getResponseHeader().getResult(), notNullValue());
		logger.debug("result code : {}", receiverM.getResponseHeader().getResult());
		logger.debug("result message : {}", receiverM.getResponseHeader().getResult_text());
	}
}
