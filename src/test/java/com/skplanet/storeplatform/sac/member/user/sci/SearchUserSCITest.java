/**
 * 
 */
package com.skplanet.storeplatform.sac.member.user.sci;

import static org.junit.Assert.assertEquals;

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
	 * - 검색결과 존재.
	 * </pre>
	 */
	@Test
	public void testSearchUserByUserKey() {

		SearchUserSacReq request = new SearchUserSacReq();
		request.setUserKey("IW1023284651220101007215215");

		SearchUserSacRes result = this.searchUserSCI.searchUserByUserKey(request);
		LOGGER.debug("[SearchUserSCI-REPONSE] : \n{}", TestConvertMapperUtils.convertObjectToJson(result));
	}

	/**
	 * <pre>
	 * UserKey를 이용한 회원정보 조회 SCI TEST.
	 * - 검색결과 존재.
	 * 	 deviceId가 여러개.
	 * </pre>
	 */
	@Test
	public void testSearchUserByUserKey2() {

		SearchUserSacReq request = new SearchUserSacReq();
		request.setUserKey("IM142100006719244201304082142");

		SearchUserSacRes result = this.searchUserSCI.searchUserByUserKey(request);
		LOGGER.debug("[SearchUserSCI-REPONSE] : \n{}", TestConvertMapperUtils.convertObjectToJson(result));
	}

	/**
	 * <pre>
	 * UserKey를 이용한 회원정보 조회 SCI TEST.
	 * - 검색결과 없음 (Exception) .
	 * </pre>
	 */
	@Test
	public void testExceptSearchUserByUserKey() {
		SearchUserSacReq request = new SearchUserSacReq();
		request.setUserKey("IW1024171529820110627132506");

		try {
			SearchUserSacRes result = this.searchUserSCI.searchUserByUserKey(request);
			LOGGER.debug("[SearchUserISCI-REPONSE] : \n{}", TestConvertMapperUtils.convertObjectToJson(result));
		} catch (StorePlatformException e) {
			assertEquals("SC_MEM_9995", e.getErrorInfo().getCode());
			LOGGER.info("\nerror >> ", e);
		}
	}
}
