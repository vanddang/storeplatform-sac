package com.skplanet.storeplatform.sac.api.v1.member.idp;

import static org.hamcrest.CoreMatchers.notNullValue;
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
public class RXCreateUserIDPTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(RXCreateUserIDPTest.class);

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
	 * 로그인 상태정보 배포.
	 * 성공 Case
	 * 로그인 상태 정보 : 가능
	 * </pre>
	 */
	@Test
	public void rXCreateUserIDP() {

		new TestCaseTemplate(this.mockMvc).url("/member/idp/provisioning/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						ProvisioningReq req = new ProvisioningReq();
						req.setCmd("RXCreateUserIDP");
						HashMap resultMap = new HashMap();
						resultMap.put("user_di", "");
						resultMap.put("is_parent_approve", "N");
						resultMap.put("marketingYn", "N");
						resultMap.put("parent_rname_auth_type", "");
						resultMap.put("sp_id", "OMP10000");
						resultMap.put("parent_approve_sst_code", "");
						resultMap.put("biz_name", "");
						resultMap.put("rname_auth_sst_code", "");
						resultMap.put("user_name", "%EA%B9%80%EC%9A%A9%ED%98%B8");
						resultMap.put("target_sst_cd", "41100");
						resultMap.put("rname_auth_mns_code", "");
						resultMap.put("via_sst_code", "90300");
						resultMap.put("is_user_tn_auth", "Y");
						resultMap.put("user_tn_type", "M");
						resultMap.put("user_city_name", "");
						resultMap.put("is_im_changed", "N");
						resultMap.put("is_email_auth", "N");
						resultMap.put("emailYn", "Y");
						resultMap.put("user_ci", "");
						resultMap.put("parent_birthday", "");
						resultMap.put("parent_rname_auth_key", "");
						resultMap.put("im_int_svc_no", "900000013717");
						resultMap.put("user_birthday", "19860528");
						resultMap.put("is_mkt_email_recv", "");
						resultMap.put("is_biz_auth", "N");
						resultMap.put("auth_type", "4");
						resultMap.put("old_id", "");
						resultMap.put("is_user_tn_own", "Y");
						resultMap.put("user_id", "ggissggiss013");
						resultMap.put("user_nation_code", "KR");
						resultMap.put("browser_code", "");
						resultMap.put("PAS_INFO", "");
						resultMap.put("parent_approve_date", "");
						resultMap.put("join_sst_code", "41100");
						resultMap.put("user_area_code", "");
						resultMap.put("join_ip", "203.236.3.239");
						resultMap.put("ocb_join_code", "Y");
						resultMap.put("parent_type", "");
						resultMap.put("rname_auth_mbr_code", "");
						resultMap.put("user_nation_name", "Korea");
						resultMap.put("join_time", "111923");
						resultMap.put("modify_sst_code", "90300");
						resultMap.put("user_area_name", "");
						resultMap.put("os_code", "");
						resultMap.put("user_key", "IM190000001371720131002111911");
						resultMap.put("modify_req_time", "111923");
						resultMap.put("user_tn", "01039330198");
						resultMap.put("join_date", "20131002");
						resultMap.put("parent_email", "");
						resultMap.put("im_mem_type_cd", "100");
						resultMap.put("user_sex", "M");
						resultMap.put("join_path_code", "W");
						resultMap.put("modify_req_date", "20131002");
						resultMap.put("rname_auth_date", "");
						resultMap
								.put("join_sst_list",
										"41100%2CTAC001%5ETAC002%5ETAC003%5ETAC004%5ETAC005%5ETAC006%2C20131002%2C111923%7C42100%2CER%5EPK%2C20131002%2C111924%7C90300%2Cnull%2C20131002%2C111923");
						resultMap.put("trx_no", "900002013100211192416583");
						resultMap.put("biz_number", "");
						resultMap.put("user_tn_nation_cd", "82");
						resultMap.put("is_rname_auth", "N");
						resultMap.put("user_type", "1");
						resultMap.put("user_email", "kyh0528%4011st.co.kr");
						resultMap.put("parent_name", "");
						resultMap.put("user_status_code", "10");
						req.setReqParam(resultMap);

						LOGGER.info("request param : {}", req.toString());
						return req;
					}
				}).success(ProvisioningRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						ProvisioningRes res = (ProvisioningRes) result;
						assertThat(res.getImResult(), notNullValue());
						LOGGER.info("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}
}
