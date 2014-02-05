package com.skplanet.storeplatform.sac.api.v1.member.idp;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

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
public class RXActivateUserIdIDPTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(RXActivateUserIdIDPTest.class);

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
	 * 활성화된 통합회원 통합ID 가입자 상태 정보 배포.
	 * </pre>
	 */
	@Test
	public void rXActivateUserIdIDP() {//

		new TestCaseTemplate(this.mockMvc).url("/member/idp/provisioning/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						ProvisioningReq req = new ProvisioningReq();
						req.setCmd("RXActivateUserIdIDP");
						/* 활성화된 통합회원 통합ID 가입자 상태 정보 배포 시작 */
						HashMap resultMap = new HashMap();

						resultMap.put("tenantID", "S01"); // 현재 하드코딩 파라미터가 정상으로 전달되어지면 지워야함.
						resultMap.put("systemID", "S001");

						resultMap.put("is_email_auth", "Y");
						resultMap.put("user_status_code", "10");
						resultMap.put("trx_no", "900002013101609194051508");
						resultMap.put("sp_id", "OMP10000");
						resultMap.put("im_int_svc_no", "200000545221");
						resultMap.put("target_sst_cd", "41100");
						resultMap.put("user_id", "test0127@test0127.com");

						/* 활성화된 통합회원 통합ID 가입자 상태 정보 배포 끝 */

						req.setReqParam(resultMap);

						LOGGER.info("request param : {}", req.toString());
						return req;
					}
				}).success(ProvisioningRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						ProvisioningRes res = (ProvisioningRes) result;
						assertThat(res.getImResult(), notNullValue());
						assertThat(res.getImResult().getCmd(), is("RXActiveUserIdIDP"));
						assertEquals("test0127@test0127.com", res.getImResult().getUserId());
						LOGGER.info("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}
}
