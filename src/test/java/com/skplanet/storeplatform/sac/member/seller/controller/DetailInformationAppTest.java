package com.skplanet.storeplatform.sac.member.seller.controller;

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
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailInformationForProductReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailInformationForProductRes;
import com.skplanet.storeplatform.sac.member.common.constant.TestMemberConstant;

/**
 * App상세 판매자 기본정보 조회.
 * 
 * Updated on : 2014. 4. 24. Updated by : Rejoice, Burkhan
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class DetailInformationAppTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(DetailInformationAppTest.class);

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
	 * 상품상세 판매자 정보 조회.
	 * </pre>
	 */
	@Test
	public void detailInformationFull() {
		new TestCaseTemplate(this.mockMvc)
				.url(TestMemberConstant.PREFIX_SELLER_PATH + "/detailInformationForProduct/v1")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						DetailInformationForProductReq req = new DetailInformationForProductReq();

						req.setSellerKey("IF142158860220090630104203");
						// req.setAid("OM00044497");

						LOGGER.debug("request param : {}", req.toString());
						return req;
					}
				}).success(DetailInformationForProductRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						DetailInformationForProductRes res = (DetailInformationForProductRes) result;
						LOGGER.debug("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

}
