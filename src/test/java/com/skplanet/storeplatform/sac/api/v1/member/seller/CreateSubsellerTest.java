package com.skplanet.storeplatform.sac.api.v1.member.seller;

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
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateSubsellerRes;

/**
 * 판매자 서브계정 등록/수정
 * 
 * Updated on : 2014. 1. 20. Updated by : 한서구, 부르칸.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class CreateSubsellerTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(CreateSubsellerTest.class);

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	/**
	 * 
	 * <pre>
	 * before method 설명.
	 * </pre>
	 */
	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	/**
	 * <pre>
	 * 판매자 서브계정 등록.
	 * </pre>
	 */
	@Test
	public void createSubseller() {

		new TestCaseTemplate(this.mockMvc).url(MemberTestConstant.PREFIX_SELLER_PATH + "/createSubseller/v1")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						CreateSubsellerReq req = new CreateSubsellerReq();

						req.setSellerKey("IF102102956220120711144039");
						req.setSubSellerID("ID323R4118");
						req.setSubSellerPW("12312312");
						req.setSubSellerMemo("test");
						req.setSubSellerEmail("ID11@qqq.com");

						// 새로 추가됨
						req.setSubSellerKey("SS201402041452205570000558");
						req.setMemberPW("12312312");
						req.setOldPW("a");
						req.setIsNew("N");

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
