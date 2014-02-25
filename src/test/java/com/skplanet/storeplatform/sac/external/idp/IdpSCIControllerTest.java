package com.skplanet.storeplatform.sac.external.idp;

import org.junit.After;
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

import com.skplanet.storeplatform.external.client.idp.sci.IdpSCI;
import com.skplanet.storeplatform.external.client.idp.vo.AlredyJoinCheckByEmailEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.AlredyJoinCheckByEmailEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.AuthForIdEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.AuthForIdEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.AuthForWapEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.AuthForWapEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.CheckDupIdEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.CommonRes;
import com.skplanet.storeplatform.external.client.idp.vo.DeviceCompareEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.DeviceCompareEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.FindCommonProfileForServerEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.FindCommonProfileForServerEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.FindPasswdEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.FindPasswdEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.FindProfileForWapEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.FindProfileForWapEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.JoinForWapEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.JoinForWapEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.JoinSupServiceRequestEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.JoinSupServiceRequestEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.ModifyAuthInfoEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.ModifyAuthInfoEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.ModifyProfileEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.ModifyProfileEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.SecedeForWapEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.SecedeUserEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.SecedeUserEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.ServiceSubscriptionCheckEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.ServiceSubscriptionCheckEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.SimpleJoinEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.SimpleJoinEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.WaterMarkAuthEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.WaterMarkAuthImageEcRes;

