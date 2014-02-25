/**
 * 
 */
package com.skplanet.storeplatform.sac.member.user.sci.controller;

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
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.skplanet.storeplatform.framework.test.RequestBodySetter;
import com.skplanet.storeplatform.framework.test.SuccessCallback;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate.RunMode;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.common.util.MockRequestAttributeInitializer;

/**
 * 회원정보 관련 내부메소드 호출 SCI Controller Test.
 * 
 * Updated on : 2014. 2. 25. Updated by : 김다슬, 인크로스.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@WebAppConfiguration
@TransactionConfiguration
@Transactional
public class SearchUserSCIControllerTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchUserSCIControllerTest.class);

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;

	/**
	 * 
	 * <pre>
	 * 초기화.
	 * </pre>
	 */
	@Before
	public void before() {
		SacRequestHeader sacRequestHeader = new SacRequestHeader();
		TenantHeader tenantHeader = new TenantHeader();
		tenantHeader.setTenantId("S01");
		tenantHeader.setSystemId("S001");
		sacRequestHeader.setTenantHeader(tenantHeader);

		MockRequestAttributeInitializer.init(SacRequestHeader.class.getName(), sacRequestHeader);
		this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testSearchUserByUserKey() throws Exception {
		new TestCaseTemplate(this.mvc).url("/member/user/sci/searchUserByUserKey").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						SearchUserSacReq searchUserSacReq = new SearchUserSacReq();
						List<String> userKeyList = new ArrayList<String>();
						userKeyList.add("IW1023284651220101007215215"); // 회원정보에 등록된 deviceId가 한 개.
						userKeyList.add("IM142100006719244201304082142"); // 회원정보에 등록된 deviceId가 여러개.
						userKeyList.add("IW1024171529820110627132506"); // 회원정보 없음.

						searchUserSacReq.setUserKeyList(userKeyList);
						return searchUserSacReq;
					}
				}).success(SearchUserSacRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						SearchUserSacRes res = (SearchUserSacRes) result;
						LOGGER.info("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}
}
