package com.skplanet.storeplatform.sac.external.idp.service;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.skplanet.storeplatform.external.client.idp.vo.IDPReceiverM;
import com.skplanet.storeplatform.sac.member.common.idp.service.IDPService;

/**
 * IDP - 워터마크 인증, EC 로컬 서버가 8210 포트로 떠 있을 때만 성공
 * 
 * Updated on : 2014. 1. 14. Updated by : 김경복, 부르칸.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class WarterMarkAuthTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(WarterMarkAuthTest.class);

	@Autowired
	private IDPService idpService;

	/**
	 * <pre>
	 * 워터마크 인증.
	 * </pre>
	 */
	@Test
	public void warterMarkAuth() {

		IDPReceiverM receiverM = this.idpService.warterMarkAuth("pgw25x", "34c7d500aa3c5085704ea4bec29adc5d923d5614",
				"http://idp.innoace.com/watermark/20140114/10742_1389663959668.jpeg|1389663959668");
		assertThat(receiverM.getResponseHeader().getResult(), notNullValue());
		LOGGER.debug("result code : {}", receiverM.getResponseHeader().getResult());
		LOGGER.debug("result message : {}", receiverM.getResponseHeader().getResult_text());
	}
}
