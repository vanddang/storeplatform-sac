package com.skplanet.storeplatform.sac.member.seller.controller;

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
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateSubsellerRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailSubsellerRes;
import com.skplanet.storeplatform.sac.member.common.constant.TestMemberConstant;

/**
 * 판매자 서브계정 상세조회.
 * 
 * Updated on : 2014. 4. 24. Updated by : Rejoice, Burkhan
 */
@ActiveProfiles(value = "local")

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class DetailSubsellerTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(DetailSubsellerTest.class);

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
	 * 판매자 서브계정 상세조회.
	 * </pre>
	 */
	@Test
	public void createSubseller() {

		new TestCaseTemplate(this.mockMvc)
				.url(TestMemberConstant.PREFIX_SELLER_PATH
						+ "/detailSubseller/v1?subSellerKey=SS201403101028164500001552").httpMethod(HttpMethod.GET)
				.success(DetailSubsellerRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						DetailSubsellerRes res = (DetailSubsellerRes) result;
						assertThat(res.getSubSellerMbr(), notNullValue());
						LOGGER.debug("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

}
