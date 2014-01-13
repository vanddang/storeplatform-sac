package com.skplanet.storeplatform.sac.api.v1.member.user;

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
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByMdnReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByMdnRes;
import com.skplanet.storeplatform.sac.member.user.service.LoginService;

/**
 * 모바일 전용 회원 인증 (MDN 인증) Class Test
 * 
 * Updated on : 2014. 1. 8. Updated by : 반범진, 지티소프트
 */
@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class AuthorizeByMdnTest {

	private static final Logger logger = LoggerFactory.getLogger(AuthorizeByMdnTest.class);

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	@Test
	public void shouldAuthorizeByMdn() {
		AuthorizeByMdnReq req = new AuthorizeByMdnReq();
		req.setDeviceModelNo("SHW-M250S");
		req.setOsVerOrg("1.0");
		
		req.setDeviceId("01073215212");
		//req.setDeviceId("01088870008");
		req.setDeviceIdType("msisdn");
		req.setDeviceTelecom("KT");
		req.setNativeId("358362045580844");
		req.setRooting("Y");
		req.setDeviceAccount("vanddang@gmail.com");
		req.setIsAutoUpdate("Y");
		req.setScVer("1.0");
		
		
		try {
			AuthorizeByMdnRes res = this.loginService.authorizeByMdn(null, req);
			logger.info("res : {} " + res.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		/*new TestCaseTemplate(this.mockMvc).url("/dev/member/user/authorizeByMdn/v1")
				.httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						AuthorizeByMdnReq req = new AuthorizeByMdnReq();
						req.setDeviceAccount("vanddang@gmail.com");
						req.setDeviceId("0101231234");
						req.setDeviceModelNo("SHP-112");
						req.setDeviceTelecom("SKT");
						req.setIsAutoUpdate("Y");
						req.setNativeId("358362045580844");
						req.setOsVerOrg("1.0");
						req.setScVer("1.0");
						req.setRooting("Y");
						
						logger.info("request param : {}", req.toString());
						
						return req;
					}
				}).success(AuthorizeByMdnRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus,
							RunMode runMode) {
						AuthorizeByMdnRes res = (AuthorizeByMdnRes) result;
						logger.info("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}*/
}
