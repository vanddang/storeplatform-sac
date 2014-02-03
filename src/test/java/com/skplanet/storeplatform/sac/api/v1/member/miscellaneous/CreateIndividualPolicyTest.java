package com.skplanet.storeplatform.sac.api.v1.member.miscellaneous;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.After;
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
import com.skplanet.storeplatform.sac.api.v1.member.ConvertMapperUtils;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.CreateIndividualPolicyReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.CreateIndividualPolicyRes;

/**
 * 2.3.9. 사용자별 정책 등록 / 수정
 * 
 * Updated on : 2014. 1. 21. Updated by : 김경복, 부르칸.
 */
@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class CreateIndividualPolicyTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(CreateIndividualPolicyTest.class);

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	/** [REQUEST]. */
	private static CreateIndividualPolicyReq request;

	/** [RESPONSE]. */
	private static CreateIndividualPolicyRes response;
	/** [x-store-auth-info]. */
	private static String xStoreAuthInfo;

	/**
	 * <pre>
	 * before method 설명.
	 * </pre>
	 */
	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		// [REQUEST] 초기화
		request = new CreateIndividualPolicyReq();
		// [HEADER] 주입
		xStoreAuthInfo = "authKey=114127c7ef42667669819dad5df8d820c;ist=N";
	}

	/**
	 * <pre>
	 * After method 설명.
	 * </pre>
	 */
	@After
	public void after() {
		LOGGER.debug("[RESPONSE(SAC)-정책등록/수정] : \n{} ", response.toString());
	}

	/**
	 * <pre>
	 * 성공 CASE
	 * 정상 파라미터 전달.
	 * </pre>
	 */
	@Test
	public void createIndividualPolicy() {
		new TestCaseTemplate(this.mockMvc).url("/member/miscellaneous/createIndividualPolicy/v1")
				.addHeaders("x-store-auth-info", xStoreAuthInfo).httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {

					@Override
					public Object requestBody() {
						// key(30), code(10), value(100)
						request.setKey("seller");
						request.setPolicyCode("01");
						request.setValue("제한");
						request.setRegId("test");

						// Debug
						LOGGER.debug("[REQUEST] JSON : \n{}", ConvertMapperUtils.convertObjectToJson(request));
						return request;
					}
				}).success(CreateIndividualPolicyRes.class, new SuccessCallback() {

					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						response = (CreateIndividualPolicyRes) result;
						assertThat(response.getPolicyCode(), notNullValue());
						assertEquals(response.getKey(), request.getKey());
						assertEquals(response.getPolicyCode(), request.getPolicyCode());
						assertEquals(response.getValue(), request.getValue());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}
}
