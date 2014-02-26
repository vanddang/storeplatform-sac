package com.skplanet.storeplatform.sac.member.seller.controller;

import org.junit.After;
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
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateSubsellerRes;
import com.skplanet.storeplatform.sac.member.common.constant.TestMemberConstant;

/**
 * 판매자 서브계정 수정
 * 
 * Updated on : 2014. 1. 20. Updated by : 한서구, 부르칸.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class ModifySubsellerTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ModifySubsellerTest.class);

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	/** [REQUEST]. */
	public static CreateSubsellerReq createSubsellerReq;

	/** [RESPONSE]. */
	public static CreateSubsellerRes createSubsellerRes;

	/**
	 * 
	 * <pre>
	 * before method 설명.
	 * </pre>
	 */
	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

		createSubsellerReq = new CreateSubsellerReq();
	}

	/**
	 * <pre>
	 * 판매자 서브계정 등록.
	 * </pre>
	 */
	@Test
	public void createSubseller() {

		new TestCaseTemplate(this.mockMvc).url(TestMemberConstant.PREFIX_SELLER_PATH + "/createSubseller/v1")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						// 필수
						createSubsellerReq.setSellerKey("IF1023501184720130823173955");
						createSubsellerReq.setSubSellerID("adfdfefs");
						// createSubsellerReq.setIsNew("Y");

						createSubsellerReq.setSubSellerMemo("test2");
						createSubsellerReq.setSubSellerEmail("omc97asefd@hanmail.net");

						// 새로 추가됨
						// createSubsellerReq.setSubSellerKey("SS201402061427346800000640");
						createSubsellerReq.setMemberPW("1234567999");
						// createSubsellerReq.setOldPW("1234567999");

						LOGGER.debug("request param : {}", createSubsellerReq.toString());
						return createSubsellerReq;
					}
				}).success(CreateSubsellerRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						createSubsellerRes = (CreateSubsellerRes) result;
						// assertThat(res.getSubSellerKey(), notNullValue());
						LOGGER.debug("response param : {}", createSubsellerRes.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 판매자 서브계정 수정.
	 * </pre>
	 */
	@After
	public void after() {

		new TestCaseTemplate(this.mockMvc).url(TestMemberConstant.PREFIX_SELLER_PATH + "/modifySubseller/v1")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						CreateSubsellerReq req = new CreateSubsellerReq();

						// 필수
						req.setSellerKey("IF1023501184720130823173955");
						// req.setSubSellerID("adfdfefs");
						// req.setIsNew("N");

						req.setSubSellerMemo("test2");
						req.setSubSellerEmail("omc97@hanmail.net");

						// 새로 추가됨
						req.setSubSellerKey(createSubsellerRes.getSubSellerKey());
						req.setMemberPW("1234567999");
						// req.setOldPW("1234567999");

						LOGGER.debug("request param : {}", req.toString());
						return req;
					}
				}).success(CreateSubsellerRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						CreateSubsellerRes res = (CreateSubsellerRes) result;
						// assertThat(res.getSubSellerKey(), notNullValue());
						LOGGER.debug("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}
}
