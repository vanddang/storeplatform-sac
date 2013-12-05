package com.skplanet.storeplatform.sac.api.external.uaps;

import static org.junit.Assert.assertNotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.skplanet.external.gw.common.vo.ExMessage;
import com.skplanet.external.gw.uaps.client.sci.UAPSSCI;
import com.skplanet.external.gw.uaps.client.vo.ExOpmdInfo;
import com.skplanet.external.gw.uaps.client.vo.ExUafmapInfo;
import com.skplanet.external.gw.uaps.client.vo.ExUserInfo;

/**
 * 
 * Calss 설명
 * 
 * Updated on : 2013. 11. 28. Updated by : 김현일, 인크로스.
 */
@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring/context-*.xml", "classpath*:/integration/context-*.xml" })
public class UAPSTest extends AbstractTransactionalJUnit4SpringContextTests {

	private static final Logger LOGGER = LoggerFactory.getLogger(UAPSTest.class);

	@Autowired
	private UAPSSCI uapsSCI;

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Before
	public void before() {

	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test
	// @Ignore
	public void getDeviceInfo() {
		ExMessage<ExUafmapInfo> uafmapInfo = this.uapsSCI.getDeviceInfo("010", 1);

		assertNotNull(uafmapInfo);

		this.LOGGER.info("resut : {}", ReflectionToStringBuilder.toString(uafmapInfo, ToStringStyle.MULTI_LINE_STYLE));
	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test
	// @Ignore
	public void getOpmdInfoTest() {
		ExMessage<ExOpmdInfo> opmdInfo = this.uapsSCI.getOpmdInfo("010");

		assertNotNull(opmdInfo);

		this.LOGGER.info("resut : {}", ReflectionToStringBuilder.toString(opmdInfo, ToStringStyle.MULTI_LINE_STYLE));
	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test
	// @Ignore
	public void getMappingInfo() {
		ExMessage<ExUserInfo> userInfo = this.uapsSCI.getMappingInfo("010", 1);

		assertNotNull(userInfo);

		this.LOGGER.info("resut : {}", ReflectionToStringBuilder.toString(userInfo, ToStringStyle.MULTI_LINE_STYLE));
	}

}
