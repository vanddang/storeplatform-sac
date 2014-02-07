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
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailInformationRes;
import com.skplanet.storeplatform.sac.member.common.constant.TestMemberConstant;

@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class DetailInformationTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(DetailInformationTest.class);

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
	 * 판매자 기본정보 조회(모든키존재).
	 * </pre>
	 */
	@Test
	public void detailInformationFull() {

		new TestCaseTemplate(this.mockMvc)
				.url(TestMemberConstant.PREFIX_SELLER_PATH
						+ "/detailInformation/v1?sellerKey=IF1023038273420100111202617&aid=OA00049881")
				.httpMethod(HttpMethod.GET).success(DetailInformationRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						DetailInformationRes res = (DetailInformationRes) result;
						assertThat(res.getSellerKey(), notNullValue());
						LOGGER.debug("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 판매자 기본정보 조회(sellerKey만존재).
	 * </pre>
	 */
	@Test
	public void detailInformationSellerKey() {

		new TestCaseTemplate(this.mockMvc)
				.url(TestMemberConstant.PREFIX_SELLER_PATH
						+ "/detailInformation/v1?sellerKey=IF102158942020090723111912&aid=&keyType=INSD_SELLERMBR_NO")
				.httpMethod(HttpMethod.GET).success(DetailInformationRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						DetailInformationRes res = (DetailInformationRes) result;
						assertThat(res.getSellerKey(), notNullValue());
						LOGGER.debug("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 판매자 기본정보 조회(aid만 존재).
	 * </pre>
	 */
	@Test
	public void detailInformationAid() {

		new TestCaseTemplate(this.mockMvc)
				.url(TestMemberConstant.PREFIX_SELLER_PATH + "/detailInformation/v1?aid=OM00044495")
				.httpMethod(HttpMethod.GET).success(DetailInformationRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						DetailInformationRes res = (DetailInformationRes) result;
						assertThat(res.getSellerKey(), notNullValue());
						LOGGER.debug("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 판매자 기본정보 조회(모든키미존재)
	 * </pre>
	 */
	@Test
	public void detailInformationEmpty() {

		new TestCaseTemplate(this.mockMvc)
				.url(TestMemberConstant.PREFIX_SELLER_PATH + "/detailInformation/v1?sellerKey=&aid=")
				.httpMethod(HttpMethod.GET).success(DetailInformationRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						DetailInformationRes res = (DetailInformationRes) result;
						assertThat(res.getSellerKey(), notNullValue());
						LOGGER.debug("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}
}
