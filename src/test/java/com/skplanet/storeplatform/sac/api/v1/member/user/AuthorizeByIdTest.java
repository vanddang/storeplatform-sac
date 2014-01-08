package com.skplanet.storeplatform.sac.api.v1.member.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByIdReq;
import com.skplanet.storeplatform.sac.member.user.service.LoginService;

/**
 * ID 기반 회원 인증 (One ID, IDP 회원) Class Test
 * 
 * Updated on : 2014. 1. 8. Updated by : 반범진, 지티소프트
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@TransactionConfiguration
@Transactional
public class AuthorizeByIdTest {

	@Autowired
	private LoginService loginService;

	@Test
	public void shouldDevice() {
		String deviceId = "0101231234";
		String userId = "hdk";
		String userPw = "123qwe";

		AuthorizeByIdReq req = new AuthorizeByIdReq();
		req.setDeviceId(deviceId);
		req.setUserId(userId);
		req.setUserPw(userPw);

		try {
			this.loginService.authorizeById(null, req);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
