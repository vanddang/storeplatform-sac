package com.skplanet.storeplatform.sac.api.v1.member.idp;

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

@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class CreateUserIdIDPTest {

	private static final Logger logger = LoggerFactory.getLogger(CreateUserIdIDPTest.class);

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
	 * <pre>
	 * 신규가입정보를 미동의 사이트에 배포
	 * </pre>
	 */
	@Test
	public void createUserIdIDPTest01() {

		new TestCaseTemplate(this.mockMvc).url("/member/idp/provisioning/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						ProvisioningReq req = new ProvisioningReq();
						req.setCmd("RXCreateUserIdIDP");
						HashMap resultMap = new HashMap();

						resultMap.put("systemID", "S001");
						resultMap.put("tenantID", "S01");
						resultMap.put("sp_id", "90000");
						resultMap.put("target_sst_cd", "10100");
						resultMap.put("im_int_svc_no", "100000100350");
						resultMap.put("user_id", "tc_one_test08");
						resultMap.put("idp_id", "test01");
						resultMap.put("im_mem_type_cd", "100");

						req.setReqParam(resultMap);

						logger.info("request param : {}", req.toString());
						return req;
					}
				}).success(ProvisioningRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						ProvisioningRes res = (ProvisioningRes) result;
						// res.get
						// assertThat(res.getSellerKey(), notNullValue());
						logger.info("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

}
