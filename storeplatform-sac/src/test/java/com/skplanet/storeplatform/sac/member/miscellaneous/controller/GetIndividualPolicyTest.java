package com.skplanet.storeplatform.sac.member.miscellaneous.controller;

import com.skplanet.storeplatform.framework.test.RequestBodySetter;
import com.skplanet.storeplatform.framework.test.SuccessCallback;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate.RunMode;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetIndividualPolicyReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetIndividualPolicyReq.PolicyCode;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetIndividualPolicyRes;
import com.skplanet.storeplatform.sac.member.common.util.TestConvertMapperUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * 2.3.8. 사용자별 정책 조회
 * 
 * Updated on : 2014. 1. 21. Updated by : 김경복, 부르칸.
 */
@ActiveProfiles(value = "local")

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
	 * before.
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
		LOGGER.debug("[RESPONSE(SAC)-정책조회] : \n{}", TestConvertMapperUtils.convertObjectToJson(response));
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
						PolicyCode policyCode1 = new PolicyCode();
						policyCode1.setPolicyCode("US011701");
						PolicyCode policyCode2 = new PolicyCode();
						policyCode2.setPolicyCode("US011702");
						PolicyCode policyCode3 = new PolicyCode();
						policyCode3.setPolicyCode("US011703");
						policyCodeList.add(policyCode1);
						policyCodeList.add(policyCode2);
						policyCodeList.add(policyCode3);
						request.setPolicyCodeList(policyCodeList);
						request.setKey("01012341234");
						LOGGER.debug("[REQUEST] JSON : \n{}", TestConvertMapperUtils.convertObjectToJson(request));
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

	/**
	 * <pre>
	 * 실패 CASE.
	 * </pre>
	 */
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
						LOGGER.debug("[REQUEST] JSON : \n{}", TestConvertMapperUtils.convertObjectToJson(request));
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
