/**
 * 
 */
package com.skplanet.storeplatform.sac.member.user.sci;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.DeviceSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchDeviceIdSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchDeviceIdSacRes;
import com.skplanet.storeplatform.sac.member.common.util.TestConvertMapperUtils;

/**
 * Device SCI Test.
 * 
 * Updated on : 2014. 2. 12. Updated by : 김다슬, 인크로스.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@WebAppConfiguration
public class DeviceSCITest {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceSCITest.class);

	@Autowired
	private DeviceSCI deviceSCI;

	/**
	 * <pre>
	 * 단말 MDN 정보 조회 SCI TEST.
	 * - 검색결과 존재.
	 * </pre>
	 */
	@Test
	public void testGetDeviceMdn() {
		SearchDeviceIdSacReq request = new SearchDeviceIdSacReq();
		request.setUserKey("US201402110557052730002230");
		request.setDeviceKey("DE201402120409541480001552");
		SearchDeviceIdSacRes result = this.deviceSCI.searchDeviceId(request);
		assertThat(result.getMsisdn(), notNullValue());

		LOGGER.debug("[DeviceSCI-REPONSE] : \n{}", TestConvertMapperUtils.convertObjectToJson(result));
	}

	/**
	 * <pre>
	 * 단말 MDN 정보 조회 SCI TEST.
	 * - 검색결과 없음.
	 * </pre>
	 */
	@Test(expected = StorePlatformException.class)
	public void testExceptGetDeviceMdn() {
		SearchDeviceIdSacReq request = new SearchDeviceIdSacReq();
		request.setUserKey("US201401241840125650000649");
		request.setDeviceKey("DE201401241840125800000296");
		SearchDeviceIdSacRes result = this.deviceSCI.searchDeviceId(request);
		assertThat(result.getMsisdn(), notNullValue());

		LOGGER.debug("[DeviceSCI-REPONSE] : \n{}", TestConvertMapperUtils.convertObjectToJson(result));
	}
}
