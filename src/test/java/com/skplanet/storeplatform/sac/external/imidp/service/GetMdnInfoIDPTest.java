package com.skplanet.storeplatform.sac.external.imidp.service;

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

import com.skplanet.storeplatform.external.client.idp.vo.ImIdpReceiverM;
import com.skplanet.storeplatform.sac.member.common.idp.service.ImIdpService;

/**
 * ImIDP - MDN 정보 조회, EC 로컬 서버가 8210 포트로 떠 있을 때만 성공
 * 
 * Updated on : 2014. 1. 9. Updated by : 김경복, 부르칸.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class GetMdnInfoIDPTest {

	private static final Logger logger = LoggerFactory.getLogger(GetMdnInfoIDPTest.class);

	@Autowired
	private ImIdpService imIDPService;

	@Test
	public void getMdnInfoIDP() {
		ImIdpReceiverM receiverM = this.imIDPService.getMdnInfoIDP("01088870012");
		assertThat(receiverM.getResponseHeader().getResult(), notNullValue());
		logger.debug("result code : {}", receiverM.getResponseHeader().getResult());
		logger.debug("result message : {}", receiverM.getResponseHeader().getResult_text());
	}
}
