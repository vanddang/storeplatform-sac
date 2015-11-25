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
 * Updated on : 2014. 1. 17. Updated by : 이정숙, 인크로스
 */
@ActiveProfiles(value = "local")

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class InvalidUserTelNoTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(InvalidUserTelNoTest.class);

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
	 * 휴대폰소유변경정보배포.
	 * 성공 Case
	 * </pre>
	 */
	@Test
	public void invalidUserTelNo01() {

		new TestCaseTemplate(this.mockMvc).url("/member/idp/provisioning/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						ProvisioningReq req = new ProvisioningReq();
						req.setCmd("RXInvalidUserTelNoIDP");
						HashMap resultMap = new HashMap();
						resultMap.put("systemID", "W");
						resultMap.put("tenantID", "S01");
						resultMap.put("sp_id", "90000");
						resultMap.put("target_sst_cd", "10100");
						resultMap.put("im_int_svc_no", "999000545996");
						resultMap.put("user_id", "test0127_01");
						resultMap.put("user_tn", "01000000000");
						resultMap.put("is_user_tn_own", "Y");
						resultMap.put("modify_sst_code", "20100");
						resultMap.put("modify_req_date", "20130423");
						resultMap.put("modify_req_time", "112030");

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

	/**
	 * <pre>
	 * 휴대폰소유변경정보배포.
	 * 실패 Case
	 * user id 정보가 없을 경우
	 * </pre>
	 * 
	 * @Test public void invalidUserTelNo02() {
	 * 
	 *       new TestCaseTemplate(this.mockMvc).url("/member/idp/provisioning/v1").httpMethod(HttpMethod.POST)
	 *       .requestBody(new RequestBodySetter() {
	 * @Override public Object requestBody() { ProvisioningReq req = new ProvisioningReq();
	 *           req.setCmd("RXInvalidUserTelNoIDP"); HashMap resultMap = new HashMap();
	 * 
	 *           resultMap.put("systemID", "S001"); resultMap.put("tenantID", "S01"); resultMap.put("sp_id", "90000");
	 *           resultMap.put("target_sst_cd", "10100"); resultMap.put("im_int_svc_no", " 2222222"); //
	 *           resultMap.put("user_id", "test"); resultMap.put("user_tn", "01011112222");
	 *           resultMap.put("is_user_tn_own", "Y"); resultMap.put("modify_sst_code", "20100");
	 *           resultMap.put("modify_req_date", "20130423"); resultMap.put("modify_req_time", "112030");
	 * 
	 *           req.setReqParam(resultMap);
	 * 
	 *           LOGGER.info("request param : {}", req.toString()); return req; } }).success(ProvisioningRes.class, new
	 *           SuccessCallback() {
	 * @Override public void success(Object result, HttpStatus httpStatus, RunMode runMode) { ProvisioningRes res =
	 *           (ProvisioningRes) result; // res.get // assertThat(res.getSellerKey(), notNullValue());
	 *           LOGGER.info("response param : {}", res.toString()); } }, HttpStatus.OK,
	 *           HttpStatus.ACCEPTED).run(RunMode.JSON);
	 * 
	 *           }
	 */

}
