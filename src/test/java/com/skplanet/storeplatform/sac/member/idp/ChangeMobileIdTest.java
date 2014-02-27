/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
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
import com.skplanet.storeplatform.sac.member.idp.service.IdpProvisionService;
import com.skplanet.storeplatform.sac.member.idp.vo.ProvisioningReq;
import com.skplanet.storeplatform.sac.member.idp.vo.ProvisioningRes;

/**
 * 
 * 회선 변경 정보 Provisioning (기기변경) 테스트.
 * 
 * Updated on : 2014. 2. 10. Updated by : 반범진. 지티소프트
 */
@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class ChangeMobileIdTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ChangeMobileIdTest.class);

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Autowired
	private IdpProvisionService idpService;

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
	 * 회선 변경 정보 Provisioning (기기변경).
	 * 성공 Case
	 * </pre>
	 */
	@Test
	public void changeMobileIdTest01() {

		// <provisionInterface> CMD[ changeMobileID] REQUEST URL [
		// http://sbeta.itopping.co.kr/userpoc/IF/IDPSubsProv.omp?cmd=changeMobileID&svc_mng_num=7035516765&mdn=01049545098&be_mdn=01049545098&model_id=SSOG&min=1049545098]
		// svcMngNum [ 7035516765] mdn [ 01049545098] modelId [ SSOG]

		new TestCaseTemplate(this.mockMvc).url("/member/idp/provisioning/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
			@Override
			public Object requestBody() {
				ProvisioningReq req = new ProvisioningReq();
				req.setCmd("changeMobileID");
				HashMap map = new HashMap();
				map.put("systemID", "W");
				map.put("tenantID", "S01");

				map.put("mdn", "01049545098");
				map.put("svc_mng_num", "70355167651");
				map.put("model_id", "SSMF");
				map.put("be_mdn", "01049545098");
				map.put("min", "1049545098");

				req.setReqParam(map);

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
	 * 회선 변경 정보 Provisioning (기기변경).
	 */
	@Test
	public void changeMobileIdTestService() {
		//cmd=changeMobileID&svc_mng_num=aaaa&mdn=01049545098&be_mdn=01049545098&model_id=LGFL&min=1049545098
		try {
			HashMap map = new HashMap();
			map.put("systemID", "W");
			map.put("tenantID", "S01");

			map.put("mdn", "01048088874");
			map.put("svc_mng_num", "1242345410");
			map.put("model_id", "SSMF");
			//map.put("be_mdn", "01048088874");
			map.put("min", "1048088874");

			this.idpService.executeChangeMobileID(map);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