/**
 * Calss 설명
 * 
 * Updated on : 2014. 2. 25. Updated by : Rejoice, Burkhan
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class IdpSCIControllerTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(IdpSCIControllerTest.class);

	@Autowired
	private IdpSCI idpSCI;

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Before
	public void before() {
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@After
	public void after() {
	}

	/**
	 * <pre>
	 * 2.1.2. 서비스 중복 가입 체크. (이메일 기준).
	 * </pre>
	 */
	@Test
	public void testAlredyJoinCheckByEmail() {
		AlredyJoinCheckByEmailEcReq req = new AlredyJoinCheckByEmailEcReq();
		req.setKey("test@test.com");
		AlredyJoinCheckByEmailEcRes res = this.idpSCI.alredyJoinCheckByEmail(req);
		LOGGER.debug(res.toString());
	}

	/**
	 * <pre>
	 * 2.1.13. 아이디 중복 체크.
	 * </pre>
	 */
	@Test
	public void testCheckDupID() {
		CheckDupIdEcReq req = new CheckDupIdEcReq();
		req.setUserId("test12312");
		CommonRes res = this.idpSCI.checkDupId(req);
		LOGGER.debug(res.toString());
	}

	/**
	 * <pre>
	 * 2.1.8. 자동 가입 방지 Image 발급.
	 * </pre>
	 */
	@Test
	public void testWarterMarkImageUrl() {
		WaterMarkAuthImageEcRes res = this.idpSCI.warterMarkImageUrl();
		LOGGER.debug(res.toString());
	}

	/**
	 * <pre>
	 * 2.1.9. 자동 가입 방지 인증.
	 * [POST]
	 * </pre>
	 */
	@Test
	public void testWarterMarkAuth() {
		WaterMarkAuthEcReq req = new WaterMarkAuthEcReq();
		CommonRes res = this.idpSCI.waterMarkAuth(req);
		LOGGER.debug(res.toString());
	}

	/**
	 * <pre>
	 * 2.1.10. 휴대폰 단말 기종 조회 및 업데이트.
	 * </pre>
	 */
	@Test
	public void testDeviceCompare() {
		DeviceCompareEcReq req = new DeviceCompareEcReq();
		DeviceCompareEcRes res = this.idpSCI.deviceCompare(req);
		LOGGER.debug(res.toString());
	}

	/**
	 * <pre>
	 * 2.2.7 간편 회원 가입.
	 * </pre>
	 */
	@Test
	public void testSimpleJoin() {
		SimpleJoinEcReq req = new SimpleJoinEcReq();
		SimpleJoinEcRes res = this.idpSCI.simpleJoin(req);
		LOGGER.debug(res.toString());
	}

	/**
	 * <pre>
	 * 2.3.1. 유선 회원의 로그인.
	 * </pre>
	 */
	@Test
	public void testUserAuthForId() {
		AuthForIdEcReq req = new AuthForIdEcReq();
		AuthForIdEcRes res = this.idpSCI.authForId(req);
		LOGGER.debug(res.toString());
	}

	/**
	 * <pre>
	 * 2.3.2. 유선 회원의 비밀번호 확인.
	 * </pre>
	 */
	@Test
	public void testAuthPwd() {
		// try {
		// this.idpSCI.authPwd("abc", "1234");
		// } catch (StorePlatformException e) {
		// LOGGER.debug("ErrorInfo : \n{}", e.getErrorInfo().toString());
		// throw e;
		// }
	}

	/**
	 * <pre>
	 * 2.4.2. 기본 Profile 조회 (For SP Server).
	 * </pre>
	 */
	@Test
	public void testSearchUserCommonInfo() {
		FindCommonProfileForServerEcReq req = new FindCommonProfileForServerEcReq();
		// 1=id, 2=Email, 3=User Key, default=1
		FindCommonProfileForServerEcRes res = this.idpSCI.findCommonProfileForServer(req);
		LOGGER.debug(res.toString());
	}

	/**
	 * <pre>
	 * 2.4.3. 특정 Profile 조회 (For SO Server).
	 * </pre>
	 */
	@Test
	public void testSearchSpecialProfile() {
		// try {
		// // 1=id, 2=Email, 3=User Key, default=1
		// this.idpSCI.searchSpecialProfile("3", "01088870008");
		// } catch (StorePlatformException e) {
		// LOGGER.debug("ErrorInfo : \n{}", e.getErrorInfo().toString());
		// throw e;
		// }
	}

	/**
	 * <pre>
	 * 2.4.5. 임시 Password 발급.
	 * </pre>
	 */
	@Test
	public void testFindPasswd() {
		FindPasswdEcReq req = new FindPasswdEcReq();
		FindPasswdEcRes res = this.idpSCI.findPasswd(req);
		LOGGER.debug(res.toString());
	}

	/**
	 * <pre>
	 * 2.5.1. 인증 정보 변경 API.
	 * </pre>
	 */
	@Test
	public void testModifyAuthInfo() {
		ModifyAuthInfoEcReq req = new ModifyAuthInfoEcReq();
		// 1=id, 2=Email, 3=User Key, default=1
		ModifyAuthInfoEcRes res = this.idpSCI.modifyAuthInfo(req);
		LOGGER.debug(res.toString());
	}

	/**
	 * <pre>
	 * 2.5.2. 회원 정보 변경.
	 * </pre>
	 */
	@Test
	public void testModifyProfile() {
		ModifyProfileEcReq req = new ModifyProfileEcReq();
		ModifyProfileEcRes res = this.idpSCI.modifyProfile(req);
		LOGGER.debug(res.toString());
	}

	/**
	 * <pre>
	 * 2.6.1. 회원 해지.
	 * </pre>
	 */
	@Test
	public void testSecedeUser() {
		SecedeUserEcReq req = new SecedeUserEcReq();
		SecedeUserEcRes res = this.idpSCI.secedeUser(req);
		LOGGER.debug(res.toString());
	}

	/**
	 * 3. MDN 기반서비스 API.
	 */

	/**
	 * <pre>
	 * 3.1.1. MDN/Password 중복 가입 체크.
	 * </pre>
	 */
	@Test
	public void testAleadyJoinCheckForMdn() {
		// try {
		// this.idpSCI.aleadyJoinCheckForMdn("01088870008");
		// } catch (StorePlatformException e) {
		// LOGGER.debug("ErrorInfo : \n{}", e.getErrorInfo().toString());
		// throw e;
		// }
	}

	/**
	 * <pre>
	 * 3.2.1. 모바일 회원가입.
	 * </pre>
	 */
	@Test
	public void testJoin4Wap() {
		JoinForWapEcReq req = new JoinForWapEcReq();
		JoinForWapEcRes res = this.idpSCI.joinForWap(req);
		LOGGER.debug(res.toString());
	}

	/**
	 * <pre>
	 * 3.3.1. 무선 회원 인증 (For SP Server).
	 * </pre>
	 */
	@Test
	public void testAuthForWap() {
		AuthForWapEcReq req = new AuthForWapEcReq();
		AuthForWapEcRes res = this.idpSCI.authForWap(req);
		LOGGER.debug(res.toString());
	}

	/**
	 * <pre>
	 * 3.4.1 무선 회원 Profile 조회 (For SP Server).
	 * </pre>
	 */
	@Test
	public void testFindProfileForWap() {
		FindProfileForWapEcReq req = new FindProfileForWapEcReq();
		FindProfileForWapEcRes res = this.idpSCI.findProfileForWap(req);
		LOGGER.debug(res.toString());
	}

	/**
	 * <pre>
	 * 3.6.1. 무선 회원 해지 (For SP Server).
	 * </pre>
	 */
	@Test
	public void testSecedeUser4Wap() {
		SecedeForWapEcReq req = new SecedeForWapEcReq();
		CommonRes res = this.idpSCI.secedeForWap(req);
		LOGGER.debug(res.toString());
	}

	/**
	 * 4. 부가서비스 서비스 API.
	 */

	/**
	 * <pre>
	 * 4.1.1. 부가서비스 가입.
	 * </pre>
	 */
	@Test
	public void testJoinSupService() {
		JoinSupServiceRequestEcReq req = new JoinSupServiceRequestEcReq();
		JoinSupServiceRequestEcRes res = this.idpSCI.joinSupServiceRequest(req);
		LOGGER.debug(res.toString());
	}

	/**
	 * <pre>
	 * 4.2.1. 부가서비스 해지.
	 * </pre>
	 */
	@Test
	public void testSecedeSupService() {
		// try {
		// this.idpSCI.secedeSupService("", "", "");
		// } catch (StorePlatformException e) {
		// LOGGER.debug("ErrorInfo : \n{}", e.getErrorInfo().toString());
		// throw e;
		// }
	}

	/**
	 * <pre>
	 * 4.3.2. 부가서비스 가입 여부 조회.
	 * </pre>
	 */
	@Test
	public void testServiceSubscriptionCheck() {
		ServiceSubscriptionCheckEcReq req = new ServiceSubscriptionCheckEcReq();
		ServiceSubscriptionCheckEcRes res = this.idpSCI.serviceSubscriptionCheck(req);
		LOGGER.debug(res.toString());
	}
}
