package com.skplanet.storeplatform.sac.member.idp;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.skplanet.storeplatform.framework.test.RequestBodySetter;
import com.skplanet.storeplatform.framework.test.SuccessCallback;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate.RunMode;
import com.skplanet.storeplatform.sac.member.idp.vo.ProvisioningReq;
import com.skplanet.storeplatform.sac.member.idp.vo.ProvisioningRes;

/**
 * 
 * Calss 설명
 * 
 * Updated on : 2014. 1. 17. Updated by : Lee, Jung suk, incross.
 */
@ActiveProfiles(value = "local")

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class PreCheckDeleteUserIDPTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(PreCheckDeleteUserIDPTest.class);

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	/**
	 * 
	 * 
	 * <pre>
	 * 탈퇴가 가능한 통합회원 확인요청.
	 * </pre>
	 */
	@Test
	public void preCheckDeleteUserIDPTest01() {

		new TestCaseTemplate(this.mockMvc).url("/member/idp/provisioning/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						ProvisioningReq req = new ProvisioningReq();
						req.setCmd("RXPreCheckDeleteUserIDP");
						HashMap resultMap = new HashMap();

						resultMap.put("systemID", "S001");
						resultMap.put("tenantID", "S01");
						resultMap.put("sp_id", "90000");
						resultMap.put("target_sst_cd", "10100");
						resultMap.put("im_int_svc_no", "900000083496");
						resultMap.put("user_id", "1234");
						resultMap
								.put("user_ci",
										"sbvfRbP/38g7U4TEZwWG5IexVllTKE2SkM3HXv+M6NEF1RCG5i85xaAVoh9GdWzhcta6D8wSGpLh7Nf0XxF5Pg==");
						resultMap.put("user_di", "MC0GCCqGSIb3DQIJAyEAfWou0NPTinxWZBT+zJkd3jFIfVXwnZLzCRkO/KQfGCE=");
						req.setReqParam(resultMap);

						LOGGER.info("request param : {}", req.toString());
						return req;
					}
				}).success(ProvisioningRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						ProvisioningRes res = (ProvisioningRes) result;
						// res.get
						// assertThat(res.getSellerKey(), notNullValue());
						LOGGER.info("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

}
