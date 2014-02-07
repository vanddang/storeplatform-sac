package com.skplanet.storeplatform.sac.external.idp.service;

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

import com.skplanet.storeplatform.external.client.idp.vo.IdpReceiverM;
import com.skplanet.storeplatform.sac.member.common.idp.service.IdpService;

/**
 * IDP - 프로파일 수정, EC 로컬 서버가 8210 포트로 떠 있을 때만 성공
 * 
 * Updated on : 2014. 1. 13. Updated by : 김경복, 부르칸.
 */
@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class ModifyProfileTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ModifyProfileTest.class);

	@Autowired
	private IdpService idpService;

	/**
	 * <pre>
	 * 프로파일 수정.
	 * </pre>
	 */
	@Test
	public void modifyProfile() {
		try {

			Map<String, Object> param = new HashMap<String, Object>();

			// 1= User ID, 2=User Key, default=1
			param.put("key_type", "1");
			param.put("key", "wadawdwd");
			param.put("user_auth_key", "adwadawdd");
			param.put("user_job_code", "");
			param.put("user_zipcode", "");
			param.put("user_address", "");
			param.put("user_address2", "");
			param.put("user_sex", "");
			param.put("user_birthday", "");
			param.put("user_social_number", "");
			param.put("user_phone", "0112342341,1100111000,SS11");
			param.put("service_profile", "");
			param.put("user_calendar", "");
			param.put("user_tel", "");
			param.put("user_type", "");
			param.put("is_foreign", "");
			param.put("user_nation", "");
			param.put("user_name", "");
			param.put("is_rname_auth", "");
			// is_rname_auth=Y
			param.put("sn_auth_key", "");
			// param.put("phone_auth_key", "");
			param.put("user_id", "");
			param.put("user_ci", "");

			IdpReceiverM receiverM = this.idpService.modifyProfile(param);
			assertThat(receiverM.getResponseHeader().getResult(), notNullValue());
			LOGGER.debug("result code : {}", receiverM.getResponseHeader().getResult());
			LOGGER.debug("result message : {}", receiverM.getResponseHeader().getResult_text());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
