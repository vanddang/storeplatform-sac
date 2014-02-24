/**
 * 
 */
package com.skplanet.storeplatform.sac.member.user.sci.controller;

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
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.ChangedDeviceHistorySacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.ChangedDeviceHistorySacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchDeviceIdSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchDeviceIdSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.common.util.MockRequestAttributeInitializer;

/**
 * Calss 설명
 * 
 * Updated on : 2014. 2. 24. Updated by : 김다슬, 인크로스.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@WebAppConfiguration
@TransactionConfiguration
@Transactional
public class DeviceSCIControllerTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceSCIControllerTest.class);

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
	public void testSearchDeviceId() throws Exception {
		new TestCaseTemplate(this.mvc).url("/member/user/sci/searchDeviceId").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						SearchDeviceIdSacReq searchDeviceIdSacReq = new SearchDeviceIdSacReq();
						searchDeviceIdSacReq.setUserKey("US201402110557052730002230");
						searchDeviceIdSacReq.setDeviceKey("DE201402120409541480001552");
						return searchDeviceIdSacReq;
					}
				}).success(SearchDeviceIdSacRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						SearchDeviceIdSacRes res = (SearchDeviceIdSacRes) result;
						LOGGER.info("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	@Test
	public void testSearchChangedDeviceHistory() throws Exception {
		new TestCaseTemplate(this.mvc).url("/member/user/sci/searchChangedDeviceHistory").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						ChangedDeviceHistorySacReq changedDeviceHistorySacReq = new ChangedDeviceHistorySacReq();
						changedDeviceHistorySacReq.setUserKey("IW1023090104420100127095457");
						changedDeviceHistorySacReq.setDeviceId("01064779017"); // deviceKey = '01064779017'
						return changedDeviceHistorySacReq;
					}
				}).success(ChangedDeviceHistorySacRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						ChangedDeviceHistorySacRes res = (ChangedDeviceHistorySacRes) result;
						LOGGER.info("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}
}
