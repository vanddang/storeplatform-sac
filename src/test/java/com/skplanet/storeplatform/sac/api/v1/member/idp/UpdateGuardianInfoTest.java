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

/**
 * 
 * Calss 설명
 * 
 * Updated on : 2014. 1. 17. Updated by : Lee, Jung suk, incross.
 */
@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class UpdateGuardianInfoTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(UpdateGuardianInfoTest.class);

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
	 * 법정대리인 동의정보.
	 * </pre>
	 */
	@Test
	public void updateGuardianInfoTest01() {

		new TestCaseTemplate(this.mockMvc).url("/member/idp/provisioning/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						ProvisioningReq req = new ProvisioningReq();
						req.setCmd("RXUpdateGuardianInfoIDP");
						HashMap resultMap = new HashMap();

						resultMap.put("systemID", "S001");
						resultMap.put("tenantID", "S01");
						resultMap.put("sp_id", "OMP10000");
						resultMap.put("target_sst_cd", "41100");
						resultMap.put("im_int_svc_no", "100000003280");
						resultMap.put("parent_type", "0");
						resultMap.put("parent_rname_auth_type", "1");
						resultMap.put("parent_name", "김용석");
						resultMap.put("parent_birthday", "19701111");

						resultMap.put("parent_approve_sst_code", "1111");
						resultMap.put("is_parent_approve", "N");
						resultMap
								.put("parent_rname_auth_key",
										"14DPINETREEBUMOAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA1111");
						resultMap.put("parent_approve_date", "20131021");
						resultMap.put("parent_email", "");
						resultMap.put("modify_sst_code", "90300");
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
	 * 
	 * <pre>
	 * 법정대리인 동의정보02.
	 * parent_rname_auth_key값이 null일때
	 * is_parent_approve 이고, parent_rname_auth_type 이 6 이면, 해당 필드는 null 이다.
	 * </pre>
	 */
	@Test
	public void updateGuardianInfoTest02() {

		new TestCaseTemplate(this.mockMvc).url("/member/idp/provisioning/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						ProvisioningReq req = new ProvisioningReq();
						req.setCmd("RXUpdateGuardianInfoIDP");
						HashMap resultMap = new HashMap();

						// cmd=RXUpdateGuardianInfoIDP&
						// target_sst_cd=41100&
						// parent_email=&
						// is_parent_approve=Y
						// &parent_rname_auth_key=14DPINETREEBUMOAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA1111
						// &modify_req_time=131044
						// &modify_req_date=20131021
						// &im_int_svc_no=200000011586
						// &parent_approve_sst_cd=90300
						// &parent_type=0
						// &sp_id=OMP10000
						// &modify_sst_code=90300
						// &parent_approve_date=20131021
						// &parent_birthday=19701111
						// &parent_rname_auth_type=1
						// &parent_name=%EB%B6%80%EB%AA%A8%EB%8B%98
						// &trx_no=900002013102113104418589

						resultMap.put("systemID", "S001");
						resultMap.put("tenantID", "S01");
						resultMap.put("sp_id", "90000");
						resultMap.put("target_sst_cd", "10100");
						resultMap.put("im_int_svc_no", "shop_3940");
						resultMap.put("parent_type", "0");
						resultMap.put("parent_rname_auth_type", "6");
						resultMap.put("parent_name", "홍길동");
						resultMap.put("parent_birthday", "20140612");

						resultMap.put("parent_approve_sst_code", "1111");
						resultMap.put("is_parent_approve", "Y");
						resultMap.put("parent_rname_auth_key", null);
						resultMap.put("parent_approve_date", "20130122");
						resultMap.put("parent_email", "");
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

}
