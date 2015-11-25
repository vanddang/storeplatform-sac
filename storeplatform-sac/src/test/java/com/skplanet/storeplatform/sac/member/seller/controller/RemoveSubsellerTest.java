package com.skplanet.storeplatform.sac.member.seller.controller;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

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
import com.skplanet.storeplatform.sac.client.member.vo.seller.RemoveSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.RemoveSubsellerRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.member.common.constant.TestMemberConstant;
import com.skplanet.storeplatform.sac.member.common.util.ConvertMapperUtils;
import com.skplanet.storeplatform.sac.member.seller.service.SellerSubService;

/**
 * 판매자 회원 서브 계정 탈퇴
 * 
 * Updated on : 2014. 1. 20. Updated by : 한서구, 부르칸.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class RemoveSubsellerTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(RemoveSubsellerTest.class);

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	/** [REQUEST]. */
	public static CreateSubsellerReq createSubsellerReq;

	/** [RESPONSE]. */
	public static CreateSubsellerRes createSubsellerRes;

	@Autowired
	private SellerSubService sellerSubService;

	public static TenantHeader tenantHeader;
	public static SacRequestHeader header;

	/**
	 * 
	 * <pre>
	 * before method 설명.
	 * </pre>
	 */
	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		tenantHeader = new TenantHeader();
		tenantHeader.setSystemId("S00-02001");
		tenantHeader.setTenantId("S00");

		header = new SacRequestHeader();
		header.setTenantHeader(tenantHeader);

		// 판매자 서브계정 등록.
		createSubsellerReq = new CreateSubsellerReq();
		createSubsellerReq.setSellerKey("SE201403272110139760001941");
		createSubsellerReq.setSubSellerId("mainPerson01_sub001");
		createSubsellerReq.setSubSellerMemo("test2");
		createSubsellerReq.setSubSellerEmail("omc97asefd@hanmail.net");
		createSubsellerReq.setSubSellerPw("1234qwer");
		createSubsellerRes = this.sellerSubService.regSubseller(header, createSubsellerReq);
		LOGGER.debug(ConvertMapperUtils.convertObjectToJson(createSubsellerRes));
	}

	/**
	 * <pre>
	 * 판매자 서브계정 삭제.
	 * </pre>
	 */
	@Test
	public void removeSubseller() {

		new TestCaseTemplate(this.mockMvc).url(TestMemberConstant.PREFIX_SELLER_PATH + "/removeSubseller/v1")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						RemoveSubsellerReq req = new RemoveSubsellerReq();

						req.setSellerKey(createSubsellerReq.getSellerKey());

						List<String> removeKeyList;
						removeKeyList = new ArrayList<String>();
						removeKeyList.add(createSubsellerRes.getSubSellerKey());
						req.setSubSellerKeyList(removeKeyList);

						LOGGER.debug("request param : {}", req.toString());
						return req;
					}
				}).success(RemoveSubsellerRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						RemoveSubsellerRes res = (RemoveSubsellerRes) result;
						assertThat(res.getRemoveCnt(), notNullValue());
						LOGGER.debug("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}
}
