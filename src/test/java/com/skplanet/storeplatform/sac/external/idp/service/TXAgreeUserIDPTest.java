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

import com.skplanet.storeplatform.external.client.idp.vo.ImIDPReceiverM;
import com.skplanet.storeplatform.sac.member.common.idp.service.ImIDPService;

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
	private ImIDPService imIDPService;

	@Test
	public void TXAgreeUserIDP() {
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("key", "IW0012300123");
			param.put("key_type", "1");
			// param.put("user_mdn", "01088870008");
			// param.put("user_mdn_auth_key", "");
			/** 0:부, 1:모, 2:기타 */
			// param.put("parent_type", "0");
			/**
			 * 법정대리인실명인증수단코드 1:휴대폰 본인인증, 3:IPIN, 6: 이메일 (외국인 법정대리인 인증)
			 */
			// param.put("parent_rname_auth_type", "");
			/** 법정대리인실명인증값(해외는 미획득), CI 값(88bytes) */
			// param.put("parent_rname_auth_key", "");
			/** 법정대리인 이름 */
			// param.put("parent_name", "");
			/** 법정대리인 이메일 */
			// param.put("parent_email", "");
			/** 법정대리인 동의일자 (YYYYMMDD) */
			// param.put("parent_approve_date", "");
			/** 법정대리인동의여부, Y=동의, N=미동의 */
			param.put("is_parent_approve", "N");

			/** 항목 삭제됨 */
			// param.put("parent_mdn", "");
			/** 항목 삭제 */
			// param.put("parent_approve_sst_code", "");

			ImIDPReceiverM receiverM = this.imIDPService.agreeUser(param);
			assertThat(receiverM.getResponseHeader().getResult(), notNullValue());
			logger.debug("result code : {}", receiverM.getResponseHeader().getResult());
			logger.debug("result message : {}", receiverM.getResponseHeader().getResult_text());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
