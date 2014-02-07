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

import com.skplanet.storeplatform.external.client.idp.vo.ImIdpReceiverM;
import com.skplanet.storeplatform.sac.member.common.idp.service.ImIdpService;

/**
 * ImIDP - 서비스 이용 동의, EC 로컬 서버가 8210 포트로 떠 있을 때만 성공
 * 
 * Updated on : 2014. 1. 9. Updated by : 김경복, 부르칸.
 */
@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class TXAgreeUserIDPTest {

	private static final Logger logger = LoggerFactory.getLogger(TXAgreeUserIDPTest.class);

	@Autowired
	private ImIdpService imIDPService;

	@Test
	public void TXAgreeUserIDP() {
		Map<String, Object> param = new HashMap<String, Object>();

		/** param Key. */
		// operator_id
		// key_type
		// key
		// user_mdn
		// user_ci
		// user_di
		// join_sst_list
		// ocb_join_code
		// os_code
		// browser_code
		// user_mdn_auth_key
		// modify_req_date
		// modify_req_time
		// service_profiles

		// TODO 임시 테스트용
		param.put("key", "01088870008");
		param.put("key_type", "1");
		param.put("is_parent_approve", "N");

		ImIdpReceiverM receiverM = this.imIDPService.agreeUser(param);
		assertThat(receiverM.getResponseHeader().getResult(), notNullValue());
		logger.debug("result code : {}", receiverM.getResponseHeader().getResult());
		logger.debug("result message : {}", receiverM.getResponseHeader().getResult_text());

	}
}
