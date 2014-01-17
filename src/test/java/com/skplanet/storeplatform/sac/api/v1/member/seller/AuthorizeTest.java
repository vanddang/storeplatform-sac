package com.skplanet.storeplatform.sac.api.v1.member.seller;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

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
import com.skplanet.storeplatform.sac.api.v1.member.constant.MemberTestConstant;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeRes;

/**
 * Calss 설명
 * 
 * Updated on : 2014. 1. 14. Updated by : 김경복, 부르칸.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class AuthorizeTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizeTest.class);

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
	 * 회원 인증.
	 * </pre>
	 */
	@Test
	public void authorize() {

		new TestCaseTemplate(this.mockMvc).url(MemberTestConstant.PREFIX_SELLER_PATH + "/authorize/v1")
				.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						AuthorizeReq req = new AuthorizeReq();
						req.setSellerId("qatestqwe");
						req.setSellerPW("LmmYUGEXDpT/HvybdNVG7nfq8KwfR5EFtcrXCne9LVs=11");
						// req.setSellerPW("LmmYUGEXDpT/HvybdNVG7nfq8KwfR5EFtcrXCne9LVs=324234234");
						// loginSellerRequest.setSellerID("tattermedia");
						// loginSellerRequest.setSellerPW("FR21R/X4xRkrVm598w+JK4oW++/bqXP2+qMAZcg7/Cg=");
						LOGGER.debug("request param : {}", req.toString());
						return req;
					}
				}).success(AuthorizeRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						AuthorizeRes res = (AuthorizeRes) result;
						assertThat(res.getSellerMbr(), notNullValue());
						LOGGER.debug("response param : {}", res.getSellerMbr().toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}
}
