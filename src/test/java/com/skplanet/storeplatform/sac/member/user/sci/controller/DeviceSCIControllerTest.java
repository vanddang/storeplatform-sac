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
import com.skplanet.storeplatform.sac.member.common.util.TestConvertMapperUtils;

/**
 * 단말 정보 관련 내부메소드 호출 SCI Controller Test.
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

	/**
	 * <pre>
	 * 단말 ID 정보(msisdn|uuid|mac) 조회.
	 * </pre>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSearchDeviceId() {
		try {
			new TestCaseTemplate(this.mvc)

					.url("/member/user/sci/searchDeviceId").httpMethod(HttpMethod.POST)
					.requestBody(new RequestBodySetter() {
						@Override
						public Object requestBody() {
							SearchDeviceIdSacReq searchDeviceIdSacReq = new SearchDeviceIdSacReq();
							// searchDeviceIdSacReq.setUserKey("US201402110557052730002230");
							// searchDeviceIdSacReq.setDeviceKey("DE201402120409541480001552");
							searchDeviceIdSacReq.setUserKey("IF1527585620140521131025");
							searchDeviceIdSacReq.setDeviceKey("DE201405211313021180004529");
							LOGGER.debug("[DeviceSCI-REQUEST] : \n{}",
									TestConvertMapperUtils.convertObjectToJson(searchDeviceIdSacReq));
							return searchDeviceIdSacReq;
						}
					}).success(SearchDeviceIdSacRes.class, new SuccessCallback() {
						@Override
						public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
							SearchDeviceIdSacRes res = (SearchDeviceIdSacRes) result;
							LOGGER.info("response param : {}", res.toString());
						}
					}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
		} catch (Exception e) {
			LOGGER.info("\n>>error : {}", e.getMessage());
		}
	}

	/**
	 * <pre>
	 * 기기변경 이력 조회.
	 * </pre>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSearchChangedDeviceHistory() throws Exception {
		new TestCaseTemplate(this.mvc).url("/member/user/sci/DeviceSCIController/searchChangedDeviceHistory")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						ChangedDeviceHistorySacReq changedDeviceHistorySacReq = new ChangedDeviceHistorySacReq();
						changedDeviceHistorySacReq.setUserKey("IW1023090104420100127095457");
						changedDeviceHistorySacReq.setDeviceId("01064779017"); // deviceKey = '01064779017'
						LOGGER.debug("[DeviceSCI-REQUEST] : \n{}",
								TestConvertMapperUtils.convertObjectToJson(changedDeviceHistorySacReq));
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
