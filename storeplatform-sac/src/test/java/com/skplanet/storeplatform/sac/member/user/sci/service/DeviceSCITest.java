/**
 * 
 */
package com.skplanet.storeplatform.sac.member.user.sci.service;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.DeviceSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.ChangedDeviceHistorySacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.ChangedDeviceHistorySacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchDeviceIdSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchDeviceIdSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.common.util.MockRequestAttributeInitializer;
import com.skplanet.storeplatform.sac.member.common.util.TestConvertMapperUtils;

/**
 * 단말 정보 조회 내부메소드 인터페이스 Test.
 * 
 * Updated on : 2014. 2. 12. Updated by : 김다슬, 인크로스.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@WebAppConfiguration
@TransactionConfiguration
@Transactional
public class DeviceSCITest {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceSCITest.class);

	@Autowired
	private DeviceSCI deviceSCI;

	@Before
	public void before() {

		SacRequestHeader sacRequestHeader = new SacRequestHeader();
		TenantHeader tenant = new TenantHeader();
		tenant.setTenantId("S01");
		tenant.setSystemId("S001");
		sacRequestHeader.setTenantHeader(tenant);

		MockRequestAttributeInitializer.init(SacRequestHeader.class.getName(), sacRequestHeader);

	}

	/**
	 * <pre>
	 * 단말 MDN 정보 조회 SCI TEST. (개발기 data)
	 * - 검색결과 존재.
	 * </pre>
	 */
	@Test
	public void testGetDeviceId() {
		SearchDeviceIdSacReq request = new SearchDeviceIdSacReq();
		request.setUserKey("US201402061013557190001794");
		request.setDeviceKey("DE201402061013560500001022");

		LOGGER.debug("[DeviceSCI-REQUSET] : \n{}", TestConvertMapperUtils.convertObjectToJson(request));
		SearchDeviceIdSacRes result = this.deviceSCI.searchDeviceId(request);
		assertThat(result.getDeviceId(), notNullValue());

		LOGGER.debug("[DeviceSCI-REPONSE] : \n{}", TestConvertMapperUtils.convertObjectToJson(result));
	}

	// /**
	// * <pre>
	// * 단말 MDN 정보 조회 SCI TEST. (QA data)
	// * - 검색결과 존재.
	// * </pre>
	// */
	// @Test
	// public void testGetDeviceId() {
	// SearchDeviceIdSacReq request = new SearchDeviceIdSacReq();
	// request.setUserKey("US201402281808328020000001");
	// request.setDeviceKey("DE201402281808329680000001");
	//
	// LOGGER.debug("[DeviceSCI-REQUSET] : \n{}", TestConvertMapperUtils.convertObjectToJson(request));
	// SearchDeviceIdSacRes result = this.deviceSCI.searchDeviceId(request);
	// assertThat(result.getDeviceId(), notNullValue());
	//
	// LOGGER.debug("[DeviceSCI-REPONSE] : \n{}", TestConvertMapperUtils.convertObjectToJson(result));
	// }

	/**
	 * <pre>
	 * 단말 MDN 정보 조회 SCI TEST.
	 * - 검색결과 없음 (Exception).
	 * </pre>
	 */
	@Test
	public void testExceptGetDeviceId() {
		SearchDeviceIdSacReq request = new SearchDeviceIdSacReq();
		request.setUserKey("US201401241840125650000649");
		request.setDeviceKey("DE201401241840125800000296");

		LOGGER.debug("[DeviceSCI-REQUSET] : \n{}", TestConvertMapperUtils.convertObjectToJson(request));
		try {
			SearchDeviceIdSacRes result = this.deviceSCI.searchDeviceId(request);
			assertEquals(result.getDeviceId(), null);

			LOGGER.debug("[DeviceSCI-REPONSE] : \n{}", TestConvertMapperUtils.convertObjectToJson(result));
		} catch (StorePlatformException e) {
			assertEquals("SAC_MEM_0002", e.getErrorInfo().getCode());
			LOGGER.info("\nerror >> ", e);
		}
	}

	/**
	 * <pre>
	 * 기기변경이력 조회 SCI TEST.
	 * - 검색결과 존재. (deviceId로 조회)
	 * </pre>
	 */
	@Test
	public void testSearchChangedDeviceHistoryByDeviceId() {
		ChangedDeviceHistorySacReq request = new ChangedDeviceHistorySacReq();
		// request.setUserKey("IW1023090104420100127095457");
		// request.setDeviceId("01064779017"); // deviceKey = '01064779017'

		request.setUserKey("IW1314365020140612034108");
		request.setDeviceId("01066786220");

		LOGGER.debug("[DeviceSCI-REQUSET] : \n{}", TestConvertMapperUtils.convertObjectToJson(request));

		ChangedDeviceHistorySacRes result = this.deviceSCI.searchChangedDeviceHistory(request);
		assertEquals(result.getIsChanged(), "Y");
		assertThat(result.getDeviceKey(), notNullValue());
	}

	/**
	 * <pre>
	 * 기기변경이력 조회 SCI TEST.
	 * - 검색결과 존재. (deviceKey로 조회)
	 * </pre>
	 */
	@Test
	public void testSearchChangedDeviceHistoryByDeviceKey() {
		ChangedDeviceHistorySacReq request = new ChangedDeviceHistorySacReq();
		request.setUserKey("IM102317733722011201409999");
		request.setDeviceKey("DE201406050930427080004740");

		LOGGER.debug("[DeviceSCI-REQUSET] : \n{}", TestConvertMapperUtils.convertObjectToJson(request));
		ChangedDeviceHistorySacRes result = this.deviceSCI.searchChangedDeviceHistory(request);
		assertEquals(result.getIsChanged(), "Y");
		assertEquals(result.getDeviceKey(), request.getDeviceKey());
	}

	/**
	 * <pre>
	 * 기기변경이력 조회 SCI TEST.
	 * - 기기변경 이력 없음. (deviceId로 조회)
	 * </pre>
	 */
	@Test
	public void testNonChangedDeviceHistoryByDeviceId() {
		ChangedDeviceHistorySacReq request = new ChangedDeviceHistorySacReq();
		request.setUserKey("US201401281307217050001206");
		request.setDeviceId("01011117774");

		LOGGER.debug("[DeviceSCI-REQUSET] : \n{}", TestConvertMapperUtils.convertObjectToJson(request));
		ChangedDeviceHistorySacRes result = this.deviceSCI.searchChangedDeviceHistory(request);
		assertEquals(result.getIsChanged(), "N");
		assertThat(result.getDeviceKey(), notNullValue());
	}

	// 기기변경 이력 없는 test 가능한 Data
	// DeviceKey / DevieID / UserKey
	// DE201401272118334120000552 / 01001231122 / US201401272118331600001099
	// DE201401281307218960000638 / 01011117774 / US201401281307217050001206
	// DE201401271052494600000403 / 01001231119 / US201401271052494470000808

	/**
	 * <pre>
	 * 기기변경이력 조회 SCI TEST.
	 * - 기기변경 이력 없음. (deviceKey로 조회)
	 * </pre>
	 */
	@Test
	public void testNonChangedDeviceHistoryByDeviceKey() {
		ChangedDeviceHistorySacReq request = new ChangedDeviceHistorySacReq();
		request.setUserKey("US201401272118331600001099");
		request.setDeviceKey("DE201401272118334120000552");

		LOGGER.debug("[DeviceSCI-REQUSET] : \n{}", TestConvertMapperUtils.convertObjectToJson(request));
		ChangedDeviceHistorySacRes result = this.deviceSCI.searchChangedDeviceHistory(request);
		assertEquals(result.getIsChanged(), "N");
		assertEquals(result.getDeviceKey(), request.getDeviceKey());
	}

	/**
	 * <pre>
	 * 기기변경이력 조회 SCI TEST.
	 * - Exception (회원정보 없음 - deviceKey로 조회)
	 * </pre>
	 */
	@Test
	public void testExceptChangedDeviceHistoryByDeviceKey() {
		ChangedDeviceHistorySacReq request = new ChangedDeviceHistorySacReq();
		request.setUserKey("US201401280453225170001216");
		request.setDeviceKey("DE201401280453226380000643");

		LOGGER.debug("[DeviceSCI-REQUSET] : \n{}", TestConvertMapperUtils.convertObjectToJson(request));
		try {
			ChangedDeviceHistorySacRes result = this.deviceSCI.searchChangedDeviceHistory(request);
			assertEquals(result.getIsChanged(), "N");
			assertThat(result.getDeviceKey(), null);
		} catch (StorePlatformException e) {
			assertEquals("SAC_MEM_0003", e.getErrorInfo().getCode());
			LOGGER.info("\nerror >> ", e);
		}
	}

	/**
	 * <pre>
	 * 기기변경이력 조회 SCI TEST.
	 * - Exception (회원정보 없음 - deviceId로 조회)
	 * </pre>
	 */
	@Test
	public void testExceptChangedDeviceHistoryByDeviceId() {
		ChangedDeviceHistorySacReq request = new ChangedDeviceHistorySacReq();
		request.setUserKey("US201401280453225170001216");
		request.setDeviceId("01066786240");

		LOGGER.debug("[DeviceSCI-REQUSET] : \n{}", TestConvertMapperUtils.convertObjectToJson(request));
		try {
			ChangedDeviceHistorySacRes result = this.deviceSCI.searchChangedDeviceHistory(request);
			assertEquals(result.getIsChanged(), "N");
			assertThat(result.getDeviceKey(), null);
		} catch (StorePlatformException e) {
			assertEquals("SAC_MEM_0003", e.getErrorInfo().getCode());
			LOGGER.info("\nerror >> ", e);
		}
	}

}
