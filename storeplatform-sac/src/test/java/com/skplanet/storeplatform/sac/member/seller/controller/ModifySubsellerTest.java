package com.skplanet.storeplatform.sac.member.seller.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailSubsellerRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.RemoveSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.RemoveSubsellerRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.UpdateSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.UpdateSubsellerRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.member.common.constant.TestMemberConstant;
import com.skplanet.storeplatform.sac.member.common.util.ConvertMapperUtils;
import com.skplanet.storeplatform.sac.member.seller.service.SellerSubService;

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
	 * 판매자 서브계정 수정.
	 * </pre>
	 */
	@Test
	public void updateSubseller() {
		new TestCaseTemplate(this.mockMvc).url(TestMemberConstant.PREFIX_SELLER_PATH + "/modifySubseller/v1")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						UpdateSubsellerReq req = new UpdateSubsellerReq();

						// 필수
						req.setSellerKey(createSubsellerReq.getSellerKey());
						req.setSubSellerKey(createSubsellerRes.getSubSellerKey());
						req.setSubSellerMemo("aaaaaaaaa");
						req.setSubSellerEmail("updateSubseller@yopmail.com");
						req.setSubSellerPw("1234qwer");

						LOGGER.debug("request param : {}", req.toString());
						return req;
					}
				}).success(UpdateSubsellerRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						UpdateSubsellerRes res = (UpdateSubsellerRes) result;
						LOGGER.debug("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 판매자 회원 서브 계정 조회 및 삭제.
	 * </pre>
	 */
	@After
	public void after() {

		// 판매자 회원 서브 계정 상세조회
		DetailSubsellerReq DetailSubsellerReq = new DetailSubsellerReq();
		DetailSubsellerReq.setSubSellerKey(createSubsellerRes.getSubSellerKey());
		DetailSubsellerRes DetailSubsellerRes = this.sellerSubService.detailSubseller(header, DetailSubsellerReq);
		LOGGER.debug(ConvertMapperUtils.convertObjectToJson(DetailSubsellerRes));

		// 판매자 회원 서브 계정 삭제
		RemoveSubsellerReq RemoveSubsellerReq = new RemoveSubsellerReq();
		RemoveSubsellerReq.setSellerKey(createSubsellerReq.getSellerKey());
		List<String> removeKeyList;
		removeKeyList = new ArrayList<String>();
		removeKeyList.add(createSubsellerRes.getSubSellerKey());
		RemoveSubsellerReq.setSubSellerKeyList(removeKeyList);
		RemoveSubsellerRes RemoveSubsellerRes = this.sellerSubService.remSubseller(header, RemoveSubsellerReq);
		LOGGER.debug(ConvertMapperUtils.convertObjectToJson(RemoveSubsellerRes));

	}
}
