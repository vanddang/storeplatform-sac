package com.skplanet.storeplatform.sac.member.idp;

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
public class RXUpdateUserInfoIDPTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(RXUpdateUserInfoIDPTest.class);

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
	 * 변경된공통프로파일배포.
	 * </pre>
	 */
	@Test
	public void rXUpdateUserInfoIDP() {//

		new TestCaseTemplate(this.mockMvc).url("/member/idp/provisioning/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						ProvisioningReq req = new ProvisioningReq();
						req.setCmd("RXUpdateUserInfoIDP");
						/* 변경된공통프로파일배포 시작 */
						HashMap<String, String> resultMap = new HashMap<String, String>();

						resultMap.put("tenantID", "S01"); // 현재 하드코딩 파라미터가 정상으로 전달되어지면 지워야함.
						resultMap.put("systemID", "S01-00IDP");

						resultMap.put("biz_number", "");
						resultMap.put("modify_req_date", "20140428");
						resultMap.put("target_sst_cd", "41100");
						resultMap.put("im_int_svc_no", "900000084285");
						resultMap.put("user_tn_nation_cd", "82");
						resultMap.put("user_email", "itachi20140428678@yopmail.com");
						resultMap.put("modify_req_time", "151118");
						resultMap.put("user_tn", "01035870955");
						resultMap.put("biz_name", "");
						resultMap.put("is_user_tn_auth", "Y");
						resultMap.put("sp_id", "OMP10000");
						resultMap.put("is_user_tn_own", "Y");
						resultMap.put("modify_sst_code", "90300");
						resultMap.put("is_email_auth", "Y");
						resultMap.put("is_biz_auth", "N");
						resultMap.put("trx_no", "900002014042815111878448");
						resultMap.put("user_id", "itachi20140428001");
						resultMap.put("lang_code", "KOR");
						resultMap.put("user_tn_type", "M");

						/* 변경된공통프로파일배포 끝 */
						req.setReqParam(resultMap);

						LOGGER.info("request param : {}", req.toString());
						return req;
					}
				}).success(ProvisioningRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						ProvisioningRes res = (ProvisioningRes) result;
						assertThat(res.getImResult(), notNullValue());
						assertThat(res.getImResult().getCmd(), is("RXUpdateUserInfoIDP"));
						assertEquals("itachi20140428001", res.getImResult().getUserId());
						LOGGER.info("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}
}
