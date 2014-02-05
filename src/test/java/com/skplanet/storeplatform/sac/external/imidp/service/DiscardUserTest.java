package com.skplanet.storeplatform.sac.external.imidp.service;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

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

import com.skplanet.storeplatform.external.client.idp.vo.ImIDPReceiverM;
import com.skplanet.storeplatform.sac.member.common.idp.service.ImIDPService;

/**
 * Calss 설명
 * 
 * Updated on : 2014. 1. 15. Updated by : 김경복, 부르칸.
 */
@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class DiscardUserTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(DiscardUserTest.class);

	@Autowired
	private ImIDPService imIDPService;

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test
	public void discardUser() {
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("key", "");
			param.put("user_auth_key", "");
			param.put("term_reason_cd", "");
			// String key = (String) param.get("key");
			// String user_auth_key = (String) param.get("user_auth_key");
			// String term_reason_cd = (String) param.get("term_reason_cd");
			ImIDPReceiverM receiverM = this.imIDPService.discardUser(param);
			assertThat(receiverM.getResponseHeader().getResult(), notNullValue());
			LOGGER.debug("result code : {}", receiverM.getResponseHeader().getResult());
			LOGGER.debug("result message : {}", receiverM.getResponseHeader().getResult_text());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
