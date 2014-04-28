package com.skplanet.storeplatform.sac.member.idp;

import static org.hamcrest.CoreMatchers.is;
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
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class RXUpdateAgreeUserIDPTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(RXUpdateAgreeUserIDPTest.class);

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
	 * 이용동의변경사이트목록배포
	 * </pre>
	 */
	@Test
	public void rXUpdateAgreeUserIDP() {

		new TestCaseTemplate(this.mockMvc).url("/member/idp/provisioning/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						ProvisioningReq req = new ProvisioningReq();
						req.setCmd("RXUpdateAgreeUserIDP");
						HashMap<String, String> resultMap = new HashMap<String, String>();

						resultMap.put("tenantID", "S01");
						resultMap.put("systemID", "S01-00IDP");

						resultMap.put("is_parent_approve", "N");
						resultMap.put("user_key", "IM190000002420120140428135742");
						resultMap.put("target_sst_cd", "41100");
						resultMap.put("im_int_svc_no", "900000024201");
						resultMap.put("user_nation_name", "Korea");
						resultMap.put("modify_req_time", "135741");
						resultMap.put("parent_approve_date", "");
						resultMap.put("biz_name", "");
						resultMap.put("user_nation_code", "KR");
						resultMap.put("user_di", "");
						resultMap.put("user_tn_type", "M");
						resultMap.put("parent_birthday", "");
						resultMap.put("parent_approve_sst_code", "");
						resultMap.put("biz_number", "");
						resultMap.put("PAS_INFO", "");
						resultMap.put("im_mem_type_cd", "100");
						resultMap.put("user_type", "1");
						resultMap.put("user_area_name", "");
						resultMap.put("rname_auth_mbr_code", "");
						resultMap.put("user_passwd_modify_date", "20140428");
						resultMap.put("is_im_changed", "N");
						resultMap.put("TELECOM", "SKT");
						resultMap.put("user_tn", "01035870955");
						resultMap.put("emailYn", "N");
						resultMap.put("user_name", "이행선");
						resultMap.put("is_rname_auth", "N");
						resultMap.put("is_email_auth", "N");
						resultMap.put("parent_name", "");
						resultMap.put("join_time", "135224");
						resultMap.put("ocb_join_code", "N");
						resultMap.put("user_birthday", "19910202");
						resultMap.put("user_ci", "");
						resultMap.put("join_path_code", "W");
						resultMap.put("rname_auth_date", "");
						resultMap.put("modify_req_date", "20140428");
						resultMap.put("user_city_name", "");
						resultMap.put("join_date", "20140428");
						resultMap.put("auth_type", "4");
						resultMap.put("join_sst_code", "41100");
						resultMap.put("user_email", "itachi20140428301@yopmail.com");
						resultMap.put("parent_rname_auth_type", "");
						resultMap
								.put("join_sst_list",
										"41100,TAC001^TAC002^TAC003^TAC004^TAC005^TAC006,20140428,135741,null|42100,ER^PK,20140428,135224,null|90300,null,20140428,135224,null");
						resultMap.put("is_user_tn_auth", "Y");
						resultMap.put("is_user_tn_own", "Y");
						resultMap.put("modify_sst_code", "90300");
						resultMap.put("parent_rname_auth_key", "");
						resultMap.put("parent_type", "");
						resultMap.put("is_biz_auth", "N");
						resultMap.put("trx_no", "900002014042813574187401");
						resultMap.put("parent_email", "");
						resultMap.put("user_id", "itachi20140428301");
						resultMap.put("join_ip", "121.165.99.86");
						resultMap.put("user_tn_nation_cd", "82");
						resultMap.put("user_passwd_type", "1");
						resultMap.put("rname_auth_sst_code", "");
						resultMap.put("sp_id", "OMP10000");
						resultMap.put("user_status_code", "10");
						resultMap.put("via_sst_code", "90300");
						resultMap.put("user_sex", "M");
						resultMap.put("user_area_code", "");
						resultMap.put("rname_auth_mns_code", "");

						req.setReqParam(resultMap);

						LOGGER.info("request param : {}", req.toString());
						return req;
					}
				}).success(ProvisioningRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						ProvisioningRes res = (ProvisioningRes) result;
						assertThat(res.getImResult(), notNullValue());
						assertThat(res.getImResult().getCmd(), is("RXUpdateAgreeUserIDP"));
						LOGGER.info("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

}
