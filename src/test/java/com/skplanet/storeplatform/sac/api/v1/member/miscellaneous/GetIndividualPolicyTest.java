package com.skplanet.storeplatform.sac.api.v1.member.miscellaneous;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

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
import com.skplanet.storeplatform.sac.api.v1.member.ConvertMapperUtil;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetIndividualPolicyReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetIndividualPolicyReq.PolicyCode;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetIndividualPolicyRes;

/**
 * 사용자별 정책 조회
 * 
 * Updated on : 2014. 1. 21. Updated by : 김경복, 부르칸.
 */
@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class GetIndividualPolicyTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetIndividualPolicyTest.class);

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	/** [REQUEST]. */
	private static GetIndividualPolicyReq request;
	/** [RESPONSE]. */
	private static GetIndividualPolicyRes response;
	/** [x-store-auth-info]. */
	private static String xStoreAuthInfo;

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		// [REQUEST] 초기화
		request = new GetIndividualPolicyReq();
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
		// Debug SAC 결과
		LOGGER.debug("[RESPONSE(SAC)-정책조회] : \n{}", ConvertMapperUtil.convertObjectToJson(response));
	}

	/**
	 * <pre>
	 * 성공 CASE
	 * 정상 파라미터 전달.
	 * </pre>
	 */
	@Test
	public void getIndividualPolicy() {
		new TestCaseTemplate(this.mockMvc).url("/member/miscellaneous/getIndividualPolicy/v1")
				.addHeaders("x-store-auth-info", xStoreAuthInfo).httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {

					@Override
					public Object requestBody() {
						List<PolicyCode> policyCodeList = new ArrayList<GetIndividualPolicyReq.PolicyCode>();
						for (int i = 0; i < 3; i++) {
							PolicyCode policyCode = new PolicyCode();
							policyCode.setPolicyCode(String.valueOf(i + 3));
							policyCodeList.add(policyCode);
						}
						request.setPolicyCodeList(policyCodeList);
						request.setKey("53");
						LOGGER.debug("[REQUEST] JSON : \n{}", ConvertMapperUtil.convertObjectToJson(request));
						return request;
					}
				}).success(GetIndividualPolicyRes.class, new SuccessCallback() {

					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						response = (GetIndividualPolicyRes) result;
						assertThat(response.getPolicyList(), notNullValue());
						for (int i = 0; i < response.getPolicyList().size(); i++) {
							assertEquals(response.getPolicyList().get(i).getKey(), request.getKey());
							// assertEquals(response.getPolicyList().get(i).getKey(),
							// request.getPolicyCodeList().get(i).getPolicyCode());
						}
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	@Test
	public void getIndividualPolicyNonData() {
		new TestCaseTemplate(this.mockMvc).url("/member/miscellaneous/getIndividualPolicy/v1")
				.addHeaders("x-store-auth-info", xStoreAuthInfo).httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {

					@Override
					public Object requestBody() {
						List<PolicyCode> policyCodeList = new ArrayList<GetIndividualPolicyReq.PolicyCode>();
						for (int i = 0; i < 3; i++) {
							PolicyCode policyCode = new PolicyCode();
							policyCode.setPolicyCode(String.valueOf(i + 3));
							policyCodeList.add(policyCode);
						}
						request.setPolicyCodeList(policyCodeList);
						request.setKey("5123");
						LOGGER.debug("[REQUEST] JSON : \n{}", ConvertMapperUtil.convertObjectToJson(request));
						return request;
					}
				}).success(GetIndividualPolicyRes.class, new SuccessCallback() {

					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						response = (GetIndividualPolicyRes) result;
						assertThat(response.getPolicyList(), notNullValue());
						for (int i = 0; i < response.getPolicyList().size(); i++) {
							assertEquals(response.getPolicyList().get(i).getKey(), request.getKey());
							// assertEquals(response.getPolicyList().get(i).getKey(),
							// request.getPolicyCodeList().get(i).getPolicyCode());
						}
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}
}
