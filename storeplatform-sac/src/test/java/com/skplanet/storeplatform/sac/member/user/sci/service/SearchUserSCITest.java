/**
 * 
 */
package com.skplanet.storeplatform.sac.member.user.sci.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.SearchUserSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchOrderUserByDeviceIdSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchOrderUserByDeviceIdSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserGradeSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserGradeSacRes;
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
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
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
	 * UserKeyList를 이용한 회원정보 조회 SCI TEST.
	 * - 검색결과 존재.
	 * </pre>
	 */
	@Test
	public void testSearchUserByUserKeyList() {

		SearchUserSacReq request = new SearchUserSacReq();

		List<String> userKeyList = new ArrayList<String>();
		userKeyList.add("IW1023284651220101007215215"); // 회원정보에 등록된 deviceId가 한 개.
		userKeyList.add("IM142100006719244201304082142"); // 회원정보에 등록된 deviceId가 여러개.
		userKeyList.add("IW1024171529820110627132506"); // 회원정보 없음.

		request.setUserKeyList(userKeyList);
		LOGGER.debug("[SearchUserSCI-REQUEST] : \n{}", TestConvertMapperUtils.convertObjectToJson(request));

		try {
			SearchUserSacRes result = this.searchUserSCI.searchUserByUserKey(request);
			LOGGER.debug("[SearchUserSCI-REPONSE] : \n{}",
					TestConvertMapperUtils.convertObjectToJson(result.getUserInfo()));
		} catch (StorePlatformException e) {
			LOGGER.info("\nerror >> ", e);
		}
	}

	/**
	 * <pre>
	 * UserKeyList를 이용한 회원정보 조회 SCI TEST.
	 * - 검색결과 존재.
	 * </pre>
	 */
	@Test
	public void testSearchUserByUserKeyList2() {

		SearchUserSacReq request = new SearchUserSacReq();

		List<String> userKeyList = new ArrayList<String>();
		userKeyList.add("IF1023095942020100608204224"); // 가가입.
		userKeyList.add("IF110000000539520120801100314"); // 일시정지.

		request.setUserKeyList(userKeyList);

		LOGGER.debug("[SearchUserSCI-REQUEST] : \n{}", TestConvertMapperUtils.convertObjectToJson(request));
		try {
			SearchUserSacRes result = this.searchUserSCI.searchUserByUserKey(request);
			LOGGER.debug("[SearchUserSCI-REPONSE] : \n{}",
					TestConvertMapperUtils.convertObjectToJson(result.getUserInfo()));
		} catch (StorePlatformException e) {
			LOGGER.info("\nerror >> ", e);
		}
	}

	/**
	 * <pre>
	 * UserKeyList를 이용한 회원정보 조회 SCI TEST.
	 * - 검색결과 없음.
	 * </pre>
	 */
	@Test
	public void testExceptSearchUserByUserKeyList() {

		SearchUserSacReq request = new SearchUserSacReq();

		List<String> userKeyList = new ArrayList<String>();
		userKeyList.add("IW1024171529820110627132506"); // 회원정보 없음.

		request.setUserKeyList(userKeyList);

		LOGGER.debug("[SearchUserSCI-REQUEST] : \n{}", TestConvertMapperUtils.convertObjectToJson(request));
		try {
			SearchUserSacRes result = this.searchUserSCI.searchUserByUserKey(request);
			LOGGER.debug("[SearchUserSCI-REPONSE] : \n{}",
					TestConvertMapperUtils.convertObjectToJson(result.getUserInfo()));
		} catch (StorePlatformException e) {
			assertEquals("SC_MEM_9982", e.getErrorInfo().getCode());
			LOGGER.info("\nerror >> ", e);
		}
	}

	// /**
	// * <pre>
	// * UserKey를 이용한 회원정보 조회 SCI TEST.
	// * - 검색결과 존재. (회원정보에 등록된 deviceId가 한 개)
	// * </pre>
	// */
	// @Test
	// public void testSearchUserByUserKey() {
	//
	// SearchUserSacReq request = new SearchUserSacReq();
	// request.setUserKey("IW1023284651220101007215215");
	//
	// SearchUserSacRes result = this.searchUserSCI.searchUserByUserKey(request);
	// LOGGER.debug("[SearchUserSCI-REPONSE] : \n{}", TestConvertMapperUtils.convertObjectToJson(result));
	// }
	//
	// /**
	// * <pre>
	// * UserKey를 이용한 회원정보 조회 SCI TEST.
	// * - 검색결과 존재. (회원정보에 등록된 deviceId가 여러개)
	// * </pre>
	// */
	// @Test
	// public void testSearchUserByUserKey2() {
	//
	// SearchUserSacReq request = new SearchUserSacReq();
	// request.setUserKey("IM142100006719244201304082142");
	//
	// SearchUserSacRes result = this.searchUserSCI.searchUserByUserKey(request);
	// LOGGER.debug("[SearchUserSCI-REPONSE] : \n{}", TestConvertMapperUtils.convertObjectToJson(result));
	// }
	//
	// /**
	// * <pre>
	// * UserKey를 이용한 회원정보 조회 SCI TEST.
	// * - 검색결과 존재. (회원정보에 등록된 deviceId가 없음)
	// * </pre>
	// */
	// @Test
	// public void testNonDeviceIdForSearchUserByUserKey() {
	//
	// SearchUserSacReq request = new SearchUserSacReq();
	// request.setUserKey("IF1023001443020090915112936");
	//
	// SearchUserSacRes result = this.searchUserSCI.searchUserByUserKey(request);
	// LOGGER.debug("[SearchUserSCI-REPONSE] : \n{}", TestConvertMapperUtils.convertObjectToJson(result));
	// }
	//
	// /**
	// * <pre>
	// * UserKey를 이용한 회원정보 조회 SCI TEST.
	// * - Exception. (검색된 회원정보 없음)
	// * </pre>
	// */
	// @Test
	// public void testExceptSearchUserByUserKey() {
	// SearchUserSacReq request = new SearchUserSacReq();
	// request.setUserKey("IW1024171529820110627132506");
	//
	// try {
	// SearchUserSacRes result = this.searchUserSCI.searchUserByUserKey(request);
	// LOGGER.debug("[SearchUserISCI-REPONSE] : \n{}", TestConvertMapperUtils.convertObjectToJson(result));
	// } catch (StorePlatformException e) {
	// assertEquals("SC_MEM_9995", e.getErrorInfo().getCode());
	// LOGGER.info("\nerror >> ", e);
	// }
	// }

	/**
	 * <pre>
	 * UserKeyList를 이용한 회원정보 조회 SCI TEST.
	 * - 검색결과 없음.
	 * </pre>
	 */
	@Test
	public void testSearchOrderUserByDeviceId() {

		SearchOrderUserByDeviceIdSacReq request = new SearchOrderUserByDeviceIdSacReq();

		request.setDeviceId("01048088875");
		request.setOrderDt("20140324160000");

		LOGGER.debug("[SearchUserSCI-REQUEST] : \n{}", TestConvertMapperUtils.convertObjectToJson(request));

		SearchOrderUserByDeviceIdSacRes res = this.searchUserSCI.searchOrderUserByDeviceId(request);
		LOGGER.debug("[SearchUserSCI-REPONSE] : \n{}", TestConvertMapperUtils.convertObjectToJson(res));
	}

	/**
	 * <pre>
	 * 회원 등급정보 조회 SCI TEST.
	 * </pre>
	 */
	@Test
	public void testSearchUserGrade() {

		SearchUserGradeSacReq request = new SearchUserGradeSacReq();

		request.setUserKey("IM190000008406220140408181722");

		LOGGER.debug("[SearchUserSCI-REQUEST] : \n{}", TestConvertMapperUtils.convertObjectToJson(request));

		SearchUserGradeSacRes res = this.searchUserSCI.searchUserGrade(request);
		LOGGER.debug("[SearchUserSCI-REPONSE] : \n{}", TestConvertMapperUtils.convertObjectToJson(res));
	}
}
