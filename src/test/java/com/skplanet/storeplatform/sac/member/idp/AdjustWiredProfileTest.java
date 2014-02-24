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
import com.skplanet.storeplatform.sac.member.idp.service.IdpService;
import com.skplanet.storeplatform.sac.member.idp.vo.ProvisioningReq;
import com.skplanet.storeplatform.sac.member.idp.vo.ProvisioningRes;

/**
 * 
 * 프로파일 변경 Provisioning (유선, 통합 회원) 테스트.
 * 
 * Updated on : 2014. 2. 17. Updated by : 반범진. 지티소프트
 */
@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class AdjustWiredProfileTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdjustWiredProfileTest.class);

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Autowired
	private IdpService idpService;

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
	 * 프로파일 변경 Provisioning (유선, 통합 회원) 테스트 기존 아이디.
	 * 성공 Case
	 * </pre>
	 */
	@Test
	public void adjustWiredProfileTest01() {

		// <provisionInterface> CMD[ adjustWiredProfile] REQUEST URL [
		// http://sbeta.itopping.co.kr/userpoc/IF/IDPSubsProv.omp?cmd=adjustWiredProfile&user_address=%EC%9D%B8%EC%B2%9C+%EA%B3%84%EC%96%91%EA%B5%AC+%EB%8F%99%EC%96%91%EB%8F%99&user_address2=595-3+%ED%95%9C%EB%AB%BC%EC%9C%84%EB%84%88%EC%8A%A4%EB%B9%8C+102%EB%8F%99+402%ED%98%B8&is_foreign=N&user_sex=F&user_key=IF1023112421620111107130852&user_id=newjungyb&user_birthday=19880903&user_zipcode=407340]
		// svcMngNum [ null] mdn [ null] modelId [ null]
		// userpoc_idp2.log:2013-01-24 16:06:11,769 [ajp-9209-3] INFO
		// (com.omp.market.intf.idp.action.IdpOmpSubscriptAction:132) - <provisionInterface> CMD[ adjustWiredProfile]
		// REQUEST URL [
		// http://sbeta.itopping.co.kr/userpoc/IF/IDPSubsProv.omp?cmd=adjustWiredProfile&user_key=IF1023548360620120726182243&user_id=expertman99&is_rname_auth=N&is_foreign=N]
		// svcMngNum [ null] mdn [ null] modelId [ null]

		new TestCaseTemplate(this.mockMvc).url("/member/idp/provisioning/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
			@Override
			public Object requestBody() {
				ProvisioningReq req = new ProvisioningReq();
				req.setCmd("adjustWiredProfile");
				HashMap map = new HashMap();
				map.put("systemID", "W");
				map.put("tenantID", "S01");

				/* vanddangtest020 */
				map.put("user_key", "IF1527016020140211145705");
				map.put("im_int_svc_no", "");
				map.put("user_phone", "01066786221,,SSMF,KTF");
				map.put("user_id", "vanddangtest020");
				map.put("user_sex", "M"); //M/F
				map.put("user_birthday", "19831210");
				map.put("user_email", "vanddang@gmail.com");
				map.put("is_rname_auth", "N");
				map.put("user_name", "테스터");
				map.put("user_social_number", "8312101");

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
	 * <pre>
	 * 프로파일 변경 Provisioning (유선, 통합 회원) 테스트 통합 아이디.
	 * 성공 Case
	 * </pre>
	 */
	@Test
	public void adjustWiredProfileTest02() {

		new TestCaseTemplate(this.mockMvc).url("/member/idp/provisioning/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
			@Override
			public Object requestBody() {
				ProvisioningReq req = new ProvisioningReq();
				req.setCmd("adjustWiredProfile");
				HashMap map = new HashMap();
				map.put("systemID", "W");
				map.put("tenantID", "S01");

				map.put("user_key", "IM190000008354020140206191502");
				map.put("im_int_svc_no", "900000083540");
				map.put("user_phone", "01099999991,,SSMD,KTF|01099999990,,SSMD,KTF|01099999989,,SSMD,KTF");
				map.put("user_id", "sacuser0011");
				map.put("user_sex", "F");
				map.put("user_birthday", "19831210");
				map.put("user_email", "sacuser0011@yopmail.com");
				map.put("is_rname_auth", "Y");
				map.put("user_name", "테스터");
				map.put("user_social_number", "8312101");

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
	 * 프로파일 변경 Provisioning (유선, 통합 회원) 테스트.
	 */
	@Test
	public void changeMobileIDTestService() {

		try {
			HashMap map = new HashMap();
			map.put("systemID", "W");
			map.put("tenantID", "S01");

			/* vanddangtest022 */
			map.put("user_key", "IF1527021020140217161208");
			map.put("im_int_svc_no", "");
			map.put("user_phone", "01099999997,,SSMD,KTF|01099999996,,SSMD,KTF");
			map.put("user_id", "vanddangtest022");
			map.put("user_sex", "M"); //M/F
			map.put("user_birthday", "19831210");
			map.put("user_email", "vanddang@gmail.com");
			map.put("is_rname_auth", "Y");
			map.put("user_name", "테스터");
			map.put("user_social_number", "8312101");

			this.idpService.adjustWiredProfile(map);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
