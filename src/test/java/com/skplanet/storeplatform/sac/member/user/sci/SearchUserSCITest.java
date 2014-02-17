/**
 * 
 */
package com.skplanet.storeplatform.sac.member.user.sci;

import org.junit.Before;
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
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.SearchUserSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.common.util.MockRequestAttributeInitializer;
import com.skplanet.storeplatform.sac.member.common.util.TestConvertMapperUtils;

/**
 * 회원 정보 조회 내부메소드 인터페이스 Test.
 * 
 * Updated on : 2014. 2. 17. Updated by : 김다슬, 인크로스.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@WebAppConfiguration
public class SearchUserSCITest {

	private static final Logger LOGGER = LoggerFactory.getLogger(SearchUserSCITest.class);

	@Autowired
	private SearchUserSCI searchUserSCI;

	@Before
	public void before() {
		SacRequestHeader sacRequestHeader = new SacRequestHeader();
		TenantHeader tenantHeader = new TenantHeader();
		tenantHeader.setTenantId("S01");
		tenantHeader.setSystemId("S001");
		sacRequestHeader.setTenantHeader(tenantHeader);

		MockRequestAttributeInitializer.init(SacRequestHeader.class.getName(), sacRequestHeader);
	}

	/**
	 * <pre>
	 * UserKey를 이용한 회원정보 조회 SCI TEST.
	 * </pre>
	 */
	@Test
	public void testSearchUserByUserKey() {
		SearchUserSacReq request = new SearchUserSacReq();
		request.setUserKey("US201402110557052730002230");

		SearchUserSacRes result = this.searchUserSCI.searchUserByUserKey(request);
		LOGGER.debug("[SearchUserInternalSCI-REPONSE] : \n{}", TestConvertMapperUtils.convertObjectToJson(result));
	}

	/**
	 * <pre>
	 * UserKey를 이용한 회원정보 조회 SCI TEST.
	 * - Exception
	 * </pre>
	 */
	@Test(expected = StorePlatformException.class)
	public void testExceptSearchUserByUserKey() {
		SearchUserSacReq request = new SearchUserSacReq();
		request.setUserKey("");

		SearchUserSacRes result = this.searchUserSCI.searchUserByUserKey(request);
		LOGGER.debug("[DeviceSCI-REPONSE] : \n{}", TestConvertMapperUtils.convertObjectToJson(result));
	}

}
