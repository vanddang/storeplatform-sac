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

import com.skplanet.storeplatform.external.client.idp.sci.ImIdpSCI;
import com.skplanet.storeplatform.external.client.idp.vo.CommonRes;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.AgreeUserEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.AgreeUserEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.AuthForIdEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.AuthForIdEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.CheckIdStatusIdpImEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.CheckIdStatusIdpImEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.DiscardUserEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.DiscardUserEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.FindJoinServiceListIDPEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.FindJoinServiceListIDPEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.FindUserIdByMdnEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.GetMdnInfoIdpEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.GetMdnInfoIdpEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.ModifyPwdEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.ModifyPwdEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.ResetUserPwdIdpEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.ResetUserPwdIdpEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.SetLoginStatusEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.SetLoginStatusEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.UpdateAdditionalInfoEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.UpdateAdditionalInfoEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.UpdateGuardianEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.UpdateGuardianEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.UpdateUserInfoEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.UpdateUserInfoEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.UpdateUserNameEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.UpdateUserNameEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.UserInfoIdpSearchServerEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.UserInfoIdpSearchServerEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.UserInfoSearchServerEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.UserInfoSearchServerEcRes;

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class ImIdpSCIControllerTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ImIdpSCIControllerTest.class);

	@Autowired
	private ImIdpSCI imIdpSCI;

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
	 * 2.1.2. 서비스 이용 동의.
	 * </pre>
	 */
	@Test
	public void testAgreeUser() {
		AgreeUserEcReq req = new AgreeUserEcReq();
		AgreeUserEcRes res = this.imIdpSCI.agreeUser(req);
		LOGGER.debug(res.toString());
	}

	/**
	 * <pre>
	 * 2.1.4. 개별약관해지요청.
	 * </pre>
	 */
	@Test
	public void testDiscardUser() {
		DiscardUserEcReq req = new DiscardUserEcReq();
		DiscardUserEcRes res = this.imIdpSCI.discardUser(req);
		LOGGER.debug(res.toString());
	}

	/**
	 * <pre>
	 * 2.1.6 공통프로파일조회요청 (For Server).
	 * </pre>
	 */
	@Test
	public void testUserInfoSearchServer() {
		UserInfoSearchServerEcReq req = new UserInfoSearchServerEcReq();
		UserInfoSearchServerEcRes res = this.imIdpSCI.userInfoSearchServer(req);
		LOGGER.debug(res.toString());
	}

	/**
	 * <pre>
	 * 2.1.7 공통프로파일정보수정요청.
	 * </pre>
	 */
	@Test
	public void testUpdateUserInfo() {
		UpdateUserInfoEcReq req = new UpdateUserInfoEcReq();
		UpdateUserInfoEcRes res = this.imIdpSCI.updateUserInfo(req);
		LOGGER.debug(res.toString());
	}

	/**
	 * <pre>
	 * 2.1.8 부가프로파일정보수정요청.
	 * </pre>
	 */
	@Test
	public void testUpdateAdditionalInfo() {
		UpdateAdditionalInfoEcReq req = new UpdateAdditionalInfoEcReq();
		UpdateAdditionalInfoEcRes res = this.imIdpSCI.updateAdditionalInfo(req);
		LOGGER.debug(res.toString());
	}

	/**
	 * <pre>
	 * 2.1.9 비밀번호변경요청.
	 * </pre>
	 */
	@Test
	public void testModifyPwd() {
		ModifyPwdEcReq req = new ModifyPwdEcReq();
		ModifyPwdEcRes res = this.imIdpSCI.modifyPwd(req);
		LOGGER.debug(res.toString());
	}

	/**
	 * <pre>
	 * 2.1.10. 비밀번호초기화및 임시비밀번호발급.
	 * </pre>
	 */
	@Test
	public void testResetUserPwdIdp() {
		ResetUserPwdIdpEcReq req = new ResetUserPwdIdpEcReq();
		ResetUserPwdIdpEcRes res = this.imIdpSCI.resetUserPwdIdp(req);
		LOGGER.debug(res.toString());
	}

	/**
	 * <pre>
	 * 2.1.20 로그인 상태 정보 변경 요청.
	 * </pre>
	 */
	@Test
	public void testSetLoginStatus() {
		SetLoginStatusEcReq req = new SetLoginStatusEcReq();
		SetLoginStatusEcRes res = this.imIdpSCI.setLoginStatus(req);
		LOGGER.debug(res.toString());
	}

	/**
	 * <pre>
	 * 2.1.24 실명변경 요청.
	 * </pre>
	 */
	@Test
	public void testUpdateUserName() {
		UpdateUserNameEcReq req = new UpdateUserNameEcReq();
		UpdateUserNameEcRes res = this.imIdpSCI.updateUserName(req);
		LOGGER.debug(res.toString());
	}

	/**
	 * <pre>
	 * 2.1.25 법정대리인 동의정보 변경 요청.
	 * </pre>
	 */
	@Test
	public void testUpdateGuardian() {
		UpdateGuardianEcReq req = new UpdateGuardianEcReq();
		UpdateGuardianEcRes res = this.imIdpSCI.updateGuardian(req);
		LOGGER.debug(res.toString());
	}

	/** 2.1.26 RX 배포 Retry 요청. */
	/** 2.2.3 MDN 중복체크. */
	/** 2.2.4 MDN 조회 (SKT, non-SKT 구분). */

	/**
	 * <pre>
	 * 2.2.5 통합 ID 회원로그인.
	 * </pre>
	 */
	@Test
	public void testAuthForId() {
		AuthForIdEcReq req = new AuthForIdEcReq();
		AuthForIdEcRes res = this.imIdpSCI.authForId(req);
		LOGGER.debug(res.toString());
	}

	/**
	 * <pre>
	 * 2.2.6. 통합 ID 서비스 가입리스트 조회.
	 * </pre>
	 */
	@Test
	public void testFindJoinServiceListIDP() {
		FindJoinServiceListIDPEcReq req = new FindJoinServiceListIDPEcReq();
		FindJoinServiceListIDPEcRes res = this.imIdpSCI.findJoinServiceListIDP(req);
		LOGGER.debug(res.toString());
	}

	/**
	 * <pre>
	 * 2.2.9 ID 가입여부 체크.
	 * </pre>
	 */
	@Test
	public void testCheckIdStatusIdpIm() {
		CheckIdStatusIdpImEcReq req = new CheckIdStatusIdpImEcReq();
		CheckIdStatusIdpImEcRes res = this.imIdpSCI.checkIdStatusIdpIm(req);
		LOGGER.debug(res.toString());
	}

	/**
	 * <pre>
	 * 2.2.11 MDN 정보 조회 (SKT 가입자).
	 * </pre>
	 */
	@Test
	public void testGetMdnInfoIDP() {
		GetMdnInfoIdpEcReq req = new GetMdnInfoIdpEcReq();
		GetMdnInfoIdpEcRes res = this.imIdpSCI.getMdnInfoIDP(req);
		LOGGER.debug(res.toString());
	}

	/**
	 * <pre>
	 * 2.2.12 IM 통합회원 ID 찾기.
	 * </pre>
	 */
	@Test
	public void testFindUserIdByMdn() {
		FindUserIdByMdnEcReq req = new FindUserIdByMdnEcReq();
		CommonRes res = this.imIdpSCI.findUserIdByMdn(req);
		LOGGER.debug(res.toString());
	}

	/** 2.2.13 이용동의 가능여부 조회. */

	/**
	 * <pre>
	 * 2.2.15. 기본 프로파일 조회(For Server).
	 * </pre>
	 */
	@Test
	public void testUserInfoIdpSearchServer() {
		UserInfoIdpSearchServerEcReq req = new UserInfoIdpSearchServerEcReq();
		UserInfoIdpSearchServerEcRes res = this.imIdpSCI.userInfoIdpSearchServer(req);
		LOGGER.debug(res.toString());
	}
}
