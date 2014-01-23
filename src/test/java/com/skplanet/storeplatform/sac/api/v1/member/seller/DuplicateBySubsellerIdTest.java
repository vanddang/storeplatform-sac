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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.skplanet.storeplatform.framework.test.SuccessCallback;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate.RunMode;
import com.skplanet.storeplatform.sac.api.v1.member.constant.MemberTestConstant;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DuplicateByIdEmailRes;

/**
 * 판매자 서브계정 ID 중복체크.
 * 
 * Updated on : 2014. 1. 23. Updated by : 한서구, 부르칸.
 */
@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class DuplicateBySubsellerIdTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(DuplicateBySubsellerIdTest.class);

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
	 * 판매자 서브계정 ID 중복체크.
	 * </pre>
	 */
	@Test
	public void duplicateBySubsellerId() {
		new TestCaseTemplate(this.mockMvc)
				.url(MemberTestConstant.PREFIX_SELLER_LOCAL_PATH + "/duplicateBySubsellerId/v1?keyString=ID323R427")
				.httpMethod(HttpMethod.GET).success(DuplicateByIdEmailRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						DuplicateByIdEmailRes res = (DuplicateByIdEmailRes) result;
						assertThat(res.getIsRegistered(), notNullValue());
						LOGGER.debug("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * ID값이 없을경우.
	 * </pre>
	 */
	@Test
	public void duplicateByIdEmpty() {
		new TestCaseTemplate(this.mockMvc)
				.url(MemberTestConstant.PREFIX_SELLER_PATH + "/duplicateByIdEmail/v1?keyType=id&keyString=")
				.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.httpMethod(HttpMethod.GET).success(DuplicateByIdEmailRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						DuplicateByIdEmailRes res = (DuplicateByIdEmailRes) result;
						assertThat(res.getIsRegistered(), notNullValue());
						LOGGER.debug("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 판매자 Email 중복체크.
	 * TODO keyString 주입
	 * </pre>
	 */
	@Test
	public void duplicateByEmail() {
		new TestCaseTemplate(this.mockMvc)
				.url(MemberTestConstant.PREFIX_SELLER_PATH
						+ "/duplicateByIdEmail/v1?keyType=email&keyString=op_tabs_1001@gmail.com")
				.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.httpMethod(HttpMethod.GET).success(DuplicateByIdEmailRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						DuplicateByIdEmailRes res = (DuplicateByIdEmailRes) result;
						assertThat(res.getIsRegistered(), notNullValue());
						LOGGER.debug("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}

	/**
	 * <pre>
	 * Email 정보가 없을경우.
	 * </pre>
	 */
	@Test
	public void duplicateByEmailEmpty() {
		new TestCaseTemplate(this.mockMvc)
				.url(MemberTestConstant.PREFIX_SELLER_PATH + "/duplicateByIdEmail/v1?keyType=email&keyString=")
				.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.httpMethod(HttpMethod.GET).success(DuplicateByIdEmailRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						DuplicateByIdEmailRes res = (DuplicateByIdEmailRes) result;
						assertThat(res.getIsRegistered(), notNullValue());
						LOGGER.debug("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}
}
